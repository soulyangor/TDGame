/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.tasks;

import model.actions.findpathalgorithms.Cell;
import model.actions.findpathalgorithms.ConflictResolver;
import model.actions.findpathalgorithms.FieldMap;
import model.components.Unit;
import model.components.TDComponent;

/**
 *
 * @author Хозяин
 */
public class MoveTo implements Task {

    private final double x;
    private final double y;
    private final Cell cell;
    private Cell start = null;

    private boolean complete = false;

    private static final double DELTA = 2;

    public MoveTo(Cell cell) {
        this.x = FieldMap.toDouble(cell.getX());
        this.y = FieldMap.toDouble(cell.getY());
        this.cell = cell;
    }

    @Override
    public void execute(TDComponent component) {
        if (component instanceof Unit) {
            Unit unit = (Unit) component;
            if (start == null) {
                start = new Cell(FieldMap.toInteger(unit.getX()),
                        FieldMap.toInteger(unit.getY()));
            }
            move(unit);
            double r = Math.sqrt(Math.pow(x - unit.getX(), 2.0)
                    + Math.pow(y - unit.getY(), 2.0));
            if (r < DELTA) {
                complete = true;
                ConflictResolver.removeCell(cell);
                ConflictResolver.removeCell(start);
            }
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

    private void move(Unit unit) {
        double ux = unit.getX();
        double uy = unit.getY();
        double speed = unit.getSpeed();
        double angle = Math.atan((y - uy) / (x - ux));
        if ((x - ux) < 0) {
            angle += Math.PI;
        }
        if (angle < 0) {
            angle += 2 * Math.PI;
        }
        unit.setAngle(angle);
        double c0x = ux + FieldMap.getCellSize() * Math.cos(angle) / 2;
        double c0y = uy + FieldMap.getCellSize() * Math.sin(angle) / 2;
        FieldMap.setWalkablePlace(ux, uy);
        if (FieldMap.isWalkable(c0x, c0y)) {
            unit.setX(ux + speed * Math.cos(angle));
            unit.setY(uy + speed * Math.sin(angle));
            ConflictResolver.removeCell(start);
        }else{
            ConflictResolver.addCell(start);
        }
        FieldMap.setUnwalkablePlace(unit.getX(), unit.getY());
    }

}
