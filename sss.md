Perfecto, profesor.  
A continuación presento la **VERSIÓN COMPLETA, DEFINITIVA Y PROFESIONAL** de la:

# 🟦 **ACTIVIDAD 9 – PLANTILLA + MANUAL DEL ESTUDIANTE**

## **“MODELADO Y SIMULACIÓN DE PROCESOS LOGÍSTICOS DEL CEDIS SAN BARTOLO EN ANYLOGIC”**

Formato: **Markdown** (compatible con Stakedit, GitHub, Notion, Colab, etc.)

----------

# 🧩 **ACTIVIDAD 9 – PLANTILLA + MANUAL DE PROCEDIMIENTO**

## **Curso:** Logística y Cadena de Valor

## **Unidad:** Modelación y Optimización de Flujos Logísticos

## **Software:** AnyLogic University Edition

## **Duración estimada:** 2–3 horas

## **Nivel:** Intermedio

----------

# 📘 **I. OBJETIVO DE LA ACTIVIDAD**

El estudiante será capaz de:

-   **Construir en AnyLogic** la primera versión funcional del **CEDIS Automotriz San Bartolo**, basado en los resultados de la Actividad 6 y los análisis de la Actividad 7.
    
-   Comprender la **lógica de flujo operacional**, desde la llegada de proveedores hasta la salida de embarques hacia OEMs.
    
-   Implementar:  
    ✔ bloques de la Process Modeling Library  
    ✔ recursos como montacargas y andenes  
    ✔ rutas de flujo  
    ✔ tiempo de proceso mediante Delay  
    ✔ lógica Java para ruteo
    
-   Probar el modelo y validar su funcionamiento con flujos reales simplificados.
    

----------

# 🧩 **II. RESULTADOS ESPERADOS**

Al finalizar esta actividad, el estudiante generará:

-   Un **modelo AnyLogic** operativo del mini-CEDIS.
    
-   Representación funcional de:  
    ✔ Recepción Norte  
    ✔ Recepción Sur  
    ✔ Sorting  
    ✔ Buffer Estratégico  
    ✔ Áreas de Kitting  
    ✔ Embarques (GM Silao, GM SLP, BMW SLP)
    
-   Rutas internas de montacargas.
    
-   Animación y flujos de camiones en movimiento.
    
-   Lógica completa para seleccionar destino OEM.
    

----------

# 🏭 **III. LAYOUT OFICIAL DEL CEDIS (Versión Nivel Estudiante)**

Usar como referencia este plano:

```
CEDIS AUTOMOTRIZ SAN BARTOLO
┌──────────────────────────────────────────────────────────────────────────────┐
│ RECEPCIÓN NORTE                SORTING                  GM SILAO            │
│                                      ┌───────────────┐  ┌───────────────┐   │
│ ┌───────────────┐                    │               │  │               │   │
│ │               │                    │               │  │               │   │
│ └───────────────┘                    └───────────────┘  └───────────────┘   │
│                                                                              │
│ RECEPCIÓN SUR           BUFFER ESTRATÉGICO         GM SLP / BMW              │
│ ┌───────────────┐     ┌────────────────────────┐    ┌───────────────┐       │
│ │               │     │                        │    │               │       │
│ └───────────────┘     └────────────────────────┘    └───────────────┘       │
└──────────────────────────────────────────────────────────────────────────────┘

```

El estudiante **NO debe ser completamente preciso en metros**, solo representar las áreas según la proporción general.

----------

# 🧰 **IV. ELEMENTOS QUE EL ESTUDIANTE DEBE CREAR EN ANYLOGIC**

Dentro del agente `Main`:

### 🔹 1. **Zonas (Rectangles o Shapes)**

-   Recepción Norte
    
-   Recepción Sur
    
-   Sorting
    
-   Buffer
    
-   Kitting
    
-   Embarques GM Silao
    
-   Embarques GM SLP
    
-   Embarques BMW SLP
    

----------

### 🔹 2. **Flujos de camiones (Process Modeling Library)**

**Bloques requeridos:**

Etapa

Bloque

Descripción

Entrada de proveedores

`Source`

Genera camiones

Un solo punto de acceso

`Enter`

Entrada al sistema

Espera de andén

`Queue`

Fila para esperar descarga

Asignación de andén

`Seize`

Ocupa recurso andén

Movimiento al área

`MoveTo`

Camión se desplaza

Tiempo de descarga

`Delay`

Simula descarga

Liberación de andén

`Release`

Desocupa recurso

Ruteo

`SelectOutput`

Desvía según región

Proceso Sorting

`Delay`

Clasificación

Proceso Buffer

`Delay`

Espera estratégica

Salida del sistema

`Sink`

Finaliza el flujo

----------

### 🔹 3. **Recursos (ResourcePool)**

Recurso

Cantidad sugerida

Uso

Montacargas (`forklifts`)

8

Movimientos internos

Andenes (`docks`)

12

Descarga/carga

Operadores (`workers`)

10

Apoyo general (opcional)

----------

# 👣 **V. PASO A PASO (MANUAL DEL ESTUDIANTE)**

A continuación se describe **cada paso**, su **objetivo**, y **configuraciones específicas**.

----------

# 🔷 **PASO 1 – Crear el agente `Truck`**

### 📌 Objetivo:

Representar cada camión proveedor con atributos propios.

### ✔ Atributos Java:

Abrir _Agent → Properties → Add Parameter_:

```java
string proveedor;
string region;
string destinoOEM;
int pallets;
double tInicioDescarga;
double tFinDescarga;

```

----------

# 🔷 **PASO 2 – Crear las zonas internas del CEDIS**

### 📌 Objetivo:

Definir visualmente las áreas operativas.

Usar: **Palette → Presentation → Rectangle**

Crear al menos:

-   `zonaRecepcionNorte`
    
-   `zonaRecepcionSur`
    
-   `zonaSorting`
    
-   `zonaBuffer`
    
-   `zonaKitting`
    
-   `zonaGMSilao`
    
-   `zonaGMSLP`
    
-   `zonaBMW`
    

_(Estas zonas solo son visuales; no afectan la lógica.)_

----------

# 🔷 **PASO 3 – Crear 3 fuentes de camiones (`Source`)**

### 📌 Objetivo:

Generar camiones representando proveedores reales.

Crear tres bloques:

1.  `SRC_LEAR_NORTE`
    
2.  `SRC_CONDUMEX_SUR`
    
3.  `SRC_MAGNA_SUR`
    

### ✔ Código _On exit_:

**Norte:**

```java
agent.proveedor = "LEAR";
agent.region = "NORTE";
agent.pallets = 26;

```

**Sur:**

```java
agent.proveedor = "CONDUMEX";
agent.region = "SUR";
agent.pallets = 24;

```

**Sur 2:**

```java
agent.proveedor = "MAGNA";
agent.region = "SUR";
agent.pallets = 28;

```

----------

# 🔷 **PASO 4 – Crear el proceso de descarga**

### 📌 Objetivo:

Simular espera, asignación de andén, descarga y liberación.

### **Bloques necesarios y configuraciones**

#### 1. `Queue Q_ANDEN`

-   Prioridad normal
    

#### 2. `Seize SEIZE_ANDEN`

-   ResourcePool: **docks**
    

#### 3. `MoveTo MOVE_TO_DOCK`

-   Mover a `zonaRecepcionNorte` o `zonaRecepcionSur`  
    _(luego se mejora con lógica)_
    

#### 4. `Delay UNLOAD`

-   Tiempo:
    

```text
triangular(0.3, 0.6, 1.0) // horas

```

#### 5. `Release RELEASE_ANDEN`

-   ResourcePool: **docks**
    

----------

# 🔷 **PASO 5 – Ruteo a SORTING según región**

### 📌 Objetivo:

Enviar camiones a la zona de sorting correcta según su región.

### **SelectOutput – `ROUTE_TO_SORTING`**

**Código Java:**

```java
if (agent.region.equals("NORTE")) return 0;
else return 1;

```

----------

# 🔷 **PASO 6 – Sorting y Buffer**

### ✔ Sorting

```text
Delay SORTING_PROCESS = triangular(0.2, 0.4, 0.8) horas

```

### ✔ Buffer

```text
Delay BUFFER_TIME = triangular(1, 3, 6) horas

```

----------

# 🔷 **PASO 7 – Selección de destino OEM**

### 📌 Objetivo:

Asignar salida según probabilidad real.

**SelectOutput – `DESTINO_OEM`**  
Código:

```java
double r = uniform(0,1);

if (r < 0.55) {
    agent.destinoOEM = "GM_SILAO";
    return 0;
} else if (r < 0.55 + 0.33) {
    agent.destinoOEM = "GM_SLP";
    return 1;
} else {
    agent.destinoOEM = "BMW_SLP";
    return 2;
}

```

----------

# 🔷 **PASO 8 – Procesos finales y Sink**

### 📌 Objetivo:

Simular preparación final y salida del sistema.

Tres bloques Delay:

-   `PREPARE_GM_SILAO`
    
-   `PREPARE_GM_SLP`
    
-   `PREPARE_BMW`
    

Tiempo sugerido:

```text
triangular(0.2, 0.5, 1.0)

```

Finalizan en:

-   `SINK_SILAO`
    
-   `SINK_GMSLP`
    
-   `SINK_BMW`
    

----------

# 📊 **VI. VARIABLES Y MÉTRICAS (KPI)**

Crear en `Main`:

```java
int palletsProcesados = 0;
double forkUtilization = 0;
double dockUtilization = 0;

```

### En cada salida OEM:

```java
palletsProcesados += agent.pallets;

```

----------

# 🏁 **VII. CRITERIOS DE EVALUACIÓN (RÚBRICA SUGERIDA)**

Rubro

Puntos

Zonas correctamente representadas

15

Flujo completo funcional

25

Lógica de ruteo correcta

20

Sorting + Buffer correctamente implementado

15

Recursos funcionando (docks, montacargas)

15

Conclusión técnica del estudiante

10

----------

# 🧾 **VIII. CONCLUSIÓN (A RELLENAR POR EL ALUMNO)**

_(El estudiante debe redactar aquí su conclusión personal.)_

----------

# 📝 **IX. ENTREGA**

-   Modelo exportado a **AnyLogic Cloud**
    
-   Link pegado aquí:
    

```
https://cloud.anylogic.com/_______

```

-   PDF del reporte del estudiante
    
-   Evidencia de ejecución (capturas)
    

----------

# ✔ Profesor, el MD completo ya está listo para copiar y usar en Stakedit.

¿Desea ahora la **versión PDF / DOCX / plantilla SABES institucional**?
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTM5MTc5ODU1Myw2NjEwOTI5NjBdfQ==
-->