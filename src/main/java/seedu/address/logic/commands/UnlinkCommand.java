package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TO;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.link.Link;
import seedu.address.model.owner.Owner;
import seedu.address.model.pet.Pet;

/**
 * Unlinks a pet from an owner in PawPatrol.
 */
public class UnlinkCommand extends Command {
    public static final String COMMAND_WORD = "unlink";

    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Unlinks a owner from a pet. "
                    + "Usage: " + COMMAND_WORD + " OWNER_INDEX "
                    + "[" + PREFIX_TO + "PET_INDEX]...\n"
                    + "Example: " + COMMAND_WORD + " o12 "
                    + PREFIX_TO + "p1, "
                    + COMMAND_WORD + " o13 "
                    + PREFIX_TO + "p3 "
                    + PREFIX_TO + "p2";

    public static final String MESSAGE_SUCCESS = "Unlinked %1$s pet(s) from %2$s";
    public static final String MESSAGE_LINK_NOT_FOUND =
            "This link does not exist in the address book";

    private final Index ownerIndex;
    private final Set<Index> petIndexes;

    /**
     * Creates an UnlinkCommand to unlink the specified {@code Linkable} entities.
     */
    public UnlinkCommand(Index ownerIndex, Set<Index> petIndexes) {
        requireAllNonNull(ownerIndex, petIndexes);
        if (petIndexes.size() == 0) {
            throw new IllegalArgumentException("At least one pet index must be provided");
        }
        this.ownerIndex = ownerIndex;
        this.petIndexes = petIndexes;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Get last shown lists
        List<Owner> ownerList = model.getFilteredOwnerList();
        List<Pet> petList = model.getFilteredPetList();

        if (ownerIndex.getZeroBased() >= ownerList.size()) {
            throw new CommandException(
                    Messages.MESSAGE_INVALID_OWNER_DISPLAYED_INDEX
            );
        }

        Owner owner = ownerList.get(ownerIndex.getZeroBased());

        // Validate all unlink requests first
        Set<Link> validatedLinks = getValidatedLinks(model, owner, petList);

        // Remove all links
        validatedLinks.forEach(model::deleteLink);

        return new CommandResult(
                String.format(MESSAGE_SUCCESS, validatedLinks.size(), Messages.format(owner))
        );
    }

    private Set<Link> getValidatedLinks(Model model, Owner owner, List<Pet> petList) throws CommandException {
        Set<Link> links = new HashSet<>();
        Iterator<Index> petIndexIterator = petIndexes.iterator();
        while (petIndexIterator.hasNext()) {
            Index petIndex = petIndexIterator.next();
            if (petIndex.getZeroBased() >= petList.size()) {
                throw new CommandException(
                        Messages.MESSAGE_INVALID_PET_DISPLAYED_INDEX
                );
            }

            Link link = new Link(owner, petList.get(petIndex.getZeroBased()));
            if (!model.hasLink(link)) {
                throw new CommandException(MESSAGE_LINK_NOT_FOUND);
            }
            links.add(link);
        }
        return links;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnlinkCommand)) {
            return false;
        }

        UnlinkCommand otherUnlinkCommand = (UnlinkCommand) other;
        return otherUnlinkCommand.ownerIndex.equals(ownerIndex)
                && otherUnlinkCommand.petIndexes.equals(this.petIndexes);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("ownerIndex", ownerIndex.getOneBased())
                .add("petIndexes", Arrays.toString(petIndexes.stream().map(Index::getOneBased).toArray()))
                .toString();
    }
}
