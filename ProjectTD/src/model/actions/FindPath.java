/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.actions;

import model.actions.findpathalgorithms.Cell;
import model.actions.findpathalgorithms.FieldMap;
import model.components.Unit;

/**
 *
 * @author Хозяин
 */
public class FindPath implements Action {

    private Unit unit;
    private Cell path;
    
    private int x;
    private int y;

    public FindPath() {
        this.path = null;
    }

    @Override
    public void act() {
        int ex = FieldMap.toInteger(unit.getX());
        int ey = FieldMap.toInteger(unit.getY());
        this.path = FieldMap.searchPath(x, y, ex, ey);
    }

    @Override
    public void setUnit(Unit unit) {
        this.unit = unit;
    }
    
    public void setXY(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public Cell getPath(){
        return path;
    }

}
