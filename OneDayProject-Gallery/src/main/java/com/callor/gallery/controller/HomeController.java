package com.callor.gallery.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.callor.gallery.models.GalleryVO;
import com.callor.gallery.service.GalleryService;

import lombok.extern.slf4j.Slf4j;

/**
 * Handles requests for the application home page.
 */
@Slf4j
@Controller
public class HomeController {
	
//	private final GalleryDao galleryDao;
	private final GalleryService galleryService;
 
	public HomeController(GalleryService galleryService) {
		super();
//		this.galleryDao = galleryDao;
		this.galleryService = galleryService;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate );
		
//		List<GalleryVO> gList = galleryDao.selectAll();
		List<GalleryVO> gList = galleryService.selectAll();
		model.addAttribute("GALLERYS",gList);
		
		return "list";
	}
	
	@RequestMapping(value = "/insert", method = RequestMethod.GET)
	public String insert(Model model) {
		GalleryVO galleryVO = GalleryVO.builder()
				.g_up_image("noimage.png")
				.g_origin_image("noimage.png")
				.build();
		model.addAttribute("GALLERY", galleryVO);		

		return "input";
	}
	
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public String insert(GalleryVO galleryVO, @RequestParam("image_file") MultipartFile image_file,
			MultipartHttpServletRequest image_files, Model model) {

		log.debug("파일 업로드 {}", image_file.getOriginalFilename());

		String singleFileName = image_file.getOriginalFilename();
		GalleryVO resultVo = null;
		
		try {
			if (!singleFileName.isEmpty()) {
				resultVo = galleryService.createGallery(galleryVO, image_file);

			}
		
			if (image_files.getFiles("image_files").size() > 0) {
				List<GalleryVO> Vos = galleryService.createGallery(galleryVO, image_files);
			}
		
			model.addAttribute("GALLERY", resultVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		model.addAttribute("IMAGE", image_file.getOriginalFilename());
		

		return "input";
	}
	
}
