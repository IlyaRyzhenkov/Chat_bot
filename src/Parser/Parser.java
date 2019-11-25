package Parser;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public abstract class Parser {

    protected JSONObject parsedObject;

    public Parser(String path) throws ParseException, IOException {
        BufferedReader bufferedReader;
        StringBuilder builder = new StringBuilder();
        JSONParser parser = new JSONParser();
        bufferedReader = new BufferedReader(new FileReader(new File(path)));
        String currentLine;
        while ((currentLine = bufferedReader.readLine()) != null)
            builder.append(currentLine);
        parsedObject = (JSONObject) parser.parse(builder.toString());
    }

    protected String getStringParam(String key) { return parsedObject.get(key).toString(); }
}
