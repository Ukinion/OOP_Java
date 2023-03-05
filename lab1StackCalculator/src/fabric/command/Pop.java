package fabric.command;

import calculator.Calculator;
import context.ProgramContext;
import exceptions.NotEnoughOperandsException;
import exceptions.UndefinedExecutionContextException;

import javax.naming.NamingException;
import java.util.HashMap;
import java.util.Stack;

public class PopFromStackCommand implements Command
{
    @Override
    public void execute(ProgramContext context) throws NotEnoughOperandsException, UndefinedExecutionContextException
    {
        try {
            @SuppressWarnings("unchecked")
            var stack = (Stack<String>) context.lookupContext(Calculator.STACK_CONTEXT);
            @SuppressWarnings("unchecked")
            var variableMap = (HashMap<String, Double>) context.lookupContext(Calculator.VARIABLE_CONTEXT);

            if (stack.empty())
            { throw new NotEnoughOperandsException(); }

            stack.pop();
        }
        catch (NamingException ex)
        {
            _logger.severe("Could not find required context!\n\t\t" + ex.getMessage());
            throw new UndefinedExecutionContextException();
        }
    }
}
