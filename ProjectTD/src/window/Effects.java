/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package window;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author Sokolov@ivc.org
 */
public class Effects {

    private static final List<Love> loves = new CopyOnWriteArrayList<>();

    private Effects() {
    }

    public static void draw(Graphics2D g2d) {
        for (Love l : loves) {
            l.draw(g2d);
        }
    }

    public static void update() {
        List<Love> removeList = new ArrayList<>();
        for (Love l : loves) {
            l.update();
            if (l.getLiveTime() > 20) {
                removeList.add(l);
            }
        }
        for (Love l : removeList) {
            l.clearImage();
            loves.remove(l);
        }
        removeList = null;
    }

    public static void add(Love love) {
        loves.add(love);
    }

}
