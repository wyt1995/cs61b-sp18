import java.util.Arrays;

/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        String[] aux = new String[asciis.length];
        for (int i = 0; i < asciis.length; i++) {
            aux[i] = asciis[i];
        }

        int maxLength = 0;
        for (int i = 0; i < asciis.length; i++) {
            maxLength = Math.max(maxLength, asciis[i].length());
        }

        for (int i = 0; i < maxLength; i++) {
            sortHelperLSD(aux, maxLength - i - 1);
        }
        return aux;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        // LSD helper method for required LSD radix sort
        int max = 0;
        int[] letters = new int[asciis.length];
        for (int i = 0; i < asciis.length; i++) {
            String s = asciis[i];
            if (index < s.length()) {
                letters[i] = s.charAt(index);
                max = Math.max(max, s.charAt(index));
            } else {
                letters[i] = 0;
            }
        }
        int len = max + 1;

        int[] counts = new int[len];
        for (int letter : letters) {
            counts[letter] += 1;
        }
        for (int i = 1; i < len; i++) {
            counts[i] += counts[i - 1];
        }

        String[] sorted = new String[asciis.length];
        for (int i = asciis.length - 1; i >= 0; i--) {
            int item = letters[i];
            int place = counts[item] - 1;
            sorted[place] = asciis[i];
            counts[item] -= 1;
        }

        // copy sorted items back to the original array
        for (int i = 0; i < asciis.length; i++) {
            asciis[i] = sorted[i];
        }
    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        return;
    }

    public static void main(String[] args) {
        String[] arr = {"Hello", "counting", "sort", "radix", "lab", "100", "s", "@", "()"};
        String[] sorted = sort(arr);
        System.out.println(Arrays.toString(sorted));
    }
}
