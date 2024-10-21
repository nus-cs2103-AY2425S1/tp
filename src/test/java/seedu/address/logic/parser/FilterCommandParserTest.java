package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.role.RoleContainsKeywordsPredicate;

public class FilterCommandParserTest {

    private final FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(""));
    }

    @Test
    public void parse_validArgs_returnsFilterCommand() throws ParseException {
        FilterCommand command = parser.parse("admin manager");
        assertEquals(new FilterCommand(new RoleContainsKeywordsPredicate(Arrays.asList("admin", "manager"))), command);
    }

    @Test
    public void parse_argsWithExtraSpaces_returnsFilterCommand() throws ParseException {
        FilterCommand command = parser.parse("  admin   manager  ");
        assertEquals(new FilterCommand(new RoleContainsKeywordsPredicate(Arrays.asList("admin", "manager"))), command);
    }

    @Test
    public void parse_singleKeyword_returnsFilterCommand() throws ParseException {
        FilterCommand command = parser.parse("admin");
        assertEquals(new FilterCommand(new RoleContainsKeywordsPredicate(List.of("admin"))), command);
    }
}
