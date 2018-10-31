package com.moon.util.iterators;

import com.moon.io.FileUtil;
import com.moon.io.IOUtil;
import com.moon.lang.StringUtil;
import com.moon.util.ResourceUtil;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * @author benshaoye
 * @date 2018/9/11
 */
public class TextReaderIterator implements Iterator<String> {

    private BufferedReader reader;
    private boolean initSuccess = false;
    private boolean autoClose = true;

    private String currentString;

    private TextReaderIterator(Reader reader, boolean autoClose) {
        if (reader != null) {
            this.reader = IOUtil.getBufferedReader(reader);
            if (this.reader != null) {
                this.initSuccess = true;
                this.readLine();
            }
        }
        this.autoClose = autoClose;
    }

    public TextReaderIterator(Reader reader) {
        this(reader, false);
    }

    public TextReaderIterator(InputStream inputStream) {
        this(inputStream, false);
    }

    private TextReaderIterator(InputStream inputStream, boolean autoClose) {
        this(IOUtil.getReader(inputStream), autoClose);
    }

    public TextReaderIterator(InputStream inputStream, String charset) {
        this(inputStream, charset, false);
    }

    private TextReaderIterator(InputStream inputStream, String charset, boolean autoClose) {
        this(inputStream, Charset.forName(charset), autoClose);
    }

    public TextReaderIterator(InputStream inputStream, Charset charset) {
        this(inputStream, charset, false);
    }

    private TextReaderIterator(InputStream inputStream, Charset charset, boolean autoClose) {
        this(IOUtil.getReader(inputStream, charset), autoClose);
    }

    public TextReaderIterator(File file) {
        this(FileUtil.getFileInputStream(file), true);
    }

    public TextReaderIterator(File file, String charset) {
        this(FileUtil.getFileInputStream(file), charset, true);
    }

    public TextReaderIterator(File file, Charset charset) {
        this(FileUtil.getFileInputStream(file), charset, true);
    }

    public TextReaderIterator(CharSequence filePath) {
        this(ResourceUtil.getResourceAsInputStream(StringUtil.trimToNull(filePath)), true);
    }

    public TextReaderIterator(CharSequence filePath, String charset) {
        this(ResourceUtil.getResourceAsInputStream(StringUtil.trimToNull(filePath)), charset, true);
    }

    public TextReaderIterator(CharSequence filePath, Charset charset) {
        this(ResourceUtil.getResourceAsInputStream(StringUtil.trimToNull(filePath)), charset, true);
    }

    @Override
    public boolean hasNext() {
        return this.initSuccess && this.currentString != null;
    }

    @Override
    public String next() {
        String tempString = this.currentString;

        this.readLine();
        if (this.currentString == null) {
            this.remove();
        }
        return tempString;
    }

    @Override
    public void remove() {
        if (this.reader != null && this.autoClose) {
            try {
                this.reader.close();
                this.reader = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void readLine() {
        try {
            this.currentString = this.reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void finalize() {
        this.remove();
    }
}
