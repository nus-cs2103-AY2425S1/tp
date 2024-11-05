package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SwitchCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.profile.Profile;

public class SwitchCommandParserTest {
    private final SwitchCommandParser parser = new SwitchCommandParser();

    @Test
    public void parse_emptyInput_returnsEmptyProfileCommand() throws ParseException {
        String args = "";
        SwitchCommand resultCommand = parser.parse(args);
        assertEquals(new SwitchCommand(Profile.getEmptyProfile()), resultCommand);
    }

    @Test
    public void parse_validProfileName_returnsSwitchCommand() throws ParseException {
        String validProfileName = "john-doe";
        SwitchCommand resultCommand = parser.parse(validProfileName);
        assertEquals(new SwitchCommand(new Profile("john-doe")), resultCommand);
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
        SwitchCommand resultCommand = parser.parse(uppercaseProfileName);
        assertEquals(new SwitchCommand(new Profile("john-doe")), resultCommand);
    }

    @Test
    public void parse_specialCharacterInProfileName_throwsParseException() {
        String specialCharProfileName = "john*doe";
        assertThrows(ParseException.class, () -> parser.parse(specialCharProfileName));
    }
}
