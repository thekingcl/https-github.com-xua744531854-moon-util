package com.moon.io;

import com.moon.enums.Const;
import com.moon.lang.StringUtil;
import com.moon.lang.SupportUtil;
import com.moon.lang.ThrowUtil;
import com.moon.util.IteratorUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author benshaoye
 * @date 2018/9/11
 */
public final class FileUtil {
    private FileUtil() {
        ThrowUtil.noInstanceError();
    }


    /**
     * 将 sourceFilepath 绝对路径所指向的文件或目录复制到 targetDir
     *
     * @param sourceFilepath 源路径
     * @param targetDir      目标目录
     */
    public static void copyToDirectory(String sourceFilepath, String targetDir) {
        copyToDirectory(new File(sourceFilepath), new File(targetDir));
    }

    /**
     * 将 sourceFilepath 绝对路径所指向的文件复制到 targetDir，并将文件命名为 newFileName
     * 新文件名设置只针对复制文件有效，如果是复制文件夹，将使用原文件名
     *
     * @param sourceFilepath 源路径
     * @param targetDir      目标目录
     */
    public static void copyToDirectory(String sourceFilepath, String targetDir, String newFileName) {
        copyToFile(new File(sourceFilepath), new File(targetDir, newFileName));
    }

    public static void copyToDirectory(File sourceFile, final File targetDir) {
        if (sourceFile == null || targetDir == null) {
            return;
        } else if (sourceFile.isFile()) {
            copyToFile(sourceFile, new File(targetDir, sourceFile.getName()));
        } else if (sourceFile.isDirectory()) {
            String root = sourceFile.getParent();
            int len = root.length();
            IteratorUtil.forEach(traverseDirectory(sourceFile), file ->
                copyToFile(file, new File(targetDir, file.getAbsolutePath().substring(len))));
        }
    }

    public static void copyToFile(String sourceFilepath, String targetFilePath) {
        copyToFile(new File(sourceFilepath), new File(targetFilePath));
    }

    public static void copyToFile(File sourceFile, File targetFile) {
        if (exists(sourceFile)) {
            if (sourceFile.isDirectory()) {
                copyToDirectory(sourceFile, targetFile.getParentFile());
            } else if (sourceFile.isFile()) {
                try (FileOutputStream output = getFileOutputStream(targetFile)) {
                    FileInputStream input = getFileInputStream(sourceFile);
                    byte[] buffer = new byte[10240];
                    IteratorUtil.forEach(input, buffer, len -> IOUtil.write(output, buffer, 0, len));
                } catch (IOException e) {
                    ThrowUtil.throwRuntime(e);
                }
            }
        }
    }

    /**
     * 获取文件的输出流，如果文件不存在，会创建文件以及目录结构，创建失败返回空
     *
     * @param file
     */
    public static FileOutputStream getFileOutputStream(File file) {
        if (createNewFile(file)) {
            try {
                return new FileOutputStream(file);
            } catch (IOException e) {
                return ThrowUtil.throwRuntime(e);
            }
        }
        return ThrowUtil.throwRuntime("File not exist: " + file);
    }

    public static FileOutputStream getFileOutputStream(String filePath) {
        return getFileOutputStream(new File(StringUtil.trimToEmpty(filePath)));
    }

    public static FileInputStream getFileInputStream(String filePath) {
        return getFileInputStream(new File(StringUtil.trimToEmpty(filePath)));
    }

    /**
     * 从已知文件获取输入流，如不存在返回空
     *
     * @param file
     */
    public static FileInputStream getFileInputStream(File file) {
        try {
            return new FileInputStream(file);
        } catch (Exception e) {
            return ThrowUtil.throwRuntime(e);
        }
    }

    /**
     * 删除文件，返回 file 所指向的文件是否还存在
     *
     * @param file
     * @return
     */
    public static boolean delete(File file) {
        try {
            if (file != null && file.exists()) {
                return file.delete();
            }
            return true;
        } catch (SecurityException e) {
            return !file.exists();
        }
    }

    public static boolean mkdirs(String path) {
        return mkdirs(new File(path));
    }

    public static boolean mkdirs(File dir) {
        if (dir.exists()) {
            if (dir.isFile()) {
                return ThrowUtil.throwRuntime("The target exist and is a file: " + dir);
            } else {
                return true;
            }
        } else {
            return dir.mkdirs();
        }
    }

    /**
     * 创建文件以其目录结构，返回创建成功与否的状态
     *
     * @param file
     */
    public static boolean createNewFile(File file) {
        if (!exists(file)) {
            File parent = file.getParentFile();
            if (parent.exists() || parent.mkdirs()) {
                try {
                    return file.createNewFile();
                } catch (IOException e) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean createNewFile(String filepath) {
        return createNewFile(new File(filepath));
    }

    public final static String formatPath(String filePath) {
        if (filePath != null) {
            int index = 0;
            char[] chars = null;
            char WIN = Const.WIN_FileSeparator_Char;
            char DFT = Const.App_FileSeparatorChar;
            for (int i = 0, len = filePath.length(); i < len; i++) {
                char ch = filePath.charAt(i);
                if (!Character.isWhitespace(ch)) {
                    chars = SupportUtil.setChar(chars, index++, ch == WIN ? DFT : ch);
                }
            }
            return chars == null ? null : new String(chars, 0, index);
        }
        return null;
    }

    public static boolean exists(File file) {
        return file != null && file.exists();
    }

    public static boolean exists(String filePath) {
        return filePath != null && new File(filePath).exists();
    }

    /**
     * 遍历指定目录的文件列表，如遇不可访问的安全保护会打印相应错误信息，但不会影响程序执行
     *
     * @param dirPath
     */
    public static List<File> traverseDirectory(String dirPath) {
        return new DirectoryTraveller().traverse(dirPath).get();
    }

    public static List<File> traverseDirectory(File dirPath) {
        return new DirectoryTraveller().traverse(dirPath).get();
    }

    public static List<File> traverseAll(File... dirs) {
        Traveller<File> traveller = new DirectoryTraveller();
        for (int i = 0; i < dirs.length; i++) {
            traveller.traverse(dirs[i]);
        }
        return traveller.get();
    }

    public static List<File> traverseAll(List<File> dirs) {
        Traveller<File> traveller = new DirectoryTraveller();
        for (int i = 0; i < dirs.size(); i++) {
            traveller.traverse(dirs.get(i));
        }
        return traveller.get();
    }

    /**
     * 返回文件大小(单位 Bit)
     *
     * @param file
     * @return
     */
    public static long lengthToBit(File file) {
        return lengthToB(file) << 3;
    }

    /**
     * 返回文件大小(单位 B)
     *
     * @param filepath
     * @return
     */
    public static long length(String filepath) {
        return lengthToB(new File(filepath));
    }

    /**
     * 返回文件大小(单位 B)
     *
     * @param file
     * @return
     */
    public static long lengthToB(File file) {
        return file.length();
    }

    /**
     * 返回文件大小(单位 KB)
     *
     * @param file
     * @return
     */
    public static long lengthToKB(File file) {
        return lengthToB(file) >> 10;
    }

    /**
     * 返回文件大小(单位 MB)
     *
     * @param file
     * @return
     */
    public static long lengthToMB(File file) {
        return lengthToKB(file) >> 10;
    }

    /**
     * 返回文件大小(单位 GB)
     *
     * @param file
     * @return
     */
    public static long lengthToGB(File file) {
        return lengthToMB(file) >> 10;
    }

    /**
     * 返回文件大小(单位 TB)
     *
     * @param file
     * @return
     */
    public static long lengthToTB(File file) {
        return lengthToGB(file) >> 10;
    }

    /**
     * 返回文件大小(单位 PB)
     *
     * @param file
     * @return
     */
    public static long lengthToPB(File file) {
        return lengthToTB(file) >> 10;
    }

    /**
     * 返回文件大小(单位 EB)
     *
     * @param file
     * @return
     */
    public static long lengthToEB(File file) {
        return lengthToPB(file) >> 10;
    }

    /**
     * 返回文件大小(单位 ZB)
     *
     * @param file
     * @return
     */
    public static long lengthToZB(File file) {
        return lengthToEB(file) >> 10;
    }

    /**
     * 深度删除所有文件
     *
     * @param dirPath
     * @return
     */
    public static boolean deleteAllFiles(String dirPath) {
        return deleteAllFiles(new File(dirPath));
    }

    /**
     * 深度删除所有文件
     *
     * @param dirFile
     * @return
     */
    public static boolean deleteAllFiles(File dirFile) {
        if (dirFile == null) {
            return true;
        }
        if (dirFile.isDirectory()) {
            List<File> files = traverseDirectory(dirFile);
            for (File file : files) {
                delete(file);
            }
        }
        if (dirFile.isFile()) {
            return dirFile.delete();
        }
        return !dirFile.exists();
    }
}
