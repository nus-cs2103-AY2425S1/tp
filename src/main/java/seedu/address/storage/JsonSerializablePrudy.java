package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Prudy;
import seedu.address.model.ReadOnlyPrudy;
import seedu.address.model.client.Client;

/**
 * An Immutable Prudy that is serializable to JSON format.
 */
@JsonRootName(value = "prudy")
class JsonSerializablePrudy {

    public static final String MESSAGE_DUPLICATE_CLIENT = "Clients list contains duplicate client(s).";

    private final List<JsonAdaptedClient> clients = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializablePrudy} with the given clients.
     */
    @JsonCreator
    public JsonSerializablePrudy(@JsonProperty("clients") List<JsonAdaptedClient> clients) {
        this.clients.addAll(clients);
    }

    /**
     * Converts a given {@code ReadOnlyPrudy} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializablePrudy}.
     */
    public JsonSerializablePrudy(ReadOnlyPrudy source) {
        clients.addAll(source.getClientList().stream().map(JsonAdaptedClient::new).collect(Collectors.toList()));
    }

    /**
     * Converts this data into the model's {@code Prudy} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Prudy toModelType() throws IllegalValueException {
        Prudy prudy = new Prudy();
        for (JsonAdaptedClient jsonAdaptedClient : clients) {
            Client client = jsonAdaptedClient.toModelType();
            if (prudy.hasClient(client)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_CLIENT);
            }
            prudy.addClient(client);
        }
        return prudy;
    }

}
