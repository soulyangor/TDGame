/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.tasks;

import model.actions.DefineViewable;
import model.actions.FindPath;
import model.actions.findpathalgorithms.Cell;
import model.actions.findpathalgorithms.ConflictResolver;
import model.actions.findpathalgorithms.FieldMap;
import model.actions.findpathalgorithms.PathAnalyser;
import model.components.TDComponent;
import model.components.Unit;
import model.components.UnitGroup;
import window.DrawablePerson;

/**
 *
 * @author Хозяин
 */
public class DefinePath implements Task {

    private final FindPath findPath;
    private Cell curCell;
    private final DefineViewable defineViewable;
    private static int k = 0;
    private final UnitGroup group;

    private boolean complete = false;

    private static final double DELTA = 3;

    public DefinePath(double x, double y, UnitGroup group) {
        int i = FieldMap.toInteger(x);
        int j = FieldMap.toInteger(y);
        findPath = new FindPath();
        findPath.setXY(i, j);
        defineViewable = new DefineViewable();
        defineViewable.setGroup(group);
        this.group = group;
    }

    @Override
    public void execute(TDComponent component) {
        if (component instanceof Unit) {
            setOptions();
            Unit unit = (Unit) component;
            if (curCell == null) {
                FieldMap.setWalkablePlace(unit.getX(), unit.getY());
                findPath.setUnit(unit);
                findPath.act();
                curCell = findPath.getPath();
                //PathAnalyser.addPath(curCell);
                k++;
                //  System.out.println("Количество расчётов пути - " + k);
                if (curCell == null) {
                    //complete = true;
                    return;
                }
            } else {
                ConflictResolver.addCell(curCell);
            }

            curCell = curCell.getParent();

            if (curCell == null) {
                complete = true;
                return;
            }

            if (!ConflictResolver.addCell(curCell)) {
                FieldMap.setWalkablePlace(unit.getX(), unit.getY());
                findPath.setUnit(unit);
                FieldMap.setUnwalkablePlace(curCell);
                findPath.act();
                FieldMap.setWalkablePlace(curCell);
                curCell = findPath.getPath();
                //PathAnalyser.addPath(curCell);
                if (curCell == null) {
                    return;
                }
                k++;
            }

            component.setTask(new MoveTo(curCell));
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

    private void setOptions() {
        for (TDComponent c : group) {
            if (c instanceof DrawablePerson) {
                Unit u = (Unit) c;
                FieldMap.setWalkablePlace(u.getX(), u.getY());
            }
        }
    }

}
