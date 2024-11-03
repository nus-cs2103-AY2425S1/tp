package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagCategory;

public class CategorizeTagCommandTest {
    private static final Model model = new ModelManager();
    private static final String TEST_TAG = "test";
    private static final String TEST_TAG_SECOND = "test2";

    // TODO: Add more tests

    @Test
    public void equals() {
        CategorizeTagCommand catTagCommandA = new CategorizeTagCommand(new Tag(TEST_TAG), TagCategory.ACADEMICS);
        CategorizeTagCommand catTagCommandB = new CategorizeTagCommand(new Tag(TEST_TAG_SECOND), TagCategory.ACADEMICS);
        CategorizeTagCommand catTagCommandC = new CategorizeTagCommand(new Tag(TEST_TAG), TagCategory.GENERAL);
        CategorizeTagCommand catTagCommandD = new CategorizeTagCommand(new Tag(TEST_TAG), TagCategory.ACADEMICS);

        assertTrue(catTagCommandA.equals(catTagCommandD));
        assertTrue(catTagCommandA.equals(catTagCommandA));
        assertFalse(catTagCommandA.equals(catTagCommandB));
        assertFalse(catTagCommandA.equals(catTagCommandC));
    }
}
