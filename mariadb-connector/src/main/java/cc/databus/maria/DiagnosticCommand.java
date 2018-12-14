package cc.databus.maria;

import javax.management.*;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.*;

import static cc.databus.maria.DiagnosticCommand.Operation.*;


/**
 * Created by vincent on 7/5/16.
 * All the commands can referred by jmc at the MBean Browser tab. And the bean name is:
 *      com.sun.management.DiagnosticCommand
 * Following operations are supported:
 *  - vmCheckCommercialFeatures
 *  - vmUnlockCommercialFeatures
 *  - vmCommandLine
 *  - vmFlags
 *  - threadPrint
 *  - jfrStart
 *  - jfrCheck
 *  - jfrDump
 *  - jfrStop
 *
 *  Note: the sse has a same implementation in order to support this feature run in sse processes
 *  If this is changed, change the implementation in SSE too.
 */
public class DiagnosticCommand {

    public enum Operation {
        checkCommercialFeatures,
        unlockCommercialFeatures,
        listCommandLine,
        listVMFlags,
        dumpThread,
        jfrStart,
        jfrCheck,
        jfrDump,
        jfrStop
    }

    private final MBeanServer _localServer = ManagementFactory.getPlatformMBeanServer();

    /**
     * prepare the operation, options, and some descriptions in some data structure.
     */
    private static List<Operation> _readOnlySupportedOperations;
    private static Map<Operation, Map<String, String>> _readOnlyOperateOptionsMap;
    private static Map<Operation, String> _readOnlyOperateDescMap;
    private static Map<Operation, String> _readOnlyOperateExample;
    static {
        Map<Operation, Map<String, String>> operateOptionsMap = new HashMap<>();
        Map<Operation, String> operateDescMap = new HashMap<>();
        Map<Operation, String> operateExamples = new HashMap<>();
        List<Operation> supported = new ArrayList<>();

        operateOptionsMap.put(checkCommercialFeatures, new HashMap<>());
        operateDescMap.put(checkCommercialFeatures, "Check if the commercial features are enabled");
        supported.add(checkCommercialFeatures);
        operateExamples.put(checkCommercialFeatures,
                "!jcmd checkCommercialFeatures: check if the commercial feature is enabled");

        operateOptionsMap.put(unlockCommercialFeatures, new HashMap<>());
        operateDescMap.put(unlockCommercialFeatures, "unlock the commercial features");
        supported.add(unlockCommercialFeatures);
        operateExamples.put(unlockCommercialFeatures,
                "!jcmd unlockCommercialFeatures: unlock the commercial feature");


        operateOptionsMap.put(listCommandLine, new HashMap<>());
        operateDescMap.put(listCommandLine, "Print the command line used to start this JVM");
        supported.add(listCommandLine);
        operateExamples.put(listCommandLine,
                "!jcmd listCommandLine: list the JVM startup command line");

        operateOptionsMap.put(listVMFlags, new HashMap<>());
        operateDescMap.put(listVMFlags, "Print VM flag options and their current values" );
        supported.add(listVMFlags);
        operateExamples.put(listVMFlags,
                "!jcmd listVMFlags: list the JVM flags");

        Map tmp = new HashMap<>();
        tmp.put("locks", "[optional] print java.util.concurrent locks (BOOLEAN, false)");
        tmp.put("filename", "[optional] dump threads into given file");
        tmp.put("timeout", "[optional] timeout in seconds");
        operateOptionsMap.put(Operation.dumpThread, tmp);
        operateDescMap.put(dumpThread, "Print all stack with stackTrace");
        supported.add(dumpThread);
        operateExamples.put(dumpThread,
                "!jcmd locks=true dumpThread : dump threads");

        tmp = new HashMap<>();
        tmp.put("name", "[Required] Name that can be used to identify recording, e.g. \\\"My Recording\\\" (STRING, no default value)");
        tmp.put("dumponexit", "[optional] Dump running recording when JVM shuts down (BOOLEAN, no default value)");
        tmp.put("delay", "[optional] Delay recording start with (s)econds, (m)inutes), (h)ours), or (d)ays, e.g. 5h.");
        tmp.put("duration", "[optional] Duration of recording in (s)econds, (m)inutes, (h)ours, or (d)ays, e.g. 300s. ");
        tmp.put("filename", "[optional] Resulting recording filename. (only file name)");
        tmp.put("compress", "[optional] GZip-compress the resulting recording file (BOOLEAN, true)");
        tmp.put("maxage", "[optional] Maximum time to keep recorded data (on disk) in (s)econds, (m)inutes, (h)ours, or (d)ays, e.g. 60m, or 0 for no limit");
        tmp.put("maxsize", "[optional] Maximum amount of bytes to keep (on disk) in (k)B, (M)B or (G)B, e.g. 500M, or 0 for no limit (MEMORY SIZE, 0)");
        operateOptionsMap.put(jfrStart, tmp);
        operateDescMap.put(jfrStart, "Start a new JFR recording");
        supported.add(jfrStart);
        operateExamples.put(jfrStart,
                "!jcmd duration=10m delay=10s compress=true filename=test.jfr jfrStart : schedule a jfr recording and will run 10 minutes");

        tmp = new HashMap<>();
        tmp.put("name", "[optional] Recording name, e.g. \\\"My Recording\\\" or omit to see all recordings (STRING, no default value)");
        tmp.put("recording", "[optional] Recording number, or omit to see all recordings (JLONG, -1)");
        tmp.put("verbose", "[optional] Print event settings for the recording(s) (BOOLEAN, false)");
        operateOptionsMap.put(Operation.jfrCheck, tmp);
        operateDescMap.put(jfrCheck, "Check running JFR recording(s)");
        supported.add(jfrCheck);
        operateExamples.put(jfrCheck, "!jcmd jfrCheck: check running jfr recordings");

        tmp = new HashMap<>();
        tmp.put("name", "[optional] Recording name");
        tmp.put("recording", "[optional] Recording number, use JFR.check to list available recordings");
        tmp.put("filename", "Copy recording data to file, (just file name) (STRING, no default value)");
        tmp.put("compress", "[optional] GZip-compress \"filename\" destination (BOOLEAN, true)");
        tmp.put("timeout", "[optional] timeout in seconds");
        operateOptionsMap.put(Operation.jfrDump, tmp);
        operateDescMap.put(jfrDump, "Copies JFR recording to a file. Either the name or the recording id must be specified");
        supported.add(jfrDump);
        operateExamples.put(jfrDump, "!jcmd name=testJfr filename=test1.jfr jfrDump : dump a running jfr recording");

        tmp = new HashMap<>();
        tmp.put("name", "[optional] Recording name,.e.g \\\"My Recording\\\" (STRING, no default value)");
        tmp.put("recording", "[optional] Recording number, use jfrCheck to list all available recordings");
        tmp.put("filename", "[optional] Copy recording data to file (just file name) ");
        tmp.put("discard", "[optional] Skip writting data to previolusly specified file (if any) default false");
        tmp.put("compress", "[optional] GZip-compress \"filename\" destination (BOOLEAN, true)");
        tmp.put("timeout", "[optional] timeout in seconds");
        operateOptionsMap.put(Operation.jfrStop, tmp);
        operateDescMap.put(jfrStop, "Stops a JFR recording");
        supported.add(jfrStop);
        operateExamples.put(jfrStop, "!jcmd name=testJfr filename=test12.jfr jfrStop: stop a running jfr, and save to given file");

        _readOnlyOperateOptionsMap = Collections.unmodifiableMap(operateOptionsMap);
        _readOnlyOperateDescMap = Collections.unmodifiableMap(operateDescMap);
        _readOnlySupportedOperations = Collections.unmodifiableList(supported);
        _readOnlyOperateExample = Collections.unmodifiableMap(operateExamples);
    }


    private ObjectName _objName;



    // make it singleton
    private DiagnosticCommand() {
    }
    private static DiagnosticCommand _INSTANCE = new DiagnosticCommand();
    public static DiagnosticCommand getInstance(){
        return _INSTANCE;
    }

    // Interfaces


    public static StringBuilder help(Operation operation, final StringBuilder sb) {
        sb.append(String.format("%-14s: %s", operation.toString(), getOperationDescription(operation))).append("\n");
        Map<String, String> options = getSupportedOptions(operation);
        if (options.size() > 0) {
            for (Map.Entry<String, String> entry : options.entrySet()) {
                sb.append("\t").append(String.format("%-14s: %s", entry.getKey(), entry.getValue())).append("\n");
            }
        }
        String example = _readOnlyOperateExample.get(operation);
        if (example != null) {
            sb.append("\t(Example: ").append(example).append(")\n");
        }

        return sb;
    }

    /**
     * Client should specify the Operation and the Options
     * @param opr operation
     * @param paramMap options as a map
     * @return Object
     */
    public Object diagnose(String opr, Map<String, String> paramMap) throws MalformedObjectNameException, ReflectionException, MBeanException, InstanceNotFoundException, IOException {
        Operation oprEnum = Operation.valueOf(opr);
        if (oprEnum == null) {
            throw new IllegalStateException("Operation is invalid - " + opr);
        }
        switch (oprEnum) {
            case checkCommercialFeatures:
                return _checkCommercialFeatures();
            case unlockCommercialFeatures:
                return _unlockCommercialFeatures();
            case listCommandLine:
                return _getCommandLine();
            case listVMFlags:
                return _getVMFlags();
            case dumpThread:
                boolean locks = Boolean.valueOf(paramMap.getOrDefault("locks", "true"));
                return _dumpThread(locks);
            case jfrStart:
                return _startJfr(paramMap);
            case jfrCheck:
                return _checkJfr(paramMap);
            case jfrDump:
                return _dumpJfr(paramMap);
            case jfrStop:
                return _stopJfr(paramMap);
            default:
                throw new IllegalArgumentException(String.format("Unsupported operation: %s", opr));
        }
    }

    /**
     * Get supported options for specified Operation
     * @param opr operation
     * @return Map (option-description map)
     */
    public static Map<String, String> getSupportedOptions(Operation opr) {
        return _readOnlyOperateOptionsMap.get(opr);
    }

    /**
     * Get the description for specified Operation
     * @param opr operation
     * @return String
     */
    public static String getOperationDescription(Operation opr) {
        return _readOnlyOperateDescMap.get(opr);
    }

    public static List<Operation> getSupportedOperations() {
        return _readOnlySupportedOperations;
    }


    private Object _checkCommercialFeatures()
            throws MalformedObjectNameException, MBeanException, InstanceNotFoundException, ReflectionException {
        return _localServer.invoke(_getObjName(), "vmCheckCommercialFeatures", new Object[0], new String[0]);
    }

    private Object _unlockCommercialFeatures()
            throws MalformedObjectNameException, MBeanException, InstanceNotFoundException, ReflectionException {
        return _localServer.invoke(_getObjName(), "vmUnlockCommercialFeatures", new Object[0], new String[0]);
    }

    private Object _getCommandLine()
            throws MalformedObjectNameException, MBeanException, InstanceNotFoundException, ReflectionException {
        return _localServer.invoke(_getObjName(), "vmCommandLine", new Object[0], new String[0]);
    }

    private Object _getVMFlags()
            throws MalformedObjectNameException, MBeanException, InstanceNotFoundException, ReflectionException {
        return _localServer.invoke(_getObjName(), "vmFlags", new Object[] {new String[0]}, new String[]{String[].class.getName()});
    }

    private Object _dumpThread(boolean locks)
            throws MalformedObjectNameException, MBeanException, InstanceNotFoundException, ReflectionException, IOException {
        Object[] params = locks ? new Object[] {new String[] {"-l=true"}} : new Object[0];
        return _localServer.invoke(_getObjName(), "threadPrint", params, new String[] {String[].class.getName()});
    }

    private Object _startJfr(Map<String, String> options)
            throws MalformedObjectNameException, MBeanException, InstanceNotFoundException, ReflectionException {
        if (options.size() == 0)
            throw new IllegalArgumentException("Not any options specified");
        return _localServer.invoke(_getObjName(), "jfrStart", new Object[] {_combineParams(jfrStart, options)}, new String[] {String[].class.getName()});
    }

    private Object _checkJfr(Map<String, String> options)
            throws MalformedObjectNameException, MBeanException, InstanceNotFoundException, ReflectionException {
        return _localServer.invoke(_getObjName(), "jfrCheck", new Object[] {_combineParams(jfrCheck, options)}, new String[]{String[].class.getName()});
    }

    private Object _dumpJfr(Map<String, String> options)
            throws MalformedObjectNameException, MBeanException, InstanceNotFoundException, ReflectionException {
        if (!(options.containsKey("name") || options.containsKey("recording")))
            throw new IllegalArgumentException("Either name or recording id must be specified");
        return _localServer.invoke(_getObjName(), "jfrDump", new Object[] {_combineParams(jfrDump, options)}, new String[]{String[].class.getName()});
    }

    private Object _stopJfr(Map<String, String> options)
            throws MalformedObjectNameException, MBeanException, InstanceNotFoundException, ReflectionException {
        if (!(options.containsKey("name") || options.containsKey("recording")))
            throw new IllegalArgumentException("Either name or recording id must be specified");
        return _localServer.invoke(_getObjName(), "jfrStop", new Object[] {_combineParams(jfrStop, options)}, new String[] {String[].class.getName()});
    }

    private String[] _combineParams(Operation opr, Map<String, String> options) {
        HashSet<String> combinedParams = new HashSet<>();
        Set<String> keys = getSupportedOptions(opr).keySet();
        for (Map.Entry<String, String> entry : options.entrySet()) {
            if (keys.contains(entry.getKey().toLowerCase())) {
                combinedParams.add(String.format("%s=%s", entry.getKey(), _makeSafOptionValue(entry.getValue())));
            }
        }

        return combinedParams.toArray(new String[combinedParams.size()]);
    }

    private String _makeSafOptionValue(String optionValue) {
        if (optionValue == null || optionValue.isEmpty())
            return optionValue;

        // contains space and not surrounded by ""
        if (optionValue.contains(" ") &&
                !(optionValue.startsWith("\"") && optionValue.endsWith("\"") )) {
            return "\"" + optionValue + "\"";
        }

        return optionValue;
    }

    private synchronized ObjectName _getObjName() throws MalformedObjectNameException {
        if (_objName == null) {
            _objName = new ObjectName("com.sun.management:type=DiagnosticCommand");
        }
        return _objName;
    }

}
