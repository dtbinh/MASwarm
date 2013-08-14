/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ASwarm.grid;

import ASwarm.Agent;
import ASwarm.Relationship;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author zaytsevid
 */
public class GridRelationship extends Relationship {

    protected class GridAgent{
        public int x;
        public int y;
        public Agent ag;
    }

    protected final GridAgent[][] table;
    protected int N;
    protected Map map = new HashMap();

     public void addAgent(Agent ag, int i, int j) {
        GridAgent ga = new GridAgent();
        ga.ag = ag;
        ga.x = i;
        ga.y = j;
        table[i][j] = ga;
        map.put(ag, ga);
    }

    public GridRelationship(int N) {
        this.N = N;
        table = new GridAgent[N][N];
    }

    public Agent getAgent(int i, int j) {
        return table[i][j].ag;
    }

    public boolean isNeighbors(Agent a, Agent b) {
        return isNeighbors((GridAgent)map.get(a), (GridAgent)map.get(b));
    }

    protected boolean isNeighbors(GridAgent a, GridAgent b) {
        int i = Math.abs(a.x - b.x);
        int j = Math.abs(a.y - b.y);
        if (i == N - 1) {
            i = 1;
        }
        if (j == N - 1) {
            j = 1;
        }
        if (i <= 1 && j <= 1) {
            return true;
        } else {
            return false;
        }
    }

    public Agent[] getNeighbors(Agent ag) {
        return getNeighbors((GridAgent)map.get(ag));
    }

    protected Agent[] getNeighbors(GridAgent ag) {
        int i = ag.x;
        int j = ag.y;
        Agent res[] = new Agent[8];
        int k = 0;
        for (int di = -1; di <= 1; di++) {
            for (int dj = -1; dj <= 1; dj++) {
                if ((di == 0) && (dj == 0)) {
                    continue;
                }
                int i1 = (i + di + N) % N;
                int j1 = (j + dj + N) % N;
                res[k++] = table[i1][j1].ag;
            }
        }
        return res;
    }

    public int getX(Agent a){
        return ((GridAgent)map.get(a)).x;
    }

    public int getY(Agent a){
        return ((GridAgent)map.get(a)).y;
    }
}
