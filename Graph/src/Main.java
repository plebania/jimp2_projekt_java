import GrafFun.Dijkstra;
import struktury.Graf;
import GrafFun.Bfs;

import java.util.Random;

public class Main {
    public static void main(String args[]) {
        Random rand_obj = new Random(System.currentTimeMillis());
        Graf pom = new Graf(5, 5);
        pom.stworzGraf(0, 1, rand_obj);
        Bfs BFS = new Bfs();
        BFS.szukaj(pom, 0);
        Dijkstra DIJKSTRA = new Dijkstra();
        DIJKSTRA.szukaj(pom, 0);
        System.out.println(pom);
        System.out.println(BFS);
        System.out.println(DIJKSTRA);
    }
}
