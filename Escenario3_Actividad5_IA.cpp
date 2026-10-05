// ESCENARIO 3: DIAGNOSTICO MEDICO - DETECCION DE DIABETES
// Actividad 5 - Clasificacion Bayesiana Ingenua - Universidad SABES 2026
// C++ (Cxxdroid). Estandar C++11 o superior.
// Columnas: glucosa_mg_dl, presion_mmhg, imc, edad, clase
// Clases: 0 = SANO, 1 = DIABETES

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
    // Dataset (40 registros): glucosa_mg_dl, presion_mmhg, imc, edad, clase
    vector<vector<double>> datos = {
        {163.0, 67.0, 29.6, 57.0, 1}, {98.0, 60.0, 33.5, 35.0, 0}, {152.0, 62.0, 30.2, 62.0, 1},
        {122.0, 82.0, 38.6, 59.0, 1}, {157.0, 85.0, 36.0, 29.0, 1}, {126.0, 87.0, 26.6, 36.0, 1},
        {203.0, 56.0, 26.1, 43.0, 1}, {82.0, 58.0, 28.8, 30.0, 0}, {112.0, 84.0, 39.8, 57.0, 0},
        {100.0, 68.0, 36.0, 39.0, 0}, {114.0, 75.0, 20.6, 33.0, 0}, {92.0, 77.0, 44.0, 44.0, 0},
        {162.0, 76.0, 30.9, 53.0, 1}, {94.0, 95.0, 25.3, 30.0, 0}, {166.0, 75.0, 23.6, 63.0, 1},
        {85.0, 69.0, 25.1, 35.0, 0}, {99.0, 72.0, 29.8, 28.0, 0}, {134.0, 58.0, 38.4, 49.0, 1},
        {77.0, 99.0, 37.3, 25.0, 0}, {81.0, 68.0, 35.7, 31.0, 0}, {111.0, 44.0, 21.7, 58.0, 0},
        {155.0, 49.0, 36.3, 43.0, 0}, {128.0, 69.0, 28.5, 40.0, 0}, {125.0, 80.0, 28.4, 39.0, 0},
        {97.0, 53.0, 31.7, 24.0, 0}, {163.0, 61.0, 34.3, 34.0, 0}, {163.0, 89.0, 40.0, 43.0, 1},
        {86.0, 79.0, 28.6, 46.0, 0}, {115.0, 75.0, 41.9, 45.0, 1}, {200.0, 94.0, 36.3, 55.0, 1},
        {163.0, 76.0, 36.4, 53.0, 1}, {127.0, 49.0, 29.4, 24.0, 0}, {142.0, 65.0, 34.2, 55.0, 1},
        {77.0, 69.0, 32.0, 35.0, 0}, {97.0, 84.0, 29.2, 50.0, 0}, {108.0, 58.0, 33.1, 49.0, 0},
        {180.0, 78.0, 32.1, 34.0, 1}, {101.0, 80.0, 30.5, 29.0, 0}, {121.0, 70.0, 32.1, 19.0, 0},
        {205.0, 70.0, 35.9, 53.0, 1}
    };
    vector<string> nombres = {"SANO", "DIABETES"};
    int k = (int) nombres.size();

    string linea = "======================================================================";
    cout << linea << endl;
    cout << "ESCENARIO 3: DIAGNOSTICO MEDICO - DETECCION DE DIABETES" << endl;
    cout << linea << endl;
    cout << "\nDescripcion del problema:" << endl;
    cout << "Un centro medico necesita un sistema de apoyo para identificar pacientes" << endl;
    cout << "con alto riesgo de diabetes tipo 2 basado en indicadores clinicos." << endl;
    cout << "(Datos de practica, no usar para diagnosticos reales.)" << endl;
    cout << "\nVariables analizadas:" << endl;
    cout << "- Glucosa en sangre (mg/dL)" << endl;
    cout << "- Presion arterial diastolica (mm Hg)" << endl;
    cout << "- Indice de Masa Corporal (IMC)" << endl;
    cout << "- Edad (anios)" << endl;
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
    cout << "                      Pred: SANO          Pred: DIABETES      " << endl;
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

    // Analisis de costos de los errores (clase 1 = DIABETES es el evento que queremos detectar)
    long costoFN = 20000;  // MXN - tratamiento tardio y complicaciones
    long costoFP = 800;  // MXN - estudios de laboratorio adicionales
    long fn = matriz[1][0];
    long fp = matriz[0][1];
    long costoTotal = fn * costoFN + fp * costoFP;
    cout << "\nAnalisis de Costos:" << endl;
    cout << "Falsos Negativos (pacientes con diabetes no detectados): " << fn << " x $" << miles(costoFN) << " = $" << miles(fn * costoFN) << endl;
    cout << "Falsos Positivos (pacientes sanos enviados a estudios): " << fp << " x $" << miles(costoFP) << " = $" << miles(fp * costoFP) << endl;
    cout << "Costo Total de los errores: $" << miles(costoTotal) << endl;

    // ===================================================================
    // PREDICCIONES CON NUEVOS DATOS
    // >>> MODIFICA AQUI: cambia los numeros para crear tus 3 casos <<<
    // Orden: glucosa_mg_dl, presion_mmhg, imc, edad
    // ===================================================================
    vector<vector<double>> nuevosCasos = {
        {105.0, 70.0, 31.3, 37.0},
        {160.0, 75.0, 33.5, 49.0},
        {132.0, 72.0, 32.4, 43.0}
    };
    vector<string> descripciones = {
        "Paciente A - indicadores normales",
        "Paciente B - indicadores elevados",
        "Paciente C - caso intermedio"
    };
    vector<string> variables = {"Glucosa en sangre (mg/dL)", "Presion arterial diastolica (mm Hg)", "Indice de Masa Corporal (IMC)", "Edad (anios)"};
    int decimales[4] = {0, 0, 1, 0};

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
        if (resultado == 1) {
            cout << "  ACCIONES RECOMENDADAS:" << endl;
            cout << "     - Enviar a prueba de hemoglobina glucosilada" << endl;
            cout << "     - Canalizar con medico internista" << endl;
            cout << "     - Orientacion nutricional" << endl;
        }
    }

    cout << "\n" << linea << "\nPREGUNTAS DE ANALISIS:\n" << linea << endl;
    cout << "1. Es mas grave un falso negativo o un falso positivo en este contexto?" << endl;
    cout << "2. Por que el recall (sensibilidad) es critico en diagnosticos medicos?" << endl;
    cout << "3. Que limitaciones tiene usar solo 4 indicadores?" << endl;
    cout << "4. Como mejorar la confiabilidad del modelo para uso clinico real?" << endl;
    return 0;
}
