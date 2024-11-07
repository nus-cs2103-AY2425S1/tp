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
        assertNotNull(helpContentManager.getContent("FAQ and Known Issues"));

        // Verify that all expected keys are present
        assertEquals(17, helpContentManager.getTableOfContents().size(),
                "Table of Contents should contain 17 entries");
    }

    @Test
    public void getContent_validKey_returnsCorrectContent() {
        // Test with a few valid keys to ensure the correct content is returned
        String introductionContent = helpContentManager.getContent("Introduction");

        assertNotNull(introductionContent, "Content for 'Introduction' should not be null");
        assertEquals(true, introductionContent.contains("Financial Assurance Revolutionary Telemarketer"),
                "Introduction content mismatch");

        String faqContent = helpContentManager.getContent("FAQ and Known Issues");
        assertNotNull(faqContent, "Content for 'FAQ and Known Issues' should not be null");
        assertEquals(true, faqContent.contains("transfer my data to another computer"),
                "FAQ content mismatch");

        String addContent = helpContentManager.getContent("Adding a client");
        assertNotNull(addContent, "Content for 'Adding a client' should not be null");
        assertEquals(true, addContent.contains("Met a potential client or someone new?"),
                "Adding a client content mismatch");
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

        // Check if specific items exist in the Table of Contents
        assertEquals("Introduction", tableOfContents.get(0),
                "First item in the TOC should be 'Introduction'");
        assertEquals("Visual Features", tableOfContents.get(13),
                "Expected 'Visual Features' at position 13 in the TOC");
        assertEquals("Command Summary", tableOfContents.get(16),
                "Last item in the TOC should be 'Command Summary'");
    }

    @Test
    public void tableOfContents_sizeIsCorrect() {
        // Test to verify the Table of Contents size only once
        ObservableList<String> tableOfContents = helpContentManager.getTableOfContents();
        assertEquals(17, tableOfContents.size(), "Table of Contents should contain 17 entries");
    }

    @Test
    public void contentMap_containsExpectedKeys() {
        // Verify that all expected keys are present
        String[] expectedKeys = {
            "Introduction", "Features", "Adding a client", "Listing all clients",
            "Editing a client", "Locating clients by attribute", "Deleting a client",
            "Clearing all entries", "Marking a client as paid", "Marking a client as unpaid",
            "Upload a client's profile picture", "Exiting the Program", "Viewing a client's details",
            "Visual Features", "Saving and Editing Data", "FAQ and Known Issues"
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
