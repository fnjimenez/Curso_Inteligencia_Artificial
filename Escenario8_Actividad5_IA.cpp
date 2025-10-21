// ESCENARIO 8: Recursos Humanos - Prediccion de Ausentismo Laboral
// Archivo: Escenario8_Actividad5_IA.cpp

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
    // Dataset: Ausentismo Laboral
    // Columnas: distancia_trabajo_km, antiguedad_meses, hijos, nivel_estres(1-10), ausentismo_alto
    vector<vector<double>> datos = {
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
    
    cout << "======================================================================" << endl;
    cout << "ESCENARIO 8: RECURSOS HUMANOS - PREDICCION DE AUSENTISMO" << endl;
    cout << "======================================================================" << endl;
    cout << "\nDescripcion del problema:" << endl;
    cout << "El departamento de RRHH necesita identificar empleados con alto riesgo" << endl;
    cout << "de ausentismo para implementar estrategias de retencion y bienestar." << endl;
    cout << "\nFactores organizacionales evaluados:" << endl;
    cout << "- Distancia del hogar al trabajo (km)" << endl;
    cout << "- Antiguedad en la empresa (meses)" << endl;
    cout << "- Numero de hijos" << endl;
    cout << "- Nivel de estres percibido (escala 1-10)" << endl;
    cout << "\nClasificacion:" << endl;
    cout << "0 = Ausentismo NORMAL (< 5 dias/año)" << endl;
    cout << "1 = Ausentismo ALTO (>= 5 dias/año)" << endl;
    
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
    cout << "                    Pred: NORMAL  Pred: ALTO" << endl;
    cout << "Real: NORMAL              " << setw(3) << matriz[0][0] 
         << "        " << setw(3) << matriz[0][1] << endl;
    cout << "Real: ALTO                " << setw(3) << matriz[1][0] 
         << "        " << setw(3) << matriz[1][1] << endl;
    
    // Costo del ausentismo
    int costo_dia_ausente = 350;
    int dias_promedio_alto = 8;
    int empleados_riesgo = 0;
    for (size_t i = 0; i < X_test.size(); i++) {
        if (nb.predecir(X_test[i]) == 1) {
            empleados_riesgo++;
        }
    }
    
    int costo_anual_estimado = empleados_riesgo * dias_promedio_alto * costo_dia_ausente;
    
    cout << "\nImpacto Economico Estimado:" << endl;
    cout << "Empleados en riesgo alto: " << empleados_riesgo << endl;
    cout << "Costo anual proyectado por ausentismo: $" << costo_anual_estimado << endl;
    
    // Perfil de empleados
    vector<vector<double>> empleados_evaluacion = {
        {7, 65, 1, 3},
        {38, 16, 3, 9},
        {15, 36, 2, 5}
    };
    vector<string> descripciones = {
        "Empleado A - Perfil estable",
        "Empleado B - Alto riesgo",
        "Empleado C - Riesgo moderado"
    };
    
    cout << "\n======================================================================" << endl;
    cout << "PERFIL DE EMPLEADOS EVALUADOS" << endl;
    cout << "======================================================================" << endl;
    
    for (size_t i = 0; i < empleados_evaluacion.size(); i++) {
        vector<double> datos_emp = empleados_evaluacion[i];
        string descripcion = descripciones[i];
        int resultado = nb.predecir(datos_emp);
        map<int, double> prob = nb.predecirProba(datos_emp);
        
        cout << "\n" << descripcion << ":" << endl;
        cout << "  Distancia al trabajo: " << (int)datos_emp[0] << " km" << endl;
        cout << "  Antiguedad: " << (int)datos_emp[1] << " meses (" << (int)(datos_emp[1]/12) << " años)" << endl;
        cout << "  Hijos: " << (int)datos_emp[2] << endl;
        cout << "  Nivel de estres: " << (int)datos_emp[3] << "/10" << endl;
        cout << "  Prediccion: " << (resultado == 1 ? "⚠️ RIESGO ALTO DE AUSENTISMO" : "✓ AUSENTISMO NORMAL") << endl;
        cout << "  Probabilidad NORMAL: " << setprecision(1) << prob[0] * 100 << "%" << endl;
        cout << "  Probabilidad ALTO: " << prob[1] * 100 << "%" << endl;
        
        if (resultado == 1) {
            cout << "  📋 ESTRATEGIAS DE RETENCION:" << endl;
            if (datos_emp[0] > 25) {
                cout << "     - Considerar home office parcial" << endl;
                cout << "     - Apoyo con transporte" << endl;
            }
            if (datos_emp[1] < 24) {
                cout << "     - Programa de integracion" << endl;
                cout << "     - Mentor asignado" << endl;
            }
            if (datos_emp[2] >= 2) {
                cout << "     - Horarios flexibles" << endl;
                cout << "     - Guarderia o apoyo familiar" << endl;
            }
            if (datos_emp[3] >= 7) {
                cout << "     - Programa de manejo de estres" << endl;
                cout << "     - Evaluacion ergonomica" << endl;
                cout << "     - Sesiones con psicologo organizacional" << endl;
            }
        }
    }
    
    cout << "\n======================================================================" << endl;
    cout << "PREGUNTAS DE ANALISIS:" << endl;
    cout << "======================================================================" << endl;
    cout << "1. ¿Cual factor tiene mayor impacto en el ausentismo?" << endl;
    cout << "2. ¿Que ROI tendrian las intervenciones vs. costo de ausentismo?" << endl;
    cout << "3. ¿Como afecta el ausentismo a la productividad del equipo?" << endl;
    cout << "4. ¿Que politicas de balance vida-trabajo recomendarias?" << endl;
    
    return 0;
}