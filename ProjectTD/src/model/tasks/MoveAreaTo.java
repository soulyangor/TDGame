/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.tasks;

import java.util.ArrayList;
import java.util.List;
import model.components.Status;
import model.components.TDComponent;
import model.components.Unit;
import model.logics.Area;
import model.logics.AreaLogic;
import model.logics.Cell;
import model.logics.Place;
import model.logics.areas.Square;
import model.logics.grids.WorldGrid;
import model.units.Insertable;
import model.units.Person;
import window.Effects;
import window.Love;

/**
 *
 * @author Sokolov@ivc.org
 */
public class MoveAreaTo implements Task {

    private static final double MIN_VALUE = 10e-3;

    private Cell curCell;
    private final int x;
    private final int y;

    private Area area = new Square(1);

    private boolean complete = false;

    /**
     * Конструктор. Создаёт экземпляр задачи для движения в точку с координатами
     * (x, y)
     *
     * @param x double
     * @param y double
     */
    public MoveAreaTo(double x, double y) {
        this.x = WorldGrid.getInstance().toCellCoordinate(x);
        this.y = WorldGrid.getInstance().toCellCoordinate(y);
        AreaLogic.setArea(area);
    }

    /**
     * Конструктор. Создаёт экземпляр задачи для движения в точку cell
     *
     * @param cell Cell
     */
    public MoveAreaTo(Cell cell) {
        this.x = cell.getX();
        this.y = cell.getY();
        AreaLogic.setArea(area);
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
            if (person instanceof Insertable) {
                this.area = ((Insertable) person).getArea();
                AreaLogic.setArea(area);
            }
            if (curCell == null) {
                curCell = definePath(person);
                if (curCell == null) {
                    complete = true;
                    return;
                }
            }

            defineAngle(person, curCell);

            Unit u = WorldGrid.getInstance().getCellValue(curCell);
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
                        u.setSpeedTask(new BackArea(temp));
                    } else {
                        System.out.println(person + ": " + u + " не может отойти");
                        temp = findFreeCell(person);
                        if (temp != null) {
                            person.setSpeedTask(new BackArea(temp));
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
                System.out.print("Второй вызов ");
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
                    if ((temp != null) && (u.getStatus() != Status.STAND)) {
                        System.out.println(person + ": " + u + " идёт в ("
                                + temp.getX() + "," + temp.getY() + ")");
                        u.setSpeedTask(new BackArea(temp));
                    } else {
                        System.out.println(person + ": " + u + " не может отойти");
                        temp = findFreeCell(person);
                        if (temp != null) {
                            person.setSpeedTask(new BackArea(temp));
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
            double r = Math.sqrt(Math.pow(WorldGrid.getInstance().toRealCoordinate(curCell.getX()) - person.getX(), 2.0)
                    + Math.pow(WorldGrid.getInstance().toRealCoordinate(curCell.getY()) - person.getY(), 2.0));
            if (r < person.getSpeed()) {
                for (Place pl : area.getPlaces()) {
                    if ((pl.getCell(curCell.getX(), curCell.getY()).getX() == x)
                            && (pl.getCell(curCell.getX(), curCell.getY()).getY() == y)) {
                        complete = true;
                        return;
                    }
                }
                curCell = curCell.getCell();
                if (curCell != null) {
                    defineAngle(person, curCell);
                }
            }

            if (r > 1.5 * WorldGrid.getInstance().getCellSize()) {
                curCell = null;
            }
            if ((r > 1) && (curCell != null)
                    && ((!WorldGrid.getInstance().isWalkable(
                            WorldGrid.getInstance().toCellCoordinate(person.getX()),
                            curCell.getY())) || (!WorldGrid.getInstance().isWalkable(curCell.getX(),
                            WorldGrid.getInstance().toCellCoordinate(person.getY()))))) {
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

    private List<Unit> getUnitsByCell(Cell cell) {
        List<Unit> units = new ArrayList<>();
        for (Place pl : area.getPlaces()) {
            Unit u = WorldGrid.getInstance().getCellValue(
                    pl.getCell(cell.getX(), cell.getY()));
            if (u != null) {
                units.add(u);
            }
        }
        return units;
    }

    private boolean isFree(Unit unit, int i, int j) {
        for (Place pl : area.getPlaces()) {
            if ((WorldGrid.getInstance().getCellValue(pl.getCell(i, j)) != null)
                    && (WorldGrid.getInstance().getCellValue(pl.getCell(i, j)) != unit)) {
                return false;
            }
        }
        return true;
    }

    private Cell findFreeCell(Unit unit) {
        Cell cell = new Cell(WorldGrid.getInstance().toCellCoordinate(unit.getX()),
                WorldGrid.getInstance().toCellCoordinate(unit.getY()));
        int s = area.getSize();
        int minI = (cell.getX() - 1) < 0 ? 0 : cell.getX() - 1;
        int minJ = (cell.getY() - 1) < 0 ? 0 : cell.getY() - 1;
        int maxI = (cell.getX() + s + 1) > WorldGrid.getInstance().getMapSize()
                ? WorldGrid.getInstance().getMapSize() : cell.getX() + s + 1;
        int maxJ = (cell.getY() + s + 1) > WorldGrid.getInstance().getMapSize()
                ? WorldGrid.getInstance().getMapSize() : cell.getY() + s + 1;

        for (int i = minI; i < maxI; i++) {
            for (int j = minJ; j < maxJ; j++) {
                if (((i == cell.getX()) && (j == cell.getY()))
                        || (!isFree(unit, i, j))) {
                    continue;
                }
                if ((Math.abs(cell.getX() - i) + Math.abs(cell.getY() - j) == 2)
                        && ((!isFree(unit, i, cell.getY()))
                        || (!isFree(unit, cell.getY(), j)))) {
                    continue;
                }
                Cell c = new Cell(i, j);
                return c;
            }
        }
        return null;
    }

    private Cell definePath(Unit unit) {
        int ex = WorldGrid.getInstance().toCellCoordinate(unit.getX());
        int ey = WorldGrid.getInstance().toCellCoordinate(unit.getY());
        WorldGrid.getInstance().setWalkablePlace(unit.getX(), unit.getY());
        Cell tmp = AreaLogic.searchPath(x, y, ex, ey);
        WorldGrid.getInstance().setCellValue(unit);
        return tmp;
    }

    private void defineAngle(Unit unit, Cell cell) {
        double ex = WorldGrid.getInstance().toRealCoordinate(cell.getX());
        double ey = WorldGrid.getInstance().toRealCoordinate(cell.getY());
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
        WorldGrid.getInstance().setWalkablePlace(ux, uy);

        for (Place pl : area.getPlaces()) {
            double c0x = pl.getXInc() * WorldGrid.getInstance().getCellSize()
                    + ux + WorldGrid.getInstance().getCellSize() * Math.cos(angle) / 2;
            double c0y = pl.getYInc() * WorldGrid.getInstance().getCellSize()
                    + uy + WorldGrid.getInstance().getCellSize() * Math.sin(angle) / 2;
            if (WorldGrid.getInstance().getCellValue(c0x, c0y) != null) {
                return WorldGrid.getInstance().getCellValue(c0x, c0y);
            }
        }
        return null;
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
        WorldGrid.getInstance().setCellValue(person);
    }

    @Override
    public String toString() {
        return "Задача - идти в точку (" + x + "," + y + ")";
    }

}
