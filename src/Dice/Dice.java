package Dice;
import IDice;

import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Dice implements IDice {

    private Random rand;

    public Dice() {
        rand = new Random();
    }

    @Override
    public int d2() {
        return rand.nextInt(2) + 1;
    }

    @Override
    public int d4() {
        return rand.nextInt(4) + 1;
    }

    @Override
    public int d6() {
        return rand.nextInt(6) + 1;
    }

    @Override
    public int d8() {
        return rand.nextInt(8) + 1;
    }

    @Override
    public int d10() {
        return rand.nextInt(10) + 1;
    }

    @Override
    public int d12() {
        return rand.nextInt(12) + 1;
    }

    @Override
    public int d20() {
        return rand.nextInt(20) + 1;
    }

    @Override
    public int d100() {
        return rand.nextInt(100) + 1;
    }

    @Override
    public int[] rollAll(String dices) {
        Pattern splitToDicesPattern = Pattern.compile("([1-9][\\d]*)d(20|4|6|8|100|12|2|10)");
        Matcher splitToDicesMatcher = splitToDicesPattern.matcher(dices);
        Pattern splitDices = Pattern.compile("[\\d]+");
        ArrayList<String> splitedDices = new ArrayList<String>();
        while(splitToDicesMatcher.find())
            splitedDices.add(splitToDicesMatcher.group());
        ArrayList<Integer> results = new ArrayList<Integer>();
        for (String splitedDice : splitedDices) {
            Matcher splitDicesMatcher = splitDices.matcher(splitedDice);
            int c = 0, t = 0;
            if (splitDicesMatcher.find())
                c = Integer.parseInt(splitDicesMatcher.group());
            if (splitDicesMatcher.find())
                t = Integer.parseInt(splitDicesMatcher.group());
            for (int i = 0; i < c; i++)
                results.add(roll(t));
        }
        int[] res = new int[results.size()];
        for(int i = 0; i < res.length; i++) res[i] = results.get(i);
        return res;
    }


    @Override
    public int roll(int sides) {
        switch (sides) {
            case 2: return d2();
            case 4: return d4();
            case 6: return d6();
            case 8: return d8();
            case 10: return d10();
            case 12: return d12();
            case 20: return d20();
            case 100: return d100();
            default: return 0;
        }
    }
}
