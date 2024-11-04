package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.isCollectionEqual;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Builder for creating predicates of {@code Person}.
 */
public class PersonPredicateBuilder {

    private final List<String> nameKeywords;
    private final List<String> classIdKeywords;
    private final List<String> monthPaidKeywords;
    private final List<String> notMonthPaidKeywords;
    private boolean isSetName;
    private boolean isSetClassId;
    private boolean isSetMonthPaid;
    private boolean isSetNotMonthPaid;

    /**
     * Creates an empty {@code PersonPredicateBuilder}.
     */
    public PersonPredicateBuilder() {
        nameKeywords = new ArrayList<>();
        classIdKeywords = new ArrayList<>();
        monthPaidKeywords = new ArrayList<>();
        notMonthPaidKeywords = new ArrayList<>();
        isSetName = false;
        isSetClassId = false;
        isSetMonthPaid = false;
        isSetNotMonthPaid = false;
    }

    /**
     * Adds all strings in {@code nameKeywords} into this object's {@code nameKeywords} field.
     * @return this object
     */
    public PersonPredicateBuilder withNameKeywords(List<String> nameKeywords) {
        requireNonNull(nameKeywords);
        this.nameKeywords.addAll(nameKeywords);
        isSetName = true;
        return this;
    }

    /**
     * Adds all strings in {@code classIdKeywords} into this object's {@code classIdKeywords} field.
     * @return this object
     */
    public PersonPredicateBuilder withClassIdKeywords(List<String> classIdKeywords) {
        requireNonNull(classIdKeywords);
        this.classIdKeywords.addAll(classIdKeywords);
        isSetClassId = true;
        return this;
    }

    /**
     * Adds all strings in {@code monthPaidKeywords} into this object's {@code monthPaidKeywords} field.
     * @return this object
     */
    public PersonPredicateBuilder withMonthPaidKeywords(List<String> monthPaidKeywords) {
        requireNonNull(monthPaidKeywords);
        this.monthPaidKeywords.addAll(monthPaidKeywords);
        isSetMonthPaid = true;
        return this;
    }

    /**
     * Adds all strings in {@code notMonthPaidKeywords} into this object's {@code notMonthPaidKeywords} field.
     * @return this object
     */
    public PersonPredicateBuilder withNotMonthPaidKeywords(List<String> notMonthPaidKeywords) {
        requireNonNull(notMonthPaidKeywords);
        this.notMonthPaidKeywords.addAll(notMonthPaidKeywords);
        isSetNotMonthPaid = true;
        return this;
    }

    /**
     * Converts this object into a {@code Predicate<Person>} object that tests a {@code Person} for all fields set
     * in this object.
     */
    public Predicate<Person> build() {
        return person -> {
            boolean nameMatch = nameContainsKeywords(person);
            boolean classIdMatch = classIdContainsKeywords(person);
            boolean monthPaidMatch = monthPaidContainsKeywords(person);
            boolean notMonthPaidMatch = notMonthPaidContainsKeywords(person);

            return nameMatch && classIdMatch && monthPaidMatch && notMonthPaidMatch;
        };
    }

    private boolean nameContainsKeywords(Person person) {
        if (!isSetName) {
            return true;
        }
        return nameKeywords.stream()
                .anyMatch(keyword -> regexMatch(person.getName().fullName, keyword));
    }

    private boolean classIdContainsKeywords(Person person) {
        if (!isSetClassId) {
            return true;
        }
        return classIdKeywords.stream()
                .anyMatch(keyword -> regexMatch(person.getClassId().value, keyword));
    }

    private boolean monthPaidContainsKeywords(Person person) {
        if (!isSetMonthPaid) {
            return true;
        }
        return monthPaidKeywords.stream()
                .anyMatch(keyword -> regexMatch(person.getMonthsPaid().toString(), keyword));
    }

    private boolean notMonthPaidContainsKeywords(Person person) {
        if (!isSetNotMonthPaid) {
            return true;
        }
        return notMonthPaidKeywords.stream()
                .noneMatch(keyword -> regexMatch(person.getMonthsPaid().toString(), keyword));
    }

    private static boolean regexMatch(String value, String keyword) {
        Pattern pattern = Pattern.compile(keyword, Pattern.CASE_INSENSITIVE);
        return pattern.matcher(value).find();
    }

    /**
     * Returns true if no {@code with} functions of this object has been executed yet.
     */
    public boolean isEmpty() {
        return !isSetName && !isSetClassId && !isSetMonthPaid && !isSetNotMonthPaid;
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonPredicateBuilder)) {
            return false;
        }

        PersonPredicateBuilder otherPersonPredicateBuilder = (PersonPredicateBuilder) other;
        return isCollectionEqual(nameKeywords, otherPersonPredicateBuilder.nameKeywords)
                && isCollectionEqual(classIdKeywords, otherPersonPredicateBuilder.classIdKeywords)
                && isCollectionEqual(monthPaidKeywords, otherPersonPredicateBuilder.monthPaidKeywords)
                && isCollectionEqual(notMonthPaidKeywords, otherPersonPredicateBuilder.notMonthPaidKeywords)
                && isSetName == otherPersonPredicateBuilder.isSetName
                && isSetClassId == otherPersonPredicateBuilder.isSetClassId
                && isSetMonthPaid == otherPersonPredicateBuilder.isSetMonthPaid
                && isSetNotMonthPaid == otherPersonPredicateBuilder.isSetNotMonthPaid;
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("nameKeywords", nameKeywords)
                .add("classIdKeywords", classIdKeywords)
                .add("monthPaidKeywords", monthPaidKeywords)
                .add("notMonthPaidKeywords", notMonthPaidKeywords)
                .add("isSetName", isSetName)
                .add("isSetClassId", isSetClassId)
                .add("isSetMonthPaid", isSetMonthPaid)
                .add("isSetNotMonthPaid", isSetNotMonthPaid)
                .toString();
    }
}
