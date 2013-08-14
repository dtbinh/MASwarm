/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package schelling;

import ASwarm.ASwarm;
import ASwarm.ASwarmFactory;
import ASwarm.SwarmApplet;
import ASwarm.SwarmPanel;

/**
 *
 * @author zaytsevid
 */
public class SchellingApplet extends SwarmApplet{

    @Override
    protected void init(SwarmPanel sp) {
        Main.initPars();
        sp.setSwarmFactory(new ASwarmFactory() {

            public ASwarm createSwarm() {
                return new SchellingModel();
            }
        });
    }

}
