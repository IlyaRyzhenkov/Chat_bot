package EventParser;

import Event.Answer;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.HashMap;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.equalTo;

public class EventParserTest {

    @Rule
    public ExpectedException e = ExpectedException.none();

    private EventParser getParser(String path) {
        try {
            return new EventParser(path);
        }catch (Exception e) { return null; }
    }

    @Test
    public void getCorrectFileID() {
        EventParser parser = getParser("events/test/correct_file.json");
        Assert.assertEquals("test/correct_file", parser.getID());
    }

    @Test
    public void getIncorrectFileID() {
        EventParser parser = getParser("events/test/incorrect_id_file.json");
        e.expect(NullPointerException.class);
        e.expectMessage(not(equalTo("")));
        parser.getID();
        e = ExpectedException.none();
    }

    @Test
    public void getCorrectFileName() {
        EventParser parser = getParser("events/test/correct_file.json");
        Assert.assertEquals("TestName", parser.getName());
    }

    @Test
    public void getIncorrectFileName() {
        EventParser parser = getParser("events/test/incorrect_name_file.json");
        e.expect(NullPointerException.class);
        e.expectMessage(not(equalTo("")));
        parser.getName();
        e = ExpectedException.none();
    }

    @Test
    public void getCorrectFileText() {
        EventParser parser = getParser("events/test/correct_file.json");
        Assert.assertEquals("This event was made for test EventParser. It's not useful.", parser.getText());
    }

    @Test
    public void getIncorrectFileText() {
        EventParser parser = getParser("events/test/incorrect_text_file.json");
        e.expect(NullPointerException.class);
        e.expectMessage(not(equalTo("")));
        parser.getText();
        e = ExpectedException.none();
    }

    @Test
    public void getCorrectFileAnswers() {
        EventParser parser = getParser("events/test/correct_file.json");
        Answer[] expected = new Answer[1];
        expected[0] = new Answer("Ok", "test/test", new HashMap<>());
        Answer[] actual = parser.getAnswers();
        assertEquals(expected.length, actual.length);
        for(int i = 0; i < expected.length; i++) {
            assertEquals(expected[i].getId(), actual[i].getId());
            assertEquals(expected[i].getText(), actual[i].getText());
        }
    }

    @Test
    public void getIncorrectAnswersList() {
        EventParser parser = getParser("events/test/incorrect_answers_list_file.json");
        e.expect(NullPointerException.class);
        e.expectMessage(not(equalTo("")));
        parser.getAnswers();
        e = ExpectedException.none();
    }

    @Test
    public void getIncorrectAnswerInList() {
        EventParser parser = getParser("events/test/incorrect_answer_in_list_file.json");
        e.expect(NullPointerException.class);
        e.expectMessage(not(equalTo("")));
        parser.getAnswers();
        e = ExpectedException.none();
    }

    @Test
    public void tryReadDamagedFile() {
        EventParser parser = getParser("events/test/damaged_file.json");
        assertNull(parser);
    }

    @Test
    public void tryReadNonexistentFile() {
        EventParser parser = getParser("String that couldn't be file /*");
        assertNull(parser);
    }
}