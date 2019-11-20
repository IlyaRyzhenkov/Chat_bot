package Item.Single;

import Item.SingleItem;
import Item.iSingleItem;
import Player.Player;

public class AidKit extends SingleItem implements iSingleItem {

    public AidKit() {
        super("Item/Single/AidKit", "Аптечка", "Разовая. +5hp", 1);//TODO
    }

    @Override
    public void use(Player player){
        player.heal(5);
        super.onUse(player);
    }
}
