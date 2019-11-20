package Item.Outfitted;

import Item.Weapon;

import java.util.HashMap;

public class Screwdriver extends Weapon {
    public Screwdriver() {
        super("Item/Outfitted/Weapon/ScrewDriver", "Отвёртка", "Обычная отвёртка. +1 к силе",
                new HashMap<String, Integer>() {
                    {
                        put("strength", 1);
                    }
                });
    }
}
