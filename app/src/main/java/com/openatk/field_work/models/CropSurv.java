package com.openatk.field_work.models;

import java.util.Date;

/**
 * Created by ndrs on 7/18/2016.
 */
public class CropSurv {

    private Integer worker_Id=null;
    private String farm_name=null;
    private Integer year = null;
    private String variety=null;
    private Integer furrow_dist=null;
    private String texture=null;
    private String topography=null;
    private Double planting_density=null;
    private Date harvest_date=null;
    private String crop_class=null;
    private Date date_millable=null;
    private Integer num_millable=null;
    private Double avg_mill_stool=null;
    private Double brix=null;
    private Double stalk_length=null;
    private Double diameter=null;
    private Double weight=null;
    private String farm_sys=null;

    public CropSurv(){
        this.worker_Id = null;
        this.farm_name = null;
        this.setYear(null);
        this.variety=null;
        this.furrow_dist=null;
        this.texture=null;
        this.topography=null;
        this.planting_density=null;
        this.harvest_date=null;
        this.crop_class=null;
        this.date_millable=null;
        this.num_millable=null;
        this.avg_mill_stool=null;
        this.brix=null;
        this.stalk_length=null;
        this.diameter=null;
        this.weight=null;
        this.setFarm_sys(null);
    }
    public CropSurv(Integer worker_Id, String farm_name, Integer year, String variety,
                    Integer furrow_dist, String texture, String topography,
                    Double planting_density, Date harvest_date, String crop_class,
                    Date date_millable, Integer num_millable, Double avg_mill_stool,
                    Double brix, Double stalk_length, Double diameter, Double weight, String farm_sys){
        super();
        this.worker_Id = worker_Id;
        this.farm_name = farm_name;
        this.setYear(year);
        this.variety=variety;
        this.furrow_dist=furrow_dist;
        this.texture=texture;
        this.topography=topography;
        this.planting_density=planting_density;
        this.harvest_date=harvest_date;
        this.crop_class=crop_class;
        this.date_millable=date_millable;
        this.num_millable=num_millable;
        this.avg_mill_stool=avg_mill_stool;
        this.brix=brix;
        this.stalk_length=stalk_length;
        this.diameter=diameter;
        this.weight=weight;
        this.setFarm_sys(farm_sys);
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

    public String getVariety() {
        return variety;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    public Integer getFurrow_dist() {
        return furrow_dist;
    }

    public void setFurrow_dist(Integer furrow_dist) {
        this.furrow_dist = furrow_dist;
    }

    public String getTexture() {
        return texture;
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }

    public String getTopography() {
        return topography;
    }

    public void setTopography(String topography) {
        this.topography = topography;
    }

    public Double getPlanting_density() {
        return planting_density;
    }

    public void setPlanting_density(Double planting_density) {
        this.planting_density = planting_density;
    }

    public Date getHarvest_date() {
        return harvest_date;
    }

    public void setHarvest_date(Date harvest_date) {
        this.harvest_date = harvest_date;
    }

    public String getCrop_class() {
        return crop_class;
    }

    public void setCrop_class(String crop_class) {
        this.crop_class = crop_class;
    }

    public Date getDate_millable() {
        return date_millable;
    }

    public void setDate_millable(Date date_millable) {
        this.date_millable = date_millable;
    }

    public Integer getNum_millable() {
        return num_millable;
    }

    public void setNum_millable(Integer num_millable) {
        this.num_millable = num_millable;
    }

    public Double getAvg_mill_stool() {
        return avg_mill_stool;
    }

    public void setAvg_mill_stool(Double avg_mill_stool) {
        this.avg_mill_stool = avg_mill_stool;
    }

    public Double getBrix() {
        return brix;
    }

    public void setBrix(Double brix) {
        this.brix = brix;
    }

    public Double getStalk_length() {
        return stalk_length;
    }

    public void setStalk_length(Double stalk_length) {
        this.stalk_length = stalk_length;
    }

    public Double getDiameter() {
        return diameter;
    }

    public void setDiameter(Double diameter) {
        this.diameter = diameter;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }


    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getFarm_sys() {
        return farm_sys;
    }

    public void setFarm_sys(String farm_sys) {
        this.farm_sys = farm_sys;
    }
}
