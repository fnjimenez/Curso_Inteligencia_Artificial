<h1 id="inteligencia-artificial-—-contenido-temático-de-la-sesión-8-semana-8">Inteligencia Artificial — Contenido temático de la Sesión 8 (Semana 8)</h1>
<p><strong>Tema (programa oficial AU-20-101 V0.2, Unidad IV):</strong> 4.3 Arquitectura de un sistema de producción (4.3.1 Hechos; 4.3.2 Base de conocimientos; 4.3.3 Mecanismos de control); 4.4 Ciclo de vida de un sistema de producción <strong>Fecha:</strong> 27 de octubre al 3 de noviembre de 2026 (sesión presencial: sábado 31 de octubre) <strong>Actividad 7 oficial:</strong> Diseño de la arquitectura de producción: modelar en <strong>Prolog</strong> el sistema de reglas R1 a R10 del recurso de plataforma (Capítulo 4, p. 190) y entregar el archivo de Prolog más un <strong>reporte de la experiencia</strong> (<strong>10 puntos</strong>). Abrió el 27 de octubre (00:07) y cierra el <strong>miércoles 4 de noviembre a las 06:59</strong>; entrega recomendada: a más tardar la noche del <strong>3 de noviembre</strong>. <strong>Propósito de la sesión:</strong> Que el estudiante comprenda los componentes de un sistema de producción, el funcionamiento de su motor de inferencia y su ciclo de vida, y <strong>diseñe en el aula la arquitectura</strong> de un sistema para un problema propio, que entregará en la Actividad 7.</p>
<blockquote>
<p><strong>Esta sesión continúa la Sesión 7:</strong> allí se escribieron las reglas; hoy se organiza el sistema completo que las usa y se <strong>explica paso a paso el caso de la Actividad 7</strong> (clasificación de peces en Prolog).</p>
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
<li>Arquitectura de un sistema de producción</li>
</ol>
</li>
<li>
<ol start="2">
<li>4.3.1 Hechos (memoria de trabajo)</li>
</ol>
</li>
<li>
<ol start="3">
<li>4.3.2 Base de conocimientos</li>
</ol>
</li>
<li>
<ol start="4">
<li>4.3.3 Mecanismos de control (motor de inferencia)</li>
</ol>
</li>
<li>
<ol start="5">
<li>4.4 Ciclo de vida de un sistema de producción</li>
</ol>
</li>
<li>
<ol start="6">
<li>Ejemplo ilustrativo: sistema de asignación de muelles</li>
</ol>
</li>
<li>
<ol start="7">
<li>Caso de la Actividad 7: clasificación de peces en Prolog</li>
</ol>
</li>
<li>
<ol start="8">
<li>Errores comunes a evitar</li>
</ol>
</li>
<li>
<ol start="9">
<li>Ejercicio integrador: diseño de la arquitectura</li>
</ol>
</li>
<li>
<ol start="10">
<li>Cierre de la sesión y checklist de la Actividad 7</li>
</ol>
</li>
<li>
<ol start="11">
<li>Anexo: versión práctica, operador logístico en El Buen Fin (Python)</li>
</ol>
</li>
<li>
<ol start="12">
<li>Referencias (formato APA)</li>
</ol>
</li>
</ul>
<hr>
<h2 id="definiciones-conceptuales-clave">0. Definiciones conceptuales clave</h2>
<ul>
<li><strong>Sistema de producción:</strong> sistema que resuelve problemas aplicando reglas de producción a hechos.</li>
<li><strong>Memoria de trabajo:</strong> conjunto de hechos actuales; cambia mientras el sistema razona.</li>
<li><strong>Base de conocimientos:</strong> conjunto de reglas del dominio.</li>
<li><strong>Motor de inferencia:</strong> mecanismo que decide qué regla aplicar y en qué orden.</li>
<li><strong>Resolución de conflictos:</strong> criterio para elegir una regla cuando varias se pueden activar a la vez.</li>
</ul>
<hr>
<h2 id="arquitectura-de-un-sistema-de-producción">1. Arquitectura de un sistema de producción</h2>
<p>Un sistema de producción tiene tres componentes principales:</p>
<ol>
<li><strong>Hechos</strong> (memoria de trabajo): lo que se sabe del caso actual.</li>
<li><strong>Base de conocimientos:</strong> las reglas SI–ENTONCES.</li>
<li><strong>Mecanismo de control</strong> (motor de inferencia): el que compara, elige y ejecuta reglas.</li>
</ol>
<p><strong>Flujo:</strong> el usuario o los sensores aportan hechos → el motor busca reglas cuyas condiciones se cumplen → elige una → la ejecuta → los hechos se actualizan → el ciclo se repite hasta llegar a una conclusión o hasta que no haya más reglas aplicables.</p>
<p>Opcionalmente se agregan una <strong>interfaz de usuario</strong> y un <strong>módulo de explicación</strong> (qué reglas se usaron).</p>
<hr>
<h2 id="hechos-memoria-de-trabajo">2. 4.3.1 Hechos (memoria de trabajo)</h2>
<ul>
<li>Pueden ser <strong>iniciales</strong> (datos de entrada) o <strong>derivados</strong> (concluidos por reglas).</li>
<li>Se representan como pares atributo–valor: <code>prioridad = alta</code>, <code>muelle_libre = M3</code>.</li>
<li>Deben tener <strong>nombre, tipo y valores permitidos</strong> definidos.</li>
<li>Cambian durante la ejecución; la base de conocimientos no.</li>
</ul>
<hr>
<h2 id="base-de-conocimientos">3. 4.3.2 Base de conocimientos</h2>
<ul>
<li>Reúne todas las reglas, con identificador y fuente.</li>
<li>Puede organizarse en <strong>módulos</strong> por tema (reglas de prioridad, de asignación, de alertas).</li>
<li>Se mantiene separada del motor: se pueden <strong>agregar o corregir reglas sin cambiar el motor</strong>.</li>
<li>Debe revisarse para evitar duplicados, contradicciones y huecos.</li>
</ul>
<hr>
<h2 id="mecanismos-de-control-motor-de-inferencia">4. 4.3.3 Mecanismos de control (motor de inferencia)</h2>
<h3 id="encadenamiento-hacia-adelante">4.1 Encadenamiento hacia adelante</h3>
<p>Parte de los <strong>hechos</strong> y aplica reglas hasta llegar a conclusiones. Es útil cuando se conocen los datos y se quiere saber qué ocurre o qué hacer (planeación, monitoreo).</p>
<h3 id="encadenamiento-hacia-atrás">4.2 Encadenamiento hacia atrás</h3>
<p>Parte de una <strong>hipótesis o meta</strong> y busca qué hechos y reglas la respaldan. Es útil en <strong>diagnóstico</strong> (¿cuál es la causa probable?).</p>
<h3 id="resolución-de-conflictos">4.3 Resolución de conflictos</h3>
<p>Si varias reglas pueden activarse, el motor elige con un criterio:</p>
<ul>
<li><strong>Prioridad</strong> asignada a cada regla.</li>
<li><strong>Especificidad:</strong> gana la regla con más condiciones.</li>
<li><strong>Recencia:</strong> gana la regla que usa hechos más recientes.</li>
<li><strong>Orden de aparición</strong> en la base.</li>
</ul>
<hr>
<h2 id="ciclo-de-vida-de-un-sistema-de-producción">5. 4.4 Ciclo de vida de un sistema de producción</h2>
<ol>
<li><strong>Identificación del problema</strong> y del experto o fuente de conocimiento.</li>
<li><strong>Adquisición del conocimiento</strong> (entrevistas, normas, manuales, datos).</li>
<li><strong>Representación:</strong> escribir las reglas y definir los hechos.</li>
<li><strong>Implementación</strong> en una herramienta o lenguaje.</li>
<li><strong>Pruebas y validación</strong> con casos reales.</li>
<li><strong>Mantenimiento:</strong> actualizar reglas conforme cambia el dominio.</li>
</ol>
<hr>
<h2 id="ejemplo-ilustrativo-sistema-de-asignación-de-muelles">6. Ejemplo ilustrativo: sistema de asignación de muelles</h2>
<p><strong>Problema:</strong> asignar cada camión que llega a un muelle de un centro de distribución.</p>
<ul>
<li><strong>Hechos:</strong> tipo de carga, hora de llegada, muelles libres, prioridad del cliente.</li>
<li><strong>Reglas (base de conocimientos):</strong>
<ul>
<li>R1: SI <code>carga = refrigerada</code> ENTONCES <code>muelle_requerido = refrigerado</code>.</li>
<li>R2: SI <code>muelle_requerido = refrigerado</code> Y <code>muelle_refrigerado_libre = sí</code> ENTONCES <code>asignar_muelle_refrigerado</code>.</li>
<li>R3: SI <code>muelle_refrigerado_libre = no</code> ENTONCES <code>poner_en_espera</code>.</li>
</ul>
</li>
<li><strong>Motor:</strong> encadenamiento hacia adelante; resolución de conflictos por prioridad de regla.</li>
<li><strong>Salida:</strong> muelle asignado y explicación de la regla aplicada.</li>
</ul>
<hr>
<h2 id="caso-de-la-actividad-7-clasificación-de-peces-con-reglas-de-producción-en-prolog">7. Caso de la Actividad 7: clasificación de peces con reglas de producción en Prolog</h2>
<h3 id="el-problema">7.1 El problema</h3>
<p>El recurso de plataforma (Capítulo 4, p. 190) presenta <strong>diez reglas (R1 a R10)</strong> con las que un especialista clasifica peces. Hay que modelarlas como un <strong>sistema de producción en Prolog</strong>. Resumen de las reglas:</p>
<ul>
<li>R1: boca pequeña, dientes puntiagudos y vive en ríos de muchas partes del mundo → orden <strong>Ciprinodontiformes</strong>.</li>
<li>R2: orden Ciprinodontiformes, América del Sur, <strong>macho con gonopodio</strong> → familia <strong>Poecílidos</strong> (la hembra es 3 cm más grande que el macho).</li>
<li>R3: orden Ciprinodontiformes, América del Sur, <strong>hembra</strong> → familia <strong>Poecílidos</strong> (el macho es 3 cm más pequeño que la hembra).</li>
<li>R4: familia Poecílidos y manchas a lo largo del cuerpo → <strong>Gambusia affinis</strong>.</li>
<li>R5: familia Poecílidos, color gris y rayas verdes → <strong>Gambusia punctata</strong>.</li>
<li>R6: tamaño medio, dientes puntiagudos, ríos de todo el mundo y <strong>canales laberínticos</strong> → orden <strong>Anabátidos</strong>.</li>
<li>R7: orden Anabátidos, azul con rayas rojas, de Asia → <strong>luchador de Siam</strong>.</li>
<li>R8: orden Anabátidos, 25 cm y <strong>sin rayas</strong> → <strong>perca trepadora</strong>.</li>
<li>R9: boca pequeña, ríos de todo el mundo y cola redondeada → orden <strong>Cíclidos</strong>.</li>
<li>R10: orden Cíclidos, África, rojo con manchas negras → <strong>pez joya</strong>.</li>
</ul>
<blockquote>
<p>Verifica siempre el texto exacto de cada regla en la plataforma; este resumen es una guía para entender la estructura.</p>
</blockquote>
<h3 id="paso-1-separar-hechos-reglas-y-conclusiones">7.2 Paso 1: separar hechos, reglas y conclusiones</h3>
<p>Esta separación es la <strong>arquitectura</strong> de la sesión aplicada al caso:</p>
<ul>
<li><strong>Hechos (memoria de trabajo):</strong> lo que se observa de un pez concreto (<code>boca</code>, <code>dientes</code>, <code>color</code>, <code>rayas</code>, <code>region</code>, <code>sexo</code>…). Cambian con cada pez.</li>
<li><strong>Base de conocimientos:</strong> las diez reglas. No cambian entre un pez y otro.</li>
<li><strong>Conclusiones intermedias:</strong> orden y familia; son <strong>resultados de unas reglas y condiciones de otras</strong>.</li>
<li><strong>Conclusiones finales:</strong> la raza (R4, R5, R7, R8, R10).</li>
<li><strong>Motor de inferencia:</strong> el que ofrece Prolog; no se programa.</li>
</ul>
<p>El <strong>mapa de dependencias</strong> muestra el orden de razonamiento:</p>
<ul>
<li>R1 → orden Ciprinodontiformes → R2 o R3 → familia Poecílidos → R4 o R5 → raza.</li>
<li>R6 → orden Anabátidos → R7 o R8 → raza.</li>
<li>R9 → orden Cíclidos → R10 → raza.</li>
</ul>
<h3 id="paso-2-decisiones-de-modelado">7.3 Paso 2: decisiones de modelado</h3>
<p>Toda traducción a Prolog exige decidir y <strong>documentar</strong> estos puntos (y tu reporte debe mencionarlos):</p>
<ol>
<li><strong>Un predicado por propiedad.</strong> <code>boca(Pez, pequena)</code>, <code>color(Pez, rojo)</code>. En Prolog se escribe sin acentos ni espacios en los átomos (<code>pequena</code>, <code>america_del_sur</code>).</li>
<li><strong>Las condiciones se escriben como cuerpo de la regla</strong>, separadas por coma (la coma es el <strong>Y</strong> lógico).</li>
<li><strong>Las conclusiones intermedias son predicados</strong> (<code>orden/2</code>, <code>familia/2</code>) que otras reglas consultan.</li>
<li><strong>«Ríos de muchas partes del mundo» (R1) y «ríos de todo el mundo» (R6, R9):</strong> el texto las redacta distinto; aquí se unifican como <code>rios(Pez, todo_el_mundo)</code> y la región se maneja aparte con <code>region/2</code>. Es una <strong>decisión de modelado</strong> que debes justificar en el reporte.</li>
<li><strong>«No tiene rayas» (R8):</strong> se usa la <strong>negación como fallo</strong>, <code>\+ rayas(P, _)</code>: la condición se cumple si Prolog <strong>no logra demostrar</strong> que el pez tiene rayas. Esto supone que los hechos están completos (mundo cerrado).</li>
<li><strong>Identificar las reglas por nombre:</strong> se usa <code>regla(r1, P)</code> para poder <strong>mostrar qué reglas se activaron</strong> (explicación).</li>
</ol>
<h3 id="paso-3-el-programa-completo-archivo-peces.pl">7.4 Paso 3: el programa completo (archivo <code>peces.pl</code>)</h3>
<p>Guárdalo con extensión <code>.pl</code>. Ya fue probado en SWI-Prolog.</p>
<pre class=" language-prolog"><code class="prism  language-prolog"><span class="token operator">:-</span> <span class="token function">encoding</span><span class="token punctuation">(</span>utf8<span class="token punctuation">)</span><span class="token operator">.</span>
<span class="token comment">% =====================================================================</span>
<span class="token comment">% Sistema de producción basado en reglas: clasificación de peces</span>
<span class="token comment">% Ejercicio del capítulo 4 (reglas de producción). Motor: SWI-Prolog.</span>
<span class="token comment">% =====================================================================</span>

<span class="token comment">% Los hechos se agrupan por pez; se avisa a Prolog que cada predicado</span>
<span class="token comment">% aparece en varios lugares del archivo.</span>
<span class="token operator">:-</span> discontiguous <span class="token function">boca/2</span><span class="token punctuation">,</span> <span class="token function">dientes/2</span><span class="token punctuation">,</span> <span class="token function">rios/2</span><span class="token punctuation">,</span> <span class="token function">region/2</span><span class="token punctuation">,</span> <span class="token function">sexo/2</span><span class="token punctuation">,</span> <span class="token function">cuerpo/2</span><span class="token punctuation">,</span>
                 <span class="token function">tamano/2</span><span class="token punctuation">,</span> <span class="token function">color/2</span><span class="token punctuation">,</span> <span class="token function">rayas/2</span><span class="token punctuation">,</span> <span class="token function">cola/2</span><span class="token punctuation">,</span> <span class="token function">manchas/2</span><span class="token punctuation">,</span>
                 <span class="token function">longitud_cm/2</span><span class="token punctuation">,</span> <span class="token function">canales_laberinticos/1</span><span class="token punctuation">,</span> <span class="token function">gonopodio/1</span><span class="token operator">.</span>

<span class="token comment">% ---------- 1. BASE DE HECHOS (casos de prueba) ----------</span>
<span class="token comment">% Cada hecho describe una propiedad observada de un pez concreto.</span>

<span class="token comment">% Pez 1: hembra de América del Sur, boca pequeña, dientes puntiagudos</span>
<span class="token function">boca</span><span class="token punctuation">(</span>pez1<span class="token punctuation">,</span> pequena<span class="token punctuation">)</span><span class="token operator">.</span>
<span class="token function">dientes</span><span class="token punctuation">(</span>pez1<span class="token punctuation">,</span> puntiagudos<span class="token punctuation">)</span><span class="token operator">.</span>
<span class="token function">rios</span><span class="token punctuation">(</span>pez1<span class="token punctuation">,</span> todo_el_mundo<span class="token punctuation">)</span><span class="token operator">.</span>
<span class="token function">region</span><span class="token punctuation">(</span>pez1<span class="token punctuation">,</span> america_del_sur<span class="token punctuation">)</span><span class="token operator">.</span>
<span class="token function">sexo</span><span class="token punctuation">(</span>pez1<span class="token punctuation">,</span> hembra<span class="token punctuation">)</span><span class="token operator">.</span>
<span class="token function">cuerpo</span><span class="token punctuation">(</span>pez1<span class="token punctuation">,</span> manchas_a_lo_largo<span class="token punctuation">)</span><span class="token operator">.</span>

<span class="token comment">% Pez 2: pez de ríos de Asia con canales laberínticos, azul con rayas rojas</span>
<span class="token function">tamano</span><span class="token punctuation">(</span>pez2<span class="token punctuation">,</span> medio<span class="token punctuation">)</span><span class="token operator">.</span>
<span class="token function">dientes</span><span class="token punctuation">(</span>pez2<span class="token punctuation">,</span> puntiagudos<span class="token punctuation">)</span><span class="token operator">.</span>
<span class="token function">rios</span><span class="token punctuation">(</span>pez2<span class="token punctuation">,</span> todo_el_mundo<span class="token punctuation">)</span><span class="token operator">.</span>
<span class="token function">canales_laberinticos</span><span class="token punctuation">(</span>pez2<span class="token punctuation">)</span><span class="token operator">.</span>
<span class="token function">color</span><span class="token punctuation">(</span>pez2<span class="token punctuation">,</span> azul<span class="token punctuation">)</span><span class="token operator">.</span>
<span class="token function">rayas</span><span class="token punctuation">(</span>pez2<span class="token punctuation">,</span> rojas<span class="token punctuation">)</span><span class="token operator">.</span>
<span class="token function">region</span><span class="token punctuation">(</span>pez2<span class="token punctuation">,</span> asia<span class="token punctuation">)</span><span class="token operator">.</span>

<span class="token comment">% Pez 3: pez de ríos de África, rojo con manchas negras</span>
<span class="token function">boca</span><span class="token punctuation">(</span>pez3<span class="token punctuation">,</span> pequena<span class="token punctuation">)</span><span class="token operator">.</span>
<span class="token function">rios</span><span class="token punctuation">(</span>pez3<span class="token punctuation">,</span> todo_el_mundo<span class="token punctuation">)</span><span class="token operator">.</span>
<span class="token function">cola</span><span class="token punctuation">(</span>pez3<span class="token punctuation">,</span> redondeada<span class="token punctuation">)</span><span class="token operator">.</span>
<span class="token function">region</span><span class="token punctuation">(</span>pez3<span class="token punctuation">,</span> africa<span class="token punctuation">)</span><span class="token operator">.</span>
<span class="token function">color</span><span class="token punctuation">(</span>pez3<span class="token punctuation">,</span> rojo<span class="token punctuation">)</span><span class="token operator">.</span>
<span class="token function">manchas</span><span class="token punctuation">(</span>pez3<span class="token punctuation">,</span> negras<span class="token punctuation">)</span><span class="token operator">.</span>

<span class="token comment">% Pez 4: pez con canales laberínticos, 25 cm, sin rayas</span>
<span class="token function">tamano</span><span class="token punctuation">(</span>pez4<span class="token punctuation">,</span> medio<span class="token punctuation">)</span><span class="token operator">.</span>
<span class="token function">dientes</span><span class="token punctuation">(</span>pez4<span class="token punctuation">,</span> puntiagudos<span class="token punctuation">)</span><span class="token operator">.</span>
<span class="token function">rios</span><span class="token punctuation">(</span>pez4<span class="token punctuation">,</span> todo_el_mundo<span class="token punctuation">)</span><span class="token operator">.</span>
<span class="token function">canales_laberinticos</span><span class="token punctuation">(</span>pez4<span class="token punctuation">)</span><span class="token operator">.</span>
<span class="token function">longitud_cm</span><span class="token punctuation">(</span>pez4<span class="token punctuation">,</span> <span class="token number">25</span><span class="token punctuation">)</span><span class="token operator">.</span>

<span class="token comment">% Pez 5: macho de América del Sur con gonopodio, gris con rayas verdes</span>
<span class="token function">boca</span><span class="token punctuation">(</span>pez5<span class="token punctuation">,</span> pequena<span class="token punctuation">)</span><span class="token operator">.</span>
<span class="token function">dientes</span><span class="token punctuation">(</span>pez5<span class="token punctuation">,</span> puntiagudos<span class="token punctuation">)</span><span class="token operator">.</span>
<span class="token function">rios</span><span class="token punctuation">(</span>pez5<span class="token punctuation">,</span> todo_el_mundo<span class="token punctuation">)</span><span class="token operator">.</span>
<span class="token function">region</span><span class="token punctuation">(</span>pez5<span class="token punctuation">,</span> america_del_sur<span class="token punctuation">)</span><span class="token operator">.</span>
<span class="token function">sexo</span><span class="token punctuation">(</span>pez5<span class="token punctuation">,</span> macho<span class="token punctuation">)</span><span class="token operator">.</span>
<span class="token function">gonopodio</span><span class="token punctuation">(</span>pez5<span class="token punctuation">)</span><span class="token operator">.</span>
<span class="token function">color</span><span class="token punctuation">(</span>pez5<span class="token punctuation">,</span> gris<span class="token punctuation">)</span><span class="token operator">.</span>
<span class="token function">rayas</span><span class="token punctuation">(</span>pez5<span class="token punctuation">,</span> verdes<span class="token punctuation">)</span><span class="token operator">.</span>

<span class="token comment">% ---------- 2. BASE DE REGLAS (R1 a R10) ----------</span>
<span class="token comment">% regla(Id, Pez) es verdadera si el Pez cumple las condiciones de la regla Id.</span>

<span class="token function">regla</span><span class="token punctuation">(</span>r1<span class="token punctuation">,</span> <span class="token variable">P</span><span class="token punctuation">)</span> <span class="token operator">:-</span>                       <span class="token comment">% R1: orden Ciprinodontiformes</span>
    <span class="token function">boca</span><span class="token punctuation">(</span><span class="token variable">P</span><span class="token punctuation">,</span> pequena<span class="token punctuation">)</span><span class="token punctuation">,</span> <span class="token function">dientes</span><span class="token punctuation">(</span><span class="token variable">P</span><span class="token punctuation">,</span> puntiagudos<span class="token punctuation">)</span><span class="token punctuation">,</span> <span class="token function">rios</span><span class="token punctuation">(</span><span class="token variable">P</span><span class="token punctuation">,</span> todo_el_mundo<span class="token punctuation">)</span><span class="token operator">.</span>
<span class="token function">regla</span><span class="token punctuation">(</span>r2<span class="token punctuation">,</span> <span class="token variable">P</span><span class="token punctuation">)</span> <span class="token operator">:-</span>                       <span class="token comment">% R2: familia Poecilidos (macho con gonopodio)</span>
    <span class="token function">orden</span><span class="token punctuation">(</span><span class="token variable">P</span><span class="token punctuation">,</span> ciprinodontiformes<span class="token punctuation">)</span><span class="token punctuation">,</span> <span class="token function">region</span><span class="token punctuation">(</span><span class="token variable">P</span><span class="token punctuation">,</span> america_del_sur<span class="token punctuation">)</span><span class="token punctuation">,</span>
    <span class="token function">sexo</span><span class="token punctuation">(</span><span class="token variable">P</span><span class="token punctuation">,</span> macho<span class="token punctuation">)</span><span class="token punctuation">,</span> <span class="token function">gonopodio</span><span class="token punctuation">(</span><span class="token variable">P</span><span class="token punctuation">)</span><span class="token operator">.</span>
<span class="token function">regla</span><span class="token punctuation">(</span>r3<span class="token punctuation">,</span> <span class="token variable">P</span><span class="token punctuation">)</span> <span class="token operator">:-</span>                       <span class="token comment">% R3: familia Poecilidos (hembra)</span>
    <span class="token function">orden</span><span class="token punctuation">(</span><span class="token variable">P</span><span class="token punctuation">,</span> ciprinodontiformes<span class="token punctuation">)</span><span class="token punctuation">,</span> <span class="token function">region</span><span class="token punctuation">(</span><span class="token variable">P</span><span class="token punctuation">,</span> america_del_sur<span class="token punctuation">)</span><span class="token punctuation">,</span> <span class="token function">sexo</span><span class="token punctuation">(</span><span class="token variable">P</span><span class="token punctuation">,</span> hembra<span class="token punctuation">)</span><span class="token operator">.</span>
<span class="token function">regla</span><span class="token punctuation">(</span>r4<span class="token punctuation">,</span> <span class="token variable">P</span><span class="token punctuation">)</span> <span class="token operator">:-</span>                       <span class="token comment">% R4: Gambusia affinis</span>
    <span class="token function">familia</span><span class="token punctuation">(</span><span class="token variable">P</span><span class="token punctuation">,</span> poecilidos<span class="token punctuation">)</span><span class="token punctuation">,</span> <span class="token function">cuerpo</span><span class="token punctuation">(</span><span class="token variable">P</span><span class="token punctuation">,</span> manchas_a_lo_largo<span class="token punctuation">)</span><span class="token operator">.</span>
<span class="token function">regla</span><span class="token punctuation">(</span>r5<span class="token punctuation">,</span> <span class="token variable">P</span><span class="token punctuation">)</span> <span class="token operator">:-</span>                       <span class="token comment">% R5: Gambusia punctata</span>
    <span class="token function">familia</span><span class="token punctuation">(</span><span class="token variable">P</span><span class="token punctuation">,</span> poecilidos<span class="token punctuation">)</span><span class="token punctuation">,</span> <span class="token function">color</span><span class="token punctuation">(</span><span class="token variable">P</span><span class="token punctuation">,</span> gris<span class="token punctuation">)</span><span class="token punctuation">,</span> <span class="token function">rayas</span><span class="token punctuation">(</span><span class="token variable">P</span><span class="token punctuation">,</span> verdes<span class="token punctuation">)</span><span class="token operator">.</span>
<span class="token function">regla</span><span class="token punctuation">(</span>r6<span class="token punctuation">,</span> <span class="token variable">P</span><span class="token punctuation">)</span> <span class="token operator">:-</span>                       <span class="token comment">% R6: orden Anabatidos</span>
    <span class="token function">tamano</span><span class="token punctuation">(</span><span class="token variable">P</span><span class="token punctuation">,</span> medio<span class="token punctuation">)</span><span class="token punctuation">,</span> <span class="token function">dientes</span><span class="token punctuation">(</span><span class="token variable">P</span><span class="token punctuation">,</span> puntiagudos<span class="token punctuation">)</span><span class="token punctuation">,</span> <span class="token function">rios</span><span class="token punctuation">(</span><span class="token variable">P</span><span class="token punctuation">,</span> todo_el_mundo<span class="token punctuation">)</span><span class="token punctuation">,</span>
    <span class="token function">canales_laberinticos</span><span class="token punctuation">(</span><span class="token variable">P</span><span class="token punctuation">)</span><span class="token operator">.</span>
<span class="token function">regla</span><span class="token punctuation">(</span>r7<span class="token punctuation">,</span> <span class="token variable">P</span><span class="token punctuation">)</span> <span class="token operator">:-</span>                       <span class="token comment">% R7: luchador de Siam</span>
    <span class="token function">orden</span><span class="token punctuation">(</span><span class="token variable">P</span><span class="token punctuation">,</span> anabatidos<span class="token punctuation">)</span><span class="token punctuation">,</span> <span class="token function">color</span><span class="token punctuation">(</span><span class="token variable">P</span><span class="token punctuation">,</span> azul<span class="token punctuation">)</span><span class="token punctuation">,</span> <span class="token function">rayas</span><span class="token punctuation">(</span><span class="token variable">P</span><span class="token punctuation">,</span> rojas<span class="token punctuation">)</span><span class="token punctuation">,</span> <span class="token function">region</span><span class="token punctuation">(</span><span class="token variable">P</span><span class="token punctuation">,</span> asia<span class="token punctuation">)</span><span class="token operator">.</span>
<span class="token function">regla</span><span class="token punctuation">(</span>r8<span class="token punctuation">,</span> <span class="token variable">P</span><span class="token punctuation">)</span> <span class="token operator">:-</span>                       <span class="token comment">% R8: perca trepadora (25 cm, sin rayas)</span>
    <span class="token function">orden</span><span class="token punctuation">(</span><span class="token variable">P</span><span class="token punctuation">,</span> anabatidos<span class="token punctuation">)</span><span class="token punctuation">,</span> <span class="token function">longitud_cm</span><span class="token punctuation">(</span><span class="token variable">P</span><span class="token punctuation">,</span> <span class="token number">25</span><span class="token punctuation">)</span><span class="token punctuation">,</span> <span class="token operator">\+</span> <span class="token function">rayas</span><span class="token punctuation">(</span><span class="token variable">P</span><span class="token punctuation">,</span> <span class="token variable">_</span><span class="token punctuation">)</span><span class="token operator">.</span>
<span class="token function">regla</span><span class="token punctuation">(</span>r9<span class="token punctuation">,</span> <span class="token variable">P</span><span class="token punctuation">)</span> <span class="token operator">:-</span>                       <span class="token comment">% R9: orden Ciclidos</span>
    <span class="token function">boca</span><span class="token punctuation">(</span><span class="token variable">P</span><span class="token punctuation">,</span> pequena<span class="token punctuation">)</span><span class="token punctuation">,</span> <span class="token function">rios</span><span class="token punctuation">(</span><span class="token variable">P</span><span class="token punctuation">,</span> todo_el_mundo<span class="token punctuation">)</span><span class="token punctuation">,</span> <span class="token function">cola</span><span class="token punctuation">(</span><span class="token variable">P</span><span class="token punctuation">,</span> redondeada<span class="token punctuation">)</span><span class="token operator">.</span>
<span class="token function">regla</span><span class="token punctuation">(</span>r10<span class="token punctuation">,</span> <span class="token variable">P</span><span class="token punctuation">)</span> <span class="token operator">:-</span>                      <span class="token comment">% R10: pez joya</span>
    <span class="token function">orden</span><span class="token punctuation">(</span><span class="token variable">P</span><span class="token punctuation">,</span> ciclidos<span class="token punctuation">)</span><span class="token punctuation">,</span> <span class="token function">region</span><span class="token punctuation">(</span><span class="token variable">P</span><span class="token punctuation">,</span> africa<span class="token punctuation">)</span><span class="token punctuation">,</span> <span class="token function">color</span><span class="token punctuation">(</span><span class="token variable">P</span><span class="token punctuation">,</span> rojo<span class="token punctuation">)</span><span class="token punctuation">,</span> <span class="token function">manchas</span><span class="token punctuation">(</span><span class="token variable">P</span><span class="token punctuation">,</span> negras<span class="token punctuation">)</span><span class="token operator">.</span>

<span class="token comment">% ---------- 3. CONCLUSIONES (lo que cada regla deduce) ----------</span>
<span class="token function">orden</span><span class="token punctuation">(</span><span class="token variable">P</span><span class="token punctuation">,</span> ciprinodontiformes<span class="token punctuation">)</span> <span class="token operator">:-</span> <span class="token function">regla</span><span class="token punctuation">(</span>r1<span class="token punctuation">,</span> <span class="token variable">P</span><span class="token punctuation">)</span><span class="token operator">.</span>
<span class="token function">orden</span><span class="token punctuation">(</span><span class="token variable">P</span><span class="token punctuation">,</span> anabatidos<span class="token punctuation">)</span>         <span class="token operator">:-</span> <span class="token function">regla</span><span class="token punctuation">(</span>r6<span class="token punctuation">,</span> <span class="token variable">P</span><span class="token punctuation">)</span><span class="token operator">.</span>
<span class="token function">orden</span><span class="token punctuation">(</span><span class="token variable">P</span><span class="token punctuation">,</span> ciclidos<span class="token punctuation">)</span>           <span class="token operator">:-</span> <span class="token function">regla</span><span class="token punctuation">(</span>r9<span class="token punctuation">,</span> <span class="token variable">P</span><span class="token punctuation">)</span><span class="token operator">.</span>

<span class="token function">familia</span><span class="token punctuation">(</span><span class="token variable">P</span><span class="token punctuation">,</span> poecilidos<span class="token punctuation">)</span> <span class="token operator">:-</span> <span class="token function">regla</span><span class="token punctuation">(</span>r2<span class="token punctuation">,</span> <span class="token variable">P</span><span class="token punctuation">)</span><span class="token operator">.</span>
<span class="token function">familia</span><span class="token punctuation">(</span><span class="token variable">P</span><span class="token punctuation">,</span> poecilidos<span class="token punctuation">)</span> <span class="token operator">:-</span> <span class="token function">regla</span><span class="token punctuation">(</span>r3<span class="token punctuation">,</span> <span class="token variable">P</span><span class="token punctuation">)</span><span class="token operator">.</span>

<span class="token function">raza</span><span class="token punctuation">(</span><span class="token variable">P</span><span class="token punctuation">,</span> gambusia_affinis<span class="token punctuation">)</span>   <span class="token operator">:-</span> <span class="token function">regla</span><span class="token punctuation">(</span>r4<span class="token punctuation">,</span> <span class="token variable">P</span><span class="token punctuation">)</span><span class="token operator">.</span>
<span class="token function">raza</span><span class="token punctuation">(</span><span class="token variable">P</span><span class="token punctuation">,</span> gambusia_punctata<span class="token punctuation">)</span>  <span class="token operator">:-</span> <span class="token function">regla</span><span class="token punctuation">(</span>r5<span class="token punctuation">,</span> <span class="token variable">P</span><span class="token punctuation">)</span><span class="token operator">.</span>
<span class="token function">raza</span><span class="token punctuation">(</span><span class="token variable">P</span><span class="token punctuation">,</span> luchador_de_siam<span class="token punctuation">)</span>   <span class="token operator">:-</span> <span class="token function">regla</span><span class="token punctuation">(</span>r7<span class="token punctuation">,</span> <span class="token variable">P</span><span class="token punctuation">)</span><span class="token operator">.</span>
<span class="token function">raza</span><span class="token punctuation">(</span><span class="token variable">P</span><span class="token punctuation">,</span> perca_trepadora<span class="token punctuation">)</span>    <span class="token operator">:-</span> <span class="token function">regla</span><span class="token punctuation">(</span>r8<span class="token punctuation">,</span> <span class="token variable">P</span><span class="token punctuation">)</span><span class="token operator">.</span>
<span class="token function">raza</span><span class="token punctuation">(</span><span class="token variable">P</span><span class="token punctuation">,</span> pez_joya<span class="token punctuation">)</span>           <span class="token operator">:-</span> <span class="token function">regla</span><span class="token punctuation">(</span>r10<span class="token punctuation">,</span> <span class="token variable">P</span><span class="token punctuation">)</span><span class="token operator">.</span>

<span class="token comment">% Dato adicional que deducen R2 y R3 sobre el tamaño relativo de los sexos</span>
<span class="token function">tamano_relativo</span><span class="token punctuation">(</span><span class="token variable">P</span><span class="token punctuation">,</span> <span class="token string">'la hembra es 3 cm mas grande que el macho'</span><span class="token punctuation">)</span> <span class="token operator">:-</span> <span class="token function">regla</span><span class="token punctuation">(</span>r2<span class="token punctuation">,</span> <span class="token variable">P</span><span class="token punctuation">)</span><span class="token operator">.</span>
<span class="token function">tamano_relativo</span><span class="token punctuation">(</span><span class="token variable">P</span><span class="token punctuation">,</span> <span class="token string">'el macho es 3 cm mas pequeno que la hembra'</span><span class="token punctuation">)</span> <span class="token operator">:-</span> <span class="token function">regla</span><span class="token punctuation">(</span>r3<span class="token punctuation">,</span> <span class="token variable">P</span><span class="token punctuation">)</span><span class="token operator">.</span>

<span class="token comment">% ---------- 4. MECANISMO DE CONSULTA Y EXPLICACION ----------</span>
<span class="token function">identificar</span><span class="token punctuation">(</span><span class="token variable">P</span><span class="token punctuation">)</span> <span class="token operator">:-</span>
    <span class="token function">format</span><span class="token punctuation">(</span><span class="token string">"~nPez: ~w~n"</span><span class="token punctuation">,</span> <span class="token punctuation">[</span><span class="token variable">P</span><span class="token punctuation">]</span><span class="token punctuation">)</span><span class="token punctuation">,</span>
    <span class="token function">forall</span><span class="token punctuation">(</span><span class="token function">orden</span><span class="token punctuation">(</span><span class="token variable">P</span><span class="token punctuation">,</span> <span class="token variable">O</span><span class="token punctuation">)</span><span class="token punctuation">,</span>  <span class="token function">format</span><span class="token punctuation">(</span><span class="token string">"  Orden:   ~w~n"</span><span class="token punctuation">,</span> <span class="token punctuation">[</span><span class="token variable">O</span><span class="token punctuation">]</span><span class="token punctuation">)</span><span class="token punctuation">)</span><span class="token punctuation">,</span>
    <span class="token function">forall</span><span class="token punctuation">(</span><span class="token function">familia</span><span class="token punctuation">(</span><span class="token variable">P</span><span class="token punctuation">,</span> <span class="token variable">F</span><span class="token punctuation">)</span><span class="token punctuation">,</span> <span class="token function">format</span><span class="token punctuation">(</span><span class="token string">"  Familia: ~w~n"</span><span class="token punctuation">,</span> <span class="token punctuation">[</span><span class="token variable">F</span><span class="token punctuation">]</span><span class="token punctuation">)</span><span class="token punctuation">)</span><span class="token punctuation">,</span>
    <span class="token function">forall</span><span class="token punctuation">(</span><span class="token function">raza</span><span class="token punctuation">(</span><span class="token variable">P</span><span class="token punctuation">,</span> <span class="token variable">R</span><span class="token punctuation">)</span><span class="token punctuation">,</span>   <span class="token function">format</span><span class="token punctuation">(</span><span class="token string">"  Raza:    ~w~n"</span><span class="token punctuation">,</span> <span class="token punctuation">[</span><span class="token variable">R</span><span class="token punctuation">]</span><span class="token punctuation">)</span><span class="token punctuation">)</span><span class="token punctuation">,</span>
    <span class="token function">forall</span><span class="token punctuation">(</span><span class="token function">tamano_relativo</span><span class="token punctuation">(</span><span class="token variable">P</span><span class="token punctuation">,</span> <span class="token variable">T</span><span class="token punctuation">)</span><span class="token punctuation">,</span> <span class="token function">format</span><span class="token punctuation">(</span><span class="token string">"  Nota:    ~w~n"</span><span class="token punctuation">,</span> <span class="token punctuation">[</span><span class="token variable">T</span><span class="token punctuation">]</span><span class="token punctuation">)</span><span class="token punctuation">)</span><span class="token operator">.</span>

<span class="token function">explicar</span><span class="token punctuation">(</span><span class="token variable">P</span><span class="token punctuation">)</span> <span class="token operator">:-</span>
    <span class="token function">format</span><span class="token punctuation">(</span><span class="token string">"~nReglas que se activaron para ~w:~n"</span><span class="token punctuation">,</span> <span class="token punctuation">[</span><span class="token variable">P</span><span class="token punctuation">]</span><span class="token punctuation">)</span><span class="token punctuation">,</span>
    <span class="token function">forall</span><span class="token punctuation">(</span><span class="token punctuation">(</span> <span class="token function">member</span><span class="token punctuation">(</span><span class="token variable">R</span><span class="token punctuation">,</span> <span class="token punctuation">[</span>r1<span class="token punctuation">,</span>r2<span class="token punctuation">,</span>r3<span class="token punctuation">,</span>r4<span class="token punctuation">,</span>r5<span class="token punctuation">,</span>r6<span class="token punctuation">,</span>r7<span class="token punctuation">,</span>r8<span class="token punctuation">,</span>r9<span class="token punctuation">,</span>r10<span class="token punctuation">]</span><span class="token punctuation">)</span><span class="token punctuation">,</span> <span class="token function">regla</span><span class="token punctuation">(</span><span class="token variable">R</span><span class="token punctuation">,</span> <span class="token variable">P</span><span class="token punctuation">)</span> <span class="token punctuation">)</span><span class="token punctuation">,</span>
           <span class="token function">format</span><span class="token punctuation">(</span><span class="token string">"  ~w~n"</span><span class="token punctuation">,</span> <span class="token punctuation">[</span><span class="token variable">R</span><span class="token punctuation">]</span><span class="token punctuation">)</span><span class="token punctuation">)</span><span class="token operator">.</span>

demo <span class="token operator">:-</span> <span class="token function">forall</span><span class="token punctuation">(</span><span class="token function">member</span><span class="token punctuation">(</span><span class="token variable">P</span><span class="token punctuation">,</span> <span class="token punctuation">[</span>pez1<span class="token punctuation">,</span>pez2<span class="token punctuation">,</span>pez3<span class="token punctuation">,</span>pez4<span class="token punctuation">,</span>pez5<span class="token punctuation">]</span><span class="token punctuation">)</span><span class="token punctuation">,</span> <span class="token punctuation">(</span><span class="token function">identificar</span><span class="token punctuation">(</span><span class="token variable">P</span><span class="token punctuation">)</span><span class="token punctuation">,</span> <span class="token function">explicar</span><span class="token punctuation">(</span><span class="token variable">P</span><span class="token punctuation">)</span><span class="token punctuation">)</span><span class="token punctuation">)</span><span class="token operator">.</span>

</code></pre>
<h3 id="paso-4-cómo-consultarlo">7.5 Paso 4: cómo consultarlo</h3>
<p>En SWI-Prolog:</p>
<pre class=" language-prolog"><code class="prism  language-prolog"><span class="token operator">?-</span> <span class="token function">consult</span><span class="token punctuation">(</span><span class="token string">'peces.pl'</span><span class="token punctuation">)</span><span class="token operator">.</span>
<span class="token operator">?-</span> demo<span class="token operator">.</span>                      <span class="token comment">% clasifica los cinco peces de prueba</span>
<span class="token operator">?-</span> <span class="token function">identificar</span><span class="token punctuation">(</span>pez1<span class="token punctuation">)</span><span class="token operator">.</span>         <span class="token comment">% orden, familia, raza y nota de un pez</span>
<span class="token operator">?-</span> <span class="token function">explicar</span><span class="token punctuation">(</span>pez1<span class="token punctuation">)</span><span class="token operator">.</span>            <span class="token comment">% reglas que se activaron</span>
<span class="token operator">?-</span> <span class="token function">raza</span><span class="token punctuation">(</span><span class="token variable">P</span><span class="token punctuation">,</span> pez_joya<span class="token punctuation">)</span><span class="token operator">.</span>         <span class="token comment">% consulta inversa: qué peces son pez joya</span>
<span class="token operator">?-</span> <span class="token function">orden</span><span class="token punctuation">(</span>pez2<span class="token punctuation">,</span> <span class="token variable">O</span><span class="token punctuation">)</span><span class="token operator">.</span>            <span class="token comment">% solo el orden de pez2</span>

</code></pre>
<p><strong>Resultados esperados con los cinco peces de prueba:</strong></p>
<ul>
<li><strong>pez1:</strong> Ciprinodontiformes → Poecílidos → <em>Gambusia affinis</em>; nota: el macho es 3 cm más pequeño que la hembra. Reglas: R1, R3, R4.</li>
<li><strong>pez2:</strong> Anabátidos → <em>luchador de Siam</em>. Reglas: R6, R7.</li>
<li><strong>pez3:</strong> Cíclidos → <em>pez joya</em>. Reglas: R9, R10.</li>
<li><strong>pez4:</strong> Anabátidos → <em>perca trepadora</em>. Reglas: R6, R8.</li>
<li><strong>pez5:</strong> Ciprinodontiformes → Poecílidos → <em>Gambusia punctata</em>; nota: la hembra es 3 cm más grande que el macho. Reglas: R1, R2, R5.</li>
</ul>
<h3 id="qué-tipo-de-encadenamiento-usa-prolog">7.6 Qué tipo de encadenamiento usa Prolog</h3>
<p>Prolog resuelve con <strong>encadenamiento hacia atrás</strong>: ante la consulta <code>raza(pez1, R)</code>, busca qué regla concluye una raza (R4), para cumplirla necesita <code>familia(pez1, poecilidos)</code>, que a su vez necesita <code>orden(...)</code>, y así hasta llegar a los <strong>hechos</strong>. Es un razonamiento <strong>dirigido por la meta</strong>. Si quisieras encadenamiento hacia adelante (partir de los hechos y derivar todo lo posible) tendrías que programar el ciclo tú mismo.</p>
<p><strong>Resolución de conflictos:</strong> en este caso no hay conflicto porque las reglas se excluyen entre sí; cuando varias reglas aplican, Prolog las prueba <strong>en el orden en que están escritas</strong> y <code>forall</code> las recorre todas.</p>
<h3 id="paso-5-crear-tus-propios-peces-de-prueba">7.7 Paso 5: crear tus propios peces de prueba</h3>
<p>Agrega hechos nuevos y vuelve a consultar. Ejemplo: un pez con canales laberínticos, tamaño medio, dientes puntiagudos, azul con rayas rojas de Asia debe salir como luchador de Siam (es <code>pez2</code>). Prueba también <strong>casos que fallen</strong> (por ejemplo, un pez sin <code>cola</code> redondeada no es Cíclido) para mostrar que el sistema <strong>no clasifica de más</strong>.</p>
<h3 id="estructura-sugerida-del-reporte-de-la-experiencia">7.8 Estructura sugerida del reporte de la experiencia</h3>
<p>La plataforma pide entregar el archivo de Prolog <strong>y</strong> un reporte. Una estructura que cumple la rúbrica:</p>
<ol>
<li><strong>Portada</strong> (universidad, Centro Universitario San Felipe, materia, actividad, nombre, grupo, fecha).</li>
<li><strong>Introducción:</strong> qué es un sistema de producción y por qué Prolog sirve para modelarlo.</li>
<li><strong>Análisis del problema:</strong> hechos, reglas, conclusiones intermedias y finales; mapa de dependencias.</li>
<li><strong>Modelado en Prolog:</strong> decisiones de la sección 7.3, con fragmentos del código.</li>
<li><strong>Pruebas:</strong> captura de pantalla o texto de las consultas y resultados para los cinco peces y al menos un caso propio.</li>
<li><strong>Experiencia:</strong> qué fue fácil o difícil, qué errores tuviste y cómo los resolviste, qué aprendiste.</li>
<li><strong>Conclusión.</strong></li>
<li><strong>Bibliografía</strong> en formato APA.</li>
</ol>
<h3 id="rúbrica-de-la-actividad-10-puntos">7.9 Rúbrica de la actividad (10 puntos)</h3>
<ul>
<li><strong>Pertinencia (4 puntos):</strong> el modelo corresponde al problema planteado y las reglas R1 a R10 están representadas con fidelidad.</li>
<li><strong>Cognitivo (4 puntos):</strong> se muestra comprensión de hechos, reglas e inferencia, y se justifican las decisiones de modelado.</li>
<li><strong>Requisitos de entrega (2 puntos):</strong> archivo de Prolog y documento entregados en tiempo y forma, con portada y bibliografía; archivo nombrado <strong>ActividadN_iniciales</strong> (para esta actividad, <strong>Actividad7_XXX</strong>), de preferencia en <strong>PDF</strong>.</li>
</ul>
<h3 id="y-la-versión-práctica">7.10 Y la versión práctica</h3>
<p>Este caso es la <strong>actividad original</strong> de la plataforma. Como refuerzo, el Anexo (sección 11) explica una <strong>versión práctica</strong> de logística: un sistema experto para un operador logístico en El Buen Fin, hecho en Python/Colab con la misma arquitectura (hechos, reglas, motor). Úsala para practicar; <strong>entrega lo que pida la consigna de plataforma</strong>.</p>
<hr>
<h2 id="errores-comunes-a-evitar">8. Errores comunes a evitar</h2>
<ul>
<li>Mezclar hechos y reglas en el mismo componente.</li>
<li>No definir los valores permitidos de los hechos.</li>
<li>Olvidar el criterio de resolución de conflictos.</li>
<li>Diagramas con componentes sin explicar qué hace cada uno.</li>
<li>Entregar reglas sin probar con casos.</li>
</ul>
<hr>
<h2 id="ejercicio-integrador-diseño-de-la-arquitectura">9. Ejercicio integrador: diseño de la arquitectura</h2>
<p><strong>Individual o en parejas, 60 minutos:</strong></p>
<ol>
<li>Parte del conjunto de reglas de la Sesión 7 y amplíalo a 12 o 15 reglas.</li>
<li>Lista los <strong>hechos</strong> (nombre, tipo, valores permitidos).</li>
<li>Dibuja el <strong>diagrama de arquitectura</strong> (hechos, base de conocimientos, motor, interfaz).</li>
<li>Elige el <strong>tipo de encadenamiento</strong> y justifícalo.</li>
<li>Define el <strong>criterio de resolución de conflictos</strong>.</li>
<li>Prueba el sistema con <strong>dos casos</strong> y escribe el paso a paso del razonamiento.</li>
</ol>
<hr>
<h2 id="cierre-de-la-sesión-y-checklist-de-la-actividad-7">10. Cierre de la sesión y checklist de la Actividad 7</h2>
<p><strong>Actividad 7 (10 puntos, cierra el 4 de noviembre a las 06:59; entrega recomendada a más tardar la noche del 3).</strong> Sigue la consigna y la rúbrica de plataforma. Tu entrega incluye:</p>
<ul>
<li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled=""> Archivo <code>.pl</code> con hechos, las diez reglas R1 a R10 y conclusiones, que <strong>carga sin errores</strong> en SWI-Prolog.</li>
<li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled=""> Pruebas con los cinco peces de ejemplo y al menos un caso propio.</li>
<li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled=""> Reporte con portada, introducción, análisis del problema, modelado, pruebas, experiencia, conclusión y bibliografía APA.</li>
<li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled=""> Decisiones de modelado explicadas (ríos, región, negación en R8).</li>
<li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled=""> Documento nombrado <strong>Actividad7_iniciales</strong>, de preferencia en PDF, junto con el archivo de Prolog.</li>
</ul>
<p><strong>Tarea para la Semana 9:</strong> revisa los recursos de robótica (5.1 conceptos, clasificación y aplicaciones).</p>
<hr>
<h2 id="anexo-versión-práctica-sistema-experto-de-un-operador-logístico-en-el-buen-fin-python">11. Anexo: versión práctica, sistema experto de un operador logístico en El Buen Fin (Python)</h2>
<h3 id="el-problema-y-la-herramienta">11.1 El problema y la herramienta</h3>
<ul>
<li><strong>Problema:</strong> un operador logístico debe decidir, durante El Buen Fin, <strong>cómo despachar cada pedido</strong>: modo de transporte, de dónde surtirlo, seguro, escolta y alertas.</li>
<li><strong>Esquema:</strong> hechos → reglas → motor de inferencia → decisión con explicación (traza).</li>
<li><strong>Herramienta:</strong> <strong>Python en Google Colab</strong>, con un <strong>motor propio de encadenamiento hacia adelante</strong>. Al construirlo tú, ves cómo funciona un motor de inferencia por dentro.</li>
</ul>
<h3 id="los-cinco-grupos-de-hechos">11.2 Los cinco grupos de hechos</h3>
<ul>
<li><strong>Embarque:</strong> cliente (Prime o regular), horas de entrega prometidas, valor, si es HAZMAT, si es refrigerado, distancia.</li>
<li><strong>Inventario:</strong> cantidad pedida, stock local, stock regional, punto de reorden.</li>
<li><strong>Capacidad:</strong> utilización de la flota, demanda del día, capacidad del día, ocupación de la unidad.</li>
<li><strong>Contexto:</strong> Buen Fin activo, clima adverso, riesgo de la ruta.</li>
<li><strong>Hechos derivados:</strong> los que las reglas agregan (urgencia, alto valor, modo, origen…). Otras reglas los usan después: así se forma la <strong>cadena de inferencia</strong>.</li>
</ul>
<h3 id="las-25-reglas-por-grupo">11.3 Las 25 reglas, por grupo</h3>
<ul>
<li><strong>Urgencia y valor (R01 a R04):</strong> Buen Fin con entrega de 24 h o menos, o Prime con 48 h o menos, da urgencia alta; paquete de $5,000 o más es de alto valor.</li>
<li><strong>Riesgo y cumplimiento (R05 a R09):</strong> HAZMAT exige documentos especiales y <strong>prohíbe el transporte aéreo</strong>; alto valor lleva seguro y, en ruta riesgosa, escolta; lo refrigerado va en unidad con cadena de frío.</li>
<li><strong>Inventario (R10 a R13):</strong> surtir local, transferir de otro CEDIS, back-order, o generar orden de compra si el stock baja del punto de reorden.</li>
<li><strong>Capacidad (R14 a R16):</strong> flota por encima de 90 % activa transportista externo; demanda mayor a 130 % de la capacidad activa turno extra.</li>
<li><strong>Modo de transporte (R17 a R20):</strong> aéreo, camión exprés, camión exprés con doble operador (urgente pero con aéreo prohibido) o camión consolidado.</li>
<li><strong>Alertas y optimización (R21 a R25):</strong> alerta crítica si hay urgencia sin inventario, aviso por retraso climático, consolidación de cargas ligeras, ampliación de la ventana de recolección.</li>
</ul>
<p><strong>Cadenas que debes identificar:</strong> R05 → R06 → R19 (HAZMAT cambia el modo de transporte); R04 → R07 y R08 (alto valor genera seguro y escolta); R01 → R17 (urgencia define el modo).</p>
<h3 id="el-código-completo-google-colab">11.4 El código completo (Google Colab)</h3>
<p>Se pega en una sola celda de Colab y se ejecuta. Ya fue probado.</p>
<pre class=" language-python"><code class="prism  language-python"><span class="token comment"># -*- coding: utf-8 -*-</span>
<span class="token triple-quoted-string string">"""
Sistema experto de producción: Operador logístico - El Buen Fin 2025 (datos ilustrativos)
Arquitectura: base de hechos + base de reglas (SI-ENTONCES) + motor de inferencia con trazabilidad.
Encadenamiento hacia adelante: se aplican las reglas hasta que ya no se produce nada nuevo.
"""</span>
<span class="token keyword">from</span> dataclasses <span class="token keyword">import</span> dataclass<span class="token punctuation">,</span> field

<span class="token comment"># ---------------- 1. BASE DE HECHOS ----------------</span>
@dataclass
<span class="token keyword">class</span> <span class="token class-name">Hechos</span><span class="token punctuation">:</span>
    <span class="token triple-quoted-string string">"""Memoria de trabajo: dict nombre -&gt; valor. Las reglas pueden agregar hechos nuevos."""</span>
    d<span class="token punctuation">:</span> <span class="token builtin">dict</span> <span class="token operator">=</span> field<span class="token punctuation">(</span>default_factory<span class="token operator">=</span><span class="token builtin">dict</span><span class="token punctuation">)</span>
    <span class="token keyword">def</span> <span class="token function">get</span><span class="token punctuation">(</span>self<span class="token punctuation">,</span> k<span class="token punctuation">,</span> default<span class="token operator">=</span><span class="token boolean">None</span><span class="token punctuation">)</span><span class="token punctuation">:</span> <span class="token keyword">return</span> self<span class="token punctuation">.</span>d<span class="token punctuation">.</span>get<span class="token punctuation">(</span>k<span class="token punctuation">,</span> default<span class="token punctuation">)</span>
    <span class="token keyword">def</span> <span class="token function">set</span><span class="token punctuation">(</span>self<span class="token punctuation">,</span> k<span class="token punctuation">,</span> v<span class="token punctuation">)</span><span class="token punctuation">:</span>
        <span class="token keyword">if</span> self<span class="token punctuation">.</span>d<span class="token punctuation">.</span>get<span class="token punctuation">(</span>k<span class="token punctuation">)</span> <span class="token operator">!=</span> v<span class="token punctuation">:</span>
            self<span class="token punctuation">.</span>d<span class="token punctuation">[</span>k<span class="token punctuation">]</span> <span class="token operator">=</span> v
            <span class="token keyword">return</span> <span class="token boolean">True</span>
        <span class="token keyword">return</span> <span class="token boolean">False</span>

<span class="token comment"># ---------------- 2. BASE DE REGLAS ----------------</span>
<span class="token comment"># Cada regla: (id, descripcion, condicion(h)-&gt;bool, accion(h)-&gt;(hecho, valor), prioridad)</span>
REGLAS <span class="token operator">=</span> <span class="token punctuation">[</span>
 <span class="token comment"># Contexto y prioridad</span>
 <span class="token punctuation">(</span><span class="token string">"R01"</span><span class="token punctuation">,</span> <span class="token string">"Buen Fin activo y entrega prometida &lt;= 24 h -&gt; urgencia alta"</span><span class="token punctuation">,</span>
  <span class="token keyword">lambda</span> h<span class="token punctuation">:</span> h<span class="token punctuation">.</span>get<span class="token punctuation">(</span><span class="token string">"buen_fin"</span><span class="token punctuation">)</span> <span class="token operator">and</span> h<span class="token punctuation">.</span>get<span class="token punctuation">(</span><span class="token string">"horas_entrega"</span><span class="token punctuation">)</span> <span class="token operator">&lt;=</span> <span class="token number">24</span><span class="token punctuation">,</span> <span class="token punctuation">(</span><span class="token string">"urgencia"</span><span class="token punctuation">,</span> <span class="token string">"alta"</span><span class="token punctuation">)</span><span class="token punctuation">,</span> <span class="token number">10</span><span class="token punctuation">)</span><span class="token punctuation">,</span>
 <span class="token punctuation">(</span><span class="token string">"R02"</span><span class="token punctuation">,</span> <span class="token string">"Cliente Prime con entrega &lt;= 48 h -&gt; urgencia alta"</span><span class="token punctuation">,</span>
  <span class="token keyword">lambda</span> h<span class="token punctuation">:</span> h<span class="token punctuation">.</span>get<span class="token punctuation">(</span><span class="token string">"cliente"</span><span class="token punctuation">)</span> <span class="token operator">==</span> <span class="token string">"prime"</span> <span class="token operator">and</span> h<span class="token punctuation">.</span>get<span class="token punctuation">(</span><span class="token string">"horas_entrega"</span><span class="token punctuation">)</span> <span class="token operator">&lt;=</span> <span class="token number">48</span><span class="token punctuation">,</span> <span class="token punctuation">(</span><span class="token string">"urgencia"</span><span class="token punctuation">,</span> <span class="token string">"alta"</span><span class="token punctuation">)</span><span class="token punctuation">,</span> <span class="token number">9</span><span class="token punctuation">)</span><span class="token punctuation">,</span>
 <span class="token punctuation">(</span><span class="token string">"R03"</span><span class="token punctuation">,</span> <span class="token string">"Entrega &gt; 48 h y sin Prime -&gt; urgencia normal"</span><span class="token punctuation">,</span>
  <span class="token keyword">lambda</span> h<span class="token punctuation">:</span> h<span class="token punctuation">.</span>get<span class="token punctuation">(</span><span class="token string">"horas_entrega"</span><span class="token punctuation">)</span> <span class="token operator">&gt;</span> <span class="token number">48</span> <span class="token operator">and</span> h<span class="token punctuation">.</span>get<span class="token punctuation">(</span><span class="token string">"cliente"</span><span class="token punctuation">)</span> <span class="token operator">!=</span> <span class="token string">"prime"</span><span class="token punctuation">,</span> <span class="token punctuation">(</span><span class="token string">"urgencia"</span><span class="token punctuation">,</span> <span class="token string">"normal"</span><span class="token punctuation">)</span><span class="token punctuation">,</span> <span class="token number">5</span><span class="token punctuation">)</span><span class="token punctuation">,</span>
 <span class="token punctuation">(</span><span class="token string">"R04"</span><span class="token punctuation">,</span> <span class="token string">"Valor del paquete &gt;= $5,000 -&gt; clasificar como alto valor"</span><span class="token punctuation">,</span>
  <span class="token keyword">lambda</span> h<span class="token punctuation">:</span> h<span class="token punctuation">.</span>get<span class="token punctuation">(</span><span class="token string">"valor"</span><span class="token punctuation">)</span> <span class="token operator">&gt;=</span> <span class="token number">5000</span><span class="token punctuation">,</span> <span class="token punctuation">(</span><span class="token string">"alto_valor"</span><span class="token punctuation">,</span> <span class="token boolean">True</span><span class="token punctuation">)</span><span class="token punctuation">,</span> <span class="token number">8</span><span class="token punctuation">)</span><span class="token punctuation">,</span>
 <span class="token comment"># Riesgo</span>
 <span class="token punctuation">(</span><span class="token string">"R05"</span><span class="token punctuation">,</span> <span class="token string">"Mercancia peligrosa (HAZMAT) -&gt; requiere cumplimiento especial"</span><span class="token punctuation">,</span>
  <span class="token keyword">lambda</span> h<span class="token punctuation">:</span> h<span class="token punctuation">.</span>get<span class="token punctuation">(</span><span class="token string">"hazmat"</span><span class="token punctuation">)</span><span class="token punctuation">,</span> <span class="token punctuation">(</span><span class="token string">"cumplimiento"</span><span class="token punctuation">,</span> <span class="token string">"documentos_hazmat"</span><span class="token punctuation">)</span><span class="token punctuation">,</span> <span class="token number">10</span><span class="token punctuation">)</span><span class="token punctuation">,</span>
 <span class="token punctuation">(</span><span class="token string">"R06"</span><span class="token punctuation">,</span> <span class="token string">"HAZMAT -&gt; prohibido transporte aereo de pasajeros"</span><span class="token punctuation">,</span>
  <span class="token keyword">lambda</span> h<span class="token punctuation">:</span> h<span class="token punctuation">.</span>get<span class="token punctuation">(</span><span class="token string">"hazmat"</span><span class="token punctuation">)</span><span class="token punctuation">,</span> <span class="token punctuation">(</span><span class="token string">"aereo_permitido"</span><span class="token punctuation">,</span> <span class="token boolean">False</span><span class="token punctuation">)</span><span class="token punctuation">,</span> <span class="token number">10</span><span class="token punctuation">)</span><span class="token punctuation">,</span>
 <span class="token punctuation">(</span><span class="token string">"R07"</span><span class="token punctuation">,</span> <span class="token string">"Alto valor -&gt; seguro adicional"</span><span class="token punctuation">,</span>
  <span class="token keyword">lambda</span> h<span class="token punctuation">:</span> h<span class="token punctuation">.</span>get<span class="token punctuation">(</span><span class="token string">"alto_valor"</span><span class="token punctuation">)</span><span class="token punctuation">,</span> <span class="token punctuation">(</span><span class="token string">"seguro"</span><span class="token punctuation">,</span> <span class="token string">"adicional"</span><span class="token punctuation">)</span><span class="token punctuation">,</span> <span class="token number">7</span><span class="token punctuation">)</span><span class="token punctuation">,</span>
 <span class="token punctuation">(</span><span class="token string">"R08"</span><span class="token punctuation">,</span> <span class="token string">"Alto valor y destino con riesgo de robo alto -&gt; escolta"</span><span class="token punctuation">,</span>
  <span class="token keyword">lambda</span> h<span class="token punctuation">:</span> h<span class="token punctuation">.</span>get<span class="token punctuation">(</span><span class="token string">"alto_valor"</span><span class="token punctuation">)</span> <span class="token operator">and</span> h<span class="token punctuation">.</span>get<span class="token punctuation">(</span><span class="token string">"riesgo_ruta"</span><span class="token punctuation">)</span> <span class="token operator">==</span> <span class="token string">"alto"</span><span class="token punctuation">,</span> <span class="token punctuation">(</span><span class="token string">"escolta"</span><span class="token punctuation">,</span> <span class="token boolean">True</span><span class="token punctuation">)</span><span class="token punctuation">,</span> <span class="token number">8</span><span class="token punctuation">)</span><span class="token punctuation">,</span>
 <span class="token punctuation">(</span><span class="token string">"R09"</span><span class="token punctuation">,</span> <span class="token string">"Mercancia refrigerada -&gt; unidad con cadena de frio"</span><span class="token punctuation">,</span>
  <span class="token keyword">lambda</span> h<span class="token punctuation">:</span> h<span class="token punctuation">.</span>get<span class="token punctuation">(</span><span class="token string">"refrigerado"</span><span class="token punctuation">)</span><span class="token punctuation">,</span> <span class="token punctuation">(</span><span class="token string">"unidad"</span><span class="token punctuation">,</span> <span class="token string">"refrigerada"</span><span class="token punctuation">)</span><span class="token punctuation">,</span> <span class="token number">9</span><span class="token punctuation">)</span><span class="token punctuation">,</span>
 <span class="token comment"># Inventario</span>
 <span class="token punctuation">(</span><span class="token string">"R10"</span><span class="token punctuation">,</span> <span class="token string">"Stock &gt;= cantidad -&gt; surtir desde CEDIS local"</span><span class="token punctuation">,</span>
  <span class="token keyword">lambda</span> h<span class="token punctuation">:</span> h<span class="token punctuation">.</span>get<span class="token punctuation">(</span><span class="token string">"stock"</span><span class="token punctuation">)</span> <span class="token operator">&gt;=</span> h<span class="token punctuation">.</span>get<span class="token punctuation">(</span><span class="token string">"cantidad"</span><span class="token punctuation">)</span><span class="token punctuation">,</span> <span class="token punctuation">(</span><span class="token string">"origen"</span><span class="token punctuation">,</span> <span class="token string">"cedis_local"</span><span class="token punctuation">)</span><span class="token punctuation">,</span> <span class="token number">6</span><span class="token punctuation">)</span><span class="token punctuation">,</span>
 <span class="token punctuation">(</span><span class="token string">"R11"</span><span class="token punctuation">,</span> <span class="token string">"Stock &lt; cantidad y stock regional &gt;= cantidad -&gt; transferir de otro CEDIS"</span><span class="token punctuation">,</span>
  <span class="token keyword">lambda</span> h<span class="token punctuation">:</span> h<span class="token punctuation">.</span>get<span class="token punctuation">(</span><span class="token string">"stock"</span><span class="token punctuation">)</span> <span class="token operator">&lt;</span> h<span class="token punctuation">.</span>get<span class="token punctuation">(</span><span class="token string">"cantidad"</span><span class="token punctuation">)</span> <span class="token operator">and</span> h<span class="token punctuation">.</span>get<span class="token punctuation">(</span><span class="token string">"stock_regional"</span><span class="token punctuation">)</span> <span class="token operator">&gt;=</span> h<span class="token punctuation">.</span>get<span class="token punctuation">(</span><span class="token string">"cantidad"</span><span class="token punctuation">)</span><span class="token punctuation">,</span>
  <span class="token punctuation">(</span><span class="token string">"origen"</span><span class="token punctuation">,</span> <span class="token string">"cedis_regional"</span><span class="token punctuation">)</span><span class="token punctuation">,</span> <span class="token number">6</span><span class="token punctuation">)</span><span class="token punctuation">,</span>
 <span class="token punctuation">(</span><span class="token string">"R12"</span><span class="token punctuation">,</span> <span class="token string">"Stock insuficiente en toda la region -&gt; back-order y aviso al cliente"</span><span class="token punctuation">,</span>
  <span class="token keyword">lambda</span> h<span class="token punctuation">:</span> h<span class="token punctuation">.</span>get<span class="token punctuation">(</span><span class="token string">"stock"</span><span class="token punctuation">)</span> <span class="token operator">&lt;</span> h<span class="token punctuation">.</span>get<span class="token punctuation">(</span><span class="token string">"cantidad"</span><span class="token punctuation">)</span> <span class="token operator">and</span> h<span class="token punctuation">.</span>get<span class="token punctuation">(</span><span class="token string">"stock_regional"</span><span class="token punctuation">)</span> <span class="token operator">&lt;</span> h<span class="token punctuation">.</span>get<span class="token punctuation">(</span><span class="token string">"cantidad"</span><span class="token punctuation">)</span><span class="token punctuation">,</span>
  <span class="token punctuation">(</span><span class="token string">"origen"</span><span class="token punctuation">,</span> <span class="token string">"back_order"</span><span class="token punctuation">)</span><span class="token punctuation">,</span> <span class="token number">6</span><span class="token punctuation">)</span><span class="token punctuation">,</span>
 <span class="token punctuation">(</span><span class="token string">"R13"</span><span class="token punctuation">,</span> <span class="token string">"Stock local menor al punto de reorden -&gt; generar orden de compra"</span><span class="token punctuation">,</span>
  <span class="token keyword">lambda</span> h<span class="token punctuation">:</span> h<span class="token punctuation">.</span>get<span class="token punctuation">(</span><span class="token string">"stock"</span><span class="token punctuation">)</span> <span class="token operator">&lt;</span> h<span class="token punctuation">.</span>get<span class="token punctuation">(</span><span class="token string">"punto_reorden"</span><span class="token punctuation">)</span><span class="token punctuation">,</span> <span class="token punctuation">(</span><span class="token string">"reabasto"</span><span class="token punctuation">,</span> <span class="token string">"generar_orden_de_compra"</span><span class="token punctuation">)</span><span class="token punctuation">,</span> <span class="token number">4</span><span class="token punctuation">)</span><span class="token punctuation">,</span>
 <span class="token comment"># Capacidad</span>
 <span class="token punctuation">(</span><span class="token string">"R14"</span><span class="token punctuation">,</span> <span class="token string">"Utilizacion de la flota &gt; 90% -&gt; activar transportista externo"</span><span class="token punctuation">,</span>
  <span class="token keyword">lambda</span> h<span class="token punctuation">:</span> h<span class="token punctuation">.</span>get<span class="token punctuation">(</span><span class="token string">"utilizacion_flota"</span><span class="token punctuation">)</span> <span class="token operator">&gt;</span> <span class="token number">0.90</span><span class="token punctuation">,</span> <span class="token punctuation">(</span><span class="token string">"capacidad"</span><span class="token punctuation">,</span> <span class="token string">"transportista_externo"</span><span class="token punctuation">)</span><span class="token punctuation">,</span> <span class="token number">7</span><span class="token punctuation">)</span><span class="token punctuation">,</span>
 <span class="token punctuation">(</span><span class="token string">"R15"</span><span class="token punctuation">,</span> <span class="token string">"Utilizacion de la flota &lt;= 90% -&gt; usar flota propia"</span><span class="token punctuation">,</span>
  <span class="token keyword">lambda</span> h<span class="token punctuation">:</span> h<span class="token punctuation">.</span>get<span class="token punctuation">(</span><span class="token string">"utilizacion_flota"</span><span class="token punctuation">)</span> <span class="token operator">&lt;=</span> <span class="token number">0.90</span><span class="token punctuation">,</span> <span class="token punctuation">(</span><span class="token string">"capacidad"</span><span class="token punctuation">,</span> <span class="token string">"flota_propia"</span><span class="token punctuation">)</span><span class="token punctuation">,</span> <span class="token number">3</span><span class="token punctuation">)</span><span class="token punctuation">,</span>
 <span class="token punctuation">(</span><span class="token string">"R16"</span><span class="token punctuation">,</span> <span class="token string">"Demanda del dia &gt; 130% de la capacidad -&gt; turno extra"</span><span class="token punctuation">,</span>
  <span class="token keyword">lambda</span> h<span class="token punctuation">:</span> h<span class="token punctuation">.</span>get<span class="token punctuation">(</span><span class="token string">"demanda_dia"</span><span class="token punctuation">)</span> <span class="token operator">&gt;</span> <span class="token number">1.30</span> <span class="token operator">*</span> h<span class="token punctuation">.</span>get<span class="token punctuation">(</span><span class="token string">"capacidad_dia"</span><span class="token punctuation">)</span><span class="token punctuation">,</span> <span class="token punctuation">(</span><span class="token string">"turno_extra"</span><span class="token punctuation">,</span> <span class="token boolean">True</span><span class="token punctuation">)</span><span class="token punctuation">,</span> <span class="token number">6</span><span class="token punctuation">)</span><span class="token punctuation">,</span>
 <span class="token comment"># Modo de transporte (encadena urgencia, riesgo y capacidad)</span>
 <span class="token punctuation">(</span><span class="token string">"R17"</span><span class="token punctuation">,</span> <span class="token string">"Urgencia alta y aereo permitido y destino &gt; 600 km -&gt; transporte aereo"</span><span class="token punctuation">,</span>
  <span class="token keyword">lambda</span> h<span class="token punctuation">:</span> h<span class="token punctuation">.</span>get<span class="token punctuation">(</span><span class="token string">"urgencia"</span><span class="token punctuation">)</span> <span class="token operator">==</span> <span class="token string">"alta"</span> <span class="token operator">and</span> h<span class="token punctuation">.</span>get<span class="token punctuation">(</span><span class="token string">"aereo_permitido"</span><span class="token punctuation">,</span> <span class="token boolean">True</span><span class="token punctuation">)</span> <span class="token operator">and</span> h<span class="token punctuation">.</span>get<span class="token punctuation">(</span><span class="token string">"distancia_km"</span><span class="token punctuation">)</span> <span class="token operator">&gt;</span> <span class="token number">600</span><span class="token punctuation">,</span>
  <span class="token punctuation">(</span><span class="token string">"modo"</span><span class="token punctuation">,</span> <span class="token string">"aereo"</span><span class="token punctuation">)</span><span class="token punctuation">,</span> <span class="token number">9</span><span class="token punctuation">)</span><span class="token punctuation">,</span>
 <span class="token punctuation">(</span><span class="token string">"R18"</span><span class="token punctuation">,</span> <span class="token string">"Urgencia alta y distancia &lt;= 600 km -&gt; camion expres"</span><span class="token punctuation">,</span>
  <span class="token keyword">lambda</span> h<span class="token punctuation">:</span> h<span class="token punctuation">.</span>get<span class="token punctuation">(</span><span class="token string">"urgencia"</span><span class="token punctuation">)</span> <span class="token operator">==</span> <span class="token string">"alta"</span> <span class="token operator">and</span> h<span class="token punctuation">.</span>get<span class="token punctuation">(</span><span class="token string">"distancia_km"</span><span class="token punctuation">)</span> <span class="token operator">&lt;=</span> <span class="token number">600</span><span class="token punctuation">,</span> <span class="token punctuation">(</span><span class="token string">"modo"</span><span class="token punctuation">,</span> <span class="token string">"camion_expres"</span><span class="token punctuation">)</span><span class="token punctuation">,</span> <span class="token number">8</span><span class="token punctuation">)</span><span class="token punctuation">,</span>
 <span class="token punctuation">(</span><span class="token string">"R19"</span><span class="token punctuation">,</span> <span class="token string">"Urgencia alta pero aereo prohibido y destino &gt; 600 km -&gt; camion expres con doble operador"</span><span class="token punctuation">,</span>
  <span class="token keyword">lambda</span> h<span class="token punctuation">:</span> h<span class="token punctuation">.</span>get<span class="token punctuation">(</span><span class="token string">"urgencia"</span><span class="token punctuation">)</span> <span class="token operator">==</span> <span class="token string">"alta"</span> <span class="token operator">and</span> h<span class="token punctuation">.</span>get<span class="token punctuation">(</span><span class="token string">"aereo_permitido"</span><span class="token punctuation">,</span> <span class="token boolean">True</span><span class="token punctuation">)</span> <span class="token keyword">is</span> <span class="token boolean">False</span> <span class="token operator">and</span> h<span class="token punctuation">.</span>get<span class="token punctuation">(</span><span class="token string">"distancia_km"</span><span class="token punctuation">)</span> <span class="token operator">&gt;</span> <span class="token number">600</span><span class="token punctuation">,</span>
  <span class="token punctuation">(</span><span class="token string">"modo"</span><span class="token punctuation">,</span> <span class="token string">"camion_expres_doble_operador"</span><span class="token punctuation">)</span><span class="token punctuation">,</span> <span class="token number">9</span><span class="token punctuation">)</span><span class="token punctuation">,</span>
 <span class="token punctuation">(</span><span class="token string">"R20"</span><span class="token punctuation">,</span> <span class="token string">"Urgencia normal -&gt; camion consolidado"</span><span class="token punctuation">,</span>
  <span class="token keyword">lambda</span> h<span class="token punctuation">:</span> h<span class="token punctuation">.</span>get<span class="token punctuation">(</span><span class="token string">"urgencia"</span><span class="token punctuation">)</span> <span class="token operator">==</span> <span class="token string">"normal"</span><span class="token punctuation">,</span> <span class="token punctuation">(</span><span class="token string">"modo"</span><span class="token punctuation">,</span> <span class="token string">"camion_consolidado"</span><span class="token punctuation">)</span><span class="token punctuation">,</span> <span class="token number">5</span><span class="token punctuation">)</span><span class="token punctuation">,</span>
 <span class="token comment"># Alertas y optimizacion</span>
 <span class="token punctuation">(</span><span class="token string">"R21"</span><span class="token punctuation">,</span> <span class="token string">"Origen en back-order y urgencia alta -&gt; alerta critica"</span><span class="token punctuation">,</span>
  <span class="token keyword">lambda</span> h<span class="token punctuation">:</span> h<span class="token punctuation">.</span>get<span class="token punctuation">(</span><span class="token string">"origen"</span><span class="token punctuation">)</span> <span class="token operator">==</span> <span class="token string">"back_order"</span> <span class="token operator">and</span> h<span class="token punctuation">.</span>get<span class="token punctuation">(</span><span class="token string">"urgencia"</span><span class="token punctuation">)</span> <span class="token operator">==</span> <span class="token string">"alta"</span><span class="token punctuation">,</span> <span class="token punctuation">(</span><span class="token string">"alerta"</span><span class="token punctuation">,</span> <span class="token string">"critica_sin_inventario"</span><span class="token punctuation">)</span><span class="token punctuation">,</span> <span class="token number">10</span><span class="token punctuation">)</span><span class="token punctuation">,</span>
 <span class="token punctuation">(</span><span class="token string">"R22"</span><span class="token punctuation">,</span> <span class="token string">"Clima adverso en la ruta -&gt; agregar 12 h al tiempo estimado"</span><span class="token punctuation">,</span>
  <span class="token keyword">lambda</span> h<span class="token punctuation">:</span> h<span class="token punctuation">.</span>get<span class="token punctuation">(</span><span class="token string">"clima_adverso"</span><span class="token punctuation">)</span><span class="token punctuation">,</span> <span class="token punctuation">(</span><span class="token string">"retraso_h"</span><span class="token punctuation">,</span> <span class="token number">12</span><span class="token punctuation">)</span><span class="token punctuation">,</span> <span class="token number">6</span><span class="token punctuation">)</span><span class="token punctuation">,</span>
 <span class="token punctuation">(</span><span class="token string">"R23"</span><span class="token punctuation">,</span> <span class="token string">"Retraso estimado supera la promesa de entrega -&gt; notificar al cliente"</span><span class="token punctuation">,</span>
  <span class="token keyword">lambda</span> h<span class="token punctuation">:</span> h<span class="token punctuation">.</span>get<span class="token punctuation">(</span><span class="token string">"retraso_h"</span><span class="token punctuation">,</span> <span class="token number">0</span><span class="token punctuation">)</span> <span class="token operator">&gt;</span> <span class="token number">0</span> <span class="token operator">and</span> h<span class="token punctuation">.</span>get<span class="token punctuation">(</span><span class="token string">"horas_entrega"</span><span class="token punctuation">)</span> <span class="token operator">&lt;=</span> <span class="token number">24</span><span class="token punctuation">,</span> <span class="token punctuation">(</span><span class="token string">"alerta"</span><span class="token punctuation">,</span> <span class="token string">"notificar_cliente_retraso"</span><span class="token punctuation">)</span><span class="token punctuation">,</span> <span class="token number">8</span><span class="token punctuation">)</span><span class="token punctuation">,</span>
 <span class="token punctuation">(</span><span class="token string">"R24"</span><span class="token punctuation">,</span> <span class="token string">"Carga &lt; 40% de la unidad y urgencia normal -&gt; consolidar con otros pedidos"</span><span class="token punctuation">,</span>
  <span class="token keyword">lambda</span> h<span class="token punctuation">:</span> h<span class="token punctuation">.</span>get<span class="token punctuation">(</span><span class="token string">"ocupacion_unidad"</span><span class="token punctuation">)</span> <span class="token operator">&lt;</span> <span class="token number">0.40</span> <span class="token operator">and</span> h<span class="token punctuation">.</span>get<span class="token punctuation">(</span><span class="token string">"urgencia"</span><span class="token punctuation">)</span> <span class="token operator">==</span> <span class="token string">"normal"</span><span class="token punctuation">,</span> <span class="token punctuation">(</span><span class="token string">"optimizacion"</span><span class="token punctuation">,</span> <span class="token string">"consolidar"</span><span class="token punctuation">)</span><span class="token punctuation">,</span> <span class="token number">3</span><span class="token punctuation">)</span><span class="token punctuation">,</span>
 <span class="token punctuation">(</span><span class="token string">"R25"</span><span class="token punctuation">,</span> <span class="token string">"Buen Fin y capacidad externa activada -&gt; ampliar ventana de recoleccion"</span><span class="token punctuation">,</span>
  <span class="token keyword">lambda</span> h<span class="token punctuation">:</span> h<span class="token punctuation">.</span>get<span class="token punctuation">(</span><span class="token string">"buen_fin"</span><span class="token punctuation">)</span> <span class="token operator">and</span> h<span class="token punctuation">.</span>get<span class="token punctuation">(</span><span class="token string">"capacidad"</span><span class="token punctuation">)</span> <span class="token operator">==</span> <span class="token string">"transportista_externo"</span><span class="token punctuation">,</span>
  <span class="token punctuation">(</span><span class="token string">"ventana_recoleccion"</span><span class="token punctuation">,</span> <span class="token string">"ampliada"</span><span class="token punctuation">)</span><span class="token punctuation">,</span> <span class="token number">4</span><span class="token punctuation">)</span><span class="token punctuation">,</span>
<span class="token punctuation">]</span>

<span class="token comment"># ---------------- 3. MOTOR DE INFERENCIA ----------------</span>
<span class="token keyword">class</span> <span class="token class-name">MotorLogistico</span><span class="token punctuation">:</span>
    <span class="token keyword">def</span> <span class="token function">__init__</span><span class="token punctuation">(</span>self<span class="token punctuation">,</span> reglas<span class="token punctuation">)</span><span class="token punctuation">:</span>
        self<span class="token punctuation">.</span>reglas <span class="token operator">=</span> <span class="token builtin">sorted</span><span class="token punctuation">(</span>reglas<span class="token punctuation">,</span> key<span class="token operator">=</span><span class="token keyword">lambda</span> r<span class="token punctuation">:</span> <span class="token operator">-</span>r<span class="token punctuation">[</span><span class="token number">4</span><span class="token punctuation">]</span><span class="token punctuation">)</span>   <span class="token comment"># resolucion de conflictos: prioridad</span>
    <span class="token keyword">def</span> <span class="token function">inferir</span><span class="token punctuation">(</span>self<span class="token punctuation">,</span> datos<span class="token punctuation">)</span><span class="token punctuation">:</span>
        h <span class="token operator">=</span> Hechos<span class="token punctuation">(</span><span class="token builtin">dict</span><span class="token punctuation">(</span>datos<span class="token punctuation">)</span><span class="token punctuation">)</span>
        traza<span class="token punctuation">,</span> disparadas <span class="token operator">=</span> <span class="token punctuation">[</span><span class="token punctuation">]</span><span class="token punctuation">,</span> <span class="token builtin">set</span><span class="token punctuation">(</span><span class="token punctuation">)</span>
        cambio <span class="token operator">=</span> <span class="token boolean">True</span>
        <span class="token keyword">while</span> cambio<span class="token punctuation">:</span>                                       <span class="token comment"># ciclo: hasta que no haya cambios</span>
            cambio <span class="token operator">=</span> <span class="token boolean">False</span>
            <span class="token keyword">for</span> rid<span class="token punctuation">,</span> desc<span class="token punctuation">,</span> cond<span class="token punctuation">,</span> <span class="token punctuation">(</span>hecho<span class="token punctuation">,</span> valor<span class="token punctuation">)</span><span class="token punctuation">,</span> prio <span class="token keyword">in</span> self<span class="token punctuation">.</span>reglas<span class="token punctuation">:</span>
                <span class="token keyword">if</span> rid <span class="token keyword">in</span> disparadas<span class="token punctuation">:</span> <span class="token keyword">continue</span>
                <span class="token keyword">try</span><span class="token punctuation">:</span>
                    cumple <span class="token operator">=</span> cond<span class="token punctuation">(</span>h<span class="token punctuation">)</span>
                <span class="token keyword">except</span> TypeError<span class="token punctuation">:</span>                           <span class="token comment"># falta un hecho: la regla no aplica</span>
                    cumple <span class="token operator">=</span> <span class="token boolean">False</span>
                <span class="token keyword">if</span> cumple<span class="token punctuation">:</span>
                    disparadas<span class="token punctuation">.</span>add<span class="token punctuation">(</span>rid<span class="token punctuation">)</span>
                    <span class="token keyword">if</span> h<span class="token punctuation">.</span><span class="token builtin">set</span><span class="token punctuation">(</span>hecho<span class="token punctuation">,</span> valor<span class="token punctuation">)</span><span class="token punctuation">:</span> cambio <span class="token operator">=</span> <span class="token boolean">True</span>
                    traza<span class="token punctuation">.</span>append<span class="token punctuation">(</span><span class="token punctuation">(</span>rid<span class="token punctuation">,</span> desc<span class="token punctuation">,</span> hecho<span class="token punctuation">,</span> valor<span class="token punctuation">)</span><span class="token punctuation">)</span>
        <span class="token keyword">return</span> h<span class="token punctuation">,</span> traza

<span class="token keyword">def</span> <span class="token function">mostrar</span><span class="token punctuation">(</span>nombre<span class="token punctuation">,</span> datos<span class="token punctuation">)</span><span class="token punctuation">:</span>
    h<span class="token punctuation">,</span> traza <span class="token operator">=</span> MotorLogistico<span class="token punctuation">(</span>REGLAS<span class="token punctuation">)</span><span class="token punctuation">.</span>inferir<span class="token punctuation">(</span>datos<span class="token punctuation">)</span>
    <span class="token keyword">print</span><span class="token punctuation">(</span>f<span class="token string">"\n=== {nombre} ==="</span><span class="token punctuation">)</span>
    <span class="token keyword">print</span><span class="token punctuation">(</span><span class="token string">"Reglas disparadas:"</span><span class="token punctuation">,</span> <span class="token string">", "</span><span class="token punctuation">.</span>join<span class="token punctuation">(</span>t<span class="token punctuation">[</span><span class="token number">0</span><span class="token punctuation">]</span> <span class="token keyword">for</span> t <span class="token keyword">in</span> traza<span class="token punctuation">)</span><span class="token punctuation">)</span>
    <span class="token keyword">for</span> t <span class="token keyword">in</span> traza<span class="token punctuation">:</span> <span class="token keyword">print</span><span class="token punctuation">(</span>f<span class="token string">"  {t[0]}: {t[1]}  =&gt;  {t[2]} = {t[3]}"</span><span class="token punctuation">)</span>
    claves <span class="token operator">=</span> <span class="token punctuation">[</span><span class="token string">"urgencia"</span><span class="token punctuation">,</span><span class="token string">"modo"</span><span class="token punctuation">,</span><span class="token string">"origen"</span><span class="token punctuation">,</span><span class="token string">"capacidad"</span><span class="token punctuation">,</span><span class="token string">"unidad"</span><span class="token punctuation">,</span><span class="token string">"seguro"</span><span class="token punctuation">,</span><span class="token string">"escolta"</span><span class="token punctuation">,</span><span class="token string">"cumplimiento"</span><span class="token punctuation">,</span><span class="token string">"alerta"</span><span class="token punctuation">,</span><span class="token string">"reabasto"</span><span class="token punctuation">]</span>
    <span class="token keyword">print</span><span class="token punctuation">(</span><span class="token string">"DECISION:"</span><span class="token punctuation">,</span> <span class="token punctuation">{</span>k<span class="token punctuation">:</span> h<span class="token punctuation">.</span>get<span class="token punctuation">(</span>k<span class="token punctuation">)</span> <span class="token keyword">for</span> k <span class="token keyword">in</span> claves <span class="token keyword">if</span> h<span class="token punctuation">.</span>get<span class="token punctuation">(</span>k<span class="token punctuation">)</span> <span class="token keyword">is</span> <span class="token operator">not</span> <span class="token boolean">None</span><span class="token punctuation">}</span><span class="token punctuation">)</span>
    <span class="token keyword">return</span> h

<span class="token comment"># ---------------- 4. ESCENARIOS ----------------</span>
BASE <span class="token operator">=</span> <span class="token builtin">dict</span><span class="token punctuation">(</span>buen_fin<span class="token operator">=</span><span class="token boolean">False</span><span class="token punctuation">,</span> cliente<span class="token operator">=</span><span class="token string">"regular"</span><span class="token punctuation">,</span> horas_entrega<span class="token operator">=</span><span class="token number">72</span><span class="token punctuation">,</span> valor<span class="token operator">=</span><span class="token number">800</span><span class="token punctuation">,</span> hazmat<span class="token operator">=</span><span class="token boolean">False</span><span class="token punctuation">,</span> refrigerado<span class="token operator">=</span><span class="token boolean">False</span><span class="token punctuation">,</span>
            cantidad<span class="token operator">=</span><span class="token number">2</span><span class="token punctuation">,</span> stock<span class="token operator">=</span><span class="token number">500</span><span class="token punctuation">,</span> stock_regional<span class="token operator">=</span><span class="token number">2000</span><span class="token punctuation">,</span> punto_reorden<span class="token operator">=</span><span class="token number">100</span><span class="token punctuation">,</span> utilizacion_flota<span class="token operator">=</span><span class="token number">0.70</span><span class="token punctuation">,</span>
            demanda_dia<span class="token operator">=</span><span class="token number">900</span><span class="token punctuation">,</span> capacidad_dia<span class="token operator">=</span><span class="token number">1000</span><span class="token punctuation">,</span> distancia_km<span class="token operator">=</span><span class="token number">300</span><span class="token punctuation">,</span> riesgo_ruta<span class="token operator">=</span><span class="token string">"bajo"</span><span class="token punctuation">,</span>
            clima_adverso<span class="token operator">=</span><span class="token boolean">False</span><span class="token punctuation">,</span> ocupacion_unidad<span class="token operator">=</span><span class="token number">0.30</span><span class="token punctuation">)</span>

<span class="token keyword">if</span> __name__ <span class="token operator">==</span> <span class="token string">"__main__"</span><span class="token punctuation">:</span>
    mostrar<span class="token punctuation">(</span><span class="token string">"1. Operacion normal"</span><span class="token punctuation">,</span> BASE<span class="token punctuation">)</span>
    mostrar<span class="token punctuation">(</span><span class="token string">"2. El Buen Fin - urgencia alta"</span><span class="token punctuation">,</span> <span class="token punctuation">{</span><span class="token operator">**</span>BASE<span class="token punctuation">,</span> <span class="token string">"buen_fin"</span><span class="token punctuation">:</span> <span class="token boolean">True</span><span class="token punctuation">,</span> <span class="token string">"cliente"</span><span class="token punctuation">:</span> <span class="token string">"prime"</span><span class="token punctuation">,</span> <span class="token string">"horas_entrega"</span><span class="token punctuation">:</span> <span class="token number">24</span><span class="token punctuation">,</span>
            <span class="token string">"valor"</span><span class="token punctuation">:</span> <span class="token number">6000</span><span class="token punctuation">,</span> <span class="token string">"distancia_km"</span><span class="token punctuation">:</span> <span class="token number">900</span><span class="token punctuation">,</span> <span class="token string">"riesgo_ruta"</span><span class="token punctuation">:</span> <span class="token string">"alto"</span><span class="token punctuation">,</span> <span class="token string">"utilizacion_flota"</span><span class="token punctuation">:</span> <span class="token number">0.95</span><span class="token punctuation">,</span>
            <span class="token string">"demanda_dia"</span><span class="token punctuation">:</span> <span class="token number">1500</span><span class="token punctuation">,</span> <span class="token string">"stock"</span><span class="token punctuation">:</span> <span class="token number">1</span><span class="token punctuation">,</span> <span class="token string">"stock_regional"</span><span class="token punctuation">:</span> <span class="token number">300</span><span class="token punctuation">,</span> <span class="token string">"cantidad"</span><span class="token punctuation">:</span> <span class="token number">3</span><span class="token punctuation">,</span> <span class="token string">"punto_reorden"</span><span class="token punctuation">:</span> <span class="token number">50</span><span class="token punctuation">}</span><span class="token punctuation">)</span>
    mostrar<span class="token punctuation">(</span><span class="token string">"3. HAZMAT internacional"</span><span class="token punctuation">,</span> <span class="token punctuation">{</span><span class="token operator">**</span>BASE<span class="token punctuation">,</span> <span class="token string">"hazmat"</span><span class="token punctuation">:</span> <span class="token boolean">True</span><span class="token punctuation">,</span> <span class="token string">"horas_entrega"</span><span class="token punctuation">:</span> <span class="token number">24</span><span class="token punctuation">,</span> <span class="token string">"buen_fin"</span><span class="token punctuation">:</span> <span class="token boolean">True</span><span class="token punctuation">,</span>
            <span class="token string">"distancia_km"</span><span class="token punctuation">:</span> <span class="token number">1500</span><span class="token punctuation">,</span> <span class="token string">"valor"</span><span class="token punctuation">:</span> <span class="token number">2500</span><span class="token punctuation">}</span><span class="token punctuation">)</span>

</code></pre>
<h3 id="resultados-esperados">11.5 Resultados esperados</h3>
<ul>
<li><strong>Escenario 1, operación normal:</strong> reglas R10, R03, R20, R15, R24. Decisión: urgencia normal, camión consolidado, surtido desde CEDIS local, flota propia, consolidar.</li>
<li><strong>Escenario 2, El Buen Fin con urgencia alta</strong> (cliente Prime, 24 h, $6,000, 900 km, ruta de riesgo alto, flota al 95 %, stock local de 1): se disparan 11 reglas. Decisión: urgencia alta, <strong>transporte aéreo</strong>, transferencia desde CEDIS regional, transportista externo, seguro adicional, escolta, orden de compra y ventana de recolección ampliada.</li>
<li><strong>Escenario 3, HAZMAT internacional</strong> (1,500 km, 24 h): R01, R05, R06, R19, R10, R15. Decisión: urgencia alta, pero como el aéreo está prohibido, <strong>camión exprés con doble operador</strong> y documentos HAZMAT.</li>
</ul>
<p>El escenario 3 muestra por qué importa el encadenamiento: R06 agrega el hecho <code>aereo_permitido = False</code> y eso hace que R19 sustituya a R17.</p>
<h3 id="cómo-funciona-el-motor">11.6 Cómo funciona el motor</h3>
<ol>
<li>Carga los hechos del pedido.</li>
<li>Recorre las reglas <strong>en orden de prioridad</strong> (resolución de conflictos).</li>
<li>Si una regla se cumple y no se había disparado, <strong>agrega o cambia un hecho</strong>.</li>
<li>Si hubo cambios, <strong>vuelve a recorrer</strong> las reglas con los hechos nuevos.</li>
<li>Se detiene cuando una pasada completa ya no produce cambios.</li>
<li>Devuelve las decisiones y la <strong>traza</strong> (qué regla produjo qué).</li>
</ol>
<h3 id="lo-que-tú-debes-agregar">11.7 Lo que tú debes agregar</h3>
<ul>
<li><strong>Un escenario propio</strong> (por ejemplo, pedido refrigerado con clima adverso, o producto sin stock en toda la región).</li>
<li><strong>Tres reglas nuevas</strong> con identificador, descripción y prioridad, de una región o empresa que conozcas.</li>
<li><strong>Una prueba de conflicto:</strong> dos reglas que se contradigan, y cómo lo resuelves.</li>
</ul>
<h3 id="estructura-del-reporte">11.8 Estructura del reporte</h3>
<p>Sigue el formato de reporte de la actividad: portada (Universidad SABES, Centro Universitario San Felipe, materia, actividad, alumno, matrícula, fecha, docente), introducción, arquitectura (hechos, reglas, motor, diagrama), desarrollo con ejemplos de hecho y de regla, escenarios probados, análisis cognitivo (comprensión, coherencia lógica, justificación), conclusiones, bibliografía APA y anexos (código, resultados, métricas).</p>
<h3 id="qué-se-entrega-y-cómo-se-califica">11.9 Qué se entrega y cómo se califica</h3>
<p>Los criterios, si se asigna esta versión, son: <strong>pertinencia (4), cognitivo (4) y requisitos de entrega (2)</strong>. El archivo se nombra <strong>Actividad7_iniciales</strong>, de preferencia en PDF; el código va como anexo o se entrega el cuaderno de Colab, junto con el reporte de la experiencia.</p>
<blockquote>
<p>Esta versión es de práctica (retoma la actividad del cuatrimestre anterior). Si tu docente la asigna, los criterios son los de arriba; si no, <strong>entrega lo que pida la consigna de plataforma</strong> (caso de los peces, sección 7).</p>
</blockquote>
<hr>
<h2 id="referencias-formato-apa">12. Referencias (formato APA)</h2>
<p>Berzal, F. (2019). <em>Redes neuronales &amp; deep learning: Volumen 1. Entrenamiento de redes neuronales artificiales</em> (1.ª ed.). Independently published.</p>
<p>Bratko, I. (2012). <em>Prolog programming for artificial intelligence</em> (4th ed.). Addison-Wesley.</p>
<p>Jackson, P. (1998). <em>Introduction to expert systems</em> (3rd ed.). Addison-Wesley.</p>
<p>Russell, S., &amp; Norvig, P. (2021). <em>Artificial intelligence: A modern approach</em> (4th ed.). Pearson.</p>

