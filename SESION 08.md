<h1 id="inteligencia-artificial-—-contenido-temático-de-la-sesión-8-semana-8">Inteligencia Artificial — Contenido temático de la Sesión 8 (Semana 8)</h1>
<p><strong>Tema (programa oficial AU-20-101 V0.2, Unidad IV):</strong> 4.3 Arquitectura de un sistema de producción (4.3.1 Hechos; 4.3.2 Base de conocimientos; 4.3.3 Mecanismos de control); 4.4 Ciclo de vida de un sistema de producción <strong>Fecha:</strong> 27 de octubre al 3 de noviembre de 2026 (sesión presencial: sábado 31 de octubre) <strong>Actividad 7 oficial:</strong> Diseño de la arquitectura de producción: modelar en <strong>Prolog</strong> el sistema de reglas R1 a R10 del recurso de plataforma (Capítulo 4, p. 190) y entregar el archivo de Prolog más un <strong>reporte de la experiencia</strong> (<strong>10 puntos</strong>). Abrió el 27 de octubre (00:07) y cierra el <strong>miércoles 4 de noviembre a las 06:59</strong>; entrega recomendada: a más tardar la noche del <strong>3 de noviembre</strong>. <strong>Propósito de la sesión:</strong> Que el estudiante comprenda los componentes de un sistema de producción, el funcionamiento de su motor de inferencia y su ciclo de vida, y <strong>diseñe en el aula la arquitectura</strong> de un sistema para un problema propio, que entregará en la Actividad 7.</p>
<blockquote>
<p><strong>Esta sesión continúa la Sesión 7:</strong> allí se escribieron las reglas; hoy se organiza el sistema completo que las usa y se <strong>resuelve paso a paso el caso de la Actividad 7</strong>.</p>
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
<li>Caso de la Actividad 7: clasificación de peces con reglas de producción en Prolog</li>
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
<h3 id="nota-sobre-la-versión-práctica">7.10 Nota sobre la versión práctica</h3>
<p>Este caso es la <strong>actividad original</strong> del curso. Para reforzar el aprendizaje se plantea una <strong>versión más práctica</strong> con un problema real de logística (por ejemplo, clasificar pedidos o asignar andenes con reglas en Prolog), con la misma arquitectura y el mismo flujo de trabajo. Si tu docente la publica, <strong>sigue la consigna vigente en plataforma</strong>.</p>
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
<h2 id="referencias-formato-apa">11. Referencias (formato APA)</h2>
<p>Berzal, F. (2019). <em>Redes neuronales &amp; deep learning: Volumen 1. Entrenamiento de redes neuronales artificiales</em> (1.ª ed.). Independently published.</p>
<p>Bratko, I. (2012). <em>Prolog programming for artificial intelligence</em> (4th ed.). Addison-Wesley.</p>
<p>Jackson, P. (1998). <em>Introduction to expert systems</em> (3rd ed.). Addison-Wesley.</p>
<p>Russell, S., &amp; Norvig, P. (2021). <em>Artificial intelligence: A modern approach</em> (4th ed.). Pearson.</p>
<blockquote>
<p>Written with <a href="https://stackedit.io/">StackEdit</a>.</p>
</blockquote>

