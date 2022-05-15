package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class OknoStworzGraf extends JDialog {

    int w = 1, h = 1;
    double w_min, w_max;
    JButton przycisk_stworz = new JButton("Stwórz"),
            przycisk_anuluj = new JButton("Anuluj");

    public OknoStworzGraf() {
        JTextField wymiary_pole_tekstowe = new JTextField("w:h"),
                wagi_pole_tekstowe = new JTextField("min:max");
        JLabel wymiary_opis = new JLabel("Wymiary"),
                wagi_opis = new JLabel("Wagi");

        setLocationRelativeTo(null);
        setVisible(true);
        setTitle("Stwórz Graf");
        setLayout(new GridLayout(0, 1));

        JPanel panel_wagi_wymiary = new JPanel(new GridLayout(2, 2));
        panel_wagi_wymiary.add(wymiary_opis);
        panel_wagi_wymiary.add(wymiary_pole_tekstowe);
        panel_wagi_wymiary.add(wagi_opis);
        panel_wagi_wymiary.add(wagi_pole_tekstowe);

        JPanel panel_przyciski = new JPanel(new FlowLayout());
        panel_przyciski.add(przycisk_stworz);
        panel_przyciski.add(przycisk_anuluj);

        add(panel_wagi_wymiary);
        add(panel_przyciski);
        pack();
    }

    public void setActionListeners(ActionListener a) {
        przycisk_anuluj.addActionListener(a);
        przycisk_stworz.addActionListener(a);
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    public double getW_min() {
        return w_min;
    }

    public double getW_max() {
        return w_max;
    }
}
