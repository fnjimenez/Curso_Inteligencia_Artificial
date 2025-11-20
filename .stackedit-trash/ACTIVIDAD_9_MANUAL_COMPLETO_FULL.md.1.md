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


# 🟦 PASO 1 — CONFIGURACIÓN INICIAL DEL MODELO EN ANYLOGIC  
## (Incluye cómo activar METROS, cómo crear el agente MAIN y cómo insertar el layout)

---

## 🔧 1.1 Crear un proyecto nuevo correctamente

1. Abrir AnyLogic  
2. Ir a **File → New Model…**  
3. Asignar nombre:  
   **CEDIS_SanBartolo_ApellidoNombre**  
4. Presionar **Finish**

---

## 🔧 1.2 Si NO aparece la pantalla de propiedades (Main properties)

A veces AnyLogic NO muestra de inmediato las propiedades del agente principal.  
Para reactivarlas:

1. En la parte superior derecha, clic en:

   **View → Properties**

2. Si tampoco aparece el panel del árbol del proyecto:

   **View → Projects**

Con eso recuperas la vista completa.

---

## 🔧 1.3 Configurar las unidades del modelo (HORAS y METROS)

1. Selecciona el agente **Main** en el panel izquierdo (Projects)  
2. En la vista de **Properties**, busca:

   - **Time units → hours**  
   - **Length units → meters**

3. Si NO aparece la opción de *Length units*:

   - Da clic en el botón **Advanced properties**  
   - Se desplegarán más opciones y aparecerá la sección de Longitud

> 📌 *Esto garantiza que todo el layout que dibujes use metros reales.*

---

## 🔧 1.4 Si el lienzo aparece demasiado pequeño o enorme

En la parte inferior derecha:

- Ajusta el ZOOM con la rueda del mouse  
- O usa:  
  **View → Zoom to fit**

> Esto es útil si el rectángulo de la nave se oculta fuera de la pantalla.

---

# 🟦 PASO 2 — CREAR EL LAYOUT BASE DEL CEDIS

## 🔧 2.1 Crear la nave principal (rectángulo grande)

1. Ir a:  
   **Palette → Presentation → Rectangle**
2. Dibujar un rectángulo grande (ejemplo recomendado):  
   **250 m de ancho x 100 m de alto**
3. En propiedades del rectángulo:
   - Name: `NavePrincipal`
   - Width: `250`
   - Height: `100`
   - Fill color: *desactivado* (sólo borde)

---

## 🔧 2.2 Agregar zonas internas

1. Insertar más rectángulos para cada área:
   - Recepción Norte
   - Recepción Sur
   - Sorting
   - Buffer Estratégico
   - Kitting / Valor Agregado
   - Embarques GM Silao
   - Embarques GM SLP
   - Embarques BMW SLP

2. Ir a Presentation → Text para poner los nombres interiores.

> 📌 *No importa si no queda perfecto: el objetivo es tener zonas identificables para la simulación.*

---

# 🟦 PASO 3 — AGREGAR LA IMAGEN DEL LAYOUT COMO FONDO (MUY IMPORTANTE)

## 🔧 3.1 Insertar la imagen técnica del layout

1. Ir a:  
   **Palette → Presentation → Image**
2. Seleccionar la imagen proporcionada del CEDIS  
3. En propiedades → ajustar:
   - **Maintain proportions = true**
   - **Width y Height en METROS** (según corresponda)
4. En propiedades del lienzo:
   - **Send to back** (para que quede como fondo)
   - Lock (candado) para que no se mueva al trabajar

---

## 🔧 3.2 Ajustar la imagen para que coincida con la escala del modelo

1. Observa el tamaño de la nave (por ejemplo, ancho ≈ 250 m)  
2. Ajusta manualmente la imagen a ese tamaño:

   - Si mide 250 m de ancho → ponle Width = 250  
   - Ajusta Height proporcionalmente  

3. Si necesitas moverla:

   - Selecciona la imagen  
   - Usa las flechas o arrastra con el mouse

---

# 🟦 PASO 4 — ¿Cómo usar la imagen como guía sin afectar la simulación?

La imagen NO interviene en la simulación, sólo sirve como **referencia visual**.

Para ello:

1. Ponle nombre: `LayoutFondo`
2. Activa la opción:  
   **Ignore mouse events (no interactúa con clicks)**  
3. Colócala siempre al **fondo** del lienzo

---

# ✔ LISTO
Con esto ya tienes:

- El modelo configurado correctamente  
- Las unidades en metros  
- El agente Main activo  
- El layout importado como imagen realista  
- La nave y las zonas internas listas para empezar la lógica del flujo  

Ahora puedes continuar con la construcción del flowchart.


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
eyJoaXN0b3J5IjpbLTE5ODMyNzAwNDVdfQ==
-->