package bizbook.logic.commands.exporter;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import bizbook.commons.util.FileUtil;
import bizbook.logic.commands.exporter.exceptions.EmptyAddressBookException;
import bizbook.model.AddressBook;
import bizbook.model.person.Address;
import bizbook.model.person.Email;
import bizbook.model.person.Name;
import bizbook.model.person.Note;
import bizbook.model.person.Person;
import bizbook.model.person.Phone;
import bizbook.model.tag.Tag;
import ezvcard.Ezvcard;
import ezvcard.VCard;

/**
 * Represents a class that can import an address book from a VCF file
 */
public class VcfImporter implements Importer {
    @Override
    public AddressBook importAddressBook(Path filePath) throws IOException, EmptyAddressBookException {
        String contents = FileUtil.readFromFile(filePath);
        List<VCard> vCards = Ezvcard.parse(contents).all();

        if (vCards.isEmpty()) {
            throw new EmptyAddressBookException();
        }

        AddressBook addressBook = new AddressBook();
        addressBook.setPersons(vCards.parallelStream().map(this::convertToPerson).toList());
        return addressBook;
    }

    private Person convertToPerson(VCard vCard) {
        Set<Tag> categories = vCard.getCategories().getValues().parallelStream()
                .map(Tag::new)
                .collect(Collectors.toSet());

        Set<Note> notes = vCard.getNotes().parallelStream()
                .map(note -> new Note(note.getValue()))
                .collect(Collectors.toSet());

        return new Person(
            new Name(vCard.getFormattedName().getValue()),
            new Phone(vCard.getTelephoneNumbers().get(0).getText()),
            new Email(vCard.getEmails().get(0).getValue()),
            new Address(vCard.getAddresses().get(0).getStreetAddressFull()),
            categories,
            notes
        );
    }
}
