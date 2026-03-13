package seedu.interntrackr.command;

import org.junit.jupiter.api.Test;
import seedu.interntrackr.exception.InternTrackrException;
import seedu.interntrackr.model.Application;
import seedu.interntrackr.model.ApplicationList;
import seedu.interntrackr.storage.Storage;
import seedu.interntrackr.ui.Ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StatusCommandTest {

    @Test
    void execute_validIndex_statusUpdated() {
        ApplicationList applications = new ApplicationList();
        applications.addApplication(new Application("Google", "SWE")); // Index 1

        // Setup dummy UI and Storage
        Ui ui = new Ui();
        Storage storage = new Storage("data/test_status.txt");

        StatusCommand command = new StatusCommand(1, "Interview");
        command.execute(applications, ui, storage);

        assertEquals("Interview", applications.getApplication(1).getStatus());
    }

    @Test
    void execute_invalidIndex_exceptionThrown() {
        ApplicationList applications = new ApplicationList();
        applications.addApplication(new Application("Meta", "Data Science"));

        Ui ui = new Ui();
        Storage storage = new Storage("data/test_status_error.txt");

        // Testing boundary: index out of range
        StatusCommand command = new StatusCommand(2, "Pending");

        assertThrows(InternTrackrException.class, () -> {
            command.execute(applications, ui, storage);
        });
    }
}
