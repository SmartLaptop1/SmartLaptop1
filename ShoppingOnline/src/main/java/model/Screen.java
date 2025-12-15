package model;

public class Screen {
    private Long id;
    private String code;
    private Float sizeInch;      // Float NULL -> Float
    private String resolution;
    private String panelType;
    private Integer refreshRate; // Int NULL -> Integer

    public Screen() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public Float getSizeInch() { return sizeInch; }
    public void setSizeInch(Float sizeInch) { this.sizeInch = sizeInch; }

    public String getResolution() { return resolution; }
    public void setResolution(String resolution) { this.resolution = resolution; }

    public String getPanelType() { return panelType; }
    public void setPanelType(String panelType) { this.panelType = panelType; }

    public Integer getRefreshRate() { return refreshRate; }
    public void setRefreshRate(Integer refreshRate) { this.refreshRate = refreshRate; }
}