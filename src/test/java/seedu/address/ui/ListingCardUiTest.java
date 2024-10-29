package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import seedu.address.MainApp;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Date;
import seedu.address.model.appointment.From;
import seedu.address.model.appointment.To;
import seedu.address.model.listing.Address;
import seedu.address.model.listing.Area;
import seedu.address.model.listing.Listing;
import seedu.address.model.listing.Price;
import seedu.address.model.listing.Region;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Property;
import seedu.address.model.person.Seller;
import seedu.address.model.tag.Tag;

public class ListingCardUiTest extends ApplicationTest {

    private ListingCard listingCard;
    private Listing sampleListing;
    private final Seller sampleSeller = (Seller) createSampleSeller();

    @BeforeEach
    public void setUp() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(MainApp::new);
        FxToolkit.showStage();
        WaitForAsyncUtils.waitForFxEvents(20); // Wait for JavaFX to complete rendering
    }

    @AfterEach
    public void stopApp() throws TimeoutException {
        FxToolkit.cleanupStages(); // Clean up after tests
    }

    @Override
    public void start(Stage stage) {
        // Create a sample Listing object for testing
        sampleListing = createSampleListing();

        // Instantiate ListingCard to be tested, passing in the sample Listing and a displayed index of 1
        listingCard = new ListingCard(sampleListing, 1);

        // Set the scene for JavaFX testing
        stage.setScene(listingCard.getRoot().getScene());
        stage.show();
    }

    @Test
    void listingCard_displayCorrectDetails() {
        // Assert that the listing object is not null
        assertNotNull(listingCard.listing);

        // Check if the displayed name, price, area, region, address, and seller labels are correct
        assertEquals("1. ", listingCard.getId().getText());
        assertEquals("Sample Listing", listingCard.getName().getText());
        assertEquals("$500000", listingCard.getPrice().getText());
        assertEquals("100 m²", listingCard.getArea().getText());
        assertEquals("NORTH", listingCard.getRegion().getText());
        assertEquals("123 Main St", listingCard.getAddress().getText());
        assertEquals("John Seller", listingCard.getSeller().getText());
    }

    /**
     * Helper method to create a sample Listing object for testing.
     */
    private Listing createSampleListing() {
        return new Listing(
                new Name("Sample Listing"),
                new Address("123 Main St"),
                new Price("500000", new BigDecimal(500000)),
                new Area(100),
                Region.NORTH,
                sampleSeller,
                null
        );
    }

    /**
     * Helper method to create a sample Seller object for testing.
     */
    private Person createSampleSeller() {
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(new Tag("clients"));

        return new Seller(
                new Name("John Seller"),
                new Phone("98765432"),
                new Email("seller@example.com"),
                tagSet,
                new Appointment(new Date("2023-01-02"), new From("11:00"), new To("12:00")),
                new Property("NUS")
        );
    }
}
