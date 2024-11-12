package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.AddWeddingCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddWeddingCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.wedding.Wedding;
import seedu.address.testutil.TypicalWeddings;

public class AddWeddingCommandParserTest {

    private final AddWeddingCommandParser parser = new AddWeddingCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws Exception {
        String userInput = " " + PREFIX_NAME + "John and Jane Wedding " + PREFIX_DATE + "12/12/2026";
        Wedding expectedWedding = TypicalWeddings.WEDDING_ONE;

        AddWeddingCommand resultCommand = parser.parse(userInput);

        Wedding resultWedding = resultCommand.getWedding();

        assertEquals(expectedWedding.getWeddingName(), resultWedding.getWeddingName());
        assertEquals(expectedWedding.getWeddingDate(), resultWedding.getWeddingDate());
    }

    @Test
    public void parse_missingName_failure() {
        String userInput = " " + PREFIX_DATE + "12/12/2026";
        assertThrows(ParseException.class, () -> parser.parse(userInput),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_missingDate_failure() {
        String userInput = " " + PREFIX_NAME + "John and Jane Wedding";
        assertThrows(ParseException.class, () -> parser.parse(userInput),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidDateFormat_failure() {
        String userInput = " " + PREFIX_NAME + "John and Jane Wedding " + PREFIX_DATE + "12-12-2026";
        assertThrows(ParseException.class, () -> parser.parse(userInput),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_extraArguments_failure() {
        String userInput = "extra " + PREFIX_NAME + "John and Jane Wedding " + PREFIX_DATE + "12/12/2026";
        assertThrows(ParseException.class, () -> parser.parse(userInput),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }
}
