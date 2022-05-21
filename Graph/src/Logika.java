import GrafFun.Bfs;
import GrafFun.Dijkstra;
import gui.*;
import struktury.Graf;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Random;

import IO.Util;

public class Logika implements ActionListener {
    OknoGlowne o;
    PanelBoczny panelBoczny;
    PanelGraf panelGraf;
    Graf g = null;
    OknoStworzGraf o1;
    OknoWybierzPlik o2;
    String sciezka = "";

    Random r = new Random(System.currentTimeMillis());


    public Logika() {
        panelBoczny = new PanelBoczny();
        panelGraf = new PanelGraf();
        o = new OknoGlowne(panelBoczny, panelGraf);
        panelBoczny.setActionListeners(this);

        o.Show();
    }

    void akcjaPrzyciskuZapisz() {
        String pom = sciezka;
        if (sciezka.equals("")) {
            o2 = new OknoWybierzPlik(OknoWybierzPlik.WczytajZapisz.ZAPISZ);
            pom = o2.getSciezka();
        }
        try {
            Util.zapiszGraf(g, pom);
            sciezka = pom;
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    void akcjaPrzyciskuZapiszJako() {
        o2 = new OknoWybierzPlik(OknoWybierzPlik.WczytajZapisz.ZAPISZ);
        String pom = o2.getSciezka();
        if (!pom.equals(""))
            try {
                Util.zapiszGraf(g, pom);
                sciezka = pom;
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }

        System.out.println(sciezka);
    }

    void akcjaPrzyciskuWczytaj() {
        o2 = new OknoWybierzPlik(OknoWybierzPlik.WczytajZapisz.WCZYTAJ);
        String pom = o2.getSciezka();
        if (!pom.equals("")) {
            try {
                g = Util.wczytajGraf(pom);
                panelGraf.setGraf(g);
                sciezka = pom;
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    void akcjaPrzyciskuStworzGraf() {
        o1 = new OknoStworzGraf();
        o1.setActionListeners(this);
    }

    void akcjaPrzyciskuStworz() {
        g = new Graf(o1.getW(), o1.getH());
        g.stworzGraf(o1.getW_min(), o1.getW_max(), r);
        panelGraf.setGraf(g);
        o1.dispatchEvent(new WindowEvent(o1, WindowEvent.WINDOW_CLOSING));
    }

    void akcjaPrzyciskuAnuluj() {
        o1.dispatchEvent(new WindowEvent(o1, WindowEvent.WINDOW_CLOSING));
    }

    void akcjaPrzyciskuUruchomAlgorytm() {
        if (g == null)
            return;
        switch (panelBoczny.getWybrany_algorytm()) {
            case Dijkstra_alg -> {
                Dijkstra dij = new Dijkstra();
                dij.szukaj(g, 0);
                System.out.println(dij);
            }
            case Bfs_alg -> {
                Bfs bfs = new Bfs();
                bfs.szukaj(g, 0);
                System.out.println(bfs);
            }
        }
    }


    public void actionPerformed(ActionEvent e) {  //TODO obsluga eventow do innego threada?
        System.out.println(e.getActionCommand());
        switch (e.getActionCommand()) {
            case "Zapisz" -> akcjaPrzyciskuZapisz();
            case "Zapisz Jako" -> akcjaPrzyciskuZapiszJako();
            case "Wczytaj" -> akcjaPrzyciskuWczytaj();
            case "Stwórz Graf" -> akcjaPrzyciskuStworzGraf();
            case "Stwórz" -> akcjaPrzyciskuStworz();
            case "Anuluj" -> akcjaPrzyciskuAnuluj();
            case "Uruchom algorytm" -> akcjaPrzyciskuUruchomAlgorytm();
        }
    }
}
