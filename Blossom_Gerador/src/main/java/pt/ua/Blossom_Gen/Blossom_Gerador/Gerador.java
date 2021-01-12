package pt.ua.Blossom_Gen.Blossom_Gerador;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.io.*;

public class Gerador {
	
	//Dados estáticos que definem o que gerar 
	//Deve ser alterado conforme a quantidade/frequencia de dados que queremos gerar
	
	static int num_of_ph_monitors = 1;  		// Numero de monitores de ph que iremos gerar.
	static int num_of_hum_monitors = 1; 		// Numero de monitores de humidade que iremos gerar.
	// TODO ALTEREI DE 48 PARA 3 PARA SER MAIS FACIL DEBUG
	static int regs_per_day = 3; 				// Numero de registos que os monitores fazem por dia.
	static int days = 1;
	static String monitor_type[] = {"Low", "Medium", "High"};     // Gerar monitores especificos
																		   // Low -> ph acido / humidade baixa
																		   // Medium -> ph neutro / humidade m�dia
																		   // High -> ph b�sico / humidade alta

	public static void main(String[] args) throws InterruptedException, IOException{
		
		//Objetos para correr linhas de terminal pelo main
		Runtime rt = Runtime.getRuntime();
		Process pr = null;
		
		//Arrays para guardar os monitores criados
		ArrayList<MonitorPH> monitoresPH = new ArrayList<MonitorPH>();
		ArrayList<MonitorHum> monitoresHum = new ArrayList<MonitorHum>();
		
		//Array de ids para sensores de humidade disponiveis
		ArrayList<Integer> PH_Ids = new ArrayList<Integer>();
		
		//Encontrar os ids de monitores de ph disponiveis
		pr = rt.exec("curl http://localhost:8080/api/test/phids");  //Metodo que devolve os ids por curl
		BufferedReader stdInputph = new BufferedReader(new 			//Leitura e parsing do resultado
			     InputStreamReader(pr.getInputStream()));
		
		String sPH = null;
		while ((sPH = stdInputph.readLine()) != null) {
			String ids_no_brackets = sPH.substring(1, sPH.length()-1);
			String[] ids = ids_no_brackets.split("[,]+");
		    for(String i : ids) {
		    	PH_Ids.add(Integer.parseInt(i));
		    }
		}
		
		//Array de ids para sensores de humidade disponiveis
		ArrayList<Integer> Hum_Ids = new ArrayList<Integer>();
		
		//Encontrar os ids de monitores de humidade disponiveis
		pr = rt.exec("curl http://localhost:8080/api/test/humids");  //Metodo que devolve os ids por curl
		BufferedReader stdInputhum = new BufferedReader(new 			//Leitura e parsing do resultado
			     InputStreamReader(pr.getInputStream()));
		
		String sHum = null;
		while ((sHum = stdInputhum.readLine()) != null) {
			String ids_no_brackets = sHum.substring(1, sHum.length()-1);
			String[] ids = ids_no_brackets.split("[,]+");
		    for(String i : ids) {
		    	Hum_Ids.add(Integer.parseInt(i));
		    }
		}
		
		//Ciclo para gerar monitores de PH
		for(int i = 0; i<PH_Ids.size(); i++) {
			int opt = getRandomNumberUsingInts(0, 2);
			monitoresPH.add(new MonitorPH(PH_Ids.get(i), monitor_type[opt], regs_per_day, days));	
		}
		
		//Ciclo para gerar monitores de humidade
		for(int i = 0; i<Hum_Ids.size(); i++) {
			int opt = getRandomNumberUsingInts(0, 2);
			monitoresHum.add(new MonitorHum(Hum_Ids.get(i), monitor_type[opt], regs_per_day, days));
		}
		
		//Adicionar todos os valores de pH à base de dados
		for(int i = 0; i<monitoresPH.size(); i++) {
			for(int z = 0; z<monitoresPH.get(i).getValues().size(); z++) {
				//Curl em windows
				// String com = "curl -X POST -H \"Content-Type: application/json\" -d \"{\\\"sensorId\\\" : \\\" " + Integer.toString(monitoresPH.get(i).getId()) + " \\\", \\\"value\\\" : \\\" " + Double.toString(monitoresPH.get(i).getValues().get(z)) +  " \\\"}\" http://localhost:8080/api/test/phmeasures";
				
				//Curl em Linux
				String[] com = {"curl", "-X", "POST", "-H", "Content-Type: application/json", "-d", "{\"sensorId\" : \"" + Integer.toString(monitoresPH.get(i).getId()) + "\", \"value\" : \"" + Double.toString(monitoresPH.get(i).getValues().get(z)) +"\"}", "http://localhost:8080/api/test/phmeasures"};
				try {
					pr = rt.exec(com);
				}catch(Exception e) {
					System.out.println("Exception Error");
				}
			}
		}
				
		//Adicionar todos os valores de humidade à base de dados
		for(int i = 0; i<monitoresHum.size(); i++) {
			for(int z = 0; z<monitoresHum.get(i).getValues().size(); z++) {
				//Curl em windows
				//String com = "curl -X POST -H \"Content-Type: application/json\" -d \"{\\\"sensorId\\\" : \\\" " + Integer.toString(monitoresHum.get(i).getId()) + " \\\", \\\"value\\\" : \\\" " + Integer.toString(monitoresHum.get(i).getValues().get(z)) +  " \\\"}\" http://localhost:8080/api/test/hummeasures";

				//Curl em Linux
				String[] com = {"curl", "-X", "POST", "-H", "Content-Type: application/json", "-d", "{\"sensorId\" : \"" + Integer.toString(monitoresHum.get(i).getId()) + "\", \"value\" : \"" + Integer.toString(monitoresHum.get(i).getValues().get(z)) +"\"}", "http://localhost:8080/api/test/hummeasures"};
				try {
					pr = rt.exec(com);
				}catch(Exception e) {
					System.out.println("Exception Error");
				}
			}
		}
		
		if(days == 0) { //N�o gerar valores automaticamente, gerar em tempo real
			while(true) {
				
				//Adicionar 1 medi��o aos monitores de PH
				for(int i = 0; i<monitoresPH.size(); i++) {
					MonitorPH p = monitoresPH.get(i);
					String mode = p.getMode();
					if(mode == "Low") {
						p.generateAcidicSoil(1/regs_per_day);
						// Curl em windows 
						// String com = "curl -X POST -H \"Content-Type: application/json\" -d \"{\\\"sensorId\\\" : \\\" " + Integer.toString(p.getId()) + " \\\", \\\"value\\\" : \\\" " + Double.toString(p.getValues().get(p.getValues().size()-1)) +  " \\\"}\" http://localhost:8080/api/test/phmeasures";
						
						//Curl em linux
						String[] com = {"curl -X POST -H \"Content-Type: application/json\" -d \'{\"sensorId\" : \" " + Integer.toString(p.getId()) + " \", \"value\" : \" " + Double.toString(p.getValues().get(p.getValues().size()-1)) +  " \"}\' http://localhost:8080/api/test/phmeasures"}; 
						
						try {
							pr = rt.exec(com);
						}catch(Exception e) {
							System.out.println("Exception Error");
						}
					}
					if(mode == "Medium") {
						p.generateNeutralSoil(1/regs_per_day);
						
						// Curl em windows
						// String com = "curl -X POST -H \"Content-Type: application/json\" -d \"{\\\"sensorId\\\" : \\\" " + Integer.toString(p.getId()) + " \\\", \\\"value\\\" : \\\" " + Double.toString(p.getValues().get(p.getValues().size()-1)) +  " \\\"}\" http://localhost:8080/api/test/phmeasures";
						
						//Curl em linux
						String[] com = {"curl -X POST -H \"Content-Type: application/json\" -d \'{\"sensorId\" : \" " + Integer.toString(p.getId()) + " \", \"value\" : \" " + Double.toString(p.getValues().get(p.getValues().size()-1)) +  " \"}\' http://localhost:8080/api/test/phmeasures"}; 
						try {
							pr = rt.exec(com);
						}catch(Exception e) {
							System.out.println("Exception Error");
						}
					}
					if(mode == "High") {
						p.generateBasicSoil(1/regs_per_day);
						
						// Curl em windows
						// String com = "curl -X POST -H \"Content-Type: application/json\" -d \"{\\\"sensorId\\\" : \\\" " + Integer.toString(p.getId()) + " \\\", \\\"value\\\" : \\\" " + Double.toString(p.getValues().get(p.getValues().size()-1)) +  " \\\"}\" http://localhost:8080/api/test/phmeasures";
						
						//Curl em linux
						String[] com = {"curl -X POST -H \"Content-Type: application/json\" -d \'{\"sensorId\" : \" " + Integer.toString(p.getId()) + " \", \"value\" : \" " + Double.toString(p.getValues().get(p.getValues().size()-1)) +  " \"}\' http://localhost:8080/api/test/phmeasures"}; 
						try {
							pr = rt.exec(com);
						}catch(Exception e) {
							System.out.println("Exception Error");
						}
					}
				}
				
				//Adicionar 1 medi��o aos monitores de humidade
				for(int i = 0; i<monitoresHum.size(); i++) {
					MonitorHum h = monitoresHum.get(i);
					String mode = h.getMode();
					if(mode == "Low") {
						h.generateDrySoil(1/regs_per_day);
						
						// Curl em windows
						// String com = "curl -X POST -H \"Content-Type: application/json\" -d \"{\\\"sensorId\\\" : \\\" " + Integer.toString(h.getId()) + " \\\", \\\"value\\\" : \\\" " + Integer.toString(h.getValues().get(h.getValues().size()-1)) +  " \\\"}\" http://localhost:8080/api/test/hummeasures";
						
						//Curl em linux
						String[] com = {"curl -X POST -H \"Content-Type: application/json\" -d \'{\"sensorId\" : \" " + Integer.toString(h.getId()) + " \", \"value\" : \" " + Integer.toString(h.getValues().get(h.getValues().size()-1)) +  " \"}\' http://localhost:8080/api/test/phmeasures"}; 
						try {
							pr = rt.exec(com);
						}catch(Exception e) {
							System.out.println("Exception Error");
						}
					}
					if(mode == "Medium") {
						h.generateNeutralSoil(1/regs_per_day);
						
						// Curl em windows
						// String com = "curl -X POST -H \"Content-Type: application/json\" -d \"{\\\"sensorId\\\" : \\\" " + Integer.toString(h.getId()) + " \\\", \\\"value\\\" : \\\" " + Integer.toString(h.getValues().get(h.getValues().size()-1)) +  " \\\"}\" http://localhost:8080/api/test/hummeasures";
						
						//Curl em linux
						String[] com = {"curl -X POST -H \"Content-Type: application/json\" -d \'{\"sensorId\" : \" " + Integer.toString(h.getId()) + " \", \"value\" : \" " + Integer.toString(h.getValues().get(h.getValues().size()-1)) +  " \"}\' http://localhost:8080/api/test/phmeasures"}; 
						try {
							pr = rt.exec(com);
						}catch(Exception e) {
							System.out.println("Exception Error");
						}
					}
					if(mode == "High") {
						h.generateMoistSoil(1/regs_per_day);
						
						//Curl em windows
						//String com = "curl -X POST -H \"Content-Type: application/json\" -d \"{\\\"sensorId\\\" : \\\" " + Integer.toString(h.getId()) + " \\\", \\\"value\\\" : \\\" " + Integer.toString(h.getValues().get(h.getValues().size()-1)) +  " \\\"}\" http://localhost:8080/api/test/hummeasures";

						//Curl em linux
						String[] com = {"curl -X POST -H \"Content-Type: application/json\" -d \'{\"sensorId\" : \" " + Integer.toString(h.getId()) + " \", \"value\" : \" " + Integer.toString(h.getValues().get(h.getValues().size()-1)) +  " \"}\' http://localhost:8080/api/test/phmeasures"}; 
						try {
							pr = rt.exec(com);
						}catch(Exception e) {
							System.out.println("Exception Error");
						}
					}
				}
				
				//Esperar tempo real pela proxima medi��o
				int real_freq = (1/(regs_per_day/24));
				TimeUnit.MINUTES.sleep(real_freq * 60);
			}
			
		}
		
		/**
		for(MonitorPH phM:monitoresPH) {
			for(Double i : phM.getValues()) {
				System.out.println(i);
			}
			System.out.println("-------");
		}
		
		for(MonitorHum phM:monitoresHum) {
			for(int i : phM.getValues()) {
				System.out.println(i);
			}
			System.out.println("-------");
		}
		**/
		
		System.out.println("Done.");
	}

	public static int getRandomNumberUsingInts(int min, int max) {  //Retirado de https://www.baeldung.com/java-generating-random-numbers-in-range
	    Random random = new Random();
	    return random.ints(min, max)
	      .findFirst()
	      .getAsInt();
	}
}
