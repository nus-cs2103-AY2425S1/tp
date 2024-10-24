package seedu.address.testutil;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.product.Product;
import seedu.address.model.product.ProductName;
import seedu.address.model.tag.Tag;

import seedu.address.model.util.SampleDataUtil;


/**
 * A utility class to help with building Product objects.
 */
public class ProductBuilder {

    public static final String DEFAULT_NAME = "Apple";

    private ProductName name;
    private Set<Tag> tags;

    /**
     * Creates a {@code ProductBuilder} with the default details.
     */
    public ProductBuilder() {
        name = new ProductName(DEFAULT_NAME);
        tags = new HashSet<>();
    }

    /**
     * Initializes the ProductBuilder with the data of {@code productToCopy}.
     */
    public ProductBuilder(Product productToCopy) {
        name = productToCopy.getName();
        tags = new HashSet<>(productToCopy.getTags());
    }

    /**
     * Sets the {@code ProductName} of the {@code Product} that we are building.
     */
    public ProductBuilder withName(String name) {
        this.name = new ProductName(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Product} that we are building.
     */
    public ProductBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    public Product build() {
        return new Product(name, tags);
    }
}
