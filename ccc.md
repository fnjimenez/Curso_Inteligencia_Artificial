
¡Claro que sí! Aquí tienes el documento Markdown completo y reestructurado (`ACTIVIDAD_9_CEDIS_ANYLOGIC.md`).

He tomado el contenido original de la guía, lo he reordenado para seguir un flujo de modelado lógico (Recursos → Agentes → Flujo de Proceso), y he **integrado las configuraciones que estaban omitidas** para que el modelo sea funcional, incluyendo los montacargas, la lógica de _cross-docking_ y el cálculo de KPIs.

----------

Markdown

```
# 🟦 ACTIVIDAD 9 – MODELADO DEL CEDIS AUTOMOTRIZ SAN BARTOLO EN ANYLOGIC

### Curso: Logística y Cadena de Valor
### Unidad: Sistemas de Almacenamiento y CEDIS
### Versión: 2025 - EDICIÓN MEJORADA Y COMPLETA

---

## 1. DATOS DE IDENTIFICACIÓN

| Campo | Información a completar |
| :--- | :--- |
| **Nombre del estudiante** | |
| **Matrícula** | |
| **Carrera** | |
| **Grupo** | |
| **Fecha de entrega** | |
| **Nombre del CEDIS modelado** | CEDIS AUTOMOTRIZ SAN BARTOLO |

---

## 2. OBJETIVOS Y CONTEXTO

### 🎯 OBJETIVO GENERAL
> **Construir y documentar un modelo funcional del CEDIS en AnyLogic** que simule camiones entrando, procesos de descarga, clasificación, almacenamiento y despacho hacia tres clientes automotrices, con recursos (andenes, montacargas), tiempos y KPIs medibles.

### 📊 DATOS CLAVE DEL CEDIS
| Parámetro | Valor | Notas |
| :--- | :--- | :--- |
| **Capacidad Total** | 22,000 pallets | Capacidad máxima de almacenamiento |
| **Andenes** | 24 total | 8 recepción + 16 embarque |
| **Cross-docking** | 65% | Materiales que pasan directo sin almacenarse |
| **Recursos Internos** | Montacargas | Usados para manipulación y traslados |

---

# PARTE 1: CONFIGURACIÓN BÁSICA Y RECURSOS

## 3. PASO 1 – CREAR EL PROYECTO Y CONFIGURAR UNIDADES

### 🛠️ CONFIGURACIÓN PASO A PASO
1.  **Crear Proyecto:** `File` → `New Model…` (Nombre: `CEDIS_SanBartolo_TuApellido`).
2.  **Configurar Unidades:** Hacer click en el **nombre del modelo** (no en Main).
    * **Time units:** seleccionar `hour` (hora)
    * **Length units:** seleccionar `meter` (metro)

### ✅ CHECKLIST DE VERIFICACIÓN
* Proyecto creado con nombre personalizado correcto.
* Unidades configuradas en horas y metros.

---

## 4. PASO 2 – DIBUJAR EL LAYOUT Y DEFINIR RECURSOS

### 🎯 OBJETIVO
Definir la estructura visual y los dos grupos de recursos limitados del CEDIS (Andenes y Montacargas).

### 🛠️ CONFIGURACIÓN PASO A PASO
#### 4.1. Insertar Imagen y Zonas (Layout)
* **Insertar Imagen:** Insertar el layout PNG como fondo. Clic derecho → `Lock`.
* **Dibujar Zonas:** Usar `Rectangular Node` (Presentation → Space Markup) para definir: **Recepción Norte/Sur**, **Sorting**, **Buffer Estratégico**, **Kitting**, y las zonas de **Embarque**.

#### 4.2. Definir Pools de Recursos (ResourcePool)
En el agente `Main`, arrastrar dos bloques **ResourcePool** desde la paleta *Process Modeling Library*.

| Nombre del Pool | Capacidad | Propósito |
| :--- | :--- | :--- |
| **`docks`** | `24` | Andenes totales para descarga/carga |
| **`forklifts`** | **`8`** | Montacargas disponibles para manejo interno |

### ✅ CHECKLIST DE VERIFICACIÓN
* Imagen de layout insertada y fijada.
* `ResourcePool` **`docks`** creado con capacidad 24.
* `ResourcePool` **`forklifts`** creado con capacidad 8.

---

## 5. PASO 3 – CREAR Y CONFIGURAR EL AGENTE TRUCK

### 🎯 OBJETIVO
Crear el agente que representará a los camiones y asignar sus variables (atributos).

### 🛠️ CONFIGURACIÓN PASO A PASO
1.  En panel `Projects`, click derecho en `Agent Types` → `New Agent Type…`
2.  **Name:** `Truck`.
3.  En el canvas de **`Truck`**, agregar las siguientes `Variable` (Paleta `Agent`):

| Variable | Tipo | Valor Inicial | Descripción |
| :--- | :--- | :--- | :--- |
| `proveedor` | String | `""` | Nombre del proveedor |
| `region` | String | `""` | Norte o Sur |
| `destinoOEM` | String | `""` | Destino final del embarque |
| `pallets` | int | `0` | Cantidad de pallets |
| `tEntradaSistema` | double | `0.0` | Hora de entrada al CEDIS |
| `tSalidaSistema` | double | `0.0` | Hora de salida del CEDIS |

### ✅ CHECKLIST DE VERIFICACIÓN
* Agente **`Truck`** creado.
* Las 6 variables están agregadas con el tipo de dato correcto.

---

# PARTE 2: FLUJO DE ENTRADA Y PROCESOS CENTRALES

## 6. PASO 4 – FUENTES DE CAMIONES Y ATRIBUTOS

### 🎯 OBJETIVO
Generar camiones desde los 3 proveedores y asignarles atributos iniciales.

### 🛠️ CONFIGURACIÓN PASO A PASO
En el agente **`Main`**, arrastrar 3 bloques **`Source`** y un bloque **`Enter`**. Conectar los `Source` al `Enter`.

#### 6.1. Configuración de Sources

| Source Name | Agent type | Arrival Rate |
| :--- | :--- | :--- |
| `SRC_LEAR_NORTE` | `Truck` | `uniform(2, 4)` |
| `SRC_CONDUMEX_SUR` | `Truck` | `uniform(1, 3)` |
| `SRC_MAGNA_SUR` | `Truck` | `uniform(1.5, 3.5)` |

#### 6.2. Código `On exit`
En el campo **On exit (action)** de cada `Source`, configurar el camión:

```java
// Ejemplo para SRC_LEAR_NORTE
agent.proveedor = "LEAR";
agent.region = "NORTE";
agent.pallets = 26;
agent.destinoOEM = "GM_SILAO"; // Asignación temporal
agent.tEntradaSistema = time(); // Registro de hora

```

### ✅ CHECKLIST DE VERIFICACIÓN

-   3 Sources configurados con tasas y Agent type `Truck`.
    
-   `On exit` code correcto en cada Source, registrando la hora de entrada.
    

----------

## 7. PASO 5 – DESCARGA EN ANDENES

### 🎯 OBJETIVO

Modelar la espera y el uso limitado de los andenes para el proceso de descarga.

### 🛠️ CONFIGURACIÓN PASO A PASO

1.  **Cola de Espera:** `ENTER_CEDIS` → **`Q_ANDEN`** (`Queue`, Capacity: `unlimited`).
    
2.  **Captura de Andén:** `Q_ANDEN` → **`SEIZE_ANDEN`** (`Seize`, Resource sets: **`docks`**).
    
3.  **Descarga:** `SEIZE_ANDEN` → **`UNLOAD`** (`Delay`, Delay time: **`triangular(0.3, 0.6, 1.0)`**).
    
4.  **Liberación de Andén:** `UNLOAD` → **`RELEASE_ANDEN`** (`Release`, Resource sets: **`docks`**).
    

### ✅ CHECKLIST DE VERIFICACIÓN

-   Bloques `Queue`, `Seize`, `Delay` y `Release` conectados correctamente.
    
-   `Seize` y `Release` usan el recurso **`docks`**.
    

----------

## 8. PASO 6 – RUTEO Y PROCESAMIENTO DE RECEPCIÓN

### 🎯 OBJETIVO

Implementar la decisión por región y el uso de montacargas para el manejo interno.

### 🛠️ CONFIGURACIÓN PASO A PASO

1.  **Decisión de Ruteo:** `RELEASE_ANDEN` → **`ROUTE_RECEPCION`** (`SelectOutput`, Type: `Condition`).
    
    -   **Condición (salida 0):** `agent.region.equals("NORTE")`
        
2.  **Captura de Montacargas:** Ambas ramas del `SelectOutput` se conectan a un solo bloque:
    
    -   **Bloque:** **`SEIZE_FORKLIFT_RECEPCION`** (`Seize`, Resource sets: **`forklifts`**).
        
3.  **Procesamiento por Región:** Conectar `SEIZE_FORKLIFT_RECEPCION` a un `SelectOutput` con la misma condición de ruteo.
    
    -   **Flujo Norte:** → **`PROC_RECEPCION_NORTE`** (`Delay`, Delay time: **`exponential(0.25)`**).
        
    -   **Flujo Sur:** → **`PROC_RECEPCION_SUR`** (`Delay`, Delay time: **`exponential(0.2)`**).
        
    -   _(Nota: Se asignan tiempos de ejemplo para que el modelo funcione.)_
        

### ✅ CHECKLIST DE VERIFICACIÓN

-   `ROUTE_RECEPCION` usa la variable `agent.region`.
    
-   Se implementa el bloque **`SEIZE_FORKLIFT_RECEPCION`** utilizando el recurso `forklifts`.
    

----------

## 9. PASO 7 – CLASIFICACIÓN Y CROSS-DOCKING (Lógica de Decisión Central)

### 🎯 OBJETIVO

Simular el proceso de clasificación y decidir si el material pasa directo a embarque (65%) o a almacenamiento (35%).

### 🛠️ CONFIGURACIÓN PASO A PASO

1.  **Liberación de Montacargas (Interno):** Conectar `PROC_RECEPCION_NORTE` y `PROC_RECEPCION_SUR` a:
    
    -   **Bloque:** **`RELEASE_FORKLIFT_RECEPCION`** (`Release`, Resource sets: **`forklifts`**).
        
2.  **Clasificación (Sorting):** `RELEASE_FORKLIFT_RECEPCION` →
    
    -   **Bloque:** **`SORTING`** (`Delay`, Delay time: **`normal(0.1, 0.05)`**).
        
3.  **Decisión de Ruteo Central:** `SORTING` →
    
    -   **Bloque:** **`ROUTE_ALMACENAMIENTO`** (`SelectOutput`, Type: `Condition`).
        
    -   **Condición (Salida 0: Cross-Docking):** `random() < 0.65`
        

#### 9.1. Flujo Cross-Docking (Salida 0)

-   **Destino:** `ROUTE_ALMACENAMIENTO` (Salida 0) → Bloque **`ROUTE_EMBARQUE_FINAL`** (Ver Paso 10).
    

#### 9.2. Flujo a Almacenamiento/Kitting (Salida 1)

-   **Almacenamiento:** `ROUTE_ALMACENAMIENTO` (Salida 1) → **`SEIZE_FORKLIFT_ALMACEN`** (`Seize`, `forklifts`) → **`BUFFER_ESTRATEGICO`** (`Delay`, Delay time: **`uniform(4, 12)`** hrs).
    
-   **Kitting:** `BUFFER_ESTRATEGICO` → **`RELEASE_FORKLIFT_ALMACEN`** (`Release`, `forklifts`) → **`PROC_KITTING`** (`Delay`, Delay time: **`triangular(1.0, 1.5, 2.0)`**).
    

### ✅ CHECKLIST DE VERIFICACIÓN

-   **`ROUTE_ALMACENAMIENTO`** implementa la lógica 65/35.
    
-   El flujo de almacenamiento usa bloques **`Seize`** y **`Release`** de `forklifts`.
    

----------

## 10. PASO 8 – DESPACHO Y FIN DEL MODELO

### 🎯 OBJETIVO

Rutar las entidades hacia los 3 clientes, registrar el tiempo de ciclo final y salir del sistema.

### 🛠️ CONFIGURACIÓN PASO A PASO

1.  **Punto de Unión:** Conectar el flujo de Cross-Docking (Salida 0 del Paso 7) y el flujo de Kitting (`PROC_KITTING`) al bloque:
    
    -   **Bloque:** **`ROUTE_EMBARQUE_FINAL`** (`SelectOutput`, Type: `Condition`).
        
    -   **Condición (Ejemplo para GM Silao):** `agent.destinoOEM.equals("GM_SILAO")` (Usar múltiples salidas para los 3 clientes).
        
2.  **Proceso de Carga y KPI:** Las salidas del ruteo se unen en un último Delay de carga:
    
    -   **Bloque:** **`LOAD_TRUCK`** (`Delay`, Delay time: **`uniform(0.5, 1.0)`**).
        
    -   **Código `On exit` (Cálculo Final):**
        

Java

```
// Registrar el tiempo final
agent.tSalidaSistema = time();
double ciclo = agent.tSalidaSistema - agent.tEntradaSistema;

// Aquí se debe escribir el código para registrar el 'ciclo' en un Dataset o Statistics
// Ej: root.ds_cycle_time.add(ciclo);

```

3.  **Salida del Modelo:** `LOAD_TRUCK` →
    
    -   **Bloque:** **`SALIDA_CEDIS`** (`Sink`).
        

### ✅ CHECKLIST DE VERIFICACIÓN

-   Ruteo final configurado para los 3 destinos.
    
-   Bloque `LOAD_TRUCK` registra la hora de salida (`tSalidaSistema`).
    
-   El flujo finaliza en el `Sink`.
    

----------

# PARTE 3: ANÁLISIS Y DOCUMENTACIÓN

## 11. PASO 9 – CREAR DASHBOARD Y KPIs (Objetivos 7 y 9)

### 🎯 OBJETIVO

Crear los elementos visuales necesarios para mostrar la utilización de recursos y el rendimiento del sistema.

### 🛠️ CONFIGURACIÓN PASO A PASO

1.  **Colección de Datos (En Main):** Crear un `Dataset` o `Statistics` para recolectar el tiempo de ciclo (donde se escribió `ds_cycle_time.add(ciclo)`).
    
2.  **Gráficos de Utilización (Plots):** Arrastrar un `Plot` (Paleta `Analysis`).
    
    -   **Serie 1:** `docks.utilization()` (Utilización de Andenes)
        
    -   **Serie 2:** `forklifts.utilization()` (Utilización de Montacargas)
        
3.  **Gráfico de Tiempo de Ciclo:** Arrastrar un `Bar Chart`.
    
    -   **Valor:** El promedio del `Dataset` de tiempo de ciclo.
        
4.  **Throughput:** Usar un elemento `Text` y escribir `SALIDA_CEDIS.count() / 7` (para Throughput semanal en promedio diario).
    

----------

## 12. PASO 10 – PUBLICAR EL MODELO (Objetivo 8)

### 🎯 OBJETIVO

Exportar el modelo para su ejecución y demostración en la nube.

### 🛠️ CONFIGURACIÓN PASO A PASO

1.  **Ejecutar:** Verificar que el modelo corre sin errores.
    
2.  **Exportar:** Ir a menú **Run → Export to AnyLogic Cloud...**
    
3.  **Documentar:** Incluir el enlace de la publicación en la documentación de la entrega.
    

----------

> Written with [StackEdit](https://stackedit.io/).
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTE4ODM4MzE5ODhdfQ==
-->