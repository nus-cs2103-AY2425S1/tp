package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.product.Product;
import seedu.address.model.supplier.Supplier;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_SUPPLIER = "Suppliers list contains duplicate supplier(s).";
    public static final String MESSAGE_DUPLICATE_PRODUCT = "Products list contains duplicate product(s).";

    private final List<JsonAdaptedSupplier> suppliers = new ArrayList<>();
    private final List<JsonAdaptedProduct> products = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given suppliers.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("suppliers") List<JsonAdaptedSupplier> suppliers,
                                       @JsonProperty("products") List<JsonAdaptedProduct> products) {
        if (suppliers != null) {
            this.suppliers.addAll(suppliers);
        }
        if (products != null) {
            this.products.addAll(products);
        }
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        suppliers.addAll(source.getSupplierList().stream().map(JsonAdaptedSupplier::new).collect(Collectors.toList()));
        products.addAll(source.getProductList().stream().map(JsonAdaptedProduct::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (JsonAdaptedSupplier jsonAdaptedSupplier : suppliers) {
            Supplier supplier = jsonAdaptedSupplier.toModelType();
            if (addressBook.hasSupplier(supplier)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_SUPPLIER);
            }
            addressBook.addSupplier(supplier);
        }
        for (JsonAdaptedProduct jsonAdaptedProduct : products) {
            Product product = jsonAdaptedProduct.toModelType();
            if (addressBook.hasProduct(product)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PRODUCT);
            }
            addressBook.addProduct(product);
        }
        return addressBook;
    }

}
