/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.components;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Хозяин
 */
public class UnitGroup extends TDComponent {

    private final List<TDComponent> components;

    public UnitGroup() {
        super();
        this.components = new ArrayList<>();
    }

    @Override
    public void add(TDComponent component) {
        components.add(component);
    }

    @Override
    public void remove(TDComponent component) {
        components.remove(component);
    }

    @Override
    public TDComponent getChild(int i) {
        return components.get(i);
    }

    @Override
    public void executeTask() {
        super.executeTask();
        for (TDComponent component : components) {
            component.executeTask();
        }
    }

    public List<TDComponent> getComponents() {
        return components;
    }

    @Override
    public Iterator createIterator() {
        return new GroupIterator(components.iterator());
    }
}
