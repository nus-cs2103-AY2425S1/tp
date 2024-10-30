package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListBackupsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

class ListBackupsCommandParserTest {

    private final ListBackupsCommandParser parser = new ListBackupsCommandParser();

    @Test
    public void parse_emptyArgs_returnsListBackupsCommand() throws Exception {
        ListBackupsCommand command = parser.parse("");
        assertTrue(command instanceof ListBackupsCommand);
    }

    @Test
    public void parse_withArgs_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("extraArgs"));
    }
}
