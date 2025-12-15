<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Thêm sản phẩm & Cấu hình - SmartLaptop Admin</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">

    <style>
        body {
            margin: 0;
            font-family: "Segoe UI", sans-serif;
            background: linear-gradient(135deg, #3a7bd5, #3a6073);
            color: #333;
            overflow-x: hidden;
            animation: fadePage 0.6s ease-in;
        }

        @keyframes fadePage {
            from { opacity: 0; transform: translateY(20px); }
            to { opacity: 1; transform: translateY(0); }
        }

        .main-content {
            margin-left: 270px; /* Sidebar width */
            padding: 50px 40px;
            min-height: 100vh;
        }

        .form-card {
            background: #fff;
            border-radius: 20px;
            box-shadow: 0 10px 40px rgba(0, 0, 0, 0.08);
            padding: 40px 50px;
            max-width: 1000px; /* Tăng chiều rộng để chứa nhiều cột hơn */
            margin: 0 auto;
            transition: 0.4s;
            animation: fadeIn 0.7s ease-in-out;
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(40px); }
            to { opacity: 1; transform: translateY(0); }
        }

        h2 { font-weight: 700; font-size: 28px; color: #222; }
        h5 { font-weight: 600; color: #444; }

        .form-control, .form-select {
            border-radius: 10px;
            border: 1px solid #ccc;
            padding: 10px 15px;
        }

        .form-control:focus, .form-select:focus {
            border-color: #667eea;
            box-shadow: 0 0 0 3px rgba(102,126,234,0.2);
        }

        /* Accordion Custom Style */
        .accordion-button {
            background-color: #f1f5f9;
            color: #333;
            font-weight: 600;
        }
        .accordion-button:not(.collapsed) {
            background-color: #e0e7ff;
            color: #4f46e5;
            box-shadow: inset 0 -1px 0 rgba(0,0,0,.125);
        }
        .accordion-body {
            background-color: #fff;
        }

        .btn-submit {
            background: linear-gradient(135deg, #667eea, #764ba2);
            color: white;
            font-weight: 600;
            border: none;
            padding: 13px 35px;
            border-radius: 12px;
            box-shadow: 0 6px 15px rgba(118, 75, 162, 0.3);
            transition: 0.3s;
        }
        .btn-submit:hover {
            transform: translateY(-2px);
            box-shadow: 0 8px 18px rgba(118, 75, 162, 0.4);
        }
        
        .image-preview {
            max-width: 150px;
            margin-top: 10px;
            display: none;
            border-radius: 8px;
            border: 1px solid #ddd;
        }
    </style>
</head>

<body>
<%@ include file="sidebar.jsp" %>

<div class="main-content fade-in">
    <div class="form-card">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2><i class="fas fa-plus-circle me-2"></i> Thêm sản phẩm & Cấu hình</h2>
            <a href="${pageContext.request.contextPath}/admin/productManagement" class="btn btn-outline-secondary">
                <i class="fas fa-arrow-left me-1"></i> Quay lại
            </a>
        </div>

        <form action="${pageContext.request.contextPath}/admin/productManagement" method="post" enctype="multipart/form-data">
            <input type="hidden" name="action" value="add">

            <h5 class="mb-3 text-primary border-bottom pb-2">1. Thông tin chung</h5>
            
            <div class="row mb-3">
                <div class="col-md-6">
                    <label class="form-label">Mã sản phẩm (SKU)</label>
                    <input type="text" class="form-control" name="productCode" placeholder="VD: LAP-DELL-001" required>
                </div>
                <div class="col-md-6">
                    <label class="form-label">Tên sản phẩm</label>
                    <input type="text" class="form-control" name="productName" placeholder="VD: Dell XPS 13 Plus" required>
                </div>
            </div>

            <div class="row mb-3">
                <div class="col-md-6">
                    <label class="form-label">Danh mục</label>
                    <select class="form-select" name="categoryId" required>
                        <option value="">-- Chọn danh mục --</option>
                        <c:forEach var="cat" items="${categoryList}">
                            <option value="${cat.categoryId}">${cat.categoryName}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-md-6">
                    <label class="form-label">Thương hiệu</label>
                    <select class="form-select" name="brandId" required>
                        <option value="">-- Chọn thương hiệu --</option>
                        <c:forEach var="brand" items="${brandList}">
                            <option value="${brand.brandId}">${brand.brandName}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="row mb-3">
                <div class="col-md-4">
                    <label class="form-label">Giá nhập</label>
                    <div class="input-group">
                        <input type="number" class="form-control" name="priceImport" placeholder="10000000" required>
                        <span class="input-group-text">VNĐ</span>
                    </div>
                </div>
                <div class="col-md-4">
                    <label class="form-label">Giá bán</label>
                    <div class="input-group">
                        <input type="number" class="form-control" name="price" placeholder="12000000" required>
                        <span class="input-group-text">VNĐ</span>
                    </div>
                </div>
                <div class="col-md-4">
                    <label class="form-label">Số lượng</label>
                    <input type="number" class="form-control" name="quantity" value="1" min="1" required>
                </div>
            </div>

            <div class="mb-4">
                <label class="form-label">Hình ảnh</label>
                <input type="file" name="imageFile" class="form-control" accept="image/*" onchange="previewImage(this)">
                <img id="preview" class="image-preview">
            </div>

            
            </div>
            <div class="mt-4">
                <label class="form-label">Mô tả sản phẩm (Marketing)</label>
                <textarea class="form-control" name="description" rows="3" placeholder="Mô tả chi tiết về sản phẩm..."></textarea>
            </div>

            <div class="mt-4">
                <label class="form-label fw-bold">Trạng thái:</label>
                <div class="d-flex gap-4">
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="status" id="inStock" value="1" checked>
                        <label class="form-check-label" for="inStock">Còn hàng</label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="status" id="outStock" value="0">
                        <label class="form-check-label" for="outStock">Hết hàng</label>
                    </div>
                </div>
            </div>

            <div class="text-end mt-5">
                <button type="submit" class="btn-submit btn-lg">
                    <i class="fas fa-save me-2"></i> Lưu sản phẩm
                </button>
            </div>
        </form>
    </div>
</div>

<script>
    function previewImage(input) {
        const preview = document.getElementById('preview');
        if (input.files && input.files[0]) {
            const reader = new FileReader();
            reader.onload = function(e) {
                preview.src = e.target.result;
                preview.style.display = 'block';
            }
            reader.readAsDataURL(input.files[0]);
        } else {
            preview.style.display = 'none';
        }
    }
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>