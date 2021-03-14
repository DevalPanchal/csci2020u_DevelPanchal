package sample;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class FileLoader {

    private String fileName;
    private Map<String, Integer> weatherDataMap;

    public FileLoader(String fileName) {
        this.fileName = fileName;
        weatherDataMap = new TreeMap<>();
    }

    public Map<String, Integer> getWeatherData() {
        return this.weatherDataMap;
    }

    public void loadFile() {
        String line = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.fileName));
            while ((line = reader.readLine()) != null){
                String[] columns = line.split(",");
                // column contains all the count for each of the weather warnings
                countWeatherWarnings(columns[5], weatherDataMap);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void countWeatherWarnings(String word, Map<String, Integer> temp) {
        if (temp.containsKey(word)) {
            int previous = temp.get(word);
            temp.put(word, previous + 1);
        } else {
            temp.put(word, 1);
        }
    }

    public static void main(String[] args) {
        FileLoader loader = new FileLoader("weatherwarnings-2015.csv");
        loader.loadFile();
        System.out.println(loader.weatherDataMap);
    }
}
