package Storage;

import Item.Item;
import Item.Single.TestItem;
import Item.Suit;
import Item.Weapon;
import Item.Accessory;
import Item.Single.AidKit;
import Parser.ItemParser.ItemParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class ItemStorage implements IItemStorage{
    public Item getById(String id) {
        ItemParser parser;
        try {
            parser = new ItemParser("items/" + id + ".json");
        } catch (IOException | ParseException e) {
            return null;
        }
        if (parser.getType().compareTo("single") == 0) {
            switch (parser.getId().split("/")[1]) {
                case ("AidKit"):
                case ("BigAidKit"):
                    return new AidKit(parser.getId(), parser.getName(), parser.getInfo(), parser.getMaxNumberOfUses());
                case("TestItem"):
                    return new TestItem(parser.getId(), parser.getName(), parser.getInfo(), parser.getMaxNumberOfUses());
                default:
                    return null;
            }
        }
        if (parser.getType().compareTo("outfitted") == 0) {
            if (parser.getId().split("/")[1].compareTo("Suit") == 0)
                return new Suit(parser.getId(), parser.getName(), parser.getInfo(), parser.getAttributes());
            if (parser.getId().split("/")[1].compareTo("Weapon") == 0)
                return new Weapon(parser.getId(), parser.getName(), parser.getInfo(), parser.getAttributes());
            if (parser.getId().split("/")[1].compareTo("Accessory") == 0)
                return new Accessory(parser.getId(), parser.getName(), parser.getInfo(), parser.getAttributes());
        }
        return null;
    }
}
