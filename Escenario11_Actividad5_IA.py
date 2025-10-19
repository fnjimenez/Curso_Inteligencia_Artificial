# ESCENARIO 11: MSA - Analisis del Sistema de Medicion e Incertidumbre
# Dataset: Mediciones repetidas con diferentes operadores e instrumentos

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

# Dataset: MSA - Sistema de Medicion
# Columnas: desv_std_mediciones, rango_mediciones, experiencia_operador_meses, calibracion_dias, sistema_confiable
datos = [
    [0.015, 0.04, 48, 15, 1], [0.082, 0.25, 6, 120, 0], [0.018, 0.05, 36, 20, 1],
    [0.095, 0.30, 4, 150, 0], [0.012, 0.03, 60, 10, 1], [0.075, 0.22, 8, 110, 0],
    [0.020, 0.06, 42, 18, 1], [0.088, 0.27, 5, 135, 0], [0.016, 0.04, 52, 12, 1],
    [0.092, 0.29, 3, 145, 0], [0.014, 0.04, 55, 14, 1], [0.078, 0.23, 7, 115, 0],
    [0.019, 0.05, 40, 22, 1], [0.085, 0.26, 6, 125, 0], [0.013, 0.03, 58, 11, 1],
    [0.080, 0.24, 9, 105, 0], [0.017, 0.05, 45, 16, 1], [0.090, 0.28, 4, 140, 0],
    [0.015, 0.04, 50, 13, 1], [0.083, 0.25, 8, 118, 0], [0.021, 0.06, 38, 24, 1],
    [0.087, 0.27, 5, 130, 0], [0.016, 0.04, 46, 17, 1], [0.079, 0.23, 10, 108, 0],
    [0.014, 0.03, 54, 15, 1], [0.093, 0.29, 3, 148, 0], [0.018, 0.05, 44, 19, 1],
    [0.081, 0.24, 7, 122, 0], [0.015, 0.04, 49, 14, 1], [0.089, 0.28, 6, 138, 0]
]

print("="*70)
print("ESCENARIO 11: MSA - ANALISIS DEL SISTEMA DE MEDICION")
print("="*70)
print("\nDescripcion del problema:")
print("Un laboratorio de metrologia necesita evaluar la confiabilidad de sus")
print("sistemas de medicion segun estudios MSA (Measurement System Analysis).")
print("\nIndicadores de desempeño del sistema:")
print("- Desviacion estandar de mediciones repetidas (mm)")
print("- Rango de mediciones (mm) - Reproducibilidad")
print("- Experiencia del operador (meses)")
print("- Dias desde ultima calibracion del instrumento")
print("\nCriterios de aceptacion MSA:")
print("- %GRR < 10% = Sistema Aceptable")
print("- %GRR 10-30% = Sistema Marginal (requiere atencion)")
print("- %GRR > 30% = Sistema No Aceptable")

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
print("RESULTADOS DEL ANALISIS MSA")
print("="*70)
print(f"Accuracy del clasificador: {accuracy:.2f}%")

print(f"\nMatriz de Confusion:")
print(f"                    Pred: NO CONF  Pred: CONFIABLE")
print(f"Real: NO CONFIABLE        {matriz[0][0]:3}           {matriz[0][1]:3}")
print(f"Real: CONFIABLE           {matriz[1][0]:3}           {matriz[1][1]:3}")

# Evaluacion de sistemas de medicion
sistemas_evaluados = [
    [0.016, 0.045, 50, 14, "Micrometro Digital - Linea A"],
    [0.088, 0.27, 5, 135, "Calibrador Vernier - Linea B"],
    [0.045, 0.12, 24, 55, "CMM Tridimensional - Laboratorio"],
    [0.022, 0.07, 36, 28, "Comparador Optico - Inspeccion Final"]
]

print(f"\n{'='*70}")
print("EVALUACION DE SISTEMAS DE MEDICION")
print("="*70)

for sistema in sistemas_evaluados:
    datos_sist = sistema[:4]
    descripcion = sistema[4]
    resultado = nb.predecir(datos_sist)
    prob = nb.predecir_proba(datos_sist)
    
    # Calculos MSA aproximados
    tolerancia_proceso = 0.50  # mm - especificacion del proceso
    grr_estimado = (datos_sist[0] * 5.15 / tolerancia_proceso) * 100  # %GRR simplificado
    
    print(f"\n{descripcion}:")
    print(f"  Desv. Std. repetibilidad: {datos_sist[0]:.3f} mm")
    print(f"  Rango reproducibilidad: {datos_sist[1]:.3f} mm")
    print(f"  Experiencia operador: {datos_sist[2]} meses")
    print(f"  Dias desde calibracion: {datos_sist[3]}")
    print(f"  %GRR estimado: {grr_estimado:.1f}%")
    print(f"  Clasificacion: {'❌ SISTEMA NO CONFIABLE' if resultado == 0 else '✓ SISTEMA CONFIABLE'}")
    print(f"  Probabilidad NO CONFIABLE: {prob.get(0, 0)*100:.1f}%")
    print(f"  Probabilidad CONFIABLE: {prob.get(1, 0)*100:.1f}%")
    
    print(f"\n  📊 DIAGNOSTICO MSA:")
    if grr_estimado < 10:
        print(f"     ✓ Excelente - Sistema aceptable para produccion")
    elif grr_estimado < 30:
        print(f"     ⚠️ Marginal - Requiere mejoras")
    else:
        print(f"     ❌ Inaceptable - No usar en produccion")
    
    if resultado == 0:
        print(f"\n  🔧 ACCIONES CORRECTIVAS REQUERIDAS:")
        if datos_sist[0] > 0.05:
            print(f"     - REPETIBILIDAD ALTA: Revisar tecnica de medicion")
            print(f"       * Capacitar operador en uso correcto del instrumento")
            print(f"       * Verificar desgaste de puntas de medicion")
            print(f"       * Evaluar condiciones ambientales (temp, vibracion)")
        if datos_sist[1] > 0.15:
            print(f"     - REPRODUCIBILIDAD ALTA: Variacion entre operadores")
            print(f"       * Estandarizar procedimiento de medicion")
            print(f"       * Entrenar operadores menos experimentados")
            print(f"       * Implementar fixtures o dispositivos de sujecion")
        if datos_sist[2] < 12:
            print(f"     - OPERADOR NOVATO: Capacitacion intensiva requerida")
            print(f"       * Programa de certificacion en metrologia")
            print(f"       * Supervision durante periodo de prueba")
        if datos_sist[3] > 90:
            print(f"     - CALIBRACION VENCIDA: Recalibrar INMEDIATAMENTE")
            print(f"       * Enviar a laboratorio acreditado (ISO 17025)")
            print(f"       * Verificar todas las mediciones desde ultima cal")
            print(f"       * Implementar sistema de control de calibracion")
        
        print(f"\n  ⛔ RESTRICCIONES:")
        print(f"     - NO utilizar para liberacion de producto")
        print(f"     - Solo para mediciones preliminares")
        print(f"     - Verificar con sistema confiable")
    else:
        print(f"\n  ✓ SISTEMA APROBADO PARA:")
        print(f"     - Liberacion de lotes de produccion")
        print(f"     - Validacion de procesos")
        print(f"     - Auditorias de calidad")
        
        print(f"\n  📅 MANTENIMIENTO PREVENTIVO:")
        if datos_sist[3] > 15:
            print(f"     - Programar calibracion en {30 - datos_sist[3]} dias")
        print(f"     - Verificacion diaria con patron de referencia")
        print(f"     - Limpieza y cuidado segun manual del fabricante")

print(f"\n{'='*70}")
print("ANALISIS DE INCERTIDUMBRE DE MEDICION")
print("="*70)

# Calculo de incertidumbre combinada
print(f"\nComponentes de incertidumbre evaluados:")
print(f"1. Incertidumbre del instrumento (de certificado)")
print(f"2. Repetibilidad (desviacion estandar)")
print(f"3. Reproducibilidad (variacion entre operadores)")
print(f"4. Resolucion del instrumento")
print(f"5. Deriva por temperatura")
print(f"6. Deriva por descalibracion")

ejemplo_incertidumbre = [
    ["Micrometro Digital", 0.002, 0.016, 0.045, 0.001, 0.003, 0.008],
    ["Calibrador Vernier", 0.020, 0.088, 0.270, 0.020, 0.015, 0.040]
]

print(f"\n{'='*70}")
for inst in ejemplo_incertidumbre:
    nombre = inst[0]
    u_cert = inst[1]
    u_rep = inst[2]
    u_repro = inst[3]
    u_res = inst[4]
    u_temp = inst[5]
    u_deriv = inst[6]
    
    # Incertidumbre combinada (raiz de suma de cuadrados)
    u_combinada = math.sqrt(u_cert**2 + u_rep**2 + u_repro**2 + 
                            u_res**2 + u_temp**2 + u_deriv**2)
    
    # Incertidumbre expandida (k=2, 95% confianza)
    u_expandida = u_combinada * 2
    
    print(f"\n{nombre}:")
    print(f"  Certificado calibracion: ±{u_cert:.3f} mm")
    print(f"  Repetibilidad: ±{u_rep:.3f} mm")
    print(f"  Reproducibilidad: ±{u_repro:.3f} mm")
    print(f"  Resolucion: ±{u_res:.3f} mm")
    print(f"  Temperatura: ±{u_temp:.3f} mm")
    print(f"  Deriva: ±{u_deriv:.3f} mm")
    print(f"  ---")
    print(f"  Incertidumbre combinada: ±{u_combinada:.3f} mm")
    print(f"  Incertidumbre expandida (k=2): ±{u_expandida:.3f} mm")
    print(f"  Expresion del resultado: X ± {u_expandida:.3f} mm (95% confianza)")

print(f"\n{'='*70}")
print("PREGUNTAS DE ANALISIS:")
print("="*70)
print("1. ¿Como afecta la experiencia del operador a la repetibilidad?")
print("2. ¿Cual componente de incertidumbre es dominante y por que?")
print("3. ¿Como se relaciona %GRR con la capacidad del proceso (Cp/Cpk)?")
print("4. ¿Que criterios usarias para aceptar/rechazar un sistema de medicion?")
print("5. ¿Como implementar ANOVA para estudios GR&R mas robustos?")
print("6. ¿Que dice la norma ISO/IEC 17025 sobre incertidumbre de medicion?")
print("7. ¿Como documentar la trazabilidad metrologica de las mediciones?")
print("8. ¿Que impacto tiene la incertidumbre en decisiones de conformidad?")

print(f"\n{'='*70}")
print("CONCLUSIONES DEL ESTUDIO MSA")
print("="*70)
print(f"\nSistemas evaluados: {len(sistemas_evaluados)}")
print(f"Sistemas confiables identificados: {sum(1 for s in sistemas_evaluados if nb.predecir(s[:4]) == 1)}")
print(f"Sistemas que requieren accion correctiva: {sum(1 for s in sistemas_evaluados if nb.predecir(s[:4]) == 0)}")
print(f"\nRecomendacion general:")
print(f"Implementar programa de MSA continuo con estudios GR&R trimestrales")
print(f"para mantener la confiabilidad de los sistemas de medicion.")