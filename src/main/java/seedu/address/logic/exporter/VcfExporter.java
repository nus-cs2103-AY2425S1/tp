package seedu.address.logic.exporter;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import ezvcard.Ezvcard;
import ezvcard.VCard;
import ezvcard.property.Address;
import ezvcard.property.Categories;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

/**
 * Represents a class that can export to VCF
 */
public class VcfExporter implements Exporter {
    private final Path exportSubPath = Paths.get("bizbook.vcf");
    private final UserPrefs userPrefs;

    /**
     * Constructs a {@code VcfExporter} class configured with {@code UserPrefs}
     */
    public VcfExporter(UserPrefs userPrefs) {
        this.userPrefs = userPrefs;
    }

    @Override
    public void exportAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
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
