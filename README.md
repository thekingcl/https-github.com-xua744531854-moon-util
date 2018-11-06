# 介绍
此工具类主要为 JDK 常用操作提供便利。
写这个工具类是为了解决工作中的一些操作在已有的工具类（如：commons）
不能完全满足需求的问题，比如类型转换，CollectionUtil.isNotEmpty() 等，
直到在一次阅读 fastjson 源码时惊喜的看到了 TypeUtils 这个类
提供了强大的类型转换，便开始了自己写一个更贴合日常开发需求的行动……

### 关键类：
- [TypeUtil](https://github.com/xua744531854/moon-util/blob/master/src/main/java/com/moon/util/TypeUtil.java)：如刚才提到的，这是一个类型转换工具；
- [RunnerUtil](https://github.com/xua744531854/moon-util/blob/master/src/main/java/com/moon/util/compute/RunnerUtil.java)：这是一个运行字符串表达式的工具类，
已经能计算大多数表达式，甚至还能执行方法；
- [IteratorUtil](https://github.com/xua744531854/moon-util/blob/master/src/main/java/com/moon/util/IteratorUtil.java)：迭代器，可以迭代任意可迭代对象，
如集合、数组、Map，主要提供了统一的调用入口；
- [FilterUtil](https://github.com/xua744531854/moon-util/blob/master/src/main/java/com/moon/util/FilterUtil.java)：过滤器；
- 等。