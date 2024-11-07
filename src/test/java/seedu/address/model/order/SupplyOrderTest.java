package seedu.address.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.model.product.Ingredient;
import seedu.address.model.product.Product;
import seedu.address.model.util.Remark;
import seedu.address.testutil.PersonBuilder;

public class SupplyOrderTest {
    @Test
    void constructor_validInputs_createsSupplyOrder() {
        Person person = new PersonBuilder().build();
        Ingredient ingredient = new Ingredient(1, "Flour", 10.00);
        List<Product> items = List.of(ingredient);
        Remark remark = new Remark("Test remark");

        SupplyOrder order = new SupplyOrder(person, items, OrderStatus.PENDING, remark);

        assertNotNull(order);
        assertEquals("Supply Order", order.getOrderType());
        assertEquals(person, order.getPerson());
        assertEquals(items, order.getItems());
        assertEquals(OrderStatus.PENDING, order.getStatus());
        assertEquals(remark, order.getRemark());
    }
}