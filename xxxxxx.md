# 🟦 ACTIVIDAD 9 – MODELADO DEL CEDIS AUTOMOTRIZ SAN BARTOLO EN ANYLOGIC
### Curso: Logística y Cadena de Valor | Versión 2025 - EDICIÓN MEJORADA PARA PRINCIPIANTES

---

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

- **Primera vez:** 6-8 horas  
- **Con experiencia:** 3-4 horas  

---

## 1. DATOS DE IDENTIFICACIÓN

| Campo                    | Información a completar |
|--------------------------|------------------------|
| Nombre del estudiante    |                        |
| Matrícula                |                        |
| Carrera                  |                        |
| Grupo                    |                        |
| Fecha de entrega         |                        |
| Nombre del CEDIS modelado| CEDIS AUTOMOTRIZ SAN BARTOLO |

---

## 2. CONTEXTO Y VÍNCULO CON ACTIVIDADES ANTERIORES

### 🔗 ¿De dónde viene este proyecto?

Esta Actividad 9 **completa el trabajo** que hiciste en:

- **Actividad 6:** Diseñaste el CEDIS San Bartolo en papel (capacidad, áreas, flujos)  
- **Actividad 7:** Analizaste qué industrias podrían ubicarse en la región  
- **Actividad 8 (opcional):** Usaste métodos cuantitativos para decisiones logísticas  

Ahora vas a **dar vida a ese diseño** en una simulación digital.

### 🎯 ¿Qué voy a simular?

1. **Entrada:** Camiones de 3 proveedores (Lear, Condumex, Magna)  
2. **Procesos internos:** Descarga → Clasificación → Almacenamiento → Preparación  
3. **Salida:** Despacho hacia GM Silao, GM SLP y BMW SLP  

### 📊 Datos clave del CEDIS (Actividad 6)

- **Capacidad:** 22,000 pallets  
- **Entrada diaria:** ~7,100 pallets  
- **Salida diaria:** ~7,700 pallets  
- **Andenes:** 24 (8 recepción + 16 embarque)  
- **Cross-docking:** 65% de los materiales pasan directo sin almacenarse  

---

## 3. OBJETIVO GENERAL

> **Construir y documentar un modelo funcional del CEDIS en AnyLogic** que simule camiones entrando, procesos de descarga, clasificación, almacenamiento y despacho hacia tres clientes automotrices, con recursos, tiempos y KPIs medibles.

---

## 4. OBJETIVOS ESPECÍFICOS

Al terminar esta actividad, podrás:

1. ✅ Configurar un proyecto AnyLogic con unidades correctas  
2. ✅ Crear agentes (camiones) con información de carga y destino  
3. ✅ Dibujar el layout básico del CEDIS  
4. ✅ Construir un diagrama de flujo (flowchart) con bloques Process Modeling  
5. ✅ Gestionar recursos (andenes, montacargas)  
6. ✅ Programar decisiones de ruteo (hacia dónde va cada camión)  
7. ✅ Calcular indicadores (KPIs) como pallets procesados y tiempos  
8. ✅ Publicar el modelo en AnyLogic Cloud  
9. ✅ Crear un dashboard de monitoreo  

---

## 5. REQUISITOS PREVIOS

### Software

- **AnyLogic** instalado (versión PLE o superior)  
  👉 Descarga gratuita: [www.anylogic.com](https://www.anylogic.com/downloads/)

### Conocimientos

- Haber completado Actividades 6 y 7  
- Haber visto el video introductorio de AnyLogic (proporcionado por el profesor)  

### Materiales

- Layout del CEDIS San Bartolo (imagen PNG/JPG proporcionada)  
- Este documento MD como guía  

---

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
- [ ] Completado y funciona  
- [ ] Completado pero tengo dudas  
- [ ] No pude completarlo  

---

# PARTE 1: CONFIGURACIÓN INICIAL

---

## 7. PASO 1 – CREAR EL PROYECTO Y CONFIGURAR UNIDADES

### 🎯 Objetivo

Crear un proyecto nuevo en AnyLogic con las unidades correctas (horas y metros).

### 🧠 Lógica

Trabajaremos en un solo agente llamado `Main` que contendrá todo:
- El dibujo del CEDIS (layout)  
- El diagrama de flujo de camiones  
- Los recursos (andenes, montacargas)  
- Los indicadores de desempeño  

### 🛠️ Configuración

#### **Paso 1.1: Crear el proyecto**

1. Abrir AnyLogic  
2. Menú **File → New Model...**  
3. Asignar nombre: `CEDIS_SanBartolo_TuApellido`  
   - Ejemplo: `CEDIS_SanBartolo_Garcia`  
4. Click en **Finish**  

> 💡 **AnyLogic creará automáticamente un agente llamado `Main`**

#### **Paso 1.2: Configurar unidades de tiempo**

1. En el panel izquierdo **Projects**, hacer click en el **nombre del modelo** (arriba de Main)  
2. En la parte inferior, buscar la pestaña **Properties**  
3. Expandir la sección **Environment**  
4. Configurar:
   - **Time units:** seleccionar `hour` (hora)  
   - **Length units:** seleccionar `meter` (metro)  

> 💡 **¿Por qué horas y metros?**  
> - **Horas:** Los procesos logísticos se miden en horas (descarga = 0.5 horas)  
> - **Metros:** El CEDIS mide ~250m × 100m  

#### **Paso 1.3: Verificar que Main está activo**

1. En el panel izquierdo, hacer doble click en **Main**  
2. Debe abrirse una ventana blanca de trabajo (canvas)  
3. En la parte superior debe decir: **Main (Agent Type)**  

### ⚠️ PROBLEMAS COMUNES

| Problema | Solución |
|----------|----------|
| No encuentro "Environment" | Click en el nombre del PROYECTO (no en Main) |
| No aparece Main | Menú Projects → click derecho → New Agent Type → Nombre: Main |
| Las unidades no se guardan | Cerrar y reabrir el proyecto |

### ✅ Checklist

- [ ] Proyecto creado con nombre correcto  
- [ ] Unidades configuradas en horas y metros  
- [ ] Main está abierto y listo para trabajar  

---

## 8. PASO 2 – DIBUJAR EL LAYOUT DEL CEDIS

### 🎯 Objetivo

Crear la representación visual del CEDIS usando el layout proporcionado como referencia.

### 🧠 Lógica

Vamos a dibujar:
- La nave principal del CEDIS  
- Las zonas funcionales (Recepción, Sorting, Buffer, Kitting, Embarques)  
- Opcionalmente, insertar la imagen del layout como fondo  

### 🛠️ Configuración

#### **Paso 2.1: Insertar la imagen de fondo (RECOMENDADO)**

1. Guardar la imagen del layout en tu computadora  
2. En AnyLogic, con Main abierto, ir a menú **Insert → Image...**  
3. Buscar la imagen y hacer click en **Open**  
4. Click en el canvas para colocarla  
5. Ajustar tamaño arrastrando las esquinas  

**Para que no se mueva:**
6. Click derecho sobre la imagen → **Order → Send to Back**  
7. Click derecho → **Lock**  

> 💡 **Ahora la imagen queda de fondo y puedes dibujar encima**

#### **Paso 2.2: Dibujar las zonas principales**

En la paleta izquierda, buscar **Presentation → Space Markup**:

1. Arrastrar **Rectangular Node** al canvas  
2. Dibujar rectángulos sobre las zonas de la imagen:
   - Recepción Norte (amarillo claro)  
   - Recepción Sur (amarillo claro)  
   - Sorting (azul claro)  
   - Buffer Estratégico (amarillo)  
   - Kitting (azul claro)  
   - Embarques GM Silao (azul claro)  
   - Embarques GM SLP (azul claro)  
   - Embarques BMW SLP (azul claro)  

3. Para cambiar colores:
   - Click en el rectángulo  
   - En Properties → **Fill color** → elegir color  

#### **Paso 2.3: Agregar etiquetas de texto**

1. En la paleta, buscar **Presentation → Text**  
2. Arrastrar al canvas  
3. Escribir el nombre de cada zona:
   - "RECEPCIÓN NORTE"  
   - "RECEPCIÓN SUR"  
   - "SORTING"  
   - Etc.  

4. Cambiar tamaño de fuente:
   - Click en el texto → Properties → **Font** → Size: 14-16  

### 💡 CONSEJOS

- No necesitas ser perfecto, solo que se distinga cada zona  
- Usa colores similares al layout proporcionado  
- Si no quieres usar la imagen de fondo, está bien, solo dibuja rectángulos grandes  

### ⚠️ PROBLEMAS COMUNES

| Problema | Solución |
|----------|----------|
| La imagen tapa todo | Click derecho → Order → Send to Back |
| No puedo mover la imagen | Click derecho → Unlock |
| Los rectángulos no se ven | Cambiar Fill color y agregar borde (Line color) |

### ✅ Checklist

- [ ] Imagen de fondo insertada y bloqueada  
- [ ] 8 zonas dibujadas con rectángulos  
- [ ] Etiquetas de texto agregadas  
- [ ] Layout se ve claro y organizado  

---

# PARTE 2: CREACIÓN DE AGENTES Y FUENTES

---

## 9. PASO 3 – CREAR EL AGENTE `Truck`

### 🎯 Objetivo

Definir la "ficha técnica" de los camiones que entrarán al CEDIS.

### 🧠 Lógica

Cada camión necesita saber:
- ¿De qué proveedor viene? (Lear, Condumex, Magna)  
- ¿De qué región? (Norte o Sur)  
- ¿Cuántos pallets trae?  
- ¿A qué cliente irá? (GM Silao, GM SLP, BMW SLP)  
- ¿Cuándo entró y salió? (para calcular tiempos)  

### 🛠️ Configuración

#### **Paso 3.1: Crear el agente Truck**

1. En el panel **Projects**, click derecho en **Agent Types**  
2. Seleccionar **New Agent Type...**  
3. Nombre: `Truck`  
4. Click en **Finish**  

#### **Paso 3.2: Agregar atributos (variables)**

1. Con el agente `Truck` abierto, en la paleta superior buscar **Agent** (icono de estrella)  
2. Arrastrar **Variable** al canvas  
3. Repetir para crear estas variables:

| Nombre | Tipo | Valor inicial | ¿Para qué sirve? |
|--------|------|--------------|------------------|
| `proveedor` | String | `""` | Nombre del proveedor (Lear, Condumex, Magna) |
| `region` | String | `""` | Norte o Sur |
| `destinoOEM` | String | `""` | GM_SILAO, GM_SLP o BMW_SLP |
| `pallets` | int | `0` | Número de pallets que trae |
| `tEntradaSistema` | double | `0` | Hora en que entró |
| `tSalidaSistema` | double | `0` | Hora en que salió |

**Cómo crear cada variable:**
1. Arrastrar **Variable** al canvas de Truck  
2. En Properties:
   - **Name:** escribir el nombre (ej. `proveedor`)  
   - **Type:** seleccionar el tipo (String, int, double)  
   - **Initial value:** dejar como está  

> 💡 **No te preocupes por los valores iniciales, los asignaremos después**

### ⚠️ PROBLEMAS COMUNES

| Problema | Solución |
|----------|----------|
| No encuentro "Variable" | Buscar en paleta superior, sección Agent (icono estrella) |
| Me pide "initial value" | Para String usa `""`, para int/double usa `0` |
| Las variables no aparecen | Asegúrate de estar en el canvas de Truck, no de Main |

### ✅ Checklist

- [ ] Agente Truck creado  
- [ ] 6 variables agregadas correctamente  
- [ ] Todas las variables tienen el tipo correcto  

---

## 10. PASO 4 – CREAR LAS FUENTES DE CAMIONES

### 🎯 Objetivo

Configurar cómo y cuándo llegarán camiones al CEDIS desde cada proveedor.

### 🧠 Lógica

Tenemos 3 proveedores principales:
- **Lear** (región Norte): Envía camiones con 26 pallets  
- **Condumex** (región Sur): Envía camiones con 24 pallets  
- **Magna** (región Sur): Envía camiones con 28 pallets  

Usaremos **bloques Source** para generar camiones automáticamente.

### 🛠️ Configuración

#### **Paso 4.1: Abrir la paleta de Process Modeling**

1. Ir al agente **Main** (doble click en Projects → Main)  
2. En la paleta izquierda, buscar **Process Modeling Library**  
3. Si no la ves, ir a menú **View → Libraries → Process Modeling Library**  

#### **Paso 4.2: Crear Source para Lear**

1. Arrastrar un bloque **Source** al canvas de Main  
2. Click en el bloque → Properties:
   - **Name:** `SRC_LEAR_NORTE`  
   - **Agent type:** seleccionar `Truck`  
   - **Arrival mode:** `Rate`
   - **Arrival rate:** escribir `uniform(2, 4)`  

3. Buscar la sección **Actions → On exit** y escribir:
```java
agent.proveedor = "LEAR";
agent.region = "NORTE";
agent.pallets = 26;
agent.tEntradaSistema = time();
```

> 💡 **Explicación del código:**  
> - `agent` = el camión que acaba de ser creado  
> - `time()` = hora actual de la simulación  
> - `uniform(2, 4)` = entre 2 y 4 camiones por hora (aleatorio)  

#### **Paso 4.3: Crear Source para Condumex**

1. Arrastrar otro **Source**  
2. Configurar:
   - **Name:** `SRC_CONDUMEX_SUR`  
   - **Agent type:** `Truck`  
   - **Arrival rate:** `uniform(1, 3)`  
   - **On exit:**
```java
agent.proveedor = "CONDUMEX";
agent.region = "SUR";
agent.pallets = 24;
agent.tEntradaSistema = time();
```

#### **Paso 4.4: Crear Source para Magna**

1. Arrastrar otro **Source**  
2. Configurar:
   - **Name:** `SRC_MAGNA_SUR`  
   - **Agent type:** `Truck`  
   - **Arrival rate:** `uniform(1.5, 3.5)`  
   - **On exit:**
```java
agent.proveedor = "MAGNA";
agent.region = "SUR";
agent.pallets = 28;
agent.tEntradaSistema = time();
```

### 💡 CONSEJOS

- Coloca los 3 Sources uno debajo del otro en el lado izquierdo del canvas  
- Puedes ajustar las tasas de llegada después si quieres más o menos camiones  

### 📊 Tasas de llegada explicadas

| Proveedor | Rate | Significado |
|-----------|------|-------------|
| Lear | uniform(2,4) | Entre 2 y 4 camiones/hora |
| Condumex | uniform(1,3) | Entre 1 y 3 camiones/hora |
| Magna | uniform(1.5,3.5) | Entre 1.5 y 3.5 camiones/hora |

> 💡 **Esto da un total de ~150-250 camiones al día**

### ⚠️ PROBLEMAS COMUNES

| Problema | Solución |
|----------|----------|
| No encuentro "On exit" | Hacer scroll hacia abajo en Properties |
| Error en el código | Verifica las comillas `"` y los puntos y coma `;` |
| No aparece "Truck" en Agent type | Asegúrate de haber creado el agente Truck primero |

### ✅ Checklist

- [ ] 3 Sources creados y nombrados correctamente  
- [ ] Cada Source tiene su rate configurado  
- [ ] El código On exit funciona sin errores (no aparece línea roja)  

---

# PARTE 3: FLUJO DE ENTRADA Y ANDENES

---

## 11. PASO 5 – ENTRADA AL CEDIS Y GESTIÓN DE ANDENES

### 🎯 Objetivo

Simular que los 3 flujos de camiones entran al CEDIS, esperan si no hay andén disponible, descargan y liberan el andén.

### 🧠 Lógica

Secuencia de eventos:
1. Camiones de 3 proveedores → Se juntan en un punto de entrada  
2. Si no hay andén disponible → Esperan en cola  
3. Cuando hay andén → Lo ocupan  
4. Descargan (tarda tiempo) → Liberan el andén  

### 🛠️ Configuración

#### **Paso 5.1: Crear el ResourcePool de andenes**

1. En la paleta de Main, buscar **Agent → Resource Pool**  
2. Arrastrar al canvas (fuera del flowchart, arriba a la derecha)  
3. Configurar:
   - **Name:** `docks`  
   - **Type:** `Resource Units`  
   - **Capacity:** `24`  

> 💡 **Esto representa los 24 andenes físicos del CEDIS**

#### **Paso 5.2: Conectar los Sources a un punto común**

1. Arrastrar un bloque **Enter** al canvas (de Process Modeling Library)
2. Colocarlo a la derecha de los 3 Sources  
3. Configurar:
   - **Name:** `ENTER_CEDIS`  

4. **Conectar** los Sources al Enter:
   - Hacer click en el punto naranja del Source  
   - Arrastrar hacia el Enter  
   - Repetir para los 3 Sources  

**Diagrama esperado:**
```
SRC_LEAR_NORTE ──┐
SRC_CONDUMEX_SUR ├──> ENTER_CEDIS
SRC_MAGNA_SUR ───┘
```

#### **Paso 5.3: Crear la cola de espera**

1. Arrastrar un bloque **Queue**  
2. Colocarlo a la derecha del Enter  
3. Configurar:
   - **Name:** `Q_ANDEN`  
   - **Capacity:** `unlimited`  

4. Conectar: `ENTER_CEDIS` → `Q_ANDEN`

#### **Paso 5.4: Asignar el andén (Seize)**

1. Arrastrar un bloque **Seize**  
2. Colocarlo a la derecha de Queue  
3. Configurar:
   - **Name:** `SEIZE_ANDEN`  
   - **Resource sets:** Click en **Add** → elegir `docks` → Quantity: `1`  

4. Conectar: `Q_ANDEN` → `SEIZE_ANDEN`

> 💡 **Seize = "tomar" un recurso. El camión ocupa 1 andén**

#### **Paso 5.5: Simular la descarga (Delay)**

1. Arrastrar un bloque **Delay**  
2. Configurar:
   - **Name:** `UNLOAD`  
   - **Delay time:** `triangular(0.3, 0.6, 1.0)`  

3. Conectar: `SEIZE_ANDEN` → `UNLOAD`

> 💡 **triangular(min, moda, max) = tiempo variable entre 0.3 y 1 hora**

#### **Paso 5.6: Liberar el andén (Release)**

1. Arrastrar un bloque **Release**  
2. Configurar:
   - **Name:** `RELEASE_ANDEN`  
   - **Resource sets:** Click en **Add** → elegir `docks`  

3. Conectar: `UNLOAD` → `RELEASE_ANDEN`

### 📸 Flowchart esperado hasta aquí
```
SRC_LEAR ──┐
SRC_COND ──┼──> ENTER_CEDIS → Q_ANDEN → SEIZE_ANDEN → UNLOAD → RELEASE_ANDEN
SRC_MAGNA ─┘
```

### 💡 CONSEJOS

- Mantén todo alineado horizontalmente para que se vea ordenado  
- Puedes arrastrar los bloques para reorganizarlos  
- Usa Ctrl+Z si conectas algo mal  

### ⚠️ PROBLEMAS COMUNES

| Problema | Solución |
|----------|----------|
| No puedo conectar bloques | Asegúrate de arrastrar desde el punto naranja |
| Seize no encuentra "docks" | Primero crea el ResourcePool docks |
| Error "Cannot resolve symbol docks" | El ResourcePool debe estar en Main, no en Truck |

### ✅ Checklist

- [ ] ResourcePool `docks` creado con capacidad 24  
- [ ] Enter conecta los 3 Sources  
- [ ] Flowchart completo: Enter → Queue → Seize → Delay → Release  
- [ ] Todas las conexiones funcionan (no hay líneas rojas)  

---

## 12. PASO 6 – RUTEO HACIA RECEPCIÓN NORTE O SUR

### 🎯 Objetivo

Enviar cada camión a la zona de recepción correcta según su región de origen.

### 🧠 Lógica

Después de liberar el andén:
- Si `agent.region == "NORTE"` → va a Recepción Norte  
- Si `agent.region == "SUR"` → va a Recepción Sur  

Usaremos un bloque **SelectOutput** para decidir.

### 🛠️ Configuración

#### **Paso 6.1: Crear el bloque de decisión**

1. Arrastrar un bloque **SelectOutput**  
2. Configurar:
   - **Name:** `ROUTE_RECEPCION`  
   - **Type:** `Condition`  
   - **Condition:** seleccionar `By code`  

3. En el campo de código escribir:
```java
if (agent.region.equals("NORTE")) {
    return 0;  // Rama 0 = Recepción Norte
} else {
    return 1;  // Rama 1 = Recepción Sur
}
```

4. Conectar: `RELEASE_ANDEN` → `ROUTE_RECEPCION`

> 💡 **SelectOutput tiene 2 salidas: puerto 0 (arriba) y puerto 1 (abajo)**

#### **Paso 6.2: Crear los delays de recepción**

1. Arrastrar un **Delay** a la derecha, arriba:
   - **Name:** `DELAY_RECEP_NORTE`  
   - **Delay time:** `triangular(0.15, 0.25, 0.40)`  

2. Arrastrar otro **Delay** a la derecha, abajo:
   - **Name:** `DELAY_RECEP_SUR`  
   - **Delay time:** `triangular(0.15, 0.25, 0.40)`  

3. Conectar:
   - Rama 0 (puerto superior) de `ROUTE_RECEPCION` → `DELAY_RECEP_NORTE`  
   - Rama 1 (puerto inferior) de `ROUTE_RECEPCION` → `DELAY_RECEP_SUR`  

#### **Paso 6.3: Convergencia hacia Sorting**

Ahora las dos ramas deben juntarse para ir a Sorting.

1. Arrastrar un **Delay** más a la derecha (en el centro):
   - **Name:** `SORTING_PROCESS`  
   - **Delay time:** `triangular(0.2, 0.4, 0.8)`  

2. **Conectar ambos delays de recepción a SORTING:**
   - `DELAY_RECEP_NORTE` → `SORTING_PROCESS`  
   - `DELAY_RECEP_SUR` → `SORTING_PROCESS`  

> 💡 **AnyLogic permite que múltiples bloques se conecten a uno solo**

### 📸 Diagrama esperado
```
RELEASE_ANDEN → ROUTE_RECEPCION ─┬─(0)─> DELAY_RECEP_NORTE ─┐
                                 │                           ├─> SORTING_PROCESS
                                 └─(1)─> DELAY_RECEP_SUR ───┘
```

### 💡 CONSEJOS

- Organiza los bloques en forma de "Y" invertida para que se vea claro  
- El número (0) o (1) aparece cuando conectas cada rama  
- Si te equivocas, haz click en la flecha y presiona Delete  

### ⚠️ PROBLEMAS COMUNES

| Problema | Solución |
|----------|----------|
| Error: "equals not found" | Usa `agent.region.equals("NORTE")` no `==` |
| SelectOutput solo tiene 1 salida | Click en el bloque → Properties → Outputs: 2 |
| No sé cuál es la rama 0 | La rama superior = 0, la inferior = 1 |

### ✅ Checklist

- [ ] SelectOutput configurado con código correcto  
- [ ] 2 delays de recepción creados  
- [ ] Ambas ramas conectadas a SORTING_PROCESS  
- [ ] El flowchart se ve organizado  

---

# PARTE 4: CROSS-DOCKING, BUFFER Y KITTING

---

## 13. PASO 7 – DECISIÓN: CROSS-DOCKING, BUFFER O KITTING

### 🎯 Objetivo

Simular que el 65% de los pallets pasan directo a embarques (cross-docking), el 30% va a almacenamiento temporal (buffer) y el 5% requiere operaciones de valor agregado (kitting).

### 🧠 Lógica

Según el diseño de la Actividad 6:
- **65%** → Cross-docking (flujo directo)  
- **30%** → Buffer estratégico  
- **5%** → Kitting/Valor agregado  

Esto refleja la operación real del CEDIS.

### 🛠️ Configuración

#### **Paso 7.1: Crear la decisión de flujo**

1. Arrastrar un **SelectOutput**  
2. Configurar:
   - **Name:** `FLOW_DECISION`  
   - **Type:** `Condition`  
   - **Condition:** `By code`  

3. En el campo de código escribir:
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

4. Conectar: `SORTING_PROCESS` → `FLOW_DECISION`

> 💡 **uniform(0,1) genera un número aleatorio entre 0 y 1**  
> - Si es menor a 0.65 (65%) → Cross-docking  
> - Si es 0.65-0.95 (30%) → Buffer  
> - Si es mayor a 0.95 (5%) → Kitting  

#### **Paso 7.2: Aumentar el número de salidas del SelectOutput**

1. Click en `FLOW_DECISION`  
2. En Properties buscar **Outputs**  
3. Camb
<!--stackedit_data:
eyJoaXN0b3J5IjpbNTg4MTYxNDQxLDE4NzI5MzM5NF19
-->