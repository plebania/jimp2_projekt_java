import GrafFun.Bfs;
import GrafFun.Dijkstra;
import gui.*;
import struktury.Graf;

import java.awt.event.*;
import java.io.IOException;
import java.util.Random;

import IO.Util;

import javax.swing.*;

public class Logika implements ActionListener {
    OknoGlowne o;
    PanelBoczny panelBoczny;
    PanelGraf panelGraf;
    Graf g = null;
    OknoStworzGraf o1;
    OknoWybierzPlik o2;
    String sciezka = "";

    Random r = new Random(System.currentTimeMillis());

    private class AkcjaZapiszJako extends AbstractAction {
        AkcjaZapiszJako() {
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Zapisz Jako");
            o2 = new OknoWybierzPlik(OknoWybierzPlik.WczytajZapisz.ZAPISZ);
            String pom = o2.getSciezka();
            if (!pom.equals(""))
                try {
                    Util.zapiszGraf(g, pom);
                    sciezka = pom;
                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }
        }
    }

    private class AkcjaZapisz extends AbstractAction {

        AkcjaZapisz() {
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("zapisz");
            String pom = sciezka;
            if (sciezka.equals("")) {
                o2 = new OknoWybierzPlik(OknoWybierzPlik.WczytajZapisz.ZAPISZ);
                pom = o2.getSciezka();
                if (pom.equals("")) return;
            }
            try {
                Util.zapiszGraf(g, pom);
                sciezka = pom;
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    private class AkcjaWczytaj extends AbstractAction {

        AkcjaWczytaj() {
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Wczytaj");
            o2 = new OknoWybierzPlik(OknoWybierzPlik.WczytajZapisz.WCZYTAJ);
            String pom = o2.getSciezka();
            if (!pom.equals("")) {
                try {
                    g = Util.wczytajGraf(pom);
                    panelGraf.setGraf(g);
                    sciezka = pom;
                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
    }

    private class AkcjaUruchomAlgorytm extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("uruchom");
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
    }

    private class AkcjaStworzGraf extends AbstractAction {

        AkcjaStworzGraf() {
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Stworz Graf");
            o1 = new OknoStworzGraf();
            o1.setActionListeners(Logika.this);
        }
    }

    public Logika() {
        panelBoczny = new PanelBoczny();
        panelGraf = new PanelGraf();
        o = new OknoGlowne(panelBoczny, panelGraf);
        panelBoczny.setActions(new AkcjaZapiszJako(), new AkcjaZapisz(), new AkcjaWczytaj(), new AkcjaStworzGraf(), new AkcjaUruchomAlgorytm());

        o.Show();
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


    public void actionPerformed(ActionEvent e) {  //TODO obsluga eventow do innego threada?
        System.out.println(e.getActionCommand());
        switch (e.getActionCommand()) {
            case "Stwórz" -> akcjaPrzyciskuStworz();
            case "Anuluj" -> akcjaPrzyciskuAnuluj();
        }
    }
}
