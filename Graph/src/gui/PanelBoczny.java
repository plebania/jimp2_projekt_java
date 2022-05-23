package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;


public class PanelBoczny extends JPanel implements ActionListener {

    public enum nr_algorytmu {
        Dijkstra_alg,
        Bfs_alg
    }

    JButton przycisk_stworz = new JButton("Stwórz Graf"),
            przycisk_wczytaj = new JButton("Wczytaj"),
            przycisk_zapisz = new JButton("Zapisz"),
            przycisk_zapisz_jako = new JButton("Zapisz Jako"),
            przycisk_uruchom_algorytm = new JButton("Uruchom algorytm");
    nr_algorytmu wybrany_algorytm = nr_algorytmu.Dijkstra_alg;

    public PanelBoczny() {
        setPreferredSize(new Dimension(200, 100));


        JPanel wczytaj_zapisz_panel = new JPanel(),
                algorytmy_panel = new JPanel(new GridLayout(0, 1)),
                panel_gora = new JPanel(new GridLayout(0, 1)),
                panel_dol = new JPanel(new GridLayout(0, 1));
        JLabel Algorytm_opis = new JLabel("Algorytm:");
        JRadioButton Dijkstra = new JRadioButton("Dijkstra"),
                Bfs = new JRadioButton("BFS");
        ButtonGroup algorytmy_grupa = new ButtonGroup();

        //pola wyboru algorytmu
        Dijkstra.addActionListener(this);
        Dijkstra.setSelected(true);
        Bfs.addActionListener(this);
        //grupowanie pol wyboru
        algorytmy_grupa.add(Dijkstra);
        algorytmy_grupa.add(Bfs);
        //panel wyboru algorytmów
        algorytmy_panel.add(Dijkstra);
        algorytmy_panel.add(Bfs);

        //panel przyciskow wczytaj, zapisz
        wczytaj_zapisz_panel.add(przycisk_wczytaj);
        wczytaj_zapisz_panel.add(przycisk_zapisz);


        //panel elementów znajdujących się na górze
        panel_gora.add(Algorytm_opis);
        panel_gora.add(algorytmy_panel);
        //panel elementów znajdujących się na dole
        panel_dol.add(przycisk_uruchom_algorytm);
        panel_dol.add(wczytaj_zapisz_panel);
        panel_dol.add(przycisk_zapisz_jako);
        panel_dol.add(przycisk_stworz);


        setLayout(new BorderLayout());
        add(panel_dol, BorderLayout.SOUTH);
        add(panel_gora, BorderLayout.NORTH);
    }


    public void setActions(AbstractAction zapisz_jako, AbstractAction zapisz, AbstractAction wczytaj, AbstractAction stworz_graf, AbstractAction uruchom_algorytm) {
        przycisk_zapisz_jako.setAction(zapisz_jako);
        przycisk_zapisz_jako.getActionMap().put("Zapisz Jako", zapisz_jako);
        przycisk_zapisz_jako.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put((KeyStroke) zapisz_jako.getValue(Action.ACCELERATOR_KEY), "Zapisz Jako");
        przycisk_zapisz_jako.setText("Zapisz Jako");

        przycisk_zapisz.setAction(zapisz);
        przycisk_zapisz.getActionMap().put("Zapisz", zapisz);
        przycisk_zapisz.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put((KeyStroke) zapisz.getValue(Action.ACCELERATOR_KEY), "Zapisz");
        przycisk_zapisz.setText("Zapisz");

        przycisk_wczytaj.setAction(wczytaj);
        przycisk_wczytaj.getActionMap().put("Wczytaj", wczytaj);
        przycisk_wczytaj.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put((KeyStroke) wczytaj.getValue(Action.ACCELERATOR_KEY), "Wczytaj");
        przycisk_wczytaj.setText("Wczytaj");

        przycisk_stworz.setAction(stworz_graf);
        przycisk_stworz.getActionMap().put("Stwórz Graf", stworz_graf);
        przycisk_stworz.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put((KeyStroke) stworz_graf.getValue(Action.ACCELERATOR_KEY), "Stwórz Graf");
        przycisk_stworz.setText("Stwórz Graf");

        przycisk_uruchom_algorytm.setAction(uruchom_algorytm);
        przycisk_uruchom_algorytm.getActionMap().put("Uruchom Algorytm", uruchom_algorytm);
        przycisk_uruchom_algorytm.setText("Uruchom Algorytm");
    }


    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());
        switch (e.getActionCommand()) {
            case "Dijkstra" -> wybrany_algorytm = nr_algorytmu.Dijkstra_alg;
            case "BFS" -> wybrany_algorytm = nr_algorytmu.Bfs_alg;
        }
    }

    public nr_algorytmu getWybrany_algorytm() {
        return wybrany_algorytm;
    }
}
