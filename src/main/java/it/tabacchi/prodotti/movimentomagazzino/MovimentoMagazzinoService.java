package it.tabacchi.prodotti.movimentomagazzino;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class MovimentoMagazzinoService implements IMovimentoMagazzinoService {

    private final MovimentoMagazzinoRepository mmrepository;

    public MovimentoMagazzinoService(MovimentoMagazzinoRepository mmrepository) {
        this.mmrepository = mmrepository;
    }

    @Override
    public GraficoDto getDatiGrafico(GraficoRequest request) {
        GraficoDto graficoDto = new GraficoDto();
        List<GuadagnoDto> guadagni = new ArrayList<>();

        for (int i = 1; i <= request.numeroElementi(); i++) {
            DateGrafico dateGrafico = convertToDateGrafico(request, i);
            LocalDate inizio = dateGrafico.dataInizio;
            LocalDate fine = dateGrafico.dataFine;
            if (i == 1) {
               graficoDto.setDataInizio(inizio);
            }
            if (i == request.numeroElementi()) {
                graficoDto.setDataFine(fine);
            }
            BigDecimal ricavo = mmrepository.getRicavoTotale(inizio, fine);
            BigDecimal guadagno = mmrepository.getGuadagnoTotale(inizio, fine);
            BigDecimal media = mmrepository.getGuadagnoMedio(inizio, fine);
            BigDecimal minimo = mmrepository.getGuadagnoMinimo(inizio, fine);
            BigDecimal massimo = mmrepository.getGuadagnoMassimo(inizio, fine);
            GuadagnoDto guadagnoDto = new GuadagnoDto(guadagno, dateGrafico.dataInizio, dateGrafico.dataFine, null);
            guadagni.add(guadagnoDto);

        }
        //BigDecimal ricavoTotale = repository.getRicavoTotale(request.getDataInizio(), request.getDataFine());

        // Popola graficoDto con i dati ottenuti dal repository
        return graficoDto;
    }

    private DateGrafico convertToDateGrafico(GraficoRequest request, int precedente) {
        DateGrafico dateGrafico = new DateGrafico();

        switch (request.tipoGrafico()){
            case SETTIMANALE -> {
                dateGrafico.dataInizio = LocalDate.now().minusWeeks(precedente);
                dateGrafico.dataFine = LocalDate.now();
            }
            case MENSILE -> {
                dateGrafico.dataInizio = LocalDate.now().minusMonths(precedente);
                dateGrafico.dataFine = LocalDate.now();
            }
            case ANNUALE -> {
                dateGrafico.dataInizio = LocalDate.now().minusYears(precedente);
                dateGrafico.dataFine = LocalDate.now();
            }
        }
        
        return dateGrafico;
    }
    

}

class DateGrafico {
    LocalDate dataInizio;
    LocalDate dataFine;
}
