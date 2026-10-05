// ESCENARIO 5: PREDICCION DE RENDIMIENTO ACADEMICO
// Actividad 5 - Clasificacion Bayesiana Ingenua - Universidad SABES 2026
// C++ (Cxxdroid). Estandar C++11 o superior.
// Columnas: hrs_estudio_semanal, asistencia_pct, participacion_pct, tareas_pct, clase
// Clases: 0 = REPROBADO, 1 = APROBADO, 2 = EXCELENTE

#include <iostream>
#include <vector>
#include <map>
#include <cmath>
#include <string>
#include <sstream>
#include <iomanip>
using namespace std;

class NaiveBayes {
public:
    map<int, vector<vector<double>>> clases;
    map<int, vector<pair<double, double>>> estadisticas;  // (media, desviacion) por variable
    map<int, double> priors;

    double calcularMedia(const vector<double>& numeros) {
        double suma = 0;
        for (double x : numeros) suma += x;
        return suma / numeros.size();
    }

    double calcularDesviacion(const vector<double>& numeros) {
        double media = calcularMedia(numeros);
        double varianza = 0;
        for (double x : numeros) varianza += pow(x - media, 2);
        varianza /= numeros.size();
        return varianza > 0 ? sqrt(varianza) : 0.0001;
    }

    double probabilidadGaussiana(double x, double media, double desviacion) {
        double exponente = exp(-pow(x - media, 2) / (2 * pow(desviacion, 2)));
        return (1 / (sqrt(2 * M_PI) * desviacion)) * exponente;
    }

    void entrenar(const vector<vector<double>>& X, const vector<int>& y) {
        for (size_t i = 0; i < X.size(); i++) clases[y[i]].push_back(X[i]);
        for (auto& par : clases) {
            const auto& valores = par.second;
            priors[par.first] = (double) valores.size() / X.size();
            for (size_t j = 0; j < valores[0].size(); j++) {
                vector<double> columna;
                for (const auto& fila : valores) columna.push_back(fila[j]);
                estadisticas[par.first].push_back(make_pair(calcularMedia(columna), calcularDesviacion(columna)));
            }
        }
    }

    map<int, double> predecirProba(const vector<double>& x) {
        map<int, double> prob;
        double total = 0;
        for (auto& par : estadisticas) {
            double p = priors[par.first];
            for (size_t j = 0; j < x.size(); j++)
                p *= probabilidadGaussiana(x[j], par.second[j].first, par.second[j].second);
            prob[par.first] = p;
            total += p;
        }
        if (total > 0) for (auto& par : prob) par.second /= total;
        return prob;
    }

    int predecir(const vector<double>& x) {
        map<int, double> prob = predecirProba(x);
        int mejor = -1;
        double maximo = -1;
        for (auto& par : prob) if (par.second > maximo) { maximo = par.second; mejor = par.first; }
        return mejor;
    }
};

// Funciones de apoyo para imprimir
string centrar(const string& s, int ancho) {
    int izq = (ancho - (int) s.size()) / 2;
    int der = ancho - (int) s.size() - izq;
    return string(max(0, izq), ' ') + s + string(max(0, der), ' ');
}
string miles(long n) {
    string s = to_string(n), r;
    int cont = 0;
    for (int i = (int) s.size() - 1; i >= 0; i--) {
        r = s[i] + r;
        if (++cont % 3 == 0 && i > 0) r = "," + r;
    }
    return r;
}

int main() {
    // Dataset (40 registros): hrs_estudio_semanal, asistencia_pct, participacion_pct, tareas_pct, clase
    vector<vector<double>> datos = {
        {15.0, 94.0, 98.0, 93.0, 2}, {14.0, 94.0, 68.0, 100.0, 2}, {11.0, 55.0, 28.0, 45.0, 0},
        {17.0, 98.0, 92.0, 92.0, 2}, {20.0, 100.0, 73.0, 83.0, 2}, {20.0, 95.0, 96.0, 91.0, 2},
        {22.0, 96.0, 91.0, 88.0, 2}, {1.0, 60.0, 26.0, 43.0, 0}, {2.0, 88.0, 54.0, 69.0, 0},
        {13.0, 89.0, 94.0, 81.0, 2}, {11.0, 98.0, 61.0, 88.0, 1}, {1.0, 73.0, 39.0, 56.0, 0},
        {6.0, 47.0, 31.0, 65.0, 0}, {8.0, 94.0, 53.0, 93.0, 1}, {6.0, 76.0, 34.0, 52.0, 0},
        {20.0, 95.0, 83.0, 97.0, 2}, {12.0, 81.0, 48.0, 88.0, 1}, {5.0, 62.0, 43.0, 75.0, 0},
        {5.0, 87.0, 56.0, 66.0, 1}, {7.0, 56.0, 43.0, 59.0, 0}, {7.0, 69.0, 57.0, 55.0, 0},
        {4.0, 82.0, 61.0, 65.0, 1}, {7.0, 62.0, 68.0, 69.0, 0}, {10.0, 74.0, 45.0, 91.0, 1},
        {25.0, 100.0, 78.0, 98.0, 2}, {7.0, 79.0, 44.0, 88.0, 1}, {7.0, 69.0, 24.0, 55.0, 0},
        {20.0, 91.0, 86.0, 96.0, 2}, {8.0, 76.0, 41.0, 67.0, 0}, {10.0, 76.0, 43.0, 78.0, 1},
        {7.0, 64.0, 25.0, 47.0, 0}, {19.0, 85.0, 85.0, 98.0, 2}, {16.0, 88.0, 72.0, 90.0, 1},
        {12.0, 89.0, 94.0, 99.0, 2}, {11.0, 87.0, 70.0, 83.0, 1}, {6.0, 52.0, 24.0, 57.0, 0},
        {9.0, 78.0, 79.0, 59.0, 1}, {19.0, 100.0, 85.0, 90.0, 2}, {21.0, 82.0, 54.0, 88.0, 1},
        {9.0, 93.0, 65.0, 67.0, 1}
    };
    vector<string> nombres = {"REPROBADO", "APROBADO", "EXCELENTE"};
    int k = (int) nombres.size();

    string linea = "======================================================================";
    cout << linea << endl;
    cout << "ESCENARIO 5: PREDICCION DE RENDIMIENTO ACADEMICO" << endl;
    cout << linea << endl;
    cout << "\nDescripcion del problema:" << endl;
    cout << "Una universidad quiere identificar tempranamente estudiantes en riesgo" << endl;
    cout << "de reprobar para ofrecerles apoyo academico oportuno." << endl;
    cout << "\nVariables analizadas:" << endl;
    cout << "- Horas de estudio semanal" << endl;
    cout << "- Asistencia (%)" << endl;
    cout << "- Participacion en clase (%)" << endl;
    cout << "- Tareas entregadas a tiempo (%)" << endl;
    cout << "\nClases:" << endl;
    for (int n = 0; n < k; n++) cout << "  " << n << " = " << nombres[n] << endl;

    // Division FIJA: primeros 70% para entrenar, ultimos 30% para probar
    size_t split = (size_t) (datos.size() * 0.7);
    vector<vector<double>> X_train, X_test;
    vector<int> y_train, y_test;
    for (size_t i = 0; i < datos.size(); i++) {
        vector<double> x(datos[i].begin(), datos[i].begin() + 4);
        if (i < split) { X_train.push_back(x); y_train.push_back((int) datos[i][4]); }
        else { X_test.push_back(x); y_test.push_back((int) datos[i][4]); }
    }

    NaiveBayes nb;
    nb.entrenar(X_train, y_train);

    int correctos = 0;
    vector<vector<int>> matriz(k, vector<int>(k, 0));
    for (size_t i = 0; i < X_test.size(); i++) {
        int pred = nb.predecir(X_test[i]);
        if (pred == y_test[i]) correctos++;
        matriz[y_test[i]][pred]++;
    }
    double accuracy = 100.0 * correctos / X_test.size();

    cout << fixed << setprecision(2);
    cout << "\n" << linea << "\nRESULTADOS DEL ANALISIS\n" << linea << endl;
    cout << "Registros de entrenamiento: " << X_train.size() << "   Registros de prueba: " << X_test.size() << endl;
    cout << "Accuracy: " << accuracy << "%" << endl;
    cout << "Casos correctamente clasificados: " << correctos << "/" << X_test.size() << endl;

    cout << "\nMatriz de Confusion (filas = real, columnas = prediccion):" << endl;
    cout << "                      Pred: REPROBADO     Pred: APROBADO      Pred: EXCELENTE     " << endl;
    for (int r = 0; r < k; r++) {
        cout << "Real: " << left << setw(16) << nombres[r] << right;
        for (int p = 0; p < k; p++) cout << centrar(to_string(matriz[r][p]), 20);
        cout << endl;
    }

    cout << "\n" << linea << "\nMETRICAS POR CLASE\n" << linea << endl;
    for (int c = 0; c < k; c++) {
        int vp = matriz[c][c], predC = 0, realC = 0;
        for (int r = 0; r < k; r++) { predC += matriz[r][c]; realC += matriz[c][r]; }
        double precision = predC > 0 ? (double) vp / predC : 0;
        double recall = realC > 0 ? (double) vp / realC : 0;
        double f1 = (precision + recall) > 0 ? 2 * precision * recall / (precision + recall) : 0;
        cout << "Clase: " << nombres[c] << " (" << c << ")" << endl;
        cout << "  Precision: " << precision * 100 << "%" << endl;
        cout << "  Recall:    " << recall * 100 << "%" << endl;
        cout << "  F1-Score:  " << f1 * 100 << "%" << endl;
    }

    // ===================================================================
    // PREDICCIONES CON NUEVOS DATOS
    // >>> MODIFICA AQUI: cambia los numeros para crear tus 3 casos <<<
    // Orden: hrs_estudio_semanal, asistencia_pct, participacion_pct, tareas_pct
    // ===================================================================
    vector<vector<double>> nuevosCasos = {
        {6.0, 65.0, 38.0, 58.0},
        {10.0, 85.0, 58.0, 80.0},
        {18.0, 94.0, 86.0, 93.0}
    };
    vector<string> descripciones = {
        "Estudiante A - en riesgo",
        "Estudiante B - promedio",
        "Estudiante C - destacado"
    };
    vector<string> variables = {"Horas de estudio semanal", "Asistencia (%)", "Participacion en clase (%)", "Tareas entregadas a tiempo (%)"};
    int decimales[4] = {0, 0, 0, 0};

    cout << "\n" << linea << "\nEVALUACION DE NUEVOS CASOS\n" << linea << endl;
    for (size_t i = 0; i < nuevosCasos.size(); i++) {
        vector<double>& caso = nuevosCasos[i];
        int resultado = nb.predecir(caso);
        map<int, double> prob = nb.predecirProba(caso);
        cout << "\n" << descripciones[i] << ":" << endl;
        for (int j = 0; j < 4; j++) cout << "  " << variables[j] << ": " << setprecision(decimales[j]) << caso[j] << endl;
        cout << "  Prediccion: " << nombres[resultado] << endl;
        double suma = 0;
        cout << setprecision(1);
        for (int n = 0; n < k; n++) {
            cout << "  Probabilidad " << nombres[n] << ": " << prob[n] * 100 << "%" << endl;
            suma += prob[n];
        }
        if (suma == 0) cout << "  (Valores muy lejanos a los datos de entrenamiento: revisa tus numeros)" << endl;
        if (resultado == 0) {
            cout << "  ACCIONES RECOMENDADAS:" << endl;
            cout << "     - Canalizar a asesoria academica" << endl;
            cout << "     - Tutoria con el docente" << endl;
            cout << "     - Plan de recuperacion de tareas" << endl;
        }
        if (resultado == 2) {
            cout << "  ACCIONES RECOMENDADAS:" << endl;
            cout << "     - Invitar a proyecto de investigacion o concurso" << endl;
        }
    }

    cout << "\n" << linea << "\nPREGUNTAS DE ANALISIS:\n" << linea << endl;
    cout << "1. Que indicador predice mejor el riesgo de reprobar?" << endl;
    cout << "2. Que error es mas grave: clasificar a un REPROBADO como APROBADO o al reves?" << endl;
    cout << "3. Que intervenciones tempranas recomendarias?" << endl;
    cout << "4. Que otros datos agregarias (calificaciones previas, trabajo, transporte)?" << endl;
    return 0;
}
