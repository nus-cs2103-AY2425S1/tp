package seedu.address.model.person;

import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code FavouriteStatus} is {@code FAVOURITE}
 */
public class IsFavouritePredicate implements Predicate<Person> {
    private final Optional<FavouriteStatus> favouriteStatus;

    public IsFavouritePredicate(Optional<FavouriteStatus> favouriteStatus) {
        this.favouriteStatus = favouriteStatus;
    }

    @Override
    public boolean test(Person person) {
        return favouriteStatus.filter(status -> person.getFavouriteStatus().equals(status)).isPresent();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof IsFavouritePredicate)) {
            return false;
        }

        IsFavouritePredicate otherIsFavouritePredicate = (IsFavouritePredicate) other;
        return favouriteStatus.equals(otherIsFavouritePredicate.favouriteStatus);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", favouriteStatus).toString();
    }
}
