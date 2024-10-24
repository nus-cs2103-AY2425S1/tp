package seedu.address.logic.exporter;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import ezvcard.VCard;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

/**
 * Represents a class that can export to VCF
 */
public class VcfExporter implements Exporter {
    private final Path exportSubPath = Paths.get("addressbook.vcf");
    private final UserPrefs userPrefs;

    /**
     * Constructs a {@code VcfExporter} class configured with {@code UserPrefs}
     */
    public VcfExporter(UserPrefs userPrefs) {
        this.userPrefs = userPrefs;
    }

    @Override
    public void exportAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        String fileContent = addressBook.getPersonList()
                .parallelStream().map(this::convertToVcf).collect(Collectors.joining());

        Path exportPath = getExportPath();
        FileUtil.createIfMissing(exportPath);
        FileUtil.writeToFile(exportPath, fileContent);
    }

    private String convertToVcf(Person person) {
        VCard vCard = new VCard();
        // TODO
        return vCard.toString();
    }

    @Override
    public Path getExportPath() {
        return userPrefs.getExportDirectoryPath().resolve(exportSubPath);
    }
}
