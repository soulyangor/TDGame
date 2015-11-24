/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.actions.findpathalgorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Хозяин
 */
public class PathAnalyser {

    private static final List<Cell> pathes = new ArrayList<>();
    private static final Map<String, Conflict> conflicts = new HashMap<>();

    private PathAnalyser() {
    }

    public static void addPath(Cell cell) {
        pathes.add(cell);
        analysePath(cell);
    }
    
    public static void clearPathes(){
        pathes.clear();
    }
    
    public static Conflict isConflictable(Cell cell){
        return conflicts.get(cell.createKey());
    }

    private static void analysePath(Cell cell) {
        for (Cell p : pathes) {
            LinkedHashMap mp = p.formPath();
            Cell current = cell;
            while (current != null) {
                if (mp.containsKey(current.createKey())) {
                    addConflict(new Conflict(current));
                }
                current = current.getParent();
            }
        }
    }

    private static void addConflict(Conflict conflict) {
        if (!conflicts.containsKey(conflict.getKey())) {
            conflicts.put(conflict.getKey(), conflict);
        }
    }

}
