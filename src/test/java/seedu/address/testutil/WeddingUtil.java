package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEDDING_NAME;

import seedu.address.logic.commands.AddWeddingCommand;
import seedu.address.model.wedding.Wedding;

/**
 * a utility class for wedding
 */
public class WeddingUtil {
    /**
     * Returns an add wedding command string for adding the {@code wedding}.
     */
    public static String getAddWeddingCommand(Wedding wedding) {
        return AddWeddingCommand.COMMAND_WORD + " " + getWeddingDetails(wedding);
    }

    /**
     * Returns the part of command string for the given {@code wedding}'s details.
     */
    public static String getWeddingDetails(Wedding wedding) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_WEDDING_NAME + wedding.getWeddingName().value + " ");
        sb.append(PREFIX_VENUE + wedding.getVenue().value + " ");
        sb.append(PREFIX_DATE + wedding.getDate().value + " ");
        return sb.toString();
    }
}
