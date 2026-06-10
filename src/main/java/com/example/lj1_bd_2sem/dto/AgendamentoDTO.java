package com.example.lj1_bd_2sem.dto;

import java.time.LocalTime;

public class AgendamentoDTO {
    private Integer id;
    private LocalTime horaAgendado;
    private String clienteNome;
    private String profissionalNome;
    private String servicoNome;
    private String statusConclusao;

    public AgendamentoDTO() {
    }

    public AgendamentoDTO(Integer id, LocalTime horaAgendado, String clienteNome, String profissionalNome, String servicoNome, String statusConclusao) {
        this.id = id;
        this.horaAgendado = horaAgendado;
        this.clienteNome = clienteNome;
        this.profissionalNome = profissionalNome;
        this.servicoNome = servicoNome;
        this.statusConclusao = statusConclusao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalTime getHoraAgendado() {
        return horaAgendado;
    }

    public void setHoraAgendado(LocalTime horaAgendado) {
        this.horaAgendado = horaAgendado;
    }

    public String getClienteNome() {
        return clienteNome;
    }

    public void setClienteNome(String clienteNome) {
        this.clienteNome = clienteNome;
    }

    public String getProfissionalNome() {
        return profissionalNome;
    }

    public void setProfissionalNome(String profissionalNome) {
        this.profissionalNome = profissionalNome;
    }

    public String getServicoNome() {
        return servicoNome;
    }

    public void setServicoNome(String servicoNome) {
        this.servicoNome = servicoNome;
    }

    public String getStatusConclusao() {
        return statusConclusao;
    }

    public void setStatusConclusao(String statusConclusao) {
        this.statusConclusao = statusConclusao;
    }
}