// ESCENARIO 3: Diagnostico Medico - Deteccion de Diabetes
// Archivo: Escenario3_Actividad5_IA.cpp

#include <iostream>
#include <vector>
#include <cmath>
#include <map>
#include <algorithm>
#include <random>
#include <iomanip>

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
    // Dataset: Diagnostico de Diabetes
    // Columnas: glucosa, presion_arterial, IMC, edad, diabetes
    vector<vector<double>> datos = {
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
    
    cout << "======================================================================" << endl;
    cout << "ESCENARIO 3: DIAGNOSTICO MEDICO - DETECCION DE DIABETES" << endl;
    cout << "======================================================================" << endl;
    cout << "\nDescripcion del problema:" << endl;
    cout << "Un centro medico necesita un sistema de apoyo para identificar pacientes" << endl;
    cout << "con alto riesgo de diabetes tipo 2 basado en indicadores clinicos." << endl;
    cout << "\nIndicadores medicos evaluados:" << endl;
    cout << "- Nivel de glucosa en sangre (mg/dL)" << endl;
    cout << "- Presion arterial diastolica (mm Hg)" << endl;
    cout << "- Indice de Masa Corporal (IMC)" << endl;
    cout << "- Edad (años)" << endl;
    
    // Mezclar datos
    random_device rd;
    mt19937 g(42);
    shuffle(datos.begin(), datos.end(), g);
    
    // Dividir en entrenamiento y prueba (75%-25%)
    int split = (int)(datos.size() * 0.75);
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
    
    // Metricas medicas
    int VP = matriz[1][1];
    int FN = matriz[1][0];
    int VN = matriz[0][0];
    int FP = matriz[0][1];
    
    double sensibilidad = (VP + FN) > 0 ? (VP * 100.0) / (VP + FN) : 0;
    double especificidad = (VN + FP) > 0 ? (VN * 100.0) / (VN + FP) : 0;
    
    cout << "\n======================================================================" << endl;
    cout << "RESULTADOS DEL ANALISIS CLINICO" << endl;
    cout << "======================================================================" << endl;
    cout << fixed << setprecision(2);
    cout << "Accuracy: " << accuracy << "%" << endl;
    cout << "Sensibilidad (detecta enfermos): " << sensibilidad << "%" << endl;
    cout << "Especificidad (detecta sanos): " << especificidad << "%" << endl;
    
    cout << "\nMatriz de Confusion:" << endl;
    cout << "                    Pred: SANO  Pred: DIABETES" << endl;
    cout << "Real: SANO               " << setw(3) << matriz[0][0] 
         << "         " << setw(3) << matriz[0][1] << endl;
    cout << "Real: DIABETES           " << setw(3) << matriz[1][0] 
         << "         " << setw(3) << matriz[1][1] << endl;
    
    // Diagnostico de nuevo paciente
    vector<double> nuevo_paciente = {155, 78, 34.2, 45};
    int resultado = nb.predecir(nuevo_paciente);
    map<int, double> prob = nb.predecirProba(nuevo_paciente);
    
    cout << "\n======================================================================" << endl;
    cout << "EVALUACION DE NUEVO PACIENTE" << endl;
    cout << "======================================================================" << endl;
    cout << "Glucosa: " << (int)nuevo_paciente[0] << " mg/dL" << endl;
    cout << "Presion arterial: " << (int)nuevo_paciente[1] << " mm Hg" << endl;
    cout << "IMC: " << nuevo_paciente[2] << endl;
    cout << "Edad: " << (int)nuevo_paciente[3] << " años" << endl;
    cout << "\nDiagnostico: " << (resultado == 1 ? "RIESGO DE DIABETES ⚠️" : "PERFIL NORMAL ✓") << endl;
    cout << "Confianza diagnostico SANO: " << prob[0] * 100 << "%" << endl;
    cout << "Confianza diagnostico DIABETES: " << prob[1] * 100 << "%" << endl;
    
    cout << "\n======================================================================" << endl;
    cout << "PREGUNTAS DE ANALISIS MEDICO:" << endl;
    cout << "======================================================================" << endl;
    cout << "1. ¿Es mas grave un falso negativo o un falso positivo en este contexto?" << endl;
    cout << "2. ¿Por que la sensibilidad es critica en diagnosticos medicos?" << endl;
    cout << "3. ¿Que cambios en el estilo de vida recomendarias al paciente evaluado?" << endl;
    cout << "4. ¿Como mejorar la confiabilidad del modelo para uso clinico real?" << endl;
    
    return 0;
}