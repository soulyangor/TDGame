package model.logics;

import java.util.LinkedHashMap;

/**
 * Класс для хранения точек пути и самого пути Created by Вячеслав on
 * 11.09.2015.
 */
public class Cell {

    private final int x;
    private final int y;
    private final String key;
    private Cell cell;
    private double g;
    private double h;
    private double f;

    /**
     * Конструктор. Создаёт точку с координатами (x, y), нулевым указателем на
     * связанную клетку и нулевыми g, h, f
     *
     * @param x int
     * @param y int
     */
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.cell = null;
        this.g = 0;
        this.h = 0;
        this.f = 0;
        this.key = Integer.toString(this.x) + "|" + Integer.toString(this.y);
    }

    /**
     * Возвращает координату x
     *
     * @return int
     */
    public int getX() {
        return x;
    }

    /**
     * Возвращает координату y
     *
     * @return int
     */
    public int getY() {
        return y;
    }

    /**
     * Возвращает связанную ячейку
     *
     * @return Cell
     */
    public Cell getCell() {
        return cell;
    }

    /**
     * Устанавливает связанную ячейку
     *
     * @param cell Cell
     */
    public void setCell(Cell cell) {
        this.cell = cell;
    }

    /**
     * Возвращает фактическую стоимость клетки(параметр g), что эквивалентно
     * расстоянию до одного из концов пути в клеточных единицах
     *
     * @return double
     */
    public double getG() {
        return g;
    }

    /**
     * Устанавливает фактическую стоимость клетки(параметр g)
     *
     * @param g double
     */
    public void setG(double g) {
        this.g = g;
    }

    /**
     * Возвращает параметр F
     *
     * @return double
     */
    public double getF() {
        return f;
    }

    /**
     * Расчитывает параметр f исходя из параметров h и g
     */
    public void calcF() {
        this.f = this.h + this.g;
    }

    /**
     * Расчитывает метрику до точки (x, y), сохраняет в параметр h
     *
     * @param x int
     * @param y int
     */
    public void calcH(int x, int y) {
        this.h = Math.sqrt((x - this.x) * (x - this.x) + (y - this.y) * (y - this.y));
    }

    /**
     * Возвращает ключ в виде строки, есл ключ не создан, то создаёт его
     *
     * @return String
     */
    public final String getKey() {
        return key;
    }

    /**
     * Преобразет путь в виде связанных точек в таблицу ключ-значение
     *
     * @return LinkedHashMap
     */
    public LinkedHashMap<String, Cell> formPath() {
        LinkedHashMap<String, Cell> path = new LinkedHashMap<>();
        Cell current = this;
        while (current.getCell() != null) {
            path.put(current.getKey(), current);
            current = current.getCell();
        }
        return path;
    }

    /**
     * Сравнивает точку с передаваемым параметром, точки считаются равными, если
     * равны их координаты x и y
     *
     * @param cell Cell
     * @return boolean
     */
    public boolean equals(Cell cell) {
        return (this.x == cell.getX()) && (this.y == cell.getY());
    }

}
