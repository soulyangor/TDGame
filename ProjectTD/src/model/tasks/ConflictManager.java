/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.tasks;

import java.util.ArrayList;
import java.util.List;
import model.components.Status;
import model.components.Unit;

/**
 * Вспомогательный класс для управления состояниями конфликтующих юнитов
 *
 * @author Хозяин
 */
public class ConflictManager {

    private static final List<Unit> units = new ArrayList<>();

    private ConflictManager() {
    }

    /**
     * Добавляет юнита в список конфликтующих
     *
     * @param unit Unit
     */
    public static void addUnit(Unit unit) {
        if (!units.contains(unit)) {
            units.add(unit);
        }
    }

    /**
     * Удаление юнита из списка конфликтующих
     *
     * @param unit Unit
     */
    public static void removeUnit(Unit unit) {
        units.remove(unit);
    }

    /**
     * Устанавливает статус конфликтующих юнитов (кроме юнита unit) в
     * Status.STAND, делая, занимаемые ими ячейки, непроходимыми
     *
     * @param unit Unit
     */
    public static void setStandUnit(Unit unit) {
        for (Unit u : units) {
            if (u != unit) {
                u.setStatus(Status.STAND);
            }
        }
    }

    /**
     * Устанавливает статус конфликтующих юнитов в Status.WAIT, делая,
     * занимаемые ими ячейки, проходимыми
     */
    public static void setWaitUnit() {
        for (Unit u : units) {
            u.setStatus(Status.MOVE);
        }
    }

}
