package br.unitins.projeto.dto.produto;

public record ProdutoDTO(

    String nome,
    String descricao,
    Double preco,
    Double peso,
    Boolean ativo,
    Integer porte,
    Integer estoque, 
    Long idRaca,
    Long idCategoria,
    Long idEspecie
) {
}
