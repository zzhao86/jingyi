package com.seglino.jingyi.assets.pojo;

import java.util.Date;

import com.seglino.jingyi.common.core.po.BaseEntity;

public class Assets extends BaseEntity {
	private static final long serialVersionUID = 1738531609809715340L;

	private String categoryId;
	private Long code;
	private String name;
	private String imageUrl;
	private String owner;
	private String admin;
	private String brand;
	private String model;
	private Integer status;
	private Integer useStatus;
	private String positionId;
	private Integer useTerm;
	private Double amount;
	private Date startDate;
	private Integer purchasingMethod;
	private String orderCode;
	private String remark;
	private String serialNum;
	private String supplier;
	private String supplierContact;
	private String supplierMobile;
	private Date maintDate;
	private String maintNotes;

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getUseStatus() {
		return useStatus;
	}

	public void setUseStatus(Integer useStatus) {
		this.useStatus = useStatus;
	}

	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Integer getPurchasingMethod() {
		return purchasingMethod;
	}

	public void setPurchasingMethod(Integer purchasingMethod) {
		this.purchasingMethod = purchasingMethod;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
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
