# ESCENARIO 3: DIAGNOSTICO MEDICO - DETECCION DE DIABETES
# Actividad 5 - Clasificacion Bayesiana Ingenua - Universidad SABES 2026
# Python (Pydroid 3). NO requiere instalar librerias: solo usa "math".
#
# Columnas del dataset: glucosa_mg_dl, presion_mmhg, imc, edad, clase
# Clases: 0 = SANO, 1 = DIABETES

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


# Dataset (40 registros): glucosa_mg_dl, presion_mmhg, imc, edad, clase
datos = [
    [163, 67, 29.6, 57, 1], [98, 60, 33.5, 35, 0], [152, 62, 30.2, 62, 1],
    [122, 82, 38.6, 59, 1], [157, 85, 36.0, 29, 1], [126, 87, 26.6, 36, 1],
    [203, 56, 26.1, 43, 1], [82, 58, 28.8, 30, 0], [112, 84, 39.8, 57, 0],
    [100, 68, 36.0, 39, 0], [114, 75, 20.6, 33, 0], [92, 77, 44.0, 44, 0],
    [162, 76, 30.9, 53, 1], [94, 95, 25.3, 30, 0], [166, 75, 23.6, 63, 1],
    [85, 69, 25.1, 35, 0], [99, 72, 29.8, 28, 0], [134, 58, 38.4, 49, 1],
    [77, 99, 37.3, 25, 0], [81, 68, 35.7, 31, 0], [111, 44, 21.7, 58, 0],
    [155, 49, 36.3, 43, 0], [128, 69, 28.5, 40, 0], [125, 80, 28.4, 39, 0],
    [97, 53, 31.7, 24, 0], [163, 61, 34.3, 34, 0], [163, 89, 40.0, 43, 1],
    [86, 79, 28.6, 46, 0], [115, 75, 41.9, 45, 1], [200, 94, 36.3, 55, 1],
    [163, 76, 36.4, 53, 1], [127, 49, 29.4, 24, 0], [142, 65, 34.2, 55, 1],
    [77, 69, 32.0, 35, 0], [97, 84, 29.2, 50, 0], [108, 58, 33.1, 49, 0],
    [180, 78, 32.1, 34, 1], [101, 80, 30.5, 29, 0], [121, 70, 32.1, 19, 0],
    [205, 70, 35.9, 53, 1]
]

nombres = ['SANO', 'DIABETES']

print("=" * 70)
print("ESCENARIO 3: DIAGNOSTICO MEDICO - DETECCION DE DIABETES")
print("=" * 70)
print("\nDescripcion del problema:")
print("Un centro medico necesita un sistema de apoyo para identificar pacientes")
print("con alto riesgo de diabetes tipo 2 basado en indicadores clinicos.")
print("(Datos de practica, no usar para diagnosticos reales.)")
print("\nVariables analizadas:")
print("- Glucosa en sangre (mg/dL)")
print("- Presion arterial diastolica (mm Hg)")
print("- Indice de Masa Corporal (IMC)")
print("- Edad (anios)")
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
print(" " * 22 + "Pred: SANO          Pred: DIABETES      ")
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

# Analisis de costos de los errores (clase 1 = DIABETES es el evento que queremos detectar)
costo_falso_negativo = 20000   # MXN - tratamiento tardio y complicaciones
costo_falso_positivo = 800   # MXN - estudios de laboratorio adicionales
fn = matriz[1][0]   # era DIABETES y el modelo dijo SANO
fp = matriz[0][1]   # era SANO y el modelo dijo DIABETES
costo_total = fn * costo_falso_negativo + fp * costo_falso_positivo
print("\nAnalisis de Costos:")
print(f"Falsos Negativos (pacientes con diabetes no detectados): {fn} x ${costo_falso_negativo:,} = ${fn * costo_falso_negativo:,}")
print(f"Falsos Positivos (pacientes sanos enviados a estudios): {fp} x ${costo_falso_positivo:,} = ${fp * costo_falso_positivo:,}")
print(f"Costo Total de los errores: ${costo_total:,}")

# ===================================================================
# PREDICCIONES CON NUEVOS DATOS
# >>> MODIFICA AQUI: cambia los numeros para crear tus 3 casos <<<
# Orden: glucosa_mg_dl, presion_mmhg, imc, edad, "descripcion"
# ===================================================================
nuevos_casos = [
    [105, 70, 31.3, 37, "Paciente A - indicadores normales"],
    [160, 75, 33.5, 49, "Paciente B - indicadores elevados"],
    [132, 72, 32.4, 43, "Paciente C - caso intermedio"],
]

print("\n" + "=" * 70)
print("EVALUACION DE NUEVOS CASOS")
print("=" * 70)
for fila in nuevos_casos:
    caso = fila[:4]
    resultado = nb.predecir(caso)
    prob = nb.predecir_proba(caso)
    print(f"\n{fila[4]}:")
    print(f"  Glucosa en sangre (mg/dL): {caso[0]:.0f}")
    print(f"  Presion arterial diastolica (mm Hg): {caso[1]:.0f}")
    print(f"  Indice de Masa Corporal (IMC): {caso[2]:.1f}")
    print(f"  Edad (anios): {caso[3]:.0f}")
    print(f"  Prediccion: {nombres[resultado]}")
    for n in range(k):
        print(f"  Probabilidad {nombres[n]}: {prob[n] * 100:.1f}%")
    if sum(prob.values()) == 0:
        print("  (Valores muy lejanos a los datos de entrenamiento: revisa tus numeros)")
    if resultado == 1:
        print("  ACCIONES RECOMENDADAS:")
        print("     - Enviar a prueba de hemoglobina glucosilada")
        print("     - Canalizar con medico internista")
        print("     - Orientacion nutricional")

print("\n" + "=" * 70)
print("PREGUNTAS DE ANALISIS:")
print("=" * 70)
print("1. Es mas grave un falso negativo o un falso positivo en este contexto?")
print("2. Por que el recall (sensibilidad) es critico en diagnosticos medicos?")
print("3. Que limitaciones tiene usar solo 4 indicadores?")
print("4. Como mejorar la confiabilidad del modelo para uso clinico real?")
