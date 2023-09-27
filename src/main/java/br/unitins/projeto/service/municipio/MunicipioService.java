package br.unitins.projeto.service.municipio;

import java.util.List;

import br.unitins.projeto.dto.municipio.MunicipioDTO;
import br.unitins.projeto.dto.municipio.MunicipioResponseDTO;

public interface MunicipioService {

    List<MunicipioResponseDTO> getAll();

    MunicipioResponseDTO findById(Long id);

    MunicipioResponseDTO create(MunicipioDTO productDTO);

    MunicipioResponseDTO update(Long id, MunicipioDTO productDTO);

    void delete(Long id);

    List<MunicipioResponseDTO> findByDescricao(String descricao);

    Long count();

}
