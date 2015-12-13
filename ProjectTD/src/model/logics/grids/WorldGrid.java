/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.logics.grids;

import model.logics.Grid;
import model.components.Status;
import model.components.Unit;
import model.logics.Area;
import model.logics.Cell;
import model.logics.Place;
import model.units.Insertable;

/**
 * Класс карты. Синглтон. Предназначен для хранения карты проходимости и
 * преобразования координат
 *
 * @author Вячеслав
 */
public class WorldGrid extends Grid<Unit> {
    
    private final static WorldGrid INSTANCE = new WorldGrid();
    private Unit[][] map;
    private int mapSize;
    private int cellSize;

    /*---------------------------------Default constructor-----------------------------------*/
    private WorldGrid() {
    }
    
    public static WorldGrid getInstance() {
        return INSTANCE;
    }

    /**
     * Преобразут матричную(целочисленную) координату к
     * реально-пространственной(с плавующей точкой), учитывая размер ячейки
     * карты проходимости
     *
     * @param arg int
     * @return double
     */
    @Override
    public double toRealCoordinate(int arg) {
        return cellSize * (arg + 0.5);
    }

    /**
     * Преобразут реально-пространственную координату(с плавующей точкой) к
     * матричной(целочисленной), учитывая размер ячейки карты проходимости
     *
     * @param arg double
     * @return int
     */
    @Override
    public int toCellCoordinate(double arg) {
        return (int) (arg / cellSize);
    }

    /**
     * Задаёт карту проходимости и размер карты
     *
     * @param map Unit[][]
     * @param mapSize int
     */
    public void setMap(Unit[][] map, int mapSize) {
        this.map = map;
        this.mapSize = mapSize;
    }

    /**
     * Возвращает размер карты
     *
     * @return int
     */
    @Override
    public int getMapSize() {
        return mapSize;
    }

    /**
     * Генерирует пустую карту размером mapSize x mapSize
     *
     * @param mapSize int
     */
    public void generate(int mapSize) {
        this.mapSize = mapSize;
        this.map = new Unit[mapSize][mapSize];
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                this.map[i][j] = null;
            }
        }
    }

    /**
     * Устанавливает точку карты проходимой по реальным координатам
     *
     * @param x double
     * @param y double
     */
    @Override
    public void setWalkablePlace(double x, double y) {
        int i = toCellCoordinate(x);
        int j = toCellCoordinate(y);
        setWalkablePlace(i, j);
    }

    /**
     * Устанавливает точку карты проходимой по матричным координатам
     *
     * @param i int
     * @param j int
     */
    @Override
    public void setWalkablePlace(int i, int j) {
        if (map[i][j] instanceof Insertable) {
            Area area = ((Insertable) map[i][j]).getArea();
            for (Place pl : area.getPlaces()) {
                int ii = pl.getCell(i, j).getX();
                int jj = pl.getCell(i, j).getY();
                map[ii][jj] = null;
            }
        } else {
            map[i][j] = null;
        }
    }

    /**
     * Устанавливает точку карты проходимой по точке(ячейке)
     *
     * @param cell Cell
     */
    @Override
    public void setWalkablePlace(Cell cell) {
        int i = cell.getX();
        int j = cell.getY();
        setWalkablePlace(i, j);
    }

    /**
     * Устанавливает юнита на карту по его реальным координатам
     *
     * @param unit Unit
     */
    @Override
    public void setCellValue(Unit unit) {
        int i = toCellCoordinate(unit.getX());
        int j = toCellCoordinate(unit.getY());
        if (unit instanceof Insertable) {
            Area area = ((Insertable) unit).getArea();
            for (Place pl : area.getPlaces()) {
                int ii = pl.getCell(i, j).getX();
                int jj = pl.getCell(i, j).getY();
                map[ii][jj] = unit;
            }
        } else {
            map[i][j] = unit;
        }
    }

    /**
     * Возвращает юнита занимающего ячейку cell
     *
     * @param cell Cell
     * @return Unit
     */
    @Override
    public Unit getCellValue(Cell cell) {
        int i = cell.getX();
        int j = cell.getY();
        return map[i][j];
    }

    /**
     * Возвращает юнита занимающего ячейку с пространственными координатами (x,
     * y)
     *
     * @param x double
     * @param y double
     * @return Unit
     */
    @Override
    public Unit getCellValue(double x, double y) {
        int i = toCellCoordinate(x);
        int j = toCellCoordinate(y);
        return map[i][j];
    }

    /**
     * Возвращает юнита занимающего ячейку с матричными координатами (x, y)
     *
     * @param i int
     * @param j int
     * @return Unit
     */
    @Override
    public Unit getCellValue(int i, int j) {
        return map[i][j];
    }

    /**
     * Возвращает размер ячейки карты
     *
     * @return int
     */
    @Override
    public int getCellSize() {
        return cellSize;
    }

    /**
     * Устанавливает размер ячейки карты
     *
     * @param cellSize int
     */
    @Override
    public void setCellSize(int cellSize) {
        this.cellSize = cellSize;
    }

    /**
     * Проверяет, проходима ли точка с координатами (x, y)
     *
     * @param x double
     * @param y double
     * @return boolean
     */
    @Override
    public boolean isWalkable(double x, double y) {
        int i = toCellCoordinate(x);
        int j = toCellCoordinate(y);
        return (!((map[i][j] != null)
                && (map[i][j].getStatus() == Status.STAND)));
    }

    /**
     * Проверяет, проходима ли ячейка с координатами (i, j)
     *
     * @param i int
     * @param j int
     * @return boolean
     */
    @Override
    public boolean isWalkable(int i, int j) {
        return (!((map[i][j] != null)
                && (map[i][j].getStatus() == Status.STAND)));
    }

    /**
     * Проверяет проходима ли ячейка cell
     *
     * @param cell Cell
     * @return boolean
     */
    @Override
    public boolean isWalkable(Cell cell) {
        int i = cell.getX();
        int j = cell.getY();
        return (!((map[i][j] != null)
                && (map[i][j].getStatus() == Status.STAND)));
    }
    
}
