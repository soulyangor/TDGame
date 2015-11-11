/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.tasks;

import model.actions.FindPath;
import model.actions.findpathalgorithms.Cell;
import model.actions.findpathalgorithms.ConflictsResolver;
import model.actions.findpathalgorithms.FieldMap;
import model.components.Component;
import model.components.Unit;

/**
 *
 * @author Хозяин
 */
public class DefinePath implements Task {

    private final int x;
    private final int y;
    private final FindPath findPath;
    private Cell curCell;

    private boolean complete = false;

    private static final double DELTA = 3;

    public DefinePath(double x, double y) {
        this.x = FieldMap.toInteger(x);
        this.y = FieldMap.toInteger(y);
        findPath = new FindPath();
    }

    @Override
    public void execute(Component component) {
        if (component instanceof Unit) {
            Unit unit = (Unit) component;

            if (curCell == null) {
                findPath.setUnit(unit);
                findPath.setXY(x, y);
                findPath.act();
                curCell = findPath.getPath();
                if (curCell == null) {
                    complete = true;
                    return;
                }
            }

            curCell = curCell.getParent();
            
            if (curCell == null) {
                complete = true;
                return;
            }
            
            if(!ConflictsResolver.canWalk(curCell)){
                FieldMap.setUnwalkablePlace(curCell);
                findPath.act();
                FieldMap.setWalkablePlace(curCell);
                curCell = findPath.getPath();
            }
            
            if(!FieldMap.isWalkable(curCell)){
                FieldMap.setUnwalkablePlace(curCell);
                findPath.act();
                FieldMap.setWalkablePlace(curCell);
                curCell = findPath.getPath();
            }
            
            component.setTask(new MoveTo(curCell));

            double r = Math.sqrt(Math.pow(x - unit.getX(), 2.0)
                    + Math.pow(y - unit.getY(), 2.0));
            if (r < DELTA) {
                complete = true;
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

// Временные методы для отладки
    public Cell getCurCell() {
        return curCell;
    }

}
