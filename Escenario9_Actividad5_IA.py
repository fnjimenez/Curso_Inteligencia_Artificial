# ESCENARIO 9: Mantenimiento Predictivo - Fallas en Maquinaria
# Dataset: Equipos industriales y prediccion de fallas

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

# Dataset: Mantenimiento Predictivo
# Columnas: vibracion_mm/s, temperatura_motor_C, horas_operacion, dias_desde_mant, falla
datos = [
    [2.5, 65, 1200, 15, 0], [8.5, 95, 5800, 180, 1], [3.2, 68, 1500, 20, 0],
    [9.2, 98, 6200, 210, 1], [2.8, 66, 1300, 18, 0], [7.8, 92, 5200, 150, 1],
    [3.0, 67, 1400, 22, 0], [8.8, 96, 5900, 195, 1], [2.6, 65, 1250, 16, 0],
    [9.5, 100, 6500, 220, 1], [2.9, 68, 1350, 19, 0], [8.2, 94, 5500, 165, 1],
    [3.1, 69, 1450, 21, 0], [9.0, 97, 6100, 200, 1], [2.7, 66, 1280, 17, 0],
    [7.5, 90, 5000, 140, 1], [3.3, 70, 1550, 23, 0], [8.6, 95, 5700, 185, 1],
    [2.8, 67, 1320, 20, 0], [9.3, 99, 6300, 215, 1], [3.0, 68, 1400, 22, 0],
    [8.0, 93, 5400, 160, 1], [2.6, 65, 1260, 15, 0], [8.9, 96, 6000, 190, 1],
    [3.2, 69, 1480, 24, 0], [7.6, 91, 5100, 145, 1], [2.9, 67, 1380, 21, 0],
    [9.1, 98, 6200, 205, 1], [3.1, 68, 1420, 22, 0], [8.4, 94, 5600, 175, 1]
]

print("="*70)
print("ESCENARIO 9: MANTENIMIENTO PREDICTIVO - DETECCION DE FALLAS")
print("="*70)
print("\nDescripcion del problema:")
print("Una planta industrial necesita predecir fallas en maquinaria critica")
print("para implementar mantenimiento preventivo y evitar paros no programados.")
print("\nParametros de condicion monitoreados:")
print("- Nivel de vibracion (mm/s)")
print("- Temperatura del motor (°C)")
print("- Horas de operacion acumuladas")
print("- Dias desde ultimo mantenimiento")

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

# Costos de mantenimiento
costo_preventivo = 2500      # USD - mantenimiento programado
costo_correctivo = 15000     # USD - reparacion por falla
costo_paro_produccion = 8000 # USD por hora de paro

print(f"\n{'='*70}")
print("RESULTADOS DEL ANALISIS PREDICTIVO")
print("="*70)
print(f"Accuracy: {accuracy:.2f}%")

print(f"\nMatriz de Confusion:")
print(f"                    Pred: NORMAL  Pred: FALLA")
print(f"Real: NORMAL              {matriz[0][0]:3}         {matriz[0][1]:3}")
print(f"Real: FALLA               {matriz[1][0]:3}         {matriz[1][1]:3}")

# Analisis financiero
fallas_no_detectadas = matriz[1][0]
alertas_falsas = matriz[0][1]

costo_fallas_perdidas = fallas_no_detectadas * (costo_correctivo + costo_paro_produccion * 4)
costo_alertas_falsas = alertas_falsas * costo_preventivo
ahorro_fallas_detectadas = matriz[1][1] * (costo_correctivo - costo_preventivo)

print(f"\nAnalisis Financiero de Mantenimiento:")
print(f"Costo por fallas NO detectadas: ${costo_fallas_perdidas:,}")
print(f"Costo por alertas falsas: ${costo_alertas_falsas:,}")
print(f"Ahorro por fallas detectadas: ${ahorro_fallas_detectadas:,}")
print(f"Beneficio neto del modelo: ${ahorro_fallas_detectadas - costo_alertas_falsas - costo_fallas_perdidas:,}")

# Monitoreo de equipos
equipos_monitoreados = [
    [3.0, 68, 1400, 22, "Maquina CNC-01 - Condicion Normal"],
    [8.7, 96, 5850, 188, "Prensa Hidraulica-05 - CRITICO"],
    [5.2, 82, 3500, 90, "Torno Industrial-03 - Atencion Requerida"]
]

print(f"\n{'='*70}")
print("MONITOREO DE EQUIPOS EN TIEMPO REAL")
print("="*70)

for equipo in equipos_monitoreados:
    datos_equipo = equipo[:4]
    descripcion = equipo[4]
    resultado = nb.predecir(datos_equipo)
    prob = nb.predecir_proba(datos_equipo)
    
    print(f"\n{descripcion}:")
    print(f"  Vibracion: {datos_equipo[0]} mm/s")
    print(f"  Temperatura motor: {datos_equipo[1]}°C")
    print(f"  Horas operacion: {datos_equipo[2]:,} hrs")
    print(f"  Dias desde mantenimiento: {datos_equipo[3]}")
    print(f"  Estado: {'🚨 RIESGO DE FALLA INMINENTE' if resultado == 1 else '✓ OPERACION NORMAL'}")
    print(f"  Confianza NORMAL: {prob.get(0, 0)*100:.1f}%")
    print(f"  Confianza FALLA: {prob.get(1, 0)*100:.1f}%")
    
    if resultado == 1:
        print(f"  ⚠️ PLAN DE ACCION INMEDIATO:")
        print(f"     1. Programar mantenimiento preventivo URGENTE")
        print(f"     2. Reducir carga de trabajo al 70%")
        print(f"     3. Incrementar frecuencia de monitoreo")
        print(f"     4. Preparar equipo de respaldo")
        print(f"     5. Ordenar refacciones criticas")
        
        if datos_equipo[0] > 8.0:
            print(f"     - Vibracion CRITICA: Revisar rodamientos y balanceo")
        if datos_equipo[1] > 95:
            print(f"     - Temperatura ALTA: Verificar sistema de enfriamiento")
        if datos_equipo[2] > 5000:
            print(f"     - Horas CRITICAS: Considerar overhaul completo")
        if datos_equipo[3] > 150:
            print(f"     - Mantenimiento VENCIDO: Intervencion inmediata")

print(f"\n{'='*70}")
print("PREGUNTAS DE ANALISIS:")
print("="*70)
print("1. ¿Cual parametro es el mejor indicador de falla inminente?")
print("2. ¿Como justificar la inversion en sensores IoT con este ROI?")
print("3. ¿Que estrategia de mantenimiento es optima: preventivo vs predictivo?")
print("4. ¿Como integrar este modelo con un sistema CMMS/EAM?")
print("5. ¿Que impacto tiene en el OEE (Overall Equipment Effectiveness)?")    