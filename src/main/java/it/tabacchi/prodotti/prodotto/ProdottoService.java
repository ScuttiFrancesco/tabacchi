package it.tabacchi.prodotti.prodotto;

import it.tabacchi.exception.DuplicateDataException;
import it.tabacchi.pagination.PaginatedResponse;
import it.tabacchi.pagination.PaginationInfoRequest;
import it.tabacchi.pagination.PaginationUse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProdottoService implements IProdottoService {

    private final ProdottoRepository prepository;
    private final ProdottoMapper pmapper;

    public ProdottoService(ProdottoRepository prepository, ProdottoMapper pmapper) {
        this.prepository = prepository;
        this.pmapper = pmapper;
    }

    @Override
    @Transactional
    public ProdottoDto create(ProdottoDto request) {

            if (prepository.existsByAamsCode(request.aamsCode())) {
                throw new DuplicateDataException("AAMS code già esistente");
            }

            Prodotto prodottoEntity = pmapper.toEntity(request);
            prodottoEntity.setAttivo(true);
            prodottoEntity = prepository.save(prodottoEntity);

            return pmapper.toDto(prodottoEntity);
    }

    @Override
    @Transactional
    public ProdottoDto update(ProdottoDto update, Long id) {

        Prodotto prodottoEntity = prepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Prodotto non trovato"));

        if (prepository.existsByBarcodeAndIdNot(update.barcode(), id) || prepository.existsByAamsCodeAndIdNot(update.aamsCode(), id)) {
            throw new DuplicateDataException("Barcode o AAMS code già esistente per un altro prodotto");
        }

        pmapper.toEntityUpdate(update, prodottoEntity);
        prodottoEntity.setId(id);
        prodottoEntity = prepository.save(prodottoEntity);

        return pmapper.toDto(prodottoEntity);
    }

    @Override
    public ProdottoDto getById(Long id) {
       return pmapper.toDto(prepository.findById(id)
               .orElseThrow(() -> new EntityNotFoundException("Prodotto non trovato")));
    }

    @Override
    public void delete(Long id) {
        if (!prepository.existsById(id)) {
            throw new EntityNotFoundException("Prodotto non trovato");
        }
        prepository.deleteById(id);
    }

    @Override
    public PaginatedResponse<List<ProdottoList>> search(ProdottoFilter filter, PaginationInfoRequest paginationInfo) {

        Specification<Prodotto> spec = Specification.where((Specification<Prodotto>) null)
            .and(ProdottoSpec.byCategoria(filter.getCategoria()))
            .and(ProdottoSpec.isAttivo(filter.isAttivo()));

        Page<Prodotto> prodotti = prepository.findAll(spec, PaginationUse.pagination(paginationInfo));
        return PaginationUse.buildPaginatedResponse(prodotti, pmapper::toDtoList, paginationInfo);
    }
}
