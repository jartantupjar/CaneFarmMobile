package com.openatk.field_work.models;

/**
 * Created by ndrs on 7/18/2016.
 */
public class MonitorSurv {
    private Integer id = null;
    private Integer worker_Id=null;
    private String farm_name=null;
    private Double stalk_length=null;
    private Double internodes_u=null;
    private Double internodes_l=null;
    private Double stalk_diameter_u=null;
    private Double stalk_diameter_l=null;
    private String img_url=null;

    public MonitorSurv(){
         this.setId(null);
        this.worker_Id = null;
        this.farm_name = null;
        this.stalk_length = null;
        this.internodes_u = null;
        this.internodes_l = null;
        this.stalk_diameter_u = null;
        this.stalk_diameter_l = null;
        this.img_url = null;
    }

    public MonitorSurv(Integer id, Integer workerId, String farm_name, Double stalklength,Double internodes_u,Double internodes_l,
                       Double stalk_diameter_u,Double stalk_diameter_l,String url) {
        super();
        this.setId(id);
        this.worker_Id = workerId;
        this.farm_name = farm_name;
        this.stalk_length = stalklength;
        this.stalk_diameter_u = stalk_diameter_u;
        this.stalk_diameter_l = stalk_diameter_l;
        this.internodes_l = internodes_l;
        this.internodes_u = internodes_u;
        this.img_url = url;
    }





    public Integer getWorker_Id() {
        return worker_Id;
    }

    public void setWorker_Id(Integer worker_Id) {
        this.worker_Id = worker_Id;
    }

    public String getFarm_name() {
        return farm_name;
    }

    public void setFarm_name(String farm_name) {
        this.farm_name = farm_name;
    }

    public Double getStalk_length() {
        return stalk_length;
    }

    public void setStalk_length(Double stalk_length) {
        this.stalk_length = stalk_length;
    }

    public Double getInternodes_u() {
        return internodes_u;
    }

    public void setInternodes_u(Double internodes_u) {
        this.internodes_u = internodes_u;
    }

    public Double getInternodes_l() {
        return internodes_l;
    }

    public void setInternodes_l(Double internodes_l) {
        this.internodes_l = internodes_l;
    }

    public Double getStalk_diameter_u() {
        return stalk_diameter_u;
    }

    public void setStalk_diameter_u(Double stalk_diameter_u) {
        this.stalk_diameter_u = stalk_diameter_u;
    }

    public Double getStalk_diameter_l() {
        return stalk_diameter_l;
    }

    public void setStalk_diameter_l(Double stalk_diameter_l) {
        this.stalk_diameter_l = stalk_diameter_l;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
