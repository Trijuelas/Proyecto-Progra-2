# Changelog

Historial de cambios del sistema de nomina desarrollado en Java.

> Nota: el repositorio no tenia etiquetas (`tags`) de version al momento de crear este archivo. Las versiones se reconstruyeron a partir del historial de commits y de las mejoras visibles en el proyecto.

## Version 1.7.0 - 2026-04-22

### Resumen

Nueva version enfocada en mejorar la presentacion profesional del comprobante PDF y ajustar las deducciones salariales a reglas mas cercanas a Costa Rica.

### Mejoras

- Se rediseno el comprobante PDF para que tenga un aspecto mas profesional al recibirse por correo.
- Se agrego encabezado con fecha de emision, bloques visuales, tabla de empleado, tabla de nomina y total a recibir destacado.
- Se desglosaron las deducciones en el PDF para que el empleado pueda ver cada rebajo aplicado.
- Se agrego calculo separado de:
  - CCSS - Seguro de Enfermedad y Maternidad (SEM): 5.50%.
  - CCSS - Invalidez, Vejez y Muerte (IVM): 4.33%.
  - Banco Popular - aporte trabajador: 1.00%.
  - Impuesto sobre la renta salarial por tramos 2026.
- Se sustituyo el calculo anterior de renta fija por un calculo progresivo segun salario bruto.
- Se agrego una nota aclaratoria en el PDF indicando que no se incluyen creditos fiscales por hijos o conyuge, porque el formulario actual no solicita esos datos.
- Se corrigio un metodo auxiliar faltante en `FrmNomina.java` para que el proyecto compile correctamente.

### Detalle de renta salarial 2026

- Hasta CRC 918,000 mensuales: exento.
- Sobre el exceso de CRC 918,000 y hasta CRC 1,347,000: 10%.
- Sobre el exceso de CRC 1,347,000 y hasta CRC 2,364,000: 15%.
- Sobre el exceso de CRC 2,364,000 y hasta CRC 4,727,000: 20%.
- Sobre el exceso de CRC 4,727,000: 25%.

### Archivos principales modificados

- `src/proyecto2/LogicaNegocio/GeneradorPDF.java`
- `src/proyecto2/LogicaNegocio/logicaBase.java`
- `src/proyecto2/LogicaNegocio/CalculoNomina.java`
- `src/proyecto2/Entidades/Nomina.java`
- `src/proyecto2/presentacion/FrmNomina.java`

## Version 1.6.0 - 2026-04-22

### Resumen

Version enfocada en ajustes visuales y refinamiento de la interfaz.

### Mejoras

- Se aplicaron cambios ligeros en la interfaz para mejorar la presentacion general.
- Se mantuvo el flujo de generacion de nomina, PDF y envio por correo.
- Se reforzo la experiencia del usuario al interactuar con el modulo de nomina.

## Version 1.5.0 - 2026-04-22

### Resumen

Version enfocada en manejo de errores y soporte para diagnostico.

### Mejoras

- Se mejoro el manejo de errores durante el proceso de envio de correo.
- Se agregaron mensajes mas claros para fallos comunes.
- Se agrego soporte para registrar detalles tecnicos de errores en archivos locales.
- Se fortalecio el flujo cuando falla la autenticacion, conexion o entrega del correo.

## Version 1.4.0 - 2026-04-22

### Resumen

Version enfocada en mejorar la generacion del comprobante PDF.

### Mejoras

- Se incorporaron mejoras iniciales al PDF de nomina.
- Se mantuvo el almacenamiento de documentos en la carpeta `documentos_pdf`.
- Se preparo la base para que el comprobante pudiera adjuntarse al correo del empleado.

## Version 1.3.0 - 2026-04-22

### Resumen

Version de documentacion y entrega academica.

### Mejoras

- Se agrego el manual de usuario del sistema.
- Se documento el funcionamiento general del proyecto.
- Se explicaron los requisitos, flujo de uso y archivos generados.
- Se fortalecio la documentacion para facilitar instalacion, revision y mantenimiento.

## Version 1.2.0 - 2026-04-21

### Resumen

Version enfocada en robustecer el acceso a archivos y la resolucion de rutas.

### Mejoras

- Se amplio la utilidad de acceso a archivos.
- Se robustecio `ArchivoTexto` para trabajar con rutas del proyecto.
- Se preciso el contrato de la interfaz `IArchivo`.
- Se centralizo la resolucion de rutas para evitar problemas al ejecutar desde NetBeans o desde consola.

## Version 1.1.0 - 2026-04-20 / 2026-04-21

### Resumen

Version enfocada en modernizar la experiencia de usuario y ordenar la logica interna.

### Mejoras

- Se modernizo la presentacion visual de las pantallas de login y nomina.
- Se agregaron validaciones visuales en formularios.
- Se optimizo la experiencia de uso con mensajes, ayudas y estados visibles.
- Se mejoro la compatibilidad con Windows.
- Se centralizaron constantes relacionadas con SMTP.
- Se limpiaron encabezados generados por plantillas del IDE.

## Version 1.0.0 - 2026-04-20

### Resumen

Primera version funcional del proyecto.

### Funcionalidades iniciales

- Proyecto base en Java con estructura por capas.
- Interfaz grafica para login y gestion de nomina.
- Entidades principales `Empleado` y `Nomina`.
- Calculo basico de salario bruto, deducciones y salario neto.
- Generacion de comprobantes PDF con iText.
- Envio de comprobantes por correo usando JavaMail.
- Persistencia de registros en archivos de texto.
- Carpetas de soporte para `data`, `documentos_pdf` y `lib`.
