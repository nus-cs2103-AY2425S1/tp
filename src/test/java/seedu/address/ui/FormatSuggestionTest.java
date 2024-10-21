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
}
