// ESCENARIO 7: Seguridad Industrial - Prediccion de Accidentes
// Archivo: Escenario7_Actividad5_IA.cpp

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
    // Dataset: Accidentes en Fabrica
    // Columnas: hrs_trabajo_continuo, nivel_ruido_db, temperatura_C, dias_sin_capacitacion, accidente
    vector<vector<double>> datos = {
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
    
    cout << "======================================================================" << endl;
    cout << "ESCENARIO 7: SEGURIDAD INDUSTRIAL - PREDICCION DE ACCIDENTES" << endl;
    cout << "======================================================================" << endl;
    cout << "\nDescripcion del problema:" << endl;
    cout << "Una planta industrial necesita identificar condiciones de alto riesgo" << endl;
    cout << "para prevenir accidentes laborales y proteger a sus trabajadores." << endl;
    cout << "\nFactores de riesgo evaluados:" << endl;
    cout << "- Horas de trabajo continuo" << endl;
    cout << "- Nivel de ruido ambiental (dB)" << endl;
    cout << "- Temperatura en area de trabajo (°C)" << endl;
    cout << "- Dias desde ultima capacitacion en seguridad" << endl;
    
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
    
    // Metricas de seguridad
    int VP = matriz[1][1];
    int FN = matriz[1][0];
    int VN = matriz[0][0];
    int FP = matriz[0][1];
    
    double sensibilidad = (VP + FN) > 0 ? (VP * 100.0) / (VP + FN) : 0;
    
    cout << "\n======================================================================" << endl;
    cout << "RESULTADOS DEL ANALISIS DE SEGURIDAD" << endl;
    cout << "======================================================================" << endl;
    cout << fixed << setprecision(2);
    cout << "Accuracy: " << accuracy << "%" << endl;
    cout << "Sensibilidad (deteccion de riesgos): " << sensibilidad << "%" << endl;
    cout << "⚠️ CRITICO - Riesgos NO detectados: " << FN << endl;
    
    cout << "\nMatriz de Confusion:" << endl;
    cout << "                    Pred: SEGURO  Pred: RIESGO" << endl;
    cout << "Real: SEGURO              " << setw(3) << matriz[0][0] 
         << "         " << setw(3) << matriz[0][1] << endl;
    cout << "Real: RIESGO              " << setw(3) << matriz[1][0] 
         << "         " << setw(3) << matriz[1][1] << endl;
    
    // Evaluacion de turnos
    vector<vector<double>> turnos_evaluacion = {
        {7, 76, 24, 8},
        {13, 96, 36, 52},
        {10, 88, 30, 25}
    };
    vector<string> descripciones = {
        "Turno Matutino - Area A",
        "Turno Nocturno - Area B (Alto Riesgo)",
        "Turno Vespertino - Area C"
    };
    
    cout << "\n======================================================================" << endl;
    cout << "EVALUACION DE CONDICIONES POR TURNO" << endl;
    cout << "======================================================================" << endl;
    
    for (size_t i = 0; i < turnos_evaluacion.size(); i++) {
        vector<double> datos_turno = turnos_evaluacion[i];
        string descripcion = descripciones[i];
        int resultado = nb.predecir(datos_turno);
        map<int, double> prob = nb.predecirProba(datos_turno);
        
        cout << "\n" << descripcion << ":" << endl;
        cout << "  Horas trabajo continuo: " << (int)datos_turno[0] << " hrs" << endl;
        cout << "  Nivel ruido: " << (int)datos_turno[1] << " dB" << endl;
        cout << "  Temperatura: " << (int)datos_turno[2] << "°C" << endl;
        cout << "  Dias sin capacitacion: " << (int)datos_turno[3] << endl;
        cout << "  Evaluacion: " << (resultado == 1 ? "🚨 ALTO RIESGO DE ACCIDENTE" : "✓ CONDICIONES SEGURAS") << endl;
        cout << "  Probabilidad SEGURO: " << setprecision(1) << prob[0] * 100 << "%" << endl;
        cout << "  Probabilidad RIESGO: " << prob[1] * 100 << "%" << endl;
        
        if (resultado == 1) {
            cout << "  ⚠️ MEDIDAS CORRECTIVAS INMEDIATAS:" << endl;
            if (datos_turno[0] > 10) {
                cout << "     - Reducir horas de trabajo continuo" << endl;
                cout << "     - Implementar rotacion de personal" << endl;
            }
            if (datos_turno[1] > 90) {
                cout << "     - Proporcionar proteccion auditiva reforzada" << endl;
                cout << "     - Evaluar aislamiento acustico" << endl;
            }
            if (datos_turno[2] > 32) {
                cout << "     - Mejorar ventilacion/climatizacion" << endl;
                cout << "     - Hidratacion obligatoria cada hora" << endl;
            }
            if (datos_turno[3] > 30) {
                cout << "     - Capacitacion urgente en seguridad" << endl;
                cout << "     - Revision de procedimientos" << endl;
            }
        }
    }
    
    cout << "\n======================================================================" << endl;
    cout << "PREGUNTAS DE ANALISIS:" << endl;
    cout << "======================================================================" << endl;
    cout << "1. ¿Por que es critico minimizar los falsos negativos en seguridad?" << endl;
    cout << "2. ¿Cual factor de riesgo requiere intervencion mas urgente?" << endl;
    cout << "3. ¿Como impacta el costo de un accidente vs. medidas preventivas?" << endl;
    cout << "4. ¿Que normativas de seguridad (OSHA/STPS) se relacionan con estos factores?" << endl;
    cout << "5. ¿Como implementarias un sistema de alertas tempranas?" << endl;
    
    return 0;
}