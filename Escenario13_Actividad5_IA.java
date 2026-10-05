// ESCENARIO 13: LOGISTICA - QUIEBRE DE INVENTARIO (STOCKOUT)
// Actividad 5 - Clasificacion Bayesiana Ingenua - Universidad SABES 2026
// Java (Jedona Java Compiler). El archivo DEBE llamarse Escenario13_Actividad5_IA.java
// Columnas: demanda_diaria, inventario_actual, lead_time_reposicion, variabilidad_pct, clase
// Clases: 0 = SIN QUIEBRE, 1 = QUIEBRE

import java.util.*;

public class Escenario13_Actividad5_IA {

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
        // Dataset (40 registros): demanda_diaria, inventario_actual, lead_time_reposicion, variabilidad_pct, clase
        double[][] datos = {
            {147.0, 1610.0, 6.0, 20.0, 1}, {194.0, 640.0, 7.0, 8.0, 1}, {147.0, 2530.0, 3.0, 5.0, 0},
            {151.0, 1720.0, 1.0, 6.0, 0}, {180.0, 1510.0, 3.0, 19.0, 0}, {157.0, 910.0, 3.0, 49.0, 1},
            {52.0, 1380.0, 6.0, 21.0, 0}, {174.0, 1270.0, 5.0, 13.0, 0}, {105.0, 1340.0, 7.0, 15.0, 0},
            {114.0, 480.0, 6.0, 16.0, 0}, {248.0, 680.0, 6.0, 30.0, 1}, {127.0, 1800.0, 7.0, 14.0, 0},
            {120.0, 630.0, 7.0, 16.0, 0}, {242.0, 760.0, 6.0, 34.0, 1}, {90.0, 1570.0, 2.0, 13.0, 0},
            {102.0, 440.0, 5.0, 33.0, 1}, {99.0, 840.0, 6.0, 31.0, 1}, {148.0, 1530.0, 3.0, 28.0, 0},
            {123.0, 1270.0, 9.0, 16.0, 1}, {146.0, 1270.0, 3.0, 18.0, 0}, {70.0, 400.0, 4.0, 17.0, 0},
            {116.0, 1350.0, 4.0, 15.0, 0}, {278.0, 800.0, 11.0, 29.0, 1}, {122.0, 1240.0, 4.0, 12.0, 0},
            {285.0, 1070.0, 11.0, 32.0, 1}, {139.0, 1030.0, 3.0, 21.0, 0}, {110.0, 1760.0, 5.0, 17.0, 0},
            {169.0, 1200.0, 8.0, 16.0, 1}, {137.0, 290.0, 5.0, 41.0, 1}, {95.0, 2610.0, 2.0, 13.0, 0},
            {129.0, 1630.0, 3.0, 23.0, 0}, {71.0, 2360.0, 3.0, 14.0, 0}, {206.0, 1040.0, 3.0, 20.0, 0},
            {120.0, 350.0, 10.0, 36.0, 1}, {162.0, 2520.0, 5.0, 25.0, 0}, {143.0, 650.0, 8.0, 18.0, 0},
            {85.0, 1940.0, 4.0, 15.0, 0}, {214.0, 950.0, 9.0, 12.0, 1}, {237.0, 1340.0, 7.0, 39.0, 1},
            {73.0, 980.0, 5.0, 5.0, 0}
        };
        String[] nombres = {"SIN QUIEBRE", "QUIEBRE"};
        int k = nombres.length;

        String linea = "======================================================================";
        System.out.println(linea);
        System.out.println("ESCENARIO 13: LOGISTICA - QUIEBRE DE INVENTARIO (STOCKOUT)");
        System.out.println(linea);
        System.out.println("\nDescripcion del problema:");
        System.out.println("Un centro de distribucion quiere saber que SKUs tienen riesgo de");
        System.out.println("quedarse sin existencias antes de que llegue la reposicion, para");
        System.out.println("priorizar compras urgentes.");
        System.out.println("\nVariables analizadas:");
        System.out.println("- Demanda promedio diaria (unidades)");
        System.out.println("- Inventario actual (unidades)");
        System.out.println("- Lead time de reposicion (dias)");
        System.out.println("- Variabilidad de la demanda (%)");
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
        System.out.println("                      Pred: SIN QUIEBRE   Pred: QUIEBRE       ");
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

        // Analisis de costos de los errores (clase 1 = QUIEBRE es el evento que queremos detectar)
        int costoFN = 6000;  // MXN - venta perdida y cliente insatisfecho
        int costoFP = 900;  // MXN - compra urgente innecesaria / exceso de inventario
        int fn = matriz[1][0];
        int fp = matriz[0][1];
        int costoTotal = fn * costoFN + fp * costoFP;
        System.out.println("\nAnalisis de Costos:");
        System.out.println("Falsos Negativos (quiebres no detectados): " + fn + " x $" + String.format("%,d", costoFN) + " = $" + String.format("%,d", fn * costoFN));
        System.out.println("Falsos Positivos (alertas falsas): " + fp + " x $" + String.format("%,d", costoFP) + " = $" + String.format("%,d", fp * costoFP));
        System.out.println("Costo Total de los errores: $" + String.format("%,d", costoTotal));

        // ===================================================================
        // PREDICCIONES CON NUEVOS DATOS
        // >>> MODIFICA AQUI: cambia los numeros para crear tus 3 casos <<<
        // Orden: demanda_diaria, inventario_actual, lead_time_reposicion, variabilidad_pct
        // ===================================================================
        double[][] nuevosCasos = {
            {110.0, 1800.0, 3.0, 12.0},
            {190.0, 700.0, 9.0, 35.0},
            {150.0, 1100.0, 6.0, 22.0}
        };
        String[] descripciones = {
            "SKU 1001 - rotacion normal",
            "SKU 2050 - alta demanda, poco inventario",
            "SKU 3307 - caso intermedio"
        };

        System.out.println("\n" + linea);
        System.out.println("EVALUACION DE NUEVOS CASOS");
        System.out.println(linea);
        for (int i = 0; i < nuevosCasos.length; i++) {
            double[] caso = nuevosCasos[i];
            int resultado = nb.predecir(caso);
            Map<Integer, Double> prob = nb.predecirProba(caso);
            System.out.println("\n" + descripciones[i] + ":");
            System.out.println("  Demanda promedio diaria (unidades): " + String.format("%.0f", caso[0]));
            System.out.println("  Inventario actual (unidades): " + String.format("%.0f", caso[1]));
            System.out.println("  Lead time de reposicion (dias): " + String.format("%.0f", caso[2]));
            System.out.println("  Variabilidad de la demanda (%): " + String.format("%.0f", caso[3]));
            System.out.println("  Prediccion: " + nombres[resultado]);
            double suma = 0;
            for (int n = 0; n < k; n++) {
                System.out.println("  Probabilidad " + nombres[n] + ": " + String.format("%.1f", prob.get(n) * 100) + "%");
                suma += prob.get(n);
            }
            if (suma == 0) System.out.println("  (Valores muy lejanos a los datos de entrenamiento: revisa tus numeros)");
            if (resultado == 1) {
                System.out.println("  ACCIONES RECOMENDADAS:");
                System.out.println("     - Generar orden de compra urgente");
                System.out.println("     - Transferir stock de otro almacen");
                System.out.println("     - Revisar punto de reorden");
            }
        }

        System.out.println("\n" + linea);
        System.out.println("PREGUNTAS DE ANALISIS:");
        System.out.println(linea);
        System.out.println("1. Cual variable explica mejor el riesgo de quiebre?");
        System.out.println("2. Como se relaciona este modelo con el punto de reorden?");
        System.out.println("3. Es peor un quiebre o una compra innecesaria? Por que?");
        System.out.println("4. Que dato agregarias (temporada, promociones, etc.)?");
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
