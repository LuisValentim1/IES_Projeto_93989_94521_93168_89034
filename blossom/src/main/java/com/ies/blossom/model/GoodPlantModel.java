package com.ies.blossom.model;

import com.ies.blossom.entitys.Parcel;

public class GoodPlantModel {
	
	
	private Boolean phNull;
	private Boolean humNull;
	private Double phMeasure;
	private Double humMeasure;
	private Boolean goodPh;
	private Boolean goodHum;
	private Parcel parcel;
	public GoodPlantModel(Boolean phNull, Boolean humNull, Double phMeasure, Double humMeasure, Boolean goodPh,
			Boolean goodHum, Parcel parcel) {
		super();
		this.phNull = phNull;
		this.humNull = humNull;
		this.phMeasure = phMeasure;
		this.humMeasure = humMeasure;
		this.goodPh = goodPh;
		this.goodHum = goodHum;
		this.parcel = parcel;
	}
	
	public Boolean isPhNull() {
		return phNull;
	}
	public Boolean isHumNull() {
		return humNull;
	}
	public Double getPhMeasure() {
		return phMeasure;
	}
	public Double getHumMeasure() {
		return humMeasure;
	}
	public Boolean isGoodPh() {
		return goodPh;
	}
	public Boolean isGoodHum() {
		return goodHum;
	}
	public Parcel getParcel() {
		return parcel;
	}
	
	

}
