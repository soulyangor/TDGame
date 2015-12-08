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
import model.components.Unit;

/**
 *
 * @author Sokolov@ivc.org
 */
public class Love {

    private final int frames = 7;
    private int currentFrame = 1;
    public int show = 0;

    private Unit unit;

    private final Image image;

    public Love(Unit unit) {
        this.unit = unit;
        BufferedImage original = null;
        InputStream input = Window.class.getResourceAsStream("/resources/Любофф.png");
        try {
            original = ImageIO.read(input);
        } catch (IOException ex) {
            Logger.getLogger(DrawPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        image = original.getScaledInstance(512, 64, 1);
    }

    public void draw(Graphics2D g2d) {
        double x = unit.getX();
        double y = unit.getY();
        g2d.drawImage(image, (int) x - 32, (int) y - 54, (int) x + 32, (int) y + 10,
                currentFrame * 64, 0, (currentFrame + 1) * 64, 64, null);
        calcFrame();
    }

    private void calcFrame() {
        if (currentFrame == frames) {
            currentFrame = 0;
            show++;
        } else {
            currentFrame++;
        }
    }

}
