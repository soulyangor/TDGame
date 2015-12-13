/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.logics;

/**
 * Абстрактный класс карт
 *
 * @author Sokolov@ivc.org
 * @param <E>
 */
public abstract class Grid<E> {

    public abstract double toRealCoordinate(int arg);

    public abstract int toCellCoordinate(double arg);

    public abstract int getMapSize();

    public abstract boolean isWalkable(double x, double y);

    public abstract boolean isWalkable(int i, int j);

    public abstract boolean isWalkable(Cell cell);
    
    public abstract int getCellSize();
    
    public abstract void setCellSize(int cellSize);
    
    public abstract void setCellValue(E value);
    
    public abstract E getCellValue(double x, double y);
    
    public abstract E getCellValue(int x, int y);
    
    public abstract E getCellValue(Cell cell);
    
    public abstract void setWalkablePlace(double x, double y);
    
    public abstract void setWalkablePlace(int i, int j);

    public abstract void setWalkablePlace(Cell cell);
 
}
