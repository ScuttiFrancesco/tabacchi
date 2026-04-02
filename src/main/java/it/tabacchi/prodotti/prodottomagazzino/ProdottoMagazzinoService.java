package it.tabacchi.prodotti.prodottomagazzino;

import it.tabacchi.enums.Categoria;
import it.tabacchi.exception.DuplicateDataException;
import it.tabacchi.pagination.PaginatedResponse;
import it.tabacchi.pagination.PaginationInfoRequest;
import it.tabacchi.pagination.PaginationUse;
import it.tabacchi.prodotti.prodotto.Prodotto;
import it.tabacchi.prodotti.prodotto.ProdottoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdottoMagazzinoService implements IProdottoMagazzinoService {

    private final ProdottoMagazzinoRepository pmrepository;
    private final ProdottoMagazzinoMapper pmmapper;
    private final ProdottoRepository prepository;

    public ProdottoMagazzinoService(ProdottoMagazzinoRepository pmrepository, ProdottoMagazzinoMapper pmmapper, ProdottoRepository prepository) {
        this.pmrepository = pmrepository;
        this.pmmapper = pmmapper;
        this.prepository = prepository;
    }

    @Override
    public ProdottoMagazzinoDto create(ProdottoMagazzinoRequest request) {

        Prodotto prodotto = prepository.findByBarcode(request.barcodeProdotto())
                .orElseThrow(() -> new EntityNotFoundException("Il prodotto con barcode " + request.barcodeProdotto() + " non esiste."));
        if (prodotto.getProdottoMagazzino() != null){
            throw new DuplicateDataException("Esiste già un prodotto in magazzino associato al prodotto con barcode: " + request.barcodeProdotto());
        }

        ProdottoMagazzino pmEntity = pmmapper.toEntity(request);
        pmEntity.setProdotto(prodotto);
        pmEntity.setScortaAttuale(0);
        pmEntity = pmrepository.save(pmEntity);

        return pmmapper.toDto(pmEntity);
    }

    @Override
    public ProdottoMagazzinoDto update(ProdottoMagazzinoRequest update, Long id) {

        ProdottoMagazzino pmEntity = pmrepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Il prodotto in magazzino con id " + id + " non esiste."));
        if (pmrepository.existsByProdottoBarcodeAndIdNot(update.barcodeProdotto(), id)){
            throw new DuplicateDataException("Esiste già un prodotto in magazzino con il barcode: " + update.barcodeProdotto());
        }
        Prodotto prodotto = prepository.findByBarcode(update.barcodeProdotto())
                .orElseThrow(() -> new EntityNotFoundException("Il prodotto con barcode " + update.barcodeProdotto() + " non esiste."));
        pmmapper.toEntityUpdate(update, pmEntity);
        pmEntity.setProdotto(prodotto);
        pmEntity.setId(id);
        pmEntity = pmrepository.save(pmEntity);
        return pmmapper.toDto(pmEntity);
    }

    @Override
    public ProdottoMagazzinoDto getById(Long id) {
        return pmmapper.toDto(pmrepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Il prodotto in magazzino con id " + id + " non esiste.")));
    }

    @Override
    public void delete(Long id) {
        if (!pmrepository.existsById(id)){
            throw new EntityNotFoundException("Il prodotto in magazzino con id " + id + " non esiste.");
        }
        pmrepository.deleteById(id);
    }

    @Override
    public PaginatedResponse<List<ProdottoMagazzinoList>> getAll(PaginationInfoRequest paginationInfo) {

        Page<ProdottoMagazzino> page = pmrepository.findAll(PaginationUse.pagination(paginationInfo));
        return PaginationUse.buildPaginatedResponse(page, pmmapper::toDtoList, paginationInfo);
    }

    @Override
    public PaginatedResponse<List<ProdottoMagazzinoList>> getAllByCategoria(Categoria categoria, PaginationInfoRequest paginationInfo) {
        Page<ProdottoMagazzino> page = pmrepository.findAllByProdottoCategoria(categoria, PaginationUse.pagination(paginationInfo));
        return PaginationUse.buildPaginatedResponse(page, pmmapper::toDtoList, paginationInfo);
    }
}
