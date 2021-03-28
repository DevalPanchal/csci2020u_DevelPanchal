package sample;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Controller {
    @FXML private Canvas canvas;
    @FXML private GraphicsContext gc;

    private final String GOOGStockURL = "https://query1.finance.yahoo.com/v7/finance/download/GOOG?period1=1262322000&period2=1451538000&interval=1mo&events=history&includeAdjustedClose=true";
    private final String AAPLStockURL = "https://query1.finance.yahoo.com/v7/finance/download/AAPL?period1=1262322000&period2=1451538000&interval=1mo&events=history&includeAdjustedClose=true";
    private List<Float> AAPLStock = new ArrayList<Float>();
    private List<Float> GOOGStock = new ArrayList<Float>();

    public void downloadStockPrices(String url, List<Float> stockClosingList) {
        try {
            InputStream file = new URL(url).openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(file, "UTF-8"));
            // read through the header so that the parsing of floats doesn't cause NumberFormatException
            reader.readLine();
            String line = "";
            while((line = reader.readLine()) != null) {
                String[] columns = line.split(",");
                stockClosingList.add(Float.parseFloat(columns[4].trim()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void drawLinePlot(List<Float> stock1, List<Float> stock2) {
        int height = 500;
        int width = 600;
        int x = 50;
        int y = 500;

        gc.strokeLine(x, y, x, y - 450);
        gc.strokeLine(x, y, x + 600, y);

        int dataPoint = Math.max(stock1.size(), stock1.size());
        float stockMax1 = Collections.max(stock1);
        float stockMax2 = Collections.max(stock2);

        float xAxis = (float) width / dataPoint;
        float yAxis = (float) height / Math.max(stockMax1, stockMax2);


        plotLine(stock1, height, yAxis, xAxis, Color.BLUE);
        plotLine(stock2, height, yAxis, xAxis, Color.RED);
    }

    public void plotLine(List<Float> stock, int height, float yAxis, float xAxis, Color color) {
        gc.setStroke(color);

        for (int i = 0; i < stock.size() - 1; i++) {
            gc.strokeLine(i * xAxis + 50, height - stock.get(i) * yAxis, (i+1)*xAxis + 50,height-stock.get(i + 1) * yAxis);
        }
    }

    public void initialize() {
        gc = canvas.getGraphicsContext2D();

        downloadStockPrices(GOOGStockURL, GOOGStock);
        downloadStockPrices(AAPLStockURL, AAPLStock);
        System.out.println(GOOGStock);
        System.out.println(AAPLStock);

        drawLinePlot(GOOGStock, AAPLStock);
    }
}
