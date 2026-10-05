// ESCENARIO 8: RECURSOS HUMANOS - PREDICCION DE AUSENTISMO
// Actividad 5 - Clasificacion Bayesiana Ingenua - Universidad SABES 2026
// Java (Jedona Java Compiler). El archivo DEBE llamarse Escenario8_Actividad5_IA.java
// Columnas: distancia_km, antiguedad_meses, hijos, nivel_estres, clase
// Clases: 0 = NORMAL, 1 = ALTO

import java.util.*;

public class Escenario8_Actividad5_IA {

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
        // Dataset (40 registros): distancia_km, antiguedad_meses, hijos, nivel_estres, clase
        double[][] datos = {
            {7.0, 78.0, 2.0, 7.0, 0}, {44.0, 20.0, 4.0, 8.0, 1}, {37.0, 8.0, 2.0, 4.0, 1},
            {37.0, 13.0, 5.0, 8.0, 1}, {5.0, 74.0, 1.0, 1.0, 0}, {50.0, 23.0, 3.0, 9.0, 1},
            {21.0, 27.0, 2.0, 4.0, 1}, {7.0, 69.0, 1.0, 4.0, 0}, {11.0, 35.0, 2.0, 2.0, 0},
            {25.0, 26.0, 2.0, 1.0, 1}, {13.0, 74.0, 4.0, 7.0, 0}, {7.0, 83.0, 1.0, 5.0, 0},
            {24.0, 82.0, 2.0, 4.0, 0}, {14.0, 23.0, 3.0, 2.0, 0}, {52.0, 29.0, 2.0, 3.0, 1},
            {44.0, 23.0, 2.0, 5.0, 1}, {61.0, 21.0, 4.0, 7.0, 1}, {22.0, 69.0, 1.0, 3.0, 0},
            {49.0, 14.0, 1.0, 9.0, 1}, {12.0, 54.0, 1.0, 3.0, 0}, {21.0, 46.0, 1.0, 5.0, 0},
            {15.0, 64.0, 2.0, 5.0, 0}, {5.0, 79.0, 1.0, 6.0, 0}, {16.0, 70.0, 2.0, 3.0, 0},
            {2.0, 63.0, 2.0, 9.0, 0}, {46.0, 18.0, 3.0, 10.0, 1}, {14.0, 91.0, 1.0, 5.0, 0},
            {27.0, 63.0, 2.0, 5.0, 0}, {12.0, 59.0, 0.0, 3.0, 0}, {8.0, 98.0, 0.0, 9.0, 0},
            {4.0, 86.0, 1.0, 3.0, 0}, {35.0, 22.0, 2.0, 9.0, 1}, {58.0, 9.0, 4.0, 8.0, 1},
            {26.0, 42.0, 2.0, 9.0, 1}, {54.0, 17.0, 2.0, 9.0, 1}, {16.0, 45.0, 0.0, 3.0, 0},
            {6.0, 36.0, 2.0, 2.0, 0}, {26.0, 54.0, 1.0, 6.0, 0}, {4.0, 38.0, 4.0, 5.0, 1},
            {1.0, 53.0, 0.0, 4.0, 0}
        };
        String[] nombres = {"NORMAL", "ALTO"};
        int k = nombres.length;

        String linea = "======================================================================";
        System.out.println(linea);
        System.out.println("ESCENARIO 8: RECURSOS HUMANOS - PREDICCION DE AUSENTISMO");
        System.out.println(linea);
        System.out.println("\nDescripcion del problema:");
        System.out.println("El departamento de RRHH necesita identificar empleados con alto riesgo");
        System.out.println("de ausentismo para implementar estrategias de retencion y bienestar.");
        System.out.println("\nVariables analizadas:");
        System.out.println("- Distancia del hogar al trabajo (km)");
        System.out.println("- Antiguedad en la empresa (meses)");
        System.out.println("- Numero de hijos");
        System.out.println("- Nivel de estres percibido (1-10)");
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
        System.out.println("                      Pred: NORMAL        Pred: ALTO          ");
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

        // Analisis de costos de los errores (clase 1 = ALTO es el evento que queremos detectar)
        int costoFN = 9000;  // MXN - horas extra y produccion perdida
        int costoFP = 1000;  // MXN - programa de bienestar innecesario
        int fn = matriz[1][0];
        int fp = matriz[0][1];
        int costoTotal = fn * costoFN + fp * costoFP;
        System.out.println("\nAnalisis de Costos:");
        System.out.println("Falsos Negativos (empleados con ausentismo alto no detectados): " + fn + " x $" + String.format("%,d", costoFN) + " = $" + String.format("%,d", fn * costoFN));
        System.out.println("Falsos Positivos (empleados marcados en riesgo sin serlo): " + fp + " x $" + String.format("%,d", costoFP) + " = $" + String.format("%,d", fp * costoFP));
        System.out.println("Costo Total de los errores: $" + String.format("%,d", costoTotal));

        // ===================================================================
        // PREDICCIONES CON NUEVOS DATOS
        // >>> MODIFICA AQUI: cambia los numeros para crear tus 3 casos <<<
        // Orden: distancia_km, antiguedad_meses, hijos, nivel_estres
        // ===================================================================
        double[][] nuevosCasos = {
            {12.0, 64.0, 1.0, 4.0},
            {40.0, 22.0, 3.0, 7.0},
            {26.0, 43.0, 2.0, 6.0}
        };
        String[] descripciones = {
            "Empleado A - perfil estable",
            "Empleado B - alto riesgo",
            "Empleado C - riesgo moderado"
        };

        System.out.println("\n" + linea);
        System.out.println("EVALUACION DE NUEVOS CASOS");
        System.out.println(linea);
        for (int i = 0; i < nuevosCasos.length; i++) {
            double[] caso = nuevosCasos[i];
            int resultado = nb.predecir(caso);
            Map<Integer, Double> prob = nb.predecirProba(caso);
            System.out.println("\n" + descripciones[i] + ":");
            System.out.println("  Distancia del hogar al trabajo (km): " + String.format("%.0f", caso[0]));
            System.out.println("  Antiguedad en la empresa (meses): " + String.format("%.0f", caso[1]));
            System.out.println("  Numero de hijos: " + String.format("%.0f", caso[2]));
            System.out.println("  Nivel de estres percibido (1-10): " + String.format("%.0f", caso[3]));
            System.out.println("  Prediccion: " + nombres[resultado]);
            double suma = 0;
            for (int n = 0; n < k; n++) {
                System.out.println("  Probabilidad " + nombres[n] + ": " + String.format("%.1f", prob.get(n) * 100) + "%");
                suma += prob.get(n);
            }
            if (suma == 0) System.out.println("  (Valores muy lejanos a los datos de entrenamiento: revisa tus numeros)");
            if (resultado == 1) {
                System.out.println("  ACCIONES RECOMENDADAS:");
                System.out.println("     - Entrevista con RRHH");
                System.out.println("     - Evaluar horario flexible o transporte");
                System.out.println("     - Programa de manejo de estres");
            }
        }

        System.out.println("\n" + linea);
        System.out.println("PREGUNTAS DE ANALISIS:");
        System.out.println(linea);
        System.out.println("1. Cual factor tiene mayor impacto en el ausentismo?");
        System.out.println("2. Que retorno tendrian las intervenciones vs. el costo del ausentismo?");
        System.out.println("3. Que implicaciones eticas tiene usar datos personales como el numero de hijos?");
        System.out.println("4. Que politicas de balance vida-trabajo recomendarias?");
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
