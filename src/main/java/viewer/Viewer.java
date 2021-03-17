package viewer;

import binarization.Binarization;
import filtration.Filtration;
import minucja.Minucja;
import shared.ImageSharedOperations;
import skeletonization.Skeletonization;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;

public class Viewer extends JFrame {

    private final JMenuBar menuBar = new JMenuBar();

    private final JMenu files = new JMenu("File");
    private final JMenuItem loadImage = new JMenuItem("Load image");
    private final JMenuItem saveImageJPG = new JMenuItem("Save image as jpg");
    private final JMenuItem saveImagePNG = new JMenuItem("Save image as png");
    private final JMenuItem saveImageTIFF = new JMenuItem("Save image as tiff");
    private final JMenuItem saveImageBMP = new JMenuItem("Save image as bmp");
//    private final JMenuItem saveImageSVG = new JMenuItem("Save image as svg");

    private final JMenu binarization = new JMenu("Binarization");
    private final JMenuItem binarizationOtsu = new JMenuItem("Otsu's method");

    private final JMenu filter = new JMenu("Filter");
    private final JMenuItem filterMedian3 = new JMenuItem("Median filter (mask 3x3)");
    private final JMenuItem filterMedian5 = new JMenuItem("Median filter (mask 5x5)");

    private final JMenu skeletonization = new JMenu("Skeletonization");
    private final JMenuItem skeletonizationKMM = new JMenuItem("KMM algorithm");

    private final JMenu minucja = new JMenu("Minucja");
    private final JMenuItem crossingNumber = new JMenuItem("Crossing Number Algorithm");
    private final JMenuItem falseMin = new JMenuItem("Delete false minutia");

    private final JLabel imageLabel = new JLabel();

    public Viewer() {
        this.setLayout(new BorderLayout());
        this.setTitle("Podstawy Biometrii - Projekt 1");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

        this.menuBar.add(this.files);
        this.files.add(this.loadImage);
        this.files.add(this.saveImageJPG);
        this.files.add(this.saveImagePNG);
        this.files.add(this.saveImageTIFF);
        this.files.add(this.saveImageBMP);

        this.menuBar.add(this.binarization);
        this.binarization.add(this.binarizationOtsu);

        this.menuBar.add(this.filter);
        this.filter.add(this.filterMedian3);
        this.filter.add(this.filterMedian5);

        this.menuBar.add(this.skeletonization);
        this.skeletonization.add(this.skeletonizationKMM);

        this.menuBar.add(this.minucja);
        this.minucja.add(this.crossingNumber);
        this.minucja.add(this.falseMin);


        this.add(this.menuBar, BorderLayout.NORTH);
        this.add(this.imageLabel, BorderLayout.CENTER);
        this.imageLabel.setHorizontalAlignment(JLabel.CENTER);
        this.imageLabel.setVerticalAlignment(JLabel.CENTER);

        this.loadImage.addActionListener((ActionEvent e) -> {
            JFileChooser imageOpener = new JFileChooser();
            imageOpener.setFileFilter(new FileFilter() {
                @Override
                public boolean accept(File f) {
                    String fileName = f.getName().toLowerCase();
                    if (fileName.endsWith(".jpg") || fileName.endsWith(".png")
                            || fileName.endsWith(".tiff") || fileName.endsWith(".bmp")) {
                        return true;
                    } else return false;
                }

                @Override
                public String getDescription() {
                    return "Image files (.jpg, .png, .tiff, .bmp)";
                }
            });

            int returnValue = imageOpener.showDialog(null, "Select image");
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                BufferedImage img = ImageSharedOperations.loadImage(imageOpener.getSelectedFile().getPath());
                this.imageLabel.setIcon(new ImageIcon(img));
            }
        });

        this.saveImageJPG.addActionListener((ActionEvent e) -> {
            String path = "./image.jpg";
            BufferedImage img = ImageSharedOperations.convertIconToImage((ImageIcon) this.imageLabel.getIcon());
            ImageSharedOperations.saveImage(img, path, "jpg");
        });
        this.saveImagePNG.addActionListener((ActionEvent e) -> {
            String path = "./image.png";
            BufferedImage img = ImageSharedOperations.convertIconToImage((ImageIcon) this.imageLabel.getIcon());
            ImageSharedOperations.saveImage(img, path, "png");
        });
        this.saveImageTIFF.addActionListener((ActionEvent e) -> {
            String path = "./image.tiff";
            BufferedImage img = ImageSharedOperations.convertIconToImage((ImageIcon) this.imageLabel.getIcon());
            ImageSharedOperations.saveImage(img, path, "tiff");
        });
        this.saveImageBMP.addActionListener((ActionEvent e) -> {
            String path = "./image.bmp";
            BufferedImage img = ImageSharedOperations.convertIconToImage((ImageIcon) this.imageLabel.getIcon());
            ImageSharedOperations.saveImage(img, path, "bmp");
        });


        this.binarizationOtsu.addActionListener((ActionEvent e) -> {
            BufferedImage img = ImageSharedOperations.convertIconToImage((ImageIcon) this.imageLabel.getIcon());
            this.imageLabel.setIcon(new ImageIcon(Binarization.binarizationOtsu(img)));
        });

        this.filterMedian3.addActionListener((ActionEvent e) -> {
            BufferedImage img = ImageSharedOperations.convertIconToImage((ImageIcon) this.imageLabel.getIcon());
            this.imageLabel.setIcon(new ImageIcon(Filtration.median(img,3)));
        });

        this.filterMedian5.addActionListener((ActionEvent e) -> {
            BufferedImage img = ImageSharedOperations.convertIconToImage((ImageIcon) this.imageLabel.getIcon());
            this.imageLabel.setIcon(new ImageIcon(Filtration.median(img,5)));
        });

        this.skeletonizationKMM.addActionListener((ActionEvent e) -> {
            BufferedImage img = ImageSharedOperations.convertIconToImage((ImageIcon) this.imageLabel.getIcon());
            this.imageLabel.setIcon(new ImageIcon(Skeletonization.KMM(img)));
        });

        this.crossingNumber.addActionListener((ActionEvent e) -> {
            BufferedImage img = ImageSharedOperations.convertIconToImage((ImageIcon) this.imageLabel.getIcon());
            this.imageLabel.setIcon(new ImageIcon(Minucja.minucja(img)));
        });

        this.falseMin.addActionListener((ActionEvent e) -> {
            BufferedImage img = ImageSharedOperations.convertIconToImage((ImageIcon) this.imageLabel.getIcon());
            this.imageLabel.setIcon(new ImageIcon(Minucja.delete(img)));
        });
    }
}
