// ESCENARIO 6: Logistica Internacional - Retrasos en Envios
// Archivo: Escenario6_Actividad5_IA.java

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

public class Escenario6_Actividad5_IA {
    public static void main(String[] args) {
        // Dataset: Envios Internacionales
        // Columnas: distancia_km, peso_kg, num_documentos_aduanales, dias_procesamiento, retraso
        double[][] datos = {
            {5200, 850, 3, 2, 0}, {12500, 2300, 8, 5, 1}, {3800, 450, 2, 1, 0},
            {15000, 3200, 12, 7, 1}, {4100, 620, 2, 2, 0}, {8900, 1800, 6, 4, 1},
            {3200, 380, 2, 1, 0}, {11200, 2100, 9, 6, 1}, {4800, 720, 3, 2, 0},
            {13800, 2850, 11, 6, 1}, {3500, 410, 2, 1, 0}, {9500, 1950, 7, 5, 1},
            {4500, 680, 3, 2, 0}, {14200, 3100, 13, 8, 1}, {3900, 520, 2, 2, 0},
            {10800, 2200, 8, 5, 1}, {3300, 390, 2, 1, 0}, {12800, 2650, 10, 6, 1},
            {4200, 590, 3, 2, 0}, {11800, 2400, 9, 5, 1}, {3600, 430, 2, 1, 0},
            {13200, 2900, 11, 7, 1}, {4700, 710, 3, 2, 0}, {9200, 1850, 7, 4, 1},
            {3400, 400, 2, 1, 0}, {14800, 3300, 14, 8, 1}, {4400, 640, 3, 2, 0},
            {10200, 2050, 8, 5, 1}, {3700, 480, 2, 1, 0}, {12200, 2500, 10, 6, 1}
        };
        
        System.out.println("======================================================================");
        System.out.println("ESCENARIO 6: LOGISTICA INTERNACIONAL - PREDICCION DE RETRASOS");
        System.out.println("======================================================================");
        System.out.println("\nDescripcion del problema:");
        System.out.println("Una empresa de logistica internacional necesita predecir que envios");
        System.out.println("tienen alto riesgo de retrasarse para tomar medidas preventivas.");
        System.out.println("\nFactores logisticos analizados:");
        System.out.println("- Distancia del envio (km)");
        System.out.println("- Peso del paquete (kg)");
        System.out.println("- Numero de documentos aduanales requeridos");
        System.out.println("- Dias de procesamiento en aduana");
        
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
        System.out.printf("Envios correctamente clasificados: %d/%d\n", correctos, X_test.length);
        
        System.out.println("\nMatriz de Confusion:");
        System.out.println("                    Pred: A TIEMPO  Pred: RETRASO");
        System.out.printf("Real: A TIEMPO            %3d           %3d\n", matriz[0][0], matriz[0][1]);
        System.out.printf("Real: RETRASO             %3d           %3d\n", matriz[1][0], matriz[1][1]);
        
        // Analisis de costo
        int costo_falso_negativo = 5000;  // USD - compensacion por retraso no previsto
        int costo_falso_positivo = 500;   // USD - costo de medidas preventivas innecesarias
        
        int costo_total = (matriz[1][0] * costo_falso_negativo) + (matriz[0][1] * costo_falso_positivo);
        
        System.out.println("\nAnalisis de Costos:");
        System.out.printf("Falsos Negativos (retrasos no detectados): %d x $%,d = $%,d\n", 
                         matriz[1][0], costo_falso_negativo, matriz[1][0] * costo_falso_negativo);
        System.out.printf("Falsos Positivos (alarmas falsas): %d x $%,d = $%,d\n", 
                         matriz[0][1], costo_falso_positivo, matriz[0][1] * costo_falso_positivo);
        System.out.printf("Costo Total: $%,d\n", costo_total);
        
        // Evaluacion de nuevos envios
        double[][] nuevos_envios = {
            {4500, 650, 3, 2},
            {13500, 2800, 11, 7},
            {7200, 1200, 5, 3}
        };
        String[] descripciones = {
            "Envio Nacional - Standard",
            "Envio Internacional - Complejo",
            "Envio Regional - Moderado"
        };
        
        System.out.println("\n======================================================================");
        System.out.println("EVALUACION DE ENVIOS PENDIENTES");
        System.out.println("======================================================================");
        
        for (int i = 0; i < nuevos_envios.length; i++) {
            double[] datos_envio = nuevos_envios[i];
            String descripcion = descripciones[i];
            int resultado = nb.predecir(datos_envio);
            Map<Integer, Double> prob = nb.predecirProba(datos_envio);
            
            System.out.println("\n" + descripcion + ":");
            System.out.printf("  Distancia: %,.0f km\n", datos_envio[0]);
            System.out.printf("  Peso: %.0f kg\n", datos_envio[1]);
            System.out.printf("  Documentos aduanales: %.0f\n", datos_envio[2]);
            System.out.printf("  Dias procesamiento: %.0f\n", datos_envio[3]);
            System.out.println("  Prediccion: " + (resultado == 1 ? "🚨 RIESGO DE RETRASO" : "✓ ENTREGA A TIEMPO"));
            System.out.printf("  Confianza A TIEMPO: %.1f%%\n", prob.get(0) * 100);
            System.out.printf("  Confianza RETRASO: %.1f%%\n", prob.get(1) * 100);
            
            if (resultado == 1) {
                System.out.println("  📋 ACCIONES RECOMENDADAS:");
                System.out.println("     - Acelerar tramites aduanales");
                System.out.println("     - Notificar cliente sobre posible retraso");
                System.out.println("     - Asignar courier premium");
                System.out.println("     - Revisar ruta alternativa");
            }
        }
        
        System.out.println("\n======================================================================");
        System.out.println("PREGUNTAS DE ANALISIS:");
        System.out.println("======================================================================");
        System.out.println("1. ¿Cual factor logistico impacta mas en los retrasos?");
        System.out.println("2. ¿Como optimizarias la relacion costo-beneficio del modelo?");
        System.out.println("3. ¿Que KPIs logisticos adicionales deberian considerarse?");
        System.out.println("4. ¿Como afectan las regulaciones aduanales al modelo predictivo?");
    }
}0