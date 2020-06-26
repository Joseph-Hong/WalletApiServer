package com.kakaopay.wallet.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.LinkedList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.cmm.entity.BaseVO;
import com.cmm.entity.EgovMap;
import com.cmm.entity.SearchVO;
import com.cmm.util.CommonUtil;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

public class BaseController
{
	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

	@Autowired
	private MessageSource messageSource;


	protected void setPaginationInfo(SearchVO vo, Integer totalRecordCount) {
		int pageIndex = CommonUtil.nvl(vo.getPageIndex(), 1);
		int pageUnit = CommonUtil.nvl(vo.getPageUnit(), 10);
		if(CommonUtil.isEmpty(vo.getPageIndex()) || pageIndex < 1) {
			pageIndex = 1;
			vo.setPageIndex(pageIndex);
		}

		if(CommonUtil.isEmpty(vo.getPageUnit()) || pageUnit < 1) {
			pageUnit = 10;
			vo.setPageUnit(pageUnit);
		}

		if(totalRecordCount != null && totalRecordCount > 0 && totalRecordCount <= (pageIndex - 1) * pageUnit) {
			pageIndex = ((totalRecordCount - 1) / pageUnit) + 1;
			vo.setPageIndex(pageIndex);
		}

		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(pageIndex);
		paginationInfo.setRecordCountPerPage(pageUnit);
		paginationInfo.setPageSize(vo.getPageSize());

		vo.setFirstIndex(paginationInfo.getFirstRecordIndex());
		vo.setLastIndex(paginationInfo.getLastRecordIndex());
		vo.setRecordCountPerPage(pageUnit);
	}

	protected List getErrorMessage(BindingResult bindingResult)
	{
		List errors = new ArrayList<>();
		for (Object object : bindingResult.getAllErrors()) {
			if(object instanceof FieldError) {
				FieldError fieldError = (FieldError) object;

				/**
				  * Use null as second parameter if you do not use i18n (internationalization)
				  */
				//String message = messageSource.getMessage(fieldError, null);

				Map errorMap = new HashMap();
				String code = "";
				Object [] arguments = fieldError.getArguments();
				List<Object> fieldNameList = new LinkedList<Object>();

				if(CommonUtil.isNotEmpty(arguments)) {
					DefaultMessageSourceResolvable dm = (DefaultMessageSourceResolvable)arguments[0];
					code = dm.getCode();

					fieldNameList.add(code);

					if(1 < arguments.length) {
						for(int i = 1; i<arguments.length; i++) {
							fieldNameList.add(arguments[i]);
							// logger.debug("errorCode : {}, arg no.{} content : {}", fieldError.getDefaultMessage(), i, arguments[i]);
						}
					}
				}

				errorMap.put("field_name", fieldNameList);
				// errorMap.put("field_name", code);
				errorMap.put("validation_message", fieldError.getDefaultMessage());
				//errorMap.put("validation_message", GlobalConstants.PARAGRAPH_HTML_HEAD + message + GlobalConstants.PARAGRAPH_HTML_TAIL);

				errors.add(errorMap);
			}
		}

		return errors;
	}

	///////////////////////////////////////////////////////////////////
	protected boolean uploadFiles(HttpServletRequest request, Map<String, Object> entity, String uploadPath) throws Exception
	{
		/*
		MultipartHttpServletRequest multipart = (MultipartHttpServletRequest) request;
		MultipartFile file = multipart.getFile("upload_file");

		if(CommonUtil.isNotEmpty(file.getOriginalFilename())) {
			FileAttachmentVO uploadFile = FileUploadUtil.uploadFile(file, uploadPath);
			//entity.put("file_name", uploadFile.getFileName());
			//entity.put("file_name", uploadFile.getTempFileName());

			// Convert Spring Multipart to File
			BufferedImage bImg = ImageIO.read(file.getInputStream());
			entity.put("img_size_width", bImg.getWidth());
			entity.put("img_size_height", bImg.getHeight());
		}
		*/

		//In case of Image file
		//[Step-10] : Save the uploaded data as image file in local path

		return true;
	}

	protected String getBody(HttpServletRequest request) throws IOException {

        String body = null;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    throw ex;
                }
            }
        }

        body = stringBuilder.toString();
        return body;
    }
}
