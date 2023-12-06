package br.unitins.projeto.service.metodo_recebimento.pix;

import java.util.List;
import java.util.stream.Collectors;

import br.unitins.projeto.dto.metodo.recebimento.pix.PixRecebimentoDTO;
import br.unitins.projeto.dto.metodo.recebimento.pix.PixRecebimentoResponseDTO;
import br.unitins.projeto.model.PixRecebimento;
import br.unitins.projeto.model.TipoChavePix;
import br.unitins.projeto.repository.PixRecebimentoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class PixRecebimentoServiceImpl implements PixRecebimentoService {

    @Inject
    PixRecebimentoRepository repository;

    @Override
    public List<PixRecebimentoResponseDTO> findAllInativo(int page, int pageSize){
        List<PixRecebimento> list = repository.listAllInativo().page(page, pageSize).list();
        return list.stream().map(PixRecebimentoResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public PixRecebimentoResponseDTO findByAtivo() {
        PixRecebimento pixRecebimento = repository.findByAtivo();

        if (pixRecebimento == null)
            pixRecebimento = new PixRecebimento();

        return new PixRecebimentoResponseDTO(pixRecebimento);

    }

    @Override
    public PixRecebimentoResponseDTO findById(Long id) {
        PixRecebimento pixRecebimento = repository.findById(id);

        if (pixRecebimento == null)
            throw new NotFoundException("Método de Pagamento não encontrado.");

        return new PixRecebimentoResponseDTO(pixRecebimento);
    }

    @Override
    @Transactional
    public PixRecebimentoResponseDTO create(@Valid PixRecebimentoDTO dto) {
        PixRecebimento pixRecebimento = new PixRecebimento();
        pixRecebimento.setChave(dto.chave());
        pixRecebimento.setTipoChavePix(TipoChavePix.valueOf(dto.tipoChavePix()));
        pixRecebimento.setAtivo(true);
        this.UpdateAtivo();
        repository.persist(pixRecebimento);

        return new PixRecebimentoResponseDTO(pixRecebimento);
    }

    @Override
    @Transactional
    public PixRecebimentoResponseDTO update(Long id, @Valid PixRecebimentoDTO dto) {

        PixRecebimento entity = repository.findById(id);

        entity.setTipoChavePix(TipoChavePix.valueOf(dto.tipoChavePix()));
        entity.setChave(dto.chave());

        return new PixRecebimentoResponseDTO(entity);
    }

    private void UpdateAtivo() {
        PixRecebimento pixRecebimentoAtivo = repository.findByAtivo();
        if (pixRecebimentoAtivo != null) {
            pixRecebimentoAtivo.setAtivo(false);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<PixRecebimentoResponseDTO> findByChave(String chave, int page, int pageSize) {
        List<PixRecebimento> list = repository.findByChave(chave).page(page, pageSize).list();
        return list.stream().map(PixRecebimentoResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public long count() {
        return repository.listAllInativo().count();
    }

    @Override
    public long countByChave(String chave) {
        return repository.findByChave(chave).count();
    }
}
