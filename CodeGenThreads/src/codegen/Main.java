/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package codegen;

import ASwarm.Params;
import ASwarm.ASwarm;
import ASwarm.ASwarmFactory;
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
        // TODO code application logic here
        /*byte bs[] = new byte[6];
        bs[0]=0;
        bs[1]=0;
        bs[2]=0;
        bs[3]=1;
        bs[4]=1;
        bs[5]=1;
        Code c = new Code();
        c.generate();
        //bs = c.toArray();
        //c.fromArray(bs);
        System.out.println(c.toString());
        System.out.println(c.calcF());*/
        //byte bs[] = c.toArray();
        //for(byte b : bs)System.out.println(""+b);
        /*Population pop = new Population();
        pop.generate();
        int n = 10000;
        while (n-->0) {
            int h = pop.calcH();
            System.out.println(h);
            pop.multiply();
        }*/
        initPars();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            //Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        SwarmFrame md = new SwarmFrame("Code evolution");
        md.setSwarmFactory(new ASwarmFactory() {
            public ASwarm createSwarm() {
                return new CevSwarm();
            }
        });
        //m.start();
        md.setVisible(true);
        md.setMain();
    }

    static void initPars() {
        Params.map.put("n", "10");
        Params.map.put("R", "2");
        Params.map.put("x", "29");
        Params.map.put("mutation", "0.001");
    }

}
