package seedu.interntrackr.model;

import java.time.LocalDate;

/**
 * Represents a single deadline.
 */
public class Deadline {
    private String deadlineType;
    private LocalDate dueDate;
    private boolean isDone;

    /**
     * Constructs a Deadline with the given category, dueDate.
     *
     * @param deadlineType The category of the deadline.
     * @param dueDate      The due date of the deadline.
     */
    public Deadline(String deadlineType, LocalDate dueDate) {
        this.deadlineType = deadlineType;
        this.dueDate = dueDate;
        this.isDone = false;
    }

    /**
     * Constructs a Deadline with the given deadlineType, dueDate, isDone.
     *
     * @param deadlineType The deadlineType of the deadline.
     * @param dueDate      The due date of the deadline.
     * @param isDone       The completeness of the deadline.
     */
    public Deadline(String deadlineType, LocalDate dueDate, boolean isDone) {
        this.deadlineType = deadlineType;
        this.dueDate = dueDate;
        this.isDone = isDone;
    }

    /**
     * Returns the completeness of this deadline.
     *
     * @return The deadline's isDone field.
     */
    public boolean getIsDone() {
        return this.isDone;
    }

    /**
     * Sets the deadline as completed.
     */
    public void setDone() {
        this.isDone = true;
    }

    /**
     * Sets the deadline as not completed.
     */
    public void setNotDone() {
        this.isDone = false;
    }

    /**
     * Returns a formatted string representation of this deadline's completeness.
     *
     * @return A human-readable string of the deadline's isDone field.
     */
    private String markIsDone() {
        return this.isDone ? "[X]" : "[ ]";
    }

    /**
     * Returns a formatted string representation of this deadline.
     *
     * @return A human-readable string.
     */
    @Override
    public String toString() {
        return "Deadline Type: " + this.deadlineType + " | Due Date: " + this.dueDate + " | Done: " + markIsDone();
    }

    /**
     * Returns a pipe-delimited string for saving to the storage file.
     *
     * @return A storage-formatted string.
     */
    public String toStorageString() {
        return this.deadlineType + " | " + this.dueDate + " | " + this.isDone;
    }
}
