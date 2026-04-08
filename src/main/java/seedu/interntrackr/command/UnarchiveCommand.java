package seedu.interntrackr.command;

import java.util.logging.Logger;

import seedu.interntrackr.exception.InternTrackrException;
import seedu.interntrackr.model.Application;
import seedu.interntrackr.model.ApplicationList;
import seedu.interntrackr.storage.Storage;
import seedu.interntrackr.ui.Ui;

/**
 * Restores an archived internship application back to the active list.
 */
public class UnarchiveCommand extends Command {
    private static final Logger logger = Logger.getLogger(UnarchiveCommand.class.getName());

    private final int index;

    /**
     * Creates an UnarchiveCommand targeting the application at the specified 1-based
     * display index (as shown in the {@code list archive} output).
     *
     * @param index The 1-based display index of the archived application to unarchive.
     * @throws IllegalArgumentException If the index is not a positive integer.
     */
    public UnarchiveCommand(int index) {
        if (index <= 0) {
            throw new IllegalArgumentException("Index must be a positive integer (1-based).");
        }
        this.index = index;
        logger.fine("UnarchiveCommand created for display index: " + index);
    }

    /**
     * Executes the unarchive command by restoring the archived application at the stored
     * display index back to the active list.
     *
     * <p>The index is resolved against archived entries only, so it always matches
     * what the user sees in the {@code list archive} output.</p>
     *
     * @param applications The current list of applications.
     * @param ui The UI object used to display output to the user.
     * @param storage The storage object used to persist the updated list.
     * @throws InternTrackrException If the display index is out of range.
     */
    @Override
    public void execute(ApplicationList applications, Ui ui, Storage storage) throws InternTrackrException {
        assert applications != null : "ApplicationList must not be null";
        assert ui != null : "Ui must not be null";
        assert storage != null : "Storage must not be null";
        assert index > 0 : "Index must be positive at execution time";

        logger.info("Executing UnarchiveCommand for display index: " + index);

        Application app = applications.getArchivedApplication(index);

        app.setArchived(false);

        ui.showMessage("Got it. I've restored this application to your active list:");
        ui.showMessage("  " + app.toString());
        ui.showMessage("It will now appear in the default list again.");

        storage.save(applications.getApplications());
        logger.fine("UnarchiveCommand executed and saved for: " + app.getCompany());
    }
}
