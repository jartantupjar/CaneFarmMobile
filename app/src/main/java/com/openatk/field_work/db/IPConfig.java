package com.openatk.field_work.db;

public class IPConfig {

    private  String ipAddress = "192.168.0.12:8080";
    private  String webApp="SRA";
    private  String baseUrl = "http://" + ipAddress +"/" + webApp +"/";

    public String getBaseUrl() {
        return baseUrl;
    }

}
