package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DateCommand;
import seedu.address.model.person.Date;

import java.util.Optional;


public class DateCommandParserTest {
    private DateCommandParser parser = new DateCommandParser();
    private final String nonEmptyDate = "Some date.";
//    @Test
//    public void parse_namePhoneEmailSpecified_success() {
//        // have date with all 3 inputs
//        String userInput = PREFIX_NAME + VALID_NAME_AMY + " "
//                + PREFIX_PHONE + VALID_PHONE_AMY + " "
//                + PREFIX_EMAIL + VALID_EMAIL_AMY + " "
//                + PREFIX_DATE + nonEmptyDate;
//        DateCommand expectedCommand = new DateCommand(Optional.of(VALID_NAME_AMY), Optional.of(VALID_PHONE_AMY),
//                Optional.of(VALID_EMAIL_AMY), new Date(nonEmptyDate));
//        assertParseSuccess(parser, userInput, expectedCommand);
//        // no date
//        userInput = PREFIX_NAME + VALID_NAME_AMY + " "
//                + PREFIX_PHONE + VALID_PHONE_AMY + " "
//                + PREFIX_EMAIL + VALID_EMAIL_AMY + " "
//                + PREFIX_DATE;
//        expectedCommand = new DateCommand(Optional.of(VALID_NAME_AMY), Optional.of(VALID_PHONE_AMY),
//                Optional.of(VALID_EMAIL_AMY), new Date(""));
//        assertParseSuccess(parser, userInput, expectedCommand);
//    }
    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DateCommand.MESSAGE_USAGE);
        // no parameters
        assertParseFailure(parser, DateCommand.COMMAND_WORD, expectedMessage);
        // no index
        assertParseFailure(parser, DateCommand.COMMAND_WORD + " " + nonEmptyDate, expectedMessage);
    }
}
