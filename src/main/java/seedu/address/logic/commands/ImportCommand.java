package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.person.*;
import seedu.address.model.tag.Tag;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class ImportCommand extends Command {
    public static final String COMMAND_WORD = "import";
    public static final String MESSAGE_SUCCESS = "The contacts from %s have been successfully imported";
    public static final String MESSAGE_USAGE = "wip";
    public static final String MESSAGE_FILE_DOES_NOT_EXIST = "The specified file does not exist";
    public static final String MESSAGE_INCORRECT_FILE_FORMAT = "The format of the specified file is incorrect";
    public static final String MESSAGE_ERROR_READING_FILE = "There was an error when reading the file";
    public static final String COLUMN_HEADERS = "Name,Phone Number,Email Address,Address,Tags";
    public static final String PATH = "./data/";
    private String fileName;
    private String filePath;

    public ImportCommand(String fileName) {
        this.fileName = fileName;
        this.filePath = PATH + fileName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        File file = getCsvFile();
        if (!checkCsvFileFormat(file)) {
            throw new CommandException(MESSAGE_INCORRECT_FILE_FORMAT);
        }
        List<Person> personList = getPersonList(file);
        for (Person person : personList) {
            if (!model.hasPerson(person)) {
                model.addPerson(person);
            }
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, fileName));
    }

    private File getCsvFile() throws CommandException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new CommandException(MESSAGE_FILE_DOES_NOT_EXIST);
        }
        return file;
    }

    private boolean checkCsvFileFormat(File file) throws CommandException {
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            br.close();
            fr.close();
            return line.equals(COLUMN_HEADERS);
        } catch (IOException e) {
            throw new CommandException(MESSAGE_ERROR_READING_FILE);
        }
    }

    private List<Person> getPersonList(File file) throws CommandException {
        ArrayList<Person> personList = new ArrayList<>();
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String trimmedLine = trimLine(line);
                Person person = parseLine(trimmedLine);
                personList.add(person);
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            throw new CommandException(MESSAGE_ERROR_READING_FILE);
        }
        return personList;
    }

    private String trimLine(String line) throws CommandException {
        try {
            return line.substring(1, line.length() - 1);
        } catch (StringIndexOutOfBoundsException e) {
            throw new CommandException(MESSAGE_INCORRECT_FILE_FORMAT);
        }
    }

    private Person parseLine(String line) throws CommandException {
        String[] arr = line.split("\",\"");
        if (arr.length != 4 && arr.length != 5) {
            throw new CommandException(MESSAGE_INCORRECT_FILE_FORMAT);
        }
        try {
            Name name = ParserUtil.parseName(arr[0]);
            Phone phone = ParserUtil.parsePhone(arr[1]);
            Email email = ParserUtil.parseEmail(arr[2]);
            Address address = ParserUtil.parseAddress(arr[3]);
            List<String> tagList = new ArrayList<>();
            if (arr.length == 5) {
                tagList = Arrays.asList(arr[4].split(","));
            }
            Set<Tag> tags = ParserUtil.parseTags(tagList);
            Person person = new Person(name, phone, email, address, tags);
            return person;
        } catch (ParseException e) {
            throw new CommandException(MESSAGE_INCORRECT_FILE_FORMAT);
        }
    }
}
