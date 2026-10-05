// ESCENARIO 9: MANTENIMIENTO PREDICTIVO - DETECCION DE FALLAS
// Actividad 5 - Clasificacion Bayesiana Ingenua - Universidad SABES 2026
// C++ (Cxxdroid). Estandar C++11 o superior.
// Columnas: vibracion_mm_s, temperatura_motor_C, horas_operacion, dias_desde_mant, clase
// Clases: 0 = NORMAL, 1 = FALLA

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
    // Dataset (40 registros): vibracion_mm_s, temperatura_motor_C, horas_operacion, dias_desde_mant, clase
    vector<vector<double>> datos = {
        {4.0, 72.0, 1216.0, 61.0, 0}, {6.7, 102.0, 8669.0, 245.0, 1}, {6.8, 65.0, 2621.0, 17.0, 0},
        {3.4, 91.0, 3868.0, 104.0, 1}, {8.9, 80.0, 4709.0, 172.0, 1}, {2.5, 61.0, 721.0, 52.0, 0},
        {1.9, 86.0, 2565.0, 16.0, 0}, {4.3, 93.0, 839.0, 155.0, 0}, {3.6, 64.0, 4442.0, 227.0, 1},
        {4.0, 56.0, 344.0, 55.0, 0}, {3.9, 58.0, 2032.0, 12.0, 0}, {12.3, 104.0, 2700.0, 211.0, 1},
        {1.4, 55.0, 2196.0, 5.0, 0}, {2.5, 67.0, 563.0, 122.0, 0}, {6.4, 69.0, 1037.0, 36.0, 0},
        {1.8, 74.0, 1234.0, 19.0, 0}, {1.6, 57.0, 2801.0, 46.0, 0}, {5.5, 65.0, 760.0, 77.0, 0},
        {6.6, 64.0, 4691.0, 137.0, 0}, {10.0, 88.0, 7978.0, 174.0, 1}, {10.4, 92.0, 6241.0, 203.0, 1},
        {2.1, 55.0, 1560.0, 33.0, 0}, {8.2, 84.0, 5986.0, 210.0, 1}, {12.1, 92.0, 4436.0, 216.0, 1},
        {2.1, 68.0, 2057.0, 30.0, 0}, {10.3, 73.0, 3917.0, 196.0, 1}, {4.2, 76.0, 2009.0, 58.0, 0},
        {5.6, 67.0, 1186.0, 63.0, 0}, {1.3, 75.0, 788.0, 22.0, 0}, {9.0, 109.0, 4835.0, 272.0, 1},
        {4.2, 63.0, 681.0, 77.0, 0}, {5.0, 98.0, 3958.0, 68.0, 1}, {7.7, 91.0, 2290.0, 105.0, 0},
        {3.4, 77.0, 1863.0, 56.0, 0}, {2.8, 55.0, 658.0, 56.0, 0}, {7.0, 108.0, 6194.0, 33.0, 1},
        {14.1, 105.0, 6128.0, 166.0, 1}, {13.9, 112.0, 4024.0, 214.0, 1}, {5.1, 68.0, 1001.0, 64.0, 0},
        {8.8, 103.0, 4911.0, 225.0, 1}
    };
    vector<string> nombres = {"NORMAL", "FALLA"};
    int k = (int) nombres.size();

    string linea = "======================================================================";
    cout << linea << endl;
    cout << "ESCENARIO 9: MANTENIMIENTO PREDICTIVO - DETECCION DE FALLAS" << endl;
    cout << linea << endl;
    cout << "\nDescripcion del problema:" << endl;
    cout << "Una planta industrial necesita predecir fallas en maquinaria critica" << endl;
    cout << "para implementar mantenimiento preventivo y evitar paros no programados." << endl;
    cout << "\nVariables analizadas:" << endl;
    cout << "- Nivel de vibracion (mm/s)" << endl;
    cout << "- Temperatura del motor (C)" << endl;
    cout << "- Horas de operacion acumuladas" << endl;
    cout << "- Dias desde el ultimo mantenimiento" << endl;
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
    cout << "                      Pred: NORMAL        Pred: FALLA         " << endl;
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

    // Analisis de costos de los errores (clase 1 = FALLA es el evento que queremos detectar)
    long costoFN = 80000;  // MXN - paro no programado de la linea
    long costoFP = 6000;  // MXN - mano de obra y refacciones adelantadas
    long fn = matriz[1][0];
    long fp = matriz[0][1];
    long costoTotal = fn * costoFN + fp * costoFP;
    cout << "\nAnalisis de Costos:" << endl;
    cout << "Falsos Negativos (fallas no detectadas): " << fn << " x $" << miles(costoFN) << " = $" << miles(fn * costoFN) << endl;
    cout << "Falsos Positivos (mantenimientos innecesarios): " << fp << " x $" << miles(costoFP) << " = $" << miles(fp * costoFP) << endl;
    cout << "Costo Total de los errores: $" << miles(costoTotal) << endl;

    // ===================================================================
    // PREDICCIONES CON NUEVOS DATOS
    // >>> MODIFICA AQUI: cambia los numeros para crear tus 3 casos <<<
    // Orden: vibracion_mm_s, temperatura_motor_C, horas_operacion, dias_desde_mant
    // ===================================================================
    vector<vector<double>> nuevosCasos = {
        {3.8, 68.0, 1571.0, 57.0},
        {9.0, 94.0, 5187.0, 184.0},
        {6.4, 81.0, 3379.0, 120.0}
    };
    vector<string> descripciones = {
        "Maquina CNC-01",
        "Prensa hidraulica-05",
        "Torno industrial-03"
    };
    vector<string> variables = {"Nivel de vibracion (mm/s)", "Temperatura del motor (C)", "Horas de operacion acumuladas", "Dias desde el ultimo mantenimiento"};
    int decimales[4] = {1, 0, 0, 0};

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
            cout << "     - Programar mantenimiento inmediato" << endl;
            cout << "     - Revisar rodamientos y lubricacion" << endl;
            cout << "     - Preparar equipo de respaldo" << endl;
        }
    }

    cout << "\n" << linea << "\nPREGUNTAS DE ANALISIS:\n" << linea << endl;
    cout << "1. Cual parametro es el mejor indicador de falla inminente?" << endl;
    cout << "2. Conviene mas el mantenimiento preventivo o el predictivo? Por que?" << endl;
    cout << "3. Como justificar la inversion en sensores con este modelo?" << endl;
    cout << "4. Que impacto tiene en el OEE (Overall Equipment Effectiveness)?" << endl;
    return 0;
}
