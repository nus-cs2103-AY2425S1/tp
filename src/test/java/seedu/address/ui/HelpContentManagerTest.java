package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;

public class HelpContentManagerTest {

    private HelpContentManager helpContentManager;

    @BeforeEach
    public void setUp() {
        helpContentManager = new HelpContentManager();
    }

    @Test
    public void initializeContents_contentMapInitialized() {
        // Check that the contentMap is initialized and contains the expected keys
        assertNotNull(helpContentManager.getContent("Introduction"));
        assertNotNull(helpContentManager.getContent("Quick Start"));
        assertNotNull(helpContentManager.getContent("Command Summary"));

        // Verify that all expected keys are present
        assertEquals(13, helpContentManager.getTableOfContents().size(),
                "Table of Contents should contain 13 entries");
    }

    @Test
    public void getContent_validKey_returnsCorrectContent() {
        // Check if valid keys return the correct content
        String quickStartContent = helpContentManager.getContent("Quick Start");
        assertNotNull(quickStartContent, "Content for 'Quick Start' should not be null");
        assertEquals(true, quickStartContent.contains("Ensure you have Java `17`"),
                "Quick Start content mismatch");

        String introductionContent = helpContentManager.getContent("Introduction");
        assertNotNull(introductionContent, "Content for 'Introduction' should not be null");
        assertEquals(true, introductionContent.contains("Financial Assurance Revolutionary Telemarketer"),
                "Introduction content mismatch");
    }

    @Test
    public void getContent_invalidKey_returnsNull() {
        // Check if an invalid key returns null
        String nonExistentContent = helpContentManager.getContent("Non Existent Key");
        assertNull(nonExistentContent, "Content for non-existent key should return null");
    }

    @Test
    public void getTableOfContents_initialized_returnsCorrectItems() {
        // Check that the Table of Contents is initialized and contains the correct items
        ObservableList<String> tableOfContents = helpContentManager.getTableOfContents();
        assertNotNull(tableOfContents, "Table of Contents should not be null");
        assertEquals(13, tableOfContents.size(), "Table of Contents should contain 13 entries");

        // Check if specific items exist in the Table of Contents
        assertEquals("Introduction", tableOfContents.get(0),
                "First item in the TOC should be 'Introduction'");
        assertEquals("Quick Start", tableOfContents.get(1),
                "Second item in the TOC should be 'Quick Start'");
    }

    @Test
    public void contentMap_sizeAndKeys() {
        // Ensure the content map has the expected number of entries
        assertEquals(13, helpContentManager.getTableOfContents().size(), "Content map should contain 13 sections");

        // Verify that all expected keys are present
        String[] expectedKeys = {
            "Introduction", "Quick Start", "Command Format Guidelines", "Adding a Contact",
            "Listing All Contacts", "Editing a Contact", "Finding Contacts", "Deleting a Contact",
            "Clearing All Entries", "Marking a Person as Paid/Unpaid", "Saving and Editing Data",
            "Exiting the Program", "Command Summary"
        };

        for (String key : expectedKeys) {
            assertNotNull(helpContentManager.getContent(key), "Content for key '" + key + "' should not be null");
        }
    }

    @Test
    public void emptyContentHandling_nonExistentEntry_returnsNull() {
        // Check if a non-existent entry returns null
        String missingContent = helpContentManager.getContent("NonExistentEntry");
        assertNull(missingContent, "Content for a non-existent entry should be null");
    }
}
