package seedu.address.logic.commands.reminder;
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsDeletePredicate;
import seedu.address.model.person.Person;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.ReminderDescription;

/**
 * Edits a reminder in the address book.
 */
public class EditReminderCommand extends Command {
    public static final String COMMAND_WORD = "redit"; // reminder edit
    public static final String COMMAND_WORD_SHORT = "re"; // reminder edit

    public static final String MESSAGE_USAGE = COMMAND_WORD + " or " + COMMAND_WORD_SHORT
            + ": Edits a reminder in Client Hub identified "
            + "by the index number displayed in the reminder list.\n"
            + "Parameters: INDEX "
            + "[" + PREFIX_DATE_TIME + "DATE_TIME] "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION]\n"
            + "Examples:\n"
            + COMMAND_WORD + " 1 " + PREFIX_DATE_TIME + "2022-01-01 00:00 "
            + PREFIX_DESCRIPTION + "New Year's\n"
            + COMMAND_WORD_SHORT + " 1 " + PREFIX_DATE_TIME + "2022-01-01 00:00 "
            + PREFIX_DESCRIPTION + "New Year's\n"
            + "Additional Info:\n"
            + "- " + "INDEX must be a positive integer.\n"
            + "- " + "Existing values will be overwritten by the input values.";

    public static final String MESSAGE_EDIT_REMINDER_SUCCESS = "Edited reminder %1$s";
    public static final String MESSAGE_REMINDER_NOT_EDITED = "At least one field must be edited";

    private final Index index;
    private final EditReminderFields editReminderFields;

    /**
     * @param index of the reminder in the reminder list to edit
     * @param editReminderFields details of the edited reminder
     */
    public EditReminderCommand(Index index, EditReminderFields editReminderFields) {
        requireNonNull(index);
        requireNonNull(editReminderFields);

        this.index = index;
        this.editReminderFields = new EditReminderFields(editReminderFields);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Reminder> lastShownList = model.getDisplayReminders();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX);
        }

        Reminder reminderToEdit = lastShownList.get(index.getZeroBased());
        Reminder editedReminder = createEditedReminder(reminderToEdit, editReminderFields);

        if (reminderToEdit.isSameReminder(editedReminder)
                || !editReminderFields.isAnyFieldEdited()) {
            throw new CommandException(MESSAGE_REMINDER_NOT_EDITED);
        }

        // Parse input using the NameContainsKeywordsPredicate
        String fullName = reminderToEdit.getPersonName() + "$";
        String[] nameKeywords = fullName.split("\\s+");
        NameContainsKeywordsDeletePredicate predicate = new NameContainsKeywordsDeletePredicate(
                List.of(nameKeywords));
        ObservableList<Person> persons = model.getClientHub().getPersonList();
        List<Person> matchingPersons = persons.filtered(predicate);

        // Check if there is exactly one match
        if (matchingPersons.size() == 1) {
            Person person = matchingPersons.get(0);
            person.deleteReminder(reminderToEdit);
            person.addReminder(editedReminder);
            model.setReminder(reminderToEdit, editedReminder);
            model.updateFilteredReminderList();
            return new CommandResult(String.format(MESSAGE_EDIT_REMINDER_SUCCESS, Messages.format(editedReminder)));
        } else {
            throw new CommandException("More than one person with the specified name found. Please be more specific.");
        }
    }

    /**
     * Creates and returns an edited a {@code Reminder} with the details of {@code reminderToEdit}
     * edited with the details of {@code editReminderFields}.
     */
    public static Reminder createEditedReminder(Reminder reminderToEdit, EditReminderFields editReminderFields) {
        assert reminderToEdit != null;

        // EditReminder does not allow editing of personName
        String personName = reminderToEdit.getPersonName();
        LocalDateTime updatedDateTime = editReminderFields.getDateTime().orElse(reminderToEdit.getDateTime());
        ReminderDescription updatedDescription = editReminderFields.getDescription()
                .orElse(reminderToEdit.getDescription());

        return new Reminder(personName, updatedDateTime, updatedDescription);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof EditReminderCommand; // instanceof handles nulls
    }

    @Override
    public String toString() {
        return "EditReminderCommand";
    }

    /**
     * Stores the details to edit the reminder with. Each non-empty field value will replace the
     * corresponding field value of the reminder.
     */
    public static class EditReminderFields {
        private LocalDateTime dateTime;
        private ReminderDescription description;

        /**
         * Default constructor for {@code EditReminderFields}.
         * Initializes an instance with no specified fields.
         */
        public EditReminderFields() {}

        /**
         * Copy constructor for {@code EditReminderFields}.
         * Creates a new instance by copying the fields from the specified {@code EditReminderFields} instance.
         *
         * @param toCopy The {@code EditReminderFields} instance to copy from. Must not be {@code null}.
         */
        public EditReminderFields(EditReminderFields toCopy) {
            setDateTime(toCopy.dateTime);
            setDescription(toCopy.description);
        }

        /**
         * @return true if any field is edited
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(dateTime, description);
        }

        public void setDateTime(LocalDateTime dateTime) {
            this.dateTime = dateTime;
        }

        public Optional<LocalDateTime> getDateTime() {
            return Optional.ofNullable(dateTime);
        }
        public void setDescription(ReminderDescription description) {
            this.description = description;
        }

        public Optional<ReminderDescription> getDescription() {
            return Optional.ofNullable(description);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this || other instanceof EditReminderFields) {
                return true;
            }

            EditReminderFields otherEditReminderFields = (EditReminderFields) other;
            return Objects.equals(dateTime, otherEditReminderFields.dateTime)
                    && Objects.equals(description, otherEditReminderFields.description);
        }

        @Override
        public String toString() {
            return "EditReminderFields";
        }
    }
}
