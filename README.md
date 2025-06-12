# konfio-android-test

<img src="https://github.com/devjorgecastro/konfio-android-test/blob/develop/repo-assets/konfio-demo.gif?raw=true" width="200">

## ⚙️ Configuración

Para ejecutar el proyecto, necesitas agregar la siguiente propiedad en el archivo `local.properties`:

```properties
# Android SDK (reemplaza PATH_TO_SDK con la ruta de tu Android SDK)
sdk.dir=/PATH_TO_SDK/Android/sdk
```

## 📊 Análisis de Código - Sonarqube

El proyecto está configurado para reportar métricas de calidad a una instancia de Sonarqube montada en un VPS de OVH usando Docker. Puedes acceder al dashboard con las siguientes credenciales:

```properties
URL: http://51.222.110.137:9000
Usuario: guest
Password: @Guest123456
```

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

### Sonarqube Report
<img src="https://github.com/devjorgecastro/konfio-android-test/blob/develop/repo-assets/sonarqube-report.png?raw=true" width="700">

