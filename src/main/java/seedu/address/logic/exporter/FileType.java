package seedu.address.logic.exporter;

import seedu.address.model.ReadOnlyUserPrefs;

/**
 * Represents a file type that can be exported
 */
public enum FileType {
    CSV,
    VCF;

    public static final String MESSAGE_CONSTRAINTS = "File type should either be CSV or VCF";

    /**
     * Creates an {@code Exporter} for this file type
     */
    public Exporter export(ReadOnlyUserPrefs userPrefs) {
        return switch (this) {
        case CSV -> new CsvExporter(userPrefs);
        case VCF -> new VcfExporter(userPrefs);
        default -> throw new AssertionError("This should never be called");
        };
    }
}
