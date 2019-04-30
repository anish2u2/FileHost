package org.video;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller("/upload")
public class Uploader {
	
	private static String UPLOADED_FOLDER = "F://temp//";
	
	@GetMapping
	public String getPage() {
		return "upload";
	}
	
	@PostMapping
	public String upload(@RequestParam("file") MultipartFile file,RedirectAttributes redirectAttributes) {
		
		if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:uploadStatus";
        }

        try {
        	
            // Get the file and save it somewhere
           // byte[] bytes = file.getBytes();
            File path = new File(UPLOADED_FOLDER + file.getOriginalFilename());
            IOUtils.copy(file.getInputStream(), new FileOutputStream(path));
            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/uploadStatus";
	}
	
	@GetMapping("/uploadStatus")
    public String uploadStatus() {
        return "uploadStatus";
    }
	
}
