package charts;

import org.gicentre.utils.stat.*;
import processing.core.*;

public class Line extends PApplet {

    XYChart lineChart;

    @Override
    public void settings() {
        size(500, 500);
    }

    @Override
    public void setup() {
        frameRate(60);
        textFont(createFont("Arial", 10), 10);

        // Both x and y data set here.  
        lineChart = new XYChart(this);
        lineChart.setData(new float[]{1900, 1910, 1920, 1930, 1940, 1950,
            1960, 1970, 1980, 1990, 2000},
                new float[]{6322, 6489, 6401, 7657, 9649, 9767,
                    12167, 15154, 18200, 23124, 28645});

        // Axis formatting and labels.
        lineChart.showXAxis(true);
        lineChart.showYAxis(true);
        lineChart.setMinY(0);

        //lineChart.setYFormat("%f");  // Monetary value in $US
        //lineChart.setXFormat("%f");      // Year

        // Symbol colours
        lineChart.setPointColour(color(180, 50, 50, 100));
        lineChart.setPointSize(5);
        lineChart.setLineWidth(2);
    }

    @Override
    public void draw() {
        background(255);
        textSize(9);
        //lineChart.setData(aquarium.Aquarium.data);
        lineChart.draw(15, 15, width - 30, height - 30);

        // Draw a title over the top of the chart.
        fill(120);
        textSize(20);
        text("Income per person, United Kingdom", 70, 30);
        textSize(11);
        text("Gross domestic product measured in inflation-corrected $US",
                70, 45);
    }

}
