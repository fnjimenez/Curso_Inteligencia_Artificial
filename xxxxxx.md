# 🟦 ACTIVIDAD 9 – MODELADO DEL CEDIS AUTOMOTRIZ SAN BARTOLO EN ANYLOGIC

### Curso: Logística y Cadena de Valor

### Unidad: Sistemas de Almacenamiento y CEDIS

### Versión: 2025 - EDICIÓN MEJORADA PARA PRINCIPIANTES

----------

## 📌 ANTES DE EMPEZAR - LEE ESTO PRIMERO

### ¿Qué voy a hacer en esta actividad?

Vas a construir un **modelo de simulación** del CEDIS (Centro de Distribución) San Bartolo en el software AnyLogic. Este CEDIS distribuye piezas automotrices a tres plantas ensambladoras: GM Silao, GM San Luis Potosí y BMW San Luis Potosí.

### ¿Qué es un modelo de simulación?

Es como un **videojuego de tu CEDIS** donde puedes ver cómo entran camiones, se descargan, los materiales circulan por el almacén y salen hacia los clientes. Te permite probar diferentes configuraciones sin construir el almacén real.

### ¿Qué necesito saber antes?

✅ **No necesitas ser experto en programación**  
✅ Este documento te guía paso a paso  
✅ Cada sección tiene: 🎯 Objetivo | 🧠 Explicación | 🛠️ Qué hacer | 💡 Consejos  
✅ Si te atoras, busca las secciones **"⚠️ PROBLEMAS COMUNES"**

### Tiempo estimado

-   **Primera vez:** 4-6 horas
-   **Con experiencia:** 2-3 horas

----------

## 1. DATOS DE IDENTIFICACIÓN

Campo

Información a completar

Nombre del estudiante

Matrícula

Carrera

Grupo

Fecha de entrega

Nombre del CEDIS modelado

CEDIS AUTOMOTRIZ SAN BARTOLO

----------

## 2. CONTEXTO Y VÍNCULO CON ACTIVIDADES ANTERIORES

### 🔗 ¿De dónde viene este proyecto?

Esta Actividad 9 **completa el trabajo** que hiciste en:

-   **Actividad 6:** Diseñaste el CEDIS San Bartolo en papel (capacidad, áreas, flujos)
-   **Actividad 7:** Analizaste qué industrias podrían ubicarse en la región
-   **Actividad 8 (opcional):** Usaste métodos cuantitativos para decisiones logísticas

Ahora vas a **dar vida a ese diseño** en una simulación digital.

### 🎯 ¿Qué voy a simular?

1.  **Entrada:** Camiones de 3 proveedores (Lear, Condumex, Magna)
2.  **Procesos internos:** Descarga → Clasificación → Almacenamiento → Preparación
3.  **Salida:** Despacho hacia GM Silao, GM SLP y BMW SLP

### 📊 Datos clave del CEDIS (Actividad 6)

-   **Capacidad:** 22,000 pallets
-   **Entrada diaria:** ~7,100 pallets
-   **Salida diaria:** ~7,700 pallets
-   **Andenes:** 24 (8 recepción + 16 embarque)
-   **Cross-docking:** 65% de los materiales pasan directo sin almacenarse

----------

## 3. OBJETIVO GENERAL

> **Construir y documentar un modelo funcional del CEDIS en AnyLogic** que simule camiones entrando, procesos de descarga, clasificación, almacenamiento y despacho hacia tres clientes automotrices, con recursos, tiempos y KPIs medibles.

----------

## 4. OBJETIVOS ESPECÍFICOS

Al terminar esta actividad, podrás:

1.  ✅ Configurar un proyecto AnyLogic con unidades correctas
2.  ✅ Crear agentes (camiones) con información de carga y destino
3.  ✅ Dibujar el layout básico del CEDIS
4.  ✅ Construir un diagrama de flujo (flowchart) con bloques Process Modeling
5.  ✅ Gestionar recursos (andenes, montacargas)
6.  ✅ Programar decisiones de ruteo (hacia dónde va cada camión)
7.  ✅ Calcular indicadores (KPIs) como pallets procesados y tiempos
8.  ✅ Publicar el modelo en AnyLogic Cloud
9.  ✅ Crear un dashboard de monitoreo

----------

## 5. REQUISITOS PREVIOS

### Software

-   **AnyLogic** instalado (versión PLE o superior)  
    👉 Descarga gratuita: [www.anylogic.com](https://www.anylogic.com/downloads/)

### Conocimientos

-   Haber completado Actividades 6 y 7
-   Haber visto el video introductorio de AnyLogic (proporcionado por el profesor)

### Materiales

-   Layout del CEDIS San Bartolo (imagen PNG/JPG proporcionada)
-   Este documento MD como guía

----------

## 6. ¿CÓMO USAR ESTE DOCUMENTO?

### 📖 Estructura de cada paso

Cada sección sigue este formato:

```
🎯 OBJETIVO → Qué vas a lograr
🧠 LÓGICA → Por qué lo haces así
🛠️ CONFIGURACIÓN → Instrucciones técnicas paso a paso
💻 CÓDIGO (si aplica) → Qué escribir en AnyLogic
💡 CONSEJOS → Trucos útiles
⚠️ PROBLEMAS COMUNES → Qué hacer si algo falla

```

### ✅ Checklist de avance

Al final de cada sección marca:

-   [ ] Completado y funciona
-   [ ] Completado pero tengo dudas
-   [ ] No pude completarlo

----------

# PARTE 1: CONFIGURACIÓN INICIAL

----------

## 7. PASO 1 – CREAR EL PROYECTO Y CONFIGURAR UNIDADES

### 🎯 Objetivo

Crear un proyecto nuevo en AnyLogic con las unidades correctas (horas y metros).

### 🧠 Lógica

Trabajaremos en un solo agente llamado `Main` que contendrá todo:

-   El dibujo del CEDIS (layout)
-   El diagrama de flujo de camiones
-   Los recursos (andenes, montacargas)
-   Los indicadores de desempeño

### 🛠️ Configuración

#### **Paso 1.1: Crear el proyecto**

1.  Abrir AnyLogic
2.  Menú **File → New Model...**
3.  Asignar nombre: `CEDIS_SanBartolo_TuApellido`
    -   Ejemplo: `CEDIS_SanBartolo_Garcia`
4.  Click en **Finish**

> 💡 **AnyLogic creará automáticamente un agente llamado `Main`**

#### **Paso 1.2: Configurar unidades de tiempo**

1.  En el panel izquierdo **Projects**, hacer click en el **nombre del modelo** (arriba de Main)
2.  En la parte inferior, buscar la pestaña **Properties**
3.  Expandir la sección **Environment**
4.  Configurar:
    -   **Time units:** seleccionar `hour` (hora)
    -   **Length units:** seleccionar `meter` (metro)

![Screenshot esperado: Properties → Environment → hour/meter]

> 💡 **¿Por qué horas y metros?**
> 
> -   **Horas:** Los procesos logísticos se miden en horas (descarga = 0.5 horas)
> -   **Metros:** El CEDIS mide ~250m × 100m

#### **Paso 1.3: Verificar que Main está activo**

1.  En el panel izquierdo, hacer doble click en **Main**
2.  Debe abrirse una ventana blanca de trabajo (canvas)
3.  En la parte superior debe decir: **Main (Agent Type)**

### ⚠️ PROBLEMAS COMUNES

Problema

Solución

No encuentro "Environment"

Click en el nombre del PROYECTO (no en Main)

No aparece Main

Menú Projects → click derecho → New Agent Type → Nombre: Main

Las unidades no se guardan

Cerrar y reabrir el proyecto

### ✅ Checklist

-   [ ] Proyecto creado con nombre correcto
-   [ ] Unidades configuradas en horas y metros
-   [ ] Main está abierto y listo para trabajar

----------

## 8. PASO 2 – DIBUJAR EL LAYOUT DEL CEDIS

### 🎯 Objetivo

Crear la representación visual del CEDIS usando el layout proporcionado como referencia.

### 🧠 Lógica

Vamos a dibujar:

-   La nave principal del CEDIS
-   Las zonas funcionales (Recepción, Sorting, Buffer, Kitting, Embarques)
-   Opcionalmente, insertar la imagen del layout como fondo

### 🛠️ Configuración

#### **Paso 2.1: Insertar la imagen de fondo (RECOMENDADO)**

1.  Guardar la imagen del layout en tu computadora
2.  En AnyLogic, con Main abierto, ir a menú **Insert → Image...**
3.  Buscar la imagen y hacer click en **Open**
4.  Click en el canvas para colocarla
5.  Ajustar tamaño arrastrando las esquinas

**Para que no se mueva:** 6. Click derecho sobre la imagen → **Order → Send to Back**  
7. Click derecho → **Lock**

> 💡 **Ahora la imagen queda de fondo y puedes dibujar encima**

#### **Paso 2.2: Dibujar las zonas principales**

En la paleta izquierda, buscar **Presentation → Space Markup**:

1.  Arrastrar **Rectangular Node** al canvas
    
2.  Dibujar rectángulos sobre las zonas de la imagen:
    
    -   Recepción Norte (amarillo claro)
    -   Recepción Sur (amarillo claro)
    -   Sorting (azul claro)
    -   Buffer Estratégico (amarillo)
    -   Kitting (azul claro)
    -   Embarques GM Silao (azul claro)
    -   Embarques GM SLP (azul claro)
    -   Embarques BMW SLP (azul claro)
3.  Para cambiar colores:
    
    -   Click en el rectángulo
    -   En Properties → **Fill color** → elegir color

#### **Paso 2.3: Agregar etiquetas de texto**

1.  En la paleta, buscar **Presentation → Text**
    
2.  Arrastrar al canvas
    
3.  Escribir el nombre de cada zona:
    
    -   "RECEPCIÓN NORTE"
    -   "RECEPCIÓN SUR"
    -   "SORTING"
    -   Etc.
4.  Cambiar tamaño de fuente:
    
    -   Click en el texto → Properties → **Font** → Size: 14-16

### 💡 CONSEJOS

-   No necesitas ser perfecto, solo que se distinga cada zona
-   Usa colores similares al layout proporcionado
-   Si no quieres usar la imagen de fondo, está bien, solo dibuja rectángulos grandes

### 🎨 Resultado esperado

```
┌─────────────────────────────────────────────────────────────┐
│         CEDIS AUTOMOTRIZ SAN BARTOLO                        │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  [Recep. Norte] [Recep. Sur]     [Sorting]    [Buffer]     │
│                                                             │
│                   [Kitting]                                 │
│                                                             │
│  [Embarques GM Silao] [GM SLP] [BMW SLP]                   │
│                                                             │
└─────────────────────────────────────────────────────────────┘

```

### ⚠️ PROBLEMAS COMUNES

Problema

Solución

La imagen tapa todo

Click derecho → Order → Send to Back

No puedo mover la imagen

Click derecho → Unlock

Los rectángulos no se ven

Cambiar Fill color y agregar borde (Line color)

### ✅ Checklist

-   [ ] Imagen de fondo insertada y bloqueada
-   [ ] 8 zonas dibujadas con rectángulos
-   [ ] Etiquetas de texto agregadas
-   [ ] Layout se ve claro y organizado

----------

# PARTE 2: CREACIÓN DE AGENTES Y FUENTES

----------

## 9. PASO 3 – CREAR EL AGENTE `Truck`

### 🎯 Objetivo

Definir la "ficha técnica" de los camiones que entrarán al CEDIS.

### 🧠 Lógica

Cada camión necesita saber:

-   ¿De qué proveedor viene? (Lear, Condumex, Magna)
-   ¿De qué región? (Norte o Sur)
-   ¿Cuántos pallets trae?
-   ¿A qué cliente irá? (GM Silao, GM SLP, BMW SLP)
-   ¿Cuándo entró y salió? (para calcular tiempos)

### 🛠️ Configuración

#### **Paso 3.1: Crear el agente Truck**

1.  En el panel **Projects**, click derecho en **Agent Types**
2.  Seleccionar **New Agent Type...**
3.  Nombre: `Truck`
4.  Click en **Finish**

#### **Paso 3.2: Agregar atributos (variables)**

1.  Con el agente `Truck` abierto, en la paleta superior buscar **Agent** (icono de estrella)
2.  Arrastrar **Variable** al canvas
3.  Repetir para crear estas variables:

Nombre

Tipo

Valor inicial

¿Para qué sirve?

`proveedor`

String

`""`

Nombre del proveedor (Lear, Condumex, Magna)

`region`

String

`""`

Norte o Sur

`destinoOEM`

String

`""`

GM_SILAO, GM_SLP o BMW_SLP

`pallets`

int

`0`

Número de pallets que trae

`tEntradaSistema`

double

`0`

Hora en que entró

`tSalidaSistema`

double

`0`

Hora en que salió

**Cómo crear cada variable:**

1.  Arrastrar **Variable** al canvas de Truck
2.  En Properties:
    -   **Name:** escribir el nombre (ej. `proveedor`)
    -   **Type:** seleccionar el tipo (String, int, double)
    -   **Initial value:** dejar como está

> 💡 **No te preocupes por los valores iniciales, los asignaremos después**

### 📸 Resultado esperado

Debes ver en el canvas de Truck algo así:

```
Truck (Agent Type)
├─ proveedor : String
├─ region : String
├─ destinoOEM : String
├─ pallets : int
├─ tEntradaSistema : double
└─ tSalidaSistema : double

```

### ⚠️ PROBLEMAS COMUNES

Problema

Solución

No encuentro "Variable"

Buscar en paleta superior, sección Agent (icono estrella)

Me pide "initial value"

Para String usa `""`, para int/double usa `0`

Las variables no aparecen

Asegúrate de estar en el canvas de Truck, no de Main

### ✅ Checklist

-   [ ] Agente Truck creado
-   [ ] 6 variables agregadas correctamente
-   [ ] Todas las variables tienen el tipo correcto

----------

## 10. PASO 4 – CREAR LAS FUENTES DE CAMIONES

### 🎯 Objetivo

Configurar cómo y cuándo llegarán camiones al CEDIS desde cada proveedor.

### 🧠 Lógica

Tenemos 3 proveedores principales:

-   **Lear** (región Norte): Envía camiones con 26 pallets
-   **Condumex** (región Sur): Envía camiones con 24 pallets
-   **Magna** (región Sur): Envía camiones con 28 pallets

Usaremos **bloques Source** para generar camiones automáticamente.

### 🛠️ Configuración

#### **Paso 4.1: Abrir la paleta de Process Modeling**

1.  Ir al agente **Main** (doble click en Projects → Main)
2.  En la paleta izquierda, buscar **Process Modeling Library**
3.  Si no la ves, ir a menú **View → Libraries → Process Modeling Library**

#### **Paso 4.2: Crear Source para Lear**

1.  Arrastrar un bloque **Source** al canvas de Main
    
2.  Click en el bloque → Properties:
    
    -   **Name:** `SRC_LEAR_NORTE`
    -   **Agent type:** seleccionar `Truck`
    -   **Arrival rate:** escribir `uniform(2, 4)`
3.  Buscar la sección **On exit (code)** y escribir:
    

```java
agent.proveedor = "LEAR";
agent.region = "NORTE";
agent.pallets = 26;
agent.tEntradaSistema = time();

```

> 💡 **Explicación del código:**
> 
> -   `agent` = el camión que acaba de ser creado
> -   `time()` = hora actual de la simulación
> -   `uniform(2, 4)` = entre 2 y 4 camiones por hora (aleatorio)

#### **Paso 4.3: Crear Source para Condumex**

1.  Arrastrar otro **Source**
2.  Configurar:
    -   **Name:** `SRC_CONDUMEX_SUR`
    -   **Agent type:** `Truck`
    -   **Arrival rate:** `uniform(1, 3)`
    -   **On exit:**

```java
agent.proveedor = "CONDUMEX";
agent.region = "SUR";
agent.pallets = 24;
agent.tEntradaSistema = time();

```

#### **Paso 4.4: Crear Source para Magna**

1.  Arrastrar otro **Source**
2.  Configurar:
    -   **Name:** `SRC_MAGNA_SUR`
    -   **Agent type:** `Truck`
    -   **Arrival rate:** `uniform(1.5, 3.5)`
    -   **On exit:**

```java
agent.proveedor = "MAGNA";
agent.region = "SUR";
agent.pallets = 28;
agent.tEntradaSistema = time();

```

### 💡 CONSEJOS

-   Coloca los 3 Sources uno debajo del otro en el lado izquierdo del canvas
-   Puedes ajustar las tasas de llegada después si quieres más o menos camiones

### 📊 Tasas de llegada explicadas

Proveedor

Rate

Significado

Lear

uniform(2,4)

Entre 2 y 4 camiones/hora

Condumex

uniform(1,3)

Entre 1 y 3 camiones/hora

Magna

uniform(1.5,3.5)

Entre 1.5 y 3.5 camiones/hora

> 💡 **Esto da un total de ~150-250 camiones al día**

### ⚠️ PROBLEMAS COMUNES

Problema

Solución

No encuentro "On exit"

Hacer scroll hacia abajo en Properties

Error en el código

Verifica las comillas `"` y los puntos y coma `;`

No aparece "Truck" en Agent type

Asegúrate de haber creado el agente Truck primero

### ✅ Checklist

-   [ ] 3 Sources creados y nombrados correctamente
-   [ ] Cada Source tiene su rate configurado
-   [ ] El código On exit funciona sin errores (no aparece línea roja)

----------

# PARTE 3: FLUJO DE ENTRADA Y ANDENES

----------

## 11. PASO 5 – ENTRADA AL CEDIS Y GESTIÓN DE ANDENES

### 🎯 Objetivo

Simular que los 3 flujos de camiones entran al CEDIS, esperan si no hay andén disponible, descargan y liberan el andén.

### 🧠 Lógica

Secuencia de eventos:

1.  Camiones de 3 proveedores → Se juntan en un punto de entrada
2.  Si no hay andén disponible → Esperan en cola
3.  Cuando hay andén → Lo ocupan
4.  Descargan (tarda tiempo) → Liberan el andén

### 🛠️ Configuración

#### **Paso 5.1: Crear el ResourcePool de andenes**

1.  En la paleta de Main, buscar **Agent → Resource Pool**
2.  Arrastrar al canvas (fuera del flowchart, arriba a la derecha)
3.  Configurar:
    -   **Name:** `docks`
    -   **Type:** `Resource Units`
    -   **Capacity:** `24`

> 💡 **Esto representa los 24 andenes físicos del CEDIS**

#### **Paso 5.2: Conectar los Sources a un punto común**

1.  Arrastrar un bloque **Enter** al canvas
    
2.  Colocarlo a la derecha de los 3 Sources
    
3.  Configurar:
    
    -   **Name:** `ENTER_CEDIS`
4.  **Conectar** los Sources al Enter:
    
    -   Hacer click en el punto naranja del Source
    -   Arrastrar hacia el Enter
    -   Repetir para los 3 Sources

![Diagrama esperado:]

```
SRC_LEAR_NORTE ──┐
SRC_CONDUMEX_SUR ├──> ENTER_CEDIS
SRC_MAGNA_SUR ───┘

```

#### **Paso 5.3: Crear la cola de espera**

1.  Arrastrar un bloque **Queue**
    
2.  Colocarlo a la derecha del Enter
    
3.  Configurar:
    
    -   **Name:** `Q_ANDEN`
    -   **Capacity:** `unlimited`
4.  Conectar: `ENTER_CEDIS` → `Q_ANDEN`
    

#### **Paso 5.4: Asignar el andén (Seize)**

1.  Arrastrar un bloque **Seize**
    
2.  Colocarlo a la derecha de Queue
    
3.  Configurar:
    
    -   **Name:** `SEIZE_ANDEN`
    -   **Resource sets:** Click en **Add** → elegir `docks` → Quantity: `1`
4.  Conectar: `Q_ANDEN` → `SEIZE_ANDEN`
    

> 💡 **Seize = "tomar" un recurso. El camión ocupa 1 andén**

#### **Paso 5.5: Simular la descarga (Delay)**

1.  Arrastrar un bloque **Delay**
    
2.  Configurar:
    
    -   **Name:** `UNLOAD`
    -   **Delay time:** `triangular(0.3, 0.6, 1.0)`
3.  Conectar: `SEIZE_ANDEN` → `UNLOAD`
    

> 💡 **triangular(min, moda, max) = tiempo variable entre 0.3 y 1 hora**

#### **Paso 5.6: Liberar el andén (Release)**

1.  Arrastrar un bloque **Release**
    
2.  Configurar:
    
    -   **Name:** `RELEASE_ANDEN`
    -   **Resource sets:** Click en **Add** → elegir `docks`
3.  Conectar: `UNLOAD` → `RELEASE_ANDEN`
    

### 📸 Flowchart esperado hasta aquí

```
SRC_LEAR ──┐
SRC_COND ──┼──> ENTER_CEDIS → Q_ANDEN → SEIZE_ANDEN → UNLOAD → RELEASE_ANDEN
SRC_MAGNA ─┘

```

### 💡 CONSEJOS

-   Mantén todo alineado horizontalmente para que se vea ordenado
-   Puedes arrastrar los bloques para reorganizarlos
-   Usa Ctrl+Z si conectas algo mal

### ⚠️ PROBLEMAS COMUNES

Problema

Solución

No puedo conectar bloques

Asegúrate de arrastrar desde el punto naranja

Seize no encuentra "docks"

Primero crea el ResourcePool docks

Error "Cannot resolve symbol docks"

El ResourcePool debe estar en Main, no en Truck

### ✅ Checklist

-   [ ] ResourcePool `docks` creado con capacidad 24
-   [ ] Enter conecta los 3 Sources
-   [ ] Flowchart completo: Enter → Queue → Seize → Delay → Release
-   [ ] Todas las conexiones funcionan (no hay líneas rojas)

----------

## 12. PASO 6 – RUTEO HACIA RECEPCIÓN NORTE O SUR

### 🎯 Objetivo

Enviar cada camión a la zona de recepción correcta según su región de origen.

### 🧠 Lógica

Después de liberar el andén:

-   Si `agent.region == "NORTE"` → va a Recepción Norte
-   Si `agent.region == "SUR"` → va a Recepción Sur

Usaremos un bloque **SelectOutput** para decidir.

### 🛠️ Configuración

#### **Paso 6.1: Crear el bloque de decisión**

1.  Arrastrar un bloque **SelectOutput**
    
2.  Configurar:
    
    -   **Name:** `ROUTE_RECEPCION`
    -   **Type:** `Condition`
    -   **Condition:** seleccionar `By code`
3.  En el campo de código escribir:
    

```java
if (agent.region.equals("NORTE")) {
    return 0;  // Rama 0 = Recepción Norte
} else {
    return 1;  // Rama 1 = Recepción Sur
}

```

4.  Conectar: `RELEASE_ANDEN` → `ROUTE_RECEPCION`

> 💡 **SelectOutput tiene 2 salidas: puerto 0 (arriba) y puerto 1 (abajo)**

#### **Paso 6.2: Crear los delays de recepción**

1.  Arrastrar un **Delay** a la derecha, arriba:
    
    -   **Name:** `DELAY_RECEP_NORTE`
    -   **Delay time:** `triangular(0.15, 0.25, 0.40)`
2.  Arrastrar otro **Delay** a la derecha, abajo:
    
    -   **Name:** `DELAY_RECEP_SUR`
    -   **Delay time:** `triangular(0.15, 0.25, 0.40)`
3.  Conectar:
    
    -   Rama 0 (puerto superior) de `ROUTE_RECEPCION` → `DELAY_RECEP_NORTE`
    -   Rama 1 (puerto inferior) de `ROUTE_RECEPCION` → `DELAY_RECEP_SUR`

#### **Paso 6.3: Convergencia hacia Sorting**

Ahora las dos ramas deben juntarse para ir a Sorting.

1.  Arrastrar un **Delay** más a la derecha (en el centro):
    
    -   **Name:** `SORTING_PROCESS`
    -   **Delay time:** `triangular(0.2, 0.4, 0.8)`
2.  **Conectar ambos delays de recepción a SORTING:**
    
    -   `DELAY_RECEP_NORTE` → `SORTING_PROCESS`
    -   `DELAY_RECEP_SUR` → `SORTING_PROCESS`

> 💡 **AnyLogic permite que múltiples bloques se conecten a uno solo**

### 📸 Diagrama esperado

```
RELEASE_ANDEN → ROUTE_RECEPCION ─┬─(0)─> DELAY_RECEP_NORTE ─┐
                                 │                           ├─> SORTING_PROCESS
                                 └─(1)─> DELAY_RECEP_SUR ───┘

```

### 💡 CONSEJOS

-   Organiza los bloques en forma de "Y" invertida para que se vea claro
-   El número (0) o (1) aparece cuando conectas cada rama
-   Si te equivocas, haz click en la flecha y presiona Delete

### ⚠️ PROBLEMAS COMUNES

Problema

Solución

Error: "equals not found"

Usa `agent.region.equals("NORTE")` no `==`

SelectOutput solo tiene 1 salida

Click en el bloque → Properties → Outputs: 2

No sé cuál es la rama 0

La rama superior = 0, la inferior = 1

### ✅ Checklist

-   [ ] SelectOutput configurado con código correcto
-   [ ] 2 delays de recepción creados
-   [ ] Ambas ramas conectadas a SORTING_PROCESS
-   [ ] El flowchart se ve organizado

----------

# PARTE 4: CROSS-DOCKING, BUFFER Y KITTING

----------

## 13. PASO 7 – DECISIÓN: CROSS-DOCKING O BUFFER ESTRATÉGICO

### 🎯 Objetivo

Simular que el 65% de los pallets pasan directo a embarques (cross-docking) y el 35% va a almacenamiento temporal (buffer).

### 🧠 Lógica

Según el diseño de la Actividad 6:

-   **65%** → Cross-docking (flujo directo)
-   **30%** → Buffer estratégico
-   **5%** → Kitting/Valor agregado

Esto refleja la operación real del CEDIS.

### 🛠️ Configuración

#### **Paso 7.1: Crear la decisión de flujo**

1.  Arrastrar un **SelectOutput**
2.  Configurar:
    -   **Name:** `FLOW_DECISION`
    -   **Type:** `Condition
` (continuación)

-   **Condition:** `By code`

3.  En el campo de código escribir:

```java
double r = uniform(0, 1);

if (r < 0.65) {
    return 0;  // Cross-docking directo (65%)
} else if (r < 0.95) {
    return 1;  // Buffer estratégico (30%)
} else {
    return 2;  // Kitting / Valor agregado (5%)
}

```

4.  Conectar: `SORTING_PROCESS` → `FLOW_DECISION`

> 💡 **uniform(0,1) genera un número aleatorio entre 0 y 1**
> 
> -   Si es menor a 0.65 (65%) → Cross-docking
> -   Si es 0.65-0.95 (30%) → Buffer
> -   Si es mayor a 0.95 (5%) → Kitting

#### **Paso 7.2: Aumentar el número de salidas del SelectOutput**

1.  Click en `FLOW_DECISION`
2.  En Properties buscar **Outputs**
3.  Cambiar de `2` a `3`

> 💡 **Ahora el bloque tendrá 3 salidas (puertos 0, 1, 2)**

#### **Paso 7.3: Crear el proceso de Buffer**

1.  Arrastrar un **Delay** debajo de FLOW_DECISION:
    -   **Name:** `BUFFER_TIME`
    -   **Delay time:** `triangular(1, 3, 6)`

> 💡 **Los materiales que van a buffer esperan 1-6 horas**

#### **Paso 7.4: Crear el proceso de Kitting**

1.  Arrastrar otro **Delay** más abajo:
    -   **Name:** `KITTING_PROCESS`
    -   **Delay time:** `triangular(0.15, 0.30, 0.50)`

> 💡 **Kitting = operaciones de valor agregado (etiquetado, secuenciación)**

### 📸 Diagrama esperado

```
SORTING_PROCESS → FLOW_DECISION ─┬─(0)─> [hacia Destino OEM] (Cross-docking)
                                 │
                                 ├─(1)─> BUFFER_TIME → [hacia Destino OEM]
                                 │
                                 └─(2)─> KITTING_PROCESS → [hacia Destino OEM]

```

> ⚠️ **NOTA IMPORTANTE:** Por ahora dejamos las salidas sin conectar. Las conectaremos en el siguiente paso.

### 💡 CONSEJOS

-   Organiza los bloques verticalmente: Cross-docking arriba, Buffer en medio, Kitting abajo
-   Deja espacio a la derecha para el siguiente paso
-   Usa las guías de alineación de AnyLogic (aparecen automáticamente)

### ⚠️ PROBLEMAS COMUNES

Problema

Solución

Error en el código

Verifica que usas `<` no `<=` y que los puntos y coma están bien

Solo veo 2 salidas

Cambia Outputs a `3` en Properties

No entiendo los porcentajes

0.65 = 65%, 0.95 = 65%+30%=95%, >0.95 = 5% restante

### ✅ Checklist

-   [ ] FLOW_DECISION configurado con 3 salidas
-   [ ] Código de decisión funciona sin errores
-   [ ] BUFFER_TIME creado
-   [ ] KITTING_PROCESS creado
-   [ ] Los 3 flujos están claros visualmente

----------

## 14. PASO 8 – ASIGNACIÓN DE DESTINO OEM (GM SILAO, GM SLP, BMW SLP)

### 🎯 Objetivo

Decidir a qué cliente final irán los materiales: GM Silao, GM San Luis Potosí o BMW San Luis Potosí.

### 🧠 Lógica

Según el análisis de la Actividad 6, la distribución de destinos es aproximadamente:

-   **55%** → GM Silao (cliente más grande)
-   **33%** → GM San Luis Potosí
-   **12%** → BMW San Luis Potosí

### 🛠️ Configuración

#### **Paso 8.1: Crear el bloque de asignación de destino**

1.  Arrastrar un **SelectOutput**
    
2.  Configurar:
    
    -   **Name:** `DESTINO_OEM`
    -   **Type:** `Condition`
    -   **Condition:** `By code`
    -   **Outputs:** `3`
3.  En el campo de código escribir:
    

```java
double r = uniform(0, 1);

if (r < 0.55) {
    agent.destinoOEM = "GM_SILAO";
    return 0;  // Rama 0
} else if (r < 0.88) {  // 0.55 + 0.33 = 0.88
    agent.destinoOEM = "GM_SLP";
    return 1;  // Rama 1
} else {
    agent.destinoOEM = "BMW_SLP";
    return 2;  // Rama 2
}

```

> 💡 **Este código hace 2 cosas:**
> 
> 1.  Asigna el destino al atributo `destinoOEM` del camión
> 2.  Lo envía por la rama correcta

#### **Paso 8.2: Conectar los 3 flujos anteriores al DESTINO_OEM**

Ahora vamos a conectar todo:

**Flujo 1 - Cross-docking:**

-   Conectar: Rama 0 de `FLOW_DECISION` → `DESTINO_OEM`

**Flujo 2 - Buffer:**

-   Conectar: `BUFFER_TIME` → `DESTINO_OEM`

**Flujo 3 - Kitting:**

-   Conectar: `KITTING_PROCESS` → `DESTINO_OEM`

> 💡 **Los 3 flujos convergen en DESTINO_OEM antes de ir a embarques**

### 📸 Diagrama completo hasta aquí

```
SORTING_PROCESS → FLOW_DECISION ─┬─(0)─> [Cross-docking directo] ─┐
                                 │                                 │
                                 ├─(1)─> BUFFER_TIME ─────────────┤
                                 │                                 ├─> DESTINO_OEM
                                 └─(2)─> KITTING_PROCESS ─────────┘

```

#### **Paso 8.3: Crear los procesos de preparación por cliente**

Ahora creamos 3 delays finales que representan la preparación de embarque para cada OEM:

**Para GM Silao:**

1.  Arrastrar un **Delay**:
    -   **Name:** `PREPARE_GM_SILAO`
    -   **Delay time:** `triangular(0.25, 0.40, 0.60)`

**Para GM SLP:** 2. Arrastrar otro **Delay**:

-   **Name:** `PREPARE_GM_SLP`
-   **Delay time:** `triangular(0.25, 0.40, 0.60)`

**Para BMW SLP:** 3. Arrastrar otro **Delay**:

-   **Name:** `PREPARE_BMW_SLP`
-   **Delay time:** `triangular(0.30, 0.45, 0.70)`

> 💡 **BMW tiene tiempos ligeramente mayores por requisitos de calidad más estrictos**

#### **Paso 8.4: Conectar DESTINO_OEM a los PREPARE**

-   Rama 0 de `DESTINO_OEM` → `PREPARE_GM_SILAO`
-   Rama 1 de `DESTINO_OEM` → `PREPARE_GM_SLP`
-   Rama 2 de `DESTINO_OEM` → `PREPARE_BMW_SLP`

### 💡 CONSEJOS

-   Organiza los 3 PREPARE en paralelo (uno al lado del otro)
-   Puedes agregar etiquetas de texto arriba de cada uno ("→ GM Silao", "→ GM SLP", "→ BMW SLP")
-   Usa colores para diferenciar: click en el bloque → Properties → Fill color

### ⚠️ PROBLEMAS COMUNES

Problema

Solución

Error: "Cannot assign to destinoOEM"

Verifica que creaste la variable en el agente Truck

Los porcentajes no suman 100%

55% + 33% + 12% = 100% ✓ Está bien

No sé cuál es cada rama

0=arriba, 1=medio, 2=abajo (en orden vertical)

### ✅ Checklist

-   [ ] DESTINO_OEM configurado con 3 salidas
-   [ ] Código asigna destinoOEM correctamente
-   [ ] 3 bloques PREPARE creados
-   [ ] Todas las conexiones funcionan
-   [ ] El flowchart se ve organizado

----------

## 15. PASO 9 – SALIDA DEL CEDIS Y REGISTRO DE MÉTRICAS

### 🎯 Objetivo

Crear el punto de salida del CEDIS y registrar los indicadores clave (KPIs) al momento de salir cada camión.

### 🧠 Lógica

Al salir, cada camión debe:

1.  Registrar su hora de salida
2.  Actualizar el contador de pallets procesados
3.  Calcular el tiempo de ciclo (entrada → salida)
4.  Desaparecer del sistema

### 🛠️ Configuración

#### **Paso 9.1: Crear variables globales en Main**

Antes de crear el Sink, necesitamos variables para almacenar los KPIs.

1.  Ir al agente **Main**
2.  En la paleta superior, buscar **Agent → Variable**
3.  Crear estas variables arrastrándolas al canvas (fuera del flowchart, en la parte superior):

Nombre

Tipo

Valor inicial

¿Para qué sirve?

`palletsProcessed`

int

`0`

Total de pallets procesados

`trucksProcessed`

int

`0`

Total de camiones procesados

`avgCycleTime`

double

`0.0`

Tiempo promedio en el CEDIS

`totalCycleTime`

double

`0.0`

Suma de todos los tiempos (para calcular promedio)

**Cómo crearlas:**

-   Arrastrar **Variable** al canvas
-   Properties → Name: `palletsProcessed`
-   Type: `int`
-   Initial value: `0`
-   Repetir para las otras 3

> 💡 **Estas variables se actualizarán cada vez que salga un camión**

#### **Paso 9.2: Crear el bloque de salida (Sink)**

1.  Arrastrar un bloque **Sink** a la derecha de los 3 PREPARE
    
2.  Configurar:
    
    -   **Name:** `EXIT_CEDIS`
3.  Conectar los 3 bloques PREPARE al Sink:
    
    -   `PREPARE_GM_SILAO` → `EXIT_CEDIS`
    -   `PREPARE_GM_SLP` → `EXIT_CEDIS`
    -   `PREPARE_BMW_SLP` → `EXIT_CEDIS`

> 💡 **Sink = punto de salida. Los camiones desaparecen aquí**

#### **Paso 9.3: Programar el registro de métricas**

1.  Click en el bloque `EXIT_CEDIS`
2.  Buscar en Properties la sección **On exit**
3.  Escribir este código:

```java
// Registrar hora de salida
agent.tSalidaSistema = time();

// Calcular tiempo de ciclo de este camión
double tCiclo = agent.tSalidaSistema - agent.tEntradaSistema;

// Actualizar contadores
palletsProcessed += agent.pallets;
trucksProcessed += 1;

// Actualizar tiempo promedio
totalCycleTime += tCiclo;
avgCycleTime = totalCycleTime / trucksProcessed;

```

> 💡 **Explicación línea por línea:**
> 
> -   `time()` = hora actual
> -   `tCiclo` = cuánto tiempo estuvo el camión en el CEDIS
> -   `+=` significa "sumar a la variable existente"
> -   `avgCycleTime` = promedio simple

### 📸 Flowchart completo final

```
SRC_LEAR ──┐
SRC_COND ──┼─> ENTER → Q_ANDEN → SEIZE → UNLOAD → RELEASE
SRC_MAGNA ─┘                                         |
                                                     v
                                              ROUTE_RECEPCION
                                               /          \
                                    RECEP_NORTE          RECEP_SUR
                                               \          /
                                                SORTING_PROCESS
                                                     |
                                              FLOW_DECISION
                                             /      |      \
                                      (0) Directo  (1)    (2)
                                            |    BUFFER  KITTING
                                            |      |      |
                                            \      |      /
                                             DESTINO_OEM
                                             /     |     \
                                       PREPARE  PREPARE  PREPARE
                                       GM_SILAO GM_SLP  BMW_SLP
                                             \     |     /
                                              EXIT_CEDIS

```

### ⚠️ PROBLEMAS COMUNES

Problema

Solución

Error: "palletsProcessed cannot be resolved"

Crea primero las variables en Main

Sink no acepta múltiples entradas

Sí acepta, solo conecta normalmente

avgCycleTime da error

Usa `0.0` como inicial, no `0`

### ✅ Checklist

-   [ ] 4 variables creadas en Main (pallets, trucks, avgCycleTime, totalCycleTime)
-   [ ] EXIT_CEDIS creado y conectado
-   [ ] Código On exit funciona sin errores
-   [ ] El flowchart completo está conectado de inicio a fin

----------

# PARTE 5: RECURSOS ADICIONALES (OPCIONAL)

----------

## 16. PASO 10 – AGREGAR MONTACARGAS Y OPERADORES (OPCIONAL)

### 🎯 Objetivo

Modelar el uso de montacargas y personal operativo en procesos clave.

### 🧠 Lógica

Algunos procesos requieren recursos físicos:

-   **Montacargas:** Para mover pallets en sorting, buffer y kitting
-   **Operadores:** Para tareas manuales

Podemos modelarlos con ResourcePools adicionales.

### 🛠️ Configuración

> ⚠️ **ESTE PASO ES OPCIONAL.** Solo hazlo si:
> 
> -   Ya terminaste los pasos anteriores
> -   Tu modelo funciona correctamente
> -   Quieres agregar más realismo

#### **Paso 10.1: Crear los ResourcePools**

1.  En Main, arrastrar **Resource Pool**:
    
    -   **Name:** `forklifts`
    -   **Capacity:** `12`
2.  Arrastrar otro **Resource Pool**:
    
    -   **Name:** `workers`
    -   **Capacity:** `20`

#### **Paso 10.2: Usar montacargas en SORTING**

Para que SORTING use montacargas:

1.  **ANTES de SORTING_PROCESS**, agregar un **Seize**:
    
    -   Name: `SEIZE_FORK_SORTING`
    -   Resource sets: `forklifts`, Quantity: `2`
2.  **DESPUÉS de SORTING_PROCESS**, agregar un **Release**:
    
    -   Name: `RELEASE_FORK_SORTING`
    -   Resource sets: `forklifts`
3.  Reconectar:
    
    -   `DELAY_RECEP_NORTE/SUR` → `SEIZE_FORK_SORTING` → `SORTING_PROCESS` → `RELEASE_FORK_SORTING` → `FLOW_DECISION`

> 💡 **Esto significa: Sorting necesita 2 montacargas simultáneamente**

#### **Paso 10.3: Usar montacargas en BUFFER (opcional)**

Repetir el proceso para BUFFER_TIME si quieres que también use montacargas.

### 💡 CONSEJOS

-   Solo agrega recursos si tienes tiempo y quieres experimentar
-   Los recursos aumentan el realismo pero complican el modelo
-   Puedes ver la utilización de recursos en la simulación

### ✅ Checklist (si decidiste hacerlo)

-   [ ] ResourcePools forklifts y workers creados
-   [ ] Seize/Release agregados en al menos un proceso
-   [ ] El modelo sigue funcionando correctamente

----------

# PARTE 6: INDICADORES Y VISUALIZACIÓN

----------

## 17. PASO 11 – CREAR DASHBOARD DE MONITOREO

### 🎯 Objetivo

Construir un panel visual que muestre los KPIs en tiempo real durante la simulación.

### 🧠 Lógica

Vamos a crear un "tablero de control" que muestre:

-   Pallets procesados
-   Camiones procesados
-   Tiempo promedio de ciclo
-   Utilización de andenes

### 🛠️ Configuración

#### **Paso 11.1: Crear el título del dashboard**

1.  En Main, en la paleta buscar **Presentation → Text**
2.  Arrastrar al canvas (arriba a la derecha, fuera del flowchart)
3.  Escribir: **"📊 DASHBOARD - CEDIS SAN BARTOLO"**
4.  Properties:
    -   Font: **Bold**, Size: **18**
    -   Text color: **Azul oscuro**

#### **Paso 11.2: Crear etiquetas fijas**

Crear 4 objetos Text con estos textos (uno debajo del otro):

```
Pallets procesados:
Camiones procesados:
Tiempo promedio de ciclo (horas):
Utilización de andenes (%):

```

Properties para cada uno:

-   Font size: **12**
-   Text alignment: **Left**

#### **Paso 11.3: Crear textos dinámicos (valores)**

Ahora vamos a crear textos que cambien automáticamente durante la simulación.

**Para Pallets procesados:**

1.  Arrastrar **Text** al lado derecho de "Pallets procesados:"
2.  En Properties:
    -   **Text:** borrar todo y escribir: `palletsProcessed`
    -   Font: **Bold**, Size: **14**
    -   Text color: **Verde**

> 💡 **Al escribir el nombre de la variable SIN comillas, AnyLogic la vincula automáticamente**

**Para Camiones procesados:**

1.  Otro **Text** al lado de "Camiones procesados:"
2.  Text: `trucksProcessed`
3.  Font: Bold, 14, Verde

**Para Tiempo promedio:**

1.  Otro **Text**
2.  Text: `format("%.2f", avgCycleTime)`
3.  Font: Bold, 14, Naranja

> 💡 **format("%.2f", ...) muestra solo 2 decimales**

**Para Utilización de andenes:**

1.  Otro **Text**
2.  Text: `format("%.1f", docks.utilization() * 100)`
3.  Font: Bold, 14, Rojo

> 💡 **docks.utilization() devuelve un valor entre 0 y 1, lo multiplicamos por 100 para %**

#### **Paso 11.4: Agregar una gráfica de tiempo (opcional pero recomendado)**

1.  En la paleta buscar **Analysis → Time Plot**
2.  Arrastrar debajo del dashboard de texto
3.  Properties:
    -   **Title:** "Evolución de Pallets Procesados"
    -   **Data items:** Click **Add** → `palletsProcessed`
    -   Width: **300**, Height: **200**

### 📸 Resultado esperado del dashboard

```
┌────────────────────────────────────────┐
│  📊 DASHBOARD - CEDIS SAN BARTOLO      │
├────────────────────────────────────────┤
│  Pallets procesados:          1,245    │
│  Camiones procesados:           89     │
│  Tiempo promedio de ciclo:     3.42 h  │
│  Utilización de andenes:       78.5%   │
│                                         │
│  [Gráfica de evolución en tiempo]      │
└────────────────────────────────────────┘

```

### 💡 CONSEJOS

-   Coloca el dashboard en la esquina superior derecha del canvas
-   Puedes agregar un rectángulo de fondo (Shapes → Rectangle) con color claro
-   Usa **Ctrl + flechas** para mover textos con precisión

### ⚠️ PROBLEMAS COMUNES

Problema

Solución

El texto muestra "palletsProcessed" literal

Quita las comillas, debe ser sin `""`

Error en format()

Verifica las comillas dobles `"%.2f"`

utilization() no funciona

Escribe `docks.utilization()` exactamente así

### ✅ Checklist

-   [ ] Título del dashboard creado
-   [ ] 4 etiquetas fijas agregadas
-   [ ] 4 textos dinámicos vinculados a variables
-   [ ] (Opcional) Gráfica de tiempo agregada
-   [ ] El dashboard se ve ordenado y legible

----------

## 18. PASO 12 – PANTALLA INICIAL Y DESCRIPCIÓN

### 🎯 Objetivo

Crear una introducción visual que explique qué representa el modelo.

### 🧠 Lógica

Al abrir el modelo, el usuario debe entender inmediatamente:

-   Qué CEDIS es
-   Qué flujos se simulan
-   Qué zonas existen

### 🛠️ Configuración

#### **Paso 12.1: Crear título principal**

1.  En Main, arrastrar **Text** a la parte superior central
2.  Escribir:

```
🏭 CEDIS AUTOMOTRIZ SAN BARTOLO
Simulación Operativa de Flujos Logísticos

```

3.  Properties:
    -   Font: **Bold**, Size: **20**
    -   Text alignment: **Center**
    -   Text color: **Azul oscuro**

#### **Paso 12.2: Crear cuadro de descripción**

1.  Arrastrar **Shapes → Rectangle** debajo del título
    
2.  Ajustar tamaño: ~400px ancho × 150px alto
    
3.  Properties:
    
    -   Fill color: **Amarillo claro (#FFFFCC)**
    -   Line color: **Gris**
4.  Arrastrar **Text** dentro del rectángulo
    
5.  Escribir:
    

```
📦 DESCRIPCIÓN DEL MODELO:

Este modelo representa el flujo de camiones desde 3 proveedores 
(Lear, Condumex, Magna) hacia el CEDIS San Bartolo. 

Incluye:
✓ Recepción Norte y Sur
✓ Proceso de Sorting
✓ Cross-docking (65%), Buffer (30%), Kitting (5%)
✓ Despacho hacia GM Silao, GM SLP y BMW SLP

```

6.  Properties: Font size: **11**, Text alignment: **Left**

#### **Paso 12.3: Agregar leyenda de zonas**

Si usaste la imagen del layout, agrega una pequeña leyenda:

1.  Crear varios **Text** pequeños con símbolos:

```
🟨 Recepción
🟦 Sorting
🟧 Buffer
🟩 Embarques

```

2.  Colocarlos en una esquina como referencia visual

### 📸 Resultado esperado

```
┌──────────────────────────────────────────────────────────┐
│        🏭 CEDIS AUTOMOTRIZ SAN BARTOLO                   │
│        Simulación Operativa de Flujos Logísticos         │
├──────────────────────────────────────────────────────────┤
│                                                           │
│  ┌────────────────────────────────────────────────┐     │
│  │ 📦 DESCRIPCIÓN DEL MODELO:                     │     │
│  │                                                 │     │
│  │ Este modelo representa el flujo de camiones... │     │
│  │ ...                                             │     │
│  └────────────────────────────────────────────────┘     │
│                                                           │
│  [LAYOUT DEL CEDIS]                [DASHBOARD]          │
│                                                           │
│  [FLOWCHART]                                             │
└──────────────────────────────────────────────────────────┘

```

### ✅ Checklist

-   [ ] Título principal creado y visible
-   [ ] Cuadro de descripción agregado
-   [ ] Leyenda de zonas (opcional) agregada
-   [ ] El modelo tiene una presentación profesional

----------

# PARTE 7: EJECUCIÓN Y PUBLICACIÓN

----------

## 19. PASO 13 – EJECUTAR Y PROBAR EL MODELO

### 🎯 Objetivo

Verificar que el modelo funciona correctamente antes de publicarlo.

### 🧠 Lógica

Antes de compartir el modelo, debemos:

1.  Ejecutarlo
2.  Ver que los camiones fluyen correctamente
3.  Verificar que los KPIs se actualizan
4.  Detectar y corregir errores

### 🛠️ Configuración

#### **Paso 13.1: Guardar el proyecto**

1.  Menú **File → Save** (o Ctrl+S)
2.  Verificar que se guardó correctamente

#### **Paso 13.2: Ejecutar la simulación**

1.  En la barra superior, buscar el botón ▶️ **Run**
2.  Click en **Run**
3.  Debería abrirse una ventana nueva con el modelo ejecutándose

> 💡 **Lo que debes ver:**
> 
> -   Camiones apareciendo en los Sources
> -   Moviéndose por el flowchart
> -   Los números del dashboard aumentando
> -   La utilización de andenes cambiando

#### **Paso 13.3: Observar el comportamiento**

Durante los primeros 5-10 minutos de simulación (tiempo real):

✅ **Señales de que funciona bien:**

-   Los camiones fluyen continuamente
-   No se acumulan demasiado en las colas
-   Los KPIs aumentan gradualmente
-   Utilización de andenes entre 60-85%

❌ **Señales de problema:**

-   Los camiones se atoran en un punto
-   Las colas crecen infinitamente
-   Los KPIs no cambian
-   Errores en consola

#### **Paso 13.4: Tabla de valores de referencia**

Después de **1 día simulado** (24 horas simuladas ≈ 2-3 minutos reales), deberías ver aproximadamente:

KPI

Valor esperado

Tu modelo

Pallets procesados

6,000 - 8,000

_____

Camiones procesados

200 - 300

_____

Tiempo promedio ciclo

2.5 - 4.5 horas

_____

Utilización andenes

65% - 85%

_____

> 💡 **Si tus valores están muy lejos, ajusta:**
> 
> -   Las tasas de llegada (uniform) en los Sources
> -   Los tiempos de Delay
> -   La capacidad de recursos

### ⚠️ PROBLEMAS COMUNES Y SOLUCIONES

Problema

Causa probable

Solución

Camiones no avanzan

Falta una conexión

Revisa que todos los bloques estén conectados

Cola infinita en Q_ANDEN

Pocos andenes o descarga muy lenta

Aumenta docks.capacity o reduce UNLOAD time

KPIs no cambian

Código On exit tiene error

Revisa el código en EXIT_CEDIS

Error "NullPointerException"

Variable no inicializada

Verifica initial values de variables

Utilización 100% constante

Llegan demasiados camiones

Reduce las tasas en Sources

### 💡 CONSEJOS PARA AJUSTAR EL MODELO

**Si quieres más tráfico:**

```java
// En los Sources, cambiar rates a:
uniform(3, 6)  // Más camiones

```

**Si quieres procesos más rápidos:**

```java
// En los Delays, cambiar a:
triangular(0.1, 0.2, 0.3)  // Tiempos menores

```

**Si quieres más recursos:**

```java
// Cambiar capacidad:
docks.capacity = 32;  // Más andenes

```

### ✅ Checklist

-   [ ] Modelo ejecutado sin errores de compilación
-   [ ] Camiones fluyen de inicio a fin
-   [ ] Dashboard se actualiza en tiempo real
-   [ ] KPIs están en rangos razonables
-   [ ] He ajustado parámetros si era necesario

----------

## 20. PASO 14 – PUBLICAR EN ANYLOGIC CLOUD

### 🎯 Objetivo

Exportar el modelo a la nube para que tu profesor pueda verlo y evaluarlo.

### 🧠 Lógica

AnyLogic Cloud permite compartir modelos vía web sin que otros necesiten instalar el software.

### 🛠️ Configuración

#### **Paso 14.1: Exportar a la nube**

1.  En AnyLogic, menú **File → Export → To AnyLogic Cloud...**
2.  Aparecerá una ventana de login

#### **Paso 14.2: Iniciar sesión**

Si no tienes cuenta:

1.  Click en **Sign up**
2.  Puedes usar tu cuenta de Google
3.  O crear una nueva con email

Si ya tienes cuenta:

1.  Iniciar sesión normalmente

#### **Paso 14.3: Configurar la publicación**

1.  **Model name:** `CEDIS_SanBartolo_TuApellido_TuMatricula`
    
    -   Ejemplo: `CEDIS_SanBartolo_Garcia_U12345`
2.  **Access:** Seleccionar **Public**
(continuación del Paso 14.3)

3.  **Description:** Escribir una descripción breve:

```
Modelo de simulación del CEDIS Automotriz San Bartolo.
Actividad 9 - Logística y Cadena de Valor
Estudiante: [Tu Nombre]
Matrícula: [Tu Matrícula]

```

4.  **Tags:** Agregar etiquetas (opcional):
    
    -   `cedis`
    -   `logistica`
    -   `automotriz`
5.  Click en **Upload**
    

> 💡 **La subida puede tardar 2-5 minutos dependiendo de tu conexión**

#### **Paso 14.4: Obtener el enlace**

1.  Cuando termine la subida, click en **Open in browser**
    
2.  Se abrirá tu navegador con el modelo
    
3.  **Copiar la URL completa** de la barra de direcciones
    
    -   Ejemplo: `https://cloud.anylogic.com/model/abc123xyz`
4.  **Probar el modelo en la nube:**
    
    -   Click en el botón ▶️ **Run model**
    -   Verificar que funciona igual que en tu computadora

#### **Paso 14.5: Guardar el enlace**

Pega tu enlace aquí para incluirlo en tu reporte:

> 🔗 **Enlace al modelo en AnyLogic Cloud:**  
> `_________________________________________________`

### 💡 CONSEJOS

-   Guarda este enlace en un documento de texto también
-   Prueba el enlace en modo incógnito para verificar que es público
-   Si necesitas actualizar el modelo, puedes exportarlo de nuevo (sobrescribirá)

### ⚠️ PROBLEMAS COMUNES

Problema

Solución

No puedo crear cuenta

Usa la opción "Sign in with Google"

Error al subir

Verifica tu conexión a internet

El modelo no se ve en la nube

Asegúrate de seleccionar "Public" no "Private"

No funciona en la nube pero sí local

Puede que uses librerías no soportadas (poco común)

### ✅ Checklist

-   [ ] Modelo exportado exitosamente
-   [ ] Cuenta en AnyLogic Cloud creada
-   [ ] Modelo probado en el navegador
-   [ ] Enlace copiado y guardado
-   [ ] Modelo configurado como "Public"

----------

# PARTE 8: DOCUMENTACIÓN Y ENTREGA

----------

## 21. EVIDENCIAS A ENTREGAR

### 📋 Contenido del reporte

Tu reporte debe incluir las siguientes secciones:

#### **1. PORTADA**

Incluir:

-   Nombre de la universidad/institución
-   Nombre del curso: Logística y Cadena de Valor
-   Título: "Actividad 9 - Modelado del CEDIS Automotriz San Bartolo"
-   Tu nombre completo
-   Tu matrícula
-   Fecha de entrega

----------

#### **2. INTRODUCCIÓN (1/2 cuartilla)**

Escribe un breve texto que explique:

-   ¿Qué es el CEDIS San Bartolo?
-   ¿Por qué es importante modelarlo?
-   ¿Qué problema logístico resuelve?
-   ¿Qué aprenderás con este ejercicio?

**Ejemplo de inicio:**

> "El CEDIS Automotriz San Bartolo es un centro de distribución estratégico ubicado en el Bajío mexicano que consolida materiales de múltiples proveedores (Lear, Condumex, Magna) para abastecer tres ensambladoras automotrices: GM Silao, GM San Luis Potosí y BMW San Luis Potosí. Este modelo de simulación permite..."

----------

#### **3. DESCRIPCIÓN DEL MODELO (1 cuartilla)**

Explica:

**a) Elementos modelados:**

-   3 fuentes de camiones (Sources)
-   Sistema de andenes con 24 docks
-   Procesos de recepción, sorting, buffer y kitting
-   Ruteo hacia 3 destinos OEM

**b) Parámetros clave:**

-   Tasas de llegada de camiones
-   Tiempos de proceso
-   Capacidad de recursos
-   Distribución de destinos (55% GM Silao, 33% GM SLP, 12% BMW)

**c) Decisiones de diseño:**

-   ¿Por qué usaste esos tiempos?
-   ¿Por qué 24 andenes?
-   ¿Por qué 65% cross-docking?

> 💡 **Justifica tus decisiones con datos de la Actividad 6**

----------

#### **4. CAPTURAS DE PANTALLA (OBLIGATORIAS)**

Incluir al menos estas 6 imágenes:

##### **Captura 1: Layout completo del CEDIS**

-   Vista del canvas con el layout dibujado
-   Se deben ver todas las zonas etiquetadas
-   Incluir la imagen de fondo si la usaste

##### **Captura 2: Diagrama de flujo (flowchart)**

-   Vista completa del flowchart desde Source hasta Sink
-   Asegúrate de que se lean los nombres de los bloques
-   Puede ser en dos partes si no cabe en una imagen

##### **Captura 3: Configuración de un bloque clave**

-   Ejemplo: Properties del bloque FLOW_DECISION
-   Mostrando el código que programaste

##### **Captura 4: ResourcePool de andenes**

-   Mostrando la configuración de `docks`
-   Capacity: 24

##### **Captura 5: Modelo en ejecución**

-   Screenshot durante la simulación
-   Se deben ver:
    -   Camiones en el flowchart (puntos moviéndose)
    -   Dashboard con números actualizándose
    -   Tiempo de simulación corriendo

##### **Captura 6: Dashboard con KPIs**

-   Vista ampliada del dashboard
-   Mostrando valores después de 1 día simulado

> 💡 **Para capturar pantalla:**
> 
> -   Windows: `Win + Shift + S`
> -   Mac: `Cmd + Shift + 4`

----------

#### **5. TABLA DE RESULTADOS**

Crear una tabla con los KPIs obtenidos:

Indicador

Unidad

Valor Esperado (Act. 6)

Valor Obtenido

¿Cumple?

Pallets procesados/día

pallets

7,100

_______

✓/✗

Camiones procesados/día

camiones

250

_______

✓/✗

Tiempo promedio de ciclo

horas

3.0 - 4.0

_______

✓/✗

Utilización de andenes

%

70 - 85

_______

✓/✗

Throughput por hora

pallets/h

1,100

_______

✓/✗

**Análisis de resultados:**

Escribir 2-3 párrafos analizando:

-   ¿Los resultados están en rangos esperados?
-   ¿Qué KPI está mejor/peor?
-   ¿Qué ajustes hiciste para mejorar el desempeño?

----------

#### **6. ENLACE A ANYLOGIC CLOUD**

Incluir el enlace de forma visible:

> 🔗 **Enlace al modelo en AnyLogic Cloud:**  
> `https://cloud.anylogic.com/model/___________`

**Instrucciones para el profesor:**

> "Para ejecutar el modelo, hacer click en el botón 'Run model' y esperar a que cargue. El modelo simulará automáticamente 24 horas. Los indicadores se pueden ver en el dashboard superior derecho."

----------

#### **7. ANÁLISIS DE CUELLOS DE BOTELLA**

Identifica problemas operativos del CEDIS:

**¿Dónde se acumulan más camiones?**

-   [ ] En Q_ANDEN (esperando andén)
-   [ ] En SORTING
-   [ ] En BUFFER
-   [ ] En las zonas de preparación

**¿Qué recurso está más saturado?**

-   [ ] Andenes (docks)
-   [ ] Montacargas (si los usaste)
-   [ ] Ninguno, todo fluye bien

**Propuestas de mejora:**

Escribe 3-4 propuestas concretas:

1.  **Si hay cola en andenes:**  
    → "Aumentar de 24 a 28 andenes reduciría el tiempo de espera en 30%"
    
2.  **Si el tiempo de ciclo es alto:**  
    → "Optimizar el proceso de sorting con tecnología automática"
    
3.  **Si la utilización es baja:**  
    → "Reducir andenes a 20 o aumentar la tasa de llegada de camiones"
    

----------

#### **8. CONCLUSIÓN PERSONAL (10-15 líneas)**

Reflexiona sobre:

**¿Qué aprendiste?**

-   Sobre modelado de procesos logísticos
-   Sobre el uso de AnyLogic
-   Sobre el funcionamiento de un CEDIS real

**¿Qué dificultades tuviste?**

-   Técnicas (configuración, código)
-   Conceptuales (entender el flujo)
-   De tiempo

**¿Cómo se relaciona con un CEDIS real?**

-   ¿Este modelo representa fielmente la operación?
-   ¿Qué falta por modelar?
-   ¿Cómo usarías este modelo en una empresa real?

**¿Qué mejorarías del modelo?**

-   Elementos adicionales (WMS, AGVs, etc.)
-   Más realismo
-   Mejor visualización

**Ejemplo de conclusión:**

> "El desarrollo de este modelo me permitió comprender la complejidad operativa de un CEDIS automotriz de alto volumen. Aunque al inicio me costó entender la lógica del cross-docking, después fue evidente su importancia para la eficiencia. La simulación reveló que el cuello de botella principal está en el proceso de sorting, donde se acumulan hasta 15 camiones en espera. En un caso real, recomendaría invertir en sistemas automáticos de clasificación. Este ejercicio conecta directamente con la Actividad 6 donde diseñamos el CEDIS conceptualmente, y ahora pudimos validar que la capacidad de 24 andenes es suficiente para el volumen proyectado. AnyLogic demostró ser una herramienta poderosa para tomar decisiones logísticas basadas en datos simulados antes de hacer inversiones millonarias en infraestructura."

----------

#### **9. REFERENCIAS**

Incluir al menos 3 fuentes:

1.  Material del curso (Actividades 6 y 7)
2.  Documentación de AnyLogic
3.  Artículos sobre CEDIS automotrices

**Formato APA ejemplo:**

```
AnyLogic. (2024). Process Modeling Library Documentation. 
    Recuperado de https://anylogic.help/

García, J. (2023). Diseño de Centros de Distribución en la Industria 
    Automotriz. Revista Logística México, 15(3), 45-62.

```

----------

## 22. FORMATO DEL REPORTE

### 📄 Especificaciones técnicas

Elemento

Especificación

**Extensión**

6-8 cuartillas (sin contar portada)

**Fuente**

Arial 11 o Times New Roman 12

**Interlineado**

1.5

**Márgenes**

2.5 cm todos los lados

**Formato de entrega**

PDF

**Nombre del archivo**

`Act9_CEDIS_Apellido_Matricula.pdf`

### 📦 Entregables

**Archivo 1: Reporte en PDF**

-   Incluye todo lo anterior
-   Con capturas de pantalla insertadas
-   Bien formateado y sin errores

**Archivo 2: Modelo de AnyLogic (opcional)**

-   Si el profesor lo solicita
-   Archivo `.alp`
-   Mismo nombre: `Act9_CEDIS_Apellido_Matricula.alp`

**Archivo 3: Enlace a la nube**

-   Puede ir en el reporte
-   O en un archivo de texto separado

----------

## 23. RÚBRICA DE EVALUACIÓN

### 📊 Distribución de puntos (100 puntos total)

Criterio

Puntos

Detalle

**1. Modelo funcional**

40 pts

- Flowchart completo y conectado

15

Todos los bloques conectados correctamente

- Código sin errores

10

Sources, SelectOutput, Exit funcionan

- Recursos configurados

8

docks con capacidad correcta

- Ejecución exitosa

7

El modelo corre sin errores

**2. Dashboard y KPIs**

15 pts

- Variables creadas

5

Las 4 variables principales

- Dashboard visible y funcional

5

Textos dinámicos actualizándose

- Valores razonables

5

KPIs en rangos esperados

**3. Publicación en Cloud**

10 pts

- Modelo publicado correctamente

5

Enlace funcional

- Acceso público

5

Cualquiera puede verlo

**4. Documentación**

25 pts

- Introducción y contexto

5

Clara y bien redactada

- Descripción técnica del modelo

8

Explica decisiones de diseño

- Capturas de pantalla

7

Las 6 capturas obligatorias

- Análisis de resultados

5

Tabla de KPIs y análisis

**5. Conclusión y análisis crítico**

10 pts

- Reflexión personal

5

Aprendizajes y dificultades

- Propuestas de mejora

5

Cuellos de botella y soluciones

**TOTAL**

**100 pts**

### 🎯 Criterios de excelencia (puntos extra)

-   **+5 pts:** Uso de recursos adicionales (montacargas, operadores)
-   **+5 pts:** Gráficas de evolución de KPIs
-   **+5 pts:** Experimentación con diferentes escenarios
-   **+3 pts:** Layout visualmente profesional con imagen de fondo

**Máximo con extras: 118 puntos (calificación máxima: 100)**

----------

## 24. CHECKLIST FINAL DE AUTOEVALUACIÓN

### ✅ Antes de entregar, verifica:

#### **MODELO (AnyLogic)**

-   [ ] El proyecto se llama `CEDIS_SanBartolo_TuApellido`
-   [ ] Unidades configuradas en horas y metros
-   [ ] Agente Truck creado con 6 atributos
-   [ ] 3 Sources configurados correctamente
-   [ ] Flowchart completo de inicio a fin
-   [ ] ResourcePool docks con capacidad 24
-   [ ] SelectOutput para ruteo Norte/Sur funciona
-   [ ] SelectOutput para decisión Cross-docking/Buffer/Kitting funciona
-   [ ] SelectOutput para destino OEM funciona
-   [ ] 4 variables globales creadas (pallets, trucks, avgCycleTime, totalCycleTime)
-   [ ] Código en EXIT_CEDIS actualiza KPIs correctamente
-   [ ] Dashboard visible con 4 indicadores
-   [ ] Layout dibujado o imagen de fondo insertada
-   [ ] Pantalla inicial con descripción del modelo
-   [ ] El modelo corre sin errores por al menos 24 horas simuladas
-   [ ] Los KPIs están en rangos razonables

#### **PUBLICACIÓN**

-   [ ] Modelo exportado a AnyLogic Cloud
-   [ ] Configurado como "Public"
-   [ ] Enlace copiado y guardado
-   [ ] Modelo probado en navegador (funciona)

#### **DOCUMENTACIÓN**

-   [ ] Portada completa con todos los datos
-   [ ] Introducción de al menos 1/2 cuartilla
-   [ ] Descripción técnica del modelo
-   [ ] 6 capturas de pantalla incluidas
-   [ ] Tabla de KPIs con resultados
-   [ ] Enlace a AnyLogic Cloud en el documento
-   [ ] Análisis de cuellos de botella
-   [ ] Conclusión personal de 10-15 líneas
-   [ ] Al menos 3 referencias bibliográficas
-   [ ] Formato correcto (Arial 11, 1.5, márgenes 2.5cm)
-   [ ] Documento guardado como PDF
-   [ ] Nombre del archivo correcto: `Act9_CEDIS_Apellido_Matricula.pdf`
-   [ ] Revisión ortográfica (sin errores)

#### **CALIDAD**

-   [ ] Todas las capturas son legibles (no borrosas)
-   [ ] El texto está bien redactado (sin "muletillas")
-   [ ] Las tablas están alineadas correctamente
-   [ ] Los números tienen formato consistente (ej: 1,234 no 1234)
-   [ ] Las secciones están numeradas
-   [ ] Hay coherencia entre Actividad 6, 7 y 9

----------

## 25. PROBLEMAS COMUNES Y SOLUCIONES RÁPIDAS

### 🔧 Guía de troubleshooting

#### **Problema: "El modelo no compila"**

**Síntomas:**

-   Botón Run está deshabilitado
-   Aparecen líneas rojas en el código
-   Mensaje de error en la consola

**Soluciones:**

1.  Revisa que todas las variables estén creadas (en Truck y en Main)
2.  Verifica los puntos y coma `;` al final de cada línea de código
3.  Checa las comillas `"` en los nombres (deben ser comillas rectas, no curvas)
4.  Asegúrate de escribir `equals()` no `==` para comparar Strings
5.  Haz click en el error de la consola para ver dónde está el problema

----------

#### **Problema: "Los camiones no avanzan"**

**Síntomas:**

-   Camiones se crean pero no se mueven
-   Se quedan en un bloque específico
-   La simulación parece congelada

**Soluciones:**

1.  Verifica que TODOS los bloques están conectados
2.  Revisa que el último bloque es un **Sink** (no puede quedar sin salida)
3.  Asegúrate de que los ResourcePools tienen capacidad > 0
4.  Verifica que no hay un Seize sin su Release correspondiente

----------

#### **Problema: "Cola infinita en Q_ANDEN"**

**Síntomas:**

-   La Queue crece sin parar
-   Nunca baja de 50+ camiones
-   Utilización de andenes = 100%

**Soluciones:**

1.  **Reducir llegada de camiones:**
    
    ```java
    // En Sources, cambiar:
    uniform(1, 2)  // Menos camiones
    
    ```
    
2.  **Aumentar andenes:**
    
    ```java
    // En docks:
    capacity = 32;  // Más andenes
    
    ```
    
3.  **Acelerar descarga:**
    
    ```java
    // En UNLOAD:
    triangular(0.2, 0.3, 0.5)  // Más rápido
    
    ```
    

----------

#### **Problema: "Los KPIs no se actualizan"**

**Síntomas:**

-   Dashboard muestra siempre 0
-   O muestra el nombre de la variable literal

**Soluciones:**

1.  **Si muestra "palletsProcessed" literal:**
    
    -   Quita las comillas `""` del campo Text
    -   Debe ser: `palletsProcessed` sin comillas
2.  **Si muestra 0 siempre:**
    
    -   Verifica que el código en EXIT_CEDIS está correcto
    -   Asegúrate de usar `+=` no `=`
    -   Revisa que las variables están en **Main**, no en Truck

----------

#### **Problema: "Error al subir a Cloud"**

**Síntomas:**

-   Upload falla al 50%
-   Error de conexión
-   No aparece en el navegador

**Soluciones:**

1.  Verifica tu conexión a internet
2.  Intenta con otro navegador
3.  Reduce el tamaño del modelo (elimina gráficas no esenciales)
4.  Cierra sesión y vuelve a entrar
5.  Si persiste, usa el archivo .alp como alternativa

----------

#### **Problema: "El modelo funciona local pero no en Cloud"**

**Síntomas:**

-   Corre bien en tu computadora
-   Da error en AnyLogic Cloud

**Soluciones:**

1.  No uses librerías externas no soportadas
2.  Evita rutas de archivos locales (C:...)
3.  No uses `System.out.println()` (usa traceln() en su lugar)
4.  Asegúrate de que el modelo es auto-contenido

----------

## 26. CONSEJOS FINALES PARA PRINCIPIANTES

### 💡 Estrategia de trabajo recomendada

#### **Sesión 1 (1.5 horas): Estructura básica**

-   [ ] Crear proyecto
-   [ ] Configurar unidades
-   [ ] Crear agente Truck
-   [ ] Crear 3 Sources
-   [ ] Hacer flujo básico hasta RELEASE_ANDEN
-   **Meta:** Ver camiones entrando y descargando

#### **Sesión 2 (1.5 horas): Procesos internos**

-   [ ] Agregar ruteo Norte/Sur
-   [ ] Crear proceso de Sorting
-   [ ] Agregar decisión Cross-docking/Buffer/Kitting
-   **Meta:** Ver camiones tomando rutas diferentes

#### **Sesión 3 (1 hora): Destinos y salida**

-   [ ] Agregar asignación de destino OEM
-   [ ] Crear bloques PREPARE
-   [ ] Agregar EXIT_CEDIS con código de KPIs
-   **Meta:** Modelo completo funcionando

#### **Sesión 4 (1 hora): Visualización**

-   [ ] Dibujar layout
-   [ ] Crear dashboard
-   [ ] Agregar pantalla inicial
-   **Meta:** Modelo profesional

#### **Sesión 5 (0.5 horas): Pruebas y ajustes**

-   [ ] Ejecutar múltiples veces
-   [ ] Ajustar parámetros
-   [ ] Verificar KPIs
-   **Meta:** Resultados óptimos

#### **Sesión 6 (1 hora): Publicación y documentación**

-   [ ] Exportar a Cloud
-   [ ] Tomar capturas
-   [ ] Iniciar reporte
-   **Meta:** Evidencias listas

#### **Sesión 7 (1.5 horas): Reporte**

-   [ ] Completar todas las secciones
-   [ ] Revisar ortografía
-   [ ] Generar PDF
-   **Meta:** Entregable final

----------

### 🎓 Aprendizajes clave de esta actividad

Al completar este proyecto, habrás aprendido:

**Técnicas:** ✅ Modelado de procesos con AnyLogic  
✅ Uso de bloques Process Modeling  
✅ Programación básica en Java  
✅ Gestión de recursos (ResourcePools)  
✅ Cálculo de KPIs en simulación  
✅ Visualización de datos en dashboards

**Conceptuales:** ✅ Funcionamiento de un CEDIS automotriz  
✅ Importancia del cross-docking  
✅ Gestión de múltiples proveedores y clientes  
✅ Identificación de cuellos de botella  
✅ Toma de decisiones basada en simulación

**Profesionales:** ✅ Documentación técnica de modelos  
✅ Análisis de resultados  
✅ Presentación de evidencias  
✅ Pensamiento crítico sobre procesos logísticos

----------

### 🌟 ¿Qué hacer después de entregar?

**Si quieres seguir experimentando:**

1.  **Escenarios alternativos:**
    
    -   ¿Qué pasa si BMW duplica su demanda?
    -   ¿Y si Lear cierra temporalmente?
    -   ¿Cuántos andenes mínimos necesitas?
2.  **Mejoras del modelo:**
    
    -   Agregar turnos (día/noche con diferentes tasas)
    -   Modelar fallas de equipos
    -   Incluir costos operativos
    -   Agregar niveles de prioridad (JIT vs stock)
3.  **Integración con otras actividades:**
    
    -   Usar datos de tu Actividad 8 (métodos cuantitativos)
    -   Validar capacidades de tu Actividad 6
    -   Simular ubicaciones alternativas de tu Actividad 7

----------

## 27. ESPACIO PARA TU CONCLUSIÓN PERSONAL

> ✍️ **Después de completar el modelo, escribe aquí tu reflexión personal (mínimo 10-12 líneas):**

**Guía de preguntas para tu reflexión:**

1.  ¿Qué fue lo más difícil de este proyecto?
2.  ¿Qué aprendiste sobre logística que no sabías antes?
3.  ¿Cómo se relaciona esto con tu carrera profesional?
4.  ¿Usarías AnyLogic en el futuro? ¿Para qué?
5.  ¿Qué le dirías a un compañero que va a hacer esta actividad?

```
_________________________________________________________________
_________________________________________________________________
_________________________________________________________________
_________________________________________________________________
_________________________________________________________________
_________________________________________________________________
_________________________________________________________________
_________________________________________________________________
_________________________________________________________________
_________________________________________________________________
_________________________________________________________________
_________________________________________________________________

```

----------

## 28. RECURSOS ADICIONALES Y AYUDA

### 📚 Documentación oficial

-   **AnyLogic Help:** https://anylogic.help/
-   **Process Modeling Library:** https://anylogic.help/library-reference-guides/process-modeling-library/index.html
-   **Tutoriales en video:** https://www.anylogic.com/resources/educational-videos/

### 🎥 Videos recomendados

-   "Introduction to Process Modeling in AnyLogic" (15 min)
-   "Resource Pools Tutorial" (10 min)
-   "How to Export to AnyLogic Cloud" (5 min)

### 💬 Comunidad y soporte

-   **Foro oficial:** https://anylogic.help/forum/
-   Grupo de clase (si existe)
-   Horas de oficina del profesor

### 📧 Contacto para dudas

-   **Profesor:** [email del profesor]
-   **Horario de asesorías:** [horario]
-   **Foro del curso:** [link]

----------

## 29. GLOSARIO DE TÉRMINOS

Para estudiantes que no están familiarizados con algunos conceptos:

Término

Definición

**Agent**

Objeto que fluye por el modelo (en nuestro caso, camiones)

**CEDIS**

Centro de Distribución - almacén regional

**Cross-docking**

Transferencia directa sin almacenamiento

**Flowchart**

Diagrama de flujo de procesos

**KPI**

Key Performance Indicator - indicador clave de desempeño

**OEM**

Original Equipment Manufacturer - ensambladoras (GM, BMW)

**ResourcePool**

Conjunto de recursos limitados (andenes, montacargas)

**Sink**

Punto de salida del sistema

**Source**

Punto de entrada/generación de agentes

**Throughput**

Cantidad de unidades procesadas por unidad de tiempo

**Utilization**

Porcentaje de uso de un recurso

----------

## 30. MENSAJE FINAL PARA EL ESTUDIANTE

### 🎉 ¡Felicidades por llegar hasta aquí!

Este documento tiene más de **13,000 palabras** diseñadas específicamente para guiarte paso a paso, incluso si nunca has usado AnyLogic antes.

### 💪 Recuerda:

**Es normal sentirse abrumado al inicio.** Todos los ingenieros y profesionales de logística empezaron sin saber nada de simulación. Lo importante es:

✅ **Ir paso por paso** - No intentes hacer todo de golpe  
✅ **Probar frecuentemente** - Ejecuta el modelo después de cada cambio importante  
✅ **No temer a equivocarse** - Los errores son parte del aprendizaje  
✅ **Pedir ayuda cuando la necesites** - Usa los recursos disponibles

### 🚀 Habilidades que estás desarrollando:

-   Pensamiento sistémico
-   Modelado de procesos
-   Análisis cuantitativo
-   Programación básica
-   Documentación técnica
-   Presentación de resultados

**Estas habilidades son ALTAMENTE VALORADAS en la industria.**

### 📊 Dato motivacional:

Según LinkedIn, "Simulation Modeling" está entre las top 10 habilidades más demandadas en logística e ingeniería industrial en 2025.

### ✨ Tu modelo es único

Aunque todos seguimos las mismas instrucciones, **tu modelo será único** porque:

-   Tomarás decisiones de diseño propias
-   Ajustarás parámetros de forma diferente
-   Tu análisis tendrá tu perspectiva personal

**¡Eso es valioso!**

### 🎯 Al terminar este proyecto podrás decir:

> "Diseñé y simulé un centro de distribución automotriz completo que procesa más de 7,000 pallets diarios y abastece a tres ensambladoras OEM, optimizando recursos y analizando KPIs operativos mediante AnyLogic."

**Eso se ve EXCELENTE en un CV.**

----------

### 📞 ¿Necesitas ayuda adicional?

**Antes de preguntar, intenta:**

1.  Revisar la sección de "Problemas Comunes"
2.  Ejecutar el modelo para ver el error exacto
3.  Revisar que seguiste todos los pasos

**Al pedir ayuda, proporciona:**

-   Screenshot del error
-
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTEwMjM5Njc0NzgsNTg4MTYxNDQxLDE4Nz
I5MzM5NF19
-->