package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.model.person.predicates.AddressContainsSubstringPredicate;
import seedu.address.model.person.predicates.EmailContainsSubstringPredicate;
import seedu.address.model.person.predicates.JobContainsSubstringPredicate;
import seedu.address.model.person.predicates.NameContainsSubstringPredicate;
import seedu.address.model.person.predicates.PhoneContainsSubstringPredicate;
import seedu.address.model.person.predicates.RemarkContainsSubstringPredicate;

public class FilterCommandParserTest {

    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFilterCommand() {
        FilterCommand expectedFilterCommand =
                new FilterCommand(new NameContainsSubstringPredicate("Alice"));
        assertParseSuccess(parser, " n/ Alice", expectedFilterCommand);

        // multiple whitespaces between flag and substring
        assertParseSuccess(parser, " n/ \n  Alice \t", expectedFilterCommand);
    }

    @Test
    public void parse_addressFlag_returnsAddressFilterCommand() {
        FilterCommand expectedFilterCommand =
                new FilterCommand(new AddressContainsSubstringPredicate("Block 123"));
        assertParseSuccess(parser, " a/ Block 123", expectedFilterCommand);
    }

    @Test
    public void parse_emailFlag_returnsEmailFilterCommand() {
        FilterCommand expectedFilterCommand =
                new FilterCommand(new EmailContainsSubstringPredicate("alice@hello.com"));
        assertParseSuccess(parser, " e/ alice@hello.com", expectedFilterCommand);
    }

    @Test
    public void parse_jobFlag_returnsRemarkFilterCommand() {
        FilterCommand expectedFilterCommand =
                new FilterCommand(new JobContainsSubstringPredicate("Software Engineer"));
        assertParseSuccess(parser, " j/ Software Engineer", expectedFilterCommand);
    }

    @Test
    public void parse_nameFlag_returnsNameFilterCommand() {
        FilterCommand expectedFilterCommand =
                new FilterCommand(new NameContainsSubstringPredicate("Alice"));
        assertParseSuccess(parser, " n/ Alice", expectedFilterCommand);
    }


    @Test
    public void parse_phoneFlag_returnsPhoneFilterCommand() {
        FilterCommand expectedFilterCommand =
                new FilterCommand(new PhoneContainsSubstringPredicate("91112222"));
        assertParseSuccess(parser, " p/ 91112222", expectedFilterCommand);
    }


    @Test
    public void parse_remarkFlag_returnsRemarkFilterCommand() {
        FilterCommand expectedFilterCommand =
                new FilterCommand(new RemarkContainsSubstringPredicate("is a celebrity"));
        assertParseSuccess(parser, " r/ is a celebrity", expectedFilterCommand);
    }
}
