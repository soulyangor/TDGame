/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.tasks;

import model.actions.findpathalgorithms.ConflictsResolver;
import model.actions.findpathalgorithms.FieldMap;
import model.components.Component;
import model.components.Unit;
import model.units.Building;

/**
 *
 * @author Хозяин
 */
public class SetWalkableCells implements Task {

    @Override
    public void execute(Component component) {
        /*for (Component c : component) {
            if ((c instanceof Unit) && (!(c instanceof Building))) {
                Unit u = (Unit) c;
                FieldMap.setWalkablePlace(u.getX(), u.getY());
            }
        }*/
        ConflictsResolver.clear();
    }

    @Override
    public boolean isComplete() {
        return false;
    }

    @Override
    public void notComplete() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
