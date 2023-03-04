package fabric.command;

import contex.ProgramContex;
import java.util.Stack;
import javax.naming.NamingException;
import java.util.logging.Logger;

public interface Command
{
    Logger _logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    void execute(ProgramContex contex);

    static Stack<Double> assertAndGetStack(ProgramContex contex) throws RuntimeException
    {
        Stack<Double> stack = null;
        try
        { stack = (Stack<Double>) contex.lookupContex("stack"); }
        catch (NamingException ex)
        {
            _logger.severe("Could not find stack in context!\n\t\tAborting...\n\t\t" + ex.getMessage());
            throw new RuntimeException("No stack.");
        }

        assert (stack != null);
        return stack;
    }
}
