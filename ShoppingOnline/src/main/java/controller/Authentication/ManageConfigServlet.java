package controller.Authentication;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import model.*;
import dao.*;
import connect.DBConnection;

@WebServlet("/admin/manageConfig")
public class ManageConfigServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr == null || idStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/admin/productManagement");
            return;
        }

        try {
            long productId = Long.parseLong(idStr);
            ProductDAO productDAO = new ProductDAO();
            Product p = productDAO.getProductById(productId);

            if (p == null) {
                response.sendRedirect(request.getContextPath() + "/admin/productManagement?error=notFound");
                return;
            }
            request.setAttribute("product", p);

            // Lấy thông tin chi tiết cấu hình cũ
            ProductDetailDAO detailDAO = new ProductDetailDAO();
            ComponentDAO compDAO = new ComponentDAO();
            ProductDetail detail = detailDAO.getDetailByProductId(productId);
            
            if (detail != null) {
                if (detail.getCpuId() != null) request.setAttribute("cpu", compDAO.getCpuById(detail.getCpuId()));
                if (detail.getGpuId() != null) request.setAttribute("gpu", compDAO.getGpuById(detail.getGpuId()));
                if (detail.getRamId() != null) request.setAttribute("ram", compDAO.getRamById(detail.getRamId()));
                if (detail.getStorageId() != null) request.setAttribute("storage", compDAO.getStorageById(detail.getStorageId()));
                if (detail.getScreenId() != null) request.setAttribute("screen", compDAO.getScreenById(detail.getScreenId()));
            }

            request.getRequestDispatcher("/admin/manageConfig.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin/productManagement");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String productIdStr = request.getParameter("productId");
        
        if (productIdStr == null || productIdStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/admin/productManagement?error=missingId");
            return;
        }

        long productId = Long.parseLong(productIdStr);
        Connection conn = null;

        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false); 

            ComponentDAO compDao = new ComponentDAO();

            // 1. Xử lý CPU
            Long cpuId = null;
            String cpuCode = request.getParameter("cpuCode");
            if (cpuCode != null && !cpuCode.trim().isEmpty()) {
                Cpu cpu = new Cpu();
                cpu.setCode(cpuCode);
                cpu.setName(request.getParameter("cpuName"));
                cpu.setCores(parseInteger(request.getParameter("cpuCores")));
                cpu.setThreads(parseInteger(request.getParameter("cpuThreads")));
                cpu.setBaseClockGHz(parseFloat(request.getParameter("cpuBase")));
                cpu.setTurboClockGHz(parseFloat(request.getParameter("cpuTurbo")));
                cpuId = compDao.saveAndGetCpuId(cpu, conn);
            }

            // 2. Xử lý GPU
            Long gpuId = null;
            String gpuCode = request.getParameter("gpuCode");
            if (gpuCode != null && !gpuCode.trim().isEmpty()) {
                Gpu gpu = new Gpu();
                gpu.setCode(gpuCode);
                gpu.setName(request.getParameter("gpuName"));
                gpu.setVramGB(parseInteger(request.getParameter("gpuVram")));
                gpuId = compDao.saveAndGetGpuId(gpu, conn);
            }

            // 3. Xử lý RAM
            Long ramId = null;
            String ramCode = request.getParameter("ramCode");
            if (ramCode != null && !ramCode.trim().isEmpty()) {
                Ram ram = new Ram();
                ram.setCode(ramCode);
                ram.setSizeGB(parseInteger(request.getParameter("ramSize")));
                ram.setType(request.getParameter("ramType"));
                ramId = compDao.saveAndGetRamId(ram, conn);
            }

            // 4. Xử lý Storage
            Long storageId = null;
            String storageCode = request.getParameter("storageCode");
            if (storageCode != null && !storageCode.trim().isEmpty()) {
                Storage storage = new Storage();
                storage.setCode(storageCode);
                storage.setCapacityGB(parseInteger(request.getParameter("storageCapacity")));
                storage.setType(request.getParameter("storageType"));
                storageId = compDao.saveAndGetStorageId(storage, conn);
            }

            // 5. Xử lý Screen
            Long screenId = null;
            String screenCode = request.getParameter("screenCode");
            if (screenCode != null && !screenCode.trim().isEmpty()) {
                Screen screen = new Screen();
                screen.setCode(screenCode);
                screen.setSizeInch(parseFloat(request.getParameter("screenSize")));
                screen.setResolution(request.getParameter("screenRes"));
                screen.setPanelType(request.getParameter("screenPanel"));
                screen.setRefreshRate(parseInteger(request.getParameter("screenRefresh")));
                screenId = compDao.saveAndGetScreenId(screen, conn);
            }

            // 6. Cập nhật bảng ProductDetail
            ProductDetail detail = new ProductDetail();
            detail.setProductId(productId);
            detail.setCpuId(cpuId);
            detail.setGpuId(gpuId);
            detail.setRamId(ramId);
            detail.setStorageId(storageId);
            detail.setScreenId(screenId);
            
            ProductDetailDAO detailDao = new ProductDetailDAO();
            ProductDetail existingDetail = detailDao.getDetailByProductId(productId);
            
            if (existingDetail == null) {
                detail.setStatus(1);
                detailDao.addProductDetail(detail, conn);
            } else {
                detailDao.updateProductDetail(detail, conn);
            }

            conn.commit(); 
            response.sendRedirect(request.getContextPath() + "/admin/productManagement?update=success_config");

        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) try { conn.rollback(); } catch (SQLException ex) {}
            response.sendRedirect(request.getContextPath() + "/admin/productManagement?update=fail_config");
        } finally {
            if (conn != null) try { conn.close(); } catch (SQLException ex) {}
        }
    }

    private Integer parseInteger(String value) {
        if (value == null || value.trim().isEmpty()) return null;
        try { return Integer.parseInt(value.trim()); } catch (Exception e) { return null; }
    }

    private Float parseFloat(String value) {
        if (value == null || value.trim().isEmpty()) return null;
        try { return Float.parseFloat(value.trim()); } catch (Exception e) { return null; }
    }
}