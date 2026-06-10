package com.example.lj1_bd_2sem.controller;

import com.example.lj1_bd_2sem.dao.ClienteDAO;
import com.example.lj1_bd_2sem.dao.UsuarioDAO;
import com.example.lj1_bd_2sem.model.Cliente;
import com.example.lj1_bd_2sem.model.Usuario;
import com.example.lj1_bd_2sem.util.ScreenManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CadastroUsuarioController {

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtLogin;

    @FXML
    private PasswordField txtSenha;

    @FXML
    private ComboBox<String> comboPerfil;

    @FXML
    private VBox boxClienteExclusivo;

    @FXML
    private TextField txtBalanca;

    @FXML
    private Button btnCadastrar;

    @FXML
    private Hyperlink linkLogin;

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final ClienteDAO clienteDAO = new ClienteDAO();


    @FXML
    public void initialize() {
        boxClienteExclusivo.setManaged(false);
        boxClienteExclusivo.setVisible(false);

        List<String> enumPerfis = new ArrayList<>();
        enumPerfis.add("ADM");
        enumPerfis.add("PROFISSIONAL");
        enumPerfis.add("CLIENTE");

        ObservableList<String> opcoesCbPerfis = FXCollections.observableArrayList(enumPerfis);
        comboPerfil.setItems(opcoesCbPerfis);
    }

    @FXML
    public void handlePerfilChange() {
        if (comboPerfil.getValue() != null){
            if (comboPerfil.getValue().equals("CLIENTE")){
                boxClienteExclusivo.setManaged(true);
                boxClienteExclusivo.setVisible(true);
            }
        }
    }

    @FXML
    public void handleCadastrar() {
        String nome = txtNome.getText();
        String login = txtLogin.getText();
        String senha = txtSenha.getText();
        String tipoPerfil = comboPerfil.getValue();

        Stage stage = (Stage) linkLogin.getScene().getWindow();
        if (nome != null){
            if (login != null){
                if (senha != null){
                    if (tipoPerfil != null){
                        Usuario u = new Usuario();
                        u.setNome(nome);
                        u.setLogin(login);
                        u.setSenha(senha);
                        u.setPerfil(tipoPerfil);

                        try{
                            int id = usuarioDAO.registrarUsuario(u);
                            if (tipoPerfil.equals("CLIENTE")){
                                Cliente c = new Cliente();
                                c.setId(id);
                                c.setBalanca(Double.parseDouble(txtBalanca.getText()));

                                ScreenManager.trocarTela("/home.fxml", stage, "Home");
                            } else if(tipoPerfil.equals("ADM")){
                                ScreenManager.trocarTela("/dashboard.fxml", stage, "Dashboard");
                            }

                        } catch (SQLException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    @FXML
    public void irParaTelaLogin() {
        // Método vazio
    }
}