package com.example.lj1_bd_2sem.model;

import java.time.LocalTime;

public class Agenda {
    private Integer id;
    private Integer clienteId;
    private Integer profissionalId;
    private Integer servicoId;
    private LocalTime horaAgendado;
    private String statusConclusao; // PENDENTE, EM ANDAMENTO, CONCLUIDO, CANCELADO

    public Agenda() {
    }

    public Agenda(Integer id, Integer clienteId, Integer profissionalId, Integer servicoId,
                  LocalTime horaAgendado, String statusConclusao) {
        this.id = id;
        this.clienteId = clienteId;
        this.profissionalId = profissionalId;
        this.servicoId = servicoId;
        this.horaAgendado = horaAgendado;
        this.statusConclusao = statusConclusao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public Integer getProfissionalId() {
        return profissionalId;
    }

    public void setProfissionalId(Integer profissionalId) {
        this.profissionalId = profissionalId;
    }

    public Integer getServicoId() {
        return servicoId;
    }

    public void setServicoId(Integer servicoId) {
        this.servicoId = servicoId;
    }

    public LocalTime getHoraAgendado() {
        return horaAgendado;
    }

    public void setHoraAgendado(LocalTime horaAgendado) {
        this.horaAgendado = horaAgendado;
    }

    public String getStatusConclusao() {
        return statusConclusao;
    }

    public void setStatusConclusao(String statusConclusao) {
        this.statusConclusao = statusConclusao;
    }
}