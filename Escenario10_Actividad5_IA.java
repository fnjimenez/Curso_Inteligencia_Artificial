// ESCENARIO 10: Control de Calidad Avanzado - Clasificación Premium/Aceptable/Defectuoso
// Archivo: Escenario10_Actividad5_IA.java

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

public class Escenario10_Actividad5_IA {
    
    private static double[][] generarDataset(int n, Random rand) {
        double[][] datos = new double[n][5];
        
        for (int i = 0; i < n; i++) {
            // Dimensiones (mm)
            datos[i][0] = 99.5 + rand.nextGaussian() * 0.5;  // Largo: 100 ± 0.5 mm
            datos[i][1] = 49.8 + rand.nextGaussian() * 0.3;  // Ancho: 50 ± 0.3 mm
            datos[i][2] = 9.95 + rand.nextGaussian() * 0.15; // Grosor: 10 ± 0.15 mm
            
            // Propiedades físicas
            datos[i][3] = 7800 + rand.nextGaussian() * 50;   // Dureza HRC: 7800 ± 50
            datos[i][4] = 0.1 + rand.nextGaussian() * 0.03;  // Rugosidad Ra: 0.1 ± 0.03 μm
        }
        
        return datos;
    }
    
    private static int[] generarEtiquetas(double[][] X) {
        int[] etiquetas = new int[X.length];
        
        for (int i = 0; i < X.length; i++) {
            double largo = X[i][0];
            double ancho = X[i][1];
            double grosor = X[i][2];
            double dureza = X[i][3];
            double rugosidad = X[i][4];
            
            // Criterios de clasificación
            boolean dimensionesExcelentes = 
                Math.abs(largo - 100.0) < 0.2 &&
                Math.abs(ancho - 50.0) < 0.15 &&
                Math.abs(grosor - 10.0) < 0.08;
            
            boolean propiedadesExcelentes =
                dureza > 7850 && rugosidad < 0.08;
            
            boolean dimensionesAceptables =
                Math.abs(largo - 100.0) < 0.5 &&
                Math.abs(ancho - 50.0) < 0.3 &&
                Math.abs(grosor - 10.0) < 0.15;
            
            boolean propiedadesAceptables =
                dureza > 7750 && rugosidad < 0.13;
            
            if (dimensionesExcelentes && propiedadesExcelentes) {
                etiquetas[i] = 2; // Premium
            } else if (dimensionesAceptables && propiedadesAceptables) {
                etiquetas[i] = 1; // Aceptable
            } else {
                etiquetas[i] = 0; // Defectuoso
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
        System.out.print("            ");
        for (int i = 0; i < numClases; i++) {
            System.out.printf("Pred_%d  ", i);
        }
        System.out.println();
        
        String[] nombres = {"Defectuoso", "Aceptable ", "Premium   "};
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
    
    private static double calcularCostoTotal(int[] y_true, int[] y_pred) {
        // Costos por tipo de error (en USD)
        final double COSTO_DEFECTO_COMO_PREMIUM = 500.0;    // Producto defectuoso vendido como premium
        final double COSTO_DEFECTO_COMO_ACEPTABLE = 200.0;  // Producto defectuoso vendido como aceptable
        final double COSTO_PREMIUM_COMO_DEFECTO = 150.0;    // Producto premium rechazado
        final double COSTO_PREMIUM_COMO_ACEPTABLE = 50.0;   // Producto premium vendido como aceptable
        final double COSTO_ACEPTABLE_COMO_DEFECTO = 80.0;   // Producto aceptable rechazado
        final double COSTO_ACEPTABLE_COMO_PREMIUM = 30.0;   // Producto aceptable vendido como premium
        
        double costoTotal = 0;
        
        for (int i = 0; i < y_true.length; i++) {
            if (y_true[i] == 0 && y_pred[i] == 2) costoTotal += COSTO_DEFECTO_COMO_PREMIUM;
            else if (y_true[i] == 0 && y_pred[i] == 1) costoTotal += COSTO_DEFECTO_COMO_ACEPTABLE;
            else if (y_true[i] == 2 && y_pred[i] == 0) costoTotal += COSTO_PREMIUM_COMO_DEFECTO;
            else if (y_true[i] == 2 && y_pred[i] == 1) costoTotal += COSTO_PREMIUM_COMO_ACEPTABLE;
            else if (y_true[i] == 1 && y_pred[i] == 0) costoTotal += COSTO_ACEPTABLE_COMO_DEFECTO;
            else if (y_true[i] == 1 && y_pred[i] == 2) costoTotal += COSTO_ACEPTABLE_COMO_PREMIUM;
        }
        
        return costoTotal;
    }
    
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("ESCENARIO 10: CONTROL DE CALIDAD AVANZADO");
        System.out.println("Clasificacion: Premium/Aceptable/Defectuoso");
        System.out.println("========================================\n");
        
        Random rand = new Random(42);
        
        // Generar dataset
        System.out.println("Generando dataset de componentes manufacturados...");
        double[][] X = generarDataset(500, rand);
        int[] y = generarEtiquetas(X);
        
        // Contar clases
        Map<Integer, Integer> conteoClases = new HashMap<>();
        for (int clase : y) {
            conteoClases.put(clase, conteoClases.getOrDefault(clase, 0) + 1);
        }
        
        System.out.println("\nDistribucion de clases:");
        System.out.println("- Defectuoso (0): " + conteoClases.get(0));
        System.out.println("- Aceptable (1): " + conteoClases.get(1));
        System.out.println("- Premium (2): " + conteoClases.get(2));
        
        // Dividir dataset
        int nTest = (int)(X.length * 0.2);
        int nTrain = X.length - nTest;
        
        double[][] X_train = new double[nTrain][5];
        int[] y_train = new int[nTrain];
        double[][] X_test = new double[nTest][5];
        int[] y_test = new int[nTest];
        
        dividirDataset(X, y, X_train, y_train, X_test, y_test, 0.2, rand);
        
        System.out.println("\nConjunto de entrenamiento: " + X_train.length + " muestras");
        System.out.println("Conjunto de prueba: " + X_test.length + " muestras");
        
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
        String[] nombresClases = {"Defectuoso", "Aceptable", "Premium"};
        
        for (int i = 0; i < 3; i++) {
            Map<String, Double> m = metricas.get(i);
            System.out.printf("\nClase %d (%s):\n", i, nombresClases[i]);
            System.out.printf("  Precision: %.2f%%\n", m.get("precision") * 100);
            System.out.printf("  Recall: %.2f%%\n", m.get("recall") * 100);
            System.out.printf("  F1-Score: %.2f%%\n", m.get("f1") * 100);
        }
        
        // Análisis de costos
        double costoTotal = calcularCostoTotal(y_test, y_pred);
        System.out.println("\n=== ANALISIS ECONOMICO ===");
        System.out.printf("Costo total de errores: $%.2f USD\n", costoTotal);
        System.out.printf("Costo promedio por producto: $%.2f USD\n", costoTotal / y_test.length);
        
        // Ejemplo de predicción
        System.out.println("\n=== EJEMPLO DE PREDICCION ===");
        double[] ejemplo = {99.85, 49.95, 9.98, 7820, 0.09};
        System.out.println("Componente nuevo:");
        System.out.printf("  Largo: %.2f mm\n", ejemplo[0]);
        System.out.printf("  Ancho: %.2f mm\n", ejemplo[1]);
        System.out.printf("  Grosor: %.2f mm\n", ejemplo[2]);
        System.out.printf("  Dureza: %.0f HRC\n", ejemplo[3]);
        System.out.printf("  Rugosidad: %.3f μm\n", ejemplo[4]);
        
        int prediccion = modelo.predecir(ejemplo);
        Map<Integer, Double> probabilidades = modelo.predecirProba(ejemplo);
        
        System.out.println("\nPrediccion: " + nombresClases[prediccion]);
        System.out.println("Probabilidades:");
        for (int i = 0; i < 3; i++) {
            System.out.printf("  %s: %.2f%%\n", nombresClases[i], probabilidades.get(i) * 100);
        }
        
        // Preguntas de reflexión
        System.out.println("\n=== PREGUNTAS DE REFLEXION ===");
        System.out.println("1. ¿Por que es importante tener 3 categorias en lugar de 2 (pasa/no pasa)?");
        System.out.println("2. ¿Cual es el error mas costoso: clasificar Premium como Defectuoso o viceversa?");
        System.out.println("3. ¿Como afecta la rugosidad superficial en la clasificacion final?");
        System.out.println("4. ¿Que estrategias implementarias para reducir el costo total de errores?");
        System.out.println("5. ¿Es adecuado un modelo probabilistico para control de calidad critico?");
    }
}