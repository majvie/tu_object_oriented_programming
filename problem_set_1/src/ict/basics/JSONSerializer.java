package ict.basics;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.*; 
import rbs.flight.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;  

//Maximilian Vieweg, e11806443
//TU Wien, Objektorientiertes Programmieren

public class JSONSerializer {
	static GsonBuilder builder;
	static Gson gson;
	static Path dirPath;
	
	static {
		builder = new GsonBuilder(); 
		builder.setPrettyPrinting(); 
		gson = builder.create();
		dirPath = Path.of("");
		// for other subfolder first create directory
		// Files.createDirectories(Paths.get("/path/to/directory"));
	}

	public static void main(String[] args) {
		List<OneWayFlight> flightList = new ArrayList<OneWayFlight>();
		
		flightList.add(new OneWayFlight("0", "Wien", "Beijing", 200));
		flightList.add(new OneWayFlight("1", "Beijing", "Hong Kong", 300));
		flightList.add(new OneWayFlight("2", "Hong Kong", "Los Angeles", 400));
		flightList.add(new OneWayFlight("3", "Los Angeles", "New York", 500));
		flightList.add(new OneWayFlight("4", "New York", "London", 600));
		flightList.add(new OneWayFlight("5", "London", "Berlin", 100));
		
		System.out.println(dirPath.toAbsolutePath());
		String fileName = "oneWayFlights.json";
		JSONSerializer.serializeJSON(flightList, fileName);
		List<OneWayFlight> reconstructedList = JSONSerializer.deserializeJSON(fileName);
		
		for (OneWayFlight flight : reconstructedList) {
			System.out.println(flight);
		}
	}
	
	public static String serializeJSON(List<OneWayFlight> flights, String fileName) {
		try {
			String jsonString = gson.toJson(flights);
			Path filePath = dirPath.resolve(fileName);
			Files.write(filePath, jsonString.getBytes());
			
			return jsonString;
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
			return null;
		}
		
		
	}
	
	public static List<OneWayFlight> deserializeJSON(String fileName) {		
		try {
			Path filePath = dirPath.resolve(fileName);
			String jsonString = Files.readString(filePath);
			Type typeList = new TypeToken<ArrayList<OneWayFlight>>() {}.getType();
			List<OneWayFlight> flightList = gson.fromJson(jsonString, typeList);
			
			return flightList;
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
			return null;
		}

	}
}
