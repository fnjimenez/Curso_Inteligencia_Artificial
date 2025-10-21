// ESCENARIO 3: Diagnostico Medico - Deteccion de Diabetes
// Archivo: Escenario3_Actividad5_IA.java

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

public class Escenario3_Actividad5_IA {
    public static void main(String[] args) {
        // Dataset: Diagnostico de Diabetes
        // Columnas: glucosa, presion_arterial, IMC, edad, diabetes
        double[][] datos = {
            {148, 72, 33.6, 50, 1}, {85, 66, 26.6, 31, 0}, {183, 64, 23.3, 32, 1},
            {89, 66, 28.1, 21, 0}, {137, 40, 43.1, 33, 1}, {116, 74, 25.6, 30, 0},
            {78, 50, 31.0, 26, 0}, {115, 0, 35.3, 29, 0}, {197, 70, 30.5, 53, 1},
            {125, 96, 0.0, 54, 1}, {110, 92, 37.6, 30, 0}, {168, 74, 38.0, 34, 1},
            {139, 80, 27.1, 57, 0}, {189, 60, 30.1, 59, 1}, {166, 72, 25.8, 51, 1},
            {100, 0, 30.0, 32, 0}, {118, 84, 45.8, 31, 1}, {107, 74, 29.6, 31, 0},
            {103, 30, 43.3, 33, 0}, {115, 70, 30.5, 34, 0}, {126, 88, 39.3, 27, 0},
            {99, 84, 35.4, 50, 0}, {196, 90, 39.8, 41, 1}, {119, 80, 35.3, 29, 0},
            {143, 94, 36.6, 51, 1}, {125, 70, 31.1, 41, 1}, {147, 76, 39.4, 43, 1},
            {97, 66, 23.2, 22, 0}, {145, 82, 32.5, 70, 1}, {117, 92, 34.1, 38, 0}
        };
        
        System.out.println("======================================================================");
        System.out.println("ESCENARIO 3: DIAGNOSTICO MEDICO - DETECCION DE DIABETES");
        System.out.println("======================================================================");
        System.out.println("\nDescripcion del problema:");
        System.out.println("Un centro medico necesita un sistema de apoyo para identificar pacientes");
        System.out.println("con alto riesgo de diabetes tipo 2 basado en indicadores clinicos.");
        System.out.println("\nIndicadores medicos evaluados:");
        System.out.println("- Nivel de glucosa en sangre (mg/dL)");
        System.out.println("- Presion arterial diastolica (mm Hg)");
        System.out.println("- Indice de Masa Corporal (IMC)");
        System.out.println("- Edad (años)");
        
        // Mezclar datos
        Random random = new Random(42);
        List<double[]> listaDatos = Arrays.asList(datos);
        Collections.shuffle(listaDatos, random);
        
        // Dividir en entrenamiento y prueba (75%-25%)
        int split = (int)(datos.length * 0.75);
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
        
        // Metricas medicas
        int VP = matriz[1][1];  // Verdaderos Positivos
        int FN = matriz[1][0];  // Falsos Negativos
        int VN = matriz[0][0];  // Verdaderos Negativos
        int FP = matriz[0][1];  // Falsos Positivos
        
        double sensibilidad = (VP + FN) > 0 ? (VP * 100.0) / (VP + FN) : 0;
        double especificidad = (VN + FP) > 0 ? (VN * 100.0) / (VN + FP) : 0;
        
        System.out.println("\n======================================================================");
        System.out.println("RESULTADOS DEL ANALISIS CLINICO");
        System.out.println("======================================================================");
        System.out.printf("Accuracy: %.2f%%\n", accuracy);
        System.out.printf("Sensibilidad (detecta enfermos): %.2f%%\n", sensibilidad);
        System.out.printf("Especificidad (detecta sanos): %.2f%%\n", especificidad);
        
        System.out.println("\nMatriz de Confusion:");
        System.out.println("                    Pred: SANO  Pred: DIABETES");
        System.out.printf("Real: SANO               %3d         %3d\n", matriz[0][0], matriz[0][1]);
        System.out.printf("Real: DIABETES           %3d         %3d\n", matriz[1][0], matriz[1][1]);
        
        // Diagnostico de nuevo paciente
        double[] nuevo_paciente = {155, 78, 34.2, 45};
        int resultado = nb.predecir(nuevo_paciente);
        Map<Integer, Double> prob = nb.predecirProba(nuevo_paciente);
        
        System.out.println("\n======================================================================");
        System.out.println("EVALUACION DE NUEVO PACIENTE");
        System.out.println("======================================================================");
        System.out.printf("Glucosa: %.0f mg/dL\n", nuevo_paciente[0]);
        System.out.printf("Presion arterial: %.0f mm Hg\n", nuevo_paciente[1]);
        System.out.printf("IMC: %.1f\n", nuevo_paciente[2]);
        System.out.printf("Edad: %.0f años\n", nuevo_paciente[3]);
        System.out.println("\nDiagnostico: " + (resultado == 1 ? "RIESGO DE DIABETES ⚠️" : "PERFIL NORMAL ✓"));
        System.out.printf("Confianza diagnostico SANO: %.2f%%\n", prob.get(0) * 100);
        System.out.printf("Confianza diagnostico DIABETES: %.2f%%\n", prob.get(1) * 100);
        
        System.out.println("\n======================================================================");
        System.out.println("PREGUNTAS DE ANALISIS MEDICO:");
        System.out.println("======================================================================");
        System.out.println("1. ¿Es mas grave un falso negativo o un falso positivo en este contexto?");
        System.out.println("2. ¿Por que la sensibilidad es critica en diagnosticos medicos?");
        System.out.println("3. ¿Que cambios en el estilo de vida recomendarias al paciente evaluado?");
        System.out.println("4. ¿Como mejorar la confiabilidad del modelo para uso clinico real?");
    }
}