package sample;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
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

    public void drawLinePlot(int x, int y, List<Float> stock1, List<Float> stock2) {
        // draw vertical line
        gc.strokeLine(x, y, x, y - 450);
        // draw horizontal line
        gc.strokeLine(x, y, x + 600, y);

        for (int i = 0; i < stock1.size(); i++) {
            gc.strokeLine(x, 500 - stock1.get(i), x + 10, 500 - (stock1.get(i)));
            x = x + 10;
        }

    }

    public void initialize() {
        gc = canvas.getGraphicsContext2D();

        downloadStockPrices(GOOGStockURL, GOOGStock);
        downloadStockPrices(AAPLStockURL, AAPLStock);
        System.out.println(GOOGStock);
        System.out.println(AAPLStock);

        drawLinePlot(50, 500, GOOGStock, AAPLStock);
    }
}
