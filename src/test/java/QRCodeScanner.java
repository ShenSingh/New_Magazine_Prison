import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.google.zxing.*;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class QRCodeScanner {

    public static void main(String[] args) throws ChecksumException, FormatException {
        // Get default webcam
        Webcam webcam = Webcam.getDefault();

        // Create a JFrame for webcam display
        JFrame frame = new JFrame("QR Code Scanner");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a panel to display webcam feed
        WebcamPanel panel = new WebcamPanel(webcam);
        panel.setMirrored(true);

        // Add webcam panel to the frame
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(640, 480);

        // Start capturing images from the webcam
        webcam.open();

        // Continuously scan for QR codes
        while (true) {
            BufferedImage image = webcam.getImage();
            if (image != null) {
                try {
                    LuminanceSource source = new BufferedImageLuminanceSource(image);
                    BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                    Reader reader = new MultiFormatReader();
                    Result result = reader.decode(bitmap);
                    if (result != null) {
                        System.out.println("QR Code Content: " + result.getText());
                        webcam.close();
                        // Perform actions with the QR code content here
                        frame.setVisible(false);
                        // For example, open a URL, display information, etc.
                    }
                } catch (NotFoundException e) {
                    // QR code not found in the image
                }
            }
        }
    }
}

