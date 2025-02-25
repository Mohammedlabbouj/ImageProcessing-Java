package com.example;

public class Complex {
    private final double re; // Real part
    private final double im; // Imaginary part

    public Complex(double real, double imag) {
        re = real;
        im = imag;
    }

    public Complex times(Complex b) {
        if (b == null) {
            throw new IllegalArgumentException("Cannot multiply by null Complex number");
        }
        double real = this.re * b.re - this.im * b.im;
        double imag = this.re * b.im + this.im * b.re;
        return new Complex(real, imag);
    }

    public Complex plus(Complex b) {
        if (b == null) {
            throw new IllegalArgumentException("Cannot add null Complex number");
        }
        return new Complex(this.re + b.re, this.im + b.im);
    }

    public Complex minus(Complex b) {
        if (b == null) {
            throw new IllegalArgumentException("Cannot subtract null Complex number");
        }
        return new Complex(this.re - b.re, this.im - b.im);
    }

    public double abs() {
        return Math.sqrt(re * re + im * im);
    }

    public Complex conjugate() {
        return new Complex(re, -im);
    }

    // Add the scale method
    public Complex scale(double alpha) {
        return new Complex(alpha * re, alpha * im);
    }

    public double getReal() {
        return re;
    }

    public double getImag() {
        return im;
    }
}
