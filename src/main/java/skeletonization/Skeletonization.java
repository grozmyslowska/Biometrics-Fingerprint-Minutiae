package skeletonization;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

public abstract class Skeletonization {

    //na podstawie http://home.agh.edu.pl/~jsw/aos/lab05-t.php
    //oraz https://pages.mini.pw.edu.pl/~panasiukp/teaching/files/KMM.pdf

    static int[][] tab;
    static int width;
    static int height;

    static boolean end;

    static final List<Integer> czworki = Arrays.asList(3, 6, 7, 12, 14, 15, 24, 28, 30, 48, 56, 60, 96, 112, 120, 129, 131, 135, 192, 193, 195, 224, 225, 240);
    static final List<Integer> wyciecia = Arrays.asList(3, 5, 7, 12, 13, 14, 15, 20, 21, 22, 23, 28, 29, 30, 31, 48, 52, 53, 54, 55, 56, 60, 61, 62, 63, 65, 67, 69, 71, 77, 79, 80, 81, 83, 84, 85, 86, 87, 88, 89, 91, 92, 93, 94, 95, 97, 99, 101, 103, 109, 111, 112, 113, 115, 116, 117, 118, 119, 120, 121, 123, 124, 125, 126, 127, 131, 133, 135, 141, 143, 149, 151, 157, 159, 181, 183, 189, 191, 192, 193, 195, 197, 199, 205, 207, 208, 209, 211, 212, 213, 214, 215, 216, 217, 219, 220, 221, 222, 223, 224, 225, 227, 229, 231, 237, 239, 240, 241, 243, 244, 245, 246, 247, 248, 249, 251, 252, 253, 254, 255);

    public static BufferedImage KMM (BufferedImage img){

        width = img.getWidth();
        height = img.getHeight();

        tab = new int[width+2][height+2];

        jedynki(img);

        end = false;
        while (!end) {
            end = true;
            dwojkiitrojki();
            czworki();
            usunczworki();
            usundwojki();
            usuntrojki();
        }

        Color c = new Color(0,0,0);
        Color b = new Color(255,255,255);

        for(int w = 0; w < width; w++) {
            for(int h = 0; h < height; h++) {
                if(tab[w+1][h+1] == 1) img.setRGB(w,h,c.getRGB());
                else img.setRGB(w,h,b.getRGB());
            }
        }

        return img;
    }

    static void jedynki(BufferedImage img){
        for(int w = 0; w < width; w++) {
            for(int h = 0; h < height; h++) {
                Color c = new Color(img.getRGB(w, h));
                tab[w+1][h+1] = c.getRed() == 0 ? 1 : 0;
            }
        }
    }

    static void dwojkiitrojki(){
        for(int w = 1; w <= width; w++) {
            for(int h = 1; h <= height; h++) {

                if(tab[w][h] == 1) {
                    if (tab[w][h - 1] == 0 || tab[w - 1][h] == 0 || tab[w + 1][h] == 0 || tab[w][h + 1] == 0)
                        tab[w][h] = 2;
                    else if (tab[w - 1][h - 1] == 0 || tab[w + 1][h - 1] == 0 || tab[w - 1][h + 1] == 0 || tab[w + 1][h + 1] == 0)
                        tab[w][h] = 3;
                }
            }
        }
    }

    static void czworki(){
        for(int w = 1; w <= width; w++) {
            for (int h = 1; h <= height; h++) {
                if(tab[w][h] == 2)
                    if (czworki.contains(sprawdzarka(w,h))) tab[w][h] = 4;
            }
        }
    }

    static int sprawdzarka(int w, int h){
        int val = 0;
        val += tab[w-1][h-1] != 0 ? 128 : 0;
        val += tab[w][h-1] != 0 ? 1 : 0;
        val += tab[w+1][h-1] != 0 ? 2 : 0;
        val += tab[w-1][h] != 0 ? 64 : 0;
        val += tab[w+1][h] != 0 ? 4 : 0;
        val += tab[w-1][h+1] != 0 ? 32 : 0;
        val += tab[w][h+1] != 0 ? 16 : 0;
        val += tab[w+1][h+1] != 0 ? 8 : 0;
        return val;
    }

    static void usunczworki(){
        for(int w = 1; w <= width; w++) {
            for (int h = 1; h <= height; h++) {
                if(tab[w][h] == 4) tab[w][h] = 0;
            }
        }
    }
    static void usundwojki(){
        for(int w = 1; w <= width; w++) {
            for (int h = 1; h <= height; h++) {
                if(tab[w][h] == 2)
                    if (wyciecia.contains(sprawdzarka(w, h)))
                        tab[w][h] = 0;
                    else
                        tab[w][h] = 1;

            }
        }
    }
    static void usuntrojki(){
        for(int w = 1; w <= width; w++) {
            for (int h = 1; h <= height; h++) {
                if(tab[w][h] == 3) {
                    end = false;
                    if (wyciecia.contains(sprawdzarka(w, h)))
                        tab[w][h] = 0;
                    else
                        tab[w][h] = 1;
                }
            }
        }
    }
}
