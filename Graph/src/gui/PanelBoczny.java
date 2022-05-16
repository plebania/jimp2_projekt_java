package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class PanelBoczny extends JPanel implements ActionListener {

    public enum nr_algorytmu {
        Dijkstra_alg,
        Bfs_alg
    }

    JButton przycisk_stworz = new JButton("Stwórz Graf"),
            przycisk_wczytaj = new JButton("Wczytaj"),
            przycisk_zapisz = new JButton("Zapisz"),
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
        panel_dol.add(przycisk_stworz);


        setLayout(new BorderLayout());
        add(panel_dol, BorderLayout.SOUTH);
        add(panel_gora, BorderLayout.NORTH);
        setBackground(Color.BLUE);
    }

    public void setActionListeners(ActionListener a) {
        przycisk_wczytaj.addActionListener(a);
        przycisk_zapisz.addActionListener(a);
        przycisk_stworz.addActionListener(a);
        przycisk_uruchom_algorytm.addActionListener(a);
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
