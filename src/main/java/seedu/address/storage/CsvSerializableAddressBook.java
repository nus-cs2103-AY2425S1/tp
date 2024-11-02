package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Listing;
import seedu.address.model.tag.Tag;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
public class CsvSerializableAddressBook {

    private final List<CsvAdaptedPerson> persons = new ArrayList<>();

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for comma-seperated value files use.
     *
     * @param source future changes to this will not affect the created {@code CsvSerialiozableAddressBook}.
     */
    public CsvSerializableAddressBook(ReadOnlyAddressBook source) {
        persons.addAll(source.getPersonList().stream().map(person -> new CsvAdaptedPerson(
                person.getName().fullName,
                person.getPhone().value,
                person.getAddress().value,
                person.getEmail().value,
                person.getRemark().value,
                tagsToString(person.getTags()),
                listingsToString(person.getListings().getListings())
        )).collect(Collectors.toList()));
    }

    public List<CsvAdaptedPerson> getPersons() {
        return this.persons;
    }

    private static String tagsToString(Set<Tag> tags) {
        return tags.stream()
                .map(tag -> tag.tagName)
                .collect(Collectors.joining("; "));
    }

    private static String listingsToString(ArrayList<Listing> listings) {
        return listings.stream()
                .map(listing -> listing.toString())
                .collect(Collectors.joining("; "));
    }
}
