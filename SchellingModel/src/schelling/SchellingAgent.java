/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package schelling;

import ASwarm.Agent;
import ASwarm.Params;
import ASwarm.grid.GridRelationship;


/**
 *
 * @author zaytsevid
 */
public class SchellingAgent extends Agent{
    int col;
    double Z = 4;
    double M = 2;
    int nNei = 8;
    //double u1,u2;

    void init(int i, int j, GridRelationship rel, int c) {
        Z = Double.parseDouble(Params.map.get("Z"));
        M = Double.parseDouble(Params.map.get("M"));
        r = rel;
        rel.addAgent(this,i,j);
        col = c;
    }

    double getU(){
        Agent ns[] = ((GridRelationship)r).getNeighbors(this);
        int k=0;
        for(Agent n : ns)
            if(((SchellingAgent)n).col == col)
                k++;
        if(k<=nNei/2)
            return (2*k)/nNei*Z;
        else
            return M+(Z-M)*(2*(nNei-k))/nNei;
    }

    int getDiffNeigh() {
        Agent ns[] = ((GridRelationship)r).getNeighbors(this);
        int k=0;
        for(Agent n : ns)
            if(((SchellingAgent)n).col != col)
                k++;
        return k;
    }

    int x() {
        return ((GridRelationship)r).getX(this);
    }

    int y() {
        return ((GridRelationship)r).getY(this);
    }

    void setParams() {
        Z = Double.parseDouble(Params.map.get("Z"));
        M = Double.parseDouble(Params.map.get("M"));
    }
}
