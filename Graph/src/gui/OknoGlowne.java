package gui;

import javax.swing.*;
import java.awt.*;

public class OknoGlowne extends JFrame {

    public OknoGlowne(PanelBoczny pb, PanelGraf pg) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(400, 500);
        add(new JScrollPane(pg), BorderLayout.CENTER);
        add(pb, BorderLayout.EAST);
        Show();
    }

    public void Show() {
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
