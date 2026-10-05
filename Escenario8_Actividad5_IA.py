# ESCENARIO 8: RECURSOS HUMANOS - PREDICCION DE AUSENTISMO
# Actividad 5 - Clasificacion Bayesiana Ingenua - Universidad SABES 2026
# Python (Pydroid 3). NO requiere instalar librerias: solo usa "math".
#
# Columnas del dataset: distancia_km, antiguedad_meses, hijos, nivel_estres, clase
# Clases: 0 = NORMAL, 1 = ALTO

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


# Dataset (40 registros): distancia_km, antiguedad_meses, hijos, nivel_estres, clase
datos = [
    [7, 78, 2, 7, 0], [44, 20, 4, 8, 1], [37, 8, 2, 4, 1],
    [37, 13, 5, 8, 1], [5, 74, 1, 1, 0], [50, 23, 3, 9, 1],
    [21, 27, 2, 4, 1], [7, 69, 1, 4, 0], [11, 35, 2, 2, 0],
    [25, 26, 2, 1, 1], [13, 74, 4, 7, 0], [7, 83, 1, 5, 0],
    [24, 82, 2, 4, 0], [14, 23, 3, 2, 0], [52, 29, 2, 3, 1],
    [44, 23, 2, 5, 1], [61, 21, 4, 7, 1], [22, 69, 1, 3, 0],
    [49, 14, 1, 9, 1], [12, 54, 1, 3, 0], [21, 46, 1, 5, 0],
    [15, 64, 2, 5, 0], [5, 79, 1, 6, 0], [16, 70, 2, 3, 0],
    [2, 63, 2, 9, 0], [46, 18, 3, 10, 1], [14, 91, 1, 5, 0],
    [27, 63, 2, 5, 0], [12, 59, 0, 3, 0], [8, 98, 0, 9, 0],
    [4, 86, 1, 3, 0], [35, 22, 2, 9, 1], [58, 9, 4, 8, 1],
    [26, 42, 2, 9, 1], [54, 17, 2, 9, 1], [16, 45, 0, 3, 0],
    [6, 36, 2, 2, 0], [26, 54, 1, 6, 0], [4, 38, 4, 5, 1],
    [1, 53, 0, 4, 0]
]

nombres = ['NORMAL', 'ALTO']

print("=" * 70)
print("ESCENARIO 8: RECURSOS HUMANOS - PREDICCION DE AUSENTISMO")
print("=" * 70)
print("\nDescripcion del problema:")
print("El departamento de RRHH necesita identificar empleados con alto riesgo")
print("de ausentismo para implementar estrategias de retencion y bienestar.")
print("\nVariables analizadas:")
print("- Distancia del hogar al trabajo (km)")
print("- Antiguedad en la empresa (meses)")
print("- Numero de hijos")
print("- Nivel de estres percibido (1-10)")
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
print(" " * 22 + "Pred: NORMAL        Pred: ALTO          ")
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

# Analisis de costos de los errores (clase 1 = ALTO es el evento que queremos detectar)
costo_falso_negativo = 9000   # MXN - horas extra y produccion perdida
costo_falso_positivo = 1000   # MXN - programa de bienestar innecesario
fn = matriz[1][0]   # era ALTO y el modelo dijo NORMAL
fp = matriz[0][1]   # era NORMAL y el modelo dijo ALTO
costo_total = fn * costo_falso_negativo + fp * costo_falso_positivo
print("\nAnalisis de Costos:")
print(f"Falsos Negativos (empleados con ausentismo alto no detectados): {fn} x ${costo_falso_negativo:,} = ${fn * costo_falso_negativo:,}")
print(f"Falsos Positivos (empleados marcados en riesgo sin serlo): {fp} x ${costo_falso_positivo:,} = ${fp * costo_falso_positivo:,}")
print(f"Costo Total de los errores: ${costo_total:,}")

# ===================================================================
# PREDICCIONES CON NUEVOS DATOS
# >>> MODIFICA AQUI: cambia los numeros para crear tus 3 casos <<<
# Orden: distancia_km, antiguedad_meses, hijos, nivel_estres, "descripcion"
# ===================================================================
nuevos_casos = [
    [12, 64, 1, 4, "Empleado A - perfil estable"],
    [40, 22, 3, 7, "Empleado B - alto riesgo"],
    [26, 43, 2, 6, "Empleado C - riesgo moderado"],
]

print("\n" + "=" * 70)
print("EVALUACION DE NUEVOS CASOS")
print("=" * 70)
for fila in nuevos_casos:
    caso = fila[:4]
    resultado = nb.predecir(caso)
    prob = nb.predecir_proba(caso)
    print(f"\n{fila[4]}:")
    print(f"  Distancia del hogar al trabajo (km): {caso[0]:.0f}")
    print(f"  Antiguedad en la empresa (meses): {caso[1]:.0f}")
    print(f"  Numero de hijos: {caso[2]:.0f}")
    print(f"  Nivel de estres percibido (1-10): {caso[3]:.0f}")
    print(f"  Prediccion: {nombres[resultado]}")
    for n in range(k):
        print(f"  Probabilidad {nombres[n]}: {prob[n] * 100:.1f}%")
    if sum(prob.values()) == 0:
        print("  (Valores muy lejanos a los datos de entrenamiento: revisa tus numeros)")
    if resultado == 1:
        print("  ACCIONES RECOMENDADAS:")
        print("     - Entrevista con RRHH")
        print("     - Evaluar horario flexible o transporte")
        print("     - Programa de manejo de estres")

print("\n" + "=" * 70)
print("PREGUNTAS DE ANALISIS:")
print("=" * 70)
print("1. Cual factor tiene mayor impacto en el ausentismo?")
print("2. Que retorno tendrian las intervenciones vs. el costo del ausentismo?")
print("3. Que implicaciones eticas tiene usar datos personales como el numero de hijos?")
print("4. Que politicas de balance vida-trabajo recomendarias?")
