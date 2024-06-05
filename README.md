# APS 8

Este projeto é o projeto de APS de 8ª Fase ...

## Setup

Criar um banco de dados postgres.

```sql
CREATE DATABASE aps7;
```

Criar os schemas necessários no banco.

```sql
CREATE SCHEMA security;
CREATE SCHEMA general;
```

Com o java 21 instalado rodar os comandos abaixo para instalar e rodar a aplicação.

```bash
./mvnw install
./mvnw spring-boot:run
```
