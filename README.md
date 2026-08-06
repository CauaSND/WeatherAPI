# Weather API - REST Backend Service

A high-performance REST API built with **Java** and **Spring Boot** for retrieving real-time weather data. This project
integrates with the **OpenWeatherMap API**, implementing a resilient architecture optimized with **Redis** distributed
caching and comprehensive unit test coverage.

**Developed by:** Cauã Silva  
**Email:** caua.sndias@gmail.com  
**GitHub:** https://github.com/CauaSND/WeatherAPI

---

## 📋 Project Objective

The Weather API provides a robust backend service for querying current weather information by city and state. The system
implements clean architecture patterns with strategy-based dependency injection, enabling flexible infrastructure
swapping without affecting business logic. This project demonstrates production-grade Java development practices
including efficient caching, proper exception handling, and comprehensive API security.

---

## 🎯 Core Features

- **Real-time Weather Data**: Retrieve current temperature, humidity, pressure, and "feels like" metrics via
  OpenWeatherMap integration
- **Efficient Caching**: Distributed Redis cache with intelligent TTL strategies (15 minutes for weather, 30 days for
  geolocation)
- **State-aware City Matching**: Automatic Unicode normalization to disambiguate cities with identical names across
  different Brazilian states
- **Clean Architecture**: Strict separation of concerns with interface-based adapters for seamless infrastructure
  replacement
- **Comprehensive Error Handling**: Custom exception hierarchy with global error handler providing structured error
  responses
- **Automated Testing**: JUnit 5 + Mockito test suite covering adapter layer and critical business logic

---

## 🛠️ Technology Stack

| Component                                 | Technology                   |
|-------------------------------------------|------------------------------|
| **Language**                              | Java 21                      |
| **Framework**                             | Spring Boot 3.x (Spring Web) |
| **Cache**                                 | Redis 7+ with Jedis Client   |
| **Testing**                               | JUnit 5, Mockito             |
| **Serialization**                         | Gson                         |
| **Build Tool**                            | Maven 3.x                    |
| **Code Utilities**                        | Project Lombok               |
| **Thymeleaf** (Renderização do Front-end) 
| **Docker** (Containerização)              

---

## 📐 System Architecture (Ports & Adapters / Hexagonal)

The codebase follows hexagonal architecture principles to ensure infrastructure agnosticism:

```
com.api.WeatherAPI/
├── config/              # Bean configuration & infrastructure setup
├── controllers/         # HTTP endpoints (routing only)
├── services/            # Business logic with cache-first pattern
├── adapters/            # Port implementations
│   ├── WeatherGateway   # Interface for external API calls
│   ├── OpenWeatherAdapter   # OpenWeatherMap API integration
│   ├── WeatherCache     # Interface for caching abstraction
│   └── RedisCache       # Redis persistence implementation
├── dtos/                # Data transfer objects (Lombok @Builder)
└── expection/           # Custom exception hierarchy
```

**Design Principle**: Services depend only on interfaces (`WeatherGateway`, `WeatherCache`), never on concrete
implementations. This allows swapping Redis for in-memory cache without modifying business logic.

---

## 🔌 API Endpoints

### Get Current Weather

```http
GET /current/{state}/{city}
```

**Path Parameters:**

- `state` (string, required): Brazilian state name (normalized format, e.g., `sao-paulo`, `rio-de-janeiro`)
- `city` (string, required): City name

**Response:**

```json
{
  "temp": 28.5,
  "feels_like": 30.2,
  "humidity": 65,
  "pressure": 1013
}
```

**Status Codes:**

- `200 OK`: Weather data retrieved successfully
- `404 Not Found`: State/city combination not found
- `500 Internal Server Error`: OpenWeatherMap API failure or Redis connection issue

---

### Get Available Cities

```http
GET /location/cities/{city}
```

**Path Parameters:**

- `city` (string, required): City name to search

**Response:**

```json
[
  {
    "name": "São Paulo",
    "state": "São Paulo",
    "lat": -23.5505,
    "lon": -46.6333
  }
]
```

**Status Codes:**

- `200 OK`: City list retrieved successfully
- `500 Internal Server Error`: OpenWeatherMap API unavailable

---

## 🔧 Environment Variables

Configure the following environment variables before running:

```bash
weatherkey     # OpenWeatherMap API key (required)
redisURL       # Redis host (default: localhost)
redisPORT      # Redis port (default: 6379)
redisPassword  # Redis authentication password
```

---

## 🐳 running with Docker

This is a **Multi-stage Build** in Docker, The entire instructure (API Spring Boot + Cache Redis) runs isolated within the same Docker network 
, make a safe comunication, fast and in a simulation enviroment  

You do not need to have Java, Maven or Redis in your machine, only Dcker can compile and execute the whole application 

⚠️ IMPORTANT (API Key): This project consumes real-time data from OpenWeather. To get real weather data, you need a valid API key.
Otherwise, mock (false) data will be processed and returned.

### 🚀 Step by Step
(If you prefer to use mock(false) data, you can skip steps 1 and 3).

### 1. Get a key (If you want real data)

1. open [OpenWeather](https://openweathermap.org/) log-in with a free acount.
2. Go to API key section and get your API key

### 2. Clone this repo 

```bash
git clone [https://github.com/CauaSND/WeatherAPI.git](https://github.com/CauaSND/WeatherAPI.git)
cd WeatherAPI
```

### 3. Configure your credentials (.env)
**The project has a file called .env.example. Do a copy of it and renome to .env and change only the weatherkey:**
```bash
cp .env.example .env.example
```
```
weatherkey=your-key
```
**Security note:** Put '.env' in '.gitignore' before committing. The final '.env' file you generate is ignored by Git to ensure your private keys are never pushed to the public repository.

### 4. Run containers
With the .env file filled in the root, run the command below in the terminal to build the images and start all services on the same network:

```bash
docker compose up --build
```

### 5. Open your browser 
Once Spring Boot has successfully booted in the terminal, open your browser and access the front-end screen in Thymeleaf:
👉 http://localhost:8080



## 📊 Testing Strategy

- **Framework**: JUnit 5 with MockitoExtension
- **Pattern**: Given-When-Then structure with @BeforeEach fixture setup
- **Coverage**: Focus on adapter layer and state-matching logic
- **Mocking**: RestTemplate stubs for OpenWeatherMap API responses

Example test:

```java

@ExtendWith (MockitoExtension.class)
class OpenWeatherAdapterTest {
    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    OpenWeatherAdapter adapter;

    @Test
    void shouldMatchCityByNormalizedState () {
        // Given: Multiple results for city "Osasco"
        // When: Filter by state "sao-paulo"
        // Then: Return coordinates for São Paulo entry
    }
}
```

---

## 🔐 Caching Strategy

| Resource         | TTL        | Key Format         |
|------------------|------------|--------------------|
| Weather Data     | 15 minutes | `{cityName}`       |
| City Geolocation | 30 days    | `{cityName}Cities` |

The cache-first pattern ensures minimal external API calls and optimal response times:

```
REQUEST
  → Cache HIT: Return cached data
  → Cache MISS: Query OpenWeatherMap → Cache result → Return
```

---

## 🌍 State Normalization Logic

The system disambiguates cities with identical names across Brazilian states using Unicode-aware normalization:

```java
// Input: state = "São Paulo"
// Process:
//   1. Decompose accents (NFD): "Sa˜o Paulo"
//   2. Remove combining marks: "Sao Paulo"
//   3. Convert to lowercase: "sao paulo"
//   4. Replace spaces with hyphens: "sao-paulo"
// Output: "sao-paulo"
```

**Example**: City "Osasco" exists in both São Paulo and Paraná states. The API returns results for both; state parameter
selects the correct one.

---

## 🚨 Exception Handling

Custom exceptions with HTTP status mapping:

- **`LocationNotFoundException`** → HTTP 404
    - Thrown when state/city combination not found

- **`WeatherApiException`** → HTTP 500
    - Thrown when OpenWeatherMap API fails or returns null

All exceptions are caught by `@RestControllerAdvice` global handler for consistent error responses.

---

## 📝 Project Structure

- `src/main/java/com/api/WeatherAPI/` — Source code
    - `controllers/` — REST endpoint handlers
    - `services/` — Business logic layer
    - `adapters/` — External integration ports
    - `config/` — Spring beans and infrastructure configuration
    - `dtos/` — Data transfer objects
    - `expection/` — Custom exception classes

- `src/test/java/` — Unit tests

---

## 🤝 Contributing

For feature requests or bug reports, please open an issue on
the [GitHub repository](https://github.com/CauaSND/WeatherAPI).

---

## 📄 License

This project is provided as-is for educational and development purposes.

---

**Last Updated:** June 2026  
**Java Version:** 21  
**Spring Boot Version:** 3.x
