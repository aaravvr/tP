package seedu.interntrackr.command;

import java.time.LocalDate;

import seedu.interntrackr.exception.InternTrackrException;
import seedu.interntrackr.model.Application;
import seedu.interntrackr.model.ApplicationList;
import seedu.interntrackr.model.Deadline;
import seedu.interntrackr.storage.Storage;
import seedu.interntrackr.ui.Ui;

/**
 * Adds an application deadline to an internship.
 */
public class DeadlineCommand extends Command {
    private int index;
    private String deadlineType;
    private LocalDate dueDate;

    public DeadlineCommand(int index, String deadlineType, LocalDate dueDate) {
        this.index = index;
        this.deadlineType = deadlineType;
        this.dueDate = dueDate;
    }

    @Override
    public void execute(ApplicationList applications, Ui ui, Storage storage) throws InternTrackrException {
        if (index < 1 || index > applications.getSize()) {
            throw new InternTrackrException("Invalid application index.");
        }

        // Creates a new Deadline instance
        Deadline newDeadline = new Deadline(deadlineType, dueDate);

        // Set deadline for the specified application
        Application app = applications.getApplication(index);
        app.setDeadline(newDeadline);

        // Show UI message
        ui.showMessage("Deadline updated! " + app.getCompany() + "'s " + app.getRole()
                + " 's " + this.deadlineType + " due date is now on the [" + this.dueDate + "]");

        // Save to storage
        storage.save(applications.getApplications());
    }
}
