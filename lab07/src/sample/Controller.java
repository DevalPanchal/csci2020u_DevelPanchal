package sample;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;

import java.util.Map;
import java.util.TreeMap;

public class Controller {
    @FXML
    private Canvas mainCanvas;

    @FXML
    private GraphicsContext gc;

    FileLoader loader = new FileLoader("weatherwarnings-2015.csv");

    private Map<String, Integer> weatherDataMap = new TreeMap<>();

    private static Color[] pieColours = {
            Color.AQUA, Color.GOLD, Color.DARKORANGE, Color.DARKSALMON
    };

    @FXML
    public void initialize() {
        gc = mainCanvas.getGraphicsContext2D();

        loader.loadFile();
        weatherDataMap = loader.getWeatherData();
//        System.out.printf("Weather Data: %s\n", weatherDataMap);
        drawPieChart(500, 300, weatherDataMap, pieColours);
        drawLegend(500, 25, "Weather Warning", weatherDataMap, pieColours);
    }

    public void drawPieChart(int w, int h, Map<String, Integer> tempMap, Color[]  color) {
        int arcSum = 0;
        int colorIndex = 0;
        double radius = 0;

        for (Map.Entry<String, Integer> entry: tempMap.entrySet()) {
            arcSum += entry.getValue();
        }
//        System.out.println(tempMap);

        for (Map.Entry<String, Integer> entry: tempMap.entrySet()) {
            double rad = 360 * ( (double) entry.getValue() / arcSum);
            gc.setFill(color[colorIndex]);
            colorIndex++;

            gc.fillArc(w, h, 240, 240, radius, rad, ArcType.ROUND);
            radius += rad;

        }
    }

    public void drawLegend(int x, int y, String title, Map<String, Integer> tempMap, Color[] color) {
        gc.setFont(new Font("Calibri", 20));
        gc.setFill(Color.BLACK);
        gc.fillText(title, x, y);

        double YOffset = 150;
        for (Map.Entry<String, Integer> entry: tempMap.entrySet()) {
            gc.fillText(entry.getKey(), x + 30, YOffset);
            YOffset += 20;
        }

        YOffset = 135;
        for(int i = 0; i < color.length; i++) {
            gc.setFill(pieColours[i]);
            gc.fillRect(x, YOffset, y, 15);
            YOffset += 20;
        }
    }
}