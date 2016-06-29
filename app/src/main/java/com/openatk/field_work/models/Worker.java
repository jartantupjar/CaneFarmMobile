package com.openatk.field_work.models;

import java.util.Date;

public class Worker {

	static final String COLUMN_ID="_id";



	private Integer id = null;
	private String remote_id = null;
	private Date dateNameChanged = null;
	private String name = "";
	
	private Boolean deleted = null;
	private Date dateDeletedChanged = null;


	private String address="";
	private String sex = "";
	private String civil="";
	private String education = "";
	private String phone="";



	//todo check if i have to add details of TableFarmerDetails inside Worker()
	public Worker() {
		this.remote_id = null;
		this.dateNameChanged = null;
		this. name = null;
		this.deleted = null;
		this.dateDeletedChanged = null;
	}
		
	public Worker(String name) {
		this.name = name;
		this.dateNameChanged = new Date();
	}
	
	public Worker(Date dateNameChanged, String name) {
		this.dateNameChanged = dateNameChanged;
		this.name = name;
	}

	
	public Worker(Integer id, String remote_id, Date dateNameChanged,
			String name, Boolean deleted, Date dateDeletedChanged) {
		super();
		this.id = id;
		this.remote_id = remote_id;
		this.dateNameChanged = dateNameChanged;
		this.name = name;
		this.deleted = deleted;
		this.dateDeletedChanged = dateDeletedChanged;
	}

	public Worker(Integer id, String remote_id, Date dateNameChanged, String name, String address,
				  String sex, String civil, String education, String phone, Boolean deleted, Date dateDeletedChanged) {

		super();
		this.id = id;
		this.remote_id = remote_id;
		this.dateNameChanged = dateNameChanged;
		this.name = name;
		this.address= address;
		this.sex=sex;
		this.civil=civil;
		this.education=education;
		this.phone=phone;

		this.deleted = deleted;
		this.dateDeletedChanged = dateDeletedChanged;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRemote_id() {
		return remote_id;
	}
	public void setRemote_id(String remote_id) {
		this.remote_id = remote_id;
	}
	public Date getDateNameChanged() {
		return dateNameChanged;
	}
	public void setDateNameChanged(Date dateNameChanged) {
		this.dateNameChanged = dateNameChanged;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Date getDateDeletedChanged() {
		return dateDeletedChanged;
	}

	public void setDateDeletedChanged(Date dateDeletedChanged) {
		this.dateDeletedChanged = dateDeletedChanged;
	}

	@Override
	public String toString() {
		return this.getName();
	}


	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getCivil() {
		return civil;
	}

	public void setCivil(String civil) {
		this.civil = civil;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}


	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
