package seedu.address.testutil;

import static seedu.address.logic.commands.OrderCommandTestUtil.VALID_COUNT_ATLAS;
import static seedu.address.logic.commands.OrderCommandTestUtil.VALID_COUNT_BOTTLE;
import static seedu.address.logic.commands.OrderCommandTestUtil.VALID_DATE_ATLAS;
import static seedu.address.logic.commands.OrderCommandTestUtil.VALID_DATE_BOTTLE;
import static seedu.address.logic.commands.OrderCommandTestUtil.VALID_ITEM_ATLAS;
import static seedu.address.logic.commands.OrderCommandTestUtil.VALID_ITEM_BOTTLE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.order.Order;

/**
 * A utility class containing a list of {@code Order} objects to be used in tests.
 */
public class TypicalOrders {
    public static final Order ABACUS = new OrderBuilder().withItem("Abacus")
            .withCount("2").withDate("10-10-2025").build();

    public static final Order BLOCKS = new OrderBuilder().withItem("Blocks")
            .withCount("101").withDate("05-05-2025").build();

    public static final Order CAMERA = new OrderBuilder().withItem("Camera with spare lens")
            .withCount("1").withDate("03-03-2025").build();

    public static final Order DAGGER = new OrderBuilder().withItem("Damascus daggers")
            .withCount("5").withDate("12-12-2025").build();

    // Manually added - Order's details found in {@code OrderCommandTestUtil}
    public static final Order ATLAS = new OrderBuilder().withItem(VALID_ITEM_ATLAS)
            .withCount(VALID_COUNT_ATLAS).withDate(VALID_DATE_ATLAS).build();

    public static final Order BOTTLE = new OrderBuilder().withItem(VALID_ITEM_BOTTLE)
            .withCount(VALID_COUNT_BOTTLE).withDate(VALID_DATE_BOTTLE).build();

    private TypicalOrders() {} // prevents instantiation

    public static List<Order> getTypicalOrders() {
        return new ArrayList<>(Arrays.asList(ABACUS, BLOCKS, CAMERA, DAGGER));
    }
}
