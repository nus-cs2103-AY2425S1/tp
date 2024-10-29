package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import seedu.address.model.tag.Tag;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.product.Product;
import seedu.address.model.product.ProductName;
import seedu.address.model.product.StockLevel;
import seedu.address.model.supplier.Name;

/**
 * Jackson-friendly version of {@link Product}.
 */
class JsonAdaptedProduct {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Product's %s field is missing!";

    private final String name;
    private final String supplierName; // This is empty if the product has no supplier
    private final int curStock;
    private final int minStockLevel;
    private final int maxStockLevel;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedProduct} with the given product details.
     */
    @JsonCreator
    public JsonAdaptedProduct(
            @JsonProperty("name") String name,
            @JsonProperty("supplierName") String supplierName,
            @JsonProperty("curStock") int curStock,
            @JsonProperty("minStockLevel") int minStockLevel,
            @JsonProperty("maxStockLevel") int maxStockLevel,
            @JsonProperty("tags") List<JsonAdaptedTag> tags) {
        this.name = name;
        this.supplierName = supplierName;
        this.curStock = curStock;
        this.minStockLevel = minStockLevel;
        this.maxStockLevel = maxStockLevel;
        if (tags != null) {
            this.tags.addAll(tags);
        }
    }

    /**
     * Converts a given {@code Product} into this class for Jackson use.
     */
    public JsonAdaptedProduct(Product source) {
        name = source.getName().fullName;
        supplierName = source.getSupplierName() != null ? source.getSupplierName().fullName : "";
        StockLevel stock = source.getStockLevel();
        curStock = stock.getStockLevel();
        minStockLevel = stock.getMinStockLevel();
        maxStockLevel = stock.getMaxStockLevel();
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(java.util.stream.Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted product object into the model's {@code Product} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted product.
     */
    public Product toModelType() throws IllegalValueException {
        // Validate product name
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ProductName.class.getSimpleName()));
        }
        if (!ProductName.isValidName(name)) {
            throw new IllegalValueException(ProductName.MESSAGE_CONSTRAINTS);
        }
        final ProductName modelName = new ProductName(name);

        // Validate stock levels
        StockLevel stockLevel;
        try {
            stockLevel = new StockLevel(curStock, minStockLevel, maxStockLevel);
        } catch (Exception e) {
            throw new IllegalValueException(e.getMessage());
        }

        final List<Tag> productTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            productTags.add(tag.toModelType());
        }

        Product product = new Product(modelName, stockLevel, new HashSet<>(productTags));

        // Set supplier name if present
        if (supplierName != null && !supplierName.isEmpty()) {
            if (!Name.isValidName(supplierName)) {
                throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
            }
            product.setSupplierName(new Name(supplierName));
        }
        return product;
    }
}
