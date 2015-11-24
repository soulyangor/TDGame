/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package window;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import model.actions.findpathalgorithms.FieldMap;
import model.components.Unit;

/**
 *
 * @author Sokolov@ivc.org
 */
public class Box extends Unit {

    private Image image;
    private Graphics2D g2d;

    public Box(double x, double y) {
        super(x, y);
        BufferedImage original = null;
        InputStream input = Window.class.getResourceAsStream("/resources/box.png");
        try {
            original = ImageIO.read(input);
        } catch (IOException ex) {
            Logger.getLogger(DrawPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        image = original.getScaledInstance(64, 64, 1);
    }

    public void setGraphics(Graphics2D g2d) {
        this.g2d = g2d;
    }

    public void draw() {
        int i =FieldMap.toInteger(super.getX());
        int j =FieldMap.toInteger(super.getY());
        g2d.drawImage(image, i * 50, j * 50, 50, 50, null);
    }

}
