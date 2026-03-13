package seedu.interntrackr.command;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.interntrackr.model.Application;
import seedu.interntrackr.model.ApplicationList;
import seedu.interntrackr.ui.Ui;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class FilterCommandTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void execute_filterByStatus_printsMatchingApplications() {
        ApplicationList applications = new ApplicationList();
        Application app1 = new Application("Google", "SWE", "Applied");
        Application app2 = new Application("Meta", "DS", "Interview");
        applications.addApplication(app1);
        applications.addApplication(app2);

        Ui ui = new Ui();
        FilterCommand command = new FilterCommand("Interview");
        command.execute(applications, ui, null);

        String output = outContent.toString();
        assertTrue(output.contains("Meta"));
        assertFalse(output.contains("Google"));
    }

    @Test
    void execute_filterClear_printsAllApplications() {
        ApplicationList applications = new ApplicationList();
        applications.addApplication(new Application("Amazon", "SDE", "Applied"));

        Ui ui = new Ui();
        FilterCommand command = new FilterCommand(true); // isClear = true
        command.execute(applications, ui, null);

        String output = outContent.toString();
        assertTrue(output.contains("Filter cleared"));
        assertTrue(output.contains("Amazon"));
    }
}
