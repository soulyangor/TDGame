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
import model.actions.findpathalgorithms.ConflictResolver;
import model.actions.findpathalgorithms.FieldMap;
import model.components.TDComponent;
import model.components.UnitGroup;
import model.tasks.DefinePath;
import model.tasks.FMove;

/**
 *
 * @author Хозяин
 */
public class DrawPanel extends JComponent implements Runnable {

    long delay = 60;
    Grid grid;
    DrawablePerson p1, p2;
    UnitGroup group = new UnitGroup();

    public DrawPanel() {
        super();
        grid = new Grid();
        FieldMap.setMap(grid.getGrid(), 14);
        FieldMap.setCellSize(50);
        p1 = new DrawablePerson(200, 100);
        p2 = new DrawablePerson(100, 200);
        p1.addTask(new DefinePath(50, 50, group));
        p2.addTask(new DefinePath(350, 350, group));
        p1.addTask(new DefinePath(350, 50, group));
        p2.addTask(new DefinePath(350, 50, group));
        p1.addTask(new DefinePath(350, 350, group));
        p2.addTask(new DefinePath(50, 50, group));
        p1.addTask(new DefinePath(50, 350, group));
        p2.addTask(new DefinePath(50, 350, group));

        /* p1.addTask(new FMove(50, 50, group));
         p1.addTask(new FMove(350, 50, group));
         p1.addTask(new FMove(350, 350, group));
         p1.addTask(new FMove(50, 350, group));*/
        //Временные операции
        p1.color = Color.BLUE;
        p2.color = Color.RED;
        p2.d = 2;

        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 14; j++) {
                if (grid.getGrid()[i][j] > 0) {
                    group.add(new Box(FieldMap.toDouble(i), FieldMap.toDouble(j)));
                }
            }
        }

        group.add(p1);
        group.add(p2);
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
        for (TDComponent c : group) {
            if (c instanceof Box) {
                Box b = (Box) c;
                b.setGraphics(g2d);
                b.draw();
            }
        }
        g2d.drawRect(0, 0, 700, 700);
        ConflictResolver.print(g2d);
    }

}
