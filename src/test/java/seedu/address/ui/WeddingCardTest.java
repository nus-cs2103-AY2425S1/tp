package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Field;
import java.util.concurrent.CountDownLatch;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.scene.control.Label;
import seedu.address.model.wedding.Date;
import seedu.address.model.wedding.Venue;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingName;



public class WeddingCardTest {

    private static final Wedding SAMPLE_WEDDING = new Wedding(new WeddingName("Alice & Bob"),
            new Venue("Marina Bay Sands"), new Date("22/12/2025"));

    @BeforeAll
    public static void setUpClass() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(latch::countDown);
        latch.await();
    }

    @Test
    public void display() throws InterruptedException, NoSuchFieldException, IllegalAccessException {
        CountDownLatch latch = new CountDownLatch(1);

        final WeddingCard[] weddingCardHolder = new WeddingCard[1];

        Platform.runLater(() -> {
            weddingCardHolder[0] = new WeddingCard(SAMPLE_WEDDING, 1);
            latch.countDown();
        });

        latch.await();

        WeddingCard weddingCard = weddingCardHolder[0];

        Field idField = WeddingCard.class.getDeclaredField("id");
        idField.setAccessible(true);
        Label idLabel = (Label) idField.get(weddingCard);
        assertEquals("1. ", idLabel.getText());

        Field namesField = WeddingCard.class.getDeclaredField("names");
        namesField.setAccessible(true);
        Label namesLabel = (Label) namesField.get(weddingCard);
        assertEquals("Alice & Bob", namesLabel.getText());

        Field venueField = WeddingCard.class.getDeclaredField("venue");
        venueField.setAccessible(true);
        Label venueLabel = (Label) venueField.get(weddingCard);
        assertEquals("Marina Bay Sands", venueLabel.getText());

        Field dateTimeField = WeddingCard.class.getDeclaredField("date");
        dateTimeField.setAccessible(true);
        Label dateTimeLabel = (Label) dateTimeField.get(weddingCard);
        assertEquals("22/12/2025", dateTimeLabel.getText());
    }
}
