package gui;

import struktury.Graf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class PanelGraf extends JPanel {
    int wybranyWezel = 0;
    Dimension wymiary;
    Graf g = null, alg_g = null;
    NR_ALG nr_alg = NR_ALG.NR_ALG_NONE;
    private int dl_krawedzi = 8;
    int R;
    int[] BFS_zwiedzone = null;
    double[] Dijkstra_droga = null;
    BufferedImage grafImage = null;
    Graphics2D grafImageG2D = null;


    enum NR_ALG {
        NR_ALG_NONE,
        NR_ALG_BFS,
        NR_ALG_DIJKSTRA
    }


    public PanelGraf() {
        setLayout(null);
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (g == null) return;
                int x = e.getX() / (3 * R), y = (e.getY() / (3 * R));
                if (x >= g.getW() || y >= g.getH()) return;
                int nr = x + g.getW() * y;
                if (nr > g.size()) return;
                if (Point2D.distance(x * 3 * R + R, y * 3 * R + R, e.getX(), e.getY()) < R) {
                    wybranyWezel = nr;
                    switch (nr_alg) {
                        case NR_ALG_NONE -> narysujGraf();
                        case NR_ALG_BFS -> narysujGrafBFS();
                        case NR_ALG_DIJKSTRA -> narysujGrafDijkstra();
                    }
                    repaint();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        wymiary = new Dimension(getWidth(), getHeight());
        setPreferredSize(wymiary);
        setVisible(true);
    }


    private int getPozXWezla(int nr, int w) {
        return (nr % w) * (R * 2 + dl_krawedzi) + R;
    }

    private int getPozYWezla(int nr, int w) {
        return (nr / w) * (R * 2 + dl_krawedzi) + R;
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(grafImage, null, 0, 0);
    }

    private void drawArrowLine(Graphics2D g, int x1, int y1, int x2, int y2, int d, int h) {

        double x2_save = x2, y2_save = y2;
        double dlVec2 = (x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1);
        if (x1 == x2)
            y2 -= (R - 2);
        else {
            x2 -= (x2 - x1) * (R - 2) / Math.sqrt(dlVec2);
            y2 -= (y2 - y1) * (R - 2) / Math.sqrt(dlVec2);
        }
        int dx = x2 - x1, dy = y2 - y1;
        double D = Math.sqrt(dx * dx + dy * dy);
        double xm = D - d, xn = xm, ym = h, yn = -h, x;
        double sin = dy / D, cos = dx / D;

        x = xm * cos - ym * sin + x1;
        ym = xm * sin + ym * cos + y1;
        xm = x;

        x = xn * cos - yn * sin + x1;
        yn = xn * sin + yn * cos + y1;
        xn = x;

        int[] xpoints = {x2, (int) xm, (int) xn};
        int[] ypoints = {y2, (int) ym, (int) yn};

        g.fillPolygon(xpoints, ypoints, 3);
        if (x1 == x2)
            y2 -= d;
        else {
            x2 -= (x2_save - x1) * d / Math.sqrt(dlVec2);
            y2 -= (y2_save - y1) * d / Math.sqrt(dlVec2);
        }
        g.drawLine(x1, y1, x2, y2);
    }

    public void narysujGraf() {
        if (this.g == null) return;
        int wezlow = g.size();
        dl_krawedzi = R;
        int grK = 4;  //grK-grubosc krawedzi

        grafImageG2D.setColor(new Color(255, 255, 150));
        grafImageG2D.fillRect(0, 0, grafImage.getWidth(), grafImage.getHeight());
        grafImageG2D.setStroke(new BasicStroke(grK));

        double minWaga = g.getMinWaga(), maxWaga = g.getMaxWaga();
        for (int x = 0; x < wezlow; x++) {
            for (int y = 0; y < g.krawedzie[x].size(); y++) {
                int wierzDo = g.krawedzie[x].Do[y];
                Boolean istniejeOdwrotna = g.istniejeOdwrotna(x, wierzDo);
                if (wierzDo > x) {
                    int pom = (int) ((g.krawedzie[x].wagi[y] - minWaga) * 255 / (maxWaga - minWaga));
                    grafImageG2D.setColor(new Color(pom, pom, pom));

                    //Krawedz skierowana
                    if (!istniejeOdwrotna)
                        drawArrowLine(grafImageG2D, getPozXWezla(x, g.getW()), getPozYWezla(x, g.getW()), getPozXWezla(wierzDo, g.getW()), getPozYWezla(wierzDo, g.getW()), R / 2, Math.max(R / 4, grK * 2));
                    else
                        grafImageG2D.drawLine(getPozXWezla(x, g.getW()), getPozYWezla(x, g.getW()), getPozXWezla(wierzDo, g.getW()), getPozYWezla(wierzDo, g.getW()));

                } else {
                    if (istniejeOdwrotna)
                        continue;
                    int pom = (int) ((g.krawedzie[x].wagi[y] - minWaga) * 255 / (maxWaga - minWaga));
                    grafImageG2D.setColor(new Color(pom, pom, pom));
                    drawArrowLine(grafImageG2D, getPozXWezla(x, g.getW()), getPozYWezla(x, g.getW()), getPozXWezla(wierzDo, g.getW()), getPozYWezla(wierzDo, g.getW()), R / 2, Math.max(R / 4, grK * 2));
                }
            }
        }

        Ellipse2D.Double kolo = new Ellipse2D.Double(0, 0, 2 * R, 2 * R);
        grafImageG2D.setColor(Color.BLACK);
        for (int x = 0; x < wezlow; x++) {
            kolo.setFrame(x % g.getW() * (R * 2 + dl_krawedzi), ((double) (x / g.getW()) * (R * 2 + dl_krawedzi)), R * 2, R * 2);
            grafImageG2D.fill(kolo);
        }
        grafImageG2D.setColor(Color.BLUE);
        kolo.setFrame(wybranyWezel % g.getW() * (R * 2 + dl_krawedzi), ((double) (wybranyWezel / g.getW()) * (R * 2 + dl_krawedzi)), 2 * R, 2 * R);
        grafImageG2D.fill(kolo);
        revalidate();
        updateUI();
    }

    public void narysujGrafBFS() {
        if (this.g == null || this.alg_g == null) return;
        int wezlowAlgorytm = alg_g.size(), wezlowGraf = g.size();
        dl_krawedzi = R;
        int grK = 4;  //grK-grubosc krawedzi
        grafImageG2D.setStroke(new BasicStroke(grK));
        grafImageG2D.setColor(new Color(255, 255, 150));
        grafImageG2D.fillRect(0, 0, grafImage.getWidth(), grafImage.getHeight());

        Color kolor_odwiedzone = new Color(0, 155, 0);
        for (int x = 0; x < wezlowAlgorytm; x++) {
            for (int y = 0; y < alg_g.krawedzie[x].size(); y++) {
                int wierzDo = alg_g.krawedzie[x].Do[y];
                Boolean istniejeOdwrotna = alg_g.istniejeOdwrotna(x, wierzDo);
                grafImageG2D.setColor(Color.GRAY);
                if (wierzDo > x) {
                    for (int z = 0; z < alg_g.krawedzie[x].size(); z++)
                        if (alg_g.krawedzie[x].Do[z] == wierzDo) {
                            grafImageG2D.setColor(kolor_odwiedzone);
                            break;
                        }
                    if (!istniejeOdwrotna)
                        drawArrowLine(grafImageG2D, getPozXWezla(x, alg_g.getW()), getPozYWezla(x, alg_g.getW()), getPozXWezla(wierzDo, alg_g.getW()), getPozYWezla(wierzDo, alg_g.getW()), R / 2, Math.max(R / 4, grK * 2));
                    else
                        grafImageG2D.drawLine(getPozXWezla(x, alg_g.getW()), getPozYWezla(x, alg_g.getW()), getPozXWezla(wierzDo, alg_g.getW()), getPozYWezla(wierzDo, alg_g.getW()));

                } else {
                    if (istniejeOdwrotna)
                        continue;
                    drawArrowLine(grafImageG2D, getPozXWezla(x, alg_g.getW()), getPozYWezla(x, alg_g.getW()), getPozXWezla(wierzDo, alg_g.getW()), getPozYWezla(wierzDo, alg_g.getW()), R / 2, Math.max(R / 4, grK * 2));
                }
            }
        }

        Ellipse2D.Double kolo = new Ellipse2D.Double(0, 0, 2 * R, 2 * R);
        for (int x = 0; x < wezlowGraf; x++) {
            kolo.setFrame(x % g.getW() * (R * 2 + dl_krawedzi), ((double) (x / g.getW()) * (R * 2 + dl_krawedzi)), R * 2, R * 2);
            if (BFS_zwiedzone[x] == 2)
                grafImageG2D.setColor(kolor_odwiedzone);
            else
                grafImageG2D.setColor(Color.BLACK);
            grafImageG2D.fill(kolo);
        }
        grafImageG2D.setColor(Color.BLUE);
        kolo.setFrame(wybranyWezel % alg_g.getW() * (R * 2 + dl_krawedzi), ((double) (wybranyWezel / alg_g.getW()) * (R * 2 + dl_krawedzi)), 2 * R, 2 * R);
        grafImageG2D.fill(kolo);
        revalidate();
        updateUI();
    }


    public void narysujGrafDijkstra() {
        if (this.alg_g == null) return;
        int wezlowGraf = alg_g.size(), wezlowAlgorytm = g.size();
        dl_krawedzi = R;
        int grK = 4;  //grK-grubosc krawedzi
        grafImageG2D.setStroke(new BasicStroke(grK));
        grafImageG2D.setColor(new Color(255, 255, 150));
        grafImageG2D.fillRect(0, 0, grafImage.getWidth(), grafImage.getHeight());
        double minWaga = alg_g.getMinWaga(), maxWaga = alg_g.getMaxWaga();

        for (int x = 0; x < wezlowAlgorytm; x++) {
            for (int y = 0; y < alg_g.krawedzie[x].size(); y++) {
                int wierzDo = alg_g.krawedzie[x].Do[y];
                Boolean istniejeOdwrotna = alg_g.istniejeOdwrotna(x, wierzDo);
                if (wierzDo > x) {
                    int pom = (int) ((alg_g.krawedzie[x].wagi[y] - minWaga) * 255 / (maxWaga - minWaga));
                    grafImageG2D.setColor(new Color(pom, 0, 0));
                    grafImageG2D.drawLine(getPozXWezla(x, alg_g.getW()), getPozYWezla(x, alg_g.getW()), getPozXWezla(wierzDo, alg_g.getW()), getPozYWezla(wierzDo, alg_g.getW()));

                    //Krawedz skierowana
                    if (!istniejeOdwrotna)
                        drawArrowLine(grafImageG2D, getPozXWezla(x, alg_g.getW()), getPozYWezla(x, alg_g.getW()), getPozXWezla(wierzDo, alg_g.getW()), getPozYWezla(wierzDo, alg_g.getW()), R / 2, Math.max(R / 4, grK * 2));
                    else
                        grafImageG2D.drawLine(getPozXWezla(x, alg_g.getW()), getPozYWezla(x, alg_g.getW()), getPozXWezla(wierzDo, alg_g.getW()), getPozYWezla(wierzDo, alg_g.getW()));
                } else {
                    if (istniejeOdwrotna)
                        continue;
                    int pom = (int) ((alg_g.krawedzie[x].wagi[y] - minWaga) * 255 / (maxWaga - minWaga));
                    grafImageG2D.setColor(new Color(pom, 0, 0));
                    drawArrowLine(grafImageG2D, getPozXWezla(x, alg_g.getW()), getPozYWezla(x, alg_g.getW()), getPozXWezla(wierzDo, alg_g.getW()), getPozYWezla(wierzDo, alg_g.getW()), R / 2, Math.max(R / 4, grK * 2));

                }
            }
        }

        Ellipse2D.Double kolo = new Ellipse2D.Double(0, 0, 2 * R, 2 * R);
        for (int x = 0; x < wezlowGraf; x++) {
            if (Dijkstra_droga[x] >= Double.POSITIVE_INFINITY)
                grafImageG2D.setColor(Color.BLACK);
            else {
                int pom = (int) ((Dijkstra_droga[x]) * 255 / (maxWaga));
                grafImageG2D.setColor(new Color(pom, 0, 0));
            }
            kolo.setFrame(x % g.getW() * (R * 2 + dl_krawedzi), ((double) (x / g.getW()) * (R * 2 + dl_krawedzi)), R * 2, R * 2);
            grafImageG2D.fill(kolo);
        }
        grafImageG2D.setColor(Color.BLUE);
        kolo.setFrame(wybranyWezel % alg_g.getW() * (R * 2 + dl_krawedzi), ((double) (wybranyWezel / alg_g.getW()) * (R * 2 + dl_krawedzi)), 2 * R, 2 * R);
        grafImageG2D.fill(kolo);
        revalidate();
        updateUI();
    }


    void setWymiary(int w, int h) {
        R = Math.min(w / (3 * g.getW()), h / (3 * g.getH()));
        if (R > 25) R = 25;
        if (R < 10) R = 10;
        wymiary.width = g.getW() * R * 3 + 8;
        wymiary.height = g.getH() * R * 3 + 8;
        grafImage = new BufferedImage(wymiary.width, wymiary.height, BufferedImage.TYPE_INT_RGB);
        grafImageG2D = grafImage.createGraphics();
        setPreferredSize(wymiary);
    }

    public void setGraf(Graf g, int w, int h) {
        this.g = g;
        setWymiary(w, h);
        BFS_zwiedzone = null;
        Dijkstra_droga = null;
        nr_alg = NR_ALG.NR_ALG_NONE;
        narysujGraf();
        this.repaint();
    }

    public void setGrafBFS(Graf g, int[] zwiedzone, int w, int h) {
        alg_g = g;
        setWymiary(w, h);
        BFS_zwiedzone = zwiedzone;
        Dijkstra_droga = null;
        nr_alg = NR_ALG.NR_ALG_BFS;
        narysujGrafBFS();
        repaint();
    }

    public void setGrafDijkstra(Graf g, double[] droga, int w, int h) {
        alg_g = g;
        setWymiary(w, h);
        BFS_zwiedzone = null;
        Dijkstra_droga = droga;
        nr_alg = NR_ALG.NR_ALG_DIJKSTRA;
        narysujGrafDijkstra();
        repaint();
    }

    public int getWybranyWezel() {
        return wybranyWezel;
    }


    @Override
    public Dimension getPreferredSize() {
        return wymiary;
    }
}
