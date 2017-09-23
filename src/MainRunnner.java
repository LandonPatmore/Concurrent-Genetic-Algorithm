public class MainRunnner {

    public static void main(String[] args) {
        GeneticAlgorithm g = new GeneticAlgorithm(6, 6);
        g.initialize();
        g.neighbors();
    }
}
