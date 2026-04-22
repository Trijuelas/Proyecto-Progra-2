## Sistema de Nómina
# Descripción

Este proyecto consiste en un sistema de nómina desarrollado en Java que permite calcular el salario de un empleado, generar un comprobante en PDF y enviarlo automáticamente por correo electrónico.

El sistema integra múltiples funcionalidades en una sola interfaz gráfica, facilitando el ingreso de datos y la automatización del proceso de generación y envío de comprobantes.

 Funcionalidades principales
 Cálculo de nómina
Cálculo de deducciones del salario
Obtención del salario neto del empleado
Procesamiento basado en salario base ingresado
 Generación de PDF
Creación automática de comprobantes de nómina
Almacenamiento en la carpeta documentos_pdf
Uso de la librería iText para generación de documentos
 Envío por correo
Envío automático del comprobante PDF
Uso de correo Gmail mediante autenticación
Adjunta el archivo generado al correo del empleado
 Interfaz de usuario

El sistema cuenta con una interfaz gráfica donde se ingresan los siguientes datos:

Correo remitente (Gmail)
Contraseña de aplicación
Nombre del empleado
Cédula
Salario base
Correo del empleado

Desde esta pantalla se puede generar y enviar el comprobante con un solo botón.

 Tecnologías utilizadas
Java
Java Swing (Interfaz gráfica)
iText (Generación de PDF)
JavaMail API (Envío de correos)
Git & GitHub
 Estructura del proyecto
src/
 ├── AccesoDatos/
 ├── Entidades/
 │    ├── Empleado.java
 │    └── Nomina.java
 ├── Excepciones/
 ├── LogicaNegocio/
 │    ├── CalculoNomina.java
 │    ├── GeneradorPDF.java
 │    ├── MailService.java
 │    └── Correo.java
 ├── main/
 │    └── Main.java
 └── presentacion/
      ├── FrmLogin.java
      └── FrmNomina.java

# Cómo ejecutar el proyecto

Clonar el repositorio:

git clone https://github.com/Trijuelas/Proyecto-Progra-2.git
Abrir el proyecto en NetBeans o Visual Studio Code

Ejecutar la clase principal:

Main.java

# Requisitos importantes
Contar con una cuenta de Gmail
Usar una contraseña de aplicación (no la contraseña normal)
Tener conexión a internet para el envío de correos

RagdollC137 = Richard Tapia Navarro