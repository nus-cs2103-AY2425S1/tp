package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.FilterCommandParser.INCORRECT_AGE_AND_RANGE;
import static seedu.address.logic.parser.FilterCommandParser.INCORRECT_DATE_FORMAT;
import static seedu.address.logic.parser.FilterCommandParser.INCORRECT_RANGE;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.model.Range;
import seedu.address.model.person.PersonWithCriteriaPredicate;
import seedu.address.model.tag.Tag;


public class FilterCommandParserTest {
    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validAge_returnsFilterCommand() {

        String age = " b/ 10-99";
        List<Range<?>> firstRanges = new ArrayList<>();
        firstRanges.add(new Range<Integer>(10, 99));
        PersonWithCriteriaPredicate criteriaPredicate = new PersonWithCriteriaPredicate(firstRanges);
        FilterCommand expected = new FilterCommand(criteriaPredicate);
        assertParseSuccess(parser, age, expected);

        // lower boundary age range
        String ageLowerBoundary = " b/ 0-0";
        List<Range<?>> lowerBoundaryRanges = new ArrayList<>();
        lowerBoundaryRanges.add(new Range<Integer>(0, 0));
        PersonWithCriteriaPredicate lowerBoundaryPredicate = new PersonWithCriteriaPredicate(lowerBoundaryRanges);
        FilterCommand expectedLowerBoundaryCommand = new FilterCommand(lowerBoundaryPredicate);
        assertParseSuccess(parser, ageLowerBoundary, expectedLowerBoundaryCommand);

        // upper boundary age range using Integer.MAX_VALUE
        String ageUpperBoundary = " b/ " + Integer.MAX_VALUE + "-" + Integer.MAX_VALUE;
        List<Range<?>> upperBoundaryRanges = new ArrayList<>();
        upperBoundaryRanges.add(new Range<Integer>(Integer.MAX_VALUE, Integer.MAX_VALUE));
        PersonWithCriteriaPredicate upperBoundaryPredicate = new PersonWithCriteriaPredicate(upperBoundaryRanges);
        FilterCommand expectedUpperBoundaryCommand = new FilterCommand(upperBoundaryPredicate);
        assertParseSuccess(parser, ageUpperBoundary, expectedUpperBoundaryCommand);
    }

    @Test
    public void parse_validAppointment_returnsFilterCommand() {

        String appointment = " ap/ 01/01/2024-01/01/2025";
        LocalDate firstDate = LocalDate.of(2024, 1, 1);
        LocalDate secondDate = LocalDate.of(2025, 1, 1);
        List<Range<?>> ranges = new ArrayList<>();
        ranges.add(new Range<LocalDate>(firstDate, secondDate));
        PersonWithCriteriaPredicate criteriaPredicate = new PersonWithCriteriaPredicate(ranges);
        FilterCommand expected = new FilterCommand(criteriaPredicate);
        assertParseSuccess(parser, appointment, expected);

        // single day appointment range
        String singleDayRange = " ap/ 01/01/2025-01/01/2025";
        LocalDate singleDate = LocalDate.of(2025, 1, 1);
        List<Range<?>> singleDayRanges = new ArrayList<>();
        singleDayRanges.add(new Range<LocalDate>(singleDate, singleDate));
        PersonWithCriteriaPredicate singleDayPredicate = new PersonWithCriteriaPredicate(singleDayRanges);
        FilterCommand expectedSingleDayCommand = new FilterCommand(singleDayPredicate);
        assertParseSuccess(parser, singleDayRange, expectedSingleDayCommand);
    }

    @Test
    public void parse_validTag_returnsFilterCommand() {
        String tagStr = " t/ obesity";
        String differentCaseTagStr = " t/ OBesity";
        Tag tag = new Tag("obesity");
        Set<Tag> tagSet = Set.of(tag);
        PersonWithCriteriaPredicate criteriaPredicate = new PersonWithCriteriaPredicate(new ArrayList<>(), tagSet);
        FilterCommand expected = new FilterCommand(criteriaPredicate);
        assertParseSuccess(parser, tagStr, expected);
        assertParseSuccess(parser, differentCaseTagStr, expected);
    }


    @Test
    public void parse_invalidRange_throwsParseException() {
        // invalid age range
        String age = " b/ 10-";
        assertParseFailure(parser, age, String.format(INCORRECT_AGE_AND_RANGE));
        age = " b/ 10-fifty";
        assertParseFailure(parser, age, String.format(INCORRECT_AGE_AND_RANGE));
        age = " b/ 100-0";
        assertParseFailure(parser, age, String.format(INCORRECT_AGE_AND_RANGE));
        age = " b/ -5--1";
        assertParseFailure(parser, age, String.format(INCORRECT_AGE_AND_RANGE));

        // invalid appointment range
        String appointment = " ap/ 01/01/2024-01-01-2026";
        assertParseFailure(parser, appointment, String.format(INCORRECT_RANGE));
        appointment = " ap/ 01/01/2025-01/01/2024";
        assertParseFailure(parser, appointment, String.format(INCORRECT_RANGE));
        appointment = " ap/ 01/01/2025-12/30/2025"; // incorrect date format
        assertParseFailure(parser, appointment, String.format(INCORRECT_DATE_FORMAT));


    }
    @Test
    public void parse_invalidArgs_throwsParseException() {
        // name
        String name = " n/ Alice";
        assertParseFailure(parser, name, String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

}
