package seedu.address.model.person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.tag.Tag;
import static java.util.Objects.requireNonNull;

/**
 * Tests that a {@code Person}'s arguments matches all the requirements
 */
public class ArgumentPredicate implements Predicate<Person> {
    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final Set<Tag> tagList;
    private final String projectStatus;
    private final String paymentStatus;
    private final String clientStatus;
    private final String deadline;

    public ArgumentPredicate(String name, String phone, String email, String address, Set<Tag>tagList,
                             String projectStatus, String paymentStatus, String clientStatus,
                             String deadline) {
        requireNonNull(name);
        requireNonNull(phone);
        requireNonNull(email);
        requireNonNull(address);
        requireNonNull(tagList);
        requireNonNull(projectStatus);
        requireNonNull(paymentStatus);
        requireNonNull(clientStatus);
        requireNonNull(deadline);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tagList = tagList;
        this.projectStatus = projectStatus;
        this.paymentStatus = paymentStatus;
        this.clientStatus = clientStatus;
        this.deadline = deadline;
    }

    private void addIfPresent(Supplier<String> fieldSupplier, Predicate<Person> predicate,
                              Person person, List<Boolean> validParameters) {
        if (!fieldSupplier.get().isEmpty()) {
            validParameters.add(predicate.test(person));
        }
    }

    private void addIfTagPresent(Set<Tag> tags, Predicate<Person> predicate,
                                 Person person, List<Boolean> validParameters) {
        if (!tags.isEmpty()) {
            validParameters.add(predicate.test(person));
        }
    }

    private List<Boolean> getValidParameters(Person person) {
        List<Boolean> validParameters = new ArrayList<>();

            addIfPresent(() -> name, p -> {
                String[] nameKeywords = name.split("\\s+");
                NameContainsKeywordsPredicate namePredicate = new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords));
                return namePredicate.test(p);
            }, person, validParameters);

            addIfPresent(() -> phone, p -> phone.equals(p.getPhone().toString()), person, validParameters);

            addIfPresent(() -> email, p -> email.equals(p.getEmail().toString()), person, validParameters);

            addIfPresent(() -> address, p -> address.equals(p.getAddress().toString()), person, validParameters);

            addIfPresent(() -> projectStatus, p -> projectStatus.equals(p.getProjectStatus().toString()),
                    person, validParameters);

            addIfPresent(() -> paymentStatus, p -> paymentStatus.equals(p.getPaymentStatus().toString()),
                    person, validParameters);

            addIfPresent(() -> clientStatus, p -> paymentStatus.equals(p.getClientStatus().toString()),
                    person, validParameters);

            addIfPresent(() -> deadline, p -> deadline.equals(p.getDeadline().toString()),
                    person, validParameters);

            addIfTagPresent(tagList, p -> tagList.stream().anyMatch(p.getTags()::contains), person, validParameters);

        return validParameters;
    }

    @Override
    public boolean test(Person person) {
        List<Boolean> validParameters = getValidParameters(person);
        return validParameters.stream().allMatch(Boolean::booleanValue);
    }

//    @Override
//    public boolean equals(Object other) {
//        if (other == this) {
//            return true;
//        }
//
//        // instanceof handles nulls
//        if (!(other instanceof ArgumentPredicate)) {
//            return false;
//        }
//
//        ArgumentPredicate argumentPredicate = (ArgumentPredicate) other;
//        return toFind.equals(argumentPredicate.toFind);
//    }
//
//    @Override
//    public String toString() {
//        return new ToStringBuilder(this).add("person", toFind).toString();
//    }
}
