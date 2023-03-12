package context;

import javax.naming.NamingException;
import java.util.HashMap;

public class ProgramContext
{
    private final HashMap<String, Object> _contextMap;

    public ProgramContext()
    { _contextMap = new HashMap<>(); }

    public HashMap<String, Object> addContext(String key, Object value)
    {
        _contextMap.put(key, value);
        return _contextMap;
    }

    public Object lookupContext(String key) throws NamingException
    {
        Object object = _contextMap.get(key);

        if (object == null)
        { throw new NamingException("Undefined key: " + key + "\n\t\t" + "System could not find the context!\n\t\t"); }

        return object;
    }
}
