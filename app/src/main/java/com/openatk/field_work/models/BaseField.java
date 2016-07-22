package com.openatk.field_work.models;

import com.google.android.gms.maps.model.LatLng;

import com.openatk.openatklib.atkmap.models.ATKPolygon;
import java.util.Date;
import java.util.List;

/**
 * Created by ndrs on 7/5/2016.
 */


public class BaseField {

    private Integer id = null;
    private String name = "";
    private String username="";
    private Integer worker_Id=null;
    private Float tAcres = 0.0f;
    private List<LatLng> boundary;
    private String cropLoc="";
    private String managementType="";
    private String district="";
    private Double nitrogen= null;
    private Date dateUpdated = null;
    private Double phosporus=  null;
    private Double potassium= null;
    private Double ph_level= null;

    public BaseField(){

    }
    public BaseField(Object makeNull){
        if(makeNull == null){
            this.name=null;
            this.username = null;
            this.worker_Id=null;
            this.tAcres=null;
            this.boundary=null;
            this.cropLoc=null;
            this.setManagementType(null);
            this.setDistrict(null);
            this.setNitrogen(null);
            this.setDateUpdated(null);
            this.setPhosporus(null);
            this.setPotassium(null);
            this.setPh_level(null);
        }
    }
    public BaseField(Integer id, String name,String username, Integer workerId,
                     float acres,List<LatLng> boundary,String cropLoc, String managementType,
                     String district, Double nitrogen,Double phosporus, Double potassium, Double ph_level){
        super();
        this.setId(id);
        this.setName(name);
        this.worker_Id=workerId;
        this.tAcres=acres;
        this.boundary=boundary;
        this.setCropLoc(cropLoc);
        this.setManagementType(managementType);
        this.setDistrict(district);
        this.setNitrogen(nitrogen);
        this.setPhosporus(phosporus);
        this.setPotassium(potassium);
        this.setPh_level(ph_level);
    }

    public ATKPolygon getATKPolygon(){
        return new ATKPolygon(this.getId(), this.getBoundary());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWorker_Id() {
        return worker_Id;
    }

    public void setWorker_Id(Integer worker_Id) {
        this.worker_Id = worker_Id;
    }

    public Float gettAcres() {
        return tAcres;
    }

    public void settAcres(Float tAcres) {
        this.tAcres = tAcres;
    }

    public List<LatLng> getBoundary() {
        return boundary;
    }

    public void setBoundary(List<LatLng> boundary) {
        this.boundary = boundary;
    }

    public String getCropLoc() {
        return cropLoc;
    }

    public void setCropLoc(String cropLoc) {
        this.cropLoc = cropLoc;
    }

    public String toString() {
        return this.getName();
    }

    public String getManagementType() {
        return managementType;
    }

    public void setManagementType(String managementType) {
        this.managementType = managementType;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Double getNitrogen() {
        return nitrogen;
    }

    public void setNitrogen(Double nitrogen) {
        this.nitrogen = nitrogen;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Double getPhosporus() {
        return phosporus;
    }

    public void setPhosporus(Double phosporus) {
        this.phosporus = phosporus;
    }

    public Double getPotassium() {
        return potassium;
    }

    public void setPotassium(Double potassium) {
        this.potassium = potassium;
    }

    public Double getPh_level() {
        return ph_level;
    }

    public void setPh_level(Double ph_level) {
        this.ph_level = ph_level;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
