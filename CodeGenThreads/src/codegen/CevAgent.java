/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package codegen;

import ASwarm.Parallel.ParAgent;

/**
 *
 * @author zaytsevid
 */
public class CevAgent extends ParAgent{
    Code code;
    int f;

    public CevAgent(CevSwarm m) {
        super(m);
    }
    
    void generateCode() {
        code = new Code();
        code.generate();
    }

    @Override
    protected void calcF() {
        f = code.calcF();
    }

}
