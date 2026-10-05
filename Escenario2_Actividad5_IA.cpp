// ESCENARIO 2: PREDICCION DE ABANDONO DE CLIENTES (CHURN)
// Actividad 5 - Clasificacion Bayesiana Ingenua - Universidad SABES 2026
// C++ (Cxxdroid). Estandar C++11 o superior.
// Columnas: meses_servicio, llamadas_soporte, gasto_mensual_usd, minutos_uso, clase
// Clases: 0 = SE QUEDA, 1 = ABANDONA

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
    // Dataset (40 registros): meses_servicio, llamadas_soporte, gasto_mensual_usd, minutos_uso, clase
    vector<vector<double>> datos = {
        {16.0, 2.0, 95.2, 368.0, 0}, {14.0, 1.0, 74.7, 277.0, 0}, {7.0, 8.0, 22.6, 260.0, 1},
        {26.0, 4.0, 41.7, 392.0, 0}, {7.0, 8.0, 39.6, 240.0, 1}, {8.0, 4.0, 46.1, 249.0, 1},
        {2.0, 8.0, 49.4, 464.0, 1}, {36.0, 5.0, 94.2, 535.0, 0}, {26.0, 1.0, 80.3, 477.0, 0},
        {33.0, 1.0, 61.9, 678.0, 0}, {40.0, 4.0, 63.2, 637.0, 0}, {35.0, 1.0, 63.2, 466.0, 0},
        {13.0, 4.0, 29.0, 298.0, 1}, {37.0, 3.0, 78.4, 477.0, 0}, {24.0, 2.0, 88.2, 546.0, 0},
        {48.0, 3.0, 53.3, 228.0, 1}, {34.0, 0.0, 82.2, 259.0, 0}, {23.0, 1.0, 65.4, 437.0, 0},
        {23.0, 2.0, 69.4, 425.0, 0}, {30.0, 2.0, 79.9, 630.0, 0}, {2.0, 2.0, 24.4, 321.0, 1},
        {40.0, 2.0, 79.8, 535.0, 0}, {13.0, 8.0, 34.9, 306.0, 1}, {15.0, 10.0, 29.0, 249.0, 1},
        {26.0, 3.0, 83.0, 583.0, 0}, {2.0, 9.0, 57.6, 346.0, 1}, {13.0, 6.0, 32.5, 187.0, 1},
        {35.0, 2.0, 67.8, 509.0, 0}, {18.0, 2.0, 61.9, 524.0, 0}, {27.0, 6.0, 54.1, 430.0, 0},
        {5.0, 6.0, 46.3, 248.0, 1}, {16.0, 5.0, 62.4, 215.0, 1}, {15.0, 7.0, 32.7, 253.0, 1},
        {47.0, 4.0, 88.1, 548.0, 0}, {16.0, 2.0, 69.7, 203.0, 1}, {16.0, 2.0, 75.5, 498.0, 0},
        {30.0, 2.0, 50.3, 677.0, 0}, {35.0, 1.0, 88.3, 476.0, 0}, {40.0, 8.0, 54.8, 268.0, 1},
        {17.0, 1.0, 66.9, 490.0, 0}
    };
    vector<string> nombres = {"SE QUEDA", "ABANDONA"};
    int k = (int) nombres.size();

    string linea = "======================================================================";
    cout << linea << endl;
    cout << "ESCENARIO 2: PREDICCION DE ABANDONO DE CLIENTES (CHURN)" << endl;
    cout << linea << endl;
    cout << "\nDescripcion del problema:" << endl;
    cout << "Una empresa de telecomunicaciones quiere predecir que clientes tienen" << endl;
    cout << "mayor riesgo de cancelar su servicio (abandono/churn)." << endl;
    cout << "\nVariables analizadas:" << endl;
    cout << "- Meses de servicio con la empresa" << endl;
    cout << "- Llamadas al soporte tecnico" << endl;
    cout << "- Gasto mensual promedio (USD)" << endl;
    cout << "- Minutos de uso mensual" << endl;
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
    cout << "                      Pred: SE QUEDA      Pred: ABANDONA      " << endl;
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

    // Analisis de costos de los errores (clase 1 = ABANDONA es el evento que queremos detectar)
    long costoFN = 4000;  // MXN - ingreso anual perdido del cliente
    long costoFP = 400;  // MXN - descuento de retencion innecesario
    long fn = matriz[1][0];
    long fp = matriz[0][1];
    long costoTotal = fn * costoFN + fp * costoFP;
    cout << "\nAnalisis de Costos:" << endl;
    cout << "Falsos Negativos (clientes que abandonan sin ser detectados): " << fn << " x $" << miles(costoFN) << " = $" << miles(fn * costoFN) << endl;
    cout << "Falsos Positivos (clientes leales marcados en riesgo): " << fp << " x $" << miles(costoFP) << " = $" << miles(fp * costoFP) << endl;
    cout << "Costo Total de los errores: $" << miles(costoTotal) << endl;

    // ===================================================================
    // PREDICCIONES CON NUEVOS DATOS
    // >>> MODIFICA AQUI: cambia los numeros para crear tus 3 casos <<<
    // Orden: meses_servicio, llamadas_soporte, gasto_mensual_usd, minutos_uso
    // ===================================================================
    vector<vector<double>> nuevosCasos = {
        {29.0, 2.0, 73.1, 495.0},
        {14.0, 6.0, 42.8, 271.0},
        {21.0, 4.0, 57.9, 383.0}
    };
    vector<string> descripciones = {
        "Cliente A - antiguo y satisfecho",
        "Cliente B - nuevo con muchas quejas",
        "Cliente C - caso intermedio"
    };
    vector<string> variables = {"Meses de servicio con la empresa", "Llamadas al soporte tecnico", "Gasto mensual promedio (USD)", "Minutos de uso mensual"};
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
            cout << "     - Llamada de retencion personalizada" << endl;
            cout << "     - Ofrecer plan con descuento" << endl;
            cout << "     - Revisar historial de quejas" << endl;
        }
    }

    cout << "\n" << linea << "\nPREGUNTAS DE ANALISIS:\n" << linea << endl;
    cout << "1. Cual caracteristica es mas indicativa de abandono?" << endl;
    cout << "2. Que estrategias de retencion recomendarias para clientes en riesgo?" << endl;
    cout << "3. Cual es el costo de un falso negativo (predecir que se queda pero abandona)?" << endl;
    cout << "4. Como afecta el numero de llamadas a soporte en la decision del cliente?" << endl;
    return 0;
}
