# Banco UCN

Proyecto base en Java para estudiar **arquitectura hexagonal**, también conocida como **Ports and Adapters Architecture**, mediante un ejemplo sencillo de transferencia bancaria.

El proyecto **no incluye ArchUnit**. La verificación arquitectural será desarrollada por los estudiantes como parte de la actividad indicada en `ACTIVIDAD.md`.

## ¿Por qué se llama arquitectura hexagonal?

Se llama *hexagonal* por una representación visual propuesta para mostrar que la aplicación puede comunicarse con distintos elementos externos a través de varios lados o puntos de conexión. Cada lado puede representar una forma diferente de entrada o salida: una interfaz web, una API REST, una base de datos, archivos, mensajería, pruebas automatizadas o servicios externos.

La forma geométrica no es lo esencial. Lo importante es la idea arquitectónica:

> El núcleo de la aplicación debe quedar protegido de los detalles tecnológicos externos.

En este proyecto, la lógica bancaria central no depende de JPA, de una base de datos, de un framework web ni de clases de infraestructura. Los detalles externos quedan en los adaptadores.

## Idea central

La arquitectura organiza el sistema alrededor de un núcleo estable:

```text
Adaptadores de entrada  --->  Aplicación  --->  Dominio
                                      |
                                      v
                                  Puertos
                                      ^
                                      |
                         Adaptadores de salida
```

El flujo de control puede ir desde una interfaz externa hacia el caso de uso y luego hacia persistencia. Sin embargo, el flujo de dependencias debe proteger el centro: las clases externas pueden depender del núcleo, pero el núcleo no debería depender de las clases externas.

## Zonas de la arquitectura

### 1. Dominio

El dominio contiene las reglas de negocio puras. En este proyecto está representado por:

```text
domain/model/Account.java
```

`Account` modela una cuenta bancaria y contiene reglas como:

- no se puede depositar un monto negativo;
- no se puede retirar un monto negativo;
- no se puede retirar más dinero que el saldo disponible.

El dominio debe poder probarse sin levantar una base de datos, sin iniciar un servidor web y sin depender de frameworks.

### 2. Modelo de dominio

Un modelo de dominio es una clase que representa un concepto central del problema. En este caso, `Account` representa una cuenta bancaria desde la mirada del negocio.

No debe confundirse con una entidad JPA. Una clase del modelo de dominio puede existir sin anotaciones como `@Entity`, `@Table` o `@Id`.

### 3. Puerto

Un puerto es una interfaz que expresa una necesidad o una capacidad del sistema sin comprometerse con una tecnología concreta.

En este proyecto, el puerto es:

```text
domain/port/AccountRepository.java
```

Este puerto declara operaciones como buscar y guardar cuentas. No indica si esas operaciones se harán con memoria, JPA, PostgreSQL, MySQL, archivos o una API externa.

### 4. Aplicación

La capa de aplicación coordina casos de uso. En este proyecto está representada por:

```text
application/TransferService.java
```

`TransferService` no contiene detalles web ni detalles de persistencia. Su responsabilidad es coordinar la transferencia:

1. buscar la cuenta de origen;
2. buscar la cuenta de destino;
3. retirar dinero de la cuenta de origen;
4. depositar dinero en la cuenta de destino;
5. guardar los cambios mediante el puerto `AccountRepository`.

### 5. Adaptador de entrada

Un adaptador de entrada recibe solicitudes desde el exterior y las traduce a llamadas a la aplicación.

En este proyecto está representado por:

```text
adapter/in/web/AccountController.java
adapter/in/web/TransferRequest.java
adapter/in/web/TransferResponse.java
```

Aunque se llama `web`, el ejemplo no usa un framework web real. Se deja simple para que la estructura arquitectónica sea más fácil de observar.

### 6. Adaptador de salida

Un adaptador de salida implementa un puerto usando una tecnología concreta o una estrategia específica.

En este proyecto está representado por:

```text
adapter/out/persistence/InMemoryAccountRepository.java
adapter/out/persistence/AccountEntity.java
adapter/out/persistence/AccountMapper.java
```

`InMemoryAccountRepository` implementa el puerto `AccountRepository` usando memoria. `AccountEntity` muestra cómo una entidad JPA debe quedar fuera del dominio. `AccountMapper` transforma entre el modelo de persistencia y el modelo de dominio.

### 7. Bootstrap

El bootstrap crea y conecta manualmente los objetos principales de la aplicación.

```text
bootstrap/App.java
```

Aquí se ensamblan el repositorio, el servicio de transferencia y el controlador. En una aplicación real, esta responsabilidad podría asumirla un framework como Spring, pero en este proyecto se hace manualmente para que las dependencias sean visibles.

## Estructura del proyecto

```text
cl.ucn.bank
├── domain
│   ├── model
│   │   └── Account.java
│   └── port
│       └── AccountRepository.java
├── application
│   └── TransferService.java
├── adapter
│   ├── in
│   │   └── web
│   │       ├── AccountController.java
│   │       ├── TransferRequest.java
│   │       └── TransferResponse.java
│   └── out
│       └── persistence
│           ├── AccountEntity.java
│           ├── AccountMapper.java
│           └── InMemoryAccountRepository.java
└── bootstrap
    └── App.java
```

## Dependencias principales

El proyecto usa Java 17, Maven y JUnit para pruebas unitarias del dominio.

También incluye `jakarta.persistence-api` únicamente para mostrar anotaciones JPA en la clase `AccountEntity`, ubicada en el adaptador de persistencia. El dominio no usa JPA.

## Ejecutar pruebas

```bash
mvn clean test
```

## Ejecutar la aplicación de ejemplo

```bash
mvn exec:java -Dexec.mainClass="cl.ucn.bank.bootstrap.App"
```

También se puede ejecutar la clase `App` directamente desde el IDE.

## Qué observar en el código

Al revisar el proyecto, conviene observar estas decisiones:

- `Account` contiene reglas de negocio y no depende de JPA.
- `AccountRepository` es una interfaz, no una implementación concreta.
- `TransferService` depende del puerto `AccountRepository`, no de `InMemoryAccountRepository`.
- `AccountController` llama a la aplicación, no al adaptador de persistencia.
- `AccountEntity` está fuera del dominio porque pertenece a la persistencia.
- `AccountMapper` evita mezclar el modelo de dominio con el modelo de base de datos.

## Relación con la actividad

La actividad de los estudiantes consiste en agregar pruebas arquitecturales con ArchUnit para verificar automáticamente que estas decisiones se mantengan. Las instrucciones evaluables están en:

```text
ACTIVIDAD.md
```
