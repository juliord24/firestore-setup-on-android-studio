  
# Como usar Firebase en Android  
  
## Crear un proyecto en Firebase  
Lo primero es crear un proyecto en Firebase, para ello debemos ir a la [consola](https://console.firebase.google.com/) de Firebase y dar clic en el botón de "Agregar proyecto". Luego simplemente seguimos los pasos.  
![[Pasted image 20240219205233.png]]

Cuando lo tengamos creado debemos agregar una app Android
![[Pasted image 20240219205340.png]]
Si queremos usar el servicio de Authentication de Firebase tendremos que introducir la firma SHA-1 de la aplicación
![[Pasted image 20240219205501.png]]
Podéis encontrar cómo en este [vídeo](https://youtu.be/PkvW5WoUonQ?t=506)

Resumen:
- Copiar este comando en una cmd
```
  keytool -list -v -alias androiddebugkey -keystore
  %USERPROFILE%\.android\debug.keystore
```
- La contraseña es "android"
- Veréis algo como esto
![[Pasted image 20240219210431.png]]
Después de esto seguimos los pasos y ya tendríamos listo el proyecto en Firebase


## Firestore Database

### Setup de la base de datos
Para empezar a usar Firestore Database:
- Lo seleccionamos en Compilación>Firestore Database
- Creamos la base de datos
- Elegimos como ubicación eur3(Europe).
- Comenzamos en modo de prueba para poder guardar información en la base de datos

Ya tenemos lista nuestra base de datos, vamos a ver como funciona.

Antes de nada debemos de entender que Firestore no es una base de datos SQL como las que hemos utilizado hasta ahora.
- En Firestore guardamos la información en documentos que a su vez contienen campos(fields) en los que se almacena la información
- Estos documentos se agrupan en colecciones.

Si lo comparamos con una base de datos SQL podríamos pensar que:
- Cada colección es una "base de datos"
- Cada documento es una "tabla"
- Cada campo es un "registro"

Esta comparación es solo para que podamos comprenderlo mejor y no es del todo precisa.
[Aquí](https://firebase.google.com/docs/firestore?authuser=0#how_does_it_work) podéis encontrar más información acerca de cómo funciona Firestore.

### Setup de Android Studio
Si al añadir nuestra aplicación android al proyecto de Firebase hemos omitido algún paso aquí entro en detalle de cómo completarlos

#### Archivo de configuración de Firebase

- Hacemos click en el engranaje de la esquina superior izquierda en la consola de Firebase y seleccionamos configuración del proyecto

![[Pasted image 20240219212516.png]]
- Navegamos hasta "Tus apps" y descargamos el archivo "google-services.json"
![[Pasted image 20240219212811.png]]
- Añadimos el archivo a la carpeta app de nuestra __aplicación__(es importante que sea en esta ruta y no otra)
![[Pasted image 20240219213708.png]]
- En el archivo _build.gradle.kts_ a nivel de __proyecto__ añadimos el plugin de servicios de google como dependencia
```
 id("com.google.gms.google-services") version "4.4.1" apply false
```
![[Pasted image 20240219214221.png]]
- En el archivo _build.gradle.kts_ a nivel de __aplicación__ añadimos el plugin de servicios de google
```
id("com.google.gms.google-services")
```
![[Pasted image 20240219214704.png]]
- En el archivo _build.gradle.kts_ a nivel de __aplicación__(el mismo de antes) añadimos las dependencias de los productos de firebase que vamos a usar, en este caso Firestore y Authentication, también añadiremos BoM para no tener que especificar la versión de la librería que vamos a usar
```
implementation(platform("com.google.firebase:firebase-bom:32.7.2")) implementation("com.google.firebase:firebase-firestore")
implementation("com.google.firebase:firebase-auth")
```
![[Pasted image 20240219215650.png]]

Con esto ya hemos terminado la parte de configuración, empezamos a picar!!

A partir de ahora la documentación estará en el código.

PD: después de haber modificado los archivos Gradle debemos sincronizarlos