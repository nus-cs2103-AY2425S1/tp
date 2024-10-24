package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.FilterPredicate;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Module;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Filters and list out all person in address book whose details match multiple specified conditions.
 * Matching process is case-insensitive and utilizes the provided keywords to filer result.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";
    public static final String MESSAGE_NOT_FILTERED = "At least one field to filter must be provided.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filter all persons whose details contain all "
            + "the specified keywords (case-insensitive) as condition and display them as  a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " n/joe m/MA1521";

    private final FilterPersonDescriptor filterPersonDescriptor;
    private final FilterPredicate predicate;

    /**
     * @param filterPersonDescriptor details to filter thr person with
     */
    public FilterCommand(FilterPersonDescriptor filterPersonDescriptor) {
        requireNonNull(filterPersonDescriptor);

        this.filterPersonDescriptor = filterPersonDescriptor;
        this.predicate = new FilterPredicate(filterPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FilterCommand)) {
            return false;
        }

        FilterCommand otherFilterCommand = (FilterCommand) other;
        return filterPersonDescriptor.equals(otherFilterCommand.filterPersonDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("filterPersonDescriptor", filterPersonDescriptor)
                .toString();
    }

    /**
     * Stores the details to filter the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class FilterPersonDescriptor {
        private Name name;
        private Phone phone;
        private Gender gender;
        private Set<Module> modules;
        private Set<Tag> tags;

        public FilterPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public FilterPersonDescriptor(FilterPersonDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setGender(toCopy.gender);
            setModules(toCopy.modules);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldFiltered() {
            return CollectionUtil.isAnyNonNull(name, phone, gender, modules, tags);
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

        public void setModules(Set<Module> modules) {
            this.modules = (modules != null) ? new HashSet<>(modules) : null;
        }

        public Optional<Set<Module>> getModules() {
            return (modules != null) ? Optional.of(Collections.unmodifiableSet(modules)) : Optional.empty();
        }

        public void setGender(Gender gender) {
            this.gender = gender;
        }

        public Optional<Gender> getGender() {
            return Optional.ofNullable(gender);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof FilterPersonDescriptor)) {
                return false;
            }

            FilterPersonDescriptor otherFilterPersonDescriptor = (FilterPersonDescriptor) other;
            return Objects.equals(name, otherFilterPersonDescriptor.name)
                    && Objects.equals(phone, otherFilterPersonDescriptor.phone)
                    && Objects.equals(gender, otherFilterPersonDescriptor.gender)
                    && Objects.equals(modules, otherFilterPersonDescriptor.modules)
                    && Objects.equals(tags, otherFilterPersonDescriptor.tags);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("gender", gender)
                    .add("modules", modules)
                    .add("tags", tags)
                    .toString();
        }
    }
}
