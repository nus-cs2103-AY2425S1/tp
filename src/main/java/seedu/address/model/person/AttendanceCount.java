package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class AttendanceCount {
    public Integer count;
    public static final String VALIDATION_REGEX = "\\d+";
    public static final String MESSAGE_CONSTRAINTS =
            "The attendance count is invalid.";



    public AttendanceCount(String count) {
        checkArgument(isValidAttendanceCount(count), MESSAGE_CONSTRAINTS);
        this.count = Integer.parseInt(count);
    }

    public static boolean isValidAttendanceCount(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public AttendanceCount increment() {
        Integer newCount = this.count + 1;
        return new AttendanceCount(newCount.toString());
    }

    public AttendanceCount decrement() {
        assert this.count != 0;
        Integer newCount = this.count - 1;
        return new AttendanceCount(newCount.toString());
    }

    @Override
    public String toString() {
        return String.valueOf(count);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AttendanceCount)) {
            return false;
        }

        AttendanceCount otherAttendenceCount = (AttendanceCount) other;
        return count.equals(otherAttendenceCount.count);
    }

    @Override
    public int hashCode() {
        return count.hashCode();
    }

}
