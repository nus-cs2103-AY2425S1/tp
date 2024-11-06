package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_FAVOURITE_LABEL;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.FavouriteStatus;
import seedu.address.model.person.IsFavouritePredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.RoleContainsKeywordsPredicate;
import seedu.address.model.person.TelegramContainsKeywordsPredicate;

public class FindCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPrefix_throwsParseException() {
        assertParseFailure(parser, "t/chen", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_nonEmptyPreamble_throwsParseException() {
        assertParseFailure(parser, " some random preamble", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_emptyArgumentAfterPrefix_throwsParseException() {
        assertParseFailure(parser, "n/ ", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "r/ ", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "t/ ", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "r/member    n/  ", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "r/   n/james ", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "r/   n/james  t/ ", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_emptyNameKeyword_throwsParseException() {
        // Test where the name field is empty
        String userInput = " n/  r/Developer"; // n/ contains only whitespace
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_emptyRoleKeyword_throwsParseException() {
        // Test where the role field is empty
        String userInput = " n/John Doe r/ "; // r/ contains only whitespace
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_emptyTelegramKeyword_throwsParseException() {
        // Test where the telegram field is empty
        String userInput = " n/John Doe t/";
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_emptyNameAndRoleKeywords_throwsParseException() {
        // Test where both name and role fields are empty
        String userInput = " n/  r/ "; // both contain only whitespace
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_emptyNameAndRoleAndTelegramKeywords_throwsParseException() {
        // Test where all name, role and telegram fields are empty
        String userInput = " n/  r/  t/ ";
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_nonEmptyFavouriteLabel_throwsParseException() {
        // Test where there is non empty input for favourite label
        String userInput = " f/something ";
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FAVOURITE_LABEL);
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces, not searching for favourite contact
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(List.of("chen", "albert")),
                        new RoleContainsKeywordsPredicate(List.of("member", "exco")),
                        new TelegramContainsKeywordsPredicate(List.of()),
                        new IsFavouritePredicate(Optional.empty()));
        assertParseSuccess(parser, " n/chen  n/albert  r/member r/exco ", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n n/  chen  \n \t n/ albert \t r/ \t member r/ \n exco \t ",
                expectedFindCommand);

        // searching for favourite contact
        expectedFindCommand = new FindCommand(new NameContainsKeywordsPredicate(List.of()),
                new RoleContainsKeywordsPredicate(List.of()),
                new TelegramContainsKeywordsPredicate(List.of()),
                new IsFavouritePredicate(Optional.of(FavouriteStatus.FAVOURITE)));
        assertParseSuccess(parser, " f/ ", expectedFindCommand);
    }

}
