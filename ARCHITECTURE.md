# TravelHub Mobile - Arquitectura y Estructura del Proyecto

## Descripción General

TravelHub Mobile es una aplicación Android nativa desarrollada con **Kotlin** y **Jetpack Compose**, diseñada para que viajeros puedan explorar hospedajes, realizar reservas y gestionar su perfil desde dispositivos móviles.

Los diseños fueron extraídos directamente del **Figma Design System** del proyecto TravelHub, manteniendo fidelidad en colores, tipografía, espaciados y componentes.

---

## Stack Tecnológico

| Tecnología | Versión | Propósito |
|---|---|---|
| Kotlin | 2.0.21 | Lenguaje principal |
| Jetpack Compose | BOM 2024.09.00 | UI declarativa |
| Material 3 | (via BOM) | Componentes de diseño |
| Navigation Compose | 2.7.7 | Navegación entre pantallas |
| Material Icons Extended | 1.6.8 | Iconografía |
| Android Gradle Plugin | 9.0.1 | Build system |
| Min SDK | 24 (Android 7.0) | Compatibilidad mínima |
| Target SDK | 36 | Target API |

---

## Arquitectura

La aplicación sigue una arquitectura **Screen-based** con separación por capas:

```
┌─────────────────────────────────────────┐
│              MainActivity               │
│         (Entry Point + Theme)           │
├─────────────────────────────────────────┤
│            Navigation Layer             │
│     NavGraph.kt + Routes.kt            │
│   (Gestión de rutas y navegación)       │
├─────────────────────────────────────────┤
│             UI Layer                    │
│  ┌─────────┬────────────┬────────────┐  │
│  │ Screens │ Components │   Theme    │  │
│  │ (9 vistas│ (Reusables)│ (Design   │  │
│  │  de app) │            │  System)  │  │
│  └─────────┴────────────┴────────────┘  │
└─────────────────────────────────────────┘
```

---

## Estructura de Archivos

```
app/src/main/java/com/example/travelhubapp_mobile/
│
├── MainActivity.kt                    # Entry point de la aplicación
│
├── navigation/
│   ├── Routes.kt                      # Constantes de rutas de navegación
│   └── NavGraph.kt                    # Grafo de navegación (NavHost)
│
├── ui/
│   ├── theme/
│   │   ├── Color.kt                   # Paleta de colores (Figma Design System)
│   │   ├── Type.kt                    # Tipografía Inter (escalas del Figma)
│   │   └── Theme.kt                   # MaterialTheme personalizado
│   │
│   ├── components/
│   │   └── Components.kt             # Componentes reutilizables
│   │
│   └── screens/
│       ├── LoginScreen.kt            # Inicio de sesión
│       ├── RegistroScreen.kt         # Registro de viajero
│       ├── HomeScreen.kt             # Búsqueda de hospedajes
│       ├── BuscarHotelesScreen.kt    # Resultados de búsqueda
│       ├── CheckoutScreen.kt         # Proceso de reserva
│       ├── ReservaConfirmadaScreen.kt # Confirmación exitosa
│       ├── MisReservasScreen.kt      # Favoritos / reservas guardadas
│       ├── PerfilScreen.kt           # Perfil de usuario
│       └── ImprimirScreen.kt         # Detalle e impresión de reserva
│
└── res/
    ├── values/
    │   ├── colors.xml
    │   ├── strings.xml
    │   └── themes.xml
    └── drawable/
        └── ic_launcher_*.xml
```

---

## Navegación

### Flujo de Navegación

```
Login ──────────────┐
  │                 │
  ▼                 ▼
Registro         Home (Tab 0)
                   │
          ┌────────┼────────────┬──────────┐
          ▼        ▼            ▼          ▼
     Buscar    Mis Reservas   Perfil    (Tabs)
    Hoteles     (Tab 2)      (Tab 3)
    (Tab 1)
       │
       ▼
    Checkout
       │
       ▼
  Reserva Confirmada
       │
       ▼
    Imprimir
```

### Bottom Navigation (4 tabs)

| Tab | Ícono | Pantalla | Ruta |
|---|---|---|---|
| Inicio | Home | HomeScreen | `home` |
| Buscar | Search | BuscarHotelesScreen | `buscar_hoteles` |
| Favoritos | Heart | MisReservasScreen | `mis_reservas` |
| Perfil | Person | PerfilScreen | `perfil` |

### Rutas Definidas

| Ruta | Pantalla | Tipo |
|---|---|---|
| `login` | LoginScreen | Stack |
| `registro` | RegistroScreen | Stack |
| `home` | HomeScreen | Tab |
| `buscar_hoteles` | BuscarHotelesScreen | Tab |
| `checkout` | CheckoutScreen | Stack |
| `reserva_confirmada` | ReservaConfirmadaScreen | Stack |
| `mis_reservas` | MisReservasScreen | Tab |
| `perfil` | PerfilScreen | Tab |
| `imprimir` | ImprimirScreen | Stack |

---

## Design System (desde Figma)

### Paleta de Colores

#### Primarios (Azules)
| Token | Hex | Uso |
|---|---|---|
| Blue50 | `#EFF6FF` | Backgrounds claros |
| Blue100 | `#DBEAFE` | Texto sobre gradiente, containers |
| Blue600 | `#155DFC` | **Color primario**, botones, links |
| Blue700 | `#1447E6` | Hover states |
| Blue900 | `#1C398E` | Gradiente secundario, textos oscuros |

#### Neutrales (Grises)
| Token | Hex | Uso |
|---|---|---|
| Gray50 | `#F9FAFB` | Background de listas |
| Gray200 | `#E5E7EB` | Bordes suaves, dividers |
| Gray300 | `#D1D5DC` | Bordes de inputs |
| Gray400 | `#9CA3AF` | Placeholders |
| Gray500 | `#6A7282` | Texto secundario |
| Gray600 | `#4A5565` | Texto body |
| Gray700 | `#364153` | Labels de formularios |
| Gray900 | `#1E2939` | Headings, texto principal |

#### Estado
| Token | Hex | Uso |
|---|---|---|
| Success | `#00A63E` | Confirmaciones |
| SuccessLight | `#DCFCE7` | Background de banners success |
| Warning/StarYellow | `#F0B100` | Estrellas de rating |
| Destructive | `#E7000B` | Errores, eliminar |

### Tipografía

Fuente: **Inter** (system default en Compose)

| Estilo | Peso | Tamaño | Uso |
|---|---|---|---|
| displayLarge | Bold | 30sp | Títulos hero |
| headlineLarge | Bold | 24sp | Títulos de sección (h1) |
| headlineMedium | Bold | 20sp | Subtítulos (h2) |
| headlineSmall | Bold | 18sp | Subtítulos (h3) |
| titleLarge | SemiBold | 18sp | Nombres de hotel |
| titleMedium | SemiBold | 16sp | Labels destacados |
| titleSmall | SemiBold | 14sp | Labels de formulario |
| bodyLarge | Normal | 16sp | Texto principal |
| bodyMedium | Normal | 14sp | Texto secundario |
| bodySmall | Normal | 12sp | Captions, metadata |
| labelLarge | SemiBold | 16sp | Botones grandes |
| labelMedium | Medium | 14sp | Botones medianos |
| labelSmall | Medium | 12sp | Bottom nav labels |

### Gradiente Principal

```
Brush.linearGradient(Blue600 → Blue900)
// #155DFC → #1C398E
```

Usado en: Login, Registro, Header de Perfil.

---

## Componentes Reutilizables

Definidos en `ui/components/Components.kt`:

| Componente | Descripción | Props principales |
|---|---|---|
| `THLogo` | Logo "TH" en caja blanca con bordes redondeados | modifier |
| `THInput` | Campo de texto con label, ícono y soporte password | value, label, placeholder, leadingIcon, isPassword |
| `THButton` | Botón primario azul full-width | text, onClick |
| `THOutlineButton` | Botón outline con ícono opcional | text, onClick, icon |
| `THBackButton` | Botón circular de retroceso | onClick |
| `THBottomBar` | Barra de navegación inferior con 4 tabs | selected, onSelect |
| `StarRating` | Estrella amarilla + texto de rating | rating |

### Formas (Shapes)

| Token | Valor | Uso |
|---|---|---|
| `CardShape` | RoundedCorner 10dp | Cards de hotel, secciones |
| `FormShape` | RoundedCorner 16dp | Formularios, containers grandes |
| `InputShape` | RoundedCorner 10dp | Campos de texto |
| `CircleShape` | Circular | Avatares, botones de acción |

---

## Pantallas

### 1. LoginScreen
- Gradiente azul de fondo
- Logo TH + título "TravelHub"
- Formulario: email + contraseña con toggle de visibilidad
- Link "¿Olvidaste tu contraseña?"
- Botón "Iniciar sesión"
- Link a registro

### 2. RegistroScreen
- Gradiente azul de fondo
- 5 campos: nombre, email, teléfono, contraseña, confirmar contraseña
- Botón "Crear cuenta"
- Link a login

### 3. HomeScreen
- Header con logo "TravelHub"
- Título "Encuentra tu hotel ideal"
- Formulario de búsqueda: destino, check-in, check-out, huéspedes
- Botón "Buscar hoteles"

### 4. BuscarHotelesScreen
- Header con back button + "Resultados" + filtro
- Contador de hoteles encontrados
- Lista scrolleable de HotelCards
- Cada card: imagen, nombre, rating, precio, botón "Ver detalle"

### 5. CheckoutScreen
- Resumen de reserva (hotel, fechas, huéspedes, noches)
- Formulario de datos personales
- Resumen de pago con total
- Botón "Confirmar reserva"

### 6. ReservaConfirmadaScreen
- Ícono de check verde
- Número de reserva (#123456)
- Botones: descargar confirmación, compartir
- Banner informativo azul
- Botón "Volver al inicio"
- Contacto de ayuda

### 7. MisReservasScreen
- Header con contador de hoteles guardados
- Lista de reservas con imagen, nombre, rating, precio
- Botón eliminar por reserva
- Botón "Buscar más hoteles"

### 8. PerfilScreen
- Header con gradiente: avatar, nombre, email
- Stats: reservas, puntos, rating
- Reservas recientes con estado (Confirmada/Completada)
- Menú: información personal, métodos de pago, notificaciones, seguridad
- Botón "Cerrar sesión"

### 9. ImprimirScreen
- Botones: imprimir + descargar
- Compartir vía: link, email, WhatsApp, SMS
- Banner de reserva confirmada
- Detalles del hotel y la reserva

---

## Métricas del Proyecto

| Métrica | Valor |
|---|---|
| Archivos Kotlin | 14 |
| Líneas de código | ~1,145 |
| Pantallas | 9 |
| Componentes reutilizables | 7 |
| Dependencias principales | 3 (Compose, Navigation, Material Icons) |

---

## Ramas del Repositorio

| Rama | Contenido |
|---|---|
| `main` | Esqueleto del proyecto (template Android) |
| `all-pages` | Todas las 9 pantallas implementadas |
| `feature/hu-home-hospedajes` | HU: Vista principal de hospedajes + botón reservar |
| `feature/hu-login-registro` | HU: Bienvenida + Login + Registro viajero |

---

## Cómo Ejecutar

1. Abrir el proyecto en **Android Studio** (Ladybug o superior)
2. Esperar a que termine el **Gradle Sync**
3. Seleccionar un dispositivo/emulador (API 24+)
4. Clic en **Run ▶️**

## Requisitos

- Android Studio Ladybug+
- JDK 21
- Android SDK 36
- Emulador o dispositivo con Android 7.0+ (API 24)
