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
 * Класс для работы с группами компонентов
 *
 * @author Хозяин
 */
public class UnitGroup extends TDComponent {

    private final List<TDComponent> components = new ArrayList<>();

    /**
     * Конструктор
     */
    public UnitGroup() {
        super();
    }

    /**
     * Добавление нового компонента в группу
     *
     * @param component TDComponent
     */
    @Override
    public void add(TDComponent component) {
        components.add(component);
    }

    /**
     * Удаление компонента из группы
     *
     * @param component TDComponent
     */
    @Override
    public void remove(TDComponent component) {
        components.remove(component);
    }

    /**
     * Получение компонента по индексу
     *
     * @param i int
     * @return TDComponent
     */
    @Override
    public TDComponent getChild(int i) {
        return components.get(i);
    }

    /**
     * Выполнение задач компонента. Выполнение включает как собственные задачи,
     * так и задачи составных компонентов, при чем задачи составляющих
     * компонентов выполняются после задач самого компонента
     */
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

    /**
     * Возвращает список составляющих компонентов
     *
     * @return List
     */
    public List<TDComponent> getComponents() {
        return components;
    }

    /**
     * Создаёт и возвращает итератор группы
     *
     * @return Iterator
     */
    @Override
    public Iterator createIterator() {
        return new GroupIterator(components.iterator());
    }
}
