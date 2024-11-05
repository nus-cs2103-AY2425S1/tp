package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagCategory;

public class CategorizeTagCommandTest {
    private static final String TEST_TAG_1 = "test";
    private static final String TEST_TAG_2 = "test2";

    @Test
    public void equals() {
        CategorizeTagCommand commandA = new CategorizeTagCommand(List.of(new Tag(TEST_TAG_1)), TagCategory.ACADEMICS);
        CategorizeTagCommand commandB = new CategorizeTagCommand(List.of(new Tag(TEST_TAG_2)), TagCategory.ACADEMICS);
        CategorizeTagCommand commandC = new CategorizeTagCommand(List.of(new Tag(TEST_TAG_1)), TagCategory.GENERAL);
        CategorizeTagCommand commandD = new CategorizeTagCommand(List.of(new Tag(TEST_TAG_1)), TagCategory.ACADEMICS);
        CategorizeTagCommand commandE = new CategorizeTagCommand(
                Arrays.asList(new Tag(TEST_TAG_1), new Tag(TEST_TAG_2)), TagCategory.ACADEMICS);

        // Same object -> returns true
        assertTrue(commandA.equals(commandA));

        // Same tags and category -> returns true
        assertTrue(commandA.equals(commandD));

        // Different tags -> returns false
        assertFalse(commandA.equals(commandB));

        // Different categories -> returns false
        assertFalse(commandA.equals(commandC));

        // Different number of tags -> returns false
        assertFalse(commandA.equals(commandE));

        // Different object type -> returns false
        assertFalse(commandA.equals(new Object()));
    }
}
