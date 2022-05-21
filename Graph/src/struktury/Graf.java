package struktury;

import java.util.Random;

public class Graf {
    public TD_Krawedzi[] krawedzie;
    int wezlow, w, h;

    void initGraf(int wezlow, int w, int h) {
        this.wezlow = wezlow;
        this.w = w;
        this.h = h;
        krawedzie = new TD_Krawedzi[wezlow];
        for (int x = 0; x < wezlow; x++)
            krawedzie[x] = new TD_Krawedzi();
    }

    public Graf() {
        initGraf(2, 0, 0);
    }

    public Graf(int wezlow) {
        initGraf(wezlow, 0, 0);
    }

    public Graf(TD_Krawedzi[] krawedzie, int wezlow, int w, int h) {
        this.krawedzie = krawedzie;
        this.wezlow = wezlow;
        this.w = w;
        this.h = h;
    }

    public Graf(int w, int h) {
        initGraf(w * h, w, h);
    }

    public void setW(int w) //TODO przemyśleć czy nie zainicjować ponownie grafu
    {
        this.w = w;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    public int size() {
        return wezlow;
    }

    void dodajKrawedz(int w1, int w2, double waga) {
        krawedzie[w1].dodajKrawedz(w2, waga);
    }

    void dodajKrawedzDwostronna(int w1, int w2, double waga) {
        krawedzie[w1].dodajKrawedz(w2, waga);
        krawedzie[w2].dodajKrawedz(w1, waga);
    }

    double rand(double min, double max, Random r) {
        return min + (max - min) * r.nextDouble();
    }

    public void stworzGraf(double min_wag, double max_wag, Random r) {
        initGraf(w * h, w, h);

        //krawedzi = 2 * h * w - w - h;
        if (w == 1 || h == 1) {
            for (int x = 0; x < w * h - 1; x++)
                dodajKrawedzDwostronna(x, x + 1, rand(min_wag, max_wag, r));
            return;
        }

        for (int x = 0; x < w - 1; x++) {
            for (int y = 0; y < h - 1; y++) {
                dodajKrawedzDwostronna(y * w + x, y * w + x + 1, rand(min_wag, max_wag, r));
                dodajKrawedzDwostronna(y * w + x, (y + 1) * w + x, rand(min_wag, max_wag, r));
            }
            dodajKrawedzDwostronna((h - 1) * w + x, (h - 1) * w + x + 1, rand(min_wag, max_wag, r));
        }
        for (int y = 0; y < h - 1; y++)
            dodajKrawedzDwostronna((y + 1) * w - 1, (y + 2) * w - 1, rand(min_wag, max_wag, r));
    }

    @Override
    public String toString() {
        String out = w + " " + h + "\n";
        for (int x = 0; x < wezlow; x++) {
            out += "\t";
            for (int y = 0; y < krawedzie[x].size(); y++)
                out += " " + krawedzie[x].Do[y] + " :" + krawedzie[x].wagi[y] + " ";
            out += "\n";
        }
        return out;
    }
}
