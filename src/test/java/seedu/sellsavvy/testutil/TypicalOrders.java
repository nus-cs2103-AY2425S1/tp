package seedu.sellsavvy.testutil;

import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.VALID_COUNT_ATLAS;
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.VALID_COUNT_BOTTLE;
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.VALID_DATE_ATLAS;
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.VALID_DATE_BOTTLE;
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.VALID_ITEM_ATLAS;
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.VALID_ITEM_BOTTLE;

import seedu.sellsavvy.model.order.Order;
import seedu.sellsavvy.model.order.Status;

/**
 * A utility class containing a list of {@code Order} objects to be used in tests.
 */
public class TypicalOrders {
    public static final Order ABACUS = new OrderBuilder().withItem("Abacus")
            .withCount("2").withDate("10-10-2025").build();

    public static final Order BLOCKS = new OrderBuilder().withItem("Blocks")
            .withCount("101").withDate("05-05-2025").withStatus(Status.COMPLETED).build();

    public static final Order CAMERA = new OrderBuilder().withItem("Camera with spare lens")
            .withCount("1").withDate("03-03-2020").build();

    public static final Order DAGGER = new OrderBuilder().withItem("Damascus daggers")
            .withCount("5").withDate("12-12-2025").build();

    // Manually added - Order's details found in {@code OrderCommandTestUtil}
    public static final Order ATLAS = new OrderBuilder().withItem(VALID_ITEM_ATLAS)
            .withCount(VALID_COUNT_ATLAS).withDate(VALID_DATE_ATLAS).build();

    public static final Order BOTTLE = new OrderBuilder().withItem(VALID_ITEM_BOTTLE)
            .withCount(VALID_COUNT_BOTTLE).withDate(VALID_DATE_BOTTLE).build();

    private TypicalOrders() {} // prevents instantiation
}
