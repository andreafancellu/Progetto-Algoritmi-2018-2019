import editdistance.EditDistanceResult;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.*;

public class LennonTest {

    @Test
    public void tryToFindWordInDictionaryTest() {
        String[] dictionary = {"ciao", "caio", "oca"};
        Set<String> dictionarySet = new HashSet<>(Arrays.asList(dictionary));
        EditDistanceResult result;

        String test = "ciao";
        result = LennonDiff.tryToFindWordInDictionaryHash(test, dictionarySet);
        assertEquals(0, result.getDistance());
        assertEquals("ciao", result.getOriginal());

        test = "caio";
        result = LennonDiff.tryToFindWordInDictionaryHash(test, dictionarySet);
        assertEquals(0, result.getDistance());
        assertEquals("caio", result.getOriginal());

        test = "oca";
        result = LennonDiff.tryToFindWordInDictionaryHash(test, dictionarySet);
        assertEquals(0, result.getDistance());
        assertEquals("oca", result.getOriginal());
    }

    @Test
    public void aggregateDictionary() {
        String[] dictionary = {
                "e", "o", "a", "i",
                "il", "la", "le", "si",
                "ciao", "anni", "nodo", "mare",
                "cinque", "sempre", "scuola",
                "cavallo", "armadio",
                "tavolino"
        };
        Map<Integer, List<String>> aggregatedDictionary = LennonDiff.aggregateDictionary(dictionary);

        assertEquals(4, aggregatedDictionary.get(1).size());
        assertEquals(4, aggregatedDictionary.get(2).size());
        assertEquals(4, aggregatedDictionary.get(4).size());
        assertEquals(3, aggregatedDictionary.get(6).size());
        assertEquals(2, aggregatedDictionary.get(7).size());
        assertEquals(1, aggregatedDictionary.get(8).size());
        assertNull(aggregatedDictionary.get(20));

        String test = "e";
        assertTrue(aggregatedDictionary.get(1).contains(test));
        test = "o";
        assertTrue(aggregatedDictionary.get(1).contains(test));
        test = "a";
        assertTrue(aggregatedDictionary.get(1).contains(test));
        test = "i";
        assertTrue(aggregatedDictionary.get(1).contains(test));
        test = "il";
        assertTrue(aggregatedDictionary.get(2).contains(test));
        test = "la";
        assertTrue(aggregatedDictionary.get(2).contains(test));
        test = "le";
        assertTrue(aggregatedDictionary.get(2).contains(test));
        test = "si";
        assertTrue(aggregatedDictionary.get(2).contains(test));
        test = "ciao";
        assertTrue(aggregatedDictionary.get(4).contains(test));
        test = "anni";
        assertTrue(aggregatedDictionary.get(4).contains(test));
        test = "nodo";
        assertTrue(aggregatedDictionary.get(4).contains(test));
        test = "mare";
        assertTrue(aggregatedDictionary.get(4).contains(test));
        test = "cinque";
        assertTrue(aggregatedDictionary.get(6).contains(test));
        test = "sempre";
        assertTrue(aggregatedDictionary.get(6).contains(test));
        test = "scuola";
        assertTrue(aggregatedDictionary.get(6).contains(test));
        test = "armadio";
        assertTrue(aggregatedDictionary.get(7).contains(test));
        test = "cavallo";
        assertTrue(aggregatedDictionary.get(7).contains(test));
        test = "tavolino";
        assertTrue(aggregatedDictionary.get(8).contains(test));
    }

    @Test
    public void findPlausibleCorrectionsTest(){
        String[] dictionary = {
                "e", "o", "a", "i",
                "il", "la", "le", "si",
                "ciao", "anni", "nodo", "mare",
                "cinque", "sempre", "scuola",
                "cavallo", "armadio",
                "tavolino"
        };
        Map<Integer, List<String>> aggregatedDictionary = LennonDiff.aggregateDictionary(dictionary);
        EditDistanceResult result;

        String test = "u";
        result = LennonDiff.findPlausibleCorrections(test,aggregatedDictionary);
        assertEquals(1, result.getDistance());
        assertEquals("u", result.getOriginal());
        assertEquals(4,result.getWords().size());
        assertTrue(result.getWords().contains("e"));
        assertTrue(result.getWords().contains("o"));
        assertTrue(result.getWords().contains("a"));
        assertTrue(result.getWords().contains("i"));

        test = "cinqve";
        result = LennonDiff.findPlausibleCorrections(test,aggregatedDictionary);
        assertEquals(1, result.getDistance());
        assertEquals("cinqve", result.getOriginal());
        assertEquals(1,result.getWords().size());
        assertTrue(result.getWords().contains("cinque"));

        test = "tavlinop";
        result = LennonDiff.findPlausibleCorrections(test,aggregatedDictionary);
        assertEquals(2, result.getDistance());
        assertEquals("tavlinop", result.getOriginal());
        assertEquals(1,result.getWords().size());
        assertTrue(result.getWords().contains("tavolino"));

        test = "nodo";
        result = LennonDiff.findPlausibleCorrections(test,aggregatedDictionary);
        assertEquals(0, result.getDistance());
        assertEquals("nodo", result.getOriginal());
        assertEquals(1,result.getWords().size());
        assertTrue(result.getWords().contains("nodo"));


    }


}
