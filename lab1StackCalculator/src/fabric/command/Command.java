package fabric.command;

import context.ProgramContext;
import java.util.logging.Logger;

public interface Command
{
    Logger _logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    void execute(ProgramContext context);
}
