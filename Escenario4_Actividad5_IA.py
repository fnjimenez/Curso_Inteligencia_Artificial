# ESCENARIO 4: Deteccion de Fraude en Transacciones
# Dataset: Transacciones bancarias

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

# Dataset: Deteccion de Fraude
# Columnas: monto, hora, distancia_ubicacion_usual, intentos_previos, fraude
datos = [
    [45.50, 14, 2.5, 0, 0], [1250.00, 3, 850, 1, 1], [89.99, 18, 5.2, 0, 0],
    [2500.00, 2, 1200, 2, 1], [32.75, 12, 1.8, 0, 0], [150.00, 20, 8.5, 0, 0],
    [3200.00, 4, 2500, 3, 1], [67.80, 16, 3.2, 0, 0], [1800.00, 1, 950, 1, 1],
    [25.50, 10, 0.5, 0, 0], [950.00, 5, 650, 1, 1], [110.25, 19, 12.0, 0, 0],
    [2100.00, 3, 1500, 2, 1], [78.90, 15, 4.5, 0, 0], [1500.00, 2, 800, 1, 1],
    [55.00, 13, 6.8, 0, 0], [2800.00, 4, 1800, 2, 1], [42.30, 11, 2.1, 0, 0],
    [1100.00, 1, 700, 1, 1], [95.75, 17, 9.2, 0, 0], [3500.00, 5, 3000, 3, 1],
    [38.60, 14, 1.5, 0, 0], [1650.00, 2, 900, 1, 1], [72.40, 16, 5.8, 0, 0],
    [2200.00, 3, 1300, 2, 1], [48.90, 12, 3.5, 0, 0], [1350.00, 4, 750, 1, 1],
    [81.20, 18, 7.5, 0, 0], [2900.00, 1, 2100, 2, 1], [59.50, 15, 4.2, 0, 0]
]

print("="*70)
print("ESCENARIO 4: DETECCION DE FRAUDE EN TRANSACCIONES BANCARIAS")
print("="*70)
print("\nDescripcion del problema:")
print("Un banco necesita identificar transacciones potencialmente fraudulentas")
print("en tiempo real para proteger a sus clientes.")
print("\nIndicadores de riesgo analizados:")
print("- Monto de la transaccion (USD)")
print("- Hora del dia (0-23)")
print("- Distancia de ubicacion usual (km)")
print("- Intentos previos fallidos")

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
print(f"                    Pred: LEGITIMA  Pred: FRAUDE")
print(f"Real: LEGITIMA            {matriz[0][0]:3}           {matriz[0][1]:3}")
print(f"Real: FRAUDE              {matriz[1][0]:3}           {matriz[1][1]:3}")

# Analisis de transaccion sospechosa
nueva_transaccion = [1800.00, 3, 1100, 1]
resultado = nb.predecir(nueva_transaccion)
prob = nb.predecir_proba(nueva_transaccion)

print(f"\n{'='*70}")
print("ANALISIS DE TRANSACCION EN TIEMPO REAL")
print("="*70)
print(f"Monto: ${nueva_transaccion[0]}")
print(f"Hora: {nueva_transaccion[1]:02d}:00 hrs")
print(f"Distancia de ubicacion usual: {nueva_transaccion[2]} km")
print(f"Intentos previos fallidos: {nueva_transaccion[3]}")
print(f"\nVeredicto: {'🚨 FRAUDE DETECTADO' if resultado == 1 else '✓ TRANSACCION LEGITIMA'}")
print(f"Probabilidad LEGITIMA: {prob.get(0, 0)*100:.2f}%")
print(f"Probabilidad FRAUDE: {prob.get(1, 0)*100:.2f}%")

if resultado == 1:
    print("\n⚠️ ACCION RECOMENDADA: Bloquear transaccion y contactar al cliente")
else:
    print("\n✓ ACCION: Aprobar transaccion")

print(f"\n{'='*70}")
print("PREGUNTAS DE ANALISIS:")
print("="*70)
print("1. ¿Cual es el costo de un falso positivo (bloquear transaccion legitima)?")
print("2. ¿Cual es el costo de un falso negativo (aprobar fraude)?")
print("3. ¿Que patron caracteriza mejor las transacciones fraudulentas?")
print("4. ¿Como balancear seguridad vs. experiencia del cliente?")