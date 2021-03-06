/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.monitoranuvem.model;

import br.com.monitoranuvem.connection.ConnectionMySql;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Marcio
 */
public class SendAlertsBD {

    private Connection conn;

    public boolean criarAlerts(SendAlerts sendAlert) throws ClassNotFoundException, SQLException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateForMySql = "";
        Date data = new Date();
        conn = new ConnectionMySql().getConnection();
        PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO SENDALERTS (IDALERTS,DATESENDALERTS,SEND,STATUS) "
                + "VALUES (?,?,?,?)"
        );
        stmt.setInt(1, sendAlert.getAlerts().getIdAlerts());
        dateForMySql = sdf.format(data);
        stmt.setString(2, dateForMySql);
        stmt.setInt(3, 0);
        stmt.setInt(4, 0);
        int ret = stmt.executeUpdate();
        conn.close();
        if (ret > 0) {
            return true;
        }
        return false;
    }

    public int criarAlerts(int idAlerts) throws ClassNotFoundException, SQLException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateForMySql = "";
        Date data = new Date();
        conn = new ConnectionMySql().getConnection();
        PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO SENDALERTS (IDALERTS,DATESENDALERTS,SEND,STATUS) "
                + "VALUES (?,?,?,?)"
        );
        stmt.setInt(1, idAlerts);
        dateForMySql = sdf.format(data);
        stmt.setString(2, dateForMySql);
        stmt.setInt(3, 0);
        stmt.setInt(4, 0);
        int ret = stmt.executeUpdate();
        conn.close();
        if (ret > 0) {
            return montaId();
        }
        return 0;
    }

    private int montaId() throws ClassNotFoundException, SQLException {
        int num = 0;
        conn = new ConnectionMySql().getConnection();
        PreparedStatement stmt = conn.prepareStatement(
                "SELECT MAX(IDSENDALERTS) ID \n"
                + "FROM SENDALERTS ");
        ResultSet resultado = stmt.executeQuery();
        while (resultado.next()) {
            num = resultado.getInt("ID");
        }
        conn.close();
        return num;
    }

    public void atualizaSendAlert(int idSendAlerts) throws ClassNotFoundException, SQLException {
        conn = new ConnectionMySql().getConnection();
        PreparedStatement stmt = conn.prepareStatement(
                "UPDATE SENDALERTS SET STATUS=? WHERE IDSENDALERTS=?"
        );
        stmt.setInt(1, 1);
        stmt.setInt(2, idSendAlerts);
        stmt.executeUpdate();
        conn.close();
    }
    
    public void atualizaStatusMail(int idSendAlerts) throws ClassNotFoundException, SQLException {
        conn = new ConnectionMySql().getConnection();
        PreparedStatement stmt = conn.prepareStatement(
                "UPDATE SENDALERTS SET SEND=? WHERE IDSENDALERTS=?"
        );
        stmt.setInt(1, 1);
        stmt.setInt(2, idSendAlerts);
        stmt.executeUpdate();
        conn.close();
    }
    
    public int existeAlert(int idAlert) throws ClassNotFoundException, SQLException {
        int num = 0;
        conn = new ConnectionMySql().getConnection();
        PreparedStatement stmt = conn.prepareStatement(
                "SELECT COUNT(*) AS QUANTIDADE \n"
                + "FROM SENDALERTS	\n"
                + "WHERE IDALERTS=? AND STATUS =?");
        stmt.setInt(1, idAlert);
        stmt.setInt(2, 0);
        ResultSet resultado = stmt.executeQuery();
        while (resultado.next()) {
            num = resultado.getInt("QUANTIDADE");
        }
        conn.close();
        return num;
    }

    public int buscaSendAlert(int idAlerts) throws ClassNotFoundException, SQLException {
        int idSendAlert = 0;
        conn = new ConnectionMySql().getConnection();
        PreparedStatement stmt = conn.prepareStatement(
                "SELECT IDSENDALERTS FROM SENDALERTS WHERE IDALERTS=? AND STATUS =?"
        );
        stmt.setInt(1, idAlerts);
        stmt.setInt(2, 0);
        ResultSet resultado = stmt.executeQuery();
        if (resultado.next()) {
            idSendAlert = resultado.getInt("IDSENDALERTS");
        }
        conn.close();
        return idSendAlert;
    }

    public ArrayList<SendAlerts> listaSendAlerts() throws ClassNotFoundException, SQLException, ParseException {
        SendAlerts send;
        String date_s;
        SimpleDateFormat dt;
        Date date;
        ArrayList<SendAlerts> list = new ArrayList<>();
        conn = new ConnectionMySql().getConnection();
        PreparedStatement stmt = conn.prepareStatement(
                "SELECT IDSENDALERTS,IDALERTS,DATESENDALERTS,SEND,STATUS "
                + "FROM SENDALERTS WHERE STATUS=?"
        );
        stmt.setInt(1, 0);
        ResultSet resultado = stmt.executeQuery();
        while (resultado.next()) {
            send = new SendAlerts();
            send.setIdSendAlerts(resultado.getInt("IDSENDALERTS"));
            send.setAlerts(new AlertsBD().buscaAlerts(resultado.getInt("IDALERTS")));
            date_s = resultado.getString("DATESENDALERTS");
            if (date_s != null) {
                dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                date = dt.parse(date_s);
                send.setDateSendAlerts(date);
            } else {
                send.setDateSendAlerts(null);
            }
            send.setSend(resultado.getInt("SEND"));
            send.setStatus(resultado.getInt("STATUS"));
            list.add(send);
        }
        conn.close();
        return list;                
    }
    
    public ArrayList<SendAlerts> listaSendAlertsHistory() throws ClassNotFoundException, SQLException, ParseException {
        SendAlerts send;
        String date_s;
        SimpleDateFormat dt;
        Date date;
        String text ="";
        ArrayList<SendAlerts> list = new ArrayList<>();
        conn = new ConnectionMySql().getConnection();
        PreparedStatement stmt = conn.prepareStatement(
                "SELECT IDSENDALERTS,IDALERTS,DATESENDALERTS,SEND,STATUS "
                + "FROM SENDALERTS"
        );
        ResultSet resultado = stmt.executeQuery();
        while (resultado.next()) {
            send = new SendAlerts();
            send.setIdSendAlerts(resultado.getInt("IDSENDALERTS"));
            send.setAlerts(new AlertsBD().buscaAlerts(resultado.getInt("IDALERTS")));
            date_s = resultado.getString("DATESENDALERTS");
            if (date_s != null) {
                dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                date = dt.parse(date_s);
                send.setDateSendAlerts(date);
            } else {
                send.setDateSendAlerts(null);
            }
            send.setSend(resultado.getInt("SEND"));
            send.setStatus(resultado.getInt("STATUS"));
            text = new HistorySendAlertsBD().diferenca(resultado.getInt("IDSENDALERTS"));
            send.setDuracao(text);
            list.add(send);
        }
        conn.close();
        return list;                
    }
}
