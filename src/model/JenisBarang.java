package model;

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
public class JenisBarang {

    public int id;
    public String namaJenis;

    public JenisBarang() {
    }

    public JenisBarang(int id, String namaJenis) {
        this.id = id;
        this.namaJenis = namaJenis;
    }

    public List<JenisBarang> get(Connection connection) {
        List<JenisBarang> jenisBarangs = new ArrayList<>();
        String sql = "SELECT * FROM jenis_barang";

        try ( Statement statement = connection.createStatement();  ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                JenisBarang jenisBarang = new JenisBarang();
                jenisBarang.id = resultSet.getInt("id");
                jenisBarang.namaJenis = resultSet.getString("nama_jenis");

                jenisBarangs.add(jenisBarang);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return jenisBarangs;
    }

    public JenisBarang get(Connection connection, int id) {
        JenisBarang jenisBarang = new JenisBarang();
        String sql = "SELECT * FROM jenis_barang WHERE id='" + id + "'";

        try ( Statement statement = connection.createStatement();  ResultSet resultSet = statement.executeQuery(sql)) {

            if (resultSet.next()) {
                jenisBarang.id = resultSet.getInt("id");
                jenisBarang.namaJenis = resultSet.getString("nama_jenis");
            }
            resultSet.close();
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return jenisBarang;
    }
}
