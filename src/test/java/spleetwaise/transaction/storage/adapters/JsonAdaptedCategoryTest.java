package spleetwaise.transaction.storage.adapters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import spleetwaise.address.commons.exceptions.IllegalValueException;
import spleetwaise.transaction.model.transaction.Category;

public class JsonAdaptedCategoryTest {

    private static final String VALID_CATEGORY = "FOOD";
    private static final String INVALID_CATEGORY1 = " ";
    private static final String INVALID_CATEGORY2 = " FOOD";

    @Test
    public void testConstructor_string_category() {
        // Test creating an instance with a valid string category
        assertEquals(VALID_CATEGORY, (new JsonAdaptedCategory(VALID_CATEGORY)).getCategory());
    }

    @Test
    public void testConstructor_string_invalidCategory() {
        // Test creating an instance with a valid string amount
        assertThrows(IllegalValueException.class, () -> (new JsonAdaptedCategory(INVALID_CATEGORY1)).toModelType());
        assertThrows(IllegalValueException.class, () -> (new JsonAdaptedCategory(INVALID_CATEGORY2)).toModelType());
    }

    @Test
    public void testConstructor_category() {
        // Create Category object
        Category category = new Category(VALID_CATEGORY);
        // Create a JsonAdaptedCategory instance from the category
        assertEquals(VALID_CATEGORY, (new JsonAdaptedCategory(category)).getCategory());
    }

    @Test
    public void testToModelType() throws IllegalValueException {
        // Test converting a valid string amount to an Amount object
        Category modelCategory = (new JsonAdaptedCategory(VALID_CATEGORY)).toModelType();
        assertEquals(VALID_CATEGORY, modelCategory.toString());
    }


}
