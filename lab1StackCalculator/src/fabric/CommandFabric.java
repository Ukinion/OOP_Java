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

    public CommandFabric(String configName)
    {
        _logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        try 
        { _cfgScanner = new Scanner(new File(configName)); }
        catch (FileNotFoundException ex)
        { _logger.severe("Scanner could not open config file.\n\t\tHave to start aborting... \n\t\t" + ex.getMessage()); }
    }

    public HashMap<String, Command> createCommandMap()
    {
        HashMap<String, Command> commandMap = new HashMap<String, Command>();

        int cntLine = 0;
        String curLine;
        String[] keyAndValue;
        Class<?> commandClass;

        while (_cfgScanner.hasNextLine())
        {
            ++cntLine;
            curLine = _cfgScanner.nextLine();
            keyAndValue = curLine.split("=");

            try
            {
                commandClass = Class.forName(keyAndValue[1]);
                commandMap.put(keyAndValue[0], (Command) commandClass.getConstructor().newInstance());
            }
            catch (ArrayIndexOutOfBoundsException ex)
            { _logger.warning("Incorrect command line: №" + cntLine + "\n\t\t" + "Trying to ignore...\n\t\t" + ex.getMessage()); }
            catch (ClassNotFoundException ex)
            { _logger.severe("Config contains unsupported command.\n\t\t Error!\n\t\t" + ex.getMessage()); }
            catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | InvocationTargetException ex)
            { _logger.severe("System detected internal error.\n\t\t" + ex.getMessage()); }
        }

        return commandMap;
    }
}


