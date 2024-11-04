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
     * Creates a {@code PersonPredicateBuilder} with a copy of all internal data from the provided
     * {@code PersonPredicateBuilder}.
     */
    public PersonPredicateBuilder(PersonPredicateBuilder personPredicateBuilder) {
        nameKeywords = new ArrayList<>(personPredicateBuilder.nameKeywords);
        classIdKeywords = new ArrayList<>(personPredicateBuilder.classIdKeywords);
        monthPaidKeywords = new ArrayList<>(personPredicateBuilder.monthPaidKeywords);
        notMonthPaidKeywords = new ArrayList<>(personPredicateBuilder.notMonthPaidKeywords);
        isSetName = personPredicateBuilder.isSetName;
        isSetClassId = personPredicateBuilder.isSetClassId;
        isSetMonthPaid = personPredicateBuilder.isSetMonthPaid;
        isSetNotMonthPaid = personPredicateBuilder.isSetNotMonthPaid;
    }

    /**
     * Adds all strings in {@code nameKeywords} into this object's {@code nameKeywords} field.
     * @return this object
     */
    public PersonPredicateBuilder withNameKeywords(List<String> nameKeywords) {
        requireNonNull(nameKeywords);
        PersonPredicateBuilder newBuilder = new PersonPredicateBuilder(this);
        newBuilder.nameKeywords.addAll(nameKeywords);
        newBuilder.isSetName = true;
        return newBuilder;
    }

    /**
     * Adds all strings in {@code classIdKeywords} into this object's {@code classIdKeywords} field.
     * @return this object
     */
    public PersonPredicateBuilder withClassIdKeywords(List<String> classIdKeywords) {
        requireNonNull(classIdKeywords);
        PersonPredicateBuilder newBuilder = new PersonPredicateBuilder(this);
        newBuilder.classIdKeywords.addAll(classIdKeywords);
        newBuilder.isSetClassId = true;
        return newBuilder;
    }

    /**
     * Adds all strings in {@code monthPaidKeywords} into this object's {@code monthPaidKeywords} field.
     * @return this object
     */
    public PersonPredicateBuilder withMonthPaidKeywords(List<String> monthPaidKeywords) {
        requireNonNull(monthPaidKeywords);
        PersonPredicateBuilder newBuilder = new PersonPredicateBuilder(this);
        newBuilder.monthPaidKeywords.addAll(monthPaidKeywords);
        newBuilder.isSetMonthPaid = true;
        return newBuilder;
    }

    /**
     * Adds all strings in {@code notMonthPaidKeywords} into this object's {@code notMonthPaidKeywords} field.
     * @return this object
     */
    public PersonPredicateBuilder withNotMonthPaidKeywords(List<String> notMonthPaidKeywords) {
        requireNonNull(notMonthPaidKeywords);
        PersonPredicateBuilder newBuilder = new PersonPredicateBuilder(this);
        newBuilder.notMonthPaidKeywords.addAll(notMonthPaidKeywords);
        newBuilder.isSetNotMonthPaid = true;
        return newBuilder;
    }

    /**
     * Converts this object into a {@code Predicate<Person>} object that tests a {@code Person} for all fields set
     * in this object.
     */
    public Predicate<Person> build() {
        // guarantee immutability: copy all internal fields
        List<String> nameKeywordsCopy = new ArrayList<>(nameKeywords);
        List<String> classIdKeywordsCopy = new ArrayList<>(classIdKeywords);
        List<String> monthPaidKeywordsCopy = new ArrayList<>(monthPaidKeywords);
        List<String> notMonthPaidKeywordsCopy = new ArrayList<>(notMonthPaidKeywords);
        boolean isSetNameCopy = isSetName;
        boolean isSetClassIdCopy = isSetClassId;
        boolean isSetMonthPaidCopy = isSetMonthPaid;
        boolean isSetNotMonthPaidCopy = isSetNotMonthPaid;
        return person -> {
            boolean nameMatch = nameContainsKeywords(person, isSetNameCopy, nameKeywordsCopy);
            boolean classIdMatch = classIdContainsKeywords(person, isSetClassIdCopy, classIdKeywordsCopy);
            boolean monthPaidMatch = monthPaidContainsKeywords(person, isSetMonthPaidCopy, monthPaidKeywordsCopy);
            boolean notMonthPaidMatch = notMonthPaidContainsKeywords(
                    person, isSetNotMonthPaidCopy, notMonthPaidKeywordsCopy);

            return nameMatch && classIdMatch && monthPaidMatch && notMonthPaidMatch;
        };
    }

    private static boolean nameContainsKeywords(Person person, boolean isSetName, List<String> nameKeywords) {
        if (!isSetName) {
            return true;
        }
        return nameKeywords.stream()
                .anyMatch(keyword -> regexMatch(person.getName().fullName, keyword));
    }

    private static boolean classIdContainsKeywords(Person person, boolean isSetClassId, List<String> classIdKeywords) {
        if (!isSetClassId) {
            return true;
        }
        return classIdKeywords.stream()
                .anyMatch(keyword -> regexMatch(person.getClassId().value, keyword));
    }

    private static boolean monthPaidContainsKeywords(
            Person person, boolean isSetMonthPaid, List<String> monthPaidKeywords) {
        if (!isSetMonthPaid) {
            return true;
        }
        return monthPaidKeywords.stream()
                .anyMatch(keyword -> regexMatch(person.getMonthsPaid().toString(), keyword));
    }

    private static boolean notMonthPaidContainsKeywords(
            Person person, boolean isSetNotMonthPaid, List<String> notMonthPaidKeywords) {
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
