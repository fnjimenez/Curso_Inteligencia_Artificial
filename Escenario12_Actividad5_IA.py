# ESCENARIO 12: LOGISTICA - CUMPLIMIENTO DE PROVEEDORES (OTIF)
# Actividad 5 - Clasificacion Bayesiana Ingenua - Universidad SABES 2026
# Python (Pydroid 3). NO requiere instalar librerias: solo usa "math".
#
# Columnas del dataset: lead_time_dias, pedido_unidades, historial_otif_pct, pedidos_abiertos, clase
# Clases: 0 = CUMPLE OTIF, 1 = INCUMPLE

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


# Dataset (40 registros): lead_time_dias, pedido_unidades, historial_otif_pct, pedidos_abiertos, clase
datos = [
    [6, 50, 100, 6, 0], [7, 630, 93.9, 2, 0], [4, 50, 89.0, 4, 0],
    [7, 640, 96.6, 3, 0], [5, 610, 100, 7, 0], [4, 760, 93.5, 1, 0],
    [8, 590, 94.9, 3, 0], [7, 680, 96.7, 7, 0], [5, 660, 94.0, 3, 0],
    [3, 590, 92.2, 2, 0], [10, 750, 85.8, 10, 1], [5, 750, 98.5, 7, 0],
    [6, 700, 95.6, 4, 0], [2, 660, 97.5, 7, 0], [7, 320, 88.6, 8, 1],
    [12, 340, 91.0, 6, 1], [3, 400, 88.3, 7, 1], [9, 920, 80.1, 6, 1],
    [2, 560, 98.5, 7, 0], [6, 1320, 95.1, 11, 1], [4, 100, 90.9, 4, 0],
    [11, 1220, 82.1, 11, 1], [3, 320, 92.3, 4, 0], [11, 1110, 84.1, 7, 1],
    [5, 790, 81.0, 8, 1], [7, 120, 91.9, 3, 0], [7, 820, 86.2, 1, 1],
    [6, 410, 90.9, 2, 1], [4, 730, 95.8, 4, 0], [6, 380, 98.7, 5, 0],
    [3, 190, 84.1, 7, 1], [5, 750, 92.5, 5, 0], [9, 50, 93.7, 7, 1],
    [3, 1150, 94.1, 3, 1], [4, 250, 92.4, 3, 0], [10, 490, 94.8, 10, 1],
    [4, 90, 97.3, 3, 0], [3, 480, 82.3, 7, 1], [5, 150, 92.3, 4, 0],
    [4, 600, 94.0, 3, 0]
]

nombres = ['CUMPLE OTIF', 'INCUMPLE']

print("=" * 70)
print("ESCENARIO 12: LOGISTICA - CUMPLIMIENTO DE PROVEEDORES (OTIF)")
print("=" * 70)
print("\nDescripcion del problema:")
print("Una empresa de distribucion necesita anticipar que pedidos a")
print("proveedores NO llegaran completos y a tiempo (OTIF) para")
print("activar planes de contingencia antes de que falte producto.")
print("\nVariables analizadas:")
print("- Lead time prometido por el proveedor (dias)")
print("- Tamano del pedido (unidades)")
print("- Historial de cumplimiento OTIF del proveedor (%)")
print("- Pedidos abiertos con ese proveedor")
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
print(" " * 22 + "Pred: CUMPLE OTIF   Pred: INCUMPLE      ")
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

# Analisis de costos de los errores (clase 1 = INCUMPLE es el evento que queremos detectar)
costo_falso_negativo = 8000   # MXN - paro de linea / venta perdida
costo_falso_positivo = 1200   # MXN - pedido de respaldo innecesario
fn = matriz[1][0]   # era INCUMPLE y el modelo dijo CUMPLE OTIF
fp = matriz[0][1]   # era CUMPLE OTIF y el modelo dijo INCUMPLE
costo_total = fn * costo_falso_negativo + fp * costo_falso_positivo
print("\nAnalisis de Costos:")
print(f"Falsos Negativos (pedidos incumplidos no detectados): {fn} x ${costo_falso_negativo:,} = ${fn * costo_falso_negativo:,}")
print(f"Falsos Positivos (alertas falsas): {fp} x ${costo_falso_positivo:,} = ${fp * costo_falso_positivo:,}")
print(f"Costo Total de los errores: ${costo_total:,}")

# ===================================================================
# PREDICCIONES CON NUEVOS DATOS
# >>> MODIFICA AQUI: cambia los numeros para crear tus 3 casos <<<
# Orden: lead_time_dias, pedido_unidades, historial_otif_pct, pedidos_abiertos, "descripcion"
# ===================================================================
nuevos_casos = [
    [5, 400, 96.0, 3, "Proveedor A - pedido regular"],
    [9, 950, 84.5, 10, "Proveedor B - pedido grande, proveedor saturado"],
    [6, 600, 90.0, 6, "Proveedor C - caso intermedio"],
]

print("\n" + "=" * 70)
print("EVALUACION DE NUEVOS CASOS")
print("=" * 70)
for fila in nuevos_casos:
    caso = fila[:4]
    resultado = nb.predecir(caso)
    prob = nb.predecir_proba(caso)
    print(f"\n{fila[4]}:")
    print(f"  Lead time prometido por el proveedor (dias): {caso[0]:.0f}")
    print(f"  Tamano del pedido (unidades): {caso[1]:.0f}")
    print(f"  Historial de cumplimiento OTIF del proveedor (%): {caso[2]:.1f}")
    print(f"  Pedidos abiertos con ese proveedor: {caso[3]:.0f}")
    print(f"  Prediccion: {nombres[resultado]}")
    for n in range(k):
        print(f"  Probabilidad {nombres[n]}: {prob[n] * 100:.1f}%")
    if sum(prob.values()) == 0:
        print("  (Valores muy lejanos a los datos de entrenamiento: revisa tus numeros)")
    if resultado == 1:
        print("  ACCIONES RECOMENDADAS:")
        print("     - Confirmar fecha con el proveedor")
        print("     - Activar proveedor alterno")
        print("     - Aumentar inventario de seguridad")

print("\n" + "=" * 70)
print("PREGUNTAS DE ANALISIS:")
print("=" * 70)
print("1. Cual variable pesa mas para predecir un incumplimiento?")
print("2. Por que el historial OTIF no basta por si solo?")
print("3. Que pasaria si el costo de un incumplimiento fuera el doble?")
print("4. Que otro dato del proveedor agregarias al modelo?")
