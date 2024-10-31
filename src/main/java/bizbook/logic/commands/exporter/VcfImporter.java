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
import ezvcard.property.Categories;
import ezvcard.property.FormattedName;
import ezvcard.property.Telephone;

/**
 * Represents a class that can import an address book from a VCF file
 */
public class VcfImporter implements Importer {
    public static final String MESSAGE_MISSING_INFORMATION = "A vCard inside the file is missing information needed to "
            + "make a person.";

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

    /**
     * Converts a {@link VCard} to a {@link Person}
     *
     * @throws InvalidFileException when the vCard cannot be converted
     */
    private Person convertToPerson(VCard vCard) throws InvalidFileException {
        validateVCard(vCard);

        PersonVCard personVCard = new PersonVCard(vCard);

        return new Person(
                personVCard.getName(),
                personVCard.getPhone(),
                personVCard.getEmail(),
                personVCard.getAddress(),
                personVCard.getTags(),
                personVCard.getNotes()
        );
    }

    private static class PersonVCard {
        private final VCard vCard;
        PersonVCard(VCard vCard) {
            this.vCard = vCard;
        }

        private void validateInfoNotNull(Object object) throws InvalidFileException {
            if (object == null) {
                throw new InvalidFileException(MESSAGE_MISSING_INFORMATION);
            }
        }

        private void validateNotEmpty(List<?> objects) throws InvalidFileException {
            if (objects.isEmpty()) {
                throw new InvalidFileException(MESSAGE_MISSING_INFORMATION);
            }
        }

        Name getName() throws InvalidFileException {
            FormattedName name = vCard.getFormattedName();
            validateInfoNotNull(name);
            return new Name(name.getValue());
        }

        Phone getPhone() throws InvalidFileException {
            List<Telephone> numbers = vCard.getTelephoneNumbers();
            validateNotEmpty(numbers);
            return new Phone(numbers.get(0).getText());
        }

        Email getEmail() throws InvalidFileException {
            List<ezvcard.property.Email> emails = vCard.getEmails();
            validateNotEmpty(emails);
            return new Email(emails.get(0).getValue());
        }

        Address getAddress() throws InvalidFileException {
            List<ezvcard.property.Address> addresses = vCard.getAddresses();
            validateNotEmpty(addresses);
            return new Address(addresses.get(0).getStreetAddressFull());
        }

        Set<Tag> getTags() {
            Categories categories = vCard.getCategories();
            if (categories == null) {
                return new LinkedHashSet<>();
            }

            return vCard.getCategories().getValues().parallelStream()
                    .map(Tag::new)
                    .collect(Collectors.toSet());
        }

        Set<Note> getNotes() {
            return vCard.getNotes().parallelStream()
                    .map(note -> new Note(note.getValue()))
                    .collect(Collectors.toSet());
        }
    }
}
