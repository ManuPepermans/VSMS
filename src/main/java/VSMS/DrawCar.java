package VSMS;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DrawCar extends JPanel{
    int D_W = 640;
    int D_H =400;

    public List<Car> cars;

    public DrawCar() {
        //cars = new ArrayList<>();

    }

    public void addCar(int x, int y)
    {
        cars.add(new Car(x,y));
    }
    public void draw()
    {

                for (Car car : cars) {
                    //car.refactor();
                    repaint();
                }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Car car : cars) {
            car.drawCar(g);
        }
    }

    public class Car {
        private static final int INCREMENT = 5;
        int x, y;
        public Car(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public void drawCar(Graphics g) {
            g.setColor(Color.BLUE);
            g.fillRect(x, y, 20, 6);
            g.setColor(Color.BLACK); // body
            g.fillOval(x + 3, y + 4, 3, 3); // wheel
            g.fillOval(x + 12, y + 4, 3, 3); // wheel
            g.fillRect(x + 3, y - 4, 12, 4); // top
        }

        public void refactor(){

            x = D_W/2 + x;
            y = D_H/2 + y;
        }

    }





}
