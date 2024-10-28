package seedu.address.model.reminder;

public class ReminderDescription {
    private String description;

    public ReminderDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReminderDescription // instanceof handles nulls
                && description.equals(((ReminderDescription) other).description)); // state check
    }

    @Override
    public int hashCode() {
        return description.hashCode();
    }

    @Override
    public String toString() {
        return description;
    }
}
