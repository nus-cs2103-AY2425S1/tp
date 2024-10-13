package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.student.PresentDates;
import seedu.address.model.tut.TutDate;

/**
 * Jackson-friendly version of {@link PresentDates}.
 */
class JsonAdaptedPresentDates {

    private final List<JsonAdaptedTutDate> dates = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPresentDates} with the given dates.
     */
    @JsonCreator
    public JsonAdaptedPresentDates(@JsonProperty("dates") List<JsonAdaptedTutDate> dates) {
        if (dates != null) {
            this.dates.addAll(dates);
        }
    }

    /**
     * Converts a given {@code PresentDates} into this class for Jackson use.
     */
    public JsonAdaptedPresentDates(PresentDates source) {
        dates.addAll(source.getList().stream()
                .map(JsonAdaptedTutDate::new)
                .toList());
    }

    /**
     * Converts this Jackson-friendly adapted object into the model's {@code PresentDates} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public PresentDates toModelType() throws IllegalValueException {
        List<TutDate> modelDates = new ArrayList<>();
        for (JsonAdaptedTutDate jsonDate : dates) {
            modelDates.add(jsonDate.toModelType());
        }
        return new PresentDates(new ArrayList<>(modelDates));
    }
}
