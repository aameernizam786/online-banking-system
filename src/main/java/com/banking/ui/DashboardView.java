package com.banking.ui;



import com.banking.dao.AccountDAO;
import com.banking.dao.TransactionDAO;
import com.banking.model.Account;
import com.banking.model.TransactionRecord;
import com.banking.model.User;
import com.banking.service.BankService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class DashboardView {
    private final User user;
    private final AccountDAO accountDAO = new AccountDAO();
    private final TransactionDAO transactionDAO = new TransactionDAO();
    private final BankService bankService = new BankService();

    public DashboardView(User user) { this.user = user; }

    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        Label welcome = new Label("Welcome, " + user.getFullName());
        Button refresh = new Button("Refresh");
        HBox top = new HBox(10, welcome, refresh);
        root.setTop(top);

        ListView<Account> accountList = new ListView<>();
        try { accountList.setItems(FXCollections.observableArrayList(accountDAO.findByUserId(user.getUserId()))); }
        catch (SQLException e) { e.printStackTrace(); }

        ObservableList<String> txObs = FXCollections.observableArrayList();
        ListView<String> txList = new ListView<>(txObs);

        VBox center = new VBox(10, new Label("Accounts"), accountList, new Label("Transactions"), txList);
        root.setCenter(center);

        TextField amountF = new TextField(); amountF.setPromptText("Amount");
        TextField toAccF = new TextField(); toAccF.setPromptText("To Account ID (for transfer)");
        Button depositBtn = new Button("Deposit");
        Button withdrawBtn = new Button("Withdraw");
        Button transferBtn = new Button("Transfer");
        HBox controls = new HBox(10, amountF, toAccF, depositBtn, withdrawBtn, transferBtn);
        root.setBottom(controls);

        refresh.setOnAction(ev -> {
            try { accountList.setItems(FXCollections.observableArrayList(accountDAO.findByUserId(user.getUserId()))); }
            catch (SQLException e) { e.printStackTrace(); }
        });

        accountList.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            txObs.clear();
            if (newV != null) {
                try {
                    List<TransactionRecord> trs = transactionDAO.findByAccountId(newV.getAccountId());
                    for (TransactionRecord tr : trs) txObs.add(tr.getTimestamp() + " " + tr.getType() + " " + tr.getAmount());
                } catch (SQLException e) { e.printStackTrace(); }
            }
        });

        depositBtn.setOnAction(ev -> {
            Account sel = accountList.getSelectionModel().getSelectedItem();
            if (sel == null) return;
            try {
                BigDecimal amt = new BigDecimal(amountF.getText());
                bankService.deposit(sel.getAccountId(), amt);
                refresh.fire();
            } catch (Exception e) { showAlert(e.getMessage()); }
        });

        withdrawBtn.setOnAction(ev -> {
            Account sel = accountList.getSelectionModel().getSelectedItem();
            if (sel == null) return;
            try {
                BigDecimal amt = new BigDecimal(amountF.getText());
                bankService.withdraw(sel.getAccountId(), amt);
                refresh.fire();
            } catch (Exception e) { showAlert(e.getMessage()); }
        });

        transferBtn.setOnAction(ev -> {
            Account sel = accountList.getSelectionModel().getSelectedItem();
            if (sel == null) return;
            try {
                BigDecimal amt = new BigDecimal(amountF.getText());
                long toAcc = Long.parseLong(toAccF.getText());
                bankService.transfer(sel.getAccountId(), toAcc, amt);
                refresh.fire();
            } catch (Exception e) { showAlert(e.getMessage()); }
        });

        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Online Bank - Dashboard");
        stage.setScene(scene);
        stage.show();
    }

    private void showAlert(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        a.showAndWait();
    }
}
