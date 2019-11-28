package Item.Single;

import Item.SingleItem;
import Item.ISingleItem;
import Player.Player;

public class TestItem extends SingleItem implements ISingleItem {

    public TestItem(String id, String name, String info, int maxNumberOfUses) {
        super(id, name, info, maxNumberOfUses);
    }

    @Override
    public void use(Player player) {
        super.onUse(player);
    }
}
