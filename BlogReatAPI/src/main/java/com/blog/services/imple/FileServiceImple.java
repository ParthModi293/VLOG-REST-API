package com.blog.services.imple;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import com.blog.services.FileService;

public class FileServiceImple implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getResouece(String path, String fileName) throws FileNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

}
