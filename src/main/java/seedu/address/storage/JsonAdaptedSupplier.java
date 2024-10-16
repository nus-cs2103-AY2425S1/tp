package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.product.Product;
import seedu.address.model.supplier.Company;
import seedu.address.model.supplier.Supplier;
import seedu.address.model.supplier.SupplierContact;
import seedu.address.model.supplier.SupplierEmail;
import seedu.address.model.supplier.SupplierName;
import seedu.address.model.person.SupplierStatus;

/**
 * Jackson-friendly version of {@link Supplier}.
 */
public class JsonAdaptedSupplier {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Supplier's %s field is missing!";
    private final String supplierName;
    private final String supplierPhone;
    private final String supplierEmail;
    private final String supplierCompany;
    private final String product;
    private final String status;

    /**
     * Constructs a {@code JsonAdaptedSupplier} with the given supplier details.
     */
    @JsonCreator
    public JsonAdaptedSupplier(@JsonProperty("supplierName") String supplierName,
                               @JsonProperty("supplierPhone") String supplierPhone,
                               @JsonProperty("supplierEmail") String supplierEmail,
                               @JsonProperty("supplierAddress") String supplierCompany,
                               @JsonProperty("product") String product,
                               @JsonProperty("status") String status) {
        this.supplierName = supplierName;
        this.supplierPhone = supplierPhone;
        this.supplierEmail = supplierEmail;
        this.supplierCompany = supplierCompany;
        this.product = product;
        this.status = status;
    }

    /**
     * Converts a given {@code Supplier} into this class for Jackson use.
     */
    public JsonAdaptedSupplier(Supplier source) {
        this.supplierName = source.getName().supplierName;
        this.supplierPhone = source.getPhone().value;
        this.supplierEmail = source.getEmail().value;
        this.supplierCompany = source.getCompany().value;
        this.product = source.getProduct().productName;
        this.status = source.getSupplierStatus().status;
    }

    /**
     * Converts this Jackson-friendly adapted supplier object into the model's {@code Supplier} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted supplier.
     */
    public Supplier toModelType() throws IllegalValueException {
        if (supplierName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    SupplierName.class.getSimpleName()));
        }
        if (!SupplierName.isValidName(supplierName)) {
            throw new IllegalValueException(SupplierName.MESSAGE_CONSTRAINTS);
        }
        final SupplierName modelName = new SupplierName(supplierName);

        if (supplierPhone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    SupplierContact.class.getSimpleName()));
        }
        if (!SupplierContact.isValidPhone(supplierPhone)) {
            throw new IllegalValueException(SupplierContact.MESSAGE_CONSTRAINTS);
        }
        final SupplierContact modelPhone = new SupplierContact(supplierPhone);

        if (supplierEmail == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    SupplierEmail.class.getSimpleName()));
        }
        if (!SupplierEmail.isValidEmail(supplierEmail)) {
            throw new IllegalValueException(SupplierEmail.MESSAGE_CONSTRAINTS);
        }
        final SupplierEmail modelEmail = new SupplierEmail(supplierEmail);

        if (supplierCompany == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    SupplierEmail.class.getSimpleName()));
        }
        if (!Company.isValidCompany(supplierCompany)) {
            throw new IllegalValueException(Company.MESSAGE_CONSTRAINTS);
        }
        final Company modelCompany = new Company(supplierCompany);

        if (product == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Product.class.getSimpleName()));
        }
        if (!Product.isValidProductName(product)) {
            throw new IllegalValueException(Product.MESSAGE_CONSTRAINTS);
        }
        final Product modelProduct = new Product(product);

        if (status == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    SupplierStatus.class.getSimpleName()));
        }
        if (!SupplierStatus.isValidStatus(status)) {
            throw new IllegalValueException(SupplierStatus.MESSAGE_CONSTRAINTS);
        }
        final SupplierStatus modelStatus = new SupplierStatus(status);

        return new Supplier(modelName, modelPhone, modelEmail, modelCompany, modelProduct, modelStatus);
    }
}
