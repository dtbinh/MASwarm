/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package schelling;

import ASwarm.ASwarm;
import ASwarm.ASwarmFactory;
import ASwarm.Params;
import ASwarm.SwarmFrame;
import javax.swing.UIManager;

/**
 *
 * @author zaytsevid
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            //Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        initPars();
        SwarmFrame md = new SwarmFrame("Schelling Model");
        md.setSwarmFactory(new ASwarmFactory() {

            public ASwarm createSwarm() {
                return new SchellingModel();
            }
        });
        md.sp.setPaintStep(1000);
        //md.sp.setGraphStep(100);
        //md.sp.setGraphPaintStep(0);
        //md.sp.killLineChart();
        //m.start();
        md.setVisible(true);
        md.setMain();

        //System.exit(0);
    }

    static void initPars() {
        Params.map.put("N", "20");
        Params.map.put("Z", "4");
        Params.map.put("M", "2");
    }
}

