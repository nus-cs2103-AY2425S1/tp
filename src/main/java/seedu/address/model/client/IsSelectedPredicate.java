package seedu.address.model.client;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;

/**
 * Tests if a {@code Client} has been selected by the user.
 */
public class IsSelectedPredicate implements Predicate<Client> {
    private final Client client;

    /**
     * @param model current state of address book.
     * @param index the index of the client selected.
     */
    public IsSelectedPredicate(Model model, Index index) {
        requireAllNonNull(model, index);
        this.client = model.getFilteredClientList().get(index.getZeroBased());
    }

    @Override
    public boolean test(Client client) {
        return this.client.isSameClient(client);
    }
}
