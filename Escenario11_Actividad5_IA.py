# ESCENARIO 11: MSA - ANALISIS DEL SISTEMA DE MEDICION
# Actividad 5 - Clasificacion Bayesiana Ingenua - Universidad SABES 2026
# Python (Pydroid 3). NO requiere instalar librerias: solo usa "math".
#
# Columnas del dataset: desv_std_mm, rango_mm, experiencia_meses, dias_calibracion, clase
# Clases: 0 = INADECUADO, 1 = MARGINAL, 2 = ACEPTABLE

import math


class NaiveBayes:
    def __init__(self):
        self.clases = {}

    def calcular_media(self, numeros):
        return sum(numeros) / len(numeros)

    def calcular_desviacion(self, numeros):
        media = self.calcular_media(numeros)
        varianza = sum((x - media) ** 2 for x in numeros) / len(numeros)
        return math.sqrt(varianza) if varianza > 0 else 0.0001

    def probabilidad_gaussiana(self, x, media, desviacion):
        exponente = math.exp(-((x - media) ** 2) / (2 * desviacion ** 2))
        return (1 / (math.sqrt(2 * math.pi) * desviacion)) * exponente

    def entrenar(self, X, y):
        for i in range(len(X)):
            clase = y[i]
            if clase not in self.clases:
                self.clases[clase] = []
            self.clases[clase].append(X[i])
        self.estadisticas = {}
        self.priors = {}
        for clase in sorted(self.clases):
            valores = self.clases[clase]
            self.priors[clase] = len(valores) / len(X)
            self.estadisticas[clase] = []
            for j in range(len(valores[0])):
                columna = [fila[j] for fila in valores]
                media = self.calcular_media(columna)
                desviacion = self.calcular_desviacion(columna)
                self.estadisticas[clase].append((media, desviacion))

    def predecir_proba(self, x):
        probabilidades = {}
        for clase in sorted(self.estadisticas):
            probabilidades[clase] = self.priors[clase]
            for j in range(len(x)):
                media, desviacion = self.estadisticas[clase][j]
                probabilidades[clase] *= self.probabilidad_gaussiana(x[j], media, desviacion)
        total = sum(probabilidades.values())
        if total > 0:
            for clase in probabilidades:
                probabilidades[clase] /= total
        return probabilidades

    def predecir(self, x):
        probabilidades = self.predecir_proba(x)
        return max(probabilidades, key=probabilidades.get)


# Dataset (40 registros): desv_std_mm, rango_mm, experiencia_meses, dias_calibracion, clase
datos = [
    [0.006, 0.1, 63, 2, 2], [0.031, 0.09, 50, 23, 2], [0.101, 0.23, 23, 126, 0],
    [0.057, 0.22, 8, 52, 0], [0.075, 0.35, 13, 153, 0], [0.071, 0.26, 2, 140, 0],
    [0.08, 0.29, 15, 113, 0], [0.042, 0.24, 33, 54, 1], [0.079, 0.26, 4, 146, 0],
    [0.058, 0.12, 19, 110, 1], [0.04, 0.13, 25, 82, 1], [0.04, 0.08, 49, 50, 1],
    [0.023, 0.02, 55, 55, 2], [0.061, 0.16, 20, 41, 1], [0.061, 0.12, 26, 112, 1],
    [0.101, 0.28, 6, 145, 0], [0.03, 0.06, 61, 20, 2], [0.077, 0.32, 9, 132, 0],
    [0.051, 0.15, 38, 35, 1], [0.088, 0.29, 11, 99, 0], [0.083, 0.3, 18, 133, 0],
    [0.01, 0.06, 47, 12, 2], [0.084, 0.14, 26, 93, 1], [0.011, 0.04, 72, 7, 2],
    [0.06, 0.12, 33, 78, 1], [0.025, 0.06, 42, 11, 2], [0.069, 0.24, 7, 151, 0],
    [0.032, 0.08, 42, 13, 2], [0.108, 0.26, 11, 155, 0], [0.047, 0.15, 44, 63, 1],
    [0.014, 0.19, 46, 17, 2], [0.073, 0.22, 10, 88, 0], [0.02, 0.03, 40, 12, 2],
    [0.021, 0.03, 44, 38, 2], [0.007, 0.02, 54, 8, 2], [0.035, 0.05, 23, 48, 1],
    [0.098, 0.27, 5, 87, 0], [0.068, 0.13, 18, 83, 1], [0.026, 0.04, 36, 41, 2],
    [0.064, 0.13, 29, 86, 1]
]

nombres = ['INADECUADO', 'MARGINAL', 'ACEPTABLE']

print("=" * 70)
print("ESCENARIO 11: MSA - ANALISIS DEL SISTEMA DE MEDICION")
print("=" * 70)
print("\nDescripcion del problema:")
print("Un laboratorio de metrologia necesita evaluar la confiabilidad de sus")
print("sistemas de medicion segun estudios MSA (Measurement System Analysis).")
print("Criterio: %GRR < 10% Aceptable, 10-30% Marginal, > 30% Inadecuado.")
print("\nVariables analizadas:")
print("- Desviacion estandar de mediciones repetidas (mm)")
print("- Rango de mediciones (mm)")
print("- Experiencia del operador (meses)")
print("- Dias desde la ultima calibracion")
print("\nClases:")
for n in range(len(nombres)):
    print(f"  {n} = {nombres[n]}")

# Division FIJA: primeros 70% para entrenar, ultimos 30% para probar
# (siempre da el mismo resultado, en Python, Java y C++)
split = int(len(datos) * 0.7)
X_train = [d[:4] for d in datos[:split]]
y_train = [d[4] for d in datos[:split]]
X_test = [d[:4] for d in datos[split:]]
y_test = [d[4] for d in datos[split:]]

nb = NaiveBayes()
nb.entrenar(X_train, y_train)

predicciones = [nb.predecir(x) for x in X_test]
correctos = sum(1 for i in range(len(X_test)) if predicciones[i] == y_test[i])
accuracy = correctos / len(X_test) * 100

k = len(nombres)
matriz = [[0] * k for _ in range(k)]
for i in range(len(X_test)):
    matriz[y_test[i]][predicciones[i]] += 1

print("\n" + "=" * 70)
print("RESULTADOS DEL ANALISIS")
print("=" * 70)
print(f"Registros de entrenamiento: {len(X_train)}   Registros de prueba: {len(X_test)}")
print(f"Accuracy: {accuracy:.2f}%")
print(f"Casos correctamente clasificados: {correctos}/{len(X_test)}")

print("\nMatriz de Confusion (filas = real, columnas = prediccion):")
print(" " * 22 + "Pred: INADECUADO    Pred: MARGINAL      Pred: ACEPTABLE     ")
for r in range(k):
    fila = f"Real: {nombres[r]:<16}"
    for p in range(k):
        fila += f"{matriz[r][p]:^20}"
    print(fila)

print("\n" + "=" * 70)
print("METRICAS POR CLASE")
print("=" * 70)
for c in range(k):
    vp = matriz[c][c]
    pred_c = sum(matriz[r][c] for r in range(k))
    real_c = sum(matriz[c])
    precision = vp / pred_c if pred_c > 0 else 0
    recall = vp / real_c if real_c > 0 else 0
    f1 = 2 * precision * recall / (precision + recall) if (precision + recall) > 0 else 0
    print(f"Clase: {nombres[c]} ({c})")
    print(f"  Precision: {precision * 100:.2f}%")
    print(f"  Recall:    {recall * 100:.2f}%")
    print(f"  F1-Score:  {f1 * 100:.2f}%")

# ===================================================================
# PREDICCIONES CON NUEVOS DATOS
# >>> MODIFICA AQUI: cambia los numeros para crear tus 3 casos <<<
# Orden: desv_std_mm, rango_mm, experiencia_meses, dias_calibracion, "descripcion"
# ===================================================================
nuevos_casos = [
    [0.083, 0.27, 10, 123, "Micrometro digital - Linea A"],
    [0.055, 0.13, 29, 72, "Calibrador vernier - Linea B"],
    [0.02, 0.06, 50, 20, "CMM - Laboratorio"],
]

print("\n" + "=" * 70)
print("EVALUACION DE NUEVOS CASOS")
print("=" * 70)
for fila in nuevos_casos:
    caso = fila[:4]
    resultado = nb.predecir(caso)
    prob = nb.predecir_proba(caso)
    print(f"\n{fila[4]}:")
    print(f"  Desviacion estandar de mediciones repetidas (mm): {caso[0]:.3f}")
    print(f"  Rango de mediciones (mm): {caso[1]:.2f}")
    print(f"  Experiencia del operador (meses): {caso[2]:.0f}")
    print(f"  Dias desde la ultima calibracion: {caso[3]:.0f}")
    print(f"  Prediccion: {nombres[resultado]}")
    for n in range(k):
        print(f"  Probabilidad {nombres[n]}: {prob[n] * 100:.1f}%")
    if sum(prob.values()) == 0:
        print("  (Valores muy lejanos a los datos de entrenamiento: revisa tus numeros)")
    if resultado == 0:
        print("  ACCIONES RECOMENDADAS:")
        print("     - Retirar el instrumento de uso")
        print("     - Recalibrar y repetir el estudio GR&R")
        print("     - Capacitar al operador")
    if resultado == 1:
        print("  ACCIONES RECOMENDADAS:")
        print("     - Usar con precaucion y programar calibracion")

print("\n" + "=" * 70)
print("PREGUNTAS DE ANALISIS:")
print("=" * 70)
print("1. Como afecta la experiencia del operador a la repetibilidad?")
print("2. Que error es mas riesgoso: aceptar un sistema INADECUADO o rechazar uno ACEPTABLE?")
print("3. Como se relaciona el %GRR con la capacidad del proceso (Cp/Cpk)?")
print("4. Que dice la norma ISO/IEC 17025 sobre incertidumbre de medicion?")
