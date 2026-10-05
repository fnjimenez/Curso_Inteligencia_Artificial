// ESCENARIO 1: CONTROL DE CALIDAD EN MANUFACTURA
// Actividad 5 - Clasificacion Bayesiana Ingenua - Universidad SABES 2026
// Java (Jedona Java Compiler). El archivo DEBE llamarse Escenario1_Actividad5_IA.java
// Columnas: diametro_mm, longitud_mm, peso_g, temperatura_C, clase
// Clases: 0 = APTA, 1 = DEFECTUOSA

import java.util.*;

public class Escenario1_Actividad5_IA {

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
        // Dataset (40 registros): diametro_mm, longitud_mm, peso_g, temperatura_C, clase
        double[][] datos = {
            {10.9, 48.7, 45.9, 199.0, 1}, {10.2, 52.0, 47.7, 202.0, 1}, {10.0, 50.0, 45.3, 179.0, 0},
            {10.1, 49.8, 45.3, 181.0, 0}, {10.0, 50.1, 45.2, 183.0, 0}, {9.9, 50.0, 44.8, 178.0, 0},
            {10.2, 50.1, 45.3, 184.0, 0}, {10.2, 50.2, 44.7, 184.0, 0}, {10.0, 50.4, 43.1, 173.0, 1},
            {10.3, 50.1, 45.1, 181.0, 0}, {10.1, 49.8, 45.4, 180.0, 0}, {9.8, 50.1, 45.2, 182.0, 0},
            {10.1, 49.8, 45.4, 179.0, 0}, {9.9, 50.1, 45.7, 182.0, 1}, {10.2, 49.8, 46.0, 172.0, 1},
            {10.4, 50.2, 45.0, 181.0, 0}, {9.3, 50.2, 45.0, 183.0, 1}, {9.7, 50.1, 45.4, 183.0, 0},
            {9.9, 50.4, 45.3, 184.0, 0}, {9.8, 50.0, 44.8, 180.0, 0}, {10.5, 50.1, 45.2, 180.0, 0},
            {9.5, 50.0, 44.4, 185.0, 1}, {9.6, 50.5, 49.2, 186.0, 1}, {10.2, 50.6, 44.8, 180.0, 0},
            {10.5, 50.0, 44.8, 181.0, 0}, {10.1, 49.9, 45.3, 181.0, 0}, {9.8, 50.3, 45.3, 183.0, 0},
            {9.7, 50.2, 45.2, 181.0, 0}, {9.3, 50.3, 45.4, 179.0, 1}, {10.7, 50.8, 45.8, 184.0, 1},
            {10.3, 50.0, 45.4, 182.0, 0}, {9.8, 50.1, 45.2, 180.0, 0}, {9.9, 50.4, 45.3, 182.0, 0},
            {9.7, 50.2, 45.4, 182.0, 0}, {11.6, 52.7, 44.3, 187.0, 1}, {10.0, 50.5, 45.1, 181.0, 0},
            {10.2, 48.3, 45.4, 194.0, 1}, {10.4, 50.0, 45.6, 181.0, 0}, {9.3, 49.0, 41.1, 181.0, 1},
            {11.2, 48.7, 46.4, 183.0, 1}
        };
        String[] nombres = {"APTA", "DEFECTUOSA"};
        int k = nombres.length;

        String linea = "======================================================================";
        System.out.println(linea);
        System.out.println("ESCENARIO 1: CONTROL DE CALIDAD EN MANUFACTURA");
        System.out.println(linea);
        System.out.println("\nDescripcion del problema:");
        System.out.println("Una fabrica produce piezas metalicas y necesita clasificarlas como");
        System.out.println("APTAS o DEFECTUOSAS segun sus dimensiones y temperatura de fundicion.");
        System.out.println("\nVariables analizadas:");
        System.out.println("- Diametro (mm)");
        System.out.println("- Longitud (mm)");
        System.out.println("- Peso (g)");
        System.out.println("- Temperatura de fundicion (C)");
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
        System.out.println("                      Pred: APTA          Pred: DEFECTUOSA    ");
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

        // Analisis de costos de los errores (clase 1 = DEFECTUOSA es el evento que queremos detectar)
        int costoFN = 2500;  // MXN - reclamo y devolucion del cliente
        int costoFP = 300;  // MXN - reproceso o desperdicio de una pieza buena
        int fn = matriz[1][0];
        int fp = matriz[0][1];
        int costoTotal = fn * costoFN + fp * costoFP;
        System.out.println("\nAnalisis de Costos:");
        System.out.println("Falsos Negativos (piezas defectuosas que pasan al cliente): " + fn + " x $" + String.format("%,d", costoFN) + " = $" + String.format("%,d", fn * costoFN));
        System.out.println("Falsos Positivos (piezas buenas rechazadas): " + fp + " x $" + String.format("%,d", costoFP) + " = $" + String.format("%,d", fp * costoFP));
        System.out.println("Costo Total de los errores: $" + String.format("%,d", costoTotal));

        // ===================================================================
        // PREDICCIONES CON NUEVOS DATOS
        // >>> MODIFICA AQUI: cambia los numeros para crear tus 3 casos <<<
        // Orden: diametro_mm, longitud_mm, peso_g, temperatura_C
        // ===================================================================
        double[][] nuevosCasos = {
            {10.2, 50.1, 45.2, 181.0},
            {11.6, 51.4, 47.3, 193.0},
            {10.6, 50.6, 45.9, 186.0}
        };
        String[] descripciones = {
            "Pieza #A101 - lote normal",
            "Pieza #A102 - lote sospechoso",
            "Pieza #A103 - caso intermedio"
        };

        System.out.println("\n" + linea);
        System.out.println("EVALUACION DE NUEVOS CASOS");
        System.out.println(linea);
        for (int i = 0; i < nuevosCasos.length; i++) {
            double[] caso = nuevosCasos[i];
            int resultado = nb.predecir(caso);
            Map<Integer, Double> prob = nb.predecirProba(caso);
            System.out.println("\n" + descripciones[i] + ":");
            System.out.println("  Diametro (mm): " + String.format("%.1f", caso[0]));
            System.out.println("  Longitud (mm): " + String.format("%.1f", caso[1]));
            System.out.println("  Peso (g): " + String.format("%.1f", caso[2]));
            System.out.println("  Temperatura de fundicion (C): " + String.format("%.0f", caso[3]));
            System.out.println("  Prediccion: " + nombres[resultado]);
            double suma = 0;
            for (int n = 0; n < k; n++) {
                System.out.println("  Probabilidad " + nombres[n] + ": " + String.format("%.1f", prob.get(n) * 100) + "%");
                suma += prob.get(n);
            }
            if (suma == 0) System.out.println("  (Valores muy lejanos a los datos de entrenamiento: revisa tus numeros)");
            if (resultado == 1) {
                System.out.println("  ACCIONES RECOMENDADAS:");
                System.out.println("     - Separar la pieza para inspeccion manual");
                System.out.println("     - Revisar temperatura del horno");
                System.out.println("     - Verificar calibracion del molde");
            }
        }

        System.out.println("\n" + linea);
        System.out.println("PREGUNTAS DE ANALISIS:");
        System.out.println(linea);
        System.out.println("1. Que tan confiable es el modelo con este accuracy?");
        System.out.println("2. Cual seria el costo de clasificar una pieza DEFECTUOSA como APTA?");
        System.out.println("3. Que caracteristica parece ser mas determinante para la clasificacion?");
        System.out.println("4. Como podrias mejorar el modelo con mas datos?");
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
