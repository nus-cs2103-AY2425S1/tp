package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;


import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.*;
import seedu.address.model.tag.Tag;

import java.util.*;

/**
 * Adds a person to the address book.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Useing either of the parameter blow \n "
            + "Parameters: "
            + PREFIX_NAME + "NAME \n"
            + "c/ " + "CONTACT_NUMBER \n"
            + "r/ " + "ROOM_NUMBER";


    /**
     * Creates an AddCommand to add the specified {@code Person}
     */


    public ViewCommand(Person person) {
        requireNonNull(person);

    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        /*
        searching implementation not implemented yet
         */
        return new CommandResult("view command executed");
    }


    public static class ViewPersonDescriptor {
        private Name name;
        private Phone phone;
        // room number currently missing

        public ViewPersonDescriptor() {}


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

            // instanceof handles nulls
            if (!(other instanceof EditCommand.EditPersonDescriptor)) {
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
