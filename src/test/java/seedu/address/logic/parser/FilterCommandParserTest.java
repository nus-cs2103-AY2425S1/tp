package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.model.Range;
import seedu.address.model.person.PersonWithCriteriaPredicate;





public class FilterCommandParserTest {
    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFilterCommand() {
        // age
        String age = " b/ 10-99";
        List<Range<?>> firstRanges = new ArrayList<>();
        firstRanges.add(new Range<Integer>(10, 99));
        PersonWithCriteriaPredicate criteriaPredicate = new PersonWithCriteriaPredicate(firstRanges);
        FilterCommand expected = new FilterCommand(criteriaPredicate);
        assertParseSuccess(parser, age, expected);

        // appointment
        String appointment = " ap/ 01/01/2024-01/01/2025";
        LocalDate firstDate = LocalDate.of(2024, 1, 1);
        LocalDate secondDate = LocalDate.of(2025, 1, 1);
        List<Range<?>> ranges = new ArrayList<>();
        ranges.add(new Range<LocalDate>(firstDate, secondDate));
        criteriaPredicate = new PersonWithCriteriaPredicate(ranges);
        expected = new FilterCommand(criteriaPredicate);
        assertParseSuccess(parser, appointment, expected);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // name
        String name = " n/ Alice";
        assertParseFailure(parser, name, String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

}
