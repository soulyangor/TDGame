/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.components;

import java.util.Iterator;
import java.util.Stack;

/**
 * Итератор для перебора дерева сомпонентов
 *
 * @author Хозяин
 */
public class GroupIterator implements Iterator<TDComponent> {

    private final Stack<Iterator<TDComponent>> stack = new Stack<>();

    /**
     * Конструктор, помещает в стек итератор
     *
     * @param iterator Iterator
     */
    public GroupIterator(Iterator<TDComponent> iterator) {
        stack.push(iterator);
    }

    /**
     * Проверяет наличие следующего элемента в переборе
     *
     * @return boolean
     */
    @Override
    public boolean hasNext() {
        if (stack.empty()) {
            return false;
        } else {
            Iterator<TDComponent> iterator = stack.peek();
            if (!iterator.hasNext()) {
                stack.pop();
                return hasNext();
            } else {
                return true;
            }
        }
    }

    /**
     * Возвращает следующий элементв переборе
     *
     * @return TDComponent
     */
    @Override
    public TDComponent next() {
        if (hasNext()) {
            Iterator<TDComponent> iterator = stack.peek();
            TDComponent component = iterator.next();
            if (component instanceof UnitGroup) {
                stack.push(component.createIterator());
            }
            return component;
        } else {
            return null;
        }
    }

}
