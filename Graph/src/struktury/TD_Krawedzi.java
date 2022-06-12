package struktury;

public class TD_Krawedzi {
    public double[] wagi;
    public int[] Do;
    int rozmiar, komorek;

    public TD_Krawedzi() {
        komorek = 0;
        rozmiar = 4;
        wagi = new double[4];
        Do = new int[4];
        for (int x = 0; x < 4; x++) {
            wagi[x] = 0;
            Do[x] = -1;
        }
    }

    public TD_Krawedzi(int rozmiar) {
        komorek = 0;
        int pom = 2;
        while (pom < rozmiar)
            pom *= 2;
        this.rozmiar = pom;
        wagi = new double[pom];
        Do = new int[pom];
        for (int x = 0; x < pom; x++) {
            wagi[x] = 0;
            Do[x] = -1;
        }
    }

    public void dodajKrawedz(int Do, double waga) {
        if (komorek >= rozmiar) {
            double[] wagi_pom = new double[rozmiar * 2];
            int[] Do_pom = new int[rozmiar * 2];
            System.arraycopy(wagi, 0, wagi_pom, 0, rozmiar);
            System.arraycopy(this.Do, 0, Do_pom, 0, rozmiar);
            rozmiar *= 2;
            wagi = wagi_pom;
            this.Do = Do_pom;
        }
        wagi[komorek] = waga;
        this.Do[komorek] = Do;
        komorek++;
    }

    @Override
    public String toString() {
        String out = "[";
        for (int x = 0; x < komorek - 1; x++)
            out += "(" + Do[x] + ", " + wagi[x] + "),";
        out += "(" + Do[komorek - 1] + ", " + wagi[komorek - 1] + ")]";
        return out;
    }

    public int size() {
        return komorek;
    }
}
