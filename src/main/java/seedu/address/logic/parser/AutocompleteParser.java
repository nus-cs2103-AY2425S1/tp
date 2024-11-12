package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ArchiveCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.GradeCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.LoadCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Module;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Parses user input for autocomplete features.
 */
public class AutocompleteParser {
    /**
     * List of commands to match for when autocompleting.
     */
    private static final String[] commands = {
        AddCommand.COMMAND_WORD,
        ArchiveCommand.COMMAND_WORD,
        EditCommand.COMMAND_WORD,
        DeleteCommand.COMMAND_WORD,
        ClearCommand.COMMAND_WORD,
        FindCommand.COMMAND_WORD,
        ListCommand.COMMAND_WORD,
        ExitCommand.COMMAND_WORD,
        HelpCommand.COMMAND_WORD,
        GradeCommand.COMMAND_WORD,
        UndoCommand.COMMAND_WORD,
        RedoCommand.COMMAND_WORD,
        LoadCommand.COMMAND_WORD,
        FilterCommand.COMMAND_WORD,
    };

    /**
     * List of prefixes to match for when autocompleting.
     */
    private static final Prefix[] prefixes = {
        PREFIX_MODULE,
        PREFIX_TAG,
        PREFIX_GENDER,
        PREFIX_PATH
    };

    /**
     * List of genders to match for when autocompleting.
     */
    private static final String[] validGenders = {
        "male",
        "female"
    };

    private String filePath = "archived";

    /**
     * Overloaded constructor for AutocompleteParser. Only used for testing purposes.
     *
     * @param filePath File directory to use when matching for file paths.
     */
    public AutocompleteParser(String filePath) {
        if (!filePath.isEmpty()) {
            this.filePath = filePath;
        }
    }

    /**
     * Constructor for AutocompleteParser.
     */
    public AutocompleteParser() {
    }

    /**
     * Parses user input for a list of suggestions for autocompletion.
     *
     * @param userInput Full input from user.
     * @param ab AddressBook to base the suggestions off of.
     * @param caretPosition Current caret position in the text field.
     * @return HashMap of items consisting of (item, full input from user with word under caret substituted with item).
     */
    public HashMap<String, String> parseCommand(String userInput, ReadOnlyAddressBook ab, int caretPosition) {
        String wordUnderCaret = getWordFromCaretPosition(userInput, caretPosition);

        if (shouldReturnEmptyList(wordUnderCaret)) {
            return new HashMap<>();
        }
        assert(!wordUnderCaret.isEmpty());

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(" " + wordUnderCaret.trim(), prefixes);
        int startIndex = getPreviousWhitespaceIndex(userInput, caretPosition);
        int endIndex = getNextWhitespaceIndex(userInput, caretPosition);

        if (isCaretOnFirstWord(userInput, caretPosition)) {
            return getCommandSuggestions(userInput, wordUnderCaret, startIndex, endIndex);
        }

        if (argMultimap.getValue(PREFIX_MODULE).isPresent()) {
            return getModuleSuggestions(userInput, wordUnderCaret, ab, startIndex, endIndex, argMultimap);
        }

        if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            return getTagSuggestions(userInput, wordUnderCaret, ab, startIndex, endIndex, argMultimap);
        }

        if (argMultimap.getValue(PREFIX_GENDER).isPresent()) {
            return getGenderSuggestions(userInput, wordUnderCaret, startIndex, endIndex, argMultimap);
        }

        if (argMultimap.getValue(PREFIX_PATH).isPresent()) {
            return getPathSuggestions(userInput, wordUnderCaret, startIndex, endIndex, argMultimap);
        }

        return new HashMap<>();
    }

    /**
     * Checks if the conditions for returning an empty list of suggestions are met.
     *
     * @param wordUnderCaret The word that is currently being typed under the caret.
     * @return true if word under caret is empty.
     */
    private boolean shouldReturnEmptyList(String wordUnderCaret) {
        return wordUnderCaret.isEmpty();
    }

    /**
     * Generates suggestions for modules based on the current user input.
     *
     * @param userInput The full user input string.
     * @param wordUnderCaret The word that is currently being typed under the caret.
     * @param ab The AddressBook that contains persons with modules to search for suggestions.
     * @param startIndex The index of the start of the word under the caret.
     * @param endIndex The index of the end of the word under the caret.
     * @param argMultimap The tokenized argument multimap containing the parsed user input.
     * @return A HashMap of module suggestions.
     */
    private HashMap<String, String> getModuleSuggestions(String userInput, String wordUnderCaret,
                                                         ReadOnlyAddressBook ab, int startIndex, int endIndex,
                                                         ArgumentMultimap argMultimap) {
        HashMap<String, String> suggestionList = new HashMap<>();
        for (Person person : ab.getPersonList()) {
            for (Module module : person.getModules()) {
                String moduleString = module.module;
                if (isStringMatching(moduleString, argMultimap.getValue(PREFIX_MODULE).get())
                        && !suggestionList.containsKey(moduleString)) {
                    suggestionList.put(moduleString, getCompleteStringWithReplacement(userInput, wordUnderCaret,
                            moduleString, startIndex, endIndex));
                }
            }
        }
        return suggestionList;
    }

    /**
     * Generates suggestions for tags based on the current user input.
     *
     * @param userInput The full user input string.
     * @param wordUnderCaret The word that is currently being typed under the caret.
     * @param ab The AddressBook that contains persons with tags to search for suggestions.
     * @param startIndex The index of the start of the word under the caret.
     * @param endIndex The index of the end of the word under the caret.
     * @param argMultimap The tokenized argument multimap containing the parsed user input.
     * @return A HashMap of tag suggestions.
     */
    private HashMap<String, String> getTagSuggestions(String userInput, String wordUnderCaret, ReadOnlyAddressBook ab,
                                                      int startIndex, int endIndex, ArgumentMultimap argMultimap) {
        HashMap<String, String> suggestionList = new HashMap<>();
        for (Person person : ab.getPersonList()) {
            for (Tag tag : person.getTags()) {
                if (isStringMatching(tag.tagName, argMultimap.getValue(PREFIX_TAG).get())
                        && !suggestionList.containsKey(tag.tagName)) {
                    suggestionList.put(tag.tagName, getCompleteStringWithReplacement(userInput, wordUnderCaret,
                            tag.tagName, startIndex, endIndex));
                }
            }
        }
        return suggestionList;
    }

    /**
     * Generates suggestions for genders based on the current user input.
     *
     * @param userInput The full user input string.
     * @param wordUnderCaret The word that is currently being typed under the caret.
     * @param startIndex The index of the start of the word under the caret.
     * @param endIndex The index of the end of the word under the caret.
     * @param argMultimap The tokenized argument multimap containing the parsed user input.
     * @return A HashMap of gender suggestions.
     */
    private HashMap<String, String> getGenderSuggestions(String userInput, String wordUnderCaret,
                                                         int startIndex, int endIndex, ArgumentMultimap argMultimap) {
        HashMap<String, String> suggestionList = new HashMap<>();
        for (String gender : validGenders) {
            if (isStringMatching(gender, argMultimap.getValue(PREFIX_GENDER).get())) {
                suggestionList.put(gender, getCompleteStringWithReplacement(userInput, wordUnderCaret, gender,
                        startIndex, endIndex));
            }
        }
        return suggestionList;
    }

    /**
     * Generates suggestions for file paths based on the current user input.
     *
     * @param userInput The full user input string.
     * @param wordUnderCaret The word that is currently being typed under the caret.
     * @param startIndex The index of the start of the word under the caret.
     * @param endIndex The index of the end of the word under the caret.
     * @param argMultimap The tokenized argument multimap containing the parsed user input.
     * @return A HashMap of file path suggestions.
     */
    private HashMap<String, String> getPathSuggestions(String userInput, String wordUnderCaret,
                                                         int startIndex, int endIndex, ArgumentMultimap argMultimap) {
        HashMap<String, String> suggestionList = new HashMap<>();
        for (String file : getAllFilesInArchiveDirectory()) {
            if (isStringMatching(file, argMultimap.getValue(PREFIX_PATH).get())) {
                suggestionList.put(file, getCompleteStringWithReplacement(userInput, wordUnderCaret, file,
                        startIndex, endIndex));
            }
        }
        return suggestionList;
    }

    /**
     * Generates suggestions for commands based on the current user input.
     *
     * @param userInput The full user input string.
     * @param wordUnderCaret The word that is currently being typed under the caret.
     * @param startIndex The index of the start of the word under the caret.
     * @param endIndex The index of the end of the word under the caret.
     * @return A HashMap of command suggestions.
     */
    private HashMap<String, String> getCommandSuggestions(String userInput, String wordUnderCaret,
                                                          int startIndex, int endIndex) {
        HashMap<String, String> suggestionList = new HashMap<>();
        for (String command : commands) {
            // If full command has already been typed out, do not show suggestions.
            if (command.equals(wordUnderCaret)) {
                return new HashMap<>();
            }
            if (isStringMatching(command, wordUnderCaret)) {
                suggestionList.put(command, getCompleteStringWithReplacement(userInput, wordUnderCaret, command,
                        startIndex, endIndex));
            }
        }
        return suggestionList;
    }

    private String getWordFromCaretPosition(String fullInput, int caretPosition) {
        String text = fullInput.substring(0, caretPosition);
        int startIndex = getPreviousWhitespaceIndex(text, caretPosition);

        return text.substring(startIndex + 1);
    }

    private boolean isCaretOnFirstWord(String fullInput, int caretPosition) {
        int startIndex = getPreviousWhitespaceIndex(fullInput, 0);
        int endIndex = getNextWhitespaceIndex(fullInput, 0);

        return caretPosition >= startIndex && caretPosition <= endIndex;
    }

    private int getPreviousWhitespaceIndex(String text, int caretPosition) {
        int index = caretPosition - 1;
        while (index >= 0 && !Character.isWhitespace(text.charAt(index))) {
            index--;
        }

        return index;
    }

    private int getNextWhitespaceIndex(String text, int caretPosition) {
        int endIndex = caretPosition;
        while (endIndex < text.length()
                && !Character.isWhitespace(text.charAt(endIndex))) {
            endIndex++;
        }

        return endIndex;
    }

    /**
     * Gets the full user input string with the suggestion slotted in
     *
     * @param fullUserInput The full user input string.
     * @param word The word that is currently being typed under the caret.
     * @param command The word to replace the word under the caret.
     * @param startIndex The index of the start of the word under the caret.
     * @param endIndex The index of the end of the word under the caret.
     * @return Full user input string with the suggestion slotted in
     */
    private String getCompleteStringWithReplacement(String fullUserInput, String word, String command,
                                                    int startIndex, int endIndex) {
        String stringToSlotIn;
        stringToSlotIn = getStringWithPrefix(word, command);

        StringBuffer buf = new StringBuffer(fullUserInput);
        buf.replace(startIndex + 1, endIndex, stringToSlotIn);
        return buf.toString();
    }

    /**
     * Gets all the file names in the directory "archived".
     * Will return an empty list if the directory does not exist.
     * @return An ArrayList with all the name of json file
     */

    private ArrayList<String> getAllFilesInArchiveDirectory() {
        File directory = Paths.get(filePath).toFile();

        // Check if the directory exists and is indeed a directory
        if (directory.exists() && directory.isDirectory()) {
            // Get all files and directories in the specified path
            return getAllJsonFiles(directory);
        } else {
            // Return an empty list if the directory does not exist
            return new ArrayList<>();
        }
    }

    /**
     * Gets all the json file name from a directory of given Path
     * @param directory The file path to the directory
     * @return An array list of all json file name
     * */
    private ArrayList<String> getAllJsonFiles(File directory) {
        File[] files = directory.listFiles();
        assert (files != null);
        ArrayList<String> result = new ArrayList<>();
        for (File file : files) {
            // add file name if it is a json file
            if (isJson(file)) {
                result.add(file.getName());
            }
        }
        return result;
    }

    /**
     * Checks if the given file is a json file.
     * @param file the file to check
     * */
    private boolean isJson(File file) {
        return file.isFile() && file.getName().toLowerCase().endsWith(".json");
    }

    private String getStringWithPrefix(String word, String command) {
        for (Prefix prefix : prefixes) {
            if (word.startsWith(prefix.getPrefix())) {
                return prefix.getPrefix() + command;
            }
        }
        return command;
    }

    private boolean isStringMatching(String targetString, String input) {
        return targetString.toLowerCase().startsWith(input.toLowerCase());
    }
}

