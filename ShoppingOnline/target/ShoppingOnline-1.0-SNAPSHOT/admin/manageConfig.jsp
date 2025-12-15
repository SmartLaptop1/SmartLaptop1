<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Cấu hình chi tiết: ${product.productName}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <style>
        body { 
            background: #333; 
            font-family: 'Segoe UI', sans-serif; 
            margin: 0; 
        }

        
        .main-content {
            margin-left: 260px;
            padding: 30px;
            min-height: 100vh;
            transition: margin-left 0.3s;
        }

        .config-card { 
            background: white; 
            border-radius: 12px; 
            padding: 30px; 
            box-shadow: 0 5px 20px rgba(0,0,0,0.08); 
           
            margin: 0 auto; 
        }

        .section-title { 
            color: #2c3e50; 
            border-bottom: 2px solid #e9ecef; 
            padding-bottom: 10px; 
            margin-bottom: 20px; 
            margin-top: 30px;
            font-weight: 700; 
            text-transform: uppercase;
            font-size: 1.1rem;
        }
        
        .header-box { display: flex; justify-content: space-between; align-items: center; margin-bottom: 30px; }
        .form-label { font-weight: 600; font-size: 0.9rem; color: #555; }
    </style>
</head>
<body>

<%@ include file="sidebar.jsp" %>

<div class="main-content">
    
    <div class="container-fluid"> <div class="config-card">
            
            <div class="header-box">
                <div>
                    <h3 class="mb-1"><i class="fas fa-microchip text-primary me-2"></i>Cấu hình kỹ thuật</h3>
                    <div class="text-muted">Sản phẩm: <strong>${product.productName}</strong> (Mã: ${product.productCode})</div>
                </div>
                <a href="${pageContext.request.contextPath}/admin/productManagement" class="btn btn-outline-secondary">
                    <i class="fas fa-arrow-left me-1"></i> Quay lại
                </a>
            </div>

            <form action="${pageContext.request.contextPath}/admin/manageConfig" method="post">
                <input type="hidden" name="productId" value="${product.productId}">

                <h5 class="section-title"><i class="fas fa-memory me-2"></i>Bộ vi xử lý (CPU)</h5>
                <div class="row g-3">
                    <div class="col-md-3">
                        <label class="form-label">Mã CPU (Code) <span class="text-danger">*</span></label>
                        <input type="text" class="form-control" name="cpuCode" value="${cpu.code}" placeholder="VD: I7-13700H">
                    </div>
                    <div class="col-md-5">
                        <label class="form-label">Tên đầy đủ (Name) <span class="text-danger">*</span></label>
                        <input type="text" class="form-control" name="cpuName" value="${cpu.name}">
                    </div>
                    <div class="col-md-2">
                        <label class="form-label">Số nhân</label>
                        <input type="number" class="form-control" name="cpuCores" value="${cpu.cores}">
                    </div>
                    <div class="col-md-2">
                        <label class="form-label">Số luồng</label>
                        <input type="number" class="form-control" name="cpuThreads" value="${cpu.threads}">
                    </div>
                    <div class="col-md-3">
                        <label class="form-label">Xung cơ bản</label>
                        <input type="number" step="0.1" class="form-control" name="cpuBase" value="${cpu.baseClockGHz}">
                    </div>
                    <div class="col-md-3">
                        <label class="form-label">Xung tối đa</label>
                        <input type="number" step="0.1" class="form-control" name="cpuTurbo" value="${cpu.turboClockGHz}">
                    </div>
                </div>

                <h5 class="section-title mt-4"><i class="fas fa-gamepad me-2"></i>Đồ họa (GPU)</h5>
                <div class="row g-3">
                    <div class="col-md-3">
                        <label class="form-label">Mã GPU (Code) <span class="text-danger">*</span></label>
                        <input type="text" class="form-control" name="gpuCode" value="${gpu.code}">
                    </div>
                    <div class="col-md-6">
                        <label class="form-label">Tên đầy đủ (Name) <span class="text-danger">*</span></label>
                        <input type="text" class="form-control" name="gpuName" value="${gpu.name}">
                    </div>
                    <div class="col-md-3">
                        <label class="form-label">VRAM (GB)</label>
                        <input type="number" class="form-control" name="gpuVram" value="${gpu.vramGB}">
                    </div>
                </div>

                <h5 class="section-title mt-4"><i class="fas fa-hdd me-2"></i>RAM</h5>
                <div class="row g-3">
                    <div class="col-md-4">
                        <label class="form-label">Mã RAM <span class="text-danger">*</span></label>
                        <input type="text" class="form-control" name="ramCode" value="${ram.code}">
                    </div>
                    <div class="col-md-4">
                        <label class="form-label">Dung lượng (GB)</label>
                        <input type="number" class="form-control" name="ramSize" value="${ram.sizeGB}">
                    </div>
                    <div class="col-md-4">
                        <label class="form-label">Loại RAM</label>
                        <input type="text" class="form-control" name="ramType" value="${ram.type}">
                    </div>
                </div>

                <h5 class="section-title mt-4"><i class="fas fa-database me-2"></i>Ổ cứng</h5>
                <div class="row g-3">
                    <div class="col-md-4">
                        <label class="form-label">Mã ổ cứng <span class="text-danger">*</span></label>
                        <input type="text" class="form-control" name="storageCode" value="${storage.code}">
                    </div>
                    <div class="col-md-4">
                        <label class="form-label">Dung lượng (GB)</label>
                        <input type="number" class="form-control" name="storageCapacity" value="${storage.capacityGB}">
                    </div>
                    <div class="col-md-4">
                        <label class="form-label">Loại ổ cứng</label>
                        <input type="text" class="form-control" name="storageType" value="${storage.type}">
                    </div>
                </div>

                <h5 class="section-title mt-4"><i class="fas fa-desktop me-2"></i>Màn hình</h5>
                <div class="row g-3">
                    <div class="col-md-3">
                        <label class="form-label">Mã màn hình <span class="text-danger">*</span></label>
                        <input type="text" class="form-control" name="screenCode" value="${screen.code}">
                    </div>
                    <div class="col-md-2">
                        <label class="form-label">Kích thước</label>
                        <input type="number" step="0.1" class="form-control" name="screenSize" value="${screen.sizeInch}">
                    </div>
                    <div class="col-md-3">
                        <label class="form-label">Độ phân giải</label>
                        <input type="text" class="form-control" name="screenRes" value="${screen.resolution}">
                    </div>
                    <div class="col-md-2">
                        <label class="form-label">Tấm nền</label>
                        <input type="text" class="form-control" name="screenPanel" value="${screen.panelType}">
                    </div>
                    <div class="col-md-2">
                        <label class="form-label">Tần số quét</label>
                        <input type="number" class="form-control" name="screenRefresh" value="${screen.refreshRate}">
                    </div>
                </div>

                <div class="d-grid gap-2 mt-5">
                    <button type="submit" class="btn btn-primary btn-lg">
                        <i class="fas fa-save me-2"></i> LƯU CẤU HÌNH
                    </button>
                </div>
            </form>
        </div>
    </div>
</div> </body>
</html>