package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.model.client.Client;
import seedu.address.model.client.exceptions.ClientNotFoundException;
import seedu.address.model.client.exceptions.DuplicateClientException;
import seedu.address.testutil.ClientBuilder;

class ClientBookTest {

    private ClientBook clientBook;
    private Client clientAlice;
    private Client clientBob;

    @BeforeEach
    void setUp() {
        clientBook = new ClientBook();
        clientAlice = new ClientBuilder().withName("Alice").buildBuyer();
        clientBob = new ClientBuilder().withName("Bob").buildSeller();
    }

    // ============= Constructor Tests ==================
    @Test
    void constructor_initializesEmptyClientList() {
        assertTrue(clientBook.getClientList().isEmpty());
    }

    @Test
    void constructor_copiesExistingClientList() {
        ClientBook anotherClientBook = new ClientBook(clientBook);
        assertEquals(clientBook, anotherClientBook);
    }

    // ============= Client List Operations Tests ==================
    @Test
    void setClients_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> clientBook.setClients(null));
    }

    @Test
    void setClients_containsNullClient_throwsNullPointerException() {
        List<Client> listWithNullClient = Arrays.asList(clientAlice, null);
        assertThrows(NullPointerException.class, () -> clientBook.setClients(listWithNullClient));
    }

    @Test
    void setClients_containsDuplicateClient_throwsDuplicateClientException() {
        List<Client> listWithDuplicateClient = Arrays.asList(clientAlice, clientAlice);
        assertThrows(DuplicateClientException.class, () -> clientBook.setClients(listWithDuplicateClient));
    }

    @Test
    void setClients_validClients_setsClientsSuccessfully() {
        clientBook.setClients(Arrays.asList(clientAlice, clientBob));
        assertEquals(Arrays.asList(clientAlice, clientBob), clientBook.getClientList());
    }

    @Test
    void resetData_nullData_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> clientBook.resetData(null));
    }

    @Test
    void resetData_validData_replacesExistingData() {
        ClientBook newData = new ClientBook();
        newData.addClient(clientAlice);
        clientBook.resetData(newData);
        assertEquals(newData, clientBook);
    }

    // ============= Client Existence Check Tests ==================
    @Test
    void hasClient_nullClient_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> clientBook.hasClient(null));
    }

    @Test
    void hasClient_clientNotInClientBook_returnsFalse() {
        assertFalse(clientBook.hasClient(clientAlice));
    }

    @Test
    void hasClient_clientInClientBook_returnsTrue() {
        clientBook.addClient(clientAlice);
        assertTrue(clientBook.hasClient(clientAlice));
    }

    @Test
    void sameEmailExists_sameEmailExists_returnsTrue() {
        clientBook.addClient(clientAlice);
        Client clientWithSameEmail = new ClientBuilder().withEmail(clientAlice.getEmail().value).buildBuyer();
        assertTrue(clientBook.sameEmailExists(clientWithSameEmail));
    }

    @Test
    void sameEmailExists_differentEmailExists_returnsFalse() {
        clientBook.addClient(clientAlice);
        assertFalse(clientBook.sameEmailExists(clientBob));
    }

    // ============= Add, Replace, and Remove Client Tests ==================
    @Test
    void addClient_nullClient_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> clientBook.addClient(null));
    }

    @Test
    void addClient_duplicateClient_throwsDuplicateClientException() {
        clientBook.addClient(clientAlice);
        assertThrows(DuplicateClientException.class, () -> clientBook.addClient(clientAlice));
    }

    @Test
    void addClient_validClient_addsSuccessfully() {
        clientBook.addClient(clientAlice);
        assertTrue(clientBook.hasClient(clientAlice));
    }

    @Test
    void setClient_nullTarget_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> clientBook.setClient(null, clientAlice));
    }

    @Test
    void setClient_nullEditedClient_throwsNullPointerException() {
        clientBook.addClient(clientAlice);
        assertThrows(NullPointerException.class, () -> clientBook.setClient(clientAlice, null));
    }

    @Test
    void setClient_targetNotInClientBook_throwsClientNotFoundException() {
        assertThrows(ClientNotFoundException.class, () -> clientBook.setClient(clientAlice, clientBob));
    }

    @Test
    void setClient_editedClientHasDuplicateIdentity_throwsDuplicateClientException() {
        clientBook.addClient(clientAlice);
        clientBook.addClient(clientBob);
        Client duplicateClient = new ClientBuilder(clientBob).withName(clientAlice.getName().fullName).buildSeller();
        assertThrows(DuplicateClientException.class, () -> clientBook.setClient(clientAlice, duplicateClient));
    }

    @Test
    void setClient_validReplacement_replacesSuccessfully() {
        clientBook.addClient(clientAlice);
        clientBook.setClient(clientAlice, clientBob);
        assertFalse(clientBook.hasClient(clientAlice));
        assertTrue(clientBook.hasClient(clientBob));
    }

    @Test
    void removeClient_nullClient_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> clientBook.removeClient(null));
    }

    @Test
    void removeClient_clientNotInClientBook_throwsClientNotFoundException() {
        assertThrows(ClientNotFoundException.class, () -> clientBook.removeClient(clientAlice));
    }

    @Test
    void removeClient_existingClient_removesSuccessfully() {
        clientBook.addClient(clientAlice);
        clientBook.removeClient(clientAlice);
        assertFalse(clientBook.hasClient(clientAlice));
    }

    // ============= Utility Tests ==================
    @Test
    void getClientList_modifyList_throwsUnsupportedOperationException() {
        ObservableList<Client> clientList = clientBook.getClientList();
        assertThrows(UnsupportedOperationException.class, () -> clientList.remove(0));
    }

    @Test
    void equals_sameObject_returnsTrue() {
        assertTrue(clientBook.equals(clientBook));
    }

    @Test
    void equals_differentType_returnsFalse() {
        assertFalse(clientBook.equals(5));
    }

    @Test
    void equals_differentClientList_returnsFalse() {
        ClientBook differentClientBook = new ClientBook();
        differentClientBook.addClient(clientAlice);
        assertFalse(clientBook.equals(differentClientBook));
    }

    @Test
    void equals_sameClientList_returnsTrue() {
        ClientBook anotherClientBook = new ClientBook();
        clientBook.addClient(clientAlice);
        anotherClientBook.addClient(clientAlice);
        assertTrue(clientBook.equals(anotherClientBook));
    }
}
