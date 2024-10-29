package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_NAME;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.editcommands.EditStudentCommand;
import seedu.address.logic.parser.editcommands.EditStudentCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;

public class EditStudentCommandParserTest {

    private EditStudentCommandParser parser = new EditStudentCommandParser();
    @Test
    public void parse_noFieldEdited_failure() {
        String userInput = "i/1";
        assertThrows(ParseException.class, () -> parser.parse(userInput), EditStudentCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void parse_invalidPrefix_failure() {
        String userInput = PREFIX_INDEX + "1 " + PREFIX_TAG + " " + PREFIX_TASK_NAME;
        assertThrows(ParseException.class, ()-> parser.parse(userInput),
            String.format(Messages.MESSAGE_ILLEGAL_PREFIX_USED, EditStudentCommand.MESSAGE_USAGE));
    }
}