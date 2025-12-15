package dao;

import connect.DBConnection;
import model.ProductDetail;
import java.sql.*;

public class ProductDetailDAO {

    // ✅ PHƯƠNG THỨC SỬA ĐỔI: Thêm chi tiết sản phẩm, nhận Connection
    public boolean addProductDetail(ProductDetail detail, Connection conn) throws SQLException {
        String sql = "INSERT INTO ProductDetail (ProductId, CpuId, GpuId, RamId, StorageId, ScreenId, DateCreate, Status) " +
                     "VALUES (?, ?, ?, ?, ?, ?, NOW(), ?)";
        
        // Sử dụng conn được truyền vào, KHÔNG MỞ/ĐÓNG kết nối
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setLong(1, detail.getProductId());
            
            if (detail.getCpuId() != null) ps.setLong(2, detail.getCpuId()); else ps.setNull(2, Types.BIGINT);
            if (detail.getGpuId() != null) ps.setLong(3, detail.getGpuId()); else ps.setNull(3, Types.BIGINT);
            if (detail.getRamId() != null) ps.setLong(4, detail.getRamId()); else ps.setNull(4, Types.BIGINT);
            if (detail.getStorageId() != null) ps.setLong(5, detail.getStorageId()); else ps.setNull(5, Types.BIGINT);
            if (detail.getScreenId() != null) ps.setLong(6, detail.getScreenId()); else ps.setNull(6, Types.BIGINT);
            
            ps.setInt(7, detail.getStatus());

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
            
        } 
        // KHÔNG CÓ try/catch, để SQLException propagate ra ngoài
    }
    // Lấy chi tiết cấu hình theo ProductID
    public ProductDetail getDetailByProductId(long productId) {
        String sql = "SELECT * FROM ProductDetail WHERE ProductId = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ProductDetail pd = new ProductDetail();
                    pd.setId(rs.getLong("Id"));
                    pd.setProductId(rs.getLong("ProductId"));
                    pd.setCpuId(rs.getObject("CpuId") != null ? rs.getLong("CpuId") : null);
                    pd.setGpuId(rs.getObject("GpuId") != null ? rs.getLong("GpuId") : null);
                    pd.setRamId(rs.getObject("RamId") != null ? rs.getLong("RamId") : null);
                    pd.setStorageId(rs.getObject("StorageId") != null ? rs.getLong("StorageId") : null);
                    pd.setScreenId(rs.getObject("ScreenId") != null ? rs.getLong("ScreenId") : null);
                    return pd;
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    // Cập nhật ProductDetail (Dùng cho phương thức POST)
    public boolean updateProductDetail(ProductDetail detail, Connection conn) throws SQLException {
        String sql = "UPDATE ProductDetail SET CpuId=?, GpuId=?, RamId=?, StorageId=?, ScreenId=?, DateUpdate=NOW() WHERE ProductId=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            if (detail.getCpuId() != null) ps.setLong(1, detail.getCpuId()); else ps.setNull(1, Types.BIGINT);
            if (detail.getGpuId() != null) ps.setLong(2, detail.getGpuId()); else ps.setNull(2, Types.BIGINT);
            if (detail.getRamId() != null) ps.setLong(3, detail.getRamId()); else ps.setNull(3, Types.BIGINT);
            if (detail.getStorageId() != null) ps.setLong(4, detail.getStorageId()); else ps.setNull(4, Types.BIGINT);
            if (detail.getScreenId() != null) ps.setLong(5, detail.getScreenId()); else ps.setNull(5, Types.BIGINT);
            
            ps.setLong(6, detail.getProductId());
            
            return ps.executeUpdate() > 0;
        }
    }
}