# 介绍
此工具类主要为 JDK 常用操作提供便利。
写这个工具类是为了解决工作中的一些操作在已有的工具类（如：commons）
不能完全满足需求的问题，比如类型转换，CollectUtil.isNotEmpty() 等，
直到在一次阅读 fastjson 源码时惊喜的看到了 TypeUtils 这个类
提供了强大的类型转换，便开始了自己写一个更贴合日常开发需求的行动……

### 关键类：
- [TypeUtil](https://github.com/xua744531854/moon-util/blob/master/src/main/java/com/moon/util/TypeUtil.java)：如刚才提到的，这是一个类型转换工具；
- [RunnerUtil](#RunnerUtil)：这是一个运行字符串表达式的工具类，
已经能计算大多数表达式，甚至还能执行方法；
- [IteratorUtil](https://github.com/xua744531854/moon-util/blob/master/src/main/java/com/moon/util/IteratorUtil.java)：迭代器，可以迭代任意可迭代对象，
如集合、数组、Map，主要提供了统一的调用入口；
- [FilterUtil](https://github.com/xua744531854/moon-util/blob/master/src/main/java/com/moon/util/FilterUtil.java)：过滤器；
- [ExcelUtil](#ExcelUtil)
- 等。

### RunnerUtil:
此类是用于运行一个字符串表达式（如：1+1，2+5 等）的工具类，
语法上较大程度参考了 JavaScript 语法，如在表达一个对象时，键名不用像 JSON 文件那样必须加双引号，
也舍弃 Java 内多数用 “${/*..*/}” 取值的语法，详情下面介绍，
经过大约两个星期的实现，目前包括以下[数据类型](#数据类型)和[运算类型](#运算类型)

#### 数据类型：
1. null：这是一个关键字，但因为它符合和变量的定义规则，所以需要注意一下，同样被定义为关键字的还有 true 和 false。
2. boolean：true 和 false
``` Java
RunnerUtil.run(" null   "); // null
RunnerUtil.run("   true "); // true
RunnerUtil.run("false"); // false
// 表达式中多余的空格自动忽略
```
3. 数字：这里面的数字统一采用 Java 里的 int 和 double 型数据，直接参与运算的也只有是这两种类型，区别就是有没有小数点。
``` Java
RunnerUtil.run(" 12  "); // 12
RunnerUtil.run(" 12.5 "); // 12.5
// 表示数字必须是连续，中间不能有空格的
// 否则将抛出异常，如
RunnerUtil.run(" 12. 5"); // 异常
RunnerUtil.run(" 1 2 "); // 异常
```
> 表示数字的字符之间应该是连续的，如：25、36.9 等；如果是不连续的会抛出异常，如：2 5、36 .9 等；
4. 字符串：Java 里的字符串用双引号包裹，在这里还将表示字符的单引号“征用”，双引号单引号包裹的都表示普通字符串的直接值，这样做也是为了书写方便（与 JavaScript 相似），同时也就没有了 char 类型数据啦啦啦……

``` Java
RunnerUtil.run(" 'abcdef'  "); // "abcdef"
RunnerUtil.run(" \"abcdef\"  "); // "abcdef"
RunnerUtil.run(" 'abc   def'  "); // "abc   def"
```
5. List：实际上是 ArrayList，对应 JavaScript 里面的数组。Java 的数组也对应 JavaScript 数组。

``` Java
RunnerUtil.run(" { } "); 
// 总是返回一个空 ArrayList

RunnerUtil.run(" {1,2,,4, } "); 
// 总是返回一个包含：1、2、null、4 这几项的 ArrayList

// 可以看出最后一个逗号之后如果是结束符号会自动忽略
// 中间的逗号与逗号之间若没有其他非空白符号会插入一个 null 值
```
6. Map：实际上是 HashMap，对应 JavaScript 里的对象。同样对应 JavaScript 对象的还有普通 POJO。

Map 对应的是 JavaScript 里的对象，但是在这里 Map 的键可以是这些数据类型：

> null、true / false、数字（int / double）、字符串，不能再是其他 Java 对象了

``` Java
RunnerUtil.run(" {:} "); // 总是返回一个空 HashMap，
// 注意与空 List 的异同，都是用花括号表示
// 但空 Map 里面需要有一个冒号，否则就是 List

RunnerUtil.run(" {key: 'value'}");
// 总是返回包含一个键值对的 HashMap
// 可以看出，对象的键名是字符串的话可以不用引号包裹
// 但是值必须被包裹
RunnerUtil.run(" {true: 'value'}"); // 键是 true
/*
 * 这里的 true 不是字符串，而是 boolean。
 * 同样，未被引号包裹的 null、false、数字都是对应类型的数据，而不是字符串
 * 其他符合变量命名规则的键都是普通字符串，被单引号或双引号包裹的也是
 */
RunnerUtil.run(" {'true': 'value', 25: false, 'name': \"张三\"}");
```

#### 运算支持的类型：
1. 普通四则混合运算：+、-、*、/、%、()
``` java
RunnerUtil.run(" 1 + 1 "); // 2
RunnerUtil.run(" 1 + (3 * 4)) "); // 13
RunnerUtil.run(" 'Hello ' + \"World!\" ");  // "Hello World!"
RunnerUtil.run(" true + false "); // "truefalse"
/*
 * true+false 在 Java 中是不允许的
 * 但如果是“+”运算的话，这里均作为普通字符串；
 * 相当于调用了 toString 方法
 */
```
2. 位运算：&、|、^、<<、>>
``` java
RunnerUtil.run(" 1 ^ 1 "); 
RunnerUtil.run(" 1 & 1 "); 
RunnerUtil.run(" 1 | 1 "); 
RunnerUtil.run(" 1 << 1 "); 
RunnerUtil.run(" 1 >> 1 ");
```
3. 比较运算：>、>=、==、<=、<
``` java
RunnerUtil.run(" 1 + 1 == 2 "); // true
RunnerUtil.run(" 1 + 1 < 2 "); // false
```
4. 逻辑运算：&&、||、!
``` java
RunnerUtil.run("1+1==2 && 5 > 4"); // true
```
6. 变量：命名规则与 Java 变量命名规则相同，同时 null、true、false 不能作为变量

表达式中包含变量就代表这个表达式在运行得到结果时需要从外部获取数据，如果不能正确的从数据源读取到数据，运行就会抛出异常；
``` java
RunnerUtil.run(" 'Hello, ' + name "); // 抛出异常

Map data = new HashMap();
data.put("name", "Li Lei!");

RunnerUtil.run(" 'Hello, ' + name ", data); // "Hello, Li Lei!"
```
7. 链式取值：链式语法与 JavaScript 很相似
``` java
HashMap data = new HashMap(); 

ArrayList list = new ArrayList(); 
list.add(true); 
list.add(false); 
list.add(25); 
list.add('隔壁老王'); 

HashMap map = new HashMap(); 
map.put("name", "小四"); 
map.put("index", 2); 
map.put(true, "true 是 Boolean 类型作为键"); 

data.put("list", list); 
data.put("map", map); 

RunnerUtil.run("map.name", data); // "小四"

RunnerUtil.run("map['name']", data); 
// "小四" （也可以这样取值）

RunnerUtil.run("list[ 2 ]", data);
// 25 （索引取值需要用方括号包裹） 

RunnerUtil.run("list[3]", data);
// "隔壁老王" （索引取值需要用方括号包裹） 

RunnerUtil.run("list[map.index]", data); // 25
// （这是高级点的用法，方括号包含另一个表达式
// 返回值是一个索引，然后返回索引指向的值）

RunnerUtil.run("[true]", data); // "true 是 Boolean 类型作为键"
// 如果不用方括号包括，true 就是一个直接值，返回 true
// 那么问题来了：
// 如果传入的数据不是 Map 或 POJO，而是 List 或数组怎么办呢？
RunnerUtil.run(" [1] ", list); // false
// 啊……唐宗宋祖，略显风骚！

// 这种链式语法与 JavaScript 很相似
```
8. 运行方法：目前只能运行无参和一个参数的方法，变长参数的方法支持不完善，慎用。
``` java
这里的数据 data 继续用上一条的 data，具体数据不写了

RunnerUtil.run("map.size()", data); // 3
RunnerUtil.run("map.get('name')", data); // "小四" 
RunnerUtil.run("map.get('name').length()", data); // 2
RunnerUtil.run("map.name.length()", data); // 2
RunnerUtil.run(" [3].length() ", list); // 4
// 唐宗宋祖，又显风骚！
```
9. 运行静态方法： @ ；运行静态方法需要用到“@”符号作为标记。目前也不支持多参数方法调用。

当你打开源码会发现这是一整个独立的工具库，很多方法和 commons-lang 包内容相似（个人认为不是重复造轮子，也有很多不同的和不如的）...，运行静态方法也可以运行这个工具库内的所有工具方法，暂时未将 RunnerUtil 剥离出来，也还不支持自定义的静态方法调用，不过这个工具库所提供的功能
``` java
RunnerUtil.run("@System.currentTimeMillis() ");
// 15.....（一个毫秒数）
RunnerUtil.run("@Objects.toString(25) "); // "25"
```

综上，就是这个工具库所支持的字符串表达式运算了，以上所列举的运算可以嵌套、连接、但是不能交叉的进行运算。

也考虑过使用 Java 内置的 js 引擎，但是 js 引擎的效率非常低（写这个初衷就是为了下面的[ExcelUtil](#ExcelUtil)导出，数据量一大直接就“卡死”了），
这个运算器现在功能相对独立和完善，运行效率也不错，最关键的是能直接与 Java 进行数据交互，完全可独立使用。

接下来要做的是加入的功能是多参数方法调用，希望对大家的日常开发有所帮助，也希望大家给点意见，如出现 BUG 一定在最快的时间内修改，谢谢大家啦啦啦！！

### 更多示例请移步：
1. [RunnerUtilTestTest](https://github.com/xua744531854/moon-util/blob/master/src/test/java/com/moon/util/compute/RunnerUtilTestTest.java)
2. [ParseDelimitersTestTest](https://github.com/xua744531854/moon-util/blob/master/src/test/java/com/moon/util/compute/core/ParseDelimitersTestTest.java)
3. [ParseCoreTestTest](https://github.com/xua744531854/moon-util/blob/master/src/test/java/com/moon/util/compute/core/ParseCoreTestTest.java)

### ExcelUtil:
这是一个 Excel 导出工具，这个导出灵感来自于 html 的 table，同时借鉴了流行前端框架 angularJs 1.x 的思想
（其实现在 vue 更流行，不过当初写 ExcelUtil 的时候还不会 vue），
它内部核心是采用了上面的 RunnerUtil 能运行表达式的功能，当初写 RunnerUtil 的初衷就是为了这个导出，
如果采用 Java 内置脚本引擎会“卡死的”，但 RunnerUtil 的功能相对完善，完全可单独使用。下面介绍一下 ExcelUtil 的使用方法：
``` java
  /*
   * 这里可以通过 type 指定将要生成的 excel 类型；
   * value 是一个数组，用于生成多个 sheet
   */
  @TableExcel(type = TableExcel.Type.XLS, value = {
    // 通过指定 sheetName，设置 sheet 页的名字 
    // 由于采用 RunnerUtil 运行，表达式内应该是一个返回字符串的数据，故需要用单引号包裹
    @TableSheet(sheetName = "'人员信息'", value = {
      // 这里引入了作用域的概念，每一层都可以使用“var”定义一个自己单独的作用域
      // 同一级作用域互不影响，var 的声明方式一共有四种，在下面有详细介绍
      // 根据 RunnerUtil 的语法，这儿声明的是一个 ArrayList
      @TableRow(var = "in = {'序号', '姓名', '性别', '年龄', '电话'}", value = {
        // 中间的“in”是一个迭代符号，由于灵感来自 JavaScript，故引入了 js 的迭代方式
        // 但是“in”并不是一个关键字，任然可作为普通变量使用
        // 中间作为迭代符号的 in 两端至少各有一个空格
        @TableCell(var = "(in, $index) in in", value = "$name"),
      }),
      // 冒号与上面提到的“in”迭代器的意义完全一样，因为冒号是 java 自身的迭代符号
      // 冒号两端不需要有空格
      @TableRow(var = "($item, index):employees", value = {
        @TableCell("index + 1"),
        @TableCell("$item.name"),
        @TableCell("$item.sex"),
        @TableCell("$item.age"),
        @TableCell("$item.mobile"),
      }),
      // 如果是一个数字则循环指定数字代表次数
      // 这里 skipRows 代表将在这儿空出一行 
      @TableRow(skipRows = "1", var = "$num:10", value = {
        @TableCell("$num"),
        @TableCell("$num+1"),
        @TableCell("$num+2"),
        @TableCell("$num+3", rowspan = "2"), // 合并行
        @TableCell("$num+4", colspan = "3"), // 合并列
      }, when = "$num==1"),// 渲染条件，true 和 false 表示渲染或不渲染
    }),
  })
 public Workbool exportExcel(Object data){
   // TableExcel 只能注解在调用的方法上
   // 通过 render 方法执行渲染过程
   return ExcelUtil.render(data);
   
   // 可使用 renderTo 方法指定 Workbook
   // return ExcelUtil.renderTo(new XSSFWorkbook(), data);
 }
```
- 说明一下：
TableRow 的 skipRows 属性默认是 0，可不写。注意这儿的 var 参数，在注解中引入的作用域的概念，sheet、row、cell 均有自己的作用域，
下层作用域不影响上层作用域的数据，但是相同变量名下层作用域会优先于上层作用域。

- var 有以下几种表示方式：
1. var = "";  var = "   " 等，空白字符串，没有任何意义；
2. var = "$varName = expression"; 这个表达式表示用 expression 代表的表达式从上层作用域取得对应的值，并命别名为 $varName，供下层作用域使用，相当于声明一个变量；
3. var = "$varName in 100"; 这是用了 in 表达式，代表一个迭代器，可迭代以下数据：Iterable、Iterator、String、int、Map、数组、JavaBean 按字段迭代；
> 但是 in 并不是关键字，仍然可以是变量名，如：var = "in in in.employee"

4. var = "$varName:100";这儿将 in 替换成了英文冒号，实际上作用完全一样，只是 in 的两端需要各有一个空格，因为参考的 JavaScript 是用 in 迭代，而 Java 是用“:”迭代；
> 通常 Iterator （迭代器模式）可用于超大数据导出，int 可迭代对应次数，String 将认为是一个 char 字符数组进行迭代；

5. var = "($item, $key, $index, $size, $isFirst, $isLast) in collectExpression";可获取迭代项、键名、索引、size、是否第一项、是否最后一项
- 使用和效果示例：

![https://raw.githubusercontent.com/xua744531854/files/master/images/1542364910(1).png](https://raw.githubusercontent.com/xua744531854/files/master/images/1542364910(1).png)

![https://raw.githubusercontent.com/xua744531854/files/master/images/1542364958(1).png](https://raw.githubusercontent.com/xua744531854/files/master/images/1542364958(1).png)

- 下面列出一个渲染十列数据的性能测试表（本机环境 i7-8700 16G Win10）：
150 万行以上不建议能用 xls 格式了（180 万行直接 OOM），500 万行以上 TableExcel 的 type 值应为 SUPER；

|行数（万行）|生成数据耗时（ms）|write到文件耗时（ms）|总耗时（ms）|
|-----------:|-----------------:|--------------------:|-----------:|
|  100 |   6,182 |   5,565 |    11,747 |
|  300 |  14,800 |  16,693 |    31,493 |
|  500 |  25,876 |  27,317 |    53,193 |
|  700 |  36,121 |  42,171 |    78,292 |
|  999 |  53,532 |  54,745 |   108,277 |
| 4000 | 240,453 | 271,832 |   512,285 |
| 6000 | 366,987 | 423,351 |   790,338 |
| 8000 | 528,654 | 498,490 | 1,027,144 |

从上面的数据看出，数据量和时间呈正相关性，接近线性关系，100 万行数据生成数据耗时 6s，总耗时 12s，
绝大多数业务场景下可以做到“瞬间”生成表格，没有太明显的延迟，还是可以接受的！哈哈哈哈.

更多功能：
- 可以为 TableCell 指定合并行列（用 colspan、rowspan 属性，注意只能返回数字）
