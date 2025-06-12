# konfio-android-test

![Demo de la app](repo-assets/konfio-demo.gif)

## 🏗️ Estructura del Proyecto

El proyecto sigue los principios de Clean Architecture con una clara separación de capas:

```
app/
├── data/           # Capa de datos
│   ├── remote/     # Implementación de API
│   ├── local/      # Base de datos Room
│   ├── repository/ # Implementación de repositorios
│   └── mapper/     # Mappers de datos
├── domain/         # Reglas de negocio
│   ├── model/      # Modelos de dominio
│   ├── repository/ # Interfaces de repositorio
│   └── usecase/    # Casos de uso
└── ui/             # Capa de presentación
    ├── screens/    # Pantallas de la app
    ├── components/ # Componentes reutilizables
    └── theme/      # Estilos y temas
```

## 🛠️ Tecnologías Utilizadas

- **Lenguaje:** Kotlin
- **UI Framework:** Jetpack Compose
- **Arquitectura:** Clean Architecture + MVI
- **Inyección de Dependencias:** Hilt
- **Networking:** Ktor
- **Base de Datos:** Room
- **Testing:** JUnit5 + MockK
- **Imágenes:** Coil
- **Navegación:** Compose Navigation
- **Corrutinas & Flows**

## 📱 Características Principales

- Implementación de Clean Architecture con separación clara de responsabilidades
- Patrón MVI (Model-View-Intent) para manejo de estado predecible
- Caché offline-first con Room
- Manejo de estados de carga, error y datos vacíos
- Animaciones fluidas con Compose
- Navegación tipo "shared element transition"
- Tests unitarios exhaustivos

## 🏛️ Patrones de Diseño

- **Repository Pattern:** Abstracción de fuentes de datos
- **Dependency Injection:** Inversión de control con Hilt
- **Observer Pattern:** Implementado a través de Flows
- **Strategy Pattern:** Manejo de errores y validaciones

## 🔄 Flujo de Datos

1. **UI Layer (MVI)**
   - Estados inmutables
   - Eventos unidireccionales
   - Side effects para navegación

2. **Domain Layer**
   - Casos de uso independientes
   - Modelos de dominio puros
   - Reglas de negocio centralizadas

3. **Data Layer**
   - Caché offline-first
   - Manejo de errores robusto

## 🧪 Testing

- Tests unitarios para:
  - ViewModels
  - Use Cases
  - Repositories
  - Error Handling

## 🎨 UI/UX

- Material Design 3 (Material You)
- Animaciones fluidas
- Manejo de errores amigable
- Diseño responsive

## 🔄 Continuous Integration
- JUnit5 para testing
- Gradle para automatización

## 📱 Screenshots

[Aquí irían los screenshots de la aplicación]

