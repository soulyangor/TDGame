/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.logics.vision;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.Union;
import java.util.List;
import model.components.Unit;
import model.logics.Grid;
import model.units.VisibleArea;

/**
 *
 * @author vita
 */
public class Visions {
    
    
   public static List<Union> see(VisibleArea  p, WorldGrid worldGrid){
       Unit u = p.getUnit();
       int x = worldGrid.toCellCoordinate(u.getX());
       int y = worldGrid.toCellCoordinate(u.getY());
       WorldGrid;
   }
    
}
