package Item.Single;

import Item.SingleItem;
import Item.iSingleItem;
import Player.Player;

public class AidKit extends SingleItem implements iSingleItem {

    public AidKit(String id, String name, String info, int maxNumberOfUses) {
        super(id, name, info, maxNumberOfUses);
    }

    @Override
    public void use(Player player){
        player.heal(5);
        super.onUse(player);
    }
}
