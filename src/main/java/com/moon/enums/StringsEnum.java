package com.moon.enums;

import com.moon.lang.StringUtil;

/**
 * @author benshaoye
 * @date 2018/9/11
 */
public interface StringsEnum {
    /**
     * 空字符串
     */
    String EMPTY = new String();
    /**
     * 点号、句号
     */
    String DOT = String.valueOf(CharsEnum.DOT);
    /**
     * 空格
     */
    String SPACE = String.valueOf(CharsEnum.SPACE);
    /**
     * 下划线
     */
    String UNDERLINE = String.valueOf(CharsEnum.UNDERLINE);
    /**
     * 逗号
     */
    String COMMA = String.valueOf(CharsEnum.COMMA);
    /**
     * 减号
     */
    String MINUS = String.valueOf(CharsEnum.MINUS);
    /**
     * 冒号
     */
    String COLON = String.valueOf(CharsEnum.COLON);
    /**
     * 竖线
     */
    String VERTICAL_LINE = String.valueOf(CharsEnum.VERTICAL_LINE);

    /**
     * 常见英文符号：键盘上能直接敲出来的英文符号
     */
    String symbolCache = StringUtil.distinctChars("~`!@#$%^&*()_+-={}|[]\\:\";'<>?,./");
    /**
     * 常见中文符号：键盘上能直接敲出来的中文符号
     */
    String ChineseSymbolCache = StringUtil.distinctChars("~·！@#￥%…&*（）—+-={}|【】、：”；’《》？，。、");
    /**
     * 数字
     */
    String numberCache = "0123456789";
    /**
     * 小写字母
     */
    String lowerCaseCache = "abcdefghijklmnopqrstuvwxyz";
    /**
     * 大写字母
     */
    String upperCaseCache = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
}
