package exceptions;

public class FabricInternalErrorException extends RuntimeException
{
    public FabricInternalErrorException()
    { super("InternalError: fabric could not create command map\n\t\t"); }
}
