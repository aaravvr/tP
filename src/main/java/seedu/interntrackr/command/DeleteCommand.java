package seedu.interntrackr.command;

import seedu.interntrackr.model.Application;
import seedu.interntrackr.model.ApplicationList;
import seedu.interntrackr.storage.Storage;
import seedu.interntrackr.ui.Ui;
import seedu.interntrackr.exception.InternTrackrException;

import java.util.logging.Logger;

/**
 * Deletes an existing internship application from the tracker.
 */
public class DeleteCommand extends Command {
    private static final Logger logger = Logger.getLogger(DeleteCommand.class.getName());

    private final int index;

    public DeleteCommand(int index) {
        assert index > 0 : "Index must be a positive integer (1-based)";

        if (index <= 0) {
            throw new IllegalArgumentException("Index must be a positive integer (1-based).");
        }

        this.index = index;
        logger.fine("DeleteCommand created for index: " + index);
    }

    @Override
    public void execute(ApplicationList applications, Ui ui, Storage storage) throws InternTrackrException {
        assert applications != null : "ApplicationList must not be null";
        assert ui != null : "Ui must not be null";
        assert storage != null : "Storage must not be null";
        assert index > 0 : "Index must be positive at execution time";

        if (applications == null) {
            logger.severe("execute() called with null ApplicationList.");
            throw new InternTrackrException("ApplicationList is null. Cannot delete application.");
        }
        if (ui == null) {
            logger.severe("execute() called with null Ui.");
            throw new InternTrackrException("Ui is null. Cannot display result.");
        }
        if (storage == null) {
            logger.severe("execute() called with null Storage.");
            throw new InternTrackrException("Storage is null. Cannot save changes.");
        }

        if (index < 1 || index > applications.getSize()) {
            logger.warning("Delete index " + index + " out of range. List size: " + applications.getSize());
            throw new InternTrackrException("Invalid application index. Please provide a valid number.");
        }

        logger.info("Executing DeleteCommand for index: " + index);

        Application appToRemove = applications.getApplication(index);
        applications.deleteApplication(index);

        assert applications.getSize() >= 0 : "List size must be non-negative after deletion";

        ui.showMessage("Noted. I've removed this application:");
        ui.showMessage("  " + appToRemove.toString());
        ui.showMessage("Now you have " + applications.getSize() + " application(s) in the list.");

        storage.save(applications.getApplications());
        logger.fine("DeleteCommand executed and saved. Total applications: " + applications.getSize());
    }
}
