# 🟦 ACTIVIDAD 9 – MANUAL COMPLETO PARA CONSTRUIR Y SIMULAR EL CEDIS SAN BARTOLO EN ANYLOGIC
## Versión plantilla + manual del estudiante (2025)

## 1. Objetivo General
El alumno construirá de forma guiada un modelo de simulación en **AnyLogic** que represente la operación del **CEDIS Automotriz San Bartolo**, integrando:
- Flujo de proveedores
- Recepción y descarga
- Sorting y buffer estratégico
- Kitting / valor agregado
- Embarques a clientes automotrices (GM Silao, GM SLP, BMW SLP)
- Movimientos internos (montacargas)
- Rutas, recursos y KPIs

## 2. Requisitos Previos
- Haber completado Actividad 6 (diseño del sistema de almacenamiento)
- Tener lista la Actividad 7 (análisis de redes e industrias)
- AnyLogic instalado (versión PLE o University)
- Conocimientos básicos de bloques: Source, Queue, Seize, Delay, Release, MoveTo, Sink

---

# 🟧 3. Estructura Oficial del Proyecto
El proyecto se dividirá en cinco etapas:

1. **Creación del entorno y layout**
2. **Generación de proveedores (camiones)**
3. **Recepción y descarga**
4. **Sorting → Buffer → Kitting**
5. **Asignación de destino y Embarques**
6. **KPIs y tablero de control**

---

# 🟩 4. Etapa 1 – CREAR EL ENTORNO Y EL LAYOUT

## 🎯 Objetivo
Construir el layout del CEDIS en AnyLogic utilizando las zonas internas y externas proporcionadas.

## 🔧 Pasos
1. Abrir AnyLogic → **New Model**
2. Configurar unidades:
   - *Time units:* HOURS
   - *Length units:* METERS
3. Dibujar la nave rectangular
4. Agregar áreas internas:
   - Recepción Norte
   - Recepción Sur
   - Sorting
   - Buffer Estratégico
   - Kitting / Valor Agregado
   - Embarques (GM Silao, GM SLP, BMW)

---

# 🟦 5. Etapa 2 – GENERAR PROVEEDORES

## 🎯 Objetivo
Modelar proveedores que llegan como camiones al CEDIS desde diferentes regiones.

## 🔧 Pasos
En **Main**:
1. Crear un Agent Type:
   - Name: `Truck`
   - Atributos:
     ```java
     string proveedor;
     string region;
     string destinoOEM;
     int pallets;
     ```

2. Agregar 3 bloques `Source`:
   - `SRC_LEAR_NORTE`
   - `SRC_CONDUMEX_SUR`
   - `SRC_MAGNA_SUR`

---

# 🟨 6. Etapa 3 – RECEPCIÓN Y DESCARGA

## 🎯 Objetivo
Simular el proceso de recepción → asignación de andén → descarga → liberación.

## 🔧 Pasos
1. `Enter` → `ENTER_CEDIS`
2. `Queue` → `Q_ANDEN`
3. `Seize` → recurso `docks`
4. `MoveTo` → zona Recepción Norte/Sur
5. `Delay UNLOAD`
6. `Release`

---

# 🟥 7. Etapa 4 – SORTING → BUFFER → KITTING

## 🎯 Objetivo
Procesar materiales al llegar, clasificarlos, almacenarlos temporalmente y prepararlos.

## 🔧 Pasos
1. `Delay SORTING_PROCESS`
2. `MoveTo BUFFER`
3. `Delay BUFFER_TIME`
4. `MoveTo KITTING`
5. `Delay KITTING_PROCESS` (opcional)

---

# 🟧 8. Etapa 5 – ASIGNACIÓN DE DESTINO Y EMBARQUES

## 🎯 Objetivo
Asignar cada camión al cliente apropiado: GM Silao, GM SLP o BMW.

## 💻 Código Java:
```java
double r = uniform(0,1);
if (r < 0.55) return 0;
else if (r < 0.88) return 1;
else return 2;
```

---

# 🟦 9. Etapa 6 – RECURSOS Y KPIs

## 🔧 Recursos
```java
forklifts.capacity = 12;
docks.capacity = 24;
```

## KPIs
```java
int palletsProcessed = 0;
```

---

# 🟩 10. Validación
Verificar:
- Flujo continuo
- Sin congestión crítica
- Tiempos razonables

---

# 🟦 11. Entregables
- Modelo AnyLogic exportado
- Capturas
- Archivo MD final

---

# 🟩 12. Conclusión
Esta actividad integra:
- Diseño conceptual
- Análisis de red
- Simulación operativa

Fin del documento.
<!--stackedit_data:
eyJoaXN0b3J5IjpbMzU2NTUxNTA0XX0=
-->