import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

public class Day3 {
    private static final Pattern SPECIAL_CHARACTERS = Pattern.compile("[$/&+=@#*%-]");
    private static final Pattern GEAR = Pattern.compile("[*]");

    public static void main(String[] args) {

        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/Day3.txt"))) {
            String[][] input = reader
                    .lines()
                    .toList()
                    .stream()
                    .map(line -> line.split(""))
                    .toArray(String[][]::new);

            int result = partChecker(input);
            System.out.println(result);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public static int partChecker(String[][] schematic) {
        List<Integer> numberList = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < schematic.length; i++) {
            for (int j = 0; j < schematic[i].length; ) {
                builder.setLength(0);
                if (isNumeric(schematic[i][j])) {
                    checkLine(schematic, j, i, builder);
                    if (!builder.isEmpty()) {
                        String numerals = builder.toString();
                        numberList.add(Integer.parseInt(builder.toString()));
                    }
                }
                if (builder.isEmpty()) {
                    j++;
                } else {
                    while (j < schematic.length - 1 && isNumeric(schematic[i][j])) {
                        j++;
                    }
                }

            }

        }
        return numberList
                .stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    private static void checkLine(String[][] schematic, int j, int i, StringBuilder builder) {

        checkLeft(schematic, j, i, builder);
        checkRight(schematic, j, i, builder);
        if (i < schematic.length - 1) {
            boolean below = SPECIAL_CHARACTERS.matcher(schematic[i + 1][j]).matches();
            if (j > 0) {
                below = below || SPECIAL_CHARACTERS.matcher(schematic[i + 1][j - 1]).matches();
            }
            if (j < schematic[i].length - 1) {
                below = below || SPECIAL_CHARACTERS.matcher(schematic[i + 1][j + 1]).matches();
            }
            checkAboveOrBelow(schematic, j, i, builder, below);
        }
        if (i > 0) {
            boolean above = SPECIAL_CHARACTERS.matcher(schematic[i - 1][j]).matches();
            if (j > 0) {
                above = above || SPECIAL_CHARACTERS.matcher(schematic[i - 1][j - 1]).matches();
            }
            if (j < schematic[i].length - 1) {
                above = above || SPECIAL_CHARACTERS.matcher(schematic[i - 1][j + 1]).matches();
            }
            checkAboveOrBelow(schematic, j, i, builder, above);
        }

    }

    private static void checkAboveOrBelow(String[][] schematic,
                                          int j,
                                          int i,
                                          StringBuilder builder,
                                          boolean aboveOrBelow) {
        if (aboveOrBelow) {
            int begin = getIndex(j, Operators.DECREMENT, schematic[i]);
            int end = getIndex(j, Operators.INCREMENT, schematic[i]);

            while (begin < end + 1) {
                builder.append(schematic[i][begin]);
                begin++;
            }
        }
    }

    private static void checkRight(String[][] schematic, int j, int i, StringBuilder builder) {
        if (j < schematic[i].length - 1 && SPECIAL_CHARACTERS.matcher(schematic[i][j + 1]).matches()) {
            for (int k = 0; k <= j; k++) {
                if (!isNumeric(schematic[i][k]) && !builder.isEmpty()) {
                    builder.setLength(0);
                }
                if (isNumeric(schematic[i][k])) {
                    builder.append(schematic[i][k]);
                }
            }
        }
    }

    private static void checkLeft(String[][] schematic, int j, int i, StringBuilder builder) {
        if (j > 0 && SPECIAL_CHARACTERS.matcher(schematic[i][j - 1]).matches()) {
            for (int k = j; isNumeric(schematic[i][k]); k++) {
                builder.append(schematic[i][k]);
            }
        }
    }

    private static int getIndex(int j, Operators operator, String[] schematic) {
        int k = j;
        while (isIndexInBoundsAndNumeric(operator, schematic, k)) {
            if (operator == Operators.INCREMENT) {
                ++k;
            } else {
                --k;
            }
        }
        return k;
    }

    private static boolean isIndexInBoundsAndNumeric(Operators operator, String[] schematic, int k) {
        return (operator == Operators.INCREMENT
                ? k < schematic.length - 1
                : k > 0) && (operator == Operators.INCREMENT
                ? isNumeric(schematic[k + 1])
                : isNumeric(schematic[k - 1]));
    }


    private static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }



}
