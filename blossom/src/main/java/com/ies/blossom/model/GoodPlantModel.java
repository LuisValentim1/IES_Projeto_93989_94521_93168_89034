package com.ies.blossom.model;

import com.ies.blossom.entitys.Parcel;

public class GoodPlantModel {
	
	
	public GoodPlantMeasureModel phStatus;
	public GoodPlantMeasureModel humStatus;
	
	public GoodPlantMeasureModel getPhStatus() {
		return phStatus;
	}

	public GoodPlantMeasureModel getHumStatus() {
		return humStatus;
	}

	public GoodPlantModel(GoodPlantMeasureModel phStatus, GoodPlantMeasureModel humStatus) {
		this.phStatus = phStatus;
		this.humStatus = humStatus;
	}

}
