package br.unitins.projeto.service.estado;

import java.util.List;

import br.unitins.projeto.dto.estado.EstadoDTO;
import br.unitins.projeto.dto.estado.EstadoResponseDTO;
import jakarta.validation.Valid;

public interface EstadoService {

    List<EstadoResponseDTO> getAll();

    EstadoResponseDTO findById(Long id);

    EstadoResponseDTO create(@Valid EstadoDTO productDTO);

    EstadoResponseDTO update(Long id, @Valid EstadoDTO productDTO);

    List<EstadoResponseDTO> findAllPaginado(int pageNumber, int pageSize);

    void delete(Long id);

    List<EstadoResponseDTO> findByFiltro(String nome, String situacao, int pageNumber, int pageSize);

    Long count();

    Long countByFiltro(String nome, String situacao);


}
