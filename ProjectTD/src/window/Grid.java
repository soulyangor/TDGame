/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package window;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Хозяин
 */
public class Grid {

    private int[][] grid;
    private Image image;
    private Image imgTw;
    private Image imgShadow;
    private Graphics2D g2d;

    public Grid() {
        Random rand = new Random();
        grid = new int[14][14];
        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 14; j++) {
                grid[i][j] = 0;
                if ((j == 5) || (j == 6) || (j == 4) || (j == 3)) {
                    grid[i][j] = 10;
                }
                if (i == 9) {
                    grid[i][j] = 0;
                }
            }
        }
        BufferedImage original = null;
        InputStream input = Window.class.getResourceAsStream("/resources/tile.png");
        try {
            original = ImageIO.read(input);
        } catch (IOException ex) {
            Logger.getLogger(DrawPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        image = original.getScaledInstance(64, 64, 1);
        /*input = Window.class.getResourceAsStream("/resources/shadow.png");
         try {
         original = ImageIO.read(input);
         } catch (IOException ex) {
         Logger.getLogger(DrawPanel.class.getName()).log(Level.SEVERE, null, ex);
         }
         imgTw = original.getScaledInstance(64, 64, 1);
         input = Window.class.getResourceAsStream("/resources/twilight.png");
         try {
         original = ImageIO.read(input);
         } catch (IOException ex) {
         Logger.getLogger(DrawPanel.class.getName()).log(Level.SEVERE, null, ex);
         }
         imgShadow = original.getScaledInstance(64, 64, 1);*/
    }

    public void setGraphics(Graphics2D g2d) {
        this.g2d = g2d;
    }

    public void drawGrid(DrawablePerson p) {
        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 10; j++) {
                g2d.drawImage(image, i * 50, j * 50, 50, 50, null);
                g2d.setColor(Color.BLACK);
                g2d.setFont(new Font(Font.DIALOG, Font.ITALIC, 10));
                g2d.drawString("(" + i + "," + j + ")", i * 50, j * 50 + 10);
            }
        }
    }

    public int[][] getGrid() {
        return this.grid;
    }

}
