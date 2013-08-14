/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ASwarm;

import java.util.ArrayList;

/**
 *
 * @author zaytsevid
 */
public class Globals {
    ArrayList<Double> data;// = new ArrayList<Double>[2];
    public long time = 0;
    double h = 0;
    GlobalsPainter painter;

    public Globals() {
        data = new ArrayList<Double>();
    }

    void setPainter(GlobalsPainter md) {
        painter = md;
    }

    void change() {
        //if(time%1000==0)
            painter.paintGlobals(this);
    }
}
