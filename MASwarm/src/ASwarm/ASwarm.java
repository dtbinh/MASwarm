/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ASwarm;

import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author zaytsevid
 */
public abstract class ASwarm extends Thread {
    protected JPanel panel;
    protected ArrayList<Agent> agents = new ArrayList<Agent>();
    protected Relationship rel;
    protected Globals globals;
    protected SwarmController controller;

    public ASwarm() {
    }

    protected abstract void calcG(ArrayList<Agent> ags);

    protected abstract ArrayList<Agent> selectAgents();

    protected void step() {
        ArrayList<Agent> a = selectAgents();
        calcG(a);
    }

    JPanel getPanel() {
        return panel;
    }

    @Override
    public void run() {
        while (true) {
            step();
            globals.time++;
            globals.h = calcH();
            globals.change();
            synchronized (this) {
                while (state == State.paused) {
                    try {
                        this.wait();
                    } catch (InterruptedException ex) {
                        return;
                    }
                }
            }
        }
    }

    protected void pause() {
        synchronized (this) {
            if (state == State.playing) {
                state = State.paused;
            }
        }
        //globals.paintGraph();
    }

    void setGlobals(Globals g) {
        globals = g;
        globals.time = 0;
        globals.h = calcH();
        globals.change();
    }

    protected abstract double calcH();

    protected void setParams() {

    }

    void setController(SwarmController sc) {
        controller = sc;
    }

    enum State {

        notstarted,
        playing,
        paused
    }
    private State state = State.notstarted;

    void play() {
        if (state == State.notstarted) {
            synchronized (this) {
                state = State.playing;
            }
            start();
        } else if (state == State.paused) {
            synchronized (this) {
                state = State.playing;
                this.notifyAll();
            }
        }

    }
}
