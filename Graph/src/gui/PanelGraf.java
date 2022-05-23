package gui;

import struktury.Graf;
import struktury.TD_Przyciskow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelGraf extends JPanel implements ActionListener {
    TD_Przyciskow[] przyciskiKrawedzie;
    PrzyciskWezel[] przyciskiWezly;
    Dimension wymiary;
    Graf g = null;
    private int dl_krawedzi = 8;
    int R;

    public PanelGraf() {
        wymiary = new Dimension(getWidth(), getHeight());
    }


    public Graf getG() {
        return g;
    }

    private int getPozXWezla(int nr, int w) {
        return (nr % w) * (R * 2 + dl_krawedzi) + R;
    }

    private int getPozYWezla(int nr, int w) {
        return (nr / w) * (R * 2 + dl_krawedzi) + R;
    }

    public void setGraf(Graf g) {
        System.out.println(getWidth());
        removeAll();
        this.g = g;
        R = (getWidth() - (g.getW() - 1) * 2) / (g.getW());
        if (R > 50) R = 50;
        if (R < 20) R = 20;
        wymiary.width = g.getW() * R * 2 + 8;
        wymiary.height = g.getH() * R * 2 + 8;
        System.out.println(g.size());
        przyciskiWezly = new PrzyciskWezel[g.size()];
        przyciskiKrawedzie = new TD_Przyciskow[g.size()];

        dl_krawedzi = R;
        for (int x = 0; x < przyciskiKrawedzie.length; x++)
            przyciskiKrawedzie[x] = new TD_Przyciskow();
        for (int x = 0; x < g.size(); x++) {
            przyciskiWezly[x] = new PrzyciskWezel();
            przyciskiWezly[x].setBounds(x % g.getW() * (R * 2 + dl_krawedzi), (x / g.getW()) * (R * 2 + dl_krawedzi), R * 2, R * 2);
            add(przyciskiWezly[x]);
        }

        for (int x = 0; x < g.krawedzie.length; x++) {
            for (int y = 0; y < g.krawedzie[x].size(); y++) {
                int wierzDo = g.krawedzie[x].Do[y];
                if (wierzDo > x) {
                    przyciskiKrawedzie[x].dodajKrawedz(getPozXWezla(x, g.getW()), getPozYWezla(x, g.getW()), getPozXWezla(wierzDo, g.getW()), getPozYWezla(wierzDo, g.getW()), 4);
                    int pom = (int) ((g.krawedzie[x].wagi[y] - g.getMinWaga()) * 255 / (g.getMaxWaga() - g.getMinWaga()));
                    przyciskiKrawedzie[x].przyciski[przyciskiKrawedzie[x].size() - 1].setKolorDomyslny(new Color(pom, pom, pom));
                    add(przyciskiKrawedzie[x].przyciski[przyciskiKrawedzie[x].size() - 1]);
                } else {
                    int pom = 1;
                    for (int z = 0; z < g.krawedzie[wierzDo].size(); z++) {
                        if (g.krawedzie[wierzDo].Do[z] == x) {
                            przyciskiKrawedzie[x].dodajKrawedz(przyciskiKrawedzie[wierzDo].przyciski[z]);
                            pom = 0;
                            break;
                        }
                    }
                    if (pom == 1) {
                        przyciskiKrawedzie[x].dodajKrawedz(getPozXWezla(x, g.getW()), getPozYWezla(x, g.getW()), getPozXWezla(wierzDo, g.getW()), getPozYWezla(wierzDo, g.getW()), 4);
                        add(przyciskiKrawedzie[x].przyciski[przyciskiKrawedzie[x].size() - 1]);
                    }
                }
            }
        }
        revalidate();
        setLayout(null);
        updateUI();
    }

    public void setKoloryBFS(Graf g) {
        if (this.g.size() != g.size()) {
            //TODO custom exception
            return;
        }

        for (int x = 0; x < g.size(); x++)
            for (int y = 0; y < this.g.krawedzie[x].size(); y++)
                przyciskiKrawedzie[x].przyciski[y].setKolor(Color.WHITE);
        for (int x = 0; x < g.size(); x++) {
            //Dostosowywanie koloru do drogi
            for (int y = 0; y < g.krawedzie[x].size(); y++) {
                //przyciskiKrawedzie[x].przyciski[y].setKolor(Color.WHITE);  Nie mam pojęcia dlaczego to nie działa
                for (int z = 0; z < this.g.krawedzie[x].size(); z++) {
                    if (this.g.krawedzie[x].Do[z] == g.krawedzie[x].Do[y]) {
                        Color pom1 = new Color(0, 255, 0);
                        przyciskiKrawedzie[x].przyciski[z].setKolor(pom1);
                        przyciskiWezly[g.krawedzie[x].Do[y]].setKolor(pom1);
                    }
                }
            }
        }
        revalidate();
        updateUI();
    }


    public void setKoloryDijkstra(Graf g) {
        if (this.g.size() != g.size()) {
            //TODO custom exception
            return;
        }
        double pom_min = g.getMinWaga(), pom_max = g.getMaxWaga();
        double wsp = 255 / (pom_max - pom_min);

        //hacky solution
        for (int x = 0; x < g.size(); x++)
            for (int y = 0; y < this.g.krawedzie[x].size(); y++)
                przyciskiKrawedzie[x].przyciski[y].setKolor(Color.WHITE);

        for (int x = 0; x < g.size(); x++) {
            //Dostosowywanie koloru do drogi
            for (int y = 0; y < g.krawedzie[x].size(); y++) {
                //przyciskiKrawedzie[x].przyciski[y].setKolor(Color.WHITE);  Nie mam pojęcia dlaczego to nie działa
                int pom = (int) ((g.krawedzie[x].wagi[y] - pom_min) * wsp);
                for (int z = 0; z < this.g.krawedzie[x].size(); z++) {
                    if (this.g.krawedzie[x].Do[z] == g.krawedzie[x].Do[y]) {
                        Color pom1 = new Color(pom, 0, 0);
                        przyciskiKrawedzie[x].przyciski[z].setKolor(pom1);
                        przyciskiWezly[g.krawedzie[x].Do[y]].setKolor(pom1);
                    }
                }
            }
        }
        revalidate();
        updateUI();
    }

    @Override
    public Dimension getPreferredSize() {
        return wymiary;
    }

    public void actionPerformed(ActionEvent e) {

    }
}
