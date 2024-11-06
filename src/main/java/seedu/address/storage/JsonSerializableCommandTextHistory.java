package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.CommandTextHistory;

/**
 * An Immutable CommandTextHistory that is serializable to JSON format.
 */
@JsonRootName(value = "commandtexthistory")
public class JsonSerializableCommandTextHistory {

    private final List<String> commandTextList = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableCommandTextHistory} with the given
     * command text history.
     */
    @JsonCreator
    public JsonSerializableCommandTextHistory(
            @JsonProperty("commandTextList") List<String> commandTextList) {
        this.commandTextList.addAll(commandTextList);
    }

    /**
     * Converts a given {@code CommandTextHistory} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created
     *               {@code JsonSerializableCommandTextHistory}.
     */
    public JsonSerializableCommandTextHistory(CommandTextHistory source) {
        commandTextList.addAll(source.getCommandTextList());
    }

    /**
     * Converts this command text history into the model's
     * {@code CommandTextHistory} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public CommandTextHistory toModelType() throws IllegalValueException {
        CommandTextHistory commandTextHistory = new CommandTextHistory();
        for (String commandText : commandTextList) {
            commandTextHistory.addCommandText(commandText);
        }
        return commandTextHistory;
    }
}
