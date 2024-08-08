package pvh.prefeitura.scrapconv.controller;

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

        String url = "https://transparencia.ro.gov.br/convenios/ConsultaConvenios";
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
