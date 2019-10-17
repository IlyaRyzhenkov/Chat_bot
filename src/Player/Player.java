package Player;

import java.util.HashMap;

public class Player {
    private HashMap<String, String> importantData;

    public Player() {
        this.importantData = new HashMap<String, String>();
    }

    public HashMap<String, String> getImportantData() {
        return this.importantData;
    }

    public void remember(String id, String answer) {
        this.importantData.put(id, answer);
    }

    public String recall(String id) {
        return importantData.get(id);
    }

    public void setImportantData(HashMap<String, String> data){
        importantData = data;
    }
}
