package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AgentAssist;
import seedu.address.model.ReadOnlyAgentAssist;
import seedu.address.model.client.Client;

/**
 * An Immutable AgentAssist that is serializable to JSON format.
 */
@JsonRootName(value = "agentassist")
class JsonSerializableAgentAssist {

    public static final String MESSAGE_DUPLICATE_CLIENT = "Clients list contains duplicate client(s).";

    private final List<JsonAdaptedClient> clients = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAgentAssist} with the given clients.
     */
    @JsonCreator
    public JsonSerializableAgentAssist(@JsonProperty("clients") List<JsonAdaptedClient> clients) {
        this.clients.addAll(clients);
    }

    /**
     * Converts a given {@code ReadOnlyAgentAssist} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAgentAssist}.
     */
    public JsonSerializableAgentAssist(ReadOnlyAgentAssist source) {
        clients.addAll(source.getClientList().stream().map(JsonAdaptedClient::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AgentAssist} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AgentAssist toModelType() throws IllegalValueException {
        AgentAssist agentAssist = new AgentAssist();
        for (JsonAdaptedClient jsonAdaptedClient : clients) {
            Client client = jsonAdaptedClient.toModelType();
            if (agentAssist.hasClient(client)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_CLIENT);
            }
            agentAssist.addClient(client);
        }
        return agentAssist;
    }

}
