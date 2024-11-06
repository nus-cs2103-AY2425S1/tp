package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import seedu.address.model.person.Buyer;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Seller;
import seedu.address.model.tag.Tag;


public class ListingCardUiTest extends ApplicationTest {

    private ListingCard listingCard;
    private Listing sampleListing;
    private final Seller sampleSeller = (Seller) createSampleSeller();
    private final Buyer sampleBuyerOne = (Buyer) createSampleBuyerOne();
    private final Buyer sampleBuyerTwo = (Buyer) createSampleBuyerTwo();


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
    void listingCard_uiComponentsAreVisible() {
        assertNotNull(listingCard.getName());
        assertNotNull(listingCard.getPrice());
        assertNotNull(listingCard.getArea());
        assertNotNull(listingCard.getRegion());
        assertNotNull(listingCard.getAddress());
        assertNotNull(listingCard.getSeller());
        assertNotNull(listingCard.getBuyers());

        assertTrue(listingCard.getName().isVisible());
        assertTrue(listingCard.getPrice().isVisible());
        assertTrue(listingCard.getArea().isVisible());
        assertTrue(listingCard.getRegion().isVisible());
        assertTrue(listingCard.getAddress().isVisible());
        assertTrue(listingCard.getSeller().isVisible());
        assertTrue(listingCard.getBuyers().isVisible());
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


        // Check if the buyers are correctly displayed
        FlowPane buyersFlowPane = listingCard.getBuyers();
        assertEquals(2, buyersFlowPane.getChildren().size());

        // Check if the buyer name is accurate and lexicographically accurate
        Label buyerOne = (Label) buyersFlowPane.getChildren().get(0);
        Label buyerTwo = (Label) buyersFlowPane.getChildren().get(1);
        assertEquals("John Buyer", buyerOne.getText());
        assertEquals("Sarah Buyer", buyerTwo.getText());

    }

    @Test
    void listingCard_handlesDifferentIndexes() {
        // Test ListingCard with various indexes
        ListingCard listingCardIndex5 = new ListingCard(sampleListing, 5);
        assertEquals("5. ", listingCardIndex5.getId().getText());

        ListingCard listingCardIndex10 = new ListingCard(sampleListing, 10);
        assertEquals("10. ", listingCardIndex10.getId().getText());
    }

    /**
     * Helper method to create a sample Listing object for testing.
     */
    private Listing createSampleListing() {
        Set<Person> buyersSet = new HashSet<>();
        buyersSet.add(sampleBuyerOne);
        buyersSet.add(sampleBuyerTwo);

        return new Listing(
                new Name("Sample Listing"),
                new Address("123 Main St"),
                new Price("500000", new BigDecimal(500000)),
                new Area("100"),
                Region.NORTH,
                sampleSeller,
                buyersSet
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
                new Appointment(new Date("02-01-23"), new From("1100"), new To("1200"))
        );
    }

    /**
     * Helper method to create a sample Buyer object for testing.
     */
    private Person createSampleBuyerOne() {
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(new Tag("friends"));
        tagSet.add(new Tag("colleagues"));

        return new Buyer(
                new Name("John Buyer"),
                new Phone("91234567"),
                new Email("buyer@example.com"),
                tagSet,
                new Appointment(new Date("01-01-23"), new From("10:00"), new To("11:00"))
        );
    }

    /**
     * Helper method to create a sample Buyer object for testing.
     */
    private Person createSampleBuyerTwo() {
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(new Tag("friends"));

        return new Buyer(
                new Name("Sarah Buyer"),
                new Phone("91112222"),
                new Email("buyer@example.com"),
                tagSet,
                new Appointment(new Date("01-01-23"), new From("10:00"), new To("11:00"))
        );
    }
}
