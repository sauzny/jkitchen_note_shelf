package org.example.mavenp.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@Slf4j
public class DownLoadApi {

    @Value("${local.repository}")
    private String localRepository;

    @GetMapping("/repository/public/**//{fileName:.+}")
    public void saveTest(@PathVariable("fileName") String fileName,
                         HttpServletRequest request,
                         HttpServletResponse response) throws UnsupportedEncodingException {

        String servletPath = request.getServletPath();
        Path path = Paths.get(localRepository, servletPath.replaceAll("/repository/public/", ""));
        File file = path.toFile();

        // 配置文件下载
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        // 下载文件能正常显示中文
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));

        // 实现文件下载
        byte[] buffer = new byte[1024];

        try (FileInputStream fis = new FileInputStream(file); BufferedInputStream bis = new BufferedInputStream(fis)) {

            OutputStream os = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
            log.info("successfully {}", file.getPath());
        } catch (Exception e) {
            log.error("failed {}", e.getMessage());
        }
    }
}
