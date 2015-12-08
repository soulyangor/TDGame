/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.logics;

import model.components.Status;
import model.components.Unit;

/**
 * Класс для хранения карты проходимости и преобразования координат
 *
 * @author Хозяин
 */
public class GameField{

    /*-----------------------------------Static fields---------------------------------------*/
    private static Unit[][] map;
    private static int mapSize;
    private static int cellSize;

    /*---------------------------------Default constructor-----------------------------------*/
    private GameField() {
    }

    /**
     * Преобразут матричную(целочисленную) координату к
     * реально-пространственной(с плавующей точкой), учитывая размер ячейки
     * карты проходимости
     *
     * @param arg int
     * @return double
     */
    public static double toRealCoordinate(int arg) {
        return GameField.cellSize * (arg + 0.5);
    }

    /**
     * Преобразут реально-пространственную координату(с плавующей точкой) к
     * матричной(целочисленной), учитывая размер ячейки карты проходимости
     *
     * @param arg double
     * @return int
     */
    public static int toCellCoordinate(double arg) {
        return (int) (arg / GameField.cellSize);
    }

    /**
     * Задаёт карту проходимости и размер карты
     *
     * @param map Unit[][]
     * @param mapSize int
     */
    public static void setMap(Unit[][] map, int mapSize) {
        GameField.map = map;
        GameField.mapSize = mapSize;
    }

    /**
     * Возвращает размер карты
     *
     * @return int
     */
    public static int getMapSize() {
        return mapSize;
    }

    /**
     * Генерирует пустую карту размером mapSize x mapSize
     *
     * @param mapSize int
     */
    public static void generate(int mapSize) {
        GameField.mapSize = mapSize;
        GameField.map = new Unit[mapSize][mapSize];
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                GameField.map[i][j] = null;
            }
        }
    }

    /**
     * Устанавливает точку карты проходимой по реальным координатам
     *
     * @param x double
     * @param y double
     */
    public static void setWalkablePlace(double x, double y) {
        int i = toCellCoordinate(x);
        int j = toCellCoordinate(y);
        GameField.map[i][j] = null;
    }

    /**
     * Устанавливает точку карты проходимой по матричным координатам
     *
     * @param i int
     * @param j int
     */
    public static void setWalkablePlace(int i, int j) {
        GameField.map[i][j] = null;
    }

    /**
     * Устанавливает точку карты проходимой по точке(ячейке)
     *
     * @param cell Cell
     */
    public static void setWalkablePlace(Cell cell) {
        int i = cell.getX();
        int j = cell.getY();
        GameField.map[i][j] = null;
    }

    /**
     * Устанавливает юнита на карту по его реальным координатам
     *
     * @param unit Unit
     */
    public static void setUnit(Unit unit) {
        int i = toCellCoordinate(unit.getX());
        int j = toCellCoordinate(unit.getY());
        GameField.map[i][j] = unit;
    }

    /**
     * Возвращает юнита занимающего ячейку cell
     *
     * @param cell Cell
     * @return Unit
     */
    public static Unit getUnit(Cell cell) {
        int i = cell.getX();
        int j = cell.getY();
        return GameField.map[i][j];
    }

    /**
     * Возвращает юнита занимающего ячейку с пространственными координатами (x,
     * y)
     *
     * @param x double
     * @param y double
     * @return Unit
     */
    public static Unit getUnit(double x, double y) {
        int i = toCellCoordinate(x);
        int j = toCellCoordinate(y);
        return GameField.map[i][j];
    }

    /**
     * Возвращает юнита занимающего ячейку с матричными координатами (x, y)
     *
     * @param i int
     * @param j int
     * @return Unit
     */
    public static Unit getUnit(int i, int j) {
        return GameField.map[i][j];
    }

    /**
     * Возвращает размер ячейки карты
     *
     * @return int
     */
    public static int getCellSize() {
        return cellSize;
    }

    /**
     * Устанавливает размер ячейки карты
     *
     * @param cellSize int
     */
    public static void setCellSize(int cellSize) {
        GameField.cellSize = cellSize;
    }

    /**
     * Проверяет, проходима ли точка с координатами (x, y)
     *
     * @param x double
     * @param y double
     * @return boolean
     */
    public static boolean isWalkable(double x, double y) {
        int i = toCellCoordinate(x);
        int j = toCellCoordinate(y);
        return (!((GameField.map[i][j] != null)
                && (GameField.map[i][j].getStatus() == Status.STAND)));
    }

    /**
     * Проверяет, проходима ли ячейка с координатами (i, j)
     *
     * @param i int
     * @param j int
     * @return boolean
     */
    public static boolean isWalkable(int i, int j) {
        return (!((GameField.map[i][j] != null)
                && (GameField.map[i][j].getStatus() == Status.STAND)));
    }

    /**
     * Проверяет проходима ли ячейка cell
     *
     * @param cell Cell
     * @return boolean
     */
    public static boolean isWalkable(Cell cell) {
        int i = cell.getX();
        int j = cell.getY();
        return (!((GameField.map[i][j] != null)
                && (GameField.map[i][j].getStatus() == Status.STAND)));
    }

    public static boolean isAbsWalkable(double x, double y) {
        int i = toCellCoordinate(x);
        int j = toCellCoordinate(y);
        return (!((GameField.map[i][j] != null)
                && (GameField.map[i][j].getStatus() != Status.MOVE)));
    }

    public static boolean isAbsWalkable(int i, int j) {
        return (!((GameField.map[i][j] != null)
                && (GameField.map[i][j].getStatus() != Status.MOVE)));
    }

    public static boolean isAbsWalkable(Cell cell) {
        int i = cell.getX();
        int j = cell.getY();
        return (!((GameField.map[i][j] != null)
                && (GameField.map[i][j].getStatus() != Status.MOVE)));
    }

}
