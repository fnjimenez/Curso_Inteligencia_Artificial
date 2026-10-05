// ESCENARIO 2: PREDICCION DE ABANDONO DE CLIENTES (CHURN)
// Actividad 5 - Clasificacion Bayesiana Ingenua - Universidad SABES 2026
// Java (Jedona Java Compiler). El archivo DEBE llamarse Escenario2_Actividad5_IA.java
// Columnas: meses_servicio, llamadas_soporte, gasto_mensual_usd, minutos_uso, clase
// Clases: 0 = SE QUEDA, 1 = ABANDONA

import java.util.*;

public class Escenario2_Actividad5_IA {

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
        // Dataset (40 registros): meses_servicio, llamadas_soporte, gasto_mensual_usd, minutos_uso, clase
        double[][] datos = {
            {16.0, 2.0, 95.2, 368.0, 0}, {14.0, 1.0, 74.7, 277.0, 0}, {7.0, 8.0, 22.6, 260.0, 1},
            {26.0, 4.0, 41.7, 392.0, 0}, {7.0, 8.0, 39.6, 240.0, 1}, {8.0, 4.0, 46.1, 249.0, 1},
            {2.0, 8.0, 49.4, 464.0, 1}, {36.0, 5.0, 94.2, 535.0, 0}, {26.0, 1.0, 80.3, 477.0, 0},
            {33.0, 1.0, 61.9, 678.0, 0}, {40.0, 4.0, 63.2, 637.0, 0}, {35.0, 1.0, 63.2, 466.0, 0},
            {13.0, 4.0, 29.0, 298.0, 1}, {37.0, 3.0, 78.4, 477.0, 0}, {24.0, 2.0, 88.2, 546.0, 0},
            {48.0, 3.0, 53.3, 228.0, 1}, {34.0, 0.0, 82.2, 259.0, 0}, {23.0, 1.0, 65.4, 437.0, 0},
            {23.0, 2.0, 69.4, 425.0, 0}, {30.0, 2.0, 79.9, 630.0, 0}, {2.0, 2.0, 24.4, 321.0, 1},
            {40.0, 2.0, 79.8, 535.0, 0}, {13.0, 8.0, 34.9, 306.0, 1}, {15.0, 10.0, 29.0, 249.0, 1},
            {26.0, 3.0, 83.0, 583.0, 0}, {2.0, 9.0, 57.6, 346.0, 1}, {13.0, 6.0, 32.5, 187.0, 1},
            {35.0, 2.0, 67.8, 509.0, 0}, {18.0, 2.0, 61.9, 524.0, 0}, {27.0, 6.0, 54.1, 430.0, 0},
            {5.0, 6.0, 46.3, 248.0, 1}, {16.0, 5.0, 62.4, 215.0, 1}, {15.0, 7.0, 32.7, 253.0, 1},
            {47.0, 4.0, 88.1, 548.0, 0}, {16.0, 2.0, 69.7, 203.0, 1}, {16.0, 2.0, 75.5, 498.0, 0},
            {30.0, 2.0, 50.3, 677.0, 0}, {35.0, 1.0, 88.3, 476.0, 0}, {40.0, 8.0, 54.8, 268.0, 1},
            {17.0, 1.0, 66.9, 490.0, 0}
        };
        String[] nombres = {"SE QUEDA", "ABANDONA"};
        int k = nombres.length;

        String linea = "======================================================================";
        System.out.println(linea);
        System.out.println("ESCENARIO 2: PREDICCION DE ABANDONO DE CLIENTES (CHURN)");
        System.out.println(linea);
        System.out.println("\nDescripcion del problema:");
        System.out.println("Una empresa de telecomunicaciones quiere predecir que clientes tienen");
        System.out.println("mayor riesgo de cancelar su servicio (abandono/churn).");
        System.out.println("\nVariables analizadas:");
        System.out.println("- Meses de servicio con la empresa");
        System.out.println("- Llamadas al soporte tecnico");
        System.out.println("- Gasto mensual promedio (USD)");
        System.out.println("- Minutos de uso mensual");
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
        System.out.println("                      Pred: SE QUEDA      Pred: ABANDONA      ");
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

        // Analisis de costos de los errores (clase 1 = ABANDONA es el evento que queremos detectar)
        int costoFN = 4000;  // MXN - ingreso anual perdido del cliente
        int costoFP = 400;  // MXN - descuento de retencion innecesario
        int fn = matriz[1][0];
        int fp = matriz[0][1];
        int costoTotal = fn * costoFN + fp * costoFP;
        System.out.println("\nAnalisis de Costos:");
        System.out.println("Falsos Negativos (clientes que abandonan sin ser detectados): " + fn + " x $" + String.format("%,d", costoFN) + " = $" + String.format("%,d", fn * costoFN));
        System.out.println("Falsos Positivos (clientes leales marcados en riesgo): " + fp + " x $" + String.format("%,d", costoFP) + " = $" + String.format("%,d", fp * costoFP));
        System.out.println("Costo Total de los errores: $" + String.format("%,d", costoTotal));

        // ===================================================================
        // PREDICCIONES CON NUEVOS DATOS
        // >>> MODIFICA AQUI: cambia los numeros para crear tus 3 casos <<<
        // Orden: meses_servicio, llamadas_soporte, gasto_mensual_usd, minutos_uso
        // ===================================================================
        double[][] nuevosCasos = {
            {29.0, 2.0, 73.1, 495.0},
            {14.0, 6.0, 42.8, 271.0},
            {21.0, 4.0, 57.9, 383.0}
        };
        String[] descripciones = {
            "Cliente A - antiguo y satisfecho",
            "Cliente B - nuevo con muchas quejas",
            "Cliente C - caso intermedio"
        };

        System.out.println("\n" + linea);
        System.out.println("EVALUACION DE NUEVOS CASOS");
        System.out.println(linea);
        for (int i = 0; i < nuevosCasos.length; i++) {
            double[] caso = nuevosCasos[i];
            int resultado = nb.predecir(caso);
            Map<Integer, Double> prob = nb.predecirProba(caso);
            System.out.println("\n" + descripciones[i] + ":");
            System.out.println("  Meses de servicio con la empresa: " + String.format("%.0f", caso[0]));
            System.out.println("  Llamadas al soporte tecnico: " + String.format("%.0f", caso[1]));
            System.out.println("  Gasto mensual promedio (USD): " + String.format("%.1f", caso[2]));
            System.out.println("  Minutos de uso mensual: " + String.format("%.0f", caso[3]));
            System.out.println("  Prediccion: " + nombres[resultado]);
            double suma = 0;
            for (int n = 0; n < k; n++) {
                System.out.println("  Probabilidad " + nombres[n] + ": " + String.format("%.1f", prob.get(n) * 100) + "%");
                suma += prob.get(n);
            }
            if (suma == 0) System.out.println("  (Valores muy lejanos a los datos de entrenamiento: revisa tus numeros)");
            if (resultado == 1) {
                System.out.println("  ACCIONES RECOMENDADAS:");
                System.out.println("     - Llamada de retencion personalizada");
                System.out.println("     - Ofrecer plan con descuento");
                System.out.println("     - Revisar historial de quejas");
            }
        }

        System.out.println("\n" + linea);
        System.out.println("PREGUNTAS DE ANALISIS:");
        System.out.println(linea);
        System.out.println("1. Cual caracteristica es mas indicativa de abandono?");
        System.out.println("2. Que estrategias de retencion recomendarias para clientes en riesgo?");
        System.out.println("3. Cual es el costo de un falso negativo (predecir que se queda pero abandona)?");
        System.out.println("4. Como afecta el numero de llamadas a soporte en la decision del cliente?");
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
