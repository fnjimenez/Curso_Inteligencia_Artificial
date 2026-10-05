# ESCENARIO 5: PREDICCION DE RENDIMIENTO ACADEMICO
# Actividad 5 - Clasificacion Bayesiana Ingenua - Universidad SABES 2026
# Python (Pydroid 3). NO requiere instalar librerias: solo usa "math".
#
# Columnas del dataset: hrs_estudio_semanal, asistencia_pct, participacion_pct, tareas_pct, clase
# Clases: 0 = REPROBADO, 1 = APROBADO, 2 = EXCELENTE

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


# Dataset (40 registros): hrs_estudio_semanal, asistencia_pct, participacion_pct, tareas_pct, clase
datos = [
    [15, 94, 98, 93, 2], [14, 94, 68, 100, 2], [11, 55, 28, 45, 0],
    [17, 98, 92, 92, 2], [20, 100, 73, 83, 2], [20, 95, 96, 91, 2],
    [22, 96, 91, 88, 2], [1, 60, 26, 43, 0], [2, 88, 54, 69, 0],
    [13, 89, 94, 81, 2], [11, 98, 61, 88, 1], [1, 73, 39, 56, 0],
    [6, 47, 31, 65, 0], [8, 94, 53, 93, 1], [6, 76, 34, 52, 0],
    [20, 95, 83, 97, 2], [12, 81, 48, 88, 1], [5, 62, 43, 75, 0],
    [5, 87, 56, 66, 1], [7, 56, 43, 59, 0], [7, 69, 57, 55, 0],
    [4, 82, 61, 65, 1], [7, 62, 68, 69, 0], [10, 74, 45, 91, 1],
    [25, 100, 78, 98, 2], [7, 79, 44, 88, 1], [7, 69, 24, 55, 0],
    [20, 91, 86, 96, 2], [8, 76, 41, 67, 0], [10, 76, 43, 78, 1],
    [7, 64, 25, 47, 0], [19, 85, 85, 98, 2], [16, 88, 72, 90, 1],
    [12, 89, 94, 99, 2], [11, 87, 70, 83, 1], [6, 52, 24, 57, 0],
    [9, 78, 79, 59, 1], [19, 100, 85, 90, 2], [21, 82, 54, 88, 1],
    [9, 93, 65, 67, 1]
]

nombres = ['REPROBADO', 'APROBADO', 'EXCELENTE']

print("=" * 70)
print("ESCENARIO 5: PREDICCION DE RENDIMIENTO ACADEMICO")
print("=" * 70)
print("\nDescripcion del problema:")
print("Una universidad quiere identificar tempranamente estudiantes en riesgo")
print("de reprobar para ofrecerles apoyo academico oportuno.")
print("\nVariables analizadas:")
print("- Horas de estudio semanal")
print("- Asistencia (%)")
print("- Participacion en clase (%)")
print("- Tareas entregadas a tiempo (%)")
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
print(" " * 22 + "Pred: REPROBADO     Pred: APROBADO      Pred: EXCELENTE     ")
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
# Orden: hrs_estudio_semanal, asistencia_pct, participacion_pct, tareas_pct, "descripcion"
# ===================================================================
nuevos_casos = [
    [6, 65, 38, 58, "Estudiante A - en riesgo"],
    [10, 85, 58, 80, "Estudiante B - promedio"],
    [18, 94, 86, 93, "Estudiante C - destacado"],
]

print("\n" + "=" * 70)
print("EVALUACION DE NUEVOS CASOS")
print("=" * 70)
for fila in nuevos_casos:
    caso = fila[:4]
    resultado = nb.predecir(caso)
    prob = nb.predecir_proba(caso)
    print(f"\n{fila[4]}:")
    print(f"  Horas de estudio semanal: {caso[0]:.0f}")
    print(f"  Asistencia (%): {caso[1]:.0f}")
    print(f"  Participacion en clase (%): {caso[2]:.0f}")
    print(f"  Tareas entregadas a tiempo (%): {caso[3]:.0f}")
    print(f"  Prediccion: {nombres[resultado]}")
    for n in range(k):
        print(f"  Probabilidad {nombres[n]}: {prob[n] * 100:.1f}%")
    if sum(prob.values()) == 0:
        print("  (Valores muy lejanos a los datos de entrenamiento: revisa tus numeros)")
    if resultado == 0:
        print("  ACCIONES RECOMENDADAS:")
        print("     - Canalizar a asesoria academica")
        print("     - Tutoria con el docente")
        print("     - Plan de recuperacion de tareas")
    if resultado == 2:
        print("  ACCIONES RECOMENDADAS:")
        print("     - Invitar a proyecto de investigacion o concurso")

print("\n" + "=" * 70)
print("PREGUNTAS DE ANALISIS:")
print("=" * 70)
print("1. Que indicador predice mejor el riesgo de reprobar?")
print("2. Que error es mas grave: clasificar a un REPROBADO como APROBADO o al reves?")
print("3. Que intervenciones tempranas recomendarias?")
print("4. Que otros datos agregarias (calificaciones previas, trabajo, transporte)?")
