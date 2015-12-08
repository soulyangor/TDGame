/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.components;

import java.util.Comparator;

/**
 * Компаратор для сортировки по координате y
 *
 * @author Хозяин
 */
public class SortedByY implements Comparator<Unit> {

    /**
     * Сравнивает два юнита по координате y. 
     * Если o1 > o2, возвращается положительное число. 
     * Если o1 < o2, возвращается отрицательное число. 
     * Если o1 = o2, возвращается нуль
     *
     * @param o1 Unit @param o2 Unit @return int
     */
    @Override
    public int compare(Unit o1, Unit o2) {
        return (int) (o1.getY() - o2.getY());
    }
}
