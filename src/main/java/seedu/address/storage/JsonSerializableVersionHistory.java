package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.VersionHistory;
import seedu.address.model.group.Group;
import seedu.address.model.student.Student;
import seedu.address.model.task.Task;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "versionhistory")
class JsonSerializableVersionHistory {

    private final List<JsonSerializableAddressBook> addressBooks = new ArrayList<>();
    private final int currentVersionIndex;

    /**
     * Constructs a {@code JsonSerializableVersionHistory} with the given persons.
     */
    @JsonCreator
    public JsonSerializableVersionHistory(@JsonProperty("addressBooks") List<JsonSerializableAddressBook> addressBooks,
                                          @JsonProperty("currentVersionIndex") int currentVersionIndex) {
        this.addressBooks.addAll(addressBooks);
        this.currentVersionIndex = currentVersionIndex;

    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableVersionHistory(VersionHistory source) {
        addressBooks.addAll(source.versions.stream().map(JsonSerializableAddressBook::new).collect(Collectors.toList()));
        this.currentVersionIndex = source.currentVersionIndex;
    }

    /**
     * Converts this version history into addressbooks readable by the system.
     */
    public VersionHistory toModelType() throws IllegalValueException {
        VersionHistory versionHistory = new VersionHistory();
        for (JsonSerializableAddressBook jsonAddressBook : addressBooks) {
            AddressBook addressBook = jsonAddressBook.toModelType();
            versionHistory.versions.add(addressBook);
        }
        versionHistory.currentVersionIndex = currentVersionIndex;
        return versionHistory;
    }

}
