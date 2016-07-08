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
    private Integer worker_Id=null;
    private Float tAcres = 0.0f;
    private List<LatLng> boundary;
    private String cropLoc="";
    private String cropClass="";
    private String farmSys="";
    private Date dateUpdated = null;
    private String cropVariety="";
    private String furrowDist="";
    private String soilAnalysis="";
    private Date harvestDate= null;

    public BaseField(){

    }
    public BaseField(Object makeNull){
        if(makeNull == null){
            this.name=null;
            this.worker_Id=null;
            this.tAcres=null;
            this.boundary=null;
            this.cropLoc=null;
            this.cropClass=null;
            this.farmSys=null;
            this.cropVariety=null;
            this.dateUpdated=null;
            this.furrowDist=null;
            this.soilAnalysis=null;
            this.harvestDate=null;
        }
    }
    public BaseField(Integer id, String name,Integer workerId,
                     float acres,List<LatLng> boundary,String cropLoc,String cropClass,String farmSys,String cropVariety,
                     Date dateUpdated,String furrowDist,String soilAnalysis,Date harvestDate){
        super();
        this.setId(id);
        this.setName(name);
        this.worker_Id=workerId;
        this.tAcres=acres;
        this.boundary=boundary;
        this.setCropLoc(cropLoc);
        this.setCropClass(cropClass);
        this.setFarmSys(farmSys);
        this.setCropVariety(cropVariety);
        this.setDateUpdated(dateUpdated);
        this.setFurrowDist(furrowDist);
        this.setSoilAnalysis(soilAnalysis);
        this.setHarvestDate(harvestDate);
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

    public String getCropClass() {
        return cropClass;
    }

    public void setCropClass(String cropClass) {
        this.cropClass = cropClass;
    }

    public String getFarmSys() {
        return farmSys;
    }

    public void setFarmSys(String farmSys) {
        this.farmSys = farmSys;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public String getCropVariety() {
        return cropVariety;
    }

    public void setCropVariety(String cropVariety) {
        this.cropVariety = cropVariety;
    }

    public String getFurrowDist() {
        return furrowDist;
    }

    public void setFurrowDist(String furrowDist) {
        this.furrowDist = furrowDist;
    }

    public String getSoilAnalysis() {
        return soilAnalysis;
    }

    public void setSoilAnalysis(String soilAnalysis) {
        this.soilAnalysis = soilAnalysis;
    }

    public Date getHarvestDate() {
        return harvestDate;
    }

    public void setHarvestDate(Date harvestDate) {
        this.harvestDate = harvestDate;
    }
}
