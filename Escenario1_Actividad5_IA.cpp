// ESCENARIO 1: Control de Calidad en Manufactura
// Archivo: Escenario1_Actividad5_IA.cpp

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
    // Dataset: Control de Calidad de Piezas
    // Columnas: diametro(mm), longitud(mm), peso(g), temperatura(°C), calidad
    vector<vector<double>> datos = {
        {10.2, 50.1, 45.2, 180, 1}, {10.1, 50.3, 45.5, 182, 1}, {10.3, 50.0, 45.1, 181, 1},
        {9.8, 49.5, 44.0, 175, 0}, {10.0, 50.2, 45.3, 183, 1}, {9.9, 49.8, 44.8, 179, 1},
        {11.5, 51.2, 47.0, 190, 0}, {10.2, 50.0, 45.0, 180, 1}, {10.1, 50.1, 45.2, 181, 1},
        {9.7, 49.3, 43.5, 172, 0}, {10.3, 50.2, 45.4, 182, 1}, {10.0, 50.0, 45.0, 180, 1},
        {11.8, 51.5, 47.5, 195, 0}, {10.2, 50.3, 45.6, 183, 1}, {9.6, 49.0, 43.0, 170, 0},
        {10.1, 50.1, 45.1, 181, 1}, {10.4, 50.4, 45.7, 184, 1}, {9.5, 48.8, 42.5, 168, 0},
        {10.0, 50.0, 45.0, 180, 1}, {10.3, 50.2, 45.3, 182, 1}, {12.0, 52.0, 48.0, 200, 0},
        {10.2, 50.1, 45.2, 181, 1}, {9.9, 49.9, 44.9, 179, 1}, {10.1, 50.0, 45.1, 180, 1},
        {11.2, 51.0, 46.5, 188, 0}, {10.0, 50.2, 45.2, 182, 1}, {10.3, 50.1, 45.4, 181, 1},
        {9.8, 49.6, 44.2, 176, 0}, {10.2, 50.0, 45.0, 180, 1}, {10.1, 50.3, 45.5, 183, 1}
    };
    
    cout << "======================================================================" << endl;
    cout << "ESCENARIO 1: CONTROL DE CALIDAD EN MANUFACTURA" << endl;
    cout << "======================================================================" << endl;
    cout << "\nDescripcion del problema:" << endl;
    cout << "Una fabrica produce piezas metalicas y necesita clasificarlas como" << endl;
    cout << "APTAS o DEFECTUOSAS segun sus dimensiones y temperatura de fundicion." << endl;
    cout << "\nCaracteristicas medidas:" << endl;
    cout << "- Diametro (mm)" << endl;
    cout << "- Longitud (mm)" << endl;
    cout << "- Peso (gramos)" << endl;
    cout << "- Temperatura de fundicion (°C)" << endl;
    
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
    cout << "\nDatos de entrenamiento: " << X_train.size() << endl;
    cout << "Datos de prueba: " << X_test.size() << endl;
    cout << fixed << setprecision(2);
    cout << "\nAccuracy: " << accuracy << "%" << endl;
    cout << "Piezas correctamente clasificadas: " << correctos << "/" << X_test.size() << endl;
    
    cout << "\nMatriz de Confusion:" << endl;
    cout << "                    Predicho DEFECTUOSA  Predicho APTA" << endl;
    cout << "Real DEFECTUOSA            " << setw(3) << matriz[0][0] 
         << "               " << setw(3) << matriz[0][1] << endl;
    cout << "Real APTA                  " << setw(3) << matriz[1][0] 
         << "               " << setw(3) << matriz[1][1] << endl;
    
    // Prediccion de nueva pieza
    vector<double> nueva_pieza = {10.15, 50.05, 45.25, 181.5};
    int resultado = nb.predecir(nueva_pieza);
    map<int, double> probabilidades = nb.predecirProba(nueva_pieza);
    
    cout << "\n======================================================================" << endl;
    cout << "PREDICCION DE NUEVA PIEZA" << endl;
    cout << "======================================================================" << endl;
    cout << "Diametro: " << nueva_pieza[0] << " mm" << endl;
    cout << "Longitud: " << nueva_pieza[1] << " mm" << endl;
    cout << "Peso: " << nueva_pieza[2] << " g" << endl;
    cout << "Temperatura: " << nueva_pieza[3] << " °C" << endl;
    cout << "\nClasificacion: " << (resultado == 1 ? "APTA ✓" : "DEFECTUOSA ✗") << endl;
    cout << "Confianza DEFECTUOSA: " << probabilidades[0] * 100 << "%" << endl;
    cout << "Confianza APTA: " << probabilidades[1] * 100 << "%" << endl;
    
    cout << "\n======================================================================" << endl;
    cout << "PREGUNTAS DE ANALISIS PARA EL ALUMNO:" << endl;
    cout << "======================================================================" << endl;
    cout << "1. ¿Que tan confiable es el modelo con este accuracy?" << endl;
    cout << "2. ¿Cual seria el costo de clasificar incorrectamente una pieza DEFECTUOSA como APTA?" << endl;
    cout << "3. ¿Que caracteristica parece ser mas determinante para la clasificacion?" << endl;
    cout << "4. ¿Como podrias mejorar el modelo con mas datos?" << endl;
    
    return 0;
}