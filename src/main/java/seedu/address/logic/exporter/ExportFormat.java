package seedu.address.logic.exporter;

import seedu.address.model.ReadOnlyAddressBook;

import java.nio.file.Path;

public interface ExportFormat {
    public void exportAddressBook(ReadOnlyAddressBook addressBook);

    public Path getExportPath();
}
