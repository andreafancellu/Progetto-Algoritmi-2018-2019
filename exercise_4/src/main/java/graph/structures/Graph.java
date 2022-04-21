package graph.structures;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import graph.exceptions.GraphEdgeMissingException;
import graph.exceptions.GraphNodeMissingException;

import java.util.*;

public class Graph<K, V, E> {

    private HashMap<K, Node<K, V, E>> nodes; // chiave sono nodi che fanno prte del grafo, vlore sono gli oggetti nodo che rappresenta il nodo corrente il vaore, e il peso
    //rappresentato come un hashmap (insieme) di nodi

    private boolean directed;

    public Graph(boolean directed) {
        nodes = new HashMap<>();
        this.directed = directed;
    }

    //dire se il grafo e direzionato o no
    public boolean isDirected() {
        return directed;
    }

    //numero di nodi all'interno del grafo
    public int nodeCount() {
        return nodes.size();
    }

    //numero di archi all'interno del grafo
    public int edgeCount() {
        //estraggo il valore dell'hashmap ossia i singoli nodi e di questi conto i nodi e ne faccio la somma
        int edgesCounter = nodes.values().stream().mapToInt(Node::edgesCount).sum();
        //giustamente se non è diretto allora dimezzo perchè un nodo lo avrò contato doppio
        return directed ? edgesCounter : edgesCounter / 2;
    }

    //aggiunta di un nodo con value nulla, chiamo il metodo sottostante
    public void addNode(@NotNull K key) {
        addNode(key, null);
    }

    //aggiunta di un nodo con value definita
    public void addNode(@NotNull K key, @Nullable V value) {
        if (key != null && !containsNode(key)) {
            nodes.put(key, new Node<>(key, value));
        }
    }

    public @Nullable
    Node getNode(K key) {
        return nodes.get(key);
    }

    public boolean containsNode(K key) {
        return nodes.containsKey(key);
    }

    //recupero di tutti i nodi all'interno di un arco
    public ArrayList<Node<K, V, E>> getNodes() {
        return new ArrayList<>(this.nodes.values());
    }

    //rimozione di un nodo con chiave k
    public void removeNode(K key) {
        //prima vado a fare un ciclo ed eliminare se presenti gli archi verso il nodo
        for (Node<K, V, E> node : nodes.values()) {
            node.removeEdge(key);
        }
        //poi alla fine vado ad eliminare la key dall'hashmap
        nodes.remove(key);
    }

    @SuppressWarnings("Duplicates")
    public void addEdge(@NotNull K from, @NotNull K to, @Nullable E label) throws GraphNodeMissingException {
        Node<K, V, E> fromNode = nodes.get(from);
        Node<K, V, E> toNode = nodes.get(to);

        if (fromNode == null) {
            if (toNode == null) {
                throw new GraphNodeMissingException("Missing Nodes FROM and TO in graph.");
            } else {
                throw new GraphNodeMissingException("Missing Node FROM in graph.");
            }
        } else {
            if (toNode == null) {
                throw new GraphNodeMissingException("Missing Node TO in graph.");
            }
        }
        //utilizzo il metodo della classe Node per verificare se tale arco esista già
        if (!fromNode.hasEdge(to)) {
            fromNode.addEdge(to, label);
            //se non è diretto devo aggiungerlo anche nell'altro senso, e quindi nella sua hashmap
            if (!directed) {
                toNode.addEdge(from, label);
            }
        }
    }

    public void addNodesEdge(@NotNull K fromKey, @NotNull K toKey, @Nullable E label) {
        addNodesEdge(fromKey, null, toKey, null, label);
    }
    //metodo veloce per aggiungere due nodi al grafo con il relativo arco che li collega
    public void addNodesEdge(@NotNull K fromKey, @Nullable V fromValue, @NotNull K toKey, @Nullable V toValue, @Nullable E label) {
        if (fromKey == null || toKey == null) {
            return;
        }

        Node<K, V, E> fromNode = nodes.get(fromKey);
        Node<K, V, E> toNode = nodes.get(toKey);

        if (fromNode == null) {
            addNode(fromKey, fromValue);
            fromNode = nodes.get(fromKey);
        }

        if (toNode == null) {
            addNode(toKey, toValue);
            toNode = nodes.get(toKey);
        }

        if (!fromNode.hasEdge(toKey)) {
            fromNode.addEdge(toKey, label);
            if (!directed) {
                toNode.addEdge(fromKey, label);
            }
        }
    }
    //ritorno il singolo arco che vado a cercare
    public @Nullable
    Edge getEdge(K from, K to) {
        // Thanks prof.
        Node<K, V, E> fromNode = nodes.get(from);

        if (fromNode != null) {
            return fromNode.getEdge(to);
        } else {
            return null;
        }
    }

    public boolean containsEdge(K from, K to) {
        return getEdge(from, to) != null;
    }
    //ritorno un array list con tutti gli archi
    public ArrayList<Edge<K, E>> getEdges() {
        ArrayList<Edge<K, E>> edges = new ArrayList<>();
        //aggiungo tutti gli archi per ogni nodo
        for (Node<K, V, E> node : nodes.values()) {
            edges.addAll(node.getEdges());
        }
        return edges;
    }
    //utilizzo il metodo che avevo già fatto per restituire tutti i nodi adiacenti a quello che gli passo
    public ArrayList<Node<K, V, E>> getAdjacentNodes(K key) {
        ArrayList<Node<K, V, E>> adjacentNodes = new ArrayList<>();
        nodes.get(key).getAdjacentNodesKeys().forEach(k -> adjacentNodes.add(nodes.get(k)));
        return adjacentNodes;
    }

    @SuppressWarnings("Duplicates")
    //update di un arco mi serve per la proprietà del rilassamento di un arco
    public void updateEdge(@NotNull K from, @NotNull K to, @Nullable E newLabel) throws GraphNodeMissingException, GraphEdgeMissingException {
        Node<K, V, E> fromNode = nodes.get(from);
        Node<K, V, E> toNode = nodes.get(to);

        if (fromNode == null) {
            if (toNode == null) {
                throw new GraphNodeMissingException("Missing Nodes FROM and TO in graph.");
            } else {
                throw new GraphNodeMissingException("Missing Node FROM in graph.");
            }
        } else {
            if (toNode == null) {
                throw new GraphNodeMissingException("Missing Node TO in graph.");
            }
        }

        // We should update an edge only if it already exists.
        // This check is needed because we use a property of HashMaps:
        // if we add a value with a key that already exists, the old value is replaced.
        // This removed the need to make a custom method.
        if (fromNode.hasEdge(to)) {
            fromNode.addEdge(to, newLabel);
            if (!directed) {
                toNode.addEdge(from, newLabel);
            }
        } else {
            throw new GraphEdgeMissingException("Missing EDGE in graph.");
        }
    }
    //rimuovere un arco, all'interno di un grafo, faccio ovviamente riferimento al metodo
    //già fatto nella classe node
    public void removeEdge(@NotNull K from, @NotNull K to) throws GraphNodeMissingException {
        Node<K, V, E> fromNode = nodes.get(from);
        Node<K, V, E> toNode = nodes.get(to);

        if (fromNode != null) {
            fromNode.removeEdge(to);
            if (!directed) {
                if (toNode != null) {
                    toNode.removeEdge(from);
                } else {
                    throw new GraphNodeMissingException("Missing Node TO in graph");
                }
            }
        } else {
            throw new GraphNodeMissingException("Missing Node FROM in graph.");
        }
    }

}
