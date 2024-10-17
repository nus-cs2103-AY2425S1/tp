package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Comparator;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;
public class SortCommandParserTest {

    private final SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_validArgsName_returnsSortCommand() throws Exception {
        SortCommand command = parser.parse("name");
        Comparator<? extends Person> expectedComparator = Comparator.comparing(Person::getNameString);
        SortCommand expectedCommand = new SortCommand(expectedComparator);
        assertEquals(expectedCommand, command);
    }

    @Test
    public void parse_validArgsSubject_returnsSortCommand() throws Exception {
        SortCommand command = parser.parse("subject");
        Comparator<? extends Person> expectedComparator = Comparator.comparing(Person::getSubjectString);
        SortCommand expectedCommand = new SortCommand(expectedComparator);
        assertEquals(expectedCommand, command);
    }

    @Test
    public void parse_validArgsClasses_returnsSortCommand() throws Exception {
        SortCommand command = parser.parse("classes");
        Comparator<? extends Person> expectedComparator = Comparator.comparing(Person::getClassesString);
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
}
