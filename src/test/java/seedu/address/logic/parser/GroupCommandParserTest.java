package seedu.address.logic.parser;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENTS;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.GroupCommand;
import seedu.address.logic.parser.exceptions.ParseException;


public class GroupCommandParserTest {

    private final GroupCommandParser parser = new GroupCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws Exception {
        String userInput = " " + PREFIX_GROUP + "Group A " + PREFIX_STUDENTS + "Alice " + PREFIX_STUDENTS + "Bob";
        GroupCommand expectedCommand = new GroupCommand("Group A", List.of("Alice", "Bob"));
        GroupCommand command = parser.parse(userInput);
        assertEquals(expectedCommand, command);
    }

    @Test
    public void parse_missingGroupPrefix_throwsParseException() {
        String userInput = " " + PREFIX_STUDENTS + "Alice " + PREFIX_STUDENTS + "Bob";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_missingStudentsPrefix_throwsParseException() {
        String userInput = " " + PREFIX_GROUP + "Group A";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_duplicateGroupPrefix_throwsParseException() {
        String userInput = " " + PREFIX_GROUP + "Group A " + PREFIX_GROUP + "Group B " + PREFIX_STUDENTS + "Alice";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_duplicateStudentsPrefix_success() throws Exception {
        String userInput = " " + PREFIX_GROUP + "Group A " + PREFIX_STUDENTS + "Alice " + PREFIX_STUDENTS + "Alice";
        GroupCommand expectedCommand = new GroupCommand("Group A", List.of("Alice", "Alice"));
        GroupCommand command = parser.parse(userInput);
        assertEquals(expectedCommand, command);
    }
}
