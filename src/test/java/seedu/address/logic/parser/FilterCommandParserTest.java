package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HIGH_RISK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_LOW_RISK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_MEDIUM_RISK;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.model.person.PersonHasFeaturePredicate;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;


public class FilterCommandParserTest {
    private FilterCommandParser parser = new FilterCommandParser();

    private PersonHasFeaturePredicate highTagOnlyPredicate =
          new PersonHasFeaturePredicate(new Tag(VALID_TAG_HIGH_RISK), null);
    private PersonHasFeaturePredicate lowTagOnlyPredicate =
          new PersonHasFeaturePredicate(new Tag(VALID_TAG_LOW_RISK), null);

    private PersonHasFeaturePredicate mediumTagOnlyPredicate =
          new PersonHasFeaturePredicate(new Tag(VALID_TAG_MEDIUM_RISK), null);

    private PersonHasFeaturePredicate phoneOnlyPredicate =
          new PersonHasFeaturePredicate(null, new Phone(ALICE.getPhone().value));

    private PersonHasFeaturePredicate phoneAndTagPredicate =
          new PersonHasFeaturePredicate(new Tag(VALID_TAG_HIGH_RISK), new Phone(ALICE.getPhone().value));

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
              String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidTag_throwsParseException() {
        //non empty preamble
        FilterCommand expectedFilterCommand = new FilterCommand(phoneOnlyPredicate);
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + " p/ " + ALICE.getPhone().value,
              String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));;
        //invalid tag
        assertParseFailure(parser, INVALID_TAG_DESC,
              String.format(Tag.MESSAGE_CONSTRAINTS, FilterCommand.MESSAGE_USAGE));
        //invalid phone
        assertParseFailure(parser, INVALID_PHONE_DESC,
              String.format(Phone.MESSAGE_CONSTRAINTS, FilterCommand.MESSAGE_USAGE));

        //no features provided
        assertParseFailure(parser, "",
              String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));

        //multiple tags provided
        assertParseFailure(parser, VALID_TAG_HIGH_RISK + VALID_TAG_MEDIUM_RISK,
              String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));

        //multiple phone numbers provided
        assertParseFailure(parser, PHONE_DESC_AMY + PHONE_DESC_BOB,
              String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));


    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        //phone number only
        FilterCommand expectedFilterCommand = new FilterCommand(phoneOnlyPredicate);
        assertParseSuccess(parser, " p/ " + ALICE.getPhone().value, expectedFilterCommand);

        //parse with preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + " p/ " + ALICE.getPhone().value, expectedFilterCommand);
        //tag only
        expectedFilterCommand = new FilterCommand(highTagOnlyPredicate);
        assertParseSuccess(parser, " t/ High Risk", expectedFilterCommand);

        //phone number and tag
        expectedFilterCommand =
              new FilterCommand(phoneAndTagPredicate);
        assertParseSuccess(parser, " t/ High Risk p/" + ALICE.getPhone().value, expectedFilterCommand);
        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n t/ High Risk \n \t p/" + ALICE.getPhone().value,
              expectedFilterCommand);



    }

}
