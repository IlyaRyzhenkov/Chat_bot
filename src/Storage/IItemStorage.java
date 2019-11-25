package Storage;
import Item.Item;

public interface IItemStorage {
    Item getById(String id);
}
