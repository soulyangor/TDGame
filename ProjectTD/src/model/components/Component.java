/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.components;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import model.tasks.DefinePath;
import model.tasks.Task;

/**
 *
 * @author Хозяин
 */
public abstract class Component implements Iterable<Component> {

    private Task speedTask;
    private Task executedTask;
    private final Queue<Task> tasks;

    public Component() {
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
            return;
        }
        if ((executedTask == null) || (tasks.isEmpty())) {
            return;
        }
        executedTask.execute(this);
        if (executedTask.isComplete()) {
            executedTask.notComplete();
            addTask(executedTask);
            executedTask = tasks.remove();
        }
    }

    public void add(Component component) {
        throw new UnsupportedOperationException();
    }

    public void remove(Component component) {
        throw new UnsupportedOperationException();
    }

    Component getChild(int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<Component> iterator() {
        return createIterator();
    }

    // Временный метод для отладки
    public DefinePath getExecutedTask() {
        return (DefinePath)executedTask;
    }

}
