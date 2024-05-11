package capchurPhoto;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class OpenCVTest {
    public static void main(String[] args) {
        // Load OpenCV library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        // Read an image
        Mat image = Imgcodecs.imread("path/to/your/image.jpg");

        // Check if image is loaded successfully
        if (!image.empty()) {
            System.out.println("Image loaded successfully.");

            // Display the image
            // You can add code here to display the image using JavaFX or another library
        } else {
            System.out.println("Failed to load image.");
        }
    }
}
