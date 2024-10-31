package bizbook.logic.commands.exporter;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import bizbook.commons.util.FileUtil;
import bizbook.logic.commands.exporter.exceptions.InvalidFileException;
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
    public static final String MESSAGE_MISSING_INFORMATION = "vCard %s is missing information.";

    // ================ Validation methods ==============================
    private void validateNotEmpty(List<VCard> vCards) throws InvalidFileException {
        requireNonNull(vCards);

        if (vCards.isEmpty()) {
            throw new InvalidFileException(MESSAGE_EMPTY_FILE);
        }
    }

    private void validateVCard(VCard vCard) throws InvalidFileException {
        requireNonNull(vCard);

        if (!vCard.validate(vCard.getVersion()).isEmpty()) {
            throw new InvalidFileException(MESSAGE_INVALID_FORMAT);
        }
    }

    // ================ Import methods ==============================
    @Override
    public AddressBook importAddressBook(Path filePath) throws IOException, InvalidFileException {
        requireNonNull(filePath);

        List<VCard> vCards = parseVCardFromFile(filePath);
        return convertToAddressBook(vCards);
    }

    /**
     * Loads and parses the vCards from a file path.
     *
     * @param filePath path to the VCF file
     * @return a list of vCards
     * @throws IOException when the file could not be loaded
     * @throws InvalidFileException when the file is empty
     */
    private List<VCard> parseVCardFromFile(Path filePath) throws IOException, InvalidFileException {
        String contents = FileUtil.readFromFile(filePath);
        List<VCard> vCards = Ezvcard.parse(contents).all();

        validateNotEmpty(vCards);
        return vCards;
    }

    /**
     * Converts a list of vCards to an AddressBook
     *
     * @param vCards list of vCards
     * @return address book with the vCards
     * @throws InvalidFileException when any vCard cannot be converted
     */
    private AddressBook convertToAddressBook(List<VCard> vCards) throws InvalidFileException {
        requireNonNull(vCards);

        AddressBook addressBook = new AddressBook();
        for (VCard vCard : vCards) {
            addressBook.addPerson(convertToPerson(vCard));
        }

        return addressBook;
    }

    private Person convertToPerson(VCard vCard) throws InvalidFileException {
        validateVCard(vCard);

        Set<Tag> categories = new LinkedHashSet<>();
        if (vCard.getCategories() != null) {
            categories = vCard.getCategories().getValues().parallelStream()
                    .map(Tag::new)
                    .collect(Collectors.toSet());
        }

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
