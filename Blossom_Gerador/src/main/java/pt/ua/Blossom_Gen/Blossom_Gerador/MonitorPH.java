package pt.ua.Blossom_Gen.Blossom_Gerador;

import java.util.*;

public class MonitorPH {
		
		private int id;
		private String modo;
		private int reg_freq;
		private ArrayList<Double> values = new ArrayList<Double>();	

		public MonitorPH(int given_id, String mode, int freq, int instant) {		//instant � uma op��o que permite gerar automaticamente valores de um certo numero de dias
			this.modo = mode;
			this.reg_freq = freq;
			this.id = given_id;
			if(instant > 0) {
				if(this.modo == "Low") {
					this.generateAcidicSoil(instant);
				}
				if(this.modo == "Medium") {
					this.generateNeutralSoil(instant);
				}
				if(this.modo == "High") {
					this.generateBasicSoil(instant);
				}
			}
		}
		
		public void generateAcidicSoil(int days) {
			if(days>0) {
				for(int i = 0; i<this.reg_freq*days; i++) {
					double value = getRandomDoubleInRange(0,7);
					this.values.add(value);
				}
			}
		}
		
		public void generateNeutralSoil(int days) {
			if(days>0) {
				for(int i = 0; i<this.reg_freq*days; i++) {
					double value = getRandomNumberUsingInts(4,10);
					this.values.add(value);
				}
			}
		}
		
		public void generateBasicSoil(int days) {
			if(days>0) {
				for(int i = 0; i<this.reg_freq*days; i++) {
					double value = getRandomNumberUsingInts(7,14);
					this.values.add(value);
				}
			}
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
