package seedu.address.logic.commands;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.meetup.MeetUp;
import seedu.address.model.person.Person;

import java.util.Optional;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEETUP_INFO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;


/**
 * Edits the details of an existing meetup in the address book.
 */
public class EditMeetupCommand extends Command {

    public static final String COMMAND_WORD = "edit_meetup";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the meetup identified "
            + "by the name used in the address book. "
            + "Existing meetup will be overwritten by the input meetup.\n"
            + "Parameters: NAME (must exist in address book)\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_MEETUP_INFO + "Meetup with John Doe at Subway on Sunday 3pm.";

    public static final String MESSAGE_EDIT_MEETUP_SUCCESS = "Edited Meetup: %1$s";
    public static final String MESSAGE_MEETUP_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_PERSON_NOT_FOUND = "This person cannot be found in the address book.";

    private final Person person;
    private final EditMeetUpDescriptor editMeetUpDescriptor;

    /**
     * @param editMeetUpDescriptor details to edit the person with
     */
    public EditMeetupCommand(Person person, EditMeetupCommand.EditMeetUpDescriptor editMeetUpDescriptor) {
        requireNonNull(person);
        requireNonNull(editMeetUpDescriptor);

        this.person = person;
        this.editMeetUpDescriptor = new EditMeetupCommand.EditMeetUpDescriptor(editMeetUpDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return null;
    }



    /**
     * Stores the details to edit the meetup with. Each non-empty field value will replace the
     * corresponding field value of the meetup.
     */
    public static class EditMeetUpDescriptor {
        private Person person;
        private MeetUp meetup;

        public EditMeetUpDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditMeetUpDescriptor(EditMeetupCommand.EditMeetUpDescriptor toCopy) {
            setPerson(toCopy.person);

        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(meetup);
        }

        public void setPerson(Person person) {
            this.person = person;
        }

        public Optional<Person> getPerson() {
            return Optional.ofNullable(person);
        }

        public void setMeetUp(MeetUp meetup) {
            this.meetup = meetup;
        }

        public Optional<MeetUp> getMeetUp() {
            return Optional.ofNullable(meetup);
        }


    }
}

