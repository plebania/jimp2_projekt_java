package GrafFun;


import org.junit.jupiter.api.Test;


import java.util.Random;


import static org.junit.jupiter.api.Assertions.*;

//import static org.junit.jupiter.api.Assertions.*;

class KopiecTest {


    double[] oczekiwane, otrzymane;
    int ile;

    @Test
    void TestSortowanieTablicy() {
        ile = 1000;
        oczekiwane = new double[ile];
        otrzymane = new double[ile];
        Kopiec k = new Kopiec(1);
        Random rand = new Random();
        for (int x = 0; x < ile; x++) {
            oczekiwane[x] = rand.nextDouble(100) - 50;
            k.dodaj(x, oczekiwane[x]);
        }
        java.util.Arrays.sort(oczekiwane);

        for (int x = 0; x < ile; x++)
            otrzymane[x] = k.zdejmij();


        assertArrayEquals(otrzymane, oczekiwane);
        assertEquals(k.size(), 0);
    }

    @Test
    void TestJednakoweWartosci() {
        ile = 1000;
        oczekiwane = new double[ile];
        otrzymane = new double[ile];
        Kopiec k = new Kopiec(1);
        Random rand = new Random();
        for (int x = 0; x < ile; x++) {
            oczekiwane[x] = 2;
            k.dodaj(x, oczekiwane[x]);
        }
        java.util.Arrays.sort(oczekiwane);

        for (int x = 0; x < ile; x++)
            otrzymane[x] = k.zdejmij();


        assertArrayEquals(otrzymane, oczekiwane);
        assertEquals(k.size(), 0);
    }


    @Test
    void TestZdejmowanieZPustego() {
        ile = 10;
        Kopiec k = new Kopiec(1);
        for (int x = 0; x < ile; x++) {
            k.dodaj(x, x);
        }

        for (int x = 0; x < ile; x++)
            k.zdejmij();

        assertThrows(IndexOutOfBoundsException.class, k::zdejmij);
        assertEquals(k.size(), 0);
    }
}