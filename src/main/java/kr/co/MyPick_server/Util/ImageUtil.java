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
    private static final String IMAGE_BASE_PATH = "/volume1/docker/MyPick/Server/profile/";
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

        logger.info("Attempting to access image at path: {}", imagePath.toAbsolutePath());

        if (!Files.exists(imagePath)) {
            logger.error("File does not exist: {}. Using default image.", imagePath);
            imagePath = getDefaultImagePath();
        } else if (isFileSizeExceeded(imagePath)) {
            logger.error("File size exceeds limit: {}. Using default image.", imagePath);
            imagePath = getDefaultImagePath();
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
        try {
            return Files.size(imagePath) > MAX_FILE_SIZE; // 1MB = 1048576 bytes
        } catch (IOException e) {
            logger.error("Error while checking file size: {}", imagePath, e);
            throw e;
        }
    }

    /**
     * Returns the path of the default image.
     *
     * @return Path of the default image
     */
    private Path getDefaultImagePath() {
        Path defaultImagePath = Paths.get(IMAGE_BASE_PATH + DEFAULT_IMAGE);
        logger.info("Default image path: {}", defaultImagePath.toAbsolutePath());
        return defaultImagePath;
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
     * Saves the file and returns its file name.
     *
     * @param file          MultipartFile uploaded file
     * @param customFileName Custom file name
     * @return The name of the saved file
     * @throws IOException If an error occurs during file saving
     */
    public String saveFile(MultipartFile file, int customFileName) throws Exception {
        if (file.isEmpty()) {
            throw new IOException("Failed to store empty file");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IOException("File size exceeds the allowable limit of 1MB");
        }

        // Extract the original file extension
        String originalFileName = file.getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));

        // Append the extension to the custom file name
        String fileNameWithExtension = customFileName + extension;
        Path destinationPath = Paths.get(IMAGE_BASE_PATH + fileNameWithExtension);

        // Delete existing files with the same custom file name (ignoring extension)
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(IMAGE_BASE_PATH))) {
            for (Path path : stream) {
                if (path.getFileName().toString().startsWith(customFileName + ".")) {
                    Files.delete(path);
                    logger.info("Deleted existing file with the same name (ignoring extension): {}", path);
                }
            }
        } catch (IOException e) {
            logger.error("Failed to delete existing files", e);
            throw e;
        }

        // Save the file
        try {
            Files.copy(file.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
            logger.info("File saved successfully: {}", destinationPath);
        } catch (IOException e) {
            logger.error("Failed to save file", e);
            throw e;
        }

        return fileNameWithExtension;
    }
}
