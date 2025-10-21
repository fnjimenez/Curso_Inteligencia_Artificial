// ESCENARIO 5: Prediccion de Rendimiento Academico
// Archivo: Escenario5_Actividad5_IA.cpp

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
    // Dataset: Rendimiento Academico
    // Columnas: hrs_estudio_semanal, asistencia%, participacion%, tareas_entregadas%, aprobado
    // Clase: 0=Reprobado, 1=Aprobado, 2=Excelente
    vector<vector<double>> datos = {
        {15, 95, 80, 100, 2}, {8, 75, 50, 70, 1}, {20, 98, 90, 100, 2},
        {5, 60, 30, 50, 0}, {12, 85, 70, 85, 1}, {18, 92, 85, 95, 2},
        {6, 65, 40, 55, 0}, {10, 80, 60, 75, 1}, {22, 100, 95, 100, 2},
        {4, 55, 25, 45, 0}, {14, 88, 75, 90, 1}, {19, 95, 88, 98, 2},
        {7, 70, 45, 60, 0}, {11, 82, 65, 80, 1}, {16, 90, 82, 92, 2},
        {5, 58, 28, 48, 0}, {9, 78, 55, 72, 1}, {21, 97, 92, 100, 2},
        {6, 62, 35, 52, 0}, {13, 86, 72, 88, 1}, {17, 93, 86, 96, 2},
        {4, 52, 22, 42, 0}, {10, 79, 58, 74, 1}, {20, 96, 90, 98, 2},
        {7, 68, 42, 58, 0}, {12, 84, 68, 82, 1}, {18, 94, 87, 94, 2},
        {5, 56, 26, 46, 0}, {11, 81, 62, 78, 1}, {19, 95, 89, 97, 2}
    };
    
    cout << "======================================================================" << endl;
    cout << "ESCENARIO 5: PREDICCION DE RENDIMIENTO ACADEMICO" << endl;
    cout << "======================================================================" << endl;
    cout << "\nDescripcion del problema:" << endl;
    cout << "Una universidad quiere identificar tempranamente estudiantes en riesgo" << endl;
    cout << "de reprobar para ofrecerles apoyo academico oportuno." << endl;
    cout << "\nIndicadores academicos evaluados:" << endl;
    cout << "- Horas de estudio semanal" << endl;
    cout << "- Porcentaje de asistencia" << endl;
    cout << "- Participacion en clase (%)" << endl;
    cout << "- Tareas entregadas a tiempo (%)" << endl;
    cout << "\nClasificacion de estudiantes:" << endl;
    cout << "0 = REPROBADO (< 60)" << endl;
    cout << "1 = APROBADO (60-89)" << endl;
    cout << "2 = EXCELENTE (90-100)" << endl;
    
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
    int matriz[3][3] = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
    
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
    
    cout << "\nMatriz de Confusion (3 clases):" << endl;
    cout << "                  Pred:REP  Pred:APR  Pred:EXC" << endl;
    cout << "Real:REPROBADO       " << setw(3) << matriz[0][0] 
         << "       " << setw(3) << matriz[0][1] 
         << "       " << setw(3) << matriz[0][2] << endl;
    cout << "Real:APROBADO        " << setw(3) << matriz[1][0] 
         << "       " << setw(3) << matriz[1][1] 
         << "       " << setw(3) << matriz[1][2] << endl;
    cout << "Real:EXCELENTE       " << setw(3) << matriz[2][0] 
         << "       " << setw(3) << matriz[2][1] 
         << "       " << setw(3) << matriz[2][2] << endl;
    
    // Evaluacion de estudiantes
    vector<vector<double>> estudiantes_prueba = {
        {7, 68, 45, 60},
        {12, 85, 72, 88},
        {19, 96, 90, 98}
    };
    vector<string> nombres = {
        "Estudiante A - En riesgo",
        "Estudiante B - Promedio",
        "Estudiante C - Destacado"
    };
    
    map<int, string> clases_nombres;
    clases_nombres[0] = "REPROBADO ❌";
    clases_nombres[1] = "APROBADO ✓";
    clases_nombres[2] = "EXCELENTE ⭐";
    
    cout << "\n======================================================================" << endl;
    cout << "EVALUACION DE ESTUDIANTES" << endl;
    cout << "======================================================================" << endl;
    
    for (size_t i = 0; i < estudiantes_prueba.size(); i++) {
        vector<double> datos_est = estudiantes_prueba[i];
        string nombre = nombres[i];
        int resultado = nb.predecir(datos_est);
        map<int, double> prob = nb.predecirProba(datos_est);
        
        cout << "\n" << nombre << ":" << endl;
        cout << "  Hrs estudio/semana: " << (int)datos_est[0] << endl;
        cout << "  Asistencia: " << (int)datos_est[1] << "%" << endl;
        cout << "  Participacion: " << (int)datos_est[2] << "%" << endl;
        cout << "  Tareas: " << (int)datos_est[3] << "%" << endl;
        cout << "  Prediccion: " << clases_nombres[resultado] << endl;
        cout << "  Probabilidades:" << endl;
        cout << "    Reprobar: " << setprecision(1) << prob[0] * 100 << "%" << endl;
        cout << "    Aprobar: " << prob[1] * 100 << "%" << endl;
        cout << "    Excelencia: " << prob[2] * 100 << "%" << endl;
        
        if (resultado == 0) {
            cout << "  ⚠️ RECOMENDACION: Asignar tutor y plan de mejora urgente" << endl;
        } else if (resultado == 1) {
            cout << "  💡 RECOMENDACION: Fomentar habitos de estudio" << endl;
        } else {
            cout << "  🎓 RECOMENDACION: Considerar para programa de becas" << endl;
        }
    }
    
    cout << "\n======================================================================" << endl;
    cout << "PREGUNTAS DE ANALISIS:" << endl;
    cout << "======================================================================" << endl;
    cout << "1. ¿Cual indicador es mas determinante para el exito academico?" << endl;
    cout << "2. ¿Que estrategias de intervencion propondrias para cada categoria?" << endl;
    cout << "3. ¿Como afecta la deteccion temprana al rendimiento final?" << endl;
    cout << "4. ¿Es etico usar IA para predecir el rendimiento estudiantil?" << endl;
    cout << "5. ¿Que limitaciones tiene este modelo de clasificacion?" << endl;
    
    return 0;
}