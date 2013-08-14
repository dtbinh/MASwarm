/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package codegen;

import ASwarm.ASwarm;
import ASwarm.ASwarmFactory;
import ASwarm.SwarmApplet;
import ASwarm.SwarmPanel;

/**
 *
 * @author Ivan
 */
public class CevApplet extends SwarmApplet {
    @Override
    protected void init(SwarmPanel sp) {
        Main.initPars();
        sp.setSwarmFactory(new ASwarmFactory() {
            public ASwarm createSwarm() {
                return new CevSwarm();
            }
        });
    }
}
