/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.components;

import java.util.Iterator;

/**
 * Нулевой итератор
 *
 * @author Хозяин
 */
public class NullIterator implements Iterator<TDComponent> {

    /**
     * Возвращает false
     *
     * @return false
     */
    @Override
    public boolean hasNext() {
        return false;
    }

    /**
     * возвращает null
     *
     * @return null
     */
    @Override
    public TDComponent next() {
        return null;
    }

}
