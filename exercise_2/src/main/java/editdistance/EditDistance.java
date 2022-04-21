package editdistance;

import com.sun.istack.internal.NotNull;

import java.util.Arrays;

/*
 *  EditDistance is used to get the minimum edit distance between strings
 */
public class EditDistance {

    /**
     * Calculates the edit distance between two words.
     * This is an unoptimized version and should not be used.
     *
     * @param s1 The first string to compare
     * @param s2 The second string to compare
     * @return The minimum edit distance between two words.
     */
    @Deprecated
    public static int editDistance(@NotNull String s1, @NotNull String s2) {
        int dcanc,   // dim canc
                dins,   // dim ins
                drep, // dim replace
                noop;  // no operation

        if (s1.length() == 0) {
            return s2.length();
        } else if (s2.length() == 0) {
            return s1.length();
        } else {
            /*if (s1.charAt(0) == s2.charAt(0))
                return editDistance(s1.substring(1), s2.substring(1));*/
            noop = editDistance(s1.substring(1), s2.substring(1));

            dcanc = 1 + editDistance(s1, s2.substring(1));
            /*if (dcanc == 1) {
                return dcanc;
            }*/

            dins = 1 + editDistance(s1.substring(1), s2);
            /*if (dins == 1) {
                return dcanc;
            }*/

            drep = 1 + editDistance(s1.substring(1), s2.substring(1));
            /*if (drep == 1) {
                return drep;
            }*/

            /* min between: no-op, canc, ins and replace */
            return Math.min(Math.min(noop, dcanc), Math.min(dins, drep));
        } // endif
    } // editDistance

    /**
     * Calculates the edit distance between two words.
     * This implementation uses memoization techniques to optimize the search.
     *
     * @param s1 The first string to compare
     * @param s2 The second string to compare
     * @return The minimum edit distance between two words.
     */
    public static int editDistanceDyn(String s1, String s2) {

        // Check preconditions: the two strings aren't empty (which implies not null).
        if (s1 == null) {
            if (s2 == null) {
                return 0;
            } else {
                return s2.length();
            }
        } else {
            if (s2 == null) {
                return s1.length();
            }
        }

        // If the strings are the same the ED is 0. O(n) where
        if (s1.equals(s2)) {
            return 0;
        }

        // Matrix of calculated edit distances.
        // If it hasn't already been computed, the ED is 0
        int[][] memo = new int[s1.length() + 1][s2.length() + 1];

        for (int[] line : memo) {
            Arrays.fill(line, -1);
        }

        return editDistanceDyn_wrapped(s1, s2, memo);
    }

    private static int editDistanceDyn_wrapped(String s1, String s2, int[][] memo) {
        // Check if the value we're searching has already been calculated. In this case we return the value in O(1)
        if (memo[s1.length()][s2.length()] != -1) {
            return memo[s1.length()][s2.length()];
        } else if (s1.length() == 0) {
            return memo[s1.length()][s2.length()] = s2.length();
        } else if (s2.length() == 0) {
            return memo[s1.length()][s2.length()] = s1.length();
        } else {
            // If the current character is the same, we just remove it and run again the method. O(min(s1.l, s2.l))
            if (s1.charAt(0) == s2.charAt(0)) {
                return memo[s1.length()][s2.length()] = editDistanceDyn_wrapped(s1.substring(1), s2.substring(1), memo);
            }

            // If after removing a character we discover that the remaining edit distance is 0, this will be the best
            // edit distance that we will ever find. The same idea is applied to the other two checks.
            int dcanc = 1 + editDistanceDyn_wrapped(s1, s2.substring(1), memo);
            if (dcanc == 1) {
                return memo[s1.length()][s2.length()] = dcanc;
            }

            int dins = 1 + editDistanceDyn_wrapped(s1.substring(1), s2, memo);
            if (dins == 1) {
                return memo[s1.length()][s2.length()] = dins;
            }

            int drep = 1 + editDistanceDyn_wrapped(s1.substring(1), s2.substring(1), memo);
            if (drep == 1) {
                return memo[s1.length()][s2.length()] = drep;
            }

            /* min between: no-op, canc, ins and replace */
            return memo[s1.length()][s2.length()] = Math.min(dcanc, Math.min(dins, drep));
        }
    }

} //class EditDistance