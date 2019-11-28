package Parser.EventParser;

import Event.Answer;

public interface IEventParser {
    String getID();

    String getName();

    String getText();

    String getType();

    String getAttribute();

    int getDifficulty();

    boolean isImportant();

    boolean isParent();

    Answer[] getAnswers();
}
