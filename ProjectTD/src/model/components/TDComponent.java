/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.components;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import model.tasks.MoveAreaTo;
import model.tasks.MoveTo;
import model.tasks.Task;

/**
 * Верхний компонент для описания всех элементов TD
 *
 * @author Хозяин
 */
public abstract class TDComponent implements Iterable<TDComponent> {

    private Task speedTask;
    private Task executedTask;
    private final Queue<Task> tasks;

    /**
     * Конструктор класса Создает внутри себя пустой список задач(task)
     */
    public TDComponent() {
        this.tasks = new LinkedList<>();
    }

    /**
     * Абстрактный метод для создания итератора
     *
     * @return Iterator
     */
    public abstract Iterator createIterator();

    /**
     * Устанавливает срочную задачу
     *
     * @param task Task
     */
    public void setSpeedTask(Task task) {
        this.speedTask = task;
    }

    /**
     * Добавляет задачу в список задач. Если исполняемая задача не
     * определена(это возможно, если список задач пуст), то добавляемая задача
     * устанавливается как исполняемая, в противном случае задача добавляется в
     * список задач. Фактически, исполняемая задача, является первой задачей из
     * списка задач
     *
     * @param task Task
     */
    public void addTask(Task task) {
        if (executedTask == null) {
            this.executedTask = task;
            return;
        }
        tasks.offer(task);
    }

    /**
     * Исполнение задачи. Порядок выполнения: Если задана срочная задача и она
     * не выполнена, то исполняется она. Если Срочная задача не установлена или
     * выполнена, то исполняется текущая задача, если таковая определена Если
     * исполняемая задача завершена, то она помечается как незавершенная и
     * добавляется к концу списка задач, а в качестве исполняемой
     * устанавливается первая задача из списка
     */
    public void executeTask() {
        if ((speedTask != null) && (!speedTask.isComplete())) {
            speedTask.execute(this);
            System.out.println(speedTask);
            return;
        }
        if (executedTask == null) {
            return;
        }
        System.out.println(executedTask);
        executedTask.execute(this);
        if (executedTask.isComplete()) {
            executedTask.notComplete();
            addTask(executedTask);
            executedTask = tasks.remove();
        }
    }

    public void add(TDComponent component) {
        throw new UnsupportedOperationException();
    }

    public void remove(TDComponent component) {
        throw new UnsupportedOperationException();
    }

    public TDComponent getChild(int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<TDComponent> iterator() {
        return createIterator();
    }

    // Временный метод для отладки, возвращает текущую исполняемую задачу
    // (не срочную)
    public MoveAreaTo getExecutedTask() {
        return (MoveAreaTo) executedTask;
    }

    /*public MoveTo getExecutedTask() {
     return (MoveTo) executedTask;
     }*/
}
