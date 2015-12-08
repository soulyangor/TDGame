/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.tasks;

import model.components.TDComponent;

/**
 * Интерфейс задач
 *
 * @author Хозяин
 */
public interface Task {

    /**
     * Выполнение задачи для компонента component
     *
     * @param component TComponent
     */
    void execute(TDComponent component);

    /**
     * прноверка, выполнена ли задача.
     *
     * @return boolean
     */
    boolean isComplete();

    /**
     * Установка задачи, как незавершённой
     */
    void notComplete();

}
