package graph.structures;

public class Edge<K, E> {
    private K from;//nodo da cui parte l'arco
    private K to; // nodo a cui arriva l'arco
    private E label; //peso dell'arco

    public Edge(K from, K to, E label) {
        this.from = from;
        this.to = to;
        this.label = label;
    }

    public K getFrom() {
        return from;
    }

    public K getTo() {
        return to;
    }

    public E getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "from=" + from +
                ", to=" + to +
                ", label=" + label +
                '}';
    }
}