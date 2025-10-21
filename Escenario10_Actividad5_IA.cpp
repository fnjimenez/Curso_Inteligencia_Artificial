// ESCENARIO 10: Control de Calidad Avanzado - Clasificación Premium/Aceptable/Defectuoso
// Archivo: Escenario10_Actividad5_IA.cpp

#include <iostream>
#include <vector>
#include <map>
#include <cmath>
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
        return (1.0 / (sqrt(2 * M_PI) * desviacion)) * exponente;
    }
    
public:
    void entrenar(const vector<vector<double>>& X, const vector<int>& y) {
        // Agrupar datos por clase
        for (size_t i = 0; i < X.size(); i++) {
            int clase = y[i];
            clases[clase].push_back(X[i]);
        }
        
        // Calcular estadisticas por clase
        for (auto& par : clases) {
            int clase = par.first;
            vector<vector<double>>& valores = par.second;
            
            size_t numCaracteristicas = valores[0].size();
            for (size_t j = 0; j < numCaracteristicas; j++) {
                vector<double> columna;
                for (const auto& fila : valores) {
                    columna.push_back(fila[j]);
                }
                
                double media = calcularMedia(columna);
                double desviacion = calcularDesviacion(columna);
                estadisticas[clase].push_back({media, desviacion});
            }
        }
    }
    
    map<int, double> predecirProba(const vector<double>& x) {
        map<int, double> probabilidades;
        
        for (auto& par : estadisticas) {
            int clase = par.first;
            vector<pair<double, double>>& stats = par.second;
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
        for (auto& par : probabilidades) {
            total += par.second;
        }
        if (total > 0) {
            for (auto& par : probabilidades) {
                par.second /= total;
            }
        }
        
        return probabilidades;
    }
    
    int predecir(const vector<double>& x) {
        map<int, double> probabilidades = predecirProba(x);
        int mejorClase = -1;
        double mejorProb = -1;
        
        for (auto& par : probabilidades) {
            if (par.second > mejorProb) {
                mejorProb = par.second;
                mejorClase = par.first;
            }
        }
        
        return mejorClase;
    }
};

vector<vector<double>> generarDataset(int n, mt19937& gen) {
    vector<vector<double>> datos(n, vector<double>(5));
    normal_distribution<double> distLargo(99.5, 0.5);
    normal_distribution<double> distAncho(49.8, 0.3);
    normal_distribution<double> distGrosor(9.95, 0.15);
    normal_distribution<double> distDureza(7800, 50);
    normal_distribution<double> distRugosidad(0.1, 0.03);
    
    for (int i = 0; i < n; i++) {
        datos[i][0] = distLargo(gen);      // Largo
        datos[i][1] = distAncho(gen);      // Ancho
        datos[i][2] = distGrosor(gen);     // Grosor
        datos[i][3] = distDureza(gen);     // Dureza
        datos[i][4] = distRugosidad(gen);  // Rugosidad
    }
    
    return datos;
}

vector<int> generarEtiquetas(const vector<vector<double>>& X) {
    vector<int> etiquetas(X.size());
    
    for (size_t i = 0; i < X.size(); i++) {
        double largo = X[i][0];
        double ancho = X[i][1];
        double grosor = X[i][2];
        double dureza = X[i][3];
        double rugosidad = X[i][4];
        
        // Criterios de clasificación
        bool dimensionesExcelentes = 
            abs(largo - 100.0) < 0.2 &&
            abs(ancho - 50.0) < 0.15 &&
            abs(grosor - 10.0) < 0.08;
        
        bool propiedadesExcelentes = dureza > 7850 && rugosidad < 0.08;
        
        bool dimensionesAceptables =
            abs(largo - 100.0) < 0.5 &&
            abs(ancho - 50.0) < 0.3 &&
            abs(grosor - 10.0) < 0.15;
        
        bool propiedadesAceptables = dureza > 7750 && rugosidad < 0.13;
        
        if (dimensionesExcelentes && propiedadesExcelentes) {
            etiquetas[i] = 2; // Premium
        } else if (dimensionesAceptables && propiedadesAceptables) {
            etiquetas[i] = 1; // Aceptable
        } else {
            etiquetas[i] = 0; // Defectuoso
        }
    }
    
    return etiquetas;
}

void dividirDataset(const vector<vector<double>>& X, const vector<int>& y,
                   vector<vector<double>>& X_train, vector<int>& y_train,
                   vector<vector<double>>& X_test, vector<int>& y_test,
                   double testSize, mt19937& gen) {
    int n = X.size();
    int nTest = (int)(n * testSize);
    int nTrain = n - nTest;
    
    vector<int> indices(n);
    for (int i = 0; i < n; i++) {
        indices[i] = i;
    }
    shuffle(indices.begin(), indices.end(), gen);
    
    X_train.resize(nTrain);
    y_train.resize(nTrain);
    X_test.resize(nTest);
    y_test.resize(nTest);
    
    for (int i = 0; i < nTrain; i++) {
        int idx = indices[i];
        X_train[i] = X[idx];
        y_train[i] = y[idx];
    }
    
    for (int i = 0; i < nTest; i++) {
        int idx = indices[i + nTrain];
        X_test[i] = X[idx];
        y_test[i] = y[idx];
    }
}

double calcularAccuracy(const vector<int>& y_true, const vector<int>& y_pred) {
    int correctos = 0;
    for (size_t i = 0; i < y_true.size(); i++) {
        if (y_true[i] == y_pred[i]) {
            correctos++;
        }
    }
    return (double)correctos / y_true.size();
}

void imprimirMatrizConfusion(const vector<int>& y_true, const vector<int>& y_pred, int numClases) {
    vector<vector<int>> matriz(numClases, vector<int>(numClases, 0));
    
    for (size_t i = 0; i < y_true.size(); i++) {
        matriz[y_true[i]][y_pred[i]]++;
    }
    
    cout << "\n=== MATRIZ DE CONFUSION ===" << endl;
    cout << "            ";
    for (int i = 0; i < numClases; i++) {
        cout << "Pred_" << i << "  ";
    }
    cout << endl;
    
    string nombres[] = {"Defectuoso", "Aceptable ", "Premium   "};
    for (int i = 0; i < numClases; i++) {
        cout << "Real_" << i << " (" << nombres[i] << "): ";
        for (int j = 0; j < numClases; j++) {
            cout << setw(6) << matriz[i][j] << "  ";
        }
        cout << endl;
    }
}

map<int, map<string, double>> calcularMetricas(const vector<int>& y_true, 
                                                const vector<int>& y_pred, 
                                                int numClases) {
    map<int, map<string, double>> metricas;
    
    for (int clase = 0; clase < numClases; clase++) {
        int tp = 0, fp = 0, fn = 0, tn = 0;
        
        for (size_t i = 0; i < y_true.size(); i++) {
            if (y_true[i] == clase && y_pred[i] == clase) tp++;
            else if (y_true[i] != clase && y_pred[i] == clase) fp++;
            else if (y_true[i] == clase && y_pred[i] != clase) fn++;
            else tn++;
        }
        
        double precision = (tp + fp > 0) ? (double)tp / (tp + fp) : 0;
        double recall = (tp + fn > 0) ? (double)tp / (tp + fn) : 0;
        double f1 = (precision + recall > 0) ? 2 * precision * recall / (precision + recall) : 0;
        
        metricas[clase]["precision"] = precision;
        metricas[clase]["recall"] = recall;
        metricas[clase]["f1"] = f1;
    }
    
    return metricas;
}

double calcularCostoTotal(const vector<int>& y_true, const vector<int>& y_pred) {
    // Costos por tipo de error (en USD)
    const double COSTO_DEFECTO_COMO_PREMIUM = 500.0;
    const double COSTO_DEFECTO_COMO_ACEPTABLE = 200.0;
    const double COSTO_PREMIUM_COMO_DEFECTO = 150.0;
    const double COSTO_PREMIUM_COMO_ACEPTABLE = 50.0;
    const double COSTO_ACEPTABLE_COMO_DEFECTO = 80.0;
    const double COSTO_ACEPTABLE_COMO_PREMIUM = 30.0;
    
    double costoTotal = 0;
    
    for (size_t i = 0; i < y_true.size(); i++) {
        if (y_true[i] == 0 && y_pred[i] == 2) costoTotal += COSTO_DEFECTO_COMO_PREMIUM;
        else if (y_true[i] == 0 && y_pred[i] == 1) costoTotal += COSTO_DEFECTO_COMO_ACEPTABLE;
        else if (y_true[i] == 2 && y_pred[i] == 0) costoTotal += COSTO_PREMIUM_COMO_DEFECTO;
        else if (y_true[i] == 2 && y_pred[i] == 1) costoTotal += COSTO_PREMIUM_COMO_ACEPTABLE;
        else if (y_true[i] == 1 && y_pred[i] == 0) costoTotal += COSTO_ACEPTABLE_COMO_DEFECTO;
        else if (y_true[i] == 1 && y_pred[i] == 2) costoTotal += COSTO_ACEPTABLE_COMO_PREMIUM;
    }
    
    return costoTotal;
}

int main() {
    cout << "========================================" << endl;
    cout << "ESCENARIO 10: CONTROL DE CALIDAD AVANZADO" << endl;
    cout << "Clasificacion: Premium/Aceptable/Defectuoso" << endl;
    cout << "========================================\n" << endl;
    
    mt19937 gen(42);
    
    // Generar dataset
    cout << "Generando dataset de componentes manufacturados..." << endl;
    vector<vector<double>> X = generarDataset(500, gen);
    vector<int> y = generarEtiquetas(X);
    
    // Contar clases
    map<int, int> conteoClases;
    for (int clase : y) {
        conteoClases[clase]++;
    }
    
    cout << "\nDistribucion de clases:" << endl;
    cout << "- Defectuoso (0): " << conteoClases[0] << endl;
    cout << "- Aceptable (1): " << conteoClases[1] << endl;
    cout << "- Premium (2): " << conteoClases[2] << endl;
    
    // Dividir dataset
    vector<vector<double>> X_train, X_test;
    vector<int> y_train, y_test;
    dividirDataset(X, y, X_train, y_train, X_test, y_test, 0.2, gen);
    
    cout << "\nConjunto de entrenamiento: " << X_train.size() << " muestras" << endl;
    cout << "Conjunto de prueba: " << X_test.size() << " muestras" << endl;
    
    // Entrenar modelo
    cout << "\nEntrenando clasificador Naive Bayes..." << endl;
    NaiveBayes modelo;
    modelo.entrenar(X_train, y_train);
    
    // Realizar predicciones
    cout << "Realizando predicciones..." << endl;
    vector<int> y_pred(X_test.size());
    for (size_t i = 0; i < X_test.size(); i++) {
        y_pred[i] = modelo.predecir(X_test[i]);
    }
    
    // Calcular accuracy
    double accuracy = calcularAccuracy(y_test, y_pred);
    cout << "\n=== RESULTADOS ===" << endl;
    cout << fixed << setprecision(2);
    cout << "Accuracy: " << accuracy * 100 << "%" << endl;
    
    // Matriz de confusión
    imprimirMatrizConfusion(y_test, y_pred, 3);
    
    // Métricas por clase
    map<int, map<string, double>> metricas = calcularMetricas(y_test, y_pred, 3);
    cout << "\n=== METRICAS POR CLASE ===" << endl;
    string nombresClases[] = {"Defectuoso", "Aceptable", "Premium"};
    
    for (int i = 0; i < 3; i++) {
        cout << "\nClase " << i << " (" << nombresClases[i] << "):" << endl;
        cout << "  Precision: " << metricas[i]["precision"] * 100 << "%" << endl;
        cout << "  Recall: " << metricas[i]["recall"] * 100 << "%" << endl;
        cout << "  F1-Score: " << metricas[i]["f1"] * 100 << "%" << endl;
    }
    
    // Análisis de costos
    double costoTotal = calcularCostoTotal(y_test, y_pred);
    cout << "\n=== ANALISIS ECONOMICO ===" << endl;
    cout << "Costo total de errores: $" << costoTotal << " USD" << endl;
    cout << "Costo promedio por producto: $" << costoTotal / y_test.size() << " USD" << endl;
    
    // Ejemplo de predicción
    cout << "\n=== EJEMPLO DE PREDICCION ===" << endl;
    vector<double> ejemplo = {99.85, 49.95, 9.98, 7820, 0.09};
    cout << "Componente nuevo:" << endl;
    cout << "  Largo: " << ejemplo[0] << " mm" << endl;
    cout << "  Ancho: " << ejemplo[1] << " mm" << endl;
    cout << "  Grosor: " << ejemplo[2] << " mm" << endl;
    cout << "  Dureza: " << ejemplo[3] << " HRC" << endl;
    cout << "  Rugosidad: " << ejemplo[4] << " μm" << endl;
    
    int prediccion = modelo.predecir(ejemplo);
    map<int, double> probabilidades = modelo.predecirProba(ejemplo);
    
    cout << "\nPrediccion: " << nombresClases[prediccion] << endl;
    cout << "Probabilidades:" << endl;
    for (int i = 0; i < 3; i++) {
        cout << "  " << nombresClases[i] << ": " << probabilidades[i] * 100 << "%" << endl;
    }
    
    // Preguntas de reflexión
    cout << "\n=== PREGUNTAS DE REFLEXION ===" << endl;
    cout << "1. ¿Por que es importante tener 3 categorias en lugar de 2 (pasa/no pasa)?" << endl;
    cout << "2. ¿Cual es el error mas costoso: clasificar Premium como Defectuoso o viceversa?" << endl;
    cout << "3. ¿Como afecta la rugosidad superficial en la clasificacion final?" << endl;
    cout << "4. ¿Que estrategias implementarias para reducir el costo total de errores?" << endl;
    cout << "5. ¿Es adecuado un modelo probabilistico para control de calidad critico?" << endl;
    
    return 0;
}