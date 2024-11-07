package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.model.scheme.Scheme;

/**
 * Jackson-friendly version of {@link Scheme}.
 */
public class JsonAdaptedScheme {
    private final String schemeName;

    /**
     * Constructs a {@code JsonAdaptedScheme} with the given {@code schemeName}.
     */
    @JsonCreator
    public JsonAdaptedScheme(String schemeName) {
        this.schemeName = schemeName;
    }

    public JsonAdaptedScheme(Scheme source) {
        schemeName = source.getSchemeName();
    }

    @JsonValue
    public String getSchemeName() {
        return schemeName;
    }

    public Scheme toModelType() {
        return Scheme.createScheme(schemeName);
    }


}
