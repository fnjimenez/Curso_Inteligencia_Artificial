// ESCENARIO 6: Logistica Internacional - Retrasos en Envios
// Archivo: Escenario6_Actividad5_IA.cpp

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
    // Dataset: Envios Internacionales
    // Columnas: distancia_km, peso_kg, num_documentos_aduanales, dias_procesamiento, retraso
    vector<vector<double>> datos = {
        {5200, 850, 3, 2, 0}, {12500, 2300, 8, 5, 1}, {3800, 450, 2, 1, 0},
        {15000, 3200, 12, 7, 1}, {4100, 620, 2, 2, 0}, {8900, 1800, 6, 4, 1},
        {3200, 380, 2, 1, 0}, {11200, 2100, 9, 6, 1}, {4800, 720, 3, 2, 0},
        {13800, 2850, 11, 6, 1}, {3500, 410, 2, 1, 0}, {9500, 1950, 7, 5, 1},
        {4500, 680, 3, 2, 0}, {14200, 3100, 13, 8, 1}, {3900, 520, 2, 2, 0},
        {10800, 2200, 8, 5, 1}, {3300, 390, 2, 1, 0}, {12800, 2650, 10, 6, 1},
        {4200, 590, 3, 2, 0}, {11800, 2400, 9, 5, 1}, {3600, 430, 2, 1, 0},
        {13200, 2900, 11, 7, 1}, {4700, 710, 3, 2, 0}, {9200, 1850, 7, 4, 1},
        {3400, 400, 2, 1, 0}, {14800, 3300, 14, 8, 1}, {4400, 640, 3, 2, 0},
        {10200, 2050, 8, 5, 1}, {3700, 480, 2, 1, 0}, {12200, 2500, 10, 6, 1}
    };
    
    cout << "======================================================================" << endl;
    cout << "ESCENARIO 6: LOGISTICA INTERNACIONAL - PREDICCION DE RETRASOS" << endl;
    cout << "======================================================================" << endl;
    cout << "\nDescripcion del problema:" << endl;
    cout << "Una empresa de logistica internacional necesita predecir que envios" << endl;
    cout << "tienen alto riesgo de retrasarse para tomar medidas preventivas." << endl;
    cout << "\nFactores logisticos analizados:" << endl;
    cout << "- Distancia del envio (km)" << endl;
    cout << "- Peso del paquete (kg)" << endl;
    cout << "- Numero de documentos aduanales requeridos" << endl;
    cout << "- Dias de procesamiento en aduana" << endl;
    
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
    cout << "Envios correctamente clasificados: " << correctos << "/" << X_test.size() << endl;
    
    cout << "\nMatriz de Confusion:" << endl;
    cout << "                    Pred: A TIEMPO  Pred: RETRASO" << endl;
    cout << "Real: A TIEMPO            " << setw(3) << matriz[0][0] 
         << "           " << setw(3) << matriz[0][1] << endl;
    cout << "Real: RETRASO             " << setw(3) << matriz[1][0] 
         << "           " << setw(3) << matriz[1][1] << endl;
    
    // Analisis de costo
    int costo_falso_negativo = 5000;
    int costo_falso_positivo = 500;
    
    int costo_total = (matriz[1][0] * costo_falso_negativo) + (matriz[0][1] * costo_falso_positivo);
    
    cout << "\nAnalisis de Costos:" << endl;
    cout << "Falsos Negativos (retrasos no detectados): " << matriz[1][0] 
         << " x $" << costo_falso_negativo << " = $" << (matriz[1][0] * costo_falso_negativo) << endl;
    cout << "Falsos Positivos (alarmas falsas): " << matriz[0][1] 
         << " x $" << costo_falso_positivo << " = $" << (matriz[0][1] * costo_falso_positivo) << endl;
    cout << "Costo Total: $" << costo_total << endl;
    
    // Evaluacion de nuevos envios
    vector<vector<double>> nuevos_envios = {
        {4500, 650, 3, 2},
        {13500, 2800, 11, 7},
        {7200, 1200, 5, 3}
    };
    vector<string> descripciones = {
        "Envio Nacional - Standard",
        "Envio Internacional - Complejo",
        "Envio Regional - Moderado"
    };
    
    cout << "\n======================================================================" << endl;
    cout << "EVALUACION DE ENVIOS PENDIENTES" << endl;
    cout << "======================================================================" << endl;
    
    for (size_t i = 0; i < nuevos_envios.size(); i++) {
        vector<double> datos_envio = nuevos_envios[i];
        string descripcion = descripciones[i];
        int resultado = nb.predecir(datos_envio);
        map<int, double> prob = nb.predecirProba(datos_envio);
        
        cout << "\n" << descripcion << ":" << endl;
        cout << "  Distancia: " << (int)datos_envio[0] << " km" << endl;
        cout << "  Peso: " << (int)datos_envio[1] << " kg" << endl;
        cout << "  Documentos aduanales: " << (int)datos_envio[2] << endl;
        cout << "  Dias procesamiento: " << (int)datos_envio[3] << endl;
        cout << "  Prediccion: " << (resultado == 1 ? "🚨 RIESGO DE RETRASO" : "✓ ENTREGA A TIEMPO") << endl;
        cout << "  Confianza A TIEMPO: " << setprecision(1) << prob[0] * 100 << "%" << endl;
        cout << "  Confianza RETRASO: " << prob[1] * 100 << "%" << endl;
        
        if (resultado == 1) {
            cout << "  📋 ACCIONES RECOMENDADAS:" << endl;
            cout << "     - Acelerar tramites aduanales" << endl;
            cout << "     - Notificar cliente sobre posible retraso" << endl;
            cout << "     - Asignar courier premium" << endl;
            cout << "     - Revisar ruta alternativa" << endl;
        }
    }
    
    cout << "\n======================================================================" << endl;
    cout << "PREGUNTAS DE ANALISIS:" << endl;
    cout << "======================================================================" << endl;
    cout << "1. ¿Cual factor logistico impacta mas en los retrasos?" << endl;
    cout << "2. ¿Como optimizarias la relacion costo-beneficio del modelo?" << endl;
    cout << "3. ¿Que KPIs logisticos adicionales deberian considerarse?" << endl;
    cout << "4. ¿Como afectan las regulaciones aduanales al modelo predictivo?" << endl;
    
    return 0;
}