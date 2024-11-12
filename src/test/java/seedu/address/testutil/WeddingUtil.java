package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

import seedu.address.logic.commands.AddwCommand;
import seedu.address.model.wedding.Wedding;

/**
 * A utility class for Wedding.
 */
public class WeddingUtil {

    /**
     * Returns an add command string for adding the {@code wedding}.
     */
    public static String getAddCommand(Wedding wedding) {
        return AddwCommand.COMMAND_WORD + " " + getWeddingDetails(wedding);
    }

    /**
     * Returns the part of command string for the given {@code wedding}'s details.
     */
    public static String getWeddingDetails(Wedding wedding) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + wedding.getName().fullName + " ");
        if (wedding.getClient() != null) {
            sb.append(PREFIX_NAME + wedding.getClient().getName().fullName + " ");
        }
        if (wedding.getDate() != null) {
            sb.append(PREFIX_DATE + wedding.getDate().toString() + " ");
        }
        if (wedding.getVenue() != null) {
            sb.append(PREFIX_VENUE + wedding.getVenue().toString() + " ");
        }
        return sb.toString();
    }
}
