package seedu.address.logic.parser.consultation;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.consultation.ImportConsultCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class ImportConsultCommandParserTest {

    private ImportConsultCommandParser parser = new ImportConsultCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ImportConsultCommand.MESSAGE_USAGE), () -> parser.parse("     "));
    }

    @Test
    public void parse_validArgs_returnsImportCommand() throws ParseException {
        String validFile = "consultations.csv";
        ImportConsultCommand expectedCommand = new ImportConsultCommand(validFile);
        ImportConsultCommand actualCommand = parser.parse(validFile);
        assert(expectedCommand.equals(actualCommand));
    }

    @Test
    public void parse_invalidPath_throwsParseException() {
        // Test path with parent directory reference
        assertThrows(ParseException.class,
                ImportConsultCommand.MESSAGE_FILE_OUTSIDE_PROJECT, () -> parser.parse("../consultations.csv"));

        // Test absolute path
        assertThrows(ParseException.class,
                ImportConsultCommand.MESSAGE_FILE_OUTSIDE_PROJECT, () -> parser.parse("/consultations.csv"));

        // Test current directory reference
        assertThrows(ParseException.class,
                ImportConsultCommand.MESSAGE_FILE_OUTSIDE_PROJECT, () -> parser.parse("./consultations.csv"));
    }

    @Test
    public void parse_homeDirectoryPath_success() throws ParseException {
        String homeFile = "~/documents/consultations.csv";
        ImportConsultCommand expectedCommand = new ImportConsultCommand(homeFile);
        ImportConsultCommand actualCommand = parser.parse(homeFile);
        assert(expectedCommand.equals(actualCommand));
    }
}
