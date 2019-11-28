package Item;

import Player.Player;

public abstract class SingleItem extends Item implements ISingleItem {

    private int numberOfUses;
    private int maxNumberOfUses;

    public SingleItem(String id, String name, String info, int maxNumberOfUses) {
        super(id, name, info);
        this.maxNumberOfUses = maxNumberOfUses;
        this.numberOfUses = this.maxNumberOfUses;
    }

    public int getNumberOfUses() { return this.numberOfUses; }

    public int getMaxNumberOfUses() { return  this.maxNumberOfUses; }

    @Override
    public String getName() {
        return super.getName() + "(" + this.getNumberOfUses() + "/" + this.getMaxNumberOfUses() + ")";
    }

    protected void onUse(Player player) {
        this.numberOfUses--;
        if(this.numberOfUses == 0)
            player.getInventory().removeItem(this);
    }

    public abstract void use(Player player);
}
