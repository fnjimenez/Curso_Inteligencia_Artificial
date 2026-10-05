// ESCENARIO 7: SEGURIDAD INDUSTRIAL - PREDICCION DE ACCIDENTES
// Actividad 5 - Clasificacion Bayesiana Ingenua - Universidad SABES 2026
// C++ (Cxxdroid). Estandar C++11 o superior.
// Columnas: hrs_trabajo_continuo, ruido_db, temperatura_C, dias_sin_capacitacion, clase
// Clases: 0 = SIN ACCIDENTE, 1 = ACCIDENTE

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
    // Dataset (40 registros): hrs_trabajo_continuo, ruido_db, temperatura_C, dias_sin_capacitacion, clase
    vector<vector<double>> datos = {
        {9.0, 100.0, 39.0, 54.0, 1}, {6.0, 85.0, 46.0, 62.0, 1}, {15.0, 74.0, 30.0, 32.0, 1},
        {13.0, 100.0, 35.0, 12.0, 1}, {4.0, 82.0, 18.0, 10.0, 0}, {10.0, 62.0, 21.0, 10.0, 0},
        {3.0, 76.0, 22.0, 4.0, 0}, {15.0, 106.0, 28.0, 27.0, 1}, {11.0, 76.0, 15.0, 0.0, 0},
        {4.0, 69.0, 25.0, 14.0, 0}, {7.0, 78.0, 16.0, 9.0, 0}, {4.0, 75.0, 19.0, 2.0, 0},
        {4.0, 81.0, 18.0, 3.0, 0}, {3.0, 76.0, 32.0, 1.0, 0}, {4.0, 73.0, 24.0, 5.0, 0},
        {14.0, 99.0, 29.0, 15.0, 1}, {7.0, 67.0, 26.0, 7.0, 0}, {3.0, 60.0, 31.0, 11.0, 0},
        {6.0, 88.0, 24.0, 9.0, 0}, {7.0, 64.0, 22.0, 12.0, 0}, {13.0, 87.0, 20.0, 21.0, 0},
        {11.0, 99.0, 36.0, 26.0, 1}, {3.0, 74.0, 27.0, 10.0, 0}, {5.0, 74.0, 19.0, 30.0, 0},
        {10.0, 98.0, 27.0, 21.0, 1}, {6.0, 101.0, 34.0, 48.0, 1}, {14.0, 93.0, 43.0, 33.0, 1},
        {10.0, 80.0, 32.0, 4.0, 1}, {7.0, 75.0, 21.0, 45.0, 0}, {14.0, 89.0, 47.0, 38.0, 1},
        {13.0, 89.0, 34.0, 73.0, 1}, {12.0, 94.0, 43.0, 87.0, 1}, {3.0, 72.0, 24.0, 6.0, 0},
        {6.0, 76.0, 23.0, 15.0, 0}, {11.0, 99.0, 37.0, 57.0, 1}, {12.0, 78.0, 24.0, 9.0, 0},
        {11.0, 110.0, 40.0, 56.0, 1}, {4.0, 84.0, 27.0, 9.0, 0}, {3.0, 84.0, 17.0, 7.0, 0},
        {10.0, 73.0, 27.0, 16.0, 0}
    };
    vector<string> nombres = {"SIN ACCIDENTE", "ACCIDENTE"};
    int k = (int) nombres.size();

    string linea = "======================================================================";
    cout << linea << endl;
    cout << "ESCENARIO 7: SEGURIDAD INDUSTRIAL - PREDICCION DE ACCIDENTES" << endl;
    cout << linea << endl;
    cout << "\nDescripcion del problema:" << endl;
    cout << "Una planta industrial necesita identificar condiciones de alto riesgo" << endl;
    cout << "para prevenir accidentes laborales y proteger a sus trabajadores." << endl;
    cout << "\nVariables analizadas:" << endl;
    cout << "- Horas de trabajo continuo" << endl;
    cout << "- Nivel de ruido ambiental (dB)" << endl;
    cout << "- Temperatura en el area (C)" << endl;
    cout << "- Dias desde la ultima capacitacion" << endl;
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
    cout << "                      Pred: SIN ACCIDENTE Pred: ACCIDENTE     " << endl;
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

    // Analisis de costos de los errores (clase 1 = ACCIDENTE es el evento que queremos detectar)
    long costoFN = 120000;  // MXN - costo de un accidente (incapacidad, multa, paro)
    long costoFP = 2000;  // MXN - pausa o rotacion preventiva del turno
    long fn = matriz[1][0];
    long fp = matriz[0][1];
    long costoTotal = fn * costoFN + fp * costoFP;
    cout << "\nAnalisis de Costos:" << endl;
    cout << "Falsos Negativos (condiciones de riesgo no detectadas): " << fn << " x $" << miles(costoFN) << " = $" << miles(fn * costoFN) << endl;
    cout << "Falsos Positivos (alertas preventivas innecesarias): " << fp << " x $" << miles(costoFP) << " = $" << miles(fp * costoFP) << endl;
    cout << "Costo Total de los errores: $" << miles(costoTotal) << endl;

    // ===================================================================
    // PREDICCIONES CON NUEVOS DATOS
    // >>> MODIFICA AQUI: cambia los numeros para crear tus 3 casos <<<
    // Orden: hrs_trabajo_continuo, ruido_db, temperatura_C, dias_sin_capacitacion
    // ===================================================================
    vector<vector<double>> nuevosCasos = {
        {6.0, 75.0, 23.0, 11.0},
        {12.0, 95.0, 36.0, 40.0},
        {9.0, 85.0, 29.0, 26.0}
    };
    vector<string> descripciones = {
        "Turno matutino - Area A",
        "Turno nocturno - Area B",
        "Turno vespertino - Area C"
    };
    vector<string> variables = {"Horas de trabajo continuo", "Nivel de ruido ambiental (dB)", "Temperatura en el area (C)", "Dias desde la ultima capacitacion"};
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
        if (resultado == 1) {
            cout << "  ACCIONES RECOMENDADAS:" << endl;
            cout << "     - Rotar al personal y dar pausa activa" << endl;
            cout << "     - Verificar uso de EPP (proteccion auditiva)" << endl;
            cout << "     - Programar capacitacion de seguridad" << endl;
        }
    }

    cout << "\n" << linea << "\nPREGUNTAS DE ANALISIS:\n" << linea << endl;
    cout << "1. Por que es critico minimizar los falsos negativos en seguridad?" << endl;
    cout << "2. Cual factor de riesgo requiere intervencion mas urgente?" << endl;
    cout << "3. Como se compara el costo de un accidente vs. las medidas preventivas?" << endl;
    cout << "4. Que normas (NOM-STPS) se relacionan con estos factores?" << endl;
    return 0;
}
