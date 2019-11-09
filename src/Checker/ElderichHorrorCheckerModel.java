package Checker;

import Checker.iChecker;
import Dice.Dice;

public class ElderichHorrorCheckerModel implements iChecker {

    private Dice dice;

    public ElderichHorrorCheckerModel() {
        dice = new Dice();
    }
    public boolean check(String diceSequence, int difficulty) {
        return roll(diceSequence) >= difficulty;
    }

    public int roll(String diceSequence) {
        int n = 0;
        for(int res: dice.rollAll(diceSequence))
            n += res >= 5 ? 1 : 0;
        return n;
    }
}
