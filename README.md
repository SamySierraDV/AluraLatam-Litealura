Challenge LiteAlura

Proyecto que crea un catálogo interactivo de libros (LiterAlura) consumiendo una API externa, procesando JSON, almacenando en base de datos y ofreciendo 5+ opciones de interacción por consola.

🚀 Objetivo Principal
Desarrollar un Catálogo de Libros con interfaz de consola que permita:

Consultar libros vía API específica

Procesar y almacenar datos JSON en BD

Filtrar/mostrar libros y autores

Proveer al menos 5 opciones de interacción al usuario

✨ Funcionalidades
Consumo de API de libros

Parseo y análisis de respuestas JSON

Persistencia en base de datos

Interfaz de consola interactiva (5+ opciones)

Búsqueda y filtrado de libros/autores

Exhibición de resultados al usuario

📋 Tabla de Contenidos
Requisitos

Instalación

Estructura del Proyecto

Pasos del Challenge

API

📋 Requisitos
Java 17+

Maven/Gradle (gestor de dependencias)

Base de datos (SQLite/H2/PostgreSQL)

Cliente HTTP (HttpClient/OkHttp)

Librerías JSON (Jackson/Gson)

🛠️ Instalación
Clonar repositorio

bash
git clone https://github.com/SamySierraDV/AluraLatam-Litealura.git
cd LiterAlura
Configurar dependencias

bash
mvn clean install
# o
gradle build
Configurar base de datos

sql
# Revisar archivo schema.sql o configuración application.properties
Ejecutar aplicación

bash
mvn spring-boot:run
# o
java -jar target/literalura-0.0.1-SNAPSHOT.jar
🏗️ Estructura del Proyecto
text
src/
├── main/
│   ├── java/
│   │   ├── controller/     # Menús y opciones usuario
│   │   ├── service/        # Lógica de negocio
│   │   ├── repository/     # Acceso a datos
│   │   ├── model/          # Entidades (Libro, Autor)
│   │   └── api/           # Cliente API
│   └── resources/
│       ├── schema.sql     # DDL base de datos
│       └── application.properties
📚 Pasos del Challenge
1. Configuración del Ambiente Java
Configurar JDK 17+

Crear proyecto Maven/Gradle

Agregar dependencias HTTP + JSON + BD

2. Creación del Proyecto
Estructura de carpetas

Clases base (Libro, Autor)

Configuración de base de datos

3. Consumo de la API
text
GET /works?key=API_KEY&filter=títulos
Cliente HTTP para requests

Manejo de respuestas JSON

4. Análisis de la Respuesta JSON
json
{
  "works": [{
    "title": "Don Quijote",
    "authors": [{"author": {"key": "/authors/OL..."}}
  }]
}
5. Inserción y consulta en BD
Mapeo objeto-relacional

Repositorios Spring Data

Consultas personalizadas

6. Exhibición de resultados
Menú interactivo (5+ opciones)

Listados formateados

Filtros por idioma/año/autor

🌐 API
Endpoint: API específica de libros (detalles en Trello)

Autenticación: API Key requerida

Respuesta: JSON con works, títulos, autores

Documentación: Ver backlog Trello

🎯 Opciones de Interacción (5+)
📚 Listar todos los libros

🔍 Buscar por título

✍️ Buscar por autor

🌍 Filtrar por idioma

📅 Libros por año

👥 Listar autores únicos

📊 Estadísticas

🔗 Enlaces Relacionados
Trello: https://trello.com/b/WDyMPDMb/literalura-challenge-java

Alura Latam Challenges
