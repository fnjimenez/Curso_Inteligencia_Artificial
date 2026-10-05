// ESCENARIO 4: DETECCION DE FRAUDE EN TRANSACCIONES BANCARIAS
// Actividad 5 - Clasificacion Bayesiana Ingenua - Universidad SABES 2026
// Java (Jedona Java Compiler). El archivo DEBE llamarse Escenario4_Actividad5_IA.java
// Columnas: monto_usd, hora, distancia_km, intentos_fallidos, clase
// Clases: 0 = LEGITIMA, 1 = FRAUDE

import java.util.*;

public class Escenario4_Actividad5_IA {

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
        // Dataset (40 registros): monto_usd, hora, distancia_km, intentos_fallidos, clase
        double[][] datos = {
            {1112.54, 8.0, 127.0, 1.0, 0}, {201.65, 11.0, 517.0, 1.0, 0}, {479.32, 21.0, 408.6, 0.0, 0},
            {4388.2, 3.0, 2199.5, 2.0, 1}, {1302.47, 6.0, 287.6, 2.0, 1}, {114.3, 13.0, 2620.4, 2.0, 1},
            {3545.27, 14.0, 1835.2, 3.0, 1}, {1185.95, 9.0, 1017.1, 2.0, 0}, {1455.14, 6.0, 920.8, 0.0, 0},
            {3168.67, 6.0, 1672.4, 2.0, 1}, {472.84, 3.0, 419.4, 0.0, 1}, {2557.53, 5.0, 1767.1, 2.0, 1},
            {4185.43, 12.0, 1436.5, 1.0, 1}, {5.22, 15.0, 295.4, 1.0, 0}, {121.72, 17.0, 639.8, 0.0, 0},
            {506.43, 12.0, 126.9, 1.0, 0}, {968.55, 20.0, 349.3, 1.0, 0}, {62.05, 12.0, 235.3, 0.0, 0},
            {82.24, 10.0, 315.8, 0.0, 0}, {440.34, 20.0, 348.6, 1.0, 0}, {696.24, 11.0, 341.9, 0.0, 0},
            {894.11, 6.0, 721.6, 0.0, 0}, {393.03, 13.0, 334.3, 0.0, 0}, {3779.73, 5.0, 1011.8, 0.0, 1},
            {533.97, 13.0, 819.4, 0.0, 0}, {1009.15, 20.0, 241.5, 0.0, 0}, {534.29, 19.0, 251.7, 0.0, 0},
            {628.96, 12.0, 661.4, 1.0, 0}, {753.8, 8.0, 1287.7, 2.0, 1}, {1564.23, 5.0, 37.6, 0.0, 1},
            {1440.44, 18.0, 347.3, 1.0, 0}, {2779.16, 4.0, 2873.4, 2.0, 1}, {3634.87, 8.0, 1050.6, 1.0, 1},
            {1076.07, 17.0, 30.6, 1.0, 0}, {1767.73, 6.0, 1981.8, 3.0, 1}, {1077.89, 4.0, 1902.1, 1.0, 1},
            {2201.37, 19.0, 247.9, 0.0, 1}, {1633.37, 3.0, 635.2, 0.0, 0}, {743.7, 7.0, 643.8, 1.0, 0},
            {422.37, 17.0, 247.5, 0.0, 0}
        };
        String[] nombres = {"LEGITIMA", "FRAUDE"};
        int k = nombres.length;

        String linea = "======================================================================";
        System.out.println(linea);
        System.out.println("ESCENARIO 4: DETECCION DE FRAUDE EN TRANSACCIONES BANCARIAS");
        System.out.println(linea);
        System.out.println("\nDescripcion del problema:");
        System.out.println("Un banco necesita identificar transacciones potencialmente fraudulentas");
        System.out.println("en tiempo real para proteger a sus clientes.");
        System.out.println("\nVariables analizadas:");
        System.out.println("- Monto de la transaccion (USD)");
        System.out.println("- Hora del dia (0-23)");
        System.out.println("- Distancia de la ubicacion usual (km)");
        System.out.println("- Intentos previos fallidos");
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
        System.out.println("                      Pred: LEGITIMA      Pred: FRAUDE        ");
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

        // Analisis de costos de los errores (clase 1 = FRAUDE es el evento que queremos detectar)
        int costoFN = 15000;  // MXN - monto robado que el banco reembolsa
        int costoFP = 250;  // MXN - llamada de verificacion y molestia del cliente
        int fn = matriz[1][0];
        int fp = matriz[0][1];
        int costoTotal = fn * costoFN + fp * costoFP;
        System.out.println("\nAnalisis de Costos:");
        System.out.println("Falsos Negativos (fraudes aprobados): " + fn + " x $" + String.format("%,d", costoFN) + " = $" + String.format("%,d", fn * costoFN));
        System.out.println("Falsos Positivos (transacciones legitimas bloqueadas): " + fp + " x $" + String.format("%,d", costoFP) + " = $" + String.format("%,d", fp * costoFP));
        System.out.println("Costo Total de los errores: $" + String.format("%,d", costoTotal));

        // ===================================================================
        // PREDICCIONES CON NUEVOS DATOS
        // >>> MODIFICA AQUI: cambia los numeros para crear tus 3 casos <<<
        // Orden: monto_usd, hora, distancia_km, intentos_fallidos
        // ===================================================================
        double[][] nuevosCasos = {
            {692.79, 13.0, 440.7, 0.0},
            {2330.84, 8.0, 1414.4, 1.0},
            {1511.81, 10.0, 927.6, 1.0}
        };
        String[] descripciones = {
            "Compra en supermercado - tarde",
            "Compra de madrugada en otro pais",
            "Compra mediana - caso intermedio"
        };

        System.out.println("\n" + linea);
        System.out.println("EVALUACION DE NUEVOS CASOS");
        System.out.println(linea);
        for (int i = 0; i < nuevosCasos.length; i++) {
            double[] caso = nuevosCasos[i];
            int resultado = nb.predecir(caso);
            Map<Integer, Double> prob = nb.predecirProba(caso);
            System.out.println("\n" + descripciones[i] + ":");
            System.out.println("  Monto de la transaccion (USD): " + String.format("%.2f", caso[0]));
            System.out.println("  Hora del dia (0-23): " + String.format("%.0f", caso[1]));
            System.out.println("  Distancia de la ubicacion usual (km): " + String.format("%.1f", caso[2]));
            System.out.println("  Intentos previos fallidos: " + String.format("%.0f", caso[3]));
            System.out.println("  Prediccion: " + nombres[resultado]);
            double suma = 0;
            for (int n = 0; n < k; n++) {
                System.out.println("  Probabilidad " + nombres[n] + ": " + String.format("%.1f", prob.get(n) * 100) + "%");
                suma += prob.get(n);
            }
            if (suma == 0) System.out.println("  (Valores muy lejanos a los datos de entrenamiento: revisa tus numeros)");
            if (resultado == 1) {
                System.out.println("  ACCIONES RECOMENDADAS:");
                System.out.println("     - Bloquear la transaccion");
                System.out.println("     - Enviar SMS de verificacion al cliente");
                System.out.println("     - Revisar movimientos recientes");
            }
        }

        System.out.println("\n" + linea);
        System.out.println("PREGUNTAS DE ANALISIS:");
        System.out.println(linea);
        System.out.println("1. Cual es el costo de un falso positivo (bloquear una transaccion legitima)?");
        System.out.println("2. Cual es el costo de un falso negativo (aprobar un fraude)?");
        System.out.println("3. Que patron caracteriza mejor las transacciones fraudulentas?");
        System.out.println("4. Como balancear seguridad vs. experiencia del cliente?");
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
