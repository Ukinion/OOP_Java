package exceptions;

public class ExecutionCommandErrorException extends RuntimeException
{
    public ExecutionCommandErrorException()
    { super("ExecutionError: no further calculations possible\n\t\t"); }
}
