package com.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageProcessingUI extends JFrame implements ActionListener {
    BufferedImage image;
    ImageProcessing imageProcessing;
    File selectedFile;

    JPanel mainPanel;
    JPanel imagePanel;
    JPanel controlsPanel;
    JPanel filtersHigh;
    JPanel filtersLow;
    JPanel transformations;
    JPanel traitementsFrequentiels;
    JPanel optionsPanel;
    JPanel Morphologie;
    JPanel LesPoints;

    JLabel imageLabel;
    JLabel processedImage;

    JButton openButton;
    JButton saveButton;
    JButton quiteButton;
    JButton laplacianButton;
    JButton sobelButton;
    JButton gradientButton;
    JButton prewittButton;
    JButton robertButton;
    JButton mean3x3Button;
    JButton gaussian3x3Button;
    JButton medianButton;
    JButton mean5x5Button;
    JButton gaussian5x5Button;
    JButton pyramidalButton;
    JButton ceniqueButton;
    JButton inversionButton;
    JButton binarisationButton;
    JButton contrasteButton;
    JButton divisionButton;
    JButton niveauDeGrisButton;
    JButton histogrammeButton;
    JButton FPB;
    JButton FPBB;
    JButton FPH;
    JButton FPHB;
    JButton erosionButton;
    JButton dilatationButton;
    JButton ouvertureButton;
    JButton fermetureButton;
    JButton MorphologieButton;
    JButton susanButton;
    JButton harrisButton;

    public ImageProcessingUI() {
        setTitle("Image Processing Interface");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2, 1));
        mainPanel.setBackground(new Color(200, 150, 200));

        imagePanel = new JPanel(new GridLayout(1, 2));
        imagePanel.setBackground(new Color(200, 150, 200));
        imageLabel = new JLabel("IMAGE ORIGINALE", SwingConstants.CENTER);
        processedImage = new JLabel("IMAGE TRAITÉE", SwingConstants.CENTER);

        imagePanel.add(imageLabel);
        imagePanel.add(processedImage);
        mainPanel.add(imagePanel);

        controlsPanel = new JPanel(new GridLayout(1, 3));
        controlsPanel.setBackground(new Color(200, 150, 200));

        filtersHigh = new JPanel(new GridLayout(3, 2));
        filtersHigh.setBorder(BorderFactory.createTitledBorder("Filtre passe haut"));
        laplacianButton = new JButton("Laplacien");
        sobelButton = new JButton("Sobel");
        gradientButton = new JButton("Gradient");
        prewittButton = new JButton("Prewitt");
        robertButton = new JButton("Robert");
        filtersHigh.add(laplacianButton);
        filtersHigh.add(sobelButton);
        filtersHigh.add(gradientButton);
        filtersHigh.add(prewittButton);
        filtersHigh.add(robertButton);

        filtersLow = new JPanel(new GridLayout(3, 2));
        filtersLow.setBorder(BorderFactory.createTitledBorder("Filtre passe bas"));
        mean3x3Button = new JButton("Mean3,3");
        gaussian3x3Button = new JButton("Gauss3,3");
        medianButton = new JButton("Mediane");
        mean5x5Button = new JButton("Mean5,5");
        gaussian5x5Button = new JButton("Gauss5,5");
        pyramidalButton = new JButton("Pyramidal");
        ceniqueButton = new JButton("Cenique");
        filtersLow.add(mean3x3Button);
        filtersLow.add(gaussian3x3Button);
        filtersLow.add(medianButton);
        filtersLow.add(mean5x5Button);
        filtersLow.add(gaussian5x5Button);
        filtersLow.add(pyramidalButton);
        filtersLow.add(ceniqueButton);

        transformations = new JPanel(new GridLayout(3, 2));
        transformations.setBorder(BorderFactory.createTitledBorder("Transformation"));
        inversionButton = new JButton("Inversion");
        binarisationButton = new JButton("Binarisation");
        contrasteButton = new JButton("Contraste");
        divisionButton = new JButton("Division");
        niveauDeGrisButton = new JButton("Niveau de gris");
        histogrammeButton = new JButton("Histogramme");
        transformations.add(inversionButton);
        transformations.add(binarisationButton);
        transformations.add(contrasteButton);
        transformations.add(divisionButton);
        transformations.add(niveauDeGrisButton);
        transformations.add(histogrammeButton);

        // Traitements fréquentiels
        traitementsFrequentiels = new JPanel(new GridLayout(3, 2));
        traitementsFrequentiels.setBorder(BorderFactory.createTitledBorder("Traitements fréquentiels"));
        FPB = new JButton("FPB");
        FPBB = new JButton("FPBB");
        FPH = new JButton("FPH");
        FPHB = new JButton("FPHB");
        traitementsFrequentiels.add(FPB);
        traitementsFrequentiels.add(FPBB);
        traitementsFrequentiels.add(FPH);
        traitementsFrequentiels.add(FPHB);

        controlsPanel.add(filtersHigh);
        controlsPanel.add(filtersLow);
        controlsPanel.add(transformations);
        controlsPanel.add(traitementsFrequentiels);

        optionsPanel = new JPanel(new GridLayout(3, 1));
        optionsPanel.setBorder(BorderFactory.createTitledBorder("Options"));
        openButton = new JButton("Ouvrir");
        saveButton = new JButton("Enregistrer");
        quiteButton = new JButton("Quitter");
        optionsPanel.add(openButton);
        optionsPanel.add(saveButton);
        optionsPanel.add(quiteButton);

        Morphologie = new JPanel(new GridLayout(5, 1));
        Morphologie.setBorder(BorderFactory.createTitledBorder("Morphologie"));
        // optionsPanel.add(Morphologie);

        MorphologieButton = new JButton("MorphologieFilter");
        erosionButton = new JButton("Erosion");
        dilatationButton = new JButton("Dilatation");
        ouvertureButton = new JButton("Ouverture");
        fermetureButton = new JButton("Fermeture");
        Morphologie.add(MorphologieButton);
        Morphologie.add(erosionButton);
        Morphologie.add(dilatationButton);
        Morphologie.add(ouvertureButton);
        Morphologie.add(fermetureButton);

        // <----------------------------------->

        susanButton = new JButton("Susan");
        harrisButton = new JButton("Harris");
        traitementsFrequentiels.add(susanButton);
        traitementsFrequentiels.add(harrisButton);
        // <----------------------------------->
        mainPanel.add(controlsPanel);
        add(mainPanel, BorderLayout.CENTER);
        add(optionsPanel, BorderLayout.EAST);
        add(Morphologie, BorderLayout.WEST);
        // add(LesPoints, BorderLayout.SOUTH);

        // handel aventes
        openButton.addActionListener(this);
        quiteButton.addActionListener(this);
        inversionButton.addActionListener(this);
        binarisationButton.addActionListener(this);
        contrasteButton.addActionListener(this);
        niveauDeGrisButton.addActionListener(this);
        histogrammeButton.addActionListener(this);
        mean3x3Button.addActionListener(this);
        mean5x5Button.addActionListener(this);
        gaussian3x3Button.addActionListener(this);
        gaussian5x5Button.addActionListener(this);
        pyramidalButton.addActionListener(this);
        ceniqueButton.addActionListener(this);
        medianButton.addActionListener(this);
        laplacianButton.addActionListener(this);
        sobelButton.addActionListener(this);
        prewittButton.addActionListener(this);
        gradientButton.addActionListener(this);
        robertButton.addActionListener(this);
        saveButton.addActionListener(this);
        divisionButton.addActionListener(this);
        FPB.addActionListener(this);
        FPBB.addActionListener(this);
        FPH.addActionListener(this);
        FPHB.addActionListener(this);
        erosionButton.addActionListener(this);
        dilatationButton.addActionListener(this);
        ouvertureButton.addActionListener(this);
        fermetureButton.addActionListener(this);
        MorphologieButton.addActionListener(this);
        susanButton.addActionListener(this);
        harrisButton.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == inversionButton)
                Inversion();
            if (e.getSource() == binarisationButton)
                Binarisation();
            if (e.getSource() == contrasteButton)
                Contraste();
            if (e.getSource() == niveauDeGrisButton)
                niveauDegris();
            if (e.getSource() == histogrammeButton)
                histogram();
            if (e.getSource() == mean3x3Button)
                mean3_3();
            if (e.getSource() == mean5x5Button)
                mean5_5();
            if (e.getSource() == gaussian3x3Button)
                gaussian3_3();
            if (e.getSource() == gaussian5x5Button)
                gaussian5_5();
            if (e.getSource() == pyramidalButton)
                pyramidal();
            if (e.getSource() == ceniqueButton)
                conique();
            if (e.getSource() == medianButton)
                median();
            if (e.getSource() == laplacianButton)
                laplacian();
            if (e.getSource() == sobelButton)
                sobel();
            if (e.getSource() == prewittButton)
                prewitt();
            if (e.getSource() == gradientButton)
                gradient();
            if (e.getSource() == robertButton)
                robert();
            if (e.getSource() == saveButton)
                save();
            if (e.getSource() == divisionButton)
                division();
            if (e.getSource() == FPB)
                FPB();
            if (e.getSource() == FPBB)
                FPBB();
            if (e.getSource() == FPH)
                FPH();
            if (e.getSource() == FPHB)
                FPHB();
            if (e.getSource() == erosionButton)
                erosion();
            if (e.getSource() == dilatationButton)
                dilatation();
            if (e.getSource() == ouvertureButton)
                ouverture();
            if (e.getSource() == fermetureButton)
                fermeture();
            if (e.getSource() == MorphologieButton)
                Morphologie();
            if (e.getSource() == susanButton)
                susan();
            if (e.getSource() == harrisButton)
                harris();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (button == e.getSource()) {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(button);
            frame.dispose();
        }
        if (e.getSource() == quiteButton)
            System.exit(0);
        if (e.getSource() == openButton)
            openImage();

    }

    public void susan() throws IOException {
        imageProcessing = new ImageProcessing(selectedFile);
        imageProcessing.susanCornerDetection();
        ImageIcon imageIcon = new ImageIcon(imageProcessing.image);
        processedImage.setIcon(imageIcon);
    }

    public void harris() throws IOException {
        imageProcessing = new ImageProcessing(selectedFile);
        imageProcessing.harrisCornerDetection();
        ImageIcon imageIcon = new ImageIcon(imageProcessing.image);
        processedImage.setIcon(imageIcon);
    }

    public void save() throws IOException {
        ImageIO.write(imageProcessing.image, "jpg",
                new File("D:\\Master1\\imageP\\projectDeImage\\JavaImages\\out\\" + imageProcessing.FilaName));
    }

    public void erosion() throws IOException {
        imageProcessing = new ImageProcessing(selectedFile);
        imageProcessing.erosion();
        ImageIcon imageIcon = new ImageIcon(imageProcessing.image);
        processedImage.setIcon(imageIcon);
    }

    public void Morphologie() throws IOException {
        imageProcessing = new ImageProcessing(selectedFile);
        imageProcessing.Morphologie();
        ImageIcon imageIcon = new ImageIcon(imageProcessing.image);
        processedImage.setIcon(imageIcon);
    }

    public void dilatation() throws IOException {
        imageProcessing = new ImageProcessing(selectedFile);
        imageProcessing.dilation();
        ImageIcon imageIcon = new ImageIcon(imageProcessing.image);
        processedImage.setIcon(imageIcon);
    }

    public void ouverture() throws IOException {
        imageProcessing = new ImageProcessing(selectedFile);
        imageProcessing.opening();
        ImageIcon imageIcon = new ImageIcon(imageProcessing.image);
        processedImage.setIcon(imageIcon);
    }

    public void fermeture() throws IOException {
        imageProcessing = new ImageProcessing(selectedFile);
        imageProcessing.closing();
        ImageIcon imageIcon = new ImageIcon(imageProcessing.image);
        processedImage.setIcon(imageIcon);
    }

    public void division() throws IOException {
        imageProcessing = new ImageProcessing(selectedFile);
        imageProcessing.derivative();
        ImageIcon imageIcon = new ImageIcon(imageProcessing.image);
        processedImage.setIcon(imageIcon);
    }

    public void robert() throws IOException {
        imageProcessing = new ImageProcessing(selectedFile);
        imageProcessing.robert();
        ImageIcon imageIcon = new ImageIcon(imageProcessing.image);
        processedImage.setIcon(imageIcon);
    }

    public void gradient() throws IOException {
        imageProcessing = new ImageProcessing(selectedFile);
        imageProcessing.gradient();
        ImageIcon imageIcon = new ImageIcon(imageProcessing.image);
        processedImage.setIcon(imageIcon);
    }

    public void prewitt() throws IOException {
        imageProcessing = new ImageProcessing(selectedFile);
        imageProcessing.prewitt();
        ImageIcon imageIcon = new ImageIcon(imageProcessing.image);
        processedImage.setIcon(imageIcon);
    }

    public void sobel() throws IOException {
        imageProcessing = new ImageProcessing(selectedFile);
        imageProcessing.sobel();
        ImageIcon imageIcon = new ImageIcon(imageProcessing.image);
        processedImage.setIcon(imageIcon);
    }

    public void laplacian() throws IOException {
        imageProcessing = new ImageProcessing(selectedFile);
        imageProcessing.laplacien();
        ImageIcon imageIcon = new ImageIcon(imageProcessing.image);
        processedImage.setIcon(imageIcon);
    }

    public void openImage() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            ImageIcon imageIcon = new ImageIcon(selectedFile.getAbsolutePath());
            imageLabel.setIcon(imageIcon);
        }
    }

    public void Inversion() throws IOException {

        imageProcessing = new ImageProcessing(selectedFile);
        imageProcessing.colorNegative();
        ImageIcon imageIcon = new ImageIcon(imageProcessing.image);
        processedImage.setIcon(imageIcon);
    }

    public void Binarisation() throws IOException {
        imageProcessing = new ImageProcessing(selectedFile);
        imageProcessing.Thresholding();
        ImageIcon imageIcon = new ImageIcon(imageProcessing.image);
        processedImage.setIcon(imageIcon);
    }

    public void niveauDegris() throws IOException {
        imageProcessing = new ImageProcessing(selectedFile);
        imageProcessing.grayscaleConversion();
        ImageIcon imageIcon = new ImageIcon(imageProcessing.image);
        processedImage.setIcon(imageIcon);
    }

    public void Contraste() throws IOException {
        imageProcessing = new ImageProcessing(selectedFile);
        imageProcessing.contrastAdjustment();
        ImageIcon imageIcon = new ImageIcon(imageProcessing.image);
        processedImage.setIcon(imageIcon);
    }

    public void mean3_3() throws IOException {
        imageProcessing = new ImageProcessing(selectedFile);
        imageProcessing.mean3X3();
        ImageIcon imageIcon = new ImageIcon(imageProcessing.image);
        processedImage.setIcon(imageIcon);
    }

    public void mean5_5() throws IOException {
        imageProcessing = new ImageProcessing(selectedFile);
        imageProcessing.mean5X5();
        ImageIcon imageIcon = new ImageIcon(imageProcessing.image);
        processedImage.setIcon(imageIcon);
    }

    public void pyramidal() throws IOException {
        imageProcessing = new ImageProcessing(selectedFile);
        imageProcessing.pyramidal();
        ImageIcon imageIcon = new ImageIcon(imageProcessing.image);
        processedImage.setIcon(imageIcon);
    }

    public void conique() throws IOException {
        imageProcessing = new ImageProcessing(selectedFile);
        imageProcessing.conique();
        ImageIcon imageIcon = new ImageIcon(imageProcessing.image);
        processedImage.setIcon(imageIcon);
    }

    public void median() throws IOException {
        imageProcessing = new ImageProcessing(selectedFile);
        imageProcessing.mediane();
        ImageIcon imageIcon = new ImageIcon(imageProcessing.image);
        processedImage.setIcon(imageIcon);
    }

    public void FPB() throws IOException {
        imageProcessing = new ImageProcessing(selectedFile);
        imageProcessing.FPB();
        ImageIcon imageIcon = new ImageIcon(imageProcessing.image);
        processedImage.setIcon(imageIcon);
    }

    public void FPBB() throws IOException {
        imageProcessing = new ImageProcessing(selectedFile);
        imageProcessing.FPBB();
        ImageIcon imageIcon = new ImageIcon(imageProcessing.image);
        processedImage.setIcon(imageIcon);
    }

    public void FPH() throws IOException {
        imageProcessing = new ImageProcessing(selectedFile);
        imageProcessing.FPH();
        ImageIcon imageIcon = new ImageIcon(imageProcessing.image);
        processedImage.setIcon(imageIcon);
    }

    public void FPHB() throws IOException {
        imageProcessing = new ImageProcessing(selectedFile);
        imageProcessing.FPHB();
        ImageIcon imageIcon = new ImageIcon(imageProcessing.image);
        processedImage.setIcon(imageIcon);
    }

    public void gaussian3_3() throws IOException {
        imageProcessing = new ImageProcessing(selectedFile);
        imageProcessing.Gaussien3_3();
        ImageIcon imageIcon = new ImageIcon(imageProcessing.image);
        processedImage.setIcon(imageIcon);
    }

    public void gaussian5_5() throws IOException {
        imageProcessing = new ImageProcessing(selectedFile);
        imageProcessing.Gaussian5_5();
        ImageIcon imageIcon = new ImageIcon(imageProcessing.image);
        processedImage.setIcon(imageIcon);
    }

    JButton button;

    public void histogram() throws IOException {
        imageProcessing = new ImageProcessing(selectedFile);
        imageProcessing.histogram();
        JFrame frame = new JFrame("Histogram in JLabel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(850, 500);
        JLabel label = new JLabel(imageProcessing.getHistogramIcon());
        button = new JButton("Close");
        frame.add(label);
        frame.add(button, BorderLayout.SOUTH);
        button.addActionListener(this);
        frame.setVisible(true);
        // processedImage.setIcon(imageProcessing.getHistogramIcon());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ImageProcessingUI().setVisible(true);
        });
    }
}
