package Parser.ItemParser;

import Parser.Parser;
import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.HashMap;

public class ItemParser extends Parser {
    public ItemParser(String path) throws IOException, ParseException {
        super(path);
    }

    public String getType() { return this.getStringParam("type"); }

    public String getName() { return this.getStringParam("name"); }

    public String getInfo() { return this.getStringParam("info"); }

    public String getId() { return this.getStringParam("id"); }

    public int getMaxNumberOfUses() { return this.getType().compareTo("single") == 0 ? Integer.parseInt(this.getStringParam("maxNumberOfUses")) : 0; }

    public HashMap<String, Integer> getAttributes() {
        if(this.getType().compareTo("outfitted") != 0)
            return null;
        JSONObject parsedAttributes = (JSONObject) parsedObject.get("attributes");
        return new HashMap<String, Integer>() {
            {
                put("knowledge", Integer.parseInt(parsedAttributes.getOrDefault("knowledge", 0).toString()));
                put("strength", Integer.parseInt(parsedAttributes.getOrDefault("strength", 0).toString()));
                put("luck", Integer.parseInt(parsedAttributes.getOrDefault("luck", 0).toString()));
                put("communication", Integer.parseInt(parsedAttributes.getOrDefault("communication", 0).toString()));
                put("attention", Integer.parseInt(parsedAttributes.getOrDefault("attention", 0).toString()));
            }
        };
    }
}
