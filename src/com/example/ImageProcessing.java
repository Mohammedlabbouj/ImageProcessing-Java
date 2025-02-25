package com.example;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageProcessing {

    /*
     * the structer of a pixel in java is
     * AAAA AAAA(for transparency) RRRR RRRR(for red)
     * GGGG GGGG(for green) BBBB BBBB(for blue)
     * means 32 bit_int for each pixel means
     * 8 for A and 8 for read , 8 for G and 8 for B
     */
    public final String FilaName;
    int[] histo;
    int maxHisto;
    int rgb[] = new int[3];
    public BufferedImage image;
    int width;
    int height;
    int min;
    int max;
    int t1[][],
            t2[][],
            t3[][],
            tr[][],
            tg[][],
            tb[][];

    public ImageProcessing(File file) throws IOException {
        this.FilaName = file.getName();
        this.image = ImageIO.read(file);
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.min = minPixel();
        this.max = maxPixel();
        t1 = new int[height][width];
        t2 = new int[height][width];
        t3 = new int[height][width];
        tr = new int[height][width];
        tg = new int[height][width];
        tb = new int[height][width];
    }

    public static void main(String[] args) throws IOException {
        ImageProcessing imageProcessing = new ImageProcessing(
                new File("C:\\Users\\XPRISTO\\Pictures\\chessboard.jpg"));

        imageProcessing.susanCornerDetection();

        ImageIO.write(imageProcessing.image, "jpg", new File("C:\\Users\\XPRISTO\\Pictures\\hello.jpg"));
        System.out.println("Done");
    }

    public void getRGB(int j, int i) {
        int pixel = this.image.getRGB(j, i);
        rgb[0] = (pixel >> 16) & 0xff;
        rgb[1] = (pixel >> 8) & 0xff;
        rgb[2] = (pixel) & 0xff;

    }

    public void setRGB(int j, int i, int pixel) {
        this.image.setRGB(j, i, pixel);
    }

    public void blueImage() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int pixel = this.image.getRGB(j, i);
                // Extract blue component (last 8 bits)
                // Set red and green to 0, keep alpha and blue
                // int blue = pixel & 0xFF;
                setRGB(j, i, 0xFF000000 | (0 << 16) | (pixel << 8) | 0);
            }
        }
    }

    // low filter on image
    public void mean5X5() {
        int N = 25; // N = 25
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                getRGB(j, i);
                t1[i][j] = rgb[0];
                t2[i][j] = rgb[1];
                t3[i][j] = rgb[2];
            }
        }
        // your in position i,j is the sum of the 25 pixels around it
        for (int i = 2; i < height - 2; i++) {
            for (int j = 2; j < width - 2; j++) {
                tr[i][j] = (t1[i][j] + t1[i - 2][j - 2] + t1[i - 2][j - 1] + t1[i - 2][j] + t1[i - 2][j + 1]
                        + t1[i - 2][j + 2] + t1[i - 1][j - 2] + t1[i - 1][j - 1] + t1[i - 1][j] + t1[i - 1][j + 1]
                        + t1[i - 1][j + 2] + t1[i][j - 2] + t1[i][j - 1] + t1[i][j + 1] + t1[i][j + 2]
                        + t1[i + 1][j - 2] + t1[i + 1][j - 1] + t1[i + 1][j] + t1[i + 1][j + 1] + t1[i + 1][j + 2]
                        + t1[i + 2][j - 2] + t1[i + 2][j - 1] + t1[i + 2][j] + t1[i + 2][j + 1] + t1[i + 2][j + 2])
                        / N;
                tg[i][j] = (t2[i][j] + t2[i - 2][j - 2] + t2[i - 2][j - 1] + t2[i - 2][j] + t2[i - 2][j + 1]
                        + t2[i - 2][j + 2] + t2[i - 1][j - 2] + t2[i - 1][j - 1] + t2[i - 1][j] + t2[i - 1][j + 1]
                        + t2[i - 1][j + 2] + t2[i][j - 2] + t2[i][j - 1] + t2[i][j + 1] + t2[i][j + 2]
                        + t2[i + 1][j - 2] + t2[i + 1][j - 1] + t2[i + 1][j] + t2[i + 1][j + 1] + t2[i + 1][j + 2]
                        + t2[i + 2][j - 2] + t2[i + 2][j - 1] + t2[i + 2][j] + t2[i + 2][j + 1] + t2[i + 2][j + 2])
                        / N;
                tb[i][j] = (t3[i][j] + t3[i - 2][j - 2] + t3[i - 2][j - 1] + t3[i - 2][j] + t3[i - 2][j + 1]
                        + t3[i - 2][j + 2] + t3[i - 1][j - 2] + t3[i - 1][j - 1] + t3[i - 1][j] + t3[i - 1][j + 1]
                        + t3[i - 1][j + 2] + t3[i][j - 2] + t3[i][j - 1] + t3[i][j + 1] + t3[i][j + 2]
                        + t3[i + 1][j - 2] + t3[i + 1][j - 1] + t3[i + 1][j] + t3[i + 1][j + 1] + t3[i + 1][j + 2]
                        + t3[i + 2][j - 2] + t3[i + 2][j - 1] + t3[i + 2][j] + t3[i + 2][j + 1] + t3[i + 2][j + 2])
                        / N;
                setRGB(j, i, 0xFF000000 | (tr[i][j] << 16) | (tg[i][j] << 8) | tb[i][j]);
            }
        }
    }

    public void mean3X3() {
        int N = 9; // N = 9
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                getRGB(j, i);
                t1[i][j] = rgb[0];
                t2[i][j] = rgb[1];
                t3[i][j] = rgb[2];
            }
        }
        for (int i = 1; i < height - 1; i++) {
            for (int j = 1; j < width - 1; j++) {
                tr[i][j] = (t1[i][j] +
                        t1[i - 1][j] +
                        t1[i + 1][j] +
                        t1[i][j + 1] +
                        t1[i][j - 1] +
                        t1[i - 1][j + 1] +
                        t1[i - 1][j - 1] +
                        t1[i + 1][j + 1] +
                        t1[i + 1][j - 1]) / N;

                tg[i][j] = (t2[i][j] +
                        t2[i][j + 1] +
                        t2[i][j - 1] +
                        t2[i - 1][j] +
                        t2[i + 1][j] +
                        t2[i - 1][j + 1] +
                        t2[i - 1][j - 1] +
                        t2[i + 1][j + 1] +
                        t2[i + 1][j - 1]) / N;
                tb[i][j] = (t3[i][j] +
                        t3[i][j + 1] +
                        t3[i][j - 1] +
                        t3[i - 1][j] +
                        t3[i + 1][j] +
                        t3[i - 1][j + 1] +
                        t3[i - 1][j - 1] +
                        t3[i + 1][j + 1] +
                        t3[i + 1][j - 1]) / N;
                setRGB(j, i, 0xFF000000 | (tr[i][j] << 16) | (tg[i][j] << 8) | tb[i][j]);
            }
        }
    }

    public void Gaussien3_3() {
        float N = 16.0f;
        float kernel[][] = {
                { 1.0f / N, 2.0f / N, 1.0f / N },
                { 2.0f / N, 4.0f / N, 2.0f / N },
                { 1.0f / N, 2.0f / N, 1.0f / N }
        };
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                getRGB(j, i);
                t1[i][j] = rgb[0];
                t2[i][j] = rgb[1];
                t3[i][j] = rgb[2];
            }
        }

        for (int i = 1; i < height - 1; i++) {
            for (int j = 1; j < width - 1; j++) {
                tr[i][j] = (int) (kernel[0][0] * t1[i - 1][j - 1] +
                        kernel[0][1] * t1[i - 1][j] +
                        kernel[0][2] * t1[i - 1][j + 1] +
                        kernel[1][0] * t1[i][j - 1] +
                        kernel[1][1] * t1[i][j] +
                        kernel[1][2] * t1[i][j + 1] +
                        kernel[2][0] * t1[i + 1][j - 1] +
                        kernel[2][1] * t1[i + 1][j] +
                        kernel[2][2] * t1[i + 1][j + 1]);
                tg[i][j] = (int) (kernel[0][0] * t2[i - 1][j - 1] +
                        kernel[0][1] * t2[i - 1][j] +
                        kernel[0][2] * t2[i - 1][j + 1] +
                        kernel[1][0] * t2[i][j - 1] +
                        kernel[1][1] * t2[i][j] +
                        kernel[1][2] * t2[i][j + 1] +
                        kernel[2][0] * t2[i + 1][j - 1] +
                        kernel[2][1] * t2[i + 1][j] +
                        kernel[2][2] * t2[i + 1][j + 1]);
                tb[i][j] = (int) (kernel[0][0] * t3[i - 1][j - 1] +
                        kernel[0][1] * t3[i - 1][j] +
                        kernel[0][2] * t3[i - 1][j + 1] +
                        kernel[1][0] * t3[i][j - 1] +
                        kernel[1][1] * t3[i][j] +
                        kernel[1][2] * t3[i][j + 1] +
                        kernel[2][0] * t3[i + 1][j - 1] +
                        kernel[2][1] * t3[i + 1][j] +
                        kernel[2][2] * t3[i + 1][j + 1]);
                setRGB(j, i, 0xFF000000 | (tr[i][j] << 16) | (tg[i][j] << 8) | tb[i][j]);
            }
        }
    }

    public void Gaussian5_5() {
        // Gaussian kernel 5x5
        float kernel[][] = {
                { 1.0f / 256.0f, 4.0f / 256.0f, 6.0f / 256.0f, 4.0f / 256.0f, 1.0f / 256.0f },
                { 4.0f / 256.0f, 16.0f / 256.0f, 24.0f / 256.0f, 16.0f / 256.0f, 4.0f / 256.0f },
                { 6.0f / 256.0f, 24.0f / 256.0f, 36.0f / 256.0f, 24.0f / 256.0f, 6.0f / 256.0f },
                { 4.0f / 256.0f, 16.0f / 256.0f, 24.0f / 256.0f, 16.0f / 256.0f, 4.0f / 256.0f },
                { 1.0f / 256.0f, 4.0f / 256.0f, 6.0f / 256.0f, 4.0f / 256.0f, 1.0f / 256.0f }
        };
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                getRGB(j, i);
                t1[i][j] = rgb[0];
                t2[i][j] = rgb[1];
                t3[i][j] = rgb[2];
            }
        }
        for (int i = 2; i < height - 2; i++) {
            for (int j = 2; j < width - 2; j++) {
                tr[i][j] = (int) (kernel[0][0] * t1[i - 2][j - 2] +
                        kernel[0][1] * t1[i - 2][j - 1] +
                        kernel[0][2] * t1[i - 2][j] +
                        kernel[0][3] * t1[i - 2][j + 1] +
                        kernel[0][4] * t1[i - 2][j + 2] +
                        kernel[1][0] * t1[i - 1][j - 2] +
                        kernel[1][1] * t1[i - 1][j - 1] +
                        kernel[1][2] * t1[i - 1][j] +
                        kernel[1][3] * t1[i - 1][j + 1] +
                        kernel[1][4] * t1[i - 1][j + 2] +
                        kernel[2][0] * t1[i][j - 2] +
                        kernel[2][1] * t1[i][j - 1] +
                        kernel[2][2] * t1[i][j] +
                        kernel[2][3] * t1[i][j + 1] +
                        kernel[2][4] * t1[i][j + 2] +
                        kernel[3][0] * t1[i + 1][j - 2] +
                        kernel[3][1] * t1[i + 1][j - 1] +
                        kernel[3][2] * t1[i + 1][j] +
                        kernel[3][3] * t1[i + 1][j + 1] +
                        kernel[3][4] * t1[i + 1][j + 2] +
                        kernel[4][0] * t1[i + 2][j - 2] +
                        kernel[4][1] * t1[i + 2][j - 1] +
                        kernel[4][2] * t1[i + 2][j] +
                        kernel[4][3] * t1[i + 2][j + 1] +
                        kernel[4][4] * t1[i + 2][j + 2]);
                tg[i][j] = (int) (kernel[0][0] * t2[i - 2][j - 2] +
                        kernel[0][1] * t2[i - 2][j - 1] +
                        kernel[0][2] * t2[i - 2][j] +
                        kernel[0][3] * t2[i - 2][j + 1] +
                        kernel[0][4] * t2[i - 2][j + 2] +
                        kernel[1][0] * t2[i - 1][j - 2] +
                        kernel[1][1] * t2[i - 1][j - 1] +
                        kernel[1][2] * t2[i - 1][j] +
                        kernel[1][3] * t2[i - 1][j + 1] +
                        kernel[1][4] * t2[i - 1][j + 2] +
                        kernel[2][0] * t2[i][j - 2] +
                        kernel[2][1] * t2[i][j - 1] +
                        kernel[2][2] * t2[i][j] +
                        kernel[2][3] * t2[i][j + 1] +
                        kernel[2][4] * t2[i][j + 2] +
                        kernel[3][0] * t2[i + 1][j - 2] +
                        kernel[3][1] * t2[i + 1][j - 1] +
                        kernel[3][2] * t2[i + 1][j] +
                        kernel[3][3] * t2[i + 1][j + 1] +
                        kernel[3][4] * t2[i + 1][j + 2] +
                        kernel[4][0] * t2[i + 2][j - 2] +
                        kernel[4][1] * t2[i + 2][j - 1] +
                        kernel[4][2] * t2[i + 2][j] +
                        kernel[4][3] * t2[i + 2][j + 1] +
                        kernel[4][4] * t2[i + 2][j + 2]);
                tb[i][j] = (int) (kernel[0][0] * t3[i - 2][j - 2] +
                        kernel[0][1] * t3[i - 2][j - 1] +
                        kernel[0][2] * t3[i - 2][j] +
                        kernel[0][3] * t3[i - 2][j + 1] +
                        kernel[0][4] * t3[i - 2][j + 2] +
                        kernel[1][0] * t3[i - 1][j - 2] +
                        kernel[1][1] * t3[i - 1][j - 1] +
                        kernel[1][2] * t3[i - 1][j] +
                        kernel[1][3] * t3[i - 1][j + 1] +
                        kernel[1][4] * t3[i - 1][j + 2] +
                        kernel[2][0] * t3[i][j - 2] +
                        kernel[2][1] * t3[i][j - 1] +
                        kernel[2][2] * t3[i][j] +
                        kernel[2][3] * t3[i][j + 1] +
                        kernel[2][4] * t3[i][j + 2] +
                        kernel[3][0] * t3[i + 1][j - 2] +
                        kernel[3][1] * t3[i + 1][j - 1] +
                        kernel[3][2] * t3[i + 1][j] +
                        kernel[3][3] * t3[i + 1][j + 1] +
                        kernel[3][4] * t3[i + 1][j + 2] +
                        kernel[4][0] * t3[i + 2][j - 2] +
                        kernel[4][1] * t3[i + 2][j - 1] +
                        kernel[4][2] * t3[i + 2][j] +
                        kernel[4][3] * t3[i + 2][j + 1] +
                        kernel[4][4] * t3[i + 2][j + 2]);
                setRGB(j, i, 0xFF000000 | (tr[i][j] << 16) | (tg[i][j] << 8) | tb[i][j]);
            }
        }
    }

    public void pyramidal() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                getRGB(j, i);
                t1[i][j] = rgb[0];
                t2[i][j] = rgb[1];
                t3[i][j] = rgb[2];
            }
        }

        for (int i = 2; i < height - 2; i++) {
            for (int j = 2; j < width - 2; j++) {
                tr[i][j] = (t1[i - 2][j - 2] + 2 * t1[i - 1][j - 2] + 3 * t1[i][j - 2] + 2 * t1[i + 1][j - 2]
                        + t1[i + 2][j - 2] +
                        2 * t1[i - 2][j - 1] + 4 * t1[i - 1][j - 1] + 6 * t1[i][j - 1] + 4 * t1[i + 1][j - 1]
                        + 2 * t1[i + 2][j - 1] +
                        3 * t1[i - 2][j] + 6 * t1[i - 1][j] + 9 * t1[i][j] + 6 * t1[i + 1][j] + 3 * t1[i + 2][j] +
                        2 * t1[i - 2][j + 1] + 4 * t1[i - 1][j + 1] + 6 * t1[i][j + 1] + 4 * t1[i + 1][j + 1]
                        + 2 * t1[i + 2][j + 1] +
                        t1[i - 2][j + 2] + 2 * t1[i - 1][j + 2] + 3 * t1[i][j + 2] + 2 * t1[i + 1][j + 2]
                        + t1[i + 2][j + 2]) / 81;
                tg[i][j] = (t2[i - 2][j - 2] + 2 * t2[i - 1][j - 2] + 3 * t2[i][j - 2] + 2 * t2[i + 1][j - 2]
                        + t2[i + 2][j - 2] +
                        2 * t2[i - 2][j - 1] + 4 * t2[i - 1][j - 1] + 6 * t2[i][j - 1] + 4 * t2[i + 1][j - 1]
                        + 2 * t2[i + 2][j - 1] +
                        3 * t2[i - 2][j] + 6 * t2[i - 1][j] + 9 * t2[i][j] + 6 * t2[i + 1][j] + 3 * t2[i + 2][j] +
                        2 * t2[i - 2][j + 1] + 4 * t2[i - 1][j + 1] + 6 * t2[i][j + 1] + 4 * t2[i + 1][j + 1]
                        + 2 * t2[i + 2][j + 1] +
                        t2[i - 2][j + 2] + 2 * t2[i - 1][j + 2] + 3 * t2[i][j + 2] + 2 * t2[i + 1][j + 2]
                        + t2[i + 2][j + 2]) / 81;
                tb[i][j] = (t3[i - 2][j - 2] + 2 * t3[i - 1][j - 2] + 3 * t3[i][j - 2] + 2 * t3[i + 1][j - 2]
                        + t3[i + 2][j - 2] +
                        2 * t3[i - 2][j - 1] + 4 * t3[i - 1][j - 1] + 6 * t3[i][j - 1] + 4 * t3[i + 1][j - 1]
                        + 2 * t3[i + 2][j - 1] +
                        3 * t3[i - 2][j] + 6 * t3[i - 1][j] + 9 * t3[i][j] + 6 * t3[i + 1][j] + 3 * t3[i + 2][j] +
                        2 * t3[i - 2][j + 1] + 4 * t3[i - 1][j + 1] + 6 * t3[i][j + 1] + 4 * t3[i + 1][j + 1]
                        + 2 * t3[i + 2][j + 1] +
                        t3[i - 2][j + 2] + 2 * t3[i - 1][j + 2] + 3 * t3[i][j + 2] + 2 * t3[i + 1][j + 2]
                        + t3[i + 2][j + 2]) / 81;
                setRGB(j, i, 0xFF000000 | (tr[i][j] << 16) | (tg[i][j] << 8) | tb[i][j]);
            }
        }
    }

    public void conique() {
        // Convert to grayscale first for better edge detection
        grayscaleConversion();

        // Conical kernel (5x5)
        float[][] kernel = {
                { 0.0f, 0.0f, -1.0f, 0.0f, 0.0f },
                { 0.0f, -1.0f, -2.0f, -1.0f, 0.0f },
                { -1.0f, -2.0f, 16.0f, -2.0f, -1.0f },
                { 0.0f, -1.0f, -2.0f, -1.0f, 0.0f },
                { 0.0f, 0.0f, -1.0f, 0.0f, 0.0f }
        };

        // Copy image to temporary array
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                getRGB(j, i);
                t1[i][j] = rgb[0]; // Image is grayscale, so we can use any channel
            }
        }

        // Apply conical filter
        for (int i = 2; i < height - 2; i++) {
            for (int j = 2; j < width - 2; j++) {
                float sum = 0;

                // Apply convolution
                for (int ki = -2; ki <= 2; ki++) {
                    for (int kj = -2; kj <= 2; kj++) {
                        sum += kernel[ki + 2][kj + 2] * t1[i + ki][j + kj];
                    }
                }

                // Normalize and clamp values
                int result = Math.abs((int) sum);
                result = Math.min(255, Math.max(0, result));

                // Set the same value for all channels to maintain grayscale
                setRGB(j, i, 0xFF000000 | (result << 16) | (result << 8) | result);
            }
        }
    }

    // tarnsform functions on image
    public void derivative() {
        // First pass: Copy image data to temporary arrays
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                getRGB(j, i);
                t1[i][j] = rgb[0];
                t2[i][j] = rgb[1];
                t3[i][j] = rgb[2];
            }
        }

        // Second pass: Apply derivative filter
        for (int i = 1; i < height - 1; i++) {
            for (int j = 1; j < width - 1; j++) {
                // Horizontal and vertical derivatives for red channel
                int deriveHoriz = -t1[i][j - 1] + t1[i][j + 1];
                int deriveVert = -t1[i - 1][j] + t1[i + 1][j];
                tr[i][j] = (int) (Math.sqrt(deriveHoriz * deriveHoriz + deriveVert * deriveVert));

                // Green channel
                deriveHoriz = -t2[i][j - 1] + t2[i][j + 1];
                deriveVert = -t2[i - 1][j] + t2[i + 1][j];
                tg[i][j] = (int) (Math.sqrt(deriveHoriz * deriveHoriz + deriveVert * deriveVert));

                // Blue channel
                deriveHoriz = -t3[i][j - 1] + t3[i][j + 1];
                deriveVert = -t3[i - 1][j] + t3[i + 1][j];
                tb[i][j] = (int) (Math.sqrt(deriveHoriz * deriveHoriz + deriveVert * deriveVert));

                // Set the pixel with the new RGB values
                setRGB(j, i, 0xFF000000 | (tr[i][j] << 16) | (tg[i][j] << 8) | tb[i][j]);
            }
        }
    }

    public void mediane() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                getRGB(j, i);
                t1[i][j] = rgb[0];
                t2[i][j] = rgb[1];
                t3[i][j] = rgb[2];
            }
        }

        for (int i = 1; i < height - 1; i++) {
            for (int j = 1; j < width - 1; j++) {
                int tab1[] = new int[9];
                int tab2[] = new int[9];
                int tab3[] = new int[9];
                // ***********
                int l = 0;
                for (int k = i - 1; k <= i + 1; k++) {// i=0 1 2
                    for (int s = j - 1; s <= j + 1; s++) {// j=0 1 2
                        tab1[l] = t1[k][s];
                        tab2[l] = t2[k][s];
                        tab3[l] = t3[k][s];
                        l++;// l=0 1 2 3 4 5 6 7 8s
                    }

                }
                sortArray(tab1);
                sortArray(tab2);
                sortArray(tab3);

                tr[i][j] = tab1[4];// on remplace la valeur centrale par le median de notre tableau tri�
                tg[i][j] = tab2[4];// on remplace la valeur centrale par le median de notre tableau tri�
                tb[i][j] = tab3[4];// on remplace la valeur centrale par le median de notre tableau tri�
            }
        }
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {

                rgb[0] = tr[i][j];
                rgb[1] = tg[i][j];
                rgb[2] = tb[i][j];
                setRGB(j, i, 0xFF000000 | (rgb[0] << 16) | (rgb[1] << 8) | rgb[2]);
            }
        }
    }

    public void sortArray(int[] tab) {

        int temp;
        for (int i = 0; i < tab.length; i++) {
            for (int j = i + 1; j < tab.length; j++) {
                if (tab[i] > tab[j]) {
                    temp = tab[i];
                    tab[i] = tab[j];
                    tab[j] = temp;
                }
            }
        }

    }

    public ImageIcon getHistogramIcon() {
        BufferedImage histImage = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = histImage.createGraphics();

        // Background color
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);

        // Draw grid lines
        g2d.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i <= 256; i += 32) {
            int x = (int) ((i / 256.0) * width);
            g2d.drawLine(x, 0, x, height);
        }

        // Draw histogram bars
        int barWidth = width / 256;
        for (int i = 0; i < 256; i++) {
            int rHeight = (int) (((double) histo[i] / maxHisto) * height);

            // Draw red histogram
            g2d.setColor(new Color(255, 0, 0, 150));
            g2d.fillRect(i * barWidth, height - rHeight, barWidth, rHeight);

        }

        g2d.dispose();
        return new ImageIcon(histImage);
    }

    public void histogram() {
        histo = new int[256];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                getRGB(j, i);
                int indexHisto = (int) (rgb[0] + rgb[1] + rgb[2]) / 3;
                histo[indexHisto]++;

            }
        }
        maxHisto = Arrays.stream(histo).max().orElse(1);
    }

    public void grayscaleConversion() {
        int min = 0; // Define min and max based on the image histogram
        int max = 255; // Adjust based on your dataset
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                getRGB(j, i);
                /*
                 * f(x,y)′
                 * =
                 * (PIXMAX/max−min).f(x,y)−min
                 * 
                 */
                int gray = (int) (0.299 * rgb[0] + 0.587 * rgb[1] + 0.114 * rgb[2]);
                gray = (int) ((255.0 / (max - min)) * (gray - min));
                int newPixel = 0xFF000000 | (gray << 16) | (gray << 8) | gray;
                this.image.setRGB(j, i, newPixel);
            }
        }

    }

    public void contrastAdjustment() {// Contraste
        int min = minPixel();
        int max = maxPixel();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                getRGB(j, i);
                rgb[0] = (rgb[0] - min) * 255 / (max - min);
                rgb[1] = (rgb[0] - min) * 255 / (max - min);
                rgb[2] = (rgb[0] - min) * 255 / (max - min);
                setRGB(j, i, 0xFF000000 | (rgb[0] << 16) | (rgb[1] << 8) | rgb[2]);
            }
        }

    }

    public int minPixel() {
        getRGB(0, 0);
        int min = rgb[0];
        int l;
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                getRGB(j, i);
                l = rgb[0];
                if (l < min) {
                    min = l;
                }
            }
        }
        return min;
    }

    public int maxPixel() {
        getRGB(0, 0);
        int max = rgb[0];
        int l;
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                getRGB(j, i);
                l = rgb[0];
                if (l > max) {
                    max = l;
                }
            }
        }
        return max;
    }

    public void colorNegative() {// Inversion
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                getRGB(j, i);
                rgb[0] = 255 - rgb[0];
                rgb[1] = 255 - rgb[1];
                rgb[2] = 255 - rgb[2];
                setRGB(j, i, 0xFF000000 | (rgb[0] << 16) | (rgb[1] << 8) | rgb[2]);
            }

        }

    }

    public void Thresholding() {// Binarization
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                getRGB(j, i);
                int avg = (rgb[0] + rgb[1] + rgb[2]) / 3;
                if (avg > 127) {
                    rgb[0] = 255;
                    rgb[1] = 255;
                    rgb[2] = 255;
                } else {
                    rgb[0] = 0;
                    rgb[1] = 0;
                    rgb[2] = 0;
                }

                setRGB(j, i, 0xFF000000 | (rgb[0] << 16) | (rgb[1] << 8) | rgb[2]);
            }
        }

    }

    public void laplacien() {
        grayscaleConversion();
        float k[][] = {
                { 0.0f, 1.0f, 0.0f },
                { 1.0f, -4.0f, 1.0f },
                { 0.0f, 1.0f, 0.0f }
        };
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                getRGB(j, i);
                t1[i][j] = (rgb[0] + rgb[1] + rgb[2]) / 3;
            }
        }
        for (int i = 1; i < height - 1; i++) {
            for (int j = 1; j < width - 1; j++) {
                tr[i][j] = (int) (k[0][0] * t1[i - 1][j - 1] +
                        k[0][1] * t1[i - 1][j] +
                        k[0][2] * t1[i - 1][j + 1] +
                        k[1][0] * t1[i][j - 1] +
                        k[1][1] * t1[i][j] +
                        k[1][2] * t1[i][j + 1] +
                        k[2][0] * t1[i + 1][j - 1] +
                        k[2][1] * t1[i + 1][j] +
                        k[2][2] * t1[i + 1][j + 1]);
                int result = Math.abs((int) tr[i][j]);

                // Clamp values between 0 and 255
                result = Math.min(255, Math.max(0, result));
                setRGB(j, i, 0xFF000000 | (result << 16) | (result << 8) | result & 0xFF);
            }
        }

    }

    public void sobel() {
        // Convert to grayscale first
        grayscaleConversion();

        // Horizontal Sobel kernel
        float[][] kernelX = {
                { -1.0f, 0.0f, 1.0f },
                { -2.0f, 0.0f, 2.0f },
                { -1.0f, 0.0f, 1.0f }
        };

        // Vertical Sobel kernel (corrected)
        float[][] kernelY = {
                { -1.0f, -2.0f, -1.0f },
                { 0.0f, 0.0f, 0.0f },
                { 1.0f, 2.0f, 1.0f }
        };

        // Create temporary array for grayscale image
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                getRGB(j, i);
                // Image is already grayscale, so we can use any channel
                t1[i][j] = rgb[0];
            }
        }

        // Apply Sobel operator
        for (int i = 1; i < height - 1; i++) {
            for (int j = 1; j < width - 1; j++) {
                // Calculate gradients
                float gx = 0, gy = 0;

                // Apply convolution
                for (int k = -1; k <= 1; k++) {
                    for (int l = -1; l <= 1; l++) {
                        gx += kernelX[k + 1][l + 1] * t1[i + k][j + l];
                        gy += kernelY[k + 1][l + 1] * t1[i + k][j + l];
                    }
                }

                // Calculate magnitude using proper gradient formula
                int magnitude = (int) Math.sqrt(gx * gx + gy * gy);

                // Normalize and clamp values
                magnitude = Math.min(255, Math.max(0, magnitude));

                // Set the same value for all channels to maintain grayscale
                setRGB(j, i, 0xFF000000 | (magnitude << 16) | (magnitude << 8) | magnitude);
            }
        }
    }

    public void sobel1() {
        float kernel[][] = {
                { -1.0f, 0.0f, 1.0f },
                { -2.0f, 0.0f, 2.0f },
                { -1.0f, 0.0f, 1.0f }
        };
        float kernel2[][] = { // for the vertical
                { -1.0f, -2.0f, -1.0f },
                { 0.0f, 0.0f, 0.0f },
                { 1.0f, 2.0f, 1.0f },
                { -1.0f, -2.0f, -1.0f },
                { 0.0f, 0.0f, 0.0f },
                { 1.0f, 2.0f, 1.0f }
        };
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                getRGB(j, i);
                t1[i][j] = rgb[0];
                t2[i][j] = rgb[1];
                t3[i][j] = rgb[2];
            }
        }
        for (int i = 1; i < height - 1; i++) {
            for (int j = 1; j < width - 1; j++) {
                int filtreHorisental, filtreVertical;
                filtreHorisental = (int) (kernel[0][0] * t1[i - 1][j - 1] +
                        kernel[0][1] * t1[i - 1][j] +
                        kernel[0][2] * t1[i - 1][j + 1] +
                        kernel[1][0] * t1[i][j - 1] +
                        kernel[1][1] * t1[i][j] +
                        kernel[1][2] * t1[i][j + 1] +
                        kernel[2][0] * t1[i + 1][j - 1] +
                        kernel[2][1] * t1[i + 1][j] +
                        kernel[2][2] * t1[i + 1][j + 1]);
                filtreVertical = (int) (kernel2[0][0] * t1[i - 1][j - 1] +
                        kernel2[0][1] * t1[i - 1][j] +
                        kernel2[0][2] * t1[i - 1][j + 1] +
                        kernel2[1][0] * t1[i][j - 1] +
                        kernel2[1][1] * t1[i][j] +
                        kernel2[1][2] * t1[i][j + 1] +
                        kernel2[2][0] * t1[i + 1][j - 1] +
                        kernel2[2][1] * t1[i + 1][j] +
                        kernel2[2][2] * t1[i + 1][j + 1]);
                tr[i][j] = (int) (Math.sqrt(filtreHorisental) + Math.sqrt(filtreVertical));
                // repate this for the green and blue
                filtreHorisental = (int) (kernel[0][0] * t2[i - 1][j - 1] +
                        kernel[0][1] * t2[i - 1][j] +
                        kernel[0][2] * t2[i - 1][j + 1] +
                        kernel[1][0] * t2[i][j - 1] +
                        kernel[1][1] * t2[i][j] +
                        kernel[1][2] * t2[i][j + 1] +
                        kernel[2][0] * t2[i + 1][j - 1] +
                        kernel[2][1] * t2[i + 1][j] +
                        kernel[2][2] * t2[i + 1][j + 1]);
                filtreVertical = (int) (kernel2[0][0] * t2[i - 1][j - 1] +
                        kernel2[0][1] * t2[i - 1][j] +
                        kernel2[0][2] * t2[i - 1][j + 1] +
                        kernel2[1][0] * t2[i][j - 1] +
                        kernel2[1][1] * t2[i][j] +
                        kernel2[1][2] * t2[i][j + 1] +
                        kernel2[2][0] * t2[i + 1][j - 1] +
                        kernel2[2][1] * t2[i + 1][j] +
                        kernel2[2][2] * t2[i + 1][j + 1]);
                tg[i][j] = (int) (Math.sqrt(filtreHorisental) + Math.sqrt(filtreVertical));
                filtreHorisental = (int) (kernel[0][0] * t3[i - 1][j - 1] +
                        kernel[0][1] * t3[i - 1][j] +
                        kernel[0][2] * t3[i - 1][j + 1] +
                        kernel[1][0] * t3[i][j - 1] +
                        kernel[1][1] * t3[i][j] +
                        kernel[1][2] * t3[i][j + 1] +
                        kernel[2][0] * t3[i + 1][j - 1] +
                        kernel[2][1] * t3[i + 1][j] +
                        kernel[2][2] * t3[i + 1][j + 1]);
                filtreVertical = (int) (kernel2[0][0] * t3[i - 1][j - 1] +
                        kernel2[0][1] * t3[i - 1][j] +
                        kernel2[0][2] * t3[i - 1][j + 1] +
                        kernel2[1][0] * t3[i][j - 1] +
                        kernel2[1][1] * t3[i][j] +
                        kernel2[1][2] * t3[i][j + 1] +
                        kernel2[2][0] * t3[i + 1][j - 1] +
                        kernel2[2][1] * t3[i + 1][j] +
                        kernel2[2][2] * t3[i + 1][j + 1]);
                tb[i][j] = (int) (Math.sqrt(filtreHorisental) + Math.sqrt(filtreVertical));
                setRGB(j, i, 0xFF000000 | (tr[i][j] << 16) | (tg[i][j] << 8) | tb[i][j]);
            }
        }

    }

    public void prewitt() {
        // Convert to grayscale first for better edge detection
        grayscaleConversion();

        // Prewitt horizontal and vertical kernels
        float[][] kernelX = {
                { -1.0f, 0.0f, 1.0f },
                { -1.0f, 0.0f, 1.0f },
                { -1.0f, 0.0f, 1.0f }
        };

        float[][] kernelY = {
                { -1.0f, -1.0f, -1.0f },
                { 0.0f, 0.0f, 0.0f },
                { 1.0f, 1.0f, 1.0f }
        };

        // Copy image to temporary array
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                getRGB(j, i);
                t1[i][j] = rgb[0]; // Image is grayscale, so we can use any channel
            }
        }

        // Apply Prewitt operator
        for (int i = 1; i < height - 1; i++) {
            for (int j = 1; j < width - 1; j++) {
                float gx = 0, gy = 0;

                // Apply convolution
                for (int k = -1; k <= 1; k++) {
                    for (int l = -1; l <= 1; l++) {
                        int pixel = t1[i + k][j + l];
                        gx += kernelX[k + 1][l + 1] * pixel;
                        gy += kernelY[k + 1][l + 1] * pixel;
                    }
                }

                // Calculate gradient magnitude
                int magnitude = (int) Math.sqrt(gx * gx + gy * gy);

                // Normalize and clamp values
                magnitude = Math.min(255, Math.max(0, magnitude));

                // Set the same value for all channels to maintain grayscale
                setRGB(j, i, 0xFF000000 | (magnitude << 16) | (magnitude << 8) | magnitude);
            }
        }
    }

    public void prewitt1() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                getRGB(j, i);
                t1[i][j] = rgb[0];
                t2[i][j] = rgb[1];
                t3[i][j] = rgb[2];
            }
        }
        float kernel[][] = {
                { -1.0f, 0.0f, 1.0f },
                { -1.0f, 0.0f, 1.0f },
                { -1.0f, 0.0f, 1.0f }
        };
        float kernel2[][] = { // for the vertical
                { -1.0f, -1.0f, -1.0f },
                { 0.0f, 0.0f, 0.0f },
                { 1.0f, 1.0f, 1.0f }
        };

        for (int i = 1; i < height - 1; i++) {
            for (int j = 1; j < width - 1; j++) {
                int filtreHorisental, filtreVertical;
                filtreHorisental = (int) (kernel[0][0] * t1[i - 1][j - 1] +
                        kernel[0][1] * t1[i - 1][j] +
                        kernel[0][2] * t1[i - 1][j + 1] +
                        kernel[1][0] * t1[i][j - 1] +
                        kernel[1][1] * t1[i][j] +
                        kernel[1][2] * t1[i][j + 1] +
                        kernel[2][0] * t1[i + 1][j - 1] +
                        kernel[2][1] * t1[i + 1][j] +
                        kernel[2][2] * t1[i + 1][j + 1]);
                filtreVertical = (int) (kernel2[0][0] * t1[i - 1][j - 1] +
                        kernel2[0][1] * t1[i - 1][j] +
                        kernel2[0][2] * t1[i - 1][j + 1] +
                        kernel2[1][0] * t1[i][j - 1] +
                        kernel2[1][1] * t1[i][j] +
                        kernel2[1][2] * t1[i][j + 1] +
                        kernel2[2][0] * t1[i + 1][j - 1] +
                        kernel2[2][1] * t1[i + 1][j] +
                        kernel2[2][2] * t1[i + 1][j + 1]);
                tr[i][j] = (int) (Math.sqrt(filtreHorisental) + Math.sqrt(filtreVertical));

                filtreHorisental = (int) (kernel[0][0] * t2[i - 1][j - 1] +
                        kernel[0][1] * t2[i - 1][j] +
                        kernel[0][2] * t2[i - 1][j + 1] +
                        kernel[1][0] * t2[i][j - 1] +
                        kernel[1][1] * t2[i][j] +
                        kernel[1][2] * t2[i][j + 1] +
                        kernel[2][0] * t2[i + 1][j - 1] +
                        kernel[2][1] * t2[i + 1][j] +
                        kernel[2][2] * t2[i + 1][j + 1]);
                filtreVertical = (int) (kernel2[0][0] * t2[i - 1][j - 1] +
                        kernel2[0][1] * t2[i - 1][j] +
                        kernel2[0][2] * t2[i - 1][j + 1] +
                        kernel2[1][0] * t2[i][j - 1] +
                        kernel2[1][1] * t2[i][j] +
                        kernel2[1][2] * t2[i][j + 1] +
                        kernel2[2][0] * t2[i + 1][j - 1] +
                        kernel2[2][1] * t2[i + 1][j] +
                        kernel2[2][2] * t2[i + 1][j + 1]);
                tg[i][j] = (int) (Math.sqrt(filtreHorisental) + Math.sqrt(filtreVertical));

                filtreHorisental = (int) (kernel[0][0] * t3[i - 1][j - 1] +
                        kernel[0][1] * t3[i - 1][j] +
                        kernel[0][2] * t3[i - 1][j + 1] +
                        kernel[1][0] * t3[i][j - 1] +
                        kernel[1][1] * t3[i][j] +
                        kernel[1][2] * t3[i][j + 1] +
                        kernel[2][0] * t3[i + 1][j - 1] +
                        kernel[2][1] * t3[i + 1][j] +
                        kernel[2][2] * t3[i + 1][j + 1]);
                filtreVertical = (int) (kernel2[0][0] * t3[i - 1][j - 1] +
                        kernel2[0][1] * t3[i - 1][j] +
                        kernel2[0][2] * t3[i - 1][j + 1] +
                        kernel2[1][0] * t3[i][j - 1] +
                        kernel2[1][1] * t3[i][j] +
                        kernel2[1][2] * t3[i][j + 1] +
                        kernel2[2][0] * t3[i + 1][j - 1] +
                        kernel2[2][1] * t3[i + 1][j] +
                        kernel2[2][2] * t3[i + 1][j + 1]);
                tb[i][j] = (int) (Math.sqrt(filtreHorisental) + Math.sqrt(filtreVertical));
                setRGB(j, i, 0xFF000000 | (tr[i][j] << 16) | (tg[i][j] << 8) | tb[i][j]);
            }
        }
    }

    public void gradient(int threshold) {
        grayscaleConversion();

        float[][] kernelX = {
                { -1.0f, 0.0f, 1.0f },
                { -1.0f, 0.0f, 1.0f },
                { -1.0f, 0.0f, 1.0f }
        };

        float[][] kernelY = {
                { -1.0f, -1.0f, -1.0f },
                { 0.0f, 0.0f, 0.0f },
                { 1.0f, 1.0f, 1.0f }
        };

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                getRGB(j, i);
                t1[i][j] = rgb[0];
            }
        }

        for (int i = 1; i < height - 1; i++) {
            for (int j = 1; j < width - 1; j++) {
                float gx = 0, gy = 0;

                for (int k = -1; k <= 1; k++) {
                    for (int l = -1; l <= 1; l++) {
                        int pixel = t1[i + k][j + l];
                        gx += kernelX[k + 1][l + 1] * pixel;
                        gy += kernelY[k + 1][l + 1] * pixel;
                    }
                }

                int magnitude = (int) Math.sqrt(gx * gx + gy * gy);

                // Apply threshold for binary edges
                magnitude = magnitude > threshold ? 255 : 0;

                setRGB(j, i, 0xFF000000 | (magnitude << 16) | (magnitude << 8) | magnitude);
            }
        }
    }

    public void gradient() {
        // Convert to grayscale first for better edge detection
        grayscaleConversion();

        // Horizontal and vertical gradient kernels
        float[][] kernelX = {
                { -1.0f, 0.0f, 1.0f },
                { -1.0f, 0.0f, 1.0f },
                { -1.0f, 0.0f, 1.0f }
        };

        float[][] kernelY = {
                { -1.0f, -1.0f, -1.0f },
                { 0.0f, 0.0f, 0.0f },
                { 1.0f, 1.0f, 1.0f }
        };

        // Copy image to temporary array
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                getRGB(j, i);
                t1[i][j] = rgb[0]; // Image is grayscale, so we can use any channel
            }
        }

        // Apply gradient operator
        for (int i = 1; i < height - 1; i++) {
            for (int j = 1; j < width - 1; j++) {
                float gx = 0, gy = 0;

                // Apply convolution
                for (int k = -1; k <= 1; k++) {
                    for (int l = -1; l <= 1; l++) {
                        int pixel = t1[i + k][j + l];
                        gx += kernelX[k + 1][l + 1] * pixel;
                        gy += kernelY[k + 1][l + 1] * pixel;
                    }
                }

                // Calculate gradient magnitude properly
                int magnitude = (int) Math.sqrt(gx * gx + gy * gy);

                // Normalize and clamp values
                magnitude = Math.min(255, Math.max(0, magnitude));

                // Set the same value for all channels to maintain grayscale
                setRGB(j, i, 0xFF000000 | (magnitude << 16) | (magnitude << 8) | magnitude);
            }
        }
    }

    public void robert() {
        // Convert to grayscale first for better edge detection
        grayscaleConversion();

        // Roberts Cross kernels
        float[][] kernelX = {
                { 1.0f, 0.0f },
                { 0.0f, -1.0f }
        };

        float[][] kernelY = {
                { 0.0f, 1.0f },
                { -1.0f, 0.0f }
        };

        // Create temporary array for grayscale image
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                getRGB(j, i);
                t1[i][j] = rgb[0]; // Image is grayscale, so we can use any channel
            }
        }

        // Apply Roberts operator
        for (int i = 0; i < height - 1; i++) {
            for (int j = 0; j < width - 1; j++) {
                // Calculate gradients using Roberts cross operator
                float gx = kernelX[0][0] * t1[i][j] +
                        kernelX[1][1] * t1[i + 1][j + 1];

                float gy = kernelY[0][1] * t1[i][j + 1] +
                        kernelY[1][0] * t1[i + 1][j];

                // Calculate magnitude using proper gradient formula
                int magnitude = (int) Math.sqrt(gx * gx + gy * gy);

                // Normalize and clamp values
                magnitude = Math.min(255, Math.max(0, magnitude));

                // Set the same value for all channels to maintain grayscale
                setRGB(j, i, 0xFF000000 | (magnitude << 16) | (magnitude << 8) | magnitude);
            }
        }
    }

    public void FPB() {
        // Convert to grayscale first
        grayscaleConversion();

        // Find next power of 2 for padding
        int paddedHeight = nextPowerOf2(height);
        int paddedWidth = nextPowerOf2(width);

        // Initialize padded Complex array
        Complex[][] F = new Complex[paddedHeight][paddedWidth];

        // Fill array with image data and zero padding
        for (int i = 0; i < paddedHeight; i++) {
            for (int j = 0; j < paddedWidth; j++) {
                if (i < height && j < width) {
                    getRGB(j, i);
                    F[i][j] = new Complex(rgb[0], 0);
                } else {
                    F[i][j] = new Complex(0, 0);
                }
            }
        }

        try {
            // Compute FFT
            F = FFT2D.fft2(F);
            F = FFT2D.fftshift(F);

            // Create low-pass filter
            Complex[][] H = new Complex[paddedHeight][paddedWidth];
            int D0 = 30; // Cutoff frequency
            int M2 = paddedHeight / 2;
            int N2 = paddedWidth / 2;

            // Create filter with smooth transition
            for (int i = 0; i < paddedHeight; i++) {
                for (int j = 0; j < paddedWidth; j++) {
                    double distance = Math.sqrt(Math.pow(i - M2, 2) + Math.pow(j - N2, 2));
                    double value = distance <= D0 ? 1.0 : 0.0;
                    H[i][j] = new Complex(value, 0);
                }
            }

            // Apply filter
            Complex[][] G = new Complex[paddedHeight][paddedWidth];
            for (int i = 0; i < paddedHeight; i++) {
                for (int j = 0; j < paddedWidth; j++) {
                    G[i][j] = F[i][j].times(H[i][j]);
                }
            }

            // Inverse FFT
            G = FFT2D.ifftshift(G);
            G = FFT2D.ifft2(G);

            // Convert back to image (crop to original size)
            double max = 0;
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    max = Math.max(max, G[i][j].abs());
                }
            }

            // Normalize and set pixels
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    int value = (int) (G[i][j].abs() * 255 / max);
                    value = Math.min(255, Math.max(0, value));
                    setRGB(j, i, new Color(value, value, value).getRGB());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Helper method to find next power of 2
    private int nextPowerOf2(int n) {
        int power = 1;
        while (power < n) {
            power *= 2;
        }
        return power;
    }

    public void FPH() {
        // Convert to grayscale first
        grayscaleConversion();

        // Get padded dimensions (next power of 2)
        int paddedHeight = nextPowerOf2(height);
        int paddedWidth = nextPowerOf2(width);

        // Initialize padded Complex array
        Complex[][] F = new Complex[paddedHeight][paddedWidth];

        // Fill array with image data and zero padding
        for (int i = 0; i < paddedHeight; i++) {
            for (int j = 0; j < paddedWidth; j++) {
                if (i < height && j < width) {
                    getRGB(j, i);
                    F[i][j] = new Complex(rgb[0], 0);
                } else {
                    F[i][j] = new Complex(0, 0);
                }
            }
        }

        try {
            // Compute FFT
            F = FFT2D.fft2(F);
            F = FFT2D.fftshift(F);

            // Create high-pass filter
            Complex[][] H = new Complex[paddedHeight][paddedWidth];
            int D0 = 1; // Cutoff radius (smaller radius for high-pass)
            int M2 = paddedHeight / 2;
            int N2 = paddedWidth / 2;

            // Initialize filter to ones (pass everything)
            for (int i = 0; i < paddedHeight; i++) {
                for (int j = 0; j < paddedWidth; j++) {
                    H[i][j] = new Complex(1.0, 0);
                }
            }

            // Set central region to zero (block low frequencies)
            for (int i = M2 - D0; i <= M2 + D0; i++) {
                for (int j = N2 - D0; j <= N2 + D0; j++) {
                    if (i >= 0 && i < paddedHeight && j >= 0 && j < paddedWidth) {
                        H[i][j] = new Complex(0.0, 0);
                    }
                }
            }

            // Apply filter
            Complex[][] G = new Complex[paddedHeight][paddedWidth];
            for (int i = 0; i < paddedHeight; i++) {
                for (int j = 0; j < paddedWidth; j++) {
                    G[i][j] = F[i][j].times(H[i][j]);
                }
            }

            // Inverse FFT
            G = FFT2D.ifftshift(G);
            G = FFT2D.ifft2(G);

            // Convert back to image
            double max = 0;
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    max = Math.max(max, G[i][j].abs());
                }
            }

            // Normalize and set pixels (with color inversion)
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    int value = (int) (G[i][j].abs() * 255 / max);
                    value = 255 - Math.min(255, Math.max(0, value)); // Invert colors
                    setRGB(j, i, new Color(value, value, value).getRGB());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void FPHB() {
        // Convert to grayscale first
        grayscaleConversion();

        // Get padded dimensions (next power of 2)
        int paddedHeight = nextPowerOf2(height);
        int paddedWidth = nextPowerOf2(width);

        // Initialize padded Complex array
        Complex[][] F = new Complex[paddedHeight][paddedWidth];

        // Fill array with image data and zero padding
        for (int i = 0; i < paddedHeight; i++) {
            for (int j = 0; j < paddedWidth; j++) {
                if (i < height && j < width) {
                    getRGB(j, i);
                    F[i][j] = new Complex(rgb[0], 0);
                } else {
                    F[i][j] = new Complex(0, 0);
                }
            }
        }

        try {
            // Compute FFT
            F = FFT2D.fft2(F);
            F = FFT2D.fftshift(F);

            // Create high-pass filter
            Complex[][] H = new Complex[paddedHeight][paddedWidth];
            double D0 = 30.0; // Cutoff frequency
            int M2 = paddedHeight / 2;
            int N2 = paddedWidth / 2;

            // Create high-pass filter (inverse of low-pass)
            for (int i = 0; i < paddedHeight; i++) {
                for (int j = 0; j < paddedWidth; j++) {
                    double distance = Math.sqrt(Math.pow(i - M2, 2) + Math.pow(j - N2, 2));
                    double value = distance <= D0 ? 0.0 : 1.0; // Inverse of low-pass
                    H[i][j] = new Complex(value, 0);
                }
            }

            // Apply filter
            Complex[][] G = new Complex[paddedHeight][paddedWidth];
            for (int i = 0; i < paddedHeight; i++) {
                for (int j = 0; j < paddedWidth; j++) {
                    G[i][j] = F[i][j].times(H[i][j]);
                }
            }

            // Inverse FFT
            G = FFT2D.ifftshift(G);
            G = FFT2D.ifft2(G);

            // Convert back to image
            double max = 0;
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    max = Math.max(max, G[i][j].abs());
                }
            }

            // Normalize and set pixels (with color inversion)
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    int value = (int) (G[i][j].abs() * 255 / max);
                    value = 255 - Math.min(255, Math.max(0, value)); // Invert colors
                    setRGB(j, i, new Color(value, value, value).getRGB());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void FPBB() {
        // Convert to grayscale first
        grayscaleConversion();

        // Get padded dimensions (next power of 2)
        int paddedHeight = nextPowerOf2(height);
        int paddedWidth = nextPowerOf2(width);

        // Initialize padded Complex array
        Complex[][] F = new Complex[paddedHeight][paddedWidth];

        // Fill array with image data and zero padding
        for (int i = 0; i < paddedHeight; i++) {
            for (int j = 0; j < paddedWidth; j++) {
                if (i < height && j < width) {
                    getRGB(j, i);
                    F[i][j] = new Complex(rgb[0], 0);
                } else {
                    F[i][j] = new Complex(0, 0);
                }
            }
        }

        try {
            // Compute FFT
            F = FFT2D.fft2(F);
            F = FFT2D.fftshift(F);

            // Create Butterworth low-pass filter
            Complex[][] H = new Complex[paddedHeight][paddedWidth];
            double D0 = 30.0; // Cutoff frequency
            int n = 3; // Filter order
            int M2 = paddedHeight / 2;
            int N2 = paddedWidth / 2;

            // Create Butterworth filter
            for (int i = 0; i < paddedHeight; i++) {
                for (int j = 0; j < paddedWidth; j++) {
                    double distance = Math.sqrt(Math.pow(i - M2, 2) + Math.pow(j - N2, 2));
                    double butterworth = 1.0 / (1.0 + Math.pow(distance / D0, 2 * n));
                    H[i][j] = new Complex(butterworth, 0);
                }
            }

            // Apply filter
            Complex[][] G = new Complex[paddedHeight][paddedWidth];
            for (int i = 0; i < paddedHeight; i++) {
                for (int j = 0; j < paddedWidth; j++) {
                    G[i][j] = F[i][j].times(H[i][j]);
                }
            }

            // Inverse FFT
            G = FFT2D.ifftshift(G);
            G = FFT2D.ifft2(G);

            // Convert back to image
            double max = 0;
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    max = Math.max(max, G[i][j].abs());
                }
            }

            // Normalize and set pixels
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    int value = (int) (G[i][j].abs() * 255 / max);
                    value = Math.min(255, Math.max(0, value));
                    setRGB(j, i, new Color(value, value, value).getRGB());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void erosion() {
        // Convert to grayscale first
        grayscaleConversion();

        // Create temporary arrays for processing
        int[][] tempImage = new int[height][width];

        // Copy image data to temporary array
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                getRGB(j, i);
                tempImage[i][j] = rgb[0]; // Use red channel as we're in grayscale
            }
        }

        // Create structuring element (using cross pattern as default)
        boolean[][] se = new boolean[9][9];

        // Create cross structuring element
        for (int i = 0; i < 9; i++) {
            se[4][i] = true; // Horizontal line
            se[i][4] = true; // Vertical line
        }

        // Apply erosion
        int seRadius = 4; // Half size of structuring element (9x9 => radius 4)
        for (int i = seRadius; i < height - seRadius; i++) {
            for (int j = seRadius; j < width - seRadius; j++) {
                int minVal = 255;

                // Apply structuring element
                for (int si = -seRadius; si <= seRadius; si++) {
                    for (int sj = -seRadius; sj <= seRadius; sj++) {
                        if (se[si + seRadius][sj + seRadius]) {
                            int pixelVal = tempImage[i + si][j + sj];
                            minVal = Math.min(minVal, pixelVal);
                        }
                    }
                }

                // Set eroded value
                setRGB(j, i, new Color(minVal, minVal, minVal).getRGB());
            }
        }
    }

    // Optional: Method to create different structuring elements
    private boolean[][] createStructuringElement(String type) {

        boolean[][] se = new boolean[9][9];

        switch (type) {
            case "cross":
                // Cross pattern
                for (int i = 0; i < 9; i++) {
                    se[4][i] = true;
                    se[i][4] = true;
                }
                break;

            case "square":
                // Square pattern
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        se[i][j] = true;
                    }
                }
                break;

            case "ellipse":
                // Ellipse pattern
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        if (((Math.pow(i - 4, 2)) / 4.0 + (Math.pow(j - 4, 2)) / 9.0) <= 1) {
                            se[i][j] = true;
                        }
                    }
                }
                break;
        }

        return se;
    }

    public void dilation() {
        // Convert to grayscale first
        grayscaleConversion();
        // Create temporary arrays for processing
        int[][] tempImage = new int[height][width];

        // Copy image data to temporary array
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                getRGB(j, i);
                tempImage[i][j] = rgb[0]; // Use red channel as we're in grayscale
            }
        }

        // Create structuring element (default to cross pattern)
        boolean[][] se = createStructuringElement("cross");

        // Apply dilation
        int seRadius = 4; // Half size of structuring element (9x9 => radius 4)
        for (int i = seRadius; i < height - seRadius; i++) {
            for (int j = seRadius; j < width - seRadius; j++) {
                int maxVal = 0;

                // Apply structuring element
                for (int si = -seRadius; si <= seRadius; si++) {
                    for (int sj = -seRadius; sj <= seRadius; sj++) {
                        if (se[si + seRadius][sj + seRadius]) {
                            int pixelVal = tempImage[i + si][j + sj];
                            maxVal = Math.max(maxVal, pixelVal);
                        }
                    }
                }

                // Set dilated value
                setRGB(j, i, new Color(maxVal, maxVal, maxVal).getRGB());
            }
        }
    }

    public void opening() {
        // Convert to grayscale first
        grayscaleConversion();

        // Create temporary arrays for processing
        int[][] tempImage = new int[height][width];
        int[][] erodedImage = new int[height][width];

        // Copy image data to temporary array
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                getRGB(j, i);
                tempImage[i][j] = rgb[0]; // Use red channel as we're in grayscale
            }
        }

        // Create structuring element (using cross pattern as default)
        boolean[][] se = createStructuringElement("cross");

        // Step 1: Apply erosion
        int seRadius = 4; // Half size of structuring element (9x9 => radius 4)
        for (int i = seRadius; i < height - seRadius; i++) {
            for (int j = seRadius; j < width - seRadius; j++) {
                int minVal = 255;

                // Apply structuring element
                for (int si = -seRadius; si <= seRadius; si++) {
                    for (int sj = -seRadius; sj <= seRadius; sj++) {
                        if (se[si + seRadius][sj + seRadius]) {
                            int pixelVal = tempImage[i + si][j + sj];
                            minVal = Math.min(minVal, pixelVal);
                        }
                    }
                }

                erodedImage[i][j] = minVal;
            }
        }

        // Step 2: Apply dilation to the eroded image
        for (int i = seRadius; i < height - seRadius; i++) {
            for (int j = seRadius; j < width - seRadius; j++) {
                int maxVal = 0;

                // Apply structuring element
                for (int si = -seRadius; si <= seRadius; si++) {
                    for (int sj = -seRadius; sj <= seRadius; sj++) {
                        if (se[si + seRadius][sj + seRadius]) {
                            int pixelVal = erodedImage[i + si][j + sj];
                            maxVal = Math.max(maxVal, pixelVal);
                        }
                    }
                }

                // Set opened value
                setRGB(j, i, new Color(maxVal, maxVal, maxVal).getRGB());
            }
        }
    }

    public void closing() {
        // Convert to grayscale first
        grayscaleConversion();

        // Create temporary arrays for processing
        int[][] tempImage = new int[height][width];
        int[][] dilatedImage = new int[height][width];

        // Copy image data to temporary array
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                getRGB(j, i);
                tempImage[i][j] = rgb[0]; // Use red channel as we're in grayscale
            }
        }

        // Create structuring element (using cross pattern as default)
        boolean[][] se = createStructuringElement("cross");

        // Step 1: Apply dilation
        int seRadius = 4; // Half size of structuring element (9x9 => radius 4)
        for (int i = seRadius; i < height - seRadius; i++) {
            for (int j = seRadius; j < width - seRadius; j++) {
                int maxVal = 0;

                // Apply structuring element
                for (int si = -seRadius; si <= seRadius; si++) {
                    for (int sj = -seRadius; sj <= seRadius; sj++) {
                        if (se[si + seRadius][sj + seRadius]) {
                            int pixelVal = tempImage[i + si][j + sj];
                            maxVal = Math.max(maxVal, pixelVal);
                        }
                    }
                }

                dilatedImage[i][j] = maxVal;
            }
        }

        // Step 2: Apply erosion to the dilated image
        for (int i = seRadius; i < height - seRadius; i++) {
            for (int j = seRadius; j < width - seRadius; j++) {
                int minVal = 255;

                // Apply structuring element
                for (int si = -seRadius; si <= seRadius; si++) {
                    for (int sj = -seRadius; sj <= seRadius; sj++) {
                        if (se[si + seRadius][sj + seRadius]) {
                            int pixelVal = dilatedImage[i + si][j + sj];
                            minVal = Math.min(minVal, pixelVal);
                        }
                    }
                }

                // Set closed value
                setRGB(j, i, new Color(minVal, minVal, minVal).getRGB());
            }
        }
    }

    public void Morphologie() {
        opening();
        closing();
    }


    public void harrisCornerDetection() {
        grayscaleConversion();
        // Parameters
        double lambda = 0.04;
        double sigma = 1.5;
        int seuil = 150;
        int r = 4;
        int w = (int) (5 * sigma);

        // Convert to grayscale double array
        double[][] imd = new double[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                getRGB(j, i);
                imd[i][j] = rgb[0];
            }
        }

        // Sobel kernels
        double[][] dx = {
                { -1, 0, 1 },
                { -2, 0, 2 },
                { -1, 0, 1 }
        };
        double[][] dy = {
                { -1, -2, -1 },
                { 0, 0, 0 },
                { 1, 2, 1 }
        };

        // Compute derivatives
        double[][] Ix = conv2(imd, dx);
        double[][] Iy = conv2(imd, dy);

        // Generate Gaussian kernel
        double[][] g = generateGaussianKernel(w, sigma);

        // Compute products and apply Gaussian smoothing
        double[][] Ix2 = conv2(multiply(Ix, Ix), g);
        double[][] Iy2 = conv2(multiply(Iy, Iy), g);
        double[][] Ixy = conv2(multiply(Ix, Iy), g);

        // Compute Harris response
        double[][] R = new double[height][width];
        double maxR = Double.NEGATIVE_INFINITY;

        // Calculate R matrix and find maximum value
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                double detM = Ix2[i][j] * Iy2[i][j] - Ixy[i][j] * Ixy[i][j];
                double traceM = Ix2[i][j] + Iy2[i][j];
                R[i][j] = detM - lambda * traceM * traceM;
                maxR = Math.max(maxR, R[i][j]);
            }
        }

        // Normalize and threshold
        double[][] R1 = new double[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                R1[i][j] = (2000.0 / (1.0 + maxR)) * R[i][j];
                if (R1[i][j] <= seuil) {
                    R1[i][j] = 0;
                }
            }
        }

        // Non-maximum suppression
        for (int i = r; i < height - r; i++) {
            for (int j = r; j < width - r; j++) {
                if (R1[i][j] > 0) {
                    double centerValue = R1[i][j];
                    boolean isMax = true;

                    for (int ki = -r; ki <= r && isMax; ki++) {
                        for (int kj = -r; kj <= r && isMax; kj++) {
                            if (ki != 0 || kj != 0) {
                                if (R1[i + ki][j + kj] > centerValue) {
                                    isMax = false;
                                }
                            }
                        }
                    }

                    if (!isMax) {
                        R1[i][j] = 0;
                    }
                }
            }
        }

        // Draw corners with cross pattern
        for (int i = 1; i < height - 1; i++) {
            for (int j = 1; j < width - 1; j++) {
                if (R1[i][j] > 0) {
                    setRGB(j, i, new Color(255, 0, 0).getRGB());
                    setRGB(j - 1, i, new Color(255, 0, 0).getRGB());
                    setRGB(j + 1, i, new Color(255, 0, 0).getRGB());
                    setRGB(j, i - 1, new Color(255, 0, 0).getRGB());
                    setRGB(j, i + 1, new Color(255, 0, 0).getRGB());
                }
            }
        }
    }

    private double[][] multiply(double[][] a, double[][] b) {
        int height = a.length;
        int width = a[0].length;
        double[][] result = new double[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                result[i][j] = a[i][j] * b[i][j];
            }
        }
        return result;
    }

    // 2D Convolution
    private double[][] conv2(double[][] image, double[][] kernel) {
        int imH = image.length, imW = image[0].length;
        int kH = kernel.length, kW = kernel[0].length;
        int padH = kH / 2, padW = kW / 2;
        double[][] result = new double[imH][imW];

        for (int i = 0; i < imH; i++) {
            for (int j = 0; j < imW; j++) {
                double sum = 0;
                for (int m = 0; m < kH; m++) {
                    for (int n = 0; n < kW; n++) {
                        int r = i + m - padH;
                        int c = j + n - padW;
                        if (r >= 0 && r < imH && c >= 0 && c < imW) {
                            sum += kernel[kH - 1 - m][kW - 1 - n] * image[r][c]; // Kernel flipped
                        }
                    }
                }
                result[i][j] = sum;
            }
        }
        return result;
    }

    // Gaussian Kernel Generation
    private double[][] generateGaussianKernel(int size, double sigma) {
        double[][] kernel = new double[size][size];
        int half = size / 2;
        double sum = 0;
        double s = 2.0 * sigma * sigma;

        for (int x = -half; x <= half; x++) {
            for (int y = -half; y <= half; y++) {
                double r = x * x + y * y;
                kernel[x + half][y + half] = Math.exp(-r / s);
                sum += kernel[x + half][y + half];
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                kernel[i][j] /= sum; // Normalize
            }
        }
        return kernel;
    }

    public void susanCornerDetection() {
        // Convert to grayscale first
        grayscaleConversion();

        int rayon = 2;
        double alpha = 0.5; // 50/100
        int r = 2;

        // Create circular mask
        boolean[][] mask = new boolean[2 * rayon + 1][2 * rayon + 1];
        int[][] maskValues = new int[2 * rayon + 1][2 * rayon + 1];

        // Create circular mask pattern
        for (int i = 0; i < 2 * rayon + 1; i++) {
            for (int j = 0; j < 2 * rayon + 1; j++) {
                double distance = Math.sqrt(Math.pow(i - rayon, 2) + Math.pow(j - rayon, 2));
                if (distance <= rayon) {
                    mask[i][j] = true;
                    maskValues[i][j] = 1;
                }
            }
        }

        // Calculate maximum response (sum of mask)
        int maxResponse = 0;
        for (int i = 0; i < mask.length; i++) {
            for (int j = 0; j < mask[0].length; j++) {
                if (mask[i][j])
                    maxResponse++;
            }
        }

        // SUSAN response array
        double[][] f = new double[height][width];

        // Apply SUSAN operator
        for (int i = rayon; i < height - rayon; i++) {
            for (int j = rayon; j < width - rayon; j++) {
                getRGB(j, i);
                int nucleusIntensity = rgb[0];
                double similarity = 0;

                // Compare nucleus with each pixel in the mask
                for (int mi = -rayon; mi <= rayon; mi++) {
                    for (int mj = -rayon; mj <= rayon; mj++) {
                        if (mask[mi + rayon][mj + rayon]) {
                            getRGB(j + mj, i + mi);
                            int pixelIntensity = rgb[0];

                            // Calculate similarity using Gaussian function
                            double diff = (pixelIntensity - nucleusIntensity) / (double) maxResponse;
                            similarity += Math.exp(-Math.pow(diff, 6));
                        }
                    }
                }

                f[i][j] = similarity;
            }
        }

        // Find min and max responses
        double minF = Double.MAX_VALUE;
        double maxF = Double.MIN_VALUE;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (f[i][j] > 0) {
                    minF = Math.min(minF, f[i][j]);
                    maxF = Math.max(maxF, f[i][j]);
                }
            }
        }

        // Threshold for corner detection
        double threshold = minF + alpha * (maxF - minF);

        // Non-maximum suppression and corner detection
        int d = 2 * r + 1;
        for (int i = r; i < height - r; i += d) {
            for (int j = r; j < width - r; j += d) {
                double minWindow = Double.MAX_VALUE;

                // Find minimum in window
                for (int wi = -r; wi <= r; wi++) {
                    for (int wj = -r; wj <= r; wj++) {
                        if (f[i + wi][j + wj] > 0) {
                            minWindow = Math.min(minWindow, f[i + wi][j + wj]);
                        }
                    }
                }

                // Mark corners
                if (minWindow <= threshold) {
                    setRGB(j, i, new Color(255, 0, 0).getRGB());
                    // Mark cross pattern for better visibility
                    setRGB(j - 1, i, new Color(255, 0, 0).getRGB());
                    setRGB(j + 1, i, new Color(255, 0, 0).getRGB());
                    setRGB(j, i - 1, new Color(255, 0, 0).getRGB());
                    setRGB(j, i + 1, new Color(255, 0, 0).getRGB());
                }
            }
        }
    }

}