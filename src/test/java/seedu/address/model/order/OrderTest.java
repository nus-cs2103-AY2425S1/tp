package seedu.address.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.model.product.Ingredient;
import seedu.address.model.product.Product;
import seedu.address.model.util.Remark;
import seedu.address.testutil.PersonBuilder;

public class OrderTest {
    private static class TestOrder extends Order {
        public TestOrder(Person person, List<Product> items, OrderStatus status, Remark remark) {
            super(person, items, status, remark);
        }

        @Override
        public String getOrderType() {
            return "Test Order";
        }
    }

    @Test
    void constructor_nullPerson_throwsNullPointerException() {
        List<Product> items = new ArrayList<>();
        assertThrows(NullPointerException.class, () -> new TestOrder(null, items,
                OrderStatus.PENDING, new Remark("Test")));
    }

    @Test
    void constructor_nullItems_throwsNullPointerException() {
        Person person = new PersonBuilder().build();
        assertThrows(NullPointerException.class, () -> new TestOrder(person, null,
                OrderStatus.PENDING, new Remark("Test")));
    }

    @Test
    void constructor_validInputs_createsOrder() {
        Person person = new PersonBuilder().build();
        Ingredient ingredient = new Ingredient(1, "Flour", 10.00);
        List<Product> items = List.of(ingredient);
        Remark remark = new Remark("Test remark");

        TestOrder order = new TestOrder(person, items, OrderStatus.PENDING, remark);

        assertNotNull(order);
        assertEquals(person, order.getPerson());
        assertEquals(items, order.getItems());
        assertEquals(OrderStatus.PENDING, order.getStatus());
        assertEquals(remark, order.getRemark());
        assertEquals(person, order.getOriginalPerson());
    }

    @Test
    void getOrderDate_returnsCorrectFormat() {
        Person person = new PersonBuilder().build();
        Ingredient ingredient = new Ingredient(1, "Flour", 10.00);
        List<Product> items = List.of(ingredient);
        TestOrder order = new TestOrder(person, items, OrderStatus.PENDING, new Remark("Test"));

        String expectedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        assertEquals(expectedDate, order.getOrderDate());
    }

    @Test
    void setStatus_validStatus_updatesStatus() {
        Person person = new PersonBuilder().build();
        Ingredient ingredient = new Ingredient(1, "Sugar", 5.00);
        List<Product> items = List.of(ingredient);
        TestOrder order = new TestOrder(person, items, OrderStatus.PENDING, new Remark("Test"));

        order.setStatus(OrderStatus.COMPLETED);
        assertEquals(OrderStatus.COMPLETED, order.getStatus());
    }

    @Test
    void setOriginalPerson_validPerson_updatesOriginalPerson() {
        Person person = new PersonBuilder().build();
        Ingredient ingredient = new Ingredient(1, "Sugar", 5.00);
        List<Product> items = List.of(ingredient);
        TestOrder order = new TestOrder(person, items, OrderStatus.PENDING, new Remark("Test"));

        Person newPerson = new PersonBuilder().withName("New Person").build();
        order.setOriginalPerson(newPerson);
        assertEquals(newPerson, order.getOriginalPerson());
    }

    @Test
    void toString_returnsFormattedString() {
        Person person = new PersonBuilder().build();
        Ingredient ingredient = new Ingredient(1, "Flour", 10.00);
        List<Product> items = List.of(ingredient);
        TestOrder order = new TestOrder(person, items, OrderStatus.PENDING, new Remark("Test remark"));

        String expectedString = "Order Type: Test Order\n"
                + "Order Date: " + order.getOrderDate() + "\n"
                + "Status: Pending\n"
                + "Remark: Test remark\n"
                + "Items: \n"
                + ingredient.viewProduct() + "\n";

        assertEquals(expectedString, order.toString());
    }

    @Test
    void viewOrder_returnsFormattedItemsList() {
        Person person = new PersonBuilder().build();
        Ingredient ingredient1 = new Ingredient(1, "Flour", 10.00);
        Ingredient ingredient2 = new Ingredient(2, "Sugar", 5.00);
        List<Product> items = List.of(ingredient1, ingredient2);

        TestOrder order = new TestOrder(person, items, OrderStatus.PENDING, new Remark("Test"));

        String expectedString = ingredient1.viewProduct() + "\n" + ingredient2.viewProduct() + "\n";
        assertEquals(expectedString, order.viewOrder());
    }
}
