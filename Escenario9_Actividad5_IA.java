// ESCENARIO 9: Mantenimiento Predictivo - Deteccion de Fallas en Maquinaria
// Archivo: Escenario9_Actividad5_IA.java

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

public class Escenario9_Actividad5_IA {
    public static void main(String[] args) {
        // Dataset: Mantenimiento Predictivo
        // Columnas: vibracion_mm/s, temperatura_motor_C, horas_operacion, dias_desde_mant, falla
        double[][] datos = {
            {2.5, 65, 1200, 15, 0}, {8.5, 95, 5800, 180, 1}, {3.2, 68, 1500, 20, 0},
            {9.2, 98, 6200, 210, 1}, {2.8, 66, 1300, 18, 0}, {7.8, 92, 5200, 150, 1},
            {3.0, 67, 1400, 22, 0}, {8.8, 96, 5900, 195, 1}, {2.6, 65, 1250, 16, 0},
            {9.5, 100, 6500, 220, 1}, {2.9, 68, 1350, 19, 0}, {8.2, 94, 5500, 165, 1},
            {3.1, 69, 1450, 21, 0}, {9.0, 97, 6100, 200, 1}, {2.7, 66, 1280, 17, 0},
            {7.5, 90, 5000, 140, 1}, {3.3, 70, 1550, 23, 0}, {8.6, 95, 5700, 185, 1},
            {2.8, 67, 1320, 20, 0}, {9.3, 99, 6300, 215, 1}, {3.0, 68, 1400, 22, 0},
            {8.0, 93, 5400, 160, 1}, {2.6, 65, 1260, 15, 0}, {8.9, 96, 6000, 190, 1},
            {3.2, 69, 1480, 24, 0}, {7.6, 91, 5100, 145, 1}, {2.9, 67, 1380, 21, 0},
            {9.1, 98, 6200, 205, 1}, {3.1, 68, 1420, 22, 0}, {8.4, 94, 5600, 175, 1}
        };
        
        System.out.println("======================================================================");
        System.out.println("ESCENARIO 9: MANTENIMIENTO PREDICTIVO - DETECCION DE FALLAS");
        System.out.println("======================================================================");
        System.out.println("\nDescripcion del problema:");
        System.out.println("Una planta industrial necesita predecir fallas en maquinaria critica");
        System.out.println("para implementar mantenimiento preventivo y evitar paros no programados.");
        System.out.println("\nParametros de condicion monitoreados:");
        System.out.println("- Nivel de vibracion (mm/s)");
        System.out.println("- Temperatura del motor (°C)");
        System.out.println("- Horas de operacion acumuladas");
        System.out.println("- Dias desde ultimo mantenimiento");
        
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
        System.out.println("RESULTADOS DEL ANALISIS PREDICTIVO");
        System.out.println("======================================================================");
        System.out.printf("Accuracy: %.2f%%\n", accuracy);
        
        System.out.println("\nMatriz de Confusion:");
        System.out.println("                    Pred: NORMAL  Pred: FALLA");
        System.out.printf("Real: NORMAL              %3d         %3d\n", matriz[0][0], matriz[0][1]);
        System.out.printf("Real: FALLA               %3d         %3d\n", matriz[1][0], matriz[1][1]);
        
        // Costos de mantenimiento
        int costo_preventivo = 2500;      // USD - mantenimiento programado
        int costo_correctivo = 15000;     // USD - reparacion por falla
        int costo_paro_produccion = 8000; // USD por hora de paro
        
        // Analisis financiero
        int fallas_no_detectadas = matriz[1][0];
        int alertas_falsas = matriz[0][1];
        
        int costo_fallas_perdidas = fallas_no_detectadas * (costo_correctivo + costo_paro_produccion * 4);
        int costo_alertas_falsas = alertas_falsas * costo_preventivo;
        int ahorro_fallas_detectadas = matriz[1][1] * (costo_correctivo - costo_preventivo);
        
        System.out.println("\nAnalisis Financiero de Mantenimiento:");
        System.out.printf("Costo por fallas NO detectadas: $%,d\n", costo_fallas_perdidas);
        System.out.printf("Costo por alertas falsas: $%,d\n", costo_alertas_falsas);
        System.out.printf("Ahorro por fallas detectadas: $%,d\n", ahorro_fallas_detectadas);
        System.out.printf("Beneficio neto del modelo: $%,d\n", 
                         ahorro_fallas_detectadas - costo_alertas_falsas - costo_fallas_perdidas);
        
        // Monitoreo de equipos
        double[][] equipos_monitoreados = {
            {3.0, 68, 1400, 22},
            {8.7, 96, 5850, 188},
            {5.2, 82, 3500, 90}
        };
        String[] descripciones = {
            "Maquina CNC-01 - Condicion Normal",
            "Prensa Hidraulica-05 - CRITICO",
            "Torno Industrial-03 - Atencion Requerida"
        };
        
        System.out.println("\n======================================================================");
        System.out.println("MONITOREO DE EQUIPOS EN TIEMPO REAL");
        System.out.println("======================================================================");
        
        for (int i = 0; i < equipos_monitoreados.length; i++) {
            double[] datos_equipo = equipos_monitoreados[i];
            String descripcion = descripciones[i];
            int resultado = nb.predecir(datos_equipo);
            Map<Integer, Double> prob = nb.predecirProba(datos_equipo);
            
            System.out.println("\n" + descripcion + ":");
            System.out.printf("  Vibracion: %.1f mm/s\n", datos_equipo[0]);
            System.out.printf("  Temperatura motor: %.0f°C\n", datos_equipo[1]);
            System.out.printf("  Horas operacion: %,.0f hrs\n", datos_equipo[2]);
            System.out.printf("  Dias desde mantenimiento: %.0f\n", datos_equipo[3]);
            System.out.println("  Estado: " + (resultado == 1 ? "🚨 RIESGO DE FALLA INMINENTE" : "✓ OPERACION NORMAL"));
            System.out.printf("  Confianza NORMAL: %.1f%%\n", prob.get(0) * 100);
            System.out.printf("  Confianza FALLA: %.1f%%\n", prob.get(1) * 100);
            
            if (resultado == 1) {
                System.out.println("  ⚠️ PLAN DE ACCION INMEDIATO:");
                System.out.println("     1. Programar mantenimiento preventivo URGENTE");
                System.out.println("     2. Reducir carga de trabajo al 70%");
                System.out.println("     3. Incrementar frecuencia de monitoreo");
                System.out.println("     4. Preparar equipo de respaldo");
                System.out.println("     5. Ordenar refacciones criticas");
                
                if (datos_equipo[0] > 8.0) {
                    System.out.println("     - Vibracion CRITICA: Revisar rodamientos y balanceo");
                }
                if (datos_equipo[1] > 95) {
                    System.out.println("     - Temperatura ALTA: Verificar sistema de enfriamiento");
                }
                if (datos_equipo[2] > 5000) {
                    System.out.println("     - Horas CRITICAS: Considerar overhaul completo");
                }
                if (datos_equipo[3] > 150) {
                    System.out.println("     - Mantenimiento VENCIDO: Intervencion inmediata");
                }
            }
        }
        
        System.out.println("\n======================================================================");
        System.out.println("PREGUNTAS DE ANALISIS:");
        System.out.println("======================================================================");
        System.out.println("1. ¿Cual parametro es el mejor indicador de falla inminente?");
        System.out.println("2. ¿Como justificar la inversion en sensores IoT con este ROI?");
        System.out.println("3. ¿Que estrategia de mantenimiento es optima: preventivo vs predictivo?");
        System.out.println("4. ¿Como integrar este modelo con un sistema CMMS/EAM?");
        System.out.println("5. ¿Que impacto tiene en el OEE (Overall Equipment Effectiveness)?");
    }
}