## Proyecto-Progra-2

# Descripción

Este proyecto es una aplicación de escritorio desarrollada en Java, orientada a la gestión de nómina de empleados. Su propósito es automatizar el proceso de cálculo salarial, generación de comprobantes y envío de estos documentos por correo electrónico.

La aplicación sigue una arquitectura por capas, lo que permite separar responsabilidades entre la interfaz gráfica, la lógica de negocio, las entidades del sistema y el acceso a datos. Este enfoque facilita el mantenimiento, la escalabilidad y futuras mejoras sin afectar el funcionamiento general.

#Objetivo del sistema

El sistema busca simplificar el proceso de cálculo de nómina y entrega de comprobantes, reduciendo errores manuales y automatizando tareas repetitivas como:

Cálculo de salarios
Generación de documentos
Envío de correos electrónicos
Funcionalidades principales
Ingreso de credenciales del remitente (Gmail)
Registro de datos del empleado
Validación de datos en tiempo real
Cálculo automático de:
Salario bruto
Deducciones
Salario neto
Generación de comprobantes en PDF
Registro de operaciones en archivos de texto
Envío automático del comprobante al correo del empleado
Arquitectura del sistema

El proyecto está estructurado bajo un enfoque modular por capas:

Presentación (presentacion)
Maneja la interfaz gráfica (GUI) y la interacción con el usuario.
Lógica de Negocio (LogicaNegocio)
Contiene el procesamiento principal del sistema:
Cálculo de nómina
Generación de PDF
Validaciones
Envío de correos
Entidades (Entidades)
Define los modelos del sistema como:
Empleado
Nomina
Acceso a Datos (AccesoDatos)
Gestiona la persistencia en archivos de texto.
Excepciones (Excepciones)
Maneja errores personalizados del sistema.
Estructura del proyecto
src/
 └── proyecto2/
     ├── presentacion/
     ├── LogicaNegocio/
     ├── Entidades/
     ├── AccesoDatos/
     └── Excepciones/

data/
documentos_pdf/
lib/
Flujo de funcionamiento
El usuario inicia la aplicación.
Ingresa el correo remitente y la contraseña de aplicación.
Introduce los datos del empleado.
El sistema valida la información ingresada.
Se realiza el cálculo de la nómina.
Se genera un comprobante en PDF.
Se guarda un registro en archivos locales.
Se envía el comprobante por correo electrónico.
Interfaz de usuario
Login
Entrada segura de credenciales
Validaciones visuales
Opción de mostrar/ocultar contraseña
Mensajes de ayuda al usuario
Módulo de Nómina
Formulario completo de datos
Validación en tiempo real
Generación de PDF
Envío de correo
Retroalimentación visual del proceso
Mejoras recientes

El desarrollo incluyó mejoras progresivas distribuidas en commits:

Rediseño visual de la interfaz
Validaciones dinámicas por campo
Mejora en la experiencia de usuario:
Foco automático
Botones por defecto
Tooltips informativos
Indicadores de estado
Requisitos del sistema
Java JDK 11 o superior
NetBeans (o IDE compatible con proyectos Ant)
Conexión a Internet
Cuenta de Gmail con contraseña de aplicación
Librerías utilizadas
itextpdf-5.4.0.jar → Generación de archivos PDF
mail.jar → Envío de correos electrónicos
activation.jar → Manejo de adjuntos
Ejecución del proyecto
Desde NetBeans
Abrir el proyecto
Verificar librerías en lib
Ejecutar la clase principal
Desde consola
Compilar el proyecto incluyendo lib en el classpath
# Ejecutar:
proyecto2.main.Main
Archivos generados
data/nominas_registradas.txt → Historial de operaciones
data/ultimo_error_envio.txt → Registro de errores
documentos_pdf/ → Comprobantes generados
Seguridad y consideraciones
Se utiliza contraseña de aplicación de Gmail (no la contraseña real)
Los datos no se almacenan en base de datos, sino en archivos locales
Se recomienda no compartir credenciales en entornos públicos
Posibles mejoras futuras
Integración con base de datos (MySQL o PostgreSQL)
Exportación a otros formatos (Excel)
Soporte multiusuario
Mejoras en seguridad (cifrado de credenciales)
Interfaz más moderna con JavaFX

RagdollC137 = Richard Tapia Navarro
Trijuelas = Cristopher Solano Cordero
breyeslopez21-byte = Brandon Reyes Lopez
vicramfon18-dot = Gabriel Ramírez cordero