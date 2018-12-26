package com.janwarlen.file;

import com.janwarlen.constant.DigitConstant;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @Auther: janwarlen
 * @Date: 2018/12/19 11:25
 * @Description: 压缩工具
 */
public class ZipUtil {

    /**
     * 日志记录
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ZipUtil.class);

    /**
     * 压缩文件
     * @param fileName 文件绝对路径
     * @param outputStream 文件输出流
     * @param isKeepDirStruct 是否保留目录结构
     */
    public static void zipFile(String fileName, OutputStream outputStream, boolean isKeepDirStruct) {
        File file = new File(fileName);
        try (ZipOutputStream zout = new ZipOutputStream(outputStream);
             FileInputStream fis = new FileInputStream(file)) {
            saveZipFile(fileName, isKeepDirStruct, file, zout, fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveZipFile(String fileName, boolean isKeepDirStruct, File file,
                                    ZipOutputStream zout, FileInputStream fis) throws IOException {
        ZipEntry zipEntry = new ZipEntry(isKeepDirStruct ? fileName : file.getName());
        zout.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zout.write(bytes, 0, length);
        }
    }

    /**
     * 压缩文件
     * @param fileNameList 文件绝对路径集合
     * @param outputStream 文件输出流
     * @param isKeepDirStruct 是否保留目录结构
     */
    public static void zipFile(List<String> fileNameList, OutputStream outputStream, boolean isKeepDirStruct) {
        try (ZipOutputStream zout = new ZipOutputStream(outputStream)) {
            for (String fileName : fileNameList) {
                File file = new File(fileName);
                try (FileInputStream fis = new FileInputStream(file)) {
                    saveZipFile(fileName, isKeepDirStruct, file, zout, fis);
                }
            }
        } catch (IOException e) {
            LOGGER.info("ZipOutputStream创建失败!", e);
        }
    }

    /**
     * 压缩文件夹
     * @param dirPath 文件夹路径
     * @param outputStream 文件输出流
     * @param isKeepDirStruct 是否保留目录结构
     */
    public static void zipDirectory(String dirPath, OutputStream outputStream, boolean isKeepDirStruct) {
        File file = new File(dirPath);
        if (file.isHidden()) {
            return;
        }
        try (ZipOutputStream zipOut = new ZipOutputStream(outputStream)) {
            File[] children = file.listFiles();
            for (File child : children) {
                String fileName = isKeepDirStruct ? child.getAbsolutePath() : child.getName();
                zipFile(child,fileName,zipOut);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(fileName));
                zipOut.closeEntry();
            } else {
                zipOut.putNextEntry(new ZipEntry(fileName + "/"));
                zipOut.closeEntry();
            }
            File[] children = fileToZip.listFiles();
            for (File childFile : children) {
                zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
            }
            return;
        }
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        fis.close();
    }

    /**
     * 解压文件
     * @param zipFile 压缩包绝对路径
     * @param unZipPath 解压路径
     * @param encode 编码
     */
    public static void unZipFile(String zipFile, String unZipPath, Charset encode) {
        if (null == encode) {
            encode = Charset.forName("UTF-8");
        }
        File file = new File(zipFile);
        // 如果 destDir 为 null, 空字符串, 或者全是空格, 则解压到压缩文件所在目录
        if(StringUtils.isBlank(unZipPath)) {
            unZipPath = file.getParent();
        }
        try (FileInputStream zip = new FileInputStream(file);
             ZipArchiveInputStream zis = new ZipArchiveInputStream(zip)) {
            ArchiveEntry nextEntry;
            while ((nextEntry = zis.getNextEntry()) != null) {
                if (nextEntry.isDirectory()) {
                    File directory = new File(unZipPath, nextEntry.getName());
                    directory.mkdirs();
                } else {
                    File tmpFile = new File(unZipPath, nextEntry.getName()).getParentFile();
                    if (!tmpFile.exists()) {
                        // 防止路径不存在
                        tmpFile.mkdirs();
                    }

                    try (FileOutputStream fileOutputStream = new FileOutputStream(new File(unZipPath, nextEntry.getName()));
                        OutputStream outputStream = new BufferedOutputStream(fileOutputStream, DigitConstant.BUFFER_SIZE)) {
                        IOUtils.copy(zis, outputStream);
                    }
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
