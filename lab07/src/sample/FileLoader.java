package sample;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class FileLoader {

    private String fileName;
    private Map<String, Double> weatherDataMap;

    public FileLoader(String fileName) {
        this.fileName = fileName;
        this.weatherDataMap = new TreeMap<>();
    }

    public void loadFile() {
        String line = "";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.fileName));
            while ((line = reader.readLine()) != null){
                String[] columns = line.split(",");
                System.out.println(columns[0]);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void countWeatherWarnings(String word, Map<String, Double> temp) {
        if (temp.containsKey(word)) {

        }
    }

    public static void main(String[] args) {
        FileLoader loader = new FileLoader("weatherwarnings-2015.csv");
        loader.loadFile();
    }
}
