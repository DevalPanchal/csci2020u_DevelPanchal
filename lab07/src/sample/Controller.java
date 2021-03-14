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
            Color.AQUA, Color.GOLD, Color.DARKORANGE, Color.DARKSALMON, Color.LAWNGREEN, Color.PLUM
    };

    @FXML
    public void initialize() {
        gc = mainCanvas.getGraphicsContext2D();

        loader.loadFile();
        weatherDataMap = loader.getWeatherData();
        System.out.printf("Weather Data: %s\n", weatherDataMap);
        drawPieChart(800, 300, weatherDataMap, pieColours);
    }

    public void drawPieChart(int w, int h, Map<String, Integer> tempMap, Color[]  color) {
        int arcSum = 0;
        int colorIndex = 0;
        double radius = 0;

        for (Map.Entry<String, Integer> entry: tempMap.entrySet()) {
            arcSum += entry.getValue();
        }
        System.out.println(tempMap);

        for (Map.Entry<String, Integer> entry: tempMap.entrySet()) {
            double rad = 360 * ( (double) entry.getValue() / arcSum);
            gc.setFill(color[colorIndex]);
            colorIndex++;

            gc.fillArc(w, h, 240, 240, radius, rad, ArcType.ROUND);
            radius += rad;

        }
    }

    public void drawLegend(int x, int y, Color[] color) {
        gc.setFont(new Font("Calibri", 20));

    }
}
