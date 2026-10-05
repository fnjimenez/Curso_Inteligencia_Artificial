// ESCENARIO 3: DIAGNOSTICO MEDICO - DETECCION DE DIABETES
// Actividad 5 - Clasificacion Bayesiana Ingenua - Universidad SABES 2026
// Java (Jedona Java Compiler). El archivo DEBE llamarse Escenario3_Actividad5_IA.java
// Columnas: glucosa_mg_dl, presion_mmhg, imc, edad, clase
// Clases: 0 = SANO, 1 = DIABETES

import java.util.*;

public class Escenario3_Actividad5_IA {

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
        // Dataset (40 registros): glucosa_mg_dl, presion_mmhg, imc, edad, clase
        double[][] datos = {
            {163.0, 67.0, 29.6, 57.0, 1}, {98.0, 60.0, 33.5, 35.0, 0}, {152.0, 62.0, 30.2, 62.0, 1},
            {122.0, 82.0, 38.6, 59.0, 1}, {157.0, 85.0, 36.0, 29.0, 1}, {126.0, 87.0, 26.6, 36.0, 1},
            {203.0, 56.0, 26.1, 43.0, 1}, {82.0, 58.0, 28.8, 30.0, 0}, {112.0, 84.0, 39.8, 57.0, 0},
            {100.0, 68.0, 36.0, 39.0, 0}, {114.0, 75.0, 20.6, 33.0, 0}, {92.0, 77.0, 44.0, 44.0, 0},
            {162.0, 76.0, 30.9, 53.0, 1}, {94.0, 95.0, 25.3, 30.0, 0}, {166.0, 75.0, 23.6, 63.0, 1},
            {85.0, 69.0, 25.1, 35.0, 0}, {99.0, 72.0, 29.8, 28.0, 0}, {134.0, 58.0, 38.4, 49.0, 1},
            {77.0, 99.0, 37.3, 25.0, 0}, {81.0, 68.0, 35.7, 31.0, 0}, {111.0, 44.0, 21.7, 58.0, 0},
            {155.0, 49.0, 36.3, 43.0, 0}, {128.0, 69.0, 28.5, 40.0, 0}, {125.0, 80.0, 28.4, 39.0, 0},
            {97.0, 53.0, 31.7, 24.0, 0}, {163.0, 61.0, 34.3, 34.0, 0}, {163.0, 89.0, 40.0, 43.0, 1},
            {86.0, 79.0, 28.6, 46.0, 0}, {115.0, 75.0, 41.9, 45.0, 1}, {200.0, 94.0, 36.3, 55.0, 1},
            {163.0, 76.0, 36.4, 53.0, 1}, {127.0, 49.0, 29.4, 24.0, 0}, {142.0, 65.0, 34.2, 55.0, 1},
            {77.0, 69.0, 32.0, 35.0, 0}, {97.0, 84.0, 29.2, 50.0, 0}, {108.0, 58.0, 33.1, 49.0, 0},
            {180.0, 78.0, 32.1, 34.0, 1}, {101.0, 80.0, 30.5, 29.0, 0}, {121.0, 70.0, 32.1, 19.0, 0},
            {205.0, 70.0, 35.9, 53.0, 1}
        };
        String[] nombres = {"SANO", "DIABETES"};
        int k = nombres.length;

        String linea = "======================================================================";
        System.out.println(linea);
        System.out.println("ESCENARIO 3: DIAGNOSTICO MEDICO - DETECCION DE DIABETES");
        System.out.println(linea);
        System.out.println("\nDescripcion del problema:");
        System.out.println("Un centro medico necesita un sistema de apoyo para identificar pacientes");
        System.out.println("con alto riesgo de diabetes tipo 2 basado en indicadores clinicos.");
        System.out.println("(Datos de practica, no usar para diagnosticos reales.)");
        System.out.println("\nVariables analizadas:");
        System.out.println("- Glucosa en sangre (mg/dL)");
        System.out.println("- Presion arterial diastolica (mm Hg)");
        System.out.println("- Indice de Masa Corporal (IMC)");
        System.out.println("- Edad (anios)");
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
        System.out.println("                      Pred: SANO          Pred: DIABETES      ");
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

        // Analisis de costos de los errores (clase 1 = DIABETES es el evento que queremos detectar)
        int costoFN = 20000;  // MXN - tratamiento tardio y complicaciones
        int costoFP = 800;  // MXN - estudios de laboratorio adicionales
        int fn = matriz[1][0];
        int fp = matriz[0][1];
        int costoTotal = fn * costoFN + fp * costoFP;
        System.out.println("\nAnalisis de Costos:");
        System.out.println("Falsos Negativos (pacientes con diabetes no detectados): " + fn + " x $" + String.format("%,d", costoFN) + " = $" + String.format("%,d", fn * costoFN));
        System.out.println("Falsos Positivos (pacientes sanos enviados a estudios): " + fp + " x $" + String.format("%,d", costoFP) + " = $" + String.format("%,d", fp * costoFP));
        System.out.println("Costo Total de los errores: $" + String.format("%,d", costoTotal));

        // ===================================================================
        // PREDICCIONES CON NUEVOS DATOS
        // >>> MODIFICA AQUI: cambia los numeros para crear tus 3 casos <<<
        // Orden: glucosa_mg_dl, presion_mmhg, imc, edad
        // ===================================================================
        double[][] nuevosCasos = {
            {105.0, 70.0, 31.3, 37.0},
            {160.0, 75.0, 33.5, 49.0},
            {132.0, 72.0, 32.4, 43.0}
        };
        String[] descripciones = {
            "Paciente A - indicadores normales",
            "Paciente B - indicadores elevados",
            "Paciente C - caso intermedio"
        };

        System.out.println("\n" + linea);
        System.out.println("EVALUACION DE NUEVOS CASOS");
        System.out.println(linea);
        for (int i = 0; i < nuevosCasos.length; i++) {
            double[] caso = nuevosCasos[i];
            int resultado = nb.predecir(caso);
            Map<Integer, Double> prob = nb.predecirProba(caso);
            System.out.println("\n" + descripciones[i] + ":");
            System.out.println("  Glucosa en sangre (mg/dL): " + String.format("%.0f", caso[0]));
            System.out.println("  Presion arterial diastolica (mm Hg): " + String.format("%.0f", caso[1]));
            System.out.println("  Indice de Masa Corporal (IMC): " + String.format("%.1f", caso[2]));
            System.out.println("  Edad (anios): " + String.format("%.0f", caso[3]));
            System.out.println("  Prediccion: " + nombres[resultado]);
            double suma = 0;
            for (int n = 0; n < k; n++) {
                System.out.println("  Probabilidad " + nombres[n] + ": " + String.format("%.1f", prob.get(n) * 100) + "%");
                suma += prob.get(n);
            }
            if (suma == 0) System.out.println("  (Valores muy lejanos a los datos de entrenamiento: revisa tus numeros)");
            if (resultado == 1) {
                System.out.println("  ACCIONES RECOMENDADAS:");
                System.out.println("     - Enviar a prueba de hemoglobina glucosilada");
                System.out.println("     - Canalizar con medico internista");
                System.out.println("     - Orientacion nutricional");
            }
        }

        System.out.println("\n" + linea);
        System.out.println("PREGUNTAS DE ANALISIS:");
        System.out.println(linea);
        System.out.println("1. Es mas grave un falso negativo o un falso positivo en este contexto?");
        System.out.println("2. Por que el recall (sensibilidad) es critico en diagnosticos medicos?");
        System.out.println("3. Que limitaciones tiene usar solo 4 indicadores?");
        System.out.println("4. Como mejorar la confiabilidad del modelo para uso clinico real?");
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
