# ESCENARIO 2: Prediccion de Abandono de Clientes (Churn)
# Dataset: Clientes de telecomunicaciones

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

# Dataset: Abandono de Clientes
# Columnas: meses_servicio, llamadas_soporte, gasto_mensual, minutos_uso, abandono
datos = [
    [24, 2, 65.5, 450, 0], [12, 5, 45.2, 320, 1], [36, 1, 78.3, 520, 0],
    [6, 8, 35.0, 180, 1], [48, 0, 85.6, 600, 0], [18, 4, 52.1, 280, 1],
    [30, 1, 72.4, 480, 0], [9, 6, 38.5, 210, 1], [42, 2, 80.2, 550, 0],
    [15, 7, 42.3, 250, 1], [27, 3, 68.7, 430, 0], [8, 9, 33.8, 160, 1],
    [33, 1, 75.5, 500, 0], [14, 5, 48.9, 290, 1], [40, 2, 82.1, 570, 0],
    [11, 6, 40.2, 230, 1], [25, 2, 66.8, 460, 0], [7, 10, 32.5, 150, 1],
    [35, 1, 77.9, 530, 0], [13, 4, 46.7, 270, 1], [29, 2, 70.3, 490, 0],
    [10, 7, 37.6, 200, 1], [44, 1, 83.4, 580, 0], [16, 5, 50.5, 300, 1],
    [31, 3, 73.2, 470, 0], [19, 4, 54.8, 310, 1], [38, 1, 79.6, 540, 0],
    [17, 6, 51.3, 285, 1], [26, 2, 67.9, 445, 0], [20, 3, 56.4, 330, 1]
]

print("="*70)
print("ESCENARIO 2: PREDICCION DE ABANDONO DE CLIENTES (CHURN)")
print("="*70)
print("\nDescripcion del problema:")
print("Una empresa de telecomunicaciones quiere predecir que clientes tienen")
print("mayor riesgo de cancelar su servicio (abandono/churn).")
print("\nCaracteristicas analizadas:")
print("- Meses de servicio con la empresa")
print("- Numero de llamadas al soporte tecnico")
print("- Gasto mensual promedio (USD)")
print("- Minutos de uso mensual")

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

print(f"\nMatriz de Confusion:")
print(f"                    Pred: SE QUEDA  Pred: ABANDONA")
print(f"Real: SE QUEDA            {matriz[0][0]:3}            {matriz[0][1]:3}")
print(f"Real: ABANDONA            {matriz[1][0]:3}            {matriz[1][1]:3}")

# Analisis de cliente nuevo
nuevo_cliente = [22, 4, 58.5, 350]
resultado = nb.predecir(nuevo_cliente)
prob = nb.predecir_proba(nuevo_cliente)

print(f"\n{'='*70}")
print("ANALISIS DE CLIENTE NUEVO")
print("="*70)
print(f"Meses de servicio: {nuevo_cliente[0]}")
print(f"Llamadas a soporte: {nuevo_cliente[1]}")
print(f"Gasto mensual: ${nuevo_cliente[2]}")
print(f"Minutos de uso: {nuevo_cliente[3]}")
print(f"\nPrediccion: {'RIESGO DE ABANDONO ⚠️' if resultado == 1 else 'SE MANTIENE ✓'}")
print(f"Probabilidad de quedarse: {prob.get(0, 0)*100:.2f}%")
print(f"Probabilidad de abandonar: {prob.get(1, 0)*100:.2f}%")

print(f"\n{'='*70}")
print("PREGUNTAS DE ANALISIS:")
print("="*70)
print("1. ¿Cual caracteristica es mas indicativa de abandono?")
print("2. ¿Que estrategias de retencion recomendarias para clientes en riesgo?")
print("3. ¿Cual es el costo de un falso negativo (predecir que se queda pero abandona)?")
print("4. ¿Como afecta el numero de llamadas a soporte en la decision del cliente?")