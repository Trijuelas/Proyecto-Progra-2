# Manual de Usuario - Sistema de Nómina

## Introducción

Bienvenido al Sistema de Nómina, una aplicación desarrollada en Java que facilita el cálculo de salarios, la generación de comprobantes en PDF y el envío automático por correo electrónico. Este manual le guiará a través de la instalación, configuración y uso del sistema.

## Requisitos del Sistema

Antes de comenzar, asegúrese de tener instalado lo siguiente:

- **Java JDK**: Versión 8 o superior.
- **IDE**: NetBeans, Eclipse o Visual Studio Code con soporte para Java.
- **Cuenta de Gmail**: Necesaria para el envío de correos electrónicos.
- **Conexión a Internet**: Requerida para enviar correos.

## Instalación

1. **Descargar el proyecto**:
   - Clone el repositorio desde GitHub:
     ```
     git clone https://github.com/Trijuelas/Proyecto-Progra-2.git
     ```

2. **Abrir en el IDE**:
   - Importe el proyecto en su IDE preferido (NetBeans recomendado).

3. **Verificar dependencias**:
   - Asegúrese de que las librerías `iText` y `JavaMail` estén presentes en la carpeta `lib/`.

4. **Compilar el proyecto**:
   - En NetBeans: Haga clic derecho en el proyecto y seleccione "Build".
   - En otros IDE: Compile el proyecto según las opciones disponibles.

## Configuración Inicial

### Configuración de Gmail

Para enviar correos electrónicos, debe configurar una contraseña de aplicación en Gmail:

1. Vaya a su cuenta de Gmail.
2. Active la verificación en dos pasos si no la tiene.
3. Genere una contraseña de aplicación:
   - Configuración > Cuentas y Importación > Otras configuraciones de Gmail > Contraseñas de aplicación.
4. Use esta contraseña en el sistema, no su contraseña principal.

## Uso del Sistema

### Iniciar la Aplicación

1. Ejecute la clase `Main.java` desde su IDE.
2. Aparecerá la ventana de login (si aplica) o directamente la interfaz principal.

### Interfaz Principal

La interfaz gráfica permite ingresar los siguientes datos:

- **Correo remitente**: Su dirección de Gmail.
- **Contraseña de aplicación**: La contraseña generada anteriormente.
- **Nombre del empleado**: Nombre completo del empleado.
- **Cédula**: Número de identificación.
- **Salario base**: Salario mensual en la moneda local.
- **Correo del empleado**: Dirección de correo donde se enviará el comprobante.

### Generar y Enviar Nómina

1. Ingrese todos los datos requeridos en los campos correspondientes.
2. Haga clic en el botón "Generar y Enviar" (o similar, dependiendo de la interfaz).
3. El sistema:
   - Calcula las deducciones y el salario neto.
   - Genera un PDF con el comprobante de nómina.
   - Envía el PDF por correo electrónico al empleado.
   - Guarda el PDF en la carpeta `documentos_pdf/`.

### Verificación

- **Archivos generados**: Verifique la carpeta `documentos_pdf/` para el PDF creado.
- **Errores**: En caso de problemas con el envío, revise el archivo `ultimo_error_envio.txt` en la carpeta `data/`.

## Funcionalidades Detalladas

### Cálculo de Nómina
- El sistema calcula deducciones automáticas basadas en leyes fiscales locales (puede requerir ajustes según el país).
- Salario neto = Salario base - Deducciones.

### Generación de PDF
- Utiliza la librería iText para crear documentos profesionales.
- Incluye información del empleado, cálculos y fecha.

### Envío de Correo
- Utiliza JavaMail API para enviar correos a través de Gmail.
- Adjunta el PDF generado.

## Solución de Problemas

### Error de Autenticación
- Verifique que la contraseña de aplicación sea correcta.
- Asegúrese de que la verificación en dos pasos esté activada.

### Error de Envío
- Compruebe la conexión a Internet.
- Revise que el correo del empleado sea válido.

### Archivos no Generados
- Verifique permisos de escritura en las carpetas `documentos_pdf/` y `data/`.
- Asegúrese de que las dependencias estén correctamente incluidas.

## Soporte

Para soporte técnico, contacte a los desarrolladores:
- RagdollC137 = Richard Tapia Navarro
- Trijuelas = Cristopher Solano Cordero
- vicramfon18-dot = Gabriel Ramírez Cordero
- breyeslopez21-byte = Brandon Reyes Lopez

## Actualizaciones

Mantenga el proyecto actualizado clonando los últimos cambios desde el repositorio GitHub.

---

Este manual está sujeto a cambios. Consulte el README.md para información adicional.