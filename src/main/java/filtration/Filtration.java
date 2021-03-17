package filtration;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.Arrays;

public abstract class Filtration {

    public static BufferedImage median (BufferedImage img, int r) {
        BufferedImage copy = deepCopy(img);
        for(int w = 0; w < img.getWidth(); w++) {
            for(int h = 0; h < img.getHeight(); h++) {

                int[] Red = new int[r*r];
                int[] Green = new int[r*r];
                int[] Blue = new int[r*r];
                int iter = 0;

                for(int i = -r/2; i <= r/2; i++) {
                    for(int j = -r/2; j <= r/2; j++) {
                        int ii = i, jj = j;
                        if(w + i < 0 || w + i > img.getWidth() - 1) ii = 0;
                        if(h + j < 0 || h + j > img.getHeight() - 1) jj = 0;
                        Color c = new Color(img.getRGB(w + ii, h + jj));
                        Red[iter] = c.getRed();
                        Green[iter] = c.getGreen();
                        Blue[iter++] = c.getBlue();
                    }
                }

                Arrays.sort(Red);
                Arrays.sort(Green);
                Arrays.sort(Blue);

                Color c = new Color(Red[r*r/2], Green[r*r/2], Blue[r*r/2]);

                copy.setRGB(w, h, c.getRGB());
            }
        }
        return copy;
    }

    public static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(bi.getRaster().createCompatibleWritableRaster());
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

}
