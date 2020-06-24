# analisis-sintactico-function
Analizador sintáctico con capacidad de reconocer la estructura de una funcion con parametros.

## Contenido
  - [Objetivo](#Objetivo)
 - [Requerimientos](##Requerimientos)
 - [Gramatica](#Gramatica)
 - [Posibles entradas](#Entradas)
 - [Compilar](#Compilar)
 - [Ejecutar](#Ejecutar)
 - [Descargar](#Descargar)
---
## Objetivo
- Crear un analizador sintactico, basado en un autómata con pila, capaz de identificar tokens de una gramatica establecida.
---
## Requerimientos 📋
[Java 8](https://www.java.com/es/download/)
---
## Gramatica 📖
  - FUNCION --> function NOMBRE PARAMETROS
  - PARAMETROS --> ( PARAMLIST )
  - PARAMLIST --> var VARIABLE LIST 
  - VARIABLE --> NOMBRE : TIPO
  - NOMBRE --> LETRA RESTOL
  - RESTOL --> LETRA RESTOL | ε
  - LETRA --> a...z
  - TIPO --> integer | real | boolean | string
  - LIST --> ; VARIABLE LIST | ε
---
 ## Descargar 🎁
````bash
git clone https://github.com/SalimVazquez/analisis-sintactico-function.git
````
---
## Compilar ⚙️
```bash
javac Main.java
```
---
## Ejecutar 🚀
```bash
java Main
```
--- 
## Entradas 📝
  - function abc ( var abc : integer )
  - function abc ( var abc : integer ; def : string )
---
## Autores ✒️
* [**Salim Vazquez Solis 🤓**](https://github.com/SalimVazquez)
