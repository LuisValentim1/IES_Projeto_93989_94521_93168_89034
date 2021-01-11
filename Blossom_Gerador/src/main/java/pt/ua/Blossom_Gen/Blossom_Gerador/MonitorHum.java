package pt.ua.Blossom_Gen.Blossom_Gerador;

import java.util.ArrayList;
import java.util.Random;

public class MonitorHum {
		
		private int id;
		private String modo;
		private int reg_freq;
		private int maxValue;
		private int minValue;
		private ArrayList<Integer> values = new ArrayList<Integer>();	;	

		public MonitorHum(int given_id, String mode, int freq, int instant) { 		//instant � uma op��o que permite gerar automaticamente valores de um certo numero de dias
			this.modo = mode;
			this.reg_freq = freq;
			this.id = given_id;
			if(instant > 0) {
				if(this.modo == "Low") {
					this.minValue = 0;
					this.maxValue = 40;
					this.generateDrySoil(instant);
				}
				if(this.modo == "Medium") {
					this.minValue = 25;
					this.maxValue = 75;
					this.generateNeutralSoil(instant);
				}
				if(this.modo == "High") {
					this.minValue = 60;
					this.maxValue = 100;
					this.generateMoistSoil(instant);
				}
			}
		}
		
		public void generateDrySoil(int days) {
			for(int i = 0; i<this.reg_freq * days; i++) {
				if(this.values.size()>0) {
					this.generateInRange();
				}
				else {
					int value = getRandomNumberUsingInts(0,40);
					this.values.add(value);
				}
			}
		}
		
		public void generateNeutralSoil(int days) {
			for(int i = 0; i<this.reg_freq * days; i++) {
				if(this.values.size()>0) {
					this.generateInRange();
				}
				else {
					int value = getRandomNumberUsingInts(25,75);
					this.values.add(value);
				}
			}
		}
		
		public void generateMoistSoil(int days) {
			for(int i = 0; i<this.reg_freq * days; i++) {
				if(this.values.size()>0) {
					this.generateInRange();
				}
				else {
					int value = getRandomNumberUsingInts(60,100);
					this.values.add(value);
				}
			}
		}
		
		public void generateInRange() {
			int lastMeasure = this.values.get(this.values.size()-1);
			int min = lastMeasure - 20;
			int max = lastMeasure + 20;
			if(min<this.minValue) {
				min = this.minValue;
			}
			if(max>this.maxValue) {
				max = this.maxValue;
			}
			int value = getRandomNumberUsingInts(min , max);
			this.values.add(value);
		}
		
		public String getMode() {
			return this.modo;
		}
		
		public int getId() {
			return this.id;
		}
		
		public ArrayList<Integer> getValues(){
			return this.values;
		}
		
		public int getRandomNumberUsingInts(int min, int max) {  //Retirado de https://www.baeldung.com/java-generating-random-numbers-in-range
		    Random random = new Random();
		    return random.ints(min, max)
		      .findFirst()
		      .getAsInt();
		}
	}
