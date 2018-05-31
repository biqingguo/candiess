package com.itranswarp.crypto.candiess.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.crypt.EncryptionMode;
import org.apache.poi.poifs.crypt.Encryptor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelUtil {

	private Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

	private Workbook wb;
	private Sheet sheet;
	private Row row;

	/**
	 * 读取文件
	 * 
	 * @param filepath
	 * @return
	 */
	public List<List<Object>> getPurchaseInfo(String filepath) {
		List<List<Object>> list = null;
		if (filepath == null) {
			return null;
		}
		String ext = filepath.substring(filepath.lastIndexOf("."));
		try {
			InputStream is = new FileInputStream(filepath);
			if (".xlsx".equals(ext)) {
				wb = new XSSFWorkbook(is);
			} else {
				wb = null;
			}
			list = readExcelContent();
		} catch (FileNotFoundException e) {
			logger.error("FileNotFoundException", e);
		} catch (IOException e) {
			logger.error("IOException", e);
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		return list;
	}

	private List<List<Object>> readExcelContent() throws Exception {

		if (wb == null) {
			throw new Exception("Workbook对象为空！");
		}
		List<List<Object>> list = new ArrayList<List<Object>>();
		List<Object> rows = null;
		sheet = wb.getSheetAt(0);
		// 得到总行数
		int rowNum = sheet.getLastRowNum();
		int columnNum = sheet.getPhysicalNumberOfRows();
		row = sheet.getRow(0);

		for (int i = 0; i <= rowNum; i++) {
			row = sheet.getRow(i);
			rows = new ArrayList<>();
			list.add(rows);
			for (int k = 0; k <= columnNum; k++) {
				if (row.getCell(k) != null) {
					rows.add(row.getCell(k));
				}
			}
		}
		return list;
	}

	/**
	 * 创建工作簿
	 * 
	 * @param sfworkbook
	 * @param headerstr
	 * @return
	 */
	public SXSSFWorkbook createWorkbook(XSSFWorkbook sfworkbook) {
		SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook(sfworkbook, 1000);
		SXSSFSheet xfsheet = null;
		xfsheet = (SXSSFSheet) sxssfWorkbook.createSheet("表格");
		return sxssfWorkbook;
	}

	public static interface WriteExcel<T> {
		void writeHead(XSSFRow contentxfrow);

		void write(XSSFRow contentxfrow, T t);
	}

	/**
	 * 创建工作簿头
	 * 
	 * @param sfworkbook
	 * @param headerstr
	 * @return
	 */
	public void createHead(XSSFWorkbook sfworkbook, SXSSFWorkbook sxssfWorkbook, WriteExcel writeexcel) {
		XSSFSheet xfsheet = null;
		xfsheet = sfworkbook.getSheet("表格");
		XSSFRow xfrow = xfsheet.createRow(0);
		writeexcel.writeHead(xfrow);
	}

	/**
	 * 向文件写入内容
	 * 
	 * @param sfworkbook
	 * @param sxssfWorkbook
	 * @param list
	 * @param rowNum
	 */

	public <T> void writeXlsFile(XSSFWorkbook sfworkbook, SXSSFWorkbook sxssfWorkbook, List<T> list,
			WriteExcel writeexcel) {
		XSSFSheet xfsheet = null;
		xfsheet = sfworkbook.getSheet("表格");
		for (int i = 0; i < list.size(); i++) {
			XSSFRow contentxfrow = xfsheet.createRow(i + 1);
			writeexcel.write(contentxfrow, list.get(i));
		}
	}

	/**
	 * 保存excel文件
	 * 
	 * @param sfworkbook
	 * @param sxssfWorkbook
	 * @param list
	 * @param rowNum
	 */
	public String saveXlsFile(XSSFWorkbook sfworkbook, String passwd) {
		String path = System.getProperty("java.io.tmpdir").concat(UUID.randomUUID().toString().concat(".xlsx"));
		OutputStream os = null;
		try {
			os = new FileOutputStream(path);
			sfworkbook.write(os);
			os.flush();
			encryptExcel(path, passwd);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return path;
	}

	/**
	 * 对文件加密
	 * 
	 * @param excelFilePath
	 * @param excelPassword
	 */
	public void encryptExcel(String excelFilePath, String excelPassword) {
		FileOutputStream fos = null;
		try {
			logger.debug("export encryptExcel.... start");
			File fileSoucre = new File(excelFilePath);
			// Add password protection and encrypt the file
			POIFSFileSystem fs = new POIFSFileSystem();
			EncryptionInfo info = new EncryptionInfo(EncryptionMode.agile);
			Encryptor enc = info.getEncryptor();

			// set the password
			enc.confirmPassword(excelPassword);

			// encrypt the file
			OPCPackage opc = OPCPackage.open(fileSoucre, PackageAccess.READ_WRITE);
			OutputStream os = enc.getDataStream(fs);
			opc.save(os);
			opc.close();

			// save the file back to the filesystem
			fos = new FileOutputStream(fileSoucre);
			fs.writeFilesystem(fos);
			logger.debug("export encryptExcel.... end");
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					logger.error("export encryptExcel close FileOutputStream.... Exception");
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 对文件加密
	 * 
	 * @param excelFilePath
	 * @param excelPassword
	 */
	public void sendExcel(HttpServletResponse response, String path) {
		InputStream in = null;
		OutputStream out = null;
		try {
			out = response.getOutputStream();
			String contentType = "application/octet-stream;charset=UTF-8";
			response.reset();// 不加这句,会有问题
			response.setContentType(contentType);
			StringBuffer contentDisposition = new StringBuffer();
			contentDisposition.append("attachment;");
			contentDisposition.append("filename=\"");
			contentDisposition.append("download.xlsx");
			contentDisposition.append("\"");
			response.setHeader("Content-Disposition", new String(
					contentDisposition.toString().getBytes(System.getProperty("file.encoding")), "iso8859_1"));

			in = new FileInputStream(path);
			int len = 0;
			byte[] buf = new byte[1024];
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.flush();
			in.close();
			logger.debug("export excel.... end");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			File file = new File(path);
			if (file.exists()) {
				try {
					FileUtils.forceDelete(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			// if (in != null) {
			// try {
			// in.close();
			// } catch (IOException e) {
			// e.printStackTrace();
			// }
			// }
			// if (out != null) {
			// try {
			// out.close();
			// } catch (IOException e) {
			// e.printStackTrace();
			// }
			// }
		}
	}

	public static void main(String[] args) {
		String path = "C:\\Users\\cong\\Desktop\\PropertyToUser.xlsx";
		ExcelUtil excelUtil = new ExcelUtil();
		List<List<Object>> purchaseInfo = excelUtil.getPurchaseInfo(path);
		for (List<Object> list : purchaseInfo) {
			for (Object object : list) {
				String string = object.toString();
				// System.out.println(string.indexOf(".0"));
				if (string.lastIndexOf(".0") != -1) {
					string = string.substring(0, string.lastIndexOf(".0"));
				}
				System.out.println(string);
			}
		}
	}
}
