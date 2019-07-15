package charts;

import java.util.ArrayList;
import org.gicentre.utils.stat.*;
import processing.core.*;

public class Line extends PApplet {

    XYChart lineChart;

    ArrayList<Button> buttons = new ArrayList<>();
    int selected = 0;

    @Override
    public void settings() {
        size(800, 800);
    }

    @Override
    public void setup() {
        frameRate(60);
        textFont(createFont("Arial", 10), 10);

        // Both x and y data set here.  
        lineChart = new XYChart(this);
//        lineChart.setData(new float[]{1900, 1910, 1920, 1930, 1940, 1950,
//            1960, 1970, 1980, 1990, 2000},
//                new float[]{6322, 6489, 6401, 7657, 9649, 9767,
//                    12167, 15154, 18200, 23124, 28645});

        // Axis formatting and labels.
        lineChart.showXAxis(true);
        lineChart.showYAxis(true);
        lineChart.setMinY(0);

        //lineChart.setYFormat("%f");  // Monetary value in $US
        //lineChart.setXFormat("%f");      // Year
        // Symbol colours
        //lineChart.setPointColour(color(180, 50, 50, 100));
        lineChart.setPointSize(0);
        lineChart.setLineWidth(3);
        lineChart.setLineColour(color(0, 0, 255, 100));

        buttons.add(new Button(this, 125, 650, 1, "Vision"));
        buttons.add(new Button(this, 325, 650, 1, "Metabolism"));
        buttons.add(new Button(this, 525, 650, 1, "Velocity"));
    }

    @Override
    public void draw() {
        background(255);
        textSize(9);
        if (aquarium.Aquarium.data.size() > 0) {
            lineChart.setData(aquarium.Aquarium.data.get(selected));
        }
        lineChart.draw(50, 100, 700, 500);
        fill(120);
        textAlign(CENTER, CENTER);
        textSize(50);
        text(buttons.get(selected).text, width / 2, 50);

        // Draw Buttons
        for (Button b : buttons) {
            b.draw(g);
        }
    }

    @Override
    public void mouseReleased() {
        for (int i = 0; i < buttons.size(); i++) {
            if (buttons.get(i).click()) {
                selected = i;
            }
        }
    }

}
