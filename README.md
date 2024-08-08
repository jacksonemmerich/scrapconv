# Getting Started
Vamos criar um projeto utilizando Spring Boot 3, você pode seguir os seguintes passos. Este exemplo incluirá a criação de uma aplicação Spring Boot, a definição de uma rota REST para receber os parâmetros, e a utilização do Jsoup para realizar o web scraping.

### Passo 1: Configurar o Projeto Spring Boot

Primeiro, crie um novo projeto Spring Boot. Você pode usar o Spring Initializr (https://start.spring.io/) para gerar a estrutura do projeto.

Selecione as seguintes dependências:
- Spring Web
- Spring Boot DevTools

### Passo 2: Adicionar Dependência do Jsoup

Adicione a dependência do Jsoup no seu arquivo `pom.xml`:

```xml
<dependency>
    <groupId>org.jsoup</groupId>
    <artifactId>jsoup</artifactId>
    <version>1.14.3</version>
</dependency>
```

### Passo 3: Criar o Controller

Crie um controller para receber as requisições e realizar o web scraping. Aqui está um exemplo básico de como isso pode ser feito:

```java
package com.example.webscraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class WebScraperController {

    @GetMapping("/scrape")
    public String scrape(
            @RequestParam(name = "TipoDocumento") int tipoDocumento,
            @RequestParam(name = "Ano") int ano,
            @RequestParam(name = "Mes", required = false) String mes,
            @RequestParam(name = "NumeroDocumento", required = false) String numeroDocumento,
            @RequestParam(name = "IdProcedimentoLicitatorio", required = false) String idProcedimentoLicitatorio,
            @RequestParam(name = "CodigoUnidadeGestora", required = false) String codigoUnidadeGestora,
            @RequestParam(name = "Cnpj") String cnpj,
            @RequestParam(name = "RazaoSocial", required = false) String razaoSocial,
            @RequestParam(name = "Aditivo", required = false) String aditivo,
            @RequestParam(name = "Objeto", required = false) String objeto,
            @RequestParam(name = "__RequestVerificationToken") String requestVerificationToken) {
        
        String url = "URL_DO_SITE_ALVO";
        try {
            Document document = Jsoup.connect(url)
                .data("TipoDocumento", String.valueOf(tipoDocumento))
                .data("Ano", String.valueOf(ano))
                .data("Mes", mes != null ? mes : "")
                .data("NumeroDocumento", numeroDocumento != null ? numeroDocumento : "")
                .data("IdProcedimentoLicitatorio", idProcedimentoLicitatorio != null ? idProcedimentoLicitatorio : "")
                .data("CodigoUnidadeGestora", codigoUnidadeGestora != null ? codigoUnidadeGestora : "")
                .data("Cnpj", cnpj)
                .data("RazaoSocial", razaoSocial != null ? razaoSocial : "")
                .data("Aditivo", aditivo != null ? aditivo : "")
                .data("Objeto", objeto != null ? objeto : "")
                .data("__RequestVerificationToken", requestVerificationToken)
                .post();

            // Processar o documento como necessário
            return document.html();

        } catch (IOException e) {
            e.printStackTrace();
            return "Erro ao realizar o web scraping: " + e.getMessage();
        }
    }
}
```

### Passo 4: Executar a Aplicação

Execute sua aplicação Spring Boot. Você pode fazer isso a partir de sua IDE ou usando o Maven:

```sh
mvn spring-boot:run
```

### Testando a API

Para testar a API, você pode usar ferramentas como Postman ou curl para enviar uma requisição GET para o endpoint `/scrape` com os parâmetros necessários.

Exemplo de requisição curl, troque o cnpj para visualizar os dados do seu município:

```sh
curl -G \
  --data-urlencode "TipoDocumento=2" \
  --data-urlencode "Ano=2024" \
  --data-urlencode "Cnpj=05.903.125/0001-45" \
  --data-urlencode "__RequestVerificationToken=CfDJ8DbC90TdULxJvfMvFleupFKBMK0BKAt36UDbRNfj23RNnbiLhMFJjAi4dmHJ_6-Jtavz8g51AB2TWS7HbJCszAl1HGN51pp6Mdmm0-JFZOO927D8cdI6VfrRT8cmOMU90ysIrxES3stDFccYoVQXyoY" \
  "http://localhost:8080/scrape"
```

Este exemplo básico demonstra como criar uma API Spring Boot para realizar web scraping usando o Jsoup. Dependendo da complexidade da página que você está raspando, você pode precisar ajustar a lógica de processamento do documento.

