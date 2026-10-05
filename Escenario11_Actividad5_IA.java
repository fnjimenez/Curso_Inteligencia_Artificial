// ESCENARIO 11: MSA - ANALISIS DEL SISTEMA DE MEDICION
// Actividad 5 - Clasificacion Bayesiana Ingenua - Universidad SABES 2026
// Java (Jedona Java Compiler). El archivo DEBE llamarse Escenario11_Actividad5_IA.java
// Columnas: desv_std_mm, rango_mm, experiencia_meses, dias_calibracion, clase
// Clases: 0 = INADECUADO, 1 = MARGINAL, 2 = ACEPTABLE

import java.util.*;

public class Escenario11_Actividad5_IA {

    static class NaiveBayes {
        Map<Integer, List<double[]>> clases = new TreeMap<>();
        Map<Integer, double[][]> estadisticas = new TreeMap<>(); // [variable][0 = media, 1 = desviacion]
        Map<Integer, Double> priors = new TreeMap<>();

        double calcularMedia(double[] numeros) {
            double suma = 0;
            for (double x : numeros) suma += x;
            return suma / numeros.length;
        }

        double calcularDesviacion(double[] numeros) {
            double media = calcularMedia(numeros);
            double varianza = 0;
            for (double x : numeros) varianza += Math.pow(x - media, 2);
            varianza /= numeros.length;
            return varianza > 0 ? Math.sqrt(varianza) : 0.0001;
        }

        double probabilidadGaussiana(double x, double media, double desviacion) {
            double exponente = Math.exp(-Math.pow(x - media, 2) / (2 * Math.pow(desviacion, 2)));
            return (1 / (Math.sqrt(2 * Math.PI) * desviacion)) * exponente;
        }

        void entrenar(double[][] X, int[] y) {
            for (int i = 0; i < X.length; i++) {
                clases.computeIfAbsent(y[i], c -> new ArrayList<>()).add(X[i]);
            }
            for (Map.Entry<Integer, List<double[]>> e : clases.entrySet()) {
                List<double[]> valores = e.getValue();
                priors.put(e.getKey(), (double) valores.size() / X.length);
                int nVar = valores.get(0).length;
                double[][] stats = new double[nVar][2];
                for (int j = 0; j < nVar; j++) {
                    double[] columna = new double[valores.size()];
                    for (int r = 0; r < valores.size(); r++) columna[r] = valores.get(r)[j];
                    stats[j][0] = calcularMedia(columna);
                    stats[j][1] = calcularDesviacion(columna);
                }
                estadisticas.put(e.getKey(), stats);
            }
        }

        Map<Integer, Double> predecirProba(double[] x) {
            Map<Integer, Double> prob = new TreeMap<>();
            double total = 0;
            for (Map.Entry<Integer, double[][]> e : estadisticas.entrySet()) {
                double p = priors.get(e.getKey());
                for (int j = 0; j < x.length; j++) {
                    p *= probabilidadGaussiana(x[j], e.getValue()[j][0], e.getValue()[j][1]);
                }
                prob.put(e.getKey(), p);
                total += p;
            }
            if (total > 0) for (Integer c : prob.keySet()) prob.put(c, prob.get(c) / total);
            return prob;
        }

        int predecir(double[] x) {
            Map<Integer, Double> prob = predecirProba(x);
            int mejor = -1;
            double max = -1;
            for (Map.Entry<Integer, Double> e : prob.entrySet()) {
                if (e.getValue() > max) { max = e.getValue(); mejor = e.getKey(); }
            }
            return mejor;
        }
    }

    public static void main(String[] args) {
        // Dataset (40 registros): desv_std_mm, rango_mm, experiencia_meses, dias_calibracion, clase
        double[][] datos = {
            {0.006, 0.1, 63.0, 2.0, 2}, {0.031, 0.09, 50.0, 23.0, 2}, {0.101, 0.23, 23.0, 126.0, 0},
            {0.057, 0.22, 8.0, 52.0, 0}, {0.075, 0.35, 13.0, 153.0, 0}, {0.071, 0.26, 2.0, 140.0, 0},
            {0.08, 0.29, 15.0, 113.0, 0}, {0.042, 0.24, 33.0, 54.0, 1}, {0.079, 0.26, 4.0, 146.0, 0},
            {0.058, 0.12, 19.0, 110.0, 1}, {0.04, 0.13, 25.0, 82.0, 1}, {0.04, 0.08, 49.0, 50.0, 1},
            {0.023, 0.02, 55.0, 55.0, 2}, {0.061, 0.16, 20.0, 41.0, 1}, {0.061, 0.12, 26.0, 112.0, 1},
            {0.101, 0.28, 6.0, 145.0, 0}, {0.03, 0.06, 61.0, 20.0, 2}, {0.077, 0.32, 9.0, 132.0, 0},
            {0.051, 0.15, 38.0, 35.0, 1}, {0.088, 0.29, 11.0, 99.0, 0}, {0.083, 0.3, 18.0, 133.0, 0},
            {0.01, 0.06, 47.0, 12.0, 2}, {0.084, 0.14, 26.0, 93.0, 1}, {0.011, 0.04, 72.0, 7.0, 2},
            {0.06, 0.12, 33.0, 78.0, 1}, {0.025, 0.06, 42.0, 11.0, 2}, {0.069, 0.24, 7.0, 151.0, 0},
            {0.032, 0.08, 42.0, 13.0, 2}, {0.108, 0.26, 11.0, 155.0, 0}, {0.047, 0.15, 44.0, 63.0, 1},
            {0.014, 0.19, 46.0, 17.0, 2}, {0.073, 0.22, 10.0, 88.0, 0}, {0.02, 0.03, 40.0, 12.0, 2},
            {0.021, 0.03, 44.0, 38.0, 2}, {0.007, 0.02, 54.0, 8.0, 2}, {0.035, 0.05, 23.0, 48.0, 1},
            {0.098, 0.27, 5.0, 87.0, 0}, {0.068, 0.13, 18.0, 83.0, 1}, {0.026, 0.04, 36.0, 41.0, 2},
            {0.064, 0.13, 29.0, 86.0, 1}
        };
        String[] nombres = {"INADECUADO", "MARGINAL", "ACEPTABLE"};
        int k = nombres.length;

        String linea = "======================================================================";
        System.out.println(linea);
        System.out.println("ESCENARIO 11: MSA - ANALISIS DEL SISTEMA DE MEDICION");
        System.out.println(linea);
        System.out.println("\nDescripcion del problema:");
        System.out.println("Un laboratorio de metrologia necesita evaluar la confiabilidad de sus");
        System.out.println("sistemas de medicion segun estudios MSA (Measurement System Analysis).");
        System.out.println("Criterio: %GRR < 10% Aceptable, 10-30% Marginal, > 30% Inadecuado.");
        System.out.println("\nVariables analizadas:");
        System.out.println("- Desviacion estandar de mediciones repetidas (mm)");
        System.out.println("- Rango de mediciones (mm)");
        System.out.println("- Experiencia del operador (meses)");
        System.out.println("- Dias desde la ultima calibracion");
        System.out.println("\nClases:");
        for (int n = 0; n < k; n++) System.out.println("  " + n + " = " + nombres[n]);

        // Division FIJA: primeros 70% para entrenar, ultimos 30% para probar
        int split = (int) (datos.length * 0.7);
        double[][] X_train = new double[split][4];
        int[] y_train = new int[split];
        double[][] X_test = new double[datos.length - split][4];
        int[] y_test = new int[datos.length - split];
        for (int i = 0; i < datos.length; i++) {
            if (i < split) {
                System.arraycopy(datos[i], 0, X_train[i], 0, 4);
                y_train[i] = (int) datos[i][4];
            } else {
                System.arraycopy(datos[i], 0, X_test[i - split], 0, 4);
                y_test[i - split] = (int) datos[i][4];
            }
        }

        NaiveBayes nb = new NaiveBayes();
        nb.entrenar(X_train, y_train);

        int correctos = 0;
        int[][] matriz = new int[k][k];
        for (int i = 0; i < X_test.length; i++) {
            int pred = nb.predecir(X_test[i]);
            if (pred == y_test[i]) correctos++;
            matriz[y_test[i]][pred]++;
        }
        double accuracy = 100.0 * correctos / X_test.length;

        System.out.println("\n" + linea);
        System.out.println("RESULTADOS DEL ANALISIS");
        System.out.println(linea);
        System.out.println("Registros de entrenamiento: " + X_train.length + "   Registros de prueba: " + X_test.length);
        System.out.println("Accuracy: " + String.format("%.2f", accuracy) + "%");
        System.out.println("Casos correctamente clasificados: " + correctos + "/" + X_test.length);

        System.out.println("\nMatriz de Confusion (filas = real, columnas = prediccion):");
        System.out.println("                      Pred: INADECUADO    Pred: MARGINAL      Pred: ACEPTABLE     ");
        for (int r = 0; r < k; r++) {
            StringBuilder fila = new StringBuilder(String.format("Real: %-16s", nombres[r]));
            for (int p = 0; p < k; p++) fila.append(centrar(String.valueOf(matriz[r][p]), 20));
            System.out.println(fila);
        }

        System.out.println("\n" + linea);
        System.out.println("METRICAS POR CLASE");
        System.out.println(linea);
        for (int c = 0; c < k; c++) {
            int vp = matriz[c][c], predC = 0, realC = 0;
            for (int r = 0; r < k; r++) { predC += matriz[r][c]; realC += matriz[c][r]; }
            double precision = predC > 0 ? (double) vp / predC : 0;
            double recall = realC > 0 ? (double) vp / realC : 0;
            double f1 = (precision + recall) > 0 ? 2 * precision * recall / (precision + recall) : 0;
            System.out.println("Clase: " + nombres[c] + " (" + c + ")");
            System.out.println("  Precision: " + String.format("%.2f", precision * 100) + "%");
            System.out.println("  Recall:    " + String.format("%.2f", recall * 100) + "%");
            System.out.println("  F1-Score:  " + String.format("%.2f", f1 * 100) + "%");
        }

        // ===================================================================
        // PREDICCIONES CON NUEVOS DATOS
        // >>> MODIFICA AQUI: cambia los numeros para crear tus 3 casos <<<
        // Orden: desv_std_mm, rango_mm, experiencia_meses, dias_calibracion
        // ===================================================================
        double[][] nuevosCasos = {
            {0.083, 0.27, 10.0, 123.0},
            {0.055, 0.13, 29.0, 72.0},
            {0.02, 0.06, 50.0, 20.0}
        };
        String[] descripciones = {
            "Micrometro digital - Linea A",
            "Calibrador vernier - Linea B",
            "CMM - Laboratorio"
        };

        System.out.println("\n" + linea);
        System.out.println("EVALUACION DE NUEVOS CASOS");
        System.out.println(linea);
        for (int i = 0; i < nuevosCasos.length; i++) {
            double[] caso = nuevosCasos[i];
            int resultado = nb.predecir(caso);
            Map<Integer, Double> prob = nb.predecirProba(caso);
            System.out.println("\n" + descripciones[i] + ":");
            System.out.println("  Desviacion estandar de mediciones repetidas (mm): " + String.format("%.3f", caso[0]));
            System.out.println("  Rango de mediciones (mm): " + String.format("%.2f", caso[1]));
            System.out.println("  Experiencia del operador (meses): " + String.format("%.0f", caso[2]));
            System.out.println("  Dias desde la ultima calibracion: " + String.format("%.0f", caso[3]));
            System.out.println("  Prediccion: " + nombres[resultado]);
            double suma = 0;
            for (int n = 0; n < k; n++) {
                System.out.println("  Probabilidad " + nombres[n] + ": " + String.format("%.1f", prob.get(n) * 100) + "%");
                suma += prob.get(n);
            }
            if (suma == 0) System.out.println("  (Valores muy lejanos a los datos de entrenamiento: revisa tus numeros)");
            if (resultado == 0) {
                System.out.println("  ACCIONES RECOMENDADAS:");
                System.out.println("     - Retirar el instrumento de uso");
                System.out.println("     - Recalibrar y repetir el estudio GR&R");
                System.out.println("     - Capacitar al operador");
            }
            if (resultado == 1) {
                System.out.println("  ACCIONES RECOMENDADAS:");
                System.out.println("     - Usar con precaucion y programar calibracion");
            }
        }

        System.out.println("\n" + linea);
        System.out.println("PREGUNTAS DE ANALISIS:");
        System.out.println(linea);
        System.out.println("1. Como afecta la experiencia del operador a la repetibilidad?");
        System.out.println("2. Que error es mas riesgoso: aceptar un sistema INADECUADO o rechazar uno ACEPTABLE?");
        System.out.println("3. Como se relaciona el %GRR con la capacidad del proceso (Cp/Cpk)?");
        System.out.println("4. Que dice la norma ISO/IEC 17025 sobre incertidumbre de medicion?");
    }

    static String centrar(String s, int ancho) {
        int izq = (ancho - s.length()) / 2;
        int der = ancho - s.length() - izq;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < izq; i++) sb.append(' ');
        sb.append(s);
        for (int i = 0; i < der; i++) sb.append(' ');
        return sb.toString();
    }
}
