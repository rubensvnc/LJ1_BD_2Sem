package com.example.lj1_bd_2sem.controller;

import com.example.lj1_bd_2sem.dao.ClienteDAO;
import com.example.lj1_bd_2sem.model.Cliente;
import com.example.lj1_bd_2sem.model.Usuario;
import com.example.lj1_bd_2sem.util.ScreenManager;
import com.example.lj1_bd_2sem.util.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import java.util.Optional;

public class HomeController {
    @FXML private Button gotoMarketBtn, gotoSalonBtn, gotoPharmacyBtn, btnRecarregar;
    @FXML private Label lblSaldo;
    private ClienteDAO clienteDAO = new ClienteDAO();

    @FXML
    public void initialize() {
        atualizarSaldo();
    }

    private void atualizarSaldo() {
        Usuario user = SessionManager.getUsuarioLogado();
        if (user != null && user.getPerfil().equals("CLIENTE")) {
            Cliente c = clienteDAO.buscarPorId(user.getId());
            if (c != null) lblSaldo.setText(String.format("Saldo: R$ %.2f", c.getBalanca()));
        } else {
            lblSaldo.setText("");
        }
    }

    @FXML
    public void recarregarSaldo() {
        Usuario user = SessionManager.getUsuarioLogado();
        if (user == null || !user.getPerfil().equals("CLIENTE")) {
            mostrarAlerta("Erro: Usuário não é cliente.");
            return;
        }
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Recarregar Saldo");
        dialog.setHeaderText("Digite o valor a adicionar (use ponto ou vírgula):");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String valorStr = result.get().trim().replace(",", ".");
            try {
                double valor = Double.parseDouble(valorStr);
                if (valor <= 0) {
                    mostrarAlerta("Valor deve ser positivo.");
                    return;
                }
                // Busca o cliente atual
                Cliente cliente = clienteDAO.buscarPorId(user.getId());
                if (cliente == null) {
                    mostrarAlerta("Cliente não encontrado no sistema.");
                    return;
                }
                double novoSaldo = cliente.getBalanca() + valor;
                clienteDAO.atualizarSaldo(user.getId(), novoSaldo);
                // Atualiza a sessão? O cliente não tem o saldo na sessão, mas atualizamos o label
                atualizarSaldo();
                mostrarAlerta("Recarga realizada! Novo saldo: R$ " + String.format("%.2f", novoSaldo));
            } catch (NumberFormatException e) {
                mostrarAlerta("Valor inválido. Use números, ponto ou vírgula.");
            }
        }
    }

    @FXML public void gotoMarket() { trocarTela("/vendas_produtos_mercado.fxml", "Mercado"); }
    @FXML public void gotoSalon() { trocarTela("/agendamento_servico.fxml", "Salão"); }
    @FXML public void gotoPharmacy() { trocarTela("/vendas_produtos_farmacia.fxml", "Farmácia"); }

    private void trocarTela(String fxml, String titulo) {
        Stage stage = (Stage) gotoMarketBtn.getScene().getWindow();
        ScreenManager.trocarTela(fxml, stage, titulo);
    }

    private void mostrarAlerta(String msg) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}