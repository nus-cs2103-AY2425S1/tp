package seedu.address.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.model.product.Ingredient;
import seedu.address.model.product.Pastry;
import seedu.address.model.product.Product;
import seedu.address.model.util.Remark;
import seedu.address.testutil.PersonBuilder;

public class CustomerOrderTest {
    @Test
    void constructor_validInputs_createsCustomerOrder() {
        Person person = new PersonBuilder().build();
        Ingredient ingredient = new Ingredient(1, "Flour", 10.00);
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(ingredient);
        Product pastry = new Pastry(1, "Bread", 2, ingredients);
        List<Product> items = List.of(pastry);
        Remark remark = new Remark("Test remark");

        CustomerOrder order = new CustomerOrder(person, items, OrderStatus.PENDING, remark);

        assertNotNull(order);
        assertEquals("Customer Order", order.getOrderType());
        assertEquals(person, order.getPerson());
        assertEquals(items, order.getItems());
        assertEquals(OrderStatus.PENDING, order.getStatus());
        assertEquals(remark, order.getRemark());
    }
}