# ESCENARIO 1: Control de Calidad en Manufactura
# Dataset: Piezas de metal evaluadas por dimensiones y temperatura

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

# Dataset: Control de Calidad de Piezas
# Columnas: diametro(mm), longitud(mm), peso(g), temperatura(°C), calidad
datos = [
    [10.2, 50.1, 45.2, 180, 1], [10.1, 50.3, 45.5, 182, 1], [10.3, 50.0, 45.1, 181, 1],
    [9.8, 49.5, 44.0, 175, 0], [10.0, 50.2, 45.3, 183, 1], [9.9, 49.8, 44.8, 179, 1],
    [11.5, 51.2, 47.0, 190, 0], [10.2, 50.0, 45.0, 180, 1], [10.1, 50.1, 45.2, 181, 1],
    [9.7, 49.3, 43.5, 172, 0], [10.3, 50.2, 45.4, 182, 1], [10.0, 50.0, 45.0, 180, 1],
    [11.8, 51.5, 47.5, 195, 0], [10.2, 50.3, 45.6, 183, 1], [9.6, 49.0, 43.0, 170, 0],
    [10.1, 50.1, 45.1, 181, 1], [10.4, 50.4, 45.7, 184, 1], [9.5, 48.8, 42.5, 168, 0],
    [10.0, 50.0, 45.0, 180, 1], [10.3, 50.2, 45.3, 182, 1], [12.0, 52.0, 48.0, 200, 0],
    [10.2, 50.1, 45.2, 181, 1], [9.9, 49.9, 44.9, 179, 1], [10.1, 50.0, 45.1, 180, 1],
    [11.2, 51.0, 46.5, 188, 0], [10.0, 50.2, 45.2, 182, 1], [10.3, 50.1, 45.4, 181, 1],
    [9.8, 49.6, 44.2, 176, 0], [10.2, 50.0, 45.0, 180, 1], [10.1, 50.3, 45.5, 183, 1]
]

print("="*70)
print("ESCENARIO 1: CONTROL DE CALIDAD EN MANUFACTURA")
print("="*70)
print("\nDescripcion del problema:")
print("Una fabrica produce piezas metalicas y necesita clasificarlas como")
print("APTAS o DEFECTUOSAS segun sus dimensiones y temperatura de fundicion.")
print("\nCaracteristicas medidas:")
print("- Diametro (mm)")
print("- Longitud (mm)")
print("- Peso (gramos)")
print("- Temperatura de fundicion (°C)")

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
    pred = nb.predecir(X_test[i])
    real = y_test[i]
    matriz[real][pred] += 1

print(f"\n{'='*70}")
print("RESULTADOS DEL ANALISIS")
print("="*70)
print(f"\nDatos de entrenamiento: {len(X_train)}")
print(f"Datos de prueba: {len(X_test)}")
print(f"\nAccuracy: {accuracy:.2f}%")
print(f"Piezas correctamente clasificadas: {correctos}/{len(X_test)}")

print(f"\nMatriz de Confusion:")
print(f"                    Predicho DEFECTUOSA  Predicho APTA")
print(f"Real DEFECTUOSA            {matriz[0][0]:3}               {matriz[0][1]:3}")
print(f"Real APTA                  {matriz[1][0]:3}               {matriz[1][1]:3}")

# Prediccion de nueva pieza
nueva_pieza = [10.15, 50.05, 45.25, 181.5]
resultado = nb.predecir(nueva_pieza)
probabilidades = nb.predecir_proba(nueva_pieza)

print(f"\n{'='*70}")
print("PREDICCION DE NUEVA PIEZA")
print("="*70)
print(f"Diametro: {nueva_pieza[0]} mm")
print(f"Longitud: {nueva_pieza[1]} mm")
print(f"Peso: {nueva_pieza[2]} g")
print(f"Temperatura: {nueva_pieza[3]} °C")
print(f"\nClasificacion: {'APTA ✓' if resultado == 1 else 'DEFECTUOSA ✗'}")
print(f"Confianza DEFECTUOSA: {probabilidades.get(0, 0)*100:.2f}%")
print(f"Confianza APTA: {probabilidades.get(1, 0)*100:.2f}%")

print(f"\n{'='*70}")
print("PREGUNTAS DE ANALISIS PARA EL ALUMNO:")
print("="*70)
print("1. ¿Que tan confiable es el modelo con este accuracy?")
print("2. ¿Cual seria el costo de clasificar incorrectamente una pieza DEFECTUOSA como APTA?")
print("3. ¿Que caracteristica parece ser mas determinante para la clasificacion?")
print("4. ¿Como podrias mejorar el modelo con mas datos?")