/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package window;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import model.logics.Cell;
import model.components.Status;
import model.logics.Area;
import model.logics.areas.Square;
import model.units.Insertable;
import model.units.Person;

/**
 *
 * @author Хозяин
 */
public class DrawablePerson extends Person implements Insertable {

    private final int frames = 8;
    private int currentFrame = 1;

    private Graphics2D g2d;
    private final Image image;

    public boolean isAtack;
    public boolean atacking = false;

    //временные поля
    private final Area area = new Square(2);
    public Color color;
    public int d;

    public DrawablePerson(double x, double y, String res) {
        super(x, y, 3, 150, 10);
        BufferedImage original = null;
        InputStream input = Window.class.getResourceAsStream(res);
        try {
            original = ImageIO.read(input);
        } catch (IOException ex) {
            Logger.getLogger(DrawPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        image = original.getScaledInstance(576, 256, 1);
    }

    public int getIntX() {
        double x = super.getX();
        return (int) (x / 50);
    }

    public int getIntY() {
        double y = super.getY();
        return (int) (y / 50);
    }

    public void setGraphics(Graphics2D g2d) {
        this.g2d = g2d;
    }

    public void draw() {
        double x = super.getX();
        double y = super.getY();
        int fy = 2;
        double angle = super.getAngle();
        if (((angle - 5 * Math.PI / 4) > 0) && ((-angle + 7 * Math.PI / 4) > 0)) {
            fy = 0;
        }
        if (((angle - 3 * Math.PI / 4) > 0) && ((-angle + 5 * Math.PI / 4) > 0)) {
            fy = 1;
        }
        if (((angle - Math.PI / 4) > 0) && ((-angle + 3 * Math.PI / 4) > 0)) {
            fy = 2;
        }
        if (((angle - Math.PI / 4) < 0) || ((angle - 7 * Math.PI / 4) > 0)) {
            fy = 3;
        }
        if ((super.getStatus() == Status.STAND)
                || (super.getStatus() == Status.WAIT)) {
            g2d.drawImage(image, (int) x - 1 * 32 - 8, (int) y - 1 * 32 - 8, (int) x + 3 * 32 - 8, (int) y + 3 * 32 - 8,
                    0, fy * 64, 64, (fy + 1) * 64, null);
        } else {
            /*g2d.drawImage(image, (int) x - 32, (int) y - 32, (int) x + 32, (int) y + 32,
             currentFrame * 64, fy * 64, (currentFrame + 1) * 64, (fy + 1) * 64, null);*/
            g2d.drawImage(image, (int) x - 1 * 32 - 8, (int) y - 1 * 32 - 8, (int) x + 3 * 32 - 8, (int) y + 3 * 32 - 8,
                    currentFrame * 64, fy * 64, (currentFrame + 1) * 64, (fy + 1) * 64, null);
        }
        g2d.setColor(color);
        g2d.drawLine((int) x, (int) y,
                (int) (x + 50 * Math.cos(angle)), (int) (y + 50 * Math.sin(angle)));
        // Временная логика для отладки
        Cell cell = super.getExecutedTask().getCurCell();
        g2d.setColor(color);
        if (cell != null) {
            g2d.drawRect(cell.getX() * 50 + 15 + d, cell.getY() * 50 + 15 + d,
                    20 - 2 * d, 20 - 2 * d);
        }
        while (cell != null) {
            g2d.drawArc(cell.getX() * 50 + 15 + d, cell.getY() * 50 + 15 + d,
                    20 - 2 * d, 20 - 2 * d, 0, 360);
            cell = cell.getCell();
        }
        calcFrame();
    }

    private void calcFrame() {
        if (currentFrame == frames) {
            currentFrame = 1;
        } else {
            currentFrame++;
        }
    }

    @Override
    public String toString() {
        if (this.color == Color.RED) {
            return "Красный";
        }
        if (this.color == Color.BLUE) {
            return "Синий";
        }
        if (this.color == Color.CYAN) {
            return "Бирюзовый";
        }
        if (this.color == Color.BLACK) {
            return "Чёрный";
        }
        return "другой цвет";
    }

    @Override
    public Area getArea() {
        return area;
    }

}
