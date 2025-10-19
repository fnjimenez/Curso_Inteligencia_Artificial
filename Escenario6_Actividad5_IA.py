# ESCENARIO 6: Logistica Internacional - Retrasos en Envios
# Dataset: Envios internacionales y prediccion de retrasos

import math
import random

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
        for clase, valores in self.clases.items():
            self.estadisticas[clase] = []
            for i in range(len(valores[0])):
                columna = [fila[i] for fila in valores]
                media = self.calcular_media(columna)
                desviacion = self.calcular_desviacion(columna)
                self.estadisticas[clase].append((media, desviacion))
    def predecir_proba(self, x):
        probabilidades = {}
        for clase, stats in self.estadisticas.items():
            probabilidades[clase] = 1
            for i in range(len(x)):
                media, desviacion = stats[i]
                probabilidades[clase] *= self.probabilidad_gaussiana(x[i], media, desviacion)
        total = sum(probabilidades.values())
        if total > 0:
            for clase in probabilidades:
                probabilidades[clase] /= total
        return probabilidades
    def predecir(self, x):
        probabilidades = self.predecir_proba(x)
        return max(probabilidades, key=probabilidades.get)

# Dataset: Envios Internacionales
# Columnas: distancia_km, peso_kg, num_documentos_aduanales, dias_procesamiento, retraso
datos = [
    [5200, 850, 3, 2, 0], [12500, 2300, 8, 5, 1], [3800, 450, 2, 1, 0],
    [15000, 3200, 12, 7, 1], [4100, 620, 2, 2, 0], [8900, 1800, 6, 4, 1],
    [3200, 380, 2, 1, 0], [11200, 2100, 9, 6, 1], [4800, 720, 3, 2, 0],
    [13800, 2850, 11, 6, 1], [3500, 410, 2, 1, 0], [9500, 1950, 7, 5, 1],
    [4500, 680, 3, 2, 0], [14200, 3100, 13, 8, 1], [3900, 520, 2, 2, 0],
    [10800, 2200, 8, 5, 1], [3300, 390, 2, 1, 0], [12800, 2650, 10, 6, 1],
    [4200, 590, 3, 2, 0], [11800, 2400, 9, 5, 1], [3600, 430, 2, 1, 0],
    [13200, 2900, 11, 7, 1], [4700, 710, 3, 2, 0], [9200, 1850, 7, 4, 1],
    [3400, 400, 2, 1, 0], [14800, 3300, 14, 8, 1], [4400, 640, 3, 2, 0],
    [10200, 2050, 8, 5, 1], [3700, 480, 2, 1, 0], [12200, 2500, 10, 6, 1]
]

print("="*70)
print("ESCENARIO 6: LOGISTICA INTERNACIONAL - PREDICCION DE RETRASOS")
print("="*70)
print("\nDescripcion del problema:")
print("Una empresa de logistica internacional necesita predecir que envios")
print("tienen alto riesgo de retrasarse para tomar medidas preventivas.")
print("\nFactores logisticos analizados:")
print("- Distancia del envio (km)")
print("- Peso del paquete (kg)")
print("- Numero de documentos aduanales requeridos")
print("- Dias de procesamiento en aduana")

random.shuffle(datos)
split = int(len(datos) * 0.7)

X_train = [d[:4] for d in datos[:split]]
y_train = [d[4] for d in datos[:split]]
X_test = [d[:4] for d in datos[split:]]
y_test = [d[4] for d in datos[split:]]

nb = NaiveBayes()
nb.entrenar(X_train, y_train)

correctos = sum(1 for i in range(len(X_test)) if nb.predecir(X_test[i]) == y_test[i])
accuracy = (correctos / len(X_test)) * 100

matriz = [[0, 0], [0, 0]]
for i in range(len(X_test)):
    matriz[y_test[i]][nb.predecir(X_test[i])] += 1

print(f"\n{'='*70}")
print("RESULTADOS DEL ANALISIS")
print("="*70)
print(f"Accuracy: {accuracy:.2f}%")
print(f"Envios correctamente clasificados: {correctos}/{len(X_test)}")

print(f"\nMatriz de Confusion:")
print(f"                    Pred: A TIEMPO  Pred: RETRASO")
print(f"Real: A TIEMPO            {matriz[0][0]:3}           {matriz[0][1]:3}")
print(f"Real: RETRASO             {matriz[1][0]:3}           {matriz[1][1]:3}")

# Analisis de costo
costo_falso_negativo = 5000  # USD - compensacion por retraso no previsto
costo_falso_positivo = 500   # USD - costo de medidas preventivas innecesarias

costo_total = (matriz[1][0] * costo_falso_negativo) + (matriz[0][1] * costo_falso_positivo)

print(f"\nAnalisis de Costos:")
print(f"Falsos Negativos (retrasos no detectados): {matriz[1][0]} x ${costo_falso_negativo} = ${matriz[1][0] * costo_falso_negativo}")
print(f"Falsos Positivos (alarmas falsas): {matriz[0][1]} x ${costo_falso_positivo} = ${matriz[0][1] * costo_falso_positivo}")
print(f"Costo Total: ${costo_total}")

# Evaluacion de nuevos envios
nuevos_envios = [
    [4500, 650, 3, 2, "Envio Nacional - Standard"],
    [13500, 2800, 11, 7, "Envio Internacional - Complejo"],
    [7200, 1200, 5, 3, "Envio Regional - Moderado"]
]

print(f"\n{'='*70}")
print("EVALUACION DE ENVIOS PENDIENTES")
print("="*70)

for envio in nuevos_envios:
    datos_envio = envio[:4]
    descripcion = envio[4]
    resultado = nb.predecir(datos_envio)
    prob = nb.predecir_proba(datos_envio)
    
    print(f"\n{descripcion}:")
    print(f"  Distancia: {datos_envio[0]:,} km")
    print(f"  Peso: {datos_envio[1]} kg")
    print(f"  Documentos aduanales: {datos_envio[2]}")
    print(f"  Dias procesamiento: {datos_envio[3]}")
    print(f"  Prediccion: {'🚨 RIESGO DE RETRASO' if resultado == 1 else '✓ ENTREGA A TIEMPO'}")
    print(f"  Confianza A TIEMPO: {prob.get(0, 0)*100:.1f}%")
    print(f"  Confianza RETRASO: {prob.get(1, 0)*100:.1f}%")
    
    if resultado == 1:
        print(f"  📋 ACCIONES RECOMENDADAS:")
        print(f"     - Acelerar tramites aduanales")
        print(f"     - Notificar cliente sobre posible retraso")
        print(f"     - Asignar courier premium")
        print(f"     - Revisar ruta alternativa")

print(f"\n{'='*70}")
print("PREGUNTAS DE ANALISIS:")
print("="*70)
print("1. ¿Cual factor logistico impacta mas en los retrasos?")
print("2. ¿Como optimizarias la relacion costo-beneficio del modelo?")
print("3. ¿Que KPIs logisticos adicionales deberian considerarse?")
print("4. ¿Como afectan las regulaciones aduanales al modelo predictivo?")