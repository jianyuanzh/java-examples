import java.util.ArrayList;
import java.util.List;

// larger than orgin
//
public class SwapNumber {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Usage: swapNumber <Number>");
            return;
        }

        int num = Integer.parseInt(args[0]);
        System.out.println("Input is " + num );
        System.out.println("Solution is " + findSolution(num));;
    }

    private static int findSolution(int num) {
        int origin = num;
        List<Integer> digits = new ArrayList<>();
        while (num != 0) {
            int mod = num % 10;
            digits.add(mod);
            num = num / 10;
        }

        boolean found = false;
        for (int i = 1; i < digits.size(); i++) {
            for (int j = 0; j < i; j++) {
                if (digits.get(j) > digits.get(i)) {
                    int tmp = digits.get(i);
                    digits.set(i, digits.get(j));
                    digits.set(j, tmp);
                    found = true;
                    break;
                }
            }
        }

        if (!found) {
            throw new IllegalArgumentException("No result found for  input " + origin);
        }

        int result = 0;
        for (int i = digits.size() -1; i >=0 ; i--) {
            result = result * 10 + digits.get(i);
        }

        return result;
    }

}
