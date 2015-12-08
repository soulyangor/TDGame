/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.tasks;

import model.components.Status;
import model.components.TDComponent;
import model.components.Unit;
import model.logics.Cell;
import model.logics.GameField;
import model.logics.Logic;
import model.units.Person;

/**
 *
 * @author Хозяин
 */
public class Back implements Task {

    private Cell curCell;
    private final int x;
    private final int y;

    private boolean complete = false;

    public Back(Cell cell) {
        this.x = cell.getX();
        this.y = cell.getY();
    }

    @Override
    public void execute(TDComponent component) {
        if (component instanceof Person) {
            Person person = (Person) component;
            if (curCell == null) {
                curCell = definePath(person);
                if (curCell == null) {
                    complete = true;
                    return;
                }
                defineAngle(person, curCell);
            }

            move(person);
            double r = Math.sqrt(Math.pow(GameField.toRealCoordinate(curCell.getX()) - person.getX(), 2.0)
                    + Math.pow(GameField.toRealCoordinate(curCell.getY()) - person.getY(), 2.0));
            if (r < person.getSpeed()) {
                curCell = curCell.getCell();
                if (curCell != null) {
                    defineAngle(person, curCell);
                }
            }

            complete = curCell == null;

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

    private Cell definePath(Unit unit) {
        int ex = GameField.toCellCoordinate(unit.getX());
        int ey = GameField.toCellCoordinate(unit.getY());
        GameField.setWalkablePlace(unit.getX(), unit.getY());
        Cell tmp = Logic.searchPath(x, y, ex, ey);
        GameField.setUnit(unit);
        return tmp;
    }

    private void defineAngle(Unit unit, Cell cell) {
        double ex = GameField.toRealCoordinate(cell.getX());
        double ey = GameField.toRealCoordinate(cell.getY());
        double ux = unit.getX();
        double uy = unit.getY();
        double angle = Math.atan((ey - uy) / (ex - ux));
        if ((ex - ux) < 0) {
            angle += Math.PI;
        }
        if (angle < 0) {
            angle += 2 * Math.PI;
        }
        unit.setAngle(angle);
    }

    private Unit getUnitByMove(Unit unit) {
        double ux = unit.getX();
        double uy = unit.getY();
        double angle = unit.getAngle();
        GameField.setWalkablePlace(ux, uy);
        double c0x = ux + GameField.getCellSize() * Math.cos(angle) / 2;
        double c0y = uy + GameField.getCellSize() * Math.sin(angle) / 2;
        return GameField.getUnit(c0x, c0y);
    }

    private void move(Person person) {
        if (getUnitByMove(person) != null) {
            person.setStatus(Status.WAIT);
            ConflictManager.addUnit(person);
            System.out.println(person + ": не топаю");
        } else {
            person.setStatus(Status.MOVE);
            ConflictManager.removeUnit(person);
            System.out.println(person + ": топаю");
        }        
        person.move();        
        GameField.setUnit(person);
    }

    @Override
    public String toString() {
        return "Задача - отойти в точку (" + x + "," + y + ")";
    }
}
