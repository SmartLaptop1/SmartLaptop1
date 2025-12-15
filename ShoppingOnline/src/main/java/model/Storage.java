/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class Storage {
    private Long id;
    private String code;
    private Integer capacityGB; // Int NULL -> Integer
    private String type;

    public Storage() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public Integer getCapacityGB() { return capacityGB; }
    public void setCapacityGB(Integer capacityGB) { this.capacityGB = capacityGB; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}
