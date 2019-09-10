package com.seglino.jingyi.assets.dto;

import java.util.Date;

import com.seglino.jingyi.common.core.dto.BaseDto;

@SuppressWarnings("unused")
public class AssetsExportDto extends BaseDto {
	private String categoryId;
	private String categoryName;
	private String positionId;
	private String positionName;
	private long code;
	private String codeLabel;
	private String name;
	private String brand;
	private String model;
	private int status;
	private String statusName;
	private int useStatus;
	private String useStatusName;
	private String admin;
	private String adminName;
	private int purchasingMethod;
	private String purchasingMethodName;
	private Date startDate;
	private String owner;
	private int useTerm;
	private String useTermName;
	private double amount;
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

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public Long getCode() {
		return code;
	}

	public void setCode(long code) {
		this.code = code;
	}

	public String getCodeLabel() {
		return String.format("%06d", code);
	}

	public void setCodeLabel(String codeLabel) {
		this.codeLabel = codeLabel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStatusName() {
		return AssetsEntryData.getStatusName(status);
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public int getUseStatus() {
		return useStatus;
	}

	public void setUseStatus(int useStatus) {
		this.useStatus = useStatus;
	}

	public String getUseStatusName() {
		return AssetsEntryData.getUseStatusName(useStatus);
	}

	public void setUseStatusName(String useStatusName) {
		this.useStatusName = useStatusName;
	}

	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public int getPurchasingMethod() {
		return purchasingMethod;
	}

	public void setPurchasingMethod(int purchasingMethod) {
		this.purchasingMethod = purchasingMethod;
	}

	public String getPurchasingMethodName() {
		return AssetsEntryData.getPurchasingMethodName(purchasingMethod);
	}

	public void setPurchasingMethodName(String purchasingMethodName) {
		this.purchasingMethodName = purchasingMethodName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public int getUseTerm() {
		return useTerm;
	}

	public void setUseTerm(int useTerm) {
		this.useTerm = useTerm;
	}

	public String getUseTermName() {
		return useTerm + "个月";
	}

	public void setUseTermName(String useTermName) {
		this.useTermName = useTermName;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
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
