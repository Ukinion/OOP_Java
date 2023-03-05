package fabric.command;

import calculator.Calculator;
import context.ProgramContext;
import exceptions.NotEnoughOperandsException;
import exceptions.SquareNegativeNumberException;
import exceptions.UndefinedExecutionContextException;

import javax.naming.NamingException;
import java.lang.Math;
import java.util.HashMap;
import java.util.Stack;

public class SquareCommand implements Command
{

    @Override
    public void execute(ProgramContext context)
            throws NotEnoughOperandsException, UndefinedExecutionContextException, SquareNegativeNumberException
    {
        try {
            @SuppressWarnings("unchecked")
            var stack = (Stack<String>) context.lookupContext(Calculator.STACK_CONTEXT);
            @SuppressWarnings("unchecked")
            var variableMap = (HashMap<String, Double>) context.lookupContext(Calculator.VARIABLE_CONTEXT);

            if (stack.empty())
            { throw new NotEnoughOperandsException(); }

            String topVariable = stack.peek();
            Double sqrtVal = Double.valueOf(Math.sqrt(variableMap.get(topVariable)));

            if (sqrtVal.isNaN())
            { throw new SquareNegativeNumberException(); }
            variableMap.put(topVariable, Math.sqrt(variableMap.get(topVariable)));
        }
        catch (NamingException ex)
        {
            _logger.severe("Could not find required context!\n\t\t" + ex.getMessage());
            throw new UndefinedExecutionContextException();
        }
    }

}
