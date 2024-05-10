package com.callor.gallery.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.callor.gallery.dao.GalleryDao;
import com.callor.gallery.dao.ImagesDao;
import com.callor.gallery.models.GalleryVO;
import com.callor.gallery.models.ImageVO;
import com.callor.gallery.service.FileUploadService;
import com.callor.gallery.service.GalleryService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class GalleryServiceImpl implements GalleryService {

	private final GalleryDao galleryDao;
	private final FileUploadService fileUploadService;
	private final ImagesDao imagesDao;
	
	public GalleryServiceImpl(GalleryDao galleryDao, FileUploadService fileUploadService,ImagesDao imagesDao) {
		super();
		this.galleryDao = galleryDao;
		this.fileUploadService = fileUploadService;
		this.imagesDao = imagesDao;
//		galleryDao.create_table(null);

	}
	
	@Autowired
	public void create_table() {
		galleryDao.create_table(null);
		imagesDao.create_table(null);
	}

	@Override
	public List<GalleryVO> selectAll() {

		return galleryDao.selectAll();
	}

	
	
	@Override
	public GalleryVO createGallery(GalleryVO galleryVO, MultipartFile image_file) throws Exception {
	
		
		setGalleryOptions(galleryVO);
		String fileName = fileUploadService.fileUpload(image_file);

		galleryVO.setG_origin_image(image_file.getOriginalFilename());
		galleryVO.setG_up_image(fileName);

		
		int ret = galleryDao.insert(galleryVO);
		if (ret > 0) {
			return galleryVO;
		}

		return null;
	}

	/*
	 * 멀티 파일을 업로드 했을때 사용할 메소드
	 */
	
	@Transactional
	@Override
	public List<GalleryVO> createGallery(GalleryVO galleryVO, MultipartHttpServletRequest image_files)
			throws Exception {

 
		setGalleryOptions(galleryVO);
		galleryVO.setG_origin_image("");
		galleryVO.setG_up_image("");
		
	
		
		int gRet = galleryDao.insert(galleryVO);
		
		//mapper의 
		String i_gid = galleryVO.getG_id();
		

		List<ImageVO> resultNames = fileUploadService.filesUpload(image_files);

		
		int iRet = imagesDao.inserts(i_gid, resultNames);
		
		
		List<MultipartFile> result = image_files.getFiles("image_files");
		
	
		
		return null;
	}

	private void setGalleryOptions(GalleryVO vo) {

		LocalDateTime lt = LocalDateTime.now();
		DateTimeFormatter date = DateTimeFormatter.ofPattern("YYYY-MM-dd");
		DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm:ss");
		vo.setG_id(UUID.randomUUID().toString());
		vo.setG_date(lt.format(date));
		vo.setG_time(lt.format(time));
	}
	
}
