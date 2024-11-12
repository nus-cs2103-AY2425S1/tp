package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.product.Ingredient;
import seedu.address.model.product.Ingredients;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.Remark;

public class SupplierTest {

    private Supplier supplier;
    private Supplier sameSupplier;
    private Supplier differentSupplier;
    private Set<Tag> tags;

    @BeforeEach
    public void setUp() {
        tags = new HashSet<>();
        tags.add(new Tag("wholesale"));

        // Ingredients supplied by the supplier
        Ingredients ingredients = new Ingredients(List.of(
                new Ingredient(1, "Flour", 1.50),
                new Ingredient(2, "Sugar", 0.80),
                new Ingredient(3, "Butter", 2.00)
        ));

        supplier = new Supplier(
                new Name("Supplier ABC"),
                new Phone("91234567"),
                new Email("supplierabc@example.com"),
                new Address("123 Clementi Rd, #04-01"),
                ingredients,
                new Remark("Reliable supplier"),
                tags
        );

        // Create a supplier with the same details to check for equality
        sameSupplier = new Supplier(
                new Name("Supplier ABC"),
                new Phone("91234567"),
                new Email("supplierabc@example.com"),
                new Address("123 Clementi Rd, #04-01"),
                ingredients,
                new Remark("Reliable supplier"),
                tags
        );

        // Create a supplier with different details
        Ingredients differentIngredients = new Ingredients(List.of(
                new Ingredient(4, "Milk", 1.20),
                new Ingredient(5, "Cheese", 3.00)
        ));

        differentSupplier = new Supplier(
                new Name("Supplier XYZ"),
                new Phone("92345678"),
                new Email("supplierxyz@example.com"),
                new Address("456 Orchard Rd"),
                differentIngredients,
                new Remark("Occasional supplier"),
                new HashSet<>(Set.of(new Tag("seasonal")))
        );
    }

    @Test
    public void testEquals() {
        // Same object -> returns true
        assertEquals(supplier, supplier);

        // Different object, same values -> returns true
        assertEquals(supplier, sameSupplier);

        // Different values -> returns false
        assertNotEquals(supplier, differentSupplier);

        // Null -> returns false
        assertNotEquals(supplier, null);

        // Different type -> returns false
        assertNotEquals(supplier, new Object());
    }

    @Test
    public void testHashCode() {
        // Same object, same hash code
        assertEquals(supplier.hashCode(), sameSupplier.hashCode());

        // Different values, different hash code
        assertNotEquals(supplier.hashCode(), differentSupplier.hashCode());
    }

    @Test
    public void testToString() {
        String expectedString = "Name: Supplier ABC Phone: 91234567 Email: supplierabc@example.com "
                + "Address: 123 Clementi Rd, #04-01 Tags: [wholesale] Remark: Reliable supplier "
                + "Ingredients Supplied: Flour, Sugar, Butter";
        assertEquals(expectedString, supplier.toString());
    }

    @Test
    public void getIngredientsSupplied_returnsCorrectIngredients() {
        List<String> expectedIngredients = List.of("Flour", "Sugar", "Butter");
        assertEquals(expectedIngredients, supplier.getIngredientsSupplied().getIngredientNames());
    }
}