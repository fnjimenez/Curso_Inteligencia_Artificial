// ESCENARIO 6: LOGISTICA INTERNACIONAL - PREDICCION DE RETRASOS
// Actividad 5 - Clasificacion Bayesiana Ingenua - Universidad SABES 2026
// Java (Jedona Java Compiler). El archivo DEBE llamarse Escenario6_Actividad5_IA.java
// Columnas: distancia_km, peso_kg, documentos_aduanales, dias_procesamiento, clase
// Clases: 0 = A TIEMPO, 1 = RETRASO

import java.util.*;

public class Escenario6_Actividad5_IA {

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
        // Dataset (40 registros): distancia_km, peso_kg, documentos_aduanales, dias_procesamiento, clase
        double[][] datos = {
            {7649.0, 2444.0, 10.0, 7.0, 1}, {2932.0, 4531.0, 3.0, 6.0, 1}, {16298.0, 2517.0, 8.0, 2.0, 1},
            {3129.0, 256.0, 3.0, 1.0, 0}, {16273.0, 2103.0, 12.0, 6.0, 1}, {4829.0, 970.0, 3.0, 2.0, 0},
            {5735.0, 515.0, 9.0, 2.0, 0}, {3325.0, 1231.0, 2.0, 2.0, 0}, {3801.0, 156.0, 7.0, 3.0, 0},
            {7599.0, 2619.0, 7.0, 3.0, 1}, {6958.0, 451.0, 7.0, 5.0, 0}, {5325.0, 331.0, 2.0, 3.0, 0},
            {9481.0, 950.0, 3.0, 1.0, 1}, {2908.0, 1026.0, 5.0, 3.0, 0}, {6803.0, 1546.0, 11.0, 8.0, 1},
            {12948.0, 2365.0, 7.0, 6.0, 1}, {9340.0, 3293.0, 20.0, 5.0, 1}, {2954.0, 892.0, 9.0, 2.0, 0},
            {1427.0, 1104.0, 2.0, 1.0, 0}, {18218.0, 2320.0, 16.0, 8.0, 1}, {6074.0, 1886.0, 3.0, 5.0, 0},
            {2228.0, 1020.0, 4.0, 2.0, 0}, {5910.0, 1655.0, 3.0, 3.0, 0}, {5267.0, 1381.0, 7.0, 2.0, 0},
            {1508.0, 366.0, 6.0, 2.0, 0}, {537.0, 805.0, 4.0, 3.0, 0}, {15127.0, 4459.0, 5.0, 7.0, 1},
            {4311.0, 979.0, 8.0, 4.0, 0}, {4927.0, 500.0, 2.0, 3.0, 0}, {4224.0, 2526.0, 2.0, 1.0, 0},
            {4326.0, 146.0, 3.0, 2.0, 0}, {10083.0, 224.0, 5.0, 2.0, 0}, {5149.0, 1783.0, 10.0, 3.0, 1},
            {12353.0, 750.0, 7.0, 10.0, 1}, {3072.0, 477.0, 5.0, 4.0, 0}, {14079.0, 2020.0, 12.0, 6.0, 1},
            {9041.0, 425.0, 3.0, 2.0, 0}, {17726.0, 4127.0, 7.0, 3.0, 1}, {7365.0, 628.0, 2.0, 4.0, 0},
            {13319.0, 4912.0, 15.0, 7.0, 1}
        };
        String[] nombres = {"A TIEMPO", "RETRASO"};
        int k = nombres.length;

        String linea = "======================================================================";
        System.out.println(linea);
        System.out.println("ESCENARIO 6: LOGISTICA INTERNACIONAL - PREDICCION DE RETRASOS");
        System.out.println(linea);
        System.out.println("\nDescripcion del problema:");
        System.out.println("Una empresa de logistica internacional necesita predecir que envios");
        System.out.println("tienen alto riesgo de retrasarse para tomar medidas preventivas.");
        System.out.println("\nVariables analizadas:");
        System.out.println("- Distancia del envio (km)");
        System.out.println("- Peso de la carga (kg)");
        System.out.println("- Documentos aduanales requeridos");
        System.out.println("- Dias de procesamiento en aduana");
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
        System.out.println("                      Pred: A TIEMPO      Pred: RETRASO       ");
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

        // Analisis de costos de los errores (clase 1 = RETRASO es el evento que queremos detectar)
        int costoFN = 5000;  // MXN - penalizacion y compensacion al cliente
        int costoFP = 500;  // MXN - medidas preventivas innecesarias
        int fn = matriz[1][0];
        int fp = matriz[0][1];
        int costoTotal = fn * costoFN + fp * costoFP;
        System.out.println("\nAnalisis de Costos:");
        System.out.println("Falsos Negativos (retrasos no previstos): " + fn + " x $" + String.format("%,d", costoFN) + " = $" + String.format("%,d", fn * costoFN));
        System.out.println("Falsos Positivos (alarmas falsas): " + fp + " x $" + String.format("%,d", costoFP) + " = $" + String.format("%,d", fp * costoFP));
        System.out.println("Costo Total de los errores: $" + String.format("%,d", costoTotal));

        // ===================================================================
        // PREDICCIONES CON NUEVOS DATOS
        // >>> MODIFICA AQUI: cambia los numeros para crear tus 3 casos <<<
        // Orden: distancia_km, peso_kg, documentos_aduanales, dias_procesamiento
        // ===================================================================
        double[][] nuevosCasos = {
            {4553.0, 831.0, 4.0, 3.0},
            {11581.0, 2671.0, 10.0, 6.0},
            {8067.0, 1751.0, 7.0, 4.0}
        };
        String[] descripciones = {
            "Envio Nacional - estandar",
            "Envio Internacional - complejo",
            "Envio Regional - moderado"
        };

        System.out.println("\n" + linea);
        System.out.println("EVALUACION DE NUEVOS CASOS");
        System.out.println(linea);
        for (int i = 0; i < nuevosCasos.length; i++) {
            double[] caso = nuevosCasos[i];
            int resultado = nb.predecir(caso);
            Map<Integer, Double> prob = nb.predecirProba(caso);
            System.out.println("\n" + descripciones[i] + ":");
            System.out.println("  Distancia del envio (km): " + String.format("%.0f", caso[0]));
            System.out.println("  Peso de la carga (kg): " + String.format("%.0f", caso[1]));
            System.out.println("  Documentos aduanales requeridos: " + String.format("%.0f", caso[2]));
            System.out.println("  Dias de procesamiento en aduana: " + String.format("%.0f", caso[3]));
            System.out.println("  Prediccion: " + nombres[resultado]);
            double suma = 0;
            for (int n = 0; n < k; n++) {
                System.out.println("  Probabilidad " + nombres[n] + ": " + String.format("%.1f", prob.get(n) * 100) + "%");
                suma += prob.get(n);
            }
            if (suma == 0) System.out.println("  (Valores muy lejanos a los datos de entrenamiento: revisa tus numeros)");
            if (resultado == 1) {
                System.out.println("  ACCIONES RECOMENDADAS:");
                System.out.println("     - Acelerar tramites aduanales");
                System.out.println("     - Notificar al cliente sobre posible retraso");
                System.out.println("     - Asignar courier premium o ruta alterna");
            }
        }

        System.out.println("\n" + linea);
        System.out.println("PREGUNTAS DE ANALISIS:");
        System.out.println(linea);
        System.out.println("1. Cual factor logistico impacta mas en los retrasos?");
        System.out.println("2. Como optimizarias la relacion costo-beneficio del modelo?");
        System.out.println("3. Que KPIs logisticos adicionales deberian considerarse?");
        System.out.println("4. Como afectan las regulaciones aduanales al modelo predictivo?");
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
