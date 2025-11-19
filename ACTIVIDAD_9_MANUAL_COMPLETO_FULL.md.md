# 🟦 ACTIVIDAD 9 – MODELADO DEL CEDIS AUTOMOTRIZ SAN BARTOLO EN ANYLOGIC  
### Curso: Logística y Cadena de Valor  
### Unidad: Sistemas de Almacenamiento y CEDIS  
### Versión: 2025

## 1. DATOS DE IDENTIFICACIÓN

| Campo                    | Información a completar por el estudiante |
|--------------------------|-------------------------------------------|
| Nombre del estudiante    |                                           |
| Matrícula                |                                           |
| Carrera                  |                                           |
| Grupo                    |                                           |
| Fecha de entrega         |                                           |
| Nombre del CEDIS modelado| CEDIS AUTOMOTRIZ SAN BARTOLO             |

---

## 2. CONTEXTO Y VÍNCULO CON ACTIVIDADES ANTERIORES

Esta Actividad 9 se construye sobre el trabajo previo desarrollado en:

- **Actividad 6:** Diseño conceptual del sistema de almacenamiento regional (CEDIS San Bartolo).  
- **Actividad 7:** Análisis de redes logísticas, industrias factibles en la región y justificación de ubicación.  
- (Opcional) **Actividad 8:** Métodos de análisis cuantitativo para toma de decisiones en logística.

Ahora, en la **Actividad 9**, llevarás ese diseño conceptual a un **modelo de simulación en AnyLogic**, donde representarás:

- Los **flujos de camiones (proveedores)** hacia el CEDIS.  
- Las **operaciones internas** del CEDIS (recepción, sorting, buffer, kitting, embarque).  
- Los **flujos de salida** hacia los clientes finales: **GM Silao, GM SLP y BMW SLP**.  

El objetivo es que puedas **visualizar y analizar** cómo se comporta el CEDIS bajo diferentes condiciones operativas.

---

## 3. OBJETIVO GENERAL DE LA ACTIVIDAD

> **Construir y documentar un modelo operativo del CEDIS Automotriz San Bartolo en AnyLogic**, representando el flujo de entrada, procesos internos y salida hacia tres OEMs (GM Silao, GM SLP, BMW SLP), integrando recursos, colas, tiempos de proceso y rutas de material, para analizar el desempeño logístico del sistema.

---

## 4. OBJETIVOS ESPECÍFICOS

Al finalizar esta actividad, el estudiante será capaz de:

1. **Configurar** un modelo de simulación en AnyLogic con unidades de tiempo y longitud adecuadas.  
2. **Definir agentes** (camiones) con atributos de proveedor, región, destino y número de pallets.  
3. **Construir un layout básico** de un CEDIS con zonas funcionales: recepción, sorting, buffer, kitting y embarques.  
4. **Implementar un diagrama de flujo (flowchart)** usando la Process Modeling Library.  
5. **Incorporar recursos (ResourcePools)** para andenes, montacargas y operadores.  
6. **Configurar lógica de ruteo y asignación de destino** (GM Silao, GM SLP, BMW SLP).  
7. **Generar indicadores clave (KPIs)** como pallets procesados, utilización de recursos y tiempos promedio.  
8. **Publicar el modelo en AnyLogic Cloud** y elaborar conclusiones sobre el desempeño del CEDIS.

---

## 5. REQUISITOS PREVIOS

Antes de iniciar esta actividad, el estudiante debe:

- Haber leído y comprendido las instrucciones de las **Actividades 6 y 7**.  
- Tener instalado **AnyLogic** (versión PLE o superior).  
- Haber visto el video de referencia para el modelo base de almacén.  
- Contar con el layout conceptual del **CEDIS AUTOMOTRIZ SAN BARTOLO** (imagen proporcionada por el profesor).

---

## 6. INSTRUCCIONES GENERALES PARA EL ESTUDIANTE

A continuación se presentan los **pasos guiados** para construir el modelo.  
Cada sección incluye:

- 🎯 **Objetivo del paso**  
- 🧠 **Descripción de la lógica**  
- 🛠️ **Configuración en AnyLogic**  
- 💻 **Código Java (si aplica)**  

Te recomiendo ir **paso por paso** en el orden indicado.

---

# 7. PASO 1 – CONFIGURACIÓN INICIAL DEL MODELO

### 🎯 Objetivo
Crear un proyecto nuevo en AnyLogic con unidades adecuadas y un agente principal `Main` que representará el CEDIS.

### 🧠 Lógica
Trabajaremos en un solo agente principal (`Main`) que contendrá:

- El **layout** del CEDIS (nave, zonas internas, patios).  
- El **diagrama de flujo** de camiones y pallets.  
- Los **recursos** y **KPIs**.

### 🛠️ Configuración

1. Abrir AnyLogic → `File → New Model…`
2. Asignar un nombre al proyecto, por ejemplo:  
   `CEDIS_SanBartolo_ApellidoNombre`
3. En la vista de propiedades de `Main`, configurar:
   - **Time units:** `hours`  
   - **Length units:** `meters`

---

# 8. PASO 2 – DIBUJAR EL LAYOUT BÁSICO DEL CEDIS

### 🎯 Objetivo
Representar la nave del CEDIS y sus zonas principales para tener una referencia espacial.

### 🧠 Lógica
No buscamos un plano arquitectónico perfecto, sino una **representación funcional** de las zonas:

- Recepción Norte  
- Recepción Sur  
- Sorting  
- Buffer Estratégico  
- Kitting / Valor agregado  
- Embarques GM Silao  
- Embarques GM SLP  
- Embarques BMW SLP  

### 🛠️ Configuración

En el agente `Main`:

1. Usar la sección **Presentation → Shapes → Rectangle** para dibujar:
   - Un rectángulo grande para la nave (ej. 250 × 100 m aprox.).  
   - Rectángulos internos para cada zona, siguiendo el layout proporcionado.

2. Nombrar cada zona con `Text`:
   - `RECEPCIÓN NORTE`  
   - `RECEPCIÓN SUR`  
   - `SORTING`  
   - `BUFFER ESTRATÉGICO`  
   - `KITTING / VALOR AGREGADO`  
   - `EMBARQUES GM SILAO`  
   - `EMBARQUES GM SLP`  
   - `EMBARQUES BMW SLP`

3. Opcional: agregar caminos (`Polyline` o `Path`) donde se moverán montacargas.

---

# 9. PASO 3 – CREAR EL TIPO DE AGENTE `Truck`

### 🎯 Objetivo
Definir la estructura de datos del camión (agente) que entrará, circulará y saldrá del CEDIS.

### 🧠 Lógica
Cada camión debe “saber”:

- Qué proveedor es.  
- De qué región viene (norte/sur).  
- Cuántos pallets trae.  
- A qué OEM se dirigirá (GM Silao, GM SLP, BMW SLP).

### 🛠️ Configuración

1. En el panel de proyectos → `Agent Types → New Agent Type…`
2. Nombre: `Truck`
3. En la sección **Properties → Agent**, seleccionar:
   - `Agent type` → `Generic`
4. Agregar atributos:

```java
String proveedor;
String region;
String destinoOEM;
int pallets;
double tEntradaSistema;
double tSalidaSistema;
```

---

# 10. PASO 4 – DEFINIR FUENTES DE CAMIONES (PROVEEDORES)

(Sigue igual… contenido completo …)

<!--stackedit_data:
eyJoaXN0b3J5IjpbLTEwNDA4MjQxNTBdfQ==
-->