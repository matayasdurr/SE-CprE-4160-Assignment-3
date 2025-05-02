import java.util.*;

public class PermutationGenerator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a string: ");
        String input = scanner.nextLine();
        scanner.close();

        if (input == null || input.isEmpty()) {
            System.out.println("Input cannot be empty.");
            return;
        }

        List<String> permutations = generatePermutationsIterative(input);
        System.out.println("Permutations:");
        for (String perm : permutations) {
            System.out.println(perm);
        }
    }

    // Iterative method to generate permutations
    public static List<String> generatePermutationsIterative(String str) {
        List<String> result = new ArrayList<>();
        result.add(""); // start with empty string

        for (char c : str.toCharArray()) {
            List<String> temp = new ArrayList<>();
            for (String s : result) {
                for (int i = 0; i <= s.length(); i++) {
                    String perm = s.substring(0, i) + c + s.substring(i);
                    temp.add(perm);
                }
            }
            result = temp;
        }

        return result;
    }
}
