package graph.structures;

import java.util.ArrayList;
import java.util.HashMap;

public class Node<K, V, E> {
    private K key;
    private V value;
    //hashmap con tutti gli archi che partono dal nodo considerato
    private HashMap<K, E> edges;

    public Node(K key, V value) {
        this.key = key;
        this.value = value;
        edges = new HashMap<>();
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    //ottenre il peso dell'arco che porta ad un determinato nodo
    public E getLabel(K to) {
        return edges.get(to);
    }

    //presenza o meno di un determinato arco
    public boolean hasEdge(K to) {
        return edges.containsKey(to);
    }

    //aggiunta di un arco all'hasmap che li contiene
    public void addEdge(K to, E label) {
        edges.put(to, label);
    }

    //ottenere un arco verso un altro nodo
    public Edge<K, E> getEdge(K to) {
        if (edges.containsKey(to)) {
            return new Edge<>(this.key, to, edges.get(to));
        } else {
            return null;
        }
    }
    //ottenere tutti gli archi uscenti dal nodo
    public ArrayList<Edge<K, E>> getEdges() {
        ArrayList<Edge<K, E>> edgeList = new ArrayList<>();
        this.edges.forEach((k, e) -> edgeList.add(new Edge<>(this.key, k, e)));
        return edgeList;
    }
    //ottenere tutti gli archi adiacenti al nodo
    public ArrayList<K> getAdjacentNodesKeys() {
        return new ArrayList<>(this.edges.keySet());
    } //key set ritorna tutte le chiavi presenti nell'hashmap edges che sono proprio i nodi interessati

    public int edgesCount() {
        return edges.size();
    }

    public void removeEdge(K to) {
        edges.remove(to);
    }

    @Override
    public String toString() {
        return "Node{" +
                "key=" + key +
                ", value=" + value +
                ", edges=" + edges +
                '}';
    }
}