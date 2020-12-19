package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Random;

/*
//**输入**
public int tCiDao,tStart;//跨越磁道时间tCiDao    启动时间tStart
public  int rRound;//转速
public  int nShanQu,nByte;//每磁道的扇区数 每扇区中字节数
public int ranCiDao,ranFangXiang;//当前随机的磁道   随机的方向
public  int number=10;//要求访问序列的个数
public  int readNum=1000;//每次读取字节数
public  int[] track=new int[number];//请求访问磁道序列
public int[] RequestTime=new int [number];//磁道请求访问的时间
    public int[] Threadnumber=new int[number];//提出请求的线程
     public int[] Threadnumber=new int[number];//提出请求的线程   
 
//**输出**
public int sum=0;//磁盘移动总和
public int tFind=0;//寻道时间
public int tAvgRound=0;//平均旋转延迟时间
public int tComeIn=0;//传输时间
public int[] trackOut=new int[number];//运用算法后的输出序列

 */

public class Controller {
    //实例化数据类
    public ostest cgl = new ostest();


    @FXML
    private AnchorPane rootLayout;
    //输入界面字段创建
    public TextField inserttime1;
    public TextField insertspeed;
    public TextField insertsector;
    public TextField inserttime2;
    public TextField insertbyte;
    public Button buttonreset;
    public TextField inserttrack;
    public TextField insertdirection;
    public Button buttonrandom;
    public Button FCFSbutton;
    public Button SSTFbutton;
    public Button SCANbutton;
    public Button LOOKbutton;

    //输出界面字段创建
    public Label trackl0;
    public Label trackl1;
    public Label trackl2;
    public Label trackl3;
    public Label trackl4;
    public Label trackl5;
    public Label trackl6;
    public Label trackl7;
    public Label trackl8;
    public Label trackl9;
    public Label timel0;
    public Label timel1;
    public Label timel2;
    public Label timel3;
    public Label timel4;
    public Label timel5;
    public Label timel6;
    public Label timel7;
    public Label timel8;
    public Label timel9;
    public Label threadl0;
    public Label threadl1;
    public Label threadl2;
    public Label threadl3;
    public Label threadl4;
    public Label threadl5;
    public Label threadl6;
    public Label threadl7;
    public Label threadl8;
    public Label threadl9;
    public Label trackoutl0;
    public Label trackoutl1;
    public Label trackoutl2;
    public Label trackoutl3;
    public Label trackoutl4;
    public Label trackoutl5;
    public Label trackoutl6;
    public Label trackoutl7;
    public Label trackoutl8;
    public Label trackoutl9;
    public Label outl1;
    public Label outl2;
    public Label outl3;
    public Label outl4;


    public Button buttonback;

    //清除已输入内容函数
    public void insertreset(ActionEvent event) {
        inserttime1.clear();
        inserttime2.clear();
        insertspeed.clear();
        insertsector.clear();
        insertbyte.clear();
    }

    //点击按钮时随机生成方向和当前磁道函数
    public void insertrandom(ActionEvent actionEvent) {
        Random r = new Random();
        inserttrack.clear();
        insertdirection.clear();
        int track = r.nextInt(200);
        inserttrack.appendText(String.valueOf(track));
        int direction = r.nextInt(200) % 2;
        //向内0 向外1
        if (direction == 0)
            insertdirection.appendText("内");
        else insertdirection.appendText("外");
    }

    //默认产生随机方向和磁道函数
    public void firstrandom() {
        Random r = new Random();
        inserttrack.clear();
        insertdirection.clear();
        int track = r.nextInt(200);
        inserttrack.appendText(String.valueOf(track));
        int direction = r.nextInt(200) % 2;
        //向内0 向外1
        if (direction == 0)
            insertdirection.appendText("内");
        else insertdirection.appendText("外");
    }

    //输入非法警告函数
    public void f_alert_informationDialog(String p_header, String p_message) {
        Alert _alert = new Alert(Alert.AlertType.INFORMATION);
        _alert.setTitle("错误");
        _alert.setHeaderText(p_header);
        _alert.setContentText(p_message);
        _alert.show();
    }

    //传入输入参数函数,返回true则为参数合法
    public boolean setsubmit() {
        try {
            cgl.tCiDao = Integer.parseInt(inserttime1.getText());
            cgl.tStart = Integer.parseInt(inserttime2.getText());
            cgl.rRound = Integer.parseInt(insertspeed.getText());
            cgl.nShanQu = Integer.parseInt(insertsector.getText());
            cgl.nByte = Integer.parseInt(insertbyte.getText());
            cgl.ranCiDao = Integer.parseInt(inserttrack.getText());
        } catch (Exception e) {
            f_alert_informationDialog("", "输入非法");
            return false;
        }
        if (insertdirection.getText().equals("内"))
            cgl.ranFangXiang = 0;
        else cgl.ranFangXiang = 1;
        return true;
    }

    //FCFS
    public void FCFSsubmit(ActionEvent actionEvent) {
        if (!setsubmit())
            return;
        cgl.FCFS();
        giveanswer();
    }

    //SSTF
    public void SSTFsubmit(ActionEvent actionEvent) {
        if (!setsubmit())
            return;
        cgl.SSTF();
        giveanswer();
    }

    //SCAN
    public void SCANsubmit(ActionEvent actionEvent) {
        if (!setsubmit())
            return;
        cgl.SCAN();
        giveanswer();
    }

    //LOOK
    public void LOOKsubmit(ActionEvent actionEvent) {
        if (!setsubmit())
            return;
        cgl.LOOK();
        giveanswer();
    }

    //将界面切换为结果界面
    public void giveanswer() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL location = getClass().getResource("answer.fxml");
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        try {
            Parent root = fxmlLoader.load();
            Stage primaryStage = (Stage) rootLayout.getScene().getWindow();
            primaryStage.setTitle("基于磁头引臂调度算法的磁盘I/O访问的调度及其时间参数计算的模拟实现");
            primaryStage.setScene(new Scene(root, 600, 400));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Controller controller = fxmlLoader.getController();
        controller.trackl0.setText(String.valueOf(cgl.track[0]));
        controller.trackl1.setText(String.valueOf(cgl.track[1]));
        controller.trackl2.setText(String.valueOf(cgl.track[2]));
        controller.trackl3.setText(String.valueOf(cgl.track[3]));
        controller.trackl4.setText(String.valueOf(cgl.track[4]));
        controller.trackl5.setText(String.valueOf(cgl.track[5]));
        controller.trackl6.setText(String.valueOf(cgl.track[6]));
        controller.trackl7.setText(String.valueOf(cgl.track[7]));
        controller.trackl8.setText(String.valueOf(cgl.track[8]));
        controller.trackl9.setText(String.valueOf(cgl.track[9]));
        controller.timel0.setText(String.valueOf(cgl.RequestTime[0]));
        controller.timel1.setText(String.valueOf(cgl.RequestTime[1]));
        controller.timel2.setText(String.valueOf(cgl.RequestTime[2]));
        controller.timel3.setText(String.valueOf(cgl.RequestTime[3]));
        controller.timel4.setText(String.valueOf(cgl.RequestTime[4]));
        controller.timel5.setText(String.valueOf(cgl.RequestTime[5]));
        controller.timel6.setText(String.valueOf(cgl.RequestTime[6]));
        controller.timel7.setText(String.valueOf(cgl.RequestTime[7]));
        controller.timel8.setText(String.valueOf(cgl.RequestTime[8]));
        controller.timel9.setText(String.valueOf(cgl.RequestTime[9]));
        controller.threadl0.setText(String.valueOf(cgl.Threadnumber[0]));
        controller.threadl1.setText(String.valueOf(cgl.Threadnumber[1]));
        controller.threadl2.setText(String.valueOf(cgl.Threadnumber[2]));
        controller.threadl3.setText(String.valueOf(cgl.Threadnumber[3]));
        controller.threadl4.setText(String.valueOf(cgl.Threadnumber[4]));
        controller.threadl5.setText(String.valueOf(cgl.Threadnumber[5]));
        controller.threadl6.setText(String.valueOf(cgl.Threadnumber[6]));
        controller.threadl7.setText(String.valueOf(cgl.Threadnumber[7]));
        controller.threadl8.setText(String.valueOf(cgl.Threadnumber[8]));
        controller.threadl9.setText(String.valueOf(cgl.Threadnumber[9]));
        controller.trackoutl0.setText(String.valueOf(cgl.trackOut[0]));
        controller.trackoutl1.setText(String.valueOf(cgl.trackOut[1]));
        controller.trackoutl2.setText(String.valueOf(cgl.trackOut[2]));
        controller.trackoutl3.setText(String.valueOf(cgl.trackOut[3]));
        controller.trackoutl4.setText(String.valueOf(cgl.trackOut[4]));
        controller.trackoutl5.setText(String.valueOf(cgl.trackOut[5]));
        controller.trackoutl6.setText(String.valueOf(cgl.trackOut[6]));
        controller.trackoutl7.setText(String.valueOf(cgl.trackOut[7]));
        controller.trackoutl8.setText(String.valueOf(cgl.trackOut[8]));
        controller.trackoutl9.setText(String.valueOf(cgl.trackOut[9]));
        controller.outl1.setText("引臂移动量："+String.valueOf(cgl.sum)+"    "+"寻道时间："+String.valueOf(cgl.tFind)+"ms");
        controller.outl2.setText("平均旋转延迟时间："+String.valueOf(cgl.tAvgRound)+"ms");
        controller.outl3.setText("传输时间："+String.valueOf(cgl.tComeIn)+"ms");
        controller.outl4.setText(" ");
    }

    //返回按钮执行方法,返回输入界面
    public void insertback(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL location = getClass().getResource("sample.fxml");
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        try {
            Parent root = fxmlLoader.load();
            Stage primaryStage = (Stage) rootLayout.getScene().getWindow();
            primaryStage.setTitle("基于磁头引臂调度算法的磁盘I/O访问的调度及其时间参数计算的模拟实现");
            primaryStage.setScene(new Scene(root, 600, 400));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Controller controller = fxmlLoader.getController();
        controller.firstrandom();
    }
}

/*
class insert {
    int a, b, c, d, e, f, g;

    void testinsert(String a1, String b1, String c1, String d1, String e1, String f1, String g1) {
        try {
            a = Integer.parseInt(a1);
            b = Integer.parseInt(b1);
            c = Integer.parseInt(c1);
            d = Integer.parseInt(d1);
            e = Integer.parseInt(e1);
            f = Integer.parseInt(f1);
        } catch (Exception e) {
            f_alert_informationDialog("", "输入非法");
            return;
        }
        if (g1.equals("内"))
            g = 0;
        else g = 1;
        System.out.println(a + " " + b + " " + c + " " + d + " " + e + " " + f + " " + g);
    }

    public void f_alert_informationDialog(String p_header, String p_message) {
        Alert _alert = new Alert(Alert.AlertType.INFORMATION);
        _alert.setTitle("错误");
        _alert.setHeaderText(p_header);
        _alert.setContentText(p_message);
        _alert.show();
    }
}*/
