package seedu.address.testutil;


import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.DeliveryList;

public class TypicalDeliveries {
    public static final Delivery APPLES = new DeliveryBuilder().withCost("100").withDate("2024-10-16")
            .withEta("2024-12-31").withUndeclaredId().withItemName("apples").withStatus("not delivered")
            .withTime("00:00:00").build();

    public static final Delivery ORANGES = new DeliveryBuilder().withCost("120").withDate("2024-10-17")
            .withEta("2024-11-23").withUndeclaredId().withItemName("oranges").withStatus("delivering")
            .withTime("00:10:00").build();

    public static final Delivery BANANAS = new DeliveryBuilder().withCost("140").withDate("2024-10-18")
            .withEta("2024-10-20").withUndeclaredId().withItemName("bananas").withStatus("delivered")
            .withTime("10:00:01").build();

    public static final Delivery PEARS = new DeliveryBuilder().withCost("80").withDate("2024-10-19")
            .withEta("2025-01-05").withUndeclaredId().withItemName("pears").withStatus("not delivered")
            .withTime("02:30:20").build();

    public static final DeliveryList DELIVERIES = new DeliveryListBuilder().withNewDelivery(APPLES)
        .withNewDelivery(ORANGES).withNewDelivery(BANANAS).build();

}