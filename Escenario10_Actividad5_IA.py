# ESCENARIO 10: Control de Calidad - Inspeccion de Productos
# Dataset: Inspecciones y defectos en linea de produccion

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

# Dataset: Control de Calidad
# Columnas: espesor_mm, dureza_shore, acabado_rugosidad, peso_g, calidad
# Clase: 0=Defectuoso, 1=Aceptable, 2=Premium
datos = [
    [5.02, 85, 0.8, 102.5, 1], [4.85, 78, 1.5, 98.2, 0], [5.00, 88, 0.5, 103.0, 2],
    [4.80, 75, 1.8, 97.5, 0], [5.01, 86, 0.7, 102.8, 1], [5.03, 90, 0.4, 103.5, 2],
    [4.78, 73, 2.0, 96.8, 0], [4.99, 84, 0.9, 102.2, 1], [5.02, 89, 0.6, 103.2, 2],
    [4.82, 76, 1.6, 98.0, 0], [5.00, 85, 0.8, 102.6, 1], [5.01, 91, 0.3, 103.8, 2],
    [4.79, 74, 1.9, 97.2, 0], [5.02, 86, 0.7, 102.7, 1], [5.03, 92, 0.5, 104.0, 2],
    [4.81, 77, 1.7, 98.5, 0], [4.98, 83, 1.0, 102.0, 1], [5.00, 90, 0.4, 103.6, 2],
    [4.83, 75, 1.8, 97.8, 0], [5.01, 87, 0.6, 102.9, 1], [5.02, 91, 0.5, 103.7, 2],
    [4.77, 72, 2.1, 96.5, 0], [4.99, 84, 0.9, 102.3, 1], [5.03, 89, 0.6, 103.4, 2],
    [4.80, 76, 1.6, 98.1, 0], [5.00, 86, 0.8, 102.5, 1], [5.01, 90, 0.4, 103.5, 2],
    [4.84, 77, 1.5, 98.3, 0], [4.98, 85, 0.7, 102.4, 1], [5.02, 92, 0.3, 103.9, 2]
]

print("="*70)
print("ESCENARIO 10: CONTROL DE CALIDAD - CLASIFICACION DE PRODUCTOS")
print("="*70)
print("\nDescripcion del problema:")
print("Una planta manufacturera necesita clasificar productos automaticamente")
print("en categorias de calidad para optimizar su comercializacion.")
print("\nParametros de calidad medidos:")
print("- Espesor de la pieza (mm) - Especificacion: 5.00 ± 0.05 mm")
print("- Dureza Shore A")
print("- Rugosidad superficial (Ra μm)")
print("- Peso (gramos)")
print("\nCategorias de calidad:")
print("0 = DEFECTUOSO (rechazo)")
print("1 = ACEPTABLE (venta estandar)")
print("2 = PREMIUM (venta premium)")

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
print("RESULTADOS DEL CONTROL DE CALIDAD")
print("="*70)
print(f"Accuracy: {accuracy:.2f}%")

print(f"\nMatriz de Confusion (3 categorias):")
print(f"                  Pred:DEF  Pred:ACEP  Pred:PREM")
print(f"Real:DEFECTUOSO      {matriz[0][0]:3}       {matriz[0][1]:3}        {matriz[0][2]:3}")
print(f"Real:ACEPTABLE       {matriz[1][0]:3}       {matriz[1][1]:3}        {matriz[1][2]:3}")
print(f"Real:PREMIUM         {matriz[2][0]:3}       {matriz[2][1]:3}        {matriz[2][2]:3}")

# Analisis financiero por categoria
precio_defectuoso = 0        # Reproceso/descarte
precio_aceptable = 50        # USD
precio_premium = 85          # USD

ingresos_reales = (matriz[0][0] * precio_defectuoso + matriz[1][1] * precio_aceptable + 
                   matriz[2][2] * precio_premium)
ingresos_potenciales = (sum(matriz[0]) * precio_defectuoso + sum(matriz[1]) * precio_aceptable + 
                        sum(matriz[2]) * precio_premium)

print(f"\nAnalisis de Valor por Clasificacion:")
print(f"Productos correctamente clasificados: ${ingresos_reales:,}")
print(f"Perdida por clasificacion incorrecta: ${ingresos_potenciales - ingresos_reales:,}")

# Lote de inspeccion
lote_produccion = [
    [4.82, 76, 1.6, 98.2, "Pieza #1247"],
    [5.01, 87, 0.7, 102.8, "Pieza #1248"],
    [5.02, 91, 0.4, 103.6, "Pieza #1249"],
    [4.92, 82, 1.1, 100.5, "Pieza #1250"]
]

print(f"\n{'='*70}")
print("INSPECCION DE LOTE DE PRODUCCION")
print("="*70)

categorias = {0: "DEFECTUOSO ❌", 1: "ACEPTABLE ✓", 2: "PREMIUM ⭐"}

for pieza in lote_produccion:
    datos_pieza = pieza[:4]
    id_pieza = pieza[4]
    resultado = nb.predecir(datos_pieza)
    prob = nb.predecir_proba(datos_pieza)
    
    print(f"\n{id_pieza}:")
    print(f"  Espesor: {datos_pieza[0]} mm (Spec: 5.00±0.05)")
    print(f"  Dureza Shore: {datos_pieza[1]}")
    print(f"  Rugosidad: {datos_pieza[2]} μm")
    print(f"  Peso: {datos_pieza[3]} g")
    print(f"  Clasificacion: {categorias[resultado]}")
    print(f"  Probabilidades:")
    print(f"    Defectuoso: {prob.get(0, 0)*100:.1f}%")
    print(f"    Aceptable: {prob.get(1, 0)*100:.1f}%")
    print(f"    Premium: {prob.get(2, 0)*100:.1f}%")
    
    if resultado == 0:
        print(f"  🔧 ACCION: Enviar a reproceso o descarte")
        if abs(datos_pieza[0] - 5.00) > 0.05:
            print(f"     - Espesor fuera de especificacion")
        if datos_pieza[2] > 1.5:
            print(f"     - Acabado superficial deficiente")
    elif resultado == 1:
        print(f"  📦 ACCION: Empacar para venta estandar - Precio: ${precio_aceptable}")
    else:
        print(f"  🌟 ACCION: Empacar para linea premium - Precio: ${precio_premium}")

print(f"\n{'='*70}")
print("PREGUNTAS DE ANALISIS:")
print("="*70)
print("1. ¿Cual parametro es mas critico para distinguir Premium de Aceptable?")
print("2. ¿Como afecta la clasificacion incorrecta al margen de utilidad?")
print("3. ¿Que mejoras de proceso reducirian productos defectuosos?")
print("4. ¿Vale la pena invertir en sensores automaticos vs inspeccion manual?")
print("5. ¿Como implementar Six Sigma con estos datos de calidad?")