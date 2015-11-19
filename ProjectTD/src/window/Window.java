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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import model.actions.findpathalgorithms.FieldMap;

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
        frame.setSize(720, 620);
        JButton btnIncrease = new JButton("Increase delay");
        JButton btnDecrease = new JButton("Decrease delay");
        JButton btnSave = new JButton("Save map");
        JLabel txt = new JLabel();
        btnDecrease.setSize(200, 30);
        btnIncrease.setSize(200, 30);
        btnSave.setSize(200, 30);
        txt.setSize(200, 30);
        btnIncrease.setLocation(0, 520);
        btnDecrease.setLocation(220, 520);
        btnSave.setLocation(0, 560);
        txt.setLocation(440, 520);
        btnIncrease.setVisible(true);
        btnDecrease.setVisible(true);
        btnSave.setVisible(true);
        txt.setVisible(true);
        DrawPanel panel = new DrawPanel();
        panel.setSize(700, 500);
        frame.add(panel);
        panel.add(btnDecrease);
        panel.add(btnIncrease);
        panel.add(btnSave);
        txt.setText("Delay is " + panel.delay + " ms");
        panel.add(txt);
        btnSave.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int[][] map = FieldMap.getMap();
                try  {
                    PrintWriter writer1 = new PrintWriter(new OutputStreamWriter(new FileOutputStream("map1.txt"), "utf-8"));
                    PrintWriter writer2 = new PrintWriter(new OutputStreamWriter(new FileOutputStream("map2.txt"), "utf-8"));
                    writer1.write(map.length+"");
                    writer2.write(map.length+"");
                    for (int i = 0; i < map.length; i++) {
                        writer1.println();
                        writer2.println();
                        for (int j = 0; j < map[i].length; j++) {
                            writer1.write( map[i][j]+" ");
                            if(map[i][j]<0){
                                writer2.write("0");
                            }else{
                                writer2.write( map[i][j]+" ");
                            }
                        }
                    }
                 writer1.close();
                 writer2.close();
                } catch (IOException ex) {

                    System.out.println(ex.getMessage());
                }
            }
        });
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
