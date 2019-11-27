package SaveLoader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import java.util.Stack;

public class JSONsaveLoader implements ISaveLoader {

    public void saveGame(String filename, GameInfo gameData) {
        JSONObject obj = new JSONObject();
        JSONArray stack = new JSONArray();
        for (int i = 0; i < gameData.getIDstack().size(); i++) {
            stack.add(gameData.getIDstack().get(i));
        }
        obj.put("stack", stack);
        JSONArray hashSet = new JSONArray();
        for (String key: gameData.getPlayerData().keySet()) {
            JSONArray pair = new JSONArray();
            pair.add(key);
            pair.add(gameData.getPlayerData().get(key));
            hashSet.add(pair);
        }
        JSONObject inventory = new JSONObject();
        inventory.put("suit", gameData.getPlayerSuitId());
        inventory.put("weapon", gameData.getPlayerWeaponId());
        inventory.put("accessory", gameData.getPlayerAccessoryId());
        JSONArray items = new JSONArray();
        for(String item: gameData.getPlayerItems())
            items.add(item);
        inventory.put("items", items);
        JSONArray attributes = new JSONArray();
        for(String a: gameData.getPlayerAttributes().keySet()) {
            JSONObject o = new JSONObject();
            o.put("attribute", a);
            o.put("value", gameData.getPlayerAttributes().get(a));
        }
        obj.put("attributes", attributes);
        obj.put("inventory", inventory);
        obj.put("Player data", hashSet);
        obj.put("StartEvent", gameData.getEventToStart());
        obj.put("hp", gameData.getPlayerHp());
        obj.put("maxHp", gameData.getMaxPlayerHp());

        String path = makeFullFilename(filename);
        try (FileWriter file = new FileWriter(path)) {
            file.write(obj.toJSONString());
        } catch (IOException e) {
            System.err.print("error while writing file");
        }
    }

    public GameInfo loadGame(String filename) {
        String path = makeFullFilename(filename);
        JSONParser parser = new JSONParser();
        try(Reader reader = new FileReader(path)) {
            JSONObject obj = (JSONObject) parser.parse(reader);
            Stack<String> IDstack = new Stack<String>();
            JSONArray JSONstack = (JSONArray) obj.get("stack");
            Iterator<String> iterator = JSONstack.iterator();
            while (iterator.hasNext()) {
                IDstack.push(iterator.next());
            }

            HashMap<String, String> playerData = new HashMap<String, String>();
            JSONArray JSONhash = (JSONArray) obj.get("Player data");
            Iterator<JSONArray> iterator2 = JSONhash.iterator();
            while (iterator2.hasNext()) {

                JSONArray pair = iterator2.next();
                playerData.put((String) pair.get(0), (String) pair.get(1));
            }
            String eventID = (String) obj.get("StartEvent");
            HashMap<String, Integer> attributes = new HashMap<String, Integer>();
            JSONArray jsonAttributes = (JSONArray) obj.get("attributes");
            Iterator<JSONObject> attributesIterator = jsonAttributes.iterator();
            while(attributesIterator.hasNext()) {
                attributes.put((String) attributesIterator.next().get("attribute"),
                        Integer.parseInt((String)attributesIterator.next().get("value")));
            }
            JSONObject jsonInventory = (JSONObject) obj.get("inventory");
            JSONArray jsonItems = (JSONArray) jsonInventory.get("items");
            ArrayList<String> items = new ArrayList<String>();
            items.addAll(jsonItems);
            String weaponId = (String)jsonInventory.get("weapon");
            String suitId = (String) jsonInventory.get("suit");
            String accessoryId = (String) jsonInventory.get("accessory");
            int playerHp = Integer.parseInt(obj.get("hp").toString());
            int maxPlayerHp = Integer.parseInt(obj.get("maxHp").toString());

            return new GameInfo(IDstack, playerData, eventID, playerHp, maxPlayerHp, items, weaponId, suitId, accessoryId, attributes);

        } catch (IOException e) {
            System.err.print("Error reading file\n");
            return null;
        } catch (ParseException e) {
            System.err.print("File damaged\n");
            return null;
        }
    }

    private String makeFullFilename(String filename) {
        if (filename.length() > 5)
            if (filename.substring(filename.length() - 5).equals(".json"))
                return filename;
        return String.format("Saved\\%s.json", filename);
    }
}
