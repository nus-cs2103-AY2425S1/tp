package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedSupplier.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalSuppliers.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.supplier.Company;
import seedu.address.model.supplier.Email;
import seedu.address.model.supplier.Name;
import seedu.address.model.supplier.Phone;

public class JsonAdaptedSupplierTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_COMPANY = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_PRODUCT = "&rice";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_COMPANY = BENSON.getCompany().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());
    private static final List<JsonAdaptedProduct> VALID_PRODUCTS = BENSON.getProducts().stream()
            .map(JsonAdaptedProduct::new)
            .collect(Collectors.toList());

    private static final String VALID_STATUS = BENSON.getStatus().status;
    @Test
    public void toModelType_validSupplierDetails_returnsSupplier() throws Exception {
        JsonAdaptedSupplier supplier = new JsonAdaptedSupplier(BENSON);
        assertEquals(BENSON, supplier.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedSupplier supplier = new JsonAdaptedSupplier(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_COMPANY,
                VALID_TAGS, VALID_PRODUCTS, VALID_STATUS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, supplier::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedSupplier supplier = new JsonAdaptedSupplier(null, VALID_PHONE, VALID_EMAIL, VALID_COMPANY,
                VALID_TAGS, VALID_PRODUCTS, VALID_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, supplier::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedSupplier supplier = new JsonAdaptedSupplier(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_COMPANY,
                VALID_TAGS, VALID_PRODUCTS, VALID_STATUS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, supplier::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedSupplier supplier = new JsonAdaptedSupplier(VALID_NAME, null, VALID_EMAIL, VALID_COMPANY,
                VALID_TAGS, VALID_PRODUCTS, VALID_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, supplier::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedSupplier supplier = new JsonAdaptedSupplier(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_COMPANY,
                VALID_TAGS, VALID_PRODUCTS, VALID_STATUS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, supplier::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedSupplier supplier = new JsonAdaptedSupplier(VALID_NAME, VALID_PHONE, null, VALID_COMPANY,
                VALID_TAGS, VALID_PRODUCTS, VALID_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, supplier::toModelType);
    }

    @Test
    public void toModelType_invalidCompany_throwsIllegalValueException() {
        JsonAdaptedSupplier supplier = new JsonAdaptedSupplier(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_COMPANY,
                VALID_TAGS, VALID_PRODUCTS, VALID_STATUS);
        String expectedMessage = Company.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, supplier::toModelType);
    }

    @Test
    public void toModelType_nullCompany_throwsIllegalValueException() {
        JsonAdaptedSupplier supplier = new JsonAdaptedSupplier(VALID_NAME, VALID_PHONE, VALID_EMAIL, null,
                VALID_TAGS, VALID_PRODUCTS, VALID_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Company.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, supplier::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedSupplier supplier = new JsonAdaptedSupplier(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_COMPANY,
                invalidTags, VALID_PRODUCTS, VALID_STATUS);
        assertThrows(IllegalValueException.class, supplier::toModelType);
    }

    @Test
    public void toModelType_invalidProducts_throwsIllegalValueException() {
        List<JsonAdaptedProduct> invalidProducts = new ArrayList<>(VALID_PRODUCTS);
        invalidProducts.add(new JsonAdaptedProduct(INVALID_PRODUCT));
        JsonAdaptedSupplier supplier = new JsonAdaptedSupplier(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_COMPANY,
                VALID_TAGS, invalidProducts, VALID_STATUS);
        assertThrows(IllegalValueException.class, supplier::toModelType);
    }
}
