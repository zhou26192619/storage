package com.loar.storage.file;

import android.graphics.Bitmap;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class FileOperator {
    /**
     * @param dir      目录
     * @param fileName 文件名
     * @return
     */
    public static File createFile(String dir, String fileName) {
        File fDir = new File(dir);
        if (!fDir.exists()) {
            fDir.mkdirs();
        }
        File file = new File(fDir, fileName);
        return file;
    }

    public static boolean isExists(String filePath) {
        File fDir = new File(filePath);
        return fDir.exists();
    }

    public static boolean delete(String filePath) {
        if (filePath == null) {
            return true;
        }
        File fDir = new File(filePath);
        if (fDir.exists()) {
            return fDir.delete();
        }
        return true;
    }

    public static long fileSize(String filePath) throws Exception {
        if (isExists(filePath)) {
            File file = new File(filePath);
            return file.length();
        } else {
            throw new Exception("file does not exit");
        }
    }

    public static File save(InputStream ins, String dir, String fileName, boolean isAppend) throws IOException {
        File file = createFile(dir, fileName);
        OutputStream os = null;
        try {
            os = new FileOutputStream(file, isAppend);
            int bytesRead = 0;
            byte[] buffer = new byte[4096];
            while ((bytesRead = ins.read(buffer, 0, 4096)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } finally {
            try {
                os.close();
                ins.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }


    public static File appendToFile(String content, String dir, String fileName) throws IOException {
        File file = createFile(dir, fileName);
        if (content == null) {
            return file;
        }
        ByteArrayInputStream in = new ByteArrayInputStream(content.getBytes());
        save(in, dir, fileName, true);
        return file;
    }

    public static void saveBitmap(Bitmap bm, String dir, String fileName)
            throws IOException {
        File file = createFile(dir, fileName);
        FileOutputStream os = new FileOutputStream(file);
        bm.compress(Bitmap.CompressFormat.JPEG, 100, os);
        os.flush();
        os.close();
    }

    public static String inputstreamToString(InputStream in) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
