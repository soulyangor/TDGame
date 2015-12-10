/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.logics;

/**
 *
 * @author Sokolov@ivc.org
 */
public class Place {

    private final int xInc;
    private final int yInc;

    public Place(int xIncrement, int yIncrement) {
        this.xInc = xIncrement;
        this.yInc = yIncrement;
    }

    public int getXInc() {
        return xInc;
    }

    public int getYInc() {
        return yInc;
    }

    public Cell getCell(int x, int y) {
        return new Cell(x + xInc, y + yInc);
    }

}
