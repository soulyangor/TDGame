/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package window;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;

/**
 *
 * @author Хозяин
 */
public class Window {

    private static JFrame frame;

    public static void main(String[] args) {
        frame = new JFrame("Тест TD");
        BufferedImage original = null;
        InputStream input = Window.class.getResourceAsStream("/resources/Argos Icon 15.png");
        try {
            original = ImageIO.read(input);
        } catch (IOException ex) {
            Logger.getLogger(DrawPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        Image image = original.getScaledInstance(48, 48, 1);
        frame.setIconImage(image);
        frame.setSize(720, 600);
        JButton btnIncrease = new JButton("Increase delay");
        JButton btnDecrease = new JButton("Decrease delay");
        JLabel txt = new JLabel();
        btnDecrease.setSize(200, 30);
        btnIncrease.setSize(200, 30);
        txt.setSize(200, 30);
        btnIncrease.setLocation(0, 520);
        btnDecrease.setLocation(220, 520);
        txt.setLocation(440, 520);
        btnIncrease.setVisible(true);
        btnDecrease.setVisible(true);
        txt.setVisible(true);
        DrawPanel panel = new DrawPanel();
        panel.setSize(700, 500);
        frame.add(panel);
        panel.add(btnDecrease);
        panel.add(btnIncrease);
        txt.setText("Delay is " + panel.delay + " ms");
        panel.add(txt);
        btnIncrease.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                panel.delay += 10;
                txt.setText("Delay is " + panel.delay + " ms");
            }
        });
        btnDecrease.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (panel.delay < 10) {
                    return;
                }
                panel.delay -= 10;
                txt.setText("Delay is " + panel.delay + " ms");
            }
        });
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

}
