package com.ies.blossom.model;

import com.ies.blossom.entitys.Parcel;
import com.ies.blossom.entitys.Plant;

public class GoodPlantModel {
	
	
	private GoodPlantMeasureModel phStatus;
	private GoodPlantMeasureModel humStatus;
	private Parcel parcel;
	
	public Parcel getParcel() {
		return this.parcel;
	}
	
	public boolean plantNull() {
		return this.parcel.getPlant() == null;
	}
	
	public GoodPlantMeasureModel getPhStatus() {
		if(this.plantNull()) {
			return null;
		}
		return phStatus;
	}

	public GoodPlantMeasureModel getHumStatus() {
		if(this.plantNull()) {
			return null;
		}
		return humStatus;
	}
	
	public Boolean isGood() {
		if(this.plantNull() || (this.phStatus == null && this.humStatus == null)) {
			return null;
		}
		
		if(this.phStatus != null && this.humStatus != null) {
			return this.phStatus.isGood() && this.humStatus.isGood();
		} else if(this.phStatus == null && this.humStatus != null) {
			return this.humStatus.isGood();
		} else {
			return this.phStatus.isGood();
		}
	}

	public GoodPlantModel(Parcel parcel, GoodPlantMeasureModel phStatus, GoodPlantMeasureModel humStatus) {
		this.parcel = parcel;
		this.phStatus = phStatus;
		this.humStatus = humStatus;
	}

}
