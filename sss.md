
Aquí va, profesor 👇  
Te dejo la **Actividad 9** en **formato Markdown**, pensada como:

-   **Plantilla de entrega**
    
-   **Manual paso a paso del estudiante**
    
-   Con **objetivo de cada paso**, **explicación lógica**, **configuraciones** y **código Java** donde aplica.
    

La puedes usar tal cual en Moodle / PDF / Word / GitHub.

----------

```markdown
# 🟦 ACTIVIDAD 9 – MODELADO DEL CEDIS AUTOMOTRIZ SAN BARTOLO EN ANYLOGIC  
### Curso: Logística y Cadena de Valor  
### Unidad: Sistemas de Almacenamiento y CEDIS  
### Versión: 2025

---

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

> 💡 **Nota:** Este layout servirá como “mapa visual”; la lógica de simulación se maneja en el flowchart.

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
4. Agregar atributos (en la pestaña **Agent → Variables**):

```java
// En el agente Truck:
String proveedor;
String region;
String destinoOEM;
int pallets;
double tEntradaSistema;
double tSalidaSistema;

```

> Estos atributos se usarán más adelante para lógica de ruteo y KPIs.

----------

# 10. PASO 4 – DEFINIR FUENTES DE CAMIONES (PROVEEDORES)

### 🎯 Objetivo

Modelar la llegada de camiones desde distintos proveedores/regiones.

### 🧠 Lógica

Usaremos 3 `Source` para simplificar:

-   Proveedor **LEAR** (Región Norte)
    
-   Proveedor **CONDUMEX** (Región Sur)
    
-   Proveedor **MAGNA** (Región Sur)
    

Cada `Source` generará agentes `Truck`.

### 🛠️ Configuración

En `Main` → arrastrar desde **Process Modeling Library**:

1.  `Source` → renombrar a `SRC_LEAR_NORTE`
    
    -   `Agent type`: `Truck`
        
    -   `Arrival mode`: `Rate`
        
    -   `Rate`: `uniform(2, 4)` // camiones por hora (ajustable)
        
    -   En `On exit`:
        

```java
agent.proveedor = "LEAR";
agent.region = "NORTE";
agent.pallets = 26;
agent.tEntradaSistema = time();

```

2.  `Source` → `SRC_CONDUMEX_SUR`
    
    -   `Rate`: `uniform(1, 3)`
        
    -   `On exit`:
        

```java
agent.proveedor = "CONDUMEX";
agent.region = "SUR";
agent.pallets = 24;
agent.tEntradaSistema = time();

```

3.  `Source` → `SRC_MAGNA_SUR`
    
    -   `Rate`: `uniform(1.5, 3.5)`
        
    -   `On exit`:
        

```java
agent.proveedor = "MAGNA";
agent.region = "SUR";
agent.pallets = 28;
agent.tEntradaSistema = time();

```

4.  Unir los 3 `Source` hacia un único bloque `Enter` llamado: `ENTER_CEDIS`.
    

----------

# 11. PASO 5 – GESTIÓN DE ANDENES DE RECEPCIÓN

### 🎯 Objetivo

Simular que los camiones esperan si no hay andén disponible, ocupan un andén mientras descargan y luego lo liberan.

### 🧠 Lógica

Usaremos:

-   `Queue` → cola de camiones esperando andén.
    
-   `Seize` → asigna un recurso “andén”.
    
-   `Delay` → representa el tiempo de descarga.
    
-   `Release` → libera el andén.
    

### 🛠️ Configuración

1.  Crear un `ResourcePool` en `Main`:
    

```java
// ResourcePool docks
ResourcePool docks;

```

En propiedades de `docks`:

-   `Capacity`: `24` // total de andenes
    

2.  En el flowchart de `Main` agregar:
    

-   `Queue` → `Q_ANDEN`
    
-   `Seize` → `SEIZE_ANDEN`
    
    -   `Resource pool`: `docks`
        
-   `Delay` → `UNLOAD`
    
    -   `Delay time`: `triangular(0.3, 0.6, 1.0)` // horas
        
-   `Release` → `RELEASE_ANDEN`
    
    -   `Resource pool`: `docks`
        

3.  Conectar:  
    `ENTER_CEDIS → Q_ANDEN → SEIZE_ANDEN → UNLOAD → RELEASE_ANDEN`
    

----------

# 12. PASO 6 – RUTEO HACIA RECEPCIÓN NORTE / SUR

### 🎯 Objetivo

Enviar camiones a la zona de recepción según su región de origen.

### 🧠 Lógica

Si `agent.region == "NORTE"` → Recepción Norte  
Si `agent.region == "SUR"` → Recepción Sur

### 🛠️ Configuración

1.  Agregar un bloque `SelectOutput` → `ROUTE_RECEPCION`
    
    -   `Condition type`: `By code`
        
    -   Código:
        

```java
if (agent.region.equals("NORTE")) return 0; // rama 0 → Recepción Norte
else return 1;                              // rama 1 → Recepción Sur

```

2.  Conectar `RELEASE_ANDEN → ROUTE_RECEPCION`.
    
3.  Crear dos `Delay` (simbolizan tiempo de maniobra y acomodo):
    

-   `DELAY_RECEP_NORTE`
    
-   `DELAY_RECEP_SUR`
    

4.  Conectar:
    

-   Rama 0 de `ROUTE_RECEPCION` → `DELAY_RECEP_NORTE`
    
-   Rama 1 de `ROUTE_RECEPCION` → `DELAY_RECEP_SUR`
    

----------

# 13. PASO 7 – PROCESO EN SORTING Y BUFFER ESTRATÉGICO

### 🎯 Objetivo

Simular la clasificación de pallets y su paso a un buffer estratégico.

### 🧠 Lógica

Todos los camiones, después de la recepción, alimentan el área de **SORTING**, donde se clasifican y se genera el flujo de pallets hacia el **BUFFER**.

### 🛠️ Configuración

1.  Crear un bloque `Delay` → `SORTING_PROCESS`
    
    -   `Delay time`: `triangular(0.2, 0.4, 0.8)` horas
        
2.  Crear un bloque `Delay` → `BUFFER_TIME`
    
    -   `Delay time`: `triangular(1, 3, 6)` horas
        
3.  Conectar:
    

-   `DELAY_RECEP_NORTE → SORTING_PROCESS`
    
-   `DELAY_RECEP_SUR → SORTING_PROCESS`
    
-   `SORTING_PROCESS → BUFFER_TIME`
    

> 💡 Opcional: si se quiere más detalle, se pueden agregar colas y recursos específicos para sorting.

----------

# 14. PASO 8 – ASIGNACIÓN DE DESTINO OEM (GM SILAO, GM SLP, BMW)

### 🎯 Objetivo

Modelar hacia qué cliente final se dirigirán los pallets del CEDIS.

### 🧠 Lógica

Supondremos una distribución aproximada:

-   55 % → GM Silao
    
-   33 % → GM SLP
    
-   12 % → BMW SLP
    

### 🛠️ Configuración

1.  Agregar un `SelectOutput` → `DESTINO_OEM`
    
    -   `Condition type`: `By code`
        
    -   Código:
        

```java
double r = uniform(0, 1);

if (r < 0.55) {
    agent.destinoOEM = "GM_SILAO";
    return 0; // rama 0
} else if (r < 0.55 + 0.33) {
    agent.destinoOEM = "GM_SLP";
    return 1; // rama 1
} else {
    agent.destinoOEM = "BMW_SLP";
    return 2; // rama 2
}

```

2.  Conectar: `BUFFER_TIME → DESTINO_OEM`.
    
3.  Crear 3 `Delay`:
    

-   `PREPARE_GM_SILAO`
    
-   `PREPARE_GM_SLP`
    
-   `PREPARE_BMW_SLP`
    

4.  Conectar cada rama de `DESTINO_OEM` a su `Delay` correspondiente.
    
5.  (Opcional) Agregar un `Sink` final (`EXIT_CEDIS`) donde terminan todos los camiones:
    

-   `PREPARE_GM_SILAO → EXIT_CEDIS`
    
-   `PREPARE_GM_SLP → EXIT_CEDIS`
    
-   `PREPARE_BMW_SLP → EXIT_CEDIS`
    

En `EXIT_CEDIS → On exit` puedes actualizar métricas:

```java
palletsProcessed += agent.pallets;
agent.tSalidaSistema = time();
double tCiclo = agent.tSalidaSistema - agent.tEntradaSistema;
// promedio móvil
avgCycleTime = 0.9 * avgCycleTime + 0.1 * tCiclo;

```

----------

# 15. PASO 9 – RECURSOS ADICIONALES: MONTACARGAS Y OPERADORES (OPCIONAL)

### 🎯 Objetivo

Agregar realismo modelando el uso de montacargas y/o operadores.

### 🧠 Lógica

Puedes usar un `ResourcePool forklifts` que se ocupe en ciertas etapas (sorting, buffer, kitting).

### 🛠️ Configuración básica

En `Main`:

```java
ResourcePool forklifts;
ResourcePool workers;

```

-   `forklifts.capacity = 12;`
    
-   `workers.capacity = 20;`
    

Luego, en bloques clave (por ejemplo `SORTING_PROCESS` o `BUFFER_TIME`), puedes usar:

-   `Seize` (forklifts + workers) → `Delay` → `Release`.
    

----------

# 16. PASO 10 – VARIABLES Y KPIs

### 🎯 Objetivo

Registrar resultados cuantitativos del modelo.

### 🧠 Lógica

Necesitamos al menos:

-   `palletsProcessed`
    
-   `avgCycleTime`
    
-   `forkUtilization` (si se usan montacargas)
    
-   `dockUtilization`
    

### 🛠️ Configuración

En `Main` → pestaña **Variables**:

```java
int palletsProcessed = 0;
double avgCycleTime = 0;
double dockUtilization = 0;
double forkUtilization = 0;

```

Actualizar:

-   En evento periódico (por ejemplo, cada 1 hora simulada):
    

```java
dockUtilization = docks.utilization();
forkUtilization = forklifts.utilization();

```

-   En `EXIT_CEDIS → On exit` ya mostramos ejemplo para `palletsProcessed` y `avgCycleTime`.
    

----------

# 17. PASO 11 – PUBLICACIÓN EN ANYLOGIC CLOUD

### 🎯 Objetivo

Exportar el modelo al entorno web y obtener un enlace para compartir.

### 🛠️ Pasos

1.  En AnyLogic → `File → Export → To AnyLogic Cloud…`
    
2.  Iniciar sesión (Google o cuenta AnyLogic).
    
3.  Asignar nombre al modelo:  
    `CEDIS_SanBartolo_ApellidoNombre`
    
4.  Seleccionar si será **Public** o **Private**.
    
5.  Finalizar exportación.
    
6.  En el navegador, abrir el modelo en AnyLogic Cloud.
    
7.  Probar `Run`.
    
8.  Copiar el enlace del modelo.
    

Pega ese enlace en el siguiente campo:

> 🔗 **Enlace al modelo en AnyLogic Cloud:**  
> `https://cloud.anylogic.com/...`

----------

# 18. EVIDENCIAS A ENTREGAR EN LA ACTIVIDAD 9

El reporte de la actividad debe incluir:

1.  **Portada** con tus datos.
    
2.  **Descripción breve del CEDIS San Bartolo** (1/2 cuartilla).
    
3.  **Capturas de pantalla** de:
    
    -   Layout del CEDIS en AnyLogic.
        
    -   Diagrama de flujo (flowchart).
        
    -   Ejecución del modelo (en tiempo de simulación).
        
4.  **Tabla con KPIs** (pallets procesados, tiempos, utilización).
    
5.  **Enlace a AnyLogic Cloud**.
    
6.  **Conclusión personal** sobre:
    
    -   ¿El CEDIS funciona adecuadamente con los parámetros utilizados?
        
    -   ¿Qué cuellos de botella detectas?
        
    -   ¿Qué cambiarías (número de andenes, montacargas, horarios, etc.)?
        

----------

# 19. ESPACIO PARA CONCLUSIÓN DEL ESTUDIANTE

> ✏️ **Conclusión del estudiante (mínimo 10–12 líneas):**  
> (Describa lo que aprendió, las dificultades principales, qué mejoraría del modelo y cómo se relaciona este ejercicio con un CEDIS real.)

----------

# 20. CHECKLIST FINAL (AUTOEVALUACIÓN DEL ALUMNO)

Marca con ✅ lo que cumpliste:

-   Configuré el modelo con unidades de horas y metros.
    
-   Creé el agente `Truck` con sus atributos.
    
-   Dibujé el layout básico del CEDIS San Bartolo.
    
-   Implementé el flujo de camiones (Sources → Enter → Queue → Seize → Delay → Release).
    
-   Programé el ruteo hacia recepciones norte/sur según región.
    
-   Modelé sorting y buffer.
    
-   Configuré el ruteo hacia GM Silao, GM SLP y BMW SLP.
    
-   Actualicé KPIs (pallets, tiempos, utilización).
    
-   Exporté el modelo a AnyLogic Cloud y obtuve enlace.
    
-   Redacté mi conclusión razonada.
    

----------

```

---

Si quieres, en el siguiente paso puedo:

- Ajustar el **nombre oficial** de la Actividad 9 según como venga en tu programa.  
- Adaptar la redacción al **nivel de tu grupo** (más técnico o más pedagógico).  
- Generar el **.md descargable** como hicimos con la Actividad 7.

```

> Written with [StackEdit](https://stackedit.io/).
<!--stackedit_data:
eyJoaXN0b3J5IjpbNjYxMDkyOTYwXX0=
-->