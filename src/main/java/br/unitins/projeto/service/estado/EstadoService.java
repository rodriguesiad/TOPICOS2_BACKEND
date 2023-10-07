package br.unitins.projeto.service.estado;

import java.util.List;

import br.unitins.projeto.dto.estado.EstadoDTO;
import br.unitins.projeto.dto.estado.EstadoResponseDTO;

public interface EstadoService {

    List<EstadoResponseDTO> getAll();

    EstadoResponseDTO findById(Long id);

    EstadoResponseDTO create(EstadoDTO productDTO);

    EstadoResponseDTO update(Long id, EstadoDTO productDTO);

    List<EstadoResponseDTO> findAllPaginado(int pageNumber, int pageSize);

    void delete(Long id);

    List<EstadoResponseDTO> findByNome(String nome, int pageNumber, int pageSize);

    Long count();

    Long countBySigla(String sigla);


}
