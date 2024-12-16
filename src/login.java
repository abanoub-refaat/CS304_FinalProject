import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class login extends JFrame {
    //declearation
    private JTextField username;
    private JLabel Lusername;
    private JButton btnSignin,btnClose;
    public login(){
        setTitle("login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,400);
        setLocationRelativeTo(null);
        setLayout(null);

        //creation
        username=new JTextField();
        Lusername=new JLabel("USERNAME");
        btnSignin=new JButton("SignIn");
        btnClose=new JButton("Close");


        //placement
        Lusername.setBounds(20,120,260,20);
        username.setBounds(20,150,260,20);
        btnSignin.setBounds(20,280,100,20);
        btnClose.setBounds(140,280,100,20);


        //add
        add(username);
        add(Lusername);
        add(btnClose);
        add(btnSignin);
        setVisible(true);


        //actions

         //close button
        btnClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        //signin button
        btnSignin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Susername= username.getText();

                if (Susername.compareToIgnoreCase("admain")==0){
                    //sucsses
                    showMSG("welcome");
                    dispose();
                }
                else{
                    //error username
                    showMSG("invaled username");
                }
            }
        });

    }
    public void showMSG(String msg){
        if (msg=="welcome"){
            JOptionPane.showMessageDialog(this,msg,"Appliction",JOptionPane.WARNING_MESSAGE);
        }
        else{
            JOptionPane.showMessageDialog(this,msg,"Appliction",JOptionPane.ERROR_MESSAGE);
        }

    }


}
