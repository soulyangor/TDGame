/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.components;

import java.util.Iterator;

/**
 * Класс, описывающий абстрактного единичного юнита
 *
 * @author Хозяин
 */
public abstract class Unit extends TDComponent {

    private double x;
    private double y;
    private double angle;
    private Status status;

    /**
     * Конструктор. Создает экземпляр класса с координатами в точке (x, y)
     *
     * @param x double
     * @param y double
     */
    public Unit(double x, double y) {
        super();
        this.x = x;
        this.y = y;
        this.status = Status.STAND;
    }

    /**
     * Создает нулевой итератор
     *
     * @return NullIterator
     */
    @Override
    public Iterator createIterator() {
        return new NullIterator();
    }

    /**
     * Возвращает статус юнита
     *
     * @return Status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Устанавливает статус юнита
     *
     * @param status - устанавливаемый статус
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Возвращает координату x
     *
     * @return double
     */
    public double getX() {
        return x;
    }

    /**
     * Устанавливает координату x
     *
     * @param x double
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Возвращает координату y
     *
     * @return double
     */
    public double getY() {
        return y;
    }

    /**
     * Устанавливает координату y
     *
     * @param y double
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Возвращает угол направления движения
     *
     * @return double
     */
    public double getAngle() {
        return angle;
    }

    /**
     * Устанавливает угол напрвления движения
     *
     * @param angle double
     */
    public void setAngle(double angle) {
        this.angle = angle;
    }

}
