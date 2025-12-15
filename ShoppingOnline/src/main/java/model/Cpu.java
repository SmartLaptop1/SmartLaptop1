/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ducan
 */


public class Cpu {
    private Long id;
    private String code;
    private String name;
    private Integer cores;          // Int NULL -> Integer
    private Integer threads;        // Int NULL -> Integer
    private Float baseClockGHz;     // Float NULL -> Float
    private Float turboClockGHz;    // Float NULL -> Float

    public Cpu() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getCores() { return cores; }
    public void setCores(Integer cores) { this.cores = cores; }

    public Integer getThreads() { return threads; }
    public void setThreads(Integer threads) { this.threads = threads; }

    public Float getBaseClockGHz() { return baseClockGHz; }
    public void setBaseClockGHz(Float baseClockGHz) { this.baseClockGHz = baseClockGHz; }

    public Float getTurboClockGHz() { return turboClockGHz; }
    public void setTurboClockGHz(Float turboClockGHz) { this.turboClockGHz = turboClockGHz; }
}
