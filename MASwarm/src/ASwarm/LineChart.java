/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ASwarm;

import java.awt.Color;
import java.awt.Graphics;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author Ivan
 */
public class LineChart extends JPanel {

    void empty() {
        rows.clear();
        bounds[0] = 1;
        bounds[1] = 1;
        dx = 1;
        xmax = 1;
    }

    void saveTo(File f) throws Exception {
        DataOutputStream os = new DataOutputStream(new FileOutputStream(f));
        List ms = rows.get(0).meanings;
        for(int i=0;i<ms.size();i++){
            os.writeBytes(""+i+";"+ms.get(i).toString().replace(".", ",")+"\r\n");
        }
        os.close();
    }

    class ChartRow {
        protected String name;
        protected Color color;
        protected List<Double> meanings = new ArrayList<Double>();
    }
    private List<ChartRow> rows = new ArrayList<LineChart.ChartRow>();
    private double[] bounds = new double[2];
    public double dx = 1;
    public int xmax = 1;
    private final int width = 400;
    private final int height = 300;

    public LineChart() {
        bounds[0] = 1;
        bounds[1] = 1;//0;
        setVisible(true);
    }

    public void addRow(String str, Color c) {
        ChartRow tmp = new ChartRow();
        tmp.name = str;
        tmp.color = c;
        rows.add(tmp);
    }

    public void addMeaning(int i, double d) {
        ChartRow r = rows.get(i);
        r.meanings.add(d);

        if (xmax < r.meanings.size()-1);
        xmax = r.meanings.size()-1;

        int q = 0;
        if (d < 0) {
            d *= -1;
            q = 1;
        }
        if (bounds[q] >= d) {
            return;
        }
        if (d < 1) {
            bounds[q] = 1;
        } else {
            double start = 2.5 * Math.pow(10, (int) Math.log10(d));
            double step = start;
            while (start < d) {
                start += step;
            }
            bounds[q] = start;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        
        super.paintComponent(g);

       setSize(width, height);
        setBackground(Color.WHITE);
        int zeroLine = height - (int) (bounds[1] * height / (bounds[0] + bounds[1]));

        g.setColor(Color.black);
        g.drawLine(0, zeroLine, width, zeroLine);
        g.drawString(String.valueOf(bounds[0]), 0, 10);
        g.drawString(String.valueOf(-bounds[1]), 0, height);
        g.drawString("0", 0, zeroLine - 1);
        g.drawString(String.valueOf(xmax), width - 10 * (int) (Math.log10(xmax) + 1), zeroLine - 1);

        double xc = ((double) width) / xmax;
        double yc = ((double) height) / (bounds[0] + bounds[1]);
 
        for (int i = 0; i < rows.size(); i++) {
            ChartRow r = rows.get(i);
            int x = 1;
            g.setColor(r.color);
            while (r.meanings.size() > x) {
                int x1 = (int) (xc * (x - 1));
                int y1 = (int) (zeroLine - yc * r.meanings.get(x - 1));
                int x2 = (int) (xc * x);
                int y2 = (int) (zeroLine - yc * r.meanings.get(x));
                g.drawLine(x1, y1, x2, y2);
                x++;
            }
        }
    }

}
