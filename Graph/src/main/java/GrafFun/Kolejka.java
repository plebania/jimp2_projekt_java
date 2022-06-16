package GrafFun;

class node {
    node nastepny, poprzedni;
    int w;
}

public class Kolejka {
    node pierwszy, ostatni;

    public Kolejka() {
        pierwszy = null;
        ostatni = null;
    }

    public void dodajWierzcholek(int w) {
        node n = new node();
        if (pierwszy == null) {
            pierwszy = n;
            pierwszy.nastepny = null;
            pierwszy.poprzedni = null;
            pierwszy.w = w;
            return;
        } else if (ostatni == null) {
            ostatni = n;
            ostatni.poprzedni = pierwszy;
            ostatni.nastepny = null;
            pierwszy.nastepny = ostatni;
            ostatni.w = w;
            return;
        }
        n.nastepny = null;
        n.poprzedni = ostatni;
        n.w = w;
        ostatni.nastepny = n;
        ostatni = n;
    }

    public int zdejmijWierzcholek() throws IndexOutOfBoundsException {

        if (pierwszy == null)
            throw new IndexOutOfBoundsException("Próba zdjęcia wierzchołka z pustej kolejki\n");
        int pom = pierwszy.w;
        pierwszy = pierwszy.nastepny;
        if (pierwszy != null)
            pierwszy.poprzedni = null;
        return pom;
    }

    @Override
    public String toString() {
        if (pierwszy == null)
            return "[]";
        String out = "[";
        node pom = pierwszy;
        out += pom.w;
        pom = pom.nastepny;
        while (pom != null) {
            out += "," + pom.w;
            pom = pom.nastepny;
        }
        out += "]";
        return out;
    }
}
