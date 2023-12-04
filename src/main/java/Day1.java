import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;


public class Day1 {

    private static final Map<String, Integer> NUMBER_MAP = new TreeMap<>();
    static {
      NUMBER_MAP.put("one", 1);
      NUMBER_MAP.put("two", 2);
      NUMBER_MAP.put("three", 3);
      NUMBER_MAP.put("four", 4);
      NUMBER_MAP.put("five", 5);
      NUMBER_MAP.put("six", 6);
      NUMBER_MAP.put("seven", 7);
      NUMBER_MAP.put("eight", 8);
      NUMBER_MAP.put("nine", 9);
    };

    public static void main(String[] args) {

        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/Day1.txt"))) {
            String[] input = reader
                    .lines()
                    .toArray(String[]::new);

            long result = calibrator(input);

            System.out.println(result);


        } catch (IOException ex){
            ex.printStackTrace();
        }

    }

    public static long calibrator(String[] calibration){
        long sum = 0;

        for(String numberLine: calibration){
            int first = 0;
            int last = 0;
            first = getFirst(numberLine);
            last = getLast(numberLine);

            sum += Integer.parseInt(String.format("%d%d", first, last));
        }

        return sum;
    }

    private static int getFirst(String numberLine) {
        String previous = null;
        int first = 0;
        for (var entry: NUMBER_MAP.entrySet()){
            if (previous == null){
                if (numberLine.contains(entry.getKey())){
                    previous = entry.getKey();
                    first = entry.getValue();
                }

                if (numberLine.contains(String.valueOf(entry.getValue()))){
                    previous = String.valueOf(entry.getValue());
                    first = entry.getValue();
                }
            }
                if (numberLine.contains(entry.getKey())
                        && numberLine.indexOf(previous) > numberLine.indexOf(entry.getKey())){
                    previous = entry.getKey();
                    first = entry.getValue();
                }
                if (numberLine.contains(String.valueOf(entry.getValue()))
                        && numberLine.indexOf(previous) > numberLine.indexOf(String.valueOf(entry.getValue()))){
                    previous = String.valueOf(entry.getValue());
                    first = entry.getValue();
                }
        }
        return first;
    }

    private static int getLast(String numberLine) {
        String previous = null;
        int last = 0;
        for (var entry: NUMBER_MAP.entrySet()){
            if (previous == null){
                if (numberLine.contains(entry.getKey())){
                    previous = entry.getKey();
                    last = entry.getValue();
                }

                if (numberLine.contains(String.valueOf(entry.getValue()))){
                    previous = String.valueOf(entry.getValue());
                    last = entry.getValue();
                }
            }

            if (numberLine.contains(entry.getKey())
                    && numberLine.lastIndexOf(previous) < numberLine.lastIndexOf(entry.getKey())) {
                previous = entry.getKey();
                last = entry.getValue();
            }
            if (numberLine.contains(String.valueOf(entry.getValue()))
                    && numberLine.lastIndexOf(previous) < numberLine.lastIndexOf(String.valueOf(entry.getValue()))) {
                previous = String.valueOf(entry.getValue());
                last = entry.getValue();
            }
        }
        return last;
    }
}