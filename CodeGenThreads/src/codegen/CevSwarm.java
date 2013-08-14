/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package codegen;

import ASwarm.ASwarm;
import ASwarm.Agent;
import ASwarm.Parallel.ParSwarm;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author zaytsevid
 */
public class CevSwarm extends ParSwarm {

    int N = 10;//population
    int fs[] = new int[N];
    int sum, min;
    int steps = 0;
    Random rand = new Random();
    int best = Integer.MAX_VALUE;
    JTextArea tf = new JTextArea();
    Code bestc = null, curc = null;

    public CevSwarm() {
        panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(tf);

        for (int i = 0; i < N; i++) {
            CevAgent a = new CevAgent(this);
            a.generateCode();
            agents.add(a);
        }
    }

    @Override
    protected void calcG1(ArrayList<Agent> ags) {
        min = Integer.MAX_VALUE;
        int max = 0;
        sum = 0;
        for (int i = 0; i < N; i++) {
            CevAgent a = (CevAgent) agents.get(i);
            fs[i] = a.f;
            if (fs[i] == 0) {
                System.out.println("Eurika!");
                System.out.println(a.code.toString());
                controller.pause();
                JOptionPane.showMessageDialog(panel, "Eurika!");
                min = 0;
                curc = a.code;
                break;
                //System.exit(0);
            }
            if (fs[i] < min) {
                min = fs[i];
                curc = a.code;
            }
            if (fs[i] > max) {
                max = fs[i];
            }
        }
        //тк нам нужен макс а не мин
        for (int i = 0; i < N; i++) {
            fs[i] = max - fs[i] + 1;
            sum += fs[i];
        }

        Code chs[] = new Code[N];
        //chs[0] = new Code();
        //chs[0].generate();
        for (int i = 0; i < N; i++) {
            int p1 = getI();
            int p2 = getI();
            chs[i] = new Code();
            chs[i].generateChild(((CevAgent) agents.get(p1)).code, ((CevAgent) agents.get(p2)).code);
        }
        for (int i = 0; i < N; i++) {
            ((CevAgent) agents.get(i)).code = chs[i];
        }
        if (min < best) {
            bestc = curc;
            best = min;
            tf.setText("" + best + "\n" + bestc.toString());
            panel.repaint();
        }
        regenerate();
    }

    @Override
    protected ArrayList<Agent> selectAgents() {
        return agents;
    }

    @Override
    protected double calcH() {
        return min;
    }

    private int getI() {
        int q = rand.nextInt(sum);

        for (int i = 0; i < N; i++) {
            if (q < fs[i]) {
                return i;
            } else {
                q -= fs[i];
            }
        }
        return 0;
    }

    private void regenerate() {
        if (globals.time == 0 )
            return;
        if (globals.time % 100000 == 0) {
            steps = 0;
            for (int i = 0; i < N; i++) {
                CevAgent a = (CevAgent) agents.get(i);
                if (rand.nextBoolean()) {
                    a.code.generate();
                }
            }
        }
        /*if (globals.time % 1000000 == 0) {
            steps = 0;
            for (int i = 0; i < N; i++) {
                CevAgent a = (CevAgent) agents.get(i);
                a.code.generate();
            }
        }*/
    }
}
