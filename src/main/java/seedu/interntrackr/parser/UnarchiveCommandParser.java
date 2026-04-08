package seedu.interntrackr.parser;

import java.util.logging.Logger;

import seedu.interntrackr.command.UnarchiveCommand;
import seedu.interntrackr.exception.InternTrackrException;

/**
 * Parses user input arguments for the unarchive command.
 */
public class UnarchiveCommandParser {
    private static final Logger logger = Logger.getLogger(UnarchiveCommandParser.class.getName());

    /**
     * Parses the given arguments and returns an UnarchiveCommand.
     *
     * @param arguments The argument string following the "unarchive" keyword.
     * @return A new UnarchiveCommand with the parsed index.
     * @throws InternTrackrException If the index is missing, non-numeric, or non-positive.
     */
    public static UnarchiveCommand parse(String arguments) throws InternTrackrException {
        if (arguments.isEmpty()) {
            logger.warning("Unarchive command missing index.");
            throw new InternTrackrException("Invalid format. Usage: unarchive INDEX");
        }
        try {
            int index = Integer.parseInt(arguments.trim());
            if (index <= 0) {
                logger.warning("Unarchive index is non-positive: " + index);
                throw new InternTrackrException("Index must be a positive integer.");
            }
            logger.fine("Parsed: UnarchiveCommand index=" + index);
            return new UnarchiveCommand(index);
        } catch (NumberFormatException e) {
            logger.warning("Unarchive index is not a number: \"" + arguments.trim() + "\"");
            throw new InternTrackrException("The application index must be a number.");
        }
    }
}
