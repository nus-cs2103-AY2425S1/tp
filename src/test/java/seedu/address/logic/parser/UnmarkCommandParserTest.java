package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.TUTORIAL_DESC_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TUTORIAL_ONE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UnmarkCommand;
import seedu.address.model.person.Person;
import seedu.address.model.person.Tutorial;
import seedu.address.testutil.PersonBuilder;

public class UnmarkCommandParserTest {
    private Parser<UnmarkCommand> parser = new UnmarkCommandParser();

    @Test
    public void parse_validTutorial_success() {
        Person expectedPerson = new PersonBuilder(BOB).withTutorials(VALID_TUTORIAL_ONE).build();

        // whitespace only preamble
        assertParseSuccess(parser, INDEX_FIRST_PERSON.getOneBased() + TUTORIAL_DESC_ONE,
                new UnmarkCommand(INDEX_FIRST_PERSON, new Tutorial(VALID_TUTORIAL_ONE)));

        // multiple tutorials - TBC
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage;

        // missing index
        expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                UnmarkCommand.MESSAGE_USAGE);
        assertParseFailure(parser, TUTORIAL_DESC_ONE, expectedMessage);

        // missing tutorial
        expectedMessage = Tutorial.MESSAGE_CONSTRAINTS;

        assertParseFailure(parser, "" + INDEX_FIRST_PERSON.getOneBased(),
                expectedMessage);

        // missing both
        expectedMessage = Tutorial.MESSAGE_CONSTRAINTS;
        assertParseFailure(parser, "", expectedMessage);
    }
}
