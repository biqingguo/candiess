package com.itranswarp.crypto.candiess.web.modules.admin.oss.cloud;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.itranswarp.crypto.candiess.common.utils.DateUtils;
import com.itranswarp.crypto.candiess.ueditor.upload.StorageManager;

@Component()
public class DiskService {
	@Value("${web.upload-path}")
	String uploadPath;

	/**
	 * 文件路径
	 * 
	 * @param prefix
	 *            前缀
	 * @param suffix
	 *            后缀
	 * @return 返回上传路径
	 */
	private String getPath(String prefix, String suffix) {
		// 生成uuid
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		// 文件路径
		String path = DateUtils.format(new Date(), "yyyyMMdd") + File.separator + uuid;

		if (StringUtils.isNotBlank(prefix)) {
			path = prefix + File.separator + path;
		}
		return path + suffix;
	}

	public String uploadSuffix(byte[] data, String suffix) {
		String path = getPath("", suffix);
		String savePath = uploadPath + File.separator + path;
		StorageManager.saveBinaryFile(data, savePath);
		return path;
	}

}
