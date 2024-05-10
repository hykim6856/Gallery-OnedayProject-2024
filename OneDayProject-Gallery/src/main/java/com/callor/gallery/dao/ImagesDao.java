package com.callor.gallery.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.callor.gallery.models.ImageVO;

public interface ImagesDao extends GenericDao<ImageVO, String> {


	public	int inserts(
			
		@Param("g_id")	String i_gid,
		@Param("images") List<ImageVO> resultNames);
	


	
}
