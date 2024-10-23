package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.MonthPaid;

/**
 * Jackson-friendly version of {@link MonthPaid}.
 */
class JsonAdaptedMonthPaid {

    private final String monthPaidValue;

    /**
     * Constructs a {@code JsonAdaptedTag} with the given {@code monthPaidValue}.
     */
    @JsonCreator
    public JsonAdaptedMonthPaid(String monthPaidValue) {
        this.monthPaidValue = monthPaidValue;
    }

    /**
     * Converts a given {@code MonthPaid} into this class for Jackson use.
     */
    public JsonAdaptedMonthPaid(MonthPaid source) {
        monthPaidValue = source.monthPaidValue;
    }

    @JsonValue
    public String getMonthPaidValue() {
        return monthPaidValue;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code MonthPaid} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public MonthPaid toModelType() throws IllegalValueException {
        if (!MonthPaid.isValidMonthPaid(monthPaidValue)) {
            throw new IllegalValueException(MonthPaid.MESSAGE_CONSTRAINTS);
        }
        return new MonthPaid(monthPaidValue);
    }

}
