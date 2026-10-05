// ESCENARIO 12: LOGISTICA - CUMPLIMIENTO DE PROVEEDORES (OTIF)
// Actividad 5 - Clasificacion Bayesiana Ingenua - Universidad SABES 2026
// Java (Jedona Java Compiler). El archivo DEBE llamarse Escenario12_Actividad5_IA.java
// Columnas: lead_time_dias, pedido_unidades, historial_otif_pct, pedidos_abiertos, clase
// Clases: 0 = CUMPLE OTIF, 1 = INCUMPLE

import java.util.*;

public class Escenario12_Actividad5_IA {

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
        // Dataset (40 registros): lead_time_dias, pedido_unidades, historial_otif_pct, pedidos_abiertos, clase
        double[][] datos = {
            {6.0, 50.0, 100.0, 6.0, 0}, {7.0, 630.0, 93.9, 2.0, 0}, {4.0, 50.0, 89.0, 4.0, 0},
            {7.0, 640.0, 96.6, 3.0, 0}, {5.0, 610.0, 100.0, 7.0, 0}, {4.0, 760.0, 93.5, 1.0, 0},
            {8.0, 590.0, 94.9, 3.0, 0}, {7.0, 680.0, 96.7, 7.0, 0}, {5.0, 660.0, 94.0, 3.0, 0},
            {3.0, 590.0, 92.2, 2.0, 0}, {10.0, 750.0, 85.8, 10.0, 1}, {5.0, 750.0, 98.5, 7.0, 0},
            {6.0, 700.0, 95.6, 4.0, 0}, {2.0, 660.0, 97.5, 7.0, 0}, {7.0, 320.0, 88.6, 8.0, 1},
            {12.0, 340.0, 91.0, 6.0, 1}, {3.0, 400.0, 88.3, 7.0, 1}, {9.0, 920.0, 80.1, 6.0, 1},
            {2.0, 560.0, 98.5, 7.0, 0}, {6.0, 1320.0, 95.1, 11.0, 1}, {4.0, 100.0, 90.9, 4.0, 0},
            {11.0, 1220.0, 82.1, 11.0, 1}, {3.0, 320.0, 92.3, 4.0, 0}, {11.0, 1110.0, 84.1, 7.0, 1},
            {5.0, 790.0, 81.0, 8.0, 1}, {7.0, 120.0, 91.9, 3.0, 0}, {7.0, 820.0, 86.2, 1.0, 1},
            {6.0, 410.0, 90.9, 2.0, 1}, {4.0, 730.0, 95.8, 4.0, 0}, {6.0, 380.0, 98.7, 5.0, 0},
            {3.0, 190.0, 84.1, 7.0, 1}, {5.0, 750.0, 92.5, 5.0, 0}, {9.0, 50.0, 93.7, 7.0, 1},
            {3.0, 1150.0, 94.1, 3.0, 1}, {4.0, 250.0, 92.4, 3.0, 0}, {10.0, 490.0, 94.8, 10.0, 1},
            {4.0, 90.0, 97.3, 3.0, 0}, {3.0, 480.0, 82.3, 7.0, 1}, {5.0, 150.0, 92.3, 4.0, 0},
            {4.0, 600.0, 94.0, 3.0, 0}
        };
        String[] nombres = {"CUMPLE OTIF", "INCUMPLE"};
        int k = nombres.length;

        String linea = "======================================================================";
        System.out.println(linea);
        System.out.println("ESCENARIO 12: LOGISTICA - CUMPLIMIENTO DE PROVEEDORES (OTIF)");
        System.out.println(linea);
        System.out.println("\nDescripcion del problema:");
        System.out.println("Una empresa de distribucion necesita anticipar que pedidos a");
        System.out.println("proveedores NO llegaran completos y a tiempo (OTIF) para");
        System.out.println("activar planes de contingencia antes de que falte producto.");
        System.out.println("\nVariables analizadas:");
        System.out.println("- Lead time prometido por el proveedor (dias)");
        System.out.println("- Tamano del pedido (unidades)");
        System.out.println("- Historial de cumplimiento OTIF del proveedor (%)");
        System.out.println("- Pedidos abiertos con ese proveedor");
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
        System.out.println("                      Pred: CUMPLE OTIF   Pred: INCUMPLE      ");
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

        // Analisis de costos de los errores (clase 1 = INCUMPLE es el evento que queremos detectar)
        int costoFN = 8000;  // MXN - paro de linea / venta perdida
        int costoFP = 1200;  // MXN - pedido de respaldo innecesario
        int fn = matriz[1][0];
        int fp = matriz[0][1];
        int costoTotal = fn * costoFN + fp * costoFP;
        System.out.println("\nAnalisis de Costos:");
        System.out.println("Falsos Negativos (pedidos incumplidos no detectados): " + fn + " x $" + String.format("%,d", costoFN) + " = $" + String.format("%,d", fn * costoFN));
        System.out.println("Falsos Positivos (alertas falsas): " + fp + " x $" + String.format("%,d", costoFP) + " = $" + String.format("%,d", fp * costoFP));
        System.out.println("Costo Total de los errores: $" + String.format("%,d", costoTotal));

        // ===================================================================
        // PREDICCIONES CON NUEVOS DATOS
        // >>> MODIFICA AQUI: cambia los numeros para crear tus 3 casos <<<
        // Orden: lead_time_dias, pedido_unidades, historial_otif_pct, pedidos_abiertos
        // ===================================================================
        double[][] nuevosCasos = {
            {5.0, 400.0, 96.0, 3.0},
            {9.0, 950.0, 84.5, 10.0},
            {6.0, 600.0, 90.0, 6.0}
        };
        String[] descripciones = {
            "Proveedor A - pedido regular",
            "Proveedor B - pedido grande, proveedor saturado",
            "Proveedor C - caso intermedio"
        };

        System.out.println("\n" + linea);
        System.out.println("EVALUACION DE NUEVOS CASOS");
        System.out.println(linea);
        for (int i = 0; i < nuevosCasos.length; i++) {
            double[] caso = nuevosCasos[i];
            int resultado = nb.predecir(caso);
            Map<Integer, Double> prob = nb.predecirProba(caso);
            System.out.println("\n" + descripciones[i] + ":");
            System.out.println("  Lead time prometido por el proveedor (dias): " + String.format("%.0f", caso[0]));
            System.out.println("  Tamano del pedido (unidades): " + String.format("%.0f", caso[1]));
            System.out.println("  Historial de cumplimiento OTIF del proveedor (%): " + String.format("%.1f", caso[2]));
            System.out.println("  Pedidos abiertos con ese proveedor: " + String.format("%.0f", caso[3]));
            System.out.println("  Prediccion: " + nombres[resultado]);
            double suma = 0;
            for (int n = 0; n < k; n++) {
                System.out.println("  Probabilidad " + nombres[n] + ": " + String.format("%.1f", prob.get(n) * 100) + "%");
                suma += prob.get(n);
            }
            if (suma == 0) System.out.println("  (Valores muy lejanos a los datos de entrenamiento: revisa tus numeros)");
            if (resultado == 1) {
                System.out.println("  ACCIONES RECOMENDADAS:");
                System.out.println("     - Confirmar fecha con el proveedor");
                System.out.println("     - Activar proveedor alterno");
                System.out.println("     - Aumentar inventario de seguridad");
            }
        }

        System.out.println("\n" + linea);
        System.out.println("PREGUNTAS DE ANALISIS:");
        System.out.println(linea);
        System.out.println("1. Cual variable pesa mas para predecir un incumplimiento?");
        System.out.println("2. Por que el historial OTIF no basta por si solo?");
        System.out.println("3. Que pasaria si el costo de un incumplimiento fuera el doble?");
        System.out.println("4. Que otro dato del proveedor agregarias al modelo?");
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
