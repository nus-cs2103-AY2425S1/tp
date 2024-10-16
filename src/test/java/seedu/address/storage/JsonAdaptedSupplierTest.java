package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalSuppliers.AMY;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.product.Product;
import seedu.address.model.supplier.Company;
import seedu.address.model.supplier.SupplierContact;
import seedu.address.model.supplier.SupplierEmail;
import seedu.address.model.supplier.SupplierName;
import seedu.address.model.person.SupplierStatus;


public class JsonAdaptedSupplierTest {
    private static final String INVALID_NAME = " ";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_EMAIL = " ";
    private static final String INVALID_COMPANY = " ";
    private static final String INVALID_PRODUCT = " ";
    private static final String INVALID_STATUS = " ";
    private static final String VALID_NAME = AMY.getName().toString();
    private static final String VALID_PHONE = AMY.getPhone().toString();
    private static final String VALID_EMAIL = AMY.getEmail().toString();
    private static final String VALID_COMPANY = AMY.getCompany().toString();
    private static final String VALID_PRODUCT = AMY.getProduct().toString();
    private static final String VALID_STATUS = AMY.getSupplierStatus().toString();
    @Test
    public void toModelType_validSupplierDetails_returnsSupplier() throws Exception {
        JsonAdaptedSupplier supplier = new JsonAdaptedSupplier(AMY);
        assertEquals(AMY, supplier.toModelType());
    }
    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedSupplier supplier = new JsonAdaptedSupplier(INVALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_COMPANY, VALID_PRODUCT, VALID_STATUS);
        String expectedMessage = SupplierName.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, supplier::toModelType);
    }
    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedSupplier supplier = new JsonAdaptedSupplier(null, VALID_PHONE, VALID_EMAIL,
                VALID_COMPANY, VALID_PRODUCT, VALID_STATUS);
        String expectedMessage = String.format(JsonAdaptedSupplier.MISSING_FIELD_MESSAGE_FORMAT,
                SupplierName.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, supplier::toModelType);
    }
    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedSupplier supplier = new JsonAdaptedSupplier(VALID_NAME, INVALID_PHONE, VALID_EMAIL,
                VALID_COMPANY, VALID_PRODUCT, VALID_STATUS);
        String expectedMessage = SupplierContact.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, supplier::toModelType);
    }
    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedSupplier supplier = new JsonAdaptedSupplier(VALID_NAME, null, VALID_EMAIL,
                VALID_COMPANY, VALID_PRODUCT, VALID_STATUS);
        String expectedMessage = String.format(JsonAdaptedSupplier.MISSING_FIELD_MESSAGE_FORMAT,
                SupplierContact.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, supplier::toModelType);
    }
    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedSupplier supplier = new JsonAdaptedSupplier(VALID_NAME, VALID_PHONE, INVALID_EMAIL,
                VALID_COMPANY, VALID_PRODUCT, VALID_STATUS);
        String expectedMessage = SupplierEmail.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, supplier::toModelType);
    }
    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedSupplier supplier = new JsonAdaptedSupplier(VALID_NAME, VALID_PHONE, null,
                VALID_COMPANY, VALID_PRODUCT, VALID_STATUS);
        String expectedMessage = String.format(JsonAdaptedSupplier.MISSING_FIELD_MESSAGE_FORMAT,
                SupplierEmail.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, supplier::toModelType);
    }
    @Test
    public void toModelType_invalidCompany_throwsIllegalValueException() {
        JsonAdaptedSupplier supplier = new JsonAdaptedSupplier(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                INVALID_COMPANY, VALID_PRODUCT, VALID_STATUS);
        String expectedMessage = Company.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, supplier::toModelType);
    }
    @Test
    public void toModelType_nullCompany_throwsIllegalValueException() {
        JsonAdaptedSupplier supplier = new JsonAdaptedSupplier(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                null, VALID_PRODUCT, VALID_STATUS);
        String expectedMessage = String.format(JsonAdaptedSupplier.MISSING_FIELD_MESSAGE_FORMAT,
                Company.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, supplier::toModelType);
    }
    @Test
    public void toModelType_invalidProduct_throwsIllegalValueException() {
        JsonAdaptedSupplier supplier = new JsonAdaptedSupplier(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_COMPANY, INVALID_PRODUCT, VALID_STATUS);
        String expectedMessage = Product.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, supplier::toModelType);
    }
    @Test
    public void toModelType_nullProduct_throwsIllegalValueException() {
        JsonAdaptedSupplier supplier = new JsonAdaptedSupplier(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_COMPANY, null, VALID_STATUS);
        String expectedMessage = String.format(JsonAdaptedSupplier.MISSING_FIELD_MESSAGE_FORMAT,
                Product.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, supplier::toModelType);
    }
    @Test
    public void toModelType_invalidStatus_throwsIllegalValueException() {
        JsonAdaptedSupplier supplier = new JsonAdaptedSupplier(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_COMPANY, VALID_PRODUCT, INVALID_STATUS);
        String expectedMessage = SupplierStatus.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, supplier::toModelType);
    }
    @Test
    public void toModelType_nullStatus_throwsIllegalValueException() {
        JsonAdaptedSupplier supplier = new JsonAdaptedSupplier(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_COMPANY, VALID_PRODUCT, null);
        String expectedMessage = String.format(JsonAdaptedSupplier.MISSING_FIELD_MESSAGE_FORMAT,
                SupplierStatus.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, supplier::toModelType);
    }
}
