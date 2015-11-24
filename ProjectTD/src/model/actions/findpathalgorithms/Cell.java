package model.actions.findpathalgorithms;

import java.util.LinkedHashMap;

/**
 * Created by Вячеслав on 11.09.2015.
 */
public class Cell {
    /*---------------------------------Private fields------------------------------------*/
    private final int x;
    private final int y;
    private Cell parent;
    private double g;
    private double h;
    private double f;

    /*------------------------------------Constructor------------------------------------*/
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.parent = null;
        this.g = 0;
        this.h = 0;
        this.f = 0;
    }

    /*--------------------------------Getters and setters---------------------------------*/
    public int getX() { return x; }

    public int getY() { return y; }

    public Cell getParent() { return parent; }
    public void setParent(Cell parent) { this.parent = parent; }

    public double getG() { return g; }
    public void setG(double g) { this.g = g; }

    public double getF() { return f; }

    /*---------------------------------------Methods----------------------------------------*/

    public void calcF(){ this.f = this.h + this.g; }

    public void calcH(int x, int y){
        this.h = Math.sqrt((x - this.x) * (x - this.x) + (y - this.y) * (y - this.y));
    }

    public final String createKey(){
        return Integer.toString(this.x) + "|" + Integer.toString(this.y);
    }

    public LinkedHashMap<String, Cell> formPath(){
        LinkedHashMap<String, Cell> path = new LinkedHashMap<>();
        Cell current = this;
        while (current.getParent() != null) {
            path.put(current.createKey(), current);
            current = current.getParent();
        }
        return path;
    }

}
