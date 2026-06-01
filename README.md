# 🌤️ Weather API - Spring Boot & Redis Cache

Uma API REST de alta performance desenvolvida em **Java** e **Spring Boot** para consulta de dados climáticos em tempo real. O projeto integra-se à API externa do **OpenWeather**, utilizando uma arquitetura resiliente otimizada por uma camada de cache distribuído com **Redis** e uma suite robusta de testes unitários.

---

## 🚀 Diferenciais de Engenharia do Projeto

Este projeto não é apenas um "consumidor de API". Ele foi desenhado seguindo boas práticas de design de software e arquitetura de produção:

* **Padrão de Projeto Strategy**: Desacoplamento absoluto da camada de cache através de interfaces genéricas. A lógica de negócios não sabe (e não se importa) se o cache está no Redis, em memória ou em um banco relacional.
* **Otimização com Redis (TTL Eficiente)**: Implementação de cache com tempo de expiração dinâmico (TTL de 15 minutos) usando o cliente Jedis. Evita chamadas desnecessárias à API externa, economiza banda e garante dados sempre atualizados.
* **Testes Unitários Automatizados**: Suite de testes com **JUnit 5** e **Mockito** cobrindo cenários de sucesso e falha, garantindo alta cobertura de código no Adapter de infraestrutura.
* **Serialização Customizada**: Uso do **Gson** para transformações eficientes de objetos Java para JSON e vice-versa na persistência do cache.

---

## 🛠️ Tecnologias Utilizadas

* **Linguagem:** Java 17 / 21
* **Framework:** Spring Boot 3.x (Spring Web)
* **Cache:** Redis & Jedis Client
* **Utilitários:** Project Lombok, Gson
* **Testes:** JUnit 5, Mockito

---

## 📐 Arquitetura do Sistema (Ports & Adapters / Clean)

O projeto é estruturado para garantir que a infraestrutura possa mudar sem afetar as regras de negócio:

com.api.WeatherAPI

conf/           # Configurações brutas de Infraestrutura (@Configuration Beans)
gateway/        # Interfaces e contratos de inversão de dependência (Strategy)
adapter/        # Implementações reais do mundo externo (OpenWeather API, Redis Cache)
dtos/           # Objetos de transferência de dados mapeados com Jackson/Lombok

