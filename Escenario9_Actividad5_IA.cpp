// ESCENARIO 9: Mantenimiento Predictivo - Deteccion de Fallas en Maquinaria
// Archivo: Escenario9_Actividad5_IA.cpp

#include <iostream>
#include <vector>
#include <cmath>
#include <map>
#include <algorithm>
#include <random>
#include <iomanip>
#include <string>

using namespace std;

class NaiveBayes {
private:
    map<int, vector<vector<double>>> clases;
    map<int, vector<pair<double, double>>> estadisticas;
    
    double calcularMedia(const vector<double>& datos) {
        double suma = 0;
        for (double val : datos) {
            suma += val;
        }
        return suma / datos.size();
    }
    
    double calcularDesviacion(const vector<double>& datos) {
        double media = calcularMedia(datos);
        double sumaCuadrados = 0;
        for (double val : datos) {
            sumaCuadrados += pow(val - media, 2);
        }
        double varianza = sumaCuadrados / datos.size();
        return varianza > 0 ? sqrt(varianza) : 0.0001;
    }
    
    double probabilidadGaussiana(double x, double media, double desviacion) {
        double exponente = exp(-pow(x - media, 2) / (2 * pow(desviacion, 2)));
        return (1 / (sqrt(2 * M_PI) * desviacion)) * exponente;
    }
    
public:
    void entrenar(const vector<vector<double>>& X, const vector<int>& y) {
        for (size_t i = 0; i < X.size(); i++) {
            clases[y[i]].push_back(X[i]);
        }
        
        for (auto& entry : clases) {
            int clase = entry.first;
            vector<vector<double>>& valores = entry.second;
            vector<pair<double, double>> stats;
            
            int numCaracteristicas = valores[0].size();
            for (int j = 0; j < numCaracteristicas; j++) {
                vector<double> columna;
                for (auto& fila : valores) {
                    columna.push_back(fila[j]);
                }
                double media = calcularMedia(columna);
                double desviacion = calcularDesviacion(columna);
                stats.push_back({media, desviacion});
            }
            estadisticas[clase] = stats;
        }
    }
    
    map<int, double> predecirProba(const vector<double>& x) {
        map<int, double> probabilidades;
        
        for (auto& entry : estadisticas) {
            int clase = entry.first;
            vector<pair<double, double>>& stats = entry.second;
            double prob = 1.0;
            
            for (size_t i = 0; i < x.size(); i++) {
                double media = stats[i].first;
                double desviacion = stats[i].second;
                prob *= probabilidadGaussiana(x[i], media, desviacion);
            }
            probabilidades[clase] = prob;
        }
        
        double total = 0;
        for (auto& p : probabilidades) {
            total += p.second;
        }
        if (total > 0) {
            for (auto& p : probabilidades) {
                probabilidades[p.first] = p.second / total;
            }
        }
        
        return probabilidades;
    }
    
    int predecir(const vector<double>& x) {
        map<int, double> probabilidades = predecirProba(x);
        int mejorClase = -1;
        double mejorProb = -1;
        
        for (auto& entry : probabilidades) {
            if (entry.second > mejorProb) {
                mejorProb = entry.second;
                mejorClase = entry.first;
            }
        }
        
        return mejorClase;
    }
};

int main() {
    // Dataset: Mantenimiento Predictivo
    // Columnas: vibracion_mm/s, temperatura_motor_C, horas_operacion, dias_desde_mant, falla
    vector<vector<double>> datos = {
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
    
    cout << "======================================================================" << endl;
    cout << "ESCENARIO 9: MANTENIMIENTO PREDICTIVO - DETECCION DE FALLAS" << endl;
    cout << "======================================================================" << endl;
    cout << "\nDescripcion del problema:" << endl;
    cout << "Una planta industrial necesita predecir fallas en maquinaria critica" << endl;
    cout << "para implementar mantenimiento preventivo y evitar paros no programados." << endl;
    cout << "\nParametros de condicion monitoreados:" << endl;
    cout << "- Nivel de vibracion (mm/s)" << endl;
    cout << "- Temperatura del motor (°C)" << endl;
    cout << "- Horas de operacion acumuladas" << endl;
    cout << "- Dias desde ultimo mantenimiento" << endl;
    
    // Mezclar datos
    random_device rd;
    mt19937 g(42);
    shuffle(datos.begin(), datos.end(), g);
    
    // Dividir en entrenamiento y prueba
    int split = (int)(datos.size() * 0.7);
    vector<vector<double>> X_train, X_test;
    vector<int> y_train, y_test;
    
    for (int i = 0; i < split; i++) {
        X_train.push_back({datos[i][0], datos[i][1], datos[i][2], datos[i][3]});
        y_train.push_back((int)datos[i][4]);
    }
    
    for (size_t i = split; i < datos.size(); i++) {
        X_test.push_back({datos[i][0], datos[i][1], datos[i][2], datos[i][3]});
        y_test.push_back((int)datos[i][4]);
    }
    
    // Entrenar modelo
    NaiveBayes nb;
    nb.entrenar(X_train, y_train);
    
    // Evaluar
    int correctos = 0;
    int matriz[2][2] = {{0, 0}, {0, 0}};
    
    for (size_t i = 0; i < X_test.size(); i++) {
        int pred = nb.predecir(X_test[i]);
        int real = y_test[i];
        matriz[real][pred]++;
        if (pred == real) {
            correctos++;
        }
    }
    
    double accuracy = (correctos * 100.0) / X_test.size();
    
    cout << "\n======================================================================" << endl;
    cout << "RESULTADOS DEL ANALISIS PREDICTIVO" << endl;
    cout << "======================================================================" << endl;
    cout << fixed << setprecision(2);
    cout << "Accuracy: " << accuracy << "%" << endl;
    
    cout << "\nMatriz de Confusion:" << endl;
    cout << "                    Pred: NORMAL  Pred: FALLA" << endl;
    cout << "Real: NORMAL              " << setw(3) << matriz[0][0] 
         << "         " << setw(3) << matriz[0][1] << endl;
    cout << "Real: FALLA               " << setw(3) << matriz[1][0] 
         << "         " << setw(3) << matriz[1][1] << endl;
    
    // Costos de mantenimiento
    int costo_preventivo = 2500;
    int costo_correctivo = 15000;
    int costo_paro_produccion = 8000;
    
    int fallas_no_detectadas = matriz[1][0];
    int alertas_falsas = matriz[0][1];
    
    int costo_fallas_perdidas = fallas_no_detectadas * (costo_correctivo + costo_paro_produccion * 4);
    int costo_alertas_falsas_total = alertas_falsas * costo_preventivo;
    int ahorro_fallas_detectadas = matriz[1][1] * (costo_correctivo - costo_preventivo);
    
    cout << "\nAnalisis Financiero de Mantenimiento:" << endl;
    cout << "Costo por fallas NO detectadas: $" << costo_fallas_perdidas << endl;
    cout << "Costo por alertas falsas: $" << costo_alertas_falsas_total << endl;
    cout << "Ahorro por fallas detectadas: $" << ahorro_fallas_detectadas << endl;
    cout << "Beneficio neto del modelo: $" << (ahorro_fallas_detectadas - costo_alertas_falsas_total - costo_fallas_perdidas) << endl;
    
    // Monitoreo de equipos
    vector<vector<double>> equipos_monitoreados = {
        {3.0, 68, 1400, 22},
        {8.7, 96, 5850, 188},
        {5.2, 82, 3500, 90}
    };
    vector<string> descripciones = {
        "Maquina CNC-01 - Condicion Normal",
        "Prensa Hidraulica-05 - CRITICO",
        "Torno Industrial-03 - Atencion Requerida"
    };
    
    cout << "\n======================================================================" << endl;
    cout << "MONITOREO DE EQUIPOS EN TIEMPO REAL" << endl;
    cout << "======================================================================" << endl;
    
    for (size_t i = 0; i < equipos_monitoreados.size(); i++) {
        vector<double> datos_equipo = equipos_monitoreados[i];
        string descripcion = descripciones[i];
        int resultado = nb.predecir(datos_equipo);
        map<int, double> prob = nb.predecirProba(datos_equipo);
        
        cout << "\n" << descripcion << ":" << endl;
        cout << "  Vibracion: " << setprecision(1) << datos_equipo[0] << " mm/s" << endl;
        cout << "  Temperatura motor: " << (int)datos_equipo[1] << "°C" << endl;
        cout << "  Horas operacion: " << (int)datos_equipo[2] << " hrs" << endl;
        cout << "  Dias desde mantenimiento: " << (int)datos_equipo[3] << endl;
        cout << "  Estado: " << (resultado == 1 ? "🚨 RIESGO DE FALLA INMINENTE" : "✓ OPERACION NORMAL") << endl;
        cout << "  Confianza NORMAL: " << setprecision(1) << prob[0] * 100 << "%" << endl;
        cout << "  Confianza FALLA: " << prob[1] * 100 << "%" << endl;
        
        if (resultado == 1) {
            cout << "  ⚠️ PLAN DE ACCION INMEDIATO:" << endl;
            cout << "     1. Programar mantenimiento preventivo URGENTE" << endl;
            cout << "     2. Reducir carga de trabajo al 70%" << endl;
            cout << "     3. Incrementar frecuencia de monitoreo" << endl;
            cout << "     4. Preparar equipo de respaldo" << endl;
            cout << "     5. Ordenar refacciones criticas" << endl;
            
            if (datos_equipo[0] > 8.0) {
                cout << "     - Vibracion CRITICA: Revisar rodamientos y balanceo" << endl;
            }
            if (datos_equipo[1] > 95) {
                cout << "     - Temperatura ALTA: Verificar sistema de enfriamiento" << endl;
            }
            if (datos_equipo[2] > 5000) {
                cout << "     - Horas CRITICAS: Considerar overhaul completo" << endl;
            }
            if (datos_equipo[3] > 150) {
                cout << "     - Mantenimiento VENCIDO: Intervencion inmediata" << endl;
            }
        }
    }
    
    cout << "\n======================================================================" << endl;
    cout << "PREGUNTAS DE ANALISIS:" << endl;
    cout << "======================================================================" << endl;
    cout << "1. ¿Cual parametro es el mejor indicador de falla inminente?" << endl;
    cout << "2. ¿Como justificar la inversion en sensores IoT con este ROI?" << endl;
    cout << "3. ¿Que estrategia de mantenimiento es optima: preventivo vs predictivo?" << endl;
    cout << "4. ¿Como integrar este modelo con un sistema CMMS/EAM?" << endl;
    cout << "5. ¿Que impacto tiene en el OEE (Overall Equipment Effectiveness)?" << endl;
    
    return 0;
}