# ESCENARIO 9: MANTENIMIENTO PREDICTIVO - DETECCION DE FALLAS
# Actividad 5 - Clasificacion Bayesiana Ingenua - Universidad SABES 2026
# Python (Pydroid 3). NO requiere instalar librerias: solo usa "math".
#
# Columnas del dataset: vibracion_mm_s, temperatura_motor_C, horas_operacion, dias_desde_mant, clase
# Clases: 0 = NORMAL, 1 = FALLA

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


# Dataset (40 registros): vibracion_mm_s, temperatura_motor_C, horas_operacion, dias_desde_mant, clase
datos = [
    [4.0, 72, 1216, 61, 0], [6.7, 102, 8669, 245, 1], [6.8, 65, 2621, 17, 0],
    [3.4, 91, 3868, 104, 1], [8.9, 80, 4709, 172, 1], [2.5, 61, 721, 52, 0],
    [1.9, 86, 2565, 16, 0], [4.3, 93, 839, 155, 0], [3.6, 64, 4442, 227, 1],
    [4.0, 56, 344, 55, 0], [3.9, 58, 2032, 12, 0], [12.3, 104, 2700, 211, 1],
    [1.4, 55, 2196, 5, 0], [2.5, 67, 563, 122, 0], [6.4, 69, 1037, 36, 0],
    [1.8, 74, 1234, 19, 0], [1.6, 57, 2801, 46, 0], [5.5, 65, 760, 77, 0],
    [6.6, 64, 4691, 137, 0], [10.0, 88, 7978, 174, 1], [10.4, 92, 6241, 203, 1],
    [2.1, 55, 1560, 33, 0], [8.2, 84, 5986, 210, 1], [12.1, 92, 4436, 216, 1],
    [2.1, 68, 2057, 30, 0], [10.3, 73, 3917, 196, 1], [4.2, 76, 2009, 58, 0],
    [5.6, 67, 1186, 63, 0], [1.3, 75, 788, 22, 0], [9.0, 109, 4835, 272, 1],
    [4.2, 63, 681, 77, 0], [5.0, 98, 3958, 68, 1], [7.7, 91, 2290, 105, 0],
    [3.4, 77, 1863, 56, 0], [2.8, 55, 658, 56, 0], [7.0, 108, 6194, 33, 1],
    [14.1, 105, 6128, 166, 1], [13.9, 112, 4024, 214, 1], [5.1, 68, 1001, 64, 0],
    [8.8, 103, 4911, 225, 1]
]

nombres = ['NORMAL', 'FALLA']

print("=" * 70)
print("ESCENARIO 9: MANTENIMIENTO PREDICTIVO - DETECCION DE FALLAS")
print("=" * 70)
print("\nDescripcion del problema:")
print("Una planta industrial necesita predecir fallas en maquinaria critica")
print("para implementar mantenimiento preventivo y evitar paros no programados.")
print("\nVariables analizadas:")
print("- Nivel de vibracion (mm/s)")
print("- Temperatura del motor (C)")
print("- Horas de operacion acumuladas")
print("- Dias desde el ultimo mantenimiento")
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
print(" " * 22 + "Pred: NORMAL        Pred: FALLA         ")
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

# Analisis de costos de los errores (clase 1 = FALLA es el evento que queremos detectar)
costo_falso_negativo = 80000   # MXN - paro no programado de la linea
costo_falso_positivo = 6000   # MXN - mano de obra y refacciones adelantadas
fn = matriz[1][0]   # era FALLA y el modelo dijo NORMAL
fp = matriz[0][1]   # era NORMAL y el modelo dijo FALLA
costo_total = fn * costo_falso_negativo + fp * costo_falso_positivo
print("\nAnalisis de Costos:")
print(f"Falsos Negativos (fallas no detectadas): {fn} x ${costo_falso_negativo:,} = ${fn * costo_falso_negativo:,}")
print(f"Falsos Positivos (mantenimientos innecesarios): {fp} x ${costo_falso_positivo:,} = ${fp * costo_falso_positivo:,}")
print(f"Costo Total de los errores: ${costo_total:,}")

# ===================================================================
# PREDICCIONES CON NUEVOS DATOS
# >>> MODIFICA AQUI: cambia los numeros para crear tus 3 casos <<<
# Orden: vibracion_mm_s, temperatura_motor_C, horas_operacion, dias_desde_mant, "descripcion"
# ===================================================================
nuevos_casos = [
    [3.8, 68, 1571, 57, "Maquina CNC-01"],
    [9.0, 94, 5187, 184, "Prensa hidraulica-05"],
    [6.4, 81, 3379, 120, "Torno industrial-03"],
]

print("\n" + "=" * 70)
print("EVALUACION DE NUEVOS CASOS")
print("=" * 70)
for fila in nuevos_casos:
    caso = fila[:4]
    resultado = nb.predecir(caso)
    prob = nb.predecir_proba(caso)
    print(f"\n{fila[4]}:")
    print(f"  Nivel de vibracion (mm/s): {caso[0]:.1f}")
    print(f"  Temperatura del motor (C): {caso[1]:.0f}")
    print(f"  Horas de operacion acumuladas: {caso[2]:.0f}")
    print(f"  Dias desde el ultimo mantenimiento: {caso[3]:.0f}")
    print(f"  Prediccion: {nombres[resultado]}")
    for n in range(k):
        print(f"  Probabilidad {nombres[n]}: {prob[n] * 100:.1f}%")
    if sum(prob.values()) == 0:
        print("  (Valores muy lejanos a los datos de entrenamiento: revisa tus numeros)")
    if resultado == 1:
        print("  ACCIONES RECOMENDADAS:")
        print("     - Programar mantenimiento inmediato")
        print("     - Revisar rodamientos y lubricacion")
        print("     - Preparar equipo de respaldo")

print("\n" + "=" * 70)
print("PREGUNTAS DE ANALISIS:")
print("=" * 70)
print("1. Cual parametro es el mejor indicador de falla inminente?")
print("2. Conviene mas el mantenimiento preventivo o el predictivo? Por que?")
print("3. Como justificar la inversion en sensores con este modelo?")
print("4. Que impacto tiene en el OEE (Overall Equipment Effectiveness)?")
