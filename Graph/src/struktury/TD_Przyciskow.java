package struktury;

import gui.PrzyciskKrawedz;

import javax.swing.*;
import java.awt.*;

public class TD_Przyciskow {
    public PrzyciskKrawedz[] przyciski;
    int rozmiar, komorek;

    public TD_Przyciskow() {
        komorek = 0;
        rozmiar = 2;
        przyciski = new PrzyciskKrawedz[2];
    }

    public TD_Przyciskow(int rozmiar) {
        komorek = 0;
        int pom = 2;
        while (pom < rozmiar)
            pom *= 2;
        this.rozmiar = pom;
        przyciski = new PrzyciskKrawedz[pom];
    }

    public void dodajKrawedz(int x1, int y1, int x2, int y2, int grubosc) {
        if (komorek >= rozmiar) {
            PrzyciskKrawedz[] przyciski_pom = new PrzyciskKrawedz[rozmiar * 2];
            System.arraycopy(przyciski, 0, przyciski_pom, 0, rozmiar);
            rozmiar *= 2;
            przyciski = przyciski_pom;
        }
        przyciski[komorek] = new PrzyciskKrawedz();
        przyciski[komorek].setWymiary(x1, y1, x2, y2, grubosc);
        przyciski[komorek].setBounds(0, 0, 1000, 1000);//TODO coś tu oooostro nie gra
        przyciski[komorek].setOpaque(false);
        przyciski[komorek].setFocusPainted(false);
        przyciski[komorek].setBorderPainted(false);
        komorek++;
    }

    public void dodajKrawedz(PrzyciskKrawedz p) {
        if (komorek >= rozmiar) {
            PrzyciskKrawedz[] przyciski_pom = new PrzyciskKrawedz[rozmiar * 2];
            System.arraycopy(przyciski, 0, przyciski_pom, 0, rozmiar);
            rozmiar *= 2;
            przyciski = przyciski_pom;
        }
        przyciski[komorek] = p;
        komorek++;
    }

    public int size() {
        return komorek;
    }
}
