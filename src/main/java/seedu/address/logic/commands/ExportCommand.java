package seedu.address.logic.commands;

import seedu.address.model.Model;
import seedu.address.model.person.Person;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ExportCommand extends Command {
    public static final String COMMAND_WORD = "export";
    public static final String MESSAGE_USAGE = "Contacts have been successfully exported";
    public static final String COLUMN_HEADERS = "Name,Phone Number,Email Address,Address,Tags";
    public static final String PATH = "./data/addressbook.csv";

    @Override
    public CommandResult execute(Model model) {
        List<Person> personList = model.getPersonList();
        try {
            File file = createCSVFile();
            exportContacts(file, personList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new CommandResult(MESSAGE_USAGE);
    }

    private File createCSVFile() throws IOException {
        File file = new File(PATH);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    private void exportContacts(File file, List<Person> personList) throws IOException {
        FileWriter fw = new FileWriter(file, false);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(COLUMN_HEADERS);
        bw.newLine();
        for (Person person : personList) {
            bw.write(person.toCSVFormat());
            bw.newLine();
        }
        bw.close();
        fw.close();
    }
}
