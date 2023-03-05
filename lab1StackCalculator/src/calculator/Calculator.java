package calculator;

import exceptions.*;
import fabric.CommandFabric;
import fabric.command.Command;
import context.ProgramContext;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Stack;
import java.util.logging.Logger;
import java.util.Scanner;


public class Calculator
{
    private Logger _logger;
    private HashMap<String, Command> _commandMap;

    private final static String COMMAND_LINE_DELIMITER = " ";
    private final static int COMMAND = 0;

    public final static String STACK_CONTEXT = "stackContext";
    public final static String VARIABLE_CONTEXT = "variableContext";
    public final static String COMMAND_INFO_CONTEXT = "commandContext";

    public Calculator(String configName) throws Exception
    {
        _logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        CommandFabric fabric = new CommandFabric(configName);
        _commandMap = fabric.createCommandMap();
    }

    public void calculate(InputStream inputStream) throws RuntimeException
    {
        _logger.info("Starting calculation...\n");
        Scanner commandFile = new Scanner(inputStream);
        HashMap<String, Double> variableMap = new HashMap<String, Double>();
        Stack<String> stack = new Stack<>();
        ProgramContext context = new ProgramContext();

        context.addContext(STACK_CONTEXT, stack);
        context.addContext(VARIABLE_CONTEXT, variableMap);

        int cntLine = 0;
        String curLine;
        String[] commandAndArguments;
        Command command;

        while (commandFile.hasNextLine())
        {
            ++cntLine;
            curLine = commandFile.nextLine();
            commandAndArguments = curLine.split(COMMAND_LINE_DELIMITER);

            if (!_commandMap.containsKey(commandAndArguments[COMMAND]))
            { throw new UnsupportedCommandException(); }

            context.addContext(COMMAND_INFO_CONTEXT, commandAndArguments);
            command = _commandMap.get(commandAndArguments[COMMAND]);

            try
            { command.execute(context); }
            catch (InvalidCommandArgumentListException ex)
            {
                _logger.severe("Not enough arguments to execute command\n\t\t" + ex.getMessage());
                throw new RuntimeException("Execution Error!");
            }
            catch (NotEnoughOperandsException ex)
            {
                _logger.severe("Aborting command execution...\n\t\t" + ex.getMessage());
                throw new RuntimeException("Execution Error!");
            }
            catch (DivisionByZeroException | SquareNegativeNumberException ex)
            {
                _logger.severe(ex.getMessage());
                throw new RuntimeException("Execution Error!");
            }
        }
    }
}

