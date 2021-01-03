package com.ies.blossom.model;

import com.ies.blossom.entitys.Parcel;

public class GoodPlantModel {
	
	
	public Boolean phNull;
	public Boolean humNull;
	public Double goodPhPercentage;
	public Double goodHumPercentage;
	public Boolean goodPh;
	public Boolean goodHum;
	public Parcel parcel;
	
	public GoodPlantModel(Parcel parcel, double acceptablePercentage) {
		
		this.parcel = parcel;
		
		this.phNull = parcel.noPhMeasure();
		if (this.phNull == null) {
			this.goodPhPercentage = null;
			this.goodPh = null;
			
		} else {
			this.goodPhPercentage = parcel.generalPhMeasurePercentage();
			if (this.goodPhPercentage < acceptablePercentage) {
				this.goodPh = false;
			} else {
				this.goodPh = true;
			}
		}
		
		
		
		this.humNull = parcel.noHumMeasure();
		if (this.humNull == null) {
			this.goodHumPercentage = null;
			this.goodHum = null;
		} else {
			this.goodHumPercentage = parcel.generalHumMeasurePercentage();
			if (this.goodHumPercentage < acceptablePercentage) {
				this.goodHum = false;
			} else {
				this.goodHum = true;
			}
		}
		
	}
	
	public Boolean isPhNull() {
		return phNull;
	}
	public Boolean isHumNull() {
		return humNull;
	}
	public Double getPhMeasure() {
		return goodPhPercentage;
	}
	public Double getHumMeasure() {
		return goodHumPercentage;
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
