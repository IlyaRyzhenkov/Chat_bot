package AlternativeInteractionControllers.FightController;

import Player.Player;
import Checker.*;

import java.util.HashMap;

public class Battle {
    private Player attacker;
    private Player defender;
    private String attackAttribute;
    private String defenceAttribute;
    private int dmg;

    private IChecker checker;
    private HashMap<String, HashMap<String, Integer>> rps;

    public Battle(Player p1, Player p2) {
        if(Math.random() > 0.5) {
            attacker = p1;
            defender = p2;
        }
        else {
            attacker = p2;
            defender = p1;
        }
        rps = new HashMap<String, HashMap<String, Integer>>() {
            {
                put("agility", new HashMap<String, Integer>() {
                    {
                        put("strength", -2);
                        put("attention", 2);
                    }
                });
                put("strength", new HashMap<String, Integer>() {
                    {
                        put("attention", -2);
                        put("agility", 2);
                    }
                });
                put("attention", new HashMap<String, Integer>() {
                    {
                        put("agility", -2);
                        put("strength", 2);
                    }
                });
            }
        };
        attackAttribute = "";
        defenceAttribute = "";
        checker = new EldritchHorrorChecker();
    }

    public String setAttribute(Player player, String attribute) {
        if(player.equals(attacker)) {
            attackAttribute = attribute;
            String s = "Вы решаете нанести ";
            switch (attribute) {
                case("attention"):
                    return s + "точный удар. [Внимание]";
                case("strength"):
                    return s + "сильный удар. [Сила]";
                case("agility"):
                    return s + "быстрый удар. [Ловкость]";
                default:
                    attackAttribute = "";
                    return "ERROR";
            }
        }
        if(player.equals(defender)) {
            defenceAttribute = attribute;
            String s = "Вы решаете ";
            switch (attribute) {
                case("attention"):
                    return s + "парировать удар. [Внимание]";
                case("strength"):
                    return s + "блокировать удар. [Сила]";
                case("agility"):
                    return s + "уклониться. [Ловкость]";
                default:
                    defenceAttribute = "";
                    return "ERROR";
            }
        }
        return "ERROR";
    }

    public boolean isBattlePhaseReady() {
        return (attackAttribute.compareTo("") != 0) && (defenceAttribute.compareTo("") != 0);
    }

    public void fight() {
        int attackValue = checker.roll(attacker.getAttribute(attackAttribute)
                + rps.get(defenceAttribute).getOrDefault(attackAttribute,0) + "d6");
        int defenceValue = checker.roll(defender.getAttribute(defenceAttribute)
                + rps.get(attackAttribute).getOrDefault(defenceAttribute, 0) + "d6");
        dmg = Math.max(0, attackValue - defenceValue);
        defender.hit(dmg);
        Player p = defender;
        defender = attacker;
        attacker = p;

    }

    public String getEventIdByPlayerRole(Player player) {
        if(player.equals(attacker)) return "Battle/attack";
        if(player.equals(defender)) return "Battle/defence";
        return "Exceptions/damaged_file";
    }

    public String getAttackerMessage() {
        return "Вы проводите " + (dmg == 0 ? "без" : "") + "успешную атаку. "
                + (attacker.getInventory().getWeapon() == null ? "Ваша рука" : attacker.getInventory().getWeapon().getName())
                + " - это последнее, что видит ваш оппонент, прежде чем получить " + dmg + " урона. У него остаётся "
                + defender.getHp() + "hp.";
    }

    public String getDefenderMessage() {
        return "Вам " + (dmg == 0 ? "" : "не ") + "удаётся избежать атаки. Вы плучаете " + dmg + " урона. Ваше здоровье: " + defender.getHp() + "hp.";
    }

    public Player getAttacker() { return attacker; }

    public Player getDefender() { return defender; }
}
