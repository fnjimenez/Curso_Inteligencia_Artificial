// ESCENARIO 8: Recursos Humanos - Prediccion de Ausentismo Laboral
// Archivo: Escenario8_Actividad5_IA.java

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

public class Escenario8_Actividad5_IA {
    public static void main(String[] args) {
        // Dataset: Ausentismo Laboral
        // Columnas: distancia_trabajo_km, antiguedad_meses, hijos, nivel_estres(1-10), ausentismo_alto
        double[][] datos = {
            {5, 48, 0, 3, 0}, {35, 12, 3, 8, 1}, {8, 60, 1, 4, 0},
            {42, 18, 4, 9, 1}, {6, 72, 1, 3, 0}, {28, 24, 2, 7, 1},
            {7, 55, 0, 2, 0}, {38, 15, 3, 8, 1}, {9, 68, 1, 4, 0},
            {45, 20, 4, 10, 1}, {5, 50, 0, 3, 0}, {32, 22, 3, 7, 1},
            {8, 65, 1, 3, 0}, {40, 16, 3, 9, 1}, {6, 58, 0, 2, 0},
            {30, 19, 2, 6, 1}, {7, 62, 1, 4, 0}, {36, 14, 3, 8, 1},
            {9, 70, 1, 3, 0}, {33, 25, 2, 7, 1}, {5, 52, 0, 3, 0},
            {44, 17, 4, 9, 1}, {8, 64, 1, 4, 0}, {29, 21, 2, 6, 1},
            {6, 56, 0, 2, 0}, {41, 13, 3, 10, 1}, {7, 66, 1, 3, 0},
            {34, 23, 3, 8, 1}, {9, 74, 1, 4, 0}, {37, 19, 3, 7, 1}
        };
        
        System.out.println("======================================================================");
        System.out.println("ESCENARIO 8: RECURSOS HUMANOS - PREDICCION DE AUSENTISMO");
        System.out.println("======================================================================");
        System.out.println("\nDescripcion del problema:");
        System.out.println("El departamento de RRHH necesita identificar empleados con alto riesgo");
        System.out.println("de ausentismo para implementar estrategias de retencion y bienestar.");
        System.out.println("\nFactores organizacionales evaluados:");
        System.out.println("- Distancia del hogar al trabajo (km)");
        System.out.println("- Antiguedad en la empresa (meses)");
        System.out.println("- Numero de hijos");
        System.out.println("- Nivel de estres percibido (escala 1-10)");
        System.out.println("\nClasificacion:");
        System.out.println("0 = Ausentismo NORMAL (< 5 dias/año)");
        System.out.println("1 = Ausentismo ALTO (>= 5 dias/año)");
        
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
        System.out.println("                    Pred: NORMAL  Pred: ALTO");
        System.out.printf("Real: NORMAL              %3d        %3d\n", matriz[0][0], matriz[0][1]);
        System.out.printf("Real: ALTO                %3d        %3d\n", matriz[1][0], matriz[1][1]);
        
        // Costo del ausentismo
        int costo_dia_ausente = 350;  // USD por dia de ausencia
        int dias_promedio_alto = 8;   // Promedio de dias de ausencia en categoria "alto"
        int empleados_riesgo = 0;
        for (int i = 0; i < X_test.length; i++) {
            if (nb.predecir(X_test[i]) == 1) {
                empleados_riesgo++;
            }
        }
        
        int costo_anual_estimado = empleados_riesgo * dias_promedio_alto * costo_dia_ausente;
        
        System.out.println("\nImpacto Economico Estimado:");
        System.out.printf("Empleados en riesgo alto: %d\n", empleados_riesgo);
        System.out.printf("Costo anual proyectado por ausentismo: $%,d\n", costo_anual_estimado);
        
        // Perfil de empleados
        double[][] empleados_evaluacion = {
            {7, 65, 1, 3},
            {38, 16, 3, 9},
            {15, 36, 2, 5}
        };
        String[] descripciones = {
            "Empleado A - Perfil estable",
            "Empleado B - Alto riesgo",
            "Empleado C - Riesgo moderado"
        };
        
        System.out.println("\n======================================================================");
        System.out.println("PERFIL DE EMPLEADOS EVALUADOS");
        System.out.println("======================================================================");
        
        for (int i = 0; i < empleados_evaluacion.length; i++) {
            double[] datos_emp = empleados_evaluacion[i];
            String descripcion = descripciones[i];
            int resultado = nb.predecir(datos_emp);
            Map<Integer, Double> prob = nb.predecirProba(datos_emp);
            
            System.out.println("\n" + descripcion + ":");
            System.out.printf("  Distancia al trabajo: %.0f km\n", datos_emp[0]);
            System.out.printf("  Antiguedad: %.0f meses (%.0f años)\n", datos_emp[1], datos_emp[1]/12);
            System.out.printf("  Hijos: %.0f\n", datos_emp[2]);
            System.out.printf("  Nivel de estres: %.0f/10\n", datos_emp[3]);
            System.out.println("  Prediccion: " + (resultado == 1 ? "⚠️ RIESGO ALTO DE AUSENTISMO" : "✓ AUSENTISMO NORMAL"));
            System.out.printf("  Probabilidad NORMAL: %.1f%%\n", prob.get(0) * 100);
            System.out.printf("  Probabilidad ALTO: %.1f%%\n", prob.get(1) * 100);
            
            if (resultado == 1) {
                System.out.println("  📋 ESTRATEGIAS DE RETENCION:");
                if (datos_emp[0] > 25) {
                    System.out.println("     - Considerar home office parcial");
                    System.out.println("     - Apoyo con transporte");
                }
                if (datos_emp[1] < 24) {
                    System.out.println("     - Programa de integracion");
                    System.out.println("     - Mentor asignado");
                }
                if (datos_emp[2] >= 2) {
                    System.out.println("     - Horarios flexibles");
                    System.out.println("     - Guarderia o apoyo familiar");
                }
                if (datos_emp[3] >= 7) {
                    System.out.println("     - Programa de manejo de estres");
                    System.out.println("     - Evaluacion ergonomica");
                    System.out.println("     - Sesiones con psicologo organizacional");
                }
            }
        }
        
        System.out.println("\n======================================================================");
        System.out.println("PREGUNTAS DE ANALISIS:");
        System.out.println("======================================================================");
        System.out.println("1. ¿Cual factor tiene mayor impacto en el ausentismo?");
        System.out.println("2. ¿Que ROI tendrian las intervenciones vs. costo de ausentismo?");
        System.out.println("3. ¿Como afecta el ausentismo a la productividad del equipo?");
        System.out.println("4. ¿Que politicas de balance vida-trabajo recomendarias?");
    }
}