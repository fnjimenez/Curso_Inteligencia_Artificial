# ESCENARIO 13: LOGISTICA - QUIEBRE DE INVENTARIO (STOCKOUT)
# Actividad 5 - Clasificacion Bayesiana Ingenua - Universidad SABES 2026
# Python (Pydroid 3). NO requiere instalar librerias: solo usa "math".
#
# Columnas del dataset: demanda_diaria, inventario_actual, lead_time_reposicion, variabilidad_pct, clase
# Clases: 0 = SIN QUIEBRE, 1 = QUIEBRE

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


# Dataset (40 registros): demanda_diaria, inventario_actual, lead_time_reposicion, variabilidad_pct, clase
datos = [
    [147, 1610, 6, 20, 1], [194, 640, 7, 8, 1], [147, 2530, 3, 5, 0],
    [151, 1720, 1, 6, 0], [180, 1510, 3, 19, 0], [157, 910, 3, 49, 1],
    [52, 1380, 6, 21, 0], [174, 1270, 5, 13, 0], [105, 1340, 7, 15, 0],
    [114, 480, 6, 16, 0], [248, 680, 6, 30, 1], [127, 1800, 7, 14, 0],
    [120, 630, 7, 16, 0], [242, 760, 6, 34, 1], [90, 1570, 2, 13, 0],
    [102, 440, 5, 33, 1], [99, 840, 6, 31, 1], [148, 1530, 3, 28, 0],
    [123, 1270, 9, 16, 1], [146, 1270, 3, 18, 0], [70, 400, 4, 17, 0],
    [116, 1350, 4, 15, 0], [278, 800, 11, 29, 1], [122, 1240, 4, 12, 0],
    [285, 1070, 11, 32, 1], [139, 1030, 3, 21, 0], [110, 1760, 5, 17, 0],
    [169, 1200, 8, 16, 1], [137, 290, 5, 41, 1], [95, 2610, 2, 13, 0],
    [129, 1630, 3, 23, 0], [71, 2360, 3, 14, 0], [206, 1040, 3, 20, 0],
    [120, 350, 10, 36, 1], [162, 2520, 5, 25, 0], [143, 650, 8, 18, 0],
    [85, 1940, 4, 15, 0], [214, 950, 9, 12, 1], [237, 1340, 7, 39, 1],
    [73, 980, 5, 5, 0]
]

nombres = ['SIN QUIEBRE', 'QUIEBRE']

print("=" * 70)
print("ESCENARIO 13: LOGISTICA - QUIEBRE DE INVENTARIO (STOCKOUT)")
print("=" * 70)
print("\nDescripcion del problema:")
print("Un centro de distribucion quiere saber que SKUs tienen riesgo de")
print("quedarse sin existencias antes de que llegue la reposicion, para")
print("priorizar compras urgentes.")
print("\nVariables analizadas:")
print("- Demanda promedio diaria (unidades)")
print("- Inventario actual (unidades)")
print("- Lead time de reposicion (dias)")
print("- Variabilidad de la demanda (%)")
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
print(" " * 22 + "Pred: SIN QUIEBRE   Pred: QUIEBRE       ")
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

# Analisis de costos de los errores (clase 1 = QUIEBRE es el evento que queremos detectar)
costo_falso_negativo = 6000   # MXN - venta perdida y cliente insatisfecho
costo_falso_positivo = 900   # MXN - compra urgente innecesaria / exceso de inventario
fn = matriz[1][0]   # era QUIEBRE y el modelo dijo SIN QUIEBRE
fp = matriz[0][1]   # era SIN QUIEBRE y el modelo dijo QUIEBRE
costo_total = fn * costo_falso_negativo + fp * costo_falso_positivo
print("\nAnalisis de Costos:")
print(f"Falsos Negativos (quiebres no detectados): {fn} x ${costo_falso_negativo:,} = ${fn * costo_falso_negativo:,}")
print(f"Falsos Positivos (alertas falsas): {fp} x ${costo_falso_positivo:,} = ${fp * costo_falso_positivo:,}")
print(f"Costo Total de los errores: ${costo_total:,}")

# ===================================================================
# PREDICCIONES CON NUEVOS DATOS
# >>> MODIFICA AQUI: cambia los numeros para crear tus 3 casos <<<
# Orden: demanda_diaria, inventario_actual, lead_time_reposicion, variabilidad_pct, "descripcion"
# ===================================================================
nuevos_casos = [
    [110, 1800, 3, 12, "SKU 1001 - rotacion normal"],
    [190, 700, 9, 35, "SKU 2050 - alta demanda, poco inventario"],
    [150, 1100, 6, 22, "SKU 3307 - caso intermedio"],
]

print("\n" + "=" * 70)
print("EVALUACION DE NUEVOS CASOS")
print("=" * 70)
for fila in nuevos_casos:
    caso = fila[:4]
    resultado = nb.predecir(caso)
    prob = nb.predecir_proba(caso)
    print(f"\n{fila[4]}:")
    print(f"  Demanda promedio diaria (unidades): {caso[0]:.0f}")
    print(f"  Inventario actual (unidades): {caso[1]:.0f}")
    print(f"  Lead time de reposicion (dias): {caso[2]:.0f}")
    print(f"  Variabilidad de la demanda (%): {caso[3]:.0f}")
    print(f"  Prediccion: {nombres[resultado]}")
    for n in range(k):
        print(f"  Probabilidad {nombres[n]}: {prob[n] * 100:.1f}%")
    if sum(prob.values()) == 0:
        print("  (Valores muy lejanos a los datos de entrenamiento: revisa tus numeros)")
    if resultado == 1:
        print("  ACCIONES RECOMENDADAS:")
        print("     - Generar orden de compra urgente")
        print("     - Transferir stock de otro almacen")
        print("     - Revisar punto de reorden")

print("\n" + "=" * 70)
print("PREGUNTAS DE ANALISIS:")
print("=" * 70)
print("1. Cual variable explica mejor el riesgo de quiebre?")
print("2. Como se relaciona este modelo con el punto de reorden?")
print("3. Es peor un quiebre o una compra innecesaria? Por que?")
print("4. Que dato agregarias (temporada, promociones, etc.)?")
