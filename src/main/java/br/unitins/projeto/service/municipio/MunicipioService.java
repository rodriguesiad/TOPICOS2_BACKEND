package br.unitins.projeto.service.municipio;

import java.util.List;

import br.unitins.projeto.dto.municipio.MunicipioDTO;
import br.unitins.projeto.dto.municipio.MunicipioResponseDTO;
import jakarta.validation.Valid;

public interface MunicipioService {

    List<MunicipioResponseDTO> getAll(int page, int pageSize);

    MunicipioResponseDTO findById(Long id);

    MunicipioResponseDTO create(@Valid MunicipioDTO productDTO);

    MunicipioResponseDTO update(Long id, @Valid MunicipioDTO productDTO);

    void delete(Long id);

    List<MunicipioResponseDTO> findByNome(String descricao, int page, int pageSize);

    Long count();

    Long countByNome(String nome);

    List<MunicipioResponseDTO> findByEstado(Long idEstado);
}
