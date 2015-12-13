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
 *
 * @author Sokolov@ivc.org
 */
public class AreaLogic {
    /*---------------------------------Static constants--------------------------------------*/

    private static final double INFINITY = 10e6;

    private static Area area = null;
    private static Grid grid = null;

    private AreaLogic() {
    }

    public static void setArea(Area area) {
        AreaLogic.area = area;
    }

    public static void setGrid(Grid grid) {
        AreaLogic.grid = grid;
    }

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
        int fi = -1;
        int fj = -1;
        /* for (Place pl : area.getPlaces()) {
         if ((pl.getCell(x, y).getX() == x0) && (pl.getCell(x, y).getY() == y0)) {
         return null;
         }
         }*/
        while ((openPoints.size() > 0) && (!complete)) {
            curCell = findPointByFmin(openPoints);
            String key = curCell.getKey();
            closePoints.put(key, curCell);
            openPoints.remove(key);
            for (Cell iter : getBorder(curCell)) {
                int i = iter.getX();
                int j = iter.getY();
                int manageValue = manageLoopByMap(i, j, curCell,
                        complete);
                if (manageValue == 1) {
                    continue;
                }
                if (manageValue == -1) {
                    break;
                }
                /* if (isComplete(i, j, x, y)) {
                 complete = true;
                 fi = i;
                 fj = j;
                 }*/
                complete = (i == x) && (j == y);
                manageLoopByPoints(i, j, x, y, curCell,
                        openPoints, closePoints);
            }
        }

        String key = Integer.toString(x) + "|" + Integer.toString(y);
        //String key = Integer.toString(fi) + "|" + Integer.toString(fj);
        curCell = openPoints.get(key);
        openPoints.clear();
        closePoints.clear();
        return curCell;
    }

    private static boolean isComplete(int x0, int y0, int x, int y) {
        for (Place pl : area.getPlaces()) {
            int i = pl.getCell(x0, y0).getX();
            int j = pl.getCell(x0, y0).getY();
            if ((i == x) && (j == y)) {
                return true;
            }
        }
        return false;
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

    private static boolean isIntoGrid(Cell cell) {
        int i = cell.getX();
        int j = cell.getY();
        return (i < grid.getMapSize()) && (i >= 0) && (j < grid.getMapSize()) && (j >= 0);
    }

    private static boolean isAreaWalkable(int x, int y) {
        if ((area == null) || (grid == null)) {
            throw new IllegalStateException("Не задана карта или область");
        }
        for (Place pl : area.getPlaces()) {
            if ((!isIntoGrid(pl.getCell(x, y)))
                    || (!grid.isWalkable(pl.getCell(x, y)))) {
                return false;
            }
        }
        return true;
    }

    private static boolean canWalk(int i, int j, Cell cell) {
        int x = cell.getX();
        int y = cell.getY();

        if (Math.abs(x - i) + Math.abs(y - j) > 1) {
            return isAreaWalkable(i, j)
                    && isAreaWalkable(x, j)
                    && isAreaWalkable(i, y);
        } else {
            return isAreaWalkable(i, j);
        }
    }

    private static int manageLoopByMap(int i, int j, Cell cell,
            boolean complete) {
        if (!canWalk(i, j, cell)) {
            return 1;
        }
        if ((cell.getX() == i) && (cell.getY() == j)) {
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
        Cell cell = null;
        if (isAreaWalkable(curCell.getX(), curCell.getY())) {
            cell = curCell;
        }
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
                tmp.setCell(cell);
                tmp.setG(curCell.getG() + step);
                tmp.calcF();
            }
        } else {
            step = ((curCell.getX() == i) || (curCell.getY() == j)) ? 1 : 1.41;
            Cell newPoint = new Cell(i, j);
            newPoint.setCell(cell);
            newPoint.setG(curCell.getG() + step);
            newPoint.calcH(x, y);
            newPoint.calcF();
            openList.put(key, newPoint);
        }
    }

    private static List<Cell> getBorder(Cell cell) {
        int minI = (cell.getX() - 1) < 0 ? 0 : cell.getX() - 1;
        int minJ = (cell.getY() - 1) < 0 ? 0 : cell.getY() - 1;
        int maxI = (cell.getX() + 2) > grid.getMapSize() ? grid.getMapSize()
                : cell.getX() + 2;
        int maxJ = (cell.getY() + 2) > grid.getMapSize() ? grid.getMapSize()
                : cell.getY() + 2;
        List<Cell> cells = new ArrayList<>();
        for (int i = minI; i < maxI; i++) {
            cells.add(new Cell(i, minJ));
        }
        for (int j = minJ + 1; j < maxJ; j++) {
            cells.add(new Cell(maxI - 1, j));
        }
        for (int i = maxI - 2; i >= minI; i--) {
            cells.add(new Cell(i, maxJ - 1));
        }
        for (int j = maxJ - 2; j > minJ; j--) {
            cells.add(new Cell(minI, j));
        }
        return cells;
    }

}
