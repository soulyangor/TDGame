/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package window;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import model.actions.findpathalgorithms.Logic;
import model.components.TDComponent;
import model.components.UnitGroup;
import model.tasks.MoveTo;

/**
 *
 * @author Хозяин
 */
public class DrawPanel extends JComponent implements Runnable {

    long delay = 60;
    Grid grid;
    DrawablePerson p1, p2, p3, p4;
    UnitGroup group = new UnitGroup();

    public DrawPanel() {
        super();
        grid = new Grid();
        Logic.generate(14);
        Logic.setCellSize(50);
        p1 = new DrawablePerson(200, 100);
        p2 = new DrawablePerson(100, 200);
        p3 = new DrawablePerson(200, 200);
        p4 = new DrawablePerson(100, 100);
        
        p1.addTask(new MoveTo(50, 50));
        p2.addTask(new MoveTo(350, 350));
        p1.addTask(new MoveTo(350, 50));
        p2.addTask(new MoveTo(350, 50));
        p1.addTask(new MoveTo(350, 350));
        p2.addTask(new MoveTo(50, 50));
        p1.addTask(new MoveTo(50, 350));
        p2.addTask(new MoveTo(50, 350));
        
        p3.addTask(new MoveTo(50, 50));
        p4.addTask(new MoveTo(350, 350));
        p3.addTask(new MoveTo(350, 50));
        p4.addTask(new MoveTo(350, 50));
        p3.addTask(new MoveTo(350, 350));
        p4.addTask(new MoveTo(50, 50));
        p3.addTask(new MoveTo(50, 350));
        p4.addTask(new MoveTo(50, 350));

        /* p1.addTask(new FMove(50, 50, group));
         p1.addTask(new FMove(350, 50, group));
         p1.addTask(new FMove(350, 350, group));
         p1.addTask(new FMove(50, 350, group));*/
        //Временные операции
        p1.color = Color.BLUE;
        p2.color = Color.RED;
        p2.d = 2;
        p3.color = Color.CYAN;
        p3.d = 1;
        p4.color = Color.BLACK;
        p4.d = 3;

        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 14; j++) {
                if (grid.getGrid()[i][j] > 0) {
                    Box b = new Box(Logic.toDouble(i), Logic.toDouble(j));
                    Logic.setUnit(b);
                    group.add(b);
                }
            }
        }

        group.add(p1);
        group.add(p2);
        group.add(p3);
        group.add(p4);
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (true) {
            long t = System.currentTimeMillis();
            group.executeTask();
            repaint();
            long dt = System.currentTimeMillis() - t;
            if (dt > 0) {
                System.out.println("Время отрисовки: " + dt + " мс");
            }
            try {
                if (dt > delay) {
                    continue;
                }
                Thread.sleep(delay - dt);
            } catch (InterruptedException ex) {
                Logger.getLogger(DrawPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        grid.setGraphics(g2d);
        p1.setGraphics(g2d);
        grid.drawGrid(p1);
        p1.draw();
        p2.setGraphics(g2d);
        p2.draw();
        p3.setGraphics(g2d);
        p3.draw();
        p4.setGraphics(g2d);
        p4.draw();
        for (TDComponent c : group) {
            if (c instanceof Box) {
                Box b = (Box) c;
                b.setGraphics(g2d);
                b.draw();
            }
        }
        g2d.drawRect(0, 0, 700, 500);
    }

}
