import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageEditor {
    private BufferedImage img;

    public void loadImage (String imagePath) throws IOException {
        this.img = ImageIO.read(new File(imagePath));
    }

    public void saveImage (String imagePath) throws IOException {
        ImageIO.write(this.img, "png", new File(imagePath));
    }

    public static int Truncate(int value) {
        if (value < 0) value = 0;
        else if (value > 255) value = 255;
        return value;
    }

    public void brightenImage (int brightnessValue, String imagePath) throws IOException {
        int rgb[];
        for (int i = 0; i < this.img.getWidth(); i++) {
            for (int j = 0; j < this.img.getHeight(); j++) {
                rgb = this.img.getRaster().getPixel(i, j, new int[3]);
                int red = Truncate(rgb[0] + brightnessValue);
                int green = Truncate(rgb[1] + brightnessValue);
                int blue = Truncate(rgb[2] + brightnessValue);
                int arr[] = { red, green, blue };
                this.img.getRaster().setPixel(i, j, arr);
            }
        }
        saveImage(imagePath);
    }

    public void increaseBrightnessWithThreads (int brightnessValue, String imagePath) throws IOException {
        int cores = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(cores);

        int height = this.img.getHeight();
        int chunkSize = this.img.getWidth() / height;

        for (int i = 0; i < cores; i++) {
            final int startRow = i * chunkSize;
            final int endRow = (i == cores - 1) ? height : startRow + chunkSize;


            executor.submit(() -> {
                for (int y = startRow; y < endRow; y++) {
                    for (int x = 0; x < img.getWidth(); x++) {
                        int rgb = img.getRGB(x, y);
                        int r = Math.min(((rgb >> 16) & 0xFF) + brightnessValue, 255);
                        int g = Math.min(((rgb >> 8) & 0xFF) + brightnessValue, 255);
                        int b = Math.min((rgb & 0xFF) + brightnessValue, 255);
                        int newRgb = (r << 16) | (g << 8) | b;
                        img.setRGB(x, y, newRgb);
                    }
                }
            });
        }
        saveImage(imagePath);
    }

    public void brightenImageWithThreadPool(int brightnessValue, String imagePath) throws IOException {
        // Uzyskanie liczby dostępnych rdzeni procesora
        int cores = Runtime.getRuntime().availableProcessors();
        // Utworzenie puli wątków
        ExecutorService executor = Executors.newFixedThreadPool(cores);

        for (int i = 0; i < this.img.getWidth(); i++) {
            final int index = i;
            executor.submit(() -> {
                for (int j = 0; j < this.img.getHeight(); j++) {
                    int rgb[];
                    rgb = this.img.getRaster().getPixel(index, j, new int[3]);
                    int red = Truncate(rgb[0] + brightnessValue);
                    int green = Truncate(rgb[1] + brightnessValue);
                    int blue = Truncate(rgb[2] + brightnessValue);
                    int arr[] = { red, green, blue };
                    this.img.getRaster().setPixel(index, j, arr);
                }
            });
        }
        saveImage(imagePath);
    }
}
