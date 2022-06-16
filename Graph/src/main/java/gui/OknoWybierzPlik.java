package gui;

import javax.swing.*;


public class OknoWybierzPlik extends JFileChooser {
    public enum WczytajZapisz {
        WCZYTAJ,
        ZAPISZ
    }

    String sciezka = "";

    public OknoWybierzPlik(WczytajZapisz kierunek) {
        setVisible(true);
        int r;
        if (kierunek == WczytajZapisz.WCZYTAJ)
            r = showOpenDialog(null);
        else
            r = showSaveDialog(null);
        if (r == JFileChooser.APPROVE_OPTION)
            sciezka = getSelectedFile().getAbsolutePath();
    }

    public String getSciezka() {
        return sciezka;
    }
}
