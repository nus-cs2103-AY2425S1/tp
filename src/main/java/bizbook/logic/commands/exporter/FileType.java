package bizbook.logic.commands.exporter;

import bizbook.model.ReadOnlyUserPrefs;

/**
 * Represents a file type that can be exported
 */
public enum FileType {
    CSV,
    VCF;

    public static final String MESSAGE_CONSTRAINTS = "File type should either be CSV or VCF";
    public static final String MESSAGE_NOT_SUPPORTED = "File type is not supported.";

    /**
     * Creates an {@link Exporter} for this file type
     */
    public Exporter exporter(ReadOnlyUserPrefs userPrefs) {
        return switch (this) {
        case CSV -> new CsvExporter(userPrefs);
        case VCF -> new VcfExporter(userPrefs);
        default -> throw new UnsupportedOperationException(MESSAGE_NOT_SUPPORTED);
        };
    }

    /**
     * Returns true if this file type has an {@link Exporter}
     */
    public boolean hasExporter() {
        return switch (this) {
        case CSV, VCF -> true;
        default -> false;
        };
    }

    /**
     * Creates an {@link Importer} for this file type
     */
    public Importer importer() {
        return switch (this) {
        case VCF -> new VcfImporter();
        default -> throw new UnsupportedOperationException(MESSAGE_NOT_SUPPORTED);
        };
    }

    /**
     * Returns true if this file type has an {@link Importer}
     */
    public boolean hasImporter() {
        return switch (this) {
        case VCF -> true;
        default -> false;
        };
    }
}
