package Item.Single;

import Item.SingleItem;
import Item.iSingleItem;
import Player.Player;

public class BigAidKit extends SingleItem implements iSingleItem{

    public BigAidKit() {
        super("Item/Single/BigAidKit", "Большая Аптечка", "Многоразовая, 3 использования. +5hp", 3);//TODO
    }

    @Override
    public void use(Player player){
        player.heal(5);
        super.onUse(player);
    }
}
