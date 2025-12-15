/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class Gpu {
    private Long id;
    private String code;
    private String name;
    private Integer vramGB; // Int NULL -> Integer

    public Gpu() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getVramGB() { return vramGB; }
    public void setVramGB(Integer vramGB) { this.vramGB = vramGB; }
}
