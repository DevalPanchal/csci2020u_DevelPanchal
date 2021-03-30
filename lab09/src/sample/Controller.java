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

    public void drawLinePlot(int initialX, int initialY, List<Float> stock1, List<Float> stock2) {
        int height = 500;
        int width = 600;
        int x = 50;
        int y = 500;

        // draw vertical line
        gc.strokeLine(x, y, x, y - initialY);
        // draw horizontal line
        gc.strokeLine(x, y, x + initialX, y);

        int dataPoint = Math.max(stock1.size(), stock1.size());
        float stockMax1 = Collections.max(stock1);
        float stockMax2 = Collections.max(stock2);

        float xDifference = (float) width / dataPoint;
        float yDifference = (float) height / Math.max(stockMax1, stockMax2);


        plotLine(stock1, height, yDifference, xDifference, Color.RED);
        plotLine(stock2, height, yDifference, xDifference, Color.BLUE);
    }

    public void plotLine(List<Float> stock, int height, float yDifference, float xDifference, Color color) {
        gc.setStroke(color);
        int size = stock.size();
        for (int i = 0; i < size - 1; i++) {
            gc.strokeLine(i * xDifference + 50, height - stock.get(i) * yDifference, (i+1)*xDifference + 50,height-stock.get(i + 1) * yDifference);
        }
    }

    public void initialize() {
        gc = canvas.getGraphicsContext2D();

        downloadStockPrices(GOOGStockURL, GOOGStock);
        downloadStockPrices(AAPLStockURL, AAPLStock);
        System.out.println(GOOGStock);
        System.out.println(AAPLStock);

        drawLinePlot(600,450,GOOGStock, AAPLStock);
    }
}
