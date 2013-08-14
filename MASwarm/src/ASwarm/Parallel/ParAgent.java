/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ASwarm.Parallel;

import ASwarm.Agent;

/**
 *
 * @author zaytsevid
 */
public abstract class ParAgent extends Agent {

    final ParSwarm ps;
    private final MyThread thread;

    public ParAgent(ParSwarm s) {
        ps = s;
        thread = new MyThread(this);
        thread.start();
    }

    private void doStep() {
        calcF();
        synchronized (ps) {
            ps.nums--;
            ps.notifyAll();
        }
    }

    protected abstract void calcF();

    void setRunning() {
        thread.setRunning();
    }

    void interrupt() {
        thread.interrupt();
    }

    private class MyThread extends Thread {

        private final ParAgent ag;
        private boolean running = false;

        private MyThread(ParAgent aThis) {
            ag = aThis;
        }

        @Override
        public void run() {
            while(true){
            synchronized (this) {
                while (!running) {
                    try {
                        this.wait();
                    } catch (InterruptedException ex) {
                        return;
                    }
                }
            
            ag.doStep();
            //synchronized (this) {
                running = false;
                this.notifyAll();
            }
            }
        }

        private void setRunning() {
            synchronized (this) {
                running = true;
                this.notifyAll();
            }
        }
    }
}
