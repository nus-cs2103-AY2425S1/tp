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
import seedu.address.model.product.ProductName;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Product}.
 */
class JsonAdaptedProduct {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Product's %s field is missing!";

    private final String name;
    private final String supplierName; // This is empty if the product has no supplier
    private final int stockLevel;
    private final int minStockLevel;
    private final int maxStockLevel;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedProduct} with the given product details.
     */
    @JsonCreator
    public JsonAdaptedProduct(@JsonProperty("name") String name, @JsonProperty("supplierName") String supplierName,
                              @JsonProperty("stockLevel") int stockLevel,
                              @JsonProperty("minStockLevel") int minStockLevel,
                              @JsonProperty("maxStockLevel") int maxStockLevel,
                              @JsonProperty("tags") List<JsonAdaptedTag> tags) {
        this.name = name;
        this.supplierName = supplierName;
        this.stockLevel = stockLevel;
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
        stockLevel = source.getStockLevel();
        minStockLevel = source.getMinStockLevel();
        maxStockLevel = source.getMaxStockLevel();
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted product object into the model's {@code Product} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted product.
     */
    public Product toModelType() throws IllegalValueException {
        final List<Tag> productTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            productTags.add(tag.toModelType());
        }
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, ProductName
                .class.getSimpleName()));
        }
        if (!ProductName.isValidName(name)) {
            throw new IllegalValueException(ProductName.MESSAGE_CONSTRAINTS);
        }
        final ProductName modelName = new ProductName(name);
        final Set<Tag> modelTags = new HashSet<>(productTags);
        Product product = new Product(modelName, modelTags);;
        product.setStockLevel(stockLevel);
        product.setMinStockLevel(minStockLevel);
        product.setMaxStockLevel(maxStockLevel);
        if (!supplierName.isEmpty()) {
            product.setSupplierName(new seedu.address.model.supplier.Name(supplierName));
        }
        return product;
    }
}
