package capchurPhoto;

import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import org.opencv.core.Mat;

public class Utils {

    public static Image mat2Image(Mat mat) {
        int width = mat.width();
        int height = mat.height();
        int channels = mat.channels();
        byte[] byteData = new byte[width * height * channels];
        mat.get(0, 0, byteData);

        WritableImage writableImage = new WritableImage(width, height);
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        switch (channels) {
            case 1:
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        int gray = byteData[y * width + x] & 0xFF;
                        pixelWriter.setArgb(x, y, gray | (gray << 8) | (gray << 16) | 0xFF000000);
                    }
                }
                break;
            case 3:
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        int index = (y * width + x) * channels;
                        int b = byteData[index] & 0xFF;
                        int g = byteData[index + 1] & 0xFF;
                        int r = byteData[index + 2] & 0xFF;
                        pixelWriter.setArgb(x, y, (r << 16) | (g << 8) | b | 0xFF000000);
                    }
                }
                break;
            case 4:
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        int index = (y * width + x) * channels;
                        int b = byteData[index] & 0xFF;
                        int g = byteData[index + 1] & 0xFF;
                        int r = byteData[index + 2] & 0xFF;
                        int a = byteData[index + 3] & 0xFF;
                        pixelWriter.setArgb(x, y, (a << 24) | (r << 16) | (g << 8) | b);
                    }
                }
                break;
        }

        return writableImage;
    }
}
