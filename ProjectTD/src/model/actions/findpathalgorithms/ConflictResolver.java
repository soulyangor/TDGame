/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.actions.findpathalgorithms;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Хозяин
 */
public class ConflictResolver {

    private static final Map<String, Cell> cells = new HashMap<>();

    private ConflictResolver() {
    }

    public static boolean addCell(Cell cell) {
        if (cells.containsKey(cell.createKey())) {
            return false;
        }
        cells.put(cell.createKey(), cell);
        return true;
    }

    public static void removeCell(Cell cell) {
        cells.remove(cell.createKey());
    }

    public static void print(Graphics2D g2d) {
        if (cells.size() < 2) {
            System.out.println("точек: " + cells.size());
        }
        for (String key : cells.keySet()) {
            g2d.setColor(Color.cyan);
            g2d.drawOval(50 * cells.get(key).getX(), 50 * cells.get(key).getY(), 50, 50);
        }
    }

}
