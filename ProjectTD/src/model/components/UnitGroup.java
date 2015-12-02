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
        System.out.println("Начало диалога");
        for (TDComponent component : components) {
            if (component instanceof Unit) {
                Unit u = (Unit) component;
                if (u.getStatus() != Status.STAND) {
                    System.out.println("Исполнение задачи: " + u);
                }
            }
            component.executeTask();
        }
        System.out.println("Конец диалога");
    }

    public List<TDComponent> getComponents() {
        return components;
    }

    @Override
    public Iterator createIterator() {
        return new GroupIterator(components.iterator());
    }
}
