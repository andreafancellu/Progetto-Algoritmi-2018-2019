package editdistance;

import org.junit.Test;

import static editdistance.EditDistance.editDistance;
import static editdistance.EditDistance.editDistanceDyn;
import static org.junit.Assert.assertEquals;

public class EditDistanceTest {

    @Test
    @SuppressWarnings("deprecation")
    public void genericTestEditDistance() {
        assertEquals(0, editDistance("Hi", "Hi"));
        assertEquals(0, editDistance("hi", "hi"));
        assertEquals(0, editDistance("", ""));
        assertEquals(0, editDistance("1", "1"));

        assertEquals(0, editDistance("hi.", "hi."));
        assertEquals(1, editDistance("Hi.", "Hi"));

        assertEquals(1, editDistance("Hi", "hi"));
        assertEquals(1, editDistance("1", "2"));

        assertEquals(2, editDistance("Hi", "hii"));
        assertEquals(2, editDistance("Hi", "hhi"));

        assertEquals(2, editDistance("", "hi"));
        assertEquals(2, editDistance("Hi", ""));

        assertEquals(1, editDistance("h", "hi"));
        assertEquals(2, editDistance("hopo", "cop"));
        assertEquals(4, editDistance("hopo", "mira"));


        assertEquals(0, editDistance("EditDistanceTest", "EditDistanceTest"));
        assertEquals(6, editDistance("EditDistance", "EditTest"));
        assertEquals(8, editDistance("EditDistanceTest", "EditTestDistance"));

        assertEquals(5, editDistance("qwert", "asdfg"));
        assertEquals(10, editDistance("qwertyuiop", "asdfghjklz"));
    }

    @Test
    public void genericTestEditDistanceDyn() {
        assertEquals(0, editDistanceDyn("Hi", "Hi"));
        assertEquals(0, editDistanceDyn("hi", "hi"));
        assertEquals(0, editDistanceDyn("", ""));
        assertEquals(0, editDistanceDyn("1", "1"));

        assertEquals(0, editDistanceDyn("hi.", "hi."));
        assertEquals(1, editDistanceDyn("Hi.", "Hi"));

        assertEquals(1, editDistanceDyn("Hi", "hi"));
        assertEquals(1, editDistanceDyn("1", "2"));

        assertEquals(2, editDistanceDyn("Hi", "hii"));
        assertEquals(2, editDistanceDyn("Hi", "hhi"));

        assertEquals(2, editDistanceDyn("", "hi"));
        assertEquals(2, editDistanceDyn("Hi", ""));

        assertEquals(1, editDistanceDyn("h", "hi"));
        assertEquals(2, editDistanceDyn("hopo", "cop"));
        assertEquals(4, editDistanceDyn("hopo", "mira"));


        assertEquals(0, editDistanceDyn("EditDistanceDynTest", "EditDistanceDynTest"));
        assertEquals(6, editDistanceDyn("EditDistance", "EditTest"));
        assertEquals(8, editDistanceDyn("EditDistanceTest", "EditTestDistance"));
    }

    @Test
    public void editDistanceNullTests() {
        assertEquals(0, editDistanceDyn(null, null));
        assertEquals(1, editDistanceDyn("a", null));
        assertEquals(1, editDistanceDyn(null, "a"));
    }

    @Test
    public void editDistanceEmptyTests() {
        assertEquals(0, editDistanceDyn("", ""));
        assertEquals(1, editDistanceDyn("", "a"));
        assertEquals(1, editDistanceDyn("a", ""));
    }

    @Test
    public void editDistanceEmptyNullTests() {
        assertEquals(0, editDistanceDyn(null, ""));
        assertEquals(0, editDistanceDyn("", null));
    }


}