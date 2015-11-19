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
import model.actions.DefineViewable;
import model.actions.findpathalgorithms.ConflictsResolver;
import model.actions.findpathalgorithms.FieldMap;
import model.components.UnitGroup;
import model.tasks.DefinePath;
import model.tasks.SetWalkableCells;

/**
 *
 * @author Хозяин
 */
public class DrawPanel extends JComponent implements Runnable {

    long delay = 60;
    Grid grid;
    DrawablePerson p1, p2;
    UnitGroup group = new UnitGroup();
    DefineViewable action = new DefineViewable();

    public DrawPanel() {
        super();
        grid = new Grid();
        FieldMap.setMap(grid.getGrid(), 14);
        FieldMap.setCellSize(50);
        p1 = new DrawablePerson(200, 100);
        p2 = new DrawablePerson(100, 200);
        p1.addTask(new DefinePath(50, 50));
        p2.addTask(new DefinePath(350, 350));
        p1.addTask(new DefinePath(350, 50));
        p2.addTask(new DefinePath(350, 50));
        p1.addTask(new DefinePath(350, 350));
        p2.addTask(new DefinePath(50, 50));
        p1.addTask(new DefinePath(50, 350));
        p2.addTask(new DefinePath(50, 350));

        //Временные операции
        p1.color = Color.BLUE;
        p2.color = Color.RED;
        p2.d = 2;
        
        group.add(p1);
        group.add(p2);
        group.setTask(new SetWalkableCells());
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
            //   System.out.println("Время отрисовки: " + dt + " мс");
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
        action.setUnit(p1);
        action.setGroup(group);
        action.act();
    //    for (Unit unit : action.getUnits()) {
    //        if (unit == p2) {
                p2.setGraphics(g2d);
                p2.draw();
      //      }
       // }
        g2d.drawRect(0, 0, 700, 500);
    }

}
