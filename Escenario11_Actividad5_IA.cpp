// ESCENARIO 11: MSA e Incertidumbre de la Medición
// Análisis del Sistema de Medición usando Naive Bayes
// Archivo: Escenario11_Actividad5_IA.cpp

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
    vector<vector<double>> datos(n, vector<double>(6));
    
    normal_distribution<double> distRepet(5.0, 8.0);
    normal_distribution<double> distReprod(4.0, 7.0);
    normal_distribution<double> distNdc(3.0, 3.0);
    normal_distribution<double> distLin(2.0, 4.0);
    normal_distribution<double> distEst(3.0, 5.0);
    
    for (int i = 0; i < n; i++) {
        // 1. Repetibilidad (%)
        datos[i][0] = abs(distRepet(gen));
        
        // 2. Reproducibilidad (%)
        datos[i][1] = abs(distReprod(gen));
        
        // 3. GRR Total (%)
        datos[i][2] = sqrt(pow(datos[i][0], 2) + pow(datos[i][1], 2));
        
        // 4. ndc (Número de categorías distintas)
        datos[i][3] = abs(distNdc(gen));
        
        // 5. Linearidad (%)
        datos[i][4] = abs(distLin(gen));
        
        // 6. Estabilidad (%)
        datos[i][5] = abs(distEst(gen));
    }
    
    return datos;
}

vector<int> generarEtiquetas(const vector<vector<double>>& X) {
    vector<int> etiquetas(X.size());
    
    for (size_t i = 0; i < X.size(); i++) {
        double repetibilidad = X[i][0];
        double reproducibilidad = X[i][1];
        double grr = X[i][2];
        double ndc = X[i][3];
        double linearidad = X[i][4];
        double estabilidad = X[i][5];
        
        // Criterios de clasificación según estándares MSA
        // Clase 2: ACEPTABLE
        if (grr < 10.0 && ndc >= 5 && linearidad < 5.0 && estabilidad < 10.0) {
            etiquetas[i] = 2;
        }
        // Clase 1: MARGINAL
        else if (grr < 30.0 && ndc >= 3 && linearidad < 10.0 && estabilidad < 20.0) {
            etiquetas[i] = 1;
        }
        // Clase 0: INACEPTABLE
        else {
            etiquetas[i] = 0;
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
    cout << "              ";
    for (int i = 0; i < numClases; i++) {
        cout << "Pred_" << i << "  ";
    }
    cout << endl;
    
    string nombres[] = {"Inaceptable", "Marginal   ", "Aceptable  "};
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

double calcularCostoMejora(const vector<int>& y_true, const vector<int>& y_pred) {
    // Costos asociados a sistemas de medición
    const double COSTO_CALIBRACION = 2000.0;
    const double COSTO_CAPACITACION = 1500.0;
    const double COSTO_EQUIPO_NUEVO = 15000.0;
    const double COSTO_PRODUCTO_DEFECTUOSO = 500.0;
    
    double costoTotal = 0;
    int sistemasInaceptables = 0;
    int sistemasMarginales = 0;
    
    for (size_t i = 0; i < y_true.size(); i++) {
        // Contar sistemas por categoría real
        if (y_true[i] == 0) {
            sistemasInaceptables++;
            costoTotal += COSTO_EQUIPO_NUEVO;
        } else if (y_true[i] == 1) {
            sistemasMarginales++;
            costoTotal += COSTO_CALIBRACION + COSTO_CAPACITACION;
        }
        
        // Penalización por clasificación incorrecta
        if (y_true[i] != y_pred[i]) {
            if (y_true[i] == 0 && y_pred[i] > 0) {
                costoTotal += COSTO_PRODUCTO_DEFECTUOSO * 10;
            } else if (y_true[i] > 0 && y_pred[i] == 0) {
                costoTotal += COSTO_CALIBRACION;
            }
        }
    }
    
    return costoTotal;
}

int main() {
    cout << "========================================" << endl;
    cout << "ESCENARIO 11: MSA E INCERTIDUMBRE DE MEDICION" << endl;
    cout << "Analisis del Sistema de Medicion (MSA)" << endl;
    cout << "========================================\n" << endl;
    
    mt19937 gen(42);
    
    // Generar dataset
    cout << "Generando dataset de estudios MSA..." << endl;
    vector<vector<double>> X = generarDataset(400, gen);
    vector<int> y = generarEtiquetas(X);
    
    // Contar clases
    map<int, int> conteoClases;
    for (int clase : y) {
        conteoClases[clase]++;
    }
    
    cout << "\nDistribucion de sistemas de medicion:" << endl;
    cout << "- Inaceptable (0): " << conteoClases[0] << endl;
    cout << "- Marginal (1): " << conteoClases[1] << endl;
    cout << "- Aceptable (2): " << conteoClases[2] << endl;
    
    // Dividir dataset
    vector<vector<double>> X_train, X_test;
    vector<int> y_train, y_test;
    dividirDataset(X, y, X_train, y_train, X_test, y_test, 0.2, gen);
    
    cout << "\nConjunto de entrenamiento: " << X_train.size() << " estudios" << endl;
    cout << "Conjunto de prueba: " << X_test.size() << " estudios" << endl;
    
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
    string nombresClases[] = {"Inaceptable", "Marginal", "Aceptable"};
    
    for (int i = 0; i < 3; i++) {
        cout << "\nClase " << i << " (" << nombresClases[i] << "):" << endl;
        cout << "  Precision: " << metricas[i]["precision"] * 100 << "%" << endl;
        cout << "  Recall: " << metricas[i]["recall"] * 100 << "%" << endl;
        cout << "  F1-Score: " << metricas[i]["f1"] * 100 << "%" << endl;
    }
    
    // Análisis de costos
    double costoMejora = calcularCostoMejora(y_test, y_pred);
    cout << "\n=== ANALISIS ECONOMICO ===" << endl;
    cout << "Costo estimado de mejora/reemplazo: $" << costoMejora << " USD" << endl;
    cout << "Costo promedio por sistema: $" << costoMejora / y_test.size() << " USD" << endl;
    
    // Ejemplo de predicción
    cout << "\n=== EJEMPLO DE PREDICCION ===" << endl;
    vector<double> ejemplo = {8.5, 6.2, 10.6, 4.2, 3.8, 8.5};
    cout << "Sistema de medicion evaluado:" << endl;
    cout << "  Repetibilidad (EV): " << ejemplo[0] << "%" << endl;
    cout << "  Reproducibilidad (AV): " << ejemplo[1] << "%" << endl;
    cout << "  GRR Total: " << ejemplo[2] << "%" << endl;
    cout << "  ndc: " << ejemplo[3] << endl;
    cout << "  Linearidad: " << ejemplo[4] << "%" << endl;
    cout << "  Estabilidad: " << ejemplo[5] << "%" << endl;
    
    int prediccion = modelo.predecir(ejemplo);
    map<int, double> probabilidades = modelo.predecirProba(ejemplo);
    
    cout << "\nPrediccion: " << nombresClases[prediccion] << endl;
    cout << "Probabilidades:" << endl;
    for (int i = 0; i < 3; i++) {
        cout << "  " << nombresClases[i] << ": " << probabilidades[i] * 100 << "%" << endl;
    }
    
    // Interpretación según estándares MSA
    cout << "\n=== INTERPRETACION SEGUN MSA ===" << endl;
    if (prediccion == 2) {
        cout << "✓ Sistema ACEPTABLE: Puede usarse sin restricciones" << endl;
        cout << "  - GRR < 10%: Excelente variacion del sistema" << endl;
        cout << "  - ndc >= 5: Resolucion adecuada" << endl;
    } else if (prediccion == 1) {
        cout << "⚠ Sistema MARGINAL: Aceptable con reservas" << endl;
        cout << "  - GRR entre 10-30%: Requiere mejoras" << endl;
        cout << "  - Considerar: calibracion y capacitacion" << endl;
    } else {
        cout << "✗ Sistema INACEPTABLE: NO usar para mediciones criticas" << endl;
        cout << "  - GRR > 30%: Variacion excesiva" << endl;
        cout << "  - Accion requerida: Reemplazar equipo o proceso" << endl;
    }
    
    // Preguntas de reflexión
    cout << "\n=== PREGUNTAS DE REFLEXION ===" << endl;
    cout << "1. ¿Por que un GRR < 10% se considera aceptable segun AIAG?" << endl;
    cout << "2. ¿Cual es la diferencia entre repetibilidad y reproducibilidad?" << endl;
    cout << "3. ¿Que significa que ndc = 2 en un sistema de medicion?" << endl;
    cout << "4. ¿Como afecta un sistema marginal a las decisiones de calidad?" << endl;
    cout << "5. ¿Es etico usar IA para clasificar sistemas de medicion automaticamente?" << endl;
    cout << "6. ¿Que impacto tiene un falso negativo (sistema malo clasificado como bueno)?" << endl;
    
    return 0;
}