package fabric;

import fabric.command.Command;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;
import java.util.Scanner;
import java.util.HashMap;
import java.io.File;

public class CommandFabric
{
    private Logger _logger;
    private Scanner _cfgScanner;

    private final static String CONFIG_DELIMITER = "=";
    private final static int COMMAND_USER_NAME = 0;
    private final static int COMMAND_CLASS_NAME = 1;

    public CommandFabric(String configName) throws FileNotFoundException
    {
        _logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        try
        { _cfgScanner = new Scanner(new File(configName)); }
        catch (FileNotFoundException ex)
        {
            _logger.severe("Scanner could not open config file!\n\t\tHave to start aborting...\n\t\t" + ex.getMessage());
            throw ex;
        }
    }

    public HashMap<String, Command> createCommandMap() throws Exception
    {
        HashMap<String, Command> commandMap = new HashMap<>();

        int cntLine = 0;
        String curLine;
        String[] configData;
        Class<?> commandClass;

        while (_cfgScanner.hasNextLine())
        {
            ++cntLine;
            curLine = _cfgScanner.nextLine();
            configData = curLine.split(CONFIG_DELIMITER);

            try
            {
                commandClass = Class.forName(configData[COMMAND_CLASS_NAME]);
                commandMap.put(configData[COMMAND_USER_NAME], (Command) commandClass.getConstructor().newInstance());
            }
            catch (ArrayIndexOutOfBoundsException ex)
            {
                _logger.severe("Incorrect command line: №" + cntLine + "\n\t\t" + "Stop processing...\n\t\t" + ex.getMessage());
                throw ex;
            }
            catch (ClassNotFoundException ex)
            {
                _logger.severe("Config contains unsupported command.\n\t\t Error!\n\t\t" + ex.getMessage());
                throw ex;
            }
            catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | InvocationTargetException ex)
            {
                _logger.severe("System detected internal error.\n\t\t" + ex.getMessage());
                throw ex;
            }
        }
        return commandMap;
    }
}
