/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package codegen;

import ASwarm.Params;
import java.util.Random;

/**
 *
 * @author zaytsevid
 */
public class Code {

    int n;// = 10;
    int x;// = 29;
    int R;// = 2;
    int cs[];
    double mut = 0.001;
    
    Random rand = new Random();

    public Code() {
        n = Integer.parseInt(Params.map.get("n"));
        x = Integer.parseInt(Params.map.get("x"));
        R = Integer.parseInt(Params.map.get("R"));
        mut = Double.parseDouble(Params.map.get("mutation"));
        cs = new int[x];
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (int c : cs) {
            int sh = 1;
            for (int i = 0; i < n; i++) {
                char ch = '0';
                if ((sh & c) != 0) {
                    ch = '1';
                }
                sh = sh << 1;
                sb.append(ch);
            }
            sb.append('\n');
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public byte[] toArray() {
        byte[] res = new byte[n * x];
        int k = 0;
        for (int c : cs) {
            int sh = 1;
            for (int i = 0; i < n; i++) {
                byte b = 0;
                if ((sh & c) != 0) {
                    b = 1;
                }
                sh <<= 1;
                res[k++] = b;
            }
        }
        return res;
    }

    void fromArray(byte[] bs) {
        int k = 0;
        for (int j = 0; j < x; j++) {
            cs[j] = 0;
            int sh = 1;
            for (int i = 0; i < n; i++) {
                if (bs[k++] == 1) {
                    cs[j] += sh;
                }
                sh <<= 1;
            }
        }
    }

    int calcF() {
        int pnum = 1 << n;
        int res = 0;
        points:
        for (int p = 0; p < pnum; p++) {
            for (int i = 0; i < x; i++) {
                if (dist(cs[i], p) <= R) {
                    continue points;
                }
            }
            res++;
        }
        return res;
    }

    public int dist(int p1, int p2) {
        int res = 0;
        int sh = 1;
        for (int i = 0; i < n; i++) {
            if ((p1 & sh) != (p2 & sh)) {
                res++;
            }
            sh <<= 1;
        }
        return res;
    }

    void generate() {
        byte bs[] = new byte[n * x];
        for (int i = 0; i < bs.length; i++) {
            bs[i] = (byte) (rand.nextBoolean() ? 1 : 0);
        }
        fromArray(bs);
    }

    void generateChild(Code c1, Code c2) {
        mut = Double.parseDouble(Params.map.get("mutation"));
        byte b1[] = c1.toArray();
        byte b2[] = c2.toArray();
        int q = rand.nextInt(n * x);
        byte bs[] = new byte[n * x];
        for (int i = 0; i < bs.length; i++) {
            bs[i] = (i < q) ? b1[i] : b2[i];
        }
        for (int i = 0; i < bs.length; i++) {
            if (rand.nextDouble() < mut) {
                bs[i] ^= 1;
            }
        }
        fromArray(bs);
    }
}
