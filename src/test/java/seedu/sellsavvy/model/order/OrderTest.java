package seedu.sellsavvy.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.sellsavvy.logic.commands.customercommands.CustomerCommandTestUtil.VALID_NAME_BOB;
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.VALID_DATE_ATLAS;
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.VALID_DATE_BOTTLE;
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.VALID_DATE_OUTDATED;
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.VALID_ITEM_BOTTLE;
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.VALID_QUANTITY_BOTTLE;
import static seedu.sellsavvy.testutil.Assert.assertThrows;
import static seedu.sellsavvy.testutil.TypicalOrders.ATLAS;
import static seedu.sellsavvy.testutil.TypicalOrders.BOTTLE;

import org.junit.jupiter.api.Test;

import seedu.sellsavvy.testutil.OrderBuilder;

public class OrderTest {

    @Test
    public void isSameOrder() {
        // same object -> returns true
        assertTrue(ATLAS.isSameOrder(ATLAS));

        // null -> returns false
        assertFalse(ATLAS.isSameOrder(null));

        // same item, all other attributes different -> returns true
        Order editedAtlas = new OrderBuilder(ATLAS).withQuantity(VALID_QUANTITY_BOTTLE)
                .withDate(VALID_DATE_BOTTLE).build();
        assertTrue(ATLAS.isSameOrder(editedAtlas));

        // different item, all other attributes same -> returns false
        editedAtlas = new OrderBuilder(ATLAS).withItem(VALID_ITEM_BOTTLE).build();
        assertFalse(ATLAS.isSameOrder(editedAtlas));

        // item description differs in case, all other attributes same -> returns false
        Order editedBottle = new OrderBuilder(BOTTLE).withItem(VALID_ITEM_BOTTLE.toLowerCase()).build();
        assertFalse(BOTTLE.isSameOrder(editedBottle));

        // item description has trailing spaces, all other attributes same -> returns false
        String itemWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBottle = new OrderBuilder(BOTTLE).withItem(itemWithTrailingSpaces).build();
        assertFalse(BOTTLE.isSameOrder(editedBottle));
    }

    @Test
    public void hasDateElapsed() {
        // order date has elapsed
        Order editedAtlas = new OrderBuilder(ATLAS).withDate(VALID_DATE_OUTDATED).build();
        assertTrue(editedAtlas.hasDateElapsed());

        // order date has not elapsed
        editedAtlas = new OrderBuilder(ATLAS).withDate(VALID_DATE_ATLAS).build();
        assertFalse(editedAtlas.hasDateElapsed());
    }

    @Test
    public void equals() {
        // same values -> returns true
        Order atlasCopy = new OrderBuilder(ATLAS).build();
        assertTrue(ATLAS.equals(atlasCopy));

        // same object -> returns true
        assertTrue(ATLAS.equals(ATLAS));

        // null -> returns false
        assertFalse(ATLAS.equals(null));

        // different type -> returns false
        assertFalse(ATLAS.equals(5));

        // different order -> returns false
        assertFalse(ATLAS.equals(BOTTLE));

        // different item -> returns false
        Order editedAtlas = new OrderBuilder(ATLAS).withItem(VALID_ITEM_BOTTLE).build();
        assertFalse(ATLAS.equals(editedAtlas));

        // different quantity -> returns false
        editedAtlas = new OrderBuilder(ATLAS).withQuantity(VALID_QUANTITY_BOTTLE).build();
        assertFalse(ATLAS.equals(editedAtlas));

        // different date -> returns false
        editedAtlas = new OrderBuilder(ATLAS).withDate(VALID_DATE_BOTTLE).build();
        assertFalse(ATLAS.equals(editedAtlas));

        // different status -> returns false
        editedAtlas = new OrderBuilder(ATLAS).withStatus(Status.COMPLETED).build();
        assertFalse(ATLAS.equals(editedAtlas));
    }

    @Test
    public void isSimilarTo() {
        // same values -> returns true
        Order atlasCopy = new OrderBuilder(ATLAS).build();
        assertTrue(ATLAS.isSimilarTo(atlasCopy));

        // same object -> returns true
        assertTrue(ATLAS.isSimilarTo(ATLAS));

        // null -> throws exception
        assertThrows(NullPointerException.class, () ->ATLAS.isSimilarTo(null));

        // different order -> returns false
        assertFalse(ATLAS.isSimilarTo(BOTTLE));

        // different item -> returns false
        Order editedAtlas = new OrderBuilder(ATLAS).withItem(VALID_ITEM_BOTTLE).build();
        assertFalse(ATLAS.isSimilarTo(editedAtlas));

        // different quantity -> returns false
        editedAtlas = new OrderBuilder(ATLAS).withQuantity(VALID_QUANTITY_BOTTLE).build();
        assertFalse(ATLAS.isSimilarTo(editedAtlas));

        // different date -> returns false
        editedAtlas = new OrderBuilder(ATLAS).withDate(VALID_DATE_BOTTLE).build();
        assertFalse(ATLAS.isSimilarTo(editedAtlas));

        // different status -> returns false
        editedAtlas = new OrderBuilder(ATLAS).withStatus(Status.COMPLETED).build();
        assertFalse(ATLAS.isSimilarTo(editedAtlas));

        // Similar item -> returns true
        // Original item : "Atlas A"
        // different casing
        editedAtlas = new OrderBuilder(ATLAS).withItem("atlas a").build();
        assertTrue(ATLAS.isSimilarTo(editedAtlas));
        assertTrue(editedAtlas.isSimilarTo(ATLAS));

        //different spacing
        editedAtlas = new OrderBuilder(ATLAS).withItem("Atlas A").build();
        assertTrue(ATLAS.isSimilarTo(editedAtlas));
        assertTrue(editedAtlas.isSimilarTo(ATLAS));

        //different casing and spacing
        editedAtlas = new OrderBuilder(ATLAS).withItem("a TlAsA").build();
        assertTrue(ATLAS.isSimilarTo(editedAtlas));
        assertTrue(editedAtlas.isSimilarTo(ATLAS));
    }

    @Test
    public void toStringMethod() {
        String expected = Order.class.getCanonicalName()
                + "{item=" + ATLAS.getItem() + ", quantity=" + ATLAS.getQuantity()
                + ", date=" + ATLAS.getDate() + ", status=" + ATLAS.getStatus() + "}";
        assertEquals(expected, ATLAS.toString());
    }

    @Test
    public void createCopy() {
        Order atlasCopy = ATLAS.createCopy();
        assertEquals(ATLAS, atlasCopy);
        assertNotSame(ATLAS, atlasCopy);
    }
}
