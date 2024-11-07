/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pemvismodul5;
//Import 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
// End Import

public class Aplikasi extends javax.swing.JFrame {//inisiasi GLobal Variable
    Connection conn;
    private DefaultTableModel modelKaryawan, modelProyek, modelTransaksi;
    //end global variable

    public Aplikasi() {
        initComponents();
        setLocationRelativeTo(null);
        //public main
        conn = koneksi.getConnection();
        // Model untuk Tabel Karyawan
        modelKaryawan = new DefaultTableModel();
        tblKaryawan.setModel(modelKaryawan);
        modelKaryawan.addColumn("ID");
        modelKaryawan.addColumn("Nama");
        modelKaryawan.addColumn("JABATAN");
        modelKaryawan.addColumn("DAPARTEMEN");
        // Model untuk Tabel Proyek
        modelProyek = new DefaultTableModel();
        tblProyek.setModel(modelProyek);
        modelProyek.addColumn("ID Proyek");
        modelProyek.addColumn("Nama Proyek");
        modelProyek.addColumn("Durasi (minggu)");

        // Model untuk Tabel Transaksi
        modelTransaksi = new DefaultTableModel();
        tblTransaksi.setModel(modelTransaksi);
        modelTransaksi.addColumn("ID Karyawan");
        modelTransaksi.addColumn("ID Proyek");
        modelTransaksi.addColumn("Peran");

        loadDataKaryawan();
        loadDataProyek();
        loadDataTransaksi();
        // Load ComboBox dengan data karyawan dan proyek
        loadKaryawanToComboBox();
        loadProyekToComboBox();
        //end public main
   }
    
    // Load Data Karyawan ke ComboBox
    private void loadKaryawanToComboBox() {
        cbIDKaryawan.removeAllItems();
        try {
            String sql = "SELECT id, nama FROM karyawan";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String idNama = rs.getInt("id") + " - " + rs.getString("nama");
                cbIDKaryawan.addItem(idNama);
            }
        } catch (SQLException e) {
            System.out.println("Error Load Karyawan to ComboBox: " + e.getMessage());
        }
    }

    // Load Data Proyek ke ComboBox
    private void loadProyekToComboBox() {
        cbIDProyek.removeAllItems();
        try {
            String sql = "SELECT id_proyek, nama_proyek FROM proyek";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String idNamaProyek = rs.getInt("id_proyek") + " - " + rs.getString("nama_proyek");
                cbIDProyek.addItem(idNamaProyek);
            }
        } catch (SQLException e) {
            System.out.println("Error Load Proyek to ComboBox: " + e.getMessage());
        }
    }


    // Load Data Karyawan
    private void loadDataKaryawan() {
        modelKaryawan.setRowCount(0);
        try {
            String sql = "SELECT * FROM karyawan";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                modelKaryawan.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("nama"),
                    rs.getString("jabatan"),
                    rs.getString("departemen")
                });
            }
        } catch (SQLException e) {
            System.out.println("Error Load Data Karyawan: " + e.getMessage());
        }
        loadKaryawanToComboBox(); // Panggil setelah data karyawan dimuat
    }

    // Load Data Proyek
    private void loadDataProyek() {
        modelProyek.setRowCount(0);
        try {
            String sql = "SELECT * FROM proyek";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                modelProyek.addRow(new Object[]{
                    rs.getInt("id_proyek"),
                    rs.getString("nama_proyek"),
                    rs.getInt("durasi")
                });
            }
        } catch (SQLException e) {
            System.out.println("Error Load Data Proyek: " + e.getMessage());
        }
        loadProyekToComboBox(); // Panggil setelah data proyek dimuat
    }

    // Load Data Transaksi
    private void loadDataTransaksi() {
        modelTransaksi.setRowCount(0);
        try {
            String sql = "SELECT * FROM transaksi";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                modelTransaksi.addRow(new Object[]{
                    rs.getInt("id_karyawan"),
                    rs.getInt("id_proyek"),
                    rs.getString("peran")
                });
            }
        } catch (SQLException e) {
            System.out.println("Error Load Data Transaksi: " + e.getMessage());
        }
    }

    // Fungsi CRUD untuk Karyawan
    private void saveDataKaryawan() {
        try {
            String sql = "INSERT INTO karyawan (nama, jabatan, departemen) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, tfNama.getText());
            ps.setString(2, tfJabatan.getText());
            ps.setString(3, tfDapartemen.getText());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data karyawan berhasil disimpan");
            loadDataKaryawan();
        } catch (SQLException e) {
            System.out.println("Error Save Data Karyawan: " + e.getMessage());
        }
    }


    private void updateDataKaryawan() {
        try {
            String sql = "UPDATE karyawan SET nama = ?, jabatan = ?, departemen = ? WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, tfNama.getText());
            ps.setString(2, tfJabatan.getText());
            ps.setString(3, tfDapartemen.getText());
            ps.setInt(4, Integer.parseInt(tfID.getText()));
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data karyawan berhasil diupdate");
            loadDataKaryawan();
        } catch (SQLException e) {
            System.out.println("Error Update Data Karyawan: " + e.getMessage());
        }
    }

    private void deleteDataKaryawan() {
        try {
            String sql = "DELETE FROM karyawan WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(tfID.getText()));
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data karyawan berhasil dihapus");
            loadDataKaryawan();
        } catch (SQLException e) {
            System.out.println("Error Delete Data Karyawan: " + e.getMessage());
        }
    }

    // Fungsi CRUD untuk Proyek
    private void saveDataProyek() {
        try {
            String sql = "INSERT INTO proyek (nama_proyek, durasi) VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, tfNmProyek.getText());
            ps.setInt(2, Integer.parseInt(tfDrsPengerjaan.getText()));
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data proyek berhasil disimpan");
            loadDataProyek();
        } catch (SQLException e) {
            System.out.println("Error Save Data Proyek: " + e.getMessage());
        }
    }

    private void updateDataProyek() {
        try {
            String sql = "UPDATE proyek SET nama_proyek = ?, durasi = ? WHERE id_proyek = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, tfNmProyek.getText());
            ps.setInt(2, Integer.parseInt(tfDrsPengerjaan.getText()));
            ps.setInt(3, Integer.parseInt(tfIDP.getText()));
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data proyek berhasil diupdate");
            loadDataProyek();
        } catch (SQLException e) {
            System.out.println("Error Update Data Proyek: " + e.getMessage());
        }
    }

    private void deleteDataProyek() {
        try {
            String sql = "DELETE FROM proyek WHERE id_proyek = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(tfIDP.getText()));
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data proyek berhasil dihapus");
            loadDataProyek();
        } catch (SQLException e) {
            System.out.println("Error Delete Data Proyek: " + e.getMessage());
        }
    }

    // Fungsi CRUD untuk Transaksi
private void saveDataTransaksi() {
    try {
        // Mengambil ID Karyawan dan ID Proyek dari ComboBox
        String selectedKaryawan = cbIDKaryawan.getSelectedItem().toString();
        String selectedProyek = cbIDProyek.getSelectedItem().toString();

        // Memisahkan ID dari string
        int idKaryawan = Integer.parseInt(selectedKaryawan.split(" - ")[0]); // Mengambil ID Karyawan
        int idProyek = Integer.parseInt(selectedProyek.split(" - ")[0]); // Mengambil ID Proyek

        // Cek apakah kombinasi id_karyawan dan id_proyek sudah ada
        String checkSql = "SELECT COUNT(*) FROM transaksi WHERE id_karyawan = ? AND id_proyek = ?";
        PreparedStatement checkPs = conn.prepareStatement(checkSql);
        checkPs.setInt(1, idKaryawan);
        checkPs.setInt(2, idProyek);
        ResultSet checkRs = checkPs.executeQuery();
        checkRs.next();
        int count = checkRs.getInt(1);

        if (count > 0) {
            // Jika sudah ada, berikan pesan kepada pengguna
            JOptionPane.showMessageDialog(this, "Data transaksi sudah ada untuk Karyawan dan Proyek ini.");
        } else {
            // Jika belum ada, simpan data baru
            String sql = "INSERT INTO transaksi (id_karyawan, id_proyek, peran) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idKaryawan);
            ps.setInt(2, idProyek);
            ps.setString(3, tfPeran.getText());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data transaksi berhasil disimpan");
            loadDataTransaksi();
        }
    } catch (SQLException e) {
        System.out.println("Error Save Data Transaksi: " + e.getMessage());
    } catch (NumberFormatException e) {
        System.out.println("Error Parsing ID: " + e.getMessage());
    }
}
    private void updateDataTransaksi() {
        try {
            String sql = "UPDATE transaksi SET peran = ? WHERE id_karyawan = ? AND id_proyek = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, tfPeran.getText());
            ps.setInt(2, Integer.parseInt(cbIDKaryawan.getSelectedItem().toString().split(" - ")[0]));
            ps.setInt(3, Integer.parseInt(cbIDProyek.getSelectedItem().toString().split(" - ")[0]));
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data transaksi berhasil diupdate");
            loadDataTransaksi();
        } catch (SQLException e) {
            System.out.println("Error Update Data Transaksi: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error Parsing ID: " + e.getMessage());
        }
    }

private void deleteDataTransaksi() {
    try {
        // Mengambil ID Karyawan dan ID Proyek dari ComboBox
        String selectedKaryawan = cbIDKaryawan.getSelectedItem().toString();
        String selectedProyek = cbIDProyek.getSelectedItem().toString();

        // Memisahkan ID dari string
        int idKaryawan = Integer.parseInt(selectedKaryawan.split(" - ")[0]); // Mengambil ID Karyawan
        int idProyek = Integer.parseInt(selectedProyek.split(" - ")[0]); // Mengambil ID Proyek

        // Cek apakah kombinasi id_karyawan dan id_proyek ada
        String checkSql = "SELECT COUNT(*) FROM transaksi WHERE id_karyawan = ? AND id_proyek = ?";
        PreparedStatement checkPs = conn.prepareStatement(checkSql);
        checkPs.setInt(1, idKaryawan);
        checkPs.setInt(2, idProyek);
        ResultSet checkRs = checkPs.executeQuery();
        checkRs.next();
        int count = checkRs.getInt(1);

        if (count == 0) {
            JOptionPane.showMessageDialog(this, "Data transaksi tidak ditemukan untuk Karyawan dan Proyek ini.");
        } else {
            // Jika data ada, lakukan penghapusan
            String sql = "DELETE FROM transaksi WHERE id_karyawan = ? AND id_proyek = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idKaryawan);
            ps.setInt(2, idProyek);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Data transaksi berhasil dihapus");
                loadDataTransaksi(); // Memuat ulang data transaksi
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menghapus data transaksi.");
            }
        }
    } catch (SQLException e) {
        System.out.println("Error Delete Data Transaksi: " + e.getMessage());
    } catch (NumberFormatException e) {
        System.out.println("Error Parsing ID: " + e.getMessage());
    }
}
    
    //memeriksa status data sesuai inputan
    
    // Memeriksa status data sesuai inputan
    // Fungsi untuk mengupdate status tombol ON/OFF
    private void updateButtonStatus() {
        boolean isIdFilled = !tfID.getText().trim().isEmpty();
        boolean isNameFilled = !tfNama.getText().trim().isEmpty();
        boolean isJobFilled = !tfJabatan.getText().trim().isEmpty();
        boolean isDeptFilled = !tfDapartemen.getText().trim().isEmpty();

        // Mengupdate tombol berdasarkan kondisi
        // Tombol Edit
        if (isIdFilled && isNameFilled && isJobFilled && isDeptFilled) {
            btnEditKaryawan.setText("Edit (ON)");
            btnEditKaryawan.setEnabled(true);
        } else {
            btnEditKaryawan.setText("Edit (OFF)");
            btnEditKaryawan.setEnabled(false);
        }

        // Tombol Simpan
        if (isNameFilled && isJobFilled && isDeptFilled) {
            btnTambahKaryawan.setText("Simpan (ON)");
            btnTambahKaryawan.setEnabled(true);
        } else {
            btnTambahKaryawan.setText("Simpan (OFF)");
            btnTambahKaryawan.setEnabled(false);
        }

        // Tombol Hapus
        if (isIdFilled) {
            btnHapusKaryawan.setText("Hapus (ON)");
            btnHapusKaryawan.setEnabled(true);
        } else {
            btnHapusKaryawan.setText("Hapus (OFF)");
            btnHapusKaryawan.setEnabled(false);
        }
    }
    
    // Fungsi untuk mengupdate status tombol pada proyek
    private void updateProjectButtonStatus() {
        boolean isProjectNameFilled = !tfNmProyek.getText().trim().isEmpty();
        boolean isProjectDurationFilled = !tfDrsPengerjaan.getText().trim().isEmpty();

        // Mengupdate tombol berdasarkan kondisi
        // Tombol Edit
        if (isProjectNameFilled && isProjectDurationFilled) {
            btnEditProyek.setText("Edit (ON)");
            btnEditProyek.setEnabled(true);
        } else {
            btnEditProyek.setText("Edit (OFF)");
            btnEditProyek.setEnabled(false);
        }

        // Tombol Simpan
        if (isProjectNameFilled && isProjectDurationFilled) {
            btnTambahProyek.setText("Simpan (ON)");
            btnTambahProyek.setEnabled(true);
        } else {
            btnTambahProyek.setText("Simpan (OFF)");
            btnTambahProyek.setEnabled(false);
        }

        // Tombol Hapus
        if (isProjectNameFilled) {
            btnHapusProyek.setText("Hapus (ON)");
            btnHapusProyek.setEnabled(true);
        } else {
            btnHapusProyek.setText("Hapus (OFF)");
            btnHapusProyek.setEnabled(false);
        }
    }
    
    // Fungsi untuk mengupdate status tombol pada Transaksi
    private void updateTransactionButtonStatus() {
        boolean isEmployeeSelected = !cbIDKaryawan.getSelectedItem().toString().equals("Pilih ID Karyawan");
        boolean isProjectSelected = !cbIDProyek.getSelectedItem().toString().equals("Pilih ID Proyek");
        boolean isRoleFilled = !tfPeran.getText().trim().isEmpty();

        // Mengupdate tombol berdasarkan kondisi
        // Tombol Edit
        if (isEmployeeSelected && isProjectSelected && isRoleFilled) {
            btnEditTransaksi.setText("Edit (ON)");
            btnEditTransaksi.setEnabled(true);
        } else {
            btnEditTransaksi.setText("Edit (OFF)");
            btnEditTransaksi.setEnabled(false);
        }

        // Tombol Simpan
        if (isEmployeeSelected && isProjectSelected && isRoleFilled) {
            btnTambahTransaksi.setText("Simpan (ON)");
            btnTambahTransaksi.setEnabled(true);
        } else {
            btnTambahTransaksi.setText("Simpan (OFF)");
            btnTambahTransaksi.setEnabled(false);
        }

    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        Karyawan = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        Nama = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        tfID = new javax.swing.JTextField();
        tfNama = new javax.swing.JTextField();
        tfJabatan = new javax.swing.JTextField();
        tfDapartemen = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        btnTambahKaryawan = new javax.swing.JButton();
        btnEditKaryawan = new javax.swing.JButton();
        btnHapusKaryawan = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblKaryawan = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        Proyek = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        Nama1 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        tfIDP = new javax.swing.JTextField();
        tfNmProyek = new javax.swing.JTextField();
        tfDrsPengerjaan = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        btnTambahProyek = new javax.swing.JButton();
        btnEditProyek = new javax.swing.JButton();
        btnHapusProyek = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblProyek = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        Transaksi = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        Nama2 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        tfPeran = new javax.swing.JTextField();
        cbIDKaryawan = new javax.swing.JComboBox<>();
        cbIDProyek = new javax.swing.JComboBox<>();
        jPanel9 = new javax.swing.JPanel();
        btnTambahTransaksi = new javax.swing.JButton();
        btnEditTransaksi = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblTransaksi = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(212, 189, 172));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Forte", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 102));
        jLabel1.setText("MANAJEMEN KARYAWAN DAN PROYEK");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        jPanel2.add(jLabel1, gridBagConstraints);

        jPanel3.setBackground(new java.awt.Color(212, 189, 172));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setIcon(new javax.swing.ImageIcon("C:\\Users\\safira rihadatul a\\OneDrive\\文件\\BANGUNAN.png")); // NOI18N
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 370, 510, 200));

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));

        Karyawan.setBackground(new java.awt.Color(255, 255, 255));
        Karyawan.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(212, 189, 172));
        jPanel4.setLayout(new java.awt.GridBagLayout());

        jLabel3.setText("Jabatan :");
        jLabel3.setPreferredSize(new java.awt.Dimension(80, 16));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 42;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jLabel3, gridBagConstraints);

        jLabel4.setText("Dapartemen :");
        jLabel4.setPreferredSize(new java.awt.Dimension(80, 16));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jLabel4, gridBagConstraints);

        Nama.setText("Nama :");
        Nama.setPreferredSize(new java.awt.Dimension(80, 16));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(Nama, gridBagConstraints);

        jLabel6.setText("ID :");
        jLabel6.setPreferredSize(new java.awt.Dimension(80, 16));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 70;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jLabel6, gridBagConstraints);

        tfID.setMinimumSize(new java.awt.Dimension(90, 25));
        tfID.setPreferredSize(new java.awt.Dimension(90, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(tfID, gridBagConstraints);

        tfNama.setMinimumSize(new java.awt.Dimension(90, 25));
        tfNama.setPreferredSize(new java.awt.Dimension(90, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 86;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(tfNama, gridBagConstraints);

        tfJabatan.setMinimumSize(new java.awt.Dimension(90, 25));
        tfJabatan.setPreferredSize(new java.awt.Dimension(90, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 86;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(tfJabatan, gridBagConstraints);

        tfDapartemen.setMinimumSize(new java.awt.Dimension(90, 25));
        tfDapartemen.setPreferredSize(new java.awt.Dimension(90, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(tfDapartemen, gridBagConstraints);

        Karyawan.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, 300, 140));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        btnTambahKaryawan.setText("Tambah");
        btnTambahKaryawan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahKaryawanActionPerformed(evt);
            }
        });
        jPanel5.add(btnTambahKaryawan);

        btnEditKaryawan.setText("Edit");
        btnEditKaryawan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditKaryawanActionPerformed(evt);
            }
        });
        jPanel5.add(btnEditKaryawan);

        btnHapusKaryawan.setText("Hapus");
        btnHapusKaryawan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusKaryawanActionPerformed(evt);
            }
        });
        jPanel5.add(btnHapusKaryawan);

        Karyawan.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 153, 300, 40));

        tblKaryawan.setBackground(new java.awt.Color(212, 189, 172));
        tblKaryawan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Nama", "Jabatan", "Dapartemen"
            }
        ));
        jScrollPane1.setViewportView(tblKaryawan);

        Karyawan.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(31, 196, 410, 150));

        jLabel5.setIcon(new javax.swing.ImageIcon("C:\\Users\\safira rihadatul a\\OneDrive\\文件\\orang1.png")); // NOI18N
        Karyawan.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 160, 190));

        jTabbedPane1.addTab("Karyawan", Karyawan);

        Proyek.setBackground(new java.awt.Color(255, 255, 255));
        Proyek.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setBackground(new java.awt.Color(212, 189, 172));
        jPanel6.setLayout(new java.awt.GridBagLayout());

        jLabel7.setText("Durasi Pengerjaan :");
        jLabel7.setPreferredSize(new java.awt.Dimension(106, 16));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 15, 0, 0);
        jPanel6.add(jLabel7, gridBagConstraints);

        Nama1.setText("Nama Proyek :");
        Nama1.setPreferredSize(new java.awt.Dimension(106, 16));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 29;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 15, 0, 0);
        jPanel6.add(Nama1, gridBagConstraints);

        jLabel9.setText("ID :");
        jLabel9.setPreferredSize(new java.awt.Dimension(106, 16));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 87;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 15, 0, 0);
        jPanel6.add(jLabel9, gridBagConstraints);

        tfIDP.setMinimumSize(new java.awt.Dimension(75, 25));
        tfIDP.setPreferredSize(new java.awt.Dimension(75, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 10);
        jPanel6.add(tfIDP, gridBagConstraints);

        tfNmProyek.setMinimumSize(new java.awt.Dimension(75, 25));
        tfNmProyek.setPreferredSize(new java.awt.Dimension(75, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 86;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 10);
        jPanel6.add(tfNmProyek, gridBagConstraints);

        tfDrsPengerjaan.setMinimumSize(new java.awt.Dimension(75, 25));
        tfDrsPengerjaan.setPreferredSize(new java.awt.Dimension(75, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 86;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 10);
        jPanel6.add(tfDrsPengerjaan, gridBagConstraints);

        Proyek.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 310, 110));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        btnTambahProyek.setText("Tambah");
        btnTambahProyek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahProyekActionPerformed(evt);
            }
        });
        jPanel7.add(btnTambahProyek);

        btnEditProyek.setText("Edit");
        btnEditProyek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditProyekActionPerformed(evt);
            }
        });
        jPanel7.add(btnEditProyek);

        btnHapusProyek.setText("Hapus");
        btnHapusProyek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusProyekActionPerformed(evt);
            }
        });
        jPanel7.add(btnHapusProyek);

        Proyek.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 130, 310, -1));

        tblProyek.setBackground(new java.awt.Color(212, 189, 172));
        tblProyek.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Nama Proyek", "Durasi Pengerjaan"
            }
        ));
        jScrollPane2.setViewportView(tblProyek);

        Proyek.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 430, 180));

        jLabel8.setIcon(new javax.swing.ImageIcon("C:\\Users\\safira rihadatul a\\OneDrive\\文件\\orang2.png")); // NOI18N
        Proyek.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 20, -1, -1));

        jTabbedPane1.addTab("Proyek", Proyek);

        Transaksi.setBackground(new java.awt.Color(255, 255, 255));
        Transaksi.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel8.setBackground(new java.awt.Color(212, 189, 172));
        jPanel8.setLayout(new java.awt.GridBagLayout());

        jLabel10.setText("Peran :");
        jLabel10.setPreferredSize(new java.awt.Dimension(75, 16));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 39;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 5, 0, 0);
        jPanel8.add(jLabel10, gridBagConstraints);

        Nama2.setText("ID Proyek :");
        Nama2.setPreferredSize(new java.awt.Dimension(60, 16));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jPanel8.add(Nama2, gridBagConstraints);

        jLabel12.setText("ID Karyawan :");
        jLabel12.setPreferredSize(new java.awt.Dimension(75, 16));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 0, 0);
        jPanel8.add(jLabel12, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 154;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 8, 0);
        jPanel8.add(tfPeran, gridBagConstraints);

        cbIDKaryawan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 5, 0, 0);
        jPanel8.add(cbIDKaryawan, gridBagConstraints);

        cbIDProyek.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 5, 0, 0);
        jPanel8.add(cbIDProyek, gridBagConstraints);

        Transaksi.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 30, 330, 70));

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        btnTambahTransaksi.setText("Tambah");
        btnTambahTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahTransaksiActionPerformed(evt);
            }
        });
        jPanel9.add(btnTambahTransaksi);

        btnEditTransaksi.setText("Edit");
        btnEditTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditTransaksiActionPerformed(evt);
            }
        });
        jPanel9.add(btnEditTransaksi);

        Transaksi.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 103, 290, 30));

        jScrollPane4.setBackground(new java.awt.Color(212, 189, 172));

        tblTransaksi.setBackground(new java.awt.Color(212, 189, 172));
        tblTransaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID Karyawan", "ID Proyek", "Peran"
            }
        ));
        jScrollPane4.setViewportView(tblTransaksi);

        Transaksi.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 147, 420, 200));

        jLabel11.setIcon(new javax.swing.ImageIcon("C:\\Users\\safira rihadatul a\\OneDrive\\文件\\orang3.png")); // NOI18N
        Transaksi.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, -1, -1));

        jTabbedPane1.addTab("Transaksi", Transaksi);

        jPanel3.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 470, 410));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 537, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTambahKaryawanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahKaryawanActionPerformed
        // TODO add your handling code here:
        if (tfNama.getText().trim().isEmpty() || tfJabatan.getText().trim().isEmpty() || 
            tfDapartemen.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Harap lengkapi data karyawan sebelum menyimpan!", "Peringatan", JOptionPane.WARNING_MESSAGE);
        } else {
            // Lanjutkan dengan penyimpanan jika data lengkap
            saveDataKaryawan();
        }
    }//GEN-LAST:event_btnTambahKaryawanActionPerformed

    private void btnEditKaryawanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditKaryawanActionPerformed
        // TODO add your handling code here:
        if (tfID.getText().trim().isEmpty() || tfNama.getText().trim().isEmpty() || 
            tfJabatan.getText().trim().isEmpty() || tfDapartemen.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Data tidak lengkap, tidak dapat diedit!", "Peringatan", JOptionPane.WARNING_MESSAGE);
        } else {
            // Lakukan update jika data lengkap
            updateDataKaryawan();
        }
    }//GEN-LAST:event_btnEditKaryawanActionPerformed

    private void btnHapusKaryawanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusKaryawanActionPerformed
        // TODO add your handling  code here:
        if (tfID.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID Karyawan tidak ditemukan, tidak bisa dihapus!", "Peringatan", JOptionPane.WARNING_MESSAGE);
        } else {
            // Lanjutkan dengan proses hapus jika ID ditemukan
            deleteDataKaryawan();
        }
    }//GEN-LAST:event_btnHapusKaryawanActionPerformed

    private void btnTambahProyekActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahProyekActionPerformed
        // TODO add your handling code here:
        if (tfNmProyek.getText().trim().isEmpty() || tfDrsPengerjaan.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Harap lengkapi data proyek sebelum menyimpan!", "Peringatan", JOptionPane.WARNING_MESSAGE);
        } else {
            // Lanjutkan dengan penyimpanan proyek
            saveDataProyek();
        }
    }//GEN-LAST:event_btnTambahProyekActionPerformed

    private void btnEditProyekActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditProyekActionPerformed
        // TODO add your handling code here:
        if (tfNmProyek.getText().trim().isEmpty() || tfDrsPengerjaan.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Data proyek tidak lengkap, tidak dapat diedit!", "Peringatan", JOptionPane.WARNING_MESSAGE);
        } else {
            // Lakukan update proyek jika data lengkap
            updateDataProyek();
        }
    }//GEN-LAST:event_btnEditProyekActionPerformed

    private void btnHapusProyekActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusProyekActionPerformed
        // TODO add your handling code here:
        if (tfIDP.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama proyek tidak ditemukan, tidak bisa dihapus!", "Peringatan", JOptionPane.WARNING_MESSAGE);
        } else {
            // Lanjutkan dengan proses hapus proyek
            deleteDataProyek();
        }
    }//GEN-LAST:event_btnHapusProyekActionPerformed

    private void btnTambahTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahTransaksiActionPerformed
        // TODO add your handling code here:
        if (cbIDKaryawan.getSelectedItem().toString().equals("Pilih ID Karyawan") || 
            cbIDProyek.getSelectedItem().toString().equals("Pilih ID Proyek") || 
            tfPeran.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Harap lengkapi data transaksi sebelum menyimpan!", "Peringatan", JOptionPane.WARNING_MESSAGE);
        } else {
            // Lanjutkan dengan penyimpanan transaksi jika data lengkap
            saveDataTransaksi();
        }
    }//GEN-LAST:event_btnTambahTransaksiActionPerformed

    private void btnEditTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditTransaksiActionPerformed
        // TODO add your handling code here:
        if (!cbIDKaryawan.getSelectedItem().toString().equals("Pilih ID Karyawan") && 
            !cbIDProyek.getSelectedItem().toString().equals("Pilih ID Proyek") && 
            !tfPeran.getText().trim().isEmpty()) {
            // Proses Edit Transaksi jika data valid
            updateDataTransaksi();
        } else {
            JOptionPane.showMessageDialog(this, "Data tidak lengkap, tidak dapat diedit!", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnEditTransaksiActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Aplikasi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Karyawan;
    private javax.swing.JLabel Nama;
    private javax.swing.JLabel Nama1;
    private javax.swing.JLabel Nama2;
    private javax.swing.JPanel Proyek;
    private javax.swing.JPanel Transaksi;
    private javax.swing.JButton btnEditKaryawan;
    private javax.swing.JButton btnEditProyek;
    private javax.swing.JButton btnEditTransaksi;
    private javax.swing.JButton btnHapusKaryawan;
    private javax.swing.JButton btnHapusProyek;
    private javax.swing.JButton btnTambahKaryawan;
    private javax.swing.JButton btnTambahProyek;
    private javax.swing.JButton btnTambahTransaksi;
    private javax.swing.JComboBox<String> cbIDKaryawan;
    private javax.swing.JComboBox<String> cbIDProyek;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tblKaryawan;
    private javax.swing.JTable tblProyek;
    private javax.swing.JTable tblTransaksi;
    private javax.swing.JTextField tfDapartemen;
    private javax.swing.JTextField tfDrsPengerjaan;
    private javax.swing.JTextField tfID;
    private javax.swing.JTextField tfIDP;
    private javax.swing.JTextField tfJabatan;
    private javax.swing.JTextField tfNama;
    private javax.swing.JTextField tfNmProyek;
    private javax.swing.JTextField tfPeran;
    // End of variables declaration//GEN-END:variables
}
