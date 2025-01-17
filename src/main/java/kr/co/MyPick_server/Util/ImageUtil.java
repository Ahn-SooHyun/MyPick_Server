package kr.co.MyPick_server.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.Base64;

@Component
public class ImageUtil {

    Logger logger = LoggerFactory.getLogger(ImageUtil.class);

    // Fixed paths for images
    private static final String IMAGE_BASE_PATH = "src/main/resources/static/profileImage/";
    private static final String DEFAULT_IMAGE = "index.png";
    private static final long MAX_FILE_SIZE = 1048576; // 1MB in bytes

    /**
     * Reads an image file, checks its size, and encodes it into Base64 format.
     * If the image size exceeds 1MB, a default image is returned instead.
     *
     * @param fileName The name of the image file (filename.extension)
     * @return The Base64-encoded image data
     * @throws Exception If the file does not exist or cannot be read
     */
    public String getBase64EncodedImage(String fileName) throws Exception {
        Path imagePath = Paths.get(IMAGE_BASE_PATH + fileName);

        logger.info(imagePath.toString());

        if (!Files.exists(imagePath) || isFileSizeExceeded(imagePath)) {
            imagePath = getDefaultImagePath(); // Use default image
        }

        byte[] imageBytes = Files.readAllBytes(imagePath);
        String fileType = getFileExtension(imagePath.getFileName().toString());
        return "data:image/" + fileType + ";base64," + Base64.getEncoder().encodeToString(imageBytes);
    }

    /**
     * Checks if the file size exceeds 1MB.
     *
     * @param imagePath Path of the image file
     * @return True if file size exceeds 1MB, otherwise false
     * @throws Exception If the file cannot be accessed
     */
    private boolean isFileSizeExceeded(Path imagePath) throws Exception {
        return Files.size(imagePath) > MAX_FILE_SIZE; // 1MB = 1048576 bytes
    }

    /**
     * Returns the path of the default image.
     *
     * @return Path of the default image
     */
    private Path getDefaultImagePath() {
        return Paths.get(IMAGE_BASE_PATH + DEFAULT_IMAGE);
    }

    /**
     * Extracts the file extension from the file name.
     *
     * @param fileName The name of the file
     * @return The file extension (returns "jpeg" if no extension is found)
     */
    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "jpeg" : fileName.substring(dotIndex + 1);
    }

    /**
     * 파일을 저장하고 파일 이름을 반환합니다.
     *
     * @param file MultipartFile 업로드된 파일
     * @return 저장된 파일의 이름
     * @throws IOException 파일 저장 중 오류가 발생했을 경우
     */
    public String saveFile(MultipartFile file, int customFileName) throws Exception {
        if (file.isEmpty()) {
            throw new IOException("Failed to store empty file");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IOException("File size exceeds the allowable limit of 1MB");
        }

        // 원본 파일의 확장자 추출
        String originalFileName = file.getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));

        // 사용자 지정 파일명에 확장자 추가
        String fileNameWithExtension = customFileName + extension;
        Path destinationPath = Paths.get(IMAGE_BASE_PATH + fileNameWithExtension);

        // 동일한 이름을 가진 파일(확장자 무시) 삭제
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(IMAGE_BASE_PATH))) {
            for (Path path : stream) {
                if (path.getFileName().toString().startsWith(customFileName + ".")) {
                    Files.delete(path);
                    logger.info("Deleted existing file with the same name (ignoring extension): " + path);
                }
            }
        } catch (IOException e) {
            logger.error("Failed to delete existing files", e);
            throw e;
        }

        // 파일 복사 및 저장
        try {
            Files.copy(file.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
            logger.info("File saved successfully: " + destinationPath);
        } catch (IOException e) {
            logger.error("Failed to save file", e);
            throw e;
        }

        return fileNameWithExtension;
    }


}
