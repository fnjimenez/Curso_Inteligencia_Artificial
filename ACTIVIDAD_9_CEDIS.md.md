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


<!--stackedit_data:
eyJoaXN0b3J5IjpbMTIxODA1NDAyOSwtMTc3ODMzODU0MCwyMD
Y0MTIzNzQwLDExNDI1MzU4MiwtOTY3OTQ5MzU2LDE1NjQ1ODY4
NjRdfQ==
-->