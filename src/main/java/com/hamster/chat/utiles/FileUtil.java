package com.hamster.chat.utiles;

import com.hamster.chat.exception.CustomizeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 文件上传工具类
 */
@Slf4j
public class FileUtil {

    private FileUtil() {
    }

    public static Map<String, String> uploadFile(MultipartFile file, String rootPath) {
        String fileName = file.getOriginalFilename().toLowerCase();
        String contentType = fileName;
        int i = contentType.indexOf(".");
        int leg = contentType.length();
        contentType = (i > 0 ? (i + 1) == leg ? " " : contentType.substring(i, contentType.length()) : " ");
        fileName = UUID.randomUUID().toString().replace("-", "") + contentType;

        File targetFile = new File(rootPath, fileName);
        if (!targetFile.exists()) {
            targetFile.getParentFile().mkdirs();
        }
        try {
            file.transferTo(targetFile);
        } catch (IOException e) {
            log.error("[上传文件[{}]失败。错误信息{}", file.getOriginalFilename(), e.getMessage());
            throw new CustomizeException("文件上传失败");
        }
        Map<String, String> map = new HashMap<>(2);
        map.put("path", targetFile.getAbsolutePath());
        map.put("fileName", fileName);
        return map;
    }
}
