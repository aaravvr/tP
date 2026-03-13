package seedu.interntrackr.parser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import seedu.interntrackr.command.AddCommand;
import seedu.interntrackr.command.Command;
import seedu.interntrackr.command.DeadlineCommand;
import seedu.interntrackr.command.DeleteCommand;
import seedu.interntrackr.command.ExitCommand;
import seedu.interntrackr.command.FilterCommand;
import seedu.interntrackr.command.ListCommand;
import seedu.interntrackr.command.OverviewCommand;
import seedu.interntrackr.command.StatusCommand;
import seedu.interntrackr.exception.InternTrackrException;

/**
 * Parses raw user input into executable command objects.
 */
public class Parser {

    public static Command parse(String fullCommand) throws InternTrackrException {
        String[] parts = fullCommand.trim().split(" ", 2);
        String commandWord = parts[0].toLowerCase();
        String arguments = parts.length > 1 ? parts[1].trim() : "";

        switch (commandWord) {
        case "add":
            if (!arguments.contains("c/") || !arguments.contains("r/")) {
                throw new InternTrackrException("Invalid format. Usage: add c/COMPANY r/ROLE");
            }
            return parseAddCommand(arguments);

        case "overview":
            return new OverviewCommand();

        case "list":
            return new ListCommand();

        case "status":
            if (!arguments.contains(" s/")) {
                throw new InternTrackrException("Invalid format. Usage: status INDEX s/STATUS");
            }
            String[] statusArgs = arguments.split(" s/", 2);
            try {
                int index = Integer.parseInt(statusArgs[0].trim());
                String parsedStatus = statusArgs[1].replace("\"", "").trim();
                return new StatusCommand(index, parsedStatus);
            } catch (NumberFormatException e) {
                throw new InternTrackrException("The application index must be a number.");
            }

        case "delete":
            if (arguments.isEmpty()) {
                throw new InternTrackrException("Invalid format. Usage: delete INDEX");
            }
            try {
                int deleteIndex = Integer.parseInt(arguments.trim());
                return new DeleteCommand(deleteIndex);
            } catch (NumberFormatException e) {
                throw new InternTrackrException("The application index must be a number.");
            }

        case "filter":
            if (arguments.isEmpty()) {
                throw new InternTrackrException("Invalid format. Usage: filter s/STATUS or filter clear");
            }
            if (arguments.equalsIgnoreCase("clear")) {
                return new FilterCommand(true);
            }
            if (!arguments.startsWith("s/")) {
                throw new InternTrackrException("Invalid format. Usage: filter s/STATUS");
            }
            String filterStatus = arguments.substring(2).replace("\"", "").trim();
            return new FilterCommand(filterStatus);

        case "deadline":
            if (arguments.startsWith("add ")) {
                String subArgs = arguments.substring(4).trim();
                if (!subArgs.contains(" t/") || !subArgs.contains(" d/")) {
                    throw new InternTrackrException(
                            "Invalid format. Usage: deadline add INDEX t/TYPE d/DD-MM-YYYY [n/NOTES]");
                }
                try {
                    int typeIndex = subArgs.indexOf(" t/");
                    int dateIndex = subArgs.indexOf(" d/");
                    if (typeIndex == -1 || dateIndex == -1 || typeIndex > dateIndex) {
                        throw new InternTrackrException(
                                "Invalid format. Usage: deadline add INDEX t/TYPE d/DD-MM-YYYY");
                    }

                    int index = Integer.parseInt(subArgs.substring(0, typeIndex).trim());
                    String deadlineType = subArgs.substring(typeIndex + 3, dateIndex).trim().replace("\"", "");

                    String dueDateStr = getDueDateStr(subArgs, dateIndex, deadlineType);

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    LocalDate dueDate = LocalDate.parse(dueDateStr, formatter);
                    return new DeadlineCommand(index, deadlineType, dueDate);

                } catch (NumberFormatException e) {
                    throw new InternTrackrException("The application index must be a number.");
                } catch (DateTimeParseException e) {
                    throw new InternTrackrException("Invalid date format. Use DD-MM-YYYY.");
                }
            } else {
                throw new InternTrackrException(
                        "Invalid format. Usage: deadline add INDEX t/TYPE d/DD-MM-YYYY [n/NOTES]");
            }

        case "exit":
            return new ExitCommand();

        default:
            throw new InternTrackrException("I'm sorry, but I don't know what that command means :-(");
        }
    }

    private static String getDueDateStr(String subArgs, int dateIndex, String deadlineType)
            throws InternTrackrException {

        String dueDateStr = subArgs.substring(dateIndex + 3).trim().replace("\"", "");

        int notesIndex = dueDateStr.indexOf(" n/");
        if (notesIndex != -1) {
            dueDateStr = dueDateStr.substring(0, notesIndex).trim();
        }

        if (deadlineType.isEmpty() || dueDateStr.isEmpty()) {
            throw new InternTrackrException("Deadline type and due date cannot be empty.");
        }
        return dueDateStr;
    }

    private static AddCommand parseAddCommand(String arguments) throws InternTrackrException {
        String company = "";
        String role = "";

        try {
            int cIndex = arguments.indexOf("c/");
            int rIndex = arguments.indexOf("r/");

            if (cIndex < rIndex) {
                company = arguments.substring(cIndex + 2, rIndex).trim().replace("\"", "");
                role = arguments.substring(rIndex + 2).trim().replace("\"", "");
            } else {
                role = arguments.substring(rIndex + 2, cIndex).trim().replace("\"", "");
                company = arguments.substring(cIndex + 2).trim().replace("\"", "");
            }

            if (company.isEmpty() || role.isEmpty()) {
                throw new InternTrackrException("Company and role cannot be empty.");
            }

            return new AddCommand(company, role);
        } catch (Exception e) {
            throw new InternTrackrException("Error parsing add command.");
        }
    }
}
