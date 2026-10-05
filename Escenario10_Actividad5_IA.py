# ESCENARIO 10: CONTROL DE CALIDAD AVANZADO - 3 CATEGORIAS
# Actividad 5 - Clasificacion Bayesiana Ingenua - Universidad SABES 2026
# Python (Pydroid 3). NO requiere instalar librerias: solo usa "math".
#
# Columnas del dataset: espesor_mm, dureza_shore, rugosidad_ra, peso_g, clase
# Clases: 0 = DEFECTUOSO, 1 = ACEPTABLE, 2 = PREMIUM

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


# Dataset (40 registros): espesor_mm, dureza_shore, rugosidad_ra, peso_g, clase
datos = [
    [4.79, 80, 1.9, 99.8, 0], [4.97, 84, 0.3, 103.7, 1], [4.85, 76, 1.9, 97.4, 0],
    [5.03, 86, 0.7, 101.7, 1], [4.84, 77, 1.5, 96.2, 0], [4.98, 84, 0.4, 101.4, 1],
    [4.96, 86, 0.4, 103.9, 2], [5.01, 89, 0.5, 103.0, 2], [4.98, 86, 0.6, 102.7, 1],
    [4.97, 86, 0.7, 102.3, 1], [4.81, 76, 2.1, 98.4, 0], [4.77, 76, 2.1, 99.3, 0],
    [4.83, 75, 1.1, 97.7, 0], [4.99, 85, 0.8, 101.9, 1], [5.02, 91, 0.3, 103.5, 2],
    [5.03, 90, 0.5, 104.3, 2], [4.8, 72, 1.7, 97.1, 0], [5.0, 90, 0.7, 104.0, 2],
    [4.79, 76, 1.7, 98.4, 0], [5.03, 90, 0.2, 103.8, 2], [5.03, 89, 1.0, 104.2, 2],
    [4.99, 83, 0.8, 101.8, 1], [4.98, 80, 0.8, 103.5, 1], [5.05, 86, 0.9, 101.7, 1],
    [4.76, 74, 2.0, 98.0, 0], [5.0, 84, 0.5, 103.6, 1], [5.01, 86, 0.6, 103.0, 2],
    [5.03, 88, 0.4, 103.2, 2], [5.01, 89, 0.2, 103.9, 2], [4.82, 75, 1.5, 98.6, 0],
    [4.99, 92, 0.9, 102.5, 1], [5.03, 96, 0.3, 104.0, 2], [4.86, 75, 1.8, 97.4, 0],
    [4.81, 77, 1.1, 97.6, 0], [5.04, 86, 0.7, 102.2, 1], [5.05, 95, 0.7, 103.5, 2],
    [5.02, 88, 0.5, 103.3, 2], [4.79, 74, 1.2, 96.6, 0], [4.75, 73, 1.4, 98.9, 0],
    [5.04, 87, 1.1, 103.2, 1]
]

nombres = ['DEFECTUOSO', 'ACEPTABLE', 'PREMIUM']

print("=" * 70)
print("ESCENARIO 10: CONTROL DE CALIDAD AVANZADO - 3 CATEGORIAS")
print("=" * 70)
print("\nDescripcion del problema:")
print("Una planta manufacturera necesita clasificar productos automaticamente")
print("en categorias de calidad para optimizar su comercializacion.")
print("\nVariables analizadas:")
print("- Espesor de la pieza (mm)")
print("- Dureza Shore A")
print("- Rugosidad superficial (Ra um)")
print("- Peso (g)")
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
print(" " * 22 + "Pred: DEFECTUOSO    Pred: ACEPTABLE     Pred: PREMIUM       ")
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
# Orden: espesor_mm, dureza_shore, rugosidad_ra, peso_g, "descripcion"
# ===================================================================
nuevos_casos = [
    [4.8, 75, 1.6, 98.0, "Pieza #1247"],
    [5.0, 85, 0.7, 102.5, "Pieza #1248"],
    [5.02, 90, 0.5, 103.7, "Pieza #1249"],
]

print("\n" + "=" * 70)
print("EVALUACION DE NUEVOS CASOS")
print("=" * 70)
for fila in nuevos_casos:
    caso = fila[:4]
    resultado = nb.predecir(caso)
    prob = nb.predecir_proba(caso)
    print(f"\n{fila[4]}:")
    print(f"  Espesor de la pieza (mm): {caso[0]:.2f}")
    print(f"  Dureza Shore A: {caso[1]:.0f}")
    print(f"  Rugosidad superficial (Ra um): {caso[2]:.1f}")
    print(f"  Peso (g): {caso[3]:.1f}")
    print(f"  Prediccion: {nombres[resultado]}")
    for n in range(k):
        print(f"  Probabilidad {nombres[n]}: {prob[n] * 100:.1f}%")
    if sum(prob.values()) == 0:
        print("  (Valores muy lejanos a los datos de entrenamiento: revisa tus numeros)")
    if resultado == 0:
        print("  ACCIONES RECOMENDADAS:")
        print("     - Rechazar la pieza")
        print("     - Revisar parametros del proceso")
    if resultado == 2:
        print("  ACCIONES RECOMENDADAS:")
        print("     - Enviar a linea de venta premium")

print("\n" + "=" * 70)
print("PREGUNTAS DE ANALISIS:")
print("=" * 70)
print("1. Cual parametro es mas critico para distinguir PREMIUM de ACEPTABLE?")
print("2. Como afecta la clasificacion incorrecta al margen de utilidad?")
print("3. Que mejoras de proceso reducirian los productos defectuosos?")
print("4. Vale la pena invertir en sensores automaticos vs. inspeccion manual?")
