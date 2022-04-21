package editdistance;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.util.List;

/**
 * Contains all the strings with a specific edit distance.
 */
public class EditDistanceResult {
    private List<String> words;
    private String original;
    private int distance;

    public EditDistanceResult(@NotNull List<String> words, @NotNull String original, int distance) {
        this.words = words;
        this.original = original;
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "EditDistanceResult" +
                "\n\tOriginal Word = " + original +
                "\n\tEdit Distance = " + distance +
                "\n\tFound " + words.size() + " Word" + (words.size() != 1 ? 's' : "") + ":" + allString("\n\t\t- ");
    }

    /**
     * This method concatenates all words strings into a list. Every string is separated by a specified separator.
     *
     * @param separator The separator used between two strings (and before the first string)
     * @return A concatenated String.
     */
    private @NotNull
    String allString(@Nullable String separator) {
        StringBuilder finale = new StringBuilder();
        boolean primoCiclo = true;

        for (String s : words) {
            if (primoCiclo) {
                finale = new StringBuilder(separator);
                finale.append(s);
                primoCiclo = false;
            } else {
                finale.append(separator).append(s);
            }
        }

        return finale.toString();
    }

    public String getOriginal() {
        return this.original;
    }

    public List<String> getWords() {
        return this.words;
    }

    public int getDistance() {
        return this.distance;
    }
}
