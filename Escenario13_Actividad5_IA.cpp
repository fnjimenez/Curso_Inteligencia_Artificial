// ESCENARIO 13: LOGISTICA - QUIEBRE DE INVENTARIO (STOCKOUT)
// Actividad 5 - Clasificacion Bayesiana Ingenua - Universidad SABES 2026
// C++ (Cxxdroid). Estandar C++11 o superior.
// Columnas: demanda_diaria, inventario_actual, lead_time_reposicion, variabilidad_pct, clase
// Clases: 0 = SIN QUIEBRE, 1 = QUIEBRE

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
    // Dataset (40 registros): demanda_diaria, inventario_actual, lead_time_reposicion, variabilidad_pct, clase
    vector<vector<double>> datos = {
        {147.0, 1610.0, 6.0, 20.0, 1}, {194.0, 640.0, 7.0, 8.0, 1}, {147.0, 2530.0, 3.0, 5.0, 0},
        {151.0, 1720.0, 1.0, 6.0, 0}, {180.0, 1510.0, 3.0, 19.0, 0}, {157.0, 910.0, 3.0, 49.0, 1},
        {52.0, 1380.0, 6.0, 21.0, 0}, {174.0, 1270.0, 5.0, 13.0, 0}, {105.0, 1340.0, 7.0, 15.0, 0},
        {114.0, 480.0, 6.0, 16.0, 0}, {248.0, 680.0, 6.0, 30.0, 1}, {127.0, 1800.0, 7.0, 14.0, 0},
        {120.0, 630.0, 7.0, 16.0, 0}, {242.0, 760.0, 6.0, 34.0, 1}, {90.0, 1570.0, 2.0, 13.0, 0},
        {102.0, 440.0, 5.0, 33.0, 1}, {99.0, 840.0, 6.0, 31.0, 1}, {148.0, 1530.0, 3.0, 28.0, 0},
        {123.0, 1270.0, 9.0, 16.0, 1}, {146.0, 1270.0, 3.0, 18.0, 0}, {70.0, 400.0, 4.0, 17.0, 0},
        {116.0, 1350.0, 4.0, 15.0, 0}, {278.0, 800.0, 11.0, 29.0, 1}, {122.0, 1240.0, 4.0, 12.0, 0},
        {285.0, 1070.0, 11.0, 32.0, 1}, {139.0, 1030.0, 3.0, 21.0, 0}, {110.0, 1760.0, 5.0, 17.0, 0},
        {169.0, 1200.0, 8.0, 16.0, 1}, {137.0, 290.0, 5.0, 41.0, 1}, {95.0, 2610.0, 2.0, 13.0, 0},
        {129.0, 1630.0, 3.0, 23.0, 0}, {71.0, 2360.0, 3.0, 14.0, 0}, {206.0, 1040.0, 3.0, 20.0, 0},
        {120.0, 350.0, 10.0, 36.0, 1}, {162.0, 2520.0, 5.0, 25.0, 0}, {143.0, 650.0, 8.0, 18.0, 0},
        {85.0, 1940.0, 4.0, 15.0, 0}, {214.0, 950.0, 9.0, 12.0, 1}, {237.0, 1340.0, 7.0, 39.0, 1},
        {73.0, 980.0, 5.0, 5.0, 0}
    };
    vector<string> nombres = {"SIN QUIEBRE", "QUIEBRE"};
    int k = (int) nombres.size();

    string linea = "======================================================================";
    cout << linea << endl;
    cout << "ESCENARIO 13: LOGISTICA - QUIEBRE DE INVENTARIO (STOCKOUT)" << endl;
    cout << linea << endl;
    cout << "\nDescripcion del problema:" << endl;
    cout << "Un centro de distribucion quiere saber que SKUs tienen riesgo de" << endl;
    cout << "quedarse sin existencias antes de que llegue la reposicion, para" << endl;
    cout << "priorizar compras urgentes." << endl;
    cout << "\nVariables analizadas:" << endl;
    cout << "- Demanda promedio diaria (unidades)" << endl;
    cout << "- Inventario actual (unidades)" << endl;
    cout << "- Lead time de reposicion (dias)" << endl;
    cout << "- Variabilidad de la demanda (%)" << endl;
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
    cout << "                      Pred: SIN QUIEBRE   Pred: QUIEBRE       " << endl;
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

    // Analisis de costos de los errores (clase 1 = QUIEBRE es el evento que queremos detectar)
    long costoFN = 6000;  // MXN - venta perdida y cliente insatisfecho
    long costoFP = 900;  // MXN - compra urgente innecesaria / exceso de inventario
    long fn = matriz[1][0];
    long fp = matriz[0][1];
    long costoTotal = fn * costoFN + fp * costoFP;
    cout << "\nAnalisis de Costos:" << endl;
    cout << "Falsos Negativos (quiebres no detectados): " << fn << " x $" << miles(costoFN) << " = $" << miles(fn * costoFN) << endl;
    cout << "Falsos Positivos (alertas falsas): " << fp << " x $" << miles(costoFP) << " = $" << miles(fp * costoFP) << endl;
    cout << "Costo Total de los errores: $" << miles(costoTotal) << endl;

    // ===================================================================
    // PREDICCIONES CON NUEVOS DATOS
    // >>> MODIFICA AQUI: cambia los numeros para crear tus 3 casos <<<
    // Orden: demanda_diaria, inventario_actual, lead_time_reposicion, variabilidad_pct
    // ===================================================================
    vector<vector<double>> nuevosCasos = {
        {110.0, 1800.0, 3.0, 12.0},
        {190.0, 700.0, 9.0, 35.0},
        {150.0, 1100.0, 6.0, 22.0}
    };
    vector<string> descripciones = {
        "SKU 1001 - rotacion normal",
        "SKU 2050 - alta demanda, poco inventario",
        "SKU 3307 - caso intermedio"
    };
    vector<string> variables = {"Demanda promedio diaria (unidades)", "Inventario actual (unidades)", "Lead time de reposicion (dias)", "Variabilidad de la demanda (%)"};
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
            cout << "     - Generar orden de compra urgente" << endl;
            cout << "     - Transferir stock de otro almacen" << endl;
            cout << "     - Revisar punto de reorden" << endl;
        }
    }

    cout << "\n" << linea << "\nPREGUNTAS DE ANALISIS:\n" << linea << endl;
    cout << "1. Cual variable explica mejor el riesgo de quiebre?" << endl;
    cout << "2. Como se relaciona este modelo con el punto de reorden?" << endl;
    cout << "3. Es peor un quiebre o una compra innecesaria? Por que?" << endl;
    cout << "4. Que dato agregarias (temporada, promociones, etc.)?" << endl;
    return 0;
}
