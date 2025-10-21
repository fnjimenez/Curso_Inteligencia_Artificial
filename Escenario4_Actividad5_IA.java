// ESCENARIO 4: Deteccion de Fraude en Transacciones
// Archivo: Escenario4_Actividad5_IA.java

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
        for (int i = 0; i < X.length; i++) {
            int clase = y[i];
            if (!clases.containsKey(clase)) {
                clases.put(clase, new ArrayList<>());
            }
            clases.get(clase).add(X[i]);
        }
        
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

public class Escenario4_Actividad5_IA {
    public static void main(String[] args) {
        // Dataset: Deteccion de Fraude
        // Columnas: monto, hora, distancia_ubicacion_usual, intentos_previos, fraude
        double[][] datos = {
            {45.50, 14, 2.5, 0, 0}, {1250.00, 3, 850, 1, 1}, {89.99, 18, 5.2, 0, 0},
            {2500.00, 2, 1200, 2, 1}, {32.75, 12, 1.8, 0, 0}, {150.00, 20, 8.5, 0, 0},
            {3200.00, 4, 2500, 3, 1}, {67.80, 16, 3.2, 0, 0}, {1800.00, 1, 950, 1, 1},
            {25.50, 10, 0.5, 0, 0}, {950.00, 5, 650, 1, 1}, {110.25, 19, 12.0, 0, 0},
            {2100.00, 3, 1500, 2, 1}, {78.90, 15, 4.5, 0, 0}, {1500.00, 2, 800, 1, 1},
            {55.00, 13, 6.8, 0, 0}, {2800.00, 4, 1800, 2, 1}, {42.30, 11, 2.1, 0, 0},
            {1100.00, 1, 700, 1, 1}, {95.75, 17, 9.2, 0, 0}, {3500.00, 5, 3000, 3, 1},
            {38.60, 14, 1.5, 0, 0}, {1650.00, 2, 900, 1, 1}, {72.40, 16, 5.8, 0, 0},
            {2200.00, 3, 1300, 2, 1}, {48.90, 12, 3.5, 0, 0}, {1350.00, 4, 750, 1, 1},
            {81.20, 18, 7.5, 0, 0}, {2900.00, 1, 2100, 2, 1}, {59.50, 15, 4.2, 0, 0}
        };
        
        System.out.println("======================================================================");
        System.out.println("ESCENARIO 4: DETECCION DE FRAUDE EN TRANSACCIONES BANCARIAS");
        System.out.println("======================================================================");
        System.out.println("\nDescripcion del problema:");
        System.out.println("Un banco necesita identificar transacciones potencialmente fraudulentas");
        System.out.println("en tiempo real para proteger a sus clientes.");
        System.out.println("\nIndicadores de riesgo analizados:");
        System.out.println("- Monto de la transaccion (USD)");
        System.out.println("- Hora del dia (0-23)");
        System.out.println("- Distancia de ubicacion usual (km)");
        System.out.println("- Intentos previos fallidos");
        
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
        System.out.println("                    Pred: LEGITIMA  Pred: FRAUDE");
        System.out.printf("Real: LEGITIMA            %3d           %3d\n", matriz[0][0], matriz[0][1]);
        System.out.printf("Real: FRAUDE              %3d           %3d\n", matriz[1][0], matriz[1][1]);
        
        // Analisis financiero
        int fallas_no_detectadas = matriz[1][0];
        int alertas_falsas = matriz[0][1];
        
        int costo_fraude_no_detectado = 15000;  // USD promedio
        int costo_alerta_falsa = 50;  // USD costo de revision
        
        int costo_fallas_perdidas = fallas_no_detectadas * costo_fraude_no_detectado;
        int costo_alertas_falsas = alertas_falsas * costo_alerta_falsa;
        int ahorro_fraudes_detectados = matriz[1][1] * costo_fraude_no_detectado;
        
        System.out.println("\nAnalisis Financiero:");
        System.out.printf("Costo por fraudes NO detectados: $%,d\n", costo_fallas_perdidas);
        System.out.printf("Costo por alertas falsas: $%,d\n", costo_alertas_falsas);
        System.out.printf("Ahorro por fraudes detectados: $%,d\n", ahorro_fraudes_detectados);
        
        // Analisis de transaccion sospechosa
        double[] nueva_transaccion = {1800.00, 3, 1100, 1};
        int resultado = nb.predecir(nueva_transaccion);
        Map<Integer, Double> prob = nb.predecirProba(nueva_transaccion);
        
        System.out.println("\n======================================================================");
        System.out.println("ANALISIS DE TRANSACCION EN TIEMPO REAL");
        System.out.println("======================================================================");
        System.out.printf("Monto: $%.2f\n", nueva_transaccion[0]);
        System.out.printf("Hora: %02d:00 hrs\n", (int)nueva_transaccion[1]);
        System.out.printf("Distancia de ubicacion usual: %.0f km\n", nueva_transaccion[2]);
        System.out.printf("Intentos previos fallidos: %.0f\n", nueva_transaccion[3]);
        System.out.println("\nVeredicto: " + (resultado == 1 ? "🚨 FRAUDE DETECTADO" : "✓ TRANSACCION LEGITIMA"));
        System.out.printf("Probabilidad LEGITIMA: %.2f%%\n", prob.get(0) * 100);
        System.out.printf("Probabilidad FRAUDE: %.2f%%\n", prob.get(1) * 100);
        
        if (resultado == 1) {
            System.out.println("\n⚠️ ACCION RECOMENDADA: Bloquear transaccion y contactar al cliente");
        } else {
            System.out.println("\n✓ ACCION: Aprobar transaccion");
        }
        
        System.out.println("\n======================================================================");
        System.out.println("PREGUNTAS DE ANALISIS:");
        System.out.println("======================================================================");
        System.out.println("1. ¿Cual es el costo de un falso positivo (bloquear transaccion legitima)?");
        System.out.println("2. ¿Cual es el costo de un falso negativo (aprobar fraude)?");
        System.out.println("3. ¿Que patron caracteriza mejor las transacciones fraudulentas?");
        System.out.println("4. ¿Como balancear seguridad vs. experiencia del cliente?");
    }
}