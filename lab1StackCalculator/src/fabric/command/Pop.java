package fabric.command;

import calculator.Calculator;
import context.ProgramContext;
import exceptions.InvalidCommandArgumentListException;
import exceptions.NotEnoughOperandsException;
import exceptions.UndefinedExecutionContextException;

import javax.naming.NamingException;
import java.util.HashMap;
import java.util.Stack;

public class PopFromStackCommand implements Command
{
    private final static int NUM_OPERANDS = 1;

    @Override
    public void execute(ProgramContext context) throws RuntimeException
    {
        try
        {
            @SuppressWarnings("unchecked")
            var stack = (Stack<String>) context.lookupContext(Calculator.STACK_CONTEXT);
            @SuppressWarnings("unchecked")
            var argsCommand = (String[]) context.lookupContext(Calculator.COMMAND_INFO_CONTEXT);

            if (argsCommand.length != NUM_OPERANDS)
            { throw new InvalidCommandArgumentListException(); }

            if (stack.empty())
            { throw new NotEnoughOperandsException(); }

            stack.pop();
        }
        catch (NamingException ex)
        {
            _logger.severe(ex.getMessage() + "Could not find required context!\n");
            throw new UndefinedExecutionContextException();
        }
    }
}
