# ESCENARIO 2: PREDICCION DE ABANDONO DE CLIENTES (CHURN)
# Actividad 5 - Clasificacion Bayesiana Ingenua - Universidad SABES 2026
# Python (Pydroid 3). NO requiere instalar librerias: solo usa "math".
#
# Columnas del dataset: meses_servicio, llamadas_soporte, gasto_mensual_usd, minutos_uso, clase
# Clases: 0 = SE QUEDA, 1 = ABANDONA

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


# Dataset (40 registros): meses_servicio, llamadas_soporte, gasto_mensual_usd, minutos_uso, clase
datos = [
    [16, 2, 95.2, 368, 0], [14, 1, 74.7, 277, 0], [7, 8, 22.6, 260, 1],
    [26, 4, 41.7, 392, 0], [7, 8, 39.6, 240, 1], [8, 4, 46.1, 249, 1],
    [2, 8, 49.4, 464, 1], [36, 5, 94.2, 535, 0], [26, 1, 80.3, 477, 0],
    [33, 1, 61.9, 678, 0], [40, 4, 63.2, 637, 0], [35, 1, 63.2, 466, 0],
    [13, 4, 29.0, 298, 1], [37, 3, 78.4, 477, 0], [24, 2, 88.2, 546, 0],
    [48, 3, 53.3, 228, 1], [34, 0, 82.2, 259, 0], [23, 1, 65.4, 437, 0],
    [23, 2, 69.4, 425, 0], [30, 2, 79.9, 630, 0], [2, 2, 24.4, 321, 1],
    [40, 2, 79.8, 535, 0], [13, 8, 34.9, 306, 1], [15, 10, 29.0, 249, 1],
    [26, 3, 83.0, 583, 0], [2, 9, 57.6, 346, 1], [13, 6, 32.5, 187, 1],
    [35, 2, 67.8, 509, 0], [18, 2, 61.9, 524, 0], [27, 6, 54.1, 430, 0],
    [5, 6, 46.3, 248, 1], [16, 5, 62.4, 215, 1], [15, 7, 32.7, 253, 1],
    [47, 4, 88.1, 548, 0], [16, 2, 69.7, 203, 1], [16, 2, 75.5, 498, 0],
    [30, 2, 50.3, 677, 0], [35, 1, 88.3, 476, 0], [40, 8, 54.8, 268, 1],
    [17, 1, 66.9, 490, 0]
]

nombres = ['SE QUEDA', 'ABANDONA']

print("=" * 70)
print("ESCENARIO 2: PREDICCION DE ABANDONO DE CLIENTES (CHURN)")
print("=" * 70)
print("\nDescripcion del problema:")
print("Una empresa de telecomunicaciones quiere predecir que clientes tienen")
print("mayor riesgo de cancelar su servicio (abandono/churn).")
print("\nVariables analizadas:")
print("- Meses de servicio con la empresa")
print("- Llamadas al soporte tecnico")
print("- Gasto mensual promedio (USD)")
print("- Minutos de uso mensual")
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
print(" " * 22 + "Pred: SE QUEDA      Pred: ABANDONA      ")
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

# Analisis de costos de los errores (clase 1 = ABANDONA es el evento que queremos detectar)
costo_falso_negativo = 4000   # MXN - ingreso anual perdido del cliente
costo_falso_positivo = 400   # MXN - descuento de retencion innecesario
fn = matriz[1][0]   # era ABANDONA y el modelo dijo SE QUEDA
fp = matriz[0][1]   # era SE QUEDA y el modelo dijo ABANDONA
costo_total = fn * costo_falso_negativo + fp * costo_falso_positivo
print("\nAnalisis de Costos:")
print(f"Falsos Negativos (clientes que abandonan sin ser detectados): {fn} x ${costo_falso_negativo:,} = ${fn * costo_falso_negativo:,}")
print(f"Falsos Positivos (clientes leales marcados en riesgo): {fp} x ${costo_falso_positivo:,} = ${fp * costo_falso_positivo:,}")
print(f"Costo Total de los errores: ${costo_total:,}")

# ===================================================================
# PREDICCIONES CON NUEVOS DATOS
# >>> MODIFICA AQUI: cambia los numeros para crear tus 3 casos <<<
# Orden: meses_servicio, llamadas_soporte, gasto_mensual_usd, minutos_uso, "descripcion"
# ===================================================================
nuevos_casos = [
    [29, 2, 73.1, 495, "Cliente A - antiguo y satisfecho"],
    [14, 6, 42.8, 271, "Cliente B - nuevo con muchas quejas"],
    [21, 4, 57.9, 383, "Cliente C - caso intermedio"],
]

print("\n" + "=" * 70)
print("EVALUACION DE NUEVOS CASOS")
print("=" * 70)
for fila in nuevos_casos:
    caso = fila[:4]
    resultado = nb.predecir(caso)
    prob = nb.predecir_proba(caso)
    print(f"\n{fila[4]}:")
    print(f"  Meses de servicio con la empresa: {caso[0]:.0f}")
    print(f"  Llamadas al soporte tecnico: {caso[1]:.0f}")
    print(f"  Gasto mensual promedio (USD): {caso[2]:.1f}")
    print(f"  Minutos de uso mensual: {caso[3]:.0f}")
    print(f"  Prediccion: {nombres[resultado]}")
    for n in range(k):
        print(f"  Probabilidad {nombres[n]}: {prob[n] * 100:.1f}%")
    if sum(prob.values()) == 0:
        print("  (Valores muy lejanos a los datos de entrenamiento: revisa tus numeros)")
    if resultado == 1:
        print("  ACCIONES RECOMENDADAS:")
        print("     - Llamada de retencion personalizada")
        print("     - Ofrecer plan con descuento")
        print("     - Revisar historial de quejas")

print("\n" + "=" * 70)
print("PREGUNTAS DE ANALISIS:")
print("=" * 70)
print("1. Cual caracteristica es mas indicativa de abandono?")
print("2. Que estrategias de retencion recomendarias para clientes en riesgo?")
print("3. Cual es el costo de un falso negativo (predecir que se queda pero abandona)?")
print("4. Como afecta el numero de llamadas a soporte en la decision del cliente?")
