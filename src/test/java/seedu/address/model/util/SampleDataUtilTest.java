package seedu.address.model.util;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.product.Product;
import seedu.address.model.supplier.Supplier;
import seedu.address.model.tag.Tag;

public class SampleDataUtilTest {

    @Test
    public void getSampleSuppliers_validSuppliersArray_correctArrayLength() {
        Supplier[] sampleSuppliers = SampleDataUtil.getSampleSuppliers();
        assertEquals(6, sampleSuppliers.length);
    }

    @Test
    public void getSampleSuppliers_suppliersHaveEmptyProductList_emptyProductLists() {
        Supplier[] sampleSuppliers = SampleDataUtil.getSampleSuppliers();
        for (Supplier supplier : sampleSuppliers) {
            assertEquals(SampleDataUtil.EMPTY_PRODUCT_LIST, supplier.getProducts());
        }
    }

    @Test
    public void getTagSet_validTags_correctTagSetSize() {
        Set<Tag> tags = SampleDataUtil.getTagSet("friends", "colleagues");
        assertEquals(2, tags.size());
    }

    @Test
    public void getProductSet_validProductNames_correctProductSetSize() {
        Set<Product> products = SampleDataUtil.getProductSet("Product A", "Product B");
        assertEquals(2, products.size());
    }

    @Test
    public void getSampleAddressBook_validSampleAddressBook_correctSuppliersAdded() {
        Supplier[] expectedSuppliers = SampleDataUtil.getSampleSuppliers();
        List<Supplier> actualSuppliers = SampleDataUtil.getSampleAddressBook().getSupplierList();

        assertArrayEquals(expectedSuppliers, actualSuppliers.toArray());
    }
    @Test
    public void getSampleProducts_validProductsArray_correctArrayLength() {
        Product[] sampleProducts = SampleDataUtil.getSampleProducts();
        assertEquals(6, sampleProducts.length);
    }
}
