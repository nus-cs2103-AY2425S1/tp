package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.product.Product;
import seedu.address.model.product.ProductName;
import seedu.address.model.tag.Tag;

public class JsonAdaptedProductTest {

    private static final String VALID_NAME = "Apple";
    private static final String VALID_SUPPLIER_NAME = "SupplierA";
    private static final int VALID_STOCK_LEVEL = 50;
    private static final int VALID_MIN_STOCK_LEVEL = 10;
    private static final int VALID_MAX_STOCK_LEVEL = 100;
    private static final List<JsonAdaptedTag> VALID_TAGS = List.of(new JsonAdaptedTag("fruit"),
            new JsonAdaptedTag("organic"));
    private static final Set<Tag> EXPECTED_TAGS = Set.of(new Tag("fruit"), new Tag("organic"));
    private static final String INVALID_NAME = "R@chel";
    private Product expectedProduct;

    @BeforeEach
    public void setUp() {
        expectedProduct = new Product(new ProductName(VALID_NAME), EXPECTED_TAGS);
        expectedProduct.setSupplierName(new seedu.address.model.supplier.Name(VALID_SUPPLIER_NAME));
        expectedProduct.setStockLevel(VALID_STOCK_LEVEL);
        expectedProduct.setMinStockLevel(VALID_MIN_STOCK_LEVEL);
        expectedProduct.setMaxStockLevel(VALID_MAX_STOCK_LEVEL);
    }

    @Test
    public void toModelType_validProductDetails_returnsProduct() throws Exception {
        JsonAdaptedProduct jsonProduct = new JsonAdaptedProduct(VALID_NAME, VALID_SUPPLIER_NAME, VALID_STOCK_LEVEL,
                VALID_MIN_STOCK_LEVEL, VALID_MAX_STOCK_LEVEL, VALID_TAGS);
        assertEquals(expectedProduct, jsonProduct.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedProduct product = new JsonAdaptedProduct(INVALID_NAME, VALID_SUPPLIER_NAME, VALID_STOCK_LEVEL,
                VALID_MIN_STOCK_LEVEL, VALID_MAX_STOCK_LEVEL, VALID_TAGS);
        assertThrows(IllegalValueException.class, product::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedProduct product = new JsonAdaptedProduct(null, VALID_SUPPLIER_NAME, VALID_STOCK_LEVEL,
                VALID_MIN_STOCK_LEVEL, VALID_MAX_STOCK_LEVEL, VALID_TAGS);
        assertThrows(IllegalValueException.class, product::toModelType);
    }

    @Test
    public void constructor_product_createsJsonAdaptedProduct() throws Exception {
        JsonAdaptedProduct jsonAdaptedProduct = new JsonAdaptedProduct(expectedProduct);
        assertEquals(expectedProduct, jsonAdaptedProduct.toModelType());
    }
}
