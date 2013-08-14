/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package codegen;

import java.util.Random;

/**
 *
 * @author zaytsevid
 */
public class Population {

    int N = 10;
    Code cs[] = new Code[N];
    int fs[] = new int[N];
    int sum, min;
    Random rand = new Random();

    public Population() {
    }

    void generate() {
        for (int i = 0; i < N; i++) {
            cs[i] = new Code();
            cs[i].generate();
        }
    }

    int calcH() {
        min = Integer.MAX_VALUE;
        int max = 0;
        sum = 0;
        for (int i = 0; i < N; i++) {
            fs[i] = cs[i].calcF();
            if (fs[i] == 0) {
                System.out.println("Eurika!");
                System.out.println(cs[i].toString());
                System.exit(0);
            }
            if (fs[i] < min) {
                min = fs[i];
            }
            if (fs[i] > max) {
                max = fs[i];
            }
        }
        //тк нам нужен макс а не мин
        for (int i = 0; i < N; i++) {
            fs[i]=max-fs[i]+1;
            sum += fs[i];
        }


        return min;
    }

    void multiply() {
        //double roulette[] = new double[N];
        //int sum=0;
        Code chs[] = new Code[N];
        //chs[0] = new Code();
        //chs[0].generate();
        for (int i = 0; i < N; i++) {
            int p1 = getI();
            int p2 = getI();
            chs[i] = new Code();
            chs[i].generateChild(cs[p1], cs[p2]);
        }
        cs = chs;
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
}

