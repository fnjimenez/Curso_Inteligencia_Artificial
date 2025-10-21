// ESCENARIO 1: Control de Calidad en Manufactura
// Archivo: Escenario1_Actividad5_IA.java

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
        for (double p : probabilidades.values()) {
            total += p;
        }
        if (total > 0) {
            for (Map.Entry<Integer, Double> entry : probabilidades.entrySet()) {
                probabilidades.put(entry.getKey(), entry.getValue() / total);
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

public class Escenario1_Actividad5_IA {
    public static void main(String[] args) {
        // Dataset: Control de Calidad de Piezas
        // Columnas: diametro(mm), longitud(mm), peso(g), temperatura(°C), calidad
        double[][] datos = {
            {10.2, 50.1, 45.2, 180, 1}, {10.1, 50.3, 45.5, 182, 1}, {10.3, 50.0, 45.1, 181, 1},
            {9.8, 49.5, 44.0, 175, 0}, {10.0, 50.2, 45.3, 183, 1}, {9.9, 49.8, 44.8, 179, 1},
            {11.5, 51.2, 47.0, 190, 0}, {10.2, 50.0, 45.0, 180, 1}, {10.1, 50.1, 45.2, 181, 1},
            {9.7, 49.3, 43.5, 172, 0}, {10.3, 50.2, 45.4, 182, 1}, {10.0, 50.0, 45.0, 180, 1},
            {11.8, 51.5, 47.5, 195, 0}, {10.2, 50.3, 45.6, 183, 1}, {9.6, 49.0, 43.0, 170, 0},
            {10.1, 50.1, 45.1, 181, 1}, {10.4, 50.4, 45.7, 184, 1}, {9.5, 48.8, 42.5, 168, 0},
            {10.0, 50.0, 45.0, 180, 1}, {10.3, 50.2, 45.3, 182, 1}, {12.0, 52.0, 48.0, 200, 0},
            {10.2, 50.1, 45.2, 181, 1}, {9.9, 49.9, 44.9, 179, 1}, {10.1, 50.0, 45.1, 180, 1},
            {11.2, 51.0, 46.5, 188, 0}, {10.0, 50.2, 45.2, 182, 1}, {10.3, 50.1, 45.4, 181, 1},
            {9.8, 49.6, 44.2, 176, 0}, {10.2, 50.0, 45.0, 180, 1}, {10.1, 50.3, 45.5, 183, 1}
        };
        
        System.out.println("======================================================================");
        System.out.println("ESCENARIO 1: CONTROL DE CALIDAD EN MANUFACTURA");
        System.out.println("======================================================================");
        System.out.println("\nDescripcion del problema:");
        System.out.println("Una fabrica produce piezas metalicas y necesita clasificarlas como");
        System.out.println("APTAS o DEFECTUOSAS segun sus dimensiones y temperatura de fundicion.");
        System.out.println("\nCaracteristicas medidas:");
        System.out.println("- Diametro (mm)");
        System.out.println("- Longitud (mm)");
        System.out.println("- Peso (gramos)");
        System.out.println("- Temperatura de fundicion (°C)");
        
        // Mezclar datos
        Random random = new Random(42);
        List<double[]> listadatos = Arrays.asList(datos);
        Collections.shuffle(listaD atos, random);
        
        // Dividir en entrenamiento y prueba
        int split = (int)(datos.length * 0.7);
        double[][] X_train = new double[split][4];
        int[] y_train = new int[split];
        double[][] X_test = new double[datos.length - split][4];
        int[] y_test = new int[datos.length - split];
        
        for (int i = 0; i < split; i++) {
            System.arraycopy(listaD atos.get(i), 0, X_train[i], 0, 4);
            y_train[i] = (int)listaD atos.get(i)[4];
        }
        
        for (int i = split; i < datos.length; i++) {
            System.arraycopy(listaD atos.get(i), 0, X_test[i - split], 0, 4);
            y_test[i - split] = (int)listaD atos.get(i)[4];
        }
        
        // Entrenar modelo
        NaiveBayes nb = new NaiveBayes();
        nb.entrenar(X_train, y_train);
        
        // Evaluar
        int correctos = 0;
        int[][] matriz = new int[2][2];
        
        for (int i = 0; i < X_test.length; i++) {
            int pred = nb.predecir(X_test[i]);
            int real = y_test[i];
            matriz[real][pred]++;
            if (pred == real) {
                correctos++;
            }
        }
        
        double accuracy = (correctos * 100.0) / X_test.length;
        
        System.out.println("\n======================================================================");
        System.out.println("RESULTADOS DEL ANALISIS");
        System.out.println("======================================================================");
        System.out.println("\nDatos de entrenamiento: " + X_train.length);
        System.out.println("Datos de prueba: " + X_test.length);
        System.out.printf("\nAccuracy: %.2f%%\n", accuracy);
        System.out.printf("Piezas correctamente clasificadas: %d/%d\n", correctos, X_test.length);
        
        System.out.println("\nMatriz de Confusion:");
        System.out.println("                    Predicho DEFECTUOSA  Predicho APTA");
        System.out.printf("Real DEFECTUOSA            %3d               %3d\n", matriz[0][0], matriz[0][1]);
        System.out.printf("Real APTA                  %3d               %3d\n", matriz[1][0], matriz[1][1]);
        
        // Prediccion de nueva pieza
        double[] nueva_pieza = {10.15, 50.05, 45.25, 181.5};
        int resultado = nb.predecir(nueva_pieza);
        Map<Integer, Double> probabilidades = nb.predecirProba(nueva_pieza);
        
        System.out.println("\n======================================================================");
        System.out.println("PREDICCION DE NUEVA PIEZA");
        System.out.println("======================================================================");
        System.out.printf("Diametro: %.2f mm\n", nueva_pieza[0]);
        System.out.printf("Longitud: %.2f mm\n", nueva_pieza[1]);
        System.out.printf("Peso: %.2f g\n", nueva_pieza[2]);
        System.out.printf("Temperatura: %.1f °C\n", nueva_pieza[3]);
        System.out.println("\nClasificacion: " + (resultado == 1 ? "APTA ✓" : "DEFECTUOSA ✗"));
        System.out.printf("Confianza DEFECTUOSA: %.2f%%\n", probabilidades.get(0) * 100);
        System.out.printf("Confianza APTA: %.2f%%\n", probabilidades.get(1) * 100);
        
        System.out.println("\n======================================================================");
        System.out.println("PREGUNTAS DE ANALISIS PARA EL ALUMNO:");
        System.out.println("======================================================================");
        System.out.println("1. ¿Que tan confiable es el modelo con este accuracy?");
        System.out.println("2. ¿Cual seria el costo de clasificar incorrectamente una pieza DEFECTUOSA como APTA?");
        System.out.println("3. ¿Que caracteristica parece ser mas determinante para la clasificacion?");
        System.out.println("4. ¿Como podrias mejorar el modelo con mas datos?");
    }
}