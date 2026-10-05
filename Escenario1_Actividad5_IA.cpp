// ESCENARIO 1: CONTROL DE CALIDAD EN MANUFACTURA
// Actividad 5 - Clasificacion Bayesiana Ingenua - Universidad SABES 2026
// C++ (Cxxdroid). Estandar C++11 o superior.
// Columnas: diametro_mm, longitud_mm, peso_g, temperatura_C, clase
// Clases: 0 = APTA, 1 = DEFECTUOSA

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
    // Dataset (40 registros): diametro_mm, longitud_mm, peso_g, temperatura_C, clase
    vector<vector<double>> datos = {
        {10.9, 48.7, 45.9, 199.0, 1}, {10.2, 52.0, 47.7, 202.0, 1}, {10.0, 50.0, 45.3, 179.0, 0},
        {10.1, 49.8, 45.3, 181.0, 0}, {10.0, 50.1, 45.2, 183.0, 0}, {9.9, 50.0, 44.8, 178.0, 0},
        {10.2, 50.1, 45.3, 184.0, 0}, {10.2, 50.2, 44.7, 184.0, 0}, {10.0, 50.4, 43.1, 173.0, 1},
        {10.3, 50.1, 45.1, 181.0, 0}, {10.1, 49.8, 45.4, 180.0, 0}, {9.8, 50.1, 45.2, 182.0, 0},
        {10.1, 49.8, 45.4, 179.0, 0}, {9.9, 50.1, 45.7, 182.0, 1}, {10.2, 49.8, 46.0, 172.0, 1},
        {10.4, 50.2, 45.0, 181.0, 0}, {9.3, 50.2, 45.0, 183.0, 1}, {9.7, 50.1, 45.4, 183.0, 0},
        {9.9, 50.4, 45.3, 184.0, 0}, {9.8, 50.0, 44.8, 180.0, 0}, {10.5, 50.1, 45.2, 180.0, 0},
        {9.5, 50.0, 44.4, 185.0, 1}, {9.6, 50.5, 49.2, 186.0, 1}, {10.2, 50.6, 44.8, 180.0, 0},
        {10.5, 50.0, 44.8, 181.0, 0}, {10.1, 49.9, 45.3, 181.0, 0}, {9.8, 50.3, 45.3, 183.0, 0},
        {9.7, 50.2, 45.2, 181.0, 0}, {9.3, 50.3, 45.4, 179.0, 1}, {10.7, 50.8, 45.8, 184.0, 1},
        {10.3, 50.0, 45.4, 182.0, 0}, {9.8, 50.1, 45.2, 180.0, 0}, {9.9, 50.4, 45.3, 182.0, 0},
        {9.7, 50.2, 45.4, 182.0, 0}, {11.6, 52.7, 44.3, 187.0, 1}, {10.0, 50.5, 45.1, 181.0, 0},
        {10.2, 48.3, 45.4, 194.0, 1}, {10.4, 50.0, 45.6, 181.0, 0}, {9.3, 49.0, 41.1, 181.0, 1},
        {11.2, 48.7, 46.4, 183.0, 1}
    };
    vector<string> nombres = {"APTA", "DEFECTUOSA"};
    int k = (int) nombres.size();

    string linea = "======================================================================";
    cout << linea << endl;
    cout << "ESCENARIO 1: CONTROL DE CALIDAD EN MANUFACTURA" << endl;
    cout << linea << endl;
    cout << "\nDescripcion del problema:" << endl;
    cout << "Una fabrica produce piezas metalicas y necesita clasificarlas como" << endl;
    cout << "APTAS o DEFECTUOSAS segun sus dimensiones y temperatura de fundicion." << endl;
    cout << "\nVariables analizadas:" << endl;
    cout << "- Diametro (mm)" << endl;
    cout << "- Longitud (mm)" << endl;
    cout << "- Peso (g)" << endl;
    cout << "- Temperatura de fundicion (C)" << endl;
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
    cout << "                      Pred: APTA          Pred: DEFECTUOSA    " << endl;
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

    // Analisis de costos de los errores (clase 1 = DEFECTUOSA es el evento que queremos detectar)
    long costoFN = 2500;  // MXN - reclamo y devolucion del cliente
    long costoFP = 300;  // MXN - reproceso o desperdicio de una pieza buena
    long fn = matriz[1][0];
    long fp = matriz[0][1];
    long costoTotal = fn * costoFN + fp * costoFP;
    cout << "\nAnalisis de Costos:" << endl;
    cout << "Falsos Negativos (piezas defectuosas que pasan al cliente): " << fn << " x $" << miles(costoFN) << " = $" << miles(fn * costoFN) << endl;
    cout << "Falsos Positivos (piezas buenas rechazadas): " << fp << " x $" << miles(costoFP) << " = $" << miles(fp * costoFP) << endl;
    cout << "Costo Total de los errores: $" << miles(costoTotal) << endl;

    // ===================================================================
    // PREDICCIONES CON NUEVOS DATOS
    // >>> MODIFICA AQUI: cambia los numeros para crear tus 3 casos <<<
    // Orden: diametro_mm, longitud_mm, peso_g, temperatura_C
    // ===================================================================
    vector<vector<double>> nuevosCasos = {
        {10.2, 50.1, 45.2, 181.0},
        {11.6, 51.4, 47.3, 193.0},
        {10.6, 50.6, 45.9, 186.0}
    };
    vector<string> descripciones = {
        "Pieza #A101 - lote normal",
        "Pieza #A102 - lote sospechoso",
        "Pieza #A103 - caso intermedio"
    };
    vector<string> variables = {"Diametro (mm)", "Longitud (mm)", "Peso (g)", "Temperatura de fundicion (C)"};
    int decimales[4] = {1, 1, 1, 0};

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
            cout << "     - Separar la pieza para inspeccion manual" << endl;
            cout << "     - Revisar temperatura del horno" << endl;
            cout << "     - Verificar calibracion del molde" << endl;
        }
    }

    cout << "\n" << linea << "\nPREGUNTAS DE ANALISIS:\n" << linea << endl;
    cout << "1. Que tan confiable es el modelo con este accuracy?" << endl;
    cout << "2. Cual seria el costo de clasificar una pieza DEFECTUOSA como APTA?" << endl;
    cout << "3. Que caracteristica parece ser mas determinante para la clasificacion?" << endl;
    cout << "4. Como podrias mejorar el modelo con mas datos?" << endl;
    return 0;
}
