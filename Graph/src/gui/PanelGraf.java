package gui;

import struktury.Graf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelGraf extends JPanel implements ActionListener {
    JButton[] krawedzie;
    PrzyciskOkragly[] wezly;
    Dimension wymiary;

    public PanelGraf() {
        wymiary = new Dimension(getWidth(), getHeight());
        //add(scroll);
        //krawedzie =
    }

    public void setGraf(Graf g) {
        System.out.println(getWidth());
        removeAll();
        int R = (getWidth() - (g.getW() - 1) * 2) / (g.getW());
        if (R > 50) R = 50;
        if (R < 20) R = 20;
        wymiary.width = g.getW() * R * 2 + 8;
        wymiary.height = g.getH() * R * 2 + 8;
        System.out.println(g.size());
        wezly = new PrzyciskOkragly[g.size()];
        for (int x = 0; x < g.size(); x++) {
            wezly[x] = new PrzyciskOkragly();
            wezly[x].setBounds(((x % g.getW()) * (R * 2 + 2)), ((x / g.getW()) * (R * 2 + 2)), R, R);
            add(wezly[x]);
        }
        revalidate();
        setLayout(null);
        updateUI();
    }

    @Override
    public Dimension getPreferredSize() {
        return wymiary;
    }

    public void actionPerformed(ActionEvent e) {

    }
}
