package com.example.Generator;

import java.util.*;

public class MonitorPH {
		
		private int id;
		private String modo;
		private int reg_freq;
		private double maxValue;
		private double minValue;
		private ArrayList<Double> values = new ArrayList<Double>();	

		public MonitorPH(int given_id, String mode, int freq, int instant) {		//instant � uma op��o que permite gerar automaticamente valores de um certo numero de dias
			this.modo = mode;
			this.reg_freq = freq;
			this.id = given_id;
			if(instant > 0) {
				if(this.modo == "Low") {
					this.minValue = 4;
					this.maxValue = 6;
					this.generateAcidicSoil(instant);				
				}
				if(this.modo == "Medium") {
					this.minValue = 6;
					this.maxValue = 8;
					this.generateNeutralSoil(instant);
				}
				if(this.modo == "High") {
					this.minValue = 7;
					this.maxValue = 9;
					this.generateBasicSoil(instant);
				}
			}
		}
		
		public void generateAcidicSoil(int days) {
			for(int i = 0; i<this.reg_freq*days; i++) {				
				if(this.values.size()>0) {
					this.generateInRange();
				}
				else {
					double value = getRandomDoubleInRange(4,6);
					this.values.add(value);
				}
			}
		}
		
		public void generateNeutralSoil(int days) {
			for(int i = 0; i<this.reg_freq*days; i++) {
				if(this.values.size()>0) {
					this.generateInRange();
					}
				else {
					double value = getRandomNumberUsingInts(6,8);
					this.values.add(value);
				}
			}
		}
		
		public void generateBasicSoil(int days) {
			for(int i = 0; i<this.reg_freq*days; i++) {
				if(this.values.size()>0) {
					this.generateInRange();
				}
				else {
					double value = getRandomNumberUsingInts(7,9);
					this.values.add(value);
				}
			}
		}
		
		public void generateInRange() {
			double lastMeasure = this.values.get(this.values.size()-1);
			double min = lastMeasure - 0.5;
			double max = lastMeasure + 0.5;
			if(min<this.minValue) {
				min = this.minValue;
			}
			if(max>this.maxValue) {
				max = this.maxValue;
			}
			double value = getRandomDoubleInRange(min , max);
			this.values.add(value);
		}
		
		public ArrayList<Double> getValues(){
			return this.values;
		}
		
		public int getId() {
			return this.id;
		}
		
		public String getMode() {
			return this.modo;
		}
		
		public int getRandomNumberUsingInts(int min, int max) {  //Retirado de https://www.baeldung.com/java-generating-random-numbers-in-range
		    Random random = new Random();
		    return random.ints(min, max)
		      .findFirst()
		      .getAsInt();
		}
		
		public double getRandomDoubleInRange(double rangeMin, double rangeMax) {
			Random r = new Random();
			double randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
			return randomValue;
		}
	}
