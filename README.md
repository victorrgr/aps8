# APS 8

Este projeto é o projeto de APS de 8ª Fase para permitir armazenamento de informações de veículos para poder calcular emissões destes veículos.

O cálculo das emissões acontece como um relatório que utiliza as viagens cadastradas para reunir informações de combustívél e processá-las em um resultado agrupado pelos veículos.

## Setup

Criar um banco de dados postgres.

```sql
CREATE DATABASE aps8;
```

Criar os schemas necessários no banco.

```sql
CREATE SCHEMA security;
CREATE SCHEMA general;
```

(Opcional) Modificar a senha padrão.

```yml
application:
  default-password: <senha>
```

(Opcional) Modificar a configuração do arquivo `aplication.yml` para `true` no caso de desejar registros de exemplo por padrão.

```yml
application:
  input-test-data: true
```

Com o java 21 instalado rodar os comandos abaixo para instalar e rodar a aplicação.

```bash
./mvnw install
./mvnw spring-boot:run
```