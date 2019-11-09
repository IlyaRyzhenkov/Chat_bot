package Dice;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.*;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DiceTest {

    private int[] diceTypes;
    int timesToTest;
    Dice dice;

    public DiceTest() {
        diceTypes = new int[] {2, 4, 6, 8, 10, 12, 20, 100};
        timesToTest = 100;
        dice = new Dice();
    }

    @Test
    public void diceRollTest() {
        for(int t: diceTypes) {
            int[] results = new int[t];
            for(int i = 0; i < results.length; i++)
                results[i] = 0;
            for(int i = 0; i < Math.pow(t, 2) * timesToTest; i++)
                results[dice.roll(t) - 1]++;
            for (int result : results)
                assertFalse(0 == result);


        }
    }

    @Test
    public void rollAllTest() {
        for(int i = 0; i < timesToTest; i++) {
            StringBuilder builder = new StringBuilder();
            int len = dice.d10();
            int[] dType = new int[len];
            int[] dCount = new int[len];
            for(int j = 0; j < len; j++) {
                int n = dice.d20();
                dCount[j] = n;
                builder.append(n);
                builder.append("d");
                n = dice.d8() - 1;
                dType[j] = n;
                builder.append(diceTypes[n]);
            }
            int[] actual = dice.rollAll(builder.toString());
            int expectedCount = 0;
            for(int c: dCount) expectedCount += c;
            assertEquals(expectedCount, actual.length);
            int n = 0;
            for(int t = 0; t < dType.length; t++) {
                for(int c = 0; c < dCount[t]; c++) {
                    assertTrue("Fail on rolling d" + diceTypes[dType[t]] + ", actual res = " + actual[n],(actual[n] >= 1) && (actual[n] <= diceTypes[dType[t]]));
                    n++;
                }
            }
        }
    }

    @Test
    public void rollTest() {
        for(int t: diceTypes) {
            for(int i = 0; i < Math.pow(t, 2) * timesToTest; i++) {
                int res = dice.roll(t);
                assertTrue((res >= 1) && (res <= t));
            }
        }
    }
}