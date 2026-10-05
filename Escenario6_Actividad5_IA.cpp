// ESCENARIO 6: LOGISTICA INTERNACIONAL - PREDICCION DE RETRASOS
// Actividad 5 - Clasificacion Bayesiana Ingenua - Universidad SABES 2026
// C++ (Cxxdroid). Estandar C++11 o superior.
// Columnas: distancia_km, peso_kg, documentos_aduanales, dias_procesamiento, clase
// Clases: 0 = A TIEMPO, 1 = RETRASO

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
    // Dataset (40 registros): distancia_km, peso_kg, documentos_aduanales, dias_procesamiento, clase
    vector<vector<double>> datos = {
        {7649.0, 2444.0, 10.0, 7.0, 1}, {2932.0, 4531.0, 3.0, 6.0, 1}, {16298.0, 2517.0, 8.0, 2.0, 1},
        {3129.0, 256.0, 3.0, 1.0, 0}, {16273.0, 2103.0, 12.0, 6.0, 1}, {4829.0, 970.0, 3.0, 2.0, 0},
        {5735.0, 515.0, 9.0, 2.0, 0}, {3325.0, 1231.0, 2.0, 2.0, 0}, {3801.0, 156.0, 7.0, 3.0, 0},
        {7599.0, 2619.0, 7.0, 3.0, 1}, {6958.0, 451.0, 7.0, 5.0, 0}, {5325.0, 331.0, 2.0, 3.0, 0},
        {9481.0, 950.0, 3.0, 1.0, 1}, {2908.0, 1026.0, 5.0, 3.0, 0}, {6803.0, 1546.0, 11.0, 8.0, 1},
        {12948.0, 2365.0, 7.0, 6.0, 1}, {9340.0, 3293.0, 20.0, 5.0, 1}, {2954.0, 892.0, 9.0, 2.0, 0},
        {1427.0, 1104.0, 2.0, 1.0, 0}, {18218.0, 2320.0, 16.0, 8.0, 1}, {6074.0, 1886.0, 3.0, 5.0, 0},
        {2228.0, 1020.0, 4.0, 2.0, 0}, {5910.0, 1655.0, 3.0, 3.0, 0}, {5267.0, 1381.0, 7.0, 2.0, 0},
        {1508.0, 366.0, 6.0, 2.0, 0}, {537.0, 805.0, 4.0, 3.0, 0}, {15127.0, 4459.0, 5.0, 7.0, 1},
        {4311.0, 979.0, 8.0, 4.0, 0}, {4927.0, 500.0, 2.0, 3.0, 0}, {4224.0, 2526.0, 2.0, 1.0, 0},
        {4326.0, 146.0, 3.0, 2.0, 0}, {10083.0, 224.0, 5.0, 2.0, 0}, {5149.0, 1783.0, 10.0, 3.0, 1},
        {12353.0, 750.0, 7.0, 10.0, 1}, {3072.0, 477.0, 5.0, 4.0, 0}, {14079.0, 2020.0, 12.0, 6.0, 1},
        {9041.0, 425.0, 3.0, 2.0, 0}, {17726.0, 4127.0, 7.0, 3.0, 1}, {7365.0, 628.0, 2.0, 4.0, 0},
        {13319.0, 4912.0, 15.0, 7.0, 1}
    };
    vector<string> nombres = {"A TIEMPO", "RETRASO"};
    int k = (int) nombres.size();

    string linea = "======================================================================";
    cout << linea << endl;
    cout << "ESCENARIO 6: LOGISTICA INTERNACIONAL - PREDICCION DE RETRASOS" << endl;
    cout << linea << endl;
    cout << "\nDescripcion del problema:" << endl;
    cout << "Una empresa de logistica internacional necesita predecir que envios" << endl;
    cout << "tienen alto riesgo de retrasarse para tomar medidas preventivas." << endl;
    cout << "\nVariables analizadas:" << endl;
    cout << "- Distancia del envio (km)" << endl;
    cout << "- Peso de la carga (kg)" << endl;
    cout << "- Documentos aduanales requeridos" << endl;
    cout << "- Dias de procesamiento en aduana" << endl;
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
    cout << "                      Pred: A TIEMPO      Pred: RETRASO       " << endl;
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

    // Analisis de costos de los errores (clase 1 = RETRASO es el evento que queremos detectar)
    long costoFN = 5000;  // MXN - penalizacion y compensacion al cliente
    long costoFP = 500;  // MXN - medidas preventivas innecesarias
    long fn = matriz[1][0];
    long fp = matriz[0][1];
    long costoTotal = fn * costoFN + fp * costoFP;
    cout << "\nAnalisis de Costos:" << endl;
    cout << "Falsos Negativos (retrasos no previstos): " << fn << " x $" << miles(costoFN) << " = $" << miles(fn * costoFN) << endl;
    cout << "Falsos Positivos (alarmas falsas): " << fp << " x $" << miles(costoFP) << " = $" << miles(fp * costoFP) << endl;
    cout << "Costo Total de los errores: $" << miles(costoTotal) << endl;

    // ===================================================================
    // PREDICCIONES CON NUEVOS DATOS
    // >>> MODIFICA AQUI: cambia los numeros para crear tus 3 casos <<<
    // Orden: distancia_km, peso_kg, documentos_aduanales, dias_procesamiento
    // ===================================================================
    vector<vector<double>> nuevosCasos = {
        {4553.0, 831.0, 4.0, 3.0},
        {11581.0, 2671.0, 10.0, 6.0},
        {8067.0, 1751.0, 7.0, 4.0}
    };
    vector<string> descripciones = {
        "Envio Nacional - estandar",
        "Envio Internacional - complejo",
        "Envio Regional - moderado"
    };
    vector<string> variables = {"Distancia del envio (km)", "Peso de la carga (kg)", "Documentos aduanales requeridos", "Dias de procesamiento en aduana"};
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
            cout << "     - Acelerar tramites aduanales" << endl;
            cout << "     - Notificar al cliente sobre posible retraso" << endl;
            cout << "     - Asignar courier premium o ruta alterna" << endl;
        }
    }

    cout << "\n" << linea << "\nPREGUNTAS DE ANALISIS:\n" << linea << endl;
    cout << "1. Cual factor logistico impacta mas en los retrasos?" << endl;
    cout << "2. Como optimizarias la relacion costo-beneficio del modelo?" << endl;
    cout << "3. Que KPIs logisticos adicionales deberian considerarse?" << endl;
    cout << "4. Como afectan las regulaciones aduanales al modelo predictivo?" << endl;
    return 0;
}
