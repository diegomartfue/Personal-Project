import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Color;

public class Filters {

  public static void main(String[] args) throws IOException {
    // Read in the image file.
    File f = new File("dog.png");
    BufferedImage img = ImageIO.read(f);

    // For debugging
    System.out.println("Before:");
    System.out.println(Utilities.getRGBArray(0, 0, img)[0]);
    System.out.println(Utilities.getRGBArray(0, 0, img)[1]);
    System.out.println(Utilities.getRGBArray(0, 0, img)[2]);
    
    // Set border thickness and color
    int borderThickness = 10; // Set the border thickness to 10 pixels
    int[] borderColor = {255, 0, 0}; // Set the border color to red 

    
    //applyGrayscale(img);
    //applyNorak(img);
    //applyBorder(img, borderThickness, borderColor);
    //applyMirror(img);
    applyCustom(img);
    //applyBlur(img);

    // For debugging
    System.out.println("After:");
    System.out.println(Utilities.getRGBArray(0, 0, img)[0]);
    System.out.println(Utilities.getRGBArray(0, 0, img)[1]);
    System.out.println(Utilities.getRGBArray(0, 0, img)[2]);
    // 53 53 53

    // Write the result to a new image file.
    f = new File("dog_filtered.png");
    ImageIO.write(img, "png", f);
  }

  // Apply grayscale filter: Converts each pixel to grayscale.
  public static void applyGrayscale(BufferedImage img) {
    for (int i = 0; i < img.getHeight(); i++) {
      for (int j = 0; j < img.getWidth(); j++) {
        int[] rgb = Utilities.getRGBArray(i, j, img);
        int sum = (rgb[0] + rgb[1] + rgb[2]) / 3;
        int[] rgbUpdated = { sum, sum, sum };
        Utilities.setRGB(rgbUpdated, i, j, img);
      }
    }
  }

  // Apply "Norak" filter: Converts pixels to grayscale and sets them to white if brightness is above a threshold.
  public static void applyNorak(BufferedImage img) {
    for (int i = 0; i < img.getHeight(); i++) {
      for (int j = 0; j < img.getWidth(); j++) {
        int[] rgb = Utilities.getRGBArray(i, j, img);
        int sum = (rgb[0] + rgb[1] + rgb[2]) / 3;
        if (sum > 153) {
          int[] rgbUpdated = { sum, sum, sum };
          Utilities.setRGB(rgbUpdated, i, j, img);
        }
      }
    }
  }

  // Apply border filter: Sets pixels on the border of the image to a specified color.
  public static void applyBorder(BufferedImage img, int borderThickness, int[] borderColor) {
    int width = img.getWidth();
    int height = img.getHeight();
    
    for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
            // Check if the current pixel is within the border thickness
            if (j < borderThickness || j >= width - borderThickness ||
                i < borderThickness || i >= height - borderThickness) {
                // Set the border color for the pixels within the border thickness
                img.setRGB(j, i, new Color(borderColor[0], borderColor[1], borderColor[2]).getRGB());
            }
        }
    }
}

  // Apply mirror filter: Creates a mirror effect by swapping pixels between left and right sides.
  public static void applyMirror(BufferedImage img) {
    int width = img.getWidth();
    int height = img.getHeight();

    for (int i = 0; i < height; i++) {
        for (int j = 0; j < width / 2; j++) {
            int[] leftPixel = Utilities.getRGBArray(i, j, img);
            int[] rightPixel = Utilities.getRGBArray(i, width - j - 1, img);

            // Swap the pixel values to create the mirror effect
            Utilities.setRGB(rightPixel, i, j, img);
            Utilities.setRGB(leftPixel, i, width - j - 1, img);
        }
    }
}

  // Apply blur filter (not implemented)
  public static void applyBlur(BufferedImage img) {
    // TODO: Implement the blur filter
  }

  // Apply custom filter: Swaps pixels between the top and bottom of the image.
  public static void applyCustom(BufferedImage img) {
    int width = img.getWidth();
    int height = img.getHeight();

    for (int i = 0; i < height / 2; i++) {
        for (int j = 0; j < width; j++) {
            // Swap pixels between the top and bottom of the image
            int[] topPixel = Utilities.getRGBArray(i, j, img);
            int[] bottomPixel = Utilities.getRGBArray(height - i - 1, j, img);

            Utilities.setRGB(bottomPixel, i, j, img);
            Utilities.setRGB(topPixel, height - i - 1, j, img);
            }
        }
    }
}
