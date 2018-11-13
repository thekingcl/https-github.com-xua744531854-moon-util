# 介绍
此工具类主要为 JDK 常用操作提供便利。
写这个工具类是为了解决工作中的一些操作在已有的工具类（如：commons）
不能完全满足需求的问题，比如类型转换，CollectionUtil.isNotEmpty() 等，
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
此类是用于运行一个字符串表达式（如：1+1，2+5 等）的工具类，经过大约两个星期的实现，目前包括以下数据类型和运算

#### 数据类型
- 关键字：null、true、false；（boolean 型数据）

- 数字类型：参与运算的数字类型只有 int 型和 double 型，带小数点的自动识别为 double，否则是 int；
> 表示数字的字符应该是连续的，如：25、36.9 等；如果是不连续的会报错，如：2 5、36 .9

- 字符串，用单引号双引号包裹的都是字符串，RunnerUtil 运行的是字符串表达式，由于 Java 中双引号表示的是字符串，为了书写方便，
就将单引号“征用”来表示字符串了，当然也不再支持 char 类型数据（用的本来也不多），如：
> 'name'、"Hello World！"、'Hi, Li Lei!'

- 数组，实际上是 ArrayList
> 示例：
<br>RunnerUtil.run("{}"); // 返回一个空 ArrayList
<br>RunnerUtil.run("{1,,2,}"); // 返回包含 1、null、2 共三项的 ArrayList
<br>RunnerUtil.run("{1,,2,null,false,true}");

- Map，实际上是 HashMap

这儿有一点需要注意，可表示键的数据类型可以是数字类型（int、double）、boolean、null 和 字符串；
在表示键时，字符串不必须用双引号或单引号包裹，除非包含空格，
表示值的字符串必须用单引号或双引号包裹；（与 JavaScript 的对象相似，不同的是 js 的所有键均是字符串）
无论键或者值均不能省略（与数组不同，两个连续逗号中间没有任何字符会默认插入一个 null 值）
> 示例：
<br>RunnerUtil.run("{:}"); // 返回一个空 HashMap
<br>RunnerUtil.run("{key:''}"); // 包含一个值是空字符串，键是字符串 "key" 的映射
<br>RunnerUtil.run("{'':null, true: '今天天气很棒', 'name': '张三', age: 23, 36.5: ''}");
分别包含："" - null，true - "今天天气很棒"，"name" - "张三"，"age" - 23，36.5 - "" 这些键值对

#### 运算类型
- 基本四则混合运算；+、-、*、/、()；
> 示例：
<br>RunnerUtil.run("1+1"); // 2
<br>RunnerUtil.run("1+(2*5)"); // 11

- 位运算：^、&、|、<<、>>；
> 示例：
<br>RunnerUtil.run("1^1");
<br>RunnerUtil.run("1&1");
<br>RunnerUtil.run("1|1");
<br>RunnerUtil.run("1<<1");
<br>RunnerUtil.run("1>>1");

- 比较运算：==、>、>=、<、<=；
> 示例：
<br>RunnerUtil.run("1+1==2");
<br>RunnerUtil.run("1+1<=2");
<br>RunnerUtil.run("1+1>=2");

- 逻辑运算：&&、||、!；
> 示例：
<br>RunnerUtil.run("1+1==2 && 5 > 4");

- 变量：表达式作为第一个参数，数据作为第二个参数
> 示例：
<br>HashMap data = new HashMap();
<br>data.put("name", "张三");
<br>
<br>RunnerUtil.run("name + '学习成绩优异，一直在班上名列前茅！'", data); // "张三学习成绩优异，一直在班上名列前茅！"

- 链式取值：链式取值可以是数组、List、Map、JavaBean、ResultSet
> 示例：
<br>HashMap data = new HashMap();
<br>ArrayList list = new ArrayList();
<br>list.add(true);
<br>list.add(false);
<br>list.add(25);
<br>list.add('隔壁老王');
<br>data.put("list", list);
<br>
<br>HashMap map = new HashMap();
<br>map.put("name", "小四");
<br>map.put("index", 2);
<br>data.put("map", map);
<br>
<br>RunnerUtil.run("map.name", data); // "小四"
<br>RunnerUtil.run("list[ 2 ]", data); // 25 （索引取值需要用方括号包裹）
<br>RunnerUtil.run("list[3]", data); // "隔壁老王" （索引取值需要用方括号包裹）
<br>RunnerUtil.run("list[map.index]", data); // 25 （这是高级点的用法，方括号包含另一个表达式，返回值是一个索引，然后返回索引指向的值）

- 运行简单成员方法：目前可以运行无参和包括一个参数的方法
> 示例：
<br>RunnerUtil.run("'name'.length()"); // 4
<br>
<br>HashMap data = new HashMap();
<br>ArrayList list = new ArrayList();
<br>list.add(true);
<br>list.add(false);
<br>list.add(25);
<br>list.add('隔壁老王');
<br>data.put("list", list);
<br>RunnerUtil.run("list.size()"); // 4
<br>RunnerUtil.run("list.get(2)"); // 25

- 用“@”符号运行 JDK 常用包下和本工具库的静态方法，目前同样只支持无参和包括一个参数的方法
> 示例：
<br>RunnerUtil.run("@System.currentTimeMillis()");
<br>RunnerUtil.run("@DateUtil.nowDate()");
<br>RunnerUtil.run("@Objects.toString(25)"); // "25"

- 综合运算，对上面所列出的运算汇总
> 示例：
<br>RunnerUtil.run("@System.currentTimeMillis() + ' 36 ' + @Objects.toString(25)"); // "15369548 36 25",前面的毫秒数就意思意思
<br>
<br>HashMap data = new HashMap();
<br>ArrayList list = new ArrayList();
<br>list.add(true);
<br>list.add(false);
<br>list.add(25);
<br>list.add('隔壁老王');
<br>data.put("list", list);
<br>
<br>HashMap map = new HashMap();
<br>map.put("name", "小四");
<br>map.put("index", 2);
<br>data.put("map", map);
<br>
<br>RunnerUtil.run("list.get(2) + 36 + map.name + map.name.length()"); // "61小四4"

综上就是 RunnerUtil 所支持的功能，以后将进一步完成多参数方法的调用。
在开发这个计算器过程中层考虑过 Java 内置的脚本引擎，但经过测试，脚本引擎的功能虽然强大，
但是采用脚本引擎运算这些简单表达式的性能却很低，希望此工具类能对用户有所帮助。

### ExcelUtil:
这是一个 Excel 导出工具，这个导出灵感来自于 html 的 table，同时采用了目前流行前端框架 angularJs 1.x 的思想
（其实现在 vue 更流行，不过当初写 ExcelUtil 的时候还不会 vue），
它内部核心是采用了上面的 RunnerUtil 能运行表达式的功能，当初写 RunnerUtil 的初衷就是为了这个导出，
如果采用 Java 内置脚本引擎会“卡死的”，但 RunnerUtil 的功能相对完善，完全可单独使用。下面介绍一下 ExcelUtil 的使用方法：
> 使用 ExcelUtil.render(/* data */) 时需要在调用位置的方法用 @TableExcel 注解（名字上也体现了灵感的来源）
// 可用 type 指定生成的 excel 类型
<br>@TableExcel(type = TableExcel.Type.XLSX, value = { 
// 可用指定 sheet 名，注意由于 RunnerUtil 运行，所以用单引号包裹的才是字符串
<br>&ngsp;&ngsp;@TableSheet(sheetName = "'新建 Sheet 页'", value = {
// 相对 html 的 table，sheet 可认为有无限列，所以下一行不会“自动换行”，需要手动“跳过”行
<br>&ngsp;&ngsp;&ngsp;&ngsp;@TableRow(skipRows = "0", value = {
<br>&ngsp;&ngsp;&ngsp;&ngsp;&ngsp;&ngsp;@TableCell("'序号'"),
<br>&ngsp;&ngsp;&ngsp;&ngsp;&ngsp;&ngsp;@TableCell("'姓名'"),
<br>&ngsp;&ngsp;&ngsp;&ngsp;&ngsp;&ngsp;@TableCell("'年龄'"),
<br>&ngsp;&ngsp;&ngsp;&ngsp;&ngsp;&ngsp;@TableCell("'性别'"),
<br>&ngsp;&ngsp;&ngsp;&ngsp;&ngsp;&ngsp;@TableCell("'地址'"),
<br>&ngsp;&ngsp;&ngsp;&ngsp;}),
<br>// skipRows 默认是 0，可不写。注意这儿的 var 参数，在注解中引入的作用域的概念，sheet、row、cell 均有自己的作用域，
<br>// 下层作用域不影响上层作用域的数据，但是相同变量名下层作用域会优先于上层作用域
<br>// var 有四种表示方式：
<br>// 1、var = "";  var = "   " 等，空白字符串，没有任何意义；
<br>// 2、var = "$varName = expression"; 这个表达式表示用 expression 代表的表达式从上层作用域取得对应的值，并命别名为 $varName，供下层作用域使用，相当于声明一个变量；
<br>// 3、var = "$varName in 100"; 这是用了 in 表达式，代表一个迭代器，可迭代以下数据：Iterable、Iterator、String、int、Map、数组、JavaBean 按字段迭代；
<br>// 4、var = "$varName:100";这儿将 in 替换成了英文冒号，实际上作用完全一样，只是 in 的两端需要各有一个空格；
<br>// 通常 Iterator 可用于超大数据导出，int 可迭代对应次数，String 将认为是一个 char 字符数组进行迭代；
<br>&ngsp;&ngsp;&ngsp;&ngsp;@TableRow(var = "$var in 100", {
<br>&ngsp;&ngsp;&ngsp;&ngsp;&ngsp;&ngsp;@TableCell("$var + 1"),
<br>&ngsp;&ngsp;&ngsp;&ngsp;&ngsp;&ngsp;@TableCell("'张三'"),
<br>&ngsp;&ngsp;&ngsp;&ngsp;&ngsp;&ngsp;@TableCell("'25'"),
<br>&ngsp;&ngsp;&ngsp;&ngsp;&ngsp;&ngsp;@TableCell("'男'"),
<br>&ngsp;&ngsp;&ngsp;&ngsp;&ngsp;&ngsp;@TableCell("'杭州西湖美景'"),
<br>&ngsp;&ngsp;&ngsp;&ngsp;}),
<br>&ngsp;&ngsp;})
<br>})
<br>public Workbook exportExcel(Object data){
<br>&ngsp;&ngsp;return ExcelUtil.render();// 运行将生成一个包含一行标题，共 100 行数据的 Workbook 文档，然后可进行进一步操作
<br>&ngsp;&ngsp;// 也可以使用 ExcelUtil.renderTo(workbook); 方法，将以上数据生成到指定的 Workbook 文档；
<br>}
<br>

下列出一个渲染十列数据的性能测试表（本机环境 i7-8700 16G Win10）：
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