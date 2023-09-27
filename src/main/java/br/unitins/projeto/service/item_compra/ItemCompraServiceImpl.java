package br.unitins.projeto.service.item_compra;

import br.unitins.projeto.dto.item_compra.ItemCompraDTO;
import br.unitins.projeto.dto.item_compra.ItemCompraResponseDTO;
import br.unitins.projeto.model.ItemCompra;
import br.unitins.projeto.model.Produto;
import br.unitins.projeto.repository.ItemCompraRepository;
import br.unitins.projeto.repository.ProdutoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.ws.rs.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ItemCompraServiceImpl implements ItemCompraService {
    @Inject
    ItemCompraRepository repository;

    @Inject
    ProdutoRepository produtoRepository;

    @Override
    public List<ItemCompraResponseDTO> getAll() {
        List<ItemCompra> list = repository.listAll();
        return list.stream().map(item -> ItemCompraResponseDTO.valueOf(item)).collect(Collectors.toList());
    }

    @Override
    public ItemCompraResponseDTO findById(Long id) {
        ItemCompra itemCompra = repository.findById(id);

        if (itemCompra == null)
            throw new NotFoundException("Item não encontrado.");

        return ItemCompraResponseDTO.valueOf(itemCompra);
    }

    @Override
    @Transactional
    public ItemCompraResponseDTO create(ItemCompra entity) throws ConstraintViolationException {
        repository.persist(entity);
        return ItemCompraResponseDTO.valueOf(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public ItemCompra toModel(@Valid ItemCompraDTO dto) {
        ItemCompra entity = new ItemCompra();

        Produto produto = this.getProduto(dto.idProduto());
        entity.setProduto(produto);
        entity.setPreco(produto.getPreco());
        entity.setQuantidade(dto.quantidade());

        return entity;
    }

    private Produto getProduto(Long id) {
        Produto produto = produtoRepository.findById(id);

        if (produto == null)
            throw new NotFoundException("Produto não encontrado.");

        return produto;
    }

}
