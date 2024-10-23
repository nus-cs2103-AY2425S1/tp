package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code FavouriteStatus} is {@code FAVOURITE}
 */
public class IsFavouritePredicate implements Predicate<Person> {
    private final FavouriteStatus favouriteStatus;

    public IsFavouritePredicate(FavouriteStatus favouriteStatus) {
        this.favouriteStatus = favouriteStatus;
    }

    @Override
    public boolean test(Person person) {
        return person.getFavouriteStatus().equals(favouriteStatus);
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
