# ESCENARIO 7: Seguridad Industrial - Prediccion de Accidentes
# Dataset: Condiciones de trabajo y accidentes en fabrica

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

# Dataset: Accidentes en Fabrica
# Columnas: hrs_trabajo_continuo, nivel_ruido_db, temperatura_C, dias_sin_capacitacion, accidente
datos = [
    [6, 75, 22, 5, 0], [12, 95, 35, 45, 1], [7, 78, 24, 8, 0],
    [14, 98, 38, 60, 1], [5, 70, 20, 3, 0], [11, 92, 33, 40, 1],
    [6, 72, 21, 4, 0], [13, 96, 36, 55, 1], [7, 76, 23, 7, 0],
    [15, 100, 40, 65, 1], [6, 74, 22, 6, 0], [12, 94, 34, 42, 1],
    [7, 77, 24, 9, 0], [14, 97, 37, 58, 1], [5, 71, 21, 4, 0],
    [11, 91, 32, 38, 1], [6, 73, 22, 5, 0], [13, 95, 35, 50, 1],
    [7, 75, 23, 8, 0], [12, 93, 33, 44, 1], [6, 76, 22, 6, 0],
    [14, 99, 39, 62, 1], [5, 72, 21, 3, 0], [11, 90, 31, 36, 1],
    [7, 74, 23, 7, 0], [13, 96, 36, 52, 1], [6, 75, 22, 5, 0],
    [12, 92, 34, 46, 1], [7, 77, 24, 8, 0], [15, 98, 38, 64, 1]
]

print("="*70)
print("ESCENARIO 7: SEGURIDAD INDUSTRIAL - PREDICCION DE ACCIDENTES")
print("="*70)
print("\nDescripcion del problema:")
print("Una planta industrial necesita identificar condiciones de alto riesgo")
print("para prevenir accidentes laborales y proteger a sus trabajadores.")
print("\nFactores de riesgo evaluados:")
print("- Horas de trabajo continuo")
print("- Nivel de ruido ambiental (dB)")
print("- Temperatura en area de trabajo (°C)")
print("- Dias desde ultima capacitacion en seguridad")

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

# Metricas de seguridad
VP = matriz[1][1]  # Verdaderos Positivos - Riesgos detectados
FN = matriz[1][0]  # Falsos Negativos - Riesgos no detectados (CRITICO)
VN = matriz[0][0]  # Verdaderos Negativos - Condiciones seguras
FP = matriz[0][1]  # Falsos Positivos - Alarmas falsas

sensibilidad = (VP / (VP + FN) * 100) if (VP + FN) > 0 else 0

print(f"\n{'='*70}")
print("RESULTADOS DEL ANALISIS DE SEGURIDAD")
print("="*70)
print(f"Accuracy: {accuracy:.2f}%")
print(f"Sensibilidad (deteccion de riesgos): {sensibilidad:.2f}%")
print(f"⚠️ CRITICO - Riesgos NO detectados: {FN}")

print(f"\nMatriz de Confusion:")
print(f"                    Pred: SEGURO  Pred: RIESGO")
print(f"Real: SEGURO              {matriz[0][0]:3}         {matriz[0][1]:3}")
print(f"Real: RIESGO              {matriz[1][0]:3}         {matriz[1][1]:3}")

# Evaluacion de turnos
turnos_evaluacion = [
    [7, 76, 24, 8, "Turno Matutino - Area A"],
    [13, 96, 36, 52, "Turno Nocturno - Area B (Alto Riesgo)"],
    [10, 88, 30, 25, "Turno Vespertino - Area C"]
]

print(f"\n{'='*70}")
print("EVALUACION DE CONDICIONES POR TURNO")
print("="*70)

for turno in turnos_evaluacion:
    datos_turno = turno[:4]
    descripcion = turno[4]
    resultado = nb.predecir(datos_turno)
    prob = nb.predecir_proba(datos_turno)
    
    print(f"\n{descripcion}:")
    print(f"  Horas trabajo continuo: {datos_turno[0]} hrs")
    print(f"  Nivel ruido: {datos_turno[1]} dB")
    print(f"  Temperatura: {datos_turno[2]}°C")
    print(f"  Dias sin capacitacion: {datos_turno[3]}")
    print(f"  Evaluacion: {'🚨 ALTO RIESGO DE ACCIDENTE' if resultado == 1 else '✓ CONDICIONES SEGURAS'}")
    print(f"  Probabilidad SEGURO: {prob.get(0, 0)*100:.1f}%")
    print(f"  Probabilidad RIESGO: {prob.get(1, 0)*100:.1f}%")
    
    if resultado == 1:
        print(f"  ⚠️ MEDIDAS CORRECTIVAS INMEDIATAS:")
        if datos_turno[0] > 10:
            print(f"     - Reducir horas de trabajo continuo")
            print(f"     - Implementar rotacion de personal")
        if datos_turno[1] > 90:
            print(f"     - Proporcionar proteccion auditiva reforzada")
            print(f"     - Evaluar aislamiento acustico")
        if datos_turno[2] > 32:
            print(f"     - Mejorar ventilacion/climatizacion")
            print(f"     - Hidratacion obligatoria cada hora")
        if datos_turno[3] > 30:
            print(f"     - Capacitacion urgente en seguridad")
            print(f"     - Revision de procedimientos")

print(f"\n{'='*70}")
print("PREGUNTAS DE ANALISIS:")
print("="*70)
print("1. ¿Por que es critico minimizar los falsos negativos en seguridad?")
print("2. ¿Cual factor de riesgo requiere intervencion mas urgente?")
print("3. ¿Como impacta el costo de un accidente vs. medidas preventivas?")
print("4. ¿Que normativas de seguridad (OSHA/STPS) se relacionan con estos factores?")
print("5. ¿Como implementarias un sistema de alertas tempranas?")