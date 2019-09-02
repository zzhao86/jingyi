package com.seglino.jingyi.webapi.vo.back.assets;

import java.util.Date;

import com.seglino.jingyi.assets.dto.AssetsEntryData;
import com.seglino.jingyi.common.core.vo.BaseVo;

public class AssetsListVo extends BaseVo {
	private String categoryName;
	private String positionName;
	private String code;
	private String name;
	private String brand;
	private String model;
	private int status;
	@SuppressWarnings("unused")
	private String statusName;
	private int useStatus;
	@SuppressWarnings("unused")
	private String useStatusName;
	private String adminName;
	private int purchasingMethod;
	@SuppressWarnings("unused")
	private String purchasingMethodName;
	private Date startDate;
	private String owner;

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public String getCode() {
		return String.format("%06d", Long.valueOf(code));
	}

	public void setCode(String code) {
		this.code = code;
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

	public int getUseStatus() {
		return useStatus;
	}

	public void setUseStatus(int useStatus) {
		this.useStatus = useStatus;
	}

	public String getUseStatusName() {
		return AssetsEntryData.getUseStatusName(useStatus);
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
}
