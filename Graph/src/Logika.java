import GrafFun.Bfs;
import GrafFun.Dijkstra;
import Problemy.Util;
import gui.*;
import struktury.Graf;

import java.awt.event.*;
import java.io.IOException;
import java.util.Random;

import javax.swing.*;

public class Logika implements ActionListener {
    OknoGlowne o;
    PanelBoczny panelBoczny;
    PanelGraf panelGraf;
    Graf g = null;
    OknoStworzGraf o1;
    OknoWybierzPlik o2;
    String sciezka = "";
    JScrollPane panelGrafScroll;

    Random r = new Random(System.currentTimeMillis());

    public Logika() {
        panelBoczny = new PanelBoczny();
        panelGraf = new PanelGraf();
        panelGrafScroll = new JScrollPane(panelGraf);
        o = new OknoGlowne(panelBoczny, panelGrafScroll);
        panelBoczny.setActions(new AkcjaZapiszJako(), new AkcjaZapisz(), new AkcjaWczytaj(), new AkcjaStworzGraf(), new AkcjaUruchomAlgorytm());
        o.Show();
    }

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
                    IO.Util.zapiszGraf(g, pom);
                    sciezka = pom;
                } catch (IOException ex) {
                    Problemy.Util.createPopup(ex.getMessage());
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
                IO.Util.zapiszGraf(g, pom);
                sciezka = pom;
            } catch (IOException ex) {
                Problemy.Util.createPopup(ex.getMessage());
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
                    g = IO.Util.wczytajGraf(pom);
                    panelGraf.setGraf(g, panelGrafScroll.getWidth(), panelGrafScroll.getHeight());
                    sciezka = pom;
                } catch (Exception ex) {
                    Problemy.Util.createPopup("Problem z wczytaniem pliku\nkomunikat błędu: " + ex.getMessage());
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
            try {
                switch (panelBoczny.getWybrany_algorytm()) {
                    case NR_ALG_NONE -> {
                        System.out.println(panelGrafScroll.getWidth());
                        panelGraf.setGraf(g, panelGrafScroll.getWidth(), panelGrafScroll.getHeight());
                    }
                    case NR_ALG_DIJKSTRA -> {
                        Dijkstra dij = new Dijkstra();
                        panelGraf.setGrafDijkstra(dij.szukaj(g, panelGraf.getWybranyWezel()), dij.droga, panelGrafScroll.getWidth(), panelGrafScroll.getHeight());
                        System.out.println("dijkstra done");
                    }
                    case NR_ALG_BFS -> {
                        Bfs bfs = new Bfs();
                        panelGraf.setGrafBFS(bfs.szukaj(g, panelGraf.getWybranyWezel()), bfs.zwiedzone, panelGrafScroll.getWidth(), panelGrafScroll.getHeight());
                        System.out.println("bfs done");
                    }
                }
            } catch (OutOfMemoryError ex) {
                Problemy.Util.createPopup("Out of Memory");
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


    void akcjaPrzyciskuStworz() {
        g = new Graf(o1.getW(), o1.getH());
        g.stworzGraf(o1.getW_min(), o1.getW_max(), r);
        panelGraf.setGraf(g, panelGrafScroll.getWidth(), panelGrafScroll.getHeight());
        o1.dispatchEvent(new WindowEvent(o1, WindowEvent.WINDOW_CLOSING));
        sciezka = "";
    }

    void akcjaPrzyciskuAnuluj() {
        o1.dispatchEvent(new WindowEvent(o1, WindowEvent.WINDOW_CLOSING));
    }


    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());
        switch (e.getActionCommand()) {
            case "Stwórz" -> akcjaPrzyciskuStworz();
            case "Anuluj" -> akcjaPrzyciskuAnuluj();
        }
    }
}
