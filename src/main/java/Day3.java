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
            long result2 = checkLineForGear(input);
            System.out.println(result2);
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
            for (int k = j; isNumeric(schematic[i][k]) && k < schematic[i].length; k++) {
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

    public static long checkLineForGear(String[][] schematic) {
        List<Pair> gearList = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        Pair gearPairs = new Pair();

        for (int column = 0; column < schematic.length; column++) {
            builder.setLength(0);
            int skip = 0;
            for (int row = 0; row < schematic[column].length;) {
                if (GEAR.matcher(schematic[column][row]).matches()) {
                    checkGearLeft(schematic, column, row, builder);
                    checkBuilder(builder, gearPairs, gearList);
                    skip = checkGearRight(schematic, column, row, builder);
                    checkBuilder(builder, gearPairs, gearList);
                    checkGearAboveAndBelow(gearPairs, gearList, schematic, column, row, builder, Position.ABOVE);
                    checkBuilder(builder, gearPairs, gearList);
                    checkGearAboveAndBelow(gearPairs, gearList, schematic, column, row, builder, Position.BELOW);
                    checkBuilder(builder, gearPairs, gearList);
                    gearPairs.clear();
                }
                if (skip > 0){
                    row += skip + 1;
                    skip = 0;
                }
                else {
                    row++;
                }
            }
        }
        return gearList
                .stream()
                .mapToLong(Pair::getRatio)
                .sum();
    }

    private static void checkBuilder(StringBuilder builder, Pair gearPairs, List<Pair> gearList) {
        if (!builder.isEmpty()) {
            if (gearPairs.isFirstSet()) {
                gearPairs.setSecond(Integer.parseInt(builder.toString()));
                builder.setLength(0);
            } else {
                gearPairs.setFirst(Integer.parseInt(builder.toString()));
                builder.setLength(0);
            }
        }

        if (gearPairs.isFirstSet() && gearPairs.isSecondSet()) {
            gearList.add(new Pair(gearPairs));
            gearPairs.clear();
        }
    }

    private static int checkGearRight(String[][] schematic, int column, int row, StringBuilder builder) {
        int skip = 0;
        if (row < schematic[column].length - 1 && isNumeric(schematic[column][row + 1])) {
            for (int k = row + 1; isNumeric(schematic[column][k]) && k < schematic[column].length; k++) {
                builder.append(schematic[column][k]);
                skip++;
            }
        }
        return skip;
    }

    private static void checkGearLeft(String[][] schematic, int column, int row, StringBuilder builder) {
        if (row > 0 && isNumeric(schematic[column][row - 1])) {
            for (int i = 0; i < row; i++) {
                if (!isNumeric(schematic[column][i]) && !builder.isEmpty()) {
                    builder.setLength(0);
                }
                if (isNumeric(schematic[column][i])) {
                    builder.append(schematic[column][i]);
                }
            }
        }
    }

    private static void checkGearAboveAndBelow(Pair gearPairs, List<Pair> gearList, String[][] schematic,
                                               int column,
                                               int row,
                                               StringBuilder builder,
                                               Position position) {


        if (column > 0 && position == Position.BELOW) {
            if (row > 0 && !isNumeric(schematic[column+1][row]) && isNumeric(schematic[column+1][row + 1]) && isNumeric(schematic[column+1][row - 1])){
                int begin = getIndex(row - 1, Operators.DECREMENT, schematic[column + 1]);
                int end = getIndex(row - 1, Operators.INCREMENT, schematic[column + 1]);
                while (begin <= end) {
                    builder.append(schematic[column + 1][begin]);
                    begin++;
                }
                checkBuilder(builder, gearPairs, gearList);
                begin = getIndex(row + 1, Operators.DECREMENT, schematic[column + 1]);
                end = getIndex(row + 1, Operators.INCREMENT, schematic[column + 1]);
                while (begin <= end) {
                    builder.append(schematic[column + 1][begin]);
                    begin++;
                }
                checkBuilder(builder, gearPairs, gearList);
                return;
            }
            int index = getClosestNumberIndex(schematic[column + 1], row);
            if (Math.abs(index - row) < 2) {
                int begin = getIndex(index, Operators.DECREMENT, schematic[column + 1]);
                int end = getIndex(index, Operators.INCREMENT, schematic[column + 1]);
                while (begin <= end) {
                    builder.append(schematic[column + 1][begin]);
                    begin++;
                }
            }
        }
        if (column < schematic.length && position == Position.ABOVE) {
            if (row < schematic[column].length && !isNumeric(schematic[column-1][row]) && isNumeric(schematic[column-1][row + 1]) && isNumeric(schematic[column-1][row - 1])){
                int begin = getIndex(row - 1, Operators.DECREMENT, schematic[column - 1]);
                int end = getIndex(row - 1, Operators.INCREMENT, schematic[column -1]);
                while (begin <= end) {
                    builder.append(schematic[column - 1][begin]);
                    begin++;
                }
                checkBuilder(builder, gearPairs, gearList);
                begin = getIndex(row + 1, Operators.DECREMENT, schematic[column - 1]);
                end = getIndex(row + 1, Operators.INCREMENT, schematic[column - 1]);
                while (begin <= end) {
                    builder.append(schematic[column - 1][begin]);
                    begin++;
                }
                checkBuilder(builder, gearPairs, gearList);
                return;
            }
            int index = getClosestNumberIndex(schematic[column - 1], row);
            if (Math.abs(index - row) < 2) {
                int begin = getIndex(index, Operators.DECREMENT, schematic[column - 1]);
                int end = getIndex(index, Operators.INCREMENT, schematic[column - 1]);
                while (begin <= end) {
                    builder.append(schematic[column - 1][begin]);
                    begin++;
                }
            }
        }
    }

    private static int getClosestNumberIndex(String[] schematic, int row) {
        int index = row;

            while (index < schematic.length && !isNumeric(schematic[index])){
                index++;
            }

        if (Math.abs(row - index) > 1) {
            index = row;
            while (index >= 0 && !isNumeric(schematic[index])) {
                --index;
            }
        }

        return index;
    }
}

