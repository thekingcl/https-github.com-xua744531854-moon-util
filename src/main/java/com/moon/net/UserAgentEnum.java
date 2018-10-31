package com.moon.net;

/**
 * 基本格式：
 * 浏览器标识 (操作系统标识;安全等级标识;浏览器语言) 渲染引擎标识版本信息
 * <p>
 * 操作系统标识：
 * ---------------------------------------------------------
 * | 操作系统标识                                            |
 * ---------------------------------------------------------
 * | Windows  | Windows NT 10.0       —— Windows 10        |
 * |          | Windows NT 6.1        —— Windows 7         |
 * |          | Windows NT 6.0        —— Windows vista     |
 * |          | Windows NT 5.2        —— Windows 2003      |
 * |          | Windows NT 5.1        —— Windows xp        |
 * |          | Windows NT 5.0        —— Windows 2000      |
 * |          | Windows ME                                 |
 * |          | Windows 98                                 |
 * ---------------------------------------------------------
 * | Mac      | Macintosh;PPC Mac OS X                     |
 * |          | Macintosh;Intel Mac OS X                   |
 * ---------------------------------------------------------
 * | Linux    | X11;Linux ppc                              |
 * |          | X11;Linux ppc64                            |
 * |          | X11;Linux i686                             |
 * |          | X11;Linux x86_64                           |
 * ---------------------------------------------------------
 * | FreeBSD  | X11;FreeBSD(version no.) i386              |
 * |          | X11;FreeBSD(version no.) AMD64             |
 * ---------------------------------------------------------
 * | Solaris  | X11;SunOS i86pc                            |
 * |          | X11;SunOS sun4u                            |
 * ---------------------------------------------------------
 * <p>
 * 加密等级标识：
 * N：无安全加密；
 * I：弱安全加密；
 * U：强安全加密；
 * <p>
 * 渲染引擎版本信息：
 * 目前主流渲染引擎有：Gecko、WebKit、KHTML、Presto、Trident、Tasman 等
 * 格式：渲染引擎/版本信息
 * <p>
 * 版本信息：
 * 显示浏览器的真实版本信息，格式为：浏览器/版本信息
 *
 * @author benshaoye
 */
public enum UserAgentEnum {
    Chrome69("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.81 Safari/537.36");

    private final String userAgent;

    UserAgentEnum(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getText() {
        return userAgent;
    }
}
