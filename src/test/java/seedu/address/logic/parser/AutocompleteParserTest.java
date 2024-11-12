package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.getStressTestAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class AutocompleteParserTest {
    private static final int PARSER_TIME_LIMIT = 300; // in milliseconds

    @TempDir
    public Path temporaryFolder;
    private Model model;

    @BeforeEach
    public void setUp() throws IOException {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void parseCommand_emptyInput_returnsNoSuggestions() {
        AutocompleteParser autocompleteParser = new AutocompleteParser();
        String userInput = "";

        HashMap<String, String> suggestions = autocompleteParser.parseCommand(userInput, model.getAddressBook(),
                0);

        assertEquals(suggestions, new HashMap<>());
    }

    @Test
    public void parseCommand_blankInput_returnsNoSuggestions() {
        AutocompleteParser autocompleteParser = new AutocompleteParser();
        String userInput = " ";

        HashMap<String, String> suggestions = autocompleteParser.parseCommand(userInput, model.getAddressBook(),
                userInput.length());

        assertEquals(suggestions, new HashMap<>());
    }

    @Test
    public void parseCommand_caretOnBlankCharacter_returnsNoSuggestions() {
        AutocompleteParser autocompleteParser = new AutocompleteParser();
        String userInput = "m/MODULE ";

        HashMap<String, String> suggestions = autocompleteParser.parseCommand(userInput, model.getAddressBook(),
                userInput.length());

        assertEquals(suggestions, new HashMap<>());
    }

    @Test
    public void parseCommand_invalidPrefix_returnsNoSuggestions() {
        AutocompleteParser autocompleteParser = new AutocompleteParser();
        String userInput = "add ummm/";

        HashMap<String, String> suggestions = autocompleteParser.parseCommand(userInput, model.getAddressBook(),
                userInput.length());

        assertEquals(suggestions, new HashMap<>());
    }

    @Test
    public void parseCommand_prefixOnFirstWord_returnsNoSuggestions() {
        AutocompleteParser autocompleteParser = new AutocompleteParser();
        String userInput = "m/";

        HashMap<String, String> suggestions = autocompleteParser.parseCommand(userInput, model.getAddressBook(),
                userInput.length());

        assertEquals(suggestions, new HashMap<>());
    }

    // Test for command parsing
    @Test
    public void parseCommand_commandNotOnFirstWord_returnsNoSuggestions() {
        AutocompleteParser autocompleteParser = new AutocompleteParser();
        String userInput = "m/MODULE ad";

        HashMap<String, String> suggestions = autocompleteParser.parseCommand(userInput, model.getAddressBook(),
                userInput.length());

        assertEquals(suggestions, new HashMap<>());
    }

    @Test
    public void parseCommand_aInput_returnsAddArchiveCommandSuggestion() {
        AutocompleteParser autocompleteParser = new AutocompleteParser();
        String userInput = "a";
        HashMap<String, String> expectedSuggestions = new HashMap<>();
        expectedSuggestions.put("add", "add");
        expectedSuggestions.put("archive", "archive");

        HashMap<String, String> suggestions = autocompleteParser.parseCommand(userInput, model.getAddressBook(),
                userInput.length());

        assertEquals(suggestions, expectedSuggestions);
    }

    @Test
    public void parseCommand_completeCommandInput_returnsNoSuggestions() {
        AutocompleteParser autocompleteParser = new AutocompleteParser();
        String userInput = "add";
        HashMap<String, String> expectedSuggestions = new HashMap<>();

        HashMap<String, String> suggestions = autocompleteParser.parseCommand(userInput, model.getAddressBook(),
                userInput.length());

        assertEquals(suggestions, expectedSuggestions);
    }

    // Test for module parsing
    @Test
    public void parseCommand_modulePrefixInput_returnsAllModuleSuggestions() {
        AutocompleteParser autocompleteParser = new AutocompleteParser();
        String userInput = "add m/";
        HashMap<String, String> expectedSuggestions = new HashMap<>();
        expectedSuggestions.put("MA1522", "add m/MA1522");
        expectedSuggestions.put("CS1101", "add m/CS1101");
        expectedSuggestions.put("EL1101", "add m/EL1101");

        HashMap<String, String> suggestions = autocompleteParser.parseCommand(userInput, model.getAddressBook(),
                userInput.length());

        assertEquals(suggestions, expectedSuggestions);
    }

    @Test
    public void parseCommand_modulePrefixInputStartWithM_returnsOneModuleSuggestion() {
        AutocompleteParser autocompleteParser = new AutocompleteParser();
        String userInput = "ed m/M";
        HashMap<String, String> expectedSuggestions = new HashMap<>();
        expectedSuggestions.put("MA1522", "ed m/MA1522");

        HashMap<String, String> suggestions = autocompleteParser.parseCommand(userInput, model.getAddressBook(),
                userInput.length());

        assertEquals(suggestions, expectedSuggestions);
    }

    @Test
    public void parseCommand_modulePrefixInputCaretOnPrefix_returnsAllModuleSuggestions() {
        AutocompleteParser autocompleteParser = new AutocompleteParser();
        String userInput = "add m/M";
        HashMap<String, String> expectedSuggestions = new HashMap<>();
        expectedSuggestions.put("MA1522", "add m/MA1522");
        expectedSuggestions.put("CS1101", "add m/CS1101");
        expectedSuggestions.put("EL1101", "add m/EL1101");

        HashMap<String, String> suggestions = autocompleteParser.parseCommand(userInput, model.getAddressBook(),
                userInput.length() - 1);

        assertEquals(suggestions, expectedSuggestions);
    }

    // Test for tag parsing
    @Test
    public void parseCommand_tagPrefixInput_returnsAllTagSuggestions() {
        AutocompleteParser autocompleteParser = new AutocompleteParser();
        String userInput = "edit t/";
        HashMap<String, String> expectedSuggestions = new HashMap<>();
        expectedSuggestions.put("friends", "edit t/friends");
        expectedSuggestions.put("owesMoney", "edit t/owesMoney");
        expectedSuggestions.put("runner", "edit t/runner");

        HashMap<String, String> suggestions = autocompleteParser.parseCommand(userInput, model.getAddressBook(),
                userInput.length());

        assertEquals(suggestions, expectedSuggestions);
    }

    @Test
    public void parseCommand_tagPrefixInputStartWithF_returnsOneTagSuggestion() {
        AutocompleteParser autocompleteParser = new AutocompleteParser();
        String userInput = "edit t/f";
        HashMap<String, String> expectedSuggestions = new HashMap<>();
        expectedSuggestions.put("friends", "edit t/friends");

        HashMap<String, String> suggestions = autocompleteParser.parseCommand(userInput, model.getAddressBook(),
                userInput.length());

        assertEquals(suggestions, expectedSuggestions);
    }

    @Test
    public void parseCommand_tagPrefixInputCaretOnPrefix_returnsAllTagSuggestions() {
        AutocompleteParser autocompleteParser = new AutocompleteParser();
        String userInput = "edit t/f";
        HashMap<String, String> expectedSuggestions = new HashMap<>();
        expectedSuggestions.put("friends", "edit t/friends");
        expectedSuggestions.put("owesMoney", "edit t/owesMoney");
        expectedSuggestions.put("runner", "edit t/runner");

        HashMap<String, String> suggestions = autocompleteParser.parseCommand(userInput, model.getAddressBook(),
                userInput.length() - 1);

        assertEquals(suggestions, expectedSuggestions);
    }

    // Test for gender parsing
    @Test
    public void parseCommand_genderPrefixInput_returnsAllGenderSuggestions() {
        AutocompleteParser autocompleteParser = new AutocompleteParser();
        String userInput = "add g/";
        HashMap<String, String> expectedSuggestions = new HashMap<>();
        expectedSuggestions.put("male", "add g/male");
        expectedSuggestions.put("female", "add g/female");

        HashMap<String, String> suggestions = autocompleteParser.parseCommand(userInput, model.getAddressBook(),
                userInput.length());

        assertEquals(suggestions, expectedSuggestions);
    }

    @Test
    public void parseCommand_genderPrefixInputStartsWithM_returnsMaleGenderSuggestion() {
        AutocompleteParser autocompleteParser = new AutocompleteParser();
        String userInput = "add g/m";
        HashMap<String, String> expectedSuggestions = new HashMap<>();
        expectedSuggestions.put("male", "add g/male");

        HashMap<String, String> suggestions = autocompleteParser.parseCommand(userInput, model.getAddressBook(),
                userInput.length());

        assertEquals(suggestions, expectedSuggestions);
    }

    @Test
    public void parseCommand_genderPrefixInputCaretOnPrefix_returnsAllGenderSuggestions() {
        AutocompleteParser autocompleteParser = new AutocompleteParser();
        String userInput = "add g/m";
        HashMap<String, String> expectedSuggestions = new HashMap<>();
        expectedSuggestions.put("male", "add g/male");
        expectedSuggestions.put("female", "add g/female");

        HashMap<String, String> suggestions = autocompleteParser.parseCommand(userInput, model.getAddressBook(),
                userInput.length() - 1);

        assertEquals(suggestions, expectedSuggestions);
    }
    // Test file paths
    @Test
    public void parseCommand_existingFilePath_returnsFilePathSuggestion() throws IOException {
        // Add test file
        Path archiveTestFile = temporaryFolder.resolve("archiveTest.json");
        Files.createFile(archiveTestFile);

        AutocompleteParser autocompleteParser = new AutocompleteParser(temporaryFolder.toString());
        String userInput = "archive pa/";
        HashMap<String, String> suggestions = autocompleteParser.parseCommand(userInput, model.getAddressBook(),
                userInput.length());

        assertTrue(suggestions.containsKey("archiveTest.json"));
    }

    @Test
    public void parseCommand_nonExistingFilePath_returnsNoFilePathSuggestion() {
        AutocompleteParser autocompleteParser = new AutocompleteParser(temporaryFolder.toString());
        String userInput = "archive pa/";
        HashMap<String, String> suggestions = autocompleteParser.parseCommand(userInput, model.getAddressBook(),
                userInput.length());

        assertTrue(suggestions.isEmpty());
    }

    @Test
    public void parseCommand_nonExistingDirectory_returnsNoFilePathSuggestion() {
        AutocompleteParser autocompleteParser = new AutocompleteParser("X@!9393049118!!");
        String userInput = "archive pa/";
        HashMap<String, String> suggestions = autocompleteParser.parseCommand(userInput, model.getAddressBook(),
                userInput.length());

        assertTrue(suggestions.isEmpty());
    }



    // Test caret positions
    @Test
    public void parseCommand_caretPositionAtSecondLetter_returnsEditCommandSuggestion() {
        AutocompleteParser autocompleteParser = new AutocompleteParser();
        String userInput = "ed 1 m/";
        HashMap<String, String> expectedSuggestions = new HashMap<>();
        expectedSuggestions.put("edit", "edit 1 m/");

        HashMap<String, String> suggestions = autocompleteParser.parseCommand(userInput, model.getAddressBook(), 2);

        assertEquals(suggestions, expectedSuggestions);
    }

    @Test
    public void parseCommand_caretPositionAtModule_returnsEditCommandSuggestion() {
        AutocompleteParser autocompleteParser = new AutocompleteParser();
        String userInput = "add 1 m/CS m/1010";
        HashMap<String, String> expectedSuggestions = new HashMap<>();
        expectedSuggestions.put("CS1101", "add 1 m/CS1101 m/1010");

        HashMap<String, String> suggestions = autocompleteParser.parseCommand(userInput, model.getAddressBook(), 9);

        assertEquals(suggestions, expectedSuggestions);
    }

    // Stress tests
    @Test
    public void stressTestParseCommand_oneThousandEntries_executeWithinTime() {
        AddressBook stressTestAB = getStressTestAddressBook();

        long start = System.currentTimeMillis();
        AutocompleteParser autocompleteParser = new AutocompleteParser();
        String userInput = "add 1 m/CS m/1010";
        HashMap<String, String> expectedSuggestions = new HashMap<>();
        expectedSuggestions.put("CS1101", "add 1 m/CS1101 m/1010");

        HashMap<String, String> suggestions = autocompleteParser.parseCommand(userInput, stressTestAB, 9);
        long end = System.currentTimeMillis();

        assertTrue((end - start) < PARSER_TIME_LIMIT);
    }
}
