// ESCENARIO 7: Seguridad Industrial - Prediccion de Accidentes
// Archivo: Escenario7_Actividad5_IA.java

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

public class Escenario7_Actividad5_IA {
    public static void main(String[] args) {
        // Dataset: Accidentes en Fabrica
        // Columnas: hrs_trabajo_continuo, nivel_ruido_db, temperatura_C, dias_sin_capacitacion, accidente
        double[][] datos = {
            {6, 75, 22, 5, 0}, {12, 95, 35, 45, 1}, {7, 78, 24, 8, 0},
            {14, 98, 38, 60, 1}, {5, 70, 20, 3, 0}, {11, 92, 33, 40, 1},
            {6, 72, 21, 4, 0}, {13, 96, 36, 55, 1}, {7, 76, 23, 7, 0},
            {15, 100, 40, 65, 1}, {6, 74, 22, 6, 0}, {12, 94, 34, 42, 1},
            {7, 77, 24, 9, 0}, {14, 97, 37, 58, 1}, {5, 71, 21, 4, 0},
            {11, 91, 32, 38, 1}, {6, 73, 22, 5, 0}, {13, 95, 35, 50, 1},
            {7, 75, 23, 8, 0}, {12, 93, 33, 44, 1}, {6, 76, 22, 6, 0},
            {14, 99, 39, 62, 1}, {5, 72, 21, 3, 0}, {11, 90, 31, 36, 1},
            {7, 74, 23, 7, 0}, {13, 96, 36, 52, 1}, {6, 75, 22, 5, 0},
            {12, 92, 34, 46, 1}, {7, 77, 24, 8, 0}, {15, 98, 38, 64, 1}
        };
        
        System.out.println("======================================================================");
        System.out.println("ESCENARIO 7: SEGURIDAD INDUSTRIAL - PREDICCION DE ACCIDENTES");
        System.out.println("======================================================================");
        System.out.println("\nDescripcion del problema:");
        System.out.println("Una planta industrial necesita identificar condiciones de alto riesgo");
        System.out.println("para prevenir accidentes laborales y proteger a sus trabajadores.");
        System.out.println("\nFactores de riesgo evaluados:");
        System.out.println("- Horas de trabajo continuo");
        System.out.println("- Nivel de ruido ambiental (dB)");
        System.out.println("- Temperatura en area de trabajo (°C)");
        System.out.println("- Dias desde ultima capacitacion en seguridad");
        
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
        
        // Metricas de seguridad
        int VP = matriz[1][1];  // Verdaderos Positivos - Riesgos detectados
        int FN = matriz[1][0];  // Falsos Negativos - Riesgos NO detectados (CRITICO)
        int VN = matriz[0][0];  // Verdaderos Negativos
        int FP = matriz[0][1];  // Falsos Positivos
        
        double sensibilidad = (VP + FN) > 0 ? (VP * 100.0) / (VP + FN) : 0;
        
        System.out.println("\n======================================================================");
        System.out.println("RESULTADOS DEL ANALISIS DE SEGURIDAD");
        System.out.println("======================================================================");
        System.out.printf("Accuracy: %.2f%%\n", accuracy);
        System.out.printf("Sensibilidad (deteccion de riesgos): %.2f%%\n", sensibilidad);
        System.out.printf("⚠️ CRITICO - Riesgos NO detectados: %d\n", FN);
        
        System.out.println("\nMatriz de Confusion:");
        System.out.println("                    Pred: SEGURO  Pred: RIESGO");
        System.out.printf("Real: SEGURO              %3d         %3d\n", matriz[0][0], matriz[0][1]);
        System.out.printf("Real: RIESGO              %3d         %3d\n", matriz[1][0], matriz[1][1]);
        
        // Evaluacion de turnos
        double[][] turnos_evaluacion = {
            {7, 76, 24, 8},
            {13, 96, 36, 52},
            {10, 88, 30, 25}
        };
        String[] descripciones = {
            "Turno Matutino - Area A",
            "Turno Nocturno - Area B (Alto Riesgo)",
            "Turno Vespertino - Area C"
        };
        
        System.out.println("\n======================================================================");
        System.out.println("EVALUACION DE CONDICIONES POR TURNO");
        System.out.println("======================================================================");
        
        for (int i = 0; i < turnos_evaluacion.length; i++) {
            double[] datos_turno = turnos_evaluacion[i];
            String descripcion = descripciones[i];
            int resultado = nb.predecir(datos_turno);
            Map<Integer, Double> prob = nb.predecirProba(datos_turno);
            
            System.out.println("\n" + descripcion + ":");
            System.out.printf("  Horas trabajo continuo: %.0f hrs\n", datos_turno[0]);
            System.out.printf("  Nivel ruido: %.0f dB\n", datos_turno[1]);
            System.out.printf("  Temperatura: %.0f°C\n", datos_turno[2]);
            System.out.printf("  Dias sin capacitacion: %.0f\n", datos_turno[3]);
            System.out.println("  Evaluacion: " + (resultado == 1 ? "🚨 ALTO RIESGO DE ACCIDENTE" : "✓ CONDICIONES SEGURAS"));
            System.out.printf("  Probabilidad SEGURO: %.1f%%\n", prob.get(0) * 100);
            System.out.printf("  Probabilidad RIESGO: %.1f%%\n", prob.get(1) * 100);
            
            if (resultado == 1) {
                System.out.println("  ⚠️ MEDIDAS CORRECTIVAS INMEDIATAS:");
                if (datos_turno[0] > 10) {
                    System.out.println("     - Reducir horas de trabajo continuo");
                    System.out.println("     - Implementar rotacion de personal");
                }
                if (datos_turno[1] > 90) {
                    System.out.println("     - Proporcionar proteccion auditiva reforzada");
                    System.out.println("     - Evaluar aislamiento acustico");
                }
                if (datos_turno[2] > 32) {
                    System.out.println("     - Mejorar ventilacion/climatizacion");
                    System.out.println("     - Hidratacion obligatoria cada hora");
                }
                if (datos_turno[3] > 30) {
                    System.out.println("     - Capacitacion urgente en seguridad");
                    System.out.println("     - Revision de procedimientos");
                }
            }
        }
        
        System.out.println("\n======================================================================");
        System.out.println("PREGUNTAS DE ANALISIS:");
        System.out.println("======================================================================");
        System.out.println("1. ¿Por que es critico minimizar los falsos negativos en seguridad?");
        System.out.println("2. ¿Cual factor de riesgo requiere intervencion mas urgente?");
        System.out.println("3. ¿Como impacta el costo de un accidente vs. medidas preventivas?");
        System.out.println("4. ¿Que normativas de seguridad (OSHA/STPS) se relacionan con estos factores?");
        System.out.println("5. ¿Como implementarias un sistema de alertas tempranas?");
    }
}