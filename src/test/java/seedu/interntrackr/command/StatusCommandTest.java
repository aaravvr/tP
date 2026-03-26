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
        applications.addApplication(new Application("Google", "SWE"));

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

        StatusCommand command = new StatusCommand(2, "Pending");

        assertThrows(InternTrackrException.class, () -> {
            command.execute(applications, ui, storage);
        });
    }

    @Test
    void execute_zeroIndex_exceptionThrown() {
        ApplicationList applications = new ApplicationList();
        applications.addApplication(new Application("Apple", "iOS"));

        Ui ui = new Ui();
        Storage storage = new Storage("data/test_status.txt");
        StatusCommand command = new StatusCommand(0, "Accepted");

        assertThrows(InternTrackrException.class, () -> {
            command.execute(applications, ui, storage);
        });
    }

    @Test
    void execute_negativeIndex_exceptionThrown() {
        ApplicationList applications = new ApplicationList();
        applications.addApplication(new Application("Netflix", "Backend"));

        Ui ui = new Ui();
        Storage storage = new Storage("data/test_status.txt");
        StatusCommand command = new StatusCommand(-1, "Offered");

        assertThrows(InternTrackrException.class, () -> {
            command.execute(applications, ui, storage);
        });
    }

    @Test
    void execute_emptyStatus_exceptionThrown() {
        ApplicationList applications = new ApplicationList();
        applications.addApplication(new Application("Amazon", "SDE"));

        Ui ui = new Ui();
        Storage storage = new Storage("data/test_status.txt");
        StatusCommand command = new StatusCommand(1, "");

        assertThrows(InternTrackrException.class, () -> {
            command.execute(applications, ui, storage);
        });
    }

    @Test
    void execute_invalidStatusName_exceptionThrown() {
        ApplicationList applications = new ApplicationList();
        applications.addApplication(new Application("ByteDance", "Frontend"));

        Ui ui = new Ui();
        Storage storage = new Storage("data/test_status.txt");
        StatusCommand command = new StatusCommand(1, "Ghosted");

        assertThrows(InternTrackrException.class, () -> {
            command.execute(applications, ui, storage);
        });
    }

    @Test
    void execute_statusNormalization_success() {
        ApplicationList applications = new ApplicationList();
        applications.addApplication(new Application("Shopee", "QA"));

        Ui ui = new Ui();
        Storage storage = new Storage("data/test_status.txt");

        StatusCommand command = new StatusCommand(1, "interview");
        command.execute(applications, ui, storage);

        assertEquals("Interview", applications.getApplication(1).getStatus());
    }
}
