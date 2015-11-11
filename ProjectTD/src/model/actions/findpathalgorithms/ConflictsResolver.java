/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.actions.findpathalgorithms;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Хозяин
 */
public class ConflictsResolver {

    private static final Map<String, Cell> cells = new HashMap<>();

    private ConflictsResolver() {
    }

    public static boolean canWalk(Cell cell) {
        if (cells.containsKey(cell.createKey())) {
            return false;
        } else {
            cells.put(cell.createKey(), cell);
            return true;
        }
    }

    public static void clear() {
        cells.clear();
    }

}
