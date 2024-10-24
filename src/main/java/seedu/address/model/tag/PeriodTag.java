package seedu.address.model.tag;

import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Locale;

import seedu.address.model.tag.enums.InternshipPeriod;

/**
 * Package-private class. Represents a tag that indicates the internship period and year.
 * This tag ensures that the provided period is one of the valid options
 * (Summer, Winter, or PARTTIME) and that the year falls between 2000 and 2500.
 */
public class PeriodTag extends Tag {

    /** The message to display when the provided period or year is invalid. */
    public static final String MESSAGE_CONSTRAINTS = "Period needs to be either Summer, Winter, or PARTTIME "
            + "and within the year 2000 and 2500";

    private String period;

    /** The year associated with the internship, constrained between 2000 and 2500. */
    private int year;

    /**
     * Constructs a {@code PeriodTag} with the given tag name, period, and year.
     *
     * @param tagName The name of the tag.
     * @param period The internship period together with year (e.g., "Summer 2000", "Winter 2015", "PARTTIME 2100").
     * @param year The year of internship.
     * @throws IllegalArgumentException if the period is not valid or the year is out of range.
     */
    PeriodTag(String tagName, String period, int year) throws IllegalArgumentException {
        super(tagName);
        checkArgument(isValidPeriod(period, year), MESSAGE_CONSTRAINTS);
        this.period = period.toUpperCase();
        this.year = year;
    }

    /**
     * Help function to check if the given period and year are valid.
     * The period must match one of the valid {@link InternshipPeriod} values,
     * and the year must be between 2000 and 2500.
     *
     * @param period The internship period to validate.
     * @param year The year to validate.
     * @return {@code true} if both the period and year are valid; {@code false} otherwise.
     */
    private static boolean isValidPeriod(String period, int year) {
        try {
            // Attempt to convert the input string to a valid InternshipPeriod enum value.
            InternshipPeriod.valueOf(period.toUpperCase(Locale.ROOT));
            return year >= 2000 && year <= 2500;
        } catch (IllegalArgumentException e) {
            return false; // If the conversion fails, the value is invalid.
        }
    }

    @Override
    public String toString() {
        return "Period:" + this.period + "-" + this.year;
    }
}
