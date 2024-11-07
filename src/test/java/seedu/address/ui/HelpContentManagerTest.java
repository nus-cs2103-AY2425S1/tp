//package seedu.address.ui;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertNull;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import javafx.collections.ObservableList;
//
//public class HelpContentManagerTest {
//
//    private HelpContentManager helpContentManager;
//
//    @BeforeEach
//    public void setUp() {
//        helpContentManager = new HelpContentManager();
//    }
//
//    @Test
//    public void initializeContents_contentMapInitialized() {
//        // Check that the contentMap is initialized and contains the expected keys
//        assertNotNull(helpContentManager.getContent("Introduction"));
//        assertNotNull(helpContentManager.getContent("Command Summary"));
//
//        // Verify that all expected keys are present
//        assertEquals(16, helpContentManager.getTableOfContents().size(),
//                "Table of Contents should contain 16 entries");
//    }
//
//    @Test
//    public void getContent_validKey_returnsCorrectContent() {
//        // Check if valid keys return the correct content
//        String introductionContent = helpContentManager.getContent("Introduction");
//        assertNotNull(introductionContent, "Content for 'Introduction' should not be null");
//        assertEquals(true, introductionContent.contains("Financial Assurance Revolutionary Telemarketer"),
//                "Introduction content mismatch");
//    }
//
//    @Test
//    public void getContent_invalidKey_returnsNull() {
//        // Check if an invalid key returns null
//        String nonExistentContent = helpContentManager.getContent("Non Existent Key");
//        assertNull(nonExistentContent, "Content for non-existent key should return null");
//    }
//
//    @Test
//    public void getTableOfContents_initialized_returnsCorrectItems() {
//        // Check that the Table of Contents is initialized and contains the correct items
//        ObservableList<String> tableOfContents = helpContentManager.getTableOfContents();
//        assertNotNull(tableOfContents, "Table of Contents should not be null");
//
//        // Check if specific items exist in the Table of Contents
//        assertEquals("Introduction", tableOfContents.get(0),
//                "First item in the TOC should be 'Introduction'");
//    }
//
//    @Test
//    public void tableOfContents_sizeIsCorrect() {
//        // Test to verify the Table of Contents size only once
//        ObservableList<String> tableOfContents = helpContentManager.getTableOfContents();
//        assertEquals(12, tableOfContents.size(), "Table of Contents should contain 12 entries");
//    }
//
//    @Test
//    public void contentMap_containsExpectedKeys() {
//        // Verify that all expected keys are present
//        String[] expectedKeys = {
//            "Introduction", "Command Format Guidelines", "Adding a Contact",
//            "Listing All Contacts", "Editing a Contact", "Finding Contacts", "Deleting a Contact",
//            "Clearing All Entries", "Marking a Person as Paid/Unpaid", "Saving and Editing Data",
//            "Exiting the Program", "Command Summary"
//        };
//
//        for (String key : expectedKeys) {
//            assertNotNull(helpContentManager.getContent(key), "Content for key '" + key + "' should not be null");
//        }
//    }
//
//    @Test
//    public void emptyContentHandling_nonExistentEntry_returnsNull() {
//        // Check if a non-existent entry returns null
//        String missingContent = helpContentManager.getContent("NonExistentEntry");
//        assertNull(missingContent, "Content for a non-existent entry should be null");
//    }
//}
