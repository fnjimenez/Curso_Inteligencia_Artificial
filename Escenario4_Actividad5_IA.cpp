// ESCENARIO 4: Deteccion de Fraude en Transacciones
// Archivo: Escenario4_Actividad5_IA.cpp

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
    // Dataset: Deteccion de Fraude
    // Columnas: monto, hora, distancia_ubicacion_usual, intentos_previos, fraude
    vector<vector<double>> datos = {
        {45.50, 14, 2.5, 0, 0}, {1250.00, 3, 850, 1, 1}, {89.99, 18, 5.2, 0, 0},
        {2500.00, 2, 1200, 2, 1}, {32.75, 12, 1.8, 0, 0}, {150.00, 20, 8.5, 0, 0},
        {3200.00, 4, 2500, 3, 1}, {67.80, 16, 3.2, 0, 0}, {1800.00, 1, 950, 1, 1},
        {25.50, 10, 0.5, 0, 0}, {950.00, 5, 650, 1, 1}, {110.25, 19, 12.0, 0, 0},
        {2100.00, 3, 1500, 2, 1}, {78.90, 15, 4.5, 0, 0}, {1500.00, 2, 800, 1, 1},
        {55.00, 13, 6.8, 0, 0}, {2800.00, 4, 1800, 2, 1}, {42.30, 11, 2.1, 0, 0},
        {1100.00, 1, 700, 1, 1}, {95.75, 17, 9.2, 0, 0}, {3500.00, 5, 3000, 3, 1},
        {38.60, 14, 1.5, 0, 0}, {1650.00, 2, 900, 1, 1}, {72.40, 16, 5.8, 0, 0},
        {2200.00, 3, 1300, 2, 1}, {48.90, 12, 3.5, 0, 0}, {1350.00, 4, 750, 1, 1},
        {81.20, 18, 7.5, 0, 0}, {2900.00, 1, 2100, 2, 1}, {59.50, 15, 4.2, 0, 0}
    };
    
    cout << "======================================================================" << endl;
    cout << "ESCENARIO 4: DETECCION DE FRAUDE EN TRANSACCIONES BANCARIAS" << endl;
    cout << "======================================================================" << endl;
    cout << "\nDescripcion del problema:" << endl;
    cout << "Un banco necesita identificar transacciones potencialmente fraudulentas" << endl;
    cout << "en tiempo real para proteger a sus clientes." << endl;
    cout << "\nIndicadores de riesgo analizados:" << endl;
    cout << "- Monto de la transaccion (USD)" << endl;
    cout << "- Hora del dia (0-23)" << endl;
    cout << "- Distancia de ubicacion usual (km)" << endl;
    cout << "- Intentos previos fallidos" << endl;
    
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
    cout << "                    Pred: LEGITIMA  Pred: FRAUDE" << endl;
    cout << "Real: LEGITIMA            " << setw(3) << matriz[0][0] 
         << "           " << setw(3) << matriz[0][1] << endl;
    cout << "Real: FRAUDE              " << setw(3) << matriz[1][0] 
         << "           " << setw(3) << matriz[1][1] << endl;
    
    // Analisis financiero
    int fallas_no_detectadas = matriz[1][0];
    int alertas_falsas = matriz[0][1];
    
    int costo_fraude_no_detectado = 15000;
    int costo_alerta_falsa = 50;
    
    int costo_fallas_perdidas = fallas_no_detectadas * costo_fraude_no_detectado;
    int costo_alertas_falsas_total = alertas_falsas * costo_alerta_falsa;
    int ahorro_fraudes_detectados = matriz[1][1] * costo_fraude_no_detectado;
    
    cout << "\nAnalisis Financiero:" << endl;
    cout << "Costo por fraudes NO detectados: $" << costo_fallas_perdidas << endl;
    cout << "Costo por alertas falsas: $" << costo_alertas_falsas_total << endl;
    cout << "Ahorro por fraudes detectados: $" << ahorro_fraudes_detectados << endl;
    
    // Analisis de transaccion sospechosa
    vector<double> nueva_transaccion = {1800.00, 3, 1100, 1};
    int resultado = nb.predecir(nueva_transaccion);
    map<int, double> prob = nb.predecirProba(nueva_transaccion);
    
    cout << "\n======================================================================" << endl;
    cout << "ANALISIS DE TRANSACCION EN TIEMPO REAL" << endl;
    cout << "======================================================================" << endl;
    cout << "Monto: $" << nueva_transaccion[0] << endl;
    cout << "Hora: " << setfill('0') << setw(2) << (int)nueva_transaccion[1] << ":00 hrs" << setfill(' ') << endl;
    cout << "Distancia de ubicacion usual: " << nueva_transaccion[2] << " km" << endl;
    cout << "Intentos previos fallidos: " << (int)nueva_transaccion[3] << endl;
    cout << "\nVeredicto: " << (resultado == 1 ? "🚨 FRAUDE DETECTADO" : "✓ TRANSACCION LEGITIMA") << endl;
    cout << "Probabilidad LEGITIMA: " << prob[0] * 100 << "%" << endl;
    cout << "Probabilidad FRAUDE: " << prob[1] * 100 << "%" << endl;
    
    if (resultado == 1) {
        cout << "\n⚠️ ACCION RECOMENDADA: Bloquear transaccion y contactar al cliente" << endl;
    } else {
        cout << "\n✓ ACCION: Aprobar transaccion" << endl;
    }
    
    cout << "\n======================================================================" << endl;
    cout << "PREGUNTAS DE ANALISIS:" << endl;
    cout << "======================================================================" << endl;
    cout << "1. ¿Cual es el costo de un falso positivo (bloquear transaccion legitima)?" << endl;
    cout << "2. ¿Cual es el costo de un falso negativo (aprobar fraude)?" << endl;
    cout << "3. ¿Que patron caracteriza mejor las transacciones fraudulentas?" << endl;
    cout << "4. ¿Como balancear seguridad vs. experiencia del cliente?" << endl;
    
    return 0;
}