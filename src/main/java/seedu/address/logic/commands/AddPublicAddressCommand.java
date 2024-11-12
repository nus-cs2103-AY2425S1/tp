package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PUBLIC_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PUBLIC_ADDRESS_LABEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PUBLIC_ADDRESS_NETWORK;
import static seedu.address.model.addresses.PublicAddressesComposition.MESSAGE_DUPLICATE_PUBLIC_ADDRESS;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.addresses.PublicAddress;
import seedu.address.model.addresses.PublicAddressesComposition;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Add a public address to the person identified by the index number
 * used in the displayed person list with the corresponding network and label
 */
public class AddPublicAddressCommand extends AbstractEditCommand {
    public static final String COMMAND_WORD = "addpa"; // short for add public address

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Adds the person's public address identified by the index number "
        + "used in the displayed person list and their crypto network.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + PREFIX_PUBLIC_ADDRESS_NETWORK + "NETWORK\n"
        + PREFIX_PUBLIC_ADDRESS_LABEL + "LABEL\n"
        + PREFIX_PUBLIC_ADDRESS + "ADDRESS\n"
        + "Example: " + COMMAND_WORD + " 1 " + PREFIX_PUBLIC_ADDRESS_NETWORK + "BTC " + PREFIX_PUBLIC_ADDRESS_LABEL
        + "wallet1 " + PREFIX_PUBLIC_ADDRESS + "1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa";

    public static final String MESSAGE_ADDPA_SUCCESS = "Added Person's Public Address: %1$s";

    private EditPersonDescriptor editPersonDescriptor;

    /**
     * Adds a public address to the person identified by the index number
     * using the editPersonDescriptor
     *
     * @param index
     * @param editPersonDescriptor
     */
    public AddPublicAddressCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        super(index, editPersonDescriptor);
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    private static Person mergePersons(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());

        PublicAddressesComposition currentPublicAddresses = personToEdit.getPublicAddressesComposition();
        PublicAddressesComposition addedPublicAddresses =
            editPersonDescriptor.getPublicAddresses().orElse(new PublicAddressesComposition());
        try {
            PublicAddressesComposition combinedPublicAddresses =
                currentPublicAddresses.combineWith(addedPublicAddresses);
            return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, combinedPublicAddresses,
                updatedTags);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(String.format(MESSAGE_DUPLICATE_PUBLIC_ADDRESS, e.getMessage()));
        }

    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        // check if the public address already exists

        String paToAdd = editPersonDescriptor.getPublicAddresses()
                .map(PublicAddressesComposition::getOnePublicAddress)
                .get()
                .getPublicAddressString();

        List<String> allPAs = model.getAddressBook().getPersonList().stream()
                .flatMap(person -> person.getPublicAddressesComposition().getAllPublicAddresses().stream())
                .map(PublicAddress::getPublicAddressString)
                .toList();

        for (String pa : allPAs) {
            if (paToAdd.equals(pa)) {
                throw new CommandException(String.format(MESSAGE_DUPLICATE_PUBLIC_ADDRESS, paToAdd));
            }
        }

        try {
            return super.execute(
                model,
                AddPublicAddressCommand::mergePersons,
                MESSAGE_ADDPA_SUCCESS
            );
        } catch (IllegalArgumentException e) {
            throw new CommandException(e.getMessage());
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddPublicAddressCommand otherAddCommand)) {
            return false;
        }

        return super.equals(otherAddCommand);
    }
}
