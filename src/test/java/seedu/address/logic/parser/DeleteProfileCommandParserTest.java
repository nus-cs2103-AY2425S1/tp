package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteProfileCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.profile.Profile;

public class DeleteProfileCommandParserTest {
    private final DeleteProfileCommandParser parser = new DeleteProfileCommandParser();

    @Test
    public void parse_validProfileName_returnsDeleteProfileCommand() throws ParseException {
        String validProfileName = "john-doe";
        DeleteProfileCommand resultCommand = parser.parse(validProfileName);
        assertEquals(new DeleteProfileCommand(new Profile("john-doe")), resultCommand);
    }

    @Test
    public void parse_invalidProfileName_throwsParseException() {
        String invalidProfileName = "invalid name with spaces";
        assertThrows(ParseException.class, () -> parser.parse(invalidProfileName));
    }

    @Test
    public void parse_profileNameWithSubdirectories_throwsParseException() {
        String invalidProfilePath = "data/invalidProfile";
        assertThrows(ParseException.class, () -> parser.parse(invalidProfilePath));
    }

    @Test
    public void parse_uppercaseProfileName_returnsLowercaseProfileCommand() throws ParseException {
        String uppercaseProfileName = "JOHN-DOE";
        DeleteProfileCommand resultCommand = parser.parse(uppercaseProfileName);
        assertEquals(new DeleteProfileCommand(new Profile("john-doe")), resultCommand);
    }

    @Test
    public void parse_specialCharacterInProfileName_throwsParseException() {
        String specialCharProfileName = "john*doe";
        assertThrows(ParseException.class, () -> parser.parse(specialCharProfileName));
    }

    @Test
    public void parse_emptyProfileName_throwParseException() {
        String emptyStr = "";
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteProfileCommand.MESSAGE_USAGE), () ->
                        parser.parse(emptyStr));
    }
}
