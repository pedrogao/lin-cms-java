package com.lin.cms.demo.extensions.file;

import org.springframework.util.DigestUtils;
import org.springframework.util.unit.DataSize;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class FileUtil {

    public static boolean isAbsolute(String str) {
        Path path = FileSystems.getDefault().getPath(str);
        return path.isAbsolute();
    }

    public static void initStoreDir(String dir) {
        String absDir;
        if (isAbsolute(dir)) {
            absDir = dir;
        } else {
            String cmd = System.getProperty("user.dir");
            Path path = FileSystems.getDefault().getPath(cmd, dir);
            absDir = path.toAbsolutePath().toString();
        }
        File file = new File(absDir);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static String getFileExt(String filename) {
        int index = filename.lastIndexOf('.');
        return filename.substring(index);
    }

    public static String getFileMD5(byte[] bytes) {
        return DigestUtils.md5DigestAsHex(bytes);
    }

    public static Long parseSize(String size) {
        DataSize singleLimitData = DataSize.parse(size);
        return singleLimitData.toBytes();
    }
}
