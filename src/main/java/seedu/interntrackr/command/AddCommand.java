package seedu.interntrackr.command;

import seedu.interntrackr.model.Application;
import seedu.interntrackr.model.ApplicationList;
import seedu.interntrackr.storage.Storage;
import seedu.interntrackr.ui.Ui;
import seedu.interntrackr.exception.InternTrackrException;

import java.util.logging.Logger;

/**
 * Adds a new internship application to the tracker.
 */
public class AddCommand extends Command {
    private static final Logger logger = Logger.getLogger(AddCommand.class.getName());

    private final String company;
    private final String role;

    public AddCommand(String company, String role) {
        assert company != null && !company.isBlank() : "Company must not be null or blank";
        assert role != null && !role.isBlank() : "Role must not be null or blank";

        if (company == null || company.isBlank()) {
            throw new IllegalArgumentException("Company name cannot be null or blank.");
        }
        if (role == null || role.isBlank()) {
            throw new IllegalArgumentException("Role cannot be null or blank.");
        }

        this.company = company.trim();
        this.role = role.trim();
        logger.fine("AddCommand created: " + this.company + " | " + this.role);
    }

    @Override
    public void execute(ApplicationList applications, Ui ui, Storage storage) throws InternTrackrException {
        assert applications != null : "ApplicationList must not be null";
        assert ui != null : "Ui must not be null";
        assert storage != null : "Storage must not be null";

        if (applications == null) {
            logger.severe("execute() called with null ApplicationList.");
            throw new InternTrackrException("ApplicationList is null. Cannot add application.");
        }
        if (ui == null) {
            logger.severe("execute() called with null Ui.");
            throw new InternTrackrException("Ui is null. Cannot display result.");
        }
        if (storage == null) {
            logger.severe("execute() called with null Storage.");
            throw new InternTrackrException("Storage is null. Cannot save application.");
        }

        logger.info("Executing AddCommand: " + company + " | " + role);

        Application newApp = new Application(company, role);
        applications.addApplication(newApp);

        assert applications.getSize() > 0 : "List size must be positive after adding";

        ui.showMessage("Got it. I've added this application:");
        ui.showMessage("  " + newApp.toString());
        ui.showMessage("Now you have " + applications.getSize() + " application(s) in the list.");

        storage.save(applications.getApplications());
        logger.fine("AddCommand executed and saved. Total applications: " + applications.getSize());
    }
}
