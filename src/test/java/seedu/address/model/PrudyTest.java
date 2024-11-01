package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalClients.ALICE;
import static seedu.address.testutil.TypicalClients.getTypicalPrudy;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.client.Client;
import seedu.address.model.client.exceptions.DuplicateClientException;
import seedu.address.testutil.ClientBuilder;

public class PrudyTest {

    private final Prudy prudy = new Prudy();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), prudy.getClientList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> prudy.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyPrudy_replacesData() {
        Prudy newData = getTypicalPrudy();
        prudy.resetData(newData);
        assertEquals(newData, prudy);
    }

    @Test
    public void resetData_withDuplicateClients_throwsDuplicateClientException() {
        // Two clients with the same identity fields
        Client editedAlice = new ClientBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Client> newClients = Arrays.asList(ALICE, editedAlice);
        PrudyStub newData = new PrudyStub(newClients);

        assertThrows(DuplicateClientException.class, () -> prudy.resetData(newData));
    }

    @Test
    public void hasClient_nullClient_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> prudy.hasClient(null));
    }

    @Test
    public void hasClient_clientNotInPrudy_returnsFalse() {
        assertFalse(prudy.hasClient(ALICE));
    }

    @Test
    public void hasClient_clientInPrudy_returnsTrue() {
        prudy.addClient(ALICE);
        assertTrue(prudy.hasClient(ALICE));
    }

    @Test
    public void hasClient_clientWithSameIdentityFieldsInPrudy_returnsTrue() {
        prudy.addClient(ALICE);
        Client editedAlice = new ClientBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(prudy.hasClient(editedAlice));
    }

    @Test
    public void getClientList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> prudy.getClientList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = Prudy.class.getCanonicalName() + "{clients=" + prudy.getClientList() + "}";
        assertEquals(expected, prudy.toString());
    }

    /**
     * A stub ReadOnlyPrudy whose clients list can violate interface constraints.
     */
    private static class PrudyStub implements ReadOnlyPrudy {
        private final ObservableList<Client> clients = FXCollections.observableArrayList();

        PrudyStub(Collection<Client> clients) {
            this.clients.setAll(clients);
        }

        @Override
        public ObservableList<Client> getClientList() {
            return clients;
        }
    }

}
