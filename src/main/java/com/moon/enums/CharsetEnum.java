package com.moon.enums;

import java.nio.charset.Charset;

/**
 * @author benshaoye
 * @date 2018/9/16
 */
public enum CharsetEnum {
    /**
     * 北美、西欧、拉丁美洲、加勒比海、加拿大、非洲
     */
    ISO_8859_1("ISO-8859-1"),
    /**
     * 东欧
     */
    ISO_8859_2("ISO-8859-2"),
    /**
     * SE Europe、世界语、其他杂项
     */
    ISO_8859_3("ISO-8859-3"),
    /**
     * 斯堪的纳维亚/波罗的海（以及其他没有包括在 ISO-8859-1 中的部分）
     */
    ISO_8859_4("ISO-8859-4"),
    /**
     * 使用古代斯拉夫语字母表的语言，比如保加利亚语、白俄罗斯文、俄罗斯语、马其顿语
     */
    ISO_8859_5("ISO-8859-5"),
    /**
     * 使用阿拉伯字母的语言
     */
    ISO_8859_6("ISO-8859-6"),
    /**
     * 现代希腊语，以及由希腊语衍生的数学符号
     */
    ISO_8859_7("ISO-8859-7"),
    /**
     * 使用希伯来语的语言
     */
    ISO_8859_8("ISO-8859-8"),
    /**
     * 土耳其语。除了土耳其字符取代了冰岛文字，其它与 ISO-8859-1 相同。
     */
    ISO_8859_9("ISO-8859-9"),
    /**
     * 拉普兰语、日耳曼语、爱斯基摩北欧语
     */
    ISO_8859_10("ISO-8859-10"),
    /**
     * 与 ISO 8859-1 类似，欧元符号和其他一些字符取代了一些较少使用的符号
     */
    ISO_8859_15("ISO-8859-15"),
    /**
     * 日本语
     */
    ISO_2022_JP("ISO-2022-JP"),
    /**
     * 日本语
     */
    ISO_2022_JP_2("ISO-2022-JP-2"),
    /**
     * 韩语
     */
    ISO_2022_KR("ISO-2022-KR"),
    /**
     * UTF8 中的字符可以是 1-4 个字节长。
     * UTF-8 可以表示 Unicode 标准中的任意字符。
     * UTF-8 向后兼容 ASCII。UTF-8 是网页和电子邮件的首选编码。
     */
    UTF_8("UTF-8"),
    /**
     * 16 比特的 Unicode 转换格式是一种 Unicode 可变字符编码，能够对全部 Unicode 指令表进行编码。
     * UTF-16 主要被用于操作系统和环境中，比如微软的 Windows 2000/XP/2003/Vista/CE 以及 Java 和 .NET 字节代码环境。
     */
    UTF_16("UTF-16");

    private final String text;

    CharsetEnum(String text) {
        this.text = text;
    }

    public String text() {
        return text;
    }

    public Charset charset() {
        return forName(this.text);
    }

    public static Charset forName(String name) {
        return Charset.forName(name);
    }
}
