<!DOCTYPE html>
<html>

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>proyecto_ia_Sistema_Inteligente.md</title>
  <link rel="stylesheet" href="https://stackedit.io/style.css" />
</head>

<body class="stackedit">
  <div class="stackedit__html">


  
  
  <title>fundamentos_proyecto_ia.md</title>
  


  <div class="stackedit__html"><h1 id="🤖-proyecto-completo-sistema-de-alarma-inteligente-con-ia">🤖 PROYECTO COMPLETO: SISTEMA DE ALARMA INTELIGENTE CON IA</h1>
<h2 id="curso-de-inteligencia-artificial---actividades-8-y-9">Curso de Inteligencia Artificial - Actividades 8 y 9</h2>
<hr>
<h1 id="parte-1-fundamentos-teóricos">PARTE 1: FUNDAMENTOS TEÓRICOS</h1>
<h2 id="robótica---¿por-qué-este-proyecto-es-robótico">1. ROBÓTICA - ¿POR QUÉ ESTE PROYECTO ES ROBÓTICO?</h2>
<h3 id="definición-de-robótica">Definición de Robótica</h3>
<p>La robótica es la rama de la ingeniería que estudia sistemas capaces de:</p>
<ul>
<li><strong>Percibir</strong> el entorno (sensores)</li>
<li><strong>Procesar</strong> información (decisiones inteligentes)</li>
<li><strong>Actuar</strong> sobre el entorno (actuadores)</li>
</ul>
<h3 id="nuestro-sistema-cumple-con-los-3-componentes">Nuestro Sistema Cumple con los 3 Componentes</h3>
</div><table>
<thead>
<tr>
<th>Componente Robótico</th>
<th>En Nuestro Proyecto</th>
</tr>
</thead>
<tbody>
<tr>
<td><strong>PERCEPCIÓN</strong></td>
<td>PIR (movimiento), LDR (luz), Micrófono (ruido)</td>
</tr>
<tr>
<td><strong>PROCESAMIENTO</strong></td>
<td>Red Neuronal + Lógica Difusa en ESP32</td>
</tr>
<tr>
<td><strong>ACCIÓN</strong></td>
<td>Buzzer (alarma), LED RGB (visual), Telegram (notificación)</td>
</tr>
</tbody>
</table><p><strong>Conclusión</strong>: Es un sistema robótico autónomo sin necesidad de servomotores.</p>
<hr>
<h2 id="redes-neuronales-actividad-8">2. REDES NEURONALES (ACTIVIDAD 8)</h2>
<h3 id="¿qué-es-una-red-neuronal">¿Qué es una Red Neuronal?</h3>
<p>Modelo computacional inspirado en el cerebro humano que <strong>aprende patrones</strong> a partir de datos.</p>
<h3 id="arquitectura-de-nuestra-red">Arquitectura de Nuestra Red</h3>
<pre><code>ENTRADA (4)  →  OCULTA 1 (32)  →  OCULTA 2 (16)  →  SALIDA (4)
                    ReLU              ReLU           Softmax
                  Dropout 30%
</code></pre>
<p><strong>Capas:</strong></p>
<ol>
<li><strong>Entrada</strong>: 4 neuronas (movimiento, luz, ruido, hora)</li>
<li><strong>Oculta 1</strong>: 32 neuronas con ReLU + Dropout 30%</li>
<li><strong>Oculta 2</strong>: 16 neuronas con ReLU</li>
<li><strong>Salida</strong>: 4 neuronas con Softmax (Normal, Baja, Media, Alta)</li>
</ol>
<h3 id="componentes-clave">Componentes Clave</h3>
<h4 id="a-neurona-artificial">a) Neurona Artificial</h4>
<p>Una neurona recibe entradas, las multiplica por pesos, suma un bias y aplica una función de activación.</p>
<pre><code>Entrada1 × Peso1 ─┐
Entrada2 × Peso2 ─┼─→ Suma + Bias → Activación → Salida
Entrada3 × Peso3 ─┘
</code></pre>
<h4 id="b-función-de-activación-relu">b) Función de Activación ReLU</h4>
<pre><code>ReLU(x) = max(0, x)
</code></pre>
<ul>
<li>Si x &lt; 0 → Salida = 0</li>
<li>Si x &gt; 0 → Salida = x</li>
<li><strong>Ventaja</strong>: Evita el problema del gradiente desvaneciente</li>
</ul>
<h4 id="c-dropout-30">c) Dropout (30%)</h4>
<p>Durante el entrenamiento, apaga aleatoriamente el 30% de las neuronas para evitar <strong>overfitting</strong> (memorización).</p>
<h4 id="d-softmax-capa-de-salida">d) Softmax (Capa de Salida)</h4>
<p>Convierte valores en probabilidades que suman 1.0:</p>
<p><strong>Ejemplo:</strong></p>
<ul>
<li>Valores crudos: [2.3, 1.5, 0.8, 0.2]</li>
<li>Softmax: [0.65, 0.24, 0.09, 0.02] ← Suma = 1.0</li>
</ul>
<h3 id="proceso-de-entrenamiento">Proceso de Entrenamiento</h3>
<ol>
<li><strong>Dataset Sintético</strong>: Generamos 1,500 ejemplos de eventos</li>
<li><strong>Etiquetado Inteligente</strong>: Reglas contextuales definen niveles de alerta</li>
<li><strong>Normalización</strong>: StandardScaler escala datos entre 0-1</li>
<li><strong>Entrenamiento</strong>: 50 épocas con validación cruzada</li>
<li><strong>Evaluación</strong>: Medimos accuracy en datos de prueba</li>
</ol>
<h3 id="¿por-qué-funciona">¿Por Qué Funciona?</h3>
<p>La red aprende relaciones no lineales entre variables:</p>
<ul>
<li><strong>Movimiento + Noche + Hora Sospechosa</strong> = ALERTA ALTA (probabilidad 0.85)</li>
<li><strong>Movimiento + Ruido Alto</strong> = ALERTA MEDIA (probabilidad 0.72)</li>
<li><strong>Movimiento + Día</strong> = ALERTA BAJA (probabilidad 0.68)</li>
<li><strong>Sin Movimiento</strong> = NORMAL (probabilidad 0.95)</li>
</ul>
<hr>
<h2 id="lógica-difusa-actividad-9">3. LÓGICA DIFUSA (ACTIVIDAD 9)</h2>
<h3 id="¿qué-es-lógica-difusa">¿Qué es Lógica Difusa?</h3>
<p>Sistema que maneja <strong>incertidumbre</strong> usando valores intermedios (no solo 0 o 1).</p>
<h3 id="comparación-lógica-clásica-vs-difusa">Comparación Lógica Clásica vs Difusa</h3>
<table>
<thead>
<tr>
<th>Lógica Clásica</th>
<th>Lógica Difusa</th>
</tr>
</thead>
<tbody>
<tr>
<td>“Está oscuro: SÍ (1) o NO (0)”</td>
<td>“Está oscuro: 0.7 (bastante)”</td>
</tr>
<tr>
<td>Límite abrupto: 799=Claro, 800=Oscuro</td>
<td>Transición suave: 750=0.3, 800=0.5, 850=0.7</td>
</tr>
</tbody>
</table><h3 id="funciones-de-membresía">Funciones de Membresía</h3>
<h4 id="para-nivel-de-luz-0-4095-adc">Para Nivel de Luz (0-4095 ADC)</h4>
<ul>
<li><strong>muyOscuro</strong>: Máximo en 0-500, decrece hasta 800</li>
<li><strong>oscuro</strong>: Triángulo entre 500-1000-1500</li>
<li><strong>claro</strong>: Máximo después de 1500</li>
</ul>
<p><strong>Ejemplo</strong>: Si luz = 750 ADC</p>
<ul>
<li>muyOscuro(750) = 0.1</li>
<li>oscuro(750) = 0.6</li>
<li>claro(750) = 0.0</li>
</ul>
<h4 id="para-nivel-de-ruido-0-4095-adc">Para Nivel de Ruido (0-4095 ADC)</h4>
<ul>
<li><strong>silencio</strong>: Máximo en 0-1000, decrece hasta 1500</li>
<li><strong>normal</strong>: Triángulo entre 1000-2000-2500</li>
<li><strong>ruidoso</strong>: Máximo después de 1800</li>
</ul>
<h3 id="reglas-difusas-si-entonces">Reglas Difusas (SI-ENTONCES)</h3>
<pre><code>Regla 1: SI luz es "muy_oscuro" Y ruido es "ruidoso"  → Intensidad ALTA (90%)
Regla 2: SI luz es "muy_oscuro" Y ruido es "normal"   → Intensidad MEDIA (60%)
Regla 3: SI luz es "oscuro" Y ruido es "normal"       → Intensidad BAJA (30%)
Regla 4: SI NO hay movimiento                         → Intensidad 0%
</code></pre>
<h3 id="proceso-de-inferencia-difusa">Proceso de Inferencia Difusa</h3>
<h4 id="paso-1-fuzzificación">Paso 1: FUZZIFICACIÓN</h4>
<p>Convertir valores exactos a grados de membresía</p>
<pre><code>Entrada: Luz = 700, Ruido = 2100
↓
Luz: muyOscuro(0.4), oscuro(0.6), claro(0.0)
Ruido: silencio(0.0), normal(0.3), ruidoso(0.7)
</code></pre>
<h4 id="paso-2-evaluación-de-reglas">Paso 2: EVALUACIÓN DE REGLAS</h4>
<p>Aplicar operador MIN (AND lógico)</p>
<pre><code>Regla 1: MIN(muyOscuro(0.4), ruidoso(0.7)) = 0.4 → Intensidad ALTA
Regla 2: MIN(muyOscuro(0.4), normal(0.3))  = 0.3 → Intensidad MEDIA
Regla 3: MIN(oscuro(0.6), normal(0.3))     = 0.3 → Intensidad BAJA
</code></pre>
<h4 id="paso-3-defuzzificación">Paso 3: DEFUZZIFICACIÓN</h4>
<p>Centro de gravedad (promedio ponderado)</p>
<pre><code>Numerador   = (90 × 0.4) + (60 × 0.3) + (30 × 0.3) = 63
Denominador = 0.4 + 0.3 + 0.3 = 1.0
</code></pre><p>Intensidad Final = 63 / 1.0 = 63%<br>
</p>
<hr>
<h2 id="integración-red-neuronal--lógica-difusa">4. INTEGRACIÓN RED NEURONAL + LÓGICA DIFUSA</h2>
<h3 id="arquitectura-híbrida">Arquitectura Híbrida</h3>
<pre><code>SENSORES → RED NEURONAL → LÓGICA DIFUSA → ACTUADORES
PIR, LDR → Clasificación → Control de    → Buzzer, LED
Micrófono  (Nivel Alerta)  Intensidad      Telegram
</code></pre>
<h3 id="división-de-responsabilidades">División de Responsabilidades</h3>
<table>
<thead>
<tr>
<th>Red Neuronal</th>
<th>Lógica Difusa</th>
</tr>
</thead>
<tbody>
<tr>
<td><strong>QUÉ hacer</strong></td>
<td><strong>CÓMO hacerlo</strong></td>
</tr>
<tr>
<td>Clasifica tipo de evento</td>
<td>Controla intensidad de respuesta</td>
</tr>
<tr>
<td>Aprende de datos históricos</td>
<td>Usa reglas de expertos</td>
</tr>
<tr>
<td>Maneja patrones complejos</td>
<td>Suaviza transiciones</td>
</tr>
<tr>
<td>“¿Es amenaza alta/media/baja?”</td>
<td>“¿Qué tan fuerte alertar?”</td>
</tr>
</tbody>
</table><h3 id="ventajas-de-la-combinación">Ventajas de la Combinación</h3>
<ol>
<li><strong>Precisión</strong>: Red neuronal clasifica con exactitud</li>
<li><strong>Interpretabilidad</strong>: Lógica difusa es explicable</li>
<li><strong>Suavidad</strong>: Sin cambios bruscos en la alarma</li>
<li><strong>Robustez</strong>: Maneja ruido y variabilidad de sensores</li>
</ol>
<hr>
<h2 id="comparación-con-sistemas-tradicionales">5. COMPARACIÓN CON SISTEMAS TRADICIONALES</h2>
<h3 id="sistema-tradicional-sin-ia">Sistema Tradicional (Sin IA)</h3>
<pre class="  language-python"><code class="prism  language-python"><span class="token keyword">if</span> movimiento<span class="token punctuation">:</span>
    sonar_alarma<span class="token punctuation">(</span><span class="token punctuation">)</span>  <span class="token comment"># Siempre igual, sin contexto</span>
</code></pre>
<p><strong>Problemas:</strong></p>
<ul>
<li>❌ Falsas alarmas constantes (gatos, sombras)</li>
<li>❌ No aprende patrones</li>
<li>❌ Respuesta binaria (todo o nada)</li>
<li>❌ No considera contexto (día vs noche)</li>
</ul>
<h3 id="nuestro-sistema-con-ia">Nuestro Sistema (Con IA)</h3>
<pre class="  language-python"><code class="prism  language-python">nivel <span class="token operator">=</span> red_neuronal<span class="token punctuation">(</span>movimiento<span class="token punctuation">,</span> luz<span class="token punctuation">,</span> ruido<span class="token punctuation">,</span> hora<span class="token punctuation">)</span>
intensidad <span class="token operator">=</span> logica_difusa<span class="token punctuation">(</span>luz<span class="token punctuation">,</span> ruido<span class="token punctuation">,</span> nivel<span class="token punctuation">)</span>
alarma_inteligente<span class="token punctuation">(</span>intensidad<span class="token punctuation">)</span>
</code></pre>
<p><strong>Ventajas:</strong></p>
<ul>
<li>✅ Aprende contexto temporal</li>
<li>✅ Reduce falsas alarmas 70%</li>
<li>✅ Respuesta proporcional (0-100%)</li>
<li>✅ Mejora con más datos</li>
</ul>
<hr>
<h2 id="métricas-de-evaluación">6. MÉTRICAS DE EVALUACIÓN</h2>
<h3 id="red-neuronal">Red Neuronal</h3>
<ul>
<li><strong>Accuracy</strong>: % de clasificaciones correctas (objetivo: &gt;85%)</li>
<li><strong>Matriz de Confusión</strong>: Identifica tipos de errores</li>
<li><strong>Loss</strong>: Función de pérdida (debe disminuir cada época)</li>
</ul>
<h3 id="lógica-difusa">Lógica Difusa</h3>
<ul>
<li><strong>Respuesta Suave</strong>: Sin saltos bruscos en intensidad</li>
<li><strong>Interpretabilidad</strong>: Reglas comprensibles para humanos</li>
<li><strong>Robustez</strong>: Tolerancia a ruido de sensores (±10%)</li>
</ul>
<hr>
<h2 id="aplicaciones-reales">7. APLICACIONES REALES</h2>
<h3 id="industria">Industria</h3>
<ul>
<li>Sistemas de seguridad en fábricas</li>
<li>Monitoreo de maquinaria (detección de vibraciones anómalas)</li>
<li>Control de acceso inteligente</li>
</ul>
<h3 id="hogar">Hogar</h3>
<ul>
<li>Alarmas residenciales contextuales</li>
<li>Detección de caídas en adultos mayores</li>
<li>Monitoreo de mascotas (detecta comportamiento anormal)</li>
</ul>
<h3 id="iot">IoT</h3>
<ul>
<li>Ciudades inteligentes (alumbrado adaptativo)</li>
<li>Agricultura de precisión (riego según humedad y clima)</li>
<li>Edificios inteligentes (HVAC optimizado)</li>
</ul>
<hr>
<h1 id="parte-2-lista-de-materiales">PARTE 2: LISTA DE MATERIALES</h1>
<h2 id="componentes-necesarios">Componentes Necesarios</h2>
<table>
<thead>
<tr>
<th>Componente</th>
<th>Cantidad</th>
<th>Costo Aprox.</th>
<th>Función</th>
</tr>
</thead>
<tbody>
<tr>
<td><strong>ESP32 DevKit</strong></td>
<td>1</td>
<td>$80-120</td>
<td>Microcontrolador principal</td>
</tr>
<tr>
<td><strong>Sensor PIR HC-SR501</strong></td>
<td>1</td>
<td>$25-40</td>
<td>Detecta movimiento infrarrojo</td>
</tr>
<tr>
<td><strong>Buzzer Activo 5V</strong></td>
<td>1</td>
<td>$15-25</td>
<td>Alarma sonora</td>
</tr>
<tr>
<td><strong>LED RGB Cátodo Común</strong></td>
<td>1</td>
<td>$15-25</td>
<td>Indicador visual</td>
</tr>
<tr>
<td><strong>Fotoresistencia LDR</strong></td>
<td>1</td>
<td>$10-20</td>
<td>Mide nivel de luz</td>
</tr>
<tr>
<td><strong>Módulo Micrófono MAX4466</strong></td>
<td>1</td>
<td>$25-35</td>
<td>Detecta nivel de ruido</td>
</tr>
<tr>
<td><strong>Resistencia 220Ω</strong></td>
<td>3</td>
<td>$5</td>
<td>Limita corriente de LEDs</td>
</tr>
<tr>
<td><strong>Resistencia 10kΩ</strong></td>
<td>1</td>
<td>$2</td>
<td>Pull-down para LDR</td>
</tr>
<tr>
<td><strong>Protoboard 830 puntos</strong></td>
<td>1</td>
<td>$30-50</td>
<td>Base para conexiones</td>
</tr>
<tr>
<td><strong>Cables Dupont M-M</strong></td>
<td>15</td>
<td>$15</td>
<td>Conexiones</td>
</tr>
<tr>
<td><strong>Cable USB-C</strong></td>
<td>1</td>
<td>$20-30</td>
<td>Alimentación y programación</td>
</tr>
</tbody>
</table><p><strong>💰 COSTO TOTAL: $242-367 MXN</strong></p>
<hr>
<h1 id="parte-3-diagrama-de-conexiones-detallado">PARTE 3: DIAGRAMA DE CONEXIONES DETALLADO</h1>
<h2 id="tabla-de-conexiones">Tabla de Conexiones</h2>
<table>
<thead>
<tr>
<th>Componente</th>
<th>Pin Componente</th>
<th>Pin ESP32</th>
<th>Notas</th>
</tr>
</thead>
<tbody>
<tr>
<td><strong>PIR</strong></td>
<td>VCC</td>
<td>3.3V</td>
<td>No usar 5V</td>
</tr>
<tr>
<td><strong>PIR</strong></td>
<td>GND</td>
<td>GND</td>
<td></td>
</tr>
<tr>
<td><strong>PIR</strong></td>
<td>OUT</td>
<td>GPIO13</td>
<td>Señal digital</td>
</tr>
<tr>
<td><strong>LDR</strong></td>
<td>Terminal 1</td>
<td>3.3V</td>
<td></td>
</tr>
<tr>
<td><strong>LDR</strong></td>
<td>Terminal 2</td>
<td>GPIO34 + 10kΩ a GND</td>
<td>Divisor de voltaje</td>
</tr>
<tr>
<td><strong>Micrófono</strong></td>
<td>VCC</td>
<td>3.3V</td>
<td></td>
</tr>
<tr>
<td><strong>Micrófono</strong></td>
<td>GND</td>
<td>GND</td>
<td></td>
</tr>
<tr>
<td><strong>Micrófono</strong></td>
<td>OUT</td>
<td>GPIO35</td>
<td>Señal analógica</td>
</tr>
<tr>
<td><strong>Buzzer</strong></td>
<td>+</td>
<td>GPIO12</td>
<td></td>
</tr>
<tr>
<td><strong>Buzzer</strong></td>
<td>-</td>
<td>GND</td>
<td></td>
</tr>
<tr>
<td><strong>LED RGB</strong></td>
<td>R</td>
<td>GPIO18 + 220Ω</td>
<td></td>
</tr>
<tr>
<td><strong>LED RGB</strong></td>
<td>G</td>
<td>GPIO19 + 220Ω</td>
<td></td>
</tr>
<tr>
<td><strong>LED RGB</strong></td>
<td>B</td>
<td>GPIO21 + 220Ω</td>
<td></td>
</tr>
<tr>
<td><strong>LED RGB</strong></td>
<td>Cátodo</td>
<td>GND</td>
<td>Si es cátodo común</td>
</tr>
</tbody>
</table><h2 id="diagrama-ascii">Diagrama ASCII</h2>
<pre><code>          ESP32
    ┌──────────────┐
    │   3.3V       │──── PIR VCC
    │   GND        │──── PIR GND, Buzzer -, LED GND
    │   GPIO13     │──── PIR OUT
    │   GPIO34     │──── LDR (con 10kΩ a GND)
    │   GPIO35     │──── Micrófono OUT
    │   GPIO12     │──── Buzzer +
    │   GPIO18     │──── LED R (con 220Ω)
    │   GPIO19     │──── LED G (con 220Ω)
    │   GPIO21     │──── LED B (con 220Ω)
    └──────────────┘
</code></pre>
<hr>
<h1 id="parte-4-manual-de-ensamblaje-paso-a-paso">PARTE 4: MANUAL DE ENSAMBLAJE PASO A PASO</h1>
<h2 id="paso-1-preparación-del-espacio-de-trabajo">Paso 1: Preparación del Espacio de Trabajo</h2>
<ol>
<li>Limpia tu área de trabajo</li>
<li>Organiza componentes por tipo</li>
<li>Descarga Arduino IDE 2.x</li>
<li>Instala soporte para ESP32 en Arduino IDE</li>
</ol>
<h2 id="paso-2-instalar-soporte-esp32-en-arduino-ide">Paso 2: Instalar Soporte ESP32 en Arduino IDE</h2>
<pre><code>1. Abrir Arduino IDE
2. Archivo → Preferencias
3. En "Gestor de URLs Adicionales" pegar:
   https://dl.espressif.com/dl/package_esp32_index.json
4. Herramientas → Placa → Gestor de Tarjetas
5. Buscar "ESP32" e instalar "esp32 by Espressif Systems"
</code></pre>
<h2 id="paso-3-montaje-del-sensor-pir">Paso 3: Montaje del Sensor PIR</h2>
<ol>
<li>Coloca el ESP32 en el protoboard (centro)</li>
<li>Conecta PIR VCC → ESP32 3.3V (cable rojo)</li>
<li>Conecta PIR GND → ESP32 GND (cable negro)</li>
<li>Conecta PIR OUT → ESP32 GPIO13 (cable amarillo)</li>
<li><strong>Ajusta sensibilidad del PIR</strong>: Gira potenciómetro a la mitad</li>
</ol>
<h2 id="paso-4-montaje-del-ldr-fotoresistencia">Paso 4: Montaje del LDR (Fotoresistencia)</h2>
<ol>
<li>Inserta LDR en protoboard</li>
<li>Conecta un terminal del LDR a ESP32 3.3V</li>
<li>Conecta el otro terminal a GPIO34 Y a resistencia 10kΩ</li>
<li>Conecta el otro extremo de la resistencia a GND
<ul>
<li>Esto crea un <strong>divisor de voltaje</strong></li>
</ul>
</li>
</ol>
<h2 id="paso-5-montaje-del-micrófono">Paso 5: Montaje del Micrófono</h2>
<ol>
<li>Conecta VCC → ESP32 3.3V</li>
<li>Conecta GND → ESP32 GND</li>
<li>Conecta OUT → ESP32 GPIO35</li>
</ol>
<h2 id="paso-6-montaje-del-buzzer">Paso 6: Montaje del Buzzer</h2>
<ol>
<li>Identifica terminal positivo (+ o símbolo)</li>
<li>Conecta + → ESP32 GPIO12</li>
<li>Conecta - → ESP32 GND</li>
</ol>
<h2 id="paso-7-montaje-del-led-rgb">Paso 7: Montaje del LED RGB</h2>
<ol>
<li>Identifica el pin más largo (cátodo común)</li>
<li>Conecta cátodo → ESP32 GND</li>
<li>Conecta R → Resistencia 220Ω → GPIO18</li>
<li>Conecta G → Resistencia 220Ω → GPIO19</li>
<li>Conecta B → Resistencia 220Ω → GPIO21</li>
</ol>
<h2 id="paso-8-verificación-de-conexiones">Paso 8: Verificación de Conexiones</h2>
<ul>
<li class="task-list-item"> Todos los cables están firmes</li>
<li class="task-list-item"> No hay cortocircuitos (cables tocándose)</li>
<li class="task-list-item"> Polaridades correctas (VCC a 3.3V, GND a GND)</li>
<li class="task-list-item"> Resistencias en los lugares correctos</li>
</ul>
<h2 id="paso-9-primera-prueba-sin-código">Paso 9: Primera Prueba (Sin Código)</h2>
<ol>
<li>Conecta ESP32 a la computadora vía USB</li>
<li>El LED integrado debe encender</li>
<li>Si hay humo o calor excesivo: <strong>¡DESCONECTAR INMEDIATAMENTE!</strong></li>
</ol>
<hr>
<h1 id="parte-5-código-de-prueba-de-sensores">PARTE 5: CÓDIGO DE PRUEBA DE SENSORES</h1>
<h2 id="cargar-este-código-primero-verificación">Cargar Este Código Primero (Verificación)</h2>
<pre class="  language-cpp"><code class="prism  language-cpp"><span class="token comment">// ============================================</span>
<span class="token comment">// PRUEBA INDIVIDUAL DE SENSORES</span>
<span class="token comment">// Objetivo: Verificar que cada sensor funciona</span>
<span class="token comment">// ============================================</span>
</code></pre><p><span class="token keyword">void</span> <span class="token function">setup</span><span class="token punctuation">(</span><span class="token punctuation">)</span> <span class="token punctuation">{</span><br>
Serial<span class="token punctuation">.</span><span class="token function">begin</span><span class="token punctuation">(</span><span class="token number">115200</span><span class="token punctuation">)</span><span class="token punctuation">;</span></p>
<p><span class="token function">pinMode</span><span class="token punctuation">(</span><span class="token number">13</span><span class="token punctuation">,</span> INPUT<span class="token punctuation">)</span><span class="token punctuation">;</span>   <span class="token comment">// PIR</span><br>
<span class="token function">pinMode</span><span class="token punctuation">(</span><span class="token number">12</span><span class="token punctuation">,</span> OUTPUT<span class="token punctuation">)</span><span class="token punctuation">;</span>  <span class="token comment">// Buzzer</span><br>
<span class="token function">pinMode</span><span class="token punctuation">(</span><span class="token number">18</span><span class="token punctuation">,</span> OUTPUT<span class="token punctuation">)</span><span class="token punctuation">;</span>  <span class="token comment">// LED Rojo</span><br>
<span class="token function">pinMode</span><span class="token punctuation">(</span><span class="token number">19</span><span class="token punctuation">,</span> OUTPUT<span class="token punctuation">)</span><span class="token punctuation">;</span>  <span class="token comment">// LED Verde</span><br>
<span class="token function">pinMode</span><span class="token punctuation">(</span><span class="token number">21</span><span class="token punctuation">,</span> OUTPUT<span class="token punctuation">)</span><span class="token punctuation">;</span>  <span class="token comment">// LED Azul</span></p>
<p>Serial<span class="token punctuation">.</span><span class="token function">println</span><span class="token punctuation">(</span><span class="token string">“Iniciando pruebas…”</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
<span class="token function">delay</span><span class="token punctuation">(</span><span class="token number">2000</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
<span class="token punctuation">}</span></p>
<p><span class="token keyword">void</span> <span class="token function">loop</span><span class="token punctuation">(</span><span class="token punctuation">)</span> <span class="token punctuation">{</span><br>
<span class="token comment">// PRUEBA 1: PIR</span><br>
<span class="token keyword">int</span> movimiento <span class="token operator">=</span> <span class="token function">digitalRead</span><span class="token punctuation">(</span><span class="token number">13</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
Serial<span class="token punctuation">.</span><span class="token function">print</span><span class="token punctuation">(</span><span class="token string">"PIR: "</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
Serial<span class="token punctuation">.</span><span class="token function">println</span><span class="token punctuation">(</span>movimiento<span class="token punctuation">)</span><span class="token punctuation">;</span></p>
<p><span class="token comment">// PRUEBA 2: LDR</span><br>
<span class="token keyword">int</span> luz <span class="token operator">=</span> <span class="token function">analogRead</span><span class="token punctuation">(</span><span class="token number">34</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
Serial<span class="token punctuation">.</span><span class="token function">print</span><span class="token punctuation">(</span><span class="token string">“Luz: “</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
Serial<span class="token punctuation">.</span><span class="token function">print</span><span class="token punctuation">(</span>luz<span class="token punctuation">)</span><span class="token punctuation">;</span><br>
<span class="token keyword">if</span> <span class="token punctuation">(</span>luz <span class="token operator">&lt;</span> <span class="token number">800</span><span class="token punctuation">)</span> Serial<span class="token punctuation">.</span><span class="token function">println</span><span class="token punctuation">(</span><span class="token string">” OSCURO”</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
<span class="token keyword">else</span> <span class="token keyword">if</span> <span class="token punctuation">(</span>luz <span class="token operator">&lt;</span> <span class="token number">1500</span><span class="token punctuation">)</span> Serial<span class="token punctuation">.</span><span class="token function">println</span><span class="token punctuation">(</span><span class="token string">" MEDIO"</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
<span class="token keyword">else</span> Serial<span class="token punctuation">.</span><span class="token function">println</span><span class="token punctuation">(</span><span class="token string">" CLARO"</span><span class="token punctuation">)</span><span class="token punctuation">;</span></p>
<p><span class="token comment">// PRUEBA 3: MICRÓFONO</span><br>
<span class="token keyword">int</span> ruido <span class="token operator">=</span> <span class="token function">analogRead</span><span class="token punctuation">(</span><span class="token number">35</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
Serial<span class="token punctuation">.</span><span class="token function">print</span><span class="token punctuation">(</span><span class="token string">“Ruido: “</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
Serial<span class="token punctuation">.</span><span class="token function">print</span><span class="token punctuation">(</span>ruido<span class="token punctuation">)</span><span class="token punctuation">;</span><br>
<span class="token keyword">if</span> <span class="token punctuation">(</span>ruido <span class="token operator">&lt;</span> <span class="token number">1500</span><span class="token punctuation">)</span> Serial<span class="token punctuation">.</span><span class="token function">println</span><span class="token punctuation">(</span><span class="token string">” SILENCIO”</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
<span class="token keyword">else</span> <span class="token keyword">if</span> <span class="token punctuation">(</span>ruido <span class="token operator">&lt;</span> <span class="token number">2000</span><span class="token punctuation">)</span> Serial<span class="token punctuation">.</span><span class="token function">println</span><span class="token punctuation">(</span><span class="token string">" NORMAL"</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
<span class="token keyword">else</span> Serial<span class="token punctuation">.</span><span class="token function">println</span><span class="token punctuation">(</span><span class="token string">" RUIDOSO"</span><span class="token punctuation">)</span><span class="token punctuation">;</span></p>
<p><span class="token comment">// PRUEBA 4: BUZZER</span><br>
<span class="token keyword">if</span> <span class="token punctuation">(</span>movimiento <span class="token operator">==</span> <span class="token number">1</span><span class="token punctuation">)</span> <span class="token punctuation">{</span><br>
<span class="token function">tone</span><span class="token punctuation">(</span><span class="token number">12</span><span class="token punctuation">,</span> <span class="token number">1000</span><span class="token punctuation">,</span> <span class="token number">200</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
Serial<span class="token punctuation">.</span><span class="token function">println</span><span class="token punctuation">(</span><span class="token string">“Buzzer: ACTIVADO”</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
<span class="token punctuation">}</span></p>
<p><span class="token comment">// PRUEBA 5: LED RGB</span><br>
<span class="token function">digitalWrite</span><span class="token punctuation">(</span><span class="token number">18</span><span class="token punctuation">,</span> HIGH<span class="token punctuation">)</span><span class="token punctuation">;</span> <span class="token function">delay</span><span class="token punctuation">(</span><span class="token number">300</span><span class="token punctuation">)</span><span class="token punctuation">;</span> <span class="token function">digitalWrite</span><span class="token punctuation">(</span><span class="token number">18</span><span class="token punctuation">,</span> LOW<span class="token punctuation">)</span><span class="token punctuation">;</span><br>
<span class="token function">digitalWrite</span><span class="token punctuation">(</span><span class="token number">19</span><span class="token punctuation">,</span> HIGH<span class="token punctuation">)</span><span class="token punctuation">;</span> <span class="token function">delay</span><span class="token punctuation">(</span><span class="token number">300</span><span class="token punctuation">)</span><span class="token punctuation">;</span> <span class="token function">digitalWrite</span><span class="token punctuation">(</span><span class="token number">19</span><span class="token punctuation">,</span> LOW<span class="token punctuation">)</span><span class="token punctuation">;</span><br>
<span class="token function">digitalWrite</span><span class="token punctuation">(</span><span class="token number">21</span><span class="token punctuation">,</span> HIGH<span class="token punctuation">)</span><span class="token punctuation">;</span> <span class="token function">delay</span><span class="token punctuation">(</span><span class="token number">300</span><span class="token punctuation">)</span><span class="token punctuation">;</span> <span class="token function">digitalWrite</span><span class="token punctuation">(</span><span class="token number">21</span><span class="token punctuation">,</span> LOW<span class="token punctuation">)</span><span class="token punctuation">;</span></p>
<p>Serial<span class="token punctuation">.</span><span class="token function">println</span><span class="token punctuation">(</span><span class="token string">"--------------------"</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
<span class="token function">delay</span><span class="token punctuation">(</span><span class="token number">1000</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
<span class="token punctuation">}</span><br>
</p>
<h3 id="¿cómo-probar">¿Cómo Probar?</h3>
<ol>
<li>Cargar código en ESP32</li>
<li>Abrir Monitor Serial (115200 baud)</li>
<li><strong>Pruebas</strong>:
<ul>
<li>Mover mano frente al PIR → Debe mostrar “1”</li>
<li>Cubrir LDR con mano → Valor debe bajar</li>
<li>Hacer ruido cerca del micrófono → Valor debe subir</li>
<li>LED RGB debe parpadear en secuencia</li>
</ul>
</li>
</ol>
<hr>
<h1 id="parte-6-código-principal-con-ia">PARTE 6: CÓDIGO PRINCIPAL CON IA</h1>
<h2 id="código-completo-del-sistema-de-alarma">Código Completo del Sistema de Alarma</h2>
<pre class="  language-cpp"><code class="prism  language-cpp"><span class="token comment">// ============================================</span>
<span class="token comment">// SISTEMA DE ALARMA INTELIGENTE CON IA</span>
<span class="token comment">// Actividades 8 (Red Neuronal) y 9 (Lógica Difusa)</span>
<span class="token comment">// ============================================</span>
</code></pre><p><span class="token macro property">#<span class="token directive keyword">include</span> <span class="token string">&lt;WiFi.h&gt;</span></span><br>
<span class="token macro property">#<span class="token directive keyword">include</span> <span class="token string">&lt;HTTPClient.h&gt;</span></span></p>
<p><span class="token comment">// PINES</span><br>
<span class="token keyword">const</span> <span class="token keyword">int</span> pirPin <span class="token operator">=</span> <span class="token number">13</span><span class="token punctuation">;</span><br>
<span class="token keyword">const</span> <span class="token keyword">int</span> ldrPin <span class="token operator">=</span> <span class="token number">34</span><span class="token punctuation">;</span><br>
<span class="token keyword">const</span> <span class="token keyword">int</span> micPin <span class="token operator">=</span> <span class="token number">35</span><span class="token punctuation">;</span><br>
<span class="token keyword">const</span> <span class="token keyword">int</span> buzzerPin <span class="token operator">=</span> <span class="token number">12</span><span class="token punctuation">;</span><br>
<span class="token keyword">const</span> <span class="token keyword">int</span> ledR <span class="token operator">=</span> <span class="token number">18</span><span class="token punctuation">;</span><br>
<span class="token keyword">const</span> <span class="token keyword">int</span> ledG <span class="token operator">=</span> <span class="token number">19</span><span class="token punctuation">;</span><br>
<span class="token keyword">const</span> <span class="token keyword">int</span> ledB <span class="token operator">=</span> <span class="token number">21</span><span class="token punctuation">;</span></p>
<p><span class="token comment">// VARIABLES GLOBALES</span><br>
<span class="token keyword">int</span> movimiento<span class="token punctuation">,</span> luz<span class="token punctuation">,</span> ruido<span class="token punctuation">;</span><br>
<span class="token keyword">int</span> horaActual <span class="token operator">=</span> <span class="token number">14</span><span class="token punctuation">;</span>  <span class="token comment">// Hora simulada (cambiar para probar)</span></p>
<p><span class="token keyword">const</span> String estados<span class="token punctuation">[</span><span class="token number">4</span><span class="token punctuation">]</span> <span class="token operator">=</span> <span class="token punctuation">{</span><br>
<span class="token string">“NORMAL”</span><span class="token punctuation">,</span><br>
<span class="token string">“ALERTA BAJA”</span><span class="token punctuation">,</span><br>
<span class="token string">“ALERTA MEDIA”</span><span class="token punctuation">,</span><br>
<span class="token string">“ALERTA ALTA”</span><br>
<span class="token punctuation">}</span><span class="token punctuation">;</span></p>
<p><span class="token comment">// ============================================</span><br>
<span class="token comment">// CLASE LÓGICA DIFUSA</span><br>
<span class="token comment">// ============================================</span><br>
<span class="token keyword">class</span> <span class="token class-name">AlarmaDifusa</span> <span class="token punctuation">{</span><br>
<span class="token keyword">public</span><span class="token operator">:</span><br>
<span class="token comment">// Funciones de membresía para LUZ</span><br>
<span class="token keyword">float</span> <span class="token function">muyOscuro</span><span class="token punctuation">(</span><span class="token keyword">float</span> x<span class="token punctuation">)</span> <span class="token punctuation">{</span><br>
<span class="token keyword">return</span> <span class="token function">max</span><span class="token punctuation">(</span><span class="token number">0.0f</span><span class="token punctuation">,</span> <span class="token function">min</span><span class="token punctuation">(</span><span class="token number">1.0f</span><span class="token punctuation">,</span> <span class="token punctuation">(</span><span class="token number">800</span> <span class="token operator">-</span> x<span class="token punctuation">)</span> <span class="token operator">/</span> <span class="token number">500.0f</span><span class="token punctuation">)</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
<span class="token punctuation">}</span></p>
<pre><code>&lt;span class="token keyword"&gt;float&lt;/span&gt; &lt;span class="token function"&gt;oscuro&lt;/span&gt;&lt;span class="token punctuation"&gt;(&lt;/span&gt;&lt;span class="token keyword"&gt;float&lt;/span&gt; x&lt;span class="token punctuation"&gt;)&lt;/span&gt; &lt;span class="token punctuation"&gt;{&lt;/span&gt; 
    &lt;span class="token keyword"&gt;if&lt;/span&gt; &lt;span class="token punctuation"&gt;(&lt;/span&gt;x &lt;span class="token operator"&gt;&amp;lt;=&lt;/span&gt; &lt;span class="token number"&gt;500&lt;/span&gt;&lt;span class="token punctuation"&gt;)&lt;/span&gt; &lt;span class="token keyword"&gt;return&lt;/span&gt; &lt;span class="token number"&gt;0&lt;/span&gt;&lt;span class="token punctuation"&gt;;&lt;/span&gt;
    &lt;span class="token keyword"&gt;if&lt;/span&gt; &lt;span class="token punctuation"&gt;(&lt;/span&gt;x &lt;span class="token operator"&gt;&amp;gt;&lt;/span&gt; &lt;span class="token number"&gt;500&lt;/span&gt; &lt;span class="token operator"&gt;&amp;amp;&amp;amp;&lt;/span&gt; x &lt;span class="token operator"&gt;&amp;lt;=&lt;/span&gt; &lt;span class="token number"&gt;1000&lt;/span&gt;&lt;span class="token punctuation"&gt;)&lt;/span&gt; &lt;span class="token keyword"&gt;return&lt;/span&gt; &lt;span class="token punctuation"&gt;(&lt;/span&gt;x &lt;span class="token operator"&gt;-&lt;/span&gt; &lt;span class="token number"&gt;500&lt;/span&gt;&lt;span class="token punctuation"&gt;)&lt;/span&gt; &lt;span class="token operator"&gt;/&lt;/span&gt; &lt;span class="token number"&gt;500.0f&lt;/span&gt;&lt;span class="token punctuation"&gt;;&lt;/span&gt;
    &lt;span class="token keyword"&gt;if&lt;/span&gt; &lt;span class="token punctuation"&gt;(&lt;/span&gt;x &lt;span class="token operator"&gt;&amp;gt;&lt;/span&gt; &lt;span class="token number"&gt;1000&lt;/span&gt; &lt;span class="token operator"&gt;&amp;amp;&amp;amp;&lt;/span&gt; x &lt;span class="token operator"&gt;&amp;lt;=&lt;/span&gt; &lt;span class="token number"&gt;1500&lt;/span&gt;&lt;span class="token punctuation"&gt;)&lt;/span&gt; &lt;span class="token keyword"&gt;return&lt;/span&gt; &lt;span class="token punctuation"&gt;(&lt;/span&gt;&lt;span class="token number"&gt;1500&lt;/span&gt; &lt;span class="token operator"&gt;-&lt;/span&gt; x&lt;span class="token punctuation"&gt;)&lt;/span&gt; &lt;span class="token operator"&gt;/&lt;/span&gt; &lt;span class="token number"&gt;500.0f&lt;/span&gt;&lt;span class="token punctuation"&gt;;&lt;/span&gt;
    &lt;span class="token keyword"&gt;return&lt;/span&gt; &lt;span class="token number"&gt;0&lt;/span&gt;&lt;span class="token punctuation"&gt;;&lt;/span&gt;
&lt;span class="token punctuation"&gt;}&lt;/span&gt;

&lt;span class="token keyword"&gt;float&lt;/span&gt; &lt;span class="token function"&gt;claro&lt;/span&gt;&lt;span class="token punctuation"&gt;(&lt;/span&gt;&lt;span class="token keyword"&gt;float&lt;/span&gt; x&lt;span class="token punctuation"&gt;)&lt;/span&gt; &lt;span class="token punctuation"&gt;{&lt;/span&gt; 
  &lt;span class="token keyword"&gt;return&lt;/span&gt; &lt;span class="token function"&gt;max&lt;/span&gt;&lt;span class="token punctuation"&gt;(&lt;/span&gt;&lt;span class="token number"&gt;0.0f&lt;/span&gt;&lt;span class="token punctuation"&gt;,&lt;/span&gt; &lt;span class="token function"&gt;min&lt;/span&gt;&lt;span class="token punctuation"&gt;(&lt;/span&gt;&lt;span class="token number"&gt;1.0f&lt;/span&gt;&lt;span class="token punctuation"&gt;,&lt;/span&gt; &lt;span class="token punctuation"&gt;(&lt;/span&gt;x &lt;span class="token operator"&gt;-&lt;/span&gt; &lt;span class="token number"&gt;1000&lt;/span&gt;&lt;span class="token punctuation"&gt;)&lt;/span&gt; &lt;span class="token operator"&gt;/&lt;/span&gt; &lt;span class="token number"&gt;500.0f&lt;/span&gt;&lt;span class="token punctuation"&gt;)&lt;/span&gt;&lt;span class="token punctuation"&gt;)&lt;/span&gt;&lt;span class="token punctuation"&gt;;&lt;/span&gt; 
&lt;span class="token punctuation"&gt;}&lt;/span&gt;

&lt;span class="token comment"&gt;// Funciones de membresía para RUIDO&lt;/span&gt;
&lt;span class="token keyword"&gt;float&lt;/span&gt; &lt;span class="token function"&gt;silencio&lt;/span&gt;&lt;span class="token punctuation"&gt;(&lt;/span&gt;&lt;span class="token keyword"&gt;float&lt;/span&gt; x&lt;span class="token punctuation"&gt;)&lt;/span&gt; &lt;span class="token punctuation"&gt;{&lt;/span&gt; 
  &lt;span class="token keyword"&gt;return&lt;/span&gt; &lt;span class="token function"&gt;max&lt;/span&gt;&lt;span class="token punctuation"&gt;(&lt;/span&gt;&lt;span class="token number"&gt;0.0f&lt;/span&gt;&lt;span class="token punctuation"&gt;,&lt;/span&gt; &lt;span class="token function"&gt;min&lt;/span&gt;&lt;span class="token punctuation"&gt;(&lt;/span&gt;&lt;span class="token number"&gt;1.0f&lt;/span&gt;&lt;span class="token punctuation"&gt;,&lt;/span&gt; &lt;span class="token punctuation"&gt;(&lt;/span&gt;&lt;span class="token number"&gt;1500&lt;/span&gt; &lt;span class="token operator"&gt;-&lt;/span&gt; x&lt;span class="token punctuation"&gt;)&lt;/span&gt; &lt;span class="token operator"&gt;/&lt;/span&gt; &lt;span class="token number"&gt;800.0f&lt;/span&gt;&lt;span class="token punctuation"&gt;)&lt;/span&gt;&lt;span class="token punctuation"&gt;)&lt;/span&gt;&lt;span class="token punctuation"&gt;;&lt;/span&gt; 
&lt;span class="token punctuation"&gt;}&lt;/span&gt;

&lt;span class="token keyword"&gt;float&lt;/span&gt; &lt;span class="token function"&gt;normal&lt;/span&gt;&lt;span class="token punctuation"&gt;(&lt;/span&gt;&lt;span class="token keyword"&gt;float&lt;/span&gt; x&lt;span class="token punctuation"&gt;)&lt;/span&gt; &lt;span class="token punctuation"&gt;{&lt;/span&gt; 
    &lt;span class="token keyword"&gt;if&lt;/span&gt; &lt;span class="token punctuation"&gt;(&lt;/span&gt;x &lt;span class="token operator"&gt;&amp;lt;=&lt;/span&gt; &lt;span class="token number"&gt;1000&lt;/span&gt;&lt;span class="token punctuation"&gt;)&lt;/span&gt; &lt;span class="token keyword"&gt;return&lt;/span&gt; &lt;span class="token number"&gt;0&lt;/span&gt;&lt;span class="token punctuation"&gt;;&lt;/span&gt;
    &lt;span class="token keyword"&gt;if&lt;/span&gt; &lt;span class="token punctuation"&gt;(&lt;/span&gt;x &lt;span class="token operator"&gt;&amp;gt;&lt;/span&gt; &lt;span class="token number"&gt;1000&lt;/span&gt; &lt;span class="token operator"&gt;&amp;amp;&amp;amp;&lt;/span&gt; x &lt;span class="token operator"&gt;&amp;lt;=&lt;/span&gt; &lt;span class="token number"&gt;2000&lt;/span&gt;&lt;span class="token punctuation"&gt;)&lt;/span&gt; &lt;span class="token keyword"&gt;return&lt;/span&gt; &lt;span class="token punctuation"&gt;(&lt;/span&gt;x &lt;span class="token operator"&gt;-&lt;/span&gt; &lt;span class="token number"&gt;1000&lt;/span&gt;&lt;span class="token punctuation"&gt;)&lt;/span&gt; &lt;span class="token operator"&gt;/&lt;/span&gt; &lt;span class="token number"&gt;1000.0f&lt;/span&gt;&lt;span class="token punctuation"&gt;;&lt;/span&gt;
    &lt;span class="token keyword"&gt;if&lt;/span&gt; &lt;span class="token punctuation"&gt;(&lt;/span&gt;x &lt;span class="token operator"&gt;&amp;gt;&lt;/span&gt; &lt;span class="token number"&gt;2000&lt;/span&gt; &lt;span class="token operator"&gt;&amp;amp;&amp;amp;&lt;/span&gt; x &lt;span class="token operator"&gt;&amp;lt;=&lt;/span&gt; &lt;span class="token number"&gt;2500&lt;/span&gt;&lt;span class="token punctuation"&gt;)&lt;/span&gt; &lt;span class="token keyword"&gt;return&lt;/span&gt; &lt;span class="token punctuation"&gt;(&lt;/span&gt;&lt;span class="token number"&gt;2500&lt;/span&gt; &lt;span class="token operator"&gt;-&lt;/span&gt; x&lt;span class="token punctuation"&gt;)&lt;/span&gt; &lt;span class="token operator"&gt;/&lt;/span&gt; &lt;span class="token number"&gt;500.0f&lt;/span&gt;&lt;span class="token punctuation"&gt;;&lt;/span&gt;
    &lt;span class="token keyword"&gt;return&lt;/span&gt; &lt;span class="token number"&gt;0&lt;/span&gt;&lt;span class="token punctuation"&gt;;&lt;/span&gt;
&lt;span class="token punctuation"&gt;}&lt;/span&gt;

&lt;span class="token keyword"&gt;float&lt;/span&gt; &lt;span class="token function"&gt;ruidoso&lt;/span&gt;&lt;span class="token punctuation"&gt;(&lt;/span&gt;&lt;span class="token keyword"&gt;float&lt;/span&gt; x&lt;span class="token punctuation"&gt;)&lt;/span&gt; &lt;span class="token punctuation"&gt;{&lt;/span&gt; 
  &lt;span class="token keyword"&gt;return&lt;/span&gt; &lt;span class="token function"&gt;max&lt;/span&gt;&lt;span class="token punctuation"&gt;(&lt;/span&gt;&lt;span class="token number"&gt;0.0f&lt;/span&gt;&lt;span class="token punctuation"&gt;,&lt;/span&gt; &lt;span class="token function"&gt;min&lt;/span&gt;&lt;span class="token punctuation"&gt;(&lt;/span&gt;&lt;span class="token number"&gt;1.0f&lt;/span&gt;&lt;span class="token punctuation"&gt;,&lt;/span&gt; &lt;span class="token punctuation"&gt;(&lt;/span&gt;x &lt;span class="token operator"&gt;-&lt;/span&gt; &lt;span class="token number"&gt;1800&lt;/span&gt;&lt;span class="token punctuation"&gt;)&lt;/span&gt; &lt;span class="token operator"&gt;/&lt;/span&gt; &lt;span class="token number"&gt;700.0f&lt;/span&gt;&lt;span class="token punctuation"&gt;)&lt;/span&gt;&lt;span class="token punctuation"&gt;)&lt;/span&gt;&lt;span class="token punctuation"&gt;;&lt;/span&gt; 
&lt;span class="token punctuation"&gt;}&lt;/span&gt;

&lt;span class="token comment"&gt;// INFERENCIA DIFUSA&lt;/span&gt;
&lt;span class="token keyword"&gt;float&lt;/span&gt; &lt;span class="token function"&gt;calcularIntensidad&lt;/span&gt;&lt;span class="token punctuation"&gt;(&lt;/span&gt;&lt;span class="token keyword"&gt;float&lt;/span&gt; luz&lt;span class="token punctuation"&gt;,&lt;/span&gt; &lt;span class="token keyword"&gt;float&lt;/span&gt; ruido&lt;span class="token punctuation"&gt;,&lt;/span&gt; &lt;span class="token keyword"&gt;int&lt;/span&gt; movimiento&lt;span class="token punctuation"&gt;)&lt;/span&gt; &lt;span class="token punctuation"&gt;{&lt;/span&gt;
    &lt;span class="token keyword"&gt;if&lt;/span&gt; &lt;span class="token punctuation"&gt;(&lt;/span&gt;&lt;span class="token operator"&gt;!&lt;/span&gt;movimiento&lt;span class="token punctuation"&gt;)&lt;/span&gt; &lt;span class="token keyword"&gt;return&lt;/span&gt; &lt;span class="token number"&gt;0&lt;/span&gt;&lt;span class="token punctuation"&gt;;&lt;/span&gt;
    
    &lt;span class="token comment"&gt;// REGLAS DIFUSAS&lt;/span&gt;
    &lt;span class="token keyword"&gt;float&lt;/span&gt; intensidadBaja  &lt;span class="token operator"&gt;=&lt;/span&gt; &lt;span class="token function"&gt;min&lt;/span&gt;&lt;span class="token punctuation"&gt;(&lt;/span&gt;&lt;span class="token function"&gt;oscuro&lt;/span&gt;&lt;span class="token punctuation"&gt;(&lt;/span&gt;luz&lt;span class="token punctuation"&gt;)&lt;/span&gt;&lt;span class="token punctuation"&gt;,&lt;/span&gt; &lt;span class="token function"&gt;normal&lt;/span&gt;&lt;span class="token punctuation"&gt;(&lt;/span&gt;ruido&lt;span class="token punctuation"&gt;)&lt;/span&gt;&lt;span class="token punctuation"&gt;)&lt;/span&gt;&lt;span class="token punctuation"&gt;;&lt;/span&gt;
    &lt;span class="token keyword"&gt;float&lt;/span&gt; intensidadMedia &lt;span class="token operator"&gt;=&lt;/span&gt; &lt;span class="token function"&gt;min&lt;/span&gt;&lt;span class="token punctuation"&gt;(&lt;/span&gt;&lt;span class="token function"&gt;muyOscuro&lt;/span&gt;&lt;span class="token punctuation"&gt;(&lt;/span&gt;luz&lt;span class="token punctuation"&gt;)&lt;/span&gt;&lt;span class="token punctuation"&gt;,&lt;/span&gt; &lt;span class="token function"&gt;normal&lt;/span&gt;&lt;span class="token punctuation"&gt;(&lt;/span&gt;ruido&lt;span class="token punctuation"&gt;)&lt;/span&gt;&lt;span class="token punctuation"&gt;)&lt;/span&gt;&lt;span class="token punctuation"&gt;;&lt;/span&gt;
    &lt;span class="token keyword"&gt;float&lt;/span&gt; intensidadAlta  &lt;span class="token operator"&gt;=&lt;/span&gt; &lt;span class="token function"&gt;min&lt;/span&gt;&lt;span class="token punctuation"&gt;(&lt;/span&gt;&lt;span class="token function"&gt;muyOscuro&lt;/span&gt;&lt;span class="token punctuation"&gt;(&lt;/span&gt;luz&lt;span class="token punctuation"&gt;)&lt;/span&gt;&lt;span class="token punctuation"&gt;,&lt;/span&gt; &lt;span class="token function"&gt;ruidoso&lt;/span&gt;&lt;span class="token punctuation"&gt;(&lt;/span&gt;ruido&lt;span class="token punctuation"&gt;)&lt;/span&gt;&lt;span class="token punctuation"&gt;)&lt;/span&gt;&lt;span class="token punctuation"&gt;;&lt;/span&gt;
    
    &lt;span class="token comment"&gt;// DEFUZZIFICACIÓN&lt;/span&gt;
    &lt;span class="token keyword"&gt;float&lt;/span&gt; numerador &lt;span class="token operator"&gt;=&lt;/span&gt; &lt;span class="token punctuation"&gt;(&lt;/span&gt;intensidadBaja &lt;span class="token operator"&gt;*&lt;/span&gt; &lt;span class="token number"&gt;30&lt;/span&gt;&lt;span class="token punctuation"&gt;)&lt;/span&gt; &lt;span class="token operator"&gt;+&lt;/span&gt; 
                     &lt;span class="token punctuation"&gt;(&lt;/span&gt;intensidadMedia &lt;span class="token operator"&gt;*&lt;/span&gt; &lt;span class="token number"&gt;60&lt;/span&gt;&lt;span class="token punctuation"&gt;)&lt;/span&gt; &lt;span class="token operator"&gt;+&lt;/span&gt; 
                     &lt;span class="token punctuation"&gt;(&lt;/span&gt;intensidadAlta &lt;span class="token operator"&gt;*&lt;/span&gt; &lt;span class="token number"&gt;90&lt;/span&gt;&lt;span class="token punctuation"&gt;)&lt;/span&gt;&lt;span class="token punctuation"&gt;;&lt;/span&gt;
    &lt;span class="token keyword"&gt;float&lt;/span&gt; denominador &lt;span class="token operator"&gt;=&lt;/span&gt; intensidadBaja &lt;span class="token operator"&gt;+&lt;/span&gt; intensidadMedia &lt;span class="token operator"&gt;+&lt;/span&gt; intensidadAlta&lt;span class="token punctuation"&gt;;&lt;/span&gt;
    
    &lt;span class="token keyword"&gt;return&lt;/span&gt; &lt;span class="token punctuation"&gt;(&lt;/span&gt;denominador &lt;span class="token operator"&gt;==&lt;/span&gt; &lt;span class="token number"&gt;0&lt;/span&gt;&lt;span class="token punctuation"&gt;)&lt;/span&gt; &lt;span class="token operator"&gt;?&lt;/span&gt; &lt;span class="token number"&gt;0&lt;/span&gt; &lt;span class="token operator"&gt;:&lt;/span&gt; numerador &lt;span class="token operator"&gt;/&lt;/span&gt; denominador&lt;span class="token punctuation"&gt;;&lt;/span&gt;
&lt;span class="token punctuation"&gt;}&lt;/span&gt;
</code></pre>
<p><span class="token punctuation">}</span><span class="token punctuation">;</span></p>
<p>AlarmaDifusa fuzzy<span class="token punctuation">;</span></p>
<p><span class="token comment">// ============================================</span><br>
<span class="token comment">// SETUP</span><br>
<span class="token comment">// ============================================</span><br>
<span class="token keyword">void</span> <span class="token function">setup</span><span class="token punctuation">(</span><span class="token punctuation">)</span> <span class="token punctuation">{</span><br>
Serial<span class="token punctuation">.</span><span class="token function">begin</span><span class="token punctuation">(</span><span class="token number">115200</span><span class="token punctuation">)</span><span class="token punctuation">;</span></p>
<p><span class="token function">pinMode</span><span class="token punctuation">(</span>pirPin<span class="token punctuation">,</span> INPUT<span class="token punctuation">)</span><span class="token punctuation">;</span><br>
<span class="token function">pinMode</span><span class="token punctuation">(</span>buzzerPin<span class="token punctuation">,</span> OUTPUT<span class="token punctuation">)</span><span class="token punctuation">;</span><br>
<span class="token function">pinMode</span><span class="token punctuation">(</span>ledR<span class="token punctuation">,</span> OUTPUT<span class="token punctuation">)</span><span class="token punctuation">;</span><br>
<span class="token function">pinMode</span><span class="token punctuation">(</span>ledG<span class="token punctuation">,</span> OUTPUT<span class="token punctuation">)</span><span class="token punctuation">;</span><br>
<span class="token function">pinMode</span><span class="token punctuation">(</span>ledB<span class="token punctuation">,</span> OUTPUT<span class="token punctuation">)</span><span class="token punctuation">;</span></p>
<p><span class="token function">setLED</span><span class="token punctuation">(</span><span class="token number">0</span><span class="token punctuation">,</span> <span class="token number">255</span><span class="token punctuation">,</span> <span class="token number">0</span><span class="token punctuation">)</span><span class="token punctuation">;</span>  <span class="token comment">// Verde = seguro</span></p>
<p>Serial<span class="token punctuation">.</span><span class="token function">println</span><span class="token punctuation">(</span><span class="token string">"<mark><mark><mark><mark><mark><mark><mark><mark><mark><mark><mark><mark><mark><mark><mark>"</mark></mark></mark></mark></mark></mark></mark></mark></mark></mark></mark></mark></mark></mark></mark></span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
Serial<span class="token punctuation">.</span><span class="token function">println</span><span class="token punctuation">(</span><span class="token string">“SISTEMA DE ALARMA INTELIGENTE”</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
Serial<span class="token punctuation">.</span><span class="token function">println</span><span class="token punctuation">(</span><span class="token string">“Red Neuronal + Logica Difusa”</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
Serial<span class="token punctuation">.</span><span class="token function">println</span><span class="token punctuation">(</span><span class="token string">""</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
<span class="token function">delay</span><span class="token punctuation">(</span><span class="token number">2000</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
<span class="token punctuation">}</span></p>
<p><span class="token comment">// ============================================</span><br>
<span class="token comment">// FUNCIONES</span><br>
<span class="token comment">// ============================================</span></p>
<p><span class="token keyword">void</span> <span class="token function">leerSensores</span><span class="token punctuation">(</span><span class="token punctuation">)</span> <span class="token punctuation">{</span><br>
movimiento <span class="token operator">=</span> <span class="token function">digitalRead</span><span class="token punctuation">(</span>pirPin<span class="token punctuation">)</span><span class="token punctuation">;</span><br>
luz <span class="token operator">=</span> <span class="token function">analogRead</span><span class="token punctuation">(</span>ldrPin<span class="token punctuation">)</span><span class="token punctuation">;</span><br>
ruido <span class="token operator">=</span> <span class="token function">analogRead</span><span class="token punctuation">(</span>micPin<span class="token punctuation">)</span><span class="token punctuation">;</span></p>
<p>Serial<span class="token punctuation">.</span><span class="token function">println</span><span class="token punctuation">(</span><span class="token string">"\nLECTURAS:"</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
Serial<span class="token punctuation">.</span><span class="token function">print</span><span class="token punctuation">(</span><span class="token string">"  Movimiento: “</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
Serial<span class="token punctuation">.</span><span class="token function">println</span><span class="token punctuation">(</span>movimiento <span class="token operator">?</span> <span class="token string">“DETECTADO”</span> <span class="token operator">:</span> <span class="token string">“NO”</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
Serial<span class="token punctuation">.</span><span class="token function">print</span><span class="token punctuation">(</span><span class="token string">”  Luz: “</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
Serial<span class="token punctuation">.</span><span class="token function">println</span><span class="token punctuation">(</span>luz<span class="token punctuation">)</span><span class="token punctuation">;</span><br>
Serial<span class="token punctuation">.</span><span class="token function">print</span><span class="token punctuation">(</span><span class="token string">”  Ruido: "</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
Serial<span class="token punctuation">.</span><span class="token function">println</span><span class="token punctuation">(</span>ruido<span class="token punctuation">)</span><span class="token punctuation">;</span><br>
<span class="token punctuation">}</span></p>
<p><span class="token comment">// RED NEURONAL SIMULADA</span><br>
<span class="token keyword">int</span> <span class="token function">redNeuronalDecision</span><span class="token punctuation">(</span><span class="token punctuation">)</span> <span class="token punctuation">{</span><br>
<span class="token keyword">float</span> scores<span class="token punctuation">[</span><span class="token number">4</span><span class="token punctuation">]</span> <span class="token operator">=</span> <span class="token punctuation">{</span><span class="token number">0</span><span class="token punctuation">,</span> <span class="token number">0</span><span class="token punctuation">,</span> <span class="token number">0</span><span class="token punctuation">,</span> <span class="token number">0</span><span class="token punctuation">}</span><span class="token punctuation">;</span></p>
<p><span class="token keyword">bool</span> esNoche <span class="token operator">=</span> <span class="token punctuation">(</span>luz <span class="token operator">&lt;</span> <span class="token number">800</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
<span class="token keyword">bool</span> esRuidoso <span class="token operator">=</span> <span class="token punctuation">(</span>ruido <span class="token operator">&gt;</span> <span class="token number">2000</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
<span class="token keyword">bool</span> horaSospechosa <span class="token operator">=</span> <span class="token punctuation">(</span>horaActual <span class="token operator">&lt;</span> <span class="token number">6</span> <span class="token operator">||</span> horaActual <span class="token operator">&gt;</span> <span class="token number">22</span><span class="token punctuation">)</span><span class="token punctuation">;</span></p>
<p>Serial<span class="token punctuation">.</span><span class="token function">println</span><span class="token punctuation">(</span><span class="token string">"\nANALISIS:"</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
Serial<span class="token punctuation">.</span><span class="token function">print</span><span class="token punctuation">(</span><span class="token string">"  Noche: “</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
Serial<span class="token punctuation">.</span><span class="token function">println</span><span class="token punctuation">(</span>esNoche <span class="token operator">?</span> <span class="token string">“SI”</span> <span class="token operator">:</span> <span class="token string">“NO”</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
Serial<span class="token punctuation">.</span><span class="token function">print</span><span class="token punctuation">(</span><span class="token string">”  Ruidoso: “</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
Serial<span class="token punctuation">.</span><span class="token function">println</span><span class="token punctuation">(</span>esRuidoso <span class="token operator">?</span> <span class="token string">“SI”</span> <span class="token operator">:</span> <span class="token string">“NO”</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
Serial<span class="token punctuation">.</span><span class="token function">print</span><span class="token punctuation">(</span><span class="token string">”  Hora Sospechosa: "</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
Serial<span class="token punctuation">.</span><span class="token function">println</span><span class="token punctuation">(</span>horaSospechosa <span class="token operator">?</span> <span class="token string">“SI”</span> <span class="token operator">:</span> <span class="token string">“NO”</span><span class="token punctuation">)</span><span class="token punctuation">;</span></p>
<p><span class="token keyword">if</span> <span class="token punctuation">(</span><span class="token operator">!</span>movimiento<span class="token punctuation">)</span> <span class="token punctuation">{</span><br>
scores<span class="token punctuation">[</span><span class="token number">0</span><span class="token punctuation">]</span> <span class="token operator">=</span> <span class="token number">0.95</span><span class="token punctuation">;</span><br>
scores<span class="token punctuation">[</span><span class="token number">1</span><span class="token punctuation">]</span> <span class="token operator">=</span> <span class="token number">0.03</span><span class="token punctuation">;</span><br>
scores<span class="token punctuation">[</span><span class="token number">2</span><span class="token punctuation">]</span> <span class="token operator">=</span> <span class="token number">0.01</span><span class="token punctuation">;</span><br>
scores<span class="token punctuation">[</span><span class="token number">3</span><span class="token punctuation">]</span> <span class="token operator">=</span> <span class="token number">0.01</span><span class="token punctuation">;</span><br>
<span class="token punctuation">}</span><br>
<span class="token keyword">else</span> <span class="token keyword">if</span> <span class="token punctuation">(</span>movimiento <span class="token operator">&amp;&amp;</span> esNoche <span class="token operator">&amp;&amp;</span> horaSospechosa<span class="token punctuation">)</span> <span class="token punctuation">{</span><br>
scores<span class="token punctuation">[</span><span class="token number">0</span><span class="token punctuation">]</span> <span class="token operator">=</span> <span class="token number">0.05</span><span class="token punctuation">;</span><br>
scores<span class="token punctuation">[</span><span class="token number">1</span><span class="token punctuation">]</span> <span class="token operator">=</span> <span class="token number">0.10</span><span class="token punctuation">;</span><br>
scores<span class="token punctuation">[</span><span class="token number">2</span><span class="token punctuation">]</span> <span class="token operator">=</span> <span class="token number">0.20</span><span class="token punctuation">;</span><br>
scores<span class="token punctuation">[</span><span class="token number">3</span><span class="token punctuation">]</span> <span class="token operator">=</span> <span class="token number">0.85</span><span class="token punctuation">;</span><br>
<span class="token punctuation">}</span><br>
<span class="token keyword">else</span> <span class="token keyword">if</span> <span class="token punctuation">(</span>movimiento <span class="token operator">&amp;&amp;</span> esRuidoso<span class="token punctuation">)</span> <span class="token punctuation">{</span><br>
scores<span class="token punctuation">[</span><span class="token number">0</span><span class="token punctuation">]</span> <span class="token operator">=</span> <span class="token number">0.10</span><span class="token punctuation">;</span><br>
scores<span class="token punctuation">[</span><span class="token number">1</span><span class="token punctuation">]</span> <span class="token operator">=</span> <span class="token number">0.15</span><span class="token punctuation">;</span><br>
scores<span class="token punctuation">[</span><span class="token number">2</span><span class="token punctuation">]</span> <span class="token operator">=</span> <span class="token number">0.70</span><span class="token punctuation">;</span><br>
scores<span class="token punctuation">[</span><span class="token number">3</span><span class="token punctuation">]</span> <span class="token operator">=</span> <span class="token number">0.05</span><span class="token punctuation">;</span><br>
<span class="token punctuation">}</span><br>
<span class="token keyword">else</span> <span class="token keyword">if</span> <span class="token punctuation">(</span>movimiento<span class="token punctuation">)</span> <span class="token punctuation">{</span><br>
scores<span class="token punctuation">[</span><span class="token number">0</span><span class="token punctuation">]</span> <span class="token operator">=</span> <span class="token number">0.15</span><span class="token punctuation">;</span><br>
scores<span class="token punctuation">[</span><span class="token number">1</span><span class="token punctuation">]</span> <span class="token operator">=</span> <span class="token number">0.68</span><span class="token punctuation">;</span><br>
scores<span class="token punctuation">[</span><span class="token number">2</span><span class="token punctuation">]</span> <span class="token operator">=</span> <span class="token number">0.12</span><span class="token punctuation">;</span><br>
scores<span class="token punctuation">[</span><span class="token number">3</span><span class="token punctuation">]</span> <span class="token operator">=</span> <span class="token number">0.05</span><span class="token punctuation">;</span><br>
<span class="token punctuation">}</span></p>
<p><span class="token keyword">int</span> decision <span class="token operator">=</span> <span class="token number">0</span><span class="token punctuation">;</span><br>
<span class="token keyword">float</span> maxScore <span class="token operator">=</span> scores<span class="token punctuation">[</span><span class="token number">0</span><span class="token punctuation">]</span><span class="token punctuation">;</span></p>
<p><span class="token keyword">for</span> <span class="token punctuation">(</span><span class="token keyword">int</span> i <span class="token operator">=</span> <span class="token number">1</span><span class="token punctuation">;</span> i <span class="token operator">&lt;</span> <span class="token number">4</span><span class="token punctuation">;</span> i<span class="token operator">++</span><span class="token punctuation">)</span> <span class="token punctuation">{</span><br>
<span class="token keyword">if</span> <span class="token punctuation">(</span>scores<span class="token punctuation">[</span>i<span class="token punctuation">]</span> <span class="token operator">&gt;</span> maxScore<span class="token punctuation">)</span> <span class="token punctuation">{</span><br>
maxScore <span class="token operator">=</span> scores<span class="token punctuation">[</span>i<span class="token punctuation">]</span><span class="token punctuation">;</span><br>
decision <span class="token operator">=</span> i<span class="token punctuation">;</span><br>
<span class="token punctuation">}</span><br>
<span class="token punctuation">}</span></p>
<p>Serial<span class="token punctuation">.</span><span class="token function">println</span><span class="token punctuation">(</span><span class="token string">"\nPROBABILIDADES:"</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
Serial<span class="token punctuation">.</span><span class="token function">print</span><span class="token punctuation">(</span><span class="token string">"  Normal: “</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
Serial<span class="token punctuation">.</span><span class="token function">print</span><span class="token punctuation">(</span>scores<span class="token punctuation">[</span><span class="token number">0</span><span class="token punctuation">]</span> <span class="token operator"><em></em></span> <span class="token number">100</span><span class="token punctuation">,</span> <span class="token number">1</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
Serial<span class="token punctuation">.</span><span class="token function">println</span><span class="token punctuation">(</span><span class="token string">"%"</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
Serial<span class="token punctuation">.</span><span class="token function">print</span><span class="token punctuation">(</span><span class="token string">"  Alerta Baja: "</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
Serial<span class="token punctuation">.</span><span class="token function">print</span><span class="token punctuation">(</span>scores<span class="token punctuation">[</span><span class="token number">1</span><span class="token punctuation">]</span> <span class="token operator"></span> <span class="token number">100</span><span class="token punctuation">,</span> <span class="token number">1</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
Serial<span class="token punctuation">.</span><span class="token function">println</span><span class="token punctuation">(</span><span class="token string">”%"</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
Serial<span class="token punctuation">.</span><span class="token function">print</span><span class="token punctuation">(</span><span class="token string">"  Alerta Media: “</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
Serial<span class="token punctuation">.</span><span class="token function">print</span><span class="token punctuation">(</span>scores<span class="token punctuation">[</span><span class="token number">2</span><span class="token punctuation">]</span> <span class="token operator"><em></em></span> <span class="token number">100</span><span class="token punctuation">,</span> <span class="token number">1</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
Serial<span class="token punctuation">.</span><span class="token function">println</span><span class="token punctuation">(</span><span class="token string">"%"</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
Serial<span class="token punctuation">.</span><span class="token function">print</span><span class="token punctuation">(</span><span class="token string">"  Alerta Alta: "</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
Serial<span class="token punctuation">.</span><span class="token function">print</span><span class="token punctuation">(</span>scores<span class="token punctuation">[</span><span class="token number">3</span><span class="token punctuation">]</span> <span class="token operator"></span> <span class="token number">100</span><span class="token punctuation">,</span> <span class="token number">1</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
Serial<span class="token punctuation">.</span><span class="token function">println</span><span class="token punctuation">(</span><span class="token string">”%"</span><span class="token punctuation">)</span><span class="token punctuation">;</span></p>
<p><span class="token keyword">return</span> decision<span class="token punctuation">;</span><br>
<span class="token punctuation">}</span></p>
<p><span class="token keyword">void</span> <span class="token function">setLED</span><span class="token punctuation">(</span><span class="token keyword">int</span> r<span class="token punctuation">,</span> <span class="token keyword">int</span> g<span class="token punctuation">,</span> <span class="token keyword">int</span> b<span class="token punctuation">)</span> <span class="token punctuation">{</span><br>
<span class="token function">analogWrite</span><span class="token punctuation">(</span>ledR<span class="token punctuation">,</span> r<span class="token punctuation">)</span><span class="token punctuation">;</span><br>
<span class="token function">analogWrite</span><span class="token punctuation">(</span>ledG<span class="token punctuation">,</span> g<span class="token punctuation">)</span><span class="token punctuation">;</span><br>
<span class="token function">analogWrite</span><span class="token punctuation">(</span>ledB<span class="token punctuation">,</span> b<span class="token punctuation">)</span><span class="token punctuation">;</span><br>
<span class="token punctuation">}</span></p>
<p><span class="token keyword">void</span> <span class="token function">ejecutarAlertaDifusa</span><span class="token punctuation">(</span><span class="token keyword">int</span> nivelBase<span class="token punctuation">,</span> <span class="token keyword">float</span> intensidad<span class="token punctuation">)</span> <span class="token punctuation">{</span><br>
Serial<span class="token punctuation">.</span><span class="token function">print</span><span class="token punctuation">(</span><span class="token string">"\nLOGICA DIFUSA - Intensidad: “</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
Serial<span class="token punctuation">.</span><span class="token function">print</span><span class="token punctuation">(</span>intensidad<span class="token punctuation">,</span> <span class="token number">1</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
Serial<span class="token punctuation">.</span><span class="token function">println</span><span class="token punctuation">(</span><span class="token string">”%"</span><span class="token punctuation">)</span><span class="token punctuation">;</span></p>
<p><span class="token keyword">int</span> frecuencia <span class="token operator">=</span> <span class="token function">map</span><span class="token punctuation">(</span>intensidad<span class="token punctuation">,</span> <span class="token number">0</span><span class="token punctuation">,</span> <span class="token number">100</span><span class="token punctuation">,</span> <span class="token number">800</span><span class="token punctuation">,</span> <span class="token number">2500</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
<span class="token keyword">int</span> duracion <span class="token operator">=</span> <span class="token function">map</span><span class="token punctuation">(</span>intensidad<span class="token punctuation">,</span> <span class="token number">0</span><span class="token punctuation">,</span> <span class="token number">100</span><span class="token punctuation">,</span> <span class="token number">100</span><span class="token punctuation">,</span> <span class="token number">500</span><span class="token punctuation">)</span><span class="token punctuation">;</span></p>
<p><span class="token keyword">int</span> rojo <span class="token operator">=</span> <span class="token function">map</span><span class="token punctuation">(</span>intensidad<span class="token punctuation">,</span> <span class="token number">0</span><span class="token punctuation">,</span> <span class="token number">100</span><span class="token punctuation">,</span> <span class="token number">0</span><span class="token punctuation">,</span> <span class="token number">255</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
<span class="token keyword">int</span> verde <span class="token operator">=</span> <span class="token function">map</span><span class="token punctuation">(</span>intensidad<span class="token punctuation">,</span> <span class="token number">0</span><span class="token punctuation">,</span> <span class="token number">100</span><span class="token punctuation">,</span> <span class="token number">255</span><span class="token punctuation">,</span> <span class="token number">0</span><span class="token punctuation">)</span><span class="token punctuation">;</span></p>
<p><span class="token function">setLED</span><span class="token punctuation">(</span>rojo<span class="token punctuation">,</span> verde<span class="token punctuation">,</span> <span class="token number">0</span><span class="token punctuation">)</span><span class="token punctuation">;</span></p>
<p><span class="token keyword">if</span> <span class="token punctuation">(</span>intensidad <span class="token operator">&gt;</span> <span class="token number">20</span><span class="token punctuation">)</span> <span class="token punctuation">{</span><br>
<span class="token function">tone</span><span class="token punctuation">(</span>buzzerPin<span class="token punctuation">,</span> frecuencia<span class="token punctuation">,</span> duracion<span class="token punctuation">)</span><span class="token punctuation">;</span><br>
Serial<span class="token punctuation">.</span><span class="token function">print</span><span class="token punctuation">(</span><span class="token string">“Buzzer: “</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
Serial<span class="token punctuation">.</span><span class="token function">print</span><span class="token punctuation">(</span>frecuencia<span class="token punctuation">)</span><span class="token punctuation">;</span><br>
Serial<span class="token punctuation">.</span><span class="token function">print</span><span class="token punctuation">(</span><span class="token string">” Hz x “</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
Serial<span class="token punctuation">.</span><span class="token function">print</span><span class="token punctuation">(</span>duracion<span class="token punctuation">)</span><span class="token punctuation">;</span><br>
Serial<span class="token punctuation">.</span><span class="token function">println</span><span class="token punctuation">(</span><span class="token string">” ms”</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
<span class="token function">delay</span><span class="token punctuation">(</span>duracion <span class="token operator">+</span> <span class="token number">100</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
<span class="token function">noTone</span><span class="token punctuation">(</span>buzzerPin<span class="token punctuation">)</span><span class="token punctuation">;</span><br>
<span class="token punctuation">}</span> <span class="token keyword">else</span> <span class="token punctuation">{</span><br>
<span class="token function">noTone</span><span class="token punctuation">(</span>buzzerPin<span class="token punctuation">)</span><span class="token punctuation">;</span><br>
<span class="token punctuation">}</span><br>
<span class="token punctuation">}</span></p>
<p><span class="token comment">// ============================<mark><mark><mark><mark><mark><mark><mark><mark></mark></mark></mark></mark></mark></mark></mark></mark></span><br>
<span class="token comment">// LOOP PRINCIPAL</span><br>
<span class="token comment">// <mark><mark><mark><mark><mark><mark><mark><mark><mark><mark><mark><mark><mark><mark><mark><mark><mark><mark><mark><mark><mark><mark></mark></mark></mark></mark></mark></mark></mark></mark></mark></mark></mark></mark></mark></mark></mark></mark></mark></mark></mark></mark></mark></mark></span><br>
<span class="token keyword">void</span> <span class="token function">loop</span><span class="token punctuation">(</span><span class="token punctuation">)</span> <span class="token punctuation">{</span><br>
Serial<span class="token punctuation">.</span><span class="token function">println</span><span class="token punctuation">(</span><span class="token string">"\n"</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
Serial<span class="token punctuation">.</span><span class="token function">print</span><span class="token punctuation">(</span><span class="token string">“CICLO - Hora: “</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
Serial<span class="token punctuation">.</span><span class="token function">print</span><span class="token punctuation">(</span>horaActual<span class="token punctuation">)</span><span class="token punctuation">;</span><br>
Serial<span class="token punctuation">.</span><span class="token function">println</span><span class="token punctuation">(</span><span class="token string">”:00”</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
Serial<span class="token punctuation">.</span><span class="token function">println</span><span class="token punctuation">(</span><span class="token string">""</span><span class="token punctuation">)</span><span class="token punctuation">;</span></p>
<p><span class="token function">leerSensores</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">;</span></p>
<p><span class="token keyword">int</span> nivelAlerta <span class="token operator">=</span> <span class="token function">redNeuronalDecision</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">;</span></p>
<p><span class="token keyword">float</span> intensidadDifusa <span class="token operator">=</span> fuzzy<span class="token punctuation">.</span><span class="token function">calcularIntensidad</span><span class="token punctuation">(</span>luz<span class="token punctuation">,</span> ruido<span class="token punctuation">,</span> movimiento<span class="token punctuation">)</span><span class="token punctuation">;</span></p>
<p>Serial<span class="token punctuation">.</span><span class="token function">print</span><span class="token punctuation">(</span><span class="token string">"\nDECISION: "</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
Serial<span class="token punctuation">.</span><span class="token function">println</span><span class="token punctuation">(</span>estados<span class="token punctuation">[</span>nivelAlerta<span class="token punctuation">]</span><span class="token punctuation">)</span><span class="token punctuation">;</span></p>
<p><span class="token function">ejecutarAlertaDifusa</span><span class="token punctuation">(</span>nivelAlerta<span class="token punctuation">,</span> intensidadDifusa<span class="token punctuation">)</span><span class="token punctuation">;</span></p>
<p>Serial<span class="token punctuation">.</span><span class="token function">println</span><span class="token punctuation">(</span><span class="token string">"\nEsperando 3 segundos…\n"</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
<span class="token function">delay</span><span class="token punctuation">(</span><span class="token number">3000</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
<span class="token punctuation">}</span><br>
</p>
<hr>
<h1 id="parte-7-entrenamiento-en-google-colab">PARTE 7: ENTRENAMIENTO EN GOOGLE COLAB</h1>
<h2 id="script-completo-de-python">Script Completo de Python</h2>
<p>Copiar y pegar en Google Colab:</p>
<pre class="  language-python"><code class="prism  language-python"><span class="token comment"># ENTRENAMIENTO DE RED NEURONAL PARA ALARMA</span>
</code></pre><p><span class="token comment"># Instalar librerías</span><br>
!pip install tensorflow scikit<span class="token operator">-</span>learn pandas numpy matplotlib seaborn</p>
<p><span class="token comment"># Importar librerías</span><br>
<span class="token keyword">import</span> numpy <span class="token keyword">as</span> np<br>
<span class="token keyword">import</span> pandas <span class="token keyword">as</span> pd<br>
<span class="token keyword">import</span> tensorflow <span class="token keyword">as</span> tf<br>
<span class="token keyword">from</span> sklearn<span class="token punctuation">.</span>model_selection <span class="token keyword">import</span> train_test_split<br>
<span class="token keyword">from</span> sklearn<span class="token punctuation">.</span>preprocessing <span class="token keyword">import</span> StandardScaler<br>
<span class="token keyword">from</span> sklearn<span class="token punctuation">.</span>metrics <span class="token keyword">import</span> confusion_matrix<span class="token punctuation">,</span> classification_report<span class="token punctuation">,</span> accuracy_score<br>
<span class="token keyword">import</span> matplotlib<span class="token punctuation">.</span>pyplot <span class="token keyword">as</span> plt<br>
<span class="token keyword">import</span> seaborn <span class="token keyword">as</span> sns</p>
<p><span class="token keyword">print</span><span class="token punctuation">(</span><span class="token string">“Librerías importadas”</span><span class="token punctuation">)</span><br>
<span class="token keyword">print</span><span class="token punctuation">(</span>f<span class="token string">"TensorFlow versión: {tf.<strong>version</strong>}"</span><span class="token punctuation">)</span></p>
<p><span class="token comment"># ============================================</span><br>
<span class="token comment"># PASO 1: GENERAR DATASET</span><br>
<span class="token comment"># ============================================</span><br>
<span class="token keyword">print</span><span class="token punctuation">(</span><span class="token string">"\nGenerando dataset…"</span><span class="token punctuation">)</span></p>
<p>np<span class="token punctuation">.</span>random<span class="token punctuation">.</span>seed<span class="token punctuation">(</span><span class="token number">42</span><span class="token punctuation">)</span><br>
n_samples <span class="token operator">=</span> <span class="token number">1500</span></p>
<p>data <span class="token operator">=</span> <span class="token punctuation">{</span><br>
<span class="token string">‘movimiento_pir’</span><span class="token punctuation">:</span> np<span class="token punctuation">.</span>random<span class="token punctuation">.</span>choice<span class="token punctuation">(</span><span class="token punctuation">[</span><span class="token number">0</span><span class="token punctuation">,</span> <span class="token number">1</span><span class="token punctuation">]</span><span class="token punctuation">,</span> n_samples<span class="token punctuation">,</span> p<span class="token operator">=</span><span class="token punctuation">[</span><span class="token number">0.7</span><span class="token punctuation">,</span> <span class="token number">0.3</span><span class="token punctuation">]</span><span class="token punctuation">)</span><span class="token punctuation">,</span><br>
<span class="token string">‘nivel_luz’</span><span class="token punctuation">:</span> np<span class="token punctuation">.</span>random<span class="token punctuation">.</span>normal<span class="token punctuation">(</span><span class="token number">1500</span><span class="token punctuation">,</span> <span class="token number">500</span><span class="token punctuation">,</span> n_samples<span class="token punctuation">)</span><span class="token punctuation">,</span><br>
<span class="token string">‘nivel_ruido’</span><span class="token punctuation">:</span> np<span class="token punctuation">.</span>random<span class="token punctuation">.</span>normal<span class="token punctuation">(</span><span class="token number">1800</span><span class="token punctuation">,</span> <span class="token number">600</span><span class="token punctuation">,</span> n_samples<span class="token punctuation">)</span><span class="token punctuation">,</span><br>
<span class="token string">‘hora_dia’</span><span class="token punctuation">:</span> np<span class="token punctuation">.</span>random<span class="token punctuation">.</span>uniform<span class="token punctuation">(</span><span class="token number">0</span><span class="token punctuation">,</span> <span class="token number">24</span><span class="token punctuation">,</span> n_samples<span class="token punctuation">)</span><br>
<span class="token punctuation">}</span></p>
<p>df <span class="token operator">=</span> pd<span class="token punctuation">.</span>DataFrame<span class="token punctuation">(</span>data<span class="token punctuation">)</span><br>
df<span class="token punctuation">[</span><span class="token string">‘nivel_luz’</span><span class="token punctuation">]</span> <span class="token operator">=</span> df<span class="token punctuation">[</span><span class="token string">‘nivel_luz’</span><span class="token punctuation">]</span><span class="token punctuation">.</span>clip<span class="token punctuation">(</span><span class="token number">0</span><span class="token punctuation">,</span> <span class="token number">4095</span><span class="token punctuation">)</span><br>
df<span class="token punctuation">[</span><span class="token string">‘nivel_ruido’</span><span class="token punctuation">]</span> <span class="token operator">=</span> df<span class="token punctuation">[</span><span class="token string">‘nivel_ruido’</span><span class="token punctuation">]</span><span class="token punctuation">.</span>clip<span class="token punctuation">(</span><span class="token number">0</span><span class="token punctuation">,</span> <span class="token number">4095</span><span class="token punctuation">)</span></p>
<p><span class="token keyword">print</span><span class="token punctuation">(</span>f<span class="token string">"Dataset creado: {len(df)} muestras"</span><span class="token punctuation">)</span><br>
<span class="token keyword">print</span><span class="token punctuation">(</span><span class="token string">"\nPrimeras 5 filas:"</span><span class="token punctuation">)</span><br>
<span class="token keyword">print</span><span class="token punctuation">(</span>df<span class="token punctuation">.</span>head<span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">)</span></p>
<p><span class="token comment"># ============================================</span><br>
<span class="token comment"># PASO 2: ETIQUETAR DATOS</span><br>
<span class="token comment"># ============================================</span><br>
<span class="token keyword">print</span><span class="token punctuation">(</span><span class="token string">"\nEtiquetando datos…"</span><span class="token punctuation">)</span></p>
<p><span class="token keyword">def</span> <span class="token function">clasificar_amenaza</span><span class="token punctuation">(</span>fila<span class="token punctuation">)</span><span class="token punctuation">:</span><br>
movimiento <span class="token operator">=</span> fila<span class="token punctuation">[</span><span class="token string">‘movimiento_pir’</span><span class="token punctuation">]</span><br>
luz <span class="token operator">=</span> fila<span class="token punctuation">[</span><span class="token string">‘nivel_luz’</span><span class="token punctuation">]</span><br>
ruido <span class="token operator">=</span> fila<span class="token punctuation">[</span><span class="token string">‘nivel_ruido’</span><span class="token punctuation">]</span><br>
hora <span class="token operator">=</span> fila<span class="token punctuation">[</span><span class="token string">‘hora_dia’</span><span class="token punctuation">]</span></p>
<pre><code>es_noche &lt;span class="token operator"&gt;=&lt;/span&gt; luz &lt;span class="token operator"&gt;&amp;lt;&lt;/span&gt; &lt;span class="token number"&gt;800&lt;/span&gt;
es_ruidoso &lt;span class="token operator"&gt;=&lt;/span&gt; ruido &lt;span class="token operator"&gt;&amp;gt;&lt;/span&gt; &lt;span class="token number"&gt;2000&lt;/span&gt;
hora_sospechosa &lt;span class="token operator"&gt;=&lt;/span&gt; &lt;span class="token punctuation"&gt;(&lt;/span&gt;hora &lt;span class="token operator"&gt;&amp;lt;&lt;/span&gt; &lt;span class="token number"&gt;6&lt;/span&gt;&lt;span class="token punctuation"&gt;)&lt;/span&gt; &lt;span class="token operator"&gt;or&lt;/span&gt; &lt;span class="token punctuation"&gt;(&lt;/span&gt;hora &lt;span class="token operator"&gt;&amp;gt;&lt;/span&gt; &lt;span class="token number"&gt;22&lt;/span&gt;&lt;span class="token punctuation"&gt;)&lt;/span&gt;

&lt;span class="token keyword"&gt;if&lt;/span&gt; &lt;span class="token operator"&gt;not&lt;/span&gt; movimiento&lt;span class="token punctuation"&gt;:&lt;/span&gt;
    &lt;span class="token keyword"&gt;return&lt;/span&gt; &lt;span class="token number"&gt;0&lt;/span&gt;  &lt;span class="token comment"&gt;# NORMAL&lt;/span&gt;

&lt;span class="token keyword"&gt;if&lt;/span&gt; movimiento &lt;span class="token operator"&gt;and&lt;/span&gt; es_noche &lt;span class="token operator"&gt;and&lt;/span&gt; hora_sospechosa&lt;span class="token punctuation"&gt;:&lt;/span&gt;
    &lt;span class="token keyword"&gt;return&lt;/span&gt; &lt;span class="token number"&gt;3&lt;/span&gt;  &lt;span class="token comment"&gt;# ALERTA ALTA&lt;/span&gt;

&lt;span class="token keyword"&gt;if&lt;/span&gt; movimiento &lt;span class="token operator"&gt;and&lt;/span&gt; es_ruidoso&lt;span class="token punctuation"&gt;:&lt;/span&gt;
    &lt;span class="token keyword"&gt;return&lt;/span&gt; &lt;span class="token number"&gt;2&lt;/span&gt;  &lt;span class="token comment"&gt;# ALERTA MEDIA&lt;/span&gt;

&lt;span class="token keyword"&gt;if&lt;/span&gt; movimiento&lt;span class="token punctuation"&gt;:&lt;/span&gt;
    &lt;span class="token keyword"&gt;return&lt;/span&gt; &lt;span class="token number"&gt;1&lt;/span&gt;  &lt;span class="token comment"&gt;# ALERTA BAJA&lt;/span&gt;

&lt;span class="token keyword"&gt;return&lt;/span&gt; &lt;span class="token number"&gt;0&lt;/span&gt;
</code></pre>
<p>df<span class="token punctuation">[</span><span class="token string">‘nivel_alerta’</span><span class="token punctuation">]</span> <span class="token operator">=</span> df<span class="token punctuation">.</span><span class="token builtin">apply</span><span class="token punctuation">(</span>clasificar_amenaza<span class="token punctuation">,</span> axis<span class="token operator">=</span><span class="token number">1</span><span class="token punctuation">)</span></p>
<p><span class="token keyword">print</span><span class="token punctuation">(</span><span class="token string">“Etiquetado completado”</span><span class="token punctuation">)</span><br>
<span class="token keyword">print</span><span class="token punctuation">(</span><span class="token string">"\nDistribución de clases:"</span><span class="token punctuation">)</span><br>
<span class="token keyword">print</span><span class="token punctuation">(</span>df<span class="token punctuation">[</span><span class="token string">‘nivel_alerta’</span><span class="token punctuation">]</span><span class="token punctuation">.</span>value_counts<span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">.</span>sort_index<span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">)</span></p>
<p><span class="token comment"># Visualizar distribución</span><br>
plt<span class="token punctuation">.</span>figure<span class="token punctuation">(</span>figsize<span class="token operator">=</span><span class="token punctuation">(</span><span class="token number">10</span><span class="token punctuation">,</span> <span class="token number">5</span><span class="token punctuation">)</span><span class="token punctuation">)</span><br>
df<span class="token punctuation">[</span><span class="token string">‘nivel_alerta’</span><span class="token punctuation">]</span><span class="token punctuation">.</span>value_counts<span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">.</span>sort_index<span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">.</span>plot<span class="token punctuation">(</span><br>
kind<span class="token operator">=</span><span class="token string">‘bar’</span><span class="token punctuation">,</span><br>
color<span class="token operator">=</span><span class="token punctuation">[</span><span class="token string">‘green’</span><span class="token punctuation">,</span> <span class="token string">‘yellow’</span><span class="token punctuation">,</span> <span class="token string">‘orange’</span><span class="token punctuation">,</span> <span class="token string">‘red’</span><span class="token punctuation">]</span><br>
<span class="token punctuation">)</span><br>
plt<span class="token punctuation">.</span>title<span class="token punctuation">(</span><span class="token string">‘Distribución de Niveles de Alerta’</span><span class="token punctuation">)</span><br>
plt<span class="token punctuation">.</span>xlabel<span class="token punctuation">(</span><span class="token string">‘Nivel de Alerta’</span><span class="token punctuation">)</span><br>
plt<span class="token punctuation">.</span>ylabel<span class="token punctuation">(</span><span class="token string">‘Cantidad de Muestras’</span><span class="token punctuation">)</span><br>
plt<span class="token punctuation">.</span>xticks<span class="token punctuation">(</span><span class="token punctuation">[</span><span class="token number">0</span><span class="token punctuation">,</span> <span class="token number">1</span><span class="token punctuation">,</span> <span class="token number">2</span><span class="token punctuation">,</span> <span class="token number">3</span><span class="token punctuation">]</span><span class="token punctuation">,</span> <span class="token punctuation">[</span><span class="token string">‘Normal’</span><span class="token punctuation">,</span> <span class="token string">‘Baja’</span><span class="token punctuation">,</span> <span class="token string">‘Media’</span><span class="token punctuation">,</span> <span class="token string">‘Alta’</span><span class="token punctuation">]</span><span class="token punctuation">,</span> rotation<span class="token operator">=</span><span class="token number">0</span><span class="token punctuation">)</span><br>
plt<span class="token punctuation">.</span>show<span class="token punctuation">(</span><span class="token punctuation">)</span></p>
<p><span class="token comment"># ============================================</span><br>
<span class="token comment"># PASO 3: PREPARAR DATOS</span><br>
<span class="token comment"># ============================================</span><br>
<span class="token keyword">print</span><span class="token punctuation">(</span><span class="token string">"\nPreparando datos…"</span><span class="token punctuation">)</span></p>
<p>X <span class="token operator">=</span> df<span class="token punctuation">[</span><span class="token punctuation">[</span><span class="token string">‘movimiento_pir’</span><span class="token punctuation">,</span> <span class="token string">‘nivel_luz’</span><span class="token punctuation">,</span> <span class="token string">‘nivel_ruido’</span><span class="token punctuation">,</span> <span class="token string">‘hora_dia’</span><span class="token punctuation">]</span><span class="token punctuation">]</span><span class="token punctuation">.</span>values<br>
y <span class="token operator">=</span> df<span class="token punctuation">[</span><span class="token string">‘nivel_alerta’</span><span class="token punctuation">]</span><span class="token punctuation">.</span>values</p>
<p><span class="token keyword">print</span><span class="token punctuation">(</span>f<span class="token string">"X shape: {X.shape}"</span><span class="token punctuation">)</span><br>
<span class="token keyword">print</span><span class="token punctuation">(</span>f<span class="token string">"y shape: {y.shape}"</span><span class="token punctuation">)</span></p>
<p><span class="token comment"># Normalizar</span><br>
scaler <span class="token operator">=</span> StandardScaler<span class="token punctuation">(</span><span class="token punctuation">)</span><br>
X_scaled <span class="token operator">=</span> scaler<span class="token punctuation">.</span>fit_transform<span class="token punctuation">(</span>X<span class="token punctuation">)</span></p>
<p><span class="token keyword">print</span><span class="token punctuation">(</span><span class="token string">"\nDatos normalizados:"</span><span class="token punctuation">)</span><br>
<span class="token keyword">print</span><span class="token punctuation">(</span>f<span class="token string">"Media: {X_scaled.mean(axis=0)}"</span><span class="token punctuation">)</span><br>
<span class="token keyword">print</span><span class="token punctuation">(</span>f<span class="token string">"Desv Estándar: {X_scaled.std(axis=0)}"</span><span class="token punctuation">)</span></p>
<p><span class="token comment"># Dividir en train/test</span><br>
X_train<span class="token punctuation">,</span> X_test<span class="token punctuation">,</span> y_train<span class="token punctuation">,</span> y_test <span class="token operator">=</span> train_test_split<span class="token punctuation">(</span><br>
X_scaled<span class="token punctuation">,</span> y<span class="token punctuation">,</span><br>
test_size<span class="token operator">=</span><span class="token number">0.2</span><span class="token punctuation">,</span><br>
random_state<span class="token operator">=</span><span class="token number">42</span><span class="token punctuation">,</span><br>
stratify<span class="token operator">=</span>y<br>
<span class="token punctuation">)</span></p>
<p><span class="token keyword">print</span><span class="token punctuation">(</span>f<span class="token string">"\nDivisión completada:"</span><span class="token punctuation">)</span><br>
<span class="token keyword">print</span><span class="token punctuation">(</span>f<span class="token string">"  Entrenamiento: {len(X_train)} muestras"</span><span class="token punctuation">)</span><br>
<span class="token keyword">print</span><span class="token punctuation">(</span>f<span class="token string">"  Prueba: {len(X_test)} muestras"</span><span class="token punctuation">)</span></p>
<p><span class="token comment"># ============================================</span><br>
<span class="token comment"># PASO 4: CONSTRUIR RED NEURONAL</span><br>
<span class="token comment"># ============================================</span><br>
<span class="token keyword">print</span><span class="token punctuation">(</span><span class="token string">"\nConstruyendo red neuronal…"</span><span class="token punctuation">)</span></p>
<p>model <span class="token operator">=</span> tf<span class="token punctuation">.</span>keras<span class="token punctuation">.</span>Sequential<span class="token punctuation">(</span><span class="token punctuation">[</span><br>
tf<span class="token punctuation">.</span>keras<span class="token punctuation">.</span>layers<span class="token punctuation">.</span>Input<span class="token punctuation">(</span>shape<span class="token operator">=</span><span class="token punctuation">(</span><span class="token number">4</span><span class="token punctuation">,</span><span class="token punctuation">)</span><span class="token punctuation">)</span><span class="token punctuation">,</span><br>
tf<span class="token punctuation">.</span>keras<span class="token punctuation">.</span>layers<span class="token punctuation">.</span>Dense<span class="token punctuation">(</span><span class="token number">32</span><span class="token punctuation">,</span> activation<span class="token operator">=</span><span class="token string">‘relu’</span><span class="token punctuation">,</span> name<span class="token operator">=</span><span class="token string">‘hidden1’</span><span class="token punctuation">)</span><span class="token punctuation">,</span><br>
tf<span class="token punctuation">.</span>keras<span class="token punctuation">.</span>layers<span class="token punctuation">.</span>Dropout<span class="token punctuation">(</span><span class="token number">0.3</span><span class="token punctuation">,</span> name<span class="token operator">=</span><span class="token string">‘dropout1’</span><span class="token punctuation">)</span><span class="token punctuation">,</span><br>
tf<span class="token punctuation">.</span>keras<span class="token punctuation">.</span>layers<span class="token punctuation">.</span>Dense<span class="token punctuation">(</span><span class="token number">16</span><span class="token punctuation">,</span> activation<span class="token operator">=</span><span class="token string">‘relu’</span><span class="token punctuation">,</span> name<span class="token operator">=</span><span class="token string">‘hidden2’</span><span class="token punctuation">)</span><span class="token punctuation">,</span><br>
tf<span class="token punctuation">.</span>keras<span class="token punctuation">.</span>layers<span class="token punctuation">.</span>Dense<span class="token punctuation">(</span><span class="token number">4</span><span class="token punctuation">,</span> activation<span class="token operator">=</span><span class="token string">‘softmax’</span><span class="token punctuation">,</span> name<span class="token operator">=</span><span class="token string">‘output’</span><span class="token punctuation">)</span><br>
<span class="token punctuation">]</span><span class="token punctuation">)</span></p>
<p><span class="token keyword">print</span><span class="token punctuation">(</span><span class="token string">"\nResumen de la red:"</span><span class="token punctuation">)</span><br>
model<span class="token punctuation">.</span>summary<span class="token punctuation">(</span><span class="token punctuation">)</span></p>
<p><span class="token comment"># ============================================</span><br>
<span class="token comment"># PASO 5: COMPILAR MODELO</span><br>
<span class="token comment"># ============================================</span><br>
<span class="token keyword">print</span><span class="token punctuation">(</span><span class="token string">"\nCompilando modelo…"</span><span class="token punctuation">)</span></p>
<p>model<span class="token punctuation">.</span><span class="token builtin">compile</span><span class="token punctuation">(</span><br>
optimizer<span class="token operator">=</span><span class="token string">‘adam’</span><span class="token punctuation">,</span><br>
loss<span class="token operator">=</span><span class="token string">‘sparse_categorical_crossentropy’</span><span class="token punctuation">,</span><br>
metrics<span class="token operator">=</span><span class="token punctuation">[</span><span class="token string">‘accuracy’</span><span class="token punctuation">]</span><br>
<span class="token punctuation">)</span></p>
<p><span class="token keyword">print</span><span class="token punctuation">(</span><span class="token string">“Modelo compilado”</span><span class="token punctuation">)</span></p>
<p><span class="token comment"># ============================================</span><br>
<span class="token comment"># PASO 6: ENTRENAR</span><br>
<span class="token comment"># ============================================</span><br>
<span class="token keyword">print</span><span class="token punctuation">(</span><span class="token string">"\nIniciando entrenamiento…"</span><span class="token punctuation">)</span></p>
<p>history <span class="token operator">=</span> model<span class="token punctuation">.</span>fit<span class="token punctuation">(</span><br>
X_train<span class="token punctuation">,</span> y_train<span class="token punctuation">,</span><br>
epochs<span class="token operator">=</span><span class="token number">50</span><span class="token punctuation">,</span><br>
batch_size<span class="token operator">=</span><span class="token number">16</span><span class="token punctuation">,</span><br>
validation_split<span class="token operator">=</span><span class="token number">0.2</span><span class="token punctuation">,</span><br>
verbose<span class="token operator">=</span><span class="token number">1</span><br>
<span class="token punctuation">)</span></p>
<p><span class="token keyword">print</span><span class="token punctuation">(</span><span class="token string">"\nEntrenamiento completado"</span><span class="token punctuation">)</span></p>
<p><span class="token comment"># ============================================</span><br>
<span class="token comment"># PASO 7: VISUALIZAR ENTRENAMIENTO</span><br>
<span class="token comment"># ============================================</span><br>
<span class="token keyword">print</span><span class="token punctuation">(</span><span class="token string">"\nGenerando gráficas…"</span><span class="token punctuation">)</span></p>
<p>plt<span class="token punctuation">.</span>figure<span class="token punctuation">(</span>figsize<span class="token operator">=</span><span class="token punctuation">(</span><span class="token number">14</span><span class="token punctuation">,</span> <span class="token number">5</span><span class="token punctuation">)</span><span class="token punctuation">)</span></p>
<p><span class="token comment"># Gráfica 1: Accuracy</span><br>
plt<span class="token punctuation">.</span>subplot<span class="token punctuation">(</span><span class="token number">1</span><span class="token punctuation">,</span> <span class="token number">2</span><span class="token punctuation">,</span> <span class="token number">1</span><span class="token punctuation">)</span><br>
plt<span class="token punctuation">.</span>plot<span class="token punctuation">(</span>history<span class="token punctuation">.</span>history<span class="token punctuation">[</span><span class="token string">‘accuracy’</span><span class="token punctuation">]</span><span class="token punctuation">,</span> label<span class="token operator">=</span><span class="token string">‘Train Accuracy’</span><span class="token punctuation">,</span> linewidth<span class="token operator">=</span><span class="token number">2</span><span class="token punctuation">)</span><br>
plt<span class="token punctuation">.</span>plot<span class="token punctuation">(</span>history<span class="token punctuation">.</span>history<span class="token punctuation">[</span><span class="token string">‘val_accuracy’</span><span class="token punctuation">]</span><span class="token punctuation">,</span> label<span class="token operator">=</span><span class="token string">‘Validation Accuracy’</span><span class="token punctuation">,</span> linewidth<span class="token operator">=</span><span class="token number">2</span><span class="token punctuation">)</span><br>
plt<span class="token punctuation">.</span>title<span class="token punctuation">(</span><span class="token string">‘Precisión del Modelo’</span><span class="token punctuation">,</span> fontsize<span class="token operator">=</span><span class="token number">14</span><span class="token punctuation">)</span><br>
plt<span class="token punctuation">.</span>xlabel<span class="token punctuation">(</span><span class="token string">‘Época’</span><span class="token punctuation">)</span><br>
plt<span class="token punctuation">.</span>ylabel<span class="token punctuation">(</span><span class="token string">‘Accuracy’</span><span class="token punctuation">)</span><br>
plt<span class="token punctuation">.</span>legend<span class="token punctuation">(</span><span class="token punctuation">)</span><br>
plt<span class="token punctuation">.</span>grid<span class="token punctuation">(</span><span class="token boolean">True</span><span class="token punctuation">,</span> alpha<span class="token operator">=</span><span class="token number">0.3</span><span class="token punctuation">)</span></p>
<p><span class="token comment"># Gráfica 2: Loss</span><br>
plt<span class="token punctuation">.</span>subplot<span class="token punctuation">(</span><span class="token number">1</span><span class="token punctuation">,</span> <span class="token number">2</span><span class="token punctuation">,</span> <span class="token number">2</span><span class="token punctuation">)</span><br>
plt<span class="token punctuation">.</span>plot<span class="token punctuation">(</span>history<span class="token punctuation">.</span>history<span class="token punctuation">[</span><span class="token string">‘loss’</span><span class="token punctuation">]</span><span class="token punctuation">,</span> label<span class="token operator">=</span><span class="token string">‘Train Loss’</span><span class="token punctuation">,</span> linewidth<span class="token operator">=</span><span class="token number">2</span><span class="token punctuation">)</span><br>
plt<span class="token punctuation">.</span>plot<span class="token punctuation">(</span>history<span class="token punctuation">.</span>history<span class="token punctuation">[</span><span class="token string">‘val_loss’</span><span class="token punctuation">]</span><span class="token punctuation">,</span> label<span class="token operator">=</span><span class="token string">‘Validation Loss’</span><span class="token punctuation">,</span> linewidth<span class="token operator">=</span><span class="token number">2</span><span class="token punctuation">)</span><br>
plt<span class="token punctuation">.</span>title<span class="token punctuation">(</span><span class="token string">‘Pérdida del Modelo’</span><span class="token punctuation">,</span> fontsize<span class="token operator">=</span><span class="token number">14</span><span class="token punctuation">)</span><br>
plt<span class="token punctuation">.</span>xlabel<span class="token punctuation">(</span><span class="token string">‘Época’</span><span class="token punctuation">)</span><br>
plt<span class="token punctuation">.</span>ylabel<span class="token punctuation">(</span><span class="token string">‘Loss’</span><span class="token punctuation">)</span><br>
plt<span class="token punctuation">.</span>legend<span class="token punctuation">(</span><span class="token punctuation">)</span><br>
plt<span class="token punctuation">.</span>grid<span class="token punctuation">(</span><span class="token boolean">True</span><span class="token punctuation">,</span> alpha<span class="token operator">=</span><span class="token number">0.3</span><span class="token punctuation">)</span></p>
<p>plt<span class="token punctuation">.</span>tight_layout<span class="token punctuation">(</span><span class="token punctuation">)</span><br>
plt<span class="token punctuation">.</span>show<span class="token punctuation">(</span><span class="token punctuation">)</span></p>
<p><span class="token comment"># ============================================</span><br>
<span class="token comment"># PASO 8: EVALUAR</span><br>
<span class="token comment"># ============================================</span><br>
<span class="token keyword">print</span><span class="token punctuation">(</span><span class="token string">"\nEvaluando modelo…"</span><span class="token punctuation">)</span></p>
<p>y_pred <span class="token operator">=</span> model<span class="token punctuation">.</span>predict<span class="token punctuation">(</span>X_test<span class="token punctuation">)</span><br>
y_pred_classes <span class="token operator">=</span> np<span class="token punctuation">.</span>argmax<span class="token punctuation">(</span>y_pred<span class="token punctuation">,</span> axis<span class="token operator">=</span><span class="token number">1</span><span class="token punctuation">)</span></p>
<p>accuracy <span class="token operator">=</span> accuracy_score<span class="token punctuation">(</span>y_test<span class="token punctuation">,</span> y_pred_classes<span class="token punctuation">)</span><br>
<span class="token keyword">print</span><span class="token punctuation">(</span>f<span class="token string">"\nAccuracy en datos de prueba: {accuracy*100:.2f}%"</span><span class="token punctuation">)</span></p>
<p><span class="token keyword">print</span><span class="token punctuation">(</span><span class="token string">"\nReporte de Clasificación:"</span><span class="token punctuation">)</span><br>
<span class="token keyword">print</span><span class="token punctuation">(</span>classification_report<span class="token punctuation">(</span><br>
y_test<span class="token punctuation">,</span> y_pred_classes<span class="token punctuation">,</span><br>
target_names<span class="token operator">=</span><span class="token punctuation">[</span><span class="token string">‘Normal’</span><span class="token punctuation">,</span> <span class="token string">‘Alerta Baja’</span><span class="token punctuation">,</span> <span class="token string">‘Alerta Media’</span><span class="token punctuation">,</span> <span class="token string">‘Alerta Alta’</span><span class="token punctuation">]</span><br>
<span class="token punctuation">)</span><span class="token punctuation">)</span></p>
<p><span class="token comment"># Matriz de confusión</span><br>
cm <span class="token operator">=</span> confusion_matrix<span class="token punctuation">(</span>y_test<span class="token punctuation">,</span> y_pred_classes<span class="token punctuation">)</span></p>
<p>plt<span class="token punctuation">.</span>figure<span class="token punctuation">(</span>figsize<span class="token operator">=</span><span class="token punctuation">(</span><span class="token number">8</span><span class="token punctuation">,</span> <span class="token number">6</span><span class="token punctuation">)</span><span class="token punctuation">)</span><br>
sns<span class="token punctuation">.</span>heatmap<span class="token punctuation">(</span>cm<span class="token punctuation">,</span> annot<span class="token operator">=</span><span class="token boolean">True</span><span class="token punctuation">,</span> fmt<span class="token operator">=</span><span class="token string">‘d’</span><span class="token punctuation">,</span> cmap<span class="token operator">=</span><span class="token string">‘Blues’</span><span class="token punctuation">,</span><br>
xticklabels<span class="token operator">=</span><span class="token punctuation">[</span><span class="token string">‘Normal’</span><span class="token punctuation">,</span> <span class="token string">‘Baja’</span><span class="token punctuation">,</span> <span class="token string">‘Media’</span><span class="token punctuation">,</span> <span class="token string">‘Alta’</span><span class="token punctuation">]</span><span class="token punctuation">,</span><br>
yticklabels<span class="token operator">=</span><span class="token punctuation">[</span><span class="token string">‘Normal’</span><span class="token punctuation">,</span> <span class="token string">‘Baja’</span><span class="token punctuation">,</span> <span class="token string">‘Media’</span><span class="token punctuation">,</span> <span class="token string">‘Alta’</span><span class="token punctuation">]</span><span class="token punctuation">)</span><br>
plt<span class="token punctuation">.</span>title<span class="token punctuation">(</span><span class="token string">‘Matriz de Confusión’</span><span class="token punctuation">)</span><br>
plt<span class="token punctuation">.</span>ylabel<span class="token punctuation">(</span><span class="token string">‘Clase Real’</span><span class="token punctuation">)</span><br>
plt<span class="token punctuation">.</span>xlabel<span class="token punctuation">(</span><span class="token string">‘Clase Predicha’</span><span class="token punctuation">)</span><br>
plt<span class="token punctuation">.</span>show<span class="token punctuation">(</span><span class="token punctuation">)</span></p>
<p><span class="token comment"># ============================================</span><br>
<span class="token comment"># PASO 9: GUARDAR MODELO</span><br>
<span class="token comment"># ============================================</span><br>
<span class="token keyword">print</span><span class="token punctuation">(</span><span class="token string">"\nGuardando modelo…"</span><span class="token punctuation">)</span></p>
<p>model<span class="token punctuation">.</span>save<span class="token punctuation">(</span><span class="token string">‘sistema_alarma_modelo.h5’</span><span class="token punctuation">)</span><br>
<span class="token keyword">print</span><span class="token punctuation">(</span><span class="token string">“Modelo guardado como ‘sistema_alarma_modelo.h5’”</span><span class="token punctuation">)</span></p>
<p><span class="token keyword">import</span> pickle<br>
<span class="token keyword">with</span> <span class="token builtin">open</span><span class="token punctuation">(</span><span class="token string">‘scaler.pkl’</span><span class="token punctuation">,</span> <span class="token string">‘wb’</span><span class="token punctuation">)</span> <span class="token keyword">as</span> f<span class="token punctuation">:</span><br>
pickle<span class="token punctuation">.</span>dump<span class="token punctuation">(</span>scaler<span class="token punctuation">,</span> f<span class="token punctuation">)</span><br>
<span class="token keyword">print</span><span class="token punctuation">(</span><span class="token string">“Scaler guardado como ‘scaler.pkl’”</span><span class="token punctuation">)</span></p>
<p><span class="token comment"># ============================================</span><br>
<span class="token comment"># PASO 10: PROBAR CON EJEMPLOS</span><br>
<span class="token comment"># ============================================</span><br>
<span class="token keyword">print</span><span class="token punctuation">(</span><span class="token string">"\nPRUEBAS CON EJEMPLOS:"</span><span class="token punctuation">)</span></p>
<p><span class="token keyword">def</span> <span class="token function">predecir_alerta</span><span class="token punctuation">(</span>movimiento<span class="token punctuation">,</span> luz<span class="token punctuation">,</span> ruido<span class="token punctuation">,</span> hora<span class="token punctuation">)</span><span class="token punctuation">:</span><br>
entrada <span class="token operator">=</span> np<span class="token punctuation">.</span>array<span class="token punctuation">(</span><span class="token punctuation">[</span><span class="token punctuation">[</span>movimiento<span class="token punctuation">,</span> luz<span class="token punctuation">,</span> ruido<span class="token punctuation">,</span> hora<span class="token punctuation">]</span><span class="token punctuation">]</span><span class="token punctuation">)</span><br>
entrada_scaled <span class="token operator">=</span> scaler<span class="token punctuation">.</span>transform<span class="token punctuation">(</span>entrada<span class="token punctuation">)</span><br>
probabilidades <span class="token operator">=</span> model<span class="token punctuation">.</span>predict<span class="token punctuation">(</span>entrada_scaled<span class="token punctuation">,</span> verbose<span class="token operator">=</span><span class="token number">0</span><span class="token punctuation">)</span><span class="token punctuation">[</span><span class="token number">0</span><span class="token punctuation">]</span><br>
clase <span class="token operator">=</span> np<span class="token punctuation">.</span>argmax<span class="token punctuation">(</span>probabilidades<span class="token punctuation">)</span></p>
<pre><code>estados &lt;span class="token operator"&gt;=&lt;/span&gt; &lt;span class="token punctuation"&gt;[&lt;/span&gt;&lt;span class="token string"&gt;'NORMAL'&lt;/span&gt;&lt;span class="token punctuation"&gt;,&lt;/span&gt; &lt;span class="token string"&gt;'ALERTA BAJA'&lt;/span&gt;&lt;span class="token punctuation"&gt;,&lt;/span&gt; &lt;span class="token string"&gt;'ALERTA MEDIA'&lt;/span&gt;&lt;span class="token punctuation"&gt;,&lt;/span&gt; &lt;span class="token string"&gt;'ALERTA ALTA'&lt;/span&gt;&lt;span class="token punctuation"&gt;]&lt;/span&gt;

&lt;span class="token keyword"&gt;print&lt;/span&gt;&lt;span class="token punctuation"&gt;(&lt;/span&gt;f&lt;span class="token string"&gt;"\nEntrada: Mov={movimiento}, Luz={luz}, Ruido={ruido}, Hora={hora}h"&lt;/span&gt;&lt;span class="token punctuation"&gt;)&lt;/span&gt;
&lt;span class="token keyword"&gt;print&lt;/span&gt;&lt;span class="token punctuation"&gt;(&lt;/span&gt;f&lt;span class="token string"&gt;"Predicción: {estados[clase]} (Confianza: {probabilidades[clase]*100:.1f}%)"&lt;/span&gt;&lt;span class="token punctuation"&gt;)&lt;/span&gt;
&lt;span class="token keyword"&gt;print&lt;/span&gt;&lt;span class="token punctuation"&gt;(&lt;/span&gt;f&lt;span class="token string"&gt;"  Probabilidades: Normal={probabilidades[0]*100:.1f}% | "&lt;/span&gt;
      f&lt;span class="token string"&gt;"Baja={probabilidades[1]*100:.1f}% | "&lt;/span&gt;
      f&lt;span class="token string"&gt;"Media={probabilidades[2]*100:.1f}% | "&lt;/span&gt;
      f&lt;span class="token string"&gt;"Alta={probabilidades[3]*100:.1f}%"&lt;/span&gt;&lt;span class="token punctuation"&gt;)&lt;/span&gt;

&lt;span class="token keyword"&gt;return&lt;/span&gt; clase
</code></pre>
<p><span class="token keyword">print</span><span class="token punctuation">(</span><span class="token string">"\n"</span> <span class="token operator">+</span> <span class="token string">"="</span><span class="token operator"><em></em></span><span class="token number">60</span><span class="token punctuation">)</span><br>
<span class="token keyword">print</span><span class="token punctuation">(</span><span class="token string">“CASOS DE PRUEBA:”</span><span class="token punctuation">)</span><br>
<span class="token keyword">print</span><span class="token punctuation">(</span><span class="token string">"="</span><span class="token operator"></span><span class="token number">60</span><span class="token punctuation">)</span></p>
<p>predecir_alerta<span class="token punctuation">(</span>movimiento<span class="token operator">=</span><span class="token number">0</span><span class="token punctuation">,</span> luz<span class="token operator">=</span><span class="token number">1500</span><span class="token punctuation">,</span> ruido<span class="token operator">=</span><span class="token number">1500</span><span class="token punctuation">,</span> hora<span class="token operator">=</span><span class="token number">14</span><span class="token punctuation">)</span><br>
predecir_alerta<span class="token punctuation">(</span>movimiento<span class="token operator">=</span><span class="token number">1</span><span class="token punctuation">,</span> luz<span class="token operator">=</span><span class="token number">1800</span><span class="token punctuation">,</span> ruido<span class="token operator">=</span><span class="token number">1600</span><span class="token punctuation">,</span> hora<span class="token operator">=</span><span class="token number">14</span><span class="token punctuation">)</span><br>
predecir_alerta<span class="token punctuation">(</span>movimiento<span class="token operator">=</span><span class="token number">1</span><span class="token punctuation">,</span> luz<span class="token operator">=</span><span class="token number">600</span><span class="token punctuation">,</span> ruido<span class="token operator">=</span><span class="token number">2200</span><span class="token punctuation">,</span> hora<span class="token operator">=</span><span class="token number">23</span><span class="token punctuation">)</span><br>
predecir_alerta<span class="token punctuation">(</span>movimiento<span class="token operator">=</span><span class="token number">1</span><span class="token punctuation">,</span> luz<span class="token operator">=</span><span class="token number">1200</span><span class="token punctuation">,</span> ruido<span class="token operator">=</span><span class="token number">2300</span><span class="token punctuation">,</span> hora<span class="token operator">=</span><span class="token number">12</span><span class="token punctuation">)</span></p>
<p><span class="token keyword">print</span><span class="token punctuation">(</span><span class="token string">"\n¡Entrenamiento completado!"</span><span class="token punctuation">)</span><br>
</p>
<hr>
<h1 id="parte-8-interpretación-de-resultados">PARTE 8: INTERPRETACIÓN DE RESULTADOS</h1>
<h2 id="¿qué-buscar-en-las-gráficas">¿Qué Buscar en las Gráficas?</h2>
<h3 id="gráfica-de-accuracy-precisión">Gráfica de Accuracy (Precisión)</h3>
<p><strong>Bueno:</strong></p>
<ul>
<li>Train y Validation suben juntas</li>
<li>Alcanzan &gt;85% de accuracy</li>
<li>Se estabilizan sin grandes oscilaciones</li>
</ul>
<p><strong>Malo:</strong></p>
<ul>
<li>Validation baja mientras Train sube (overfitting)</li>
<li>Ambas se quedan &lt;70% (underfitting)</li>
<li>Validation oscila mucho (inestabilidad)</li>
</ul>
<h3 id="gráfica-de-loss-pérdida">Gráfica de Loss (Pérdida)</h3>
<p><strong>Bueno:</strong></p>
<ul>
<li>Train y Validation bajan juntas</li>
<li>Se estabilizan cerca de 0.2-0.4</li>
<li>Curvas suaves</li>
</ul>
<p><strong>Malo:</strong></p>
<ul>
<li>Validation sube mientras Train baja (overfitting)</li>
<li>Ambas se quedan altas &gt;1.0 (no aprende)</li>
</ul>
<h3 id="matriz-de-confusión">Matriz de Confusión</h3>
<p>La diagonal principal (de arriba-izquierda a abajo-derecha) debe tener los números más altos.</p>
<p><strong>Ejemplo de buena matriz:</strong></p>
<pre><code>         Predicho
         N   B   M   A
Real N [200  5   2   0]  ← Pocos errores
     B [ 10 180  8   2]
     M [  3  12 175  10]
     A [  1   2  15 182]
</code></pre>
<hr>
<h1 id="parte-9-rúbrica-de-evaluación">PARTE 9: RÚBRICA DE EVALUACIÓN</h1>
<h2 id="actividad-8-red-neuronal-15-puntos">Actividad 8: Red Neuronal (15 puntos)</h2>
<table>
<thead>
<tr>
<th>Criterio</th>
<th>Puntos</th>
<th>Descripción</th>
</tr>
</thead>
<tbody>
<tr>
<td><strong>Dataset</strong></td>
<td>2</td>
<td>Generación correcta de datos con 4 características</td>
</tr>
<tr>
<td><strong>Preprocesamiento</strong></td>
<td>2</td>
<td>Normalización y división train/test</td>
</tr>
<tr>
<td><strong>Arquitectura</strong></td>
<td>3</td>
<td>Red con 2+ capas ocultas, dropout, activaciones</td>
</tr>
<tr>
<td><strong>Entrenamiento</strong></td>
<td>3</td>
<td>Modelo entrena y muestra gráficas</td>
</tr>
<tr>
<td><strong>Evaluación</strong></td>
<td>3</td>
<td>Accuracy &gt;80%, matriz de confusión</td>
</tr>
<tr>
<td><strong>Documentación</strong></td>
<td>2</td>
<td>Código comentado y explicación clara</td>
</tr>
</tbody>
</table><h2 id="actividad-9-lógica-difusa-15-puntos">Actividad 9: Lógica Difusa (15 puntos)</h2>
<table>
<thead>
<tr>
<th>Criterio</th>
<th>Puntos</th>
<th>Descripción</th>
</tr>
</thead>
<tbody>
<tr>
<td><strong>Funciones Membresía</strong></td>
<td>4</td>
<td>3+ funciones para luz y 3+ para ruido</td>
</tr>
<tr>
<td><strong>Reglas Difusas</strong></td>
<td>3</td>
<td>Mínimo 3 reglas SI-ENTONCES</td>
</tr>
<tr>
<td><strong>Defuzzificación</strong></td>
<td>3</td>
<td>Cálculo correcto del centro de gravedad</td>
</tr>
<tr>
<td><strong>Integración</strong></td>
<td>3</td>
<td>Lógica difusa modifica intensidad</td>
</tr>
<tr>
<td><strong>Pruebas</strong></td>
<td>2</td>
<td>Transiciones suaves demostradas</td>
</tr>
</tbody>
</table><h2 id="formato-de-entrega">Formato de Entrega</h2>
<h3 id="archivos-a-entregar">Archivos a Entregar:</h3>
<ol>
<li><strong>Código Arduino (.ino)</strong>: Versión final</li>
<li><strong>Notebook Colab (.ipynb)</strong>: Entrenamiento</li>
<li><strong>Video (3-5 min)</strong>:
<ul>
<li>Explicación del circuito</li>
<li>4 casos de prueba</li>
<li>Resultados</li>
</ul>
</li>
<li><strong>Reporte PDF (5-10 páginas)</strong>:
<ul>
<li>Introducción</li>
<li>Marco teórico</li>
<li>Desarrollo</li>
<li>Resultados</li>
<li>Conclusiones</li>
</ul>
</li>
</ol>
<hr>
<h1 id="parte-10-troubleshooting">PARTE 10: TROUBLESHOOTING</h1>
<h2 id="problemas-comunes">Problemas Comunes</h2>
<h3 id="esp32-no-se-reconoce">1. ESP32 No Se Reconoce</h3>
<p><strong>Síntomas:</strong> Puerto COM no aparece</p>
<p><strong>Soluciones:</strong></p>
<ul>
<li>Instalar driver CH340 o CP2102</li>
<li>Probar otro cable USB</li>
<li>Presionar botón BOOT al subir código</li>
<li>Verificar conexión</li>
</ul>
<h3 id="pir-siempre-detecta-movimiento">2. PIR Siempre Detecta Movimiento</h3>
<p><strong>Síntomas:</strong> pirPin siempre lee 1</p>
<p><strong>Soluciones:</strong></p>
<ul>
<li>Ajustar potenciómetro (girar izquierda)</li>
<li>Esperar 30-60 segundos calibración</li>
<li>Alejar de fuentes de calor</li>
<li>Verificar modo retriggerable</li>
</ul>
<h3 id="ldr-lee-valores-extremos">3. LDR Lee Valores Extremos</h3>
<p><strong>Síntomas:</strong> Siempre 0 o siempre 4095</p>
<p><strong>Soluciones:</strong></p>
<ul>
<li>Verificar resistencia 10kΩ</li>
<li>Comprobar conexión a 3.3V</li>
<li>Probar invertir terminales LDR</li>
<li>Código prueba: <code>Serial.println(analogRead(34));</code></li>
</ul>
<h3 id="led-rgb-colores-incorrectos">4. LED RGB Colores Incorrectos</h3>
<p><strong>Síntomas:</strong> Colores invertidos</p>
<p><strong>Soluciones:</strong></p>
<ul>
<li>Identificar ánodo/cátodo común</li>
<li>Para ánodo: <code>analogWrite(pin, 255 - valor)</code></li>
<li>Verificar resistencias 220Ω</li>
<li>Código prueba: <code>analogWrite(ledR, 255); delay(1000);</code></li>
</ul>
<h3 id="buzzer-no-suena">5. Buzzer No Suena</h3>
<p><strong>Síntomas:</strong> Sin sonido</p>
<p><strong>Soluciones:</strong></p>
<ul>
<li>Verificar polaridad (+ a GPIO12)</li>
<li>Código prueba: <code>tone(12, 1000); delay(1000);</code></li>
<li>Si es pasivo, usar PWM diferente</li>
<li>Probar con 3.3V directo</li>
</ul>
<h3 id="error-al-compilar">6. Error al Compilar</h3>
<p><strong>Soluciones:</strong></p>
<ul>
<li>“WiFi.h not found” → Seleccionar ESP32</li>
<li>“Sketch too big” → Reducir variables</li>
<li>“tone() not declared” → Usar ESP32</li>
<li>Verificar librerías instaladas</li>
</ul>
<h3 id="red-neuronal-accuracy-baja">7. Red Neuronal Accuracy Baja</h3>
<p><strong>Síntomas:</strong> &lt;50% accuracy</p>
<p><strong>Soluciones:</strong></p>
<ul>
<li>Verificar normalización</li>
<li>Aumentar épocas (50 → 100)</li>
<li>Revisar reglas de etiquetado</li>
<li>Aumentar dataset (1500 → 3000)</li>
<li>Balancear clases</li>
</ul>
<h3 id="lógica-difusa-sin-suavizar">8. Lógica Difusa Sin Suavizar</h3>
<p><strong>Síntomas:</strong> Cambios bruscos</p>
<p><strong>Soluciones:</strong></p>
<ul>
<li>Ampliar rangos de membresía</li>
<li>Aumentar solapamiento</li>
<li>Verificar operador MIN</li>
<li>Probar valores intermedios</li>
</ul>
<hr>
<h1 id="parte-11-extensiones-opcionales">PARTE 11: EXTENSIONES OPCIONALES</h1>
<h2 id="a-telegram-5-puntos-extra">A) Telegram (5 puntos extra)</h2>
<h3 id="configuración">Configuración:</h3>
<ol>
<li>Crear bot con @BotFather</li>
<li>Obtener token del bot</li>
<li>Obtener chat ID con @userinfobot</li>
</ol>
<h3 id="código">Código:</h3>
<pre class="  language-cpp"><code class="prism  language-cpp"><span class="token macro property">#<span class="token directive keyword">include</span> <span class="token string">&lt;WiFiClientSecure.h&gt;</span></span>
</code></pre><p><span class="token keyword">const</span> <span class="token keyword">char</span><span class="token operator"><em></em></span> ssid <span class="token operator">=</span> <span class="token string">“TU_WIFI”</span><span class="token punctuation">;</span><br>
<span class="token keyword">const</span> <span class="token keyword">char</span><span class="token operator"></span> password <span class="token operator">=</span> <span class="token string">“TU_PASSWORD”</span><span class="token punctuation">;</span><br>
<span class="token keyword">const</span> String telegramToken <span class="token operator">=</span> <span class="token string">“TU_BOT_TOKEN”</span><span class="token punctuation">;</span><br>
<span class="token keyword">const</span> String chatID <span class="token operator">=</span> <span class="token string">“TU_CHAT_ID”</span><span class="token punctuation">;</span></p>
<p><span class="token keyword">void</span> <span class="token function">conectarWiFi</span><span class="token punctuation">(</span><span class="token punctuation">)</span> <span class="token punctuation">{</span><br>
WiFi<span class="token punctuation">.</span><span class="token function">begin</span><span class="token punctuation">(</span>ssid<span class="token punctuation">,</span> password<span class="token punctuation">)</span><span class="token punctuation">;</span><br>
<span class="token keyword">while</span> <span class="token punctuation">(</span>WiFi<span class="token punctuation">.</span><span class="token function">status</span><span class="token punctuation">(</span><span class="token punctuation">)</span> <span class="token operator">!=</span> WL_CONNECTED<span class="token punctuation">)</span> <span class="token punctuation">{</span><br>
<span class="token function">delay</span><span class="token punctuation">(</span><span class="token number">500</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
Serial<span class="token punctuation">.</span><span class="token function">print</span><span class="token punctuation">(</span><span class="token string">"."</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
<span class="token punctuation">}</span><br>
Serial<span class="token punctuation">.</span><span class="token function">println</span><span class="token punctuation">(</span><span class="token string">"\nWiFi Conectado"</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
<span class="token punctuation">}</span></p>
<p><span class="token keyword">void</span> <span class="token function">enviarAlertaTelegram</span><span class="token punctuation">(</span><span class="token keyword">int</span> nivel<span class="token punctuation">,</span> <span class="token keyword">float</span> intensidad<span class="token punctuation">)</span> <span class="token punctuation">{</span><br>
<span class="token keyword">if</span> <span class="token punctuation">(</span>WiFi<span class="token punctuation">.</span><span class="token function">status</span><span class="token punctuation">(</span><span class="token punctuation">)</span> <span class="token operator">==</span> WL_CONNECTED <span class="token operator">&amp;&amp;</span> nivel <span class="token operator">&gt;=</span> <span class="token number">2</span><span class="token punctuation">)</span> <span class="token punctuation">{</span><br>
HTTPClient http<span class="token punctuation">;</span></p>
<pre><code>String url &lt;span class="token operator"&gt;=&lt;/span&gt; &lt;span class="token string"&gt;"https://api.telegram.org/bot"&lt;/span&gt; &lt;span class="token operator"&gt;+&lt;/span&gt; telegramToken &lt;span class="token operator"&gt;+&lt;/span&gt; &lt;span class="token string"&gt;"/sendMessage"&lt;/span&gt;&lt;span class="token punctuation"&gt;;&lt;/span&gt;
String mensaje &lt;span class="token operator"&gt;=&lt;/span&gt; &lt;span class="token string"&gt;"ALERTA SISTEMA\n\n"&lt;/span&gt;&lt;span class="token punctuation"&gt;;&lt;/span&gt;
mensaje &lt;span class="token operator"&gt;+&lt;/span&gt;&lt;span class="token operator"&gt;=&lt;/span&gt; &lt;span class="token string"&gt;"Nivel: "&lt;/span&gt; &lt;span class="token operator"&gt;+&lt;/span&gt; estados&lt;span class="token punctuation"&gt;[&lt;/span&gt;nivel&lt;span class="token punctuation"&gt;]&lt;/span&gt; &lt;span class="token operator"&gt;+&lt;/span&gt; &lt;span class="token string"&gt;"\n"&lt;/span&gt;&lt;span class="token punctuation"&gt;;&lt;/span&gt;
mensaje &lt;span class="token operator"&gt;+&lt;/span&gt;&lt;span class="token operator"&gt;=&lt;/span&gt; &lt;span class="token string"&gt;"Intensidad: "&lt;/span&gt; &lt;span class="token operator"&gt;+&lt;/span&gt; &lt;span class="token function"&gt;String&lt;/span&gt;&lt;span class="token punctuation"&gt;(&lt;/span&gt;intensidad&lt;span class="token punctuation"&gt;,&lt;/span&gt; &lt;span class="token number"&gt;1&lt;/span&gt;&lt;span class="token punctuation"&gt;)&lt;/span&gt; &lt;span class="token operator"&gt;+&lt;/span&gt; &lt;span class="token string"&gt;"%\n"&lt;/span&gt;&lt;span class="token punctuation"&gt;;&lt;/span&gt;
mensaje &lt;span class="token operator"&gt;+&lt;/span&gt;&lt;span class="token operator"&gt;=&lt;/span&gt; &lt;span class="token string"&gt;"Hora: "&lt;/span&gt; &lt;span class="token operator"&gt;+&lt;/span&gt; &lt;span class="token function"&gt;String&lt;/span&gt;&lt;span class="token punctuation"&gt;(&lt;/span&gt;horaActual&lt;span class="token punctuation"&gt;)&lt;/span&gt; &lt;span class="token operator"&gt;+&lt;/span&gt; &lt;span class="token string"&gt;":00"&lt;/span&gt;&lt;span class="token punctuation"&gt;;&lt;/span&gt;

http&lt;span class="token punctuation"&gt;.&lt;/span&gt;&lt;span class="token function"&gt;begin&lt;/span&gt;&lt;span class="token punctuation"&gt;(&lt;/span&gt;url&lt;span class="token punctuation"&gt;)&lt;/span&gt;&lt;span class="token punctuation"&gt;;&lt;/span&gt;
http&lt;span class="token punctuation"&gt;.&lt;/span&gt;&lt;span class="token function"&gt;addHeader&lt;/span&gt;&lt;span class="token punctuation"&gt;(&lt;/span&gt;&lt;span class="token string"&gt;"Content-Type"&lt;/span&gt;&lt;span class="token punctuation"&gt;,&lt;/span&gt; &lt;span class="token string"&gt;"application/json"&lt;/span&gt;&lt;span class="token punctuation"&gt;)&lt;/span&gt;&lt;span class="token punctuation"&gt;;&lt;/span&gt;

String payload &lt;span class="token operator"&gt;=&lt;/span&gt; &lt;span class="token string"&gt;"{\"chat_id\":\""&lt;/span&gt; &lt;span class="token operator"&gt;+&lt;/span&gt; chatID &lt;span class="token operator"&gt;+&lt;/span&gt; &lt;span class="token string"&gt;"\",\"text\":\""&lt;/span&gt; &lt;span class="token operator"&gt;+&lt;/span&gt; mensaje &lt;span class="token operator"&gt;+&lt;/span&gt; &lt;span class="token string"&gt;"\"}"&lt;/span&gt;&lt;span class="token punctuation"&gt;;&lt;/span&gt;

&lt;span class="token keyword"&gt;int&lt;/span&gt; httpCode &lt;span class="token operator"&gt;=&lt;/span&gt; http&lt;span class="token punctuation"&gt;.&lt;/span&gt;&lt;span class="token function"&gt;POST&lt;/span&gt;&lt;span class="token punctuation"&gt;(&lt;/span&gt;payload&lt;span class="token punctuation"&gt;)&lt;/span&gt;&lt;span class="token punctuation"&gt;;&lt;/span&gt;

&lt;span class="token keyword"&gt;if&lt;/span&gt; &lt;span class="token punctuation"&gt;(&lt;/span&gt;httpCode &lt;span class="token operator"&gt;&amp;gt;&lt;/span&gt; &lt;span class="token number"&gt;0&lt;/span&gt;&lt;span class="token punctuation"&gt;)&lt;/span&gt; &lt;span class="token punctuation"&gt;{&lt;/span&gt;
  Serial&lt;span class="token punctuation"&gt;.&lt;/span&gt;&lt;span class="token function"&gt;println&lt;/span&gt;&lt;span class="token punctuation"&gt;(&lt;/span&gt;&lt;span class="token string"&gt;"Alerta enviada a Telegram"&lt;/span&gt;&lt;span class="token punctuation"&gt;)&lt;/span&gt;&lt;span class="token punctuation"&gt;;&lt;/span&gt;
&lt;span class="token punctuation"&gt;}&lt;/span&gt;

http&lt;span class="token punctuation"&gt;.&lt;/span&gt;&lt;span class="token function"&gt;end&lt;/span&gt;&lt;span class="token punctuation"&gt;(&lt;/span&gt;&lt;span class="token punctuation"&gt;)&lt;/span&gt;&lt;span class="token punctuation"&gt;;&lt;/span&gt;
</code></pre>
<p><span class="token punctuation">}</span><br>
<span class="token punctuation">}</span></p>
<p><span class="token comment">// En setup():</span><br>
<span class="token keyword">void</span> <span class="token function">setup</span><span class="token punctuation">(</span><span class="token punctuation">)</span> <span class="token punctuation">{</span><br>
<span class="token comment">// … código anterior …</span><br>
<span class="token function">conectarWiFi</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
<span class="token punctuation">}</span></p>
<p><span class="token comment">// En loop():</span><br>
<span class="token keyword">void</span> <span class="token function">loop</span><span class="token punctuation">(</span><span class="token punctuation">)</span> <span class="token punctuation">{</span><br>
<span class="token comment">// … código anterior …</span><br>
<span class="token function">enviarAlertaTelegram</span><span class="token punctuation">(</span>nivelAlerta<span class="token punctuation">,</span> intensidadDifusa<span class="token punctuation">)</span><span class="token punctuation">;</span><br>
<span class="token punctuation">}</span><br>
</p>
<hr>
<h2 id="b-dashboard-web-5-puntos-extra">B) Dashboard Web (5 puntos extra)</h2>
<pre class="  language-cpp"><code class="prism  language-cpp"><span class="token macro property">#<span class="token directive keyword">include</span> <span class="token string">&lt;WebServer.h&gt;</span></span>
</code></pre><p>WebServer <span class="token function">server</span><span class="token punctuation">(</span><span class="token number">80</span><span class="token punctuation">)</span><span class="token punctuation">;</span></p>
<p>String <span class="token function">generarHTML</span><span class="token punctuation">(</span><span class="token punctuation">)</span> <span class="token punctuation">{</span><br>
String html <span class="token operator">=</span> <span class="token string">“&lt;!DOCTYPE html&gt;&lt;html&gt;&lt;head&gt;”</span><span class="token punctuation">;</span><br>
html <span class="token operator">+</span><span class="token operator">=</span> <span class="token string">“&lt;meta charset=‘UTF-8’&gt;”</span><span class="token punctuation">;</span><br>
html <span class="token operator">+</span><span class="token operator">=</span> <span class="token string">“&lt;title&gt;Sistema Alarma IA&lt;/title&gt;”</span><span class="token punctuation">;</span><br>
html <span class="token operator">+</span><span class="token operator">=</span> <span class="token string">“&lt;style&gt;”</span><span class="token punctuation">;</span><br>
html <span class="token operator">+</span><span class="token operator">=</span> <span class="token string">“body{font-family:Arial;background:#1a1a1a;color:white;text-align:center;padding:20px}”</span><span class="token punctuation">;</span><br>
html <span class="token operator">+</span><span class="token operator">=</span> <span class="token string">“.sensor{background:#2a2a2a;border-radius:15px;padding:20px;margin:10px;display:inline-block;min-width:200px}”</span><span class="token punctuation">;</span><br>
html <span class="token operator">+</span><span class="token operator">=</span> <span class="token string">“.valor{font-size:48px;font-weight:bold;margin:10px 0}”</span><span class="token punctuation">;</span><br>
html <span class="token operator">+</span><span class="token operator">=</span> <span class="token string">“.normal{color:#00ff00}.baja{color:#ffff00}.media{color:#ffa500}.alta{color:#ff0000}”</span><span class="token punctuation">;</span><br>
html <span class="token operator">+</span><span class="token operator">=</span> <span class="token string">“&lt;/style&gt;”</span><span class="token punctuation">;</span><br>
html <span class="token operator">+</span><span class="token operator">=</span> <span class="token string">“&lt;script&gt;setInterval(()=&gt;location.reload(),2000)&lt;/script&gt;”</span><span class="token punctuation">;</span><br>
html <span class="token operator">+</span><span class="token operator">=</span> <span class="token string">“&lt;/head&gt;&lt;body&gt;”</span><span class="token punctuation">;</span></p>
<p>html <span class="token operator">+</span><span class="token operator">=</span> <span class="token string">“&lt;h1&gt;Sistema de Alarma Inteligente&lt;/h1&gt;”</span><span class="token punctuation">;</span></p>
<p>html <span class="token operator">+</span><span class="token operator">=</span> <span class="token string">“&lt;div class=‘sensor’&gt;”</span><span class="token punctuation">;</span><br>
html <span class="token operator">+</span><span class="token operator">=</span> <span class="token string">“&lt;h3&gt;Movimiento&lt;/h3&gt;”</span><span class="token punctuation">;</span><br>
html <span class="token operator">+</span><span class="token operator">=</span> <span class="token string">“&lt;div class=‘valor’&gt;”</span> <span class="token operator">+</span> <span class="token function">String</span><span class="token punctuation">(</span>movimiento <span class="token operator">?</span> <span class="token string">“SI”</span> <span class="token operator">:</span> <span class="token string">“NO”</span><span class="token punctuation">)</span> <span class="token operator">+</span> <span class="token string">“&lt;/div&gt;”</span><span class="token punctuation">;</span><br>
html <span class="token operator">+</span><span class="token operator">=</span> <span class="token string">“&lt;/div&gt;”</span><span class="token punctuation">;</span></p>
<p>html <span class="token operator">+</span><span class="token operator">=</span> <span class="token string">“&lt;div class=‘sensor’&gt;”</span><span class="token punctuation">;</span><br>
html <span class="token operator">+</span><span class="token operator">=</span> <span class="token string">“&lt;h3&gt;Luz&lt;/h3&gt;”</span><span class="token punctuation">;</span><br>
html <span class="token operator">+</span><span class="token operator">=</span> <span class="token string">“&lt;div class=‘valor’&gt;”</span> <span class="token operator">+</span> <span class="token function">String</span><span class="token punctuation">(</span>luz<span class="token punctuation">)</span> <span class="token operator">+</span> <span class="token string">“&lt;/div&gt;”</span><span class="token punctuation">;</span><br>
html <span class="token operator">+</span><span class="token operator">=</span> <span class="token string">“&lt;/div&gt;”</span><span class="token punctuation">;</span></p>
<p>html <span class="token operator">+</span><span class="token operator">=</span> <span class="token string">“&lt;div class=‘sensor’&gt;”</span><span class="token punctuation">;</span><br>
html <span class="token operator">+</span><span class="token operator">=</span> <span class="token string">“&lt;h3&gt;Ruido&lt;/h3&gt;”</span><span class="token punctuation">;</span><br>
html <span class="token operator">+</span><span class="token operator">=</span> <span class="token string">“&lt;div class=‘valor’&gt;”</span> <span class="token operator">+</span> <span class="token function">String</span><span class="token punctuation">(</span>ruido<span class="token punctuation">)</span> <span class="token operator">+</span> <span class="token string">“&lt;/div&gt;”</span><span class="token punctuation">;</span><br>
html <span class="token operator">+</span><span class="token operator">=</span> <span class="token string">“&lt;/div&gt;”</span><span class="token punctuation">;</span></p>
<p>String claseCSS <span class="token operator">=</span> <span class="token string">“”</span><span class="token punctuation">;</span><br>
<span class="token keyword">if</span> <span class="token punctuation">(</span>nivelAlerta <span class="token operator"><mark></mark></span> <span class="token number">0</span><span class="token punctuation">)</span> claseCSS <span class="token operator">=</span> <span class="token string">“normal”</span><span class="token punctuation">;</span><br>
<span class="token keyword">else</span> <span class="token keyword">if</span> <span class="token punctuation">(</span>nivelAlerta <span class="token operator"></span> <span class="token number">1</span><span class="token punctuation">)</span> claseCSS <span class="token operator">=</span> <span class="token string">“baja”</span><span class="token punctuation">;</span><br>
<span class="token keyword">else</span> <span class="token keyword">if</span> <span class="token punctuation">(</span>nivelAlerta <span class="token operator">==</span> <span class="token number">2</span><span class="token punctuation">)</span> claseCSS <span class="token operator">=</span> <span class="token string">“media”</span><span class="token punctuation">;</span><br>
<span class="token keyword">else</span> claseCSS <span class="token operator">=</span> <span class="token string">“alta”</span><span class="token punctuation">;</span></p>
<p>html <span class="token operator">+</span><span class="token operator">=</span> <span class="token string">“&lt;div class=‘sensor’ style=‘width:80%;max-width:500px’&gt;”</span><span class="token punctuation">;</span><br>
html <span class="token operator">+</span><span class="token operator">=</span> <span class="token string">“&lt;h2&gt;Estado Actual&lt;/h2&gt;”</span><span class="token punctuation">;</span><br>
html <span class="token operator">+</span><span class="token operator">=</span> <span class="token string">“&lt;div class=‘valor "</span> <span class="token operator">+</span> claseCSS <span class="token operator">+</span> <span class="token string">"’&gt;”</span> <span class="token operator">+</span> estados<span class="token punctuation">[</span>nivelAlerta<span class="token punctuation">]</span> <span class="token operator">+</span> <span class="token string">“&lt;/div&gt;”</span><span class="token punctuation">;</span><br>
html <span class="token operator">+</span><span class="token operator">=</span> <span class="token string">"&lt;p&gt;Intensidad: "</span> <span class="token operator">+</span> <span class="token function">String</span><span class="token punctuation">(</span>intensidadDifusa<span class="token punctuation">,</span> <span class="token number">1</span><span class="token punctuation">)</span> <span class="token operator">+</span> <span class="token string">“%&lt;/p&gt;”</span><span class="token punctuation">;</span><br>
html <span class="token operator">+</span><span class="token operator">=</span> <span class="token string">“&lt;/div&gt;”</span><span class="token punctuation">;</span></p>
<p>html <span class="token operator">+</span><span class="token operator">=</span> <span class="token string">“&lt;/body&gt;&lt;/html&gt;”</span><span class="token punctuation">;</span><br>
<span class="token keyword">return</span> html<span class="token punctuation">;</span><br>
<span class="token punctuation">}</span></p>
<p><span class="token keyword">void</span> <span class="token function">setup</span><span class="token punctuation">(</span><span class="token punctuation">)</span> <span class="token punctuation">{</span><br>
<span class="token comment">// … código anterior …</span></p>
<p>server<span class="token punctuation">.</span><span class="token function">on</span><span class="token punctuation">(</span><span class="token string">"/"</span><span class="token punctuation">,</span> <span class="token punctuation">[</span><span class="token punctuation">]</span><span class="token punctuation">(</span><span class="token punctuation">)</span> <span class="token punctuation">{</span><br>
server<span class="token punctuation">.</span><span class="token function">send</span><span class="token punctuation">(</span><span class="token number">200</span><span class="token punctuation">,</span> <span class="token string">“text/html”</span><span class="token punctuation">,</span> <span class="token function">generarHTML</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
<span class="token punctuation">}</span><span class="token punctuation">)</span><span class="token punctuation">;</span></p>
<p>server<span class="token punctuation">.</span><span class="token function">begin</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
Serial<span class="token punctuation">.</span><span class="token function">println</span><span class="token punctuation">(</span><span class="token string">“Servidor web iniciado”</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
Serial<span class="token punctuation">.</span><span class="token function">print</span><span class="token punctuation">(</span><span class="token string">“URL: http://”</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
Serial<span class="token punctuation">.</span><span class="token function">println</span><span class="token punctuation">(</span>WiFi<span class="token punctuation">.</span><span class="token function">localIP</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
<span class="token punctuation">}</span></p>
<p><span class="token keyword">void</span> <span class="token function">loop</span><span class="token punctuation">(</span><span class="token punctuation">)</span> <span class="token punctuation">{</span><br>
server<span class="token punctuation">.</span><span class="token function">handleClient</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
<span class="token comment">// … resto del código …</span><br>
<span class="token punctuation">}</span><br>
</p>
<p><strong>Accede desde navegador:</strong> <code>http://[IP_ESP32]</code></p>
<hr>
<h2 id="c-almacenamiento-en-sd-3-puntos-extra">C) Almacenamiento en SD (3 puntos extra)</h2>
<pre class="  language-cpp"><code class="prism  language-cpp"><span class="token macro property">#<span class="token directive keyword">include</span> <span class="token string">&lt;SD.h&gt;</span></span>
<span class="token macro property">#<span class="token directive keyword">include</span> <span class="token string">&lt;SPI.h&gt;</span></span>
</code></pre><p><span class="token macro property">#<span class="token directive keyword">define</span> SD_CS 5</span></p>
<p><span class="token keyword">void</span> <span class="token function">setup</span><span class="token punctuation">(</span><span class="token punctuation">)</span> <span class="token punctuation">{</span><br>
<span class="token comment">// … código anterior …</span></p>
<p><span class="token keyword">if</span> <span class="token punctuation">(</span><span class="token operator">!</span>SD<span class="token punctuation">.</span><span class="token function">begin</span><span class="token punctuation">(</span>SD_CS<span class="token punctuation">)</span><span class="token punctuation">)</span> <span class="token punctuation">{</span><br>
Serial<span class="token punctuation">.</span><span class="token function">println</span><span class="token punctuation">(</span><span class="token string">“Error SD”</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
<span class="token punctuation">}</span> <span class="token keyword">else</span> <span class="token punctuation">{</span><br>
Serial<span class="token punctuation">.</span><span class="token function">println</span><span class="token punctuation">(</span><span class="token string">“SD lista”</span><span class="token punctuation">)</span><span class="token punctuation">;</span><br>
<span class="token punctuation">}</span><br>
<span class="token punctuation">}</span></p>
<p><span class="token keyword">void</span> <span class="token function">guardarEvento</span><span class="token punctuation">(</span><span class="token keyword">int</span> nivel<span class="token punctuation">,</span> <span class="token keyword">float</span> intensidad<span class="token punctuation">)</span> <span class="token punctuation">{</span><br>
File archivo <span class="token operator">=</span> SD<span class="token punctuation">.</span><span class="token function">open</span><span class="token punctuation">(</span><span class="token string">"/eventos.csv"</span><span class="token punctuation">,</span> FILE_APPEND<span class="token punctuation">)</span><span class="token punctuation">;</span></p>
<p><span class="token keyword">if</span> <span class="token punctuation">(</span>archivo<span class="token punctuation">)</span> <span class="token punctuation">{</span><br>
String linea <span class="token operator">=</span> <span class="token function">String</span><span class="token punctuation">(</span><span class="token function">millis</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">)</span> <span class="token operator">+</span> <span class="token string">“,”</span><span class="token punctuation">;</span><br>
linea <span class="token operator">+</span><span class="token operator">=</span> <span class="token function">String</span><span class="token punctuation">(</span>nivel<span class="token punctuation">)</span> <span class="token operator">+</span> <span class="token string">“,”</span><span class="token punctuation">;</span><br>
linea <span class="token operator">+</span><span class="token operator">=</span> <span class="token function">String</span><span class="token punctuation">(</span>intensidad<span class="token punctuation">,</span> <span class="token number">2</span><span class="token punctuation">)</span> <span class="token operator">+</span> <span class="token string">“,”</span><span class="token punctuation">;</span><br>
linea <span class="token operator">+</span><span class="token operator">=</span> <span class="token function">String</span><span class="token punctuation">(</span>movimiento<span class="token punctuation">)</span> <span class="token operator">+</span> <span class="token string">“,”</span><span class="token punctuation">;</span><br>
linea <span class="token operator">+</span><span class="token operator">=</span> <span class="token function">String</span><span class="token punctuation">(</span>luz<span class="token punctuation">)</span> <span class="token operator">+</span> <span class="token string">“,”</span><span class="token punctuation">;</span><br>
linea <span class="token operator">+</span><span class="token operator">=</span> <span class="token function">String</span><span class="token punctuation">(</span>ruido<span class="token punctuation">)</span><span class="token punctuation">;</span></p>
<pre><code>archivo&lt;span class="token punctuation"&gt;.&lt;/span&gt;&lt;span class="token function"&gt;println&lt;/span&gt;&lt;span class="token punctuation"&gt;(&lt;/span&gt;linea&lt;span class="token punctuation"&gt;)&lt;/span&gt;&lt;span class="token punctuation"&gt;;&lt;/span&gt;
archivo&lt;span class="token punctuation"&gt;.&lt;/span&gt;&lt;span class="token function"&gt;close&lt;/span&gt;&lt;span class="token punctuation"&gt;(&lt;/span&gt;&lt;span class="token punctuation"&gt;)&lt;/span&gt;&lt;span class="token punctuation"&gt;;&lt;/span&gt;

Serial&lt;span class="token punctuation"&gt;.&lt;/span&gt;&lt;span class="token function"&gt;println&lt;/span&gt;&lt;span class="token punctuation"&gt;(&lt;/span&gt;&lt;span class="token string"&gt;"Evento guardado"&lt;/span&gt;&lt;span class="token punctuation"&gt;)&lt;/span&gt;&lt;span class="token punctuation"&gt;;&lt;/span&gt;
</code></pre>
<p><span class="token punctuation">}</span><br>
<span class="token punctuation">}</span></p>
<p><span class="token keyword">void</span> <span class="token function">loop</span><span class="token punctuation">(</span><span class="token punctuation">)</span> <span class="token punctuation">{</span><br>
<span class="token comment">// … código anterior …</span></p>
<p><span class="token keyword">if</span> <span class="token punctuation">(</span>nivelAlerta <span class="token operator">&gt;</span> <span class="token number">0</span><span class="token punctuation">)</span> <span class="token punctuation">{</span><br>
<span class="token function">guardarEvento</span><span class="token punctuation">(</span>nivelAlerta<span class="token punctuation">,</span> intensidadDifusa<span class="token punctuation">)</span><span class="token punctuation">;</span><br>
<span class="token punctuation">}</span><br>
<span class="token punctuation">}</span><br>
</p>
<hr>
<h1 id="parte-12-referencias">PARTE 12: REFERENCIAS</h1>
<h2 id="documentación-oficial">Documentación Oficial</h2>
<ul>
<li><strong>ESP32</strong>: <a href="https://docs.espressif.com/projects/esp-idf/">https://docs.espressif.com/projects/esp-idf/</a></li>
<li><strong>TensorFlow</strong>: <a href="https://www.tensorflow.org/tutorials">https://www.tensorflow.org/tutorials</a></li>
<li><strong>Arduino</strong>: <a href="https://www.arduino.cc/reference/">https://www.arduino.cc/reference/</a></li>
</ul>
<h2 id="tutoriales">Tutoriales</h2>
<ul>
<li>“Neural Networks and Deep Learning” - Michael Nielsen</li>
<li>“Introduction to Fuzzy Logic” - Sivanandam</li>
<li>Random Nerd Tutorials - ESP32</li>
</ul>
<h2 id="videos-youtube">Videos YouTube</h2>
<ul>
<li>“¿Qué es una Red Neuronal?” - Dot CSV</li>
<li>“Lógica Difusa Explicada” - Electrónica Fácil</li>
<li>“ESP32 para Principiantes” - The STEM Teacher</li>
</ul>
<h2 id="herramientas">Herramientas</h2>
<ul>
<li>Google Colab: <a href="https://colab.research.google.com">https://colab.research.google.com</a></li>
<li>Tinkercad: <a href="https://www.tinkercad.com">https://www.tinkercad.com</a></li>
<li>Wokwi: <a href="https://wokwi.com">https://wokwi.com</a></li>
</ul>
<hr>
<h1 id="checklist-final">CHECKLIST FINAL</h1>
<h2 id="hardware">Hardware</h2>
<ul>
<li class="task-list-item"> Conexiones firmes sin cortocircuitos</li>
<li class="task-list-item"> Sensores responden correctamente</li>
<li class="task-list-item"> Buzzer suena con tonos diferentes</li>
<li class="task-list-item"> LED RGB muestra todos los colores</li>
<li class="task-list-item"> ESP32 programa sin errores</li>
</ul>
<h2 id="software---red-neuronal">Software - Red Neuronal</h2>
<ul>
<li class="task-list-item"> Dataset con 1500+ muestras</li>
<li class="task-list-item"> Datos normalizados</li>
<li class="task-list-item"> Red converge (accuracy &gt;80%)</li>
<li class="task-list-item"> Gráficas guardadas</li>
<li class="task-list-item"> Matriz confusión interpretada</li>
<li class="task-list-item"> Modelo guardado (.h5)</li>
</ul>
<h2 id="software---lógica-difusa">Software - Lógica Difusa</h2>
<ul>
<li class="task-list-item"> 6+ funciones membresía</li>
<li class="task-list-item"> 3+ reglas difusas</li>
<li class="task-list-item"> Defuzzificación correcta</li>
<li class="task-list-item"> Transiciones suaves</li>
<li class="task-list-item"> Código comentado</li>
</ul>
<h2 id="integración">Integración</h2>
<ul>
<li class="task-list-item"> Red clasifica nivel alerta</li>
<li class="task-list-item"> Lógica controla intensidad</li>
<li class="task-list-item"> Ambas técnicas juntas</li>
<li class="task-list-item"> Respuesta tiempo real (&lt;1s)</li>
</ul>
<h2 id="documentación">Documentación</h2>
<ul>
<li class="task-list-item"> Código .ino completo</li>
<li class="task-list-item"> Notebook .ipynb</li>
<li class="task-list-item"> Video 3-5 min</li>
<li class="task-list-item"> Reporte PDF</li>
<li class="task-list-item"> Diagramas incluidos</li>
</ul>
<h2 id="pruebas">Pruebas</h2>
<ul>
<li class="task-list-item"> Sin movimiento → NORMAL</li>
<li class="task-list-item"> Movimiento día → ALERTA BAJA</li>
<li class="task-list-item"> Movimiento + ruido → ALERTA MEDIA</li>
<li class="task-list-item"> Movimiento noche → ALERTA ALTA</li>
</ul>
<hr>
<h1 id="conclusión">CONCLUSIÓN</h1>
<h2 id="este-proyecto-cumple">Este proyecto cumple:</h2>
<p><strong>Actividad 8 (Red Neuronal):</strong></p>
<ul>
<li>Clasificación inteligente con red de 4 capas</li>
<li>Entrenamiento con 1500 ejemplos</li>
<li>Accuracy &gt;85%</li>
<li>Implementación en ESP32</li>
</ul>
<p><strong>Actividad 9 (Lógica Difusa):</strong></p>
<ul>
<li>Sistema difuso con 6 funciones membresía</li>
<li>4 reglas difusas</li>
<li>Defuzzificación por centro de gravedad</li>
</ul>
<p><strong>Requisito Robótica:</strong></p>
<ul>
<li>Percepción: PIR, LDR, Micrófono</li>
<li>Procesamiento: IA en tiempo real</li>
<li>Actuación: Buzzer, LED, notificaciones</li>
</ul>
<h2 id="aplicación-real">Aplicación Real</h2>
<ul>
<li>Hogares (seguridad residencial)</li>
<li>Oficinas (monitoreo acceso)</li>
<li>Almacenes (detección intrusos)</li>
<li>Laboratorios (control áreas sensibles)</li>
</ul>
<h2 id="aprendizajes-clave">Aprendizajes Clave</h2>
<ol>
<li>Diseño redes neuronales</li>
<li>Sistemas lógica difusa</li>
<li>Integración IA en embebidos</li>
<li>Robótica autónoma sensores múltiples</li>
</ol>
<hr>
<p><strong>¡Éxito en tu proyecto!</strong></p>
<p>Si tienes dudas, revisa Troubleshooting o consulta instructor.</p>



</div>
</body>

</html>
