/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.ui.widget;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

/**
 * 过滤文本输入框
 * <p>说明:</p>
 * 功能增强型文本输入框,增加过滤功能
 * @author DuanYong
 * @since 2013-4-11 上午9:07:52
 * @version 1.0
 */
public class JFilterTextField extends JTextField{
	private static final long serialVersionUID = 1L;
	/**原始数据*/
    private List<Object> allData;
    /**当前数据*/
    private List<Object> currentData;
    /**menu*/
    private JPopupMenu menu;
    /**JList*/
    private JList list;
    /**是否过滤*/
    private boolean shouldFilter = true;
    /**是否显示*/
    private boolean showMenu = true;
    /**滚动条*/
    private JScrollBar jScrollBar;

    public JFilterTextField(List<Object> allData) {
        super();
        this.allData = allData;
        currentData = new ArrayList<Object>(allData);       
        initComponents();
        addKeyListener(keyListener);
        addFocusListener(focusListener);
        addMouseListener(textFieldMouseListener);
        getDocument().addDocumentListener(documentListener);
    }
     /**
      * 组件初始化
      * <p>方法说明:</>
      * 组件初始化
      * @author DuanYong
      * @since 2013-4-11 下午2:19:17
      * @version 1.0
      * @exception
      */
    private void initComponents() {
        menu = new JPopupMenu();
        menu.setInvoker(this);
        menu.setBorder(BorderFactory.createEmptyBorder());
        menu.setInheritsPopupMenu(true);
        menu.setLightWeightPopupEnabled(true);

        list = new JList();
        list.setFixedCellHeight(20);
        list.setVisibleRowCount(5);
        list.setModel(new ImitateListModel());
        list.addMouseListener(listMouseListener);
        JScrollPane jScrollPane = new JScrollPane(list);
        jScrollBar = jScrollPane.getVerticalScrollBar();//获得垂直滚动条  
        menu.add(jScrollPane);
    }
    /**
     * DefaultListModel
     * <p>说明:</p>
     * DefaultListModel
     * @author DuanYong
     * @since 2013-4-11 下午2:19:53
     * @version 1.0
     */
    private class ImitateListModel extends DefaultListModel {
        private static final long serialVersionUID = 1L;

        @Override
        public int getSize() {
            return currentData.size();
        }

        @Override
        public Object getElementAt(int index) {
            return currentData.get(index);
        }
    }
   /**
    * 过滤文本
    * <p>方法说明:</>
    * 根据输入的字符查找原始数据，并把找到的数据放到当前数据集合中
    * 如果当前数据集合不为空则展示下拉列表框
    * @author DuanYong
    * @since 2013-4-11 下午2:20:40
    * @version 1.0
    * @exception 
    * @param filterStr
    */
    private void filterValues(String filterStr) {
        currentData.clear();
        if (StringUtils.isBlank(filterStr)) {
            currentData.addAll(allData);
        } else {
            for (Iterator<Object> it = allData.iterator(); it.hasNext();) {
                Object obj = it.next();
                String sValue = obj == null ? "" : obj.toString();
                if (sValue.toUpperCase().startsWith(filterStr.toUpperCase())) {
                    currentData.add(obj);
                }
            }
        }
        list.setModel(new ImitateListModel());
        list.setSelectedIndex(-1);
        if(!CollectionUtils.isEmpty(currentData)){
        	showPopup();
        }
        if(CollectionUtils.isEmpty(currentData)){
        	menu.setVisible(false);
        }
    }
    /**
     * 展示下拉列表框
     * <p>方法说明:</>
     * 在当前输入框下方展示
     * @author DuanYong
     * @since 2013-4-11 下午2:23:06
     * @version 1.0
     * @exception
     */
    private void showPopup() {
    	if(this.isShowing()){
    		 Rectangle bounds = getBounds();
    	        Point location = this.getLocationOnScreen();
    	        menu.setVisible(false);
    	        int height = 0;
    	        if(this.currentData.size() > list.getVisibleRowCount()){
    	        	height = list.getFixedCellHeight() * list.getVisibleRowCount() + 10;
    	        }else{
    	        	height = list.getFixedCellHeight() * this.currentData.size() + 10;
    	        }
    	        menu.setPopupSize(bounds.width, height);
    	        menu.setLocation(location.x, location.y + bounds.height);

    	        menu.setVisible(true);
    	        requestFocus();
    	}
       
    }
   /**
    * DocumentListener
    */
    private DocumentListener documentListener = new DocumentListener() {

        public void removeUpdate(DocumentEvent e) {
            if (shouldFilter) {
                String text = getText();
                filterValues(text);
            }

        }

        public void insertUpdate(DocumentEvent e) {
            if (shouldFilter) {
                String text = getText();
                filterValues(text);
            }

        }

        public void changedUpdate(DocumentEvent e) {
        	
        }
    };
    /**
     * MouseListener 当前输入框鼠标监听事件
     * 控制下拉列表框展示和隐藏：当点击输入框时, 如果是显示就隐藏,如果是隐藏状态就展示
     */
    private MouseListener textFieldMouseListener = new MouseListener(){

		@Override
		public void mouseClicked(MouseEvent arg0) {
			if (showMenu) {
                filterValues(getText());
                showMenu = false;
            }else{
                menu.setVisible(false);
                showMenu = true;
            }
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {

		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
    	
    };
    /**
     * FocusListener 当前输入框焦点监听事件
     * 当前输入框失去焦点且获取焦点对象不是list时，隐藏下拉列表框
     * 
     */
    private FocusListener focusListener = new FocusListener(){

    	@Override
		public void focusLost(FocusEvent arg0) {
    		if(arg0.getOppositeComponent()!=null&&!"javax.swing.JRootPane".equals(arg0.getOppositeComponent().getClass().getName())){
	    		if(!list.equals(arg0.getOppositeComponent())){
	    			shouldFilter = true;
	        		showMenu = true;
	    			menu.setVisible(false);
	    		}
    		}
		}
		
		@Override
		public void focusGained(FocusEvent arg0) {
			
		}
    	
    };
    /**
     * KeyListener 按键监听事件
     * 当前数据集合为空时 隐藏下拉列表框
     */
    private KeyListener keyListener = new KeyListener() {

        public void keyTyped(KeyEvent e) {
        }

        public void keyReleased(KeyEvent e) {

        }

        public void keyPressed(KeyEvent e) {
            String text = getText();
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                shouldFilter = false;
                if (!menu.isVisible()) {
                    filterValues(text);
                } else {
                    int index = list.getSelectedIndex();
                    index--;
                    index = index < 0 ? list.getModel().getSize() - 1 : index;

                    list.setSelectedIndex(index);
                    Object obj = list.getModel().getElementAt(index);
                    setText(obj == null ? "" : obj.toString());
                    setJScrollBarLocation(index);
                }
                shouldFilter = true;

            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                shouldFilter = false;
                if (!menu.isVisible()) {
                    filterValues(text);
                } else {
                    int index = list.getSelectedIndex();
                    index++;
                    index = index >= list.getModel().getSize() ? 0 : index;

                    list.setSelectedIndex(index);
                    Object obj = list.getModel().getElementAt(index);
                    setText(obj == null ? "" : obj.toString());
                    setJScrollBarLocation(index);
                }

                shouldFilter = true;
            } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                shouldFilter = false;
                if (menu.isVisible()) {
                    int index = list.getSelectedIndex();
                    if (index < 0) {
                        return;
                    }

                    Object obj = list.getModel().getElementAt(index);
                    setText(obj == null ? "" : obj.toString());
                    menu.setVisible(false);
                }

                shouldFilter = true;
            }
//            if(CollectionUtils.isEmpty(currentData)){
//            	menu.setVisible(false);
//            }
        }
    };
    /**
     * 设置滚动条位置
     * <p>方法说明:</>
     * <li></li>
     * @author DuanYong
     * @since 2013-10-29 下午3:18:57
     * @version 1.0
     * @exception 
     * @param index
     */
    private void setJScrollBarLocation(int index){
    	Point p = list.indexToLocation(index);
    	if(null != p){
    		jScrollBar.setValue(p.y);
    	}
    }
    /**
     * MouseAdapter 单击列表框中数据响应事件
     */
    private MouseAdapter listMouseListener = new MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent e) {
            int index = list.getSelectedIndex();
            Object obj = list.getModel().getElementAt(index);
            setText(obj == null ? "" : obj.toString());
            menu.setVisible(false);
        }
    };
    
  

    public static void main(String[] args) {
    	JFrame frame = new JFrame();
    	String[] values = new String[] { "ab", "ac", "ad", "bc", "bd", "cc" };
    	JTextField txt=new JFilterTextField(new ArrayList<Object>(Arrays.asList(values)));
    	frame.getContentPane().add(txt);
		frame.setSize(400, 80);
		frame.setVisible(true);
    }
}
