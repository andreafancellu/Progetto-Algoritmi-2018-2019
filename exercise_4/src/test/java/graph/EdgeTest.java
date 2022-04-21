package graph;

import graph.structures.Edge;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EdgeTest {
    private Edge<Integer, Character> edge;

    @Before
    public void setUp() {
        edge = new Edge<>(1, 2, 'a');
    }

    @Test
    public void getFrom() {
        assertEquals(1, edge.getFrom().intValue());
    }

    @Test
    public void getTo() {
        assertEquals(2, edge.getTo().intValue());
    }

    @Test
    public void getLabel() {
        assertEquals('a', edge.getLabel().charValue());
    }

    @Test
    public void toString1() {
        assertEquals("Edge{from=1, to=2, label=a}", edge.toString());
    }
}