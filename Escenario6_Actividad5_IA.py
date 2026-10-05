# ESCENARIO 6: LOGISTICA INTERNACIONAL - PREDICCION DE RETRASOS
# Actividad 5 - Clasificacion Bayesiana Ingenua - Universidad SABES 2026
# Python (Pydroid 3). NO requiere instalar librerias: solo usa "math".
#
# Columnas del dataset: distancia_km, peso_kg, documentos_aduanales, dias_procesamiento, clase
# Clases: 0 = A TIEMPO, 1 = RETRASO

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


# Dataset (40 registros): distancia_km, peso_kg, documentos_aduanales, dias_procesamiento, clase
datos = [
    [7649, 2444, 10, 7, 1], [2932, 4531, 3, 6, 1], [16298, 2517, 8, 2, 1],
    [3129, 256, 3, 1, 0], [16273, 2103, 12, 6, 1], [4829, 970, 3, 2, 0],
    [5735, 515, 9, 2, 0], [3325, 1231, 2, 2, 0], [3801, 156, 7, 3, 0],
    [7599, 2619, 7, 3, 1], [6958, 451, 7, 5, 0], [5325, 331, 2, 3, 0],
    [9481, 950, 3, 1, 1], [2908, 1026, 5, 3, 0], [6803, 1546, 11, 8, 1],
    [12948, 2365, 7, 6, 1], [9340, 3293, 20, 5, 1], [2954, 892, 9, 2, 0],
    [1427, 1104, 2, 1, 0], [18218, 2320, 16, 8, 1], [6074, 1886, 3, 5, 0],
    [2228, 1020, 4, 2, 0], [5910, 1655, 3, 3, 0], [5267, 1381, 7, 2, 0],
    [1508, 366, 6, 2, 0], [537, 805, 4, 3, 0], [15127, 4459, 5, 7, 1],
    [4311, 979, 8, 4, 0], [4927, 500, 2, 3, 0], [4224, 2526, 2, 1, 0],
    [4326, 146, 3, 2, 0], [10083, 224, 5, 2, 0], [5149, 1783, 10, 3, 1],
    [12353, 750, 7, 10, 1], [3072, 477, 5, 4, 0], [14079, 2020, 12, 6, 1],
    [9041, 425, 3, 2, 0], [17726, 4127, 7, 3, 1], [7365, 628, 2, 4, 0],
    [13319, 4912, 15, 7, 1]
]

nombres = ['A TIEMPO', 'RETRASO']

print("=" * 70)
print("ESCENARIO 6: LOGISTICA INTERNACIONAL - PREDICCION DE RETRASOS")
print("=" * 70)
print("\nDescripcion del problema:")
print("Una empresa de logistica internacional necesita predecir que envios")
print("tienen alto riesgo de retrasarse para tomar medidas preventivas.")
print("\nVariables analizadas:")
print("- Distancia del envio (km)")
print("- Peso de la carga (kg)")
print("- Documentos aduanales requeridos")
print("- Dias de procesamiento en aduana")
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
print(" " * 22 + "Pred: A TIEMPO      Pred: RETRASO       ")
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

# Analisis de costos de los errores (clase 1 = RETRASO es el evento que queremos detectar)
costo_falso_negativo = 5000   # MXN - penalizacion y compensacion al cliente
costo_falso_positivo = 500   # MXN - medidas preventivas innecesarias
fn = matriz[1][0]   # era RETRASO y el modelo dijo A TIEMPO
fp = matriz[0][1]   # era A TIEMPO y el modelo dijo RETRASO
costo_total = fn * costo_falso_negativo + fp * costo_falso_positivo
print("\nAnalisis de Costos:")
print(f"Falsos Negativos (retrasos no previstos): {fn} x ${costo_falso_negativo:,} = ${fn * costo_falso_negativo:,}")
print(f"Falsos Positivos (alarmas falsas): {fp} x ${costo_falso_positivo:,} = ${fp * costo_falso_positivo:,}")
print(f"Costo Total de los errores: ${costo_total:,}")

# ===================================================================
# PREDICCIONES CON NUEVOS DATOS
# >>> MODIFICA AQUI: cambia los numeros para crear tus 3 casos <<<
# Orden: distancia_km, peso_kg, documentos_aduanales, dias_procesamiento, "descripcion"
# ===================================================================
nuevos_casos = [
    [4553, 831, 4, 3, "Envio Nacional - estandar"],
    [11581, 2671, 10, 6, "Envio Internacional - complejo"],
    [8067, 1751, 7, 4, "Envio Regional - moderado"],
]

print("\n" + "=" * 70)
print("EVALUACION DE NUEVOS CASOS")
print("=" * 70)
for fila in nuevos_casos:
    caso = fila[:4]
    resultado = nb.predecir(caso)
    prob = nb.predecir_proba(caso)
    print(f"\n{fila[4]}:")
    print(f"  Distancia del envio (km): {caso[0]:.0f}")
    print(f"  Peso de la carga (kg): {caso[1]:.0f}")
    print(f"  Documentos aduanales requeridos: {caso[2]:.0f}")
    print(f"  Dias de procesamiento en aduana: {caso[3]:.0f}")
    print(f"  Prediccion: {nombres[resultado]}")
    for n in range(k):
        print(f"  Probabilidad {nombres[n]}: {prob[n] * 100:.1f}%")
    if sum(prob.values()) == 0:
        print("  (Valores muy lejanos a los datos de entrenamiento: revisa tus numeros)")
    if resultado == 1:
        print("  ACCIONES RECOMENDADAS:")
        print("     - Acelerar tramites aduanales")
        print("     - Notificar al cliente sobre posible retraso")
        print("     - Asignar courier premium o ruta alterna")

print("\n" + "=" * 70)
print("PREGUNTAS DE ANALISIS:")
print("=" * 70)
print("1. Cual factor logistico impacta mas en los retrasos?")
print("2. Como optimizarias la relacion costo-beneficio del modelo?")
print("3. Que KPIs logisticos adicionales deberian considerarse?")
print("4. Como afectan las regulaciones aduanales al modelo predictivo?")
