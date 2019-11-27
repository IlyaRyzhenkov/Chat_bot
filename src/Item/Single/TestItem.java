package Item.Single;

import Item.SingleItem;
import Item.iSingleItem;
import Player.Player;

public class TestItem extends SingleItem implements iSingleItem {

    public TestItem(String id, String name, String info, int maxNumberOfUses) {
        super(id, name, info, maxNumberOfUses);
    }

    @Override
    public void use(Player player) {
        super.onUse(player);
    }
}
