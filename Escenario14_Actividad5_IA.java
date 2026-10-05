// ESCENARIO 14: LOGISTICA - ENTREGAS FALLIDAS EN ULTIMA MILLA
// Actividad 5 - Clasificacion Bayesiana Ingenua - Universidad SABES 2026
// Java (Jedona Java Compiler). El archivo DEBE llamarse Escenario14_Actividad5_IA.java
// Columnas: distancia_ruta_km, paradas_ruta, ventana_horaria_h, incidencias_previas, clase
// Clases: 0 = ENTREGADA, 1 = FALLIDA

import java.util.*;

public class Escenario14_Actividad5_IA {

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
        // Dataset (40 registros): distancia_ruta_km, paradas_ruta, ventana_horaria_h, incidencias_previas, clase
        double[][] datos = {
            {13.1, 10.0, 2.7, 4.0, 0}, {25.5, 10.0, 4.3, 3.0, 0}, {22.5, 16.0, 4.8, 3.0, 0},
            {20.6, 27.0, 5.3, 5.0, 0}, {43.9, 18.0, 4.5, 4.0, 1}, {23.5, 14.0, 1.8, 5.0, 0},
            {14.3, 24.0, 2.4, 6.0, 1}, {12.3, 21.0, 2.3, 3.0, 0}, {51.8, 34.0, 2.0, 4.0, 1},
            {15.1, 28.0, 1.8, 4.0, 1}, {28.1, 19.0, 2.2, 8.0, 1}, {24.4, 14.0, 3.9, 5.0, 0},
            {18.8, 19.0, 2.8, 4.0, 0}, {8.1, 10.0, 4.7, 2.0, 0}, {22.6, 9.0, 4.2, 4.0, 0},
            {25.5, 25.0, 1.8, 3.0, 1}, {8.5, 21.0, 4.6, 2.0, 0}, {39.5, 30.0, 1.5, 6.0, 1},
            {33.5, 20.0, 2.2, 6.0, 1}, {20.8, 19.0, 1.4, 6.0, 1}, {41.6, 41.0, 1.6, 6.0, 1},
            {29.5, 14.0, 3.4, 6.0, 1}, {26.8, 18.0, 3.1, 0.0, 0}, {9.1, 22.0, 5.3, 5.0, 0},
            {7.1, 14.0, 4.2, 4.0, 0}, {27.0, 17.0, 2.7, 5.0, 0}, {22.7, 23.0, 4.7, 4.0, 0},
            {22.6, 28.0, 2.1, 7.0, 1}, {25.6, 12.0, 5.0, 6.0, 0}, {46.5, 21.0, 3.3, 6.0, 1},
            {35.5, 24.0, 1.0, 8.0, 1}, {13.1, 12.0, 4.4, 3.0, 0}, {21.6, 22.0, 3.9, 3.0, 0},
            {18.6, 9.0, 4.6, 4.0, 0}, {24.7, 18.0, 2.6, 5.0, 0}, {29.6, 17.0, 2.1, 6.0, 1},
            {28.4, 23.0, 1.3, 8.0, 1}, {15.5, 16.0, 3.8, 8.0, 0}, {14.6, 16.0, 2.3, 0.0, 0},
            {9.8, 13.0, 6.3, 4.0, 0}
        };
        String[] nombres = {"ENTREGADA", "FALLIDA"};
        int k = nombres.length;

        String linea = "======================================================================";
        System.out.println(linea);
        System.out.println("ESCENARIO 14: LOGISTICA - ENTREGAS FALLIDAS EN ULTIMA MILLA");
        System.out.println(linea);
        System.out.println("\nDescripcion del problema:");
        System.out.println("Una paqueteria urbana quiere predecir que rutas tendran entregas");
        System.out.println("fallidas (cliente ausente, direccion no encontrada, fuera de");
        System.out.println("horario) para reprogramar o reasignar antes de salir.");
        System.out.println("\nVariables analizadas:");
        System.out.println("- Distancia de la ruta (km)");
        System.out.println("- Numero de paradas en la ruta");
        System.out.println("- Ventana horaria de entrega acordada (horas)");
        System.out.println("- Incidencias previas en la zona");
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
        System.out.println("                      Pred: ENTREGADA     Pred: FALLIDA       ");
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

        // Analisis de costos de los errores (clase 1 = FALLIDA es el evento que queremos detectar)
        int costoFN = 350;  // MXN - segundo intento + queja del cliente
        int costoFP = 80;  // MXN - llamada de confirmacion innecesaria
        int fn = matriz[1][0];
        int fp = matriz[0][1];
        int costoTotal = fn * costoFN + fp * costoFP;
        System.out.println("\nAnalisis de Costos:");
        System.out.println("Falsos Negativos (entregas fallidas no detectadas): " + fn + " x $" + String.format("%,d", costoFN) + " = $" + String.format("%,d", fn * costoFN));
        System.out.println("Falsos Positivos (alertas falsas): " + fp + " x $" + String.format("%,d", costoFP) + " = $" + String.format("%,d", fp * costoFP));
        System.out.println("Costo Total de los errores: $" + String.format("%,d", costoTotal));

        // ===================================================================
        // PREDICCIONES CON NUEVOS DATOS
        // >>> MODIFICA AQUI: cambia los numeros para crear tus 3 casos <<<
        // Orden: distancia_ruta_km, paradas_ruta, ventana_horaria_h, incidencias_previas
        // ===================================================================
        double[][] nuevosCasos = {
            {15.0, 12.0, 4.5, 2.0},
            {34.0, 28.0, 1.5, 7.0},
            {24.0, 19.0, 3.0, 4.0}
        };
        String[] descripciones = {
            "Ruta Centro - manana",
            "Ruta Periferia - tarde",
            "Ruta Mixta - caso intermedio"
        };

        System.out.println("\n" + linea);
        System.out.println("EVALUACION DE NUEVOS CASOS");
        System.out.println(linea);
        for (int i = 0; i < nuevosCasos.length; i++) {
            double[] caso = nuevosCasos[i];
            int resultado = nb.predecir(caso);
            Map<Integer, Double> prob = nb.predecirProba(caso);
            System.out.println("\n" + descripciones[i] + ":");
            System.out.println("  Distancia de la ruta (km): " + String.format("%.1f", caso[0]));
            System.out.println("  Numero de paradas en la ruta: " + String.format("%.0f", caso[1]));
            System.out.println("  Ventana horaria de entrega acordada (horas): " + String.format("%.1f", caso[2]));
            System.out.println("  Incidencias previas en la zona: " + String.format("%.0f", caso[3]));
            System.out.println("  Prediccion: " + nombres[resultado]);
            double suma = 0;
            for (int n = 0; n < k; n++) {
                System.out.println("  Probabilidad " + nombres[n] + ": " + String.format("%.1f", prob.get(n) * 100) + "%");
                suma += prob.get(n);
            }
            if (suma == 0) System.out.println("  (Valores muy lejanos a los datos de entrenamiento: revisa tus numeros)");
            if (resultado == 1) {
                System.out.println("  ACCIONES RECOMENDADAS:");
                System.out.println("     - Llamar al cliente para confirmar");
                System.out.println("     - Reducir paradas de la ruta");
                System.out.println("     - Ofrecer punto de recoleccion");
            }
        }

        System.out.println("\n" + linea);
        System.out.println("PREGUNTAS DE ANALISIS:");
        System.out.println(linea);
        System.out.println("1. Cual variable influye mas en una entrega fallida?");
        System.out.println("2. Por que la ventana horaria podria cambiar el resultado?");
        System.out.println("3. Conviene mas tener falsos positivos o falsos negativos aqui?");
        System.out.println("4. Que dato agregarias (dia de la semana, clima, tipo de cliente)?");
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
