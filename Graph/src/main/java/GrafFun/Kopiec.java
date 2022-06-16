package GrafFun;

public class Kopiec {
    int[] od;
    double[] droga;
    int rozmiar, komorek;

    public Kopiec(int min_size) {
        komorek = 0;
        int size = 1;
        while (size < min_size)
            size *= 2;
        rozmiar = size;
        od = new int[rozmiar];
        droga = new double[rozmiar];
    }

    public void dodaj(int od, double waga) {
        int i, j;
        i = komorek;
        komorek++;
        if (komorek >= rozmiar) {
            double[] pom_droga = new double[rozmiar * 2];
            System.arraycopy(droga, 0, pom_droga, 0, rozmiar);
            droga = pom_droga;
            int[] pom_od = new int[rozmiar * 2];
            System.arraycopy(this.od, 0, pom_od, 0, rozmiar);
            this.od = pom_od;
            rozmiar *= 2;
        }
        j = (i - 1) / 2;
        while (i > 0 && droga[j] > waga) {
            droga[i] = droga[j];
            this.od[i] = this.od[j];
            i = j;
            j = (i - 1) / 2;
        }
        droga[i] = waga;
        this.od[i] = od;
    }

    public double zdejmij() throws IndexOutOfBoundsException {
        if (komorek <= 0)
            throw new IndexOutOfBoundsException("Próba zdjęcia z pustego kopca");
        int i = 0, j = 1, pom;
        double v, pom1 = droga[0];

        komorek--;
        v = droga[komorek];
        pom = od[komorek];
        while (j < komorek) {
            if (j + 1 < komorek && droga[j + 1] < droga[j])
                j++;
            if (v <= droga[j])
                break;
            droga[i] = droga[j];
            od[i] = od[j];
            i = j;
            j = j * 2 + 1;
        }
        droga[i] = v;
        od[i] = pom;
        return pom1;
    }

    int nrGora() throws IndexOutOfBoundsException {
        if (komorek <= 0)
            throw new IndexOutOfBoundsException("Próba uzyskania nr wierzchołka na szczycie pustego kopca\n");
        return od[0];
    }

    public int size() {
        return komorek;
    }

    @Override
    public String toString() {
        String out = "cells:" + komorek + "\n";
        for (int x = 0; x < komorek; x++)
            out += droga[x] + " ";
        return out;
    }
}
