package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.getStressTestAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class AutocompleteParserTest {
    private static final int PARSER_TIME_LIMIT = 300; // in milliseconds
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
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

    // Test for command parsing
    @Test
    public void parseCommand_adInput_returnsAddCommandSuggestion() {
        AutocompleteParser autocompleteParser = new AutocompleteParser();
        String userInput = "ad";
        HashMap<String, String> expectedSuggestions = new HashMap<>();
        expectedSuggestions.put("add", "add");

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
        String userInput = "m/";
        HashMap<String, String> expectedSuggestions = new HashMap<>();
        expectedSuggestions.put("MA1522", "m/MA1522");
        expectedSuggestions.put("CS1101", "m/CS1101");
        expectedSuggestions.put("EL1101", "m/EL1101");

        HashMap<String, String> suggestions = autocompleteParser.parseCommand(userInput, model.getAddressBook(),
                userInput.length());

        assertEquals(suggestions, expectedSuggestions);
    }

    @Test
    public void parseCommand_modulePrefixInputStartWithM_returnsOneModuleSuggestion() {
        AutocompleteParser autocompleteParser = new AutocompleteParser();
        String userInput = "m/M";
        HashMap<String, String> expectedSuggestions = new HashMap<>();
        expectedSuggestions.put("MA1522", "m/MA1522");

        HashMap<String, String> suggestions = autocompleteParser.parseCommand(userInput, model.getAddressBook(),
                userInput.length());

        assertEquals(suggestions, expectedSuggestions);
    }

    @Test
    public void parseCommand_modulePrefixInputCaretOnPrefix_returnsAllModuleSuggestions() {
        AutocompleteParser autocompleteParser = new AutocompleteParser();
        String userInput = "m/M";
        HashMap<String, String> expectedSuggestions = new HashMap<>();
        expectedSuggestions.put("MA1522", "m/MA1522");
        expectedSuggestions.put("CS1101", "m/CS1101");
        expectedSuggestions.put("EL1101", "m/EL1101");

        HashMap<String, String> suggestions = autocompleteParser.parseCommand(userInput, model.getAddressBook(),
                userInput.length() - 1);

        assertEquals(suggestions, expectedSuggestions);
    }

    // Test for tag parsing
    @Test
    public void parseCommand_tagPrefixInput_returnsAllTagSuggestions() {
        AutocompleteParser autocompleteParser = new AutocompleteParser();
        String userInput = "t/";
        HashMap<String, String> expectedSuggestions = new HashMap<>();
        expectedSuggestions.put("friends", "t/friends");
        expectedSuggestions.put("owesMoney", "t/owesMoney");
        expectedSuggestions.put("runner", "t/runner");

        HashMap<String, String> suggestions = autocompleteParser.parseCommand(userInput, model.getAddressBook(),
                userInput.length());

        assertEquals(suggestions, expectedSuggestions);
    }

    @Test
    public void parseCommand_tagPrefixInputStartWithF_returnsOneTagSuggestion() {
        AutocompleteParser autocompleteParser = new AutocompleteParser();
        String userInput = "t/f";
        HashMap<String, String> expectedSuggestions = new HashMap<>();
        expectedSuggestions.put("friends", "t/friends");

        HashMap<String, String> suggestions = autocompleteParser.parseCommand(userInput, model.getAddressBook(),
                userInput.length());

        assertEquals(suggestions, expectedSuggestions);
    }

    @Test
    public void parseCommand_tagPrefixInputCaretOnPrefix_returnsAllTagSuggestions() {
        AutocompleteParser autocompleteParser = new AutocompleteParser();
        String userInput = "t/f";
        HashMap<String, String> expectedSuggestions = new HashMap<>();
        expectedSuggestions.put("friends", "t/friends");
        expectedSuggestions.put("owesMoney", "t/owesMoney");
        expectedSuggestions.put("runner", "t/runner");

        HashMap<String, String> suggestions = autocompleteParser.parseCommand(userInput, model.getAddressBook(),
                userInput.length() - 1);

        assertEquals(suggestions, expectedSuggestions);
    }

    // Test for gender parsing
    @Test
    public void parseCommand_genderPrefixInput_returnsAllGenderSuggestions() {
        AutocompleteParser autocompleteParser = new AutocompleteParser();
        String userInput = "g/";
        HashMap<String, String> expectedSuggestions = new HashMap<>();
        expectedSuggestions.put("male", "g/male");
        expectedSuggestions.put("female", "g/female");

        HashMap<String, String> suggestions = autocompleteParser.parseCommand(userInput, model.getAddressBook(),
                userInput.length());

        assertEquals(suggestions, expectedSuggestions);
    }

    @Test
    public void parseCommand_genderPrefixInputStartsWithM_returnsMaleGenderSuggestion() {
        AutocompleteParser autocompleteParser = new AutocompleteParser();
        String userInput = "g/m";
        HashMap<String, String> expectedSuggestions = new HashMap<>();
        expectedSuggestions.put("male", "g/male");

        HashMap<String, String> suggestions = autocompleteParser.parseCommand(userInput, model.getAddressBook(),
                userInput.length());

        assertEquals(suggestions, expectedSuggestions);
    }

    @Test
    public void parseCommand_genderPrefixInputCaretOnPrefix_returnsAllGenderSuggestions() {
        AutocompleteParser autocompleteParser = new AutocompleteParser();
        String userInput = "g/m";
        HashMap<String, String> expectedSuggestions = new HashMap<>();
        expectedSuggestions.put("male", "g/male");
        expectedSuggestions.put("female", "g/female");

        HashMap<String, String> suggestions = autocompleteParser.parseCommand(userInput, model.getAddressBook(),
                userInput.length() - 1);

        assertEquals(suggestions, expectedSuggestions);
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
