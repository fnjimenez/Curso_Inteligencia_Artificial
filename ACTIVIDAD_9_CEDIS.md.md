# 🟦 ACTIVIDAD 9 – MODELADO DEL CEDIS AUTOMOTRIZ SAN BARTOLO EN ANYLOGIC

### Curso: Logística y Cadena de Valor
### Unidad: Sistemas de Almacenamiento y CEDIS
### Versión: 2025 - EDICIÓN MEJORADA PARA PRINCIPIANTES

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

| # | Objetivo | Estado | Prioridad |
|---|----------|--------|-----------|
| 1 | Configurar proyecto AnyLogic con unidades correctas | | 🔴 ALTA |
| 2 | Crear agentes (camiones) con información de carga y destino | | 🔴 ALTA |
| 3 | Dibujar layout básico del CEDIS | | 🟡 MEDIA |
| 4 | Construir diagrama de flujo con bloques Process Modeling | | 🔴 ALTA |
| 5 | Gestionar recursos (andenes, montacargas) | | 🔴 ALTA |
| 6 | Programar decisiones de ruteo | | 🟡 MEDIA |
| 7 | Calcular indicadores (KPIs) | | 🟢 BAJA |
| 8 | Publicar modelo en AnyLogic Cloud | | 🟢 BAJA |
| 9 | Crear dashboard de monitoreo | | 🟡 MEDIA |

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

### ✅ Sistema de Checklist
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

# PARTE 1: CONFIGURACIÓN INICIAL

---

## 7. PASO 1 – CREAR EL PROYECTO Y CONFIGURAR UNIDADES

### 🎯 Objetivo
Crear un proyecto nuevo en AnyLogic con las unidades correctas (horas y metros) para el modelo del CEDIS.

### 🧠 Lógica
Trabajaremos en un solo agente llamado `Main` que contendrá todos los elementos:
- El dibujo del layout del CEDIS
- El diagrama de flujo completo de camiones  
- Los recursos compartidos (andenes, montacargas)
- Los indicadores de desempeño (KPIs)

### 🛠️ Configuración Paso a Paso

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

### ✅ Checklist de Verificación
- [ ] Proyecto creado con nombre personalizado correcto
- [ ] Unidades configuradas en horas y metros en Environment  
- [ ] Main está abierto y visible en el canvas
- [ ] Puedo ver la ventana de Properties en la parte inferior

# PARTE 1: CONFIGURACIÓN INICIAL (CONTINUACIÓN)

---

## 8. PASO 2 – DIBUJAR EL LAYOUT DEL CEDIS

### 🎯 Objetivo
Crear la representación visual del CEDIS usando el layout proporcionado como referencia, definiendo claramente todas las zonas operativas.

### 🧠 Lógica
Un buen layout visual ayuda a:
- Entender el flujo de materiales
- Ubicar correctamente los procesos
- Comunicar el diseño a otras personas
- Debuggear problemas en la simulación

### 🛠️ Configuración Paso a Paso

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

### ✅ Checklist de Verificación
- [ ] Imagen de fondo insertada y bloqueada en posición
- [ ] 8 zonas dibujadas con rectángulos de colores diferenciados
- [ ] Todas las etiquetas de texto agregadas y legibles
- [ ] Colores consistentes según la función de cada zona
- [ ] Layout organizado y fácil de entender
- [ ] Espacio reservado para el diagrama de flujo


<!--stackedit_data:
eyJoaXN0b3J5IjpbLTE5MTQ0NzQ0NzcsMTIxODA1NDAyOSwtMT
c3ODMzODU0MCwyMDY0MTIzNzQwLDExNDI1MzU4MiwtOTY3OTQ5
MzU2LDE1NjQ1ODY4NjRdfQ==
-->