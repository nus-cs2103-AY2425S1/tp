package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.product.Product;
import seedu.address.model.supplier.Company;
import seedu.address.model.supplier.Email;
import seedu.address.model.supplier.Name;
import seedu.address.model.supplier.Phone;
import seedu.address.model.supplier.Supplier;
import seedu.address.model.supplier.SupplierStatus;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Supplier}.
 */
class JsonAdaptedSupplier {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Supplier's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String company;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final List<JsonAdaptedProduct> products = new ArrayList<>();
    private final String status;

    /**
     * Constructs a {@code JsonAdaptedSupplier} with the given supplier details.
     */
    @JsonCreator
    public JsonAdaptedSupplier(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                               @JsonProperty("email") String email, @JsonProperty("company") String company,
                               @JsonProperty("tags") List<JsonAdaptedTag> tags,
                               @JsonProperty("products") List<JsonAdaptedProduct> products,
                               @JsonProperty("status") String status) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.company = company;
        this.status = status;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        if (products != null) {
            this.products.addAll(products);
        }
    }

    /**
     * Converts a given {@code Supplier} into this class for Jackson use.
     */
    public JsonAdaptedSupplier(Supplier source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        company = source.getCompany().value;
        status = source.getStatus().status;
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        products.addAll(source.getProducts().stream()
                .map(JsonAdaptedProduct::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted supplier object into the model's {@code Supplier} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted supplier.
     */
    public Supplier toModelType() throws IllegalValueException {
        final List<Tag> supplierTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            supplierTags.add(tag.toModelType());
        }
        final List<Product> supplierProducts = new ArrayList<>();
        for (JsonAdaptedProduct product : products) {
            supplierProducts.add(product.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (company == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Company.class.getSimpleName()));
        }
        if (!Company.isValidCompany(company)) {
            throw new IllegalValueException(Company.MESSAGE_CONSTRAINTS);
        }
        final Company modelCompany = new Company(company);

        final Set<Tag> modelTags = new HashSet<>(supplierTags);
        final Set<Product> modelProducts = new HashSet<>(supplierProducts);

        if (status == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    SupplierStatus.class.getSimpleName()));
        }
        if (!SupplierStatus.isValidStatus(status)) {
            throw new IllegalValueException(SupplierStatus.MESSAGE_CONSTRAINTS);
        }
        final SupplierStatus modelStatus = new SupplierStatus(status);
        return new Supplier(modelName, modelPhone, modelEmail, modelCompany, modelTags, modelProducts, modelStatus);
    }

}
