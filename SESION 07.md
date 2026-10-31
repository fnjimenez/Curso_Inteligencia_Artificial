<h1 id="inteligencia-artificial-—-contenido-temático-de-la-sesión-7-semana-7">Inteligencia Artificial — Contenido temático de la Sesión 7 (Semana 7)</h1>
<p><strong>Tema (programa oficial AU-20-101 V0.2, Unidad IV):</strong> 4.1 Reglas de producción (4.1.1 Sintaxis de las reglas de producción); 4.2 Semántica de las reglas de producción (4.2.1 Conocimiento causal; 4.2.2 Conocimiento de diagnóstico) <strong>Fecha:</strong> 20 al 27 de octubre de 2026 (sesión presencial: sábado 24 de octubre) <strong>Actividad de la semana:</strong> ninguna con ponderación. La Actividad 6 venció el 20 de octubre; la <strong>Actividad 7</strong> (sistema de producción en Prolog, 10 pts) abrió el 27 de octubre y cierra el <strong>4 de noviembre a las 06:59</strong>; para no arriesgar, entrégala a más tardar la <strong>noche del 3 de noviembre</strong>. <strong>Propósito de la sesión:</strong> Que el estudiante aprenda a escribir <strong>reglas de producción</strong> correctas y a distinguir el conocimiento <strong>causal</strong> del de <strong>diagnóstico</strong>, como base para diseñar en la Semana 8 la arquitectura de un sistema de producción.</p>
<blockquote>
<p>Nota de uso: los ejemplos son de <strong>logística y operaciones de almacén</strong> para las tres carreras del grupo (Logística, Ingeniería Industrial y TI).</p>
</blockquote>
<hr>
<h2 id="índice">Índice</h2>
<ul>
<li>
<ol start="0">
<li>Definiciones conceptuales clave</li>
</ol>
</li>
<li>
<ol>
<li>Qué es un sistema basado en reglas</li>
</ol>
</li>
<li>
<ol start="2">
<li>4.1 Sintaxis de las reglas de producción</li>
</ol>
</li>
<li>
<ol start="3">
<li>4.2 Semántica: qué significa una regla</li>
</ol>
</li>
<li>
<ol start="4">
<li>Conocimiento causal y conocimiento de diagnóstico</li>
</ol>
</li>
<li>
<ol start="5">
<li>Cómo escribir buenas reglas</li>
</ol>
</li>
<li>
<ol start="6">
<li>Ejemplo ilustrativo: priorización de pedidos</li>
</ol>
</li>
<li>
<ol start="7">
<li>Errores comunes a evitar</li>
</ol>
</li>
<li>
<ol start="8">
<li>Ejercicio integrador de la sesión</li>
</ol>
</li>
<li>
<ol start="9">
<li>Adelanto: reglas de clasificación (el caso de los peces)</li>
</ol>
</li>
<li>
<ol start="10">
<li>Cierre de la sesión</li>
</ol>
</li>
<li>
<ol start="11">
<li>Referencias (formato APA)</li>
</ol>
</li>
</ul>
<hr>
<h2 id="definiciones-conceptuales-clave">0. Definiciones conceptuales clave</h2>
<ul>
<li><strong>Regla de producción:</strong> enunciado de la forma <strong>SI</strong> (condiciones) <strong>ENTONCES</strong> (acción o conclusión).</li>
<li><strong>Antecedente (premisa):</strong> la parte SI; las condiciones.</li>
<li><strong>Consecuente (conclusión):</strong> la parte ENTONCES; lo que se concluye o se hace.</li>
<li><strong>Hecho:</strong> dato verdadero en un momento dado (por ejemplo, «el pedido P-102 es urgente»).</li>
<li><strong>Conocimiento causal:</strong> va de la causa al efecto.</li>
<li><strong>Conocimiento de diagnóstico:</strong> va del síntoma a la causa probable.</li>
</ul>
<hr>
<h2 id="qué-es-un-sistema-basado-en-reglas">1. Qué es un sistema basado en reglas</h2>
<p>Es un programa que <strong>toma decisiones aplicando reglas</strong> a un conjunto de hechos, sin que la lógica de decisión esté escondida en el código. Ventajas:</p>
<ul>
<li>El conocimiento se <strong>lee y se modifica</strong> sin reprogramar todo.</li>
<li>Las decisiones son <strong>explicables</strong> (se puede mostrar qué reglas se usaron).</li>
<li>Se parece a cómo un especialista explica su criterio.</li>
</ul>
<hr>
<h2 id="sintaxis-de-las-reglas-de-producción">2. 4.1 Sintaxis de las reglas de producción</h2>
<p><strong>Forma general:</strong></p>
<p>SI condición₁ Y condición₂ [O condición₃] ENTONCES conclusión o acción</p>
<p><strong>Elementos:</strong></p>
<ul>
<li><strong>Condiciones:</strong> comparaciones sobre hechos (<code>prioridad = alta</code>, <code>stock &lt; 10</code>).</li>
<li><strong>Conectores:</strong> Y, O, NO.</li>
<li><strong>Acción o conclusión:</strong> asignar un valor, activar una alerta, ejecutar un paso.</li>
</ul>
<p><strong>Ejemplos válidos:</strong></p>
<ul>
<li>R1: SI <code>tipo_cliente = premium</code> Y <code>entrega &lt; 24 h</code> ENTONCES <code>prioridad = alta</code>.</li>
<li>R2: SI <code>stock &lt; punto_reorden</code> ENTONCES <code>generar_orden_de_compra</code>.</li>
<li>R3: SI <code>prioridad = alta</code> Y <code>transportista_disponible = sí</code> ENTONCES <code>asignar_ruta_express</code>.</li>
</ul>
<p><strong>Convenciones útiles:</strong> da a cada regla un identificador (R1, R2…), usa nombres claros para las variables y escribe una condición por línea.</p>
<hr>
<h2 id="semántica-qué-significa-una-regla">3. 4.2 Semántica: qué significa una regla</h2>
<p>La <strong>semántica</strong> es el significado de la regla: qué relación del mundo representa.</p>
<ul>
<li>Una regla puede expresar una <strong>ley</strong> (si se calienta el aire, aumenta la presión), una <strong>política</strong> (si el pedido supera cierto monto, requiere autorización) o una <strong>experiencia</strong> (si el motor hace ese ruido, probablemente falla el rodamiento).</li>
<li>Debe poder responder a: <strong>¿por qué es verdadera?</strong> y <strong>¿cuándo aplica?</strong></li>
<li>Dos reglas pueden <strong>chocar</strong> (conflicto) si sus condiciones se cumplen a la vez y sus conclusiones se contradicen. Hay que detectarlo desde el diseño.</li>
</ul>
<hr>
<h2 id="conocimiento-causal-y-conocimiento-de-diagnóstico">4. Conocimiento causal y conocimiento de diagnóstico</h2>
<h3 id="causal-4.2.1">4.1 Causal (4.2.1)</h3>
<p>Describe <strong>qué efecto produce una causa</strong>.</p>
<ul>
<li>SI <code>se_rompe_la_cadena_de_frío</code> ENTONCES <code>aumenta_el_deterioro_del_producto</code>.</li>
<li>SI <code>faltan_montacargas</code> ENTONCES <code>aumenta_el_tiempo_de_carga</code>.</li>
</ul>
<h3 id="de-diagnóstico-4.2.2">4.2 De diagnóstico (4.2.2)</h3>
<p>Describe <strong>qué causa explica un síntoma observado</strong>.</p>
<ul>
<li>SI <code>aumentan_los_errores_de_picking</code> Y <code>hubo_cambio_de_layout</code> ENTONCES <code>causa_probable = ubicaciones_mal_etiquetadas</code>.</li>
<li>SI <code>el_motor_se_sobrecalienta</code> Y <code>el_nivel_de_aceite_es_bajo</code> ENTONCES <code>causa_probable = falta_de_lubricación</code>.</li>
</ul>
<h3 id="cómo-se-distinguen">4.3 Cómo se distinguen</h3>
<ul>
<li><strong>Causal:</strong> razona hacia adelante (de causas a consecuencias) y sirve para <strong>predecir y planear</strong>.</li>
<li><strong>Diagnóstico:</strong> razona hacia atrás (de síntomas a causas) y sirve para <strong>identificar fallas</strong>.</li>
</ul>
<p>Un buen sistema combina ambos.</p>
<hr>
<h2 id="cómo-escribir-buenas-reglas">5. Cómo escribir buenas reglas</h2>
<ol>
<li><strong>Una idea por regla.</strong> Si necesitas «y además», separa en dos.</li>
<li><strong>Condiciones medibles.</strong> Evita «es grande»; usa «volumen &gt; 2 m³».</li>
<li><strong>Sin ambigüedad.</strong> Cada término tiene un solo significado.</li>
<li><strong>Cobertura.</strong> Revisa que todas las situaciones importantes tengan regla.</li>
<li><strong>Sin contradicciones ni duplicados.</strong></li>
<li><strong>Fuente de la regla.</strong> Anota de dónde sale (norma, política, experto).</li>
<li><strong>Prueba con casos.</strong> Aplica las reglas a ejemplos y verifica el resultado.</li>
</ol>
<hr>
<h2 id="ejemplo-ilustrativo-priorización-de-pedidos">6. Ejemplo ilustrativo: priorización de pedidos</h2>
<p><strong>Hechos de un pedido:</strong> tipo de cliente, tiempo de entrega pedido, valor, stock disponible.</p>
<ul>
<li>R1: SI <code>tipo_cliente = premium</code> ENTONCES <code>prioridad = alta</code>.</li>
<li>R2: SI <code>entrega &lt; 24 h</code> ENTONCES <code>prioridad = alta</code>.</li>
<li>R3: SI <code>prioridad = alta</code> Y <code>stock &gt;= cantidad</code> ENTONCES <code>surtir_primero</code>.</li>
<li>R4: SI <code>prioridad = alta</code> Y <code>stock &lt; cantidad</code> ENTONCES <code>escalar_a_compras</code>.</li>
<li>R5: SI <code>prioridad ≠ alta</code> Y <code>stock &gt;= cantidad</code> ENTONCES <code>surtir_en_turno</code>.</li>
</ul>
<p><strong>Caso de prueba:</strong> pedido de cliente premium, entrega en 48 h, stock suficiente → R1 da prioridad alta → R3 concluye «surtir primero».</p>
<hr>
<h2 id="errores-comunes-a-evitar">7. Errores comunes a evitar</h2>
<ul>
<li>Reglas con condiciones vagas («si hay muchos pedidos»).</li>
<li>Mezclar causa y diagnóstico en la misma regla.</li>
<li>Reglas que se contradicen sin criterio de desempate.</li>
<li>Conclusiones que dependen de hechos que el sistema nunca conoce.</li>
<li>Reglas sin identificador ni fuente.</li>
</ul>
<hr>
<h2 id="ejercicio-integrador-de-la-sesión">8. Ejercicio integrador de la sesión</h2>
<p><strong>En equipos de 3, 45 minutos:</strong></p>
<ol>
<li>Elijan un dominio de su carrera (almacén, mantenimiento, calidad, soporte técnico).</li>
<li>Escriban <strong>10 reglas</strong>: 5 causales y 5 de diagnóstico.</li>
<li>Identifiquen los <strong>hechos</strong> que necesita el sistema.</li>
<li>Busquen al menos un <strong>conflicto</strong> entre reglas y propongan cómo resolverlo.</li>
<li>Prueben las reglas con dos casos de ejemplo.</li>
</ol>
<p><strong>Plenaria:</strong> dos equipos presentan sus reglas y el grupo detecta contradicciones.</p>
<hr>
<h2 id="adelanto-reglas-de-clasificación-el-caso-de-los-peces">9. Adelanto: reglas de clasificación (el caso de los peces)</h2>
<p>No todas las reglas toman decisiones; muchas <strong>clasifican</strong>. La Actividad 7 usa un sistema de este tipo: a partir de las características observadas de un pez (boca, dientes, color, región…) se deduce su <strong>orden</strong>, su <strong>familia</strong> y su <strong>raza</strong>.</p>
<p>Ejemplo con dos reglas en español:</p>
<ul>
<li>R1: SI la boca es pequeña Y los dientes son puntiagudos Y vive en ríos del mundo ENTONCES el orden es <em>Ciprinodontiformes</em>.</li>
<li>R3: SI el orden es <em>Ciprinodontiformes</em> Y es de América del Sur Y es hembra ENTONCES la familia es <em>Poecílidos</em>.</li>
</ul>
<p>Observa que la conclusión de R1 <strong>se vuelve condición</strong> de R3: así se forman <strong>cadenas de inferencia</strong>. Es el mismo principio de la sección 6 (pedidos), aplicado a otro dominio. En la Semana 8 se explica completo el caso y su traducción a <strong>Prolog</strong>.</p>
<blockquote>
<p>Para ir adelantando: lee el recurso de la plataforma (<strong>Capítulo 4, página 190</strong>) e identifica qué datos son <strong>hechos</strong> y cuáles son <strong>conclusiones</strong>.</p>
</blockquote>
<hr>
<h2 id="cierre-de-la-sesión">10. Cierre de la sesión</h2>
<p><strong>Sin entrega en plataforma esta semana.</strong> Para la Semana 8, lleva:</p>
<ul>
<li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled=""> Tu conjunto de 10 reglas con identificador.</li>
<li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled=""> La lista de hechos que necesita tu sistema.</li>
<li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled=""> Lectura del Capítulo 4, página 190 (recurso de la Actividad 7) y tus dudas sobre las reglas R1 a R10.</li>
<li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled=""> SWI-Prolog instalado en tu equipo (<a href="https://www.swi-prolog.org/">https://www.swi-prolog.org/</a>) o acceso a SWISH en línea.</li>
</ul>
<hr>
<h2 id="referencias-formato-apa">11. Referencias (formato APA)</h2>
<p>Berzal, F. (2019). <em>Redes neuronales &amp; deep learning: Volumen 1. Entrenamiento de redes neuronales artificiales</em> (1.ª ed.). Independently published.</p>
<p>Jackson, P. (1998). <em>Introduction to expert systems</em> (3rd ed.). Addison-Wesley.</p>
<p>Russell, S., &amp; Norvig, P. (2021). <em>Artificial intelligence: A modern approach</em> (4th ed.). Pearson.</p>

