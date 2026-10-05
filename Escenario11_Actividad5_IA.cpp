// ESCENARIO 11: MSA - ANALISIS DEL SISTEMA DE MEDICION
// Actividad 5 - Clasificacion Bayesiana Ingenua - Universidad SABES 2026
// C++ (Cxxdroid). Estandar C++11 o superior.
// Columnas: desv_std_mm, rango_mm, experiencia_meses, dias_calibracion, clase
// Clases: 0 = INADECUADO, 1 = MARGINAL, 2 = ACEPTABLE

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
    // Dataset (40 registros): desv_std_mm, rango_mm, experiencia_meses, dias_calibracion, clase
    vector<vector<double>> datos = {
        {0.006, 0.1, 63.0, 2.0, 2}, {0.031, 0.09, 50.0, 23.0, 2}, {0.101, 0.23, 23.0, 126.0, 0},
        {0.057, 0.22, 8.0, 52.0, 0}, {0.075, 0.35, 13.0, 153.0, 0}, {0.071, 0.26, 2.0, 140.0, 0},
        {0.08, 0.29, 15.0, 113.0, 0}, {0.042, 0.24, 33.0, 54.0, 1}, {0.079, 0.26, 4.0, 146.0, 0},
        {0.058, 0.12, 19.0, 110.0, 1}, {0.04, 0.13, 25.0, 82.0, 1}, {0.04, 0.08, 49.0, 50.0, 1},
        {0.023, 0.02, 55.0, 55.0, 2}, {0.061, 0.16, 20.0, 41.0, 1}, {0.061, 0.12, 26.0, 112.0, 1},
        {0.101, 0.28, 6.0, 145.0, 0}, {0.03, 0.06, 61.0, 20.0, 2}, {0.077, 0.32, 9.0, 132.0, 0},
        {0.051, 0.15, 38.0, 35.0, 1}, {0.088, 0.29, 11.0, 99.0, 0}, {0.083, 0.3, 18.0, 133.0, 0},
        {0.01, 0.06, 47.0, 12.0, 2}, {0.084, 0.14, 26.0, 93.0, 1}, {0.011, 0.04, 72.0, 7.0, 2},
        {0.06, 0.12, 33.0, 78.0, 1}, {0.025, 0.06, 42.0, 11.0, 2}, {0.069, 0.24, 7.0, 151.0, 0},
        {0.032, 0.08, 42.0, 13.0, 2}, {0.108, 0.26, 11.0, 155.0, 0}, {0.047, 0.15, 44.0, 63.0, 1},
        {0.014, 0.19, 46.0, 17.0, 2}, {0.073, 0.22, 10.0, 88.0, 0}, {0.02, 0.03, 40.0, 12.0, 2},
        {0.021, 0.03, 44.0, 38.0, 2}, {0.007, 0.02, 54.0, 8.0, 2}, {0.035, 0.05, 23.0, 48.0, 1},
        {0.098, 0.27, 5.0, 87.0, 0}, {0.068, 0.13, 18.0, 83.0, 1}, {0.026, 0.04, 36.0, 41.0, 2},
        {0.064, 0.13, 29.0, 86.0, 1}
    };
    vector<string> nombres = {"INADECUADO", "MARGINAL", "ACEPTABLE"};
    int k = (int) nombres.size();

    string linea = "======================================================================";
    cout << linea << endl;
    cout << "ESCENARIO 11: MSA - ANALISIS DEL SISTEMA DE MEDICION" << endl;
    cout << linea << endl;
    cout << "\nDescripcion del problema:" << endl;
    cout << "Un laboratorio de metrologia necesita evaluar la confiabilidad de sus" << endl;
    cout << "sistemas de medicion segun estudios MSA (Measurement System Analysis)." << endl;
    cout << "Criterio: %GRR < 10% Aceptable, 10-30% Marginal, > 30% Inadecuado." << endl;
    cout << "\nVariables analizadas:" << endl;
    cout << "- Desviacion estandar de mediciones repetidas (mm)" << endl;
    cout << "- Rango de mediciones (mm)" << endl;
    cout << "- Experiencia del operador (meses)" << endl;
    cout << "- Dias desde la ultima calibracion" << endl;
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
    cout << "                      Pred: INADECUADO    Pred: MARGINAL      Pred: ACEPTABLE     " << endl;
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
    // Orden: desv_std_mm, rango_mm, experiencia_meses, dias_calibracion
    // ===================================================================
    vector<vector<double>> nuevosCasos = {
        {0.083, 0.27, 10.0, 123.0},
        {0.055, 0.13, 29.0, 72.0},
        {0.02, 0.06, 50.0, 20.0}
    };
    vector<string> descripciones = {
        "Micrometro digital - Linea A",
        "Calibrador vernier - Linea B",
        "CMM - Laboratorio"
    };
    vector<string> variables = {"Desviacion estandar de mediciones repetidas (mm)", "Rango de mediciones (mm)", "Experiencia del operador (meses)", "Dias desde la ultima calibracion"};
    int decimales[4] = {3, 2, 0, 0};

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
            cout << "     - Retirar el instrumento de uso" << endl;
            cout << "     - Recalibrar y repetir el estudio GR&R" << endl;
            cout << "     - Capacitar al operador" << endl;
        }
        if (resultado == 1) {
            cout << "  ACCIONES RECOMENDADAS:" << endl;
            cout << "     - Usar con precaucion y programar calibracion" << endl;
        }
    }

    cout << "\n" << linea << "\nPREGUNTAS DE ANALISIS:\n" << linea << endl;
    cout << "1. Como afecta la experiencia del operador a la repetibilidad?" << endl;
    cout << "2. Que error es mas riesgoso: aceptar un sistema INADECUADO o rechazar uno ACEPTABLE?" << endl;
    cout << "3. Como se relaciona el %GRR con la capacidad del proceso (Cp/Cpk)?" << endl;
    cout << "4. Que dice la norma ISO/IEC 17025 sobre incertidumbre de medicion?" << endl;
    return 0;
}
