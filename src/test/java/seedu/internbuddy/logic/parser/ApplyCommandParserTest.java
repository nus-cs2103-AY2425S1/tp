package seedu.internbuddy.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.internbuddy.testutil.TypicalIndexes.INDEX_FIRST_COMPANY;

import org.junit.jupiter.api.Test;

import seedu.internbuddy.logic.commands.ApplyCommand;
import seedu.internbuddy.logic.parser.exceptions.ParseException;
import seedu.internbuddy.model.application.AppStatus;
import seedu.internbuddy.model.application.Application;
import seedu.internbuddy.model.application.Description;
import seedu.internbuddy.model.name.Name;

public class ApplyCommandParserTest {

    private final ApplyCommandParser parser = new ApplyCommandParser();

    @Test
    public void parse_validArgs_returnsApplyCommand() throws Exception {
        String userInput = "1 n/Software Engineer d/Develop web applications";
        Name expectedName = new Name("Software Engineer");
        Description expectedDescription = new Description("Develop web applications");
        AppStatus expectedAppStatus = new AppStatus("APPLIED");
        Application expectedApplication = new Application(expectedName, expectedDescription, expectedAppStatus);

        ApplyCommand expectedCommand = new ApplyCommand(INDEX_FIRST_COMPANY, expectedApplication);

        assertEquals(expectedCommand, parser.parse(userInput));
    }

    @Test
    public void parse_optionalAppStatus_returnsApplyCommandWithDefaultAppStatus() throws Exception {
        String userInput = "1 n/Frontend Developer d/Develop user interfaces";
        Name expectedName = new Name("Frontend Developer");
        Description expectedDescription = new Description("Develop user interfaces");
        AppStatus expectedAppStatus = new AppStatus("APPLIED"); // Default app status
        Application expectedApplication = new Application(expectedName, expectedDescription, expectedAppStatus);

        ApplyCommand expectedCommand = new ApplyCommand(INDEX_FIRST_COMPANY, expectedApplication);

        assertEquals(expectedCommand, parser.parse(userInput));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        String invalidUserInput = "a n/Software Engineer d/Develop web applications";
        assertThrows(ParseException.class, () -> parser.parse(invalidUserInput));
    }

    @Test
    public void parse_missingRequiredPrefixes_throwsParseException() {
        final String invalidUserInput = "1 d/Develop web applications"; // Missing name prefix
        assertThrows(ParseException.class, () -> parser.parse(invalidUserInput));

        final String invalidUserInput2 = "1 n/Software Engineer"; // Missing description prefix
        assertThrows(ParseException.class, () -> parser.parse(invalidUserInput2));
    }

    @Test
    public void parse_duplicatePrefixes_throwsParseException() {
        String invalidUserInput = "1 n/Software Engineer n/Backend Developer d/Develop web applications";
        assertThrows(ParseException.class, () -> parser.parse(invalidUserInput));
    }

    @Test
    public void parse_invalidCommandFormat_throwsParseException() {
        String invalidUserInput = "apply";
        assertThrows(ParseException.class, () -> parser.parse(invalidUserInput));
    }
}
