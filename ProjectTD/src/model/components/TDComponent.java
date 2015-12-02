/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.components;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import model.tasks.MoveTo;
import model.tasks.Task;

/**
 *
 * @author Хозяин
 */
public abstract class TDComponent implements Iterable<TDComponent> {

    private Task speedTask;
    private Task executedTask;
    private final Queue<Task> tasks;

    public TDComponent() {
        this.tasks = new LinkedList<>();
    }

    public abstract Iterator createIterator();

    public void setTask(Task task) {
        this.speedTask = task;
    }

    public void addTask(Task task) {
        if (executedTask == null) {
            this.executedTask = task;
            return;
        }
        tasks.offer(task);
    }

    public void executeTask() {
        if ((speedTask != null) && (!speedTask.isComplete())) {
            speedTask.execute(this);
            System.out.println(speedTask);
            return;
        }
        if ((executedTask == null) || (tasks.isEmpty())) {
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

    TDComponent getChild(int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<TDComponent> iterator() {
        return createIterator();
    }

    // Временный метод для отладки
    public MoveTo getExecutedTask() {
        return (MoveTo)executedTask;
    }

}
