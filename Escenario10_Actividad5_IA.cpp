// ESCENARIO 10: CONTROL DE CALIDAD AVANZADO - 3 CATEGORIAS
// Actividad 5 - Clasificacion Bayesiana Ingenua - Universidad SABES 2026
// C++ (Cxxdroid). Estandar C++11 o superior.
// Columnas: espesor_mm, dureza_shore, rugosidad_ra, peso_g, clase
// Clases: 0 = DEFECTUOSO, 1 = ACEPTABLE, 2 = PREMIUM

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
    // Dataset (40 registros): espesor_mm, dureza_shore, rugosidad_ra, peso_g, clase
    vector<vector<double>> datos = {
        {4.79, 80.0, 1.9, 99.8, 0}, {4.97, 84.0, 0.3, 103.7, 1}, {4.85, 76.0, 1.9, 97.4, 0},
        {5.03, 86.0, 0.7, 101.7, 1}, {4.84, 77.0, 1.5, 96.2, 0}, {4.98, 84.0, 0.4, 101.4, 1},
        {4.96, 86.0, 0.4, 103.9, 2}, {5.01, 89.0, 0.5, 103.0, 2}, {4.98, 86.0, 0.6, 102.7, 1},
        {4.97, 86.0, 0.7, 102.3, 1}, {4.81, 76.0, 2.1, 98.4, 0}, {4.77, 76.0, 2.1, 99.3, 0},
        {4.83, 75.0, 1.1, 97.7, 0}, {4.99, 85.0, 0.8, 101.9, 1}, {5.02, 91.0, 0.3, 103.5, 2},
        {5.03, 90.0, 0.5, 104.3, 2}, {4.8, 72.0, 1.7, 97.1, 0}, {5.0, 90.0, 0.7, 104.0, 2},
        {4.79, 76.0, 1.7, 98.4, 0}, {5.03, 90.0, 0.2, 103.8, 2}, {5.03, 89.0, 1.0, 104.2, 2},
        {4.99, 83.0, 0.8, 101.8, 1}, {4.98, 80.0, 0.8, 103.5, 1}, {5.05, 86.0, 0.9, 101.7, 1},
        {4.76, 74.0, 2.0, 98.0, 0}, {5.0, 84.0, 0.5, 103.6, 1}, {5.01, 86.0, 0.6, 103.0, 2},
        {5.03, 88.0, 0.4, 103.2, 2}, {5.01, 89.0, 0.2, 103.9, 2}, {4.82, 75.0, 1.5, 98.6, 0},
        {4.99, 92.0, 0.9, 102.5, 1}, {5.03, 96.0, 0.3, 104.0, 2}, {4.86, 75.0, 1.8, 97.4, 0},
        {4.81, 77.0, 1.1, 97.6, 0}, {5.04, 86.0, 0.7, 102.2, 1}, {5.05, 95.0, 0.7, 103.5, 2},
        {5.02, 88.0, 0.5, 103.3, 2}, {4.79, 74.0, 1.2, 96.6, 0}, {4.75, 73.0, 1.4, 98.9, 0},
        {5.04, 87.0, 1.1, 103.2, 1}
    };
    vector<string> nombres = {"DEFECTUOSO", "ACEPTABLE", "PREMIUM"};
    int k = (int) nombres.size();

    string linea = "======================================================================";
    cout << linea << endl;
    cout << "ESCENARIO 10: CONTROL DE CALIDAD AVANZADO - 3 CATEGORIAS" << endl;
    cout << linea << endl;
    cout << "\nDescripcion del problema:" << endl;
    cout << "Una planta manufacturera necesita clasificar productos automaticamente" << endl;
    cout << "en categorias de calidad para optimizar su comercializacion." << endl;
    cout << "\nVariables analizadas:" << endl;
    cout << "- Espesor de la pieza (mm)" << endl;
    cout << "- Dureza Shore A" << endl;
    cout << "- Rugosidad superficial (Ra um)" << endl;
    cout << "- Peso (g)" << endl;
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
    cout << "                      Pred: DEFECTUOSO    Pred: ACEPTABLE     Pred: PREMIUM       " << endl;
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
    // Orden: espesor_mm, dureza_shore, rugosidad_ra, peso_g
    // ===================================================================
    vector<vector<double>> nuevosCasos = {
        {4.8, 75.0, 1.6, 98.0},
        {5.0, 85.0, 0.7, 102.5},
        {5.02, 90.0, 0.5, 103.7}
    };
    vector<string> descripciones = {
        "Pieza #1247",
        "Pieza #1248",
        "Pieza #1249"
    };
    vector<string> variables = {"Espesor de la pieza (mm)", "Dureza Shore A", "Rugosidad superficial (Ra um)", "Peso (g)"};
    int decimales[4] = {2, 0, 1, 1};

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
            cout << "     - Rechazar la pieza" << endl;
            cout << "     - Revisar parametros del proceso" << endl;
        }
        if (resultado == 2) {
            cout << "  ACCIONES RECOMENDADAS:" << endl;
            cout << "     - Enviar a linea de venta premium" << endl;
        }
    }

    cout << "\n" << linea << "\nPREGUNTAS DE ANALISIS:\n" << linea << endl;
    cout << "1. Cual parametro es mas critico para distinguir PREMIUM de ACEPTABLE?" << endl;
    cout << "2. Como afecta la clasificacion incorrecta al margen de utilidad?" << endl;
    cout << "3. Que mejoras de proceso reducirian los productos defectuosos?" << endl;
    cout << "4. Vale la pena invertir en sensores automaticos vs. inspeccion manual?" << endl;
    return 0;
}
