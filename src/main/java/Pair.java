import java.util.Objects;

public class Pair {
    private int first;
    private int second;

    public Pair() {
    }

    public Pair(int first, int second) {
        this.first = first;
        this.second = second;
    }
    public Pair(Pair pair){
        this(pair.first, pair.second);
    }

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public long getRatio(){
        return (long) first * second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair pair = (Pair) o;
        return first == pair.first && second == pair.second;
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

    public boolean isFirstSet(){
        return first != 0;
    }
    public boolean isSecondSet(){
        return second != 0;
    }

    public void clear(){
        first = 0;
        second = 0;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "first=" + first +
                ", second=" + second +
                '}';
    }
}
