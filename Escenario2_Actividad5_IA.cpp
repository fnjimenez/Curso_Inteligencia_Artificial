// ESCENARIO 2: Prediccion de Abandono de Clientes (Churn)
// Archivo: Escenario2_Actividad5_IA.cpp

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
    map<int, vector<pair<double, double>>> estadisticas; // media, desviacion
    
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
        // Agrupar datos por clase
        for (size_t i = 0; i < X.size(); i++) {
            clases[y[i]].push_back(X[i]);
        }
        
        // Calcular estadisticas por clase
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
        
        // Normalizar probabilidades
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
    // Dataset: Abandono de Clientes
    // Columnas: meses_servicio, llamadas_soporte, gasto_mensual, minutos_uso, abandono
    vector<vector<double>> datos = {
        {24, 2, 65.5, 450, 0}, {12, 5, 45.2, 320, 1}, {36, 1, 78.3, 520, 0},
        {6, 8, 35.0, 180, 1}, {48, 0, 85.6, 600, 0}, {18, 4, 52.1, 280, 1},
        {30, 1, 72.4, 480, 0}, {9, 6, 38.5, 210, 1}, {42, 2, 80.2, 550, 0},
        {15, 7, 42.3, 250, 1}, {27, 3, 68.7, 430, 0}, {8, 9, 33.8, 160, 1},
        {33, 1, 75.5, 500, 0}, {14, 5, 48.9, 290, 1}, {40, 2, 82.1, 570, 0},
        {11, 6, 40.2, 230, 1}, {25, 2, 66.8, 460, 0}, {7, 10, 32.5, 150, 1},
        {35, 1, 77.9, 530, 0}, {13, 4, 46.7, 270, 1}, {29, 2, 70.3, 490, 0},
        {10, 7, 37.6, 200, 1}, {44, 1, 83.4, 580, 0}, {16, 5, 50.5, 300, 1},
        {31, 3, 73.2, 470, 0}, {19, 4, 54.8, 310, 1}, {38, 1, 79.6, 540, 0},
        {17, 6, 51.3, 285, 1}, {26, 2, 67.9, 445, 0}, {20, 3, 56.4, 330, 1}
    };
    
    cout << "======================================================================" << endl;
    cout << "ESCENARIO 2: PREDICCION DE ABANDONO DE CLIENTES (CHURN)" << endl;
    cout << "======================================================================" << endl;
    cout << "\nDescripcion del problema:" << endl;
    cout << "Una empresa de telecomunicaciones quiere predecir que clientes tienen" << endl;
    cout << "mayor riesgo de cancelar su servicio (abandono/churn)." << endl;
    cout << "\nCaracteristicas analizadas:" << endl;
    cout << "- Meses de servicio con la empresa" << endl;
    cout << "- Numero de llamadas al soporte tecnico" << endl;
    cout << "- Gasto mensual promedio (USD)" << endl;
    cout << "- Minutos de uso mensual" << endl;
    
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
    cout << "RESULTADOS DEL ANALISIS" << endl;
    cout << "======================================================================" << endl;
    cout << fixed << setprecision(2);
    cout << "Accuracy: " << accuracy << "%" << endl;
    
    cout << "\nMatriz de Confusion:" << endl;
    cout << "                    Pred: SE QUEDA  Pred: ABANDONA" << endl;
    cout << "Real: SE QUEDA            " << setw(3) << matriz[0][0] 
         << "            " << setw(3) << matriz[0][1] << endl;
    cout << "Real: ABANDONA            " << setw(3) << matriz[1][0] 
         << "            " << setw(3) << matriz[1][1] << endl;
    
    // Analisis de cliente nuevo
    vector<double> nuevo_cliente = {22, 4, 58.5, 350};
    int resultado = nb.predecir(nuevo_cliente);
    map<int, double> prob = nb.predecirProba(nuevo_cliente);
    
    cout << "\n======================================================================" << endl;
    cout << "ANALISIS DE CLIENTE NUEVO" << endl;
    cout << "======================================================================" << endl;
    cout << "Meses de servicio: " << (int)nuevo_cliente[0] << endl;
    cout << "Llamadas a soporte: " << (int)nuevo_cliente[1] << endl;
    cout << "Gasto mensual: $" << nuevo_cliente[2] << endl;
    cout << "Minutos de uso: " << (int)nuevo_cliente[3] << endl;
    cout << "\nPrediccion: " << (resultado == 1 ? "RIESGO DE ABANDONO ⚠️" : "SE MANTIENE ✓") << endl;
    cout << "Probabilidad de quedarse: " << prob[0] * 100 << "%" << endl;
    cout << "Probabilidad de abandonar: " << prob[1] * 100 << "%" << endl;
    
    cout << "\n======================================================================" << endl;
    cout << "PREGUNTAS DE ANALISIS:" << endl;
    cout << "======================================================================" << endl;
    cout << "1. ¿Cual caracteristica es mas indicativa de abandono?" << endl;
    cout << "2. ¿Que estrategias de retencion recomendarias para clientes en riesgo?" << endl;
    cout << "3. ¿Cual es el costo de un falso negativo (predecir que se queda pero abandona)?" << endl;
    cout << "4. ¿Como afecta el numero de llamadas a soporte en la decision del cliente?" << endl;
    
    return 0;
}