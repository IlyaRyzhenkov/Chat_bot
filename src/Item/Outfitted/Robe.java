package Item.Outfitted;

import Item.Suit;

import java.util.Collections;
import java.util.HashMap;

public class Robe extends Suit {
    public Robe() {
        super("Item/Outfitted/Robe", "Рабочая одежда", "Обычная рабочая одежда. +1 к силе",
                new HashMap<String, Integer>(){
                    {
                        put("strength", 1);
                    }
                });
    }
}
