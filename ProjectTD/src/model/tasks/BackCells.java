/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.components.Status;
import model.components.Unit;
import model.logics.Cell;

/**
 *
 * @author Хозяин
 */
public class BackCells {

    private static final Map<String, Cell> cells = new HashMap<>();
    private static final List<Unit> units = new ArrayList<>();

    private BackCells() {
    }

    public static boolean add(Cell cell) {
        if (cells.containsKey(cell.createKey())) {
            return false;
        } else {
            cells.put(cell.createKey(), cell);
            return true;
        }
    }

    public static void remove(Cell cell) {
        cells.remove(cell.createKey());
    }

    public static boolean addUnit(Unit unit) {
        if (units.contains(unit)) {
            return false;
        } else {
            units.add(unit);
            return true;
        }
    }

    public static void removeUnit(Unit unit) {
        units.remove(unit);
    }

    public static void setStandUnit(Unit unit) {
        for (Unit u : units) {
            if (u != unit) {
                u.setStatus(Status.STAND);
            }
        }
    }

    public static void setWaitUnit() {
        for (Unit u : units) {
            u.setStatus(Status.MOVE);
        }
    }

}
