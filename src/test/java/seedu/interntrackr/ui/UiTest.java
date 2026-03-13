package seedu.interntrackr.ui;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UiTest {
    @Test
    public void showLine_printsCorrectDivider() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        new Ui().showLine();
        assertTrue(outContent.toString().contains("____________________________________________________________"));
    }

    @Test
    public void showWelcome_containsWelcomeMessage() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        new Ui().showWelcome();
        assertTrue(outContent.toString().contains("Welcome to InternTrackr!"));
    }
}
