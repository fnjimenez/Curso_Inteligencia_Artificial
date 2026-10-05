// ESCENARIO 10: CONTROL DE CALIDAD AVANZADO - 3 CATEGORIAS
// Actividad 5 - Clasificacion Bayesiana Ingenua - Universidad SABES 2026
// Java (Jedona Java Compiler). El archivo DEBE llamarse Escenario10_Actividad5_IA.java
// Columnas: espesor_mm, dureza_shore, rugosidad_ra, peso_g, clase
// Clases: 0 = DEFECTUOSO, 1 = ACEPTABLE, 2 = PREMIUM

import java.util.*;

public class Escenario10_Actividad5_IA {

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
        // Dataset (40 registros): espesor_mm, dureza_shore, rugosidad_ra, peso_g, clase
        double[][] datos = {
            {4.79, 80.0, 1.9, 99.8, 0}, {4.97, 84.0, 0.3, 103.7, 1}, {4.85, 76.0, 1.9, 97.4, 0},
            {5.03, 86.0, 0.7, 101.7, 1}, {4.84, 77.0, 1.5, 96.2, 0}, {4.98, 84.0, 0.4, 101.4, 1},
            {4.96, 86.0, 0.4, 103.9, 2}, {5.01, 89.0, 0.5, 103.0, 2}, {4.98, 86.0, 0.6, 102.7, 1},
            {4.97, 86.0, 0.7, 102.3, 1}, {4.81, 76.0, 2.1, 98.4, 0}, {4.77, 76.0, 2.1, 99.3, 0},
            {4.83, 75.0, 1.1, 97.7, 0}, {4.99, 85.0, 0.8, 101.9, 1}, {5.02, 91.0, 0.3, 103.5, 2},
            {5.03, 90.0, 0.5, 104.3, 2}, {4.8, 72.0, 1.7, 97.1, 0}, {5.0, 90.0, 0.7, 104.0, 2},
            {4.79, 76.0, 1.7, 98.4, 0}, {5.03, 90.0, 0.2, 103.8, 2}, {5.03, 89.0, 1.0, 104.2, 2},
            {4.99, 83.0, 0.8, 101.8, 1}, {4.98, 80.0, 0.8, 103.5, 1}, {5.05, 86.0, 0.9, 101.7, 1},
            {4.76, 74.0, 2.0, 98.0, 0}, {5.0, 84.0, 0.5, 103.6, 1}, {5.01, 86.0, 0.6, 103.0, 2},
            {5.03, 88.0, 0.4, 103.2, 2}, {5.01, 89.0, 0.2, 103.9, 2}, {4.82, 75.0, 1.5, 98.6, 0},
            {4.99, 92.0, 0.9, 102.5, 1}, {5.03, 96.0, 0.3, 104.0, 2}, {4.86, 75.0, 1.8, 97.4, 0},
            {4.81, 77.0, 1.1, 97.6, 0}, {5.04, 86.0, 0.7, 102.2, 1}, {5.05, 95.0, 0.7, 103.5, 2},
            {5.02, 88.0, 0.5, 103.3, 2}, {4.79, 74.0, 1.2, 96.6, 0}, {4.75, 73.0, 1.4, 98.9, 0},
            {5.04, 87.0, 1.1, 103.2, 1}
        };
        String[] nombres = {"DEFECTUOSO", "ACEPTABLE", "PREMIUM"};
        int k = nombres.length;

        String linea = "======================================================================";
        System.out.println(linea);
        System.out.println("ESCENARIO 10: CONTROL DE CALIDAD AVANZADO - 3 CATEGORIAS");
        System.out.println(linea);
        System.out.println("\nDescripcion del problema:");
        System.out.println("Una planta manufacturera necesita clasificar productos automaticamente");
        System.out.println("en categorias de calidad para optimizar su comercializacion.");
        System.out.println("\nVariables analizadas:");
        System.out.println("- Espesor de la pieza (mm)");
        System.out.println("- Dureza Shore A");
        System.out.println("- Rugosidad superficial (Ra um)");
        System.out.println("- Peso (g)");
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
        System.out.println("                      Pred: DEFECTUOSO    Pred: ACEPTABLE     Pred: PREMIUM       ");
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
        // Orden: espesor_mm, dureza_shore, rugosidad_ra, peso_g
        // ===================================================================
        double[][] nuevosCasos = {
            {4.8, 75.0, 1.6, 98.0},
            {5.0, 85.0, 0.7, 102.5},
            {5.02, 90.0, 0.5, 103.7}
        };
        String[] descripciones = {
            "Pieza #1247",
            "Pieza #1248",
            "Pieza #1249"
        };

        System.out.println("\n" + linea);
        System.out.println("EVALUACION DE NUEVOS CASOS");
        System.out.println(linea);
        for (int i = 0; i < nuevosCasos.length; i++) {
            double[] caso = nuevosCasos[i];
            int resultado = nb.predecir(caso);
            Map<Integer, Double> prob = nb.predecirProba(caso);
            System.out.println("\n" + descripciones[i] + ":");
            System.out.println("  Espesor de la pieza (mm): " + String.format("%.2f", caso[0]));
            System.out.println("  Dureza Shore A: " + String.format("%.0f", caso[1]));
            System.out.println("  Rugosidad superficial (Ra um): " + String.format("%.1f", caso[2]));
            System.out.println("  Peso (g): " + String.format("%.1f", caso[3]));
            System.out.println("  Prediccion: " + nombres[resultado]);
            double suma = 0;
            for (int n = 0; n < k; n++) {
                System.out.println("  Probabilidad " + nombres[n] + ": " + String.format("%.1f", prob.get(n) * 100) + "%");
                suma += prob.get(n);
            }
            if (suma == 0) System.out.println("  (Valores muy lejanos a los datos de entrenamiento: revisa tus numeros)");
            if (resultado == 0) {
                System.out.println("  ACCIONES RECOMENDADAS:");
                System.out.println("     - Rechazar la pieza");
                System.out.println("     - Revisar parametros del proceso");
            }
            if (resultado == 2) {
                System.out.println("  ACCIONES RECOMENDADAS:");
                System.out.println("     - Enviar a linea de venta premium");
            }
        }

        System.out.println("\n" + linea);
        System.out.println("PREGUNTAS DE ANALISIS:");
        System.out.println(linea);
        System.out.println("1. Cual parametro es mas critico para distinguir PREMIUM de ACEPTABLE?");
        System.out.println("2. Como afecta la clasificacion incorrecta al margen de utilidad?");
        System.out.println("3. Que mejoras de proceso reducirian los productos defectuosos?");
        System.out.println("4. Vale la pena invertir en sensores automaticos vs. inspeccion manual?");
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
