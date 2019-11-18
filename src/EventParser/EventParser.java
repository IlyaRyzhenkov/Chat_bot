package EventParser;

import Event.Answer;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.util.HashMap;

public class EventParser {

    private JSONObject parsedEvent;

    public EventParser(String path) throws ParseException, IOException {
        BufferedReader bufferedReader;
        StringBuilder builder = new StringBuilder();
        JSONParser parser = new JSONParser();
        bufferedReader = new BufferedReader(new FileReader(new File(path)));
        String currentLine;
        while ((currentLine = bufferedReader.readLine()) != null)
            builder.append(currentLine);
        parsedEvent = (JSONObject) parser.parse(builder.toString());
    }

    private String getStringParam(String key) { return parsedEvent.get(key).toString(); }

    public String getID() {
        return getStringParam("id");
    }

    public String getName() {
        return getStringParam("name");
    }

    public String getText() {
        return getStringParam("text");
    }

    public String getType() {
        return getStringParam("type");
    }

    public String getAttribute() { return this.getType().compareTo("check") == 0 ? getStringParam("attribute") : null; }

    public int getDifficulty() { return this.getType().compareTo("check") == 0
            ? Integer.parseInt(getStringParam("difficulty")) : null; }

    public boolean isImportant() {
        return Boolean.parseBoolean(getStringParam("importance"));
    }

    public boolean isParent() {
        return Boolean.parseBoolean(getStringParam("parent"));
    }



    public Answer[] getAnswers() {
        JSONArray parsedAnswers = (JSONArray) parsedEvent.get("answers");
        Answer answers[] = new Answer[parsedAnswers.size()];
        for (int i = 0; i < parsedAnswers.size(); i++) {
            HashMap<String, String> dependencies = new HashMap<String, String>();
            JSONArray parsedDependencies = (JSONArray) ((JSONObject)parsedAnswers.get(i)).get("dependencies");
            for (int j = 0; j < parsedDependencies.size(); j++) {
                dependencies.put(
                        (((JSONObject) parsedDependencies.get(j)).get("id")).toString(),
                        (((JSONObject) parsedDependencies.get(j)).get("answer")).toString()
                );
            }
            answers[i] = new Answer(
                    (((JSONObject) parsedAnswers.get(i)).get("text")).toString(),
                    (((JSONObject) parsedAnswers.get(i)).get("id")).toString(),
                    dependencies
            );
        }
        return answers;
    }

    /*public static void create(String pathToStorage)
    {
        Scanner scanner = new Scanner(System.in);
        JSONObject event = new JSONObject();
        System.out.print("Print ID: ");
        event.put("id", scanner.nextLine());
        System.out.print("\nPrint NAME: ");
        event.put("name", scanner.nextLine());
        System.out.print("\nPrint TEXT: ");
        event.put("text", scanner.nextLine());
        System.out.println("\nPrint ANSWERS like\nSome text\nID\nSend empty string to stop it.");
        JSONArray answers = new JSONArray();
        String text = "", id = "";
        while(true)
        {
            System.out.print("Text: ");
            text = scanner.nextLine();
            if(text.compareTo("") == 0)
                break;
            while(id.compareTo("") == 0)
            {
                System.out.print("\nID: ");
                id = scanner.nextLine();
            }
            JSONObject ans = new JSONObject();
            ans.put("text", text);
            ans.put("id", id);
            answers.add(ans);
        }
        event.put("answers", answers);
        System.out.println("Save changes?");
        if(scanner.next().compareTo("y")){

        }
    }*/
}