import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class Day3Test {

    @Test
    void partCheckerShouldReturnCorrectSum() {

        String[][] input = Arrays.stream("""
                467..114..
                ...*......
                ..35..633.
                ......#...
                617*......
                .....+.58.
                ..592.....
                ......755.
                ...$.*....
                .664.598..""".split(System.lineSeparator()))
                .map(line -> line.split(""))
                .toArray(String[][]::new);

        int result = 4361;
        int actual = Day3.partChecker(input);

        assertEquals(result, actual);
        }

        @Test
        void gearCheckerShouldReturnCorrectResult(){
            String[][] input = Arrays.stream("""
                467..114..
                ...*......
                ..35..633.
                ......#...
                617*......
                .....+.58.
                ..592.....
                ......755.
                ...$.*....
                .664.598..""".split(System.lineSeparator()))
                    .map(line -> line.split(""))
                    .toArray(String[][]::new);

            long expected = 467835;
            long result = Day3.checkLineForGear(input);

            assertEquals(expected, result);

        }
}