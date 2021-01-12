package com.ies.blossom.model;

public class GoodPlantMeasureModel {
	
	
	private double percentage;
	private boolean good;

	public GoodPlantMeasureModel(double percentage, boolean good){
		this.percentage = percentage;
		this.good = good;
	}

	public double getPercentage() {
		return percentage;
	}

	public boolean isGood() {
		return good;
	}

}
