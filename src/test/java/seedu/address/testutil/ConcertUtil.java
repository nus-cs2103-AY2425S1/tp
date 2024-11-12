package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.model.concert.ConcertDate.INPUT_DATE_FORMATTER;
import static seedu.address.model.concert.ConcertDate.OUTPUT_DATE_FORMATTER;

import seedu.address.logic.commands.AddConcertCommand;
import seedu.address.model.concert.Concert;
import seedu.address.model.concert.ConcertDate;

/**
 * A utility class for Person.
 */
public class ConcertUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Concert concert) {
        return AddConcertCommand.COMMAND_WORD + " " + getConcertDetails(concert);
    }

    /**
     * Returns the part of command string for the given {@code concert}'s details.
     */
    public static String getConcertDetails(Concert concert) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + concert.getName().fullName + " ");
        sb.append(PREFIX_ADDRESS + concert.getAddress().value + " ");
        sb.append(PREFIX_DATE + ConcertDate.processDate(concert.getDate().concertDate,
                OUTPUT_DATE_FORMATTER, INPUT_DATE_FORMATTER));
        return sb.toString();
    }
}
