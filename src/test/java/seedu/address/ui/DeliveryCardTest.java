package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import seedu.address.model.delivery.Delivery;
import seedu.address.testutil.TypicalDeliveries;

public class DeliveryCardTest extends ApplicationTest {

    private DeliveryCard deliveryCard;
    private Delivery typicalDelivery;

    @BeforeEach
    public void setUp() {
        typicalDelivery = TypicalDeliveries.BREAD; // Use a typical delivery from the utility class
        deliveryCard = new DeliveryCard(typicalDelivery, 1);
    }

    @Test
    public void display_correctlyDisplaysDeliveryDetails() {

        assertEquals(typicalDelivery.getDeliveryProduct().toString(), deliveryCard.getProductName());
        assertEquals(typicalDelivery.getDeliverySender().getName().fullName, deliveryCard.getSenderName());
        assertEquals(typicalDelivery.getDeliveryStatus().name(), deliveryCard.getStatus());
        assertEquals(typicalDelivery.getDeliveryDate().toString(), deliveryCard.getDeliveryTime());
        assertEquals(typicalDelivery.getDeliveryCost().toString(), deliveryCard.getCost());
        assertEquals(typicalDelivery.getDeliveryQuantity().toString(), deliveryCard.getQuantity());
    }


    @Test
    public void display_multipleDeliveries_correctlyDisplays() {

        typicalDelivery = TypicalDeliveries.CAN;
        deliveryCard = new DeliveryCard(typicalDelivery, 2);

        assertEquals(typicalDelivery.getDeliveryProduct().toString(), deliveryCard.getProductName());
        assertEquals(typicalDelivery.getDeliverySender().getName().fullName, deliveryCard.getSenderName());
        assertEquals(typicalDelivery.getDeliveryStatus().name(), deliveryCard.getStatus());
        assertEquals(typicalDelivery.getDeliveryDate().toString(), deliveryCard.getDeliveryTime());
        assertEquals(typicalDelivery.getDeliveryCost().toString(), deliveryCard.getCost());
        assertEquals(typicalDelivery.getDeliveryQuantity().toString(), deliveryCard.getQuantity());
    }
}



