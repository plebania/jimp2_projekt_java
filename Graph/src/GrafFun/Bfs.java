package GrafFun;

import struktury.Graf;


public class Bfs {
    public int[] poprzednik, zwiedzone;
    int size;

    void initBfs(int n) {
        poprzednik = new int[n];
        zwiedzone = new int[n];
        size = n;
    }

    public Graf szukaj(Graf g, int od) {
        Graf pom_g = new Graf(g.size());
        pom_g.setH(g.getH());
        pom_g.setW(g.getW());
        if (od >= g.size())
            return null;
        initBfs(g.size());
        Kolejka k = new Kolejka();
        for (int x = 0; x < g.size(); x++) {
            poprzednik[x] = 0;
            zwiedzone[x] = 0;
        }
        k.dodajWierzcholek(od);
        int w, v;
        while (k.pierwszy != null) {
            //w = k.pierwszy.w;
            w = k.zdejmijWierzcholek();

            for (int y = 0; y < g.krawedzie[w].size(); y++) {
                v = g.krawedzie[w].Do[y];
                if (zwiedzone[v] == 0) {
                    zwiedzone[v] = 1;
                    pom_g.dodajKrawedz(w, v, 1);
                    if (g.istniejeOdwrotna(w, v))
                        pom_g.dodajKrawedz(v, w, 1);
                    // out->odleglosc[v] = out->odleglosc[w] + 1;
                    poprzednik[v] = w;
                    k.dodajWierzcholek(v);
                }
            }
            zwiedzone[w] = 2;
        }
        return pom_g;
    }

    @Override
    public String toString() {
        String out = "poprzednicy:[";
        if (size <= 0)
            return "poprzednicy: []\nzwiedzone: []";
        out += poprzednik[0];
        for (int x = 1; x < size; x++)
            out += "," + poprzednik[x];
        out += "]\nzwiedzone: [";
        out += zwiedzone[0];
        for (int x = 1; x < size; x++)
            out += "," + zwiedzone[x];
        out += "]";
        return out;
    }
}
