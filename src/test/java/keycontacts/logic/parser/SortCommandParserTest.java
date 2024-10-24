package keycontacts.logic.parser;

import static keycontacts.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import keycontacts.logic.commands.SortCommand;
import keycontacts.logic.parser.exceptions.ParseException;
import keycontacts.model.student.StudentComparator;

public class SortCommandParserTest {

    private final SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(""),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidSortOrder_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" n/invalid"),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_noPrefixesPresent_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("invalid"),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsSortCommand() throws Exception {
        SortCommand command = parser.parse(" n/ASC p/DESC a/ASC gl/DESC");
        assertTrue(command instanceof SortCommand);
        StudentComparator comparator = command.getComparator();
        assertTrue(comparator != null);
    }

    @Test
    public void parse_clear_returnsSortCommand() throws Exception {
        SortCommand command = parser.parse("clear");
        assertTrue(command instanceof SortCommand);
        assertTrue(command.getComparator() == null);
    }

    @Test
    public void parse_duplicatePrefixes_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" n/ASC n/DESC"),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }
}
