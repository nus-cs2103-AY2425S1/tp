package tahub.contacts;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import tahub.contacts.commons.core.Config;
import tahub.contacts.commons.core.LogsCenter;
import tahub.contacts.commons.core.Version;
import tahub.contacts.commons.exceptions.DataLoadingException;
import tahub.contacts.commons.util.ConfigUtil;
import tahub.contacts.commons.util.StringUtil;
import tahub.contacts.logic.Logic;
import tahub.contacts.logic.LogicManager;
import tahub.contacts.model.AddressBook;
import tahub.contacts.model.Model;
import tahub.contacts.model.ModelManager;
import tahub.contacts.model.ReadOnlyAddressBook;
import tahub.contacts.model.ReadOnlyUserPrefs;
import tahub.contacts.model.UserPrefs;
import tahub.contacts.model.course.UniqueCourseList;
import tahub.contacts.model.studentcourseassociation.StudentCourseAssociationList;
import tahub.contacts.model.util.SampleDataUtil;
import tahub.contacts.storage.AddressBookStorage;
import tahub.contacts.storage.JsonAddressBookStorage;
import tahub.contacts.storage.JsonStudentCourseAssociationListStorage;
import tahub.contacts.storage.JsonUniqueCourseListStorage;
import tahub.contacts.storage.JsonUserPrefsStorage;
import tahub.contacts.storage.Storage;
import tahub.contacts.storage.StorageManager;
import tahub.contacts.storage.UserPrefsStorage;
import tahub.contacts.ui.Ui;
import tahub.contacts.ui.UiManager;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(0, 2, 2, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing AddressBook ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());
        initLogging(config);

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        AddressBookStorage addressBookStorage = new JsonAddressBookStorage(userPrefs.getAddressBookFilePath());
        JsonUniqueCourseListStorage courseListStorage =
                new JsonUniqueCourseListStorage(userPrefs.getCourseListFilePath());
        JsonStudentCourseAssociationListStorage scaListStorage =
                new JsonStudentCourseAssociationListStorage(userPrefs.getScaListFilePath());
        storage = new StorageManager(addressBookStorage, userPrefsStorage, courseListStorage, scaListStorage);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s address book and {@code userPrefs}. <br>
     * The data from the sample address book will be used instead if {@code storage}'s address book is not found,
     * or an empty address book will be used instead if errors occur when reading {@code storage}'s address book.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        logger.info("Using data file : " + storage.getAddressBookFilePath());

        Optional<ReadOnlyAddressBook> addressBookOptional;
        ReadOnlyAddressBook initialData;
        try {
            addressBookOptional = storage.readAddressBook();
            if (addressBookOptional.isEmpty()) {
                logger.info("Creating a new data file " + storage.getAddressBookFilePath()
                        + " populated with a sample AddressBook.");
            }
            initialData = addressBookOptional.orElseGet(SampleDataUtil::getSampleAddressBook);
        } catch (DataLoadingException e) {
            logger.warning("Data file at " + storage.getAddressBookFilePath() + " could not be loaded."
                    + " Will be starting with an empty AddressBook.");
            initialData = new AddressBook();
        }

        Optional<UniqueCourseList> courseListOptional;
        UniqueCourseList initialCourseList;
        try {
            courseListOptional = storage.readCourseList(userPrefs.getCourseListFilePath());
            if (courseListOptional.isEmpty()) {
                logger.info("Creating a new course list file " + storage.getCourseListFilePath()
                        + " populated with a sample CourseList.");
            }
            initialCourseList = courseListOptional.orElseGet(SampleDataUtil::getSampleCourseList);
        } catch (DataLoadingException e) {
            logger.warning("Course list file at " + storage.getCourseListFilePath() + " could not be loaded."
                    + " Will be starting with an empty CourseList.");
            initialCourseList = new UniqueCourseList();
        }

        Optional<StudentCourseAssociationList> scaListOptional;
        StudentCourseAssociationList initialScaList;
        try {
            scaListOptional = storage.readScaList(userPrefs.getScaListFilePath());
            if (scaListOptional.isEmpty()) {
                logger.info("Creating a new sca list in the " + storage.getScaListFilePath()
                        + " file, populated with a sample SCA list.");
            }
            initialScaList = scaListOptional.orElseGet(SampleDataUtil::getSampleScaList);
        } catch (DataLoadingException e) {
            logger.warning("SCA list file at " + storage.getScaListFilePath() + " could not be loaded."
                    + " Will be starting with an empty sca list.");
            initialScaList = new StudentCourseAssociationList();
        }

        return new ModelManager(initialData, userPrefs, initialCourseList, initialScaList);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@code Config#DEFAULT_CONFIG_FILE} will be used instead
     * if {@code configFilePath} is null.
     */
    protected Config initConfig(Path configFilePath) {
        Config initializedConfig;
        Path configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            if (!configOptional.isPresent()) {
                logger.info("Creating new config file " + configFilePathUsed);
            }
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataLoadingException e) {
            logger.warning("Config file at " + configFilePathUsed + " could not be loaded."
                    + " Using default config properties.");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
     */
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using preference file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            if (!prefsOptional.isPresent()) {
                logger.info("Creating new preference file " + prefsFilePath);
            }
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataLoadingException e) {
            logger.warning("Preference file at " + prefsFilePath + " could not be loaded."
                    + " Using default preferences.");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting AddressBook " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping AddressBook ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
