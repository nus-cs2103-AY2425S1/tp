package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class FormatSuggestionTest {

    @Test
    public void testRemoveFirstWord() {
        // Test normal case
        assertEquals("world hello", FormatSuggestion.removeFirstWord("hello world hello"));

        // Test single word
        assertEquals("", FormatSuggestion.removeFirstWord("hello"));

        // Test empty string
        assertEquals("", FormatSuggestion.removeFirstWord(""));

        // Test multiple spaces
        assertEquals("world  hello", FormatSuggestion.removeFirstWord("hello  world  hello"));
    }

    @Test
    public void testGetRemainingFormat_addCommand() {
        FormatSuggestion addSuggestion = new FormatSuggestion(
                "add",
                "add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]",
                new String[]{"n/", "p/", "e/", "a/", "t/"}
        );

        // Test just command
        assertEquals(" n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]",
                addSuggestion.getRemainingFormat("add"));

        // Test partial command
        assertEquals(" p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]",
                addSuggestion.getRemainingFormat("add n/John"));

        // Test multiple fields entered
        assertEquals(" e/EMAIL a/ADDRESS [t/TAG]",
                addSuggestion.getRemainingFormat("add n/John p/12345"));
    }

    @Test
    public void testGetRemainingFormat_editCommand() {
        FormatSuggestion editSuggestion = new FormatSuggestion(
                "edit",
                "edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]",
                new String[]{"n/", "p/", "e/", "a/", "t/"}
        );

        // Test just command
        assertEquals(" INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]",
                editSuggestion.getRemainingFormat("edit"));

        // Test with index
        assertEquals(" [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]",
                editSuggestion.getRemainingFormat("edit 1"));

        // Test with index and one field
        assertEquals(" [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]",
                editSuggestion.getRemainingFormat("edit 1 n/John"));
    }

    @Test
    public void testGetRemainingFormat_deleteCommand() {
        FormatSuggestion deleteSuggestion = new FormatSuggestion(
                "delete",
                "delete INDEX",
                new String[]{}
        );

        // Test just command
        assertEquals(" INDEX", deleteSuggestion.getRemainingFormat("delete"));

        // Test with index
        assertEquals("", deleteSuggestion.getRemainingFormat("delete 1"));
    }

    @Test
    public void testGetRemainingFormat_edgeCases() {
        FormatSuggestion addSuggestion = new FormatSuggestion(
                "add",
                "add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]",
                new String[]{"n/", "p/", "e/", "a/", "t/"}
        );

        // Test empty string
        assertEquals("", addSuggestion.getRemainingFormat(""));

        // Test partial command word
        assertEquals("", addSuggestion.getRemainingFormat("ad"));

        // Test with extra spaces
        assertEquals(" n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]",
                addSuggestion.getRemainingFormat("add  "));

        // Test with multiple consecutive spaces
        assertEquals(" p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]",
                addSuggestion.getRemainingFormat("add   n/John   "));
    }

    @Test
    public void testGetRemainingFormat_invalidInputs() {
        FormatSuggestion editSuggestion = new FormatSuggestion(
                "edit",
                "edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]",
                new String[]{"n/", "p/", "e/", "a/", "t/"}
        );

        // Test with invalid prefix
        assertEquals("",
                editSuggestion.getRemainingFormat("edit x/"));

        // Test with non-numeric index
        assertEquals("",
                editSuggestion.getRemainingFormat("edit abc"));
    }

    // New test methods below

    @Test
    public void testGetRemainingFormat_findCommand() {
        FormatSuggestion findSuggestion = new FormatSuggestion(
                "find",
                "find NAME",
                new String[]{}
        );

        // Test just command
        assertEquals(" NAME", findSuggestion.getRemainingFormat("find"));

        // Test with invalid name (contains numbers)
        assertEquals("", findSuggestion.getRemainingFormat("find John123"));

        // Test with valid name
        assertEquals("", findSuggestion.getRemainingFormat("find John"));
    }

    @Test
    public void testGetRemainingFormat_mixedCommands() {
        FormatSuggestion suggestion = new FormatSuggestion(
                "search",
                "search INDEX/NAME [t/TAG] [s/SORT]",
                new String[]{"t/", "s/"}
        );

        // Test with numeric index
        assertEquals(" [t/TAG] [s/SORT]", suggestion.getRemainingFormat("search 1"));

        // Test with alphabetic name
        assertEquals(" [t/TAG] [s/SORT]", suggestion.getRemainingFormat("search John"));

        // Test with alphanumeric input
        assertEquals(" [t/TAG] [s/SORT]", suggestion.getRemainingFormat("search John123"));

        // Test with one optional parameter
        assertEquals(" [s/SORT]", suggestion.getRemainingFormat("search 1 t/friend"));
    }

    @Test
    public void testGetRemainingFormat_specialCharacters() {
        FormatSuggestion addSuggestion = new FormatSuggestion(
                "add",
                "add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]",
                new String[]{"n/", "p/", "e/", "a/", "t/"}
        );

        // Test with special characters in input
        assertEquals(" p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]", addSuggestion.getRemainingFormat("add n/John@Doe"));

        // Test with multiple tags containing special characters
        assertEquals(" a/ADDRESS",
                addSuggestion.getRemainingFormat("add n/John p/12345 e/john@example.com t/friend t/co-worker"));
    }

    @Test
    public void testGetRemainingFormat_complexCommand() {
        FormatSuggestion complexSuggestion = new FormatSuggestion(
                "filter",
                "filter INDEX/NAME [d/DATE] [p/PRIORITY] [s/STATUS] [t/TAG]",
                new String[]{"d/", "p/", "s/", "t/"}
        );

        // Test with mixed optional parameters in different orders
        assertEquals(" [d/DATE] [p/PRIORITY] [t/TAG]",
                complexSuggestion.getRemainingFormat("filter 1 s/pending"));

        // Test with all optional parameters
        assertEquals("",
                complexSuggestion.getRemainingFormat(
                        "filter John d/2024-01-01 p/high s/pending t/urgent"));

        // Test with duplicate parameters
        assertEquals(" [d/DATE] [p/PRIORITY] [s/STATUS]",
                complexSuggestion.getRemainingFormat(
                        "filter 1 t/urgent t/important"));
    }

    @Test
    public void testGetRemainingFormat_caseInsensitivity() {
        FormatSuggestion suggestion = new FormatSuggestion(
                "ADD",
                "ADD n/NAME p/PHONE_NUMBER",
                new String[]{"n/", "p/"}
        );

        // Test with mixed case command
        assertEquals("", suggestion.getRemainingFormat("AdD"));

        // Test with mixed case parameters
        assertEquals(" n/NAME p/PHONE_NUMBER", suggestion.getRemainingFormat("ADD N/John"));
    }
}
