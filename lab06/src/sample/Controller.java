package sample;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;

public class Controller {
    @FXML
    private Canvas mainCanvas;

    @FXML
    private GraphicsContext gc;

    private static double[] avgHousingPricesByYear = {
            247381.0,264171.4,287715.3,294736.1, 308431.4,322635.9,340253.0,363153.7
    };

    private static double[] avgCommercialPricesByYear = {
            1121585.3,1219479.5,1246354.2,1295364.8, 1335932.6,1472362.0,1583521.9,1613246.3
    };

    private static String[] ageGroups = {
            "18-25", "26-35", "36-45", "46-55", "56-65", "65+"
    };

    private static int[] purchasesByAgeGroup = {
            648, 1021, 2453, 3173, 1868, 2247
    };

    private static Color[] pieColours = {
            Color.AQUA, Color.GOLD, Color.DARKORANGE, Color.DARKSALMON, Color.LAWNGREEN, Color.PLUM
    };

    @FXML
    public void initialize() {
        gc = mainCanvas.getGraphicsContext2D();

        drawBarGraphAvgHousing(350, 700, avgHousingPricesByYear, avgCommercialPricesByYear);
        drawPieChart(800, 300, purchasesByAgeGroup, pieColours);
        drawLegend(gc);
    }


    public void drawBarGraphAvgHousing(int w, int h, double[] data, double[] data2) {

        double xAxis = w / data.length;
        double xAxis2 = w / data2.length;

        double maxVal = Double.NEGATIVE_INFINITY;
        double minVal = Double.MAX_VALUE;

        for (double val: data) {
            if (val > maxVal)
                maxVal = val;
            if (val < minVal)
                minVal = val;
        }
        for (double val: data2) {
            if (val > maxVal)
                maxVal = val;
            if (val < minVal)
                minVal = val;
        }

        double x = 7;
        for (double val: data) {
            double height = (((val - minVal) / (maxVal - minVal)) * h) + 100;
            gc.setFill(Color.RED);
            gc.fillRect(x, (h - height), xAxis, height);
            x += xAxis + 50;
        }

        double x2 = 50;
        for (double val: data2) {
            double height = (((val - minVal) / (maxVal - minVal)) * h) - 100;
            gc.setFill(Color.BLUE);
            gc.fillRect(x2, (h - height), xAxis2, height);
            x2 += xAxis2 + 50;
        }
    }

    public void drawPieChart(int w, int h, int[] data, Color[] color) {
        int arcSum = 0;
        int colorIndex = 0;

        for (int val: data) {
            arcSum += val;
        }
        double radius = 0;

        for (double val: data) {
            double rad = 360 * (val / arcSum);
            gc.setFill(color[colorIndex]);
            colorIndex++;

            gc.fillArc(w, h, 240, 240, radius, rad, ArcType.ROUND);
            radius += rad;
        }
    }

    private void drawLegend(GraphicsContext gc){
        // Bar Graph Legend
        gc.setFont(new Font("Calibri", 20));
        gc.setFill(Color.BLACK);
        gc.fillText("Avg House Prices", 100,100);
        gc.fillText("Avg Commercial Prices", 100,120);

        // Bar Graph Colours
        gc.setFill(Color.RED);
        gc.fillRect(80,85,15,15);
        gc.setFill(Color.BLUE);
        gc.fillRect(80,105,15,15);

        // Pie Graph Legend
        double y = 100;
        for (String word : ageGroups){
            gc.fillText(word, 850, y);
            y += 20;
        }
        // Pie Graph Colours
        y = 85;
        for (int i = 0; i < pieColours.length; i++){
            gc.setFill(pieColours[i]);
            gc.fillRect(830,y,15,15);
            y += 20;
        }
    }
}
