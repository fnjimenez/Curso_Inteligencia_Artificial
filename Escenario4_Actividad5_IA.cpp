// ESCENARIO 4: DETECCION DE FRAUDE EN TRANSACCIONES BANCARIAS
// Actividad 5 - Clasificacion Bayesiana Ingenua - Universidad SABES 2026
// C++ (Cxxdroid). Estandar C++11 o superior.
// Columnas: monto_usd, hora, distancia_km, intentos_fallidos, clase
// Clases: 0 = LEGITIMA, 1 = FRAUDE

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
    // Dataset (40 registros): monto_usd, hora, distancia_km, intentos_fallidos, clase
    vector<vector<double>> datos = {
        {1112.54, 8.0, 127.0, 1.0, 0}, {201.65, 11.0, 517.0, 1.0, 0}, {479.32, 21.0, 408.6, 0.0, 0},
        {4388.2, 3.0, 2199.5, 2.0, 1}, {1302.47, 6.0, 287.6, 2.0, 1}, {114.3, 13.0, 2620.4, 2.0, 1},
        {3545.27, 14.0, 1835.2, 3.0, 1}, {1185.95, 9.0, 1017.1, 2.0, 0}, {1455.14, 6.0, 920.8, 0.0, 0},
        {3168.67, 6.0, 1672.4, 2.0, 1}, {472.84, 3.0, 419.4, 0.0, 1}, {2557.53, 5.0, 1767.1, 2.0, 1},
        {4185.43, 12.0, 1436.5, 1.0, 1}, {5.22, 15.0, 295.4, 1.0, 0}, {121.72, 17.0, 639.8, 0.0, 0},
        {506.43, 12.0, 126.9, 1.0, 0}, {968.55, 20.0, 349.3, 1.0, 0}, {62.05, 12.0, 235.3, 0.0, 0},
        {82.24, 10.0, 315.8, 0.0, 0}, {440.34, 20.0, 348.6, 1.0, 0}, {696.24, 11.0, 341.9, 0.0, 0},
        {894.11, 6.0, 721.6, 0.0, 0}, {393.03, 13.0, 334.3, 0.0, 0}, {3779.73, 5.0, 1011.8, 0.0, 1},
        {533.97, 13.0, 819.4, 0.0, 0}, {1009.15, 20.0, 241.5, 0.0, 0}, {534.29, 19.0, 251.7, 0.0, 0},
        {628.96, 12.0, 661.4, 1.0, 0}, {753.8, 8.0, 1287.7, 2.0, 1}, {1564.23, 5.0, 37.6, 0.0, 1},
        {1440.44, 18.0, 347.3, 1.0, 0}, {2779.16, 4.0, 2873.4, 2.0, 1}, {3634.87, 8.0, 1050.6, 1.0, 1},
        {1076.07, 17.0, 30.6, 1.0, 0}, {1767.73, 6.0, 1981.8, 3.0, 1}, {1077.89, 4.0, 1902.1, 1.0, 1},
        {2201.37, 19.0, 247.9, 0.0, 1}, {1633.37, 3.0, 635.2, 0.0, 0}, {743.7, 7.0, 643.8, 1.0, 0},
        {422.37, 17.0, 247.5, 0.0, 0}
    };
    vector<string> nombres = {"LEGITIMA", "FRAUDE"};
    int k = (int) nombres.size();

    string linea = "======================================================================";
    cout << linea << endl;
    cout << "ESCENARIO 4: DETECCION DE FRAUDE EN TRANSACCIONES BANCARIAS" << endl;
    cout << linea << endl;
    cout << "\nDescripcion del problema:" << endl;
    cout << "Un banco necesita identificar transacciones potencialmente fraudulentas" << endl;
    cout << "en tiempo real para proteger a sus clientes." << endl;
    cout << "\nVariables analizadas:" << endl;
    cout << "- Monto de la transaccion (USD)" << endl;
    cout << "- Hora del dia (0-23)" << endl;
    cout << "- Distancia de la ubicacion usual (km)" << endl;
    cout << "- Intentos previos fallidos" << endl;
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
    cout << "                      Pred: LEGITIMA      Pred: FRAUDE        " << endl;
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

    // Analisis de costos de los errores (clase 1 = FRAUDE es el evento que queremos detectar)
    long costoFN = 15000;  // MXN - monto robado que el banco reembolsa
    long costoFP = 250;  // MXN - llamada de verificacion y molestia del cliente
    long fn = matriz[1][0];
    long fp = matriz[0][1];
    long costoTotal = fn * costoFN + fp * costoFP;
    cout << "\nAnalisis de Costos:" << endl;
    cout << "Falsos Negativos (fraudes aprobados): " << fn << " x $" << miles(costoFN) << " = $" << miles(fn * costoFN) << endl;
    cout << "Falsos Positivos (transacciones legitimas bloqueadas): " << fp << " x $" << miles(costoFP) << " = $" << miles(fp * costoFP) << endl;
    cout << "Costo Total de los errores: $" << miles(costoTotal) << endl;

    // ===================================================================
    // PREDICCIONES CON NUEVOS DATOS
    // >>> MODIFICA AQUI: cambia los numeros para crear tus 3 casos <<<
    // Orden: monto_usd, hora, distancia_km, intentos_fallidos
    // ===================================================================
    vector<vector<double>> nuevosCasos = {
        {692.79, 13.0, 440.7, 0.0},
        {2330.84, 8.0, 1414.4, 1.0},
        {1511.81, 10.0, 927.6, 1.0}
    };
    vector<string> descripciones = {
        "Compra en supermercado - tarde",
        "Compra de madrugada en otro pais",
        "Compra mediana - caso intermedio"
    };
    vector<string> variables = {"Monto de la transaccion (USD)", "Hora del dia (0-23)", "Distancia de la ubicacion usual (km)", "Intentos previos fallidos"};
    int decimales[4] = {2, 0, 1, 0};

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
            cout << "     - Bloquear la transaccion" << endl;
            cout << "     - Enviar SMS de verificacion al cliente" << endl;
            cout << "     - Revisar movimientos recientes" << endl;
        }
    }

    cout << "\n" << linea << "\nPREGUNTAS DE ANALISIS:\n" << linea << endl;
    cout << "1. Cual es el costo de un falso positivo (bloquear una transaccion legitima)?" << endl;
    cout << "2. Cual es el costo de un falso negativo (aprobar un fraude)?" << endl;
    cout << "3. Que patron caracteriza mejor las transacciones fraudulentas?" << endl;
    cout << "4. Como balancear seguridad vs. experiencia del cliente?" << endl;
    return 0;
}
