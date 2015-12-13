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
import model.logics.grids.GameField;
import model.units.Person;
import window.Effects;
import window.Love;

/**
 * Класс для реализации задачи движения в заданную точку
 *
 * @author Хозяин
 */
public class MoveTo implements Task {

    private static final double MIN_VALUE = 10e-3;

    private Cell curCell;
    private final int x;
    private final int y;

    private boolean complete = false;

    /**
     * Конструктор. Создаёт экземпляр задачи для движения в точку с координатами
     * (x, y)
     *
     * @param x double
     * @param y double
     */
    public MoveTo(double x, double y) {
        this.x = GameField.toCellCoordinate(x);
        this.y = GameField.toCellCoordinate(y);
    }

    /**
     * Конструктор. Создаёт экземпляр задачи для движения в точку cell
     *
     * @param cell Cell
     */
    public MoveTo(Cell cell) {
        this.x = cell.getX();
        this.y = cell.getY();
    }

    /**
     * Выполнение задачи для компонента component
     *
     * @param component TDComponent
     */
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

            Unit u = GameField.getUnit(curCell);

            if ((person.getStatus() == Status.WAIT) && (u != null)
                    && (u.getStatus() == Status.WAIT)) {
                Effects.add(new Love(person));
                Effects.add(new Love(u));
                System.out.println(person.toString() + ": не могу пройти, мешает "
                        + u + ", пытаюсь обойти");
                ConflictManager.setStandUnit(person);
                if (curCell.getCell() != null) {
                    curCell = definePath(person);
                } else {
                    curCell = null;
                }
                ConflictManager.setWaitUnit();
                if (curCell == null) {
                    System.out.println(person + ": не могу обойти, прошу "
                            + u + " отойти");
                    Cell temp = findFreeCell(u);
                    if (temp != null) {
                        System.out.println(person + ": " + u + " идёт в ("
                                + temp.getX() + "," + temp.getY() + ")");
                        u.setSpeedTask(new Back(temp));
                    } else {
                        System.out.println(person + ": " + u + " не может отойти");
                        temp = findFreeCell(person);
                        if (temp != null) {
                            person.setSpeedTask(new Back(temp));
                            System.out.println(person + ": отхожу сам");
                        } else {
                            System.out.println(person + ": сам не могу отойти");
                        }
                    }
                    return;
                }
                defineAngle(person, curCell);
            }

            u = getUnitByMove(person);

            if ((person.getStatus() == Status.WAIT) && (u != null)
                    && ((u.getStatus() == Status.WAIT) || (u.getStatus() == Status.STAND))) {
                Effects.add(new Love(person));
                Effects.add(new Love(u));
                System.out.println(person.toString() + ": не могу пройти, мешает "
                        + u + ", пытаюсь обойти");
                ConflictManager.setStandUnit(person);
                if (curCell.getCell() != null) {
                    curCell = definePath(person);
                } else {
                    curCell = null;
                }
                ConflictManager.setWaitUnit();
                if (curCell == null) {
                    System.out.println(person + ": не могу обойти, прошу "
                            + u + " отойти");
                    Cell temp = findFreeCell(u);
                    if (temp != null) {
                        System.out.println(person + ": " + u + " идёт в ("
                                + temp.getX() + "," + temp.getY() + ")");
                        u.setSpeedTask(new Back(temp));
                    } else {
                        System.out.println(person + ": " + u + " не может отойти");
                        temp = findFreeCell(person);
                        if (temp != null) {
                            person.setSpeedTask(new Back(temp));
                            System.out.println(person + ": отхожу сам");
                        } else {
                            System.out.println(person + ": сам не могу отойти");
                        }
                    }
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
            if (r > 1.5 * GameField.getCellSize()) {
                curCell = null;
            }
            if ((r > 1) && (curCell != null) && ((!GameField.isWalkable(GameField.toCellCoordinate(person.getX()), curCell.getY()))
                    || (!GameField.isWalkable(curCell.getX(),
                            GameField.toCellCoordinate(person.getY()))))) {
                curCell = null;
                System.out.println("вызван для " + person);
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

    private Cell findFreeCell(Unit unit) {
        Cell cell = new Cell(GameField.toCellCoordinate(unit.getX()),
                GameField.toCellCoordinate(unit.getY()));
        int minI = (cell.getX() - 1) < 0 ? 0 : cell.getX() - 1;
        int minJ = (cell.getY() - 1) < 0 ? 0 : cell.getY() - 1;
        int maxI = (cell.getX() + 2) > GameField.getMapSize() ? GameField.getMapSize()
                : cell.getX() + 2;
        int maxJ = (cell.getY() + 2) > GameField.getMapSize() ? GameField.getMapSize()
                : cell.getY() + 2;

        for (int i = minI; i < maxI; i++) {
            for (int j = minJ; j < maxJ; j++) {
                if ((GameField.getUnit(i, j) == unit)
                        || (GameField.getUnit(i, j) != null)) {
                    continue;
                }
                if ((Math.abs(cell.getX() - i) + Math.abs(cell.getY() - j) == 2)
                        && ((GameField.getUnit(i, cell.getY()) != null)
                        || (GameField.getUnit(cell.getX(), i) != null))) {
                    continue;
                }
                Cell c = new Cell(i, j);
                return c;
            }
        }
        return null;
    }

    private void defineAngle(Unit unit, Cell cell) {
        double ex = GameField.toRealCoordinate(cell.getX());
        double ey = GameField.toRealCoordinate(cell.getY());
        double ux = unit.getX();
        double uy = unit.getY();
        double angle;
        if (Math.abs(ex - ux) < MIN_VALUE) {
            double c = Math.signum(ex - ux);
            double z = Math.signum(ey - uy);
            angle = Math.atan(z / (c * MIN_VALUE));
        } else {
            angle = Math.atan((ey - uy) / (ex - ux));
        }
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
        return "Задача - идти в точку (" + x + "," + y + ")";
    }

}
