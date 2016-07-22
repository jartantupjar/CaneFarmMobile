package com.openatk.field_work.db;

public class IPConfig {

    private  String ipAddress = "10.100.199.216:8080";
    private  String webApp="SRA";
    private  String baseUrl = "http://" + ipAddress +"/" + webApp +"/";

    public String getBaseUrl() {
        return baseUrl;
    }

}
