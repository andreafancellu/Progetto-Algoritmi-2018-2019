package graph.exceptions;

public class GraphEdgeMissingException extends GraphException {
    public GraphEdgeMissingException() {
        super();
    }

    public GraphEdgeMissingException(String message) {
        super(message);
    }
}
//nuova classse di exception per mandare un messaggio