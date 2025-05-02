import java.util.*;

public class RecursivePermutationGenerator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a string: ");
        String input = scanner.nextLine();
        scanner.close();

        if (input == null || input.isEmpty()) {
            System.out.println("Input cannot be empty.");
            return;
        }

        List<String> permutations = new ArrayList<>();
        generatePermutationsRecursive("", input, permutations);

        System.out.println("Permutations:");
        for (String perm : permutations) {
            System.out.println(perm);
        }
    }

    // Recursive method to generate permutations
    public static void generatePermutationsRecursive(String prefix, String remaining, List<String> result) {
        if (remaining.length() == 0) {
            result.add(prefix);
        } else {
            for (int i = 0; i < remaining.length(); i++) {
                String newPrefix = prefix + remaining.charAt(i);
                String newRemaining = remaining.substring(0, i) + remaining.substring(i + 1);
                generatePermutationsRecursive(newPrefix, newRemaining, result);
            }
        }
    }
}
