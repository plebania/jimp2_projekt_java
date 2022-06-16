package GrafFun;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KolejkaTest {
    int[] oczekiwane, otrzymane;
    int ile;

    @Test
    void TestJednakoweWierzcholki() {

        Kolejka k = new Kolejka();
        ile = 1000;
        oczekiwane = new int[ile];
        otrzymane = new int[ile];


        for (int x = 0; x < ile; x++) {
            k.dodajWierzcholek(2);
            oczekiwane[x] = 2;
        }
        for (int x = 0; x < ile; x++)
            otrzymane[x] = k.zdejmijWierzcholek();
        assertNull(k.pierwszy);
        assertArrayEquals(oczekiwane, otrzymane);
    }


    @Test
    void TestRozneWierzcholki() {

        Kolejka k = new Kolejka();
        ile = 1000;
        oczekiwane = new int[ile];
        otrzymane = new int[ile];


        for (int x = 0; x < ile; x++) {
            k.dodajWierzcholek(x);
            oczekiwane[x] = x;
        }
        for (int x = 0; x < ile; x++)
            otrzymane[x] = k.zdejmijWierzcholek();
        assertNull(k.pierwszy);
        assertArrayEquals(oczekiwane, otrzymane);
    }


    @Test
    void TestZdejmowamiePustej() {
        Kolejka k = new Kolejka();
        ile = 10;

        for (int x = 0; x < ile; x++) {
            k.dodajWierzcholek(x);
        }
        for (int x = 0; x < ile; x++)
            k.zdejmijWierzcholek();
        assertThrows(IndexOutOfBoundsException.class, k::zdejmijWierzcholek);
        assertNull(k.pierwszy);
    }
}