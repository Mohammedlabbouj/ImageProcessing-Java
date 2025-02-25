package com.example;

public class FFT2D {
    public static Complex[][] fft2(Complex[][] x) {
        int M = x.length;
        int N = x[0].length;
        Complex[][] result = new Complex[M][N];

        // Copy input complex numbers
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                result[i][j] = x[i][j];
            }
        }

        // FFT rows
        for (int i = 0; i < M; i++) {
            result[i] = fft1d(result[i]);
        }

        // FFT columns
        Complex[][] temp = new Complex[N][M];
        for (int j = 0; j < N; j++) {
            Complex[] column = new Complex[M];
            for (int i = 0; i < M; i++) {
                column[i] = result[i][j];
            }
            Complex[] transformedColumn = fft1d(column);
            for (int i = 0; i < M; i++) {
                temp[j][i] = transformedColumn[i];
            }
        }

        // Transpose back
        result = new Complex[M][N];
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                result[i][j] = temp[j][i];
            }
        }

        return result;
    }

    private static Complex[] fft1d(Complex[] x) {
        int n = x.length;

        if (n == 1) {
            return new Complex[] { x[0] };
        }

        // Split into even and odd
        Complex[] even = new Complex[n / 2];
        Complex[] odd = new Complex[n / 2];
        for (int k = 0; k < n / 2; k++) {
            even[k] = x[2 * k];
            odd[k] = x[2 * k + 1];
        }

        // Recursive calls
        Complex[] evenFFT = fft1d(even);
        Complex[] oddFFT = fft1d(odd);

        // Combine
        Complex[] result = new Complex[n];
        for (int k = 0; k < n / 2; k++) {
            double kth = -2 * k * Math.PI / n;
            Complex wk = new Complex(Math.cos(kth), Math.sin(kth));
            result[k] = evenFFT[k].plus(wk.times(oddFFT[k]));
            result[k + n / 2] = evenFFT[k].minus(wk.times(oddFFT[k]));
        }
        return result;
    }

    public static Complex[][] ifft2(Complex[][] x) {
        int M = x.length;
        int N = x[0].length;
        Complex[][] result = new Complex[M][N];

        // Conjugate the input
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                result[i][j] = x[i][j].conjugate();
            }
        }

        // Perform forward FFT
        result = fft2(result);

        // Conjugate and scale the result
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                result[i][j] = result[i][j].conjugate().scale(1.0 / (M * N));
            }
        }

        return result;
    }

    public static Complex[][] fftshift(Complex[][] x) {
        int M = x.length;
        int N = x[0].length;
        Complex[][] result = new Complex[M][N];

        int halfM = M / 2;
        int halfN = N / 2;

        // Shift quadrants
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                int newI = (i + halfM) % M;
                int newJ = (j + halfN) % N;
                result[newI][newJ] = x[i][j];
            }
        }

        return result;
    }

    public static Complex[][] ifftshift(Complex[][] x) {
        int M = x.length;
        int N = x[0].length;
        Complex[][] result = new Complex[M][N];

        int halfM = (M + 1) / 2;
        int halfN = (N + 1) / 2;

        // Shift quadrants back
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                int newI = (i + halfM) % M;
                int newJ = (j + halfN) % N;
                result[newI][newJ] = x[i][j];
            }
        }

        return result;
    }
}