package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.Field;
import javafx.scene.control.Label;

import org.junit.jupiter.api.Test;
import seedu.address.model.event.Event;

public class EventCardTest {

    @Test
    void constructor_validEventAndIndex_initializesFieldsCorrectly() throws NoSuchFieldException, IllegalAccessException {
        Event event = new Event("Meeting");
        EventCard eventCard = new EventCard(event, 1);

        Field idField = EventCard.class.getDeclaredField("id");
        idField.setAccessible(true);
        Label idLabel = (Label) idField.get(eventCard);
        assertEquals("1. ", idLabel.getText());

        Field valueField = EventCard.class.getDeclaredField("value");
        valueField.setAccessible(true);
        Label valueLabel = (Label) valueField.get(eventCard);
        assertEquals("Meeting", valueLabel.getText());
    }

    @Test
    void constructor_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EventCard(null, 1));
    }

    @Test
    void constructor_negativeIndex_initializesFieldsCorrectly() throws NoSuchFieldException, IllegalAccessException {
        Event event = new Event("Meeting");
        EventCard eventCard = new EventCard(event, -1);

        Field idField = EventCard.class.getDeclaredField("id");
        idField.setAccessible(true);
        Label idLabel = (Label) idField.get(eventCard);
        assertEquals("-1. ", idLabel.getText());

        Field valueField = EventCard.class.getDeclaredField("value");
        valueField.setAccessible(true);
        Label valueLabel = (Label) valueField.get(eventCard);
        assertEquals("Meeting", valueLabel.getText());
    }

    @Test
    void constructor_emptyEventValue_initializesFieldsCorrectly() throws NoSuchFieldException, IllegalAccessException {
        Event event = new Event("");
        EventCard eventCard = new EventCard(event, 1);

        Field idField = EventCard.class.getDeclaredField("id");
        idField.setAccessible(true);
        Label idLabel = (Label) idField.get(eventCard);
        assertEquals("1. ", idLabel.getText());

        Field valueField = EventCard.class.getDeclaredField("value");
        valueField.setAccessible(true);
        Label valueLabel = (Label) valueField.get(eventCard);
        assertEquals("", valueLabel.getText());
    }
}