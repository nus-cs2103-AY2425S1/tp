package seedu.address.storage;

import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Listing;
import seedu.address.model.tag.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CsvSerializableAddressBook {

    private final List<CsvAdaptedPerson> persons = new ArrayList<>();

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
