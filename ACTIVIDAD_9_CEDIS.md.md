# 🟦 MODELADO DEL CEDIS EN ANYLOGIC
<img src="https://upload.wikimedia.org/wikipedia/commons/4/4c/Logo_AnyLogic.png" alt="AnyLogic Logo" width="200" style="float: right; margin-left: 20px;">

💬 Este modelo representa tu propuesta real del CEDIS San Bartolo. Cada bloque que configures es un paso hacia la eficiencia que justificaste en la Actividad 6 (reducción del 55% en viajes, mejora del 40% en utilización). ¡Hazlo como si fuera tu proyecto profesional! 🚀

💡 **Tip Global:** Usa colores y nombres claros en el layout para que tu simulación sea fácil de interpretar. Esto refleja el diseño conceptual que planteaste (áreas de sorting, buffer y kitting).

⚠️ **Error Común:** No olvides configurar las unidades (horas y metros) antes de avanzar. Si fallas aquí, todo el modelo será inconsistente.

---

# 🟦 ACTIVIDAD 9 – MODELADO DEL CEDIS AUTOMOTRIZ SAN BARTOLO EN ANYLOGIC

### Curso: Logística y Cadena de Valor
### Sistemas de Almacenamiento y CEDIS
### Versión: 2025 - EDICIÓN  PARA PRINCIPIANTES
---

## 📌 ANTES DE EMPEZAR - LEE ESTO PRIMERO

### ¿Qué voy a hacer en esta actividad?
Vas a construir un **modelo de simulación** del CEDIS (Centro de Distribución) San Bartolo en el software AnyLogic. Este CEDIS distribuye piezas automotrices a tres plantas ensambladoras: GM Silao, GM San Luis Potosí y BMW San Luis Potosí.

### ¿Qué es un modelo de simulación?
Es como un **videojuego de tu CEDIS** donde puedes ver cómo entran camiones, se descargan, los materiales circulan por el almacén y salen hacia los clientes. Te permite probar diferentes configuraciones sin construir el almacén real.

### ¿Qué necesito saber antes?
✅ **No necesitas ser experto en programación**  
✅ Este documento te guía paso a paso  
✅ Cada sección tiene: 🎯 Objetivo | 🧠 Explicación | 🛠️ Qué hacer | 💡 Consejos    💡 *Tip:* Este paso conecta con tu diseño conceptual de la Actividad 6.
✅ Si te atoras, busca las secciones **"⚠️ PROBLEMAS COMUNES"**

### Tiempo estimado
- **Primera vez:** 4-6 horas
- **Con experiencia:** 2-3 horas

---

## 1. DATOS DE IDENTIFICACIÓN

| Campo | Información a completar |
|-------|------------------------|
| **Nombre del estudiante** | |
| **Matrícula** | |
| **Carrera** | |
| **Grupo** | |
| **Fecha de entrega** | |
| **Nombre del CEDIS modelado** | CEDIS AUTOMOTRIZ SAN BARTOLO |

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

| Parámetro | Valor | Notas |
|-----------|-------|-------|
| **Capacidad** | 22,000 pallets | Capacidad máxima de almacenamiento |
| **Entrada diaria** | ~7,100 pallets | Flujo promedio de entrada |
| **Salida diaria** | ~7,700 pallets | Flujo promedio de salida |
| **Andenes** | 24 total | 8 recepción + 16 embarque |
| **Cross-docking** | 65% | Materiales que pasan directo sin almacenarse |

---

## 3. OBJETIVO GENERAL
> **Construir y documentar un modelo funcional del CEDIS en AnyLogic** que simule camiones entrando, procesos de descarga, clasificación, almacenamiento y despacho hacia tres clientes automotrices, con recursos, tiempos y KPIs medibles.

---

## 4. OBJETIVOS ESPECÍFICOS

# 4. OBJETIVOS ESPECÍFICOS

| # | Objetivo | Estado | Prioridad | Observaciones |
|---|----------|--------|-----------|---------------|
| 1 | Configurar proyecto AnyLogic con unidades correctas | | 🔴 ALTA | |
| 2 | Crear agentes (camiones) con información de carga y destino | | 🔴 ALTA | |
| 3 | Dibujar layout básico del CEDIS | | 🟡 MEDIA | |
| 4 | Construir diagrama de flujo con bloques Process Modeling | | 🔴 ALTA | |
| 5 | Gestionar recursos (andenes, montacargas) | | 🔴 ALTA | |
| 6 | Programar decisiones de ruteo | | 🟡 MEDIA | |
| 7 | Calcular indicadores (KPIs) | | 🟢 BAJA | |
| 8 | Publicar modelo en AnyLogic Cloud | | 🟢 BAJA | |
| 9 | Crear dashboard de monitoreo | | 🟡 MEDIA | |

**💡 Tip:** Este paso conecta con tu diseño conceptual de la Actividad 6.

---

## 5. REQUISITOS PREVIOS

### 📦 Software Necesario
- **AnyLogic** instalado (versión PLE o superior)  
👉 Descarga gratuita: [www.anylogic.com](https://www.anylogic.com/downloads/)

### 🧠 Conocimientos Previos
- Haber completado Actividades 6 y 7
- Haber visto el video introductorio de AnyLogic (proporcionado por el profesor)

### 📎 Materiales de Referencia
- Layout del CEDIS San Bartolo (imagen PNG/JPG proporcionada)
- Este documento como guía principal
- Datos de la Actividad 6 para parámetros

### 🖼️ Layout de Referencia
![Layout CEDIS San Bartolo](https://raw.githubusercontent.com/fnjimenez/Curso_Logistica_CV/main/layoutt.png)

---

## 6. ¿CÓMO USAR ESTE DOCUMENTO?

### 📖 Estructura de Cada Paso
Cada sección sigue este formato estándar:

```
🎯 OBJETIVO → Qué vas a lograr en este paso
🧠 LÓGICA → Por qué lo haces así y cómo funciona
🛠️ CONFIGURACIÓN → Instrucciones técnicas paso a paso
💻 CÓDIGO → Qué escribir exactamente en AnyLogic
💡 CONSEJOS → Trucos y mejores prácticas
⚠️ PROBLEMAS COMUNES → Soluciones rápidas a errores frecuentes
```

### ✅ Sistema de Checklist  🧐 *Verifica:* Si todo coincide con los parámetros del CEDIS San Bartolo (22,000 pallets, 24 andenes).
Al final de cada sección encontrarás:

- [ ] **Completado y funciona** - Todo correcto
- [ ] **Completado pero tengo dudas** - Necesitas revisión
- [ ] **No pude completarlo** - Busca ayuda en problemas comunes

### 🎯 Flujo Recomendado
1. **Lee completamente** cada paso antes de ejecutar
2. **Sigue el orden numérico** estrictamente
3. **Ejecuta y verifica** después de cada paso importante
4. **Documenta problemas** para referencia futura

---

# PARTE 1: CONFIGURACIÓN INICIAL

---

## 1. PASO 1 CREAR EL PROYECTO Y CONFIGURAR UNIDADES

### 🎯 Objetivo  💡 *Tip:* Este paso conecta con tu diseño conceptual de la Actividad 6.
Crear un proyecto nuevo en AnyLogic con las unidades correctas (horas y metros) para el modelo del CEDIS.

### 🧠 Lógica  📌 *Recuerda:* Justificaste esta lógica en tu ensayo (reducción de viajes, eficiencia).
Trabajaremos en un solo agente llamado `Main` que contendrá todos los elementos:
- El dibujo del layout del CEDIS
- El diagrama de flujo completo de camiones  
- Los recursos compartidos (andenes, montacargas)
- Los indicadores de desempeño (KPIs)

### 🛠️ Configuración Paso a Paso  ✅ *Consejo:* Sigue el orden exacto para evitar errores en AnyLogic.

#### **Paso 1.1: Crear el Proyecto**
1. Abrir AnyLogic desde el escritorio
2. Menú **File → New Model...**
3. En el cuadro de diálogo:
   - **Model name:** `CEDIS_SanBartolo_TuApellido` (ej: `CEDIS_SanBartolo_Garcia`)
   - **Location:** Seleccionar carpeta de tu preferencia
4. Click en **Finish**

#### **Paso 1.2: Configurar Unidades de Tiempo y Espacio**
1. En el panel izquierdo **Projects**, hacer click en el **nombre del modelo** (no en Main)
2. En la parte inferior, buscar la pestaña **Properties**
3. Expandir la sección **Environment**
4. Configurar valores:
   - **Time units:** seleccionar `hour` (hora)
   - **Length units:** seleccionar `meter` (metro)

#### **Paso 1.3: Verificar que Main está Activo**
1. En panel izquierdo, hacer doble click en **Main**
2. Debe abrirse una ventana blanca de trabajo (canvas)
3. Verificar que en la parte superior dice: **Main (Agent Type)**

### ⚠️ PROBLEMAS COMUNES Y SOLUCIONES

| Problema | Síntoma | Solución |
|----------|---------|----------|
| **No encuentro "Environment"** | No aparece la sección en Properties | Click en el nombre del PROYECTO, no en Main |
| **No aparece Main** | El agente Main no está visible | Menú Projects → click derecho → New Agent Type → Nombre: Main |
| **Las unidades no se guardan** | Al cerrar se pierde la configuración | Cerrar y reabrir el proyecto, verificar en Properties |
| **Error al crear proyecto** | AnyLogic se cierra o da error | Verificar espacio en disco y permisos de la carpeta |

### 💡 CONSEJOS PRÁCTICOS
- **Nombra bien el proyecto** desde el inicio para evitar confusiones
- **Las unidades son críticas** - horas para tiempos, metros para distancias
- **Guarda frecuentemente** con Ctrl+S durante el proceso
- **Mantén Main abierto** - es tu área de trabajo principal

### ✅ Checklist de Verificación  🧐 *Verifica:* Si todo coincide con los parámetros del CEDIS San Bartolo (22,000 pallets, 24 andenes).
- [ ] Proyecto creado con nombre personalizado correcto
- [ ] Unidades configuradas en horas y metros en Environment  
- [ ] Main está abierto y visible en el canvas
- [ ] Puedo ver la ventana de Properties en la parte inferior

---

## 2. PASO 2 DIBUJAR EL LAYOUT DEL CEDIS

### 🎯 Objetivo  💡 *Tip:* Este paso conecta con tu diseño conceptual de la Actividad 6.
Crear la representación visual del CEDIS usando el layout proporcionado como referencia, definiendo claramente todas las zonas operativas.

### 🧠 Lógica  📌 *Recuerda:* Justificaste esta lógica en tu ensayo (reducción de viajes, eficiencia).
Un buen layout visual ayuda a:
- Entender el flujo de materiales
- Ubicar correctamente los procesos
- Comunicar el diseño a otras personas
- Debuggear problemas en la simulación

### 🛠️ Configuración Paso a Paso  ✅ *Consejo:* Sigue el orden exacto para evitar errores en AnyLogic.

#### **Paso 2.1: Insertar la Imagen de Fondo (ALTAMENTE RECOMENDADO)**
1. Descargar la imagen `layoutt.png` desde GitHub
2. En AnyLogic, con Main abierto, ir a menú **Insert → Image...**
3. Navegar y seleccionar la imagen, click en **Open**
4. Click en el canvas para colocarla como referencia
5. Ajustar tamaño arrastrando desde las esquinas

**Para fijar la imagen y que no estorbe:**
6. Click derecho sobre la imagen → **Order → Send to Back**  
7. Click derecho → **Lock** (para que no se mueva accidentalmente)

#### **Paso 2.2: Dibujar las Zonas Principales con Rectángulos**
En la paleta izquierda, buscar **Presentation → Space Markup → Rectangular Node**:

| Zona | Color Sugerido | Propósito |
|------|----------------|-----------|
| Recepción Norte | `#FFF2CC` (Amarillo claro) | Entrada camiones región norte |
| Recepción Sur | `#FFF2CC` (Amarillo claro) | Entrada camiones región sur |
| Sorting | `#D5E8D4` (Verde claro) | Clasificación de materiales |
| Buffer Estratégico | `#F8CECC` (Rojo claro) | Almacenamiento temporal |
| Kitting | `#DAE8FC` (Azul claro) | Valor agregado |
| Embarques GM Silao | `#E1D5E7` (Morado claro) | Salida GM Silao |
| Embarques GM SLP | `#E1D5E7` (Morado claro) | Salida GM San Luis |
| Embarques BMW SLP | `#E1D5E7` (Morado claro) | Salida BMW |

**Para cada rectángulo:**
1. Arrastrar **Rectangular Node** al canvas
2. Dibujar sobre la zona correspondiente en la imagen
3. Click derecho → **Properties → Fill color** → Elegir color
4. **Line color:** Gris oscuro para mejor contorno

#### **Paso 2.3: Agregar Etiquetas de Texto Identificadoras**
1. En paleta izquierda: **Presentation → Text**
2. Arrastrar al canvas y colocar sobre cada zona
3. Configurar texto según esta tabla:

| Texto | Tamaño Fuente | Color | Ubicación |
|-------|---------------|-------|-----------|
| "RECEPCIÓN NORTE" | 16 | Negro | Sobre recepción norte |
| "RECEPCIÓN SUR" | 16 | Negro | Sobre recepción sur |
| "SORTING" | 14 | Negro | Sobre área sorting |
| "BUFFER ESTRATÉGICO" | 12 | Negro | Sobre buffer |
| "KITTING" | 14 | Negro | Sobre kitting |
| "EMBARQUES GM SILAO" | 12 | Negro | Sobre embarques GM Silao |
| "EMBARQUES GM SLP" | 12 | Negro | Sobre embarques GM SLP |
| "EMBARQUES BMW SLP" | 12 | Negro | Sobre embarques BMW |

### ⚠️ PROBLEMAS COMUNES Y SOLUCIONES

| Problema | Síntoma | Solución |
|----------|---------|----------|
| **La imagen tapa todo** | No se ven los rectángulos | Click derecho → Order → Send to Back |
| **No puedo mover la imagen** | La imagen está bloqueada | Click derecho → Unlock temporalmente |
| **Los rectángulos no se ven** | Sólo se ve el borde | Properties → Fill color → Elegir color sólido |
| **El texto se sale** | Las etiquetas no caben | Reducir tamaño de fuente o usar abreviaciones |

### 💡 CONSEJOS DE DISEÑO
- **Usa colores consistentes** - mismo color para funciones similares
- **Mantén proporciones** - no necesita ser exacto, pero sí reconocible
- **Deja espacio para el flowchart** - el layout va a la izquierda, flowchart a la derecha
- **Grupa elementos relacionados** - recepciones juntas, embarques juntos
- **Usa la función Snap** - ayuda a alinear elementos perfectamente

### 🎨 Esquema de Colores Recomendado
```
Recepción:    #FFF2CC  (Amarillo - Entrada)
Procesamiento: #D5E8D4  (Verde - Transformación)
Almacenamiento: #F8CECC  (Rojo - Buffer)
Salida:       #E1D5E7  (Morado - Embarques)
```

### ✅ Checklist de Verificación  🧐 *Verifica:* Si todo coincide con los parámetros del CEDIS San Bartolo (22,000 pallets, 24 andenes).
- [ ] Imagen de fondo insertada y bloqueada en posición
- [ ] 8 zonas dibujadas con rectángulos de colores diferenciados
- [ ] Todas las etiquetas de texto agregadas y legibles
- [ ] Colores consistentes según la función de cada zona
- [ ] Layout organizado y fácil de entender
- [ ] Espacio reservado para el diagrama de flujo

---

# PARTE 2: CREACIÓN DE AGENTES Y FUENTES

---

## 3. PASO 3 CREAR EL AGENTE `Truck`

### 🎯 Objetivo  💡 *Tip:* Este paso conecta con tu diseño conceptual de la Actividad 6.
Definir la "ficha técnica" de los camiones que entrarán al CEDIS con todos sus atributos necesarios.

### 🧠 Lógica  📌 *Recuerda:* Justificaste esta lógica en tu ensayo (reducción de viajes, eficiencia).
Cada camión es un **agente** que fluye por el sistema y necesita almacenar información específica:
- **Origen:** Proveedor y región de procedencia
- **Carga:** Cantidad de pallets que transporta
- **Destino:** Cliente final al que va dirigido
- **Tiempos:** Registro de entrada y salida para métricas

### 🛠️ Configuración Paso a Paso  ✅ *Consejo:* Sigue el orden exacto para evitar errores en AnyLogic.

#### **Paso 3.1: Crear el Agente Truck**
1. En panel **Projects**, click derecho en **Agent Types**
2. Seleccionar **New Agent Type...**
3. En el diálogo:
   - **Name:** `Truck`
   - **Default population:** Dejar en blanco
4. Click en **Finish**

#### **Paso 3.2: Agregar Atributos (Variables) al Agente**

| Variable | Tipo | Valor Inicial | Descripción | Uso en el Modelo |
|----------|------|---------------|-------------|------------------|
| `proveedor` | String | `""` | Nombre del proveedor | Decisiones de ruteo y estadísticas |
| `region` | String | `""` | Norte o Sur | Determinar recepción destino |
| `destinoOEM` | String | `""` | GM_SILAO, GM_SLP, BMW_SLP | Asignación final de embarque |
| `pallets` | int | `0` | Cantidad de pallets | Cálculo de throughput |
| `tEntradaSistema` | double | `0.0` | Hora de entrada | Cálculo de tiempo de ciclo |
| `tSalidaSistema` | double | `0.0` | Hora de salida | Cálculo de tiempo de ciclo |

**Procedimiento para cada variable:**
1. En el canvas de **Truck**, paleta superior: **Agent → Variable**
2. Arrastrar al canvas (puedes organizarlas verticalmente)
3. En **Properties** configurar:
   - **Name:** Nombre exacto de la variable
   - **Type:** Seleccionar tipo correcto
   - **Initial value:** Valor por defecto

### ⚠️ PROBLEMAS COMUNES Y SOLUCIONES

| Problema | Síntoma | Solución |
|----------|---------|----------|
| **No encuentro "Variable"** | No aparece en paleta | Buscar en pestaña Agent (icono de estrella) |
| **Error de tipo de dato** | No acepta el valor inicial | String: `""`, int: `0`, double: `0.0` |
| **Variables no visibles** | No aparecen en el agente | Verificar que estás en canvas de Truck, no Main |
| **Error de nombre** | Caracteres inválidos | Usar solo letras, números, sin espacios |

### 💡 CONSEJOS DE BUENAS PRÁCTICAS
- **Nombres descriptivos:** Usar `tEntradaSistema` no `tiempo1`
- **Organización visual:** Agrupar variables relacionadas
- **Comentarios:** Agregar notas si es necesario
- **Tipos correctos:** String para texto, int para enteros, double para decimales

### ✅ Checklist de Verificación  🧐 *Verifica:* Si todo coincide con los parámetros del CEDIS San Bartolo (22,000 pallets, 24 andenes).
- [ ] Agente Truck creado en Agent Types
- [ ] 6 variables agregadas con nombres exactos
- [ ] Todos los tipos de datos configurados correctamente
- [ ] Valores iniciales apropiados para cada tipo
- [ ] Variables organizadas y visibles en el canvas

---

## 4. PASO 4 CREAR LAS FUENTES DE CAMIONES

### 🎯 Objetivo  💡 *Tip:* Este paso conecta con tu diseño conceptual de la Actividad 6.
Configurar la generación automática de camiones desde los tres proveedores principales con sus características específicas.

### 🧠 Lógica  📌 *Recuerda:* Justificaste esta lógica en tu ensayo (reducción de viajes, eficiencia).
Cada proveedor tiene patrones únicos:
- **Frecuencias diferentes** de llegada
- **Regiones específicas** de origen
- **Capacidades distintas** de carga
- **Horarios preferentes** de entrega

### 🛠️ Configuración Paso a Paso  ✅ *Consejo:* Sigue el orden exacto para evitar errores en AnyLogic.

#### **Paso 4.1: Preparar el Ambiente de Trabajo**
1. Regresar al agente **Main** (doble click en Projects)
2. En paleta izquierda, verificar que **Process Modeling Library** está visible
3. Si no está: **View → Palettes → Process Modeling Library**

#### **Paso 4.2: Configuración de Sources por Proveedor**  ✅ *Consejo:* Sigue el orden exacto para evitar errores en AnyLogic.

| Proveedor | Source Name | Arrival Rate | Pallets | Región | Horario Pico |
|-----------|-------------|--------------|---------|---------|-------------|
| **Lear** | `SRC_LEAR_NORTE` | `uniform(2, 4)` | 26 | NORTE | Mañana |
| **Condumex** | `SRC_CONDUMEX_SUR` | `uniform(1, 3)` | 24 | SUR | Tarde |
| **Magna** | `SRC_MAGNA_SUR` | `uniform(1.5, 3.5)` | 28 | SUR | Mixto |

**Procedimiento para cada Source:**

**Para Lear (Norte):**
1. Arrastrar **Source** desde Process Modeling Library
2. Configurar Properties:
   - **Name:** `SRC_LEAR_NORTE`
   - **Agent type:** `Truck` (debe aparecer en la lista)
   - **Arrival rate:** `uniform(2, 4)`
3. En **On exit (action)** escribir:

```java
// Configurar camiones Lear - Región Norte
agent.proveedor = "LEAR";
agent.region = "NORTE";
agent.pallets = 26;
agent.tEntradaSistema = time(); // Registrar hora de entrada
```

**Para Condumex (Sur):**
1. Arrastrar otro **Source**
2. Configurar Properties:
   - **Name:** `SRC_CONDUMEX_SUR`
   - **Agent type:** `Truck`
   - **Arrival rate:** `uniform(1, 3)`
3. En **On exit** escribir:

```java
// Configurar camiones Condumex - Región Sur
agent.proveedor = "CONDUMEX";
agent.region = "SUR";
agent.pallets = 24;
agent.tEntradaSistema = time();
```

**Para Magna (Sur):**
1. Arrastrar tercer **Source**
2. Configurar Properties:
   - **Name:** `SRC_MAGNA_SUR`
   - **Agent type:** `Truck`
   - **Arrival rate:** `uniform(1.5, 3.5)`
3. En **On exit** escribir:

```java
// Configurar camiones Magna - Región Sur
agent.proveedor = "MAGNA";
agent.region = "SUR";
agent.pallets = 28;
agent.tEntradaSistema = time();
```

### ⚠️ PROBLEMAS COMUNES Y SOLUCIONES

| Problema | Síntoma | Solución |
|----------|---------|----------|
| **No aparece "Truck"** | No hay opción en Agent type | Verificar que el agente Truck está creado |
| **Error en código On exit** | Línea roja subrayada | Revisar puntos y coma, comillas, nombres exactos |
| **No encuentro "On exit"** | No veo la sección | Scroll hacia abajo en Properties, buscar "Action" |
| **Uniform no funciona** | Error en distribución | Verificar sintaxis: `uniform(min, max)` |

### 💡 CONSEJOS DE CONFIGURACIÓN
- **Posicionamiento:** Colocar Sources en lado izquierdo del canvas, uno bajo otro
- **Nomenclatura:** Usar prefijos `SRC_` para identificar fácilmente
- **Tasas realistas:** `uniform(2, 4)` = entre 2-4 camiones/hora
- **Verificación inmediata:** Ejecutar modelo para ver si generan camiones

### 📊 Explicación de Distribuciones
- **`uniform(2, 4)`:** Valores entre 2-4 con igual probabilidad
- **Resultado:** ~3 camiones/hora en promedio
- **Cálculo diario:** 3 cam/h × 24h × 26 pallets = ~1,872 pallets/día

### ✅ Checklist de Verificación  🧐 *Verifica:* Si todo coincide con los parámetros del CEDIS San Bartolo (22,000 pallets, 24 andenes).
- [ ] 3 Sources creados con nombres descriptivos
- [ ] Cada Source configurado con Agent type: Truck
- [ ] Arrival rates específicos para cada proveedor
- [ ] Código On exit correcto en cada Source
- [ ] No hay errores (líneas rojas) en el código
- [ ] Sources posicionados ordenadamente en canvas

---

# PARTE 3: FLUJO DE ENTRADA Y ANDENES

---

## 5. PASO 5 ENTRADA AL CEDIS Y GESTIÓN DE ANDENES

### 🎯 Objetivo  💡 *Tip:* Este paso conecta con tu diseño conceptual de la Actividad 6.
Implementar el sistema de recepción donde camiones esperan, ocupan andenes, descargan y liberan recursos.

### 🧠 Lógica  📌 *Recuerda:* Justificaste esta lógica en tu ensayo (reducción de viajes, eficiencia).
Los andenes son recursos limitados que deben gestionarse eficientemente:
- **Cola de espera** cuando no hay andenes disponibles
- **Seize (tomar)** andén cuando se libera uno
- **Delay (proceso)** de descarga con tiempo variable
- **Release (liberar)** andén para siguiente camión

### 🛠️ Configuración Paso a Paso  ✅ *Consejo:* Sigue el orden exacto para evitar errores en AnyLogic.

#### **Paso 5.1: Crear ResourcePool de Andenes**
1. En agente **Main**, paleta: **Agent → Resource Pool**
2. Arrastrar al canvas (colocar en área superior derecha)
3. Configurar Properties:
   - **Name:** `docks`
   - **Type:** `Resource Units`
   - **Capacity:** `24`
   - **Show name:** Activado

#### **Paso 5.2: Construir Flowchart de Entrada**

**Bloque 1: Enter (Punto de Entrada Consolidado)**
1. Arrastrar **Enter** desde Process Modeling Library
2. Configurar:
   - **Name:** `ENTER_CEDIS`
3. **Conectar los 3 Sources al Enter:**
   - Click en punto naranja de cada Source
   - Arrastrar línea hasta el Enter
   - Repetir para los 3 Sources

**Bloque 2: Queue (Cola de Espera)**
1. Arrastrar **Queue** a la derecha del Enter
2. Configurar:
   - **Name:** `Q_ANDEN`
   - **Capacity:** `unlimited`
   - **Show name:** Activado

**Bloque 3: Seize (Tomar Andén)**
1. Arrastrar **Seize** a la derecha de Queue
2. Configurar:
   - **Name:** `SEIZE_ANDEN`
   - **Resource sets:** Click **Add**
     - **Resource:** `docks`
     - **Quantity:** `1`

**Bloque 4: Delay (Proceso de Descarga)**
1. Arrastrar **Delay** a la derecha de Seize
2. Configurar:
   - **Name:** `UNLOAD`
   - **Delay time:** `triangular(0.3, 0.6, 1.0)`

**Bloque 5: Release (Liberar Andén)**
1. Arrastrar **Release** a la derecha de Delay
2. Configurar:
   - **Name:** `RELEASE_ANDEN`
   - **Resource sets:** Click **Add** → `docks`

#### **Paso 5.3: Conectar Todo el Flowchart**
```
SRC_LEAR_NORTE ──┐
SRC_CONDUMEX_SUR ┼──> ENTER_CEDIS → Q_ANDEN → SEIZE_ANDEN → UNLOAD → RELEASE_ANDEN
SRC_MAGNA_SUR ───┘
```

**Conexiones específicas:**
- Cada Source → Enter (desde punto naranja)
- Enter → Queue
- Queue → Seize
- Seize → Delay
- Delay → Release

### ⚠️ PROBLEMAS COMUNES Y SOLUCIONES

| Problema | Síntoma | Solución |
|----------|---------|----------|
| **No puedo conectar** | Línea no se crea | Arrastrar desde punto naranja, no del bloque |
| **Seize no encuentra docks** | Error "cannot resolve" | Verificar que ResourcePool está en Main |
| **Conexión incorrecta** | Línea roja punteada | Rehacer conexión, verificar dirección |
| **Capacity agotada** | Cola infinita | Revisar Release está conectado |

### 💡 CONSEJOS DE FLOWCHART
- **Alinear horizontalmente** para mejor visualización
- **Espaciar uniformemente** entre bloques
- **Usar nombres descriptivos** en todos los bloques
- **Agrupar lógicamente** procesos relacionados
- **Dejar espacio** para expansiones futuras

### ⏱️ Tiempos de Proceso Explicados
- **`triangular(0.3, 0.6, 1.0)`:** 
  - Mínimo: 0.3 horas (18 minutos)
  - Más probable: 0.6 horas (36 minutos)
  - Máximo: 1.0 hora (60 minutos)
- **Justificación:** Depende de tipo de carga, personal disponible, etc.

### ✅ Checklist de Verificación  🧐 *Verifica:* Si todo coincide con los parámetros del CEDIS San Bartolo (22,000 pallets, 24 andenes).
- [ ] ResourcePool `docks` creado con capacidad 24
- [ ] Enter conecta los 3 Sources correctamente
- [ ] Queue con capacidad unlimited
- [ ] Seize configurado con resource `docks`, quantity 1
- [ ] Delay con distribución triangular de tiempos
- [ ] Release configurado con resource `docks`
- [ ] Todas las conexiones en secuencia correcta
- [ ] No hay líneas rojas de error

---

## 6. PASO 6 RUTEO HACIA RECEPCIÓN NORTE O SUR

### 🎯 Objetivo  💡 *Tip:* Este paso conecta con tu diseño conceptual de la Actividad 6.
Implementar la decisión que dirige cada camión a la zona de recepción correcta según su región de origen.

### 🧠 Lógica  📌 *Recuerda:* Justificaste esta lógica en tu ensayo (reducción de viajes, eficiencia).
La separación por regiones permite:
- **Optimizar flujos** internos
- **Balancear cargas** de trabajo
- **Manejar características** específicas por región
- **Preparar para procesos** diferenciados

### 🛠️ Configuración Paso a Paso  ✅ *Consejo:* Sigue el orden exacto para evitar errores en AnyLogic.

#### **Paso 6.1: Crear Bloque de Decisión**
1. Arrastrar **SelectOutput** desde Process Modeling Library
2. Colocar a la derecha de `RELEASE_ANDEN`
3. Configurar Properties:
   - **Name:** `ROUTE_RECEPCION`
   - **Type:** `Condition`
   - **Condition:** `By code`
   - **Outputs:** `2`

#### **Paso 6.2: Programar la Lógica de Decisión**  📌 *Recuerda:* Justificaste esta lógica en tu ensayo (reducción de viajes, eficiencia).
### **Paso 6.2: Configurar Tiempos de Recepción**

**Agregar estos delays después del SelectOutput ROUTE_RECEPCION:**

| Bloque | Nombre | Delay Time | Descripción |
|--------|--------|------------|-------------|
| **Delay** | `DELAY_RECEP_NORTE` | `triangular(0.15, 0.25, 0.40)` | Procesamiento recepción norte |
| **Delay** | `DELAY_RECEP_SUR` | `triangular(0.15, 0.25, 0.40)` | Procesamiento recepción sur |
| **Delay** | `SORTING_PROCESS` | `triangular(0.2, 0.4, 0.8)` | Clasificación central |

**Conexiones:**
```
RELEASE_ANDEN → ROUTE_RECEPCION ─┬─(0)─> DELAY_RECEP_NORTE ─┐
                                 │                           ├─> SORTING_PROCESS
                                 └─(1)─> DELAY_RECEP_SUR ───┘
```
En el campo de código del SelectOutput:

```java
// Decidir ruta según región del camión
if (agent.region.equals("NORTE")) {
    return 0;  // Rama 0: Recepción Norte
} else {
    return 1;  // Rama 1: Recepción Sur
}
```

#### **Paso 6.3: Crear Delays de Procesamiento por Recepción**

**Para Recepción Norte:**
1. Arrastrar **Delay** arriba a la derecha del SelectOutput
2. Configurar:
   - **Name:** `DELAY_RECEP_NORTE`
   - **Delay time:** `triangular(0.15, 0.25, 0.40)`

**Para Recepción Sur:**
1. Arrastrar **Delay** abajo a la derecha del SelectOutput
2. Configurar:
   - **Name:** `DELAY_RECEP_SUR`
   - **Delay time:** `triangular(0.15, 0.25, 0.40)`

**Proceso de Sorting (Común):**
1. Arrastrar **Delay** al centro-derecha
2. Configurar:
   - **Name:** `SORTING_PROCESS`
   - **Delay time:** `triangular(0.2, 0.4, 0.8)`

#### **Paso 6.4: Conectar las Rutas**
```
RELEASE_ANDEN → ROUTE_RECEPCION ─┬─(0)─> DELAY_RECEP_NORTE ─┐
                                 │                           ├─> SORTING_PROCESS
                                 └─(1)─> DELAY_RECEP_SUR ───┘
```

**Conexiones específicas:**
- `RELEASE_ANDEN` → `ROUTE_RECEPCION`
- Rama 0 (superior) → `DELAY_RECEP_NORTE`
- Rama 1 (inferior) → `DELAY_RECEP_SUR`
- Ambos delays → `SORTING_PROCESS`

### ⚠️ PROBLEMAS COMUNES Y SOLUCIONES

| Problema | Síntoma | Solución |
|----------|---------|----------|
| **Error "equals"** | No reconoce el método | Usar `agent.region.equals("NORTE")` no `==` |
| **Solo 1 salida** | No veo segunda rama | Properties → Outputs: cambiar a `2` |
| **Rama incorrecta** | Camiones van a zona equivocada | Verificar return 0 y return 1 |
| **No se conectan** | Líneas no permitidas | AnyLogic permite múltiples entradas a un bloque |

### 💡 CONSEJOS DE RUTEO
- **Testear decisiones:** Ejecutar y verificar que camiones van a zonas correctas
- **Balance visual:** Organizar ramas simétricamente
- **Tiempos realistas:** Recepción más rápida que descarga
- **Preparar para expansión:** Dejar espacio para más zonas si es necesario

### ⏱️ Tiempos de Recepción
- **Recepción:** `triangular(0.15, 0.25, 0.40)` = 9-24 minutos
- **Sorting:** `triangular(0.2, 0.4, 0.8)` = 12-48 minutos
- **Diferenciación:** Tiempos similares entre Norte/Sur para simplicidad

### ✅ Checklist de Verificación  🧐 *Verifica:* Si todo coincide con los parámetros del CEDIS San Bartolo (22,000 pallets, 24 andenes).
- [ ] SelectOutput configurado con 2 salidas
- [ ] Código de decisión funciona sin errores
- [ ] 2 delays de recepción creados (Norte/Sur)
- [ ] Delay de sorting común creado
- [ ] Conexiones correctas desde SelectOutput
- [ ] Ambas ramas conectadas a Sorting
- [ ] Tiempos configurados apropiadamente

---


# 🟦 ACTIVIDAD 9 – MODELADO DEL CEDIS AUTOMOTRIZ SAN BARTOLO EN ANYLOGIC

## 7. PASO 7 DECISIÓN: CROSS-DOCKING O BUFFER ESTRATÉGICO

### 🎯 Objetivo  💡 *Tip:* Este paso conecta con tu diseño conceptual de la Actividad 6.
Implementar la lógica que determina si los materiales pasan directo a embarque (cross-docking) o requieren almacenamiento temporal (buffer).

### 🧠 Lógica  📌 *Recuerda:* Justificaste esta lógica en tu ensayo (reducción de viajes, eficiencia).
Según datos reales de CEDIS automotrices:
- **65% Cross-docking:** Máxima eficiencia, costo mínimo
- **30% Buffer:** Flexibilidad operativa, manejo de picos  
- **5% Kitting:** Valor agregado, servicios especiales

### 🛠️ Configuración Paso a Paso  ✅ *Consejo:* Sigue el orden exacto para evitar errores en AnyLogic.

#### **Paso 7.1: Crear Punto de Decisión de Flujo**
1. Arrastrar **SelectOutput** a la derecha de `SORTING_PROCESS`
2. Configurar Properties:
   - **Name:** `FLOW_DECISION`
   - **Type:** `Condition`
   - **Condition:** `By code`
   - **Outputs:** `3` (Cambiar de 2 a 3)

#### **Paso 7.2: Programar Distribución Probabilística**
En el campo de código:

```java
// Decidir ruta según porcentajes predefinidos
double randomValue = uniform(0, 1);

if (randomValue < 0.65) {
    return 0;  // 65% - Cross-docking directo
} else if (randomValue < 0.95) {
    return 1;  // 30% - Buffer estratégico (0.65 + 0.30 = 0.95)
} else {
    return 2;  // 5% - Kitting/Valor agregado
}
```

#### **Paso 7.3: Crear Procesos para Cada Ruta**

**Ruta 1: Buffer Estratégico**
1. Arrastrar **Delay** en posición media-derecha
2. Configurar:
   - **Name:** `BUFFER_TIME`
   - **Delay time:** `triangular(1, 3, 6)`

**Ruta 2: Kitting/Valor Agregado**
1. Arrastrar **Delay** en posición inferior-derecha
2. Configurar:
   - **Name:** `KITTING_PROCESS`
   - **Delay time:** `triangular(0.15, 0.30, 0.50)`

**Ruta 0: Cross-docking** va directo al siguiente paso

#### **Paso 7.4: Conectar las Rutas**
```
SORTING_PROCESS → FLOW_DECISION ─┬─(0)─> [Cross-docking] ─┐
                                 ├─(1)─> BUFFER_TIME ────┤
                                 └─(2)─> KITTING_PROCESS ─┘
```

### ⚠️ PROBLEMAS COMUNES

| Problema | Solución |
|----------|----------|
| Porcentajes incorrectos | Verificar: <0.65=65%, 0.65-0.95=30%, >0.95=5% |
| Solo 2 salidas | Properties → Outputs: cambiar a `3` |
| Error uniform | Usar `uniform(0, 1)` no `random()` |

### ✅ Checklist  🧐 *Verifica:* Si todo coincide con los parámetros del CEDIS San Bartolo (22,000 pallets, 24 andenes).
- [ ] SelectOutput configurado con 3 salidas
- [ ] Código de distribución probabilística correcto
- [ ] Delay para Buffer creado con tiempos apropiados
- [ ] Delay para Kitting creado con tiempos apropiados
- [ ] Porcentajes suman 100%


## 8. PASO 8 ASIGNACIÓN DE DESTINO OEM**

### 🎯 Objetivo  💡 *Tip:* Este paso conecta con tu diseño conceptual de la Actividad 6.
Determinar a qué ensambladora final se dirige cada material.

### 🧠 Lógica  📌 *Recuerda:* Justificaste esta lógica en tu ensayo (reducción de viajes, eficiencia).
Distribución basada en volumen:
- **GM Silao (55%):** Mayor volumen
- **GM SLP (33%):** Volumen medio  
- **BMW SLP (12%):** Volumen menor, alto valor

### 🛠️ Configuración  ✅ *Consejo:* Sigue el orden exacto para evitar errores en AnyLogic.

#### **Paso 8.1: Crear Decisión de Destino**
1. Arrastrar **SelectOutput**
2. Configurar:
   - **Name:** `DESTINO_OEM`
   - **Type:** `Condition`
   - **Condition:** `By code`
   - **Outputs:** `3`

#### **Paso 8.2: Programar Asignación**
```java
double r = uniform(0, 1);
if (r < 0.55) {
    agent.destinoOEM = "GM_SILAO";
    return 0;
} else if (r < 0.88) {
    agent.destinoOEM = "GM_SLP";
    return 1;
} else {
    agent.destinoOEM = "BMW_SLP";
    return 2;
}
```

#### **Paso 8.3: Conectar Flujos Anteriores**
- Rama 0 de `FLOW_DECISION` → `DESTINO_OEM`
- `BUFFER_TIME` → `DESTINO_OEM`
- `KITTING_PROCESS` → `DESTINO_OEM`

#### **Paso 8.4: Preparación por Cliente**

| Cliente | Bloque | Nombre | Delay Time |
|---------|--------|--------|------------|
| GM Silao | Delay | `PREPARE_GM_SILAO` | `triangular(0.25, 0.40, 0.60)` |
| GM SLP | Delay | `PREPARE_GM_SLP` | `triangular(0.25, 0.40, 0.60)` |
| BMW SLP | Delay | `PREPARE_BMW_SLP` | `triangular(0.30, 0.45, 0.70)` |

**Conexiones:**
```
DESTINO_OEM ─┬─(0)─> PREPARE_GM_SILAO
             ├─(1)─> PREPARE_GM_SLP
             └─(2)─> PREPARE_BMW_SLP
```


---

### 🎯 Objetivo  💡 *Tip:* Este paso conecta con tu diseño conceptual de la Actividad 6.
Determinar a qué ensambladora final se dirige cada material y prepararlo para embarque.

### 🧠 Lógica  📌 *Recuerda:* Justificaste esta lógica en tu ensayo (reducción de viajes, eficiencia).
Distribución basada en volumen de producción:
- **GM Silao (55%):** Planta más grande, mayor volumen
- **GM SLP (33%):** Planta mediana, volumen significativo  
- **BMW SLP (12%):** Planta premium, volumen menor pero alto valor

### 🛠️ Configuración Paso a Paso  ✅ *Consejo:* Sigue el orden exacto para evitar errores en AnyLogic.

#### **Paso 8.1: Crear Punto de Decisión de Destino**
1. Arrastrar **SelectOutput** a la derecha (posición central)
2. Configurar Properties:
   - **Name:** `DESTINO_OEM`
   - **Type:** `Condition`
   - **Condition:** `By code`
   - **Outputs:** `3`

#### **Paso 8.2: Programar Asignación de Destino**
En el campo de código:

```java
// Asignar destino final según porcentajes OEM
double r = uniform(0, 1);

if (r < 0.55) {
    agent.destinoOEM = "GM_SILAO";
    return 0;  // 55% - GM Silao
} else if (r < 0.88) {
    agent.destinoOEM = "GM_SLP";
    return 1;  // 33% - GM SLP (0.55 + 0.33 = 0.88)
} else {
    agent.destinoOEM = "BMW_SLP";
    return 2;  // 12% - BMW SLP
}
```

#### **Paso 8.3: Conectar Todos los Flujos Anteriores**
**Conectar las 3 rutas al mismo SelectOutput:**
- Rama 0 de `FLOW_DECISION` (Cross-docking) → `DESTINO_OEM`
- `BUFFER_TIME` → `DESTINO_OEM`
- `KITTING_PROCESS` → `DESTINO_OEM`

#### **Paso 8.4: Crear Procesos de Preparación por Cliente**

**Para GM Silao:**
1. Arrastrar **Delay** arriba a la derecha
2. Configurar:
   - **Name:** `PREPARE_GM_SILAO`
   - **Delay time:** `triangular(0.25, 0.40, 0.60)`

**Para GM SLP:**
1. Arrastrar **Delay** al centro-derecha
2. Configurar:
   - **Name:** `PREPARE_GM_SLP`
   - **Delay time:** `triangular(0.25, 0.40, 0.60)`

**Para BMW SLP:**
1. Arrastrar **Delay** abajo a la derecha
2. Configurar:
   - **Name:** `PREPARE_BMW_SLP`
   - **Delay time:** `triangular(0.30, 0.45, 0.70)`

#### **Paso 8.5: Conectar Destinos**
```
DESTINO_OEM ─┬─(0)─> PREPARE_GM_SILAO
             ├─(1)─> PREPARE_GM_SLP
             └─(2)─> PREPARE_BMW_SLP
```

### ✅ Checklist  🧐 *Verifica:* Si todo coincide con los parámetros del CEDIS San Bartolo (22,000 pallets, 24 andenes).
- [ ] SelectOutput con 3 salidas para destinos
- [ ] Código asigna correctamente destinoOEM
- [ ] Los 3 flujos anteriores conectados al mismo SelectOutput
- [ ] 3 delays de preparación creados (uno por OEM)
- [ ] Tiempos diferenciados (BMW mayor tiempo)
- [ ] Distribución porcentual suma 100%

### 🎯 Objetivo  💡 *Tip:* Este paso conecta con tu diseño conceptual de la Actividad 6.
Definir variables globales para calcular métricas de desempeño.

### 🛠️ Configuración  ✅ *Consejo:* Sigue el orden exacto para evitar errores en AnyLogic.
En agente **Main**, crear estas variables:

| Variable | Tipo | Valor | Descripción |
|----------|------|-------|-------------|
| `palletsProcessed` | int | `0` | Contador total de pallets |
| `trucksProcessed` | int | `0` | Contador total de camiones |
| `avgCycleTime` | double | `0.0` | Tiempo promedio en sistema |
| `totalCycleTime` | double | `0.0` | Acumulador para cálculo promedio |

**Ubicación:** Área superior derecha del canvas de Main

## 9. PASO 9 SALIDA Y REGISTRO DE MÉTRICAS**

### 🎯 Objetivo  💡 *Tip:* Este paso conecta con tu diseño conceptual de la Actividad 6.
Completar el flujo y registrar indicadores de desempeño.

### 🛠️ Configuración  ✅ *Consejo:* Sigue el orden exacto para evitar errores en AnyLogic.

#### **Paso 9.1: Crear Variables KPI en Main**

| Variable | Tipo | Valor | Descripción |
|----------|------|-------|-------------|
| `palletsProcessed` | int | `0` | Total pallets procesados |
| `trucksProcessed` | int | `0` | Total camiones procesados |
| `avgCycleTime` | double | `0.0` | Tiempo promedio en sistema |
| `totalCycleTime` | double | `0.0` | Acumulador para promedio |

#### **Paso 9.2: Crear Salida**
1. Arrastrar **Sink**
2. **Name:** `EXIT_CEDIS`
3. Conectar los 3 PREPARE al Sink

#### **Paso 9.3: Código en EXIT_CEDIS (On exit)**
```java
// Registrar hora de salida
agent.tSalidaSistema = time();

// Calcular tiempo de ciclo
double tCiclo = agent.tSalidaSistema - agent.tEntradaSistema;

// Actualizar contadores
palletsProcessed += agent.pallets;
trucksProcessed += 1;

// Actualizar tiempo promedio
totalCycleTime += tCiclo;
avgCycleTime = totalCycleTime / trucksProcessed;
```

---


---

### 🎯 Objetivo  💡 *Tip:* Este paso conecta con tu diseño conceptual de la Actividad 6.
Completar el flujo con la salida del sistema y registrar todos los indicadores clave de desempeño.

### 🧠 Lógica  📌 *Recuerda:* Justificaste esta lógica en tu ensayo (reducción de viajes, eficiencia).
El punto de salida es crítico para:
- **Liberar recursos** del sistema
- **Calcular métricas** de desempeño
- **Generar reportes** automáticos
- **Validar funcionamiento** del modelo

### 🛠️ Configuración Paso a Paso  ✅ *Consejo:* Sigue el orden exacto para evitar errores en AnyLogic.

#### **Paso 9.1: Crear Variables Globales para KPIs**
En agente **Main**, crear estas variables:

| Variable | Tipo | Valor | Descripción |
|----------|------|-------|-------------|
| `palletsProcessed` | int | `0` | Contador total de pallets |
| `trucksProcessed` | int | `0` | Contador total de camiones |
| `avgCycleTime` | double | `0.0` | Tiempo promedio en sistema |
| `totalCycleTime` | double | `0.0` | Acumulador para cálculo promedio |

**Procedimiento:**
1. En **Main**, paleta: **Agent → Variable**
2. Crear las 4 variables en área superior del canvas
3. Configurar Name, Type y Initial Value para cada una

#### **Paso 9.2: Crear Punto de Salida**
1. Arrastrar **Sink** desde Process Modeling Library
2. Colocar a la derecha de los 3 delays de preparación
3. Configurar:
   - **Name:** `EXIT_CEDIS`

#### **Paso 9.3: Conectar Todas las Rutas Finales**
Conectar los 3 delays de preparación al Sink:
- `PREPARE_GM_SILAO` → `EXIT_CEDIS`
- `PREPARE_GM_SLP` → `EXIT_CEDIS`
- `PREPARE_BMW_SLP` → `EXIT_CEDIS`

#### **Paso 9.4: Programar Registro de Métricas**
En el bloque `EXIT_CEDIS`, sección **On exit**:

```java
// ===== REGISTRO DE MÉTRICAS AL SALIR =====

// 1. Registrar hora de salida del sistema
agent.tSalidaSistema = time();

// 2. Calcular tiempo de ciclo individual
double cicloIndividual = agent.tSalidaSistema - agent.tEntradaSistema;

// 3. Actualizar contadores de volumen
palletsProcessed += agent.pallets;  // Sumar pallets procesados
trucksProcessed += 1;               // Incrementar contador de camiones

// 4. Calcular tiempo promedio de ciclo
totalCycleTime += cicloIndividual;  // Acumular tiempos
avgCycleTime = totalCycleTime / trucksProcessed;  // Calcular promedio
```

### ✅ Checklist  🧐 *Verifica:* Si todo coincide con los parámetros del CEDIS San Bartolo (22,000 pallets, 24 andenes).
- [ ] 4 variables KPI creadas en Main con valores iniciales
- [ ] Sink creado como punto final del flujo
- [ ] Los 3 delays de preparación conectados al Sink
- [ ] Código On exit implementado correctamente
- [ ] No hay errores de compilación en el código

### 🎯 Objetivo  💡 *Tip:* Este paso conecta con tu diseño conceptual de la Actividad 6.
Panel visual para monitoreo en tiempo real.

### 🛠️ Configuración  ✅ *Consejo:* Sigue el orden exacto para evitar errores en AnyLogic.

#### **Paso 10.1: Título del Dashboard**
- **Text:** `📊 DASHBOARD - CEDIS SAN BARTOLO`
- **Font:** Bold, Size: 18

#### **Paso 10.2: Métricas Dinámicas**

| Métrica | Texto Dinámico | Color |
|---------|----------------|-------|
| Pallets procesados | `palletsProcessed` | Verde |
| Camiones procesados | `trucksProcessed` | Azul |
| Tiempo promedio | `format("%.2f", avgCycleTime)` | Naranja |
| Utilización andenes | `format("%.1f", docks.utilization() * 100)` | Rojo |

---

---

# PARTE 5: RECURSOS ADICIONALES Y OPTIMIZACIÓN

---

## 10. PASO 10 GESTIÓN DE MONTACARGAS (OPCIONAL)

### 🎯 Objetivo  💡 *Tip:* Este paso conecta con tu diseño conceptual de la Actividad 6.
Implementar el uso de montacargas como recurso adicional para procesos internos.

### 🧠 Lógica  📌 *Recuerda:* Justificaste esta lógica en tu ensayo (reducción de viajes, eficiencia).
Algunos procesos requieren recursos físicos:
- **Montacargas:** Para mover pallets en sorting, buffer y kitting
- **Operadores:** Para tareas manuales

### 🛠️ Configuración Paso a Paso  ✅ *Consejo:* Sigue el orden exacto para evitar errores en AnyLogic.

#### **Paso 10.1: Crear ResourcePool de Montacargas**
1. En **Main**, arrastrar **Resource Pool**
2. Configurar:
   - **Name:** `forklifts`
   - **Capacity:** `12`

#### **Paso 10.2: Usar Montacargas en Procesos Clave**

**En SORTING_PROCESS:**
1. **ANTES** del delay: Agregar **Seize**
   - **Name:** `SEIZE_FORK_SORTING`
   - **Resource:** `forklifts`, **Quantity:** `2`
2. **DESPUÉS** del delay: Agregar **Release**
   - **Name:** `RELEASE_FORK_SORTING`
   - **Resource:** `forklifts`

**Reconectar:** `DELAY_RECEP_*` → `SEIZE_FORK_SORTING` → `SORTING_PROCESS` → `RELEASE_FORK_SORTING` → `FLOW_DECISION`

### ✅ Checklist (Opcional)  🧐 *Verifica:* Si todo coincide con los parámetros del CEDIS San Bartolo (22,000 pallets, 24 andenes).
- [ ] ResourcePool forklifts creado
- [ ] Seize/Release agregados en al menos un proceso
- [ ] El modelo sigue funcionando correctamente

---

# PARTE 6: DASHBOARD Y VISUALIZACIÓN

---

## 11. PASO 11 CREAR DASHBOARD DE MONITOREO

### 🎯 Objetivo  💡 *Tip:* Este paso conecta con tu diseño conceptual de la Actividad 6.
Crear un panel de control visual que muestre en tiempo real el estado del CEDIS y las métricas clave.

### 🧠 Lógica  📌 *Recuerda:* Justificaste esta lógica en tu ensayo (reducción de viajes, eficiencia).
Un dashboard efectivo permite:
- **Monitoreo en tiempo real** de operaciones
- **Identificación rápida** de problemas
- **Comunicación clara** de resultados
- **Validación visual** del modelo

### 🛠️ Configuración Paso a Paso  ✅ *Consejo:* Sigue el orden exacto para evitar errores en AnyLogic.

#### **Paso 11.1: Crear Título del Dashboard**
1. En **Main**, paleta: **Presentation → Text**
2. Arrastrar a esquina superior derecha
3. Configurar:
   - **Text:** `📊 DASHBOARD - CEDIS SAN BARTOLO`
   - **Font:** Bold, Size: 18
   - **Text color:** `#2C3E50`

#### **Paso 11.2: Crear Etiquetas y Valores Dinámicos**

**Para Pallets Procesados:**
1. **Texto estático:** `Pallets procesados:`
2. **Texto dinámico:** `palletsProcessed`
   - **Font:** Bold, Size: 14, Color: Verde

**Para Camiones Procesados:**
1. **Texto estático:** `Camiones procesados:`
2. **Texto dinámico:** `trucksProcessed`
   - **Font:** Bold, Size: 14, Color: Azul

**Para Tiempo Promedio:**
1. **Texto estático:** `Tiempo promedio (horas):`
2. **Texto dinámico:** `format("%.2f", avgCycleTime)`
   - **Font:** Bold, Size: 14, Color: Naranja

**Para Utilización Andenes:**
1. **Texto estático:** `Utilización andenes (%):`
2. **Texto dinámico:** `format("%.1f", docks.utilization() * 100)`
   - **Font:** Bold, Size: 14, Color: Rojo

### ✅ Checklist  🧐 *Verifica:* Si todo coincide con los parámetros del CEDIS San Bartolo (22,000 pallets, 24 andenes).
- [ ] Título del dashboard creado
- [ ] 4 etiquetas estáticas de métricas
- [ ] 4 valores dinámicos vinculados a variables
- [ ] Formato correcto para números decimales
- [ ] Dashboard organizado y legible

### 🎯 Objetivo  💡 *Tip:* Este paso conecta con tu diseño conceptual de la Actividad 6.
Implementar montacargas como recurso adicional.

### 🛠️ Configuración  ✅ *Consejo:* Sigue el orden exacto para evitar errores en AnyLogic.

#### **Paso 11.1: Crear ResourcePool**
- **Name:** `forklifts`
- **Capacity:** `12`

#### **Paso 11.2: Usar en Procesos**
Agregar **Seize/Release** alrededor de `SORTING_PROCESS`:
- **Seize:** `forklifts`, Quantity: `2`
- **Release:** `forklifts`





---

# PARTE 7: EJECUCIÓN Y PUBLICACIÓN

---

## 12. PASO 12 EJECUCIÓN Y VALIDACIÓN

### 🎯 Objetivo  💡 *Tip:* Este paso conecta con tu diseño conceptual de la Actividad 6.
Verificar que el modelo funciona correctamente y produce resultados dentro de rangos esperados.

### 🧠 Lógica  📌 *Recuerda:* Justificaste esta lógica en tu ensayo (reducción de viajes, eficiencia).
Las pruebas validan que:
- **El flujo es continuo** sin bloqueos
- **Las métricas son razonables** según diseño
- **Los recursos se utilizan** eficientemente
- **No hay errores** de programación

### 🛠️ Procedimiento de Pruebas

#### **Paso 12.1: Ejecución Inicial**
1. Click en botón **Run** (▶️)
2. Observar comportamiento por 5-10 minutos
3. Verificar flujo continuo de camiones

#### **Paso 12.2: Validación de Métricas**
Después de 24 horas simuladas:

| KPI | Rango Esperado |
|-----|----------------|
| **Pallets procesados** | 6,000 - 8,000 |
| **Camiones procesados** | 200 - 300 |
| **Tiempo ciclo promedio** | 2.5 - 4.5 horas |
| **Utilización andenes** | 65% - 85% |

### ✅ Checklist  🧐 *Verifica:* Si todo coincide con los parámetros del CEDIS San Bartolo (22,000 pallets, 24 andenes).
- [ ] Modelo ejecuta sin errores
- [ ] Camiones fluyen de inicio a fin
- [ ] Dashboard muestra datos reales
- [ ] Métricas en rangos esperados

---



## 13. PASO 13 PUBLICACIÓN EN ANYLOGIC CLOUD

### 🎯 Objetivo  💡 *Tip:* Este paso conecta con tu diseño conceptual de la Actividad 6.
Publicar el modelo en la nube para compartirlo.

### 🛠️ Procedimiento

#### **Paso 13.1: Exportar a la Nube**
1. Menú: **File → Export → To AnyLogic Cloud...**
2. Configurar:
   - **Model name:** `CEDIS_SanBartolo_TuApellido_Matricula`
   - **Access:** `Public`
3. Click en **Upload**

#### **Paso 13.2: Probar en Navegador**
1. Copiar URL proporcionada
2. Abrir en navegador web
3. Verificar funcionalidad

### ✅ Checklist  🧐 *Verifica:* Si todo coincide con los parámetros del CEDIS San Bartolo (22,000 pallets, 24 andenes).
- [ ] Modelo exportado sin errores
- [ ] URL copiada y guardada
- [ ] Modelo accesible públicamente

---

## 🎯 RESUMEN DE COMPLETITUD

### ✅ **CONFIGURACIONES AHORA COMPLETAS:**

| Objetivo | Estado |  💡 *Tip:* Este paso conecta con tu diseño conceptual de la Actividad 6.
|----------|--------|
| 1. Configurar proyecto AnyLogic | ✅ COMPLETO |
| 2. Crear agentes (camiones) | ✅ COMPLETO |
| 3. Dibujar layout del CEDIS | ✅ COMPLETO |
| 4. Construir diagrama de flujo | ✅ COMPLETO |
| 5. Gestionar recursos | ✅ COMPLETO |
| 6. Programar decisiones de ruteo | ✅ COMPLETO |
| 7. Calcular indicadores (KPIs) | ✅ COMPLETO |
| 8. Publicar en AnyLogic Cloud | ✅ COMPLETO |
| 9. Crear dashboard de monitoreo | ✅ COMPLETO |

### 📊 **FLUJO COMPLETO IMPLEMENTADO:**
```
SRC_LEAR ──┐
SRC_COND ──┼─> ENTER → Q_ANDEN → SEIZE → UNLOAD → RELEASE → ROUTE_RECEPCION
SRC_MAGNA ─┘                                         │
                                                     ↓
                                              ┌─ RECEP_NORTE ─┐
                                              │               │
                                              └─ RECEP_SUR ───┘
                                                     │
                                                SORTING_PROCESS
                                                     │
                                               FLOW_DECISION
                                              /      |      \
                                      Cross-docking Buffer Kitting
                                            |        |        |
                                            ↓        ↓        ↓
                                         DESTINO_OEM (Convergen)
                                            /        |        \
                                    GM_SILAO     GM_SLP     BMW_SLP
                                       |            |           |
                                       ↓            ↓           ↓
                                    EXIT_CEDIS → KPIs & Dashboard
```

**¡Listo para entregar! 🎯**

<!--stackedit_data:
eyJoaXN0b3J5IjpbMjA3OTAwNDM0NywtMTMyNTgzMTg0MSwxMj
gzMDcwMDE0LDE0MjM1NDEyMTYsMTY1MDQwOTQxMSwtNDU1MzAz
NzE3LC0zODk5NjY3MjUsLTE5MTQ0NzQ0NzcsMTIxODA1NDAyOS
wtMTc3ODMzODU0MCwyMDY0MTIzNzQwLDExNDI1MzU4MiwtOTY3
OTQ5MzU2LDE1NjQ1ODY4NjRdfQ==
-->