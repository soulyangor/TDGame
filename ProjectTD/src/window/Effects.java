/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package window;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sokolov@ivc.org
 */
public class Effects {

    private static final List<Love> loves = new ArrayList<>();

    private Effects() {
    }

    public static void draw(Graphics2D g2d) {
        List<Love> removedLoves = new ArrayList<>();
        for (Love l : loves) {
            l.draw(g2d);
            if (l.show == 6) {
                removedLoves.add(l);
            }
        }
        for (Love l : removedLoves) {
            loves.remove(l);
        }
    }

    public static void add(Love love) {
        loves.add(love);
    }

}
