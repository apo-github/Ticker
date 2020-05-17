package com.company;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;

public class Containts extends JPanel implements ActionListener,MouseMotionListener,MouseListener,KeyListener{
    MainTicker mticker;
    Timer timer;
    String str = "声は聞こえますでしょうか?";
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();//デスクトップのサイズを取得
    int width = screenSize.width;//デスクトップの幅を取得
    int height = screenSize.height;//デスクトップの高さを取得
    int height5 = height/5;

    JLabel jl;
    int jlx = width;
    int jly = height/7;//文字の位置を中心にした調整
    int fontSize = 160;

    boolean click = false;//クリックしたときにカラーパレット表示
    boolean text = false;//クリックしたときにカラーパレット表示
    Color color;

    private final Point startPt = new Point();//windowつかんだ時の初期値
    private Window window;

    private final int SPEED = 5;
    Scanner scan = new Scanner(System.in);



    public Containts(MainTicker ticker){
        this.mticker = ticker;//メインフレームを取得
        this.setPreferredSize(new Dimension(width, height5));
        this.setOpaque(false);//透明にする(再描画の際falseにしないと前の映像が残る)
        this.setFocusable(true);
        this.addKeyListener(this);

        timer = new Timer(16,this);
        timer.start();
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if(text){
            timer.stop();
            System.out.println("文字を入力してください");
            Scanner scan = new Scanner(System.in);
            str = scan.next();//文字の入力

            text = false;
            timer.start();
        }

        if (jlx <= -width*2){//消える位置の設定
            jlx = 1920;
        }
        jlx -= SPEED;
        repaint();
    }

    public void paint(Graphics g){//paintは書き換えるときに必ずupdateを呼ぶ仕様である
        removeAll();

        //////////アンチエイリアス(文字がきれいに見える。だけ)//////
        Graphics2D g2 = (Graphics2D)g;
        //図形や線のアンチエイリアシングの有効化
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //文字描画のアンチエイリアシングの有効化
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        /////////アンチエイリアスここまで///////////////////////////

        g2.setFont(new Font("Ricty Diminished",Font.BOLD,fontSize));
        //カラーパレット
        if(click) {
            JColorChooser cframe = new JColorChooser();//カラーパレッドの表示
            click = false;//1つだけパレット作成(これをしないと無限にカラーパレットが複製される)
            color = cframe.showDialog(mticker, "色選択", Color.BLACK);
            if (color != null) {
                this.setBackground(color);
            }


        }
        g2.setColor(color);//色を変える
        g2.drawString(str,jlx,jly);

    }

    public void update(Graphics g){//バッファリング処理
        paint(g);
    }

    public void textField(){
    }

    ///////////マウスでウインドウをつかんで移動の処理/////////////////
    @Override
    public void mousePressed(MouseEvent e) {
        Object o = e.getSource();
        if (o instanceof Window){//oがWindowでならばTrue
            window = (Window) o;//oをウインドウ型へ
        }else if(o instanceof JComponent){
            window = SwingUtilities.windowForComponent(e.getComponent());//自分の上位ウインドウを取得する(この場合Frameが帰返ってくる)
        }
        startPt.setLocation(e.getPoint());
    }

    @Override
    public void mouseDragged(MouseEvent e) {//フィールドの移動
        if (window != null){
            Point eventLocationOnScreen = e.getLocationOnScreen();
            window.setLocation(eventLocationOnScreen.x - startPt.x, eventLocationOnScreen.y - startPt.y);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        click = true;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_F12){
            text = true;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
    @Override
    public void mouseReleased(MouseEvent e) {

    }
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyReleased(KeyEvent e) {

    }


    /**
        TODO
        GUIテキストフィールドを作る
     */
    class TextFrame extends JFrame{//内部クラス(使ってない。。だれか作って)
        public TextFrame() {
            this.setTitle("テキストフィールド");
            this.setVisible(true);
            this.setBounds(100, 100, 500, 100);
            this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
            JTextField textField = new JTextField(30);//入力できる文字数を指定
            text = false;
            textField.setToolTipText("文字を入力してください。");//ツールチップの表示(機能的には必須ではない)
            str = textField.getText();//テキストフィールドの文字取得
            JPanel p = new JPanel();
            p.add(textField);
            this.add(p);
        }
    }


}

