# 📱 MyComunidadDefEdition

### Proyecto de Desarrollo de Aplicaciones Móviles

**Asignatura:** DSY1105 – Desarrollo de Aplicaciones Móviles  
**Entorno de desarrollo:** Android Studio  
**Lenguaje:** Kotlin  
**Repositorio:** [MyComunidadDefEdition en GitHub](https://github.com/franbadilla-ux/MyComunidadDefEdition)

## 📌 ¿De qué trata nuestro proyecto?

MyComunidadDefEdition es una aplicación móvil que estamos desarrollando como parte de la asignatura Desarrollo de Aplicaciones Móviles.

El proyecto está enfocado en la gestión de oportunidades y activaciones corporativas. Su propósito es facilitar el registro, la consulta y la modificación de oportunidades desde un dispositivo Android, utilizando una interfaz sencilla y organizada.

Durante su desarrollo hemos trabajado con Kotlin, Jetpack Compose y distintas herramientas de Android Studio. También hemos incorporado una estructura basada en la arquitectura MVVM, que nos permite separar la interfaz de usuario, la lógica de la aplicación y el manejo de los datos.

Este proyecto representa nuestro avance durante las evaluaciones de la asignatura. A medida que hemos aprendido nuevas herramientas, hemos ido agregando funcionalidades y revisando aspectos que todavía necesitan mejoras.

## 👥 Integrantes del equipo

- **Integrante 1: GUSTAVO ADOLFO MARCHANT VERGARA
- **Integrante 2: FRANCO ALBERTO BADILLA CONCHA

El proyecto se desarrolla de manera colaborativa, utilizando GitHub para mantener un registro de los cambios y organizar las distintas versiones del código.

## 🎯 Objetivo de la aplicación

Nuestro objetivo es desarrollar una aplicación Android que permita gestionar oportunidades de forma más ordenada, facilitando el ingreso y la consulta de información.

Buscamos que el usuario pueda registrar nuevas oportunidades, revisar las existentes y modificar sus datos cuando sea necesario.

Además, queremos aplicar correctamente los conocimientos adquiridos en clases, especialmente en navegación, formularios, validaciones, gestión de estado, almacenamiento local y utilización de recursos propios del dispositivo móvil.

## 🛠️ Tecnologías utilizadas

Para desarrollar la aplicación estamos trabajando con las siguientes tecnologías:

| Tecnología | ¿Para qué la utilizamos? |
|---|---|
| Kotlin | Para programar las funcionalidades de la aplicación. |
| Android Studio | Para desarrollar, organizar, ejecutar y probar el proyecto. |
| Jetpack Compose | Para construir las pantallas y sus componentes visuales. |
| Material 3 | Para mantener un diseño consistente en la interfaz. |
| Navigation Compose | Para organizar la navegación entre pantallas. |
| ViewModel | Para manejar la lógica y el estado de la interfaz. |
| StateFlow y UiState | Para representar los datos y reflejar sus cambios en pantalla. |
| Room | Para trabajar con una base de datos local. |
| Gradle | Para administrar las dependencias y la compilación. |
| Git y GitHub | Para guardar versiones del proyecto y trabajar en equipo. |

También estamos trabajando con la integración de la cámara para la lectura de códigos QR y con la ubicación del dispositivo mediante GPS. Su funcionamiento debe comprobarse en las pruebas finales.

## 📲 Funcionalidades de la aplicación

### 1. Inicio de sesión y navegación

La aplicación contempla una pantalla de inicio de sesión y un menú desde el cual se puede acceder a las diferentes secciones.

El menú está desarrollado mediante un componente de navegación lateral, aunque todavía es necesario revisar algunas opciones para que todas tengan una función real.

Nuestro objetivo es que el usuario pueda desplazarse entre las pantallas de forma clara, sin encontrarse con botones que no realicen ninguna acción.

### 2. Gestión de oportunidades

Esta es una de las partes principales de MyComunidadDefEdition.

La aplicación está orientada a permitir que el usuario registre nuevas oportunidades y consulte la información almacenada.

Entre los datos que se consideran están:

- Nombre de la oportunidad.
- Tipo de oportunidad.
- Descripción.
- Fuente de información.
- Prioridad.
- Potencial comercial.
- Estado de gestión.
- Observaciones.

También se contempla la edición de oportunidades existentes, de manera que la información pueda actualizarse sin necesidad de crear un nuevo registro.

### 3. Formularios y validaciones

Para registrar oportunidades estamos utilizando formularios desarrollados con Jetpack Compose.

Una parte importante del proyecto es validar la información ingresada. Por ejemplo, si un usuario intenta guardar una oportunidad sin completar un campo obligatorio, la aplicación debe mostrar un mensaje que indique qué dato falta.

La idea es que estas validaciones se controlen desde la lógica de la aplicación y no solamente desde la pantalla.

También tenemos pendiente mejorar algunos detalles visuales del formulario, como los íconos y la selección del estado de gestión.

### 4. Almacenamiento local con Room

Estamos utilizando Room para manejar los datos que se guardan dentro de la aplicación.

Su función es permitir que las oportunidades registradas puedan recuperarse posteriormente, sin depender de una lista temporal que desaparezca al cerrar la aplicación.

Para organizar esta parte del proyecto trabajamos con componentes como DAO, Repository y ViewModel.

Todavía necesitamos completar las pruebas para demostrar que los registros permanecen guardados después de cerrar y volver a abrir la aplicación.

### 5. Lectura de códigos QR

Otra funcionalidad contemplada en el proyecto es la lectura de códigos QR mediante la cámara del dispositivo.

La idea es que un código QR pueda contener una referencia a una oportunidad registrada, permitiendo identificarla o consultarla.

Actualmente necesitamos mejorar la comprobación del identificador leído. Antes de abrir una oportunidad, la aplicación debería revisar si realmente existe dentro de la base de datos.

De esta manera evitamos intentar mostrar registros inexistentes.

### 6. Ubicación del dispositivo

Estamos trabajando en la integración del GPS como un recurso nativo adicional.

Esta funcionalidad permitiría obtener una referencia de la ubicación del dispositivo, aplicando los permisos necesarios de Android.

Aunque ya se identificó código relacionado con este recurso, todavía debemos comprobar su funcionamiento en un emulador o celular físico.

### 7. Registro y resumen de usuarios

El proyecto también contempla pantallas relacionadas con el registro y la visualización de información de usuarios.

Una de las mejoras que tenemos consideradas es recuperar la información del último usuario desde Room, para que el resumen no dependa únicamente de los datos mantenidos temporalmente en la interfaz.

## 🧩 ¿Cómo organizamos el código?

Para mantener el proyecto ordenado estamos aplicando la arquitectura **MVVM (Model–View–ViewModel)**.

Esta arquitectura nos ayuda a dividir las responsabilidades del código y facilita realizar cambios sin afectar directamente todas las pantallas.

La organización se basa en cuatro partes principales:

**View:** corresponde a las pantallas creadas con Jetpack Compose. Es lo que el usuario ve y utiliza.

**ViewModel:** administra el estado de las pantallas y la lógica relacionada con las acciones del usuario.

**Repository:** conecta la lógica de la aplicación con las operaciones necesarias para obtener o guardar información.

**DAO y Room:** permiten realizar consultas y operaciones sobre la base de datos local.

Algunos de los archivos que forman parte de esta organización son:

- `OportunidadFormScreen.kt`: formulario para registrar o modificar oportunidades.
- `OportunidadDao.kt`: acceso a los datos de oportunidades.
- `QrRepository.kt`: operaciones relacionadas con los registros consultados mediante QR.
- `QrScannerScreen.kt`: pantalla destinada a la lectura de códigos QR.
- `DrawerMenu.kt`: componente del menú de navegación.
- `ResumenScreen.kt`: pantalla de resumen.
- `UsuarioViewModel.kt`: manejo de información y estado relacionado con usuarios.

Esta separación nos permite trabajar cada funcionalidad de manera más organizada y facilita identificar errores durante el desarrollo.

## 💻 ¿Cómo ejecutar el proyecto en Android Studio?

Para abrir y probar MyComunidadDefEdition, se pueden seguir estos pasos.

**1. Descargar o clonar el repositorio**

Ingresar al repositorio de GitHub:

https://github.com/franbadilla-ux/MyComunidadDefEdition

También se puede clonar desde una terminal con el siguiente comando:

```bash
git clone https://github.com/franbadilla-ux/MyComunidadDefEdition.git
```

**2. Abrir el proyecto**

Iniciar Android Studio y seleccionar la opción **Open**.

Luego buscar la carpeta donde se descargó o clonó MyComunidadDefEdition.

**3. Sincronizar Gradle**

Esperar a que Android Studio reconozca el proyecto y descargue las dependencias necesarias.

Si aparece algún error de sincronización, revisar la configuración de Gradle y del SDK de Android.

**4. Seleccionar un dispositivo**

Se puede utilizar un emulador configurado en Android Studio o un celular Android conectado por USB.

Para probar funcionalidades como la cámara y el GPS, es recomendable utilizar un dispositivo que tenga disponibles esos recursos.

**5. Ejecutar la aplicación**

Seleccionar la configuración `app` y presionar el botón **Run ▶**.

Una vez instalada, revisar la navegación y comprobar que las pantallas principales funcionen correctamente.

## 🔍 Pruebas y aspectos que estamos revisando

Como este proyecto forma parte de un proceso de aprendizaje, también hemos identificado algunos puntos que necesitamos terminar o comprobar.

| Área | ¿Qué necesitamos revisar? |
|---|---|
| Compilación | Confirmar que el proyecto sincronice, compile y se ejecute sin errores. |
| Navegación | Completar las opciones del menú que todavía no tienen una acción. |
| Formularios | Mejorar íconos, validaciones y selección del estado de gestión. |
| Base de datos | Comprobar que los registros permanezcan guardados al reiniciar la aplicación. |
| Cámara y QR | Verificar que el código leído corresponda a una oportunidad existente. |
| GPS | Probar la obtención de ubicación en un celular o emulador. |
| Animaciones | Agregar transiciones o efectos visuales que mejoren la interacción. |
| Resumen de usuarios | Recuperar los datos desde Room. |
| Pruebas automatizadas | Incorporar pruebas de validaciones, consultas y persistencia. |

Estos puntos forman parte de las mejoras que estamos considerando para dejar el proyecto más completo y estable.

Nuestra intención es comprobar cada funcionalidad en Android Studio y respaldar su funcionamiento con evidencias reales.

## 🎨 Mejoras visuales y experiencia de usuario

Además del funcionamiento interno, nos interesa que la aplicación sea fácil de utilizar.

Por eso estamos revisando la organización de los formularios, los mensajes de error, los íconos y la navegación.

También queremos incorporar pequeñas animaciones mediante Jetpack Compose, por ejemplo, al cambiar de pantalla o al mostrar una confirmación.

No buscamos agregar animaciones solamente por apariencia, sino utilizarlas para que las acciones del usuario tengan una respuesta visual más clara.

## 🧪 Pruebas de funcionamiento

Antes de presentar la versión final, debemos comprobar el funcionamiento de los principales componentes.

Las pruebas consideradas incluyen:

1. Abrir la aplicación y recorrer las pantallas principales.
2. Intentar guardar formularios con campos vacíos y comprobar los mensajes de error.
3. Registrar una oportunidad con datos válidos.
4. Consultar y modificar una oportunidad existente.
5. Cerrar y volver a abrir la aplicación para revisar los datos almacenados.
6. Escanear un código QR y comprobar la búsqueda del registro.
7. Probar la ubicación GPS con los permisos correspondientes.
8. Revisar que el menú, las animaciones y los demás componentes respondan correctamente.

Estas pruebas nos permitirán identificar errores y demostrar cuáles son las funcionalidades que realmente están operativas.

## 🤝 Trabajo en equipo y control de versiones

Para desarrollar MyComunidadDefEdition utilizamos GitHub como herramienta de control de versiones.

Esto nos permite mantener un historial de los cambios, revisar las modificaciones realizadas y compartir el proyecto entre los integrantes.

También debemos mantener una planificación de tareas mediante Trello o una herramienta similar, indicando qué actividades le corresponden a cada integrante.

**Tablero de planificación:** [Agregar enlace de Trello]

Buscamos que los commits tengan descripciones claras de los cambios realizados, para que sea más fácil comprender cómo ha avanzado el proyecto.

## 📚 Desarrollo del proyecto y evaluaciones

MyComunidadDefEdition se ha ido construyendo durante las distintas etapas de la asignatura.

En la primera etapa nos enfocamos principalmente en la estructura del proyecto, la arquitectura MVVM, la navegación, los formularios y el almacenamiento de oportunidades.

En la segunda evaluación se incorporaron requisitos relacionados con el diseño de la interfaz, las validaciones, las animaciones, el trabajo colaborativo y el acceso a recursos nativos del dispositivo.

Para la presentación y defensa del Examen 3, nuestro objetivo es demostrar el avance del proyecto, explicar cómo se relacionan sus componentes y presentar pruebas de las funcionalidades desarrolladas.

También queremos poder explicar las dificultades que encontramos, cómo las abordamos y qué aspectos todavía requieren mejoras.

## 📸 Evidencias del desarrollo

Como respaldo del trabajo, debemos preparar capturas de pantalla del proyecto y de su ejecución.

Además, el informe de desarrollo debe incluir al menos ocho hitos relevantes, cada uno acompañado de una descripción, evidencia visual y resultados de las pruebas realizadas.

Entre los hitos que podemos documentar están:

1. Creación y organización del proyecto en Android Studio.
2. Implementación de la arquitectura MVVM.
3. Configuración de la navegación entre pantallas.
4. Desarrollo del listado de oportunidades.
5. Creación de formularios y validaciones.
6. Gestión de estado mediante ViewModel y UiState.
7. Integración de Room para almacenar información.
8. Pruebas de la
## Gestión y planificación del proyecto

La aplicación fue desarrollada en equipo,
utilizando GitHub para el control de versiones
y Trello para la organización de tareas.

### Herramientas colaborativas

- GitHub: repositorio del proyecto.
- Trello: planificación y seguimiento de avances.

Tablero Trello:
https://trello.com/invite/b/6abc1773019ff624efbb89a9/ATTI04879d6be7c2ac6430dff79c2eb1b1740DB442D1/mycomunidad

### Distribución del trabajo

Las tareas se asignaron entre los integrantes
y se organizaron por estado de desarrollo.

Cada avance importante se relaciona con
commits de GitHub y evidencias de ejecución.
