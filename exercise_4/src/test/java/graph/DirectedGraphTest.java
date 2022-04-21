package graph;

import graph.exceptions.GraphEdgeMissingException;
import graph.exceptions.GraphNodeMissingException;
import graph.structures.Edge;
import graph.structures.Graph;
import graph.structures.Node;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DirectedGraphTest {

    Graph<Integer, Integer, Character> graph;

    @Before
    @Test
    public void init() throws GraphNodeMissingException {
        graph = new Graph<>(true);

        assertEquals(0, graph.nodeCount());
        assertEquals(0, graph.edgeCount());

        graph.addNode(1, 1);
        graph.addNode(2, 2);

        graph.addEdge(1, 2, 'a');
    }

    @Test
    public void isDirected() {
        assertTrue(graph.isDirected());
    }

    @Test
    public void nodeCount() {
        assertEquals(2, graph.nodeCount());
        graph.addNode(3, 3);
        assertEquals(3, graph.nodeCount());

    }

    @Test
    public void edgeCount() throws GraphNodeMissingException {
        assertEquals(1, graph.edgeCount());
        graph.addEdge(2, 1, 'b');
        assertEquals(2, graph.edgeCount());
    }

    @Test
    public void addNode() {
        graph.addNode(3, 3);
        assertEquals(3, graph.getNode(3).getValue());

        graph.addNode(null, null);
        assertEquals(3, graph.nodeCount());

        graph.addNode(4, null);
        assertEquals(4, graph.nodeCount());

        graph.addNode(null, 5);
        assertEquals(4, graph.nodeCount());
    }

    @Test
    public void getNode() {
        assertEquals(1, graph.getNode(1).getValue());
        assertNull(graph.getNode(3));
        assertNull(graph.getNode(null));
    }

    @Test
    public void containsNode() {
        assertTrue(graph.containsNode(1));
        assertFalse(graph.containsNode(3));
        assertFalse(graph.containsNode(null));

    }

    @Test
    public void getNodes() {
        ArrayList<Node<Integer, Integer, Character>> nodes = graph.getNodes();
        assertEquals(2, nodes.size());
        // maybe test the returned nodes.
//        System.out.println(Arrays.toString(nodes.toArray()));
        // should return
        // [Node{key=1, value=1, edges={2=a}}, Node{key=2, value=2, edges={}}]
    }

    @Test
    public void removeNode() {
        graph.addNode(3, 3);
        assertEquals(3, graph.nodeCount());

        graph.removeNode(3);
        assertNull(graph.getNode(3));
        assertEquals(2, graph.nodeCount());

        graph.removeNode(2);
        assertEquals(1, graph.nodeCount());
        assertEquals(0, graph.edgeCount());
        assertEquals(0, graph.getNode(1).edgesCount());
    }

    @Test
    public void removeNodeMissing() {
        graph.removeNode(3);
        assertEquals(2, graph.nodeCount());
    }

    @Test
    public void removeNodeNull() {
        graph.removeNode(null);
        assertEquals(2, graph.nodeCount());
    }

    @Test
    public void addEdge() throws GraphNodeMissingException {
        graph.addEdge(2, 1, 'b');
        assertEquals('b', graph.getEdge(2, 1).getLabel());
        assertEquals(2, graph.edgeCount());
    }

    @Test(expected = GraphNodeMissingException.class)
    public void addEdgeNonExisting() throws GraphNodeMissingException {
        graph.addEdge(3, 1, 'c');
        fail();
    }

    @Test(expected = GraphNodeMissingException.class)
    public void addEdgeNullValue() throws GraphNodeMissingException {
        graph.addEdge(null, 1, 'd');
        fail();
    }

    @Test(expected = GraphNodeMissingException.class)
    public void addEdgeValueNull() throws GraphNodeMissingException {
        graph.addEdge(1, null, 'd');
        fail();
    }

    @Test(expected = GraphNodeMissingException.class)
    public void addEdgeNullNull() throws GraphNodeMissingException {
        graph.addEdge(null, null, 'd');
        fail();
    }

    @Test
    public void addNodesEdge() {
        graph.addNodesEdge(3, 3, 4, 4, 'd');
        assertTrue(graph.containsNode(3));
        assertTrue(graph.containsNode(4));
        assertEquals(3, graph.getNode(3).getValue());
        assertEquals(4, graph.getNode(4).getValue());
        assertEquals(2, graph.edgeCount());
        assertEquals('d', graph.getEdge(3, 4).getLabel());

        graph.addNodesEdge(1, 1, 5, 5, 'f');
        assertEquals(5, graph.nodeCount());
        assertEquals('f', graph.getEdge(1, 5).getLabel());
        assertEquals(3, graph.edgeCount());

        graph.addNodesEdge(2, 2, 4, 4, 'e');
        assertEquals(5, graph.nodeCount());
        assertEquals(4, graph.edgeCount());
        assertEquals('e', graph.getEdge(2, 4).getLabel());

        graph.addNodesEdge(1, 1, null, null, 'n');
        assertEquals(5, graph.nodeCount());
        assertEquals(4, graph.edgeCount());

        graph.addNodesEdge(null, null, 2, 2, 'n');
        assertEquals(5, graph.nodeCount());
        assertEquals(4, graph.edgeCount());

        graph.addNodesEdge(null, null, null, null, 'n');
        assertEquals(5, graph.nodeCount());
        assertEquals(4, graph.edgeCount());
    }

    @Test
    public void getEdge() {
        assertEquals('a', graph.getEdge(1, 2).getLabel());
        assertNull(graph.getEdge(2, 1));
        assertNull(graph.getEdge(null, 2));
        assertNull(graph.getEdge(1, null));
        assertNull(graph.getEdge(null, null));
    }

    @Test
    public void getEdges() {
        List<Edge<Integer, Character>> edges = graph.getEdges();
        assertEquals(1, edges.size());
        assertEquals('a', edges.get(0).getLabel().charValue());
    }

    @Test
    public void getAdjacentNodes() {
        assertEquals(2, graph.getAdjacentNodes(1).get(0).getValue().intValue());
        assertTrue(graph.getAdjacentNodes(2).isEmpty());
    }

    @Test
    public void containsEdge() {
        assertTrue(graph.containsEdge(1, 2));
        assertFalse(graph.containsEdge(2, 1));
        assertFalse(graph.containsEdge(null, 2));
        assertFalse(graph.containsEdge(1, null));
    }

    @Test
    public void updateEdge() throws GraphNodeMissingException, GraphEdgeMissingException {
        assertEquals('a', graph.getEdge(1, 2).getLabel());
        graph.updateEdge(1, 2, '$');
        assertEquals('$', graph.getEdge(1, 2).getLabel());
    }

    @Test(expected = GraphEdgeMissingException.class)
    public void updateEdgeNotExisting() throws GraphNodeMissingException, GraphEdgeMissingException {
        assertFalse(graph.containsEdge(2, 1));
        graph.updateEdge(2, 1, 'c');
        fail();
    }

    @Test(expected = GraphNodeMissingException.class)
    public void updateEdgeValueNull() throws GraphNodeMissingException, GraphEdgeMissingException {
        graph.updateEdge(1, null, 'c');
        fail();
    }

    @Test(expected = GraphNodeMissingException.class)
    public void updateEdgeNullValue() throws GraphNodeMissingException, GraphEdgeMissingException {
        graph.updateEdge(null, 2, 'c');
        fail();
    }

    @Test(expected = GraphNodeMissingException.class)
    public void updateEdgeNullNull() throws GraphNodeMissingException, GraphEdgeMissingException {
        graph.updateEdge(null, null, 'c');
        fail();
    }

    @Test
    public void removeEdge() throws GraphNodeMissingException {
        assertEquals(1, graph.edgeCount());
        graph.removeEdge(1, 2);
        assertEquals(0, graph.edgeCount());
    }

    @Test(expected = GraphNodeMissingException.class)
    public void removeEdgeMissingFrom() throws GraphNodeMissingException {
        assertEquals(1, graph.edgeCount());
        graph.removeEdge(3, 1);
        fail();
    }

    @Test
    public void removeEdgeMissing() throws GraphNodeMissingException {
        assertEquals(1, graph.edgeCount());
        graph.removeEdge(2, 1);
        assertEquals(1, graph.edgeCount());
    }

    @Test(expected = GraphNodeMissingException.class)
    public void removeEdgeNullValue() throws GraphNodeMissingException {
        graph.removeEdge(null, 2);
        fail();
    }

    @Test
    public void removeEdgeValueNull() throws GraphNodeMissingException {
        assertEquals(1, graph.edgeCount());
        graph.removeEdge(2, null);
        assertEquals(1, graph.edgeCount());
    }
}