package org.video;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class FileLocController {
	
	@RequestMapping("/loc/{fileLoc}")
	public String getNextDir(@PathVariable("fileLoc") String fileLoc) {
		return "player";
	}
	
}
