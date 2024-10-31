package bizbook.logic.commands.exporter;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import bizbook.commons.util.FileUtil;
import bizbook.logic.commands.exporter.exceptions.InvalidAddressBookException;
import bizbook.model.ReadOnlyAddressBook;
import bizbook.model.ReadOnlyUserPrefs;
import bizbook.model.person.Person;
import ezvcard.Ezvcard;
import ezvcard.VCard;
import ezvcard.property.Address;
import ezvcard.property.Categories;

/**
 * Represents a class that can export to VCF
 */
public class VcfExporter implements Exporter {
    private final Path exportSubPath = Paths.get("bizbook.vcf");
    private final ReadOnlyUserPrefs userPrefs;

    /**
     * Constructs a {@code VcfExporter} class configured with {@code ReadOnlyUserPrefs}
     */
    public VcfExporter(ReadOnlyUserPrefs userPrefs) {
        this.userPrefs = userPrefs;
    }

    @Override
    public void exportAddressBook(ReadOnlyAddressBook addressBook) throws IOException, InvalidAddressBookException {
        if (addressBook.getPersonList().isEmpty()) {
            throw new InvalidAddressBookException(MESSAGE_EMPTY_ADDRESS_BOOK);
        }

        List<VCard> vCards = addressBook.getPersonList()
                .parallelStream().map(this::convertToVcf).toList();
        String fileContent = Ezvcard.write(vCards).go();

        Path exportPath = getExportPath();
        FileUtil.createIfMissing(exportPath);
        FileUtil.writeToFile(exportPath, fileContent);
    }

    private VCard convertToVcf(Person person) {
        // Convert attributes to vCard representable properties
        Address address = new Address();
        address.setStreetAddress(person.getAddress().value);

        Categories categories = new Categories();
        person.getTags().forEach(tag -> categories.getValues().add(tag.tagName));

        VCard vCard = new VCard();
        vCard.setFormattedName(person.getName().fullName);
        vCard.addTelephoneNumber(person.getPhone().value);
        vCard.addEmail(person.getEmail().value);
        vCard.addAddress(address);
        vCard.setCategories(categories);
        person.getNotes()
                .forEach(note -> vCard.addNote(note.getNote()));

        return vCard;
    }

    @Override
    public Path getExportPath() {
        return userPrefs.getExportDirectoryPath().resolve(exportSubPath);
    }
}
