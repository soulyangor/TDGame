/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.logics;

import model.logics.Cell;

/**
 * Абстрактный класс карты
 *
 * @author Sokolov@ivc.org
 */
public abstract class Grid {

    public abstract double toRealCoordinate(int arg);

    public abstract int toCellCoordinate(double arg);

    public abstract int getMapSize();

    public abstract boolean isWalkable(double x, double y);

    public abstract boolean isWalkable(int i, int j);

    public abstract boolean isWalkable(Cell cell);

}
