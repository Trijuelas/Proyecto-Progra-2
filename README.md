# Proyecto-Progra-2

## Descripcion

Este proyecto es una aplicacion de escritorio en Java orientada a la gestion de nomina. Su objetivo es capturar la informacion de un empleado, calcular la nomina correspondiente, generar un comprobante en PDF y enviarlo por correo electronico.

El sistema esta organizado por capas para mantener separadas la interfaz grafica, la logica del negocio, las entidades y el acceso a datos. Esto facilita el mantenimiento del proyecto y permite mejorar la presentacion sin afectar el resto de la aplicacion.

## Que hace el sistema

- Permite ingresar los datos del remitente que enviara el correo.
- Permite registrar los datos basicos del empleado.
- Calcula salario bruto, deducciones y salario neto.
- Genera un comprobante de nomina en formato PDF.
- Guarda registros de salida en archivos de texto.
- Envia el PDF al correo del empleado usando Gmail.

## Estructura del proyecto

- `src/proyecto2/presentacion`: contiene la interfaz grafica.
- `src/proyecto2/LogicaNegocio`: contiene el calculo de nomina, generacion de PDF, validaciones y envio de correo.
- `src/proyecto2/Entidades`: contiene las clases del dominio como `Empleado` y `Nomina`.
- `src/proyecto2/AccesoDatos`: maneja la lectura y escritura en archivos de texto.
- `src/proyecto2/Excepciones`: agrupa excepciones personalizadas.
- `data`: guarda registros como nominas generadas y errores tecnicos.
- `documentos_pdf`: almacena los comprobantes PDF generados.
- `lib`: incluye las librerias externas necesarias para PDF y correo.

## Flujo de funcionamiento

1. El usuario abre la interfaz del sistema.
2. Ingresa el correo remitente de Gmail y su contrasena de aplicacion.
3. Completa nombre, cedula, salario base y correo del empleado.
4. El sistema valida visualmente los campos antes de procesar.
5. Se calcula la nomina del empleado.
6. Se genera un archivo PDF con el comprobante.
7. Se registra la operacion en archivos dentro de `data`.
8. Se envia el comprobante por correo al destinatario.

## Pantallas principales

### Login

La ventana de login ofrece una entrada inicial al sistema con una interfaz mas moderna, validaciones visuales y mejor experiencia de uso. Incluye mensajes de ayuda, estados por campo y controles para mostrar u ocultar la contrasena.

### Nomina

La ventana principal de nomina concentra el flujo completo del sistema:

- captura de remitente
- captura de datos del empleado
- validacion visual en tiempo real
- generacion del PDF
- registro del resultado
- envio por correo

## Mejoras recientes en la capa de Presentacion

Se realizaron mejoras progresivas en tres commits separados:

1. Modernizacion visual de las pantallas de login y nomina.
2. Incorporacion de validaciones visuales por campo.
3. Mejora de experiencia de usuario con foco inicial, boton por defecto, tooltips, estados de procesamiento y controles mas comodos.

## Requisitos

- Java JDK 11 o superior.
- NetBeans o un entorno compatible con proyectos Java Ant.
- Conexion a Internet para el envio de correos.
- Una cuenta de Gmail con contrasena de aplicacion para el remitente.

## Librerias utilizadas

- `itextpdf-5.4.0.jar`: generacion de archivos PDF.
- `mail.jar`: envio de correos electronicos.
- `activation.jar`: soporte para adjuntos y manejo de datos.

## Como ejecutar el proyecto

### Desde NetBeans

1. Abrir el proyecto en NetBeans.
2. Verificar que las librerias de `lib` esten correctamente enlazadas.
3. Ejecutar la clase principal configurada en el proyecto.

### Desde compilacion manual

1. Compilar el contenido de `src` incluyendo las librerias de `lib` en el classpath.
2. Ejecutar la clase principal `proyecto2.main.Main`.

## Archivos generados por el sistema

- `data/nominas_registradas.txt`: historial basico de nominas procesadas.
- `data/ultimo_error_envio.txt`: detalle tecnico del ultimo error ocurrido.
- `documentos_pdf/*.pdf`: comprobantes generados para cada empleado.

## Consideraciones importantes

- Para enviar correos con Gmail se debe usar contrasena de aplicacion, no la contrasena normal de la cuenta.
- El sistema fue ajustado para funcionar correctamente en Windows.
- La arquitectura por capas permite seguir mejorando la interfaz sin alterar la logica del negocio.

## Estado actual

El proyecto compila correctamente y cuenta con mejoras recientes en la interfaz grafica, validaciones visuales y experiencia de usuario.
