package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;




/**
 * Represents a command to view details of a person.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Use either of the parameters below\n "
            + "Parameters: "
            + PREFIX_NAME + "NAME \n"
            + "c/ " + "CONTACT_NUMBER \n"
            + "r/ " + "ROOM_NUMBER";

    private final ViewPersonDescriptor viewPersonDescriptor;

    /**
     * Creates a ViewCommand with the specified {@code ViewPersonDescriptor}.
     */
    public ViewCommand(ViewPersonDescriptor viewPersonDescriptor) {
        requireNonNull(viewPersonDescriptor);
        this.viewPersonDescriptor = new ViewPersonDescriptor(viewPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        // Searching implementation not implemented yet
        return new CommandResult("view command executed");
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ViewCommand)) {
            return false;
        }

        ViewCommand otherViewCommand = (ViewCommand) other;
        return viewPersonDescriptor.equals(otherViewCommand.viewPersonDescriptor);
    }

    @Override
    public String toString() {
        return "testing view command";
    }

    public ViewPersonDescriptor getViewPersonDescriptor() {
        return viewPersonDescriptor;
    }

    /**
     * Stores the details to view a person.
     */
    public static class ViewPersonDescriptor {
        private Name name;
        private Phone phone;

        public ViewPersonDescriptor() {}

        /**
         * Copy constructor.
         * @param toCopy Descriptor to copy from.
         */
        public ViewPersonDescriptor(ViewCommand.ViewPersonDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
        }

        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof ViewCommand.ViewPersonDescriptor)) {
                return false;
            }

            ViewCommand.ViewPersonDescriptor otherViewPersonDescriptor = (ViewCommand.ViewPersonDescriptor) other;
            return Objects.equals(name, otherViewPersonDescriptor.name)
                    && Objects.equals(phone, otherViewPersonDescriptor.phone);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .toString();
        }
    }
}

// Ensure this comment or any character is followed by a newline at the end of the file.
