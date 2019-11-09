package Checker;

import Checker.iChecker;
import Dice.Dice;

public class DiceChecker implements iChecker {

    Dice dice;

    public boolean check(String diceSequence, int difficulty) {
        return roll(diceSequence) >= difficulty;
    }

    public int roll(String diceSequence) {
        //TODO

        return 0;
    }
}
