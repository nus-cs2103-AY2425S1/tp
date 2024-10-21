package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.address.model.event.Event;


public class EventListPanelTest {

    @Test
    void constructor_validEventList_initializesListViewCorrectly() throws NoSuchFieldException, IllegalAccessException {
        ObservableList<Event> eventList = FXCollections.observableArrayList(new Event("Meeting"),
                new Event("Conference"));
        EventListPanel eventListPanel = new EventListPanel(eventList);

        Field listViewField = EventListPanel.class.getDeclaredField("eventListView");
        listViewField.setAccessible(true);
        ListView<Event> listView = (ListView<Event>) listViewField.get(eventListPanel);

        assertEquals(2, listView.getItems().size());
        assertEquals("Meeting", listView.getItems().get(0).toString());
        assertEquals("Conference", listView.getItems().get(1).toString());
    }

    @Test
    void constructor_emptyEventList_initializesListViewCorrectly() throws NoSuchFieldException, IllegalAccessException {
        ObservableList<Event> eventList = FXCollections.observableArrayList();
        EventListPanel eventListPanel = new EventListPanel(eventList);

        Field listViewField = EventListPanel.class.getDeclaredField("eventListView");
        listViewField.setAccessible(true);
        ListView<Event> listView = (ListView<Event>) listViewField.get(eventListPanel);

        assertEquals(0, listView.getItems().size());
    }

    @Test
    void updateItem_nullEvent_setsGraphicAndTextToNull() {
        EventListPanel.EventListViewCell cell = new EventListPanel(null).new EventListViewCell();
        cell.updateItem(null, true);
        assertNull(cell.getGraphic());
        assertNull(cell.getText());
    }

    @Test
    void updateItem_nonNullEvent_setsGraphicCorrectly() throws NoSuchFieldException, IllegalAccessException {
        Event event = new Event("Meeting");
        EventListPanel.EventListViewCell cell = new EventListPanel(null).new EventListViewCell();
        cell.updateItem(event, false);

        Node graphic = cell.getGraphic();
        Field eventField = graphic.getClass().getDeclaredField("event");
        eventField.setAccessible(true);
        Event eventFromCard = (Event) eventField.get(graphic);

        assertEquals("Meeting", eventFromCard.toString());
    }
}
