package seedu.sellsavvy.logic.commands.ordercommands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.DESC_ATLAS;
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.DESC_BOTTLE;
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.VALID_COUNT_BOTTLE;
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.VALID_DATE_BOTTLE;
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.VALID_ITEM_BOTTLE;

import org.junit.jupiter.api.Test;

import seedu.sellsavvy.logic.commands.ordercommands.EditOrderCommand.EditOrderDescriptor;
import seedu.sellsavvy.testutil.EditOrderDescriptorBuilder;

public class EditOrderDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditOrderDescriptor descriptorWithSameValues = new EditOrderDescriptor(DESC_ATLAS);
        assertTrue(DESC_ATLAS.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_ATLAS.equals(DESC_ATLAS));

        // null -> returns false
        assertFalse(DESC_ATLAS.equals(null));

        // different types -> returns false
        assertFalse(DESC_ATLAS.equals(5));

        // different values -> returns false
        assertFalse(DESC_ATLAS.equals(DESC_BOTTLE));

        // different name -> returns false
        EditOrderDescriptor editedAtlas = new EditOrderDescriptorBuilder(DESC_ATLAS)
                .withItem(VALID_ITEM_BOTTLE).build();
        assertFalse(DESC_ATLAS.equals(editedAtlas));

        // different quantity -> returns false
        editedAtlas = new EditOrderDescriptorBuilder(DESC_ATLAS).withQuantity(VALID_COUNT_BOTTLE).build();
        assertFalse(DESC_ATLAS.equals(editedAtlas));

        // different date -> returns false
        editedAtlas = new EditOrderDescriptorBuilder(DESC_ATLAS).withDate(VALID_DATE_BOTTLE).build();
        assertFalse(DESC_ATLAS.equals(editedAtlas));

    }

    @Test
    public void toStringMethod() {
        EditOrderDescriptor editOrderDescriptor = new EditOrderDescriptor();
        String expected = EditOrderDescriptor.class.getCanonicalName() + "{item="
                + editOrderDescriptor.getItem().orElse(null) + ", quantity="
                + editOrderDescriptor.getQuantity().orElse(null) + ", date="
                + editOrderDescriptor.getDate().orElse(null) + "}";
        assertEquals(expected, editOrderDescriptor.toString());
    }
}
