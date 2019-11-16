/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panel;

import common.RoundedBorder;
import common.RoundedButton;
import common.TemplateBarang;
import common.a_;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import model.Barang;
import model.DetailPenjualan;
import model.EntriMeja;
import model.JenisBarang;
import model.Penjualan;
import model.Pesanan;
import pages.MainPageWhiteTheme;
import styles.Colors;

/**
 *
 * @author a_lpha
 */
public class EntriBarangPanel extends javax.swing.JPanel {
    
    private List<Barang> barangs = new ArrayList<>();
    private final List<TemplateBarang> templateBarangs = new ArrayList<>();
    private final DefaultListModel<Pesanan> listModel = new DefaultListModel<>();
    
    public List<Pesanan> pesanans = new ArrayList<>();
    
    private final int circleRadius = 64;
    private final int buttonRadius = 8;
    
    public final EntriMeja entriMeja;
    private Barang selectedBarang;
    
    private GridLayout gridLayout;
    private final MainPageWhiteTheme context;
    private final Connection connection;
    
    public EntriBarangPanel(MainPageWhiteTheme context, Connection connection) {
        this.context = context;
        this.connection = connection;
        this.entriMeja = null;
        
        initComponents();
        init();
    }
    
    public EntriBarangPanel(MainPageWhiteTheme context, Connection connection, EntriMeja entriMeja) {
        this.context = context;
        this.connection = connection;
        this.entriMeja = entriMeja;
        
        initComponents();
        init();
    }
    
    private void init() {
        gridLayout = new GridLayout(0, entriMeja != null ? 3 : 4);
        gridLayout.setHgap(12);
        gridLayout.setVgap(12);
        entriBarangPanel.setLayout(gridLayout);
        
        entriBarangScroll.getVerticalScrollBar().setUnitIncrement(12);
        
        if (entriMeja != null) {
            tv_nomorMeja.setText(String.valueOf(entriMeja.getNomorMeja()));
        }
        detailPesananPanel.setVisible(entriMeja != null);
        
        detailBarangPanel.setVisible(false);
        b_kurang.setVisible(entriMeja != null);
        b_tambah.setVisible(entriMeja != null);
        b_pesan.setVisible(entriMeja != null);
        et_jumlah.setVisible(entriMeja != null);
        context.b_keranjang.setText("Rp. 0");
        
        loadBarang(new Barang().get(connection));
    }
    
    public void loadBarang(List<Barang> barangs) {
        entriBarangPanel.removeAll();
        this.barangs = barangs;
        
        barangs.forEach((_barang) -> {
            TemplateBarang templateBarang = new TemplateBarang(connection, this, _barang);
            templateBarangs.add(templateBarang);
            entriBarangPanel.add(templateBarang);
        });
        if (detailBarangPanel.isVisible()) {
            hideDetailBarang();
        } else {
            entriBarangPanel.revalidate();
        }
    }
    
    public void addToCart(Pesanan pesanan) {
        pesanans.add(pesanan);
        listModel.addElement(pesanan);
        
        b_pesan.setText("");
        b_pesan.setEnabled(false);
        b_pesan.setForeground(Color.WHITE);
        
        int total = 0;
        total = pesanans.stream().map((_pesanan) -> _pesanan.getHargaBarang() * _pesanan.getJumlahBarang()).map((subTotal) -> subTotal).reduce(total, Integer::sum);
        context.b_keranjang.setText("Rp. " + a_.convertCurrency(total));
    }
    
    public void showDetailBarang(TemplateBarang templateBarang, Barang barang) {
        selectedBarang = barang;
        
        if (!detailBarangPanel.isVisible()) {
            detailBarangPanel.setVisible(true);
            gridLayout.setColumns(3);
            entriBarangPanel.revalidate();
        }
        
        if (detailPesananPanel.isVisible()) {
            detailPesananPanel.setVisible(false);
        }
        
        if (isOrdered()) {
            b_pesan.setText("");
            b_pesan.setEnabled(false);
        } else {
            b_pesan.setText("Rp. " + a_.convertCurrency(barang.getHarga()));
            b_pesan.setEnabled(true);
        }
        
        Image image = new ImageIcon(barang.getGambar()).getImage();
        BufferedImage bufferedImage = a_.toBufferedImage(image, new Rectangle(220, 140));
        
        iv_gambarBarang.setIcon(new ImageIcon(a_.convertRoundedImage(bufferedImage, 16)));
        tv_namaBarangSubTitle.setText(("NAMA " + new JenisBarang().get(connection, barang.getIdJenis()).getNamaJenis()).toUpperCase());
        tv_namaBarang.setText("<html>" + barang.getNamaBarang() + "</html>");
        et_jumlah.setText("1");
        
        templateBarangs.forEach(((_templateBarang) -> {
            _templateBarang.setSelected(_templateBarang == templateBarang);
        }));
    }
    
    private void hideDetailBarang() {
        selectedBarang = null;
        
        detailBarangPanel.setVisible(false);
        gridLayout.setColumns(4);
        entriBarangPanel.revalidate();
        
        templateBarangs.forEach(((_templateBarang) -> {
            _templateBarang.setSelected(false);
        }));
    }
    
    public void showDetailPesanan() {
        if (detailBarangPanel.isVisible()) {
            selectedBarang = null;
            detailBarangPanel.setVisible(false);
            
            templateBarangs.forEach(((_templateBarang) -> {
                _templateBarang.setSelected(false);
            }));
        }
        
        detailPesananPanel.setVisible(true);
        gridLayout.setColumns(3);
        entriBarangPanel.revalidate();
    }
    
    private void hideDetailPesanan() {
        detailPesananPanel.setVisible(false);
        gridLayout.setColumns(4);
        entriBarangPanel.revalidate();
    }
    
    private boolean isOrdered() {
        final boolean[] isOrdered = {false};
        
        pesanans.forEach(((_pesanan) -> {
            if (_pesanan.getIdBarang() == selectedBarang.getId()) {
                isOrdered[0] = true;
            }
        }));
        return isOrdered[0];
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        entriBarangScroll = new common.a_ScrollPane(jPanel1);
        jPanel1 = new javax.swing.JPanel();
        entriBarangPanel = new javax.swing.JPanel();
        detailPesananPanel = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        tv_nomorMeja = new javax.swing.JLabel();
        b_konfirmasiPesanan = new RoundedButton(buttonRadius);
        jSeparator32 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        l_daftarPesanan = new javax.swing.JList<>();
        b_closeDetailPesanan = new javax.swing.JButton();
        detailBarangPanel = new javax.swing.JPanel();
        tv_detailTitle = new javax.swing.JLabel();
        iv_gambarBarang = new javax.swing.JLabel();
        tv_namaBarang = new javax.swing.JLabel();
        b_kurang = new RoundedButton(circleRadius);
        et_jumlah = new javax.swing.JTextField();
        b_tambah = new RoundedButton(circleRadius);
        b_pesan = new RoundedButton(buttonRadius);
        tv_namaBarangSubTitle = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        b_closeDetailBarang = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jSeparator1 = new javax.swing.JSeparator();

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jButton1.setText("jButton1");

        setBackground(new java.awt.Color(255, 255, 255));

        entriBarangScroll.setBackground(new java.awt.Color(255, 255, 255));
        entriBarangScroll.setBorder(null);
        entriBarangScroll.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING, 0, 8));

        entriBarangPanel.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout entriBarangPanelLayout = new javax.swing.GroupLayout(entriBarangPanel);
        entriBarangPanel.setLayout(entriBarangPanelLayout);
        entriBarangPanelLayout.setHorizontalGroup(
            entriBarangPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        entriBarangPanelLayout.setVerticalGroup(
            entriBarangPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel1.add(entriBarangPanel);

        entriBarangScroll.setViewportView(jPanel1);

        detailPesananPanel.setBackground(new java.awt.Color(255, 255, 255));
        detailPesananPanel.setMinimumSize(new java.awt.Dimension(360, 360));

        jLabel41.setFont(new java.awt.Font("Google Sans", 0, 22)); // NOI18N
        jLabel41.setForeground(Colors.blackTextColor);
        jLabel41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ic_cart_grey.png"))); // NOI18N
        jLabel41.setText("Keranjang");
        jLabel41.setIconTextGap(16);

        jLabel42.setFont(new java.awt.Font("Roboto Medium", 0, 11)); // NOI18N
        jLabel42.setForeground(Colors.greyTextColor);
        jLabel42.setText("NO. MEJA");

        jLabel43.setFont(new java.awt.Font("Roboto Medium", 0, 11)); // NOI18N
        jLabel43.setForeground(Colors.greyTextColor);
        jLabel43.setText("DAFTAR PESANAN");

        tv_nomorMeja.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        tv_nomorMeja.setForeground(Colors.blackTextColor);
        tv_nomorMeja.setText("01");

        b_konfirmasiPesanan.setBackground(Colors.accentColor);
        b_konfirmasiPesanan.setFont(new java.awt.Font("Product Sans Medium", 0, 16)); // NOI18N
        b_konfirmasiPesanan.setForeground(new java.awt.Color(255, 255, 255));
        b_konfirmasiPesanan.setText("Konfirmasi Pesanan");
        b_konfirmasiPesanan.setBorder(null);
        b_konfirmasiPesanan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        b_konfirmasiPesanan.setFocusPainted(false);
        b_konfirmasiPesanan.setIconTextGap(8);
        b_konfirmasiPesanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_konfirmasiPesananActionPerformed(evt);
            }
        });

        jSeparator32.setForeground(new java.awt.Color(218, 220, 224));

        l_daftarPesanan.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        l_daftarPesanan.setModel(listModel);
        l_daftarPesanan.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        l_daftarPesanan.setCellRenderer(new common.TemplateListPesanan(connection));
        jScrollPane1.setViewportView(l_daftarPesanan);

        b_closeDetailPesanan.setBackground(new java.awt.Color(255, 255, 255));
        b_closeDetailPesanan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ic_close_grey.png"))); // NOI18N
        b_closeDetailPesanan.setBorder(null);
        b_closeDetailPesanan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        b_closeDetailPesanan.setFocusPainted(false);
        b_closeDetailPesanan.setOpaque(false);
        b_closeDetailPesanan.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ic_close.png"))); // NOI18N
        b_closeDetailPesanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_closeDetailPesananActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout detailPesananPanelLayout = new javax.swing.GroupLayout(detailPesananPanel);
        detailPesananPanel.setLayout(detailPesananPanelLayout);
        detailPesananPanelLayout.setHorizontalGroup(
            detailPesananPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detailPesananPanelLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(detailPesananPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addComponent(b_konfirmasiPesanan, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(detailPesananPanelLayout.createSequentialGroup()
                        .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(tv_nomorMeja))
                    .addComponent(jSeparator32)
                    .addGroup(detailPesananPanelLayout.createSequentialGroup()
                        .addComponent(jLabel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(b_closeDetailPesanan, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(24, 24, 24))
        );
        detailPesananPanelLayout.setVerticalGroup(
            detailPesananPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detailPesananPanelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(detailPesananPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(b_closeDetailPesanan, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                    .addComponent(jLabel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(24, 24, 24)
                .addGroup(detailPesananPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel42)
                    .addComponent(tv_nomorMeja))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel43)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(b_konfirmasiPesanan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );

        detailBarangPanel.setBackground(new java.awt.Color(255, 255, 255));

        tv_detailTitle.setFont(new java.awt.Font("Google Sans", 0, 22)); // NOI18N
        tv_detailTitle.setForeground(Colors.blackTextColor);
        tv_detailTitle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ic_information.png"))); // NOI18N
        tv_detailTitle.setText("Detail Barang");
        tv_detailTitle.setIconTextGap(16);

        iv_gambarBarang.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iv_gambarBarang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/a_ logo.png"))); // NOI18N
        iv_gambarBarang.setMaximumSize(new java.awt.Dimension(260, 160));

        tv_namaBarang.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        tv_namaBarang.setForeground(Colors.blackTextColor);
        tv_namaBarang.setText("Coca Cola Zero Sugar Vanilla");

        b_kurang.setBackground(Colors.accentColor);
        b_kurang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ic_minus.png"))); // NOI18N
        b_kurang.setBorder(null);
        b_kurang.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        b_kurang.setFocusPainted(false);
        b_kurang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_kurangActionPerformed(evt);
            }
        });

        et_jumlah.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        et_jumlah.setForeground(Colors.blackTextColor);
        et_jumlah.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        et_jumlah.setText("1");
        et_jumlah.setBorder(new RoundedBorder(buttonRadius));

        b_tambah.setBackground(Colors.accentColor);
        b_tambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ic_plus.png"))); // NOI18N
        b_tambah.setBorder(null);
        b_tambah.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        b_tambah.setFocusPainted(false);
        b_tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_tambahActionPerformed(evt);
            }
        });

        b_pesan.setBackground(Colors.accentColor);
        b_pesan.setFont(new java.awt.Font("Product Sans Medium", 0, 16)); // NOI18N
        b_pesan.setForeground(new java.awt.Color(255, 255, 255));
        b_pesan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ic_cart-plus.png"))); // NOI18N
        b_pesan.setText("Pesan Barang");
        b_pesan.setBorder(null);
        b_pesan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        b_pesan.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ic_check.png"))); // NOI18N
        b_pesan.setFocusPainted(false);
        b_pesan.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        b_pesan.setIconTextGap(16);
        b_pesan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_pesanActionPerformed(evt);
            }
        });

        tv_namaBarangSubTitle.setFont(new java.awt.Font("Roboto Medium", 0, 11)); // NOI18N
        tv_namaBarangSubTitle.setForeground(Colors.greyTextColor);
        tv_namaBarangSubTitle.setText("NAMA MAKANAN");

        jLabel6.setFont(new java.awt.Font("Roboto Medium", 0, 11)); // NOI18N
        jLabel6.setForeground(Colors.greyTextColor);
        jLabel6.setText("VARIAN");

        jSeparator4.setForeground(Colors.borderColor);

        jSeparator2.setForeground(Colors.borderColor);

        b_closeDetailBarang.setBackground(new java.awt.Color(255, 255, 255));
        b_closeDetailBarang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ic_close_grey.png"))); // NOI18N
        b_closeDetailBarang.setBorder(null);
        b_closeDetailBarang.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        b_closeDetailBarang.setFocusPainted(false);
        b_closeDetailBarang.setOpaque(false);
        b_closeDetailBarang.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ic_close.png"))); // NOI18N
        b_closeDetailBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_closeDetailBarangActionPerformed(evt);
            }
        });

        jComboBox1.setBackground(new java.awt.Color(255, 255, 255));
        jComboBox1.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jComboBox1.setForeground(Colors.blackTextColor);
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pedas", "Sedang", "Original" }));
        jComboBox1.setToolTipText("");
        jComboBox1.setOpaque(false);

        javax.swing.GroupLayout detailBarangPanelLayout = new javax.swing.GroupLayout(detailBarangPanel);
        detailBarangPanel.setLayout(detailBarangPanelLayout);
        detailBarangPanelLayout.setHorizontalGroup(
            detailBarangPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detailBarangPanelLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(detailBarangPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator4)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tv_namaBarangSubTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(detailBarangPanelLayout.createSequentialGroup()
                        .addComponent(b_kurang, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(et_jumlah)
                        .addGap(18, 18, 18)
                        .addComponent(b_tambah, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(detailBarangPanelLayout.createSequentialGroup()
                        .addComponent(tv_detailTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(b_closeDetailBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator2)
                    .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(detailBarangPanelLayout.createSequentialGroup()
                        .addGroup(detailBarangPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tv_namaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(b_pesan, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(iv_gambarBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(24, 24, 24))
        );
        detailBarangPanelLayout.setVerticalGroup(
            detailBarangPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detailBarangPanelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(detailBarangPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tv_detailTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b_closeDetailBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addComponent(iv_gambarBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(tv_namaBarangSubTitle)
                .addGap(8, 8, 8)
                .addComponent(tv_namaBarang)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addGap(8, 8, 8)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(detailBarangPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(detailBarangPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(et_jumlah, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(b_kurang, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE))
                    .addComponent(b_tambah, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(b_pesan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );

        jSeparator1.setForeground(Colors.borderColor);
        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(entriBarangScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 1366, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(detailPesananPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(detailBarangPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(entriBarangScroll)
            .addComponent(detailBarangPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(detailPesananPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator1)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void b_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_tambahActionPerformed
        if (!isOrdered()) {
            int currentTotal = Integer.parseInt(et_jumlah.getText());
            int hargaBarang = selectedBarang.getHarga();
            
            currentTotal++;
            hargaBarang *= currentTotal;
            et_jumlah.setText(String.valueOf(currentTotal));
            b_pesan.setText("Rp. " + a_.convertCurrency(hargaBarang));
        }
    }//GEN-LAST:event_b_tambahActionPerformed
    
    private void b_pesanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_pesanActionPerformed
        Pesanan pesanan = new Pesanan();
        pesanan.setIdBarang(selectedBarang.getId());
        pesanan.setNamaBarang(selectedBarang.getNamaBarang());
        pesanan.setHargaBarang(selectedBarang.getHarga());
        pesanan.setJumlahBarang(Integer.parseInt(et_jumlah.getText()));
        addToCart(pesanan);
    }//GEN-LAST:event_b_pesanActionPerformed
    
    private void b_konfirmasiPesananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_konfirmasiPesananActionPerformed
        if (pesanans.isEmpty()) {
            JOptionPane.showMessageDialog(context, "Keranjang Anda masih kosong.\nAyo tambahkan beberapa barang!");
            return;
        }
        
        int dialog = JOptionPane.showConfirmDialog(context, "Konfirmasi Pesanan?", "Konfirmasi?", JOptionPane.YES_NO_OPTION);
        if (dialog == 2) {
            return;
        }
        
        int total = 0;
        total = pesanans.stream().map((_pesanan) -> _pesanan.getHargaBarang() * _pesanan.getJumlahBarang()).map((subTotal) -> subTotal).reduce(total, Integer::sum);
        
        Penjualan penjualan = new Penjualan();
        penjualan.setIdMeja(entriMeja.getId());
        penjualan.setIdPengguna(context.pengguna.getId());
        penjualan.setIdStatus(1);
        penjualan.setAtasNama(entriMeja.getAtasNama());
        penjualan.setTotal(total);
        
        int idPenjualan = penjualan.insert(connection);
        
        if (idPenjualan != -1) {
            boolean isSucceed = true;
            for (Pesanan _pesanan : pesanans) {
                DetailPenjualan detailPenjualan = new DetailPenjualan();
                detailPenjualan.setIdBarang(_pesanan.getIdBarang());
                detailPenjualan.setIdPenjualan(idPenjualan);
                detailPenjualan.setJumlahBarang(_pesanan.getJumlahBarang());
                
                if (!detailPenjualan.insert(connection)) {
                    isSucceed = false;
                }
            }
            
            if (isSucceed) {
                a_.showDialog(a_.DialogType.INSERT_SUCCESS);
                pesanans.clear();
                context.loadContent(new EntriPesananPanel(context, connection));
            } else {
                a_.showDialog(a_.DialogType.INSERT_ERROR);
            }
        } else {
            a_.showDialog(a_.DialogType.INSERT_ERROR);
        }
    }//GEN-LAST:event_b_konfirmasiPesananActionPerformed
    
    private void b_kurangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_kurangActionPerformed
        if (!isOrdered()) {
            int currentTotal = Integer.parseInt(et_jumlah.getText());
            int hargaBarang = selectedBarang.getHarga();
            
            if (currentTotal > 1) {
                currentTotal--;
                hargaBarang *= currentTotal;
                et_jumlah.setText(String.valueOf(currentTotal));
                b_pesan.setText("Rp. " + a_.convertCurrency(hargaBarang));
            }
        }
    }//GEN-LAST:event_b_kurangActionPerformed
    
    private void b_closeDetailBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_closeDetailBarangActionPerformed
        hideDetailBarang();
    }//GEN-LAST:event_b_closeDetailBarangActionPerformed
    
    private void b_closeDetailPesananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_closeDetailPesananActionPerformed
        hideDetailPesanan();
    }//GEN-LAST:event_b_closeDetailPesananActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton b_closeDetailBarang;
    private javax.swing.JButton b_closeDetailPesanan;
    private javax.swing.JButton b_konfirmasiPesanan;
    private javax.swing.JButton b_kurang;
    private javax.swing.JButton b_pesan;
    private javax.swing.JButton b_tambah;
    private javax.swing.JPanel detailBarangPanel;
    public javax.swing.JPanel detailPesananPanel;
    private javax.swing.JPanel entriBarangPanel;
    private javax.swing.JScrollPane entriBarangScroll;
    private javax.swing.JTextField et_jumlah;
    private javax.swing.JLabel iv_gambarBarang;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator32;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JList<Pesanan> l_daftarPesanan;
    private javax.swing.JLabel tv_detailTitle;
    private javax.swing.JLabel tv_namaBarang;
    private javax.swing.JLabel tv_namaBarangSubTitle;
    private javax.swing.JLabel tv_nomorMeja;
    // End of variables declaration//GEN-END:variables
}