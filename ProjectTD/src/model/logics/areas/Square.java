/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.logics.areas;

import model.logics.Place;
import model.logics.Area;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sokolov@ivc.org
 */
public class Square extends Area {

    private final int size;
    private final List<Place> places;

    public Square(int size) {
        this.size = size;
        this.places = new ArrayList<>();
        this.generate();
    }

    @Override
    public List<Place> getPlaces() {
        return places;
    }

    @Override
    public void generate() {
        if (places.isEmpty()) {
            if (size <= 1) {
                places.add(new Place(0, 0));
                return;
            }
            for (int i = 0; i < size; i++) {
                places.add(new Place(i, 0));
                if (size > 1) {
                    places.add(new Place(i, size - 1));
                }
                if ((i != 0) && (i != size - 1)) {
                    places.add(new Place(0, i));
                }
            }
        }
    }

    @Override
    public int getSize() {
        return size;
    }

}
