package seedu.address.testutil;


import java.util.Arrays;

import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.ItemName;

/**
 * A utility class containing a list of {@code Delivery} objects to be used in tests.
 */
public class TypicalDeliveries {
    public static final Delivery APPLES = new DeliveryBuilder().withCost("$100").withDate("2024-10-16")
            .withEta("2024-12-31").withUndeclaredId().withItems(Arrays.asList(new ItemName("apples")))
            .withStatus("not delivered").withTime("00:00:00").build();

    public static final Delivery ORANGES = new DeliveryBuilder().withCost("$120").withDate("2024-10-17")
            .withEta("2024-11-23").withUndeclaredId().withItems(Arrays.asList(new ItemName("oranges")))
            .withStatus("delivering").withTime("00:10:00").build();

    public static final Delivery BANANAS = new DeliveryBuilder().withCost("$140").withDate("2024-10-18")
            .withEta("2024-10-20").withUndeclaredId().withItems(Arrays.asList(new ItemName("bananas")))
            .withStatus("delivered").withTime("10:00:01").build();

    public static final Delivery PEARS = new DeliveryBuilder().withCost("$80").withDate("2024-10-19")
            .withEta("2025-01-05").withUndeclaredId().withItems(Arrays.asList(new ItemName("pears")))
            .withStatus("not delivered").withTime("02:30:20").build();

//    public static final DeliveryList DELIVERIES = new DeliveryListBuilder().withNewDelivery(APPLES)
//        .withNewDelivery(ORANGES).withNewDelivery(BANANAS).build();

}
