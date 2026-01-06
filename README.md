# E-Commerce Microservices Architecture

## Sobre o Projeto

Este projeto é uma implementação robusta de um backend de **E-commerce baseado numa Arquitetura de Microsserviços**.  
O objetivo principal foi aplicar **padrões de design avançados** para resolver problemas reais de sistemas distribuídos, como:

- Consistência de dados
- Resiliência a falhas
- Escalabilidade
- Observabilidade

O sistema simula um **fluxo completo de compra**: desde o registo do utilizador e gestão de catálogo, até à reserva de stock, pagamento e notificação, tudo **orquestrado através de eventos assíncronos**.

---

## 🏗️ Arquitetura e Padrões de Design

O projeto segue rigorosamente os princípios da **Clean Architecture (Arquitetura Hexagonal)**, isolando completamente o domínio da tecnologia.

### Destaques Técnicos (O *Core* do Projeto)

#### 📌 Saga Pattern (Coreografada)
- Gestão de transações distribuídas entre **Orders, Inventory e Payment**
- Comunicação assíncrona via eventos
- Sem acoplamento temporal entre serviços

#### 📌 Transactional Outbox Pattern
- Implementado no **orders-service**
- Garante atomicidade entre:
    - Persistência no banco de dados
    - Publicação de eventos no Kafka
- Resolve o clássico problema de **Dual Write**

#### 📌 Clean Architecture
Separação estrita em camadas:

- **Domain**  
  Entidades e regras de negócio puras (POJOs)

- **Application**  
  Casos de uso (*Use Cases*) e Portas (*Interfaces*)

- **Infrastructure**  
  Adaptadores de Base de Dados, Controllers REST, Clientes Feign e Kafka

#### 📌 Resiliência
- **Circuit Breaker (Resilience4j)**  
  Proteção contra falhas em cascata (ex: Orders → Catalog)

- **Retry & Exponential Backoff**  
  Tratamento de falhas transitórias

- **Rate Limiting (Redis)**  
  Proteção do API Gateway contra força bruta e sobrecarga

#### 📌 Concorrência e Idempotência
- **Pessimistic Locking**  
  Controlo de concorrência no inventário para evitar *overselling*

- **Consumer Idempotency**  
  Garantia de que eventos duplicados do Kafka não geram efeitos colaterais duplicados

#### 📌 Testes de Integração
- Uso de **Testcontainers**
- Validação da persistência com bases de dados reais em ambiente Docker

---

## 🚀 Serviços e Tecnologias

| Serviço               | Responsabilidade                                                | Tech Stack Principal                                   |
|----------------------|------------------------------------------------------------------|--------------------------------------------------------|
| API Gateway           | Entrada, Roteamento, Segurança (JWT), Rate Limiting           | Spring Cloud Gateway, Redis, JJWT                      |
| Service Discovery     | Registo e descoberta dinâmica de serviços                     | Netflix Eureka                                         |
| Users Service         | Gestão de Identidade e Autenticação                            | Spring Security, PostgreSQL                            |
| Catalog Service       | Gestão de Produtos e Categorias                               | Spring Data JPA, PostgreSQL, Testcontainers             |
| Orders Service        | Ciclo de Vida do Pedido (Saga + Outbox)                        | Spring Kafka, OpenFeign, Scheduler, PostgreSQL          |
| Payment Service       | Processamento de Pagamentos (Mock)                             | Spring Kafka, PostgreSQL                               |
| Inventory Service    | Controlo de Stock e Reservas                                   | Spring Kafka, Resilience4j, PostgreSQL                 |
| Notification Service | Envio de Notificações (E-mail Mock)                           | Spring Kafka, PostgreSQL                               |

---

## 🛠️ Pré-requisitos

- Java 21
- Docker e Docker Compose
- Maven (opcional — wrapper ou Docker build)

---

## ▶️ Como Executar

A infraestrutura completa é orquestrada via **Docker Compose**.

### 🔹 Clonar o repositório

```bash
git clone https://github.com/teu-usuario/ecommerce-microservices.git
cd ecommerce-microservices
```

Author: Leonardo Matioli