package it.tabacchi.prodotti.prezzostorico;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import it.tabacchi.pagination.PaginatedResponse;
import it.tabacchi.pagination.PaginationInfoRequest;
import it.tabacchi.pagination.PaginationUse;
import it.tabacchi.prodotti.prodotto.Prodotto;
import it.tabacchi.prodotti.prodotto.ProdottoRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class PrezzoStoricoService implements IPrezzoStoricoService {

    private final PrezzoStoricoRepository prezzoStoricoRepository;
    private final PrezzoStoricoMapper prezzoStoricoMapper;
    private final ProdottoRepository prodottoRepository;

    public PrezzoStoricoService(PrezzoStoricoRepository prezzoStoricoRepository, PrezzoStoricoMapper prezzoStoricoMapper, ProdottoRepository prodottoRepository) {
        this.prezzoStoricoRepository = prezzoStoricoRepository;
        this.prezzoStoricoMapper = prezzoStoricoMapper;
        this.prodottoRepository = prodottoRepository;
    }

    @Override
    public void bulkInsert(List<PrezzoStoricoRequest> prezzoStoricoRequests, LocalDate dataInizio, LocalDate dataFine) {

        Optional<PrezzoStorico> prezzoStorico = prezzoStoricoRepository.findOne();

        if (prezzoStorico.isPresent() && prezzoStorico.get().getDataInizio().equals(dataInizio)) {
            throw new IllegalArgumentException("Esiste già un prezzo storico con la stessa data di inizio: " + dataInizio);
        }
        if (prezzoStorico.isPresent() && prezzoStorico.get().getDataFine().equals(dataFine)) {
            throw new IllegalArgumentException("Esiste già un prezzo storico con la stessa data di fine: " + dataFine);
        }

       for (PrezzoStoricoRequest request : prezzoStoricoRequests) {
            Prodotto prodotto = prodottoRepository.findByBarcode(request.barcodeProdotto())
                    .orElseThrow(() -> new EntityNotFoundException("Prodotto con barcode " + request.barcodeProdotto() + " non trovato"));
            PrezzoStorico prezzoStoricoEntity = prezzoStoricoMapper.toEntity(request);
            prezzoStoricoEntity.setDataInizio(dataInizio);
            prezzoStoricoEntity.setDataFine(dataFine);
            prezzoStoricoEntity.setProdotto(prodotto);
            prezzoStoricoRepository.save(prezzoStoricoEntity);
        }
       
    }

    @Override
    public PrezzoStoricoDto update(Long id, PrezzoStoricoRequest prezzoStoricoRequest, LocalDate dataInizio, LocalDate dataFine) {
        PrezzoStorico prezzoStorico = prezzoStoricoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Prezzo storico con ID " + id + " non trovato"));

        Prodotto prodotto = prodottoRepository.findByBarcode(prezzoStoricoRequest.barcodeProdotto())
                .orElseThrow(() -> new EntityNotFoundException("Prodotto con barcode " + prezzoStoricoRequest.barcodeProdotto() + " non trovato"));

        prezzoStorico.setPrezzoVendita(prezzoStoricoRequest.prezzoVendita());
        prezzoStorico.setPrezzoAcquisto(prezzoStoricoRequest.prezzoAcquisto());
        prezzoStorico.setProdotto(prodotto);
        prezzoStorico.setDataInizio(dataInizio);
        prezzoStorico.setDataFine(dataFine);

        prezzoStorico = prezzoStoricoRepository.save(prezzoStorico);
        return prezzoStoricoMapper.toDto(prezzoStorico);
    }

    @Override
    public PaginatedResponse<List<PrezzoStoricoDto>> search(PaginationInfoRequest paginationInfo, PrezzoStoricoFilter filter) {

        Specification<PrezzoStorico> spec = Specification.where((Specification<PrezzoStorico>) null)
                .and(PrezzoStoricoSpec.byCategoria(filter.getCategoria()))
                .and(PrezzoStoricoSpec.isAttivo(filter.isAttivo()));
       
        Page<PrezzoStorico> page = prezzoStoricoRepository.findAll(spec, PaginationUse.pagination(paginationInfo));
        return PaginationUse.buildPaginatedResponse(page, prezzoStoricoMapper::toDtoList, paginationInfo);
    }

    @Override
    public PrezzoStoricoDto getPrezzoStoricoById(Long id) {
        PrezzoStorico prezzoStorico = prezzoStoricoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Prezzo storico con ID " + id + " non trovato"));
        return prezzoStoricoMapper.toDto(prezzoStorico);
    }

}
