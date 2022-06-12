package GrafFun;

import struktury.Graf;

public class Dijkstra {
    public int[] od;
    public double[] droga;
    int size;

    public Dijkstra() {
        size = 0;
    }

    void initDijkstra(int n) {
        size = n;
        od = new int[n];
        droga = new double[n];
    }

    public Graf szukaj(Graf g, int od) {
        Graf pom_g = new Graf(g.size());
        pom_g.setH(g.getH());
        pom_g.setW(g.getW());
        if (od >= g.size())
            return null;
        Kopiec k = new Kopiec(g.size());
        initDijkstra(g.size());

        for (int x = 0; x < g.size(); x++)
            droga[x] = Double.POSITIVE_INFINITY;
        k.dodaj(od, 0);
        this.od[od] = od;
        droga[od] = 0;
        int w, v;
        double waga;

        while (k.size() != 0) {
            w = k.od[0];
            k.zdejmij();

            for (int y = 0; y < g.krawedzie[w].size(); y++) {
                v = g.krawedzie[w].Do[y];
                waga = g.krawedzie[w].wagi[y];
                if (droga[w] + waga < droga[v]) {
                    droga[v] = droga[w] + waga;
                    pom_g.dodajKrawedz(w, v, droga[v]);
                    this.od[v] = w;
                    k.dodaj(v, droga[v]);
                }
            }
        }
        return pom_g;
    }

    @Override
    public String toString() {
        String out = "od:[";
        if (size <= 0)
            return "od: []\ndroga: []";
        out += od[0];
        for (int x = 1; x < size; x++)
            out += "," + od[x];
        out += "]\ndroga: [";
        out += droga[0];
        for (int x = 1; x < size; x++)
            out += "," + droga[x];
        out += "]";
        return out;
    }
}
