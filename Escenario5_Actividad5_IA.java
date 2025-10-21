// ESCENARIO 5: Prediccion de Rendimiento Academico
// Archivo: Escenario5_Actividad5_IA.java

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

public class Escenario5_Actividad5_IA {
    public static void main(String[] args) {
        // Dataset: Rendimiento Academico
        // Columnas: hrs_estudio_semanal, asistencia%, participacion%, tareas_entregadas%, aprobado
        // Clase: 0=Reprobado, 1=Aprobado, 2=Excelente
        double[][] datos = {
            {15, 95, 80, 100, 2}, {8, 75, 50, 70, 1}, {20, 98, 90, 100, 2},
            {5, 60, 30, 50, 0}, {12, 85, 70, 85, 1}, {18, 92, 85, 95, 2},
            {6, 65, 40, 55, 0}, {10, 80, 60, 75, 1}, {22, 100, 95, 100, 2},
            {4, 55, 25, 45, 0}, {14, 88, 75, 90, 1}, {19, 95, 88, 98, 2},
            {7, 70, 45, 60, 0}, {11, 82, 65, 80, 1}, {16, 90, 82, 92, 2},
            {5, 58, 28, 48, 0}, {9, 78, 55, 72, 1}, {21, 97, 92, 100, 2},
            {6, 62, 35, 52, 0}, {13, 86, 72, 88, 1}, {17, 93, 86, 96, 2},
            {4, 52, 22, 42, 0}, {10, 79, 58, 74, 1}, {20, 96, 90, 98, 2},
            {7, 68, 42, 58, 0}, {12, 84, 68, 82, 1}, {18, 94, 87, 94, 2},
            {5, 56, 26, 46, 0}, {11, 81, 62, 78, 1}, {19, 95, 89, 97, 2}
        };
        
        System.out.println("======================================================================");
        System.out.println("ESCENARIO 5: PREDICCION DE RENDIMIENTO ACADEMICO");
        System.out.println("======================================================================");
        System.out.println("\nDescripcion del problema:");
        System.out.println("Una universidad quiere identificar tempranamente estudiantes en riesgo");
        System.out.println("de reprobar para ofrecerles apoyo academico oportuno.");
        System.out.println("\nIndicadores academicos evaluados:");
        System.out.println("- Horas de estudio semanal");
        System.out.println("- Porcentaje de asistencia");
        System.out.println("- Participacion en clase (%)");
        System.out.println("- Tareas entregadas a tiempo (%)");
        System.out.println("\nClasificacion de estudiantes:");
        System.out.println("0 = REPROBADO (< 60)");
        System.out.println("1 = APROBADO (60-89)");
        System.out.println("2 = EXCELENTE (90-100)");
        
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
        int[][] matriz = new int[3][3];
        
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
        
        System.out.println("\nMatriz de Confusion (3 clases):");
        System.out.println("                  Pred:REP  Pred:APR  Pred:EXC");
        System.out.printf("Real:REPROBADO       %3d       %3d       %3d\n", matriz[0][0], matriz[0][1], matriz[0][2]);
        System.out.printf("Real:APROBADO        %3d       %3d       %3d\n", matriz[1][0], matriz[1][1], matriz[1][2]);
        System.out.printf("Real:EXCELENTE       %3d       %3d       %3d\n", matriz[2][0], matriz[2][1], matriz[2][2]);
        
        // Evaluacion de estudiantes
        double[][] estudiantes_prueba = {
            {7, 68, 45, 60},
            {12, 85, 72, 88},
            {19, 96, 90, 98}
        };
        String[] nombres = {
            "Estudiante A - En riesgo",
            "Estudiante B - Promedio",
            "Estudiante C - Destacado"
        };
        
        Map<Integer, String> clases_nombres = new HashMap<>();
        clases_nombres.put(0, "REPROBADO ❌");
        clases_nombres.put(1, "APROBADO ✓");
        clases_nombres.put(2, "EXCELENTE ⭐");
        
        System.out.println("\n======================================================================");
        System.out.println("EVALUACION DE ESTUDIANTES");
        System.out.println("======================================================================");
        
        for (int i = 0; i < estudiantes_prueba.length; i++) {
            double[] datos_est = estudiantes_prueba[i];
            String nombre = nombres[i];
            int resultado = nb.predecir(datos_est);
            Map<Integer, Double> prob = nb.predecirProba(datos_est);
            
            System.out.println("\n" + nombre + ":");
            System.out.printf("  Hrs estudio/semana: %.0f\n", datos_est[0]);
            System.out.printf("  Asistencia: %.0f%%\n", datos_est[1]);
            System.out.printf("  Participacion: %.0f%%\n", datos_est[2]);
            System.out.printf("  Tareas: %.0f%%\n", datos_est[3]);
            System.out.println("  Prediccion: " + clases_nombres.get(resultado));
            System.out.println("  Probabilidades:");
            System.out.printf("    Reprobar: %.1f%%\n", prob.getOrDefault(0, 0.0) * 100);
            System.out.printf("    Aprobar: %.1f%%\n", prob.getOrDefault(1, 0.0) * 100);
            System.out.printf("    Excelencia: %.1f%%\n", prob.getOrDefault(2, 0.0) * 100);
            
            if (resultado == 0) {
                System.out.println("  ⚠️ RECOMENDACION: Asignar tutor y plan de mejora urgente");
            } else if (resultado == 1) {
                System.out.println("  💡 RECOMENDACION: Fomentar habitos de estudio");
            } else {
                System.out.println("  🎓 RECOMENDACION: Considerar para programa de becas");
            }
        }
        
        System.out.println("\n======================================================================");
        System.out.println("PREGUNTAS DE ANALISIS:");
        System.out.println("======================================================================");
        System.out.println("1. ¿Cual indicador es mas determinante para el exito academico?");
        System.out.println("2. ¿Que estrategias de intervencion propondrias para cada categoria?");
        System.out.println("3. ¿Como afecta la deteccion temprana al rendimiento final?");
        System.out.println("4. ¿Es etico usar IA para predecir el rendimiento estudiantil?");
        System.out.println("5. ¿Que limitaciones tiene este modelo de clasificacion?");
    }
}