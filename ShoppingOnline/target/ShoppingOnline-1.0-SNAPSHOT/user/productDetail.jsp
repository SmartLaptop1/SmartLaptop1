<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="dao.ProductDAO, dao.ProductDetailDAO, dao.ComponentDAO, dao.ReviewDAO" %>
<%@ page import="model.Product, model.ProductDetail, model.Review" %>
<%@ page import="model.Cpu, model.Gpu, model.Ram, model.Storage, model.Screen" %>
<%@ page import="java.util.*" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Chi tiết sản phẩm - TechMart</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css"/>
        <style>
            body {
                background-color: #f8f9fa;
                font-family: "Roboto", sans-serif;
            }
            main {
                margin-bottom: 80px;
            }
            .product-detail {
                background: white;
                padding: 30px;
                border-radius: 12px;
                box-shadow: 0 4px 20px rgba(0,0,0,0.1);
            }
            .product-img img {
                max-width: 100%;
                border-radius: 10px;
                border: 1px solid #eee;
            }
            .product-info h2 {
                font-weight: 700;
                margin-bottom: 10px;
                color: #333;
            }
            .product-info .price {
                color: #d70018;
                font-size: 24px;
                font-weight: bold;
                margin-bottom: 20px;
            }
            .product-info p {
                font-size: 15px;
                color: #333;
            }
            .btn-add-cart {
                background-color: #0d6efd;
                color: white;
                border: none;
                padding: 12px 20px;
                border-radius: 8px;
                transition: all 0.3s;
                font-weight: 600;
                width: 100%;
            }
            .btn-add-cart:hover {
                background-color: #084298;
                transform: translateY(-2px);
            }
            .btn-back {
                background-color: #e9ecef;
                color: #495057;
                border: none;
                padding: 8px 18px;
                border-radius: 8px;
                font-weight: 500;
                transition: all 0.3s ease;
                text-decoration: none;
            }
            .btn-back:hover {
                background-color: #ced4da;
                color: #212529;
                transform: translateX(-3px);
            }
            .stock-status {
                font-weight: bold;
                color: #198754;
            }
            .stock-status.out-of-stock {
                color: #dc3545;
            }

            /* Style cho bảng cấu hình */
            .specs-table {
                margin-top: 15px;
                border: 1px solid #dee2e6;
            }
            .specs-table th {
                width: 30%;
                background-color: #f8f9fa;
                color: #495057;
                font-weight: 600;
            }
            .specs-table td {
                color: #212529;
            }
            .spec-section-title {
                font-size: 1.1rem;
                font-weight: 700;
                margin-top: 25px;
                margin-bottom: 15px;
                color: #495057;
                border-bottom: 2px solid #e9ecef;
                padding-bottom: 5px;
            }

            /* Review section */
            .review-section {
                margin-top: 60px;
            }
            .review-card {
                border-radius: 10px;
                box-shadow: 0 2px 6px rgba(0,0,0,0.08);
            }
            .review-header {
                display: flex;
                justify-content: space-between;
                align-items: center;
            }
            .rating-filter {
                display: flex;
                flex-wrap: wrap;
                gap: 10px;
                margin-bottom: 25px;
            }
            .rating-btn {
                border: 1px solid #ccc;
                border-radius: 20px;
                background: white;
                color: #333;
                padding: 6px 16px;
                font-size: 15px;
                cursor: pointer;
                transition: all 0.25s;
                text-decoration: none;
            }
            .rating-btn:hover {
                border-color: #ee4d2d;
                color: #ee4d2d;
            }
            .rating-btn.active {
                background: #ffeee8;
                border-color: #ee4d2d;
                color: #ee4d2d;
                font-weight: 500;
            }
        </style>
    </head>
    <body>
        <div class="header">
            <%@ include file="/includes/header.jsp" %>
        </div>

        <main class="container mt-5 pt-5">
            <%
                String idParam = request.getParameter("id");
                if (idParam != null) {
                    try {
                        int productId = Integer.parseInt(idParam);
                        ProductDAO dao = new ProductDAO();
                        Product p = dao.getProductById(productId);

                        if (p != null) {
                            // ==========================================
                            // 1. KHỞI TẠO BIẾN CHO CẤU HÌNH (COMPONENTS)
                            // ==========================================
                            ProductDetailDAO detailDAO = new ProductDetailDAO();
                            ComponentDAO compDAO = new ComponentDAO();
                            ProductDetail detail = detailDAO.getDetailByProductId(productId);
                        
                            Cpu cpu = null; Gpu gpu = null; Ram ram = null; Storage storage = null; Screen screen = null;
                        
                            if (detail != null) {
                                if (detail.getCpuId() != null) cpu = compDAO.getCpuById(detail.getCpuId());
                                if (detail.getGpuId() != null) gpu = compDAO.getGpuById(detail.getGpuId());
                                if (detail.getRamId() != null) ram = compDAO.getRamById(detail.getRamId());
                                if (detail.getStorageId() != null) storage = compDAO.getStorageById(detail.getStorageId());
                                if (detail.getScreenId() != null) screen = compDAO.getScreenById(detail.getScreenId());
                            }

                            // ==========================================
                            // 2. KHỞI TẠO BIẾN CHO ĐÁNH GIÁ (REVIEWS)
                            // ==========================================
                            // Bắt buộc phải khai báo ở đây để xuống dưới HTML mới dùng được
                            ReviewDAO reviewDAO = new ReviewDAO();
                            String ratingParam = request.getParameter("rating");
                            int ratingFilter = 0;
                            if (ratingParam != null && !ratingParam.isEmpty()) {
                                try { ratingFilter = Integer.parseInt(ratingParam); } catch (NumberFormatException ex) { ratingFilter = 0; }
                            }

                            // Lấy danh sách review (Cần đảm bảo ReviewDAO có hàm này, nếu chưa có xem lại bài trước)
                            List<Review> reviews = new ArrayList<>();
                            try {
                                 // Nếu bạn chưa sửa DAO, hãy dùng hàm getAll và tự lọc
                                 // reviews = reviewDAO.getReviewsByProductIdAndRating(p.getProductId(), ratingFilter); 
                             
                                 // CODE AN TOÀN: Lấy hết rồi lọc bằng Java để tránh lỗi DAO
                                 List<Review> allReviews = reviewDAO.getReviewsByProductId(p.getProductId());
                                 if (ratingFilter == 0) {
                                     reviews = allReviews;
                                 } else {
                                     for (Review r : allReviews) {
                                         if (r.getRating() == ratingFilter) reviews.add(r);
                                     }
                                 }
                            } catch (Exception e) {
                                // Ignore error if DAO fails
                            }
            %>

            <a href="javascript:history.back()" class="btn-back mb-4 d-inline-block">
                <i class="fa-solid fa-arrow-left me-2"></i> Quay lại
            </a>

            <div class="row product-detail">
                <div class="col-md-5 product-img text-center">
                    <img src="<%= request.getContextPath() %>/images/<%= p.getImages() %>" 
                         alt="<%= p.getProductName() %>" class="img-fluid">
                </div>

                <div class="col-md-7 product-info">
                    <h2><%= p.getProductName() %></h2>

                    <p class="price">
                        <%
                            java.text.NumberFormat currencyVN = 
                                java.text.NumberFormat.getCurrencyInstance(new java.util.Locale("vi", "VN"));
                        %>
                        <%= currencyVN.format(p.getPrice()) %>
                    </p>

                    <div class="mb-3">
                        <p class="mb-1"><strong>Danh mục:</strong> <%= p.getCategoryName() != null ? p.getCategoryName() : "Khác" %></p>
                        <p class="mb-1"><strong>Thương hiệu:</strong> <%= p.getBrandName() != null ? p.getBrandName() : "Khác" %></p>
                        <p class="mb-1">
                            <strong>Tình trạng:</strong> 
                            <span class="stock-status <%= (p.getQuantity() <= 0) ? "out-of-stock" : "" %>">
                                <%= p.getQuantity() > 0 ? "Còn hàng (" + p.getQuantity() + ")" : "Hết hàng" %>
                            </span>
                        </p>
                    </div>

                    <p><strong>Mô tả sản phẩm:</strong></p>
                    <p class="text-muted"><%= p.getDescription() != null ? p.getDescription() : "Đang cập nhật..." %></p>

                    <% if (detail != null) { %>



                    

                    <div class="text-center mb-4">
                    </div>

                    <div class="specs-section">
                        <h5 class="spec-section-title"><i class="fa-solid fa-microchip me-2"></i>Thông số kỹ thuật</h5>
                        <table class="table table-bordered table-striped specs-table">
                            <tbody>
                                <% if (cpu != null) { %>
                                <tr><th scope="row">CPU</th><td><%= cpu.getName() %> (Core: <%= cpu.getCores() %>, Thread: <%= cpu.getThreads() %>)</td></tr>
                                        <% } %>
                                        <% if (gpu != null) { %>
                                <tr><th scope="row">GPU</th><td><%= gpu.getName() %> (<%= gpu.getVramGB() %>GB VRAM)</td></tr>
                                        <% } %>
                                        <% if (ram != null) { %>
                                <tr><th scope="row">RAM</th><td><%= ram.getSizeGB() %>GB <%= ram.getType() %></td></tr>
                                        <% } %>
                                        <% if (storage != null) { %>
                                <tr><th scope="row">Ổ cứng</th><td><%= storage.getCapacityGB() %>GB <%= storage.getType() %></td></tr>
                                        <% } %>
                                        <% if (screen != null) { %>
                                <tr><th scope="row">Màn hình</th><td><%= screen.getSizeInch() %> inch, <%= screen.getResolution() %>, <%= screen.getPanelType() %> (<%= screen.getRefreshRate() %>Hz)</td></tr>
                                        <% } %>
                            </tbody>
                        </table>
                    </div>
                    <% } %>
                    <div class="mt-4">
                        <% if (p.getQuantity() > 0) { %>
                        <form action="<%= request.getContextPath() %>/cart" method="post">
                            <input type="hidden" name="action" value="add">
                            <input type="hidden" name="productId" value="<%= p.getProductId() %>">
                            <button type="submit" class="btn-add-cart">
                                <i class="fa-solid fa-cart-plus me-2"></i> Thêm vào giỏ hàng
                            </button>
                        </form>
                        <% } else { %>
                        <button class="btn btn-secondary w-100 py-2" disabled>
                            <i class="fa-solid fa-ban me-2"></i> Hết hàng
                        </button>
                        <% } %>
                    </div>

                </div>
            </div>

            <div class="review-section">
                <h4 class="mb-4">Đánh giá từ khách hàng</h4>

                <div class="rating-filter">
                    <a href="?id=<%= p.getProductId() %>&rating=0" class="rating-btn <%= (ratingFilter==0)?"active":"" %>">Tất cả</a>
                    <a href="?id=<%= p.getProductId() %>&rating=5" class="rating-btn <%= (ratingFilter==5)?"active":"" %>">5 sao</a>
                    <a href="?id=<%= p.getProductId() %>&rating=4" class="rating-btn <%= (ratingFilter==4)?"active":"" %>">4 sao</a>
                    <a href="?id=<%= p.getProductId() %>&rating=3" class="rating-btn <%= (ratingFilter==3)?"active":"" %>">3 sao</a>
                    <a href="?id=<%= p.getProductId() %>&rating=2" class="rating-btn <%= (ratingFilter==2)?"active":"" %>">2 sao</a>
                    <a href="?id=<%= p.getProductId() %>&rating=1" class="rating-btn <%= (ratingFilter==1)?"active":"" %>">1 sao</a>
                </div>

                <% if(reviews == null || reviews.isEmpty()){ %>
                <p class="text-muted fst-italic">Chưa có đánh giá nào cho bộ lọc này.</p>
                <% } else { %>
                <% for(Review r : reviews){ %>
                <div class="card mb-3 review-card">
                    <div class="card-body">
                        <div class="review-header mb-2">
                            <strong><i class="fa-solid fa-user"></i> <%= (r.getUserName() != null) ? r.getUserName() : "Khách hàng" %></strong>
                            <small class="text-muted">
                                <i class="fa-regular fa-clock"></i>
                                <%= new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(r.getCreatedAt()) %>
                            </small>
                        </div>
                        <div class="mb-2">
                            <% for(int i=1;i<=5;i++){ %>
                            <% if(i<=r.getRating()){ %>
                            <i class="fa-solid fa-star text-warning"></i>
                            <% } else { %>
                            <i class="fa-regular fa-star text-secondary"></i>
                            <% } %>
                            <% } %>
                        </div>
                        <p class="mb-0"><%= r.getComment() %></p>
                    </div>
                </div>
                <% } %>
                <% } %>
            </div>

            <%
                        } else {
            %>
            <div class="alert alert-warning mt-5 text-center">
                <h3>Không tìm thấy sản phẩm!</h3>
                <a href="<%= request.getContextPath() %>/home" class="btn btn-primary mt-3">Về trang chủ</a>
            </div>
            <%
                        }
                    } catch (NumberFormatException e) {
            %>
            <div class="alert alert-danger mt-5">ID sản phẩm không hợp lệ.</div>
            <%
                    }
                } else {
            %>
            <div class="alert alert-info mt-5">Vui lòng chọn một sản phẩm.</div>
            <%
                }
            %>
        </main>

        <%@ include file="/includes/footer.jsp" %>
    </body>
</html>