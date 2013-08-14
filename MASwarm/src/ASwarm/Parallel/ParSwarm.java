/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ASwarm.Parallel;

import ASwarm.ASwarm;
import ASwarm.Agent;
import java.util.ArrayList;

/**
 *
 * @author zaytsevid
 */
public abstract class ParSwarm extends ASwarm {

    int nums = 0;

    @Override
    protected void calcG(ArrayList<Agent> ags) {
        synchronized (this) {
            while (nums > 0) {
                try {
                    this.wait();
                } catch (InterruptedException ex) {
                    return;
                }
            }
            nums = ags.size();
        }
        for (Agent a : ags) {
            ParAgent pa = (ParAgent) a;
            pa.setRunning();
        }
        synchronized (this) {
            while (nums > 0) {
                try {
                    this.wait();
                } catch (InterruptedException ex) {
                    return;
                }
            }
        }
        calcG1(ags);
    }

    @Override
    public void interrupt() {
        for (Agent a : agents) {
            ParAgent pa = (ParAgent) a;
            pa.interrupt();
        }
        super.interrupt();
    }


    protected abstract void calcG1(ArrayList<Agent> ags);
}
