package EventParser;

import Event.Event;
import Event.Answer;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;

public class EventParser {

    public static Event parse(String path) throws IOException, ParseException{
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(path)));
        StringBuilder builder = new StringBuilder();
        String currentLine;
        while((currentLine = bufferedReader.readLine()) != null)
            builder.append(currentLine);
        JSONParser parser = new JSONParser();
        JSONObject parsedEvent = (JSONObject) parser.parse(builder.toString());
        JSONArray parsedAnswers = (JSONArray) parsedEvent.get("answers");
        Answer answers[] = new Answer[parsedAnswers.size()];
        for(int i = 0; i < parsedAnswers.size(); i++)
        {
            answers[i] = new Answer((String)((JSONObject)parsedAnswers.get(i)).get("text"),
                    (String)((JSONObject)parsedAnswers.get(i)).get("id"));
        }
        return new Event(parsedEvent.get("id").toString(), parsedEvent.get("name").toString(),
                parsedEvent.get("text").toString(), answers);
    }
}
