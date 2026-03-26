package seedu.interntrackr.command;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.interntrackr.exception.InternTrackrException;
import seedu.interntrackr.model.Application;
import seedu.interntrackr.model.ApplicationList;
import seedu.interntrackr.ui.Ui;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertThrows;
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
        FilterCommand command = new FilterCommand(true);
        command.execute(applications, ui, null);

        String output = outContent.toString();
        assertTrue(output.contains("Filter cleared"));
        assertTrue(output.contains("Amazon"));
    }

    @Test
    void execute_noMatchingStatus_printsNoFoundMessage() {
        ApplicationList applications = new ApplicationList();
        applications.addApplication(new Application("Slack", "UI/UX", "Applied"));

        Ui ui = new Ui();
        FilterCommand command = new FilterCommand("Interview");
        command.execute(applications, ui, null);

        String output = outContent.toString();
        assertTrue(output.contains("No applications found with status: Interview"));
    }

    @Test
    void execute_emptyStatus_exceptionThrown() {
        ApplicationList applications = new ApplicationList();
        Ui ui = new Ui();
        FilterCommand command = new FilterCommand("");

        assertThrows(InternTrackrException.class, () -> {
            command.execute(applications, ui, null);
        });
    }

    @Test
    void execute_invalidStatusName_exceptionThrown() {
        ApplicationList applications = new ApplicationList();
        Ui ui = new Ui();
        FilterCommand command = new FilterCommand("Unknown");

        assertThrows(InternTrackrException.class, () -> {
            command.execute(applications, ui, null);
        });
    }

    @Test
    void execute_multipleMatches_printsAllMatching() {
        ApplicationList applications = new ApplicationList();
        applications.addApplication(new Application("Cisco", "Security", "Applied"));
        applications.addApplication(new Application("Oracle", "Database", "Applied"));
        applications.addApplication(new Application("Intel", "Hardware", "Interview"));

        Ui ui = new Ui();
        FilterCommand command = new FilterCommand("Applied");
        command.execute(applications, ui, null);

        String output = outContent.toString();
        assertTrue(output.contains("Cisco"));
        assertTrue(output.contains("Oracle"));
        assertFalse(output.contains("Intel"));
    }

    @Test
    void execute_caseInsensitiveFilter_success() {
        ApplicationList applications = new ApplicationList();
        applications.addApplication(new Application("Grab", "DevOps", "Offered"));

        Ui ui = new Ui();
        FilterCommand command = new FilterCommand("offered");
        command.execute(applications, ui, null);

        String output = outContent.toString();
        assertTrue(output.contains("Grab"));
        assertTrue(output.contains("Offered"));
    }
}
