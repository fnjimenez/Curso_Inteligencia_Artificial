# ESCENARIO 5: Prediccion de Rendimiento Academico
# Dataset: Estudiantes y su desempeño final

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

# Dataset: Rendimiento Academico
# Columnas: hrs_estudio_semanal, asistencia%, participacion%, tareas_entregadas%, aprobado
# Clase: 0=Reprobado, 1=Aprobado, 2=Excelente
datos = [
    [15, 95, 80, 100, 2], [8, 75, 50, 70, 1], [20, 98, 90, 100, 2],
    [5, 60, 30, 50, 0], [12, 85, 70, 85, 1], [18, 92, 85, 95, 2],
    [6, 65, 40, 55, 0], [10, 80, 60, 75, 1], [22, 100, 95, 100, 2],
    [4, 55, 25, 45, 0], [14, 88, 75, 90, 1], [19, 95, 88, 98, 2],
    [7, 70, 45, 60, 0], [11, 82, 65, 80, 1], [16, 90, 82, 92, 2],
    [5, 58, 28, 48, 0], [9, 78, 55, 72, 1], [21, 97, 92, 100, 2],
    [6, 62, 35, 52, 0], [13, 86, 72, 88, 1], [17, 93, 86, 96, 2],
    [4, 52, 22, 42, 0], [10, 79, 58, 74, 1], [20, 96, 90, 98, 2],
    [7, 68, 42, 58, 0], [12, 84, 68, 82, 1], [18, 94, 87, 94, 2],
    [5, 56, 26, 46, 0], [11, 81, 62, 78, 1], [19, 95, 89, 97, 2]
]

print("="*70)
print("ESCENARIO 5: PREDICCION DE RENDIMIENTO ACADEMICO")
print("="*70)
print("\nDescripcion del problema:")
print("Una universidad quiere identificar tempranamente estudiantes en riesgo")
print("de reprobar para ofrecerles apoyo academico oportuno.")
print("\nIndicadores academicos evaluados:")
print("- Horas de estudio semanal")
print("- Porcentaje de asistencia")
print("- Participacion en clase (%)")
print("- Tareas entregadas a tiempo (%)")
print("\nClasificacion de estudiantes:")
print("0 = REPROBADO (< 60)")
print("1 = APROBADO (60-89)")
print("2 = EXCELENTE (90-100)")

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

matriz = [[0, 0, 0], [0, 0, 0], [0, 0, 0]]
for i in range(len(X_test)):
    matriz[y_test[i]][nb.predecir(X_test[i])] += 1

print(f"\n{'='*70}")
print("RESULTADOS DEL ANALISIS")
print("="*70)
print(f"Accuracy: {accuracy:.2f}%")

print(f"\nMatriz de Confusion (3 clases):")
print(f"                  Pred:REP  Pred:APR  Pred:EXC")
print(f"Real:REPROBADO       {matriz[0][0]:3}       {matriz[0][1]:3}       {matriz[0][2]:3}")
print(f"Real:APROBADO        {matriz[1][0]:3}       {matriz[1][1]:3}       {matriz[1][2]:3}")
print(f"Real:EXCELENTE       {matriz[2][0]:3}       {matriz[2][1]:3}       {matriz[2][2]:3}")

# Evaluacion de estudiantes
estudiantes_prueba = [
    [7, 68, 45, 60, "Estudiante A - En riesgo"],
    [12, 85, 72, 88, "Estudiante B - Promedio"],
    [19, 96, 90, 98, "Estudiante C - Destacado"]
]

print(f"\n{'='*70}")
print("EVALUACION DE ESTUDIANTES")
print("="*70)

clases_nombres = {0: "REPROBADO ❌", 1: "APROBADO ✓", 2: "EXCELENTE ⭐"}

for est in estudiantes_prueba:
    datos_est = est[:4]
    nombre = est[4]
    resultado = nb.predecir(datos_est)
    prob = nb.predecir_proba(datos_est)
    
    print(f"\n{nombre}:")
    print(f"  Hrs estudio/semana: {datos_est[0]}")
    print(f"  Asistencia: {datos_est[1]}%")
    print(f"  Participacion: {datos_est[2]}%")
    print(f"  Tareas: {datos_est[3]}%")
    print(f"  Prediccion: {clases_nombres[resultado]}")
    print(f"  Probabilidades:")
    print(f"    Reprobar: {prob.get(0, 0)*100:.1f}%")
    print(f"    Aprobar: {prob.get(1, 0)*100:.1f}%")
    print(f"    Excelencia: {prob.get(2, 0)*100:.1f}%")
    
    if resultado == 0:
        print(f"  ⚠️ RECOMENDACION: Asignar tutor y plan de mejora urgente")
    elif resultado == 1:
        print(f"  💡 RECOMENDACION: Fomentar habitos de estudio")
    else:
        print(f"  🎓 RECOMENDACION: Considerar para programa de becas")

print(f"\n{'='*70}")
print("PREGUNTAS DE ANALISIS:")
print("="*70)
print("1. ¿Cual indicador es mas determinante para el exito academico?")
print("2. ¿Que estrategias de intervencion propondrias para cada categoria?")
print("3. ¿Como afecta la deteccion temprana al rendimiento final?")
print("4. ¿Es etico usar IA para predecir el rendimiento estudiantil?")
print("5. ¿Que limitaciones tiene este modelo de clasificacion?")