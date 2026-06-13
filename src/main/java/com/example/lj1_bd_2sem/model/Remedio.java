package com.example.lj1_bd_2sem.model;


public class Remedio {
    private Integer idProduto;
    private Boolean usaReceita;
    private String crmMedico;

    public Remedio() {
    }

    public Remedio(Integer idProduto, Boolean usaReceita, String crmMedico) {
        this.idProduto = idProduto;
        this.usaReceita = usaReceita;
        this.crmMedico = crmMedico;
    }

    public Integer getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
    }

    public Boolean getUsaReceita() {
        return usaReceita;
    }

    public void setUsaReceita(Boolean usaReceita) {
        this.usaReceita = usaReceita;
    }

    public String getCrmMedico() {
        return crmMedico;
    }

    public void setCrmMedico(String crmMedico) {
        this.crmMedico = crmMedico;
    }
}
