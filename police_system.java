
package smart_system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class police_system extends javax.swing.JFrame {

    /**
     * Creates new form police_system
     */
    public police_system() {
        initComponents();
        this.setLocationRelativeTo(null);
        
        findData();
    }
    
    public Connection getConnection(){
        Connection con = null;
        
        try{
        con = DriverManager.getConnection("jdbc:mysql://localhost/final_project", "root","");
        }
        catch(SQLException e){
        System.out.println(e.getMessage());
        }
        return con;
     
    }
    
    public ArrayList<Data> ListData(String ValToSearch){
    ArrayList<Data> DataList = new ArrayList<Data>();
    
    Statement st;
    ResultSet rs;
    
    try{
        Connection con = getConnection();
        st = con.createStatement();
        String searchQuery = "SELECT * FROM road_crime WHERE NumberPlate like '%"+ValToSearch+"%' ";
        rs = st.executeQuery(searchQuery);
    
    Data myobj;
    while(rs.next())
    {
        myobj = new Data(
         rs.getString("NumberPlate"),
         rs.getString("Location"),
         rs.getTimestamp("Date_And_Time"),
         rs.getString("Status"));
         DataList.add(myobj);
    }
    }
    catch(SQLException e){
    System.out.println(e.getMessage());
    }
    
    return DataList;
    }
    
    public void findData()
    {
        ArrayList<Data> myobj2 = ListData(jText_Add.getText()); 
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"NumberPlate","Location","Date_And_Time","Status"});
        Object[] row = new Object[4];

        for(int i=0;i<myobj2.size();i++)
        {
            row[0] = myobj2.get(i).getNumberPlate();
            row[1] = myobj2.get(i).getLocation();
            row[2] = myobj2.get(i).getDate_and_Time();
            row[3] = myobj2.get(i).getStatus();
            model.addRow(row);
        }
        jTable_Search.setModel(model);
    }

    public void clear(){
        jText_Add.setText("");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jText_Add = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton1_search = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_Search = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(255, 255, 102));
        jPanel2.setLayout(null);

        jPanel1.setBackground(new java.awt.Color(0, 0, 51));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "LMPS SMART CCTV SYSTEM ", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Copperplate Gothic Bold", 1, 18), new java.awt.Color(255, 255, 0))); // NOI18N
        jPanel1.setLayout(null);

        jText_Add.setBackground(new java.awt.Color(255, 255, 153));
        jText_Add.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel1.add(jText_Add);
        jText_Add.setBounds(240, 60, 250, 40);

        jButton2.setBackground(new java.awt.Color(255, 255, 0));
        jButton2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 0, 51));
        jButton2.setText("ADD");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2);
        jButton2.setBounds(510, 60, 70, 40);

        jButton1_search.setBackground(new java.awt.Color(255, 255, 0));
        jButton1_search.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1_search.setForeground(new java.awt.Color(0, 0, 51));
        jButton1_search.setText("SEARCH");
        jButton1_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1_searchActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1_search);
        jButton1_search.setBounds(600, 60, 100, 40);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smart_system/newpackage/LMPS.png"))); // NOI18N
        jPanel1.add(jLabel1);
        jLabel1.setBounds(30, 30, 120, 120);

        jPanel2.add(jPanel1);
        jPanel1.setBounds(0, 0, 720, 180);

        jTable_Search.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NUMBER PLATE", "LOCATION", "DATE & TIME", "STATUS"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable_Search.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setViewportView(jTable_Search);

        jPanel2.add(jScrollPane1);
        jScrollPane1.setBounds(22, 190, 680, 230);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 719, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 441, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (jText_Add.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter Field", "Error", 0);
            } else {
                String Addtext = jText_Add.getText();
                Timestamp AddDate = new Timestamp(System.currentTimeMillis());
                
                Connection conn = null;
                PreparedStatement pstmt = null;
                Statement st;
                ResultSet rs;
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    conn = DriverManager.getConnection("jdbc:mysql://localhost/final_project", "root","");
                    String query = "INSERT INTO road_crime(NumberPlate, Location, Date_And_Time, Status) VALUES (?,?,?,?)";
                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, Addtext);
                    pstmt.setString(2, "");
                    pstmt.setTimestamp(3, AddDate);
                    pstmt.setString(4, "");
                    
                    int status = pstmt.executeUpdate();
                    if (status > 0) {
                        JOptionPane.showMessageDialog(null, "Record is inserted successfully !!!");
                        st = conn.createStatement();
                        String select_query = "select * from road_crime where NumberPlate='"+jText_Add.getText()+"' ";
                        rs = st.executeQuery(select_query);
                        while(rs.next())
                        {
                            
                            String number = rs.getString("NumberPlate");
                            String locate = rs.getString("Location");
                            Timestamp times = rs.getTimestamp("Date_And_Time");
                            String time = times.toString();
                            String found = rs.getString("Status");
                            
                            String GetData[] = {number, locate, time, found};
                            DefaultTableModel object = (DefaultTableModel)jTable_Search.getModel();
                            object.addRow(GetData);
                            
                        }
                    }
                } catch (ClassNotFoundException ex) {
                    // todo show error message or throw
                } catch (Exception f) {
                    // todo show error message or throw
                } finally {
                    if (pstmt != null) {
                        try {
                            pstmt.close();
                        } catch (SQLException s) {
                            // todo show error message
                        }
                    }
                    if (conn != null) {
                        try {
                            conn.close();
                        } catch (SQLException s2) {
                            // todo show error message
                        } 
                    }
                }
            }
        clear();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1_searchActionPerformed
        findData();
        clear();
    }//GEN-LAST:event_jButton1_searchActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(police_system.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(police_system.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(police_system.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(police_system.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        
        police_system app = new police_system();
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                app.setVisible(true);
            }
        });//thread that make project to refresh every 5 seconds
          for(int i = 1;;i++){
            try
            {
                System.out.println("Thread " + i);
                Thread.sleep(5 * 1000);
                app.findData();
            }
            catch(InterruptedException ie)
            {
                Thread.currentThread().interrupt();
            }
        }
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1_search;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable_Search;
    private javax.swing.JTextField jText_Add;
    // End of variables declaration//GEN-END:variables
}
