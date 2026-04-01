package it.tabacchi.prodotti.movimentomagazzino;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import it.tabacchi.enums.TipoMovimento;
import it.tabacchi.pagination.PaginatedResponse;
import it.tabacchi.pagination.PaginationInfoRequest;
import it.tabacchi.pagination.PaginationUse;
import it.tabacchi.prodotti.dettagliomovimento.DettaglioMovimento;
import it.tabacchi.prodotti.dettagliomovimento.DettaglioMovimentoMapper;
import it.tabacchi.prodotti.dettagliomovimento.DettaglioMovimentoRepository;
import it.tabacchi.prodotti.dettagliomovimento.DettaglioMovimentoRequest;
import it.tabacchi.prodotti.grafico.DateGraficoApp;
import it.tabacchi.prodotti.grafico.GraficoDto;
import it.tabacchi.prodotti.grafico.GraficoRequest;
import it.tabacchi.prodotti.grafico.GuadagnoDto;
import it.tabacchi.prodotti.prodotto.Prodotto;
import it.tabacchi.prodotti.prodotto.ProdottoRepository;
import it.tabacchi.prodotti.prodottomagazzino.ProdottoMagazzino;
import it.tabacchi.prodotti.prodottomagazzino.ProdottoMagazzinoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MovimentoMagazzinoService implements IMovimentoMagazzinoService {

    private final MovimentoMagazzinoRepository mmrepository;
    private final MovimentoMagazzinoMapper mmmapper;
    private final DettaglioMovimentoRepository dmrepository;
    private final DettaglioMovimentoMapper dmmapper;
    private final ProdottoRepository prepository;
    private final ProdottoMagazzinoRepository pmrepository;

    public MovimentoMagazzinoService(MovimentoMagazzinoRepository mmrepository,
                                     MovimentoMagazzinoMapper mmmapper,
                                     DettaglioMovimentoRepository dmrepository,
                                     DettaglioMovimentoMapper dmmapper,
                                     ProdottoRepository prepository,
                                     ProdottoMagazzinoRepository pmrepository) {
        this.mmrepository = mmrepository;
        this.mmmapper = mmmapper;
        this.dmrepository = dmrepository;
        this.dmmapper = dmmapper;
        this.prepository = prepository;
        this.pmrepository = pmrepository;
    }

    @Override
    @Transactional
    public MovimentoMagazzinoDto create(MovimentoMagazzinoRequest request) {
        MovimentoMagazzino movimentoEntity = mmmapper.toEntity(request);

        for (DettaglioMovimentoRequest dettaglioRequest : request.dettagliMovimento()) {
            DettaglioMovimento dettaglioEntity = gestisciDettaglioMovimento(dettaglioRequest, request.tipoMovimento(), "create");
            movimentoEntity.addDettaglioMovimento(dettaglioEntity);
        }
        movimentoEntity.setQuantitaProdotti(movimentoEntity.getDettagliMovimento().size());

        BigDecimal ricavo = movimentoEntity.getDettagliMovimento().stream()
                .map(d -> d.getPrezzoVendita().multiply(BigDecimal.valueOf(d.getQuantita())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal guadagno = movimentoEntity.getDettagliMovimento().stream()
                .map(d -> d.getPrezzoVendita().subtract(d.getPrezzoAcquisto()).multiply(BigDecimal.valueOf(d.getQuantita())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        movimentoEntity.setRicavoTotale(ricavo);
        movimentoEntity.setGuadagnoTotale(guadagno);
        movimentoEntity = mmrepository.save(movimentoEntity);

        return mmmapper.toDto(movimentoEntity);
    }

    @Override
    @Transactional
    public MovimentoMagazzinoDto update(MovimentoMagazzinoRequest request) {
        if (!mmrepository.existsById(request.id())){
            throw new EntityNotFoundException("Movimento di magazzino non trovato con id: " + request.id());
        }
        MovimentoMagazzino movimentoEntity = mmmapper.toEntity(request);
        movimentoEntity.getDettagliMovimento().clear();

        for (DettaglioMovimentoRequest dettaglioRequest : request.dettagliMovimento()) {
            DettaglioMovimento dettaglioEntity = gestisciDettaglioMovimento(dettaglioRequest, request.tipoMovimento(), "create");
            movimentoEntity.addDettaglioMovimento(dettaglioEntity);
        }
        movimentoEntity.setQuantitaProdotti(movimentoEntity.getDettagliMovimento().size());

        BigDecimal ricavo = movimentoEntity.getDettagliMovimento().stream()
                .map(d -> d.getPrezzoVendita().multiply(BigDecimal.valueOf(d.getQuantita())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal guadagno = movimentoEntity.getDettagliMovimento().stream()
                .map(d -> d.getPrezzoVendita().subtract(d.getPrezzoAcquisto()).multiply(BigDecimal.valueOf(d.getQuantita())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        movimentoEntity.setRicavoTotale(ricavo);
        movimentoEntity.setGuadagnoTotale(guadagno);
        movimentoEntity = mmrepository.save(movimentoEntity);

        return mmmapper.toDto(movimentoEntity);
    }

    @Override
    public MovimentoMagazzinoDto getById(Long id) {
        return mmmapper.toDto(mmrepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Movimento di magazzino non trovato con id: " + id)
        ));
    }

    @Override
    public PaginatedResponse<List<MovimentoMagazzinoList>> search(MovimentoMagazzinoFilter filter, PaginationInfoRequest paginationInfo) {

        Specification<MovimentoMagazzino> spec = Specification.where((Specification<MovimentoMagazzino>) null)
                .and(MovimentoMagazzinoSpec.byData(filter.getData()));

        Page<MovimentoMagazzino> movimenti = mmrepository.findAll(spec, PaginationUse.pagination(paginationInfo));
        return PaginationUse.buildPaginatedResponse(movimenti, mmmapper::toDtoList, paginationInfo);
    }

    @Override
    public void delete(Long id) {
        if (!mmrepository.existsById(id)) {
            throw new EntityNotFoundException("Movimento di magazzino non trovato con id: " + id);
        }
        mmrepository.deleteById(id);
    }

    private DettaglioMovimento gestisciDettaglioMovimento(DettaglioMovimentoRequest dettaglio, TipoMovimento tipoMovimento, String mode){
        ProdottoMagazzino prodottoMagazzino = pmrepository.findByProdottoBarcode(dettaglio.barcodeProdotto())
                .orElseThrow(() -> new EntityNotFoundException("Prodotto non trovato con barcode inserito" ));
        Prodotto prodotto = prepository.findByBarcode(dettaglio.barcodeProdotto()).orElseThrow(
                () -> new EntityNotFoundException("Prodotto non trovato con barcode inserito"));
        DettaglioMovimento dettaglioEntity =  new DettaglioMovimento();
        dettaglioEntity.setProdotto(prodotto);

        int quantitaDaAggiornare = 0;
        if (mode.equalsIgnoreCase("update")) {
            int quantitaPrecedente = dettaglioEntity.getQuantita();
             quantitaDaAggiornare = dettaglio.quantita() - quantitaPrecedente;
        }else{
            quantitaDaAggiornare = dettaglio.quantita();
        }
        prodottoMagazzino.aggiornaMagazzino(quantitaDaAggiornare, tipoMovimento);

        dettaglioEntity.setPrezzoAcquisto(prodottoMagazzino.getPrezzoAcquisto());
        dettaglioEntity.setPrezzoVendita(prodottoMagazzino.getPrezzoVendita());
        dettaglioEntity.setQuantita(dettaglio.quantita());

        pmrepository.save(prodottoMagazzino);
        return dettaglioEntity;
    }

    @Override
    public GraficoDto getDatiGrafico(GraficoRequest request) {
        GraficoDto graficoDto = new GraficoDto();
        List<GuadagnoDto> guadagni = new ArrayList<>();

        for (int i = 1; i <= request.numeroElementi(); i++) {
            DateGraficoApp dateGrafico = convertToDateGrafico(request, i);
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

    private DateGraficoApp convertToDateGrafico(GraficoRequest request, int precedente) {
        DateGraficoApp dateGrafico = new DateGraficoApp();

        switch (request.tipoGrafico()) {
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

