# ESCENARIO 4: DETECCION DE FRAUDE EN TRANSACCIONES BANCARIAS
# Actividad 5 - Clasificacion Bayesiana Ingenua - Universidad SABES 2026
# Python (Pydroid 3). NO requiere instalar librerias: solo usa "math".
#
# Columnas del dataset: monto_usd, hora, distancia_km, intentos_fallidos, clase
# Clases: 0 = LEGITIMA, 1 = FRAUDE

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


# Dataset (40 registros): monto_usd, hora, distancia_km, intentos_fallidos, clase
datos = [
    [1112.54, 8, 127.0, 1, 0], [201.65, 11, 517.0, 1, 0], [479.32, 21, 408.6, 0, 0],
    [4388.2, 3, 2199.5, 2, 1], [1302.47, 6, 287.6, 2, 1], [114.3, 13, 2620.4, 2, 1],
    [3545.27, 14, 1835.2, 3, 1], [1185.95, 9, 1017.1, 2, 0], [1455.14, 6, 920.8, 0, 0],
    [3168.67, 6, 1672.4, 2, 1], [472.84, 3, 419.4, 0, 1], [2557.53, 5, 1767.1, 2, 1],
    [4185.43, 12, 1436.5, 1, 1], [5.22, 15, 295.4, 1, 0], [121.72, 17, 639.8, 0, 0],
    [506.43, 12, 126.9, 1, 0], [968.55, 20, 349.3, 1, 0], [62.05, 12, 235.3, 0, 0],
    [82.24, 10, 315.8, 0, 0], [440.34, 20, 348.6, 1, 0], [696.24, 11, 341.9, 0, 0],
    [894.11, 6, 721.6, 0, 0], [393.03, 13, 334.3, 0, 0], [3779.73, 5, 1011.8, 0, 1],
    [533.97, 13, 819.4, 0, 0], [1009.15, 20, 241.5, 0, 0], [534.29, 19, 251.7, 0, 0],
    [628.96, 12, 661.4, 1, 0], [753.8, 8, 1287.7, 2, 1], [1564.23, 5, 37.6, 0, 1],
    [1440.44, 18, 347.3, 1, 0], [2779.16, 4, 2873.4, 2, 1], [3634.87, 8, 1050.6, 1, 1],
    [1076.07, 17, 30.6, 1, 0], [1767.73, 6, 1981.8, 3, 1], [1077.89, 4, 1902.1, 1, 1],
    [2201.37, 19, 247.9, 0, 1], [1633.37, 3, 635.2, 0, 0], [743.7, 7, 643.8, 1, 0],
    [422.37, 17, 247.5, 0, 0]
]

nombres = ['LEGITIMA', 'FRAUDE']

print("=" * 70)
print("ESCENARIO 4: DETECCION DE FRAUDE EN TRANSACCIONES BANCARIAS")
print("=" * 70)
print("\nDescripcion del problema:")
print("Un banco necesita identificar transacciones potencialmente fraudulentas")
print("en tiempo real para proteger a sus clientes.")
print("\nVariables analizadas:")
print("- Monto de la transaccion (USD)")
print("- Hora del dia (0-23)")
print("- Distancia de la ubicacion usual (km)")
print("- Intentos previos fallidos")
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
print(" " * 22 + "Pred: LEGITIMA      Pred: FRAUDE        ")
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

# Analisis de costos de los errores (clase 1 = FRAUDE es el evento que queremos detectar)
costo_falso_negativo = 15000   # MXN - monto robado que el banco reembolsa
costo_falso_positivo = 250   # MXN - llamada de verificacion y molestia del cliente
fn = matriz[1][0]   # era FRAUDE y el modelo dijo LEGITIMA
fp = matriz[0][1]   # era LEGITIMA y el modelo dijo FRAUDE
costo_total = fn * costo_falso_negativo + fp * costo_falso_positivo
print("\nAnalisis de Costos:")
print(f"Falsos Negativos (fraudes aprobados): {fn} x ${costo_falso_negativo:,} = ${fn * costo_falso_negativo:,}")
print(f"Falsos Positivos (transacciones legitimas bloqueadas): {fp} x ${costo_falso_positivo:,} = ${fp * costo_falso_positivo:,}")
print(f"Costo Total de los errores: ${costo_total:,}")

# ===================================================================
# PREDICCIONES CON NUEVOS DATOS
# >>> MODIFICA AQUI: cambia los numeros para crear tus 3 casos <<<
# Orden: monto_usd, hora, distancia_km, intentos_fallidos, "descripcion"
# ===================================================================
nuevos_casos = [
    [692.79, 13, 440.7, 0, "Compra en supermercado - tarde"],
    [2330.84, 8, 1414.4, 1, "Compra de madrugada en otro pais"],
    [1511.81, 10, 927.6, 1, "Compra mediana - caso intermedio"],
]

print("\n" + "=" * 70)
print("EVALUACION DE NUEVOS CASOS")
print("=" * 70)
for fila in nuevos_casos:
    caso = fila[:4]
    resultado = nb.predecir(caso)
    prob = nb.predecir_proba(caso)
    print(f"\n{fila[4]}:")
    print(f"  Monto de la transaccion (USD): {caso[0]:.2f}")
    print(f"  Hora del dia (0-23): {caso[1]:.0f}")
    print(f"  Distancia de la ubicacion usual (km): {caso[2]:.1f}")
    print(f"  Intentos previos fallidos: {caso[3]:.0f}")
    print(f"  Prediccion: {nombres[resultado]}")
    for n in range(k):
        print(f"  Probabilidad {nombres[n]}: {prob[n] * 100:.1f}%")
    if sum(prob.values()) == 0:
        print("  (Valores muy lejanos a los datos de entrenamiento: revisa tus numeros)")
    if resultado == 1:
        print("  ACCIONES RECOMENDADAS:")
        print("     - Bloquear la transaccion")
        print("     - Enviar SMS de verificacion al cliente")
        print("     - Revisar movimientos recientes")

print("\n" + "=" * 70)
print("PREGUNTAS DE ANALISIS:")
print("=" * 70)
print("1. Cual es el costo de un falso positivo (bloquear una transaccion legitima)?")
print("2. Cual es el costo de un falso negativo (aprobar un fraude)?")
print("3. Que patron caracteriza mejor las transacciones fraudulentas?")
print("4. Como balancear seguridad vs. experiencia del cliente?")
