package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIER_REJECT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalClients.ALICE;
import static seedu.address.testutil.TypicalClients.getTypicalAgentAssist;

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

public class AgentAssistTest {

    private final AgentAssist agentAssist = new AgentAssist();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), agentAssist.getClientList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> agentAssist.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAgentAssist_replacesData() {
        AgentAssist newData = getTypicalAgentAssist();
        agentAssist.resetData(newData);
        assertEquals(newData, agentAssist);
    }

    @Test
    public void resetData_withDuplicateClients_throwsDuplicateClientException() {
        // Two clients with the same identity fields
        Client editedAlice = new ClientBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTier(VALID_TIER_REJECT)
                .build();
        List<Client> newClients = Arrays.asList(ALICE, editedAlice);
        AgentAssistStub newData = new AgentAssistStub(newClients);

        assertThrows(DuplicateClientException.class, () -> agentAssist.resetData(newData));
    }

    @Test
    public void hasClient_nullClient_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> agentAssist.hasClient(null));
    }

    @Test
    public void hasClient_clientNotInAgentAssist_returnsFalse() {
        assertFalse(agentAssist.hasClient(ALICE));
    }

    @Test
    public void hasClient_clientInAgentAssist_returnsTrue() {
        agentAssist.addClient(ALICE);
        assertTrue(agentAssist.hasClient(ALICE));
    }

    @Test
    public void hasClient_clientWithSameIdentityFieldsInAgentAssist_returnsTrue() {
        agentAssist.addClient(ALICE);
        Client editedAlice = new ClientBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTier(VALID_TIER_REJECT)
                .build();
        assertTrue(agentAssist.hasClient(editedAlice));
    }

    @Test
    public void getClientList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> agentAssist.getClientList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = AgentAssist.class.getCanonicalName() + "{clients=" + agentAssist.getClientList() + "}";
        assertEquals(expected, agentAssist.toString());
    }

    /**
     * A stub ReadOnlyAgentAssist whose clients list can violate interface constraints.
     */
    private static class AgentAssistStub implements ReadOnlyAgentAssist {
        private final ObservableList<Client> clients = FXCollections.observableArrayList();

        AgentAssistStub(Collection<Client> clients) {
            this.clients.setAll(clients);
        }

        @Override
        public ObservableList<Client> getClientList() {
            return clients;
        }
    }

}
