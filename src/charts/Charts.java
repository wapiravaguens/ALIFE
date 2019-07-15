package charts;

import java.util.ArrayList;
import org.gicentre.utils.stat.*;
import processing.core.*;

public class Charts extends PApplet {

    XYChart lineChartPreys;
    XYChart lineChartPreysPredators;
    boolean flag = true;

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
        lineChartPreys = new XYChart(this);
        lineChartPreysPredators = new XYChart(this);

        // Axis formatting and labels.
        lineChartPreys.showXAxis(true);
        lineChartPreys.showYAxis(true);
        lineChartPreys.setMinY(0);
        lineChartPreysPredators.showXAxis(true);
        lineChartPreysPredators.showYAxis(true);
        lineChartPreysPredators.setMinY(0);

        // Symbol colours
        lineChartPreys.setPointSize(0);
        lineChartPreys.setLineWidth(3);
        lineChartPreys.setLineColour(color(0, 0, 255, 100));
        lineChartPreysPredators.setPointSize(0);
        lineChartPreysPredators.setLineWidth(3);
        lineChartPreysPredators.setLineColour(color(255, 0, 0, 100));

        // Buttons
        buttons.add(new Button(this, 125, 650, 1, "Energía Máxima"));
        buttons.add(new Button(this, 325, 650, 1, "Metabolismo"));
        buttons.add(new Button(this, 525, 650, 1, "Tamaño Adulto"));
        buttons.add(new Button(this, 125, 725, 1, "Visión"));
        buttons.add(new Button(this, 325, 725, 1, "Velocidad"));
        buttons.add(new Button(this, 525, 725, 1, "Aceleración"));
    }

    @Override
    public void draw() {
        background(255);
        
        // Get Data
        if (aquarium.Aquarium.data.size() > 0) {
            lineChartPreys.setData(aquarium.Aquarium.data.get(selected));
        }
        if (aquarium.Aquarium.data.size() > 0) {
            lineChartPreysPredators.setData(aquarium.Aquarium.data.get(selected + 6));
        }

        // Draw Chart
        textSize(15);
        fill(120);
        if (flag) {
            lineChartPreys.draw(50, 100, 700, 500);
            fill(0, 0, 255);
        } else {
            lineChartPreysPredators.draw(50, 100, 700, 500);
            fill(255, 0, 0);
        }
        
        // Draw Title
        textAlign(CENTER, CENTER);
        textSize(25);
        text(flag ? "Presa" : "Predador", width / 2, 20);
        text(buttons.get(selected).text, width / 2, 50);

        // Draw Buttons
        for (Button b : buttons) {
            b.draw(g);
        }
    }

    @Override
    public void keyPressed() {
        if (key == 'f') {
            flag = !flag;
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
