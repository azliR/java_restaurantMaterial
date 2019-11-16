package model;

import com.mysql.jdbc.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author a_lpha
 */
public class Penjualan {

    int id, idPengguna, idMeja, idStatus, total;
    String atasNama, tanggalPenjualan, keterangan;

    public Penjualan() {
    }

    public Penjualan(int id, int idPengguna, int idMeja, int idStatus, int total, String atasNama, String tanggalPenjualan, String keterangan) {
        this.id = id;
        this.idPengguna = idPengguna;
        this.idMeja = idMeja;
        this.idStatus = idStatus;
        this.total = total;
        this.atasNama = atasNama;
        this.tanggalPenjualan = tanggalPenjualan;
        this.keterangan = keterangan;
    }

    public List<Penjualan> get(Connection connection) {
        List<Penjualan> penjualans = new ArrayList<>();
        String sql = "SELECT * FROM penjualan ORDER BY tanggal_penjualan DESC";

        try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Penjualan penjualan = new Penjualan();
                penjualan.setId(resultSet.getInt("id"));
                penjualan.setIdPengguna(resultSet.getInt("id_pengguna"));
                penjualan.setIdMeja(resultSet.getInt("id_meja"));
                penjualan.setIdStatus(resultSet.getInt("id_status"));
                penjualan.setTotal(resultSet.getInt("total"));
                penjualan.setAtasNama(resultSet.getString("atas_nama"));
                penjualan.setTanggalPenjualan(resultSet.getString("tanggal_penjualan"));
                penjualan.setKeterangan(resultSet.getString("keterangan"));

                penjualans.add(penjualan);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return penjualans;
    }

    public List<Penjualan> getByIdMeja(Connection connection, int idMeja) {
        List<Penjualan> penjualans = new ArrayList<>();
        String sql = "SELECT * FROM penjualan ORDER BY tanggal_penjualan DESC WHERE id_meja='" + idMeja + "'";

        try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Penjualan penjualan = new Penjualan();
                penjualan.setId(resultSet.getInt("id"));
                penjualan.setIdPengguna(resultSet.getInt("id_pengguna"));
                penjualan.setIdMeja(resultSet.getInt("id_meja"));
                penjualan.setIdStatus(resultSet.getInt("id_status"));
                penjualan.setTotal(resultSet.getInt("total"));
                penjualan.setAtasNama(resultSet.getString("atas_nama"));
                penjualan.setTanggalPenjualan(resultSet.getString("tanggal_penjualan"));
                penjualan.setKeterangan(resultSet.getString("keterangan"));

                penjualans.add(penjualan);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return penjualans;
    }

    public int insert(Connection c) {
        int generatedId = -1;
        String sql = "INSERT INTO penjualan VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement p = (PreparedStatement) c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            p.setInt(1, id);
            p.setInt(2, idPengguna);
            p.setInt(3, idMeja);
            p.setInt(4, idStatus);
            p.setString(5, atasNama);
            p.setString(6, tanggalPenjualan);
            p.setInt(7, total);
            p.setString(8, keterangan);

            p.executeUpdate();

            try (ResultSet resultSet = p.getGeneratedKeys()) {
                if (resultSet.next()) {
                    generatedId = resultSet.getInt(1);
                }
                resultSet.close();
            }
            p.close();
        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }
        return generatedId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPengguna() {
        return idPengguna;
    }

    public void setIdPengguna(int idPengguna) {
        this.idPengguna = idPengguna;
    }

    public int getIdMeja() {
        return idMeja;
    }

    public void setIdMeja(int idMeja) {
        this.idMeja = idMeja;
    }

    public int getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(int idStatus) {
        this.idStatus = idStatus;
    }

    public String getAtasNama() {
        return atasNama;
    }

    public void setAtasNama(String atasNama) {
        this.atasNama = atasNama;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getTanggalPenjualan() {
        return tanggalPenjualan;
    }

    public void setTanggalPenjualan(String tanggalPenjualan) {
        this.tanggalPenjualan = tanggalPenjualan;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

}