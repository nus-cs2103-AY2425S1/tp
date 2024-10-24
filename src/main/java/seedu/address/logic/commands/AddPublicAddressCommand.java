package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PUBLIC_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PUBLIC_ADDRESS_LABEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PUBLIC_ADDRESS_NETWORK;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.addresses.Network;
import seedu.address.model.addresses.PublicAddress;
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
    public static final String MESSAGE_DUPLICATE_PUBLIC_ADDRESS =
            "Invalid: person %1$s already has label %2$s under the network %3$s!\n"
                    + "You may either:\n"
                    + "1. Use another label for the new public address\n"
                    + "2. Edit the existing public address for the current label"
                    + "(overwrite) using the editpa command\n";

    /**
     * Adds a public address to the person identified by the index number
     * using the editPersonDescriptor
     *
     * @param index
     * @param editPersonDescriptor
     */
    public AddPublicAddressCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        super(index, editPersonDescriptor);
    }

    private static Person mergePersons(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());

        Map<Network, Set<PublicAddress>> currentPublicAddresses = new HashMap<>(personToEdit.getPublicAddresses());
        Map<Network, Set<PublicAddress>> addedPublicAddresses =
                editPersonDescriptor.getPublicAddresses().orElse(Map.of());

        // even though this is a user input, checks should be done in the corresponding parser
        assert addedPublicAddresses.size() == 1 : "Only one network should be added at a time";
        assert addedPublicAddresses.values().toArray()[0] != null : "Public address should not be null";

        // it is okay to suppress warnings here because editPersonDescriptor.getPublicAddresses() will always return
        // a map with values of type Set<PublicAddress>
        @SuppressWarnings("unchecked")
        Set<PublicAddress> firstSet = (Set<PublicAddress>) addedPublicAddresses.values().toArray()[0];
        assert firstSet.size() == 1 : "Only one public address should be added at a time";

        Network network = (Network) addedPublicAddresses.keySet().toArray()[0];
        PublicAddress publicAddress = firstSet.iterator().next();

        // add network and public address to currentPubicAddresses
        Set<PublicAddress> publicAddresses = new HashSet<>(currentPublicAddresses.getOrDefault(network, Set.of()));
        if (publicAddresses.contains(publicAddress)) {
            throw new IllegalArgumentException(String.format(MESSAGE_DUPLICATE_PUBLIC_ADDRESS,
                    updatedName, publicAddress, network));
        }
        publicAddresses.add(publicAddress);
        currentPublicAddresses.put(network, publicAddresses);

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, currentPublicAddresses, updatedTags);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        try {
            return super.execute(
                    model,
                    AddPublicAddressCommand::mergePersons,
                    MESSAGE_ADDPA_SUCCESS
            ); // TODO: Update success message
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
        if (!(other instanceof AddPublicAddressCommand)) {
            return false;
        }

        AddPublicAddressCommand otherAddCommand = (AddPublicAddressCommand) other;
        return super.equals(otherAddCommand);
    }
}
