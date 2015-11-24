/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.actions.findpathalgorithms;

import model.components.Unit;

/**
 *
 * @author Хозяин
 */
public class Conflict {
    private final Cell cell;
    private Unit unit;
    private final String key;
    
    public Conflict(Cell cell){
        this.cell = cell;
        this.key = cell.createKey();
        this.unit = null;
    }

    public Cell getCell() {
        return cell;
    }

    public String getKey() {
        return key;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }
  
}
