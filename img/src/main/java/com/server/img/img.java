package com.server.img;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
public class img {
    @PostMapping("img")
    public String ImgInput(@RequestPart(required = false, name = "files")  MultipartFile[] files, HttpServletRequest request) throws IOException {
        ClassPathResource resource = new ClassPathResource("1716723194822_대충그린 아키텍쳐 추가 필요.png");


        System.out.println(resource.getInputStream());
        String filepath = "src/main/resources/imgs/board/upload";
        try {
            if (files != null) {
                for (MultipartFile multipart : files) {
                    if (!multipart.isEmpty()) {
                        String filename = System.currentTimeMillis()+"_"+multipart.getOriginalFilename();
                        FileUtils.copyInputStreamToFile(multipart.getInputStream(), new File(filepath, filename));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ok";
    }

    @GetMapping("img")
    public ResponseEntity<byte[]> displayBoard(@RequestParam(name = "fileName") String fileName, HttpServletRequest request ) throws IOException {


        System.out.println("getImage()........." + fileName);

        File file = new File("src/main/resources/imgs/board/upload" +File.separator+fileName);

        ResponseEntity<byte[]> result = null;

        try {

            HttpHeaders header = new HttpHeaders();

            header.add("Content-type", Files.probeContentType(file.toPath()));

            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }


}
