/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package schelling;

import ASwarm.ASwarm;
import ASwarm.Agent;
import ASwarm.Params;
import ASwarm.grid.GridPanel;
import ASwarm.grid.GridRelationship;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;

/**
 *
 * @author zaytsevid
 */
public class SchellingModel extends ASwarm {

    static int N = 20;
    Random r = new Random();

    @Override
    protected void setParams() {
        for(Agent a:agents){
            SchellingAgent sa = (SchellingAgent) a;
            sa.setParams();
        }
    }



    public SchellingModel() {
        N = Integer.parseInt(Params.map.get("N"));
        panel = new GridPanel() {

            @Override
            protected void paintPanel(Agent a, JPanel p) {
                int c = ((SchellingAgent)a).col;
                if(c==0)
                    p.setBackground(Color.red);
                else
                    p.setBackground(Color.blue);
            }
        };
        agents = new ArrayList<Agent>(N * N);
        rel = new GridRelationship(N);
        int c = 0;
        for (int i = 0; i < N; i++) {
            c = (c == 0) ? 1 : 0;
            for (int j = 0; j < N; j++) {
                SchellingAgent a = new SchellingAgent();
                a.init(i, j, (GridRelationship) rel, c);
                c = (c == 0) ? 1 : 0;//!!!
                agents.add(a);
            }
        }
        ((GridPanel)panel).init(N, (GridRelationship) rel);
        //panel.setRelationsip(rel);
    }

    @Override
    protected ArrayList<Agent> selectAgents() {
        GridRelationship re = (GridRelationship) rel;
        ArrayList<Agent> res = new ArrayList<Agent>(2);
        SchellingAgent a0,a1;
        int i = r.nextInt(N);
        int j = r.nextInt(N);
        a0 =  (SchellingAgent) re.getAgent(i, j);
        res.add(a0);
        while (true) {
            i = r.nextInt(N);
            j = r.nextInt(N);
            a1 = (SchellingAgent) re.getAgent(i, j);
            if (!re.isNeighbors(a0, a1) && a0.col != a1.col) {
                res.add(a1);
                return res;
            }
        }
    }

    private void move(SchellingAgent a, SchellingAgent b) {
        int t = a.col;
        a.col = b.col;
        b.col = t;
    }

    private void confirmMove(SchellingAgent[] ags) {
        ((GridPanel)panel).moveAgs(ags);
        panel.repaint();
    }

    @Override
    protected void calcG(ArrayList<Agent> ag) {
        SchellingAgent ags[] = new SchellingAgent[2];
        ags[0] = (SchellingAgent) ag.get(0);
        ags[1] = (SchellingAgent) ag.get(1);

        double u1 = ags[0].getU() + ags[1].getU();
        move(ags[0], ags[1]);
        double u2 = ags[0].getU() + ags[1].getU();
        double pr = Math.exp(u2);
        pr = pr / (pr + Math.exp(u1));
        if (r.nextDouble() < pr) {
            confirmMove(ags);
        } else {
            move(ags[0], ags[1]);
        }
    }

    @Override
    protected double calcH() {
        int k = 0;
        for (Agent ag : agents) {
            k += ((SchellingAgent) ag).getDiffNeigh();
        }
        return k / 2;//throw new UnsupportedOperationException("Not yet implemented");
    }
}
