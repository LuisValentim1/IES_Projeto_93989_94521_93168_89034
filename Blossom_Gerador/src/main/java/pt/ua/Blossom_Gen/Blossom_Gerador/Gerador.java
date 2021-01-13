package pt.ua.Blossom_Gen.Blossom_Gerador;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.io.*;

public class Gerador {
	
	//Dados estáticos que definem o que gerar 
	//Deve ser alterado conforme a quantidade/frequencia de dados que queremos gerar
	// TODO ALTEREI DE 48 PARA 3 PARA SER MAIS FACIL DEBUG
	static int regs_per_day = 3; 				// Numero de registos que os monitores fazem por dia.
	static int days = 1;
	static String monitor_type[] = {"Low", "Medium", "High"};     // Gerar monitores especificos
																		   // Low -> ph acido / humidade baixa
																		   // Medium -> ph neutro / humidade m�dia
																		   // High -> ph b�sico / humidade alta

	public static void main(String[] args) throws InterruptedException, IOException{
		
		Process pr;
		String data;
		int opt;
		BufferedReader buffer;
		
		//Arrays para guardar os monitores criados
		ArrayList<MonitorPH> monitoresPH = new ArrayList<MonitorPH>();
		ArrayList<MonitorHum> monitoresHum = new ArrayList<MonitorHum>();
		
		//Encontrar os ids de monitores de ph disponiveis
		pr = Runtime.getRuntime().exec(getCommand(false));
		buffer = new BufferedReader(new InputStreamReader(pr.getInputStream()));
		
		while ((data = buffer.readLine()) != null) {
		    for(String i : data.substring(1, data.length()-1).split("[,]+")) {
				opt = randomInt(0, 2);
			    monitoresPH.add(new MonitorPH(Integer.parseInt(i), monitor_type[opt], regs_per_day, days));	
		    }
		}
		
		pr = Runtime.getRuntime().exec(getCommand(true));  
		buffer = new BufferedReader(new InputStreamReader(pr.getInputStream()));

		while ((data = buffer.readLine()) != null) {
		    for(String i : data.substring(1, data.length()-1).split("[,]+")) {
				opt = randomInt(0, 2);
				monitoresHum.add(new MonitorHum(Integer.parseInt(i), monitor_type[opt], regs_per_day, days));
		    }
		}
		
		//Adicionar todos os valores de pH à base de dados
		for(int i = 0; i<monitoresPH.size(); i++) {
			for(int z = 0; z<monitoresPH.get(i).getValues().size(); z++) {
				Runtime.getRuntime().exec(postCommand(false, Integer.toString(monitoresPH.get(i).getId()), Double.toString(monitoresPH.get(i).getValues().get(z))));
			}
		}
				
		//Adicionar todos os valores de humidade à base de dados
		for(int i = 0; i<monitoresHum.size(); i++) {
			for(int z = 0; z<monitoresHum.get(i).getValues().size(); z++) {
				Runtime.getRuntime().exec(postCommand(false, Integer.toString(monitoresPH.get(i).getId()), Double.toString(monitoresPH.get(i).getValues().get(z))));
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
						String[] com = {"curl -X POST -H \"Content-Type: application/json\" -d \'{\"sensorId\" : \" " + Integer.toString(p.getId()) + " \", \"value\" : \" " + Double.toString(p.getValues().get(p.getValues().size()-1)) +  " \"}\' http://localhost:8080/api/test/phmeasures"}; 
						pr = Runtime.getRuntime().exec(com);
						
					}
					if(mode == "Medium") {
						p.generateNeutralSoil(1/regs_per_day);
						String[] com = {"curl -X POST -H \"Content-Type: application/json\" -d \'{\"sensorId\" : \" " + Integer.toString(p.getId()) + " \", \"value\" : \" " + Double.toString(p.getValues().get(p.getValues().size()-1)) +  " \"}\' http://localhost:8080/api/test/phmeasures"}; 
						pr = Runtime.getRuntime().exec(com);
						
					}
					if(mode == "High") {
						p.generateBasicSoil(1/regs_per_day);
						String[] com = {"curl -X POST -H \"Content-Type: application/json\" -d \'{\"sensorId\" : \" " + Integer.toString(p.getId()) + " \", \"value\" : \" " + Double.toString(p.getValues().get(p.getValues().size()-1)) +  " \"}\' http://localhost:8080/api/test/phmeasures"}; 
						pr = Runtime.getRuntime().exec(com);
					}
				}
				
				//Adicionar 1 medi��o aos monitores de humidade
				for(int i = 0; i<monitoresHum.size(); i++) {
					MonitorHum h = monitoresHum.get(i);
					String mode = h.getMode();
					if(mode == "Low") {
						h.generateDrySoil(1/regs_per_day);
						String[] com = {"curl -X POST -H \"Content-Type: application/json\" -d \'{\"sensorId\" : \" " + Integer.toString(h.getId()) + " \", \"value\" : \" " + Integer.toString(h.getValues().get(h.getValues().size()-1)) +  " \"}\' http://localhost:8080/api/test/phmeasures"}; 
						pr = Runtime.getRuntime().exec(com);
						
					}
					if(mode == "Medium") {
						h.generateNeutralSoil(1/regs_per_day);
						String[] com = {"curl -X POST -H \"Content-Type: application/json\" -d \'{\"sensorId\" : \" " + Integer.toString(h.getId()) + " \", \"value\" : \" " + Integer.toString(h.getValues().get(h.getValues().size()-1)) +  " \"}\' http://localhost:8080/api/test/phmeasures"}; 
						pr = Runtime.getRuntime().exec(com);
					}
					if(mode == "High") {
						h.generateMoistSoil(1/regs_per_day);
						String[] com = {"curl -X POST -H \"Content-Type: application/json\" -d \'{\"sensorId\" : \" " + Integer.toString(h.getId()) + " \", \"value\" : \" " + Integer.toString(h.getValues().get(h.getValues().size()-1)) +  " \"}\' http://localhost:8080/api/test/phmeasures"}; 
						pr = Runtime.getRuntime().exec(com);
						
					}
				}
				
				//Esperar tempo real pela proxima medi��o
				int real_freq = (1/(regs_per_day/24));
				TimeUnit.MINUTES.sleep(real_freq * 60);
			}
			
		}
	}

	public static int randomInt(int min, int max) {  
		//Retirado de https://www.baeldung.com/java-generating-random-numbers-in-range
	    return (new Random()).ints(min, max).findFirst().getAsInt();
	}

	public static double randomDouble(double rangeMin, double rangeMax) {
		return (rangeMin + (rangeMax - rangeMin) * (new Random()).nextDouble());
	}

	public static String getCommand(boolean isHumidity){
		String type = (isHumidity) ? "hum" : "ph";
		return "curl http://host.docker.internal/api/test/"+type+"ids";
	}

	public static String postCommand(boolean isHumidity, String id, String value) {
		String type = (isHumidity) ? "hum" : "ph";
		String str = "curl -X POST -H \"Content-Type: application/json\" -d ";
		str += "'{\"sensorId\" : \""+id+"\", \"value\" : \""+value+"\"}' ";
		str += "http://host.docker.internal/api/test/"+type+"measures";
		return str;
	}
}
