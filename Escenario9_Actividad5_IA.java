// ESCENARIO 9: MANTENIMIENTO PREDICTIVO - DETECCION DE FALLAS
// Actividad 5 - Clasificacion Bayesiana Ingenua - Universidad SABES 2026
// Java (Jedona Java Compiler). El archivo DEBE llamarse Escenario9_Actividad5_IA.java
// Columnas: vibracion_mm_s, temperatura_motor_C, horas_operacion, dias_desde_mant, clase
// Clases: 0 = NORMAL, 1 = FALLA

import java.util.*;

public class Escenario9_Actividad5_IA {

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
        // Dataset (40 registros): vibracion_mm_s, temperatura_motor_C, horas_operacion, dias_desde_mant, clase
        double[][] datos = {
            {4.0, 72.0, 1216.0, 61.0, 0}, {6.7, 102.0, 8669.0, 245.0, 1}, {6.8, 65.0, 2621.0, 17.0, 0},
            {3.4, 91.0, 3868.0, 104.0, 1}, {8.9, 80.0, 4709.0, 172.0, 1}, {2.5, 61.0, 721.0, 52.0, 0},
            {1.9, 86.0, 2565.0, 16.0, 0}, {4.3, 93.0, 839.0, 155.0, 0}, {3.6, 64.0, 4442.0, 227.0, 1},
            {4.0, 56.0, 344.0, 55.0, 0}, {3.9, 58.0, 2032.0, 12.0, 0}, {12.3, 104.0, 2700.0, 211.0, 1},
            {1.4, 55.0, 2196.0, 5.0, 0}, {2.5, 67.0, 563.0, 122.0, 0}, {6.4, 69.0, 1037.0, 36.0, 0},
            {1.8, 74.0, 1234.0, 19.0, 0}, {1.6, 57.0, 2801.0, 46.0, 0}, {5.5, 65.0, 760.0, 77.0, 0},
            {6.6, 64.0, 4691.0, 137.0, 0}, {10.0, 88.0, 7978.0, 174.0, 1}, {10.4, 92.0, 6241.0, 203.0, 1},
            {2.1, 55.0, 1560.0, 33.0, 0}, {8.2, 84.0, 5986.0, 210.0, 1}, {12.1, 92.0, 4436.0, 216.0, 1},
            {2.1, 68.0, 2057.0, 30.0, 0}, {10.3, 73.0, 3917.0, 196.0, 1}, {4.2, 76.0, 2009.0, 58.0, 0},
            {5.6, 67.0, 1186.0, 63.0, 0}, {1.3, 75.0, 788.0, 22.0, 0}, {9.0, 109.0, 4835.0, 272.0, 1},
            {4.2, 63.0, 681.0, 77.0, 0}, {5.0, 98.0, 3958.0, 68.0, 1}, {7.7, 91.0, 2290.0, 105.0, 0},
            {3.4, 77.0, 1863.0, 56.0, 0}, {2.8, 55.0, 658.0, 56.0, 0}, {7.0, 108.0, 6194.0, 33.0, 1},
            {14.1, 105.0, 6128.0, 166.0, 1}, {13.9, 112.0, 4024.0, 214.0, 1}, {5.1, 68.0, 1001.0, 64.0, 0},
            {8.8, 103.0, 4911.0, 225.0, 1}
        };
        String[] nombres = {"NORMAL", "FALLA"};
        int k = nombres.length;

        String linea = "======================================================================";
        System.out.println(linea);
        System.out.println("ESCENARIO 9: MANTENIMIENTO PREDICTIVO - DETECCION DE FALLAS");
        System.out.println(linea);
        System.out.println("\nDescripcion del problema:");
        System.out.println("Una planta industrial necesita predecir fallas en maquinaria critica");
        System.out.println("para implementar mantenimiento preventivo y evitar paros no programados.");
        System.out.println("\nVariables analizadas:");
        System.out.println("- Nivel de vibracion (mm/s)");
        System.out.println("- Temperatura del motor (C)");
        System.out.println("- Horas de operacion acumuladas");
        System.out.println("- Dias desde el ultimo mantenimiento");
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
        System.out.println("                      Pred: NORMAL        Pred: FALLA         ");
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

        // Analisis de costos de los errores (clase 1 = FALLA es el evento que queremos detectar)
        int costoFN = 80000;  // MXN - paro no programado de la linea
        int costoFP = 6000;  // MXN - mano de obra y refacciones adelantadas
        int fn = matriz[1][0];
        int fp = matriz[0][1];
        int costoTotal = fn * costoFN + fp * costoFP;
        System.out.println("\nAnalisis de Costos:");
        System.out.println("Falsos Negativos (fallas no detectadas): " + fn + " x $" + String.format("%,d", costoFN) + " = $" + String.format("%,d", fn * costoFN));
        System.out.println("Falsos Positivos (mantenimientos innecesarios): " + fp + " x $" + String.format("%,d", costoFP) + " = $" + String.format("%,d", fp * costoFP));
        System.out.println("Costo Total de los errores: $" + String.format("%,d", costoTotal));

        // ===================================================================
        // PREDICCIONES CON NUEVOS DATOS
        // >>> MODIFICA AQUI: cambia los numeros para crear tus 3 casos <<<
        // Orden: vibracion_mm_s, temperatura_motor_C, horas_operacion, dias_desde_mant
        // ===================================================================
        double[][] nuevosCasos = {
            {3.8, 68.0, 1571.0, 57.0},
            {9.0, 94.0, 5187.0, 184.0},
            {6.4, 81.0, 3379.0, 120.0}
        };
        String[] descripciones = {
            "Maquina CNC-01",
            "Prensa hidraulica-05",
            "Torno industrial-03"
        };

        System.out.println("\n" + linea);
        System.out.println("EVALUACION DE NUEVOS CASOS");
        System.out.println(linea);
        for (int i = 0; i < nuevosCasos.length; i++) {
            double[] caso = nuevosCasos[i];
            int resultado = nb.predecir(caso);
            Map<Integer, Double> prob = nb.predecirProba(caso);
            System.out.println("\n" + descripciones[i] + ":");
            System.out.println("  Nivel de vibracion (mm/s): " + String.format("%.1f", caso[0]));
            System.out.println("  Temperatura del motor (C): " + String.format("%.0f", caso[1]));
            System.out.println("  Horas de operacion acumuladas: " + String.format("%.0f", caso[2]));
            System.out.println("  Dias desde el ultimo mantenimiento: " + String.format("%.0f", caso[3]));
            System.out.println("  Prediccion: " + nombres[resultado]);
            double suma = 0;
            for (int n = 0; n < k; n++) {
                System.out.println("  Probabilidad " + nombres[n] + ": " + String.format("%.1f", prob.get(n) * 100) + "%");
                suma += prob.get(n);
            }
            if (suma == 0) System.out.println("  (Valores muy lejanos a los datos de entrenamiento: revisa tus numeros)");
            if (resultado == 1) {
                System.out.println("  ACCIONES RECOMENDADAS:");
                System.out.println("     - Programar mantenimiento inmediato");
                System.out.println("     - Revisar rodamientos y lubricacion");
                System.out.println("     - Preparar equipo de respaldo");
            }
        }

        System.out.println("\n" + linea);
        System.out.println("PREGUNTAS DE ANALISIS:");
        System.out.println(linea);
        System.out.println("1. Cual parametro es el mejor indicador de falla inminente?");
        System.out.println("2. Conviene mas el mantenimiento preventivo o el predictivo? Por que?");
        System.out.println("3. Como justificar la inversion en sensores con este modelo?");
        System.out.println("4. Que impacto tiene en el OEE (Overall Equipment Effectiveness)?");
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
