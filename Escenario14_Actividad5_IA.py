# ESCENARIO 14: LOGISTICA - ENTREGAS FALLIDAS EN ULTIMA MILLA
# Actividad 5 - Clasificacion Bayesiana Ingenua - Universidad SABES 2026
# Python (Pydroid 3). NO requiere instalar librerias: solo usa "math".
#
# Columnas del dataset: distancia_ruta_km, paradas_ruta, ventana_horaria_h, incidencias_previas, clase
# Clases: 0 = ENTREGADA, 1 = FALLIDA

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


# Dataset (40 registros): distancia_ruta_km, paradas_ruta, ventana_horaria_h, incidencias_previas, clase
datos = [
    [13.1, 10, 2.7, 4, 0], [25.5, 10, 4.3, 3, 0], [22.5, 16, 4.8, 3, 0],
    [20.6, 27, 5.3, 5, 0], [43.9, 18, 4.5, 4, 1], [23.5, 14, 1.8, 5, 0],
    [14.3, 24, 2.4, 6, 1], [12.3, 21, 2.3, 3, 0], [51.8, 34, 2.0, 4, 1],
    [15.1, 28, 1.8, 4, 1], [28.1, 19, 2.2, 8, 1], [24.4, 14, 3.9, 5, 0],
    [18.8, 19, 2.8, 4, 0], [8.1, 10, 4.7, 2, 0], [22.6, 9, 4.2, 4, 0],
    [25.5, 25, 1.8, 3, 1], [8.5, 21, 4.6, 2, 0], [39.5, 30, 1.5, 6, 1],
    [33.5, 20, 2.2, 6, 1], [20.8, 19, 1.4, 6, 1], [41.6, 41, 1.6, 6, 1],
    [29.5, 14, 3.4, 6, 1], [26.8, 18, 3.1, 0, 0], [9.1, 22, 5.3, 5, 0],
    [7.1, 14, 4.2, 4, 0], [27.0, 17, 2.7, 5, 0], [22.7, 23, 4.7, 4, 0],
    [22.6, 28, 2.1, 7, 1], [25.6, 12, 5.0, 6, 0], [46.5, 21, 3.3, 6, 1],
    [35.5, 24, 1.0, 8, 1], [13.1, 12, 4.4, 3, 0], [21.6, 22, 3.9, 3, 0],
    [18.6, 9, 4.6, 4, 0], [24.7, 18, 2.6, 5, 0], [29.6, 17, 2.1, 6, 1],
    [28.4, 23, 1.3, 8, 1], [15.5, 16, 3.8, 8, 0], [14.6, 16, 2.3, 0, 0],
    [9.8, 13, 6.3, 4, 0]
]

nombres = ['ENTREGADA', 'FALLIDA']

print("=" * 70)
print("ESCENARIO 14: LOGISTICA - ENTREGAS FALLIDAS EN ULTIMA MILLA")
print("=" * 70)
print("\nDescripcion del problema:")
print("Una paqueteria urbana quiere predecir que rutas tendran entregas")
print("fallidas (cliente ausente, direccion no encontrada, fuera de")
print("horario) para reprogramar o reasignar antes de salir.")
print("\nVariables analizadas:")
print("- Distancia de la ruta (km)")
print("- Numero de paradas en la ruta")
print("- Ventana horaria de entrega acordada (horas)")
print("- Incidencias previas en la zona")
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
print(" " * 22 + "Pred: ENTREGADA     Pred: FALLIDA       ")
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

# Analisis de costos de los errores (clase 1 = FALLIDA es el evento que queremos detectar)
costo_falso_negativo = 350   # MXN - segundo intento + queja del cliente
costo_falso_positivo = 80   # MXN - llamada de confirmacion innecesaria
fn = matriz[1][0]   # era FALLIDA y el modelo dijo ENTREGADA
fp = matriz[0][1]   # era ENTREGADA y el modelo dijo FALLIDA
costo_total = fn * costo_falso_negativo + fp * costo_falso_positivo
print("\nAnalisis de Costos:")
print(f"Falsos Negativos (entregas fallidas no detectadas): {fn} x ${costo_falso_negativo:,} = ${fn * costo_falso_negativo:,}")
print(f"Falsos Positivos (alertas falsas): {fp} x ${costo_falso_positivo:,} = ${fp * costo_falso_positivo:,}")
print(f"Costo Total de los errores: ${costo_total:,}")

# ===================================================================
# PREDICCIONES CON NUEVOS DATOS
# >>> MODIFICA AQUI: cambia los numeros para crear tus 3 casos <<<
# Orden: distancia_ruta_km, paradas_ruta, ventana_horaria_h, incidencias_previas, "descripcion"
# ===================================================================
nuevos_casos = [
    [15, 12, 4.5, 2, "Ruta Centro - manana"],
    [34, 28, 1.5, 7, "Ruta Periferia - tarde"],
    [24, 19, 3.0, 4, "Ruta Mixta - caso intermedio"],
]

print("\n" + "=" * 70)
print("EVALUACION DE NUEVOS CASOS")
print("=" * 70)
for fila in nuevos_casos:
    caso = fila[:4]
    resultado = nb.predecir(caso)
    prob = nb.predecir_proba(caso)
    print(f"\n{fila[4]}:")
    print(f"  Distancia de la ruta (km): {caso[0]:.1f}")
    print(f"  Numero de paradas en la ruta: {caso[1]:.0f}")
    print(f"  Ventana horaria de entrega acordada (horas): {caso[2]:.1f}")
    print(f"  Incidencias previas en la zona: {caso[3]:.0f}")
    print(f"  Prediccion: {nombres[resultado]}")
    for n in range(k):
        print(f"  Probabilidad {nombres[n]}: {prob[n] * 100:.1f}%")
    if sum(prob.values()) == 0:
        print("  (Valores muy lejanos a los datos de entrenamiento: revisa tus numeros)")
    if resultado == 1:
        print("  ACCIONES RECOMENDADAS:")
        print("     - Llamar al cliente para confirmar")
        print("     - Reducir paradas de la ruta")
        print("     - Ofrecer punto de recoleccion")

print("\n" + "=" * 70)
print("PREGUNTAS DE ANALISIS:")
print("=" * 70)
print("1. Cual variable influye mas en una entrega fallida?")
print("2. Por que la ventana horaria podria cambiar el resultado?")
print("3. Conviene mas tener falsos positivos o falsos negativos aqui?")
print("4. Que dato agregarias (dia de la semana, clima, tipo de cliente)?")
