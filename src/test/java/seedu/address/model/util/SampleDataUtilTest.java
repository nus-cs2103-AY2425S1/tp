package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.delivery.Cost;
import seedu.address.model.delivery.DateTime;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.Quantity;
import seedu.address.model.delivery.Status;
import seedu.address.model.product.Product;
import seedu.address.model.supplier.Company;
import seedu.address.model.supplier.Email;
import seedu.address.model.supplier.Name;
import seedu.address.model.supplier.Phone;
import seedu.address.model.supplier.Supplier;
import seedu.address.model.supplier.SupplierStatus;
import seedu.address.model.tag.Tag;


public class SampleDataUtilTest {

    @Test
    public void getSampleSuppliers_correctSupplierData() {
        Supplier[] suppliers = SampleDataUtil.getSampleSuppliers();

        assertEquals(6, suppliers.length); //check the expected number of suppliers

        //check specific values for one of the suppliers
        Supplier alexYeoh = suppliers[0];
        assertEquals("Alex Yeoh", alexYeoh.getName().fullName);
        assertEquals("87438807", alexYeoh.getPhone().value);
        assertEquals("alexyeoh@example.com", alexYeoh.getEmail().value);
        assertEquals("companya", alexYeoh.getCompany().value);
        assertTrue(alexYeoh.getTags().contains(new Tag("friends")));
        assertTrue(alexYeoh.getProducts().contains(new Product("rice")));
        assertEquals(new SupplierStatus("active"), alexYeoh.getStatus());
    }

    @Test
    public void getSampleDeliveries_correctDeliveryData() {
        Delivery[] deliveries = SampleDataUtil.getSampleDeliveries();

        assertEquals(6, deliveries.length); //check the expected number of deliveries

        //check specific values for one of the deliveries
        Delivery appleDelivery = deliveries[0];
        assertEquals(new Product("Apple"), appleDelivery.getDeliveryProduct());
        assertEquals(new Supplier(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                        new Company("companyA"), SampleDataUtil.getTagSet("friends"),
                        SampleDataUtil.getProductSet("rice"), SampleDataUtil.STATUS),
                appleDelivery.getDeliverySender());
        assertEquals(Status.PENDING, appleDelivery.getDeliveryStatus());
        assertEquals(new DateTime("21-03-2025 12:12"), appleDelivery.getDeliveryDate());
        assertEquals(new Cost("20"), appleDelivery.getDeliveryCost());
        assertEquals(new Quantity("500 mL"), appleDelivery.getDeliveryQuantity());
    }

    @Test
    public void getSampleAddressBook_correctPopulation() {
        ReadOnlyAddressBook sampleAddressBook = SampleDataUtil.getSampleAddressBook();

        //verify the number of suppliers and deliveries
        assertEquals(6, sampleAddressBook.getSupplierList().size());
        assertEquals(6, sampleAddressBook.getDeliveryList().size());

        //verify specific supplier and delivery details within the address book
        Supplier supplierInBook = sampleAddressBook.getSupplierList().get(0);
        assertEquals("Alex Yeoh", supplierInBook.getName().fullName);

        Delivery deliveryInBook = sampleAddressBook.getDeliveryList().get(0);
        assertEquals(new Product("Apple"), deliveryInBook.getDeliveryProduct());
    }
}

