package model.actions.findpathalgorithms;

import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Map;

/**
 * Created by Вячеслав on 17.09.2015.
 */
public class FieldMap {
    /*---------------------------------Static constants--------------------------------------*/

    private static final int VALUE_WALKABLE_CELL = 0;
    private static final double INFINITY = 10e6;

    /*-----------------------------------Static fields---------------------------------------*/
    private static int[][] map;
    private static int mapSize;
    private static int cellSize;

    /*---------------------------------Default constructor-----------------------------------*/
    private FieldMap() {
    }

    /*--------------------------------------Static methods-----------------------------------*/
    public static double toDouble(int arg) {
        return FieldMap.cellSize * (arg + 0.5);
    }

    public static int toInteger(double arg) {
        return (int) (arg / FieldMap.cellSize);
    }

    public static void setMap(int[][] map, int mapSize) {
        FieldMap.map = map;
        FieldMap.mapSize = mapSize;
    }

    public static void setWalkablePlace(double x, double y) {
        int i = toInteger(x);
        int j = toInteger(y);
        FieldMap.map[i][j] = FieldMap.VALUE_WALKABLE_CELL;
    }

    public static void setUnwalkablePlace(double x, double y) {
        int i = toInteger(x);
        int j = toInteger(y);
        FieldMap.map[i][j] = FieldMap.VALUE_WALKABLE_CELL - 10;
    }

    public static void setWalkablePlace(int i, int j) {
        FieldMap.map[i][j] = FieldMap.VALUE_WALKABLE_CELL;
    }

    public static void setUnwalkablePlace(int i, int j) {
        FieldMap.map[i][j] = FieldMap.VALUE_WALKABLE_CELL - 10;
    }

    public static void setWalkablePlace(Cell cell) {
        int i = cell.getX();
        int j = cell.getY();
        FieldMap.map[i][j] = FieldMap.VALUE_WALKABLE_CELL;
    }

    public static void setUnwalkablePlace(Cell cell) {
        int i = cell.getX();
        int j = cell.getY();
        FieldMap.map[i][j] = FieldMap.VALUE_WALKABLE_CELL - 10;
    }

    public static int getCellSize() {
        return cellSize;
    }

    public static void setCellSize(int cellSize) {
        FieldMap.cellSize = cellSize;
    }

    public static boolean isWalkable(double x, double y) {
        int i = toInteger(x);
        int j = toInteger(y);
        return FieldMap.map[i][j] == VALUE_WALKABLE_CELL;
    }

    public static boolean isWalkable(int i, int j) {
        return FieldMap.map[i][j] == VALUE_WALKABLE_CELL;
    }

    public static boolean isWalkable(Cell cell) {
        int i = cell.getX();
        int j = cell.getY();
        return FieldMap.map[i][j] == VALUE_WALKABLE_CELL;
    }

    public static Cell searchPath(int x0, int y0, int x, int y) {
        Map<String, Cell> openPoints = new LinkedHashMap<>();
        Map<String, Cell> closePoints = new LinkedHashMap<>();
        Cell newCell = new Cell(x0, y0);
        Cell curCell;
        newCell.calcH(x, y);
        newCell.calcF();
        openPoints.put(newCell.createKey(), newCell);
        boolean complete = false;
        while ((openPoints.size() > 0) && (!complete)) {
            curCell = FieldMap.findPointByFmin(openPoints);
            String key = curCell.createKey();
            closePoints.put(key, curCell);
            openPoints.remove(key);
            MinMax minMax = new MinMax(curCell);
            for (int i = minMax.getMinI(); i < minMax.getMaxI(); i++) {
                for (int j = minMax.getMinJ(); j < minMax.getMaxJ(); j++) {
                    int manageValue = FieldMap.manageLoopByMap(i, j, curCell,
                            complete);
                    if (manageValue == 1) {
                        continue;
                    }
                    if (manageValue == -1) {
                        break;
                    }
                    complete = (i == x) && (j == y);
                    FieldMap.manageLoopByPoints(i, j, x, y, curCell,
                            openPoints, closePoints);
                }
            }
        }
        String key = Integer.toString(x) + "|" + Integer.toString(y);
        curCell = openPoints.get(key);
        return curCell;
    }

    private static Cell findPointByFmin(Map<String, Cell> list) {
        Cell current = null;
        double fMin = INFINITY;
        for (Entry<String, Cell> entry : list.entrySet()) {
            if (entry.getValue().getF() < fMin) {
                fMin = entry.getValue().getF();
                current = entry.getValue();
            }
        }
        return current;
    }

    private static int manageLoopByMap(int i, int j, Cell curCell,
            boolean complete) {
        if (FieldMap.map[i][j] != VALUE_WALKABLE_CELL) {
            return 1;
        }
        if (!((curCell.getX() == i) || (curCell.getY() == j))) {
            if ((FieldMap.map[curCell.getX()][j] != VALUE_WALKABLE_CELL)
                    || (FieldMap.map[i][curCell.getY()] != VALUE_WALKABLE_CELL)) {
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
                tmp.setParent(curCell);
                tmp.setG(curCell.getG() + step);
                tmp.calcF();
            }
        } else {
            step = ((curCell.getX() == i) || (curCell.getY() == j)) ? 1 : 1.41;
            Cell newPoint = new Cell(i, j);
            newPoint.setParent(curCell);
            newPoint.setG(curCell.getG() + step);
            newPoint.calcH(x, y);
            newPoint.calcF();
            openList.put(key, newPoint);
        }
    }

    private static class MinMax {

        private final int minI;
        private final int minJ;
        private final int maxI;
        private final int maxJ;

        public int getMinI() {
            return minI;
        }

        public int getMinJ() {
            return minJ;
        }

        public int getMaxI() {
            return maxI;
        }

        public int getMaxJ() {
            return maxJ;
        }

        public MinMax(Cell cell) {
            this.minI = (cell.getX() - 1) < 0 ? 0 : cell.getX() - 1;
            this.minJ = (cell.getY() - 1) < 0 ? 0 : cell.getY() - 1;
            this.maxI = (cell.getX() + 2) > FieldMap.mapSize ? FieldMap.mapSize
                    : cell.getX() + 2;
            this.maxJ = (cell.getY() + 2) > FieldMap.mapSize ? FieldMap.mapSize
                    : cell.getY() + 2;
        }

    }

}
