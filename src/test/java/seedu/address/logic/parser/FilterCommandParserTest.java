package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.model.person.PersonMeetsCriteriaPredicate;
import seedu.address.model.tag.Tag;

public class FilterCommandParserTest {
    private final FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws Exception {
        String phoneCriteria = "+65";
        String emailCriteria = "example.com";
        String addressCriteria = "Clementi";
        String tagCriteria = "Inactive";

        String userInput = String.format(" %s%s %s%s %s%s %s%s",
                PREFIX_PHONE, phoneCriteria,
                PREFIX_EMAIL, emailCriteria,
                PREFIX_ADDRESS, addressCriteria,
                PREFIX_TAG, tagCriteria);

        PersonMeetsCriteriaPredicate predicate = new PersonMeetsCriteriaPredicate(
                Arrays.asList(phoneCriteria),
                Arrays.asList(emailCriteria),
                Arrays.asList(addressCriteria),
                new HashSet<>(Arrays.asList(new Tag(tagCriteria)))
        );

        assertParseSuccess(parser, userInput, new FilterCommand(predicate));
    }

    @Test
    public void parse_optionalFieldsMissing_success() throws Exception {
        String phoneCriteria = "+65";
        String userInput = String.format(" %s%s", PREFIX_PHONE, phoneCriteria);

        PersonMeetsCriteriaPredicate predicate = new PersonMeetsCriteriaPredicate(
                Arrays.asList(phoneCriteria),
                Arrays.asList(),
                Arrays.asList(),
                new HashSet<>()
        );

        assertParseSuccess(parser, userInput, new FilterCommand(predicate));
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, " invalid input",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_noCriteria_failure() {
        assertParseFailure(parser, "    ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }
}
