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

    public static Event parse(String path){
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(path)));
            StringBuilder builder = new StringBuilder();
            String currentLine;
            while ((currentLine = bufferedReader.readLine()) != null)
                builder.append(currentLine);
            JSONParser parser = new JSONParser();
            JSONObject parsedEvent = (JSONObject) parser.parse(builder.toString());
            JSONArray parsedAnswers = (JSONArray) parsedEvent.get("answers");
            Answer answers[] = new Answer[parsedAnswers.size()];
            for (int i = 0; i < parsedAnswers.size(); i++) {
                answers[i] = new Answer((String) ((JSONObject) parsedAnswers.get(i)).get("text"),
                        (String) ((JSONObject) parsedAnswers.get(i)).get("id"));
            }
            return new Event(parsedEvent.get("id").toString(), parsedEvent.get("name").toString(),
                    parsedEvent.get("text").toString(), answers);
        }catch (ParseException e){return null;}
        catch (IOException e) {return null;}
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