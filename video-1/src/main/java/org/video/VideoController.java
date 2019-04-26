package org.video;

import java.io.File;
import java.io.FileInputStream;
import java.net.Inet4Address;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class VideoController {

	private String server;

	public VideoController() {
		try {
			server = Inet4Address.getLocalHost().getHostAddress();
			System.out.println(server);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/video")
	public void getVideo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String filePath=request.getParameter("loc")!=null && request.getParameter("loc").contains(" AND ")?request.getParameter("loc").replace(" AND ","&"):request.getParameter("loc");
		filePath=filePath.replace(" br- ", "[").replace(" -br ", "]");
		File file = new File(filePath);
		response.setHeader("Content-type", MediaType.APPLICATION_OCTET_STREAM_VALUE);
		response.setHeader("Content-Disposition", "inline; filename=" + file.getName() + ";");
		FileInputStream stream = new FileInputStream(file);
		byte[] buffer = new byte[1024 * 16];
		int length = 0;
		while ((length = stream.read(buffer)) != -1) {
			response.getOutputStream().write(buffer, 0, length);
			response.getOutputStream().flush();
		}
		stream.close();
		response.flushBuffer();
		response.getOutputStream().close();

	}

	@RequestMapping("/player")
	public ModelAndView getPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("player");
		System.out.println(request.getParameter("filePath"));
		String filePath=request.getParameter("filePath")!=null && request.getParameter("filePath").contains(" AND ")?request.getParameter("filePath").replace(" AND ","&"):request.getParameter("filePath");
		filePath=filePath.replace(" br- ", "[").replace(" -br ", "]");
		System.out.println(filePath);
		File file = new File(filePath);
		if (file.isFile()) {
			response.sendRedirect("/video?loc=" + request.getParameter("filePath"));
		}
		mv.addObject("files", file.list());
		mv.addObject("dir", filePath.endsWith("/") ? filePath
				: filePath + "/");
		mv.addObject("hostAddress", server);
		return mv;
	}

}
