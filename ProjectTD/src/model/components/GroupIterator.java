/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.components;

import java.util.Iterator;
import java.util.Stack;

/**
 *
 * @author Хозяин
 */
public class GroupIterator implements Iterator {

    private final Stack stack = new Stack();

    public GroupIterator(Iterator iterator) {
        stack.push(iterator);
    }

    @Override
    public boolean hasNext() {
        if (stack.empty()) {
            return false;
        } else {
            Iterator iterator = (Iterator) stack.peek();
            if (!iterator.hasNext()) {
                stack.pop();
                return hasNext();
            } else {
                return true;
            }
        }
    }

    @Override
    public Object next() {
        if (hasNext()) {
            Iterator iterator = (Iterator) stack.peek();
            TDComponent component = (TDComponent) iterator.next();
            if (component instanceof UnitGroup) {
                stack.push(component.createIterator());
            }
            return component;
        } else {
            return null;
        }
    }

}
