/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.logics;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс для работы с алгоритмами поиска
 *
 * @author Sokolov@ivc.org
 */
public class Logic {

    /*---------------------------------Static constants--------------------------------------*/
    private static final double INFINITY = 10e6;

    /**
     * Ищет путь между точками (x0, y0) и (x, y)и возвращает связанные точки
     * начиная от ячейки с координатами(x, y). Алгоритм поиска - А*
     *
     * @param x0 int
     * @param y0 int
     * @param x int
     * @param y int
     * @return Cell
     */
    public static Cell searchPath(int x0, int y0, int x, int y) {
        Map<String, Cell> openPoints = new LinkedHashMap<>();
        Map<String, Cell> closePoints = new LinkedHashMap<>();
        Cell newCell = new Cell(x0, y0);
        Cell curCell;
        newCell.calcH(x, y);
        newCell.calcF();
        openPoints.put(newCell.getKey(), newCell);
        boolean complete = false;
        while ((openPoints.size() > 0) && (!complete)) {
            curCell = Logic.findPointByFmin(openPoints);
            String key = curCell.getKey();
            closePoints.put(key, curCell);
            openPoints.remove(key);
            for (Cell iter : getBorder(curCell)) {
                int i = iter.getX();
                int j = iter.getY();
                int manageValue = Logic.manageLoopByMap(i, j, curCell,
                        complete);
                if (manageValue == 1) {
                    continue;
                }
                if (manageValue == -1) {
                    break;
                }
                complete = (i == x) && (j == y);
                Logic.manageLoopByPoints(i, j, x, y, curCell,
                        openPoints, closePoints);
            }
        }

        String key = Integer.toString(x) + "|" + Integer.toString(y);
        curCell = openPoints.get(key);
        return curCell;
    }

    /**
     * Ищет путь между точками (x0, y0) и (x, y) и возвращает связанные точки
     * начиная от ячейки с координатами(x, y). Используется метод
     * manageAbsLoopByMap
     *
     * @param x0 int
     * @param y0 int
     * @param x int
     * @param y int
     * @return Cell
     */
    public static Cell searchAbsPath(int x0, int y0, int x, int y) {
        Map<String, Cell> openPoints = new LinkedHashMap<>();
        Map<String, Cell> closePoints = new LinkedHashMap<>();
        Cell newCell = new Cell(x0, y0);
        Cell curCell;
        newCell.calcH(x, y);
        newCell.calcF();
        openPoints.put(newCell.getKey(), newCell);
        boolean complete = false;
        while ((openPoints.size() > 0) && (!complete)) {
            curCell = Logic.findPointByFmin(openPoints);
            String key = curCell.getKey();
            closePoints.put(key, curCell);
            openPoints.remove(key);
            for (Cell iter : getBorder(curCell)) {
                int i = iter.getX();
                int j = iter.getY();
                int manageValue = Logic.manageAbsLoopByMap(i, j, curCell,
                        complete);
                if (manageValue == 1) {
                    continue;
                }
                if (manageValue == -1) {
                    break;
                }
                complete = (i == x) && (j == y);
                Logic.manageLoopByPoints(i, j, x, y, curCell,
                        openPoints, closePoints);
            }
        }

        String key = Integer.toString(x) + "|" + Integer.toString(y);
        curCell = openPoints.get(key);
        return curCell;
    }

    private static Cell findPointByFmin(Map<String, Cell> list) {
        Cell current = null;
        double fMin = INFINITY;
        for (Map.Entry<String, Cell> entry : list.entrySet()) {
            if (entry.getValue().getF() < fMin) {
                fMin = entry.getValue().getF();
                current = entry.getValue();
            }
        }
        return current;
    }

    private static int manageLoopByMap(int i, int j, Cell curCell,
            boolean complete) {
        if (!GameField.isWalkable(i, j)) {
            return 1;
        }
        if (!((curCell.getX() == i) || (curCell.getY() == j))) {
            if (!(GameField.isWalkable(curCell.getX(), j))
                    || (!(GameField.isWalkable(i, curCell.getY())))) {
                return 1;
            }
        }
        if ((curCell.getX() == i) && (curCell.getY() == j)) {
            return 1;
        } else if (!complete) {
            return 0;
        } else {
            return -1;
        }
    }

    private static int manageAbsLoopByMap(int i, int j, Cell curCell,
            boolean complete) {
        if (!GameField.isAbsWalkable(i, j)) {
            return 1;
        }
        if (!((curCell.getX() == i) || (curCell.getY() == j))) {
            if (!(GameField.isAbsWalkable(curCell.getX(), j))
                    || (!(GameField.isAbsWalkable(i, curCell.getY())))) {
                return 1;
            }
        }
        if ((curCell.getX() == i) && (curCell.getY() == j)) {
            return 1;
        } else if (!complete) {
            return 0;
        } else {
            return -1;
        }
    }

    private static void manageLoopByPoints(int i, int j, int x, int y,
            Cell curCell, Map<String, Cell> openList,
            Map<String, Cell> closeList) {
        String key = Integer.toString(i) + "|" + Integer.toString(j);
        double step;
        if (closeList.containsKey(key)) {
            return;
        }
        if (openList.containsKey(key)) {
            Cell tmp = openList.get(key);
            step = ((curCell.getX() == tmp.getX())
                    || (curCell.getY() == tmp.getY())) ? 1 : 1.41;
            if (tmp.getG() > curCell.getG() + step) {
                tmp.setCell(curCell);
                tmp.setG(curCell.getG() + step);
                tmp.calcF();
            }
        } else {
            step = ((curCell.getX() == i) || (curCell.getY() == j)) ? 1 : 1.41;
            Cell newPoint = new Cell(i, j);
            newPoint.setCell(curCell);
            newPoint.setG(curCell.getG() + step);
            newPoint.calcH(x, y);
            newPoint.calcF();
            openList.put(key, newPoint);
        }
    }

    private static List<Cell> getBorder(Cell cell) {
        int minI = (cell.getX() - 1) < 0 ? 0 : cell.getX() - 1;
        int minJ = (cell.getY() - 1) < 0 ? 0 : cell.getY() - 1;
        int maxI = (cell.getX() + 2) > GameField.getMapSize() ? GameField.getMapSize()
                : cell.getX() + 2;
        int maxJ = (cell.getY() + 2) > GameField.getMapSize() ? GameField.getMapSize()
                : cell.getY() + 2;
        List<Cell> cells = new ArrayList<>();
        for (int i = minI; i < maxI; i++) {
            cells.add(new Cell(i, minJ));

        }
        for (int j = minJ; j < maxJ; j++) {
            cells.add(new Cell(maxI - 1, j));
        }
        for (int i = maxI - 1; i >= minI; i--) {
            cells.add(new Cell(i, maxJ - 1));

        }
        for (int j = maxJ - 1; j >= minJ; j--) {
            cells.add(new Cell(minI, j));
        }
        return cells;
    }
}
