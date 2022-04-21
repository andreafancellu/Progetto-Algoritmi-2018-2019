import graph.exceptions.GraphNodeMissingException;
import graph.structures.Edge;
import graph.structures.Graph;

import java.io.IOException;
import java.util.Comparator;

import static csvloader.CsvLoader.graphFromCsv;
import static graph.MinimumSpanningTree.kruskalAlgorithm;

public class ItalianDistGraph {
    public static void main(String[] args) throws IOException, GraphNodeMissingException {
        Graph<String, String, Double> graph;
        graph = kruskalAlgorithm(graphFromCsv(args[0]), new ComparatorEdgeStringDouble());
        double countKm = 0;

        for (Edge<String, Double> edge : graph.getEdges())
            countKm += edge.getLabel();
        if (!graph.isDirected())
            countKm /= 2;

        // m to km
        countKm /= 1000;

        System.out.format("Node count: %d\t\t[%b: expected 18640]\n" +
                        "Edge count: %d\t\t[%b: expected 18637]\n" +
                        "Km   count: %.3f\t[%b: expected about 89939.913]",
                graph.nodeCount(), graph.nodeCount() == 18640,
                graph.edgeCount(), graph.edgeCount() == 18637,
                countKm, Math.abs(countKm - 89939.913) < 0.001);
    }

    /**
     * Comparator used in sort
     * Every type needs a different comparator
     */
    private static class ComparatorEdgeStringFloat implements Comparator<Edge<String, Float>> {
        @Override
        public int compare(Edge<String, Float> o1, Edge<String, Float> o2) {
            return o1.getLabel().compareTo(o2.getLabel());
        }
    }

    private static class ComparatorEdgeStringDouble implements Comparator<Edge<String, Double>> {
        @Override
        //faccio l'override del metodo compare per dire al comparatore deve scandirli in base
        //al peso dell'arco
        public int compare(Edge<String, Double> o1, Edge<String, Double> o2) {
            return o1.getLabel().compareTo(o2.getLabel());
        }
    }
}
