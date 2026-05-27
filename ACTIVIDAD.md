# Actividad: Verificación de Arquitectura Hexagonal con ArchUnit

## Contexto

Se entrega un sistema bancario pequeño organizado con arquitectura hexagonal. El proyecto compila y tiene pruebas unitarias del dominio, pero no incluye pruebas arquitecturales.

El desafío consiste en agregar ArchUnit y verificar automáticamente que la estructura del proyecto respete las reglas de arquitectura vistas en clase.

## Objetivo de la actividad

Implementar pruebas arquitecturales que permitan detectar dependencias incorrectas entre dominio, aplicación y adaptadores.

Al finalizar, cada grupo debe ser capaz de explicar qué regla arquitectural está protegiendo cada test y qué tipo de violación detecta.

## Parte 1: Comprensión de la estructura

Antes de escribir reglas ArchUnit, identifique qué clases pertenecen a cada zona:

- dominio;
- modelo de dominio;
- puerto;
- aplicación;
- adaptador de entrada;
- adaptador de salida;
- bootstrap o configuración.

## Parte 2: Incorporación de ArchUnit

Agregue la dependencia de ArchUnit en el archivo `pom.xml` con alcance de test.

Luego cree el paquete:

```text
src/test/java/cl/ucn/bank/architecture
```

Dentro de ese paquete cree la clase:

```text
HexagonalArchitectureTest.java
```

Use `@AnalyzeClasses` para analizar el paquete base:

```text
cl.ucn.bank
```

## Parte 3: Reglas arquitecturales mínimas

Implemente al menos cinco reglas ArchUnit.

Las reglas mínimas son:

1. El paquete `domain` solo puede depender de sí mismo y de `java..`.
2. El paquete `domain` no puede depender de `jakarta.persistence..`.
3. Los adaptadores de entrada no pueden depender directamente de adaptadores de salida.
4. La capa `application` no puede depender de `adapter.in.web`.
5. Las clases anotadas con `@Entity` deben estar en `adapter.out.persistence`.

Cada regla debe incluir una explicación usando `because(...)`.

## Parte 4: Reglas complementarias

Agregue al menos dos reglas adicionales. Puede elegir entre las siguientes opciones o proponer otras equivalentes:

1. Las clases cuyo nombre termina en `Controller` deben estar en `adapter.in.web`.
2. Las clases cuyo nombre termina en `Entity` deben estar en `adapter.out.persistence`.
3. Las clases cuyo nombre termina en `Repository` no deben estar en `adapter.in.web`.
4. La aplicación solo puede ser accedida por adaptadores de entrada, bootstrap o tests.
5. No debe haber ciclos entre paquetes de primer nivel.
6. Los adaptadores de salida no deben depender de adaptadores de entrada.

## Parte 5: Violación controlada

Introduzca una violación intencional para comprobar que ArchUnit detecta el problema.

Puede elegir una de estas alternativas:

- agregar `@Entity` a `Account`;
- importar `AccountEntity` desde `TransferService`;
- hacer que `AccountController` use directamente `InMemoryAccountRepository`;
- hacer que una clase de `domain` dependa de una clase de `adapter`;
- mover `AccountEntity` al paquete `domain.model`.

Ejecute los tests y guarde la salida de consola o una captura donde se observe la regla fallando.

Después de comprobar el fallo, revierta la violación para dejar el proyecto nuevamente limpio.

## Entregable

Entregar:

1. Código fuente del proyecto.
2. Clase `HexagonalArchitectureTest.java`.
3. Captura o salida de consola donde se observe una regla ArchUnit fallando ante una violación.

