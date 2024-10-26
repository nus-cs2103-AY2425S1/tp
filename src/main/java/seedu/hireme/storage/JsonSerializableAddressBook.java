package seedu.hireme.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.hireme.commons.exceptions.IllegalValueException;
import seedu.hireme.model.AddressBook;
import seedu.hireme.model.ReadOnlyAddressBook;
import seedu.hireme.model.internshipapplication.InternshipApplication;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_INTERNSHIP_APPLICATION =
            "Internship application list contains duplicate internship applications(s).";

    private final List<JsonAdaptedInternship> internships = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given internship applications.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("internships") List<JsonAdaptedInternship> internships) {
        this.internships.addAll(internships);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        internships.addAll(source.getList().stream().map(JsonAdaptedInternship::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (JsonAdaptedInternship jsonAdaptedInternship : internships) {
            InternshipApplication internship = jsonAdaptedInternship.toModelType();
            if (addressBook.hasItem(internship)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_INTERNSHIP_APPLICATION);
            }
            addressBook.addItem(internship);
        }
        return addressBook;
    }

}
