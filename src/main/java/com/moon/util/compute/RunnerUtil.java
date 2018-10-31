package com.moon.util.compute;

import com.moon.util.compute.core.ParseUtil;

/**
 * 介绍：
 * <p>
 * 【 1 】、此工具类没有 char 类型，单引号和双引号包裹起来的均是字符串（类似 JavaScript）
 * - 当出现交叉包裹时抛出异常或者不能得到预期结果，如：
 * - 'abc"def'.gh" ---> 抛出异常
 * - 将单引号设为字符串符号是为了书写方便，将双引号设为字符串符号是为了兼容 Java 自身语言特性
 * <p>
 * <p>
 * 【 2 】、所有整形数均采用 int 数据计算，所有浮点数均采用 double 计算；
 * - char 类型数据会被视为字符串进行，进而可能导致得不到预期结果；
 * - 其他自动转型同 Java 自身语言特性。
 * <p>
 * - 虽然是用 int 或 double 进行计算，但是由于 Java 语言的自动拆装箱，实际上数字也具有对应的方法
 * - 如：20.compareTo(30)
 * - 同样，true 和 false 也是如此
 * <p>
 * <p>
 * 【 3 】、可通过 ‘@’ 符号执行静态方法：目前只支持无参或只有一个参数的方法执行和静态字段的访问
 * - 包括{@link java.lang}、{@link java.lang.reflect}、{@link java.util}包中的静态公共方法;
 * - 调用方式形如：1 + 2 + @Objects.toString('10') --> "310"
 * - 单引号包裹的 ‘'10'’ 是此工具类支持的字符串，‘@’ 符号是此工具类支持的静态方法调用符号
 * <p>
 * - 也包括此工具类的所有工具方法，如：@DateUtil.format();
 * - 静态公共字段，如：@Console.out.getLowestLevel().name();
 * - 内部类的调用方式需如此：@Console$Level.ASSERT.name()，需要手动将 ‘$’ 符号包含进去
 * <p>
 * - 调用时注意方法本身的规则，比如只能调用公共类的静态公共方法，不建议调用过时方法等
 * <p>
 * - 虽然支持静态方法调用，但仍然建议使用已经处理好的数据，一则可以提高性能，更能避免未知的异常
 * <p>
 * <p>
 * 【 4 】、数组：用花括号（{1,2,3}）表示:
 * - 形如：{1, 2, 3}
 * - 取值：（注意区分 JS 对象和 JSON）
 * {1, {key: 'value', number: 123, boolean: true, null: null}}[1]['key'] --> value
 * [1, {key: 'value', number: 123, boolean: true, null: null}][1].number --> 123
 * <p>
 * <p>
 * 【 5 】、Map 对象用花括号表示，最终会被转换成{@link java.util.HashMap}处理：
 * - 形如：{key: 123, 'key1': '123', "key2": true}
 * - 可设置为键的包括：字符串、数字（int | double）、null、true、false，与 JS 对象不同，
 * - js 对象的所有键均为字符串
 * <p>
 * <p>
 * 【 6 】、小结
 * 1、支持的运算：
 * - 基本运算：+、-、*、/、%；
 * - 位运算：&、|、^；
 * - 比较运算：==、>、>=、==、<、<=；
 * - 逻辑运算：&&、||、！;
 * - 括号提升优先级：()
 * 2、预定义关键字：null、true、false
 * 3、int 数字：12、25 等
 * 4、double 数字：20.0、36.5 等（其他类型数字不支持）
 * 5、字符串：'string'、"string"
 * 6、其他支持：
 * - {} ===> List；
 * - {:} ===> Map；Map 的键可以是：null、true、false、数字、字符串，其他对象均会自动转为 string，值可以是任意类型
 * - @System.currentTimeMillis() ===> 无参公共静态方法调用；
 * - @DateUtil.yyyy_MM ===> 公共静态字段访问；
 * - 'string'.length() ===> 无参公共实例方法调用；
 * - {0}.get(0) ===> 带有一个参数的公共实例方法调用；
 * - 变量
 * - 链式取值和方法调用：employee.name.length()
 * <p>
 * 注意：
 * - 静态方法调用只支持部分包下的类，具体见【 3 】
 * - 方法调用只支持无参方法和只有一个参数的方法，变长参数的方法不完全支持（慎用）
 * - 基本数据类型只支持 boolean、int、double，没有 char 类型数据，被征用做字符串了
 *
 * @author benshaoye
 */
public final class RunnerUtil extends ParseUtil {

    public final static String[] DELIMITERS = {"{{", "}}"};

    private RunnerUtil() {
        super();
    }

    /**
     * 运行简单表达式，形如：
     * 1 + 2
     * 'a' + 'b'
     * 等不含有参数的表达式
     * 如果 expression 是一个包含参数的表达式，将抛出异常
     * <p>
     * 实际实现方式:
     * {@link RunnerUtil#run(String)}会缓存所有不包含参数表达式的结果，
     * 只解析执行一次，并缓存，以后的运行返回第一次缓存的结果
     *
     * @param expression
     * @return
     */
    public final static Object run(String expression) {
        return run(expression, null);
    }

    /**
     * 计算复杂表达式，形如：
     * 1 + 2 + key1[0].name      --> key1 可以是 Map 的 key 或一个实体对象的字段
     * 或
     * 'a' + '2' + [0].key.name  --> 0 是数组或 List 的索引
     * 等带有参数的表达式
     *
     * @param expression
     * @param data
     * @return
     */
    public final static Object run(String expression, Object data) {
        return run0(expression, data);
    }

    /**
     * 运行字符串中的表达式，如：
     * RunnerUtil.parseRun("1 + 2 = {{1+2}}");        // =====> "1 + 2 = 3"
     * RunnerUtil.parseRun("中华人民共和国{{'棒棒的'}}"); // =====> "中华人民共和国棒棒的"
     * <p>
     * 默认分隔符为：${@link #DELIMITERS} ==> {"{{", "}}"}；
     * 可包含多个表达式，但不能嵌套包含，也不能交叉嵌套
     * 错误示例："1 + 2 = {{ 1 + {{ 3 + 4 }} + 2 }}"
     * 正确示例："1 + 2 = {{ 1 + 2 }}  {{ 3 + 4 }}"
     * <p>
     * 如果字符串中只有一个表达式，并且始末位置分别就是始末分割符，
     * 那么这个表达式返回值可以是任意对象，否则只能返回字符串，如：
     * RunnerUtil.parseRun("中华人民共和国{{'棒棒的'}}"); // =====> "中华人民共和国棒棒的"
     * RunnerUtil.parseRun("{{'棒棒的'}}");             // =====> "棒棒的"
     * RunnerUtil.parseRun("{{1}}");                  // =====> 1
     * <p>
     * 由于花括号 “{}、{:}” 在此工具中可表示 Map 或 List，所以在可能引起边界混淆的地方最好自定义分隔符，如：
     * {{{'name'}}}
     * 应该写成： {{ {'name'}[0] }} ==> 边界处留有空格；
     * 或： $[{'name'}] ==> 自定义分隔符；
     *
     * @param expression
     * @return
     */
    public final static Object parseRun(String expression) {
        return parseRun(expression, DELIMITERS);
    }

    /**
     * 运行字符串中的带变量的表达式，如：
     * <p>
     * Map data = {"desc": "棒棒的"} // 这是一个 Map
     * RunnerUtil.parseRun("中华人民共和国{{desc}}", data); // =====> "中华人民共和国棒棒的"
     * <p>
     * 默认分隔符为：${@link #DELIMITERS} ==> {"{{", "}}"}
     * <p>
     * 由于花括号 “{}、{:}” 在此工具中可表示 Map 或 List，所以在可能引起边界混淆的地方最好自定义分隔符，如：
     * {{{'name'}}}
     * 应该写成： {{ {'name'}[0] }} ==> 边界处留有空格；
     * 或： $[{'name'}] ==> 自定义分隔符；
     *
     * @param expression
     * @param data
     * @return
     * @see #parseRun(String)
     */
    public final static Object parseRun(String expression, Object data) {
        return parseRun(expression, DELIMITERS, data);
    }

    /**
     * 可自定义分隔符
     * <p>
     * 由于花括号 “{}、{:}” 在此工具中可表示 Map 或 List，所以在可能引起边界混淆的地方最好自定义分隔符，如：
     * {{{'name'}}}
     * 应该写成： {{ {'name'}[0] }} ==> 边界处留有空格；
     * 或： $[{'name'}] ==> 自定义分隔符；
     *
     * @param expression
     * @return
     * @see #parseRun(String, String[], Object)
     */
    public final static Object parseRun(String expression, String[] delimiters) {
        return parseRun(expression, delimiters, null);
    }

    /**
     * 可自定义分隔符，如：
     * <p>
     * Map data = {"desc": "棒棒的"} // 这是一个 Map
     * String[] delimiters = {"${", "}"};
     * RunnerUtil.parseRun("中华人民共和国${desc}", delimiters,data); // =====> "中华人民共和国棒棒的"
     * <p>
     * 【注意】同一个字符串中不可包含多种不同的分隔符而运行多次
     * <p>
     * 由于花括号 “{}、{:}” 在此工具中可表示 Map 或 List，所以在可能引起边界混淆的地方最好自定义分隔符，如：
     * {{{'name'}}}
     * 应该写成： {{ {'name'}[0] }} ==> 边界处留有空格；
     * 或： $[{'name'}] ==> 自定义分隔符；
     *
     * @param expression
     * @param delimiters 必须是一个长度不小于 2 包含始末标记的非空字符串，长度大于 2 后面的内容会被忽略
     * @param data
     * @return
     * @see #parseRun(String)
     */
    public final static Object parseRun(String expression, String[] delimiters, Object data) {
        return parseRun0(expression, delimiters, data);
    }
}
