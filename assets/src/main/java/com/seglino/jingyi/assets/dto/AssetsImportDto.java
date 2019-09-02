package com.seglino.jingyi.assets.dto;

import java.util.Date;

public class AssetsImportDto {
	private String name;
	private String category;
	private String brand;
	private String model;
	private String position;
	private String useStatus;
	private String purchasingMethod;
	private Date startDate;
	private Integer useTerm;
	private Double amount;
	private String remark;
	private String supplier;
	private String supplierContact;
	private String supplierMobile;
	private Date maintDate;
	private String maintNotes;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getUseStatus() {
		return useStatus;
	}

	public void setUseStatus(String useStatus) {
		this.useStatus = useStatus;
	}

	public String getPurchasingMethod() {
		return purchasingMethod;
	}

	public void setPurchasingMethod(String purchasingMethod) {
		this.purchasingMethod = purchasingMethod;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Integer getUseTerm() {
		return useTerm;
	}

	public void setUseTerm(Integer useTerm) {
		this.useTerm = useTerm;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getSupplierContact() {
		return supplierContact;
	}

	public void setSupplierContact(String supplierContact) {
		this.supplierContact = supplierContact;
	}

	public String getSupplierMobile() {
		return supplierMobile;
	}

	public void setSupplierMobile(String supplierMobile) {
		this.supplierMobile = supplierMobile;
	}

	public Date getMaintDate() {
		return maintDate;
	}

	public void setMaintDate(Date maintDate) {
		this.maintDate = maintDate;
	}

	public String getMaintNotes() {
		return maintNotes;
	}

	public void setMaintNotes(String maintNotes) {
		this.maintNotes = maintNotes;
	}
}
