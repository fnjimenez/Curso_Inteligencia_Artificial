// ESCENARIO 12: LOGISTICA - CUMPLIMIENTO DE PROVEEDORES (OTIF)
// Actividad 5 - Clasificacion Bayesiana Ingenua - Universidad SABES 2026
// C++ (Cxxdroid). Estandar C++11 o superior.
// Columnas: lead_time_dias, pedido_unidades, historial_otif_pct, pedidos_abiertos, clase
// Clases: 0 = CUMPLE OTIF, 1 = INCUMPLE

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
    // Dataset (40 registros): lead_time_dias, pedido_unidades, historial_otif_pct, pedidos_abiertos, clase
    vector<vector<double>> datos = {
        {6.0, 50.0, 100.0, 6.0, 0}, {7.0, 630.0, 93.9, 2.0, 0}, {4.0, 50.0, 89.0, 4.0, 0},
        {7.0, 640.0, 96.6, 3.0, 0}, {5.0, 610.0, 100.0, 7.0, 0}, {4.0, 760.0, 93.5, 1.0, 0},
        {8.0, 590.0, 94.9, 3.0, 0}, {7.0, 680.0, 96.7, 7.0, 0}, {5.0, 660.0, 94.0, 3.0, 0},
        {3.0, 590.0, 92.2, 2.0, 0}, {10.0, 750.0, 85.8, 10.0, 1}, {5.0, 750.0, 98.5, 7.0, 0},
        {6.0, 700.0, 95.6, 4.0, 0}, {2.0, 660.0, 97.5, 7.0, 0}, {7.0, 320.0, 88.6, 8.0, 1},
        {12.0, 340.0, 91.0, 6.0, 1}, {3.0, 400.0, 88.3, 7.0, 1}, {9.0, 920.0, 80.1, 6.0, 1},
        {2.0, 560.0, 98.5, 7.0, 0}, {6.0, 1320.0, 95.1, 11.0, 1}, {4.0, 100.0, 90.9, 4.0, 0},
        {11.0, 1220.0, 82.1, 11.0, 1}, {3.0, 320.0, 92.3, 4.0, 0}, {11.0, 1110.0, 84.1, 7.0, 1},
        {5.0, 790.0, 81.0, 8.0, 1}, {7.0, 120.0, 91.9, 3.0, 0}, {7.0, 820.0, 86.2, 1.0, 1},
        {6.0, 410.0, 90.9, 2.0, 1}, {4.0, 730.0, 95.8, 4.0, 0}, {6.0, 380.0, 98.7, 5.0, 0},
        {3.0, 190.0, 84.1, 7.0, 1}, {5.0, 750.0, 92.5, 5.0, 0}, {9.0, 50.0, 93.7, 7.0, 1},
        {3.0, 1150.0, 94.1, 3.0, 1}, {4.0, 250.0, 92.4, 3.0, 0}, {10.0, 490.0, 94.8, 10.0, 1},
        {4.0, 90.0, 97.3, 3.0, 0}, {3.0, 480.0, 82.3, 7.0, 1}, {5.0, 150.0, 92.3, 4.0, 0},
        {4.0, 600.0, 94.0, 3.0, 0}
    };
    vector<string> nombres = {"CUMPLE OTIF", "INCUMPLE"};
    int k = (int) nombres.size();

    string linea = "======================================================================";
    cout << linea << endl;
    cout << "ESCENARIO 12: LOGISTICA - CUMPLIMIENTO DE PROVEEDORES (OTIF)" << endl;
    cout << linea << endl;
    cout << "\nDescripcion del problema:" << endl;
    cout << "Una empresa de distribucion necesita anticipar que pedidos a" << endl;
    cout << "proveedores NO llegaran completos y a tiempo (OTIF) para" << endl;
    cout << "activar planes de contingencia antes de que falte producto." << endl;
    cout << "\nVariables analizadas:" << endl;
    cout << "- Lead time prometido por el proveedor (dias)" << endl;
    cout << "- Tamano del pedido (unidades)" << endl;
    cout << "- Historial de cumplimiento OTIF del proveedor (%)" << endl;
    cout << "- Pedidos abiertos con ese proveedor" << endl;
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
    cout << "                      Pred: CUMPLE OTIF   Pred: INCUMPLE      " << endl;
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

    // Analisis de costos de los errores (clase 1 = INCUMPLE es el evento que queremos detectar)
    long costoFN = 8000;  // MXN - paro de linea / venta perdida
    long costoFP = 1200;  // MXN - pedido de respaldo innecesario
    long fn = matriz[1][0];
    long fp = matriz[0][1];
    long costoTotal = fn * costoFN + fp * costoFP;
    cout << "\nAnalisis de Costos:" << endl;
    cout << "Falsos Negativos (pedidos incumplidos no detectados): " << fn << " x $" << miles(costoFN) << " = $" << miles(fn * costoFN) << endl;
    cout << "Falsos Positivos (alertas falsas): " << fp << " x $" << miles(costoFP) << " = $" << miles(fp * costoFP) << endl;
    cout << "Costo Total de los errores: $" << miles(costoTotal) << endl;

    // ===================================================================
    // PREDICCIONES CON NUEVOS DATOS
    // >>> MODIFICA AQUI: cambia los numeros para crear tus 3 casos <<<
    // Orden: lead_time_dias, pedido_unidades, historial_otif_pct, pedidos_abiertos
    // ===================================================================
    vector<vector<double>> nuevosCasos = {
        {5.0, 400.0, 96.0, 3.0},
        {9.0, 950.0, 84.5, 10.0},
        {6.0, 600.0, 90.0, 6.0}
    };
    vector<string> descripciones = {
        "Proveedor A - pedido regular",
        "Proveedor B - pedido grande, proveedor saturado",
        "Proveedor C - caso intermedio"
    };
    vector<string> variables = {"Lead time prometido por el proveedor (dias)", "Tamano del pedido (unidades)", "Historial de cumplimiento OTIF del proveedor (%)", "Pedidos abiertos con ese proveedor"};
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
            cout << "     - Confirmar fecha con el proveedor" << endl;
            cout << "     - Activar proveedor alterno" << endl;
            cout << "     - Aumentar inventario de seguridad" << endl;
        }
    }

    cout << "\n" << linea << "\nPREGUNTAS DE ANALISIS:\n" << linea << endl;
    cout << "1. Cual variable pesa mas para predecir un incumplimiento?" << endl;
    cout << "2. Por que el historial OTIF no basta por si solo?" << endl;
    cout << "3. Que pasaria si el costo de un incumplimiento fuera el doble?" << endl;
    cout << "4. Que otro dato del proveedor agregarias al modelo?" << endl;
    return 0;
}
