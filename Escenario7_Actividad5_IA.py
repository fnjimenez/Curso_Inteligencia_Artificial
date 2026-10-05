# ESCENARIO 7: SEGURIDAD INDUSTRIAL - PREDICCION DE ACCIDENTES
# Actividad 5 - Clasificacion Bayesiana Ingenua - Universidad SABES 2026
# Python (Pydroid 3). NO requiere instalar librerias: solo usa "math".
#
# Columnas del dataset: hrs_trabajo_continuo, ruido_db, temperatura_C, dias_sin_capacitacion, clase
# Clases: 0 = SIN ACCIDENTE, 1 = ACCIDENTE

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


# Dataset (40 registros): hrs_trabajo_continuo, ruido_db, temperatura_C, dias_sin_capacitacion, clase
datos = [
    [9, 100, 39, 54, 1], [6, 85, 46, 62, 1], [15, 74, 30, 32, 1],
    [13, 100, 35, 12, 1], [4, 82, 18, 10, 0], [10, 62, 21, 10, 0],
    [3, 76, 22, 4, 0], [15, 106, 28, 27, 1], [11, 76, 15, 0, 0],
    [4, 69, 25, 14, 0], [7, 78, 16, 9, 0], [4, 75, 19, 2, 0],
    [4, 81, 18, 3, 0], [3, 76, 32, 1, 0], [4, 73, 24, 5, 0],
    [14, 99, 29, 15, 1], [7, 67, 26, 7, 0], [3, 60, 31, 11, 0],
    [6, 88, 24, 9, 0], [7, 64, 22, 12, 0], [13, 87, 20, 21, 0],
    [11, 99, 36, 26, 1], [3, 74, 27, 10, 0], [5, 74, 19, 30, 0],
    [10, 98, 27, 21, 1], [6, 101, 34, 48, 1], [14, 93, 43, 33, 1],
    [10, 80, 32, 4, 1], [7, 75, 21, 45, 0], [14, 89, 47, 38, 1],
    [13, 89, 34, 73, 1], [12, 94, 43, 87, 1], [3, 72, 24, 6, 0],
    [6, 76, 23, 15, 0], [11, 99, 37, 57, 1], [12, 78, 24, 9, 0],
    [11, 110, 40, 56, 1], [4, 84, 27, 9, 0], [3, 84, 17, 7, 0],
    [10, 73, 27, 16, 0]
]

nombres = ['SIN ACCIDENTE', 'ACCIDENTE']

print("=" * 70)
print("ESCENARIO 7: SEGURIDAD INDUSTRIAL - PREDICCION DE ACCIDENTES")
print("=" * 70)
print("\nDescripcion del problema:")
print("Una planta industrial necesita identificar condiciones de alto riesgo")
print("para prevenir accidentes laborales y proteger a sus trabajadores.")
print("\nVariables analizadas:")
print("- Horas de trabajo continuo")
print("- Nivel de ruido ambiental (dB)")
print("- Temperatura en el area (C)")
print("- Dias desde la ultima capacitacion")
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
print(" " * 22 + "Pred: SIN ACCIDENTE Pred: ACCIDENTE     ")
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

# Analisis de costos de los errores (clase 1 = ACCIDENTE es el evento que queremos detectar)
costo_falso_negativo = 120000   # MXN - costo de un accidente (incapacidad, multa, paro)
costo_falso_positivo = 2000   # MXN - pausa o rotacion preventiva del turno
fn = matriz[1][0]   # era ACCIDENTE y el modelo dijo SIN ACCIDENTE
fp = matriz[0][1]   # era SIN ACCIDENTE y el modelo dijo ACCIDENTE
costo_total = fn * costo_falso_negativo + fp * costo_falso_positivo
print("\nAnalisis de Costos:")
print(f"Falsos Negativos (condiciones de riesgo no detectadas): {fn} x ${costo_falso_negativo:,} = ${fn * costo_falso_negativo:,}")
print(f"Falsos Positivos (alertas preventivas innecesarias): {fp} x ${costo_falso_positivo:,} = ${fp * costo_falso_positivo:,}")
print(f"Costo Total de los errores: ${costo_total:,}")

# ===================================================================
# PREDICCIONES CON NUEVOS DATOS
# >>> MODIFICA AQUI: cambia los numeros para crear tus 3 casos <<<
# Orden: hrs_trabajo_continuo, ruido_db, temperatura_C, dias_sin_capacitacion, "descripcion"
# ===================================================================
nuevos_casos = [
    [6, 75, 23, 11, "Turno matutino - Area A"],
    [12, 95, 36, 40, "Turno nocturno - Area B"],
    [9, 85, 29, 26, "Turno vespertino - Area C"],
]

print("\n" + "=" * 70)
print("EVALUACION DE NUEVOS CASOS")
print("=" * 70)
for fila in nuevos_casos:
    caso = fila[:4]
    resultado = nb.predecir(caso)
    prob = nb.predecir_proba(caso)
    print(f"\n{fila[4]}:")
    print(f"  Horas de trabajo continuo: {caso[0]:.0f}")
    print(f"  Nivel de ruido ambiental (dB): {caso[1]:.0f}")
    print(f"  Temperatura en el area (C): {caso[2]:.0f}")
    print(f"  Dias desde la ultima capacitacion: {caso[3]:.0f}")
    print(f"  Prediccion: {nombres[resultado]}")
    for n in range(k):
        print(f"  Probabilidad {nombres[n]}: {prob[n] * 100:.1f}%")
    if sum(prob.values()) == 0:
        print("  (Valores muy lejanos a los datos de entrenamiento: revisa tus numeros)")
    if resultado == 1:
        print("  ACCIONES RECOMENDADAS:")
        print("     - Rotar al personal y dar pausa activa")
        print("     - Verificar uso de EPP (proteccion auditiva)")
        print("     - Programar capacitacion de seguridad")

print("\n" + "=" * 70)
print("PREGUNTAS DE ANALISIS:")
print("=" * 70)
print("1. Por que es critico minimizar los falsos negativos en seguridad?")
print("2. Cual factor de riesgo requiere intervencion mas urgente?")
print("3. Como se compara el costo de un accidente vs. las medidas preventivas?")
print("4. Que normas (NOM-STPS) se relacionan con estos factores?")
