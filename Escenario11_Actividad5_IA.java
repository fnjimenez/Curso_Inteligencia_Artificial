// ESCENARIO 11: MSA e Incertidumbre de la Medición
// Análisis del Sistema de Medición usando Naive Bayes
// Archivo: Escenario11_Actividad5_IA.java

import java.util.*;

class NaiveBayes {
    private Map<Integer, List<double[]>> clases;
    private Map<Integer, List<double[]>> estadisticas;
    
    public NaiveBayes() {
        clases = new HashMap<>();
        estadisticas = new HashMap<>();
    }
    
    private double calcularMedia(List<Double> datos) {
        double suma = 0;
        for (double val : datos) {
            suma += val;
        }
        return suma / datos.size();
    }
    
    private double calcularDesviacion(List<Double> datos) {
        double media = calcularMedia(datos);
        double sumaCuadrados = 0;
        for (double val : datos) {
            sumaCuadrados += Math.pow(val - media, 2);
        }
        double varianza = sumaCuadrados / datos.size();
        return varianza > 0 ? Math.sqrt(varianza) : 0.0001;
    }
    
    private double probabilidadGaussiana(double x, double media, double desviacion) {
        double exponente = Math.exp(-Math.pow(x - media, 2) / (2 * Math.pow(desviacion, 2)));
        return (1 / (Math.sqrt(2 * Math.PI) * desviacion)) * exponente;
    }
    
    public void entrenar(double[][] X, int[] y) {
        // Agrupar datos por clase
        for (int i = 0; i < X.length; i++) {
            int clase = y[i];
            if (!clases.containsKey(clase)) {
                clases.put(clase, new ArrayList<>());
            }
            clases.get(clase).add(X[i]);
        }
        
        // Calcular estadisticas por clase
        for (Map.Entry<Integer, List<double[]>> entry : clases.entrySet()) {
            int clase = entry.getKey();
            List<double[]> valores = entry.getValue();
            List<double[]> stats = new ArrayList<>();
            
            int numCaracteristicas = valores.get(0).length;
            for (int j = 0; j < numCaracteristicas; j++) {
                List<Double> columna = new ArrayList<>();
                for (double[] fila : valores) {
                    columna.add(fila[j]);
                }
                double media = calcularMedia(columna);
                double desviacion = calcularDesviacion(columna);
                stats.add(new double[]{media, desviacion});
            }
            estadisticas.put(clase, stats);
        }
    }
    
    public Map<Integer, Double> predecirProba(double[] x) {
        Map<Integer, Double> probabilidades = new HashMap<>();
        
        for (Map.Entry<Integer, List<double[]>> entry : estadisticas.entrySet()) {
            int clase = entry.getKey();
            List<double[]> stats = entry.getValue();
            double prob = 1.0;
            
            for (int i = 0; i < x.length; i++) {
                double media = stats.get(i)[0];
                double desviacion = stats.get(i)[1];
                prob *= probabilidadGaussiana(x[i], media, desviacion);
            }
            probabilidades.put(clase, prob);
        }
        
        // Normalizar probabilidades
        double total = 0;
        for (double prob : probabilidades.values()) {
            total += prob;
        }
        if (total > 0) {
            for (int clase : probabilidades.keySet()) {
                probabilidades.put(clase, probabilidades.get(clase) / total);
            }
        }
        
        return probabilidades;
    }
    
    public int predecir(double[] x) {
        Map<Integer, Double> probabilidades = predecirProba(x);
        int mejorClase = -1;
        double mejorProb = -1;
        
        for (Map.Entry<Integer, Double> entry : probabilidades.entrySet()) {
            if (entry.getValue() > mejorProb) {
                mejorProb = entry.getValue();
                mejorClase = entry.getKey();
            }
        }
        
        return mejorClase;
    }
}

public class Escenario11_Actividad5_IA {
    
    private static double[][] generarDataset(int n, Random rand) {
        double[][] datos = new double[n][6];
        
        for (int i = 0; i < n; i++) {
            // Características del sistema de medición
            // 1. Repetibilidad (%) - Variación del equipo
            datos[i][0] = 5.0 + rand.nextGaussian() * 8.0;
            
            // 2. Reproducibilidad (%) - Variación entre operadores
            datos[i][1] = 4.0 + rand.nextGaussian() * 7.0;
            
            // 3. GRR Total (%) - Gage R&R
            datos[i][2] = Math.sqrt(Math.pow(datos[i][0], 2) + Math.pow(datos[i][1], 2));
            
            // 4. ndc (Número de categorías distintas)
            datos[i][3] = 3.0 + rand.nextGaussian() * 3.0;
            
            // 5. Linearidad (%)
            datos[i][4] = 2.0 + rand.nextGaussian() * 4.0;
            
            // 6. Estabilidad (%)
            datos[i][5] = 3.0 + rand.nextGaussian() * 5.0;
            
            // Asegurar valores positivos
            for (int j = 0; j < 6; j++) {
                if (datos[i][j] < 0) datos[i][j] = Math.abs(datos[i][j]);
            }
        }
        
        return datos;
    }
    
    private static int[] generarEtiquetas(double[][] X) {
        int[] etiquetas = new int[X.length];
        
        for (int i = 0; i < X.length; i++) {
            double repetibilidad = X[i][0];
            double reproducibilidad = X[i][1];
            double grr = X[i][2];
            double ndc = X[i][3];
            double linearidad = X[i][4];
            double estabilidad = X[i][5];
            
            // Criterios de clasificación según estándares MSA
            // Clase 2: ACEPTABLE (Sistema de medición confiable)
            if (grr < 10.0 && ndc >= 5 && linearidad < 5.0 && estabilidad < 10.0) {
                etiquetas[i] = 2;
            }
            // Clase 1: MARGINAL (Sistema aceptable con reservas)
            else if (grr < 30.0 && ndc >= 3 && linearidad < 10.0 && estabilidad < 20.0) {
                etiquetas[i] = 1;
            }
            // Clase 0: INACEPTABLE (Sistema de medición no confiable)
            else {
                etiquetas[i] = 0;
            }
        }
        
        return etiquetas;
    }
    
    private static void dividirDataset(double[][] X, int[] y, 
                                      double[][] X_train, int[] y_train,
                                      double[][] X_test, int[] y_test,
                                      double testSize, Random rand) {
        int n = X.length;
        int nTest = (int)(n * testSize);
        int nTrain = n - nTest;
        
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            indices.add(i);
        }
        Collections.shuffle(indices, rand);
        
        for (int i = 0; i < nTrain; i++) {
            int idx = indices.get(i);
            X_train[i] = X[idx].clone();
            y_train[i] = y[idx];
        }
        
        for (int i = 0; i < nTest; i++) {
            int idx = indices.get(i + nTrain);
            X_test[i] = X[idx].clone();
            y_test[i] = y[idx];
        }
    }
    
    private static double calcularAccuracy(int[] y_true, int[] y_pred) {
        int correctos = 0;
        for (int i = 0; i < y_true.length; i++) {
            if (y_true[i] == y_pred[i]) {
                correctos++;
            }
        }
        return (double)correctos / y_true.length;
    }
    
    private static void imprimirMatrizConfusion(int[] y_true, int[] y_pred, int numClases) {
        int[][] matriz = new int[numClases][numClases];
        
        for (int i = 0; i < y_true.length; i++) {
            matriz[y_true[i]][y_pred[i]]++;
        }
        
        System.out.println("\n=== MATRIZ DE CONFUSION ===");
        System.out.print("              ");
        for (int i = 0; i < numClases; i++) {
            System.out.printf("Pred_%d  ", i);
        }
        System.out.println();
        
        String[] nombres = {"Inaceptable", "Marginal   ", "Aceptable  "};
        for (int i = 0; i < numClases; i++) {
            System.out.printf("Real_%d (%s): ", i, nombres[i]);
            for (int j = 0; j < numClases; j++) {
                System.out.printf("%6d  ", matriz[i][j]);
            }
            System.out.println();
        }
    }
    
    private static Map<Integer, Map<String, Double>> calcularMetricas(int[] y_true, int[] y_pred, int numClases) {
        Map<Integer, Map<String, Double>> metricas = new HashMap<>();
        
        for (int clase = 0; clase < numClases; clase++) {
            int tp = 0, fp = 0, fn = 0, tn = 0;
            
            for (int i = 0; i < y_true.length; i++) {
                if (y_true[i] == clase && y_pred[i] == clase) tp++;
                else if (y_true[i] != clase && y_pred[i] == clase) fp++;
                else if (y_true[i] == clase && y_pred[i] != clase) fn++;
                else tn++;
            }
            
            double precision = (tp + fp > 0) ? (double)tp / (tp + fp) : 0;
            double recall = (tp + fn > 0) ? (double)tp / (tp + fn) : 0;
            double f1 = (precision + recall > 0) ? 2 * precision * recall / (precision + recall) : 0;
            
            Map<String, Double> metricasClase = new HashMap<>();
            metricasClase.put("precision", precision);
            metricasClase.put("recall", recall);
            metricasClase.put("f1", f1);
            
            metricas.put(clase, metricasClase);
        }
        
        return metricas;
    }
    
    private static double calcularCostoMejora(int[] y_true, int[] y_pred) {
        // Costos asociados a sistemas de medición
        final double COSTO_CALIBRACION = 2000.0;        // Costo de calibración profesional
        final double COSTO_CAPACITACION = 1500.0;       // Capacitación de operadores
        final double COSTO_EQUIPO_NUEVO = 15000.0;      // Reemplazo de equipo
        final double COSTO_PRODUCTO_DEFECTUOSO = 500.0; // Producto mal clasificado
        
        double costoTotal = 0;
        int sistemasInaceptables = 0;
        int sistemasMarginales = 0;
        
        for (int i = 0; i < y_true.length; i++) {
            // Contar sistemas por categoría real
            if (y_true[i] == 0) {
                sistemasInaceptables++;
                costoTotal += COSTO_EQUIPO_NUEVO; // Requiere reemplazo
            } else if (y_true[i] == 1) {
                sistemasMarginales++;
                costoTotal += COSTO_CALIBRACION + COSTO_CAPACITACION; // Requiere mejora
            }
            
            // Penalización por clasificación incorrecta
            if (y_true[i] != y_pred[i]) {
                if (y_true[i] == 0 && y_pred[i] > 0) {
                    // Sistema inaceptable clasificado como bueno - MUY GRAVE
                    costoTotal += COSTO_PRODUCTO_DEFECTUOSO * 10;
                } else if (y_true[i] > 0 && y_pred[i] == 0) {
                    // Sistema bueno clasificado como malo - DESPERDICIO
                    costoTotal += COSTO_CALIBRACION;
                }
            }
        }
        
        return costoTotal;
    }
    
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("ESCENARIO 11: MSA E INCERTIDUMBRE DE MEDICION");
        System.out.println("Analisis del Sistema de Medicion (MSA)");
        System.out.println("========================================\n");
        
        Random rand = new Random(42);
        
        // Generar dataset
        System.out.println("Generando dataset de estudios MSA...");
        double[][] X = generarDataset(400, rand);
        int[] y = generarEtiquetas(X);
        
        // Contar clases
        Map<Integer, Integer> conteoClases = new HashMap<>();
        for (int clase : y) {
            conteoClases.put(clase, conteoClases.getOrDefault(clase, 0) + 1);
        }
        
        System.out.println("\nDistribucion de sistemas de medicion:");
        System.out.println("- Inaceptable (0): " + conteoClases.getOrDefault(0, 0));
        System.out.println("- Marginal (1): " + conteoClases.getOrDefault(1, 0));
        System.out.println("- Aceptable (2): " + conteoClases.getOrDefault(2, 0));
        
        // Dividir dataset
        int nTest = (int)(X.length * 0.2);
        int nTrain = X.length - nTest;
        
        double[][] X_train = new double[nTrain][6];
        int[] y_train = new int[nTrain];
        double[][] X_test = new double[nTest][6];
        int[] y_test = new int[nTest];
        
        dividirDataset(X, y, X_train, y_train, X_test, y_test, 0.2, rand);
        
        System.out.println("\nConjunto de entrenamiento: " + X_train.length + " estudios");
        System.out.println("Conjunto de prueba: " + X_test.length + " estudios");
        
        // Entrenar modelo
        System.out.println("\nEntrenando clasificador Naive Bayes...");
        NaiveBayes modelo = new NaiveBayes();
        modelo.entrenar(X_train, y_train);
        
        // Realizar predicciones
        System.out.println("Realizando predicciones...");
        int[] y_pred = new int[X_test.length];
        for (int i = 0; i < X_test.length; i++) {
            y_pred[i] = modelo.predecir(X_test[i]);
        }
        
        // Calcular accuracy
        double accuracy = calcularAccuracy(y_test, y_pred);
        System.out.println("\n=== RESULTADOS ===");
        System.out.printf("Accuracy: %.2f%%\n", accuracy * 100);
        
        // Matriz de confusión
        imprimirMatrizConfusion(y_test, y_pred, 3);
        
        // Métricas por clase
        Map<Integer, Map<String, Double>> metricas = calcularMetricas(y_test, y_pred, 3);
        System.out.println("\n=== METRICAS POR CLASE ===");
        String[] nombresClases = {"Inaceptable", "Marginal", "Aceptable"};
        
        for (int i = 0; i < 3; i++) {
            Map<String, Double> m = metricas.get(i);
            System.out.printf("\nClase %d (%s):\n", i, nombresClases[i]);
            System.out.printf("  Precision: %.2f%%\n", m.get("precision") * 100);
            System.out.printf("  Recall: %.2f%%\n", m.get("recall") * 100);
            System.out.printf("  F1-Score: %.2f%%\n", m.get("f1") * 100);
        }
        
        // Análisis de costos
        double costoMejora = calcularCostoMejora(y_test, y_pred);
        System.out.println("\n=== ANALISIS ECONOMICO ===");
        System.out.printf("Costo estimado de mejora/reemplazo: $%.2f USD\n", costoMejora);
        System.out.printf("Costo promedio por sistema: $%.2f USD\n", costoMejora / y_test.length);
        
        // Ejemplo de predicción
        System.out.println("\n=== EJEMPLO DE PREDICCION ===");
        double[] ejemplo = {8.5, 6.2, 10.6, 4.2, 3.8, 8.5};
        System.out.println("Sistema de medicion evaluado:");
        System.out.printf("  Repetibilidad (EV): %.2f%%\n", ejemplo[0]);
        System.out.printf("  Reproducibilidad (AV): %.2f%%\n", ejemplo[1]);
        System.out.printf("  GRR Total: %.2f%%\n", ejemplo[2]);
        System.out.printf("  ndc: %.1f\n", ejemplo[3]);
        System.out.printf("  Linearidad: %.2f%%\n", ejemplo[4]);
        System.out.printf("  Estabilidad: %.2f%%\n", ejemplo[5]);
        
        int prediccion = modelo.predecir(ejemplo);
        Map<Integer, Double> probabilidades = modelo.predecirProba(ejemplo);
        
        System.out.println("\nPrediccion: " + nombresClases[prediccion]);
        System.out.println("Probabilidades:");
        for (int i = 0; i < 3; i++) {
            System.out.printf("  %s: %.2f%%\n", nombresClases[i], probabilidades.get(i) * 100);
        }
        
        // Interpretación según estándares MSA
        System.out.println("\n=== INTERPRETACION SEGUN MSA ===");
        if (prediccion == 2) {
            System.out.println("✓ Sistema ACEPTABLE: Puede usarse sin restricciones");
            System.out.println("  - GRR < 10%: Excelente variacion del sistema");
            System.out.println("  - ndc >= 5: Resolucion adecuada");
        } else if (prediccion == 1) {
            System.out.println("⚠ Sistema MARGINAL: Aceptable con reservas");
            System.out.println("  - GRR entre 10-30%: Requiere mejoras");
            System.out.println("  - Considerar: calibracion y capacitacion");
        } else {
            System.out.println("✗ Sistema INACEPTABLE: NO usar para mediciones criticas");
            System.out.println("  - GRR > 30%: Variacion excesiva");
            System.out.println("  - Accion requerida: Reemplazar equipo o proceso");
        }
        
        // Preguntas de reflexión
        System.out.println("\n=== PREGUNTAS DE REFLEXION ===");
        System.out.println("1. ¿Por que un GRR < 10% se considera aceptable segun AIAG?");
        System.out.println("2. ¿Cual es la diferencia entre repetibilidad y reproducibilidad?");
        System.out.println("3. ¿Que significa que ndc = 2 en un sistema de medicion?");
        System.out.println("4. ¿Como afecta un sistema marginal a las decisiones de calidad?");
        System.out.println("5. ¿Es etico usar IA para clasificar sistemas de medicion automaticamente?");
        System.out.println("6. ¿Que impacto tiene un falso negativo (sistema malo clasificado como bueno)?");
    }
}