package csvloader;

import graph.exceptions.GraphNodeMissingException;
import graph.structures.Graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class CsvLoader {

    public static Graph<String, String, Double> graphFromCsv(String path)
            throws FileNotFoundException, GraphNodeMissingException {

        if (!Files.exists(Paths.get(path))) {
            throw new FileNotFoundException();
        }

        Graph<String, String, Double> graph = new Graph<>(false);
        Scanner s = new Scanner(new FileReader(path));
        String[] line;

        while(s.hasNext()) {
            line = s.nextLine().split(",");
            /*graph.addNode(line[0]);
            graph.addNode(line[1]);
            graph.addEdge(line[0], line[1], Float.parseFloat(line[2]));*/
            graph.addNodesEdge(line[0], line[1], Double.parseDouble(line[2]));
        }

        s.close();
        return graph;
    }
}
