package model;

import java.sql.Timestamp; // Sử dụng Timestamp cho DATETIME

public class ProductDetail {
    private Long id;
    private Long productId;
    private Long cpuId;
    private Long gpuId;
    private Long ramId;
    private Long storageId;
    private Long screenId;
    private Timestamp dateCreate;
    private Timestamp dateUpdate;
    private Integer status;

    public ProductDetail() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public Long getCpuId() { return cpuId; }
    public void setCpuId(Long cpuId) { this.cpuId = cpuId; }

    public Long getGpuId() { return gpuId; }
    public void setGpuId(Long gpuId) { this.gpuId = gpuId; }

    public Long getRamId() { return ramId; }
    public void setRamId(Long ramId) { this.ramId = ramId; }

    public Long getStorageId() { return storageId; }
    public void setStorageId(Long storageId) { this.storageId = storageId; }

    public Long getScreenId() { return screenId; }
    public void setScreenId(Long screenId) { this.screenId = screenId; }

    public Timestamp getDateCreate() { return dateCreate; }
    public void setDateCreate(Timestamp dateCreate) { this.dateCreate = dateCreate; }

    public Timestamp getDateUpdate() { return dateUpdate; }
    public void setDateUpdate(Timestamp dateUpdate) { this.dateUpdate = dateUpdate; }

    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
}