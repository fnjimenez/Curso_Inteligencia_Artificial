// ESCENARIO 2: Prediccion de Abandono de Clientes (Churn)
// Archivo: Escenario2_Actividad5_IA.java

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

public class Escenario2_Actividad5_IA {
    public static void main(String[] args) {
        // Dataset: Abandono de Clientes
        // Columnas: meses_servicio, llamadas_soporte, gasto_mensual, minutos_uso, abandono
        double[][] datos = {
            {24, 2, 65.5, 450, 0}, {12, 5, 45.2, 320, 1}, {36, 1, 78.3, 520, 0},
            {6, 8, 35.0, 180, 1}, {48, 0, 85.6, 600, 0}, {18, 4, 52.1, 280, 1},
            {30, 1, 72.4, 480, 0}, {9, 6, 38.5, 210, 1}, {42, 2, 80.2, 550, 0},
            {15, 7, 42.3, 250, 1}, {27, 3, 68.7, 430, 0}, {8, 9, 33.8, 160, 1},
            {33, 1, 75.5, 500, 0}, {14, 5, 48.9, 290, 1}, {40, 2, 82.1, 570, 0},
            {11, 6, 40.2, 230, 1}, {25, 2, 66.8, 460, 0}, {7, 10, 32.5, 150, 1},
            {35, 1, 77.9, 530, 0}, {13, 4, 46.7, 270, 1}, {29, 2, 70.3, 490, 0},
            {10, 7, 37.6, 200, 1}, {44, 1, 83.4, 580, 0}, {16, 5, 50.5, 300, 1},
            {31, 3, 73.2, 470, 0}, {19, 4, 54.8, 310, 1}, {38, 1, 79.6, 540, 0},
            {17, 6, 51.3, 285, 1}, {26, 2, 67.9, 445, 0}, {20, 3, 56.4, 330, 1}
        };
        
        System.out.println("======================================================================");
        System.out.println("ESCENARIO 2: PREDICCION DE ABANDONO DE CLIENTES (CHURN)");
        System.out.println("======================================================================");
        System.out.println("\nDescripcion del problema:");
        System.out.println("Una empresa de telecomunicaciones quiere predecir que clientes tienen");
        System.out.println("mayor riesgo de cancelar su servicio (abandono/churn).");
        System.out.println("\nCaracteristicas analizadas:");
        System.out.println("- Meses de servicio con la empresa");
        System.out.println("- Numero de llamadas al soporte tecnico");
        System.out.println("- Gasto mensual promedio (USD)");
        System.out.println("- Minutos de uso mensual");
        
        // Mezclar datos
        Random random = new Random(42);
        List<double[]> listaDatos = Arrays.asList(datos);
        Collections.shuffle(listaDatos, random);
        
        // Dividir en entrenamiento y prueba
        int split = (int)(datos.length * 0.7);
        double[][] X_train = new double[split][4];
        int[] y_train = new int[split];
        double[][] X_test = new double[datos.length - split][4];
        int[] y_test = new int[datos.length - split];
        
        for (int i = 0; i < split; i++) {
            System.arraycopy(listaDatos.get(i), 0, X_train[i], 0, 4);
            y_train[i] = (int)listaDatos.get(i)[4];
        }
        
        for (int i = split; i < datos.length; i++) {
            System.arraycopy(listaDatos.get(i), 0, X_test[i - split], 0, 4);
            y_test[i - split] = (int)listaDatos.get(i)[4];
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
        System.out.printf("Accuracy: %.2f%%\n", accuracy);
        
        System.out.println("\nMatriz de Confusion:");
        System.out.println("                    Pred: SE QUEDA  Pred: ABANDONA");
        System.out.printf("Real: SE QUEDA            %3d            %3d\n", matriz[0][0], matriz[0][1]);
        System.out.printf("Real: ABANDONA            %3d            %3d\n", matriz[1][0], matriz[1][1]);
        
        // Analisis de cliente nuevo
        double[] nuevo_cliente = {22, 4, 58.5, 350};
        int resultado = nb.predecir(nuevo_cliente);
        Map<Integer, Double> prob = nb.predecirProba(nuevo_cliente);
        
        System.out.println("\n======================================================================");
        System.out.println("ANALISIS DE CLIENTE NUEVO");
        System.out.println("======================================================================");
        System.out.printf("Meses de servicio: %.0f\n", nuevo_cliente[0]);
        System.out.printf("Llamadas a soporte: %.0f\n", nuevo_cliente[1]);
        System.out.printf("Gasto mensual: $%.2f\n", nuevo_cliente[2]);
        System.out.printf("Minutos de uso: %.0f\n", nuevo_cliente[3]);
        System.out.println("\nPrediccion: " + (resultado == 1 ? "RIESGO DE ABANDONO ⚠️" : "SE MANTIENE ✓"));
        System.out.printf("Probabilidad de quedarse: %.2f%%\n", prob.get(0) * 100);
        System.out.printf("Probabilidad de abandonar: %.2f%%\n", prob.get(1) * 100);
        
        System.out.println("\n======================================================================");
        System.out.println("PREGUNTAS DE ANALISIS:");
        System.out.println("======================================================================");
        System.out.println("1. ¿Cual caracteristica es mas indicativa de abandono?");
        System.out.println("2. ¿Que estrategias de retencion recomendarias para clientes en riesgo?");
        System.out.println("3. ¿Cual es el costo de un falso negativo (predecir que se queda pero abandona)?");
        System.out.println("4. ¿Como afecta el numero de llamadas a soporte en la decision del cliente?");
    }
}