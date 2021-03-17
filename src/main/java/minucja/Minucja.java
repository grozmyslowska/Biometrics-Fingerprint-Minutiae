package minucja;

import filtration.Filtration;
import javafx.util.Pair;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;


public abstract class Minucja {
    public static BufferedImage minucja(BufferedImage img) {
        BufferedImage copy = Filtration.deepCopy(img);
        for(int w = 1; w < img.getWidth() - 1; w++) {
            for(int h = 1; h < img.getHeight() - 1; h++) {
                Color c = new Color(img.getRGB(w,h));
                if(c.getRed() == 0){
                    copy.setRGB(w, h, crossingNumber(img, w, h));
                }
            }
        }

        return copy;
    }

    public static int crossingNumber(BufferedImage img, int w, int h){
        int sum=0, pointsDifference=0;
        double cn=0;
        int currentPoint=0, nextPoint=0;

        List<Pair> points = new ArrayList<>();
        Pair<Integer, Integer> p1 = new Pair<>(1,0);
        Pair<Integer, Integer> p2 = new Pair<>(1,-1);
        Pair<Integer, Integer> p3 = new Pair<>(0,-1);
        Pair<Integer, Integer> p4 = new Pair<>(-1,-1);
        Pair<Integer, Integer> p5 = new Pair<>(-1,0);
        Pair<Integer, Integer> p6 = new Pair<>(-1,1);
        Pair<Integer, Integer> p7 = new Pair<>(0,1);
        Pair<Integer, Integer> p8 = new Pair<>(1,1);
        points.add(p1);
        points.add(p2);
        points.add(p3);
        points.add(p4);
        points.add(p5);
        points.add(p6);
        points.add(p7);
        points.add(p8);
        points.add(p1);


        for(int i=0; i<8; i++){
            Color currentColor = new Color(img.getRGB(w + (int)points.get(i).getKey(), h + (int)points.get(i).getValue()));
            Color nextColor = new Color(img.getRGB(w + (int)points.get(i+1).getKey(), h + (int)points.get(i+1).getValue()));

            if(currentColor.getRed() == 0)
                currentPoint = 1;
            else
                currentPoint = 0;

            if(nextColor.getRed() == 0)
                nextPoint = 1;
            else
                nextPoint = 0;

            pointsDifference = currentPoint - nextPoint;
            sum += Math.abs(pointsDifference);
        }

        cn = 0.5 * sum;
        Color color = new Color(0, 0,0);


        //zakończenie
            if(cn == 1){
             color = new Color(255, 5, 17);
        }
            //rozgalezienie
        else if(cn >= 3){
             color = new Color(0, 231, 255, 255);
        }

        return color.getRGB();
    }

    public static BufferedImage delete(BufferedImage img){
        BufferedImage copy = Filtration.deepCopy(img);
        for(int w = 0; w < img.getWidth(); w++) {
            for (int h = 0; h < img.getHeight(); h++) {
                Color currentColor = new Color(img.getRGB(w,h));
                Color minucja = new Color(255, 5, 17);
                if(currentColor.getRGB() == minucja.getRGB()) {
                    int black = 0, white = 0;
                    for (int i = -10; i < 11; i++) {
                        for (int j = -10; j < 11; j++) {
                            int x = w + i;
                            int y = h + j;
                            if(x < img.getWidth() && x>=0 && y < img.getHeight()&& y>=0 ){
                                Color c = new Color(img.getRGB(x, y));
                                if (c.getGreen() != 255)
                                    black++;
                                if (c.getGreen() == 255)
                                    white++;
                            }
                        }
                    }
                    Color color = new Color(0, 0, 0);
                    int threshold = black/white;
                    if (black < 40) {
                        copy.setRGB(w, h, color.getRGB());
                    }
                }
            }
        }
        return copy;
    }

}
