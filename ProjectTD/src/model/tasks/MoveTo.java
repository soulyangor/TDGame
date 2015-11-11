/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.tasks;

import model.actions.Move;
import model.actions.findpathalgorithms.Cell;
import model.actions.findpathalgorithms.FieldMap;
import model.components.Unit;
import model.components.Component;

/**
 *
 * @author Хозяин
 */
public class MoveTo implements Task {

    private final double x;
    private final double y;
    private final Move move;
    private final Cell cell; 

    private boolean complete = false;

    private static final double DELTA = 2;
    
    public MoveTo(Cell cell) {
        this.x = FieldMap.toDouble(cell.getX());
        this.y = FieldMap.toDouble(cell.getY());
        move = new Move();
        move.setXY(x, y);
        this.cell = cell;
    }

    @Override
    public void execute(Component component) {
        if (component instanceof Unit) {
            Unit unit = (Unit) component;
            move.setUnit(unit);
            move.act();
            double r = Math.sqrt(Math.pow(x - unit.getX(), 2.0)
                    + Math.pow(y - unit.getY(), 2.0));
            complete = (r < DELTA);
        } else {
            throw new IllegalArgumentException("Неверный компонент");
        }
    }

    @Override
    public boolean isComplete() {
        return complete;
    }

    @Override
    public void notComplete() {
        this.complete = false;
    }   

}
