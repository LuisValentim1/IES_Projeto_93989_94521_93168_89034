package pt.ua.Blossom_Gen.Blossom_Gerador;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Gerador {
	
	static int num_of_ph_monitors = 1;  		// Numero de monitores de ph que iremos gerar.
	static int num_of_hum_monitors = 1; 		// Numero de monitores de humidade que iremos gerar.
	static int regs_per_day = 48; 				// Numero de registos que os monitores fazem por dia.
	static int days = 1;
	static String monitor_type[] = {"Low", "Medium", "High"};     // Gerar monitores especificos
																		   // Low -> ph acido / humidade baixa
																		   // Medium -> ph neutro / humidade m�dia
																		   // High -> ph b�sico / humidade alta

	public static void main(String[] args) throws InterruptedException{
		
		ArrayList<MonitorPH> monitoresPH = new ArrayList<MonitorPH>();
		ArrayList<MonitorHum> monitoresHum = new ArrayList<MonitorHum>();
		
		//Ciclo para gerar monitores de PH
		for(int i = 0; i<num_of_ph_monitors; i++) {
			int opt = getRandomNumberUsingInts(0, 2);
			MonitorPH ph = new MonitorPH(monitor_type[opt], regs_per_day, days);
			monitoresPH.add(ph);
		}
		
		//Ciclo para gerar monitores de humidade
		for(int i = 0; i<num_of_hum_monitors; i++) {
			int opt = getRandomNumberUsingInts(0, 2);
			MonitorHum humid = new MonitorHum(monitor_type[opt], regs_per_day, days);
			monitoresHum.add(humid);
		}
		
		if(days == 0) { //N�o gerar valores automaticamente, gerar em tempo real
			while(true) {
				
				//Adicionar 1 medi��o aos monitores de PH
				for(int i = 0; i<monitoresPH.size(); i++) {
					MonitorPH p = monitoresPH.get(i);
					String mode = p.getMode();
					if(mode == "Low") {
						p.generateAcidicSoil(1/regs_per_day);
					}
					if(mode == "Medium") {
						p.generateNeutralSoil(1/regs_per_day);
					}
					if(mode == "High") {
						p.generateBasicSoil(1/regs_per_day);
					}
				}
				
				//Adicionar 1 medi��o aos monitores de humidade
				for(int i = 0; i<monitoresHum.size(); i++) {
					MonitorHum h = monitoresHum.get(i);
					String mode = h.getMode();
					if(mode == "Low") {
						h.generateDrySoil(1/regs_per_day);
					}
					if(mode == "Medium") {
						h.generateNeutralSoil(1/regs_per_day);
					}
					if(mode == "High") {
						h.generateMoistSoil(1/regs_per_day);
					}
				}
				
				//Esperar tempo real pela proxima medi��o
				int real_freq = (1/(regs_per_day/24));
				TimeUnit.MINUTES.sleep(real_freq * 60);
			}
			
		}
	}
	
	public static int getRandomNumberUsingInts(int min, int max) {  //Retirado de https://www.baeldung.com/java-generating-random-numbers-in-range
	    Random random = new Random();
	    return random.ints(min, max)
	      .findFirst()
	      .getAsInt();
	}
}
