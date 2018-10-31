package com.moon.io;

import com.moon.enums.CharsetEnum;
import com.moon.lang.LongUtil;
import com.moon.lang.ThrowUtil;
import com.moon.lang.ref.LongAccessor;
import com.moon.util.ResourceUtil;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Objects;

import static com.moon.io.FileUtil.getFileInputStream;
import static com.moon.io.FileUtil.getFileOutputStream;
import static com.moon.lang.ThrowUtil.throwRuntime;
import static com.moon.util.IteratorUtil.forEach;

/**
 * @author benshaoye
 * @date 2018/9/11
 */
public final class IOUtil {
    private IOUtil() {
        ThrowUtil.noInstanceError();
    }

    public static final String DEFAULT_CHARSET = CharsetEnum.UTF_8.text();

    /*
     * -----------------------------------------------------------------------
     * get stream
     * -----------------------------------------------------------------------
     */

    public final static InputStream getResourceAsStream(String path) {
        return ResourceUtil.getResourceAsInputStream(path);
    }

    public static BufferedOutputStream getBufferedOutputStream(File file) {
        return getBufferedOutputStream(getFileOutputStream(file));
    }

    public static BufferedOutputStream getBufferedOutputStream(OutputStream os) {
        if (os instanceof BufferedOutputStream) {
            return (BufferedOutputStream) os;
        }
        return new BufferedOutputStream(os);
    }

    public static BufferedInputStream getBufferedInputStream(File file) {
        return getBufferedInputStream(getFileInputStream(file));
    }

    public static BufferedInputStream getBufferedInputStream(InputStream is) {
        if (is instanceof BufferedInputStream) {
            return (BufferedInputStream) is;
        }
        return new BufferedInputStream(is);
    }

    /*
     * -----------------------------------------------------------------------
     * get writer
     * -----------------------------------------------------------------------
     */

    public final static BufferedWriter getBufferedWriter(OutputStream os, Charset charset) {
        return new BufferedWriter(getWriter(os, charset));
    }

    public final static BufferedWriter getBufferedWriter(OutputStream os, String charset) {
        return getBufferedWriter(os, Charset.forName(charset));
    }

    public final static BufferedWriter getBufferedWriter(OutputStream os) {
        return getBufferedWriter(os, DEFAULT_CHARSET);
    }

    public final static BufferedWriter getBufferedWriter(Writer writer) {
        if (writer instanceof BufferedWriter) {
            return (BufferedWriter) writer;
        } else {
            return new BufferedWriter(writer);
        }
    }

    public final static BufferedWriter getBufferedWriter(File file) {
        return getBufferedWriter(getWriter(file));
    }

    public final static Writer getWriter(OutputStream os, Charset charset) {
        return getBufferedWriter(new OutputStreamWriter(os, charset));
    }

    public final static Writer getWriter(OutputStream os, String charset) {
        return getWriter(os, Charset.forName(charset));
    }

    public final static Writer getWriter(OutputStream os) {
        return getWriter(os, DEFAULT_CHARSET);
    }

    public final static Writer getWriter(File file) {
        try {
            return new FileWriter(file);
        } catch (IOException e) {
            return throwRuntime(e);
        }
    }

    /*
     * -----------------------------------------------------------------------
     * get reader
     * -----------------------------------------------------------------------
     */

    public final static BufferedReader getBufferedReader(InputStream is, Charset charset) {
        return new BufferedReader(getReader(is, charset));
    }

    public final static BufferedReader getBufferedReader(InputStream is, String charset) {
        return getBufferedReader(is, Charset.forName(charset));
    }

    public final static BufferedReader getBufferedReader(InputStream is) {
        return getBufferedReader(is, DEFAULT_CHARSET);
    }

    public final static BufferedReader getBufferedReader(Reader reader) {
        if (reader instanceof BufferedReader) {
            return (BufferedReader) reader;
        } else {
            return new BufferedReader(reader);
        }
    }

    public final static BufferedReader getBufferedReader(File file) {
        return getBufferedReader(getReader(file));
    }

    public final static Reader getReader(InputStream is, Charset charset) {
        return new InputStreamReader(is, charset);
    }

    public final static Reader getReader(InputStream is, String charset) {
        return getReader(is, Charset.forName(charset));
    }

    public final static Reader getReader(InputStream is) {
        return getReader(is, DEFAULT_CHARSET);
    }

    public final static Reader getReader(File file) {
        try {
            return new FileReader(file);
        } catch (FileNotFoundException e) {
            return throwRuntime(e);
        }
    }

    public final static BufferedReader getResourceAsReader(String path, Charset charset) {
        return getBufferedReader(getResourceAsStream(path), charset);
    }

    public final static Reader getResourceAsReader(String path, String charset) {
        return getResourceAsReader(path, Charset.forName(charset));
    }

    public final static Reader getResourceAsReader(String path) {
        return getResourceAsReader(path, DEFAULT_CHARSET);
    }

    /*
     * -----------------------------------------------------------------------
     * to string
     * -----------------------------------------------------------------------
     */

    public final static String toString(File file) {
        return toString(getBufferedReader(file));
    }

    public final static String toString(Reader reader) {
        try (StringWriter writer = new StringWriter()) {
            copy(reader, writer);
            return writer.toString();
        } catch (IOException e) {
            return throwRuntime(e);
        }
    }

    public final static String toString(InputStream in, Charset charset) {
        return toString(getBufferedReader(in, charset));
    }

    public final static String toString(InputStream in, String charset) {
        return toString(getBufferedReader(in, charset));
    }

    public final static String toString(InputStream in) {
        return toString(in, DEFAULT_CHARSET);
    }

    public final static String toString(URL url) {
        return toString(url, DEFAULT_CHARSET);
    }

    public final static String toString(URL url, String charset) {
        return toString(url, Charset.forName(charset));
    }

    public final static String toString(URL url, Charset charset) {
        try (InputStream is = url.openStream()) {
            return toString(is, charset);
        } catch (IOException e) {
            return throwRuntime(e);
        }
    }

    /*
     * -----------------------------------------------------------------------
     * copy
     * -----------------------------------------------------------------------
     */

    public static long copy(InputStream is, OutputStream stream) {
        LongAccessor accessor = LongAccessor.of();
        byte[] buffer = new byte[10240];
        forEach(is, buffer, limit -> {
            write(stream, buffer, 0, limit);
            accessor.add(limit);
        });
        flush(stream);
        return accessor.get();
    }

    public static long copy(Reader reader, Writer writer) {
        LongAccessor accessor = LongAccessor.of();
        char[] buffer = new char[5120];
        forEach(reader, buffer, limit -> {
            write(writer, buffer, 0, limit);
            accessor.add(limit);
        });
        flush(writer);
        return accessor.get();
    }

    public static long copy(InputStream is, Writer writer) {
        return copy(getBufferedReader(is), writer);
    }

    public static long copy(InputStream is, Writer writer, String charset) {
        return copy(getBufferedReader(is, charset), writer);
    }

    public static long copy(InputStream is, Writer writer, Charset charset) {
        return copy(getBufferedReader(is, charset), writer);
    }

    public static long copy(Reader reader, OutputStream os) {
        return copy(reader, getBufferedWriter(os));
    }

    public static long copy(Reader reader, OutputStream os, String charset) {
        return copy(reader, getBufferedWriter(os, charset));
    }

    public static long copy(Reader reader, OutputStream os, Charset charset) {
        return copy(reader, getBufferedWriter(os, charset));
    }

    public static long copy(InputStream is, StringBuilder sb, Charset charset) {
        return copy(getBufferedReader(is, charset), sb);
    }

    public static long copy(InputStream is, StringBuffer sb, Charset charset) {
        return copy(getBufferedReader(is, charset), sb);
    }

    public static long copy(InputStream is, StringBuilder sb, String charset) {
        return copy(getBufferedReader(is, charset), sb);
    }

    public static long copy(InputStream is, StringBuffer sb, String charset) {
        return copy(getBufferedReader(is, charset), sb);
    }

    public static long copy(InputStream is, StringBuilder sb) {
        return copy(getBufferedReader(is), sb);
    }

    public static long copy(InputStream is, StringBuffer sb) {
        return copy(getBufferedReader(is), sb);
    }

    public final static int copy(Reader reader, StringBuilder sb) {
        final int len = sb.length();
        forEach(reader, sb::append);
        return sb.length() - len;
    }

    public final static int copy(Reader reader, StringBuffer sb) {
        final int len = sb.length();
        forEach(reader, sb::append);
        return sb.length() - len;
    }

    /**
     * 将字符串写入 Writer
     *
     * @param cs
     * @param writer
     * @return
     */
    public final static int copy(CharSequence cs, Writer writer) {
        int len = cs == null ? 0 : cs.length();
        if (len > 0) {
            Objects.requireNonNull(writer);
            char[] chars = new char[len];
            cs.toString().getChars(0, len, chars, 0);
            write(writer, chars, 0, len);
        }
        return len;
    }

    /**
     * 将字符串写入输出流
     *
     * @param cs
     * @param os
     * @return
     */
    public final static int copy(CharSequence cs, OutputStream os) {
        int len = cs == null ? 0 : cs.length();
        if (len > 0) {
            write(os, cs.toString().getBytes());
        }
        return len;
    }

    /**
     * 将字符串写入输出流
     *
     * @param cs
     * @param os
     * @param charset
     * @return
     */
    public final static int copy(CharSequence cs, OutputStream os, String charset) {
        int len = cs == null ? 0 : cs.length();
        if (len > 0) {
            try {
                write(os, cs.toString().getBytes(charset));
            } catch (UnsupportedEncodingException e) {
                throwRuntime(e);
            }
        }
        return len;
    }

    /**
     * 将字符串写入输出流
     *
     * @param cs
     * @param os
     * @param charset
     * @return
     */
    public final static int copy(CharSequence cs, OutputStream os, Charset charset) {
        int len = cs == null ? 0 : cs.length();
        if (len > 0) {
            write(os, cs.toString().getBytes(charset));
        }
        return len;
    }

    /*
     * -----------------------------------------------------------------------
     * read
     * -----------------------------------------------------------------------
     */

    public final static int read(InputStream is, byte[] bytes, int start, int max) {
        try {
            return is.read(bytes, start, max);
        } catch (IOException e) {
            return throwRuntime(e);
        }
    }

    public final static int read(InputStream is, byte[] bytes) {
        return read(is, bytes, 0, bytes.length);
    }

    public final static int read(Reader reader, char[] chars, int start, int max) {
        try {
            return reader.read(chars, start, max);
        } catch (IOException e) {
            return throwRuntime(e);
        }
    }

    public final static int read(Reader reader, char[] chars) {
        return read(reader, chars, 0, chars.length);
    }

    /*
     * -----------------------------------------------------------------------
     * write
     * -----------------------------------------------------------------------
     */

    public final static void write(OutputStream os, byte[] bytes, int start, int limit) {
        try {
            os.write(bytes, start, limit);
        } catch (IOException e) {
            throwRuntime(e);
        }
    }

    public final static void write(OutputStream os, byte[] bytes) {
        write(os, bytes, 0, bytes.length);
    }

    public final static void write(Writer writer, char[] chars, int start, int limit) {
        try {
            writer.write(chars, start, limit);
        } catch (IOException e) {
            throwRuntime(e);
        }
    }

    /**
     * write a char array in to Writer of all char
     *
     * @param writer
     * @param chars
     */
    public final static void write(Writer writer, char[] chars) {
        write(writer, chars, 0, chars.length);
    }

    /*
     * -----------------------------------------------------------------------
     * flush and close
     * -----------------------------------------------------------------------
     */

    /**
     * flush Flushable
     *
     * @param flushable
     */
    public static void flush(Flushable flushable) {
        try {
            flushable.flush();
        } catch (IOException e) {
            throwRuntime(e);
        }
    }

    /**
     * close InputStream
     *
     * @param is
     */
    public static void close(InputStream is) {
        try {
            if (is != null) {
                is.close();
            }
        } catch (IOException e) {
            // ignore
        }
    }

    /**
     * close Reader
     *
     * @param reader
     */
    public static void close(Reader reader) {
        try {
            if (reader != null) {
                reader.close();
            }
        } catch (IOException e) {
            // ignore
        }
    }

    /**
     * close OutputStream
     *
     * @param os
     */
    public static void close(OutputStream os) {
        try {
            if (os != null) {
                os.flush();
                os.close();
            }
        } catch (IOException e) {
            // ignore
        }
    }

    /**
     * close Writer
     *
     * @param writer
     */
    public static void close(Writer writer) {
        try {
            if (writer != null) {
                writer.flush();
                writer.close();
            }
        } catch (IOException e) {
            // ignore
        }
    }

    /**
     * close Writer and Reader
     *
     * @param writer
     * @param reader
     */
    public static void close(Writer writer, Reader reader) {
        close(writer);
        close(reader);
    }

    /**
     * close OutputStream and InputStream
     *
     * @param outputStream
     * @param inputStream
     */
    public static void close(OutputStream outputStream, InputStream inputStream) {
        close(outputStream);
        close(inputStream);
    }

    /**
     * close all of Closeable
     *
     * @param closes
     */
    public static void close(Closeable... closes) {
        int len = closes.length;
        for (int i = 0; i < len; i++) {
            Closeable close = closes[i];
            if (close != null) {
                try {
                    if (close instanceof Flushable) {
                        ((Flushable) close).flush();
                    }
                    close.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }
}
