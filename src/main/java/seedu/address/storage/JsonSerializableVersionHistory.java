package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.VersionHistory;

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
        addressBooks.addAll(
                source.getVersions().stream().map(JsonSerializableAddressBook::new).collect(Collectors.toList()));
        this.currentVersionIndex = source.getCurrentVersionIndex();
    }

    /**
     * Converts this version history into addressbooks readable by the system.
     */
    public VersionHistory toModelType() throws IllegalValueException {
        VersionHistory versionHistory = new VersionHistory();
        for (JsonSerializableAddressBook jsonAddressBook : addressBooks) {
            AddressBook addressBook = jsonAddressBook.toModelType();
            versionHistory.getVersions().add(addressBook);
        }
        versionHistory.setCurrentVersionIndex(currentVersionIndex);
        return versionHistory;
    }

}
