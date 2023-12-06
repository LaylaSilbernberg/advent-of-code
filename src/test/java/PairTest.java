import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PairTest {

    @Test
    void pairShouldBeMutable(){
        Pair expected = new Pair(20, 30);
        Pair result = new Pair(10, 20);
        pairMutationTest(result);
        assertEquals(expected, result);

    }

    void pairMutationTest(Pair pair){
        pair.setFirst(20);
        pair.setSecond(30);
    }

    @Test
    void pairWithPairConstructorShouldBeDifferentPairs(){
        Pair pair = new Pair(20, 30);
        Pair copy = new Pair(pair);

        assertNotSame(pair, copy);
    }

}