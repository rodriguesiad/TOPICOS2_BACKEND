package br.unitins.projeto.service.metodo_recebimento.boleto;

import br.unitins.projeto.dto.metodo.recebimento.boleto.BoletoRecebimentoDTO;
import br.unitins.projeto.dto.metodo.recebimento.boleto.BoletoRecebimentoResponseDTO;
import br.unitins.projeto.model.BoletoRecebimento;
import br.unitins.projeto.repository.BoletoRecebimentoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class BoletoRecebimentoServiceImpl implements BoletoRecebimentoService {

    @Inject
    BoletoRecebimentoRepository repository;

    @Override
    public List<BoletoRecebimentoResponseDTO> listAllInativo(int page, int pageSize) {
        List<BoletoRecebimento> list = repository.listAllInativo().page(page, pageSize).list();
        return list.stream().map(BoletoRecebimentoResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public BoletoRecebimentoResponseDTO findByAtivo() {
        BoletoRecebimento boletoRecebimento = repository.findByAtivo(true);

        if (boletoRecebimento == null)
            boletoRecebimento = new BoletoRecebimento();

        return new BoletoRecebimentoResponseDTO(boletoRecebimento);
    }

    @Override
    public BoletoRecebimentoResponseDTO findById(Long id) {
        BoletoRecebimento boletoRecebimento = repository.findById(id);

        if (boletoRecebimento == null)
            throw new NotFoundException("Método de Pagamento não encontrado.");

        return new BoletoRecebimentoResponseDTO(boletoRecebimento);
    }

    @Transactional
    public BoletoRecebimentoResponseDTO create(@Valid BoletoRecebimentoDTO dto) {
        BoletoRecebimento boletoRecebimento = new BoletoRecebimento();
        boletoRecebimento.setBanco(dto.banco());
        boletoRecebimento.setNome(dto.nome());
        boletoRecebimento.setCnpj(dto.cnpj());
        boletoRecebimento.setAgencia(dto.agencia());
        boletoRecebimento.setConta(dto.conta());
        boletoRecebimento.setAtivo(true);
        this.UpdateAtivo();
        repository.persist(boletoRecebimento);

        return new BoletoRecebimentoResponseDTO(boletoRecebimento);
    }

    @Override
    @Transactional
    public BoletoRecebimentoResponseDTO update(Long id, @Valid BoletoRecebimentoDTO dto) {

        BoletoRecebimento boletoRecebimento = repository.findById(id);

        boletoRecebimento.setAgencia(dto.agencia());
        boletoRecebimento.setCnpj(dto.cnpj());
        boletoRecebimento.setNome(dto.nome());
        boletoRecebimento.setBanco(dto.banco());
        boletoRecebimento.setConta(dto.conta());

        return new BoletoRecebimentoResponseDTO(boletoRecebimento);
    }

    private void UpdateAtivo() {
        BoletoRecebimento boletoRecebimentoAtivo = repository.findByAtivo(true);
        if (boletoRecebimentoAtivo != null) {
            boletoRecebimentoAtivo.setAtivo(false);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<BoletoRecebimentoResponseDTO> findByCNPJ(String cnpj, int page, int pageSize) {
        List<BoletoRecebimento> list = repository.findByCNPJ(cnpj).page(page, pageSize).list();
        return list.stream().map(BoletoRecebimentoResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public long count() {
        return repository.listAllInativo().count();
    }

    @Override
    public long countByCNPJ(String cnpj) {
        return repository.findByCNPJ(cnpj).count();
    }

}
