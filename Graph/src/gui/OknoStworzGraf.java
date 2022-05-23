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
        JTextField szerokosc_pole_tekstowe = new JTextField(8), wysokosc_pole_tekstowe = new JTextField(8),
                min_pole_tekstowe = new JTextField(8), max_pole_tekstowe = new JTextField(8);
        JLabel wymiary_opis = new JLabel("Wymiary"),
                wagi_opis = new JLabel("Wagi"), dwokropek1 = new JLabel(":"), dwokropek2 = new JLabel(":");


        szerokosc_pole_tekstowe.setText("w");
        wysokosc_pole_tekstowe.setText("h");
        min_pole_tekstowe.setText("min");
        max_pole_tekstowe.setText("max");
        szerokosc_pole_tekstowe.setHorizontalAlignment(SwingConstants.RIGHT);
        wysokosc_pole_tekstowe.setHorizontalAlignment(SwingConstants.RIGHT);
        min_pole_tekstowe.setHorizontalAlignment(SwingConstants.RIGHT);
        max_pole_tekstowe.setHorizontalAlignment(SwingConstants.RIGHT);


        setLocationRelativeTo(null);
        setVisible(true);
        setTitle("Stwórz Graf");
        setLayout(new GridLayout(0, 1));

        //JPanel panelOpisanePolaTekstowe = new JPanel(new FlowLayout()), panelOpisy = new;

        JPanel w_h = new JPanel(new FlowLayout());
        JPanel min_max = new JPanel(new FlowLayout());
        JPanel panel_wagi_wymiary = new JPanel(new GridLayout(2, 2));

        panel_wagi_wymiary.add(wymiary_opis);
        w_h.add(szerokosc_pole_tekstowe);
        w_h.add(dwokropek1);
        w_h.add(wysokosc_pole_tekstowe);
        panel_wagi_wymiary.add(w_h);
        panel_wagi_wymiary.add(wagi_opis);
        min_max.add(min_pole_tekstowe);
        min_max.add(dwokropek2);
        min_max.add(max_pole_tekstowe);
        panel_wagi_wymiary.add(min_max);

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
