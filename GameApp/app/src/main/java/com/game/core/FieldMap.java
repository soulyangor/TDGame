package com.game.core;

import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.List;
import java.util.Map;

/**
 * Created by Вячеслав on 17.09.2015.
 */
public class FieldMap {
    /*-------------------------------------Enum----------------------------------------------*/
    private enum ManageValue { BREAK, CONTINUE, NOTHING }

    /*-----------------------------------Private class---------------------------------------*/
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

        public MinMax(Point point) {
            if ((point.getX() - 1) < 0) {
                this.minI = 0;
            } else {
                this.minI = point.getX() - 1;
            }
            if ((point.getY() - 1) < 0) {
                this.minJ = 0;
            } else {
                this.minJ = point.getY() - 1;
            }
            if ((point.getX() + 2) > FieldMap.mapSize) {
                this.maxI = FieldMap.mapSize;
            } else {
                this.maxI = point.getX() + 2;
            }
            if ((point.getY() + 2) > FieldMap.mapSize) {
                this.maxJ = FieldMap.mapSize;
            } else {
                this.maxJ = point.getY() + 2;
            }
        }

    }

    /*---------------------------------Private constants--------------------------------------*/

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

    private static Point findPointByMinF(Map<String, Point> list) {
        Point current = null;
        double fMin = INFINITY;
        for (Entry<String, Point> entry : list.entrySet())
            if (entry.getValue().getF() < fMin) {
                fMin = entry.getValue().getF();
                current = entry.getValue();
            }
        return current;
    }

    private static ManageValue manageLoopByMap(int i, int j, Point curPoint, boolean complete) {
        if (FieldMap.map[i][j] != VALUE_WALKABLE_CELL) {
            return ManageValue.CONTINUE;
        }
        if (!((curPoint.getX() == i) || (curPoint.getY() == j))) {
            if ((FieldMap.map[curPoint.getX()][j] != VALUE_WALKABLE_CELL)
                    || (FieldMap.map[i][curPoint.getY()] != VALUE_WALKABLE_CELL)) {
                return ManageValue.CONTINUE;
            }
        }
        if ((curPoint.getX() == i) && (curPoint.getY() == j)) {
            return ManageValue.CONTINUE;
        } else if (!complete) {
            return ManageValue.NOTHING;
        } else {
            return ManageValue.BREAK;
        }
    }

    private static void manageLoopByPoints(int i, int j, int x, int y,
                                          Point curPoint, Map<String, Point> openList,
                                          Map<String, Point> closeList) {
        String key = Integer.toString(i) + "|" + Integer.toString(j);
        double step;
        if (closeList.containsKey(key)) {
            return;
        }
        if (openList.containsKey(key)) {
            Point tmp = openList.get(key);
            step = ((curPoint.getX() == tmp.getX())
                    || (curPoint.getY() == tmp.getY())) ? 1 : 1.41;
            if (tmp.getG() > curPoint.getG() + step) {
                tmp.setParent(curPoint);
                tmp.setG(curPoint.getG() + step);
                tmp.calcF();
            }
        } else {
            step = ((curPoint.getX() == i) || (curPoint.getY() == j)) ? 1 : 1.41;
            Point newPoint = new Point(i, j);
            newPoint.setParent(curPoint);
            newPoint.setG(curPoint.getG() + step);
            newPoint.calcH(x, y);
            newPoint.calcF();
            openList.put(key, newPoint);
        }
    }

    public static double toDouble(int arg) {
        return FieldMap.cellSize * (arg + 0.5);
    }

    public static int toInteger(double arg) {
        return (int) (arg / FieldMap.cellSize);
    }

    public static void setProperties(int mapSize, int cellSize) {
        FieldMap.mapSize = mapSize;
        FieldMap.cellSize = cellSize;
        FieldMap.map = new int[mapSize][mapSize];
        for (int i = 0; i < FieldMap.mapSize; i++) {
            for (int j = 0; j < FieldMap.mapSize; j++) {
                FieldMap.map[i][j] = 0;
            }
        }
    }

    public static void setCellsValue(List<Point> cells, int value) {
        for (Point cell : cells) {
            FieldMap.map[cell.getX()][cell.getY()] = value;
        }
    }

    public static boolean isWalkable(double x, double y) {
        int i = toInteger(x);
        int j = toInteger(y);
        return FieldMap.map[i][j] == VALUE_WALKABLE_CELL;
    }

    public static Point searchPath(int x0, int y0, int x, int y) {
        Map<String, Point> openPoints = new LinkedHashMap<>();
        Map<String, Point> closePoints = new LinkedHashMap<>();
        Point newPoint = new Point(x0, y0);
        Point curPoint;
        newPoint.calcH(x, y);
        newPoint.calcF();
        openPoints.put(newPoint.createKey(), newPoint);
        boolean complete = false;
        while ((openPoints.size() > 0) && (!complete)) {
            curPoint = FieldMap.findPointByMinF(openPoints);
            String key = curPoint.createKey();
            closePoints.put(key, curPoint);
            openPoints.remove(key);
            MinMax minMax = new MinMax(curPoint);
            for (int i = minMax.getMinI(); i < minMax.getMaxI(); i++) {
                for (int j = minMax.getMinJ(); j < minMax.getMaxJ(); j++) {
                    ManageValue manageValue = FieldMap.manageLoopByMap(i, j, curPoint, complete);
                    if (manageValue == ManageValue.CONTINUE) {
                        continue;
                    }
                    if (manageValue == ManageValue.BREAK) {
                        break;
                    }
                    complete = (i == x) && (j == y);
                    FieldMap.manageLoopByPoints(i, j, x, y, curPoint,
                            openPoints, closePoints);
                }
            }
        }
        String key = Integer.toString(x) + "|" + Integer.toString(y);
        curPoint = openPoints.get(key);
        return curPoint;
    }

}