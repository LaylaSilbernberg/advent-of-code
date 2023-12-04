import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day1Test {

    @Test
    void shouldReturnCorrectSum() {
     String[] inputString = """
             two1nine
             eightwothree
             abcone2threexyz
             xtwone3four
             4nineeightseven2
             zoneight234
             7pqrstsixteen""".split(System.lineSeparator());
     long result = 281;
     long actual = Day1.calibrator(inputString);

     assertEquals(result, actual);
    }

}