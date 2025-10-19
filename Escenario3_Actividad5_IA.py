# ESCENARIO 3: Diagnostico Medico - Diabetes
# Dataset: Pacientes evaluados para diabetes tipo 2

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

# Dataset: Diagnostico de Diabetes
# Columnas: glucosa, presion_arterial, IMC, edad, diabetes
datos = [
    [148, 72, 33.6, 50, 1], [85, 66, 26.6, 31, 0], [183, 64, 23.3, 32, 1],
    [89, 66, 28.1, 21, 0], [137, 40, 43.1, 33, 1], [116, 74, 25.6, 30, 0],
    [78, 50, 31.0, 26, 0], [115, 0, 35.3, 29, 0], [197, 70, 30.5, 53, 1],
    [125, 96, 0.0, 54, 1], [110, 92, 37.6, 30, 0], [168, 74, 38.0, 34, 1],
    [139, 80, 27.1, 57, 0], [189, 60, 30.1, 59, 1], [166, 72, 25.8, 51, 1],
    [100, 0, 30.0, 32, 0], [118, 84, 45.8, 31, 1], [107, 74, 29.6, 31, 0],
    [103, 30, 43.3, 33, 0], [115, 70, 30.5, 34, 0], [126, 88, 39.3, 27, 0],
    [99, 84, 35.4, 50, 0], [196, 90, 39.8, 41, 1], [119, 80, 35.3, 29, 0],
    [143, 94, 36.6, 51, 1], [125, 70, 31.1, 41, 1], [147, 76, 39.4, 43, 1],
    [97, 66, 23.2, 22, 0], [145, 82, 32.5, 70, 1], [117, 92, 34.1, 38, 0]
]

print("="*70)
print("ESCENARIO 3: DIAGNOSTICO MEDICO - DETECCION DE DIABETES")
print("="*70)
print("\nDescripcion del problema:")
print("Un centro medico necesita un sistema de apoyo para identificar pacientes")
print("con alto riesgo de diabetes tipo 2 basado en indicadores clinicos.")
print("\nIndicadores medicos evaluados:")
print("- Nivel de glucosa en sangre (mg/dL)")
print("- Presion arterial diastolica (mm Hg)")
print("- Indice de Masa Corporal (IMC)")
print("- Edad (años)")

random.shuffle(datos)
split = int(len(datos) * 0.75)

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

# Metricas medicas importantes
VP = matriz[1][1]  # Verdaderos Positivos
FN = matriz[1][0]  # Falsos Negativos
VN = matriz[0][0]  # Verdaderos Negativos
FP = matriz[0][1]  # Falsos Positivos

sensibilidad = (VP / (VP + FN) * 100) if (VP + FN) > 0 else 0
especificidad = (VN / (VN + FP) * 100) if (VN + FP) > 0 else 0

print(f"\n{'='*70}")
print("RESULTADOS DEL ANALISIS CLINICO")
print("="*70)
print(f"Accuracy: {accuracy:.2f}%")
print(f"Sensibilidad (detecta enfermos): {sensibilidad:.2f}%")
print(f"Especificidad (detecta sanos): {especificidad:.2f}%")

print(f"\nMatriz de Confusion:")
print(f"                    Pred: SANO  Pred: DIABETES")
print(f"Real: SANO               {matriz[0][0]:3}         {matriz[0][1]:3}")
print(f"Real: DIABETES           {matriz[1][0]:3}         {matriz[1][1]:3}")

# Diagnostico de nuevo paciente
nuevo_paciente = [155, 78, 34.2, 45]
resultado = nb.predecir(nuevo_paciente)
prob = nb.predecir_proba(nuevo_paciente)

print(f"\n{'='*70}")
print("EVALUACION DE NUEVO PACIENTE")
print("="*70)
print(f"Glucosa: {nuevo_paciente[0]} mg/dL")
print(f"Presion arterial: {nuevo_paciente[1]} mm Hg")
print(f"IMC: {nuevo_paciente[2]}")
print(f"Edad: {nuevo_paciente[3]} años")
print(f"\nDiagnostico: {'RIESGO DE DIABETES ⚠️' if resultado == 1 else 'PERFIL NORMAL ✓'}")
print(f"Confianza diagnostico SANO: {prob.get(0, 0)*100:.2f}%")
print(f"Confianza diagnostico DIABETES: {prob.get(1, 0)*100:.2f}%")

print(f"\n{'='*70}")
print("PREGUNTAS DE ANALISIS MEDICO:")
print("="*70)
print("1. ¿Es mas grave un falso negativo o un falso positivo en este contexto?")
print("2. ¿Por que la sensibilidad es critica en diagnosticos medicos?")
print("3. ¿Que cambios en el estilo de vida recomendarias al paciente evaluado?")
print("4. ¿Como mejorar la confiabilidad del modelo para uso clinico real?")