package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import seedu.address.model.contact.Contact;

public class PaginationPanelTest {
    @BeforeAll
    public static void initJavaFxPlatform() {
        new JFXPanel();
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PaginationPanel(null));
    }

    @Test
    public void constructor_validObservationList_success() {
        ObservableList<Contact> observableList = FXCollections.observableList(new ArrayList<>());
        assertDoesNotThrow(() -> new PaginationPanel(observableList));
    }

    @Test
    public void convertItemIndexToDisplayIndex_invalidIndex_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> PaginationPanel.convertItemIndexToDisplayIndex(-1));
    }

    @Test
    public void convertItemIndexToDisplayIndex_validIndex_success() {
        assertEquals(1, PaginationPanel.convertItemIndexToDisplayIndex(0));
        assertEquals(10, PaginationPanel.convertItemIndexToDisplayIndex(9));
    }
}
