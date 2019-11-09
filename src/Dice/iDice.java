package Dice;

public interface iDice {
    int d2();
    int d4();
    int d6();
    int d8();
    int d10();
    int d12();
    int d20();
    int d100();
    int[] rollAll(String dices);
    int roll(int sides);
}
