package br.com.michaelmartins.desafiobanco.dto;

import java.util.Map;

public class SolicitacaoTransferencia {

    private String contaSolicitante;
    private Double valorTransferencia;
    private String contaBeneficiario;

    public SolicitacaoTransferencia() {
    }

    public SolicitacaoTransferencia(Map<String, String> mapaTransferencia) {
        this.contaSolicitante = mapaTransferencia.get("Conta do Solicitante");
        this.valorTransferencia = Double.parseDouble(mapaTransferencia.get("Valor"));
        this.contaBeneficiario = mapaTransferencia.get("Conta do Beneficiário");
    }

    public String getContaSolicitante() {
        return contaSolicitante;
    }

    public void setContaSolicitante(String contaSolicitante) {
        this.contaSolicitante = contaSolicitante;
    }

    public Double getValorTransferencia() {
        return valorTransferencia;
    }

    public void setValorTransferencia(Double valorTransferencia) {
        this.valorTransferencia = valorTransferencia;
    }

    public String getContaBeneficiario() {
        return contaBeneficiario;
    }

    public void setContaBeneficiario(String contaBeneficiario) {
        this.contaBeneficiario = contaBeneficiario;
    }

    @Override
    public String toString() {
        return "SolicitacaoTransferencia{" +
                "contaSolicitante='" + contaSolicitante + '\'' +
                ", valorTransferencia='" + valorTransferencia + '\'' +
                ", contaBeneficiario='" + contaBeneficiario + '\'' +
                '}';
    }
}
