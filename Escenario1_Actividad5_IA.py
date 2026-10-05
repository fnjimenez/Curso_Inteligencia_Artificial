# ESCENARIO 1: CONTROL DE CALIDAD EN MANUFACTURA
# Actividad 5 - Clasificacion Bayesiana Ingenua - Universidad SABES 2026
# Python (Pydroid 3). NO requiere instalar librerias: solo usa "math".
#
# Columnas del dataset: diametro_mm, longitud_mm, peso_g, temperatura_C, clase
# Clases: 0 = APTA, 1 = DEFECTUOSA

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


# Dataset (40 registros): diametro_mm, longitud_mm, peso_g, temperatura_C, clase
datos = [
    [10.9, 48.7, 45.9, 199, 1], [10.2, 52.0, 47.7, 202, 1], [10.0, 50.0, 45.3, 179, 0],
    [10.1, 49.8, 45.3, 181, 0], [10.0, 50.1, 45.2, 183, 0], [9.9, 50.0, 44.8, 178, 0],
    [10.2, 50.1, 45.3, 184, 0], [10.2, 50.2, 44.7, 184, 0], [10.0, 50.4, 43.1, 173, 1],
    [10.3, 50.1, 45.1, 181, 0], [10.1, 49.8, 45.4, 180, 0], [9.8, 50.1, 45.2, 182, 0],
    [10.1, 49.8, 45.4, 179, 0], [9.9, 50.1, 45.7, 182, 1], [10.2, 49.8, 46.0, 172, 1],
    [10.4, 50.2, 45.0, 181, 0], [9.3, 50.2, 45.0, 183, 1], [9.7, 50.1, 45.4, 183, 0],
    [9.9, 50.4, 45.3, 184, 0], [9.8, 50.0, 44.8, 180, 0], [10.5, 50.1, 45.2, 180, 0],
    [9.5, 50.0, 44.4, 185, 1], [9.6, 50.5, 49.2, 186, 1], [10.2, 50.6, 44.8, 180, 0],
    [10.5, 50.0, 44.8, 181, 0], [10.1, 49.9, 45.3, 181, 0], [9.8, 50.3, 45.3, 183, 0],
    [9.7, 50.2, 45.2, 181, 0], [9.3, 50.3, 45.4, 179, 1], [10.7, 50.8, 45.8, 184, 1],
    [10.3, 50.0, 45.4, 182, 0], [9.8, 50.1, 45.2, 180, 0], [9.9, 50.4, 45.3, 182, 0],
    [9.7, 50.2, 45.4, 182, 0], [11.6, 52.7, 44.3, 187, 1], [10.0, 50.5, 45.1, 181, 0],
    [10.2, 48.3, 45.4, 194, 1], [10.4, 50.0, 45.6, 181, 0], [9.3, 49.0, 41.1, 181, 1],
    [11.2, 48.7, 46.4, 183, 1]
]

nombres = ['APTA', 'DEFECTUOSA']

print("=" * 70)
print("ESCENARIO 1: CONTROL DE CALIDAD EN MANUFACTURA")
print("=" * 70)
print("\nDescripcion del problema:")
print("Una fabrica produce piezas metalicas y necesita clasificarlas como")
print("APTAS o DEFECTUOSAS segun sus dimensiones y temperatura de fundicion.")
print("\nVariables analizadas:")
print("- Diametro (mm)")
print("- Longitud (mm)")
print("- Peso (g)")
print("- Temperatura de fundicion (C)")
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
print(" " * 22 + "Pred: APTA          Pred: DEFECTUOSA    ")
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

# Analisis de costos de los errores (clase 1 = DEFECTUOSA es el evento que queremos detectar)
costo_falso_negativo = 2500   # MXN - reclamo y devolucion del cliente
costo_falso_positivo = 300   # MXN - reproceso o desperdicio de una pieza buena
fn = matriz[1][0]   # era DEFECTUOSA y el modelo dijo APTA
fp = matriz[0][1]   # era APTA y el modelo dijo DEFECTUOSA
costo_total = fn * costo_falso_negativo + fp * costo_falso_positivo
print("\nAnalisis de Costos:")
print(f"Falsos Negativos (piezas defectuosas que pasan al cliente): {fn} x ${costo_falso_negativo:,} = ${fn * costo_falso_negativo:,}")
print(f"Falsos Positivos (piezas buenas rechazadas): {fp} x ${costo_falso_positivo:,} = ${fp * costo_falso_positivo:,}")
print(f"Costo Total de los errores: ${costo_total:,}")

# ===================================================================
# PREDICCIONES CON NUEVOS DATOS
# >>> MODIFICA AQUI: cambia los numeros para crear tus 3 casos <<<
# Orden: diametro_mm, longitud_mm, peso_g, temperatura_C, "descripcion"
# ===================================================================
nuevos_casos = [
    [10.2, 50.1, 45.2, 181, "Pieza #A101 - lote normal"],
    [11.6, 51.4, 47.3, 193, "Pieza #A102 - lote sospechoso"],
    [10.6, 50.6, 45.9, 186, "Pieza #A103 - caso intermedio"],
]

print("\n" + "=" * 70)
print("EVALUACION DE NUEVOS CASOS")
print("=" * 70)
for fila in nuevos_casos:
    caso = fila[:4]
    resultado = nb.predecir(caso)
    prob = nb.predecir_proba(caso)
    print(f"\n{fila[4]}:")
    print(f"  Diametro (mm): {caso[0]:.1f}")
    print(f"  Longitud (mm): {caso[1]:.1f}")
    print(f"  Peso (g): {caso[2]:.1f}")
    print(f"  Temperatura de fundicion (C): {caso[3]:.0f}")
    print(f"  Prediccion: {nombres[resultado]}")
    for n in range(k):
        print(f"  Probabilidad {nombres[n]}: {prob[n] * 100:.1f}%")
    if sum(prob.values()) == 0:
        print("  (Valores muy lejanos a los datos de entrenamiento: revisa tus numeros)")
    if resultado == 1:
        print("  ACCIONES RECOMENDADAS:")
        print("     - Separar la pieza para inspeccion manual")
        print("     - Revisar temperatura del horno")
        print("     - Verificar calibracion del molde")

print("\n" + "=" * 70)
print("PREGUNTAS DE ANALISIS:")
print("=" * 70)
print("1. Que tan confiable es el modelo con este accuracy?")
print("2. Cual seria el costo de clasificar una pieza DEFECTUOSA como APTA?")
print("3. Que caracteristica parece ser mas determinante para la clasificacion?")
print("4. Como podrias mejorar el modelo con mas datos?")
