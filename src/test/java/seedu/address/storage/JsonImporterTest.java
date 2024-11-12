package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.ContactType;
import seedu.address.model.person.Email;
import seedu.address.model.person.ModuleName;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.TelegramHandle;
import seedu.address.storage.exceptions.ImporterException;

public class JsonImporterTest {
    private final File validInput = new File(
            "src/test/data/ImporterTestUtil/ImporterValidCase/2101TAs - Base sheet.json"
    );

    private Optional<Phone> validPhone = Optional.of(new Phone("97659965"));
    private Optional<Email> validEmail = Optional.of(new Email("akira1975@gmail.com"));
    private Optional<TelegramHandle> validHandle = Optional.of(new TelegramHandle("@akira_ra"));
    private Optional<ModuleName> validMod = Optional.of(new ModuleName("CS2101"));
    private Name validName = new Name("Akira");
    private ContactType validContactType = new ContactType("work");

    @Test
    public void jsonImporter_validInput_success() {
        List<File> testList = new ArrayList<>();
        testList.add(validInput);
        Model testModel = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.addPerson(
                new Person(
                        validContactType,
                        validName,
                        validPhone,
                        validEmail,
                        validHandle,
                        validMod,
                        Optional.empty(),
                        new HashSet<>()
                )
        );

        JsonImporter testImporter = new JsonImporter(testList);

        try {
            testImporter.importAllJsonFiles(testModel);
            assertEquals(expectedModel, testModel);
        } catch (ImporterException e) {
            fail();
        }
    }





}
