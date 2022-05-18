import GrafFun.Bfs;
import GrafFun.Dijkstra;
import gui.OknoGlowne;
import gui.OknoStworzGraf;
import gui.OknoWybierzPlik;
import gui.PanelBoczny;
import struktury.Graf;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Random;

import IO.Util;

public class Logika implements ActionListener {
    OknoGlowne o;
    PanelBoczny p;
    Graf g = null;
    OknoStworzGraf o1;
    OknoWybierzPlik o2;
    String sciezka = "";

    Random r = new Random(System.currentTimeMillis());


    public Logika() {
        p = new PanelBoczny();
        o = new OknoGlowne(p);
        p.setActionListeners(this);

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
                System.out.println(g);
                sciezka = pom;
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        System.out.println(sciezka);
    }

    void akcjaPrzyciskuStworzGraf() {
        o1 = new OknoStworzGraf();
        o1.setActionListeners(this);
    }

    void akcjaPrzyciskuStworz() {
        g = new Graf(o1.getW(), o1.getH());
        g.stworzGraf(o1.getW_min(), o1.getW_max(), r);
        o1.dispatchEvent(new WindowEvent(o1, WindowEvent.WINDOW_CLOSING));
    }

    void akcjaPrzyciskuAnuluj() {
        o1.dispatchEvent(new WindowEvent(o1, WindowEvent.WINDOW_CLOSING));
    }

    void akcjaPrzyciskuUruchomAlgorytm() {
        if (g == null)
            return;
        switch (p.getWybrany_algorytm()) {
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
