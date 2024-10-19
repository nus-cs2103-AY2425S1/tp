package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HIGH_RISK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_LOW_RISK;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterByTagCommand;
import seedu.address.model.person.PersonHasTagPredicate;
import seedu.address.model.tag.Tag;


public class FilterByTagCommandParserTest {
    private FilterByTagCommandParser parser = new FilterByTagCommandParser();
    private PersonHasTagPredicate multiplePredicate =
          new PersonHasTagPredicate(List.of(new Tag(VALID_TAG_HIGH_RISK),new Tag(VALID_TAG_LOW_RISK)));


    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
              String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterByTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidTag_throwsParseException() {
        assertParseFailure(parser, INVALID_TAG_DESC,
              String.format(Tag.MESSAGE_CONSTRAINTS, FilterByTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {

        FilterByTagCommand expectedFilterByTagCommand =
              new FilterByTagCommand(multiplePredicate);
        assertParseSuccess(parser, " t/ High Risk t/ Low Risk" , expectedFilterByTagCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n t/ High Risk \n \t t/ Low Risk" , expectedFilterByTagCommand);


    }

}
