package gui;

import javax.swing.*;
import java.awt.*;

public class OknoGlowne extends JFrame {

    public OknoGlowne(PanelBoczny p) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(400, 500);
        add(p, BorderLayout.EAST);
        Show();
    }

    public void Show() {
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
