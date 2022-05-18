package IO;

import struktury.Graf;
import struktury.TD_Krawedzi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;


public class Util {

    public static void zapiszGraf(Graf g, String sciezka) throws IOException {
        Files.writeString(Path.of(sciezka), g.toString(), StandardCharsets.UTF_8);
    }

    public static Graf wczytajGraf(String sciezka) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(sciezka));
        String linia = br.readLine();
        String[] pom_tab;
        pom_tab = linia.split("\\s+");
        if (pom_tab.length != 2)
            throw new IOException("Niepoprawna pierwsza linia pliku");
        int w, h, rozmiar;
        try {
            w = Integer.parseInt(pom_tab[0]);
            h = Integer.parseInt(pom_tab[1]);
        } catch (Exception e) {
            throw new IOException("Niepoprawna pierwsza linia pliku");
        }
        TD_Krawedzi[] krawedzie;
        rozmiar = w * h;
        krawedzie = new TD_Krawedzi[rozmiar];
        for (int x = 0; x < rozmiar; x++)
            krawedzie[x] = new TD_Krawedzi();

        int wierzcholekOd = 0;
        while ((linia = br.readLine()) != null) {
            pom_tab = linia.split("\\s+");
            if (pom_tab.length == 0) continue;
            if (pom_tab.length % 2 == 1) {
                try {
                    for (int x = 1; x < pom_tab.length; x += 2) {
                        if (!pom_tab[x + 1].startsWith(":"))
                            throw new IOException("Błąd wczytywania pliku " + sciezka + ", linia: " + wierzcholekOd + " brak : przed wagą");
                        pom_tab[x + 1] = pom_tab[x + 1].substring(1);
                        krawedzie[wierzcholekOd].dodajKrawedz(Integer.parseInt(pom_tab[x]), Double.parseDouble(pom_tab[x + 1]));
                    }
                } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                    System.out.println(e.getMessage());
                }
            } else
                throw new IOException("Błąd wczytywania pliku " + sciezka + ", linia: " + wierzcholekOd + " zła liczba słów w lini");
            wierzcholekOd++;
        }
        return new Graf(krawedzie, rozmiar, w, h);
    }
}
