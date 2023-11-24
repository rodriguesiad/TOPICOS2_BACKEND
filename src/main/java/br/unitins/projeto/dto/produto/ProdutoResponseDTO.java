package br.unitins.projeto.dto.produto;

import br.unitins.projeto.dto.categoria.CategoriaResponseDTO;
import br.unitins.projeto.dto.especie.EspecieResponseDTO;
import br.unitins.projeto.dto.raca.RacaResponseDTO;
import br.unitins.projeto.model.PorteAnimal;
import br.unitins.projeto.model.Produto;

public record ProdutoResponseDTO(
        Long id,
        String nome,
        String descricao,
        Double preco,
        Boolean ativo,
        Double peso,
        Integer estoque,
        PorteAnimal porte,
        RacaResponseDTO raca,
        EspecieResponseDTO especie,
        CategoriaResponseDTO categoria,
        String nomeImagem

) {
    public ProdutoResponseDTO(Produto entity) {
        this(entity.getId(),
        entity.getNome(),
        entity.getDescricao(),
        entity.getPreco(),
        entity.getAtivo(),
        entity.getPeso(),
        entity.getEstoque(),
        entity.getPorteAnimal(),
        new RacaResponseDTO(entity.getRaca()),
        new EspecieResponseDTO(entity.getEspecie()),
        new CategoriaResponseDTO(entity.getCategoria()),
        entity.getNomeImagem());
    }
}
