import parcs.*;

import java.util.ArrayList;
import java.util.List;

public class BoyerMoore implements AM {
    int badchar[];

    //A utility function to get maximum of two integers
    static int max(int a, int b) {
        return (a > b) ? a : b;
    }

    // A pattern searching function that uses Bad Character Heuristic of Boyer Moore Algorithm
    public List<Integer> search(String text, String pattern) {
        List<Integer> result = new ArrayList<>();
        int m = pattern.length();
        int n = text.length();

        int s = 0;  // s is shift of the pattern with respect to text
        while (s <= (n - m)) {
            int j = m - 1;

            //reducing index of pattern while matching at this shift s */
            while (j >= 0 && pattern.charAt(j) == text.charAt(s + j) )
                j--;

            // the pattern is present -> j = -1
            if (j < 0) {
                result.add(s);
                // shift to last occurrence of the character
                s += (s + m < n) ? m - badchar[text.charAt(s + m)] : 1;

            } else
                s += max(1, j - badchar[text.charAt(s + j)]);
        }

        return result;
    }

    public void run(AMInfo info) {
        Input input = (Input) info.parent.readObject();
        String text = input.getText();
        String pattern = input.getPattern();

        System.out.println("Input : text = " + text + ", pattern = " + pattern);

        info.parent.write(search(text, pattern).toArray());
    }
}
