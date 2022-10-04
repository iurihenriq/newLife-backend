package br.com.itbeta.newlife.service;

import br.com.itbeta.newlife.controller.dto.FuncionarioDto;
import br.com.itbeta.newlife.controller.dto.MoradorDto;
import br.com.itbeta.newlife.controller.form.FuncionarioForm;
import br.com.itbeta.newlife.controller.form.MoradorForm;
import br.com.itbeta.newlife.model.Funcionario;
import br.com.itbeta.newlife.model.Morador;
import br.com.itbeta.newlife.repository.ApartamentoRepository;
import br.com.itbeta.newlife.repository.FuncionarioRepository;
import br.com.itbeta.newlife.repository.MoradorRepository;
import br.com.itbeta.newlife.repository.projections.MoradorDetails;
import br.com.itbeta.newlife.repository.specifications.FuncionarioSpecification;
import br.com.itbeta.newlife.repository.specifications.MoradorSpecification;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class MoradoresService {

    private final MoradorRepository repository;
    private final ApartamentoRepository apartamentoRepository;

    public MoradorForm findById(Long idMorador) {
        Morador m = this.repository.findById(idMorador).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return new MoradorForm(m);
    }

    public void createMorador (MoradorForm form) {
        Morador m = Morador
                .builder()
                .nome(form.getNome())
                .rg(form.getRg())
                .cpf(form.getCpf())
                .telefonePrincipal(form.getTelefonePrincipal())
                .telefoneSecundario(form.getTelefoneSecundario())
                .observacoes(form.getObservacoes())
                .email(form.getEmail())
                .nomeContatoEmergencial(form.getNomeContatoEmergencial())
                .telefoneContatoEmergencial(form.getTelefoneContatoEmergencial())
                .build();
        m.addApartamentos(this.apartamentoRepository.findById(form.getApartamento()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
        this.repository.save(m);
    }

    public void updateMorador (Long idMorador, MoradorForm form) {
        Morador m = this.repository.findById(idMorador).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        m.addApartamentos(this.apartamentoRepository.findById(form.getApartamento()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
        m.update(form);
        repository.save(m);
    }

    public void deleteMorador (Long idMorador) {
        Morador m = this.repository.findById(idMorador).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        repository.delete(m);
    }

    public Page<Morador> findAll(Pageable pageable) {
        return this.repository.findAll(pageable);
    }

    public Page<Morador> findAll(Pageable pageable, String query) {
        return this.repository.findAll(MoradorSpecification.likeGenericQuery(query), pageable);
    }

    public List<MoradorDetails> findAllList() {
        return this.repository.findAllList();
    }

    public List<MoradorDetails> findAllList(String query) {
        return this.repository.findAllList(query);
    }

}
