package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Comparator;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;
import seedu.address.model.person.DaysAttended;

public class SortCommandParserTest {

    private final SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_validArgsName_returnsSortCommand() throws Exception {
        SortCommand command = parser.parse("name");
        Comparator<? super Person> expectedComparator = Comparator.comparing(person -> person.getName().toString());
        SortCommand expectedCommand = new SortCommand(expectedComparator);
        assertEquals(expectedCommand, command);
    }

    @Test
    public void parse_validArgsAttendance_returnsSortCommand() throws Exception {
        SortCommand command = parser.parse("attendance");
        Comparator<? super Person> expectedComparator = Comparator.comparing(
                Person::getDaysAttended,
                Comparator.comparing(
                        DaysAttended::getValue,
                        Comparator.nullsLast(Comparator.reverseOrder())
                )
        );
        SortCommand expectedCommand = new SortCommand(expectedComparator);
        assertEquals(expectedCommand, command);
    }

    @Test
    public void parse_validArgsSubject_returnsSortCommand() throws Exception {
        SortCommand command = parser.parse("subject");
        Comparator<? super Person> expectedComparator = Comparator.comparing(person -> person.getSubjects().toString());
        SortCommand expectedCommand = new SortCommand(expectedComparator);
        assertEquals(expectedCommand, command);
    }

    @Test
    public void parse_validArgsClasses_returnsSortCommand() throws Exception {
        SortCommand command = parser.parse("class");
        Comparator<? super Person> expectedComparator = Comparator.comparing(person -> person.getClasses().toString());
        SortCommand expectedCommand = new SortCommand(expectedComparator);
        assertEquals(expectedCommand, command);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("invalid"));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(""));
    }

    @Test
    public void parse_mixedCaseArgsName_returnsSortCommand() throws Exception {
        SortCommand command = parser.parse("NaMe");
        Comparator<? super Person> expectedComparator = Comparator.comparing(person -> person.getName().toString());
        SortCommand expectedCommand = new SortCommand(expectedComparator);
        assertEquals(expectedCommand, command);
    }

    @Test
    public void parse_whitespaceAroundArgs_returnsSortCommand() throws Exception {
        SortCommand command = parser.parse("  name  ");
        Comparator<? super Person> expectedComparator = Comparator.comparing(person -> person.getName().toString());
        SortCommand expectedCommand = new SortCommand(expectedComparator);
        assertEquals(expectedCommand, command);
    }

    @Test
    public void parse_nullArgs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> parser.parse(null));
    }

    @Test
    public void parse_longStringArgs_throwsParseException() {
        String longString = "name".repeat(1000);
        assertThrows(ParseException.class, () -> parser.parse(longString));
    }
}
