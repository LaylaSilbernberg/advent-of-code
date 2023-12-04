import java.io.*;
import java.nio.file.Path;
import java.util.stream.Stream;

public class Day2 {

    private static final int BLUE_TARGET = 14;
    private static final int GREEN_TARGET = 13;
    private static final int RED_TARGET = 12;

    public static void main(String[] args) {

        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/Day2.txt"))) {
            Stream<String> input = reader.lines();
            int result = gameCounter(input);
            System.out.println(result);
        } catch (IOException ex){
            ex.printStackTrace();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/Day2.txt"))){
            Stream<String> input2 = reader.lines();
            long result2 = powerCounter(input2);
            System.out.println(result2);
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public static int gameCounter(Stream<String> lines){

        return lines
                .filter(Day2::cubesAreFitting)
                .mapToInt(line -> {
                    String[] split = line.substring(0, line.indexOf(":")).split(" ");
                    return Integer.parseInt(split[1]);
                })
                .sum();

    }

    public static boolean cubesAreFitting(String line){
        String relevantGameInfo = line.substring(line.indexOf(":")+1);
        String[] cubes = relevantGameInfo.split(";");

        for(String cube: cubes){
            boolean blue = cubeHasCorrectAmount(cube, "blue", BLUE_TARGET);
            boolean red = cubeHasCorrectAmount(cube, "red", RED_TARGET);
            boolean green = cubeHasCorrectAmount(cube, "green", GREEN_TARGET);

            if(!areCubesEnough(blue, red, green)){
                return false;
            }
        }
    return true;
    }

    private static boolean areCubesEnough(boolean blue, boolean red, boolean green) {
        return blue && red && green;
    }

    private static boolean cubeHasCorrectAmount(String cube, String colour, int target){
        if(cube.contains(colour) && Character.isDigit(cube.charAt(cube.indexOf(colour)-3))){

            int number = Integer
                    .parseInt(cube.substring(cube.indexOf(colour)-3, cube.indexOf(colour)-1));

            return number <= target;
        }
        return true;
    }

    public static long powerCounter(Stream<String> lines){
        return lines
                .mapToLong(Day2::powerDeterminer)
                .sum();
    }

    public static long powerDeterminer(String line){
        int redMax = colourDeterminer(line, "red");
        int blueMax = colourDeterminer(line, "blue");
        int greenMax = colourDeterminer(line, "green");

        return (long) redMax * blueMax * greenMax;
    }

    public static int colourDeterminer(String line, String colour){
        int colourMax = 0;

        for(String segment: line.substring(line.indexOf(":") + 1).split(";")){
            if (segment.contains(colour)){
                int beginIndex = segment.indexOf(colour) - 3;
                if (Character.isDigit(segment.charAt(beginIndex))){
                    int endIndex = segment.indexOf(colour) - 1;
                    int doubleDigit = Integer.parseInt(segment.substring(beginIndex, endIndex));
                    if (doubleDigit > colourMax){
                        colourMax = doubleDigit;
                    }
                } else {
                    int charIndex = segment.indexOf(colour) - 2;
                    int singleDigit = Character.getNumericValue(segment.charAt(charIndex));
                    if (singleDigit > colourMax){
                        colourMax = singleDigit;
                    }
                }
            }
        }
        return colourMax;
    }
}
