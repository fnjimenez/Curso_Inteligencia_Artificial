// ESCENARIO 5: PREDICCION DE RENDIMIENTO ACADEMICO
// Actividad 5 - Clasificacion Bayesiana Ingenua - Universidad SABES 2026
// Java (Jedona Java Compiler). El archivo DEBE llamarse Escenario5_Actividad5_IA.java
// Columnas: hrs_estudio_semanal, asistencia_pct, participacion_pct, tareas_pct, clase
// Clases: 0 = REPROBADO, 1 = APROBADO, 2 = EXCELENTE

import java.util.*;

public class Escenario5_Actividad5_IA {

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
        // Dataset (40 registros): hrs_estudio_semanal, asistencia_pct, participacion_pct, tareas_pct, clase
        double[][] datos = {
            {15.0, 94.0, 98.0, 93.0, 2}, {14.0, 94.0, 68.0, 100.0, 2}, {11.0, 55.0, 28.0, 45.0, 0},
            {17.0, 98.0, 92.0, 92.0, 2}, {20.0, 100.0, 73.0, 83.0, 2}, {20.0, 95.0, 96.0, 91.0, 2},
            {22.0, 96.0, 91.0, 88.0, 2}, {1.0, 60.0, 26.0, 43.0, 0}, {2.0, 88.0, 54.0, 69.0, 0},
            {13.0, 89.0, 94.0, 81.0, 2}, {11.0, 98.0, 61.0, 88.0, 1}, {1.0, 73.0, 39.0, 56.0, 0},
            {6.0, 47.0, 31.0, 65.0, 0}, {8.0, 94.0, 53.0, 93.0, 1}, {6.0, 76.0, 34.0, 52.0, 0},
            {20.0, 95.0, 83.0, 97.0, 2}, {12.0, 81.0, 48.0, 88.0, 1}, {5.0, 62.0, 43.0, 75.0, 0},
            {5.0, 87.0, 56.0, 66.0, 1}, {7.0, 56.0, 43.0, 59.0, 0}, {7.0, 69.0, 57.0, 55.0, 0},
            {4.0, 82.0, 61.0, 65.0, 1}, {7.0, 62.0, 68.0, 69.0, 0}, {10.0, 74.0, 45.0, 91.0, 1},
            {25.0, 100.0, 78.0, 98.0, 2}, {7.0, 79.0, 44.0, 88.0, 1}, {7.0, 69.0, 24.0, 55.0, 0},
            {20.0, 91.0, 86.0, 96.0, 2}, {8.0, 76.0, 41.0, 67.0, 0}, {10.0, 76.0, 43.0, 78.0, 1},
            {7.0, 64.0, 25.0, 47.0, 0}, {19.0, 85.0, 85.0, 98.0, 2}, {16.0, 88.0, 72.0, 90.0, 1},
            {12.0, 89.0, 94.0, 99.0, 2}, {11.0, 87.0, 70.0, 83.0, 1}, {6.0, 52.0, 24.0, 57.0, 0},
            {9.0, 78.0, 79.0, 59.0, 1}, {19.0, 100.0, 85.0, 90.0, 2}, {21.0, 82.0, 54.0, 88.0, 1},
            {9.0, 93.0, 65.0, 67.0, 1}
        };
        String[] nombres = {"REPROBADO", "APROBADO", "EXCELENTE"};
        int k = nombres.length;

        String linea = "======================================================================";
        System.out.println(linea);
        System.out.println("ESCENARIO 5: PREDICCION DE RENDIMIENTO ACADEMICO");
        System.out.println(linea);
        System.out.println("\nDescripcion del problema:");
        System.out.println("Una universidad quiere identificar tempranamente estudiantes en riesgo");
        System.out.println("de reprobar para ofrecerles apoyo academico oportuno.");
        System.out.println("\nVariables analizadas:");
        System.out.println("- Horas de estudio semanal");
        System.out.println("- Asistencia (%)");
        System.out.println("- Participacion en clase (%)");
        System.out.println("- Tareas entregadas a tiempo (%)");
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
        System.out.println("                      Pred: REPROBADO     Pred: APROBADO      Pred: EXCELENTE     ");
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
        // Orden: hrs_estudio_semanal, asistencia_pct, participacion_pct, tareas_pct
        // ===================================================================
        double[][] nuevosCasos = {
            {6.0, 65.0, 38.0, 58.0},
            {10.0, 85.0, 58.0, 80.0},
            {18.0, 94.0, 86.0, 93.0}
        };
        String[] descripciones = {
            "Estudiante A - en riesgo",
            "Estudiante B - promedio",
            "Estudiante C - destacado"
        };

        System.out.println("\n" + linea);
        System.out.println("EVALUACION DE NUEVOS CASOS");
        System.out.println(linea);
        for (int i = 0; i < nuevosCasos.length; i++) {
            double[] caso = nuevosCasos[i];
            int resultado = nb.predecir(caso);
            Map<Integer, Double> prob = nb.predecirProba(caso);
            System.out.println("\n" + descripciones[i] + ":");
            System.out.println("  Horas de estudio semanal: " + String.format("%.0f", caso[0]));
            System.out.println("  Asistencia (%): " + String.format("%.0f", caso[1]));
            System.out.println("  Participacion en clase (%): " + String.format("%.0f", caso[2]));
            System.out.println("  Tareas entregadas a tiempo (%): " + String.format("%.0f", caso[3]));
            System.out.println("  Prediccion: " + nombres[resultado]);
            double suma = 0;
            for (int n = 0; n < k; n++) {
                System.out.println("  Probabilidad " + nombres[n] + ": " + String.format("%.1f", prob.get(n) * 100) + "%");
                suma += prob.get(n);
            }
            if (suma == 0) System.out.println("  (Valores muy lejanos a los datos de entrenamiento: revisa tus numeros)");
            if (resultado == 0) {
                System.out.println("  ACCIONES RECOMENDADAS:");
                System.out.println("     - Canalizar a asesoria academica");
                System.out.println("     - Tutoria con el docente");
                System.out.println("     - Plan de recuperacion de tareas");
            }
            if (resultado == 2) {
                System.out.println("  ACCIONES RECOMENDADAS:");
                System.out.println("     - Invitar a proyecto de investigacion o concurso");
            }
        }

        System.out.println("\n" + linea);
        System.out.println("PREGUNTAS DE ANALISIS:");
        System.out.println(linea);
        System.out.println("1. Que indicador predice mejor el riesgo de reprobar?");
        System.out.println("2. Que error es mas grave: clasificar a un REPROBADO como APROBADO o al reves?");
        System.out.println("3. Que intervenciones tempranas recomendarias?");
        System.out.println("4. Que otros datos agregarias (calificaciones previas, trabajo, transporte)?");
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
