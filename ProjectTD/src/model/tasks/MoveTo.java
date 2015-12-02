/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.tasks;

import model.logics.Cell;
import model.logics.Logic;
import model.components.Status;
import model.components.TDComponent;
import model.components.Unit;

/**
 *
 * @author Хозяин
 */
public class MoveTo implements Task {

    private Cell curCell;
    private Cell start;
    private final int x;
    private final int y;

    private boolean complete = false;

    public MoveTo(double x, double y) {
        this.x = Logic.toCellCoordinate(x);
        this.y = Logic.toCellCoordinate(y);
    }

    public MoveTo(Cell cell) {
        this.x = cell.getX();
        this.y = cell.getY();
    }

    @Override
    public void execute(TDComponent component) {
        if (component instanceof Unit) {
            Unit unit = (Unit) component;
            if (curCell == null) {
                curCell = definePath(unit);
                if (curCell == null) {
                    complete = true;
                    return;
                }
            }

            Unit u = Logic.getUnit(curCell);

            if ((unit.getStatus() == Status.WAIT) && (u != null)
                    && (u.getStatus() == Status.WAIT)) {
                System.out.println(unit.toString() + ": не могу пройти, пытаюсь обойти");
                BackCells.setStandUnit(unit);
                curCell = definePath(unit);
                BackCells.setWaitUnit();
                if (curCell == null) {
                    System.out.println(unit + ": не могу обойти, прошу "
                            + u + " отойти");
                    Cell temp = findFreeCell(u);
                    if (temp != null) {
                        System.out.println(unit + ": " + u + " идёт в ("
                                + temp.getX() + "," + temp.getY() + ")");
                        u.setTask(new Back(temp));
                    } else {
                        System.out.println(unit + ": " + u + " не может отойти");
                        temp = findFreeCell(unit);
                        if (temp != null) {
                            unit.setTask(new Back(temp));
                            System.out.println(unit + ": отхожу сам");
                        } else {
                            System.out.println(unit + ": сам не могу отойти");
                        }
                    }

                    return;
                }
            }

            move(unit, curCell);
            double r = Math.sqrt(Math.pow(
                    Logic.toRealCoordinate(curCell.getX()) - unit.getX(), 2.0)
                    + Math.pow(Logic.toRealCoordinate(curCell.getY()) - unit.getY(), 2.0));
            if (r < unit.getSpeed()) {
                curCell = curCell.getParent();
            }
            if (r > 1.5 * Logic.getCellSize()) {
                curCell = null;
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
        int ex = Logic.toCellCoordinate(unit.getX());
        int ey = Logic.toCellCoordinate(unit.getY());
        Logic.setWalkablePlace(unit.getX(), unit.getY());
        Cell tmp = Logic.searchPath(x, y, ex, ey);
        Logic.setUnit(unit);
        return tmp;
    }

    private Cell findFreeCell(Unit unit) {
        Cell cell = new Cell(Logic.toCellCoordinate(unit.getX()),
                Logic.toCellCoordinate(unit.getY()));
        int minI = (cell.getX() - 1) < 0 ? 0 : cell.getX() - 1;
        int minJ = (cell.getY() - 1) < 0 ? 0 : cell.getY() - 1;
        int maxI = (cell.getX() + 2) > Logic.getMapSize() ? Logic.getMapSize()
                : cell.getX() + 2;
        int maxJ = (cell.getY() + 2) > Logic.getMapSize() ? Logic.getMapSize()
                : cell.getY() + 2;

        for (int i = minI; i < maxI; i++) {
            for (int j = minJ; j < maxJ; j++) {
                if ((Logic.getUnit(i, j) == unit)
                        || (Logic.getUnit(i, j) != null)) {
                    continue;
                }
                if ((Math.abs(cell.getX() - i) + Math.abs(cell.getY() - j) == 2)
                        && ((Logic.getUnit(i, cell.getY()) != null)
                        || (Logic.getUnit(i, cell.getX()) != null))) {
                    continue;
                }
                Cell c = new Cell(i, j);
                return c;
            }
        }
        return null;
    }

    private void move(Unit unit, Cell cell) {
        double ex = Logic.toRealCoordinate(cell.getX());
        double ey = Logic.toRealCoordinate(cell.getY());
        double ux = unit.getX();
        double uy = unit.getY();
        double speed = unit.getSpeed();
        double angle = Math.atan((ey - uy) / (ex - ux));
        if ((ex - ux) < 0) {
            angle += Math.PI;
        }
        if (angle < 0) {
            angle += 2 * Math.PI;
        }

        Logic.setWalkablePlace(ux, uy);

        double c0x = ux + Logic.getCellSize() * Math.cos(angle) / 2;
        double c0y = uy + Logic.getCellSize() * Math.sin(angle) / 2;

        if (Logic.getUnit(c0x, c0y) != null) {
            unit.setStatus(Status.WAIT);
            BackCells.addUnit(unit);
            System.out.println(unit + ": не топаю");
        } else {
            unit.setStatus(Status.MOVE);
            BackCells.removeUnit(unit);
            System.out.println(unit + ": топаю");
        }

        if (unit.getStatus() == Status.MOVE) {
            unit.setAngle(angle);
            unit.setX(ux + speed * Math.cos(angle));
            unit.setY(uy + speed * Math.sin(angle));
        }
        Logic.setUnit(unit);
    }

}
