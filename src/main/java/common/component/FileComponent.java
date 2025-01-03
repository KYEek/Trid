package common.component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import common.Constants;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import product.domain.ImageDTO;
import util.StringUtil;

/*
 * 이미지 파일 저장 컴포넌트
 */
public class FileComponent {

	// 생성자 노출 방지
	private FileComponent() {}

	// 이미지를 저장하고 주소 및 파일명을 ImageDTO list에 저장하여 반환
	public static List<ImageDTO> saveImages(HttpServletRequest request) throws IOException, ServletException {
		List<ImageDTO> imageList = new ArrayList<>();

		String path = Constants.IMAGE_SAVE_PATH;

		String uploadPath = request.getServletContext().getRealPath(path);

		File uploadDir = new File(uploadPath);

		// 해당 경로에 디렉토리가 존재하는 확인
		if (!uploadDir.exists()) {
			uploadDir.mkdirs();
		}

		// 파일 업로드 처리
		for (Part part : request.getParts()) {
			if (imageFileValid(part)) {
				ImageDTO imageDTO = new ImageDTO();

				String fileName = part.getSubmittedFileName(); // 이미지 파일명

				// 파일 확장자가 지정된 이미지 형식인지 확인
				if (!imageExtensionValid(fileName)) {
					continue;
				}

				// 지정된 경로에 이미지 저장
				part.write(uploadPath + File.separator + fileName);

				// ImageDTO에 이미지 정보 저장
				imageDTO.setImagePath(path + File.separator + fileName);
				imageDTO.setImageName(fileName);

				imageList.add(imageDTO);
			}
		}

		return imageList;

	}

	/*
	 * 파일 형식이 이미지 파일 형식인지 확인하는 메소드
	 */
	private static boolean imageExtensionValid(String fileName) {
		int lastIndex = fileName.lastIndexOf(".");
		
		// 확장자가 없는 경우
		if(lastIndex == -1) {
			return false;
		}
		
		String extension = fileName.substring(lastIndex + 1);

		String[] extensionArr = { "jpg", "jpeg", "png" };

		for (String ext : extensionArr) {
			// 지정된 이미지 확장자 중 하나인 경우 true 반환
			if (ext.equalsIgnoreCase(extension)) {
				return true;
			}
		}

		return false;

	}
	
	/*
	 *  파일이 정상적인 이미지 파일 형식인지 확인하는 메소드
	 */
	private static boolean imageFileValid(Part part) {
		// MIME(어떤 종류의 파일 스트림인지 ex)'text/html', 'text/text', 'multipart/formed-data')
		return !StringUtil.isBlank(part.getSubmittedFileName()) // 파일명이 존재하는지
				&& (part.getSize() > 0) // 파일 크기가 유효한지
				&& part.getContentType() != null // 파일의 MIME 타입이 유효한지
				&& part.getContentType().startsWith("image/"); // 파일의 MIME 타입이 이미지 타입인지
	}

}
