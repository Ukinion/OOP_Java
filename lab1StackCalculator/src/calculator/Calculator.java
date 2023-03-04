package calculator;

import fabric.CommandFabric;
import fabric.command.Command;
import contex.ProgramContex;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Stack;
import java.util.logging.Logger;
import java.util.Scanner;


public class Calculator
{
    private Logger _logger;
    private HashMap<String, Command> _commandMap;

    public Calculator(String configName)
    {
        _logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        CommandFabric fabric = new CommandFabric(configName);
        _commandMap = fabric.createCommandMap();
    }

    public void calculate(InputStream inputStream) throws RuntimeException
    {
        _logger.info("Starting calculation...\n");
        Scanner commandFile = new Scanner(inputStream);
        HashMap<String, Double> commandParametersMap = new HashMap<String, Double>();
        Stack<Double> stack = new Stack<Double>();
        ProgramContex contex = new ProgramContex();

        contex.addContex("stackContex", stack);
        contex.addContex("parametersContex", commandParametersMap);

        int cntLine = 0;
        String curLine;
        String[] commandAndParameters;
        Command command;

        while (commandFile.hasNextLine())
        {
            ++cntLine;
            curLine = commandFile.nextLine();
            commandAndParameters = curLine.split(" ");

            try
            {
                if (!_commandMap.containsKey(commandAndParameters[0]))
                { throw new RuntimeException(); }
                contex.addContex("argsContex", commandAndParameters);
            }
            catch (RuntimeException ex)
            {
                _logger.warning("Unrecognized command: " + commandAndParameters[0] + "\n\t\tDetected at line № " + cntLine);
                continue;
            }

            command = _commandMap.get(commandAndParameters[0]);
            command.execute(contex);
        }

        if (stack.size() > 1)
        { _logger.warning("Stack is not empty!\n\t\tSuspecting expression error...\n\t\t"); }
    }
}
