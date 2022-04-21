import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import editdistance.EditDistance;
import editdistance.EditDistanceResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class LennonDiff {
    public static void main(String[] args) throws IOException {
        HashMap<String, EditDistanceResult> distanceResultHashMap = new HashMap<>();

        // todo add checks to avoid throwing the same errors avoided below
        String[] dictionary = Files.lines(Paths.get(args[1])).toArray(String[]::new);

        // Initialize the dictionarySet. It will be used to check if a word is written correctly, to avoid computing
        // the edit distance.
        Set<String> dictionarySet = new HashSet<>(Arrays.asList(dictionary));

        // This dictionary is used to avoid computing groups of dictionary words that would surely give an edit distance
        // greater than the one calculated.
        Map<Integer, List<String>> aggregatedDictionary = aggregateDictionary(dictionary);

        List<String> text = new ArrayList<>();

        // This lambda expression loads every line of the file that has to be corrected.
        // Every line is split whenever any character that isn't a lowercase letter is found.
        Files.readAllLines(Paths.get(args[0])).forEach(
                line -> text.addAll(Arrays.asList(line.toLowerCase().split("[^a-z]+")))
        );

        EditDistanceResult[] results = new EditDistanceResult[text.size()];

        long startMillisED, totalMillis = 0;
        int counter = 0;

        for (String current : text) {

            startMillisED = System.currentTimeMillis();

            EditDistanceResult result;

            if (distanceResultHashMap.containsKey(current)) {
                result = distanceResultHashMap.get(current);
            } else {
                // First look for the word in the dictionary to save time (?)
                result = tryToFindWordInDictionaryHash(current, dictionarySet);

                // If we cannot find a match, then we find the edit distance.
                if (result == null) {
                    result = findPlausibleCorrections(current, aggregatedDictionary);
                }

                distanceResultHashMap.put(current, result);
            }

            totalMillis += System.currentTimeMillis() - startMillisED;
            results[counter++] = result;
        }

        for (EditDistanceResult result : results) {
            System.out.println(result + "\n");
        }

        System.out.println("Search completed in " + totalMillis + " milliseconds.");
    }

    public static EditDistanceResult tryToFindWordInDictionaryHash(String word, Set<String> dictionarySet) {
        if (dictionarySet.contains(word)) {
            ArrayList<String> result = new ArrayList<>();
            result.add(word);
            return new EditDistanceResult(result, word, 0);
        } else {
            return null;
        }
    }

    public static @Nullable
    Map<Integer, List<String>> aggregateDictionary(@NotNull String[] dictionary) {
        Map<Integer, List<String>> ad = new HashMap<>();

        // We iterate through the dictionary in order to aggregate all the words with the same length.
        // The complexity of this implementation is O(2n) where n = dictionary.length, since both methods containsKey()
        // and get() take time O(1) and they are called for each dictionary element (n+n times)
        // An alternative would be to use an ordered array and iterate through it, however it would take O(n log(n))
        // since the worst time is of the ordering algorithm (if qsort is used)
        for (String word : dictionary) {
            // If the list for word's length doesn't exist, we add it now.
            if (!ad.containsKey(word.length())) {
                ad.put(word.length(), new ArrayList<>());
            }

            ad.get(word.length()).add(word);
        }

        return ad;
    }

    /**
     * This method compares a specified word to a list of words received through a Scanner.
     * By doing so, it will find a list with all the words which have the same smallest edit distance.
     *
     * @param word       The which has to be compared
     * @param dictionary The dictionary used
     * @return A list with all the words with the same smallest edit distance.
     */
    public static @NotNull
    EditDistanceResult findPlausibleCorrections(@NotNull String word, @NotNull Map<Integer, List<String>> dictionary) {
        // Added list initializer to avoid warnings about uninitialized lists.
        List<String> bestEditDistances = new ArrayList<>();
        int min = -1;

        for (Integer l : dictionary.keySet()) {

            if (min != -1 && l - word.length() > min) {
                continue;
            }

            for (String currentWord : dictionary.get(l)) {
                int editDistance = EditDistance.editDistanceDyn(word, currentWord);

                /* Firstly we compute the EditDistance for the current word of the dictionary.
                 * If the result is the smallest, we create a List of Strings to contain all the words
                 * in the dictionary with the same EditDistance.
                 */
                if (min == -1 || min > editDistance) {
                    bestEditDistances = new ArrayList<>();
                    bestEditDistances.add(currentWord);
                    min = editDistance;
                    // todo: check if with current edit distance we could avoid a group of dictionary words
                } else if (min == editDistance) {
                    bestEditDistances.add(currentWord);
                }

                if (editDistance == 0) {
                    /*
                     * If the EdiDistance calculated is 0, then the current word is the same word we are
                     * checking, so there isn't any word with a better EditDistance.
                     */

                    return new EditDistanceResult(bestEditDistances, word, min);
                }

                if (min != -1 && l - word.length() > min) {
                    break;
                }
            }

        }
        return new EditDistanceResult(bestEditDistances, word, min);
    }

}
