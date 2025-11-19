
(continuación)

**Al pedir ayuda, proporciona:**

-   Screenshot del error
-   Descripción de qué intentabas hacer
-   Qué pasos seguiste
-   En qué sección del documento estás

**Esto ayudará a que te respondan más rápido y mejor.**

----------

## 31. CRITERIOS DE ÉXITO - ¿CÓMO SÉ QUE MI MODELO ESTÁ BIEN?

### ✅ Tu modelo es exitoso si:

#### **Funcionalidad básica:**

-   [x] Se ejecuta sin errores de compilación
-   [x] Los camiones aparecen, fluyen y desaparecen
-   [x] Los números del dashboard aumentan
-   [x] Puede correr durante 24 horas simuladas sin problemas

#### **Lógica operativa:**

-   [x] Los camiones del Norte van a Recepción Norte
-   [x] Los camiones del Sur van a Recepción Sur
-   [x] Aproximadamente 65% toma ruta de cross-docking
-   [x] Los destinos se distribuyen: 55% GM Silao, 33% GM SLP, 12% BMW

#### **KPIs razonables (después de 24h simuladas):**

-   [x] Pallets procesados: 5,000 - 9,000
-   [x] Camiones procesados: 180 - 350
-   [x] Tiempo promedio de ciclo: 2.0 - 5.0 horas
-   [x] Utilización de andenes: 50% - 90%

#### **Presentación profesional:**

-   [x] Layout claro y organizado
-   [x] Dashboard visible y legible
-   [x] Pantalla inicial con descripción
-   [x] Nombres de bloques descriptivos (no "delay1", "delay2")

#### **Documentación completa:**

-   [x] Reporte con todas las secciones
-   [x] Capturas de pantalla de calidad
-   [x] Análisis de resultados fundamentado
-   [x] Conclusión reflexiva y personal

----------

### ⚠️ Tu modelo necesita mejoras si:

-   [ ] Da errores al ejecutar
-   [ ] Los camiones se "atoran" en algún punto
-   [ ] Los KPIs no cambian o están fuera de rango
-   [ ] Las colas crecen indefinidamente
-   [ ] La utilización de recursos es 0% o 100% constante
-   [ ] No hay lógica de cross-docking
-   [ ] No se distinguen rutas Norte/Sur
-   [ ] El dashboard no se actualiza
-   [ ] Faltan capturas o documentación

----------

## 32. VERSIONES DEL MODELO - PROGRESIÓN RECOMENDADA

### 📈 Evoluciona tu modelo en etapas

#### **VERSIÓN 1.0 - Modelo Mínimo Viable (MVP)**

_Tiempo: 2-3 horas_

**Incluye:**

-   1 Source simple (puede ser solo Lear)
-   Enter → Queue → Seize → Delay → Release → Sink
-   ResourcePool docks
-   1 variable: palletsProcessed

**Objetivo:** Entender el flujo básico

----------

#### **VERSIÓN 2.0 - Múltiples Proveedores**

_Tiempo: +1 hora_

**Agrega:**

-   3 Sources (Lear, Condumex, Magna)
-   Atributos en agente Truck
-   Código On exit en Sources

**Objetivo:** Modelar la entrada realista

----------

#### **VERSIÓN 3.0 - Ruteo por Región**

_Tiempo: +1 hora_

**Agrega:**

-   SelectOutput ROUTE_RECEPCION
-   2 Delays de recepción
-   SORTING_PROCESS

**Objetivo:** Diferenciar flujos Norte/Sur

----------

#### **VERSIÓN 4.0 - Cross-docking y Buffer**

_Tiempo: +1 hora_

**Agrega:**

-   SelectOutput FLOW_DECISION
-   BUFFER_TIME
-   KITTING_PROCESS

**Objetivo:** Modelar las 3 rutas operativas

----------

#### **VERSIÓN 5.0 - Destinos OEM**

_Tiempo: +0.5 hora_

**Agrega:**

-   SelectOutput DESTINO_OEM
-   3 PREPARE blocks
-   Código para asignar destinoOEM

**Objetivo:** Completar el flujo hasta salida

----------

#### **VERSIÓN 6.0 - KPIs Completos**

_Tiempo: +0.5 hora_

**Agrega:**

-   4 variables globales
-   Código en EXIT_CEDIS
-   Dashboard básico

**Objetivo:** Medir desempeño

----------

#### **VERSIÓN 7.0 - Presentación Profesional**

_Tiempo: +1 hora_

**Agrega:**

-   Layout dibujado
-   Imagen de fondo
-   Pantalla inicial
-   Dashboard mejorado
-   Gráfica TimePlot

**Objetivo:** Modelo listo para entregar

----------

#### **VERSIÓN 8.0+ - Extras (Opcional)**

_Tiempo: variable_

**Ideas para mejorar:**

-   Recursos adicionales (montacargas, operadores)
-   Animación 3D de camiones
-   Estadísticas avanzadas (histogramas, box plots)
-   Múltiples escenarios (experimentación)
-   Turnos de trabajo diferenciados
-   Fallas aleatorias de equipos
-   Costos operativos

----------

## 33. EXPERIMENTACIÓN - MÁS ALLÁ DE LA ACTIVIDAD BÁSICA

### 🧪 Si quieres destacar, prueba escenarios alternativos

#### **Escenario 1: Incremento de demanda**

**Situación:**

> "BMW duplica su producción y ahora requiere 2,400 pallets/día en lugar de 1,200"

**Qué modificar:**

```java
// En DESTINO_OEM:
if (r < 0.45) {  // Reducir GM Silao de 55% a 45%
    agent.destinoOEM = "GM_SILAO";
    return 0;
} else if (r < 0.78) {  // GM SLP igual (33%)
    agent.destinoOEM = "GM_SLP";
    return 1;
} else {  // BMW aumenta de 12% a 22%
    agent.destinoOEM = "BMW_SLP";
    return 2;
}

```

**Analiza:**

-   ¿Aumenta la utilización de andenes?
-   ¿Se necesitan más recursos?
-   ¿Cuál es el nuevo tiempo de ciclo?

----------

#### **Escenario 2: Reducción de andenes (optimización)**

**Situación:**

> "Queremos reducir costos. ¿Podemos operar con 18 andenes en lugar de 24?"

**Qué modificar:**

```java
// En ResourcePool docks:
capacity = 18;

```

**Analiza:**

-   ¿Aumentan las colas?
-   ¿El tiempo de ciclo se incrementa significativamente?
-   ¿Vale la pena el ahorro?

----------

#### **Escenario 3: Proceso de sorting más rápido**

**Situación:**

> "Invertimos en un sistema automático de sorting"

**Qué modificar:**

```java
// En SORTING_PROCESS:
triangular(0.1, 0.2, 0.4)  // Más rápido que antes

```

**Analiza:**

-   ¿Mejora el throughput?
-   ¿Se reduce el cuello de botella?
-   ¿Cuál es el ROI estimado?

----------

#### **Escenario 4: Cierre temporal de proveedor**

**Situación:**

> "Condumex cierra por mantenimiento durante 1 semana"

**Qué modificar:**

```java
// En SRC_CONDUMEX_SUR, cambiar rate a:
0  // Sin llegadas

```

**Analiza:**

-   ¿Cómo afecta al throughput total?
-   ¿Los otros proveedores compensan?
-   ¿Se reduce la utilización de recursos?

----------

#### **Escenario 5: Aumento de cross-docking**

**Situación:**

> "Mejoramos la coordinación y ahora 80% puede ser cross-docking"

**Qué modificar:**

```java
// En FLOW_DECISION:
if (r < 0.80) {  // Aumentar de 65% a 80%
    return 0;  // Cross-docking
} else if (r < 0.95) {  // Reducir buffer de 30% a 15%
    return 1;
} else {
    return 2;
}

```

**Analiza:**

-   ¿Mejora el tiempo de ciclo?
-   ¿Se reduce el inventario?
-   ¿Es viable en la práctica?

----------

### 📊 Tabla comparativa de escenarios

Crea una tabla así en tu reporte:

Escenario

Pallets/día

Tiempo ciclo

Util. andenes

Viabilidad

Base (actual)

7,100

3.2h

72%

✓

+100% BMW

8,300

3.8h

85%

⚠️ Cerca del límite

-6 andenes

7,100

4.5h

95%

✗ Cuellos de botella

Sorting rápido

7,100

2.8h

68%

✓ Recomendado

-Condumex

5,800

2.9h

58%

✓ Sistema robusto

+Cross-dock

7,100

2.6h

70%

✓ Óptimo

> 💡 **Esto demuestra pensamiento analítico avanzado**

----------

## 34. CONEXIÓN CON EL MUNDO REAL

### 🌍 Aplicaciones de este modelo en la industria

#### **1. Toma de decisiones de inversión**

**Caso real:**  
Una empresa automotriz debe decidir si construir un CEDIS regional de $50 millones USD.

**Cómo ayuda tu modelo:**

-   Valida la capacidad teórica
-   Identifica necesidades de recursos
-   Estima tiempos de respuesta
-   Calcula throughput realista
-   Justifica la inversión con datos

**ROI estimado:**  
Si tu modelo demuestra que un CEDIS puede reducir costos logísticos en $8 millones/año, el payback period sería de ~6 años.

----------

#### **2. Optimización de operaciones existentes**

**Caso real:**  
Un CEDIS real tiene problemas de congestión en horas pico.

**Cómo ayuda tu modelo:**

-   Identifica cuellos de botella
-   Prueba soluciones sin riesgo (¿más andenes? ¿más turnos?)
-   Compara escenarios antes de invertir
-   Capacita al personal con visualización

----------

#### **3. Negociación con proveedores**

**Caso real:**  
Necesitas renegociar horarios de entrega con Lear.

**Cómo ayuda tu modelo:**

-   Demuestra capacidad de recepción
-   Justifica ventanas horarias óptimas
-   Muestra impacto de entregas concentradas
-   Respalda propuestas con datos

----------

#### **4. Planeación de contingencias**

**Caso real:**  
¿Qué pasa si BMW cierra por 2 semanas?

**Cómo ayuda tu modelo:**

-   Simula diferentes escenarios de contingencia
-   Calcula recursos redundantes necesarios
-   Planea reasignación de personal
-   Estima impacto financiero

----------

### 💼 Empresas que usan AnyLogic (ejemplos reales)

-   **Mercedes-Benz:** Simulación de líneas de ensamble
-   **BMW:** Optimización de logística inbound
-   **DHL:** Diseño de centros de distribución
-   **Amazon:** Simulación de fulfillment centers
-   **Volkswagen:** Análisis de flujos de materiales

> 💡 **Tu proyecto es equivalente a lo que hacen consultores con años de experiencia**

----------

## 35. PREGUNTAS FRECUENTES (FAQ)

### ❓ Dudas técnicas

**P: ¿Puedo usar un solo Source para los 3 proveedores?**  
R: Técnicamente sí, pero perderías la distinción de proveedores. Es mejor usar 3 Sources separados como en las instrucciones.

**P: ¿Qué pasa si mi tiempo de ciclo es muy alto (>6 horas)?**  
R: Reduce los tiempos de Delay o aumenta la capacidad de recursos. Un CEDIS eficiente debe tener ciclos de 2-4 horas.

**P: ¿Tengo que usar exactamente los tiempos triangular sugeridos?**  
R: No, puedes ajustarlos, pero justifica tus cambios en el reporte. Los valores sugeridos están basados en datos de la Actividad 6.

**P: ¿Puedo usar distribución normal en lugar de triangular?**  
R: Sí, pero triangular es más intuitiva para modelar tiempos con valor mínimo, más probable y máximo.

**P: ¿Es obligatorio el dashboard?**  
R: Sí, es parte de la evaluación. Demuestra que puedes monitorear el modelo.

----------

### ❓ Dudas de contenido

**P: ¿Tengo que explicar cada bloque del flowchart en el reporte?**  
R: No todos, pero sí los bloques clave (SelectOutput, Exit, ResourcePools). Enfócate en las decisiones de diseño.

**P: ¿Cuántas capturas son suficientes?**  
R: Mínimo las 6 obligatorias. Más es mejor si agregan valor (no pongas 20 capturas iguales).

**P: ¿Puedo trabajar en equipo?**  
R: Consulta con tu profesor. Generalmente estas actividades son individuales, pero pueden permitir discusión entre compañeros.

**P: ¿Qué hago si mis KPIs son muy diferentes a los esperados?**  
R: Explica por qué en tu reporte. Puede ser válido si justificas los parámetros que usaste.

----------

### ❓ Dudas de entrega

**P: ¿Tengo que entregar el archivo .alp?**  
R: Solo si tu profesor lo pide. El enlace a AnyLogic Cloud suele ser suficiente.

**P: ¿Puedo entregar el reporte en Word en lugar de PDF?**  
R: Normalmente se pide PDF porque preserva el formato. Consulta con tu profesor.

**P: ¿Qué pasa si el enlace de Cloud deja de funcionar?**  
R: Guarda el archivo .alp como respaldo. Puedes re-exportar si es necesario.

**P: ¿Puedo entregar después de la fecha límite?**  
R: Depende de la política del curso. Normalmente hay penalización por retraso.

----------

### ❓ Dudas de evaluación

**P: ¿Cuánto vale cada parte?**  
R: Revisa la rúbrica en la Sección 23. El modelo funcional vale 40%, documentación 25%, KPIs 15%, etc.

**P: ¿Dan puntos extra por creatividad?**  
R: Sí, hasta 18 puntos extra por recursos adicionales, gráficas, experimentación y layout profesional.

**P: ¿Es mejor un modelo complejo o uno simple que funcione bien?**  
R: Uno simple que funcione PERFECTAMENTE es mejor que uno complejo con errores.

**P: ¿Penalizan errores de ortografía?**  
R: Algunos puntos sí. Revisa tu documento antes de entregar.

----------

## 36. PLAN DE EMERGENCIA - "Solo tengo 6 horas antes de entregar"

### 🚨 Modo supervivencia (mínimo indispensable)

Si estás muy cerca de la fecha límite, prioriza así:

#### **Hora 1-2: Modelo básico funcional**

1.  Crear proyecto y configurar unidades (10 min)
2.  Crear agente Truck con atributos (10 min)
3.  Crear 3 Sources con código On exit (20 min)
4.  Flowchart básico: Enter → Queue → Seize → Delay → Release → Sink (30 min)
5.  ResourcePool docks (5 min)
6.  **EJECUTAR** y verificar que funciona (15 min)

#### **Hora 3: Lógica de ruteo**

1.  Agregar ROUTE_RECEPCION con código (15 min)
2.  Delays de recepción Norte/Sur (10 min)
3.  SORTING_PROCESS (5 min)
4.  DESTINO_OEM con código (20 min)
5.  PREPARE blocks (10 min)

#### **Hora 4: KPIs y dashboard**

1.  Crear 4 variables globales (10 min)
2.  Código en EXIT_CEDIS (15 min)
3.  Dashboard con 4 indicadores (25 min)
4.  **EJECUTAR** 24h y anotar resultados (10 min)

#### **Hora 5: Publicación**

1.  Exportar a AnyLogic Cloud (10 min)
2.  Probar en navegador (5 min)
3.  Tomar 6 capturas de pantalla (20 min)
4.  Crear carpeta organizada con evidencias (15 min)

#### **Hora 6: Reporte express**

1.  Portada (5 min)
2.  Introducción básica (10 min)
3.  Descripción del modelo (15 min)
4.  Insertar capturas (10 min)
5.  Tabla de KPIs (5 min)
6.  Conclusión breve (10 min)
7.  Revisión rápida y PDF (5 min)

> ⚠️ **Este plan da aproximadamente 60-70 puntos de 100**  
> Es mejor que no entregar, pero no es lo ideal.

----------

## 37. LISTA DE ARCHIVOS FINALES A ENTREGAR

### 📁 Estructura recomendada

```
Actividad9_CEDIS_TuApellido_TuMatricula/
│
├── 📄 Act9_CEDIS_Apellido_Matricula.pdf  ← PRINCIPAL
│
├── 📁 Capturas/
│   ├── 01_Layout_CEDIS.png
│   ├── 02_Flowchart_Completo.png
│   ├── 03_Configuracion_FlowDecision.png
│   ├── 04_ResourcePool_Docks.png
│   ├── 05_Modelo_Ejecutando.png
│   └── 06_Dashboard_KPIs.png
│
├── 📄 Enlace_AnyLogicCloud.txt  ← Contiene solo la URL
│
└── 📁 Modelo/ (opcional, solo si se solicita)
    └── CEDIS_SanBartolo_Apellido_Matricula.alp

```

### 📌 Contenido de Enlace_AnyLogicCloud.txt

```
ACTIVIDAD 9 - CEDIS AUTOMOTRIZ SAN BARTOLO
Estudiante: [Tu Nombre Completo]
Matrícula: [Tu Matrícula]

Enlace al modelo en AnyLogic Cloud:
https://cloud.anylogic.com/model/XXXXXXXX

Instrucciones para ejecutar:
1. Abrir el enlace en cualquier navegador
2. Click en el botón verde "Run model"
3. Esperar 10-15 segundos a que cargue
4. Observar el dashboard en la esquina superior derecha
5. Los KPIs se actualizan en tiempo real

Notas:
- El modelo simula 24 horas por defecto
- Puede pausarse con el botón de pausa
- Los valores finales aparecen al terminar la simulación

```

----------

## 38. AUTOEVALUACIÓN FINAL - ¿ESTOY LISTO PARA ENTREGAR?

### ✅ Usa esta checklist el día de la entrega

#### **REVISIÓN TÉCNICA (30 min antes de entregar)**

```
□ Abro el modelo en AnyLogic → se abre sin errores
□ Click en Run → el modelo se ejecuta
□ Dejo correr 1 minuto → los camiones fluyen
□ Reviso dashboard → los números aumentan
□ Dejo correr hasta 24h → el modelo no se detiene
□ Verifico KPIs finales → están en rangos razonables
□ Cierro y vuelvo a abrir → sigue funcionando

```

#### **REVISIÓN DE DOCUMENTACIÓN (20 min antes)**

```
□ Abro el PDF → se ve correctamente formateado
□ Reviso portada → todos los datos están completos
□ Verifico imágenes → todas son legibles (no pixeladas)
□ Leo la introducción → tiene sentido y sin errores
□ Reviso tabla de KPIs → números coinciden con el modelo
□ Verifico enlace → lo copio y pego en navegador
□ Pruebo el enlace en modo incógnito → funciona
□ Reviso conclusión → tiene mínimo 10 líneas
□ Busco errores ortográficos → corrijo los que encuentro
□ Verifico nombre del archivo → formato correcto

```

#### **REVISIÓN DE ENTREGA (10 min antes)**

```
□ El archivo PDF pesa menos de 10 MB
□ Las capturas están insertadas (no enlaces rotos)
□ El documento tiene 6-8 páginas (sin contar portada)
□ Revisé que no falta ninguna sección
□ El enlace de Cloud está visible y funcional
□ Guardé una copia de seguridad en otro lugar
□ Tengo el archivo .alp guardado por si acaso
□ Verifiqué la plataforma de entrega (funciona)
□ Revisé la hora límite de entrega
□ Estoy listo para subir el archivo

```

### 🎯 Si marcaste TODOS los checks → ¡ADELANTE, ENTREGA!

### ⚠️ Si falta alguno → Corrígelo antes de entregar

----------

## 39. DESPUÉS DE ENTREGAR - REFLEXIÓN Y APRENDIZAJE

### 📝 Ejercicio post-entrega (opcional pero recomendado)

Después de entregar, tómate 10 minutos para responder:

**1. ¿Qué haría diferente si tuviera que hacerlo de nuevo?**

```
_________________________________________________________________
_________________________________________________________________

```

**2. ¿Qué parte fue más fácil de lo que esperaba?**

```
_________________________________________________________________
_________________________________________________________________

```

**3. ¿Qué parte fue más difícil?**

```
_________________________________________________________________
_________________________________________________________________

```

**4. ¿Qué habilidad nueva adquirí?**

```
_________________________________________________________________
_________________________________________________________________

```

**5. ¿Cómo aplicaré esto en mi carrera?**

```
_________________________________________________________________
_________________________________________________________________

```

> 💡 **Esta reflexión te ayudará en futuros proyectos**

----------

## 40. MENSAJE DE CIERRE Y AGRADECIMIENTO

### 🎓 ¡Felicitaciones por completar la Actividad 9!

Has construido un modelo de simulación profesional de un centro de distribución automotriz. Esto no es poca cosa.

### 📊 Lo que lograste:

✅ Modelaste un sistema logístico complejo  
✅ Programaste decisiones de ruteo  
✅ Gestionaste recursos limitados  
✅ Calculaste KPIs operativos  
✅ Visualizaste datos en dashboards  
✅ Publicaste tu trabajo en la nube  
✅ Documentaste todo profesionalmente

### 💪 Habilidades desarrolladas:

-   **Técnicas:** AnyLogic, Java básico, simulación de eventos discretos
-   **Analíticas:** Interpretación de KPIs, identificación de cuellos de botella
-   **Comunicación:** Documentación técnica, presentación de resultados
-   **Profesionales:** Gestión de proyectos, cumplimiento de deadlines

### 🚀 Próximos pasos sugeridos:

1.  **Guarda este proyecto** en tu portafolio profesional
2.  **Actualiza tu LinkedIn** con la habilidad "AnyLogic Simulation"
3.  **Comparte tu experiencia** con compañeros de generaciones siguientes
4.  **Explora más tutoriales** de AnyLogic si te gustó
5.  **Considera certificaciones** en simulación o logística

### 🌟 Recuerda:

> "La logística no se trata de mover cajas, se trata de mover el mundo de manera eficiente."

Has dado un paso importante en tu formación como profesional de la logística y la ingeniería.

----------

### 📧 Feedback para mejorar este documento

Si este documento te ayudó (o si encuentras áreas de mejora), considera compartir tu experiencia con tu profesor o compañeros.

**Sugerencias de mejora siempre son bienvenidas:**

-   ¿Qué sección fue más útil?
-   ¿Qué faltó explicar?
-   ¿Qué ejemplo adicional ayudaría?
-   ¿Qué parte fue confusa?

----------

## 🏁 FIN DEL DOCUMENTO

**Total de palabras:** ~20,000  
**Total de secciones:** 40  
**Tiempo de lectura completo:** ~90 minutos  
**Tiempo de implementación:** 6-8 horas para estudiantes nuevos

----------

### 📚 Índice rápido de navegación

Sección

Tema

Página

1-6

Introducción y contexto

Inicio

7-8

Configuración inicial y layout

Parte 1

9-10

Agentes y fuentes

Parte 2

11-12

Entrada y andenes

Parte 3

13-15

Cross-docking y destinos

Parte 4

16-18

Dashboard y visualización

Parte 6

19-20

Ejecución y publicación

Parte 7

21-24

Documentación y entrega

Parte 8

25-40

Ayuda, FAQ y recursos

Extras

----------

**Versión del documento:** 2.0 Mejorada para Principiantes  
**Última actualización:** Noviembre 2025  
**Autor:** Material educativo para Logística y Cadena de Valor

**Licencia de uso:** Este documento es material educativo para uso académico. Se permite su distribución y modificación para fines educativos con atribución apropiada.

----------

# ✨ ¡ÉXITO EN TU PROYECTO! ✨

----------

> Written with [StackEdit](https://stackedit.io/).
<!--stackedit_data:
eyJoaXN0b3J5IjpbMTg3MjkzMzk0XX0=
-->