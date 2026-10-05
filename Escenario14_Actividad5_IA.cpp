// ESCENARIO 14: LOGISTICA - ENTREGAS FALLIDAS EN ULTIMA MILLA
// Actividad 5 - Clasificacion Bayesiana Ingenua - Universidad SABES 2026
// C++ (Cxxdroid). Estandar C++11 o superior.
// Columnas: distancia_ruta_km, paradas_ruta, ventana_horaria_h, incidencias_previas, clase
// Clases: 0 = ENTREGADA, 1 = FALLIDA

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
    // Dataset (40 registros): distancia_ruta_km, paradas_ruta, ventana_horaria_h, incidencias_previas, clase
    vector<vector<double>> datos = {
        {13.1, 10.0, 2.7, 4.0, 0}, {25.5, 10.0, 4.3, 3.0, 0}, {22.5, 16.0, 4.8, 3.0, 0},
        {20.6, 27.0, 5.3, 5.0, 0}, {43.9, 18.0, 4.5, 4.0, 1}, {23.5, 14.0, 1.8, 5.0, 0},
        {14.3, 24.0, 2.4, 6.0, 1}, {12.3, 21.0, 2.3, 3.0, 0}, {51.8, 34.0, 2.0, 4.0, 1},
        {15.1, 28.0, 1.8, 4.0, 1}, {28.1, 19.0, 2.2, 8.0, 1}, {24.4, 14.0, 3.9, 5.0, 0},
        {18.8, 19.0, 2.8, 4.0, 0}, {8.1, 10.0, 4.7, 2.0, 0}, {22.6, 9.0, 4.2, 4.0, 0},
        {25.5, 25.0, 1.8, 3.0, 1}, {8.5, 21.0, 4.6, 2.0, 0}, {39.5, 30.0, 1.5, 6.0, 1},
        {33.5, 20.0, 2.2, 6.0, 1}, {20.8, 19.0, 1.4, 6.0, 1}, {41.6, 41.0, 1.6, 6.0, 1},
        {29.5, 14.0, 3.4, 6.0, 1}, {26.8, 18.0, 3.1, 0.0, 0}, {9.1, 22.0, 5.3, 5.0, 0},
        {7.1, 14.0, 4.2, 4.0, 0}, {27.0, 17.0, 2.7, 5.0, 0}, {22.7, 23.0, 4.7, 4.0, 0},
        {22.6, 28.0, 2.1, 7.0, 1}, {25.6, 12.0, 5.0, 6.0, 0}, {46.5, 21.0, 3.3, 6.0, 1},
        {35.5, 24.0, 1.0, 8.0, 1}, {13.1, 12.0, 4.4, 3.0, 0}, {21.6, 22.0, 3.9, 3.0, 0},
        {18.6, 9.0, 4.6, 4.0, 0}, {24.7, 18.0, 2.6, 5.0, 0}, {29.6, 17.0, 2.1, 6.0, 1},
        {28.4, 23.0, 1.3, 8.0, 1}, {15.5, 16.0, 3.8, 8.0, 0}, {14.6, 16.0, 2.3, 0.0, 0},
        {9.8, 13.0, 6.3, 4.0, 0}
    };
    vector<string> nombres = {"ENTREGADA", "FALLIDA"};
    int k = (int) nombres.size();

    string linea = "======================================================================";
    cout << linea << endl;
    cout << "ESCENARIO 14: LOGISTICA - ENTREGAS FALLIDAS EN ULTIMA MILLA" << endl;
    cout << linea << endl;
    cout << "\nDescripcion del problema:" << endl;
    cout << "Una paqueteria urbana quiere predecir que rutas tendran entregas" << endl;
    cout << "fallidas (cliente ausente, direccion no encontrada, fuera de" << endl;
    cout << "horario) para reprogramar o reasignar antes de salir." << endl;
    cout << "\nVariables analizadas:" << endl;
    cout << "- Distancia de la ruta (km)" << endl;
    cout << "- Numero de paradas en la ruta" << endl;
    cout << "- Ventana horaria de entrega acordada (horas)" << endl;
    cout << "- Incidencias previas en la zona" << endl;
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
    cout << "                      Pred: ENTREGADA     Pred: FALLIDA       " << endl;
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

    // Analisis de costos de los errores (clase 1 = FALLIDA es el evento que queremos detectar)
    long costoFN = 350;  // MXN - segundo intento + queja del cliente
    long costoFP = 80;  // MXN - llamada de confirmacion innecesaria
    long fn = matriz[1][0];
    long fp = matriz[0][1];
    long costoTotal = fn * costoFN + fp * costoFP;
    cout << "\nAnalisis de Costos:" << endl;
    cout << "Falsos Negativos (entregas fallidas no detectadas): " << fn << " x $" << miles(costoFN) << " = $" << miles(fn * costoFN) << endl;
    cout << "Falsos Positivos (alertas falsas): " << fp << " x $" << miles(costoFP) << " = $" << miles(fp * costoFP) << endl;
    cout << "Costo Total de los errores: $" << miles(costoTotal) << endl;

    // ===================================================================
    // PREDICCIONES CON NUEVOS DATOS
    // >>> MODIFICA AQUI: cambia los numeros para crear tus 3 casos <<<
    // Orden: distancia_ruta_km, paradas_ruta, ventana_horaria_h, incidencias_previas
    // ===================================================================
    vector<vector<double>> nuevosCasos = {
        {15.0, 12.0, 4.5, 2.0},
        {34.0, 28.0, 1.5, 7.0},
        {24.0, 19.0, 3.0, 4.0}
    };
    vector<string> descripciones = {
        "Ruta Centro - manana",
        "Ruta Periferia - tarde",
        "Ruta Mixta - caso intermedio"
    };
    vector<string> variables = {"Distancia de la ruta (km)", "Numero de paradas en la ruta", "Ventana horaria de entrega acordada (horas)", "Incidencias previas en la zona"};
    int decimales[4] = {1, 0, 1, 0};

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
            cout << "     - Llamar al cliente para confirmar" << endl;
            cout << "     - Reducir paradas de la ruta" << endl;
            cout << "     - Ofrecer punto de recoleccion" << endl;
        }
    }

    cout << "\n" << linea << "\nPREGUNTAS DE ANALISIS:\n" << linea << endl;
    cout << "1. Cual variable influye mas en una entrega fallida?" << endl;
    cout << "2. Por que la ventana horaria podria cambiar el resultado?" << endl;
    cout << "3. Conviene mas tener falsos positivos o falsos negativos aqui?" << endl;
    cout << "4. Que dato agregarias (dia de la semana, clima, tipo de cliente)?" << endl;
    return 0;
}
