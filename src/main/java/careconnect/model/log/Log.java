package careconnect.model.log;

import java.util.Date;
import java.util.Objects;

import careconnect.commons.util.AppUtil;
import careconnect.commons.util.CollectionUtil;
import careconnect.commons.util.ToStringBuilder;

/**
 * Represents a Log of a Person in the address book.
 * Guarantees: immutable; remark is valid as declared in {@link #isValidLogRemark(String)}
 */
public class Log {
    public static final String MESSAGE_CONSTRAINTS = "Log remark can take any values, and it "
            + "should not be blank."
            + "Log date should be in the format yyyy-MM-dd HH:mm, if provided.";

    /*
     * The first character of the log must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    private final Date date;
    private final String remark;

    /**
     * Constructs a {@code Log} with today's date.
     *
     * @param remark A valid log remark.
     */
    public Log(String remark) {
        CollectionUtil.requireAllNonNull(remark);
        this.date = new Date();
        AppUtil.checkArgument(isValidLogRemark(remark), MESSAGE_CONSTRAINTS);
        this.remark = remark;
    }

    /**
     * Constructs a {@code Log}.
     *
     * @param date   A valid log date.
     * @param remark A valid log remark.
     */
    public Log(Date date, String remark) {
        CollectionUtil.requireAllNonNull(remark);
        this.date = date;
        AppUtil.checkArgument(isValidLogRemark(remark), MESSAGE_CONSTRAINTS);
        this.remark = remark;
    }

    /**
     * Returns true if a given string is a valid log remark.
     */
    public static boolean isValidLogRemark(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public Date getDate() {
        return date;
    }

    public String getRemark() {
        return remark;
    }

    /**
     * Returns true if both logs have the same remark and date.
     * This defines a stronger notion of equality between two logs.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Log otherLog)) {
            return false;
        }

        return this.remark.equals(otherLog.remark)
                && this.date.equals(otherLog.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.date, this.remark);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("date", this.date)
                .add("remark", this.remark)
                .toString();
    }

}
