package com.kidbear.plane.gm.datas;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import org.apache.commons.fileupload.FileItem;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class SerialMultipartFile extends CommonsMultipartFile implements
		Serializable {

	@Override
	public String getName() {
		return super.getName();
	}

	@Override
	public String getOriginalFilename() {
		return super.getOriginalFilename();
	}

	@Override
	public String getContentType() {
		return super.getContentType();
	}

	@Override
	public boolean isEmpty() {
		return super.isEmpty();
	}

	@Override
	public long getSize() {
		return super.getSize();
	}

	@Override
	public byte[] getBytes() {
		return super.getBytes();
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return super.getInputStream();
	}

	@Override
	public void transferTo(File dest) throws IOException, IllegalStateException {
		super.transferTo(dest);
	}

	@Override
	protected boolean isAvailable() {
		return super.isAvailable();
	}

	@Override
	public String getStorageDescription() {
		return super.getStorageDescription();
	}

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -6845073973658098293L;

	public SerialMultipartFile(FileItem fileItem) {
		super(fileItem);
	}

}
