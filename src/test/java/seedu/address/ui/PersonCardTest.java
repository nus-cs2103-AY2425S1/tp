package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.DuplicatePhoneTagger;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class PersonCardTest {

    @Test
    public void display_typicalPerson_allFieldsPopulated() {
        Person typicalPerson = ALICE;
        TestPersonCard personCard = new TestPersonCard(typicalPerson, 1);

        assertEquals("1. ", personCard.id);
        assertEquals(typicalPerson.getName().fullName, personCard.name);
        assertEquals(typicalPerson.getPhone().value, personCard.phone);
        assertEquals(typicalPerson.getEmail().value, personCard.email);
        assertEquals(typicalPerson.getAddress().value, personCard.address);
        assertEquals(typicalPerson.getFinancialInfo(), personCard.financialInfo);
        assertEquals(typicalPerson.getSocialMediaHandle(), personCard.socialMediaHandle);
    }

    @Test
    public void display_tagsInAlphabeticalOrder() {
        Person personWithTags = new PersonBuilder(ALICE)
                .withTags("ooo", "aaa", "zzz")
                .build();
        TestPersonCard personCard = new TestPersonCard(personWithTags, 1);

        assertEquals(3, personCard.tags.size());
        assertEquals("aaa", personCard.tags.get(0).text);
        assertEquals("ooo", personCard.tags.get(1).text);
        assertEquals("zzz", personCard.tags.get(2).text);
    }

    @Test
    public void display_tagWithValue() {
        Person personWithTagValue = new PersonBuilder(ALICE)
                .withTags("friends:close")
                .build();
        TestPersonCard personCard = new TestPersonCard(personWithTagValue, 1);

        assertEquals(1, personCard.tags.size());
        assertEquals("friends : close", personCard.tags.get(0).text);
        assertEquals("hasValue", personCard.tags.get(0).styleClass);
    }

    @Test
    public void display_duplicatePhoneTag() {
        Person personWithDuplicatePhone = new PersonBuilder(ALICE)
                .withTags(DuplicatePhoneTagger.DUPLICATE_PHONE_TAG_NAME)
                .build();
        TestPersonCard personCard = new TestPersonCard(personWithDuplicatePhone, 1);

        assertEquals(1, personCard.tags.size());
        assertEquals(DuplicatePhoneTagger.DUPLICATE_PHONE_TAG_NAME, personCard.tags.get(0).text);
        assertEquals("duplicate", personCard.tags.get(0).styleClass);
    }

    /**
     * A test-specific version of {@code PersonCard} that doesn't rely on JavaFX components.
     */
    private static class TestPersonCard {
        public final String id;
        public final String name;
        public final String phone;
        public final String address;
        public final String email;
        public final String financialInfo;
        public final String socialMediaHandle;
        public final List<TestLabel> tags = new ArrayList<>();

        public TestPersonCard(Person person, int displayedIndex) {
            id = displayedIndex + ". ";
            name = person.getName().fullName;
            phone = person.getPhone().value;
            address = person.getAddress().value;
            email = person.getEmail().value;
            financialInfo = person.getFinancialInfo();
            socialMediaHandle = person.getSocialMediaHandle();
            person.getTags().stream()
                    .sorted(Comparator.comparing(tag -> tag.tagName))
                    .forEach(tag -> {
                        String tagText = tag.tagName;
                        String tagValue = tag.tagValue != null ? " : " + tag.tagValue : "";
                        TestLabel label = new TestLabel(tagText + tagValue);
                        if (tag.tagName.equals(DuplicatePhoneTagger.DUPLICATE_PHONE_TAG_NAME)) {
                            label.styleClass = "duplicate";
                        }
                        if (tag.tagValue != null) {
                            label.styleClass = "hasValue";
                        }
                        tags.add(label);
                    });
        }
    }

    private static class TestLabel {
        private String text;
        private String styleClass = "";

        public TestLabel(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }

        public void setText(String newText) {
            this.text = newText;
        }


    }
}
